package sivadm


/**
 * @deprecated 
 * 
 * Dette var kun i bruk ifm. funksonalitet for å plassere csv fil til
 * en katalog på serversiden.
 * Dette er nå erstattet av nedlasting av fil direkte til brukerens pc.
 * SE : IntervjuObjektController.searchResultLastNed
 * 
 */

class ListeEksportJob {
	// Lagd etter mønster av UtvalgEksportJob.groovy

	def concurrent = false

	UtvalgImportService utvalgImportService
	IntervjuObjektService intervjuObjektService

	static triggers = {
	}

	def execute(context) {
		def listeData = context.mergedJobDataMap.get('listeData')								// NY
		def fil = context.mergedJobDataMap.get('fil')

		long start = System.currentTimeMillis()
		log.info "ListeEksportJob starting..."
		
		try	{
			def intervjuObjektInstanceList = intervjuObjektService.searchAll(listeData)			// NY
			utvalgImportService.skrivUtvalgListeTilFil( intervjuObjektInstanceList, fil )		// NY
		}
		catch(Exception e) {
			log.error( e.getMessage())
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "ListeEksportJob executed in " + total + " ms"
	}
}
