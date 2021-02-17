package com.amondel2.techtalk

import grails.rest.RestfulController

class SubclassRestfulController<T> extends RestfulController<T> {
    SubclassRestfulController(Class<T> domainClass) {
        this(domainClass, false)
    }

    SubclassRestfulController(Class<T> domainClass, boolean readOnly) {
        super(domainClass, readOnly)
    }

    @Override
    List<T> listAllResources(Map params) {
        if (params.employeeGoal) {
            resource.withCriteria {
                employeeGoal {
                    eq('id',params.employeeGoal)
                }
                order 'createdDate', 'desc'
            }
        } else {
            super.listAllResources(params)
        }
    }
}