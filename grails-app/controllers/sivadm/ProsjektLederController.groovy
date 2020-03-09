package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class ProsjektLederController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [prosjektLederInstanceList: ProsjektLeder.list(params), prosjektLederInstanceTotal: ProsjektLeder.count()]
    }

    def create = {
        def prosjektLederInstance = new ProsjektLeder()
        prosjektLederInstance.properties = params
        return [prosjektLederInstance: prosjektLederInstance]
    }

    def save = {
        def prosjektLederInstance = new ProsjektLeder(params)
        if (prosjektLederInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), prosjektLederInstance.navn])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [prosjektLederInstance: prosjektLederInstance])
        }
    }

    def edit = {
        def prosjektLederInstance = ProsjektLeder.get(params.id)
        if (!prosjektLederInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [prosjektLederInstance: prosjektLederInstance]
        }
    }

    def update = {
        def prosjektLederInstance = ProsjektLeder.get(params.id)
        if (prosjektLederInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prosjektLederInstance.version > version) {
                    
                    prosjektLederInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prosjektLeder.label', default: 'ProsjektLeder')] as Object[], "Another user has updated this ProsjektLeder while you were editing")
                    render(view: "edit", model: [prosjektLederInstance: prosjektLederInstance])
                    return
                }
            }
            prosjektLederInstance.properties = params
            if (!prosjektLederInstance.hasErrors() && prosjektLederInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), prosjektLederInstance.navn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [prosjektLederInstance: prosjektLederInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def prosjektLederInstance = ProsjektLeder.get(params.id)
        if (prosjektLederInstance) {
            try {
                prosjektLederInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjektLeder.label', default: 'ProsjektLeder'), params.id])}"
            redirect(action: "list")
        }
    }
}
