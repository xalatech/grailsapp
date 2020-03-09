package sivadm

import java.text.SimpleDateFormat;

import util.DateUtil;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT'])
class FravaerController {

	def brukerService
	
    def create = {
		Intervjuer intervjuerInstance = Intervjuer.get(params.intervjuerId)
				
        def fravaerInstance = new Fravaer()
        fravaerInstance.properties = params
		fravaerInstance.prosent = 100
        return [fravaerInstance: fravaerInstance, intervjuerInstance: intervjuerInstance]
    }

    def save = {
        def fravaerInstance = new Fravaer(params)
		
		def intervjuer = Intervjuer.get(params.intervjuerId)
		fravaerInstance.intervjuer = intervjuer
		fravaerInstance.redigertAv = brukerService.getCurrentUserName()
		fravaerInstance.redigertDato = DateUtil.now()
		
        if (fravaerInstance.save(flush: true)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
            def info = fravaerInstance.fravaerType.toString() + " (" + sdf.format(fravaerInstance.fraTidspunkt) + " - " + sdf.format(fravaerInstance.tilTidspunkt) + ")"
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), info])}"
            redirect(controller: "intervjuer", action: "edit", id: intervjuer.id)
        }
        else {
            render(view: "create", model: [fravaerInstance: fravaerInstance, intervjuerInstance: intervjuer])
        }
    }
 
    def edit = {
        def fravaerInstance = Fravaer.get(params.id)
        if(!fravaerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), params.id])}"
			redirect(controller: "intervjuer", action: "edit", id: params.intervjuerId)
        }
        else {
            return [fravaerInstance: fravaerInstance]
        }
    }

    def update = {
        def fravaerInstance = Fravaer.get(params.id)
        if(fravaerInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (fravaerInstance.version > version) {
                    
                    fravaerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'fravaer.label', default: 'Fravaer')] as Object[], "Another user has updated this Fravaer while you were editing")
                    render(view: "edit", model: [fravaerInstance: fravaerInstance])
                    return
                }
            }
            fravaerInstance.properties = params
			
			fravaerInstance.redigertAv = brukerService.getCurrentUserName()
			fravaerInstance.redigertDato = DateUtil.now()
			
            if (!fravaerInstance.hasErrors() && fravaerInstance.save(flush: true)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
				def info = fravaerInstance.fravaerType.toString() + " (" + sdf.format(fravaerInstance.fraTidspunkt) + " - " + sdf.format(fravaerInstance.tilTidspunkt) + ")"
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), info])}"
                redirect(controller: "intervjuer", action: "edit", id: fravaerInstance.intervjuer.id)
            }
            else {
                render(view: "edit", model: [fravaerInstance: fravaerInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), params.id])}"
			redirect(controller: "intervjuer", action: "edit", id: params.intervjuerId)
        }
    }

    def delete = {
        def fravaerInstance = Fravaer.get(params.id)
        if(fravaerInstance) {
            try {
				def intervjuerId = fravaerInstance.intervjuer.id
                fravaerInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), params.id])}"
                redirect(controller: "intervjuer", action: "edit", id: intervjuerId)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), params.id])}"
				redirect(controller: "intervjuer", action: "edit", id: intervjuerId)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'fravaer.label', default: 'Fravaer'), params.id])}"
			redirect(controller: "intervjuer", action: "edit", id: params.intervjuerId)
        }
    }
}