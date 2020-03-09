package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT'])
class OpplaringController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 500)
        [opplaringInstanceList: Opplaring.list(params), opplaringInstanceTotal: Opplaring.count()]
    }

    def create = {
        def opplaringInstance = new Opplaring()
        opplaringInstance.properties = params
        return [opplaringInstance: opplaringInstance]
    }

    def save = {
        def opplaringInstance = new Opplaring(params)
        if (opplaringInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), opplaringInstance.id])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [opplaringInstance: opplaringInstance])
        }
    }

    def edit = {
        def opplaringInstance = Opplaring.get(params.id)
        if (!opplaringInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [opplaringInstance: opplaringInstance]
        }
    }

    def update = {
        def opplaringInstance = Opplaring.get(params.id)
        if (opplaringInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (opplaringInstance.version > version) {
                    
                    opplaringInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'opplaring.label', default: 'Opplaring')] as Object[], "Another user has updated this Opplaring while you were editing")
                    render(view: "edit", model: [opplaringInstance: opplaringInstance])
                    return
                }
            }
            opplaringInstance.properties = params
            if (!opplaringInstance.hasErrors() && opplaringInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), opplaringInstance.id])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [opplaringInstance: opplaringInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def opplaringInstance = Opplaring.get(params.id)
        if (opplaringInstance) {
            try {
                opplaringInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'opplaring.label', default: 'Opplaring'), params.id])}"
            redirect(action: "list")
        }
    }
}
