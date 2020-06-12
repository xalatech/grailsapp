package sivadm


import sivadm.Skjema
import sivadm.UtvalgImportService

class UtvalgImportJob {

	static concurrent = false

	UtvalgImportService utvalgImportService

	static triggers = {
	}

	void execute(context) {
		def utvalgTekst = context.mergedJobDataMap.get('utvalgTekst')
		def importertAv = context.mergedJobDataMap.get('importertAv')
		def skjemaId = context.mergedJobDataMap.get('skjemaId')

		long start = System.currentTimeMillis()
		log.info "UtvalgImportJob starting..."
		
		Skjema skjema = Skjema.get( Long.parseLong(skjemaId) )

		try	{
			utvalgImportService.importUtvalg(utvalgTekst, importertAv, skjema)
		}
		catch(Exception e) {
			def feilMsg = e.getMessage()
			if( feilMsg && feilMsg.length() > 250 ) {
				feilMsg = feilMsg.substring (0, 250)
			}
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "UtvalgImportJob executed in " + total + " ms"
	}
}