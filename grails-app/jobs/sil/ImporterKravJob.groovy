package sil

import sil.Krav;
import siv.type.LoggType;
import sivadm.Kjorebok;
import sivadm.Timeforing;
import sivadm.Utlegg;

class ImporterKravJob {
	
	def concurrent = false
	
	def kravService
	def loggService
	def brukerService
	def sivAdmService
	
	static triggers = {
	}
	
	def execute(context) {

		long start = System.currentTimeMillis()
		log.info "ImporterKravJob starting..."
		
		def logg
		
		try	{
			def bruker = context.mergedJobDataMap.get('bruker')
			
			logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Importerer krav", "Starter import", bruker)
			
			List<Timeforing> timeforinger = sivAdmService.hentInnsendteTimeforinger()
			List<Kjorebok> kjoreboker = sivAdmService.hentInnsendteKjoreboker()
			List<Utlegg> utlegg = sivAdmService.hentInnsendteUtlegg()
			
			List<List<Krav>> list = kravService.konverterTilKrav(timeforinger, kjoreboker, utlegg)
			
			List<Krav> kravListe = list[0]
			
			def melding
			
			if(!kravListe) {
				melding = "Fant ingen timeføringer, kjørebøker eller utlegg å importere til krav."
			}
			else {
				kravListe.each { k ->
					k.save(failOnError:true)
				}
				melding = "${timeforinger.size()} timeføringer, ${kjoreboker.size()} kjørebøker og ${utlegg.size()} utlegg importert til ${kravListe.size()} krav."
			}
			
			loggService.stoppProsess(logg, melding, false)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
			loggService.stoppProsess(logg, "Importer krav jobb feilet: " + e.getMessage(), true)
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		
		log.info "ImporterKravJob executed in " + total + " ms"
	}
	
	
	
}