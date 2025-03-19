//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.021823500
package com.pwb.todoTracker.model.response

import com.pwb.todoTracker.model.entity.user


data class UserReadListResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: MutableList<user>
)

data class UserReadSingleResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: user
)
