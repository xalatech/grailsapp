package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class PeriodeController {

	// static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def skjemaService

	def create = {
		def periodeInstance = new Periode()
		periodeInstance.properties = params
		return [periodeInstance: periodeInstance]
	}

	def save = {
		def periodeInstance = new Periode(params)

		periodeInstance.skjema = Skjema.get(params.skjemaId)

		if (periodeInstance.save(flush: true)) {

			def skjemaInstance = Skjema.get(params.skjemaId)
			
			skjemaInstance?.addToPerioder( periodeInstance )
			skjemaInstance?.save(flush: true)

			flash.message = "${message(code: 'default.created.message', args: [message(code: 'periode.label', default: 'Periode'), periodeInstance.periodeNummer])}"
			redirect(controller: "skjema", action: "edit", id: skjemaInstance.id)
		}
		else {
			render(view: "create", model: [periodeInstance: periodeInstance, "skjemaId": params.skjemaId])
		}
	}

	def edit = {
		def periodeInstance = Periode.get(params.id)
		def fraSkjemaListe = params["fraSkjemaListe"]
				
		if (!periodeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'periode.label', default: 'Periode'), params.id])}"
			if(fraSkjemaListe) {
				redirect(controller: "skjema", action: "list")
			}
			else {
				redirect(controller: "skjema", action: "edit", id: params.skjemaId)
			}			
		}
		else {
			return [periodeInstance: periodeInstance, fraSkjemaListe: fraSkjemaListe]
		}
	}

	def update = {
		def periodeInstance = Periode.get(params.id)
		def skjemaId = params["skjemaId"]
		def fraSkjemaListe = params["fraSkjemaListe"]
		
		if (periodeInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (periodeInstance.version > version) {

					periodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'periode.label', default: 'Periode')]
					as Object[], "En annen bruker har oppdatert perioden, mens du redigerte")
					render(view: "edit", model: [periodeInstance: periodeInstance])
					return
				}
			}
			periodeInstance.properties = params
			if (!periodeInstance.hasErrors() && periodeInstance.save(flush: true)) {
				def skjema = skjemaService.findByPeriode(new Long(params.id))
				if(fraSkjemaListe) {
					flash.message = "${message(code: 'sivadm.periode.oppdatert.skjema.info', args: [periodeInstance.periodeNummer, skjema.skjemaNavn])}"
					redirect(controller: "skjema", action: "list")
				}
				else {
					flash.message = "${message(code: 'sivadm.periode.oppdatert', args: [periodeInstance.periodeNummer])}"
					redirect(controller: "skjema", action: "edit", id: skjema.id)
				}
			}
			else {
				render(view: "edit", model: [periodeInstance: periodeInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'periode.label', default: 'Periode'), params.id])}"
			if(fraSkjemaListe) {
				redirect(controller: "skjema", action: "list")
			}
			else if(skjemaId) {
				redirect(controller: "skjema", action: "edit", id: skjemaId)
			}
			else {
				redirect(controller: "skjema", action: "list")
			}	
		}
	}

	def delete = {
		def periodeInstance = Periode.get(params.id)
		def fraSkjemaListe = params["fraSkjemaListe"]
		def skjemaId = params["skjemaId"]
		
		def skjema = skjemaService.findByPeriode(new Long(params.id))
			
		if (periodeInstance) {
			try {
				def name = periodeInstance.periodeNummer
				periodeInstance.delete(flush: true)
				if(fraSkjemaListe) {
					flash.message = "${message(code: 'sivadm.periode.slettet.skjema.info', args: [name, skjema.skjemaNavn])}"
					redirect(controller: "skjema", action: "list")
				}
				else {
					flash.message = "${message(code: 'sivadm.periode.slettet', args: [name])}"
					redirect(controller: "skjema", action: "edit", id: skjema.id)
				}
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'periode.label', default: 'Periode'), params.id])}"
				redirect(action: "edit", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'periode.label', default: 'Periode'), params.id])}"
			if(fraSkjemaListe) {
				redirect(controller: "skjema", action: "list")
			}
			else if(skjemaId) {
				redirect(controller: "skjema", action: "edit", id: skjemaId)
			}
			else {
				redirect(controller: "skjema", action: "list")
			}
		}
	}

	def avbryt = {
		def skjema
		def fraSkjemaListe = params["fraSkjemaListe"]
		
		if(params.skjemaId) {
			skjema = Skjema.get(params.skjemaId)
		}
		else {
			skjema = skjemaService.findByPeriode(new Long(params.id))
		}
		
		if(fraSkjemaListe) {
			redirect(controller: "skjema", action: "list")
		}
		else {
			redirect(controller: "skjema", action: "edit", id: skjema.id )
		}
	}
}
