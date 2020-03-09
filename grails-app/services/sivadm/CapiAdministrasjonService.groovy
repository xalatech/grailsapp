package sivadm

import java.util.Map;

import siv.type.SkjemaStatus;

class CapiAdministrasjonService {

	static transactional = true
	
	/**
	 * Finner intervjuobjekter som skal tildeles basert på gitte kriterier. Returnerer ikke nødvendigvis alle, det kommer an på 
	 * pagineringsdataene.
	 * 
	 * @param skjemaId
	 * @param periodeId
	 * @param intervjuObjektNummer
	 * @param tildelingStatus
	 * @param skjemaStatus
	 * @param klyngeId
	 * @param pagination
	 * @return en liste med intervjuobjekter
	 */
	public List<IntervjuObjekt> finnIntervjuObjekterForTildeling( Long skjemaId, Long periodeId, String intervjuObjektNummer,  String tildelingStatus, SkjemaStatus skjemaStatus, Long klyngeId, Map pagination) {
		return finnIntervjuObjekter(skjemaId, periodeId, intervjuObjektNummer, tildelingStatus, skjemaStatus, klyngeId, pagination, false) 
	}


	/**
	 * Returnerer antall intervjuobjekter totalt.
	 *  
	 * @param skjemaId
	 * @param periodeId
	 * @param intervjuObjektNummer
	 * @param tildelingStatus
	 * @param skjemaStatus
	 * @param klyngeId
	 * @return antall intervjuobjekter
	 */
	public int hentAntallIntervjuObjekterForTildeling( Long skjemaId, Long periodeId, String intervjuObjektNummer,  String tildelingStatus, SkjemaStatus skjemaStatus, Long klyngeId) {
		def ioList = finnIntervjuObjekter(skjemaId, periodeId, intervjuObjektNummer, tildelingStatus, skjemaStatus, klyngeId, null, true)	
		return ioList.get(0)
	}


	
	/**
	 * Returnerer enten en liste med alle intervjuobjekter som tilfredstiller søket hvis ikke paginering er satt. Kan også antall intervjuobjekter dersom count = true.
	 * 
	 * @param skjemaId
	 * @param periodeId
	 * @param intervjuObjektNummer
	 * @param tildelingStatus
	 * @param skjemaStatus
	 * @param klyngeId
	 * @param pagination
	 * @param count 
	 * @return enten en liste med intervjuobjekter eller et antall
	 */
	private def finnIntervjuObjekter( Long skjemaId, Long periodeId, String intervjuObjektNummer,  String tildelingStatus, SkjemaStatus skjemaStatus, Long klyngeId, Map pagination, boolean count) {

		def c = IntervjuObjekt.createCriteria()

		def intervjuObjekter = c {
			if(count ) {
				projections { countDistinct("id") }
			}
			
			if( pagination ) {
				int max = pagination.max
				int first = pagination.offset? Integer.parseInt(pagination.offset) : 0
				
				if(first )	{
					firstResult(first)
				}

				if(max) {
					maxResults(max)
				}
			}

			// Skjema
			if( skjemaId != null ) {
				periode {
					skjema { eq("id", skjemaId) }
				}
			}

			// Periodenummer
			if( periodeId != null ) {
				periode { eq("id", periodeId) }
			}

			// Intervjuobjektnummer
			if( intervjuObjektNummer ) {
				like("intervjuObjektNummer", intervjuObjektNummer)
			}

			// Skjemastatus
			if( skjemaStatus != null ) {
				eq( "katSkjemaStatus", skjemaStatus )
			}
			
			// Tildelingstatus (alle, tildelt eller ikke tildelt)
			if( tildelingStatus) {
				if( tildelingStatus == "tildelt")  {
					eq("katSkjemaStatus", SkjemaStatus.Utsendt_CAPI)
				}
				else if(tildelingStatus == "ikkeTildelt") {
					ne("katSkjemaStatus", SkjemaStatus.Utsendt_CAPI)
				}
			}

			// Klynge
			if(klyngeId != null) {
				def kommuneNummerList = Klynge.get(klyngeId).getKommuneNummerList()

				if(kommuneNummerList != null && kommuneNummerList.size() > 0 ) {
					adresser{ 'in'( "kommuneNummer", kommuneNummerList) }
				}
				else {
					adresser{ eq( "kommuneNummer", "-1") }
				}
			}

			// Ta ikke med ferdige
			ne("katSkjemaStatus", SkjemaStatus.Ferdig )
		}

		return intervjuObjekter
	}
}
