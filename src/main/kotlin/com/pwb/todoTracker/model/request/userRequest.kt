//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.021823500
package com.pwb.todoTracker.model.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


data class UserCreateRequest(
   	@get: NotEmpty(message = "userEmail is required")
	var userEmail: String?=null,
	@get: NotEmpty(message = "userName is required")
	var userName: String?=null,
	@get: NotEmpty(message = "userPassword is required")
	var userPassword: String?=null
)

data class UserUpdateRequest(
   	@get: NotNull(message = "userId is required")
	var userId: Int?=null,
	var userEmail: String?=null,
	var userName: String?=null,
	var userStatus: String?=null,
	var userPassword: String?=null
)

data class UserReadRequest(
   	var userId: Int?=null,
	var userEmail: String?=null,
	var userName: String?=null,
	var userStatus: String?=null,
	var userCreatedAt: String?=null,
	var userUpdatedAt: String?=null,
	var userPassword: String?=null
)

data class UserDeleteRequest(
   	@get: NotNull(message = "userId is required")
	var userId: Int?=null
)
