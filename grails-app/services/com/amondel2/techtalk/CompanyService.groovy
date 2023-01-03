package com.amondel2.techtalk

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j


@Transactional
@Slf4j
class CompanyService extends BaseService {

    def getSubPortfolios(companyId) {
        Portfolio.withCriteria{
            eq('company',findCompany(companyId))
        }
    }

    def getChildren(companyId) {
        Portfolio.findAllByCompanyAndParentIsNull(findCompany(companyId))
    }
}
