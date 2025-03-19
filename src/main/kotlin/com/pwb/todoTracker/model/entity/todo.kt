//TODO ==== BUILT WITH QOONITY VERSION-1.0 - YINKA-ANTHONY-NKAY ON 2025-03-19T22:55:33.099935
package com.pwb.todoTracker.model.entity

import org.pwb.utility.annotation.Column
import org.pwb.utility.annotation.Table

@Table
data class todo(
   	@Column(type="INT", primaryKey=true,autoIncrement=true,startValue=100,step=1,defaultValue="100")
	var todoId: Int?=null,
	@Column(type="VARCHAR(n)", nullable=true,length=100)
	var todoTitle: String?=null,
	@Column(type="VARCHAR(n)", nullable=true,length=100)
	var todoDescription: String?=null,
	@Column(type="DATETIME", nullable=true)
	var todoDueDate: String?=null,
	@Column(type="VARCHAR(n)", nullable=true,defaultValue="'ACTIVE'",length=100)
	var todoStatus: String?=null,
	@Column(type="INT", nullable=true,foreignKey=true,reference="USER(userId)")
	var todoUserId: Int?=null,
	@Column(type="DATETIME", nullable=true,defaultValue="getdate()")
	var todoCreatedAt: String?=null,
	@Column(type="DATETIME", nullable=true,defaultValue="getdate()")
	var todoUpdatedAt: String?=null
)
