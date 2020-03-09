package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class SkjemaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def oppdragService
	def utvalgImportService
	def malVersjoner = [2, 3]
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 15, 100)
		
		// default sort = oppstartsdato
		if(!params.sort) {
			params.sort = "oppstartDataInnsamling"
			params.order = "desc"
		}
		
        [skjemaInstanceList: Skjema.list(params), skjemaInstanceTotal: Skjema.count()]
    }

	def sendOppdragTilSynk = {
		def skjemaInstance = Skjema.get(params.id)
		
		if(!skjemaInstance) {
			flash.errorMessage = "Fant ikke skjema, så kan ikke send oppdrag til synkronisering mot intervjuer pc'er"
			redirect(action: "list")
			return
		}
		
		def oppdragSynketListe = oppdragService.sendOppdragTilSynkForSkjema(skjemaInstance)
				
		flash.message = "Har sendt " + oppdragSynketListe.size() + " oppdrag til synkronisering mot intervjuer pc'er for skjema " + skjemaInstance.skjemaKortNavn 
		
		redirect(action: "list")
	}
	
    def create = {
        def skjemaInstance = new Skjema()
		def prosjektId = params["prosjekt.id"]
        skjemaInstance.properties = params
        return [skjemaInstance: skjemaInstance, prosjektId: prosjektId, malVersjoner: malVersjoner]
    }

    def save = {
        def skjemaInstance = new Skjema(params)
		def prosjektId = params["prosjektId"]
        if (skjemaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'skjema.label', default: 'Skjema'), skjemaInstance.skjemaNavn])}"
            if(prosjektId) {
				// Kommer fra prosjekt skjema til create derfor retur dit
				redirect(controller: "prosjekt", action: "edit", id: prosjektId)
			}
			else {
				redirect(action: "list")	
			}
        }
        else {
            render(view: "create", model: [skjemaInstance: skjemaInstance])
        }
    }

    def edit = {
        def skjemaInstance = Skjema.get(params.id)
		def prosjektId = params["prosjekt.id"]
		def fraProsjektListe = params["fraProsjektListe"]
        if (!skjemaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjema.label', default: 'Skjema'), params.id])}"
            if(prosjektId) {
				// Kommer fra prosjekt skjema til edit derfor retur dit
				redirect(controller: "prosjekt", action: "edit", id: prosjektId)
			}
			else if(fraProsjektListe) {
				// Kommer fra prosjekt liste til edit derfor retur dit
				redirect(controller: "prosjekt", action: "list")
			}
			else {
				redirect(action: "list")
			}
        }
        else {
            return [skjemaInstance: skjemaInstance, prosjektId: prosjektId, fraProsjektListe: fraProsjektListe, malVersjoner: malVersjoner]
        }
    }

    def update = {
        def skjemaInstance = Skjema.get(params.id)
		def prosjektId = params["prosjektId"]
		def fraProsjektListe = params["fraProsjektListe"]
        if (skjemaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (skjemaInstance.version > version) {
                    skjemaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'skjema.label', default: 'Skjema')] as Object[], "En annen bruker har oppdatert skjemaet mens du redigerte")
                    render(view: "edit", model: [skjemaInstance: skjemaInstance])
                    return
                }
            }
            skjemaInstance.properties = params
			
			if( skjemaInstance.isDirty('delProduktNummer')) {
				flash.errorMessage = "${message(code: 'produktNummer.endring.advarsel')}"
			}
			
            if (!skjemaInstance.hasErrors() && skjemaInstance.save(flush: true)) {
                flash.message = "${message(code: 'sivadm.skjema.oppdatert', args: [skjemaInstance.skjemaNavn])}"
				
				if(prosjektId) {
					// Kommer fra prosjekt skjema til edit derfor retur dit
					redirect(controller: "prosjekt", action: "edit", id: prosjektId)
				}
				else if(fraProsjektListe) {
					// Kommer fra prosjekt liste til edit derfor retur dit
					redirect(controller: "prosjekt", action: "list")
				}
				else {
					redirect(action: "list")
				}
            }
            else {
                render(view: "edit", model: [skjemaInstance: skjemaInstance, prosjektId: prosjektId, fraProsjektListe: fraProsjektListe])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjema.label', default: 'Skjema'), params.id])}"
			if(prosjektId) {
				// Kommer fra prosjekt skjema til edit derfor retur dit
				redirect(controller: "prosjekt", action: "edit", id: prosjektId)
			}
			else if(fraProsjektListe) {
				// Kommer fra prosjekt liste til edit derfor retur dit
				redirect(controller: "prosjekt", action: "list")
			}
			else {
				redirect(action: "list")
			}
        }
    }

    def delete = {
        def skjemaInstance = Skjema.get(params.id)
		
		def utvalgImportList = UtvalgImport.findAllBySkjema( skjemaInstance )
		
		// sletter utvalg ogsaa
		utvalgImportList.each { UtvalgImport utvalgImport ->
			utvalgImportService.deleteUtvalg(utvalgImport)
		}
		
        if (skjemaInstance) {
            try {
                skjemaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'skjema.label', default: 'Skjema'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'skjema.label', default: 'Skjema'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjema.label', default: 'Skjema'), params.id])}"
            redirect(action: "list")
        }
    }
	
	
}
