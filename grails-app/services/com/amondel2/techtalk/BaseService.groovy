package com.amondel2.techtalk

import com.amondel2.techtalk.Company

import grails.gorm.transactions.Transactional

@Transactional
class BaseService {

    def generateGuid() {
	return UUID.randomUUID()?.toString().replaceAll("-", "")
    }
    
    def findCompany(ident) {
	findCompany()
    }
    
    def findCompany() {
	Company.first()
    }
}