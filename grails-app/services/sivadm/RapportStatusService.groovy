package sivadm

class RapportStatusService {

	static transactional = true

	def intervjuObjektService

	/**
	 * Finner antall ferdige / intervju
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallFerdige(  Long skjemaId, Long periodeNummer ) {
		
		def antallIntervju = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).intervju.count() 
		
		return antallIntervju
	}

	/**
	 * Finner antall ferdige / intervju
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallFerdige(  Long skjemaId, Long periodeNummer,  Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallIntervju = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'intervju')

		return antallIntervju
	}

	/**
	 * Finner antall ferdige / frafall
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallFerdigeFrafall(  Long skjemaId, Long periodeNummer ) {

		def antallFerdigeFrafall = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).frafall.count()

		return antallFerdigeFrafall
	}

	/**
	 * Finner antall ferdige / frafall
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallFerdigeFrafall(  Long skjemaId, Long periodeNummer,  Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallFerdigeFrafall = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'frafall')

		return antallFerdigeFrafall
	}

	/**
	 * Finner antall avganger
	 * 
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallAvganger(  Long skjemaId, Long periodeNummer ) {
		
		def antallAvganger = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).avgang.count()	
		
		return antallAvganger
	}

	/**
	 * Finner antall avganger
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallAvganger(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallAvganger = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'avgang')

		return antallAvganger
	}

	/**
	 * Finner antall ubehandlede
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUbehandlede(  Long skjemaId, Long periodeNummer ) {
		
		def antallUbehandlede = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).ubehandlede.count()
		
		return antallUbehandlede
	}

	/**
	 * Finner antall ubehandlede
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUbehandlede(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil  ) {

		def antallUbehandlede = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'ubehandlede')

		return antallUbehandlede
	}

	/**
	 * Finner antall påbegynt
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallPaabegynt(  Long skjemaId, Long periodeNummer ) {

		def antallPaabegynt = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).paabegynt.count()

		return antallPaabegynt
	}

	/**
	 * Finner antall påbegynt
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallPaabegynt(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallPaabegynt = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'paabegynt')

		return antallPaabegynt
	}

	/**
	 * Finner antall på vent
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallPaaVent(  Long skjemaId, Long periodeNummer ) {

		def antallPaaVent = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).paaVent.count()

		return antallPaaVent
	}

	/**
	 * Finner antall på vent
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallPaaVent(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallPaaVent = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'paaVent')

		return antallPaaVent
	}

	/**
	 * Finner antall nektere på vent
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallNekterePaaVent(  Long skjemaId, Long periodeNummer ) {

		def antallNekterePaaVent = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).nekterePaaVent.count()

		return antallNekterePaaVent
	}

	/**
	 * Finner antall nektere på vent
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallNekterePaaVent(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallNekterePaaVent = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'nekterePaaVent')

		return antallNekterePaaVent
	}

	/**
	 * Finner antall tildelt capi
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallTildelteCapi(  Long skjemaId, Long periodeNummer ) {
		
		def antallUtsendtCapi = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).utsendtCapi.count()
		
		return antallUtsendtCapi
	}

	/**
	 * Finner antall tildelt capi
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallTildelteCapi(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallUtsendtCapi = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'utsendtCapi')

		return antallUtsendtCapi
	}

	/**
	 * Finner antall utsendt cati
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteCati(  Long skjemaId, Long periodeNummer ) {
		def antallUtsendtCati = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).utsendtCati.count()
		
		return antallUtsendtCati
	}

	/**
	 * Finner antall utsendt cati
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteCati(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {
		def antallUtsendtCati = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'utsendtCati')

		return antallUtsendtCati
	}

	/**
	 * Finner antall utsendt cati web
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteCatiWeb(  Long skjemaId, Long periodeNummer ) {
		def antallUtsendtCatiWeb = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).utsendtCatiWeb.count()

		return antallUtsendtCatiWeb
	}

	/**
	 * Finner antall utsendt cati web
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteCatiWeb(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {
		def antallUtsendtCatiWeb = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'utsendtCatiWeb')

		return antallUtsendtCatiWeb
	}

	/**
	 * Finner antall utsendt web
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteWeb(  Long skjemaId, Long periodeNummer ) {
		def antallUtsendtWeb = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).utsendtWeb.count()

		return antallUtsendtWeb
	}

	/**
	 * Finner antall utsendt web
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallUtsendteWeb(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {
		def antallUtsendtWeb = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'utsendtWeb')

		return antallUtsendtWeb
	}


	/**
	 * Finner antall innlastede
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallInnlastede(  Long skjemaId, Long periodeNummer ) {
		
		def antallInnlastet = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).innlastet.count()
		
		return antallInnlastet
	}

	/**
	 * Finner antall innlastede
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnAntallInnlastede(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antallInnlastet = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'innlastet')

		return antallInnlastet
	}


	/**
	 * Finner totalt antall
	 *  
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnTotaltAntall(  Long skjemaId, Long periodeNummer ) {
		
		def antall = IntervjuObjekt.medSkjemaIdOgPeriodeNummer(skjemaId, periodeNummer).count()
		
		return antall
	}

	/**
	 * Finner totalt antall
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	public int finnTotaltAntall(  Long skjemaId, Long periodeNummer, Integer intervjuStatusFra, Integer intervjuStatusTil ) {

		def antall = intervjuObjektService.antallIntervjuObjekterMedTidligereIntervjustatus(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil, 'alle')

		return antall
	}
}
