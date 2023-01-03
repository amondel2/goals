package com.amondel2.techtalk

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

@Transactional
@Slf4j
class KpoService {

    def getKPOList(Date sDate,Date endDate) {
        KPOType.withCriteria {
            eq('isActive', true)
            or {
                isNull('activeStartDate')
                lte('activeStartDate',sDate)
            }
            gte('endDate',endDate)
            order('title','asc')
        }
    }

    def getKPOList(Calendar sDate, Calendar endDate) {
        getKPOList(sDate.getTime(),endDate.getTime())
    }
}
