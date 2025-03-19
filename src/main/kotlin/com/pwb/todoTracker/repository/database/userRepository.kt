//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.021823500
package com.pwb.todoTracker.repository.database

import com.pwb.todoTracker.model.entity.user
import com.pwb.todoTracker.utility.Environment
import org.pwb.utility.general.createQueryWithoutOnMappingFailure
import com.pwb.todoTracker.repository.database.query.UserQuery
import org.springframework.stereotype.Repository



@Repository
interface UserRepository{
   	fun create(user:user):Long
	fun bulkCreate(users:List<user>)
	fun update(user:user):Int
	fun delete(userId:Int):Int
	fun truncate(): Boolean
	fun read():MutableList<user>
	fun readByUserId(userId: Int):User?
	fun readByUserEmail(userEmail: String):MutableList<User>
	fun readByUserName(userName: String):MutableList<User>
	fun readByUserStatus(userStatus: String):MutableList<User>
	fun readByUserCreatedAt(userCreatedAt: String):MutableList<User>
	fun readByUserUpdatedAt(userUpdatedAt: String):MutableList<User>
	fun readByUserPassword(userPassword: String):MutableList<User>
}

@Repository
class UserRepositoryImpl(private val environment: Environment): UserRepository{
   	override fun create(user:User):Long{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(UserQuery.INSERT)
			.bind(user)
			.executeUpdate().getKey(Long::class.java)
	}

	override fun bulkCreate(users:List<user>){
		environment.databaseUtil?.getSql2o()?.beginTransaction().use { connection ->
			val query = connection?.createQueryWithoutOnMappingFailure(UserQuery.INSERT, false)
			for (user in users)
				query?.bind(user)?.addToBatch()
			query?.executeBatch()
			connection?.commit()
		}
	}

	override fun update(user:User):Int{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(UserQuery.UPDATE)
		.bind(user)
		.executeUpdate().result
	}

	override fun delete(userId:Int):Int{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(UserQuery.DELETE)
		.addParameter("userId", userId)
		.executeUpdate().result
	}

	override fun truncate(): Boolean{
		return environment.databaseUtil?.sql2oConnection!!.jdbcConnection.createStatement().execute(UserQuery.TRUNCATE)
	}

	override fun read():MutableList<user>{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(UserQuery.READ)
		.executeAndFetch(user::class.java)
	}

	override fun readByUserId(userId: Int):User?{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_ID)
				.addParameter("userId", userId)
				.executeAndFetch(User::class.java).firstOrNull()
	}

	override fun readByUserEmail(userEmail: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_EMAIL)
				.addParameter("userEmail", userEmail)
				.executeAndFetch(User::class.java)
	}

	override fun readByUserName(userName: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_NAME)
				.addParameter("userName", userName)
				.executeAndFetch(User::class.java)
	}

	override fun readByUserStatus(userStatus: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_STATUS)
				.addParameter("userStatus", userStatus)
				.executeAndFetch(User::class.java)
	}

	override fun readByUserCreatedAt(userCreatedAt: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_CREATED_AT)
				.addParameter("userCreatedAt", userCreatedAt)
				.executeAndFetch(User::class.java)
	}

	override fun readByUserUpdatedAt(userUpdatedAt: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_UPDATED_AT)
				.addParameter("userUpdatedAt", userUpdatedAt)
				.executeAndFetch(User::class.java)
	}

	override fun readByUserPassword(userPassword: String):MutableList<User>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(UserQuery.READ_BY_USER_PASSWORD)
				.addParameter("userPassword", userPassword)
				.executeAndFetch(User::class.java)
	}
}
