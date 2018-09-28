package com.amondel2.techtalk



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
