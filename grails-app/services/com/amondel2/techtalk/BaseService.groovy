package com.amondel2.techtalk

import asset.pipeline.grails.AssetResourceLocator
import com.amondel2.techtalk.Company

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import org.grails.core.io.ResourceLocator

@Transactional
@Slf4j
class BaseService {

    def generateGuid() {
	return UUID.randomUUID()?.toString().replaceAll("-", "")
    }

    AssetResourceLocator assetResourceLocator
    ResourceLocator resourceLocator

    def getTextRes() {
//        assetResourceLocator.findResourceForURI('assets/docz/test.txt').getInputStream().getText()
        assetResourceLocator.findResourceForURI('test.txt').getInputStream().getText()
    }
    
    def findCompany(ident) {
	findCompany()
    }
    
    def findCompany() {
	Company.first()
    }
}