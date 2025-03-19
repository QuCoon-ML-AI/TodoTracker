//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.115533700
package com.pwb.todoTracker.model.response

import com.pwb.todoTracker.model.entity.LoginHistory


data class LoginHistoryReadListResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: MutableList<LoginHistory>
)

data class LoginHistoryReadSingleResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: LoginHistory
)
