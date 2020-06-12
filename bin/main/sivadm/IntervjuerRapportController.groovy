package sivadm

import siv.data.ProduktKode
import siv.type.ArbeidsType

class IntervjuerRapportController {

	def brukerService
	def timeforingService
	def intervjuerService

    def listIntervjuerRapporter = {
		
	}

	def nullstillSok = {
		List<ProduktKode> produktNummerListe = timeforingService.hentProduktKode(null, true)
		def arbTypeListe = ArbeidsType.values()
		render(view: "arbeidstidRapport", model: [arbTypeListe: arbTypeListe,	produktNummerListe: produktNummerListe])
	}

	def arbeidstidRapport = {
		ArbeidstidRapportCommand arbeidstidRapportCommand = new ArbeidstidRapportCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if(!intervjuer) {
			return
		}
		
		List<ProduktKode> produktNummerListe = timeforingService.hentProduktKode(null, true)
		def arbTypeListe = ArbeidsType.values()
			
		if(!request.post) {
			render(view: "arbeidstidRapport", model: [arbTypeListe: arbTypeListe,	produktNummerListe: produktNummerListe])
			return
		}
						
		//public List intervjuerRapportArbeidstid(Intervjuer intervjuer, Date fra, Date til, String produktNummer, String arbeidsType, String gruppertPaa) {
		def arbeidDatoListe = intervjuerService.intervjuerRapportArbeidstid(intervjuer, arbeidstidRapportCommand.fra, arbeidstidRapportCommand.til, arbeidstidRapportCommand.produktNummer, arbeidstidRapportCommand.arbeidstype, "dato")
		def arbeidProsjektListe = intervjuerService.intervjuerRapportArbeidstid(intervjuer, arbeidstidRapportCommand.fra, arbeidstidRapportCommand.til, arbeidstidRapportCommand.produktNummer, arbeidstidRapportCommand.arbeidstype, "prosjekt")
		def arbeidTypeListe = intervjuerService.intervjuerRapportArbeidstid(intervjuer, arbeidstidRapportCommand.fra, arbeidstidRapportCommand.til, arbeidstidRapportCommand.produktNummer, arbeidstidRapportCommand.arbeidstype, "arbeidstype")
		
		[
			arbTypeListe: arbTypeListe,
			produktNummerListe: produktNummerListe,
			arbeidstidRapportCommand: arbeidstidRapportCommand,
			arbeidDatoListe: arbeidDatoListe,
			arbeidProsjektListe: arbeidProsjektListe,
			arbeidTypeListe: arbeidTypeListe
		]
	}

	def listSkjema = {
		params.max = Math.min(params.max ? params.int('max') : 15, 100)

		// default sort = oppstartsdato
		if(!params.sort) {
			params.sort = "oppstartDataInnsamling"
			params.order = "desc"
		}

		Integer max = params.max
		Integer first = params.offset? Integer.parseInt(params.offset) : 0


		def skjemaInstanceList = searchCriteria (first, max, false, params.sort, params.order)
		def skjemaInstanceTotal = searchCriteria (false, false, true, null, null).get(0)

		[skjemaInstanceList: skjemaInstanceList, skjemaInstanceTotal: skjemaInstanceTotal]
	}

	def searchCriteria = { first, max, countAll, sortParam, orderParam ->

		Date now = new Date()

		def c = Skjema.createCriteria()
		def skjemaInstanceList = c {

			if(first )	{
				firstResult(first)
			}

			if(max) {
				maxResults(max)
			}

			if( countAll ) {
				projections { countDistinct('id') }
			} else {
				order( sortParam, orderParam )
			}

			eq('aktivertForIntervjuing', true)

			or {
				isNull('planlagtSluttDato')
				gt('planlagtSluttDato', now)
			}

			or {
				isNull('sluttDato')
				gt('sluttDato', now)
			}
		}

		skjemaInstanceList
	}
}

class ArbeidstidRapportCommand implements grails.validation.Validateable {
	Date fra
	Date til
	String produktNummer
	String arbeidstype
	
	static constraints = {
		fra nullable: true
		til nullable: true
		produktNummer nullable: true
		arbeidstype nullable: true
	}
}
