package sivadm

import org.apache.commons.net.ftp.FTPClient
import siv.type.ProsessNavn

class MonitorController {

	def statbankServiceURL
	def prosessLoggService
	
	final String MONITOR_ERROR = "MONITOR_ERROR: " 
	
    def index = {

        def dbMonitor = sjekkDb()
        def blaiseMonitor = sjekkBlaise()
        def cas = sjekkCas()
		def sjekkSendMeldingUtJob = sjekkSendMeldingUtJob()
		def sjekkBlaiseTriggerJob = sjekkBlaiseTriggerJob()
		def sjekkMeldingInnJob = sjekkMeldingInnJob()
		def sjekkPaaVentJob = sjekkPaaVentJob()
		def sjekkUtsendtCatiJob = sjekkUtsendtCatiJob()
		def sjekkKravRyddingJob = sjekkKravRyddingJob()
		def sjekkRyddMeldingInnJob = sjekkRyddMeldingInnJob()
		def sjekkRyddMeldingUtJob = sjekkRyddMeldingUtJob()
		def sjekkLaasOppIoJob = sjekkLaasOppIoJob()
		def sjekkHentBlaiseEventsJob = sjekkHentBlaiseEventsJob()
		def silFtp = sjekkSilFtp()
		        
        [hostname: getLocalHostName(), timestamp: getTimestamp(), monitors:[dbMonitor, blaiseMonitor, cas, sjekkSendMeldingUtJob, sjekkBlaiseTriggerJob, sjekkMeldingInnJob, sjekkPaaVentJob, sjekkUtsendtCatiJob, sjekkKravRyddingJob, sjekkRyddMeldingInnJob, sjekkRyddMeldingUtJob, sjekkLaasOppIoJob, silFtp, sjekkHentBlaiseEventsJob]]
    }
    
	def nullstillMonitors = {
		log.info "Nullstiller alle monitorer"
		
		def prosessListe = ProsessLogg.list()
		
		prosessListe.each {
			it.suksess = true
			it.tidStart = new Date()
			it.tidStopp = new Date()
			it.melding = "Nullstillt manuelt"
			
			it.save(flush: true)	
		}
				
		redirect(action: "index")
	}
	
	private String getLocalHostName() {
		String hostName = ""
		try {
			InetAddress localHost = InetAddress.getLocalHost()
			hostName = localHost.getHostName()			
		} catch (UnknownHostException e) {
			log.error "Feil ved henting av hostName: " + e.message
		}		
		return hostName
	}
	
	private String getTimestamp() {		
		return Calendar.getInstance().getTime()
	}
	
    private Monitor sjekkDb(){
    	def monitor = new Monitor(navn: "${message(code: 'monitor.database.overskrift')}")
    	try{
            def antallBrukere = Bruker.count()
            def datakilde = grailsApplication.config.getProperty("dataSource.jndiName") ? grailsApplication.config.getPropert("dataSource.jndiName") : grailsApplication.config.getProperty("dataSource.url")
            monitor.beskrivelse = "${message(code: 'monitor.database.melding', args: [antallBrukere, datakilde])}" 
            monitor.status = true
        } catch (Exception e) {
            monitor.status = false
            monitor.beskrivelse = "${message(code: 'monitor.database.feil', args: [grailsApplication.config.getProperty("dataSource.url")])}"
            monitor.feilmelding = e.message
            log.error MONITOR_ERROR + "Feil i kommunikasjon med database: " + e.message
        }
    	return monitor
    }
	
	private Monitor sjekkSilFtp() {
		def monitor = new Monitor(navn: "SIL FTP")
		
		if(grailsApplication.config.getProperty("sil.sap.fil.lagre.lokalt")) {
			monitor.status = true
			monitor.beskrivelse = "SIL er satt opp til å lagre SAP filer til filområde " + grailsApplication.config.getProperty("sil.sap.fil.lokal.katalog")
		}
		else {		
			def ftp = "host: '" + grailsApplication.config.getProperty("sil.sap.fil.ftp.host") + ":" + grailsApplication.config.getProperty("sil.sap.fil.ftp.port") + "'"
			if(grailsApplication.config.getProperty("sil.sap.fil.ftp.katalog") && grailsApplication.config.getProperty("sil.sap.fil.ftp.katalog") != "") {
				ftp = ftp + ", katalog: '" + grailsApplication.config.getProperty("sil.sap.fil.ftp.ksatalog") + "'"
			}
			
			ftp = ftp + ", brukernavn: '" + grailsApplication.config.getProperty("sil.sap.fil.ftp.bruker") + "'"
			
			monitor.beskrivelse = "Sjekker om SivAdm/SIL har kontakt med ftp område (" + ftp + ") for skriving av SAP filer"
			
			def ftpClient = new FTPClient()
			
			try {
				ftpClient.connect(grailsApplication.config.getProperty("sil.sap.fil.ftp.host"), grailsApplication.config.getProperty("sil.sap.fil.ftp.port"))
				boolean isLoggedIn = ftpClient.login(grailsApplication.config.getProperty("sil.sap.fil.ftp.bruker"), grailsApplication.config.getProperty("sil.sap.fil.ftp.passord"))
				
				if(isLoggedIn) {
					monitor.status = true
					ftpClient.disconnect()
				}
				else {
					// KUNNE IKKE LOGGE PÅ FTP
					monitor.status = false
					String str = "Kunne ikke logge på ftp " + config.sil.sap.fil.ftp.host + ":" + config.sil.sap.fil.ftp.port + ", med bruker " + config.sil.sap.fil.ftp.bruker
					monitor.feilmelding = str
					log.error(str)
				}
			}
			catch (Exception ex) {
				monitor.status = false
				monitor.feilmelding = ex.getMessage()
			}
		}
		
		return monitor
	}

    private Monitor sjekkBlaise() {
   	 def monitor = new Monitor(navn: "${message(code: 'monitor.blaise.overskrift')}")
   	 try {
            String blaiseUrl = grailsApplication.config.getProperty("sync.blaise.url")
			blaiseUrl +=  "/Service.svc/help"
            def url = new URL( blaiseUrl )
            url.text
            monitor.beskrivelse = "${message(code: 'monitor.blaise.melding', args: [blaiseUrl])}"
            monitor.status = true
        } catch (Exception e){
            monitor.status = false
			String blaiseUrl = grailsApplication.config.getProperty("sync.blaise.url ")
			blaiseUrl += "/Service.svc/help"
            monitor.beskrivelse = "${message(code: 'monitor.blaise.feil', args: [blaiseUrl])}"
            monitor.feilmelding = "Feil i kommunikasjon med blaise: " + e.message
            log.error MONITOR_ERROR + monitor.feilmelding
        } 
        return monitor
   }

	private Monitor sjekkCas() {
		def monitor = new Monitor(navn: "${message(code: 'monitor.cas.overskrift')}")
		def casUrl
		try {
			casUrl = grailsApplication.config.getProperty("cas.url ")
			def url = new URL( casUrl )
			url.text
			monitor.beskrivelse = "${message(code: 'monitor.cas.melding', args: [casUrl])}"
			monitor.status = true
		} catch (Exception e){
			monitor.status = false			
			monitor.beskrivelse = "${message(code: 'monitor.cas.feil', args: [casUrl])}"
			monitor.feilmelding = "Feil i kommunikasjon med cas: " + e.message
			log.error MONITOR_ERROR + monitor.feilmelding
		} 
		return monitor
	}  
	
	private Monitor sjekkLaasOppIoJob() {
		def monitor = new Monitor(navn: "Lås opp låste IO jobb")
		monitor.beskrivelse = "Sjekker om prosessen har kjørt de 15 siste minutter (skal kjøre hvert 10. minutt)."
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.SECOND, -900)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.LAAS_OPP_IO_JOB, fraDato.getTime() )
			
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen laasOppIoJob kan ha stoppet. Fant ingen som har kjørt de siste 15 minuttene."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}

	private Monitor sjekkHentBlaiseEventsJob() {
		def monitor = new Monitor(navn: "Hent Blaise Events fra Blaise-connector")
		monitor.beskrivelse = "Sjekker om prosessen BlaiseConnectorTrigger har kjørt de 20 siste minutter (skal kjøre hvert minutt)."

		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.SECOND, -1200)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.HENT_BLAISE_EVENTS_JOB, fraDato.getTime() )

			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen hentBlaiseEventsJob kan ha stoppet. Fant ingen som har kjørt de siste 5 minuttene."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}

		return monitor
	}

	private Monitor sjekkSendMeldingUtJob() {
		
		def monitor = new Monitor(navn: "Send melding ut jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.SECOND, -1200)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.SEND_MELDING_UT_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt de siste 20 minuttene (skal kjøre hvert 5. minutt)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen sendMeldingUtJob kan ha stoppet. Fant ingen som har kjørt de siste 20 minuttene."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}  
	
	private Monitor sjekkMeldingInnJob() {
		
		def monitor = new Monitor(navn: "Melding inn jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.SECOND, -1200)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.MELDING_INN_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt de siste 20 minuttene (skal kjøre hvert 5. minutt)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen sjekkMeldingInnJob kan ha stoppet. Fant ingen som har kjørt de siste 20 minuttene."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
    	
	private Monitor sjekkBlaiseTriggerJob() {
		
		def monitor = new Monitor(navn: "Blaise trigger(BlaiseEventInn) jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.SECOND, -1200)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.BLAISE_TRIGGER_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt de siste 20 minuttene (skal kjøre hvert 5. minutt)."
			
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen blaiseTriggerJob kan ha stoppet. Fant ingen som har kjørt de siste 20 minuttene."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
		
	private Monitor sjekkPaaVentJob() {
		def monitor = new Monitor(navn: "På vent jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.HOUR_OF_DAY, -24)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.PAA_VENT_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt siste døgn (skal kjøre hver natt kl 01.00)."
			
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen paaVentJob kan ha stoppet. Fant ingen som har kjørt det siste døgnet."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
		
	private Monitor sjekkRyddMeldingInnJob() {
		def monitor = new Monitor(navn: "Rydd melding inn jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.HOUR_OF_DAY, -24)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.RYDD_MELDING_INN_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen rydding i MELDING_INN tabellen har kjørt siste døgn (skal kjøre hver natt kl 02.00)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen ryddMeldingInnJob kan ha stoppet. Fant ingen som har kjørt det siste døgnet."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
	
	private Monitor sjekkRyddMeldingUtJob() {
		def monitor = new Monitor(navn: "Rydd melding ut jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.HOUR_OF_DAY, -24)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.RYDD_MELDING_UT_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen for rydding i MELDING_UT tabellen har kjørt siste døgn (skal kjøre hver natt kl 02.30)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen ryddMeldingUtJob kan ha stoppet. Fant ingen som har kjørt det siste døgnet."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
		
	private Monitor sjekkUtsendtCatiJob() {
		
		def monitor = new Monitor(navn: "Utsendt CATI jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.HOUR_OF_DAY, -24)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.UTSENDT_CATI_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt siste døgn (skal kjøre hver natt kl 05.00)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen utsendtCatiJob kan ha stoppet. Fant ingen som har kjørt det siste døgnet."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
		
	private Monitor sjekkKravRyddingJob() {	
		def monitor = new Monitor(navn: "Krav rydde jobb")
		
		try {
			Calendar fraDato = Calendar.getInstance()
			fraDato.add(Calendar.HOUR_OF_DAY, -24)
			ProsessLogg prosessLogg = prosessLoggService.finnProsessKjoringFraDato(ProsessNavn.KRAV_RYDDING_JOB, fraDato.getTime() )
			monitor.beskrivelse = "Sjekker om prosessen har kjørt siste døgn (skal kjøre hver natt kl 06.00)."
			if(!prosessLogg) {
				monitor.status = false
				monitor.feilmelding = "Prosessen kravRyddingJob kan ha stoppet. Fant ingen som har kjørt det siste døgnet."
				log.error MONITOR_ERROR + monitor.feilmelding
			}
			else {
				monitor.status = true
			}
		}
		catch (Exception e) {
			monitor.status = false
			monitor.feilmelding = e.getMessage()
			log.error MONITOR_ERROR + monitor.feilmelding
		}
		
		return monitor
	}
}

class Monitor {
	String navn
    boolean status
    String feilmelding    
    String beskrivelse
}