package com.amondel2.techtalk

import grails.plugin.springsecurity.annotation.Secured

class UserController extends grails.plugin.springsecurity.ui.UserController {

     @Secured(['permitAll'])
    def show(){
        def id
        try {
            def p = Employees.findByUser(User.findById(params.id))
            id = p.id
        } catch(Exception e ) {
        }
        redirect(controller:"employees",action:"show",params: [ id: id])
    }


    @Secured(['ROLE_ADMIN'])
    def showMissProf(){
        def c = User.createCriteria()
        def matchingActs = c.list {
            not { 'in'('id',Employees.withCriteria{
                        projections{
                            property("user.id")
                        }})}
            projections{
                order "id"
            }
        }

     	respond matchingActs, model:['userInstanceList':matchingActs]
    }


}