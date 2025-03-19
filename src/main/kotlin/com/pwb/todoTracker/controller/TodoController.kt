//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.controller

import com.pwb.todoTracker.service.*
import org.pwb.utility.model.response.BaseResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import com.pwb.todoTracker.model.response.*
import com.pwb.todoTracker.model.request.*
import org.pwb.utility.exception.BadRequestException
import org.pwb.utility.exception.ActivityAlreadyPerformedException
import org.pwb.utility.general.camelToSnakeCase
import org.pwb.utility.constant.*
import com.pwb.todoTracker.utility.constant.*
import com.pwb.todoTracker.utility.*
import org.pwb.utility.model.request.ModulePrivilege


@RestController
@RequestMapping("/{stage}/todo")
class TodoController(
	private val todoService: TodoService,
	private val environment: Environment
){
   	@PostMapping("/create")
	fun create(@RequestHeader("Authorization") authorization:String, @Valid @RequestBody request:TodoCreateRequest, @PathVariable stage: String):BaseResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.CREATE),
			authorization)
		return todoService.create(request)
	}

	@PostMapping("/bulk-create")
	fun bulkCreate(@RequestHeader("Authorization") authorization:String, @Valid @RequestBody request:List<TodoCreateRequest>, @PathVariable stage: String):BaseResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.CREATE),
			authorization)
		return todoService.bulkCreate(request)
	}

	@PostMapping("/update")
	fun update(@RequestHeader("Authorization") authorization:String, @Valid @RequestBody request:TodoUpdateRequest, @PathVariable stage: String):BaseResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.UPDATE),
			authorization)
		return todoService.update(request)
	}

	@PostMapping("/delete")
	fun delete(@RequestHeader("Authorization") authorization:String, @Valid @RequestBody request:TodoDeleteRequest, @PathVariable stage: String):BaseResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.DELETE),
			authorization)
		return todoService.delete(request)
	}

	@GetMapping("/read")
	fun read(@RequestHeader("Authorization") authorization:String, @PathVariable stage: String):todoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.read()
	}

	@GetMapping("/read-by-todo-id/{todoId}")
	fun readByTodoId(@RequestHeader("Authorization") authorization:String, @PathVariable todoId:Int, @PathVariable stage: String):TodoReadSingleResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoId(todoId)
	}

	@GetMapping("/read-by-todo-title/{todoTitle}")
	fun readByTodoTitle(@RequestHeader("Authorization") authorization:String, @PathVariable todoTitle:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoTitle(todoTitle)
	}

	@GetMapping("/read-by-todo-description/{todoDescription}")
	fun readByTodoDescription(@RequestHeader("Authorization") authorization:String, @PathVariable todoDescription:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoDescription(todoDescription)
	}

	@GetMapping("/read-by-todo-due-date/{todoDueDate}")
	fun readByTodoDueDate(@RequestHeader("Authorization") authorization:String, @PathVariable todoDueDate:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoDueDate(todoDueDate)
	}

	@GetMapping("/read-by-todo-status/{todoStatus}")
	fun readByTodoStatus(@RequestHeader("Authorization") authorization:String, @PathVariable todoStatus:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoStatus(todoStatus)
	}

	@GetMapping("/read-by-todo-user-id/{todoUserId}")
	fun readByTodoUserId(@RequestHeader("Authorization") authorization:String, @PathVariable todoUserId:Int, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoUserId(todoUserId)
	}

	@GetMapping("/read-by-todo-created-at/{todoCreatedAt}")
	fun readByTodoCreatedAt(@RequestHeader("Authorization") authorization:String, @PathVariable todoCreatedAt:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoCreatedAt(todoCreatedAt)
	}

	@GetMapping("/read-by-todo-updated-at/{todoUpdatedAt}")
	fun readByTodoUpdatedAt(@RequestHeader("Authorization") authorization:String, @PathVariable todoUpdatedAt:String, @PathVariable stage: String):TodoReadListResponse{
		environment.userInfo = environment.jwtUtil.privilegeAuthorization<LoginResponse>(
			ModulePrivilege(this::class.java.name.split(".").last().removeSuffix("Controller").camelToSnakeCase().uppercase(), PrivilegeConstant.READ),
			authorization)
		return todoService.readByTodoUpdatedAt(todoUpdatedAt)
	}

}
