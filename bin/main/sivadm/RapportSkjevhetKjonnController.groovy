package sivadm

import siv.rapport.data.skjevhet.SkjevhetData;
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class RapportSkjevhetKjonnController {

	def rapportSkjevhetKjonnService
	
	def visRapport = {
		
		def rapportType = params.rapportType
		
		// default verdi = prosent
		if( ! rapportType ) {
			rapportType = "Prosent"
		}
	
		Long skjemaId = Long.parseLong( params.id ) 
		
		List<SkjevhetData> skjevhetKjonnList = null
		List<SkjevhetData> skjevhetAlderList = null
		List<SkjevhetData> skjevhetLandsdelList = null
		
		if( rapportType == "Prosent") {
			skjevhetKjonnList =  rapportSkjevhetKjonnService.hentSkjevhetRapportKjonnIProsent(skjemaId)
			skjevhetAlderList = rapportSkjevhetKjonnService.hentSkjevhetRapportAlderIProsent(skjemaId)
			skjevhetLandsdelList = rapportSkjevhetKjonnService.hentSkjevhetRapportLandsdelIProsent(skjemaId)
		}
		else if ( rapportType == "Absolutte tall" ){
			skjevhetKjonnList =  rapportSkjevhetKjonnService.hentSkjevhetRapportKjonn(skjemaId)
			skjevhetAlderList = rapportSkjevhetKjonnService.hentSkjevhetRapportAlder(skjemaId)
			skjevhetLandsdelList = rapportSkjevhetKjonnService.hentSkjevhetRapportLandsdel(skjemaId)
		}
		
		return [ 
			skjema: Skjema.get(skjemaId),
			skjemaId: skjemaId,
			rapportType: rapportType,
			skjevhetKjonnList: skjevhetKjonnList,
			skjevhetAlderList: skjevhetAlderList,
			skjevhetLandsdelList : skjevhetLandsdelList
		]	
	}
}
