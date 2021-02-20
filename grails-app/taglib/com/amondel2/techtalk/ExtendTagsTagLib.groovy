package com.amondel2.techtalk

import com.amondel2.techtalk.GoalStatus
import com.amondel2.techtalk.Employees

import java.text.SimpleDateFormat

class ExtendTagsTagLib {
	static namespace="ps"
    static defaultEncodeAs = [taglib:'html']
	static encodeAsForTags = [goalTypeDropDown: 'raw',goalStatusDropDown: 'raw',dirEmployeeDropDown: 'raw']
	def springSecurityService
	def employeeService
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy")

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

	def dirEmployeeDropDown = {attrs, body ->

		def str = ""
		Employees emp = Employees.findByUser(springSecurityService.getCurrentUser())
		if( employeeService.doesEmpHaveDirects(emp,attrs.year)) {
			def rtn = []
			employeeService.getAllEmployeesChildernFlat(emp.id,rtn,attrs.year)
			rtn.sort( { k1, k2 -> k1.firstName <=> k2.firstName } as Comparator )
			str = "<div style='padding:3px 0px;'><label for='${attrs.uid}_emps'>Select Employee: </label> " + g.select([ value:attrs.selId, id:attrs.uid+"_emps",name:"dir_emps",class:"form-check-inline", style: "max-width:271px;",from:rtn,optionValue:{ it.firstName + " " + it.lastName}, optionKey: 'id']) + "<button class=\"switchEmps btn btn-secondary\" >Save and Change to Selected Employee</button></div>"

		}
		out << body() << str
	}

	def generateTitle = { attrs, body ->
		StringBuilder str = new StringBuilder()
		str.append(attrs.goal?.title)
		str.append(' - ')
		def descript = attrs.goal?.description?.toString().replaceAll(/<!--.*?-->/, '')?.replaceAll(/<.*?>/, '')?.trim()
		str.append(descript?.substring(0,Math.min(descript?.size()?: 0,20)))
		if(attrs?.goal?.status in [GoalStatus.NotStarted,GoalStatus.Ongoing,GoalStatus.Behind,GoalStatus.OnTrack]) {
			str.append(' Goal Due: ')
			str.append(sdf.format((Date) attrs?.goal?.targetCompletDate))
		} else {
			str.append(' Goal Closed : ')
			str.append(sdf.format((Date) attrs?.goal?.actualCompletedDate))
		}
		out << body() << str.toString()
	}


	
}
