String pass = System.getProperty("DB_PASSWORD")?.toString() ?: System.getenv("DB_PASSWORD")?.toString()
String user = System.getProperty("DB_USER")?.toString()  ?: System.getenv("DB_USER")?.toString()
String dbString = "jdbc:mariadb://localhost:3306/feedback?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true"
		//System.getenv("JDBC_CONNECTION_STRING_GOALS")?.toString() ?: System.getProperty("JDBC_CONNECTION_STRING_GOALS")?.toString()
dataSource {
	pooled = true
	jmxExport = true
	driverClassName = "org.mariadb.jdbc.Driver"
	dialect = "org.hibernate.dialect.MariaDB53Dialect"
}

environments {
	development {
		dataSource {
			password = pass
			dbCreate = "update"
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
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.feedback.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.feedback.UserRole'
grails.plugin.springsecurity.authority.className = 'com.feedback.Role'
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
	[pattern: '/login/**',            access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]