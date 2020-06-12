package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class BrukerController {

	def springSecurityService
	def intervjuerService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 500, 600)
		
		def initialer
		def taMedIntervjuere = false
		
		if (request.post) {
			if(params.initialer) {
				initialer = params.initialer
			}
			
			if(params.taMedIntervjuere) {
				taMedIntervjuere = true
			}
		}
										
        def br = BrukerRolle.createCriteria()
        def intervjuerIdList = br {
            eq("rolle", Rolle.findByAuthority('ROLE_INTERVJUER'))
            projections { distinct("bruker.id") }
        }

        def bc = Bruker.createCriteria()
		
		def brukerList = bc {
			if(initialer) {
				or {
					ilike("username", initialer)
					ilike("navn", "%" + initialer + "%")
				}
			}

            if(!taMedIntervjuere) {
                not {
                    'in'("id", intervjuerIdList)
                }
            }
		}
				
		def brukerInstanceList = brukerList
		def brukerInstanceTotal = brukerInstanceList.size()
		
        [
			brukerInstanceList: brukerInstanceList,
			brukerInstanceTotal: brukerInstanceTotal,
			initialer: initialer,
			taMedIntervjuere: taMedIntervjuere
		]
    }

    def create = {
        def brukerInstance = new Bruker()
        brukerInstance.properties = params
		brukerInstance.enabled = true
		def rolleListe = Rolle.getAll()
        return [brukerInstance: brukerInstance, rolleListe: rolleListe]
    }

    def save = {
        def brukerInstance = new Bruker(params)
		def rolleListe = Rolle.getAll()
		def roller = []
		brukerInstance.password = springSecurityService.encodePassword(params.password)
		
        if(brukerInstance.save(flush: true)) {
			rolleListe.each {
				if(params[it.authority]) {
					roller << it
				}
			}
			
			brukerInstance.setAuthorities(roller)
			
			def info = brukerInstance?.navn ? (brukerInstance?.navn + " (" + brukerInstance?.username + ")") : brukerInstance?.username
			
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'bruker.label', default: 'Bruker'), info])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [brukerInstance: brukerInstance])
        }
    }

    def edit = {
        def brukerInstance = Bruker.get(params.id)
        if (!brukerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
            redirect(action: "list")
        }
        else {
			def rolleListe = Rolle.getAll()
            return [brukerInstance: brukerInstance, rolleListe: rolleListe]
        }
    }

    def update = {
        def brukerInstance = Bruker.get(params.id)		
        if (brukerInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (brukerInstance.version > version) {
                    
                    brukerInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bruker.label', default: 'Bruker')] as Object[], "Another user has updated this Bruker while you were editing")
                    render(view: "edit", model: [brukerInstance: brukerInstance])
                    return
                }
            }
            brukerInstance.properties = params
			def rolleListe = Rolle.getAll()
			def roller = []
			
			rolleListe.each {
				if(params[it.authority]) {
					roller << it	
				}
			}
			
			if(brukerInstance.validate()) {
				intervjuerService.oppdaterIntervjuerHvisBrukerErEndret(brukerInstance)
			}
			
			brukerInstance.setAuthorities(roller)
			
            if (!brukerInstance.hasErrors() && brukerInstance.save(flush: true)) {
				def info = brukerInstance?.navn ? (brukerInstance?.navn + " (" + brukerInstance?.username + ")") : brukerInstance?.username
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bruker.label', default: 'Bruker'), info])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [brukerInstance: brukerInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def brukerInstance = Bruker.get(params.id)
        if (brukerInstance) {
            try {
				BrukerRolle.executeUpdate("delete BrukerRolle br where br.bruker = :bruker", [bruker:brukerInstance])
				
                brukerInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
                redirect(action: "list")
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
            redirect(action: "list")
        }
    }
}
