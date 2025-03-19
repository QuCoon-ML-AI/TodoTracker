//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.131166100
package com.pwb.todoTracker.model.response

import com.pwb.todoTracker.model.entity.Privilege


data class PrivilegeReadListResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: MutableList<Privilege>
)

data class PrivilegeReadSingleResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: Privilege
)
