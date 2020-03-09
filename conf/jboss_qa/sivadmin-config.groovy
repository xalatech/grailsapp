grails.serverURL = "http://ktli-jboss-d02:8080/sivadmin"
blaiseApplicationPath = "\\\\Kpwi-blaise\\Siv"
blaiseSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
blaiseCapiSkjemaPath = "\\\\Kpwi-blaise\\Siv\\skjema"
localSkjemaPath = "u:/siv/skjema"

localSystemKommandoPath = "u:\\siv\\systemkommando"
serverSystemKommandoPath = "\\\\Kpwi-blaise\\siv\\Systemkommando"
serverSystemKommandoHttpPath = 'http://ktli-jboss-d02:8080/sivadmin/systemKommando/registrerKjoring'

grails.mail.host = "klientmail.ssb.no"
admin.mail.adresse = "raz@ssb.no"
avsender.sivadmin.epost = "ikke.svar.sivadmin@ssb.no"
sil.sap.fil.lagre.lokalt = true
sil.sap.fil.lokal.katalog = "/home/jboss/sil/"
sil.sap.fil.ftp.host = ""
sil.sap.fil.ftp.port = 21
sil.sap.fil.ftp.bruker = ""
sil.sap.fil.ftp.passord = ""
sil.sap.fil.ftp.katalog = ""
sil.sap.fil.generert.epost.liste = "ruw@ssb.no"
sil.intervjuer.kontroll.prosent = 50
sync.active = true

// sync.blaise.url = "http://kpwi-blaise.ssb.no/blaiserest/bare_test_fjern_siste_ledd"
sync.blaise.url = "http://sw-blaise-t.ssb.no/blaiserest/bare_test_fjern_siste_ledd"

timeforing.frist.antall.dager=90

io.laas.minutter = 10

utvalg.eksport.fil.path = "/home/jboss/siv/utvalg/"

behold.meldinger.inn.antall.dager=60
behold.meldinger.ut.antall.dager=60

behold.fravaer.antall.aar=2

behold.utlegg.antall.aar=2
behold.kjoreboker.antall.aar=2
behold.timeforinger.antall.aar=2

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
	jndiName = "java:sivadminDS"
	dialect = org.hibernate.dialect.Oracle10gDialect
}