package sivadm

import util.TimeUtil;
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT'])
class RapportIntervjuerController {

	def rapportIntervjuerService
    def skjemaService
	
	def visRapport = {
		RapportIntervjuerDatoCommand rapportIntervjuerDatoCommand = new RapportIntervjuerDatoCommand()
		
		def startDato = rapportIntervjuerDatoCommand.startDato
		def sluttDato = rapportIntervjuerDatoCommand.sluttDato
		
		// Setter dato til naa hvis ikke oppgitt
		if( !startDato ) {
			startDato = new Date()
		}
		
		// Setter dato til naa hvis ikke oppgitt
		if( !sluttDato ) {
			sluttDato = new Date()
		}
		
		startDato = TimeUtil.getStartOfDay(startDato)
		sluttDato = TimeUtil.getEndOfDay(sluttDato)
		
		def skjema
		
		if( params.skjema ) {
			skjema = Skjema.get(Long.parseLong( params.skjema))
		}
		
		def intervjuerResultatList
		
		if( ! params.ikkeGenerer ) {
			if( params.initialer ) {
				Intervjuer intervjuer = Intervjuer.findByInitialer(params.initialer.toLowerCase())
				if (intervjuer) {
					intervjuerResultatList = rapportIntervjuerService.hentRapportForIntervjuer(intervjuer, skjema, startDato, sluttDato)
					params.klynge = null
				}
			}
			else if( params.klynge ) {
				Klynge klynge = Klynge.get(Long.parseLong( params.klynge ))
				intervjuerResultatList = rapportIntervjuerService.hentRapportForKlynge(klynge, skjema, startDato, sluttDato)
			}
			else {
				intervjuerResultatList = rapportIntervjuerService.hentRapportForAlleIntervjuere(skjema, startDato, sluttDato)
			}
		}
		
		def klyngeList = Klynge.list()
		
		def skjemaList = skjemaService.finnAlleSortertPaaOppstart()
		
		return [
			intervjuerResultatList: intervjuerResultatList,
			klyngeList: klyngeList,
			initialer: params.initialer,
			klyngeId: params.klynge,
			startDato: startDato,
			sluttDato: sluttDato,
			skjemaList: skjemaList,
			skjemaId: params.skjema
		]
	}
}

class RapportIntervjuerDatoCommand implements grails.validation.Validateable {
	Date startDato
	Date sluttDato
}


