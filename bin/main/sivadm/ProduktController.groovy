package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ProduktController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [produktInstanceList: Produkt.list(params), produktInstanceTotal: Produkt.count()]
    }

    def create = {
        def produktInstance = new Produkt()
        produktInstance.properties = params
        return [produktInstance: produktInstance]
    }

    def save = {
        def produktInstance = new Produkt(params)
        if (produktInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'produkt.label', default: 'Produkt'), produktInstance.navn])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [produktInstance: produktInstance])
        }
    }

    def edit = {
        def produktInstance = Produkt.get(params.id)
        if (!produktInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produkt.label', default: 'Produkt'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [produktInstance: produktInstance]
        }
    }

    def update = {
        def produktInstance = Produkt.get(params.id)
        if (produktInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (produktInstance.version > version) {
                    
                    produktInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'produkt.label', default: 'Produkt')] as Object[], "Another user has updated this Produkt while you were editing")
                    render(view: "edit", model: [produktInstance: produktInstance])
                    return
                }
            }
            
			produktInstance.properties = params
			
			if( produktInstance.isDirty('produktNummer')) {
				flash.errorMessage = "${message(code: 'produktNummer.endring.advarsel')}"
			}
			
            if (!produktInstance.hasErrors() && produktInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'produkt.label', default: 'Produkt'), produktInstance.navn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [produktInstance: produktInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produkt.label', default: 'Produkt'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def produktInstance = Produkt.get(params.id)
        if (produktInstance) {
            try {
                produktInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'produkt.label', default: 'Produkt'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'produkt.label', default: 'Produkt'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produkt.label', default: 'Produkt'), params.id])}"
            redirect(action: "list")
        }
    }
}
