package com.amondel2.techtalk

import grails.plugin.springsecurity.ui.RegistrationCode
class RegistrationService {

    def springSecurityUiService

    RegistrationCode getForgotPassLink(Employees p) {
        springSecurityUiService.save(username: p.user.username, RegistrationCode,'sendForgotPasswordMail', transactionStatus)
    }
}