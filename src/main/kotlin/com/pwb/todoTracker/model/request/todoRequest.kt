//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.model.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


data class TodoCreateRequest(
   	@get: NotEmpty(message = "todoTitle is required")
	var todoTitle: String?=null,
	@get: NotEmpty(message = "todoDescription is required")
	var todoDescription: String?=null,
	@get: NotEmpty(message = "todoDueDate is required")
	var todoDueDate: String?=null,
	@get: NotNull(message = "todoUserId is required")
	var todoUserId: Int?=null
)

data class TodoUpdateRequest(
   	@get: NotNull(message = "todoId is required")
	var todoId: Int?=null,
	var todoTitle: String?=null,
	var todoDescription: String?=null,
	var todoDueDate: String?=null,
	var todoStatus: String?=null,
	var todoUserId: Int?=null
)

data class TodoReadRequest(
   	var todoId: Int?=null,
	var todoTitle: String?=null,
	var todoDescription: String?=null,
	var todoDueDate: String?=null,
	var todoStatus: String?=null,
	var todoUserId: Int?=null,
	var todoCreatedAt: String?=null,
	var todoUpdatedAt: String?=null
)

data class TodoDeleteRequest(
   	@get: NotNull(message = "todoId is required")
	var todoId: Int?=null
)
