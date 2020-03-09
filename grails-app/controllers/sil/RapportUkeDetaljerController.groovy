package sil

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import siv.type.IntervjuerStatus
import sivadm.Intervjuer
import sivadm.Klynge
import util.TimeUtil
/**
 * 
 * @author vak
 * @Deprecated erstattet av DetaljertTidsrapportController
 */
@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class RapportUkeDetaljerController {

	
	def rapportUkeDetaljerService // må slettes
	
	def detaljertTidsrapportService
	
    
	def visRapport = {
		RapportUkeDetaljerDatoCommand rapportUkeDetaljerDatoCommand = new RapportUkeDetaljerDatoCommand()
		//-------------------------------------------------------------
//		Date dato = rapportUkeDetaljerDatoCommand.dato
//
//		// se om det ligger noe i session hvis ikke noe er spesifisert i soeket
//		if( !dato ) {
//			dato = session.rapportUkeDetaljerDato
//		}
//
//		// hvis det hverken er noe i soeket eller session hent dagens dato
//		if( !dato ) {
//			dato = new Date()
//		}
//
//		// vi lagrer i session for aa faa sortable colums til aa virke
//		session.rapportUkeDetaljerDato = dato
//
		def startDato = rapportUkeDetaljerDatoCommand.startDato
		def sluttDato = rapportUkeDetaljerDatoCommand.sluttDato
		
		// se om det ligger noe i session hvis ikke noe er spesifisert i soeket
		if( !startDato ) {
			startDato = session.rapportUkeDetaljerStartDato
		}
		// Setter dato til naa hvis ikke oppgitt
		if( !startDato ) {
			startDato = new Date()
		}
		startDato = TimeUtil.getStartOfDay(startDato)
		
		
		// se om det ligger noe i session hvis ikke noe er spesifisert i soeket
		if( !sluttDato ) {
			sluttDato = session.rapportUkeDetaljerStartSluttDato
		}
		// Setter dato til naa hvis ikke oppgitt
		if( !sluttDato ) {
			sluttDato = new Date()
		}
		sluttDato = TimeUtil.getEndOfDay(sluttDato)
		
		
		// vi lagrer i session for aa faa sortable colums til aa virke
		session.rapportUkeDetaljerStartDato      = startDato
		session.rapportUkeDetaljerStartSluttDato = sluttDato
		
		def klyngeList = Klynge.list()
		
		def initialer = params.initialer
		def klynge = params.klynge
		def delProduktNummer = params.delProduktNummer
		def intervjuerStatus = params.intervjuerStatus
		
		def ukeRapportDetaljerDataList
		
		def iStatus
		if (intervjuerStatus){
			iStatus = IntervjuerStatus.valueOf(intervjuerStatus)
		}
		
		if( ! params.ikkeGenerer ) {
			if( initialer ) {
				Intervjuer intervjuer = Intervjuer.findByInitialer(initialer.toLowerCase())
				if (intervjuer) {
					ukeRapportDetaljerDataList = detaljertTidsrapportService.hentRapportForIntervjuer(startDato, sluttDato, intervjuer, delProduktNummer)
					klynge = null
				}
			}
			else if( klynge ) {
				Klynge kl = Klynge.get(Long.parseLong( klynge ))
				ukeRapportDetaljerDataList = detaljertTidsrapportService.hentRapportForKlynge(startDato, sluttDato, kl, iStatus, delProduktNummer)
			}
			else if( intervjuerStatus ) {
				ukeRapportDetaljerDataList = detaljertTidsrapportService.hentRapportForIntervjuerStatus(startDato, sluttDato, iStatus, delProduktNummer)
			}
			else if( delProduktNummer ) {
				ukeRapportDetaljerDataList = detaljertTidsrapportService.hentRapportForDelProduktNummer(startDato, sluttDato, delProduktNummer)
			}
			else {
				ukeRapportDetaljerDataList = detaljertTidsrapportService.hentRapportForAlleIntervjuere(startDato, sluttDato)
			}
		}
		
		if( ukeRapportDetaljerDataList != null && ukeRapportDetaljerDataList.size() > 0) {
			ukeRapportDetaljerDataList = sorterRapport( ukeRapportDetaljerDataList, params.sort, params.order )
		}
		
		return [
			ukeRapportDetaljerDataList: ukeRapportDetaljerDataList,
			startDato: startDato,
			sluttDato: sluttDato,
			intervjuerStatusList: IntervjuerStatus.values(),
			klyngeList: klyngeList
		]
	}
	
	private List sorterRapport( List ukeRapportDetaljerDataList, String sort, String order ) {
		if( sort == "intervjuerNummer" ) {
			ukeRapportDetaljerDataList.sort { it.intervjuerNummer }
		}
		else if( sort == "navn" ) {
			ukeRapportDetaljerDataList.sort { it.navn }
		}
		else if( sort == "initialer" ) {
			ukeRapportDetaljerDataList.sort { it.initialer }
		}
		else if( sort == "skjemaNavn" ) {
			ukeRapportDetaljerDataList.sort { it.skjemaNavn }
		}
		else if( sort == "delProduktNummer" ) {
			ukeRapportDetaljerDataList.sort { it.delProduktNummer }
		}
		else if( sort == "arbeidsDato" ) {
			ukeRapportDetaljerDataList.sort { it.arbeidsDato }
		}
		else if( sort == "arbeidsTid" ) {
			ukeRapportDetaljerDataList.sort { it.arbeidsTid }
		}
		else if( sort == "reiseTid" ) {
			ukeRapportDetaljerDataList.sort { it.reiseTid }
		}
		else if( sort == "ovelseTid" ) {
			ukeRapportDetaljerDataList.sort { it.ovelseTid }
		}
		else if( sort == "totalTid" ) {
			ukeRapportDetaljerDataList.sort { it.totalTid }
		}
		
		if( order == "desc" ) {
			ukeRapportDetaljerDataList = ukeRapportDetaljerDataList.reverse()
		}
		return ukeRapportDetaljerDataList
	}
}

class RapportUkeDetaljerDatoCommand implements Validateable {
//	Date dato
	Date startDato
	Date sluttDato
}
