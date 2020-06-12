package sil

import siv.type.LoggType;

class ImporterKravKjorKontrollerJob {

	def concurrent = false
	
	def kravService
	def loggService
	def brukerService
	
	static triggers = {
	}
	
	def execute(context) {

		long start = System.currentTimeMillis()
		log.info "ImporterKravKjorKontrollerJob starting..."
		
		def logg
		
		try	{
			def bruker = context.mergedJobDataMap.get('bruker')
			
			logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Importerer krav og kjører automatiske kontroller", "Starter import", bruker)
			
			List<Integer> cntList = kravService.importerKravKjorAutoKontrollerGenererTilManuellKontroll()
			
			def melding = "Har importert ${cntList[0]} krav, kjørt automatiske kontroller på disse og generert utvalg til manuell kontroll. ${cntList[1]} intervjuere ble valgt ut til manuell kontroll."
			
			loggService.stoppProsess(logg, melding, false)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
			loggService.stoppProsess(logg, "Importer krav og kjør automatiske kontroller jobb feilet: " + e.getMessage(), true)
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		
		log.info "ImporterKravKjorKontrollerJob executed in " + total + " ms"
	}

}
