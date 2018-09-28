package com.amondel2.techtalk

class ExtendTagsTagLib {
	static namespace="ps"
    static defaultEncodeAs = [taglib:'html']
//	static encodeAsForTags = [renderMonthlyBox: 'raw',renderCheckAllBox: 'raw']
	def springSecurityService
	
	def getUserFName = {attrs,body->
		Employees user = Employees.findByUser(springSecurityService.currentUser)
		  def name =  user ? user.firstName : ""
		  out << body() <<name
	}

	
}
