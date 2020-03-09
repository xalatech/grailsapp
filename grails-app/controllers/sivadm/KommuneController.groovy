package sivadm

import grails.converters.*


import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class KommuneController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [kommuneInstanceList: Kommune.list(params), kommuneInstanceTotal: Kommune.count()]
    }

    def create = {
        def kommuneInstance = new Kommune()
        kommuneInstance.properties = params
        return [kommuneInstance: kommuneInstance]
    }

    def save = {
        def kommuneInstance = new Kommune(params)
        if (kommuneInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'kommune.label', default: 'Kommune'), kommuneInstance.id])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [kommuneInstance: kommuneInstance])
        }
    }

    def edit = {
        def kommuneInstance = Kommune.get(params.id)
        if (!kommuneInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kommune.label', default: 'Kommune'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [kommuneInstance: kommuneInstance]
        }
    }

    def update = {
        def kommuneInstance = Kommune.get(params.id)
        if (kommuneInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (kommuneInstance.version > version) {
                    
                    kommuneInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'kommune.label', default: 'Kommune')] as Object[], "Another user has updated this Kommune while you were editing")
                    render(view: "edit", model: [kommuneInstance: kommuneInstance])
                    return
                }
            }
            kommuneInstance.properties = params
            if (!kommuneInstance.hasErrors() && kommuneInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'kommune.label', default: 'Kommune'), kommuneInstance.id])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [kommuneInstance: kommuneInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kommune.label', default: 'Kommune'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def kommuneInstance = Kommune.get(params.id)
        if (kommuneInstance) {
            try {
                kommuneInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'kommune.label', default: 'Kommune'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'kommune.label', default: 'Kommune'), params.id])}"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kommune.label', default: 'Kommune'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def ajaxGetKommuneViaPoststed = {
		String poststed = params.poststed
		def kommune = Kommune.findByKommuneNavn(poststed.toUpperCase())
		if(!kommune) {
			kommune = Kommune.findByKommuneNavn(poststed)
		}
		if(kommune) {
			render kommune as JSON
		}
	}
}
