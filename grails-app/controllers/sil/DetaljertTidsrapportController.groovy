package sil

import grails.plugin.springsecurity.annotation.Secured
import util.TimeUtil
import siv.type.IntervjuerStatus
import sivadm.Intervjuer
import sivadm.Klynge
// Etter mal fra RapportUkeDetaljerController

@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class DetaljertTidsrapportController {

	def detaljertTidsrapportService
    
	def visRapport = { detaljertTidsrapportCommand ->
		def startDato = detaljertTidsrapportCommand.startDato
		def sluttDato = detaljertTidsrapportCommand.sluttDato
		
		// se om det ligger noe i session hvis ikke noe er spesifisert i soeket
		if( !startDato ) {
			startDato = session.detaljertTidsrapportStartDato
		}
		// Setter dato til naa hvis ikke oppgitt
		if( !startDato ) {
			startDato = new Date()
		}
		startDato = TimeUtil.getStartOfDay(startDato)
		
		// se om det ligger noe i session hvis ikke noe er spesifisert i soeket
		if( !sluttDato ) {
			sluttDato = session.detaljertTidsrapportSluttDato
		}
		// Setter dato til naa hvis ikke oppgitt
		if( !sluttDato ) {
			sluttDato = new Date()
		}
		sluttDato = TimeUtil.getEndOfDay(sluttDato)
		
		// vi lagrer i session for aa faa sortable colums til aa virke
		session.detaljertTidsrapportStartDato      = startDato
		session.detaljertTidsrapportSluttDato = sluttDato
		
		def klyngeList = Klynge.list()
		def initialer = params.initialer
		def klynge = params.klynge
		def delProduktNummer = params.delProduktNummer
		def intervjuerStatus = params.intervjuerStatus
		
		def detaljertTidsrapportDataList
		
		def iStatus
		if (intervjuerStatus){
			iStatus = IntervjuerStatus.valueOf(intervjuerStatus)
		}
		
		if( ! params.ikkeGenerer ) {
			if( initialer ) {
				Intervjuer intervjuer = Intervjuer.findByInitialer(initialer.toLowerCase())
				if (intervjuer) {
					detaljertTidsrapportDataList = detaljertTidsrapportService.hentRapportForIntervjuer(startDato, sluttDato, intervjuer, delProduktNummer)
					klynge = null
				}
			}
			else if( klynge ) {
				Klynge kl = Klynge.get(Long.parseLong( klynge ))
				detaljertTidsrapportDataList = detaljertTidsrapportService.hentRapportForKlynge(startDato, sluttDato, kl, iStatus, delProduktNummer)
			}
			else if( intervjuerStatus ) {
				detaljertTidsrapportDataList = detaljertTidsrapportService.hentRapportForIntervjuerStatus(startDato, sluttDato, iStatus, delProduktNummer)
			}
			else if( delProduktNummer ) {
				detaljertTidsrapportDataList = detaljertTidsrapportService.hentRapportForDelProduktNummer(startDato, sluttDato, delProduktNummer)
			}
			else {
				detaljertTidsrapportDataList = detaljertTidsrapportService.hentRapportForAlleIntervjuere(startDato, sluttDato)
			}
		}
		
		if( detaljertTidsrapportDataList != null && detaljertTidsrapportDataList.size() > 0) {
			detaljertTidsrapportDataList = sorterRapport( detaljertTidsrapportDataList, params.sort, params.order )
		}
		
		return [
			detaljertTidsrapportDataList: detaljertTidsrapportDataList,
			startDato: startDato,
			sluttDato: sluttDato,
			intervjuerStatusList: IntervjuerStatus.values(),
			klyngeList: klyngeList
		]
	}
	
	private List sorterRapport( List detaljertTidsrapportDataList, String sort, String order ) {
		if( sort == "intervjuerNummer" ) {
			detaljertTidsrapportDataList.sort { it.intervjuerNummer }
		}
		else if( sort == "navn" ) {
			detaljertTidsrapportDataList.sort { it.navn }
		}
		else if( sort == "initialer" ) {
			detaljertTidsrapportDataList.sort { it.initialer }
		}
		else if( sort == "skjemaNavn" ) {
			detaljertTidsrapportDataList.sort { it.skjemaNavn }
		}
		else if( sort == "delProduktNummer" ) {
			detaljertTidsrapportDataList.sort { it.delProduktNummer }
		}
		else if( sort == "arbeidsDato" ) {
			detaljertTidsrapportDataList.sort { it.arbeidsDato }
		}
		else if( sort == "arbeidsTid" ) {
			detaljertTidsrapportDataList.sort { it.arbeidsTid }
		}
		else if( sort == "reiseTid" ) {
			detaljertTidsrapportDataList.sort { it.reiseTid }
		}
		else if( sort == "ovelseTid" ) {
			detaljertTidsrapportDataList.sort { it.ovelseTid }
		}
		else if( sort == "totalTid" ) {
			detaljertTidsrapportDataList.sort { it.totalTid }
		}
		
		if( order == "desc" ) {
			detaljertTidsrapportDataList = detaljertTidsrapportDataList.reverse()
		}
		
		return detaljertTidsrapportDataList
	}
}

class DetaljertTidsrapportCommand {
	Date startDato
	Date sluttDato
}
