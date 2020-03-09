package sivadm

import service.UtvalgService;
import util.UtvalgUtil;
import exception.DuplikatIntervjuObjektException;
import parser.*;
import sivadm.Skjema;
import sivadm.UtvalgImport;
import sivadm.UtvalgImportService;

class UtvalgImportJob {

	def concurrent = false

	UtvalgImportService utvalgImportService

	static triggers = {
	}

	def execute(context) {
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