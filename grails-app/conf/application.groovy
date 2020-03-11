def appName = 'sivadmin'
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all:           '*/*',
        atom:          'application/atom+xml',
        css:           'text/css',
        csv:           'text/csv',
        form:          'application/x-www-form-urlencoded',
        html:          ['text/html','application/xhtml+xml'],
        js:            'text/javascript',
        json:          ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss:           'application/rss+xml',
        text:          'text/plain',
        xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/stylesheets/*', '/javascripts/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// Lagt inn av vak ifm. oppgradering fra grails 1.3.7
// SIL-konfigurasjon
sil.sap.fil.extension = ".txt"
sil.sap.fil.encoding = "UTF8"
sil.sap.firmakode = "1100"
sil.sap.kode.time = "2010"
sil.sap.kode.reise = "0015"
sil.sap.forste.lopenummer = "51"
sil.sap.kostnadssted = "852"
sil.sap.kontopost.stat = "162001"
sil.sap.kontopost.marked = "162021"

environments {
    development {
        grails.logging.jul.usebridge = true
        database.datomaske = 'yyyy-MM-dd H:m:s'
    }
    db1u {
        database.datomaske = 'dd.MM.yyyy H:m:s'
    }
    db1t {
        database.datomaske = 'dd.MM.yyyy H:m:s'
    }
    production {
        grails.logging.jul.usebridge = false
        database.datomaske = 'dd.MM.yyyy H:m:s'
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// Added by the Spring Security Core plugin:
// grails 2.2.4 = grails.plugins.xxx
grails.plugin.springsecurity.userLookup.userDomainClassName = 'sivadm.Bruker'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'sivadm.BrukerRolle'
grails.plugin.springsecurity.authority.className = 'sivadm.Rolle'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']],
        [pattern: '/error',          access: ['permitAll']],
        [pattern: '/index',          access: ['permitAll']],
        [pattern: '/index.gsp',      access: ['permitAll']],
        [pattern: '/shutdown',       access: ['permitAll']],
        [pattern: '/assets/**',      access: ['permitAll']],
        [pattern: '/**/js/**',       access: ['permitAll']],
        [pattern: '/**/javascripts/**', access: ['permitAll']],
        [pattern: '/**/stylesheets/**', access: ['permitAll']],
        [pattern: '/**/css/**',      access: ['permitAll']],
        [pattern: '/**/images/**',   access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/javascripts/**',       filters: 'none'],
        [pattern: '/**/stylesheets/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**',             filters: 'JOINED_FILTERS']
]

// Ekstern konfigurasjon
def CONFIG_FILE = "${appName}-config.groovy"
def externalConfigFilePath = "/usr/local/jboss/jboss-as/server/ssb/deploy/" + CONFIG_FILE
def localConfigFilePath = "conf/local/${CONFIG_FILE}"

File localConfigFile = new File(localConfigFilePath)
File externalConfigFile = new File(externalConfigFilePath)

if(localConfigFile.exists()) {
    print localConfigFile
    println "Including configuration file: ${localConfigFilePath}"
    grails.config.locations = [
            "file:conf/local/sivadmin-config.groovy",
    ]
}