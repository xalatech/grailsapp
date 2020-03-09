package sivadm

import grails.converters.JSON
import grails.core.GrailsApplication
import siv.type.Kilde
import siv.type.SkjemaStatus
import util.BasicAuthUtil

import java.text.ParseException
import java.text.SimpleDateFormat

class IntervjuObjektRestController {
	
	IntervjuObjektService intervjuObjektService
	SynkroniseringService synkroniseringService
	GrailsApplication grailsApplication
	
	static allowedMethods = [hentKontaktperiode: "GET", hentKontaktperioder: "POST", oppdaterFullforingsGrad: "PUT", oppdaterSkjemaStatus: "PUT"]
	
	def hentKontaktperioder() {
		sjekkAutentisering()
		def jsonBody = request.JSON
		def intervjuObjekter = IntervjuObjekt.getAll(jsonBody)
		render lagSuksessResponsKontaktperioder(intervjuObjekter) as JSON
	}
	
	def hentKontaktperiode() {
		sjekkAutentisering()
		def ioId = params.ioId
		IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(ioId)
		if(!intervjuObjekt) {
			response.status = 404
			render lagFeilRespons('Fant ikke IntervjuObjekt') as JSON
			return
		}
		render lagSuksessResponsKontaktperiode(intervjuObjekt) as JSON
	}
	
	def oppdaterFullforingsGrad() {
		sjekkAutentisering()
		def jsonBody = request.JSON
		def ioId = params.ioId
		def fullforingsGrad = jsonBody["fullforingsGrad"]
		IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(ioId)
		if(!intervjuObjekt) {
			response.status = 404
			render lagFeilRespons('Fant ikke IntervjuObjekt') as JSON
			return
		}
		if(fullforingsGrad == null) {
			response.status = 400
			render lagFeilRespons("Feltet fullforingsGrad er påkrevd i json body") as JSON
			return
		}
		if(!(fullforingsGrad instanceof Long) && !(fullforingsGrad instanceof Integer)) {
			response.status = 400
			render lagFeilRespons("Feltet fullforingsGrad må være et nummer") as JSON
			return
		}
		def saved = false
		if(intervjuObjekt.fullforingsGrad != fullforingsGrad) {
			intervjuObjekt.fullforingsGrad = fullforingsGrad
			saved = intervjuObjekt.save(flush: true)
		} else {
			saved = true
		}
		if(saved) {
			render lagSuksessResponsFullforingsGrad(intervjuObjekt) as JSON
			return
		} else {
			response.status = 500
			render lagFeilRespons("Feil oppsto, klarte ikke å lagre fullføringsgrad") as JSON
			return
		}
		
	}
	
	def oppdaterSkjemaStatus() {
		sjekkAutentisering()
		def jsonBody = request.JSON
		def ioId = params.ioId
		def skjemaStatus = jsonBody["skjemaStatus"]
		def intervjuStatus = jsonBody["intervjuStatus"]
		def redigertAv = jsonBody["redigertAv"]
		def paVentDato = jsonBody["paVentDato"]
		def instrumentId = jsonBody["instrumentId"]
		IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(ioId)
		if(!intervjuObjekt) {
			response.status = 404
			render lagFeilRespons('Fant ikke IntervjuObjekt') as JSON
			return
		}
		if(intervjuObjekt.periode.skjema.instrumentId != instrumentId) {
			response.status = 400
			render lagFeilRespons("instrumentId må matche skjema tilknyttet intervjuobjekt") as JSON
			return
		}
		validerBodyParametere(redigertAv, skjemaStatus, paVentDato)
		if(paVentDato != null) {
			try {
				def dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
				dateFormatter.setLenient(false)
				paVentDato = dateFormatter.parse(paVentDato)
			} catch(ParseException pe) {
				response.status = 400
				render lagFeilRespons("Feil formatering av paVentDato, skal være dd.MM.yyyy") as JSON
				return
			}
		}
		def statusFeilmelding
		def skjemaStatusObjekt
		for(SkjemaStatus status : SkjemaStatus.values()) {
			if(status.name().equals(skjemaStatus)) {
				skjemaStatusObjekt = status
			}
		}
		
		if(skjemaStatusObjekt == SkjemaStatus.Ferdig) {
			boolean hasBeenStarted = false;
			if(intervjuObjekt.statusHistorikk) {
				intervjuObjekt.statusHistorikk.each {
					if(it.skjemaStatus == SkjemaStatus.Pabegynt) {
						hasBeenStarted = true
						return
					}
				}

			}
			if(!hasBeenStarted) {
				response.status = 409
				render lagFeilRespons("Skjemastatus Pabegynt må settes før Ferdig status") as JSON
				return
			}
		}
		
		if(intervjuObjekt.statusHistorikk) {
			boolean isDuplicate = false
			intervjuObjekt.statusHistorikk.each {
				if(it.skjemaStatus == skjemaStatusObjekt && it.intervjuStatus.equals(intervjuStatus)) {
					response.status = 400
					isDuplicate = true
					render lagFeilRespons("Skjemastatus ${skjemaStatusObjekt} med intervjustatus ${intervjuStatus} fantes i historikk fra før") as JSON
					return
				}
			}
			if(isDuplicate) {
				return
			}
		}

		def historikk = intervjuObjektService.sjekkStatusEndring(intervjuObjekt, skjemaStatusObjekt, intervjuStatus, redigertAv)
		if(historikk == null) {
			statusFeilmelding = "Skjemastatus ${skjemaStatusObjekt} med intervjustatus ${intervjuStatus} var satt fra før av"
		} else {
			statusFeilmelding = validerIntervjuOgSkjemaStatus(skjemaStatusObjekt, intervjuStatus, paVentDato)
		}
		if(!statusFeilmelding) {
			intervjuObjekt.intervjuStatus = intervjuStatus
			intervjuObjekt.katSkjemaStatus = skjemaStatusObjekt
			intervjuObjekt.kilde = Kilde.WEB
			intervjuObjekt.addToStatusHistorikk(historikk)
			if(paVentDato != null) {
				intervjuObjekt.paVentDato = paVentDato
			}
			try {
				if(IntervjuObjekt.get(ioId).version == intervjuObjekt.version) {
					if(intervjuObjekt.save(flush: true)) {
						synkroniseringService.synkroniserIntervjuObjektEndring(intervjuObjekt.id, false)
						render lagSuksessResponsSkjemaStatus(intervjuObjekt) as JSON
						return
					} else {
						response.status = 500
						render lagFeilRespons("Feil oppsto, klarte ikke å lagre skjemastatus") as JSON
						return
					}
				} else {
					response.status = 500
					render lagFeilRespons("Objektet blir operert på av en annen, vennligst prøv igjen senere") as JSON
					return
				}
			} catch(Throwable sose) {
				response.status = 500
				render lagFeilRespons("Objektet blir operert på av en annen, vennligst prøv igjen senere. T") as JSON
				return
			}
		} else {
			response.status = 400
			render lagFeilRespons(statusFeilmelding) as JSON
			return
		}
	}
	
	private String validerIntervjuOgSkjemaStatus(SkjemaStatus skjemaStatus,Integer intervjuStatus, Date paVent) {
		// Valideringsregler finnes i dokumentet Q:\DOK\Siv\2011\integrasjon\SivAdm io status.xls
		def msg
		
		if( (skjemaStatus == SkjemaStatus.Innlastet) && intervjuStatus) {
			msg = "${message(code: 'intervjuObjekt.skjemastatus.innlastet.feil', default: 'Intervjustatus må være blank når skjemastatus er innlastet')}"
		}
		else if( (skjemaStatus == SkjemaStatus.Ferdig) && intervjuStatus == null ) {
			msg = "${message(code: 'intervjuObjekt.skjemastatus.ferdig.feil', default: 'Intervjustatus må settes når skjemastatus settes til ferdig')}"
		}
		else if( (skjemaStatus == SkjemaStatus.Paa_vent) && !paVent) {
			msg = "${message(code: 'intervjuObjekt.paVentDato.ikke.satt', default: 'Kan ikke sette skjemastatus til På vent uten å sette På vent til dato.')}"
		}
				
		return msg
	}
	
	private Map lagSuksessResponsKontaktperioder(def intervjuObjekter) {
		def idKontaktListe = []
		intervjuObjekter.each {
			if(it != null && it.kontaktperiode != null) {
				idKontaktListe.add([
						'id': it.id,
						'kontaktperiode': it.kontaktperiode
					])
			}
		}
		Map responsMap = [
				'status': 'suksess',
				'data': idKontaktListe
			]
		return responsMap
	}
	
	private Map lagSuksessResponsKontaktperiode(IntervjuObjekt intervjuObjekt) {
		Map responsMap = [
				'status': 'suksess',
				'data': [
					'id': intervjuObjekt.id,
					'kontaktperiode': intervjuObjekt.kontaktperiode
				]
			]
		return responsMap
	}
	
	private Map lagSuksessResponsFullforingsGrad(IntervjuObjekt intervjuObjekt) {
		Map responsMap = [
				'status': 'suksess',
				'data': [
					'id': intervjuObjekt.id,
					'fullforingsGrad': intervjuObjekt.fullforingsGrad
				]
			]
		return responsMap
	}
	
	private Map lagSuksessResponsSkjemaStatus(IntervjuObjekt intervjuObjekt) {
		Map responsMap = [
				'status': 'suksess',
				'data': [
					'id': intervjuObjekt.id,
					'skjemaStatus': intervjuObjekt.katSkjemaStatus.name()
				]
			]
		if(intervjuObjekt.intervjuStatus) {
			responsMap["data"] << ['intervjuStatus': intervjuObjekt.intervjuStatus]
		}
		return responsMap
	}
	
	private Map lagFeilRespons(String feilMelding) {
		return [
			'status': "feil",
			'data': [
				'melding': feilMelding
			]
		]
	}
	
	private void sjekkAutentisering() {
		def brukerNavn = grailsApplication.config.siv.basicauth.brukernavn
		def passord = grailsApplication.config.siv.basicauth.passord
		if(!BasicAuthUtil.erAutentisert(request, brukerNavn, passord)) {
				response.status = 401
				def responsMap = ['status': 'ikke autentisert']
				render responsMap as JSON
		}
	}
	
	private void validerBodyParametere(redigertAv, skjemaStatus, paVentDato) {
		if(!skjemaStatus) {
			response.status = 400
			render lagFeilRespons("Feltet skjemaStatus er påkrevd i json body") as JSON
		}
		if(!redigertAv) {
			response.status = 400
			render lagFeilRespons("Feltet redigertAv er påkrevd i json body") as JSON
		}
	}
}