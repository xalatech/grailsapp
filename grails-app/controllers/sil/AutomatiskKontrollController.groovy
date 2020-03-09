package sil

import java.util.Date;
import grails.plugin.springsecurity.annotation.Secured
import siv.data.ProduktKode

@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class AutomatiskKontrollController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def timeforingService
	def brukerService
	def automatiskKontrollService
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [automatiskKontrollInstanceList: AutomatiskKontroll.list(params), automatiskKontrollInstanceTotal: AutomatiskKontroll.count()]
    }

    def create = {
		def automatiskKontrollInstance = new AutomatiskKontroll()
        automatiskKontrollInstance.properties = params
		
		List<ProduktKode> prodListe = timeforingService.hentProduktKode(null, true)
		
        return [automatiskKontrollInstance: automatiskKontrollInstance, produktNummerListe: prodListe]
    }

    def save = {
        def automatiskKontrollInstance = new AutomatiskKontroll(params)
		
		automatiskKontrollInstance.datoEndret = new Date()
		automatiskKontrollInstance.endretAv = brukerService.getCurrentUserName() ?: ""
		
        if(automatiskKontrollInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), automatiskKontrollInstance.kontrollNavn])}"
            redirect(action: "list", id: automatiskKontrollInstance.id)
        }
        else {
            render(view: "create", model: [automatiskKontrollInstance: automatiskKontrollInstance])
        }
    }

    def edit = {
        def automatiskKontrollInstance = AutomatiskKontroll.get(params.id)
        if (!automatiskKontrollInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), params.id])}"
            redirect(action: "list")
        }
        else {
			List<ProduktKode> prodListe = timeforingService.hentProduktKode(null, true)
			
            return [automatiskKontrollInstance: automatiskKontrollInstance, produktNummerListe: prodListe]
        }
    }

    def update = {
        def automatiskKontrollInstance = AutomatiskKontroll.get(params.id)
        if (automatiskKontrollInstance) {
			if (params.version) {
                def version = params.version.toLong()
				if (automatiskKontrollInstance.version > version) {
                    
                    automatiskKontrollInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll')] as Object[], "Another user has updated this AutomatiskKontroll while you were editing")
                    render(view: "edit", model: [automatiskKontrollInstance: automatiskKontrollInstance])
                    return
                }
            }
            automatiskKontrollInstance.properties = params
			automatiskKontrollInstance.datoEndret = new Date()
			automatiskKontrollInstance.endretAv = brukerService.getCurrentUserName() ?: ""
			if (!automatiskKontrollInstance.hasErrors() && automatiskKontrollInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), automatiskKontrollInstance.kontrollNavn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [automatiskKontrollInstance: automatiskKontrollInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def automatiskKontrollInstance = AutomatiskKontroll.get(params.id)
        if (automatiskKontrollInstance) {
            try {
				automatiskKontrollService.slettAutomatiskKontroll( automatiskKontrollInstance )
				
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll'), params.id])}"
            redirect(action: "list")
        }
    }
}
