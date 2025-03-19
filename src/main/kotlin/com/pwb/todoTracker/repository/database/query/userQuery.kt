//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.021823500
package com.pwb.todoTracker.repository.database.query

import com.pwb.todoTracker.utility.constant.PrefixConstant


object userQuery{
   	const val INSERT = """
		INSERT INTO ${PrefixConstant.ENTITY}USER(
			userEmail,
			userName,
			userStatus,
			userCreatedAt,
			userUpdatedAt,
			userPassword
		) VALUES (
			:userEmail,
			:userName,
			COALESCE(:userStatus,'ACTIVE'),
			COALESCE(:userCreatedAt,getdate()),
			COALESCE(:userUpdatedAt,getdate()),
			:userPassword
		)
	"""

	const val UPDATE = """
		UPDATE ${PrefixConstant.ENTITY}USER SET
			userEmail=:userEmail,
			userName=:userName,
			userStatus=:userStatus,
			userCreatedAt=:userCreatedAt,
			userUpdatedAt=GETDATE(),
			userPassword=:userPassword
		WHERE userId=:userId
	"""

	const val DELETE = """
		UPDATE ${PrefixConstant.ENTITY}USER SET userStatus = 'DELETED', userUpdatedAt = GETDATE()
		WHERE userId=:userId
	"""

	const val TRUNCATE = """
		TRUNCATE TABLE ${PrefixConstant.ENTITY}USER 
	"""

	const val READ = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userStatus<>'DELETED'
	"""

	const val READ_BY_USER_ID = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userId=:userId AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_EMAIL = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userEmail=:userEmail AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_NAME = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userName=:userName AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_STATUS = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userStatus=:userStatus AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_CREATED_AT = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userCreatedAt=:userCreatedAt AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_UPDATED_AT = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userUpdatedAt=:userUpdatedAt AND userStatus<>'DELETED'
	"""

	const val READ_BY_USER_PASSWORD = """
		SELECT * FROM ${PrefixConstant.ENTITY}USER WHERE userPassword=:userPassword AND userStatus<>'DELETED'
	"""
}
