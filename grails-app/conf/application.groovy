String pass = System.getProperty("DB_PASSWORD")?.toString() ?: System.getenv("DB_PASSWORD")?.toString()
String user = System.getProperty("DB_USER")?.toString()  ?: System.getenv("DB_USER")?.toString()
String dbString = System.getenv("JDBC_CONNECTION_STRING_GOALS")?.toString() ?: System.getProperty("JDBC_CONNECTION_STRING_GOALS")?.toString()
dataSource {
    pooled = true
    jmxExport = true
//    driverClassName = "com.mysql.jdbc.Driver"
//    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    driverClassName = "com.mysql.cj.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL8Dialect"
}

environments {
    development {
        dataSource {
            password = pass
            dbCreate = "none"
            username = user
            url= dbString
        }
    }
    test {
        dataSource {
            username = "sa"
            password = ''
            dbCreate = "create-drop"
            driverClassName = "org.h2.Driver"
            dialect =  "org.hibernate.dialect.H2Dialect"
//			url = "jdbc:h2:file:myDevDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production{
        dataSource {
            dbCreate = "none"
            username = user
            password = pass
            url = dbString
            properties {
                jmxEnabled = true
                initialSize = 5
                maxActive = 50
                minIdle = 5
                maxIdle = 25
                maxWait = 10000
                maxAge = 600000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
                validationInterval = 15000
                testOnBorrow = true
                testWhileIdle= true
                testOnReturn = false
                jdbcInterceptors = "ConnectionState"
                defaultTransactionIsolation= 2 // TRANSACTION_READ_COMMITTED
            }
        }
    }
}
// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.amondel2.techtalk.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.amondel2.techtalk.UserRole'
grails.plugin.springsecurity.authority.className = 'com.amondel2.techtalk.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
    [pattern: '/monitoring',    access: ['ROLE_ADMIN']],
    [pattern: '/register/**',            access: ['permitAll']],
    [pattern: '/profile/**',             access: ['ROLE_USER']],
    [pattern: '/board/**',               access: ['ROLE_USER']],
    [pattern: '/user/**',                access: ['ROLE_ADMIN']],
    [pattern: '/role/**',                access: ['ROLE_ADMIN']],
    [pattern: '/securityInfo/**',        access: ['ROLE_ADMIN']],
    [pattern: '/registrationCode/**',    access: ['ROLE_ADMIN']],
    [pattern: '/employeeGoalComment/**', access: ['ROLE_USER']],
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]