package sivadm

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import sivadm.Skjema

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class RapportStatusController {

	def intervjuObjektService
	def rapportStatusService

	def velgSkjema = {
		def skjemaList = Skjema.list()
		[ skjemaList: skjemaList ]
	}

	def velgTidligereIntervjustatus = {
		VelgTidligereIntervjustatusCommand velgTidligereIntervjustatusCommand = new VelgTidligereIntervjustatusCommand()

		Long skjemaId = Long.parseLong (params.id)
		return [
				skjema: Skjema.get(skjemaId),
				velgTidligereIntervjustatusCommand: velgTidligereIntervjustatusCommand
		]
	}

	def visRapport = {
		VelgTidligereIntervjustatusCommand velgTidligereIntervjustatusCommand = new VelgTidligereIntervjustatusCommand()

		if (params.intervjustatuskoder) {
			session.intervjustatuskoder = params.intervjustatuskoder
			session.intervjuStatusFra = params.intervjuStatusFra
			session.intervjuStatusTil = params.intervjuStatusTil
		}

		Long skjemaId = Long.parseLong (params.id)
		Long periodeNummer = null
		
		if( params.periode ) {
			periodeNummer = Long.parseLong (params.periode)
		}

		def kakeDiagramData
		def periodeRapportList
		def intervjustatuskoder = velgTidligereIntervjustatusCommand.hasErrors() ? '' : session.intervjustatuskoder
		def intervjuStatusFra = session.intervjuStatusFra
		def intervjuStatusTil = session.intervjuStatusTil

		if (intervjustatuskoder && intervjustatuskoder == 'intervall') {
			kakeDiagramData = hentRapportForPeriode(skjemaId, periodeNummer, intervjuStatusFra, intervjuStatusTil)
			periodeRapportList = hentRapportForHverPeriode (skjemaId, intervjuStatusFra, intervjuStatusTil)
		} else {
			kakeDiagramData = hentRapportForPeriode(skjemaId, periodeNummer)
			periodeRapportList = hentRapportForHverPeriode (skjemaId)
		}

		def periodeSumRapport = lagSumRapport(periodeRapportList)

		return [
			skjema: Skjema.get(skjemaId),
			periodeList: Skjema.get(skjemaId)?.perioder?.sort { it.periodeNummer },
			antallInnlastet: kakeDiagramData.antallInnlastet,
			antallUbehandlet: kakeDiagramData.antallUbehandlet,
			antallPabegynt: kakeDiagramData.antallPabegynt,
			antallPaaVent: kakeDiagramData.antallPaaVent,
			antallNekterePaaVent: kakeDiagramData.antallNekterePaaVent,
			antallUtsendtCati: kakeDiagramData.antallUtsendtCati,
			antallUtsendtCatiWeb: kakeDiagramData.antallUtsendteCatiWeb,
			antallUtsendtCapi: kakeDiagramData.antallUtsendtCapi,
			antallUtsendtWeb: kakeDiagramData.antallUtsendtWeb,
			antallFerdigIntervju: kakeDiagramData.antallFerdigIntervju,
			antallFerdigFrafall: kakeDiagramData.antallFerdigFrafall,
			antallFerdigAvganger: kakeDiagramData.antallFerdigAvganger,
			totaltAntall: kakeDiagramData.totaltAntall,
			periodeNummer: kakeDiagramData.periodeNummer,
			periodeRapportList: periodeRapportList,
			periodeSumRapport: periodeSumRapport,
			intervjustatuskoder: intervjustatuskoder,
			intervjuStatusFra: intervjuStatusFra,
			intervjuStatusTil: intervjuStatusTil
		]
	}

	/**
	 * Lager en periode data og fyller det med summen av alle verdier for alle perioder.
	 * 
	 * @param periodeDataList
	 * @return
	 */
	private PeriodeData lagSumRapport( List<PeriodeData> periodeDataList ) {
		
		PeriodeData periodeSumData = new PeriodeData()
		
		periodeDataList.each { PeriodeData periodeData ->
			periodeSumData.antallInnlastet += periodeData.antallInnlastet
			periodeSumData.antallUbehandlet += periodeData.antallUbehandlet
			periodeSumData.antallPabegynt += periodeData.antallPabegynt
			periodeSumData.antallPaaVent += periodeData.antallPaaVent
			periodeSumData.antallNekterePaaVent += periodeData.antallNekterePaaVent
			periodeSumData.antallUtsendtCati += periodeData.antallUtsendtCati
			periodeSumData.antallUtsendteCatiWeb += periodeData.antallUtsendteCatiWeb
			periodeSumData.antallUtsendtCapi += periodeData.antallUtsendtCapi
			periodeSumData.antallUtsendtWeb += periodeData.antallUtsendtWeb
			periodeSumData.antallFerdigIntervju += periodeData.antallFerdigIntervju
			periodeSumData.antallFerdigFrafall += periodeData.antallFerdigFrafall
			periodeSumData.antallFerdigAvganger += periodeData.antallFerdigAvganger
			periodeSumData.totaltAntall += periodeData.totaltAntall
		}
		periodeSumData.periodeNummer = "Sum"
		
		return periodeSumData
	}
	
	
	/**
	 * Henter ut en rapport for hver periode i skjemaet.
	 *
	 * @param skjemaId
	 * @return
	 */
	private List<PeriodeData> hentRapportForHverPeriode( Long skjemaId) {

		List rapportForHverPeriode = new ArrayList()

		def skjema = Skjema.get(skjemaId)

		if(skjema) {
			def perioder = skjema.perioder.sort {it.periodeNummer}

			perioder.each { periode ->
				PeriodeData periodeData = hentRapportForPeriode(skjemaId, periode.periodeNummer)
				rapportForHverPeriode.add (periodeData)
			}
		}

		return rapportForHverPeriode
	}

	/**
	 * Henter ut en rapport for hver periode i skjemaet.
	 *
	 * @param skjemaId
	 * @return
	 */
	private List<PeriodeData> hentRapportForHverPeriode( Long skjemaId, String intervjuStatusFra, String intervjuStatusTil) {

		List rapportForHverPeriode = new ArrayList()

		def skjema = Skjema.get(skjemaId)

		if(skjema) {
			def perioder = skjema.perioder.sort {it.periodeNummer}

			perioder.each { periode ->
				PeriodeData periodeData = hentRapportForPeriode(skjemaId, periode.periodeNummer, intervjuStatusFra, intervjuStatusTil)
				rapportForHverPeriode.add (periodeData)
			}
		}

		return rapportForHverPeriode
	}


	/**
	 * Henter ut en rapport for skjema og periode. Hvis periode = null
	 * hentes det ut for alle perioder.
	 *  
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	private PeriodeData hentRapportForPeriode( Long skjemaId, Long periodeNummer ) {

		def periodeData = new PeriodeData(
			antallInnlastet: rapportStatusService.finnAntallInnlastede(skjemaId, periodeNummer),
			antallUbehandlet: rapportStatusService.finnAntallUbehandlede(skjemaId, periodeNummer),
			antallPabegynt: rapportStatusService.finnAntallPaabegynt(skjemaId, periodeNummer),
			antallPaaVent: rapportStatusService.finnAntallPaaVent(skjemaId, periodeNummer),
			antallNekterePaaVent: rapportStatusService.finnAntallNekterePaaVent(skjemaId, periodeNummer),
			antallUtsendtCati: rapportStatusService.finnAntallUtsendteCati(skjemaId, periodeNummer),
			antallUtsendteCatiWeb: rapportStatusService.finnAntallUtsendteCatiWeb(skjemaId, periodeNummer),
			antallUtsendtCapi: rapportStatusService.finnAntallTildelteCapi(skjemaId, periodeNummer),
			antallUtsendtWeb: rapportStatusService.finnAntallUtsendteWeb (skjemaId, periodeNummer),
			antallFerdigIntervju: rapportStatusService.finnAntallFerdige(skjemaId, periodeNummer),
			antallFerdigFrafall: rapportStatusService.finnAntallFerdigeFrafall(skjemaId, periodeNummer),
			antallFerdigAvganger: rapportStatusService.finnAntallAvganger(skjemaId, periodeNummer),
			totaltAntall: rapportStatusService.finnTotaltAntall(skjemaId, periodeNummer),
			periodeNummer: periodeNummer
		)
		return periodeData
	}

	/**
	 * Henter ut en rapport for skjema og periode. Hvis periode = null
	 * hentes det ut for alle perioder.
	 *
	 * @param skjemaId
	 * @param periodeNummer
	 * @return
	 */
	private PeriodeData hentRapportForPeriode( Long skjemaId, Long periodeNummer, String intervjustatusFra, String intervjustatusTil ) {

		def fra = new Integer (intervjustatusFra)
		def til = new Integer (intervjustatusTil)

		def periodeData = new PeriodeData(
				antallInnlastet: rapportStatusService.finnAntallInnlastede(skjemaId, periodeNummer, fra, til),
				antallUbehandlet: rapportStatusService.finnAntallUbehandlede(skjemaId, periodeNummer, fra, til),
				antallPabegynt: rapportStatusService.finnAntallPaabegynt(skjemaId, periodeNummer, fra, til),
				antallPaaVent: rapportStatusService.finnAntallPaaVent(skjemaId, periodeNummer, fra, til),
				antallNekterePaaVent: rapportStatusService.finnAntallNekterePaaVent(skjemaId, periodeNummer, fra, til),
				antallUtsendtCati: rapportStatusService.finnAntallUtsendteCati(skjemaId, periodeNummer, fra, til),
				antallUtsendteCatiWeb: rapportStatusService.finnAntallUtsendteCatiWeb(skjemaId, periodeNummer, fra, til),
				antallUtsendtCapi: rapportStatusService.finnAntallTildelteCapi(skjemaId, periodeNummer, fra, til),
				antallUtsendtWeb: rapportStatusService.finnAntallUtsendteWeb (skjemaId, periodeNummer, fra, til),
				antallFerdigIntervju: rapportStatusService.finnAntallFerdige(skjemaId, periodeNummer, fra, til),
				antallFerdigFrafall: rapportStatusService.finnAntallFerdigeFrafall(skjemaId, periodeNummer, fra, til),
				antallFerdigAvganger: rapportStatusService.finnAntallAvganger(skjemaId, periodeNummer, fra, til),
				totaltAntall: rapportStatusService.finnTotaltAntall(skjemaId, periodeNummer, fra, til),
				periodeNummer: periodeNummer
		)
		return periodeData
	}

}

class PeriodeData {

	int antallInnlastet
	int antallUbehandlet
	int antallPabegynt
	int antallPaaVent
	int antallNekterePaaVent
	int antallUtsendtCati
	int antallUtsendteCatiWeb
	int antallUtsendtCapi
	int antallUtsendtWeb
	int antallFerdigIntervju
	int antallFerdigFrafall
	int antallFerdigAvganger
	int totaltAntall

	String periodeNummer
}

class VelgTidligereIntervjustatusCommand implements Validateable {

	String intervjuStatusFra = 10
	String intervjuStatusTil = 19

	static constraints = {
		intervjuStatusTil(blank: false, matches: "[0-9]+")
		intervjuStatusFra(blank: false, matches: "[0-9]+")
	}
}
