grails.serverURL = "http://localhost:8080/sivadmin"

blaiseApplicationPath = "\\\\Kpwi-blaise\\Siv"
blaiseSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
blaiseCapiSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
localSkjemaPath = "c:/siv/skjema"

localSystemKommandoPath = "c:\\siv\\systemkommando\\klient"
serverSystemKommandoPath = "c:\\siv\\systemkommando\\server"
serverSystemKommandoHttpPath = 'http://localhost:8080/sivadmin/systemKommando/registrerKjoring'

grails.mail.host = "epost-prod.ssb.no"
grails.mail.username = "mailsend_java_siv"
grails.mail.password = ""

blaise.connector.url="http://localhost:8090"
blaise.connector.user=
blaise.connector.password=

admin.mail.adresse = "vak@ssb.no"
avsender.sivadmin.epost = "mailsend_java_siv@ssb.no"

sil.sap.fil.lagre.lokalt = true
sil.sap.fil.lokal.katalog = "c:\\temp\\sap"
sil.sap.fil.ftp.host = "127.0.0.1"
sil.sap.fil.ftp.port = 21
sil.sap.fil.ftp.bruker = "sivadm"
sil.sap.fil.ftp.passord = "sivadm"
sil.sap.fil.ftp.katalog = "sap"
sil.sap.fil.generert.epost.liste = "ruw@ssb.no"
sil.intervjuer.kontroll.prosent = 50

sync.active = true
sync.blaise.url = "http://localhost:2336"

behold.meldinger.inn.antall.dager=60
behold.meldinger.ut.antall.dager=60

behold.fravaer.antall.aar=2

behold.utlegg.antall.aar=2
behold.kjoreboker.antall.aar=2
behold.timeforinger.antall.aar=2

timeforing.frist.antall.dager = 90

io.laas.minutter = 2

utvalg.eksport.fil.path = "c:\\temp\\utvalg\\"

// cas.url = "https://jira.ssb.no:8443/cas-server-webapp-3.3.3"

cas.url = "https://cas-ad-qa.ssb.no/cas"

//Jasig CAS support for the Spring Security plugin
grails.plugin.springsecurity.cas.loginUri = '/login'
grails.plugin.springsecurity.cas.serviceUrl = '${grails.serverURL}/j_spring_cas_security_check'
grails.plugin.springsecurity.cas.serverUrlPrefix = '${cas.url}'
grails.plugin.springsecurity.cas.proxyCallbackUrl = '${grails.serverURL}/secure/receptor'
grails.plugin.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'
grails.plugin.springsecurity.cas.artifactParameter = 'ticket'
grails.plugin.springsecurity.cas.sendRenew = 'false'
grails.plugin.springsecurity.cas.serviceParameter = 'service'
grails.plugin.springsecurity.cas.filterProcessesUrl = '/j_spring_cas_security_check'
grails.plugin.springsecurity.cas.key = 'grails-spring-security-cas'
grails.plugin.springsecurity.cas.useSingleSignout = false
grails.plugin.springsecurity.logout.afterLogoutUrl = '${cas.url}/logout'

		
dataSource {
    dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
    url = "jdbc:h2:tcp://localhost/~/sivadmin"
}

// Logging til sdtout

log4j = {

    root {
        info 'stdout'
        additivity = true
    }
}


// SIV Rest API Basic Auth credentials
siv.basicauth.brukernavn = ""
siv.basicauth.passord = ""
