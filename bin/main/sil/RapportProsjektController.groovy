package sil


import java.text.SimpleDateFormat

import sivadm.Prosjekt
import util.TimeUtil;
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_SIL', 'ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class RapportProsjektController {

	def rapportProsjektService

	def velgProsjekt = { command ->

		def periodeStart = command.periodeStart
		def periodeSlutt = command.periodeSlutt

		if ( params.settDato && !periodeStart) periodeStart = TimeUtil.getOneWeekAgo();

		if (periodeStart) periodeStart = TimeUtil.getStartOfDay(periodeStart)
		if (periodeSlutt) periodeSlutt = TimeUtil.getEndOfDay(periodeSlutt)

		SimpleDateFormat sdf = new SimpleDateFormat('yyyy')

		def prosjektInstanceList = Prosjekt.list().sort { a,b ->
			sdf.format(b.avslutningsDato) <=> sdf.format(a.avslutningsDato) ?: a.prosjektNavn.toLowerCase() <=> b.prosjektNavn.toLowerCase()
		}

		def prosjektListe = []
		prosjektInstanceList.each {
			prosjektListe.add(
					new ProsjektListeItem(
							prosjektId: it.id,
							listeTekst: sdf.format(it.avslutningsDato) + ' - ' + it.prosjektNavn
					)
			)
		}

		return [
				prosjektListe: prosjektListe,
				periodeStart: periodeStart,
				periodeSlutt: periodeSlutt
		]
	}


    def visRapport = { command ->

		def prosjektListe = []
		params.list('prosjektListe').each {
			prosjektListe.add(Prosjekt.findById(new Long(it)))
		}

		def periodeStart = command.periodeStart
		def periodeSlutt = command.periodeSlutt

		if ( params.settDato && !periodeStart) periodeStart = TimeUtil.getOneWeekAgo();
		
		if (periodeStart) periodeStart = TimeUtil.getStartOfDay(periodeStart)
		if (periodeSlutt) periodeSlutt = TimeUtil.getEndOfDay(periodeSlutt)

		List prosjektRapportDataList = []
		
		if( prosjektListe.size()>0 ) {
			prosjektRapportDataList = rapportProsjektService.hentProsjektRapportForProsjektListe( prosjektListe, periodeStart, periodeSlutt )
		}

		return [
			prosjektRapportDataList: prosjektRapportDataList,
		]
	}
}

class ProsjektRapportCommand {
	Date periodeStart
	Date periodeSlutt
}

class ProsjektListeItem {
	Long prosjektId
	String listeTekst
}