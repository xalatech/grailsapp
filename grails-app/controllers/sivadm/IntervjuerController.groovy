package sivadm

import java.text.SimpleDateFormat;
import java.util.Date;
import grails.converters.*
import siv.type.IntervjuerArbeidsType;
import siv.type.IntervjuerStatus;
import sil.SivAdmService


import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT'])
class IntervjuerController {

	def sivAdmService
	def springSecurityService
	def brukerService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index = {
		redirect(action: "list", params: params)
	}

	def nullstillSok = {
		redirect(action: "list")
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 300, 500)
		
		def intervjuerInstanceList
		def intervjuerInstanceTotal
		def intervjuerStatusList
		def initialer
		def status
		def klynge
		def arbeidsType
		def lokalSentral = 1

		if (request.post) {
			if(params.initialer) {
				initialer = params.initialer
			}

			if(params.intervjuerStatus) {
				status = IntervjuerStatus.valueOf (params.intervjuerStatus)
			}
			else {
				status = IntervjuerStatus.AKTIV
			}

			if(params.klynge) {
				klynge = Klynge.get(params.klynge)
			}

			if(params.arbeidsType) {
				arbeidsType = IntervjuerArbeidsType.valueOf(params.arbeidsType)
			}

			if(params.lokalSentral) {
				lokalSentral = params.lokalSentral
			}

			def c = Intervjuer.createCriteria()

			def intervjuerList = c {
				if(initialer) {
					or {
						ilike("initialer", initialer)
						ilike("navn", "%" + initialer + "%")
					}
				}

				if(status) {
					eq( "status", status)
				}

				if(klynge) {
					eq( "klynge", klynge)
				}

				if(arbeidsType) {
					or {
						eq("arbeidsType", arbeidsType)
						eq("arbeidsType", IntervjuerArbeidsType.BEGGE)
					}
				}

				if(lokalSentral == '2') {
					//Vis bare lokale intervjuere
					eq("lokal",true)
				}

				if(lokalSentral == '3') {
					//Vis bare sentrale intervjuere
					eq("lokal",false)
				}

				order("navn", "asc")
			}

			intervjuerInstanceList = intervjuerList
		}
		else {
			intervjuerInstanceList = Intervjuer.createCriteria().list {
				'eq'("status", IntervjuerStatus.AKTIV)
				if(params.sort) {
					order(params.sort, params.order)
				}
				else {
					order("navn", "asc")
				}
			}
		}

		intervjuerInstanceTotal = intervjuerInstanceList.size()

		[
			intervjuerInstanceList: intervjuerInstanceList,
			intervjuerInstanceTotal: intervjuerInstanceTotal,
			intervjuerStatusList: IntervjuerStatus.values(),
			initialer: initialer,
			status: status,
			klynge: klynge,
			arbeidsType: arbeidsType,
			lokalSentral: lokalSentral
		]
	}

	def create = {
		def intervjuerInstance = new Intervjuer()
		intervjuerInstance.properties = params
		return [intervjuerInstance: intervjuerInstance]
	}

	def save = {
		def intervjuerInstance = new Intervjuer(params)
		if (intervjuerInstance.save(flush: true)) {
			def msg = "Intervjuer med id " + intervjuerInstance.id + " opprettet, brukernavn og passord er " + intervjuerInstance.initialer
			//OPPRETT BRUKER FOR INTERVJUER
			Rolle rolleIntervjuer = Rolle.findByAuthority("ROLE_INTERVJUER")

			if(rolleIntervjuer) {
				Bruker bruker = new Bruker(navn: intervjuerInstance.navn, username: intervjuerInstance.initialer, password: springSecurityService.encodePassword(intervjuerInstance.initialer), enabled: true )
				if(bruker.save(flush: true)) {
					BrukerRolle brukerRolle = new BrukerRolle(bruker: bruker, rolle: rolleIntervjuer)
					if(!brukerRolle.save(flush: true)) {
						msg = "Intervjuer med id " + intervjuerInstance.id + " opprettet. oppretting av bruker feilet"
					}
				}
				else {
					msg = "Intervjuer med id " + intervjuerInstance.id + " opprettet. oppretting av bruker feilet"
				}
			}
			else {
				msg = "Intervjuer med id " + intervjuerInstance.id + " opprettet. oppretting av bruker feilet"
			}

			flash.message = msg
			redirect(action: "list")
		}
		else {
			render(view: "create", model: [intervjuerInstance: intervjuerInstance])
		}
	}

	def edit = {
		params.max = Math.min(params.max ? params.int('max') : 15, 100)
		params.sort = "fraTidspunkt"
		params.order = "desc"
		if (params.id) {
			session.intervjuerId = params.id
		}
		if (params.klyngeId) {
			session.klyngeId = params.klyngeId
		}
		if (params.fraIntervjuerListe) {
			session.klyngeId  = ''
		}
		def intervjuerInstance = Intervjuer.get(session.intervjuerId)
		def fravaerListe = Fravaer.findAllByIntervjuer(intervjuerInstance, params)
		def klyngeId = session.klyngeId
		if (!intervjuerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
			if(klyngeId) {
				// Kommer fra klynge skjema til edit derfor retur dit
				redirect(controller: "klynge", action: "edit", id: klyngeId)
			}
			else {
				redirect(action: "list")
			}
		}
		else {
			return [intervjuerInstance: intervjuerInstance, klyngeId: klyngeId, fravaerListe: fravaerListe]
		}
	}

	def update = {
		def intervjuerInstance = Intervjuer.get(params.id)
		def klyngeId = params["klyngeId"]
		if (intervjuerInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (intervjuerInstance.version > version) {

					intervjuerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'intervjuer.label', default: 'Intervjuer')]
					as Object[], "En annen bruker har oppdatert denne intervjueren i mellomtiden")
					render(view: "edit", model: [intervjuerInstance: intervjuerInstance])
					return
				}
			}
			intervjuerInstance.properties = params
			
			if(intervjuerInstance.validate()) {
				brukerService.oppdaterBrukerHvisIntervjuerInitialerErEndret(intervjuerInstance)
			}
			
			if(!intervjuerInstance.hasErrors() && intervjuerInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), intervjuerInstance.navn])}"

				if(intervjuerInstance.status == IntervjuerStatus.SLUTTET) {
					//Finn brukeren og sett denne til inaktiv
					def bruker = Bruker.findByUsername(intervjuerInstance.initialer)
					if(bruker && bruker.enabled) {
						bruker.enabled = false
						bruker.save()
					}
				}

				if(klyngeId) {
					// Kommer fra klynge skjema til edit derfor retur dit
					redirect(controller: "klynge", action: "edit", id: klyngeId)
				}
				else {
					redirect(action: "list")
				}
			}
			else {
				render(view: "edit", model: [intervjuerInstance: intervjuerInstance, klyngeId: klyngeId])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
			if(klyngeId) {
				// Kommer fra klynge skjema til edit derfor retur dit
				redirect(controller: "klynge", action: "edit", id: klyngeId)
			}
			else {
				redirect(action: "list")
			}
		}
	}

	def delete = {
		def intervjuerInstance = Intervjuer.get(params.id)
		if (intervjuerInstance) {
			try {
				intervjuerInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
				redirect(action: "edit", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
			redirect(action: "list")
		}
	}

	def gotoIntervjuer = {

		String initialer = params.initialer

		IntervjuerStatus status = IntervjuerStatus.valueOf (params.intervjuerStatus)

		def c = Intervjuer.createCriteria()

		def intervjuerList = c {
			if( initialer ) {
				or {
					iLike("initialer", initialer)
					iLike("navn", initialer)
				}
			}
		}

		Intervjuer intervjuer = Intervjuer.findByInitialerAndStatus( initialer, status ) ;

		if( ! intervjuer ) {
			intervjuer = Intervjuer.findByNavn(initialer)
		}

		if( intervjuer ) {
			redirect(action:"show", id:intervjuer.id)
		}
		else {
			redirect(action:"list")
		}
	}

	def apneDatoForTimeforing = {
		def intervjuerInstance = Intervjuer.get(params.id)

		if(!intervjuerInstance) {
			flash.message = "${message(code: 'xx', default: 'Fant ikke intervjuer')}"
			redirect(action:"list")
		}
		else {
			return [intervjuerInstance: intervjuerInstance]
		}
	}

	def aapneDatoForIntervjuer = {
		VelgApneDatoCommand command = new VelgApneDatoCommand()
		def intervjuerInstance = Intervjuer.get(params.id)
		Date d = command.dato
		int s = sivAdmService.apneDagForTimeforing(d, intervjuerInstance)
		if(s != -1) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
			flash.message = "${message(code: 'sivadm.intervjuer.apnet.for.dato', args: [sdf.format(d), intervjuerInstance, s])}"
		}
		else {
			flash.message = "${message(code: 'sivadm.intervjuer.apnet.for.dato.feilet', default: 'Kunne ikke åpne dato for intervjuer')}"
		}

		redirect(action:"list")
	}

	def ajaxGetIntervjuerByInitialer = {
		def intervjuer = Intervjuer.findByInitialer(params.initialer)
				
		if(intervjuer) {
			render intervjuer as JSON
		}
	}

	def ajaxGetIntervjuer = {
		def intervjuer = Intervjuer.get(params.id)
		if(intervjuer) {
			render intervjuer as JSON
		}
	}
}

class VelgApneDatoCommand implements grails.validation.Validateable{
	Date dato
}
