package sivadm

class HusholdningController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def husholdningInstance = new Husholdning()
        husholdningInstance.properties = params
        return [husholdningInstance: husholdningInstance, ioId: params.ioId]
    }

    def save = {
        def husholdningInstance = new Husholdning(params)
        
		def intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
		
		Long ioId = intervjuObjektInstance.id
		
		husholdningInstance.husholdNummer = intervjuObjektInstance?.husholdninger? intervjuObjektInstance.husholdninger.size() + 1 : 1
		
		if(husholdningInstance.save(flush: true)) {
			intervjuObjektInstance.addToHusholdninger(husholdningInstance)
		
			if(intervjuObjektInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), husholdningInstance.navn])}"
				redirect(controller:"intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
			}
			else {
				flash.errorMessage = "${message(code: 'default.invalid.max.size.message', args: ['husholdningsmedlemmer','Intervjuobjekt','dummy','10'], default: 'Intervjuobjekt kan ikke ha mer enn 10 husholdningsmedlemmer')}"
				render(view: "create", model: [husholdningInstance: husholdningInstance, ioId: intervjuObjektInstance.id])
			}
        }
        else {
            render(view: "create", model: [husholdningInstance: husholdningInstance, ioId: params.ioId])
        }
    }

    def edit = {
        def husholdningInstance = Husholdning.get(params.id)
        if (!husholdningInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
        else {
            return [husholdningInstance: husholdningInstance, ioId: params.ioId]
        }
    }

    def update = {
        def husholdningInstance = Husholdning.get(params.id)
		
		if(husholdningInstance) {
			def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByHusholdning(husholdningInstance)
            if (params.version) {
                def version = params.version.toLong()
                if (husholdningInstance.version > version) {
                    
                    husholdningInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'husholdning.label', default: 'Husholdning')] as Object[], "Another user has updated this Husholdning while you were editing")
                    render(view: "edit", model: [husholdningInstance: husholdningInstance, ioId: intervjuObjektInstance.id])
                    return
                }
            }
            husholdningInstance.properties = params
            if (!husholdningInstance.hasErrors() && husholdningInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), husholdningInstance.navn])}"
				redirect(controller:"intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
            }
            else {
                render(view: "edit", model: [husholdningInstance: husholdningInstance, ioId: intervjuObjektInstance.id])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
    }

    def delete = {
        def husholdningInstance = Husholdning.get(params.id)
			
		if(husholdningInstance) {
			def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByHusholdning(husholdningInstance)
			
			if(intervjuObjektInstance.husholdninger?.size() != husholdningInstance.husholdNummer) {
				intervjuObjektInstance.husholdninger.each {
					if(it.husholdNummer > husholdningInstance.husholdNummer) {
						it.husholdNummer = it.husholdNummer - 1
						it.save()	
					}
				}
			}
			
			intervjuObjektInstance.removeFromHusholdninger(husholdningInstance)
			
			try {
                husholdningInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), params.id])}"
				redirect(controller:"intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), params.id])}"
				redirect(controller:"intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'husholdning.label', default: 'Husholdning'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
    }
	
	def cancelCreate = {
		redirect( controller: "intervjuObjekt", action: "edit", id: params.id)
	}
}
