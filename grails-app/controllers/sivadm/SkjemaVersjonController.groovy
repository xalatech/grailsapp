package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class SkjemaVersjonController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def skjemaService

    def create = {
        def skjemaVersjonInstance = new SkjemaVersjon()
        skjemaVersjonInstance.properties = params
			
        return [skjemaVersjonInstance: skjemaVersjonInstance]
    }

    def save = {
        def skjemaVersjonInstance = new SkjemaVersjon(params)
				
		skjemaVersjonInstance.skjema = Skjema.get(params.skjemaId)
		
        if (skjemaVersjonInstance.save(flush: true)) {
			
			def skjemaInstance = Skjema.get(params.skjemaId)
			skjemaInstance?.addToSkjemaVersjoner(skjemaVersjonInstance)
			skjemaInstance?.save(flush: true)
			
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon'), skjemaVersjonInstance.id])}"
            redirect(controller: "skjema", action: "edit", id: skjemaInstance.id)
        }
        else {
            render(view: "create", model: [skjemaVersjonInstance: skjemaVersjonInstance, "skjemaId": params.skjemaId])
        }
    }
    
    def edit = {
        def skjemaVersjonInstance = SkjemaVersjon.get(params.id)
		def fraSkjemaListe = params["fraSkjemaListe"]
		
        if (!skjemaVersjonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon'), params.id])}"
            if(fraSkjemaListe) {
				redirect(controller: "skjema", action: "list")
			}
			else {
				redirect(controller: "skjema", action: "edit", id: params.skjemaId)
			}	
        }
        else {
            return [skjemaVersjonInstance: skjemaVersjonInstance, fraSkjemaListe: fraSkjemaListe]
        }
    }

    def update = {
        def skjemaVersjonInstance = SkjemaVersjon.get(params.id)
		def skjemaId = params["skjemaId"]
		def fraSkjemaListe = params["fraSkjemaListe"]
		
        if (skjemaVersjonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (skjemaVersjonInstance.version > version) {
                    
                    skjemaVersjonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon')] as Object[], "En annen bruker har oppdatert denne skjemaversjonen mens du redigerte")
                    render(view: "edit", model: [skjemaVersjonInstance: skjemaVersjonInstance])
                    return
                }
            }
			
			def skjema = skjemaService.findBySkjemaVersjon(skjemaVersjonInstance)
			
            skjemaVersjonInstance.properties = params
            if (!skjemaVersjonInstance.hasErrors() && skjemaVersjonInstance.save(flush: true)) {
				if(fraSkjemaListe) {
					flash.message = "${message(code: 'sivadm.skjemaversjon.oppdatert.skjema.info', args: [skjemaVersjonInstance.versjonsNummer, skjema.skjemaNavn])}"
					redirect(controller: "skjema", action: "list")
				}
				else {
					flash.message = "${message(code: 'sivadm.skjemaversjon.oppdatert', args: [skjemaVersjonInstance.versjonsNummer])}"
					redirect(controller: "skjema", action: "edit", id: skjema.id)
				}
            }
            else {
                render(view: "edit", model: [skjemaVersjonInstance: skjemaVersjonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon'), params.id])}"
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
        def skjemaVersjonInstance = SkjemaVersjon.get(params.id)
		def fraSkjemaListe = params["fraSkjemaListe"]
        def skjemaId = params["skjemaId"]
		
		if (skjemaVersjonInstance) {
            try {
				Skjema skjema = skjemaService.findBySkjemaVersjon(skjemaVersjonInstance)
				def name = skjemaVersjonInstance.versjonsNummer
				skjema.removeFromSkjemaVersjoner(skjemaVersjonInstance)
                skjemaVersjonInstance.delete(flush: true)
                
				if(fraSkjemaListe) {
					flash.message = "${message(code: 'sivadm.skjemaversjon.slettet.skjema.info', args: [name, skjema.skjemaNavn])}"
					redirect(controller: "skjema", action: "list")
				}
				else {
					flash.message = "${message(code: 'sivadm.skjemaversjon.slettet', args: [name])}"
					redirect(controller: "skjema", action: "edit", id: skjema.id)
				}
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon'), params.id])}"
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
			skjema = skjemaService.findBySkjemaVersjonId(new Long(params.id))
		}

		if(fraSkjemaListe) {
			redirect(controller: "skjema", action: "list")
		}
		else {
			redirect(controller: "skjema", action: "edit", id: skjema.id )
		}
	}
}
