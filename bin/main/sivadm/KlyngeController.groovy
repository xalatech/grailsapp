package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class KlyngeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 200, 200)
        [klyngeInstanceList: Klynge.list(params), klyngeInstanceTotal: Klynge.count()]
    }

    def create = {
        def klyngeInstance = new Klynge()
        klyngeInstance.properties = params
        return [klyngeInstance: klyngeInstance]
    }

    def save = {
        def klyngeInstance = new Klynge(params)
        if (klyngeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'klynge.label', default: 'Klynge'), klyngeInstance.klyngeNavn])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [klyngeInstance: klyngeInstance])
        }
    }

    def edit = {
        def klyngeInstance = Klynge.get(params.id)
        if (!klyngeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'klynge.label', default: 'Klynge'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [klyngeInstance: klyngeInstance]
        }
    }

    def update = {
        def klyngeInstance = Klynge.get(params.id)
        if (klyngeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (klyngeInstance.version > version) {
                    
                    klyngeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'klynge.label', default: 'Klynge')] as Object[], "Another user has updated this Klynge while you were editing")
                    render(view: "edit", model: [klyngeInstance: klyngeInstance])
                    return
                }
            }
            klyngeInstance.properties = params
            if (!klyngeInstance.hasErrors() && klyngeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'klynge.label', default: 'Klynge'), klyngeInstance.klyngeNavn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [klyngeInstance: klyngeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'klynge.label', default: 'Klynge'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def klyngeInstance = Klynge.get(params.id)
        if (klyngeInstance) {
            try {
                klyngeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'klynge.label', default: 'Klynge'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'klynge.label', default: 'Klynge'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'klynge.label', default: 'Klynge'), params.id])}"
            redirect(action: "list")
        }
    }
}
