package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class ProsjektDeltagerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def prosjektDeltagerInstance = new ProsjektDeltager()
		def prosjektId = params["prosjekt.id"]
        prosjektDeltagerInstance.properties = params
        return [prosjektDeltagerInstance: prosjektDeltagerInstance, prosjektId: prosjektId]
    }

    def save = {
        def prosjektDeltagerInstance = new ProsjektDeltager(params)
		def prosjektId = params["prosjektId"]
		def prosjekt = Prosjekt.get(prosjektId)
		prosjektDeltagerInstance.prosjekt = prosjekt
        if (prosjektDeltagerInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), prosjektDeltagerInstance.deltagerNavn])}"
            redirect(controller: "prosjekt", action: "edit", id: prosjektDeltagerInstance.prosjekt.id)
        }
        else {
            render(view: "create", model: [prosjektDeltagerInstance: prosjektDeltagerInstance, prosjektId: prosjektId])
        }
    }

    def edit = {
        def prosjektDeltagerInstance = ProsjektDeltager.get(params.id)
		def prosjektId = params["prosjekt.id"]
        if (!prosjektDeltagerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), params.id])}"
            redirect(controller: "prosjekt", action: "edit", id: prosjektId)
        }
        else {
            return [prosjektDeltagerInstance: prosjektDeltagerInstance, prosjektId: prosjektId]
        }
    }

    def update = {
        def prosjektDeltagerInstance = ProsjektDeltager.get(params.id)
		def prosjektId = params["prosjektId"]
		
        if (prosjektDeltagerInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prosjektDeltagerInstance.version > version) {
                    
                    prosjektDeltagerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager')] as Object[], "Another user has updated this ProsjektDeltager while you were editing")
                    render(view: "edit", model: [prosjektDeltagerInstance: prosjektDeltagerInstance])
                    return
                }
            }
            prosjektDeltagerInstance.properties = params
            if (!prosjektDeltagerInstance.hasErrors() && prosjektDeltagerInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), prosjektDeltagerInstance.deltagerNavn])}"
                redirect(controller: "prosjekt", action: "edit", id: prosjektDeltagerInstance.prosjekt.id)
            }
            else {
                render(view: "edit", model: [prosjektDeltagerInstance: prosjektDeltagerInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), params.id])}"
            redirect(controller: "prosjekt", action: "edit", id: prosjektId)
        }
    }

    def delete = {
        def prosjektDeltagerInstance = ProsjektDeltager.get(params.id)
		def prosjektId = params["prosjektId"]
		
        if (prosjektDeltagerInstance) {
            try {
                prosjektDeltagerInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), params.id])}"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), params.id])}"
            }
			redirect(controller: "prosjekt", action: "edit", id: prosjektId)
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager'), params.id])}"
			redirect(controller: "prosjekt", action: "edit", id: prosjektId)
        }
    }
}
