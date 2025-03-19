//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.repository.database

import com.pwb.todoTracker.model.entity.todo
import com.pwb.todoTracker.utility.Environment
import org.pwb.utility.general.createQueryWithoutOnMappingFailure
import com.pwb.todoTracker.repository.database.query.TodoQuery
import org.springframework.stereotype.Repository



@Repository
interface TodoRepository{
   	fun create(todo:todo):Long
	fun bulkCreate(todos:List<todo>)
	fun update(todo:todo):Int
	fun delete(todoId:Int):Int
	fun truncate(): Boolean
	fun read():MutableList<todo>
	fun readByTodoId(todoId: Int):Todo?
	fun readByTodoTitle(todoTitle: String):MutableList<Todo>
	fun readByTodoDescription(todoDescription: String):MutableList<Todo>
	fun readByTodoDueDate(todoDueDate: String):MutableList<Todo>
	fun readByTodoStatus(todoStatus: String):MutableList<Todo>
	fun readByTodoUserId(todoUserId: Int):MutableList<Todo>
	fun readByTodoCreatedAt(todoCreatedAt: String):MutableList<Todo>
	fun readByTodoUpdatedAt(todoUpdatedAt: String):MutableList<Todo>
}

@Repository
class TodoRepositoryImpl(private val environment: Environment): TodoRepository{
   	override fun create(todo:Todo):Long{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(TodoQuery.INSERT)
			.bind(todo)
			.executeUpdate().getKey(Long::class.java)
	}

	override fun bulkCreate(todos:List<todo>){
		environment.databaseUtil?.getSql2o()?.beginTransaction().use { connection ->
			val query = connection?.createQueryWithoutOnMappingFailure(TodoQuery.INSERT, false)
			for (todo in todos)
				query?.bind(todo)?.addToBatch()
			query?.executeBatch()
			connection?.commit()
		}
	}

	override fun update(todo:Todo):Int{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(TodoQuery.UPDATE)
		.bind(todo)
		.executeUpdate().result
	}

	override fun delete(todoId:Int):Int{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(TodoQuery.DELETE)
		.addParameter("todoId", todoId)
		.executeUpdate().result
	}

	override fun truncate(): Boolean{
		return environment.databaseUtil?.sql2oConnection!!.jdbcConnection.createStatement().execute(TodoQuery.TRUNCATE)
	}

	override fun read():MutableList<todo>{
		return environment.databaseUtil?.sql2oConnection!!.createQueryWithoutOnMappingFailure(TodoQuery.READ)
		.executeAndFetch(todo::class.java)
	}

	override fun readByTodoId(todoId: Int):Todo?{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_ID)
				.addParameter("todoId", todoId)
				.executeAndFetch(Todo::class.java).firstOrNull()
	}

	override fun readByTodoTitle(todoTitle: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_TITLE)
				.addParameter("todoTitle", todoTitle)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoDescription(todoDescription: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_DESCRIPTION)
				.addParameter("todoDescription", todoDescription)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoDueDate(todoDueDate: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_DUE_DATE)
				.addParameter("todoDueDate", todoDueDate)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoStatus(todoStatus: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_STATUS)
				.addParameter("todoStatus", todoStatus)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoUserId(todoUserId: Int):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_USER_ID)
				.addParameter("todoUserId", todoUserId)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoCreatedAt(todoCreatedAt: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_CREATED_AT)
				.addParameter("todoCreatedAt", todoCreatedAt)
				.executeAndFetch(Todo::class.java)
	}

	override fun readByTodoUpdatedAt(todoUpdatedAt: String):MutableList<Todo>{
		return environment.databaseUtil?.sql2oConnection!!
				.createQueryWithoutOnMappingFailure(TodoQuery.READ_BY_TODO_UPDATED_AT)
				.addParameter("todoUpdatedAt", todoUpdatedAt)
				.executeAndFetch(Todo::class.java)
	}
}
