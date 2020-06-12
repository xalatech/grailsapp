package sil

import siv.type.LoggType;

class GodkjennAlleSomBestodAutomatiskKontrollJob {
	
	def concurrent = false
	
	def kravService
	def loggService
	def brukerService
	
	static triggers = {
	}
	
	def execute(context) {
		long start = System.currentTimeMillis()
		
		log.info "GodkjennAlleSomBestodAutomatiskKontrollJob starting..."
		
		def logg
		
		try	{
			def bruker = context.mergedJobDataMap.get('bruker')
			
			logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Godkjenner krav som bestod test.", "Starter godkjenning.", bruker)
			
			int cnt = kravService.godkjennAlleBestodAutomatiskKontroll()
			
			def melding =  "Har godkjent ${cnt} krav som hadde status 'Bestod automatisk kontroll'"
			
			loggService.stoppProsess(logg, melding, false)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
			loggService.stoppProsess(logg, "Godkjenning feilet: " + e.getMessage(), true)
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		
		log.info "GodkjennAlleSomBestodAutomatiskKontrollJob executed in " + total + " ms"
	}
}