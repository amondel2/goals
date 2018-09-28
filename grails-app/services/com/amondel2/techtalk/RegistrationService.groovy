package com.amondel2.techtalk

@grails.transaction.Transactional
import grails.plugin.springsecurity.ui.RegistrationCode
import grails.transaction.Transactional

@Transactional
class RegistrationService {

    def springSecurityUiService

    RegistrationCode getForgotPassLink(Employees p) {
        springSecurityUiService.save(username: p.user.username, RegistrationCode,'sendForgotPasswordMail', transactionStatus)
    }
}