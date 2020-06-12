package sivadm

import grails.plugin.springsecurity.annotation.Secured
import siv.search.OppdragSok

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class OppdragController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def oppdragService
	
    def index = {
        redirect(action: "list", params: params)
    }
	
	def nullstillSok = {
		session.oppdragSok = null
		redirect(action: "list")
	}

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 100, 100)
        
		def oppdragSok
		
		if(request.post) {
			oppdragSok = new OppdragSok()
			bindData(oppdragSok, params)
			session.oppdragSok = oppdragSok
		}
		else {
			if(session.oppdragSok) {
				oppdragSok = session.oppdragSok 
			}
		}
			
		def oppdragInstanceList = oppdragService.sokOppdrag(oppdragSok, params)
		def oppdragInstanceTotal = oppdragService.tellSokOppdrag(oppdragSok)
		
		[
			oppdragInstanceList: oppdragInstanceList,
			oppdragInstanceTotal: oppdragInstanceTotal,
			oppdragSok: oppdragSok
		]
    }

    def create = {
        def oppdragInstance = new Oppdrag()
        oppdragInstance.properties = params
        return [oppdragInstance: oppdragInstance]
    }

    def save = {
        def oppdragInstance = new Oppdrag(params)
        if (oppdragInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), oppdragInstance.id])}"
            redirect(action: "show", id: oppdragInstance.id)
        }
        else {
            render(view: "create", model: [oppdragInstance: oppdragInstance])
        }
    }

    def show = {
        def oppdragInstance = Oppdrag.get(params.id)
        if (!oppdragInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
            redirect(action: "list")
        }
        else {
            [oppdragInstance: oppdragInstance]
        }
    }

    def edit = {
        def oppdragInstance = Oppdrag.get(params.id)
        if (!oppdragInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [oppdragInstance: oppdragInstance]
        }
    }

    def update = {
        def oppdragInstance = Oppdrag.get(params.id)
        if (oppdragInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (oppdragInstance.version > version) {
                    
                    oppdragInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'oppdrag.label', default: 'Oppdrag')] as Object[], "Another user has updated this Oppdrag while you were editing")
                    render(view: "edit", model: [oppdragInstance: oppdragInstance])
                    return
                }
            }
            oppdragInstance.properties = params
            if (!oppdragInstance.hasErrors() && oppdragInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), oppdragInstance.id])}"
                redirect(action: "show", id: oppdragInstance.id)
            }
            else {
                render(view: "edit", model: [oppdragInstance: oppdragInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def oppdragInstance = Oppdrag.get(params.id)
        if (oppdragInstance) {
            try {
                oppdragInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'oppdrag.label', default: 'Oppdrag'), params.id])}"
            redirect(action: "list")
        }
    }
}
