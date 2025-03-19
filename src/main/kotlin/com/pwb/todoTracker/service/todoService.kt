//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.service

import com.pwb.todoTracker.repository.database.todoRepository
import com.pwb.todoTracker.model.entity.todo
import org.pwb.utility.constant.ResponseConstant
import org.pwb.utility.model.response.BaseResponse
import org.springframework.stereotype.Service
import org.pwb.utility.constant.GSON
import com.pwb.todoTracker.model.response.*
import com.pwb.todoTracker.model.request.*
import org.pwb.utility.exception.*


@Service
class TodoService(
   private val todoRepository: TodoRepository
){
   	fun create(request:TodoCreateRequest):BaseResponse{
		val todo = GSON.fromJson(GSON.toJson(request), Todo::class.java).apply { todoStatus = todoStatus?:"ACTIVE" }
		todoRepository.create(todo)
		return ResponseConstant.SUCCESS 
	}

	fun bulkCreate(request:List<TodoCreateRequest>):BaseResponse{
		val todos = GSON.fromJson(GSON.toJson(request), Array<Todo>::class.java).toList()
		todoRepository.bulkCreate(todos)
		return ResponseConstant.SUCCESS
	}

	fun update(request:TodoUpdateRequest):BaseResponse{
		var todo = todoRepository.readByTodoId(request.todoId!!)
			?:throw UnableToLocateRecordException()
		todo = todo.apply{
				todoId = request.todoId?:todoId
				todoTitle = request.todoTitle?:todoTitle
				todoDescription = request.todoDescription?:todoDescription
				todoDueDate = request.todoDueDate?:todoDueDate
				todoStatus = request.todoStatus?:todoStatus
				todoUserId = request.todoUserId?:todoUserId
			}
		todoRepository.update(todo)
		return ResponseConstant.SUCCESS
	}

	fun delete(request:TodoDeleteRequest):BaseResponse{
		var updateResponse = todoRepository.delete(request.todoId!!)
		if(updateResponse < 1) throw FailedToUpdateRecord()
		return ResponseConstant.SUCCESS
	}

	fun read():todoReadListResponse{
		var todo = todoRepository.read()
		return todoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoId(todoId:Int): TodoReadSingleResponse{
		var todo = todoRepository.readByTodoId(todoId) 
			?: throw UnableToLocateRecordException()
		return TodoReadSingleResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoTitle(todoTitle:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoTitle(todoTitle) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoDescription(todoDescription:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoDescription(todoDescription) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoDueDate(todoDueDate:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoDueDate(todoDueDate) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoStatus(todoStatus:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoStatus(todoStatus) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoUserId(todoUserId:Int): TodoReadListResponse{
		var todo = todoRepository.readByTodoUserId(todoUserId) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoCreatedAt(todoCreatedAt:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoCreatedAt(todoCreatedAt) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

	fun readByTodoUpdatedAt(todoUpdatedAt:String): TodoReadListResponse{
		var todo = todoRepository.readByTodoUpdatedAt(todoUpdatedAt) 
		return TodoReadListResponse(
			responseCode = ResponseConstant.SUCCESS.responseCode,
			responseMessage = ResponseConstant.SUCCESS.responseMessage,
			data = todo
		)
	}

}
