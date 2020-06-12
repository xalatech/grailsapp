package sivadm

import siv.rapport.data.SkjemaIndikator;
import siv.service.data.KontaktInformasjonServiceData;


class RapportIndikatorService {

	def rapportFrafallService
	def rapportStatusService

	static transactional = true

	/**
	 * Henter en liste med indikatorer for alle aktive skjema
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjemaIndikator> hentSkjemaIndikatorer(List skjemaIdList) {

		def skjemaIndikatorList = new ArrayList()
		def skjemaList = Skjema.findAllByIdInList(skjemaIdList)

		skjemaList.each { Skjema skjema ->

			int bruttoUtvalg = rapportStatusService.finnTotaltAntall(skjema.id, null) - rapportStatusService.finnAntallAvganger(skjema.id, null)
			int antallIntervju = rapportFrafallService.finnAntallIntervju(skjema.id, null)
			int antallFrafall = rapportFrafallService.finnAntallFrafall(skjema.id, null)
			int antallNektere = rapportFrafallService.finnAntallNektere(skjema.id, null)
			int antallForhindret = rapportFrafallService.finnAntallForhindret(skjema.id, null)

			double bruttoSvar
			double nettoSvar
			double kontaktRate
			double rekrutteringsRate
			double andelFerdigstilt

			// bruttosvar
			if( bruttoUtvalg > 0  ) {
				bruttoSvar = (antallIntervju * 100) / bruttoUtvalg
			}
			else {
				bruttoSvar = 0
			}

			int intervjuOgFrafall = antallIntervju + antallFrafall

			// nettosvar
			if( intervjuOgFrafall > 0 ) {
				nettoSvar = (antallIntervju * 100) / intervjuOgFrafall
			}
			else {
				nettoSvar = 0
			}

			// kontaktRate
			if( bruttoUtvalg > 0 ) {
				int intervjuNektereOgForhindret = antallIntervju + antallNektere + antallForhindret
				kontaktRate = (intervjuNektereOgForhindret * 100) / bruttoUtvalg
			}
			else {
				kontaktRate = 0
			}
			
			// rekrutteringsRate
			int intervjuOgNektereOgForhindret = antallIntervju + antallNektere + antallForhindret
			
			if( intervjuOgNektereOgForhindret > 0 ) {
				rekrutteringsRate = (antallIntervju * 100) / intervjuOgNektereOgForhindret
			}
			else {
				rekrutteringsRate = 0
			}
			
			// ferdigStilt
			if( bruttoUtvalg > 0 ) {
				andelFerdigstilt = ( intervjuOgFrafall * 100) / bruttoUtvalg
			}
			else {
				andelFerdigstilt = 0
			}

			SkjemaIndikator skjemaIndikator = new SkjemaIndikator()

			skjemaIndikator.skjemaNavn = skjema.skjemaNavn
			skjemaIndikator.bruttoSvar = bruttoSvar
			skjemaIndikator.nettoSvar = nettoSvar
			skjemaIndikator.kontaktRate = kontaktRate
			skjemaIndikator.rekrutteringsRate = rekrutteringsRate
			skjemaIndikator.andelFerdigstilt = andelFerdigstilt

			skjemaIndikatorList.add( skjemaIndikator )
		}

		return skjemaIndikatorList.sort { it.skjemaNavn}
	}
}
