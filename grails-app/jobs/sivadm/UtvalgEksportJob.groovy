package sivadm

class UtvalgEksportJob {

	def concurrent = false

	UtvalgImportService utvalgImportService

	static triggers = {
	}

	def execute(context) {
		def utvalgImportId = context.mergedJobDataMap.get('utvalgImportId')
		def fil = context.mergedJobDataMap.get('fil')

		long start = System.currentTimeMillis()
		log.info "UtvalgEksportJob starting..."
		
		try	{
			UtvalgImport utvalgImport = UtvalgImport.get( Long.parseLong(utvalgImportId) )
			utvalgImportService.skrivUtvalgTilFil( utvalgImport, fil )
		}
		catch(Exception e) {
			log.error( e.getMessage())
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "UtvalgEksportJob executed in " + total + " ms"
	}
}
