//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.146805700
package com.pwb.todoTracker.model.response

import com.pwb.todoTracker.model.entity.Module


data class ModuleReadListResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: MutableList<Module>
)

data class ModuleReadSingleResponse(
   	var responseCode: String,
	var responseMessage: String,
	var data: Module
)
