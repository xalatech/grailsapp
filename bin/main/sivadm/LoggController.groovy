package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class LoggController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [loggInstanceList: Logg.list(params), loggInstanceTotal: Logg.count()]
    }

    def create = {
        def loggInstance = new Logg()
        loggInstance.properties = params
        return [loggInstance: loggInstance]
    }

    def save = {
        def loggInstance = new Logg(params)
        if (loggInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'logg.label', default: 'Logg'), loggInstance.id])}"
            redirect(action: "show", id: loggInstance.id)
        }
        else {
            render(view: "create", model: [loggInstance: loggInstance])
        }
    }

    def show = {
        def loggInstance = Logg.get(params.id)
        if (!loggInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
            redirect(action: "list")
        }
        else {
            [loggInstance: loggInstance]
        }
    }

    def edit = {
        def loggInstance = Logg.get(params.id)
        if (!loggInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [loggInstance: loggInstance]
        }
    }

    def update = {
        def loggInstance = Logg.get(params.id)
        if (loggInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (loggInstance.version > version) {
                    
                    loggInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'logg.label', default: 'Logg')] as Object[], "Another user has updated this Logg while you were editing")
                    render(view: "edit", model: [loggInstance: loggInstance])
                    return
                }
            }
            loggInstance.properties = params
            if (!loggInstance.hasErrors() && loggInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'logg.label', default: 'Logg'), loggInstance.id])}"
                redirect(action: "show", id: loggInstance.id)
            }
            else {
                render(view: "edit", model: [loggInstance: loggInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def loggInstance = Logg.get(params.id)
        if (loggInstance) {
            try {
                loggInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'logg.label', default: 'Logg'), params.id])}"
            redirect(action: "list")
        }
    }
}
