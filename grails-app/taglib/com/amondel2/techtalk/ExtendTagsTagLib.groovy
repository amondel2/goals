package com.amondel2.techtalk

import com.amondel2.techtalk.GoalStatus

class ExtendTagsTagLib {
	static namespace="ps"
    static defaultEncodeAs = [taglib:'html']
	static encodeAsForTags = [goalTypeDropDown: 'raw',goalStatusDropDown: 'raw']
	def springSecurityService
	
	def getUserFName = {attrs,body->
		Employees user = Employees.findByUser(springSecurityService.currentUser)
		  def name =  user ? user.firstName : ""
		  out << body() <<name
	}

	def goalTypeDropDown = {attrs, body ->

		out << body() << g.select([id:attrs.name,class:"form-check-inline align-top", style: "max-width:271px;",from:attrs.goalTypes,name:attrs.name,value:attrs.value, multiple:"true", optionValue:'title', optionKey: 'id'])


	}

	def goalStatusDropDown = {attrs, body ->

		out << body() << g.select([class:"form-check-inline align-top statusDropdownElm",from:GoalStatus,name:attrs.name,value:attrs.value,id:attrs.name])


	}

	def generateTitle = { attrs, body ->
		StringBuilder str = new StringBuilder()
		str.append(attrs.goal?.title)
		str.append(' - ')
		def descript = attrs.goal?.description?.toString().replaceAll(/<!--.*?-->/, '')?.replaceAll(/<.*?>/, '')?.trim()
		str.append(descript?.substring(0,Math.min(descript?.size()?: 0,20)))
		if(attrs?.goal?.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]) {
			str.append(' Goal Due: ')
			str.append(((Date) attrs?.goal?.targetCompletDate)?.format('MM-dd-yyyy'))
		} else {
			str.append(' Goal Closed : ')
			str.append(((Date) attrs?.goal?.actualCompletedDate)?.format('MM-dd-yyyy'))
		}
		out << body() << str.toString()
	}


	
}
