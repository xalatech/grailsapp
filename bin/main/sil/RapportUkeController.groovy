package sil

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import sil.rapport.data.UkeRapportUkeNummerData

@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class RapportUkeController {

	def rapportUkeService
	
    def visRapport = {
		SilRapportUkeDatoCommand silRapportUkeDatoCommand = new SilRapportUkeDatoCommand()
		Date dato = silRapportUkeDatoCommand.dato
		
		if( !dato ) {
			dato = new Date()
		}
		Calendar now = Calendar.getInstance();
		now.setTime(dato);
		String aarstall = now.get(Calendar.YEAR);

		List ukeRapportDataList = rapportUkeService.hentUkeRapport(dato) 
		
		UkeRapportUkeNummerData ukeNumre = rapportUkeService.hentSisteUkeNummere(dato)
		
		return [
			ukeRapportDataList: ukeRapportDataList,
			ukeNumre: ukeNumre,
			dato: dato,
			aarstall: aarstall
		]	
	}
	
}

class SilRapportUkeDatoCommand implements Validateable {
	Date dato
}
