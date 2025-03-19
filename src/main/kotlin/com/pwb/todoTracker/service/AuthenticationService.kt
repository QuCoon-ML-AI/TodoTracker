//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.240643800
package com.pwb.todoTracker.service

import com.pwb.todoTracker.repository.database.UserRepository
import com.pwb.todoTracker.repository.database.UserOtpRepository
import com.pwb.todoTracker.model.entity.UserOtp
import com.pwb.todoTracker.model.entity.User
import com.pwb.todoTracker.repository.database.LoginHistoryRepository
import com.pwb.todoTracker.repository.database.RoleRepository
import com.pwb.todoTracker.repository.database.RolePrivilegeRepository
import com.pwb.todoTracker.repository.database.PrivilegeRepository
import com.pwb.todoTracker.repository.database.ModuleRepository
import com.pwb.todoTracker.model.entity.LoginHistory
import com.pwb.todoTracker.model.entity.Privilege
import com.pwb.todoTracker.model.entity.Role
import com.pwb.todoTracker.model.entity.RolePrivilege
import com.pwb.todoTracker.model.entity.Module
import org.pwb.utility.general.toSqlDateTime
import org.pwb.utility.constant.ResponseConstant
import org.pwb.utility.model.response.BaseResponse
import org.springframework.stereotype.Service
import com.pwb.todoTracker.utility.Environment
import org.pwb.utility.constant.GSON
import com.pwb.todoTracker.model.response.*
import com.pwb.todoTracker.model.request.*
import org.json.JSONObject
import org.pwb.utility.jwt.JwtUtil
import org.pwb.utility.exception.*
import org.pwb.utility.binary.bcryptHash
import org.pwb.utility.binary.bcryptValidate
import org.pwb.utility.messaging.sendAsEmailTo
import org.pwb.utility.model.request.EmailModel
import java.time.LocalDateTime
import java.time.ZoneId
import org.pwb.utility.model.request.ModulePrivilege


@Service
class AuthenticationService(
   private val userRepository: UserRepository,
   private val userService: UserService,
   private val loginHistoryRepository: LoginHistoryRepository,
   private val userOtpRepository: UserOtpRepository,
   private val roleRepository: RoleRepository,
   private val rolePrivilegeRepository: RolePrivilegeRepository,
   private val moduleRepository: ModuleRepository,
   private val privilegeRepository: PrivilegeRepository,
   private val environment: Environment
){
   	fun initiateEnrollment(request:InitiateEnrollmentRequest):BaseResponse {
		val user = userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
		if (user != null && user.userStatus != "PENDING")
			throw DuplicateTransactionException("user already exists!")
		val createUpdateResponse = if (user?.userStatus == "PENDING") {
			val updateResponse = userService.update(GSON.fromJson(GSON.toJson(request), UserUpdateRequest::class.java).apply {
				userPassword = request.userPassword.bcryptHash()
				userId = user.userId
				userStatus = "PENDING"
			})
			if (updateResponse.responseCode == ResponseConstant.SUCCESS.responseCode) 1 else 0
		} else {
			userRepository.create(GSON.fromJson(GSON.toJson(request), User::class.java)
			.apply { 
				userPassword = userPassword.bcryptHash()
				userStatus = "PENDING"
			})
		}
		if (createUpdateResponse < 1) throw FailedToCreateRecord()
		val otp = generateOtp(request.userEmail!!)
		EmailModel(
			authKey=System.getenv("REPLACEMENT_KEY"),
			body="Please use the 6 digit code to complete your action. " +
					"This OTP is only valid for 5 minutes. Do not disclose your OTP to anyone or staff. $otp",
			fromEmail="support@qoonity.com",
			fromName=environment.applicationName,
			toEmail=request.userEmail,
			subject="Enrollment",
		).sendAsEmailTo()
		return BaseResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = "OTP has been sent."
		)
	}

	fun completeEnrollment(request: CompleteEnrollmentRequest):BaseResponse {
		val user = userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
			?: throw UnableToLocateRecordException("User has not initiated enrollment")
		if (user.userStatus == "ACTIVE") throw ActivityAlreadyPerformedException("User is already active")
		validateOtp(request.otp!!, request.userEmail!!)
		userRepository.update(user.apply { userStatus = "ACTIVE" })
		return ResponseConstant.SUCCESS
	}

	fun login(request: LoginRequest):LoginResponse {
		val user = userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
			?: throw UnableToLocateRecordException("User has not initiated enrollment")
		if (user.userStatus == "PENDING") throw BadRequestException("Invalid Credentials")
		if (user.userStatus != "ACTIVE") throw BadRequestException("request failed with user status: ${user.userStatus}")
		if (!(request.userPassword.bcryptValidate(user.userPassword))) throw BadRequestException("Invalid Credentials")
		loginHistoryRepository.create(LoginHistory(
			loginHistoryUsername=request.userEmail,
			loginHistoryIpAddress=environment.ipAddress,
			loginHistoryDeviceId=request.deviceId,
			loginHistoryLongitude=request.longitude,
			loginHistoryLatitude=request.latitude,
			loginHistoryStatus="ACTIVE",
		))
		environment.userInfo = GSON.fromJson(GSON.toJson(user), LoginResponse::class.java).apply {
			responseCode = ResponseConstant.SUCCESS.responseCode
			responseMessage = ResponseConstant.SUCCESS.responseMessage
		}
		val rolePrivileges = rolePrivilegeRepository.readByRolePrivilegeRoleId(environment.userInfo?.userEmail?:0)
			.map { ModulePrivilege(privilege = it.rolePrivilegePrivilegeCode!!.split("|")[1], module = it.rolePrivilegePrivilegeCode!!.split("|")[0]) }
		return environment.userInfo!!.apply { token = JwtUtil().generateJwt(request.userEmail!!, JSONObject(GSON.toJson(environment.userInfo)),
			rolePrivileges
		); privileges = rolePrivileges.map { "${it.module}|${it.privilege}" } }
	}

	fun resendOtp(request:InitiatePasswordResetRequest):BaseResponse {
		userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
			?:throw DuplicateTransactionException("user does not already exists.")
		val otp = generateOtp(request.userEmail!!)
		EmailModel(
			authKey=System.getenv("REPLACEMENT_KEY"),
			body="Please use the 6 digit code to complete your action. " +
					"This OTP is only valid for 5 minutes. Do not disclose your OTP to anyone or staff.<br> $otp",
			fromEmail="support@qoonity.com",
			fromName=environment.applicationName,
			toEmail=request.userEmail,
			subject="Enrollment",
		).sendAsEmailTo()
		return BaseResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = "OTP has been sent."
		)
	}

	fun initiatePasswordReset(request:InitiatePasswordResetRequest):BaseResponse {
		userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
			?:throw DuplicateTransactionException("user does not already exists.")
		val otp = generateOtp(request.userEmail!!)
		EmailModel(
			authKey=System.getenv("REPLACEMENT_KEY"),
			body="Please use the 6 digit code to complete your action. " +
					"This OTP is only valid for 5 minutes. Do not disclose your OTP to anyone or staff.<br> $otp",
			fromEmail="support@qoonity.com",
			fromName=environment.applicationName,
			toEmail=request.userEmail,
			subject="Enrollment",
		).sendAsEmailTo()
		return BaseResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = "OTP has been sent."
		)
	}

	fun completePasswordReset(request: CompletePasswordResetRequest):BaseResponse {
		val user = userRepository.readByUserEmail(request.userEmail!!).firstOrNull()
			?: throw UnableToLocateRecordException("user does not already exists.")
		validateOtp(request.otp!!, request.userEmail!!)
		userRepository.update(user.apply {
			userStatus = "ACTIVE"
			userPassword = request.userPassword.bcryptHash()
		})
		return ResponseConstant.SUCCESS
	}

	fun changePassword(request: ChangePasswordRequest):BaseResponse {
		val user = userRepository.readByUserEmail(request.username!!).firstOrNull()
			?: throw UnableToLocateRecordException("user does not already exists.")
		if (!(request.oldPassword.bcryptValidate(user.userPassword))) throw BadRequestException("Incorrect Old Password")
		userRepository.update(user.apply {
			userStatus = "ACTIVE"
			userPassword = request.userPassword.bcryptHash()
		})
		return ResponseConstant.SUCCESS
	}

	fun generateOtp(username: String): String {
		userRepository.readByUserEmail(username).firstOrNull()
			?: throw UnableToLocateRecordException("Email does not exists")
		val userOtp = (100000..999999).random().toString()
		userOtpRepository.readByUserOtpUsername(username).firstOrNull()?.userOtpId?.let {
			userOtpRepository.delete(it)
		}
		userOtpRepository.create(UserOtp(userOtpUsername=username, userOtpOtp= userOtp.bcryptHash(), userOtpStatus = "ACTIVE", userOtpUpdatedAt = LocalDateTime.now().toSqlDateTime()))
		return userOtp
	}

	fun validateOtp(otp: String, username: String): Boolean {
		val userOtp = userOtpRepository.readByUserOtpUsername(username).firstOrNull()
			?: throw InvalidOtpException("OTP is invalid.")
		if (!(otp.bcryptValidate(userOtp.userOtpOtp))) throw InvalidOtpException("OTP is invalid.")
		if (isExpired(userOtp.userOtpUpdatedAt!!.replace(" ", "T"))) throw OtpExpiredException("OTP is expired.")
		return true
	}

	fun isExpired(dateTime: String, expiryInMinute: Long=5): Boolean{
		val localDateTime = LocalDateTime.parse(dateTime)
		val minuteTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond/60
		val localDateTimeNow = LocalDateTime.now()
		val minuteTimestampNow = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant().epochSecond/60
		val diff = minuteTimestampNow - minuteTimestamp
		println(diff)
		return diff > expiryInMinute
	}
	fun loadAppConfig(): BaseResponse{
		//TODO LOAD MODULES
		val moduleNames = listOf("USER","TODO","USER_OTP","LOGIN_HISTORY","PRIVILEGE","ROLE","ROLE_PRIVILEGE","MODULE","AUDIT_LOG")
		moduleRepository.truncate()
		val modules =  moduleNames.map{Module(
			moduleName=it,
			moduleDescription=it,
			moduleStatus="ACTIVE"
		)}
		moduleRepository.bulkCreate(modules)
		//TODO LOAD PRIVILEGE
		privilegeRepository.truncate()
		val createPrivileges = moduleNames.map{Privilege(
			privilegeCode="$it|CREATE",
			privilegeName="CREATE",
			privilegeModuleName=it,
			privilegeDescription="$it|CREATE",
			privilegeStatus="ACTIVE"
		)}
		privilegeRepository.bulkCreate(createPrivileges)
		val updatePrivileges = moduleNames.map{Privilege(
			privilegeCode="$it|UPDATE",
			privilegeName="UPDATE",
			privilegeModuleName=it,
			privilegeDescription="$it|UPDATE",
			privilegeStatus="ACTIVE"
		)}
		privilegeRepository.bulkCreate(updatePrivileges)
		val deletePrivileges = moduleNames.map{Privilege(
			privilegeCode="$it|DELETE",
			privilegeName="DELETE",
			privilegeModuleName=it,
			privilegeDescription="$it|DELETE",
			privilegeStatus="ACTIVE"
		)}
		privilegeRepository.bulkCreate(deletePrivileges)
		val readPrivileges = moduleNames.map{Privilege(
			privilegeCode="$it|READ",
			privilegeName="READ",
			privilegeModuleName=it,
			privilegeDescription="$it|READ",
			privilegeStatus="ACTIVE"
		)}
		privilegeRepository.bulkCreate(readPrivileges)
		val checkerPrivileges = moduleNames.map{Privilege(
			privilegeCode="$it|CHECKER",
			privilegeName="CHECKER",
			privilegeModuleName=it,
			privilegeDescription="$it|CHECKER",
			privilegeStatus="ACTIVE"
		)}
		privilegeRepository.bulkCreate(checkerPrivileges)
		//TODO CREATE SUPER_ADMIN ROLE
		val superAdminRole = Role(
			roleName="SUPER_ADMIN",
			roleDescription="SUPER_ADMIN",
			roleStatus="ACTIVE"
		)
		val roleId = roleRepository.create(superAdminRole)
		//TODO LOAD SUPER_ADMIN ROLE PRIVILEGE
		val unionPrivilege = mutableListOf<Privilege>()
		unionPrivilege.addAll(createPrivileges)
		unionPrivilege.addAll(deletePrivileges)
		unionPrivilege.addAll(updatePrivileges)
		unionPrivilege.addAll(readPrivileges)
		unionPrivilege.addAll(checkerPrivileges)
		val rolePrivileges = unionPrivilege.map{RolePrivilege(
			rolePrivilegeRoleId=roleId.toInt(),
			rolePrivilegePrivilegeCode="${it.privilegeCode}",
			rolePrivilegeStatus="ACTIVE"
		)}
		rolePrivilegeRepository.bulkCreate(rolePrivileges)
		return ResponseConstant.SUCCESS
	}
}
