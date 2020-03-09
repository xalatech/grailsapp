grails.serverURL = "http://sivadm-test.ssb.no/sivadmin"
blaiseApplicationPath = "\\\\sw-blaise-t\\Siv"
blaiseSkjemaPath = "\\\\sw-blaise-t\\Siv\\skjema"
blaiseCapiSkjemaPath = "\\\\sw-blaise-t\\Siv\\skjema"

localSkjemaPath = "\\\\Client\\D\$\\siv\\skjema"
localSystemKommandoPath = "\\\\Client\\D\$\\siv\\systemkommando"

// localSkjemaPath = "u:/siv/skjema"
// localSystemKommandoPath = "u:\\siv\\systemkommando"

serverSystemKommandoPath = "\\\\sw-blaise-t\\siv\\Systemkommando"
serverSystemKommandoHttpPath = 'http://sivadm-test.ssb.no/sivadmin/systemKommando/registrerKjoring'

grails.mail.host = "epost-prod.ssb.no"
grails.mail.username = "mailsend_java_siv"
grails.mail.password = ""

admin.mail.adresse = "ruw@ssb.no"
avsender.sivadmin.epost = "mailsend_java_siv@ssb.no"

sil.sap.fil.lagre.lokalt = true
sil.sap.fil.lokal.katalog = "/usr/share/tomcat/siv/sap/"
sil.sap.fil.ftp.host = ""
sil.sap.fil.ftp.port = 21
sil.sap.fil.ftp.bruker = ""
sil.sap.fil.ftp.passord = ""
sil.sap.fil.ftp.katalog = ""
sil.sap.fil.generert.epost.liste = "ruw@ssb.no"
sil.intervjuer.kontroll.prosent = 50
sync.active = true

// sync.blaise.url = "http://kpwi-blaise.ssb.no/blaiserest"
sync.blaise.url = "http://sw-blaise-t.ssb.no/blaiserest"

timeforing.frist.antall.dager=90

io.laas.minutter = 10

utvalg.eksport.fil.path = "/usr/share/tomcat/siv/utvalg/"

behold.meldinger.inn.antall.dager=60
behold.meldinger.ut.antall.dager=60

behold.fravaer.antall.aar=2

behold.utlegg.antall.aar=2
behold.kjoreboker.antall.aar=2
behold.timeforinger.antall.aar=2

blaise.connector.url="http://pw-blaisecon-t1:8080"
blaise.connector.user=
blaise.connector.password=

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
	jndiName = "java:comp/env/sivadminDS"
	dialect = org.hibernate.dialect.Oracle10gDialect
}
// SIV Rest API Basic Auth credentials
siv.basicauth.brukernavn = ""
siv.basicauth.passord = ""
