package sivadm

import grails.plugin.springsecurity.annotation.Secured
import siv.rapport.data.SkjemaListeItem

import java.text.SimpleDateFormat;

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class RapportIndikatorController {

	def rapportIndikatorService

	def velgSkjema = {

		def skjemaInstanceList = Skjema.findAll()

		def skjemaListe = []
		SimpleDateFormat sdf = new SimpleDateFormat('dd.MM.yyyy')
		skjemaInstanceList.each {
			skjemaListe.add(
					new SkjemaListeItem(
							skjemaId: it.id,
							skjema: it,
							listeTekst: (it.planlagtSluttDato == null ? '-------------' : sdf.format(it.planlagtSluttDato)) + '  -  ' + it.delProduktNummer + '  -  ' + it.skjemaNavn
					)
			)
		}

		skjemaListe.sort { a, b  ->
				b.skjema.planlagtSluttDato <=> a.skjema.planlagtSluttDato
		}

		return [skjemaListe: skjemaListe ]
	}

	def visRapport = {

		def skjemaIdList = []

		params.list('skjemaListe').each {
			skjemaIdList.add(new Long(it))
		}

		def skjemaIndikatorList = rapportIndikatorService.hentSkjemaIndikatorer(skjemaIdList)
		
		return [ skjemaIndikatorList: skjemaIndikatorList ]
	}
	
	def visUtregning = {
		
	}
}

