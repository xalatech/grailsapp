package sivadm

import siv.type.Kilde

import java.util.List;
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class RapportFrafallController {

	def rapportFrafallService

	def visRapport = {

		Long skjemaId = Long.parseLong (params.id)
		Long periodeNummer = null

		if( params.periode ) {
			periodeNummer = Long.parseLong (params.periode)
		}

		def kakeDiagramData = hentRapportForPeriode(skjemaId, periodeNummer)

		def periodeRapportList = hentRapportForHverPeriode (skjemaId)
		
		def periodeSumRapport = lagSumRapport(periodeRapportList)

        def periodeRapportMedKildeList =  hentRapportMedKildeForHverPeriode(skjemaId)

        def periodeSumRapportMedKilde = lagSumRapportMedKilde(periodeRapportMedKildeList)

        return [
			skjema: Skjema.get(skjemaId),
			periodeList: Skjema.get(skjemaId)?.perioder.sort { it.periodeNummer },
			antallNektere: kakeDiagramData.antallNektere,
			antallForhindret: kakeDiagramData.antallForhindret,
			antallIkkeTruffet: kakeDiagramData.antallIkkeTruffet,
			antallAndreAarsaker: kakeDiagramData.antallAndreAarsaker,
			antallIntervju: kakeDiagramData.antallIntervju,
			antallAvganger: kakeDiagramData.antallAvganger,
			antallOverganger: kakeDiagramData.antallOverganger,
			totaltAntall: kakeDiagramData.totaltAntall,
			bruttoAntall: kakeDiagramData.bruttoAntall,
			periodeNummer: kakeDiagramData.periodeNummer,
			periodeRapportList: periodeRapportList,
			periodeSumRapport: periodeSumRapport,
            periodeRapportMedKildeList: periodeRapportMedKildeList,
            periodeSumRapportMedKilde: periodeSumRapportMedKilde
		]
	}

    /**
     * Lager en periode frafall data og fyller det med summen av alle verdier for alle perioder.
     *
     * @param periodeFrafallMedKildeDataList
     * @return
     */
    private PeriodeFrafallMedKildeData lagSumRapportMedKilde( List<PeriodeFrafallMedKildeData> periodeFrafallMedKildeDataList ) {

        PeriodeFrafallMedKildeData periodeFrafallSumData = new PeriodeFrafallMedKildeData()

        periodeFrafallMedKildeDataList.each { PeriodeFrafallMedKildeData periodeFrafallMedKildeData ->

            periodeFrafallSumData.antallIntervju += periodeFrafallMedKildeData.antallIntervju
            periodeFrafallSumData.antallCAPI += periodeFrafallMedKildeData.antallCAPI
            periodeFrafallSumData.antallCATI += periodeFrafallMedKildeData.antallCATI
            periodeFrafallSumData.antallWEB += periodeFrafallMedKildeData.antallWEB
        }

        periodeFrafallSumData.periodeNummer = "Sum"

        return periodeFrafallSumData
    }

    /**
	 * Lager en periode frafall data og fyller det med summen av alle verdier for alle perioder.
	 * 
	 * @param periodeFrafallDataList
	 * @return
	 */
	private PeriodeFrafallData lagSumRapport( List<PeriodeFrafallData> periodeFrafallDataList ) {
		
		PeriodeFrafallData periodeFrafallSumData = new PeriodeFrafallData()
		
		periodeFrafallDataList.each { PeriodeFrafallData periodeFrafallData ->
			
			periodeFrafallSumData.antallNektere += periodeFrafallData.antallNektere
			periodeFrafallSumData.antallForhindret += periodeFrafallData.antallForhindret
			periodeFrafallSumData.antallIkkeTruffet += periodeFrafallData.antallIkkeTruffet
			periodeFrafallSumData.antallAndreAarsaker += periodeFrafallData.antallAndreAarsaker
			periodeFrafallSumData.antallIntervju += periodeFrafallData.antallIntervju
			periodeFrafallSumData.antallAvganger += periodeFrafallData.antallAvganger
			periodeFrafallSumData.antallOverganger += periodeFrafallData.antallOverganger
			periodeFrafallSumData.totaltAntall += periodeFrafallData.totaltAntall
			periodeFrafallSumData.bruttoAntall += periodeFrafallData.bruttoAntall
		}
		
		periodeFrafallSumData.periodeNummer = "Sum"
		
		return periodeFrafallSumData
	}

    /**
     * Henter ut en rapport for hver periode i skjemaet (IO med kilde).
     *
     * @param skjemaId
     * @return
     */
    private List<PeriodeFrafallMedKildeData> hentRapportMedKildeForHverPeriode( Long skjemaId) {

        List rapportForHverPeriode = new ArrayList()

        def skjema = Skjema.get(skjemaId)

        if(skjema) {
            def perioder = skjema.perioder.sort {it.periodeNummer}

            perioder.each { periode ->
                PeriodeFrafallMedKildeData periodeFrafallData = hentRapporMedKildetForPeriode(skjemaId, periode.periodeNummer)
                rapportForHverPeriode.add (periodeFrafallData)
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
	private List<PeriodeFrafallData> hentRapportForHverPeriode( Long skjemaId) {

		List rapportForHverPeriode = new ArrayList()

		def skjema = Skjema.get(skjemaId)

		if(skjema) {
			def perioder = skjema.perioder.sort {it.periodeNummer}

			perioder.each { periode ->
				PeriodeFrafallData periodeFrafallData = hentRapportForPeriode(skjemaId, periode.periodeNummer)
				rapportForHverPeriode.add (periodeFrafallData)
			}
		}

		return rapportForHverPeriode
	}


    /**
     * Henter ut en rapport for skjema og periode. Hvis periode = null
     * hentes det ut for alle perioder. IO med kilde.
     *
     * @param skjemaId
     * @param periodeNummer
     * @return
     */
    private PeriodeFrafallMedKildeData hentRapporMedKildetForPeriode( Long skjemaId, Long periodeNummer ) {

        def skjema = Skjema.get(skjemaId)

        def periodeData = new PeriodeFrafallMedKildeData()

        periodeData.antallIntervju = rapportFrafallService.finnAntallIntervju(skjemaId, periodeNummer)
        periodeData.antallCAPI = rapportFrafallService.finnAntallMedKilde(skjemaId, periodeNummer, Kilde.CAPI)
        periodeData.antallCATI = rapportFrafallService.finnAntallMedKilde(skjemaId, periodeNummer, Kilde.CATI)
        periodeData.antallWEB = rapportFrafallService.finnAntallMedKilde(skjemaId, periodeNummer, Kilde.WEB)

        periodeData.periodeNummer = periodeNummer

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
	private PeriodeFrafallData hentRapportForPeriode( Long skjemaId, Long periodeNummer ) {

		def skjema = Skjema.get(skjemaId)

		def periodeList = skjema.perioder.sort{ it.periodeNummer }

		def periodeData = new PeriodeFrafallData()

		periodeData.antallNektere = rapportFrafallService.finnAntallNektere(skjemaId, periodeNummer)

		periodeData.antallForhindret = rapportFrafallService.finnAntallForhindret(skjemaId, periodeNummer)

		periodeData.antallIkkeTruffet = rapportFrafallService.finnAntallIkkeTruffet(skjemaId, periodeNummer)

		periodeData.antallAndreAarsaker = rapportFrafallService.finnAntallAndreAarsaker(skjemaId, periodeNummer)

		periodeData.antallIntervju = rapportFrafallService.finnAntallIntervju(skjemaId, periodeNummer)

		periodeData.antallAvganger = rapportFrafallService.finnAntallAvganger(skjemaId, periodeNummer)

		periodeData.antallOverganger = rapportFrafallService.finnAntallOverføringer(skjemaId, periodeNummer)

		periodeData.totaltAntall = periodeData.antallNektere +
				periodeData.antallForhindret + periodeData.antallIkkeTruffet +
				periodeData.antallAndreAarsaker + periodeData.antallIntervju +
				periodeData.antallAvganger + periodeData.antallOverganger

		periodeData.periodeNummer = periodeNummer

		periodeData.bruttoAntall = rapportFrafallService.finnBruttoAntall(skjemaId, periodeNummer)

		return periodeData
	}
}

class PeriodeFrafallData {
	int bruttoAntall
	int antallNektere
	int antallForhindret
	int antallIkkeTruffet
	int antallAndreAarsaker
	int antallIntervju
	int antallAvganger
	int antallOverganger
	int totaltAntall
	String periodeNummer
}

class PeriodeFrafallMedKildeData {
    int antallCAPI
    int antallCATI
    int antallWEB
    int antallIntervju
    String periodeNummer
}