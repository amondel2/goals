/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amondel2.techtalk

import grails.validation.Validateable
/**
 *
 * @author aaron
 */
class EmployeeMCommand implements Validateable {

    String id
    String name
    String employeeId
    String companyId
    String parentId
    boolean canEdit = true

    boolean hasChildren = false



    static constraints = {
        id(nullable: true)
        name(nullable: false, blank: false, validator: { val, cmd ->
            if (val.size() > 255) {
                return ['page.size.error', 255]
            }


        })
        employeeId(nullable: false, blank: false)
        companyId(nullable: true)
    }

    EmployeeMCommand() {}

    EmployeeMCommand(json) {
        this.id = json.id
        this.name = json.toString()
        this.employeeId = json.employeeId
        this.parentId = EmployeeBoss.findByEmployeeAndDefaultBoss(json,true)?.first()?.id
    }

    def getDataForJSTree(){
        return [
            id: this.id,
            text: this.name,
            children: this.hasChildren,
            canedit: this.canEdit,
            data: [
                id: this.id,
                name: this.name,
                employeeId: this.employeeId,
                parentId: this.parentId
        ]
        ]
    }
}


