package sivadm

class UtvalgSlettJob {

	def concurrent = false
	
	UtvalgImportService utvalgImportService
	
	static triggers = {
	}
	
	def execute(context) {
		Long utvalgImportId = context.mergedJobDataMap.get('utvalgImportId')

		long start = System.currentTimeMillis()
		log.info "UtvalgSlettJob starting..."
		
		UtvalgImport utvalgImport = UtvalgImport.get( utvalgImportId )

		try	{
			utvalgImportService.deleteUtvalg(utvalgImport)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "UtvalgSlettJob executed in " + total + " ms"
	}
	
}
