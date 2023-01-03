package com.amondel2.techtalk

import asset.pipeline.grails.AssetResourceLocator
import grails.core.GrailsApplication
import org.springframework.context.i18n.LocaleContextHolder as LCH
import grails.gorm.transactions.Transactional

@Transactional
class BaseFMService {

    GrailsApplication grailsApplication
    def messageSource



    def getErrorList(cmd) throws Exception {
        HashMap<String, LinkedList<String>> errorMap = new HashMap<>()

        cmd.errors.allErrors.each {
            String fieldName = it?.field
            String code = it?.codes?.last()
            String msg
                    try{
                        msg = messageSource.getMessage(code, it?.arguments, LCH.locale)
                    } catch (Exception e) {
                        msg = code
                    }

            if (errorMap.containsKey(fieldName)) {
                errorMap.get(fieldName).add(msg)
            } else {
                def list = new LinkedList<String>([msg])
                errorMap.put(fieldName, list)
            }
        }

        errorMap
    }
}