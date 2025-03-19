//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.model.response

import com.pwb.todoTracker.model.entity.UserOtp


data class UserOtpReadListResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: MutableList<UserOtp>
)

data class UserOtpReadSingleResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: UserOtp
)
