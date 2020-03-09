package sivadm

import util.TimeUtil;
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_INTERVJUER'])
class RapportEgneResultaterController {

	static final String HELE_LANDET = 'Hele landet'

	def rapportIntervjuerService
    def skjemaService
	def springSecurityService
	
	def visRapport = {
		EgneResultaterDatoCommand rapportIntervjuerDatoCommand = new EgneResultaterDatoCommand()
		
		def startDato = rapportIntervjuerDatoCommand.startDato
		def sluttDato = rapportIntervjuerDatoCommand.sluttDato
		
		// Setter dato til naa hvis ikke oppgitt
		if( !startDato ) {
			startDato = new Date()
		}
		startDato = TimeUtil.getStartOfDay(startDato)
		
		// Setter dato til naa hvis ikke oppgitt
		if( !sluttDato ) {
			sluttDato = new Date()
		}
		sluttDato = TimeUtil.getEndOfDay(sluttDato)
		
		def skjema
		if( params.skjema ) {
			skjema = Skjema.get(Long.parseLong( params.skjema))
		}
		
		def intervjuerResultatList = null
		Bruker bruker = Bruker.findById(springSecurityService.currentUser.id)
		Intervjuer intervjuer = Intervjuer.findByInitialer(bruker.username)
		def klyngeList = Klynge.findById(intervjuer.klynge.id)
		def klyngeMap = lagKlyngeMap(klyngeList)
		def skjemaList = skjemaService.finnAlleSortertPaaOppstart()
		
		if( ! params.ikkeGenerer ) {
			if( ! params.klynge ) {
				intervjuerResultatList = rapportIntervjuerService.hentRapportForIntervjuer(intervjuer, skjema, startDato, sluttDato)
			}else {
				if ( (klyngeMap.get(params.klynge)).equals(HELE_LANDET) ){ 
					intervjuerResultatList = rapportIntervjuerService.hentRapportForHeleLandet(skjema, startDato, sluttDato)
				}else {
					def klyngeNavn = klyngeMap.get(params.klynge)
					def klynge = Klynge.findByKlyngeNavn(klyngeNavn)
				 	intervjuerResultatList = rapportIntervjuerService.hentRapportForKlynge(klynge, skjema, startDato, sluttDato)
				}
			}
		}
		def intervjuerResultat = intervjuerResultatList==null ?  null : intervjuerResultatList.last() 
		return [
			navn: bruker.navn,
			intervjuerResultat: intervjuerResultat,
			klyngeMap: klyngeMap,
			initialer: bruker.username,
			klyngeId: params.klynge,
			startDato: startDato,
			sluttDato: sluttDato,
			skjemaList: skjemaList,
			skjemaId: params.skjema
		]
	}

	private Map lagKlyngeMap(def klyngeList) {
		int i=0
		def klyngeMap = [:]
		klyngeList.each {
			klyngeMap.put(Integer.toString(i), it.klyngeNavn)
			i++
		}
		klyngeMap.put(Integer.toString(i), HELE_LANDET)
		return klyngeMap
	}
}

class EgneResultaterDatoCommand implements grails.validation.Validateable {
	Date startDato
	Date sluttDato
}


