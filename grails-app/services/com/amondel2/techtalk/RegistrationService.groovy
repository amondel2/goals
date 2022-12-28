package com.amondel2.techtalk

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.plugin.springsecurity.ui.SpringSecurityUiService

@Transactional
class RegistrationService {

    SpringSecurityUiService springSecurityUiService

    RegistrationCode getForgotPassLink(Employees p) {
        springSecurityUiService.save(username: p.user.username, RegistrationCode,'sendForgotPasswordMail', transactionStatus)
    }
    
    def removeLoginToken(Employees emp) {
        emp.restToken = null
		emp.save(flush:true)   
    }
}
