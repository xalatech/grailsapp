package sil

import sil.Krav;
import sil.type.KravStatus;
import siv.type.LoggType;
import sivadm.Kjorebok;
import sivadm.Timeforing;
import sivadm.Utlegg;

class KjorAutomatiskeKontrollerJob {
	
	def concurrent = false
	
	def loggService
	def brukerService
	def automatiskKontrollService
	
	static triggers = {
	}
	
	def execute(context) {

		long start = System.currentTimeMillis()
		log.info "KjorAutomatiskeKontrollerJob starting..."
		
		def logg
		def melding
		
		try	{
			def bruker = context.mergedJobDataMap.get('bruker')
			
			logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Kjører automatiske kontroller", "Starter automatiske kontroller", bruker)
			
			List<Krav> kravListe = Krav.findAllByKravStatus(KravStatus.OPPRETTET)
			
			if(!kravListe) {
				melding = "Fant ingen krav å kjøre automatisk kontroll på"
				loggService.stoppProsess(logg, melding, false)
				long stop = System.currentTimeMillis()
				long total = stop - start
				log.info "KjorAutomatiskeKontrollerJob executed in " + total + " ms"
				return
			}
			
			List<Krav> feiletListe = automatiskKontrollService.kjorAutomatiskeKontroller(kravListe)
			
			if(!feiletListe) {
				melding = "Har kjørt automatisk kontroll på ${kravListe.size()} krav, hvorav alle bestod de automatiske kontrollene."
			}
			else {
				melding = "Har kjørt automatisk kontroll på ${kravListe.size()} krav, hvorav ${feiletListe.size()} av dem feilet, bruk linken Intervjuere til kontroll i menyen for å se på disse kravene."
			}
			
			loggService.stoppProsess(logg, melding, false)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
			loggService.stoppProsess(logg, "Kjøring av automatiske kontroller feilet: " + e.getMessage(), true)
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		
		log.info "KjorAutomatiskeKontrollerJob executed in " + total + " ms"
	}
	
	
	
}