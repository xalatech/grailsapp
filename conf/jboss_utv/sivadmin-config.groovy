grails.serverURL = "http://jboss-utv.ssb.no/sivadmin"
blaiseApplicationPath = "\\\\Kpwi-blaise\\Siv"
blaiseSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
blaiseCapiSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
localSkjemaPath = "u:/siv/skjema"

localSystemKommandoPath = "u:\\siv\\systemkommando"
serverSystemKommandoPath = "\\\\Kpwi-blaise\\siv\\Systemkommando"
serverSystemKommandoHttpPath = 'http://jboss-utv.ssb.no/sivadmin/systemKommando/registrerKjoring'

grails.mail.host = "localhost"
grails.mail.port = "2500"
admin.mail.adresse = "raz@ssb.no"
avsender.sivadmin.epost = "ikke.svar.sivadmin@ssb.no"
sil.sap.fil.lagre.lokalt = true
sil.sap.fil.lokal.katalog = "/sil/"
sil.sap.fil.ftp.host = ""
sil.sap.fil.ftp.port = 21
sil.sap.fil.ftp.bruker = ""
sil.sap.fil.ftp.passord = ""
sil.sap.fil.ftp.katalog = ""
sil.sap.fil.generert.epost.liste = "stian.karlsen@ssb.no"
sil.intervjuer.kontroll.prosent = 50
sync.active = false
sync.blaise.url = "http://localhost:9090/blaiseintegration"
timeforing.frist.antall.dager=90

io.laas.minutter = 10

utvalg.eksport.fil.path = "/home/jboss/siv/utvalg/"

behold.meldinger.inn.antall.dager=60
behold.meldinger.ut.antall.dager=60

behold.fravaer.antall.aar=2

behold.utlegg.antall.aar=2
behold.kjoreboker.antall.aar=2
behold.timeforinger.antall.aar=2

cas.url = "https://jira.ssb.no:8443/cas-server-webapp-3.3.3"

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
	dbCreate = "update" // one of 'create', 'create-drop','update'
	jndiName = "java:sivadminDS"
	dialect = org.hibernate.dialect.Oracle10gDialect
}