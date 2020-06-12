package sivadm

import siv.data.IntervjuerSystemKommando;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class SystemKommandoController {

	def brukerService
	def systemKommandoService

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


	def index = {
		redirect(action: "list", params: params)
	}

	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def systemKommandoInstanceList
		if (params.sort==null){  // default sortering på redigertDato
			systemKommandoInstanceList = SystemKommando.list(params).sort{it.redigertDato}
			systemKommandoInstanceList.reverse(true)
		}else { // brukeren ber om sortering på et felt ved å trykke på kolonnenavn
			systemKommandoInstanceList = SystemKommando.list(params)
		}
		[systemKommandoInstanceList: systemKommandoInstanceList , systemKommandoInstanceTotal: SystemKommando.count()]
	}

	
	def create = {
		def systemKommandoInstance = new SystemKommando()
		systemKommandoInstance.properties = params
		return [systemKommandoInstance: systemKommandoInstance]
	}

	
	def save = {
		def systemKommandoInstance = new SystemKommando(params)

		systemKommandoInstance.redigertAv = brukerService.getCurrentUserName()
		systemKommandoInstance.redigertDato = now()

		if (systemKommandoInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'systemKommando.label', default: 'SystemKommando'), systemKommandoInstance.id])}"
			redirect(action: "list")
		}
		else {
			render(view: "create", model: [systemKommandoInstance: systemKommandoInstance])
		}
	}

	
	def edit = {

		def systemKommandoInstance = SystemKommando.get(params.id)

		if (!systemKommandoInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'systemKommando.label', default: 'SystemKommando'), params.id])}"
			redirect(action: "list")
		}
		else {

			List<IntervjuerSystemKommando> intervjuerSystemKommandoList = systemKommandoService.hentAlleIntervjuereForEnSystemKommando(systemKommandoInstance)
			
			def valgteIntervjuereIdList = intervjuerSystemKommandoList.collect {it.intervjuerId}
			
			def ikkeValgteIntervjuere = systemKommandoService.hentAlleIntervjuereSommIkkeErValgtForEnSystemKommando(systemKommandoInstance)

			return [ ikkeValgteIntervjuere: ikkeValgteIntervjuere.sort{ it.navn },
					intervjuerSystemKommandoList: intervjuerSystemKommandoList.sort{ it.navn},
					systemKommandoInstance: systemKommandoInstance
			]
		}
	}

	
	def update = {
		def systemKommandoInstance = SystemKommando.get(params.id)
		if (systemKommandoInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (systemKommandoInstance.version > version) {

					systemKommandoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'systemKommando.label', default: 'SystemKommando')]
					as Object[], "Another user has updated this SystemKommando while you were editing")
					render(view: "edit", model: [systemKommandoInstance: systemKommandoInstance])
					return
				}
			}
			systemKommandoInstance.properties = params
			if (!systemKommandoInstance.hasErrors() && systemKommandoInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'systemKommando.label', default: 'SystemKommando'), systemKommandoInstance.id])}"
				redirect(action: "list")
			}
			else {
				render(view: "edit", model: [systemKommandoInstance: systemKommandoInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'systemKommando.label', default: 'SystemKommando'), params.id])}"
			redirect(action: "list")
		}
	}

	
	def delete = {
		
		def systemKommandoInstance = SystemKommando.get(params.id)
		
		if (systemKommandoInstance) {
			systemKommandoService.slettSystemKommando(systemKommandoInstance)
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'systemKommando.label', default: 'SystemKommando'), params.id])}"
		}
		
		redirect(action: "list")
	}

	
	def leggTilIntervjuere = {

		List<Intervjuer> intervjuere = Intervjuer.getAll(params.list('intervjuere'))
		SystemKommando systemKommando = SystemKommando.get(params.id)
		
		if( intervjuere ) {
			systemKommandoService.leggTilIntervjuereForSystemKommando(intervjuere, systemKommando)
		}
		redirect(action: "edit", id: params.id)
	}

	
	def leggTilAlleIntervjuere = {
		
		SystemKommando systemKommando = SystemKommando.get(params.id)
		
		systemKommandoService.leggTilAlleIntervjuereForSystemKommando( systemKommando )
		
		redirect(action: "edit", id: params.id)
	}

	
	def taVekkIntervjuer = {
		SystemKommando systemKommando = SystemKommando.get(params.id)
		Intervjuer intervjuer = Intervjuer.get(params.valgtIntervjuer)
		
		if( intervjuer ) {
			systemKommandoService.taVekkIntervjuer(systemKommando, intervjuer)
		}
		

		redirect(action: "edit", id: params.id)
	}

	
	
	def taVekkAlleIntervjuer = {
		SystemKommando systemKommando = SystemKommando.get(params.id)

		systemKommandoService.taVekkAlleIntervjuere(systemKommando)

		redirect(action: "edit", id: params.id)
	}
	
	
	/**
	 * Brukes fra CAPI applet for aa registrere kjoringer av systemkommandoer.
	 */
	def registrerKjoring = {
		
		log.info "Registrer kjøring kalt opp fra applet."
		
		try {
			def initialer = params.initialer
			def systemKommandoId = params.systemKommandoId
			Boolean suksess =  Boolean.parseBoolean( params.suksess )
			
			log.info "Verdier: Initialer=" + initialer + ", Systemkommando=" + systemKommandoId + ", Suksess=" + suksess
			
			Intervjuer intervjuer = Intervjuer.findByInitialer(initialer)
			SystemKommando systemKommando = SystemKommando.get( Long.parseLong(systemKommandoId) )
				 
			systemKommandoService.registrerSystemKommandoUtfortForIntervjuer( intervjuer, systemKommando, suksess )
			
			render "OK"
		}	
		catch (Exception e) {
			log.error(e.getMessage())
			render "FEIL"
		}	
	}

	
	/**
	 * Hent dato
	 * @return
	 */
	private Date now() {
		return new Date()
	}
}
