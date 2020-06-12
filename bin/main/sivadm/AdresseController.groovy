package sivadm

import siv.type.MeldingType;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class AdresseController {

	SynkroniseringService synkroniseringService
	XmlService xmlService
	def brukerService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def adresseInstance = new Adresse()
        adresseInstance.properties = params
		adresseInstance.gyldigFom = new Date()
		
		if(!params.gjeldende) {
			adresseInstance.gjeldende = true
		}
		
        return [adresseInstance: adresseInstance, ioId: params.ioId]
    }

    def save = {
        def adresseInstance = new Adresse(params)
		
		def intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
		
		Long ioId = intervjuObjektInstance.id
		
		if(brukerService.getCurrentUserName()) {
			adresseInstance.redigertAv = brukerService.getCurrentUserName()
		}
		
		if(adresseInstance.gjeldende) {
			// Hvis adressen er satt til gjeldende sjekk at det ikke er andre
			// adresser av samme type som også er satt til gjeldende, og hvis så
			// returner feilmelding
			
			intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
			
			if(intervjuObjektInstance?.adresser?.size() > 0) {
				boolean harGjeldende = false
				
				intervjuObjektInstance.adresser.each { a ->
					if( (a.adresseType == adresseInstance.adresseType) && (a.gjeldende == true ) ) {
						harGjeldende = true
					}
				}
				
				if(harGjeldende) {
					flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.adresse.flere.gjeldende', args:[adresseInstance.adresseType], default: 'Intervjuobjektet har allerede en gjeldende adresse av samme type')}"
					render(view: "create", model: [adresseInstance: adresseInstance, ioId: params.ioId])
					return
				}
			}
		}
		
		if(!adresseInstance.postSted || adresseInstance.postSted.equals("")) {
			flash.errorMessage = "${message(code: 'adresse.postSted.blank', default: 'Poststed må fylles ut')}"
			render(view: "create", model: [adresseInstance: adresseInstance, ioId: params.ioId, poststedError: true])
			return
		}
		
        if(adresseInstance.save()) {
			
			intervjuObjektInstance.addToAdresser(adresseInstance)
			def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

			if(!intervjuObjektInstance.bareWebSkjema()) {
				if (altIBlaise5) {
					synkroniseringService.synkroniserAdresseEndringV5(intervjuObjektInstance)
				} else {
					synkroniseringService.synkroniserAdresseEndring(intervjuObjektInstance.id)
				}
			}
			
			def info = adresseInstance.gateAdresse ?: adresseInstance.id
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'adresse.label', default: 'Adresse'), info])}"
            		
			redirect(controller:"intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
        }
        else {
			render(view: "create", model: [adresseInstance: adresseInstance, ioId: params.ioId])
        }
    }

    def edit = {
        def adresseInstance = Adresse.get(params.id)
        if (!adresseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adresse.label', default: 'Adresse'), params.id])}"
			redirect(controller: "intervjuObjekt", action: "edit", id: params.ioId)
        }
        else {
            return [adresseInstance: adresseInstance, ioId: params.ioId]
        }
    }

    def update = {
        def adresseInstance = Adresse.get(params.id)
        if (adresseInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (adresseInstance.version > version) {
                    
                    adresseInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'adresse.label', default: 'Adresse')] as Object[], "Another user has updated this Adresse while you were editing")
                    render(view: "edit", model: [adresseInstance: adresseInstance])
                    return
                }
            }
						
            adresseInstance.properties = params
									
			def intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
						
			if(adresseInstance.gjeldende) {
				// Hvis adressen er satt til gjeldende sjekk at det ikke er andre
				// adresser av samme type som også er satt til gjeldende, og hvis så
				// returner feilmelding
				
				if(intervjuObjektInstance?.adresser?.size() > 1) {
					boolean harGjeldende = false
					
					intervjuObjektInstance.adresser.each { a ->
						if(adresseInstance.id == a.id) {
							//Ser på seg selv ikke gjør noe
						}
						else if(a.adresseType == adresseInstance.adresseType && a.gjeldende == Boolean.TRUE) {
							harGjeldende = true
						}
					}
							
					if(harGjeldende) {
						flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.adresse.flere.gjeldende', args:[adresseInstance.adresseType], default: 'Intervjuobjektet har allerede en gjeldende adresse av samme type')}"
						render(view: "edit", model: [adresseInstance: adresseInstance, ioId: params.ioId])
						return
					}
				}
			}
						
			if(brukerService.getCurrentUserName()) {
				adresseInstance.redigertAv = brukerService.getCurrentUserName()
			}
			
			if(!adresseInstance.postSted || adresseInstance.postSted.equals("")) {
				flash.errorMessage = "${message(code: 'adresse.postSted.blank', default: 'Poststed må fylles ut')}"
				render(view: "edit", model: [adresseInstance: adresseInstance, ioId: params.ioId, poststedError: true])
				return
			}
			
            if (!adresseInstance.hasErrors() && adresseInstance.save(flush: true)) {
				def info = adresseInstance.gateAdresse ?: adresseInstance.id

				if(!intervjuObjektInstance) {
					intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
				}

				def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

				if(!intervjuObjektInstance.bareWebSkjema()) {
					if (altIBlaise5) {
						synkroniseringService.synkroniserAdresseEndringV5(intervjuObjektInstance)
					} else {
						synkroniseringService.synkroniserAdresseEndring(intervjuObjektInstance.id)
					}

					flash.message = "${message(code: 'adresse.oppdatert', args: [info])}"
				} else {
					flash.message = "${message(code: 'adresse.oppdatert.webonly', args: [info])}"
				}

				redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
            }
            else {
                render(view: "edit", model: [adresseInstance: adresseInstance, ioId: params.ioId])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adresse.label', default: 'Adresse'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
    }
	
	def avbryt = {
		def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByAdresse(Adresse.get(params.id))
		redirect( controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
	}
	
	def cancelCreate = {
		redirect( controller: "intervjuObjekt", action: "edit", id: params.id)
	}
	
	def delete = {
		def adresseInstance = Adresse.get(params.id)
			
		if(adresseInstance) {
			def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByAdresse(adresseInstance)
			
			intervjuObjektInstance.removeFromAdresser( adresseInstance )
			intervjuObjektInstance.save(flush: true)
			
			try {
				adresseInstance.delete(flush: true)
				def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

				if(!intervjuObjektInstance.bareWebSkjema()) {
					if (altIBlaise5) {
						synkroniseringService.synkroniserAdresseEndringV5(intervjuObjektInstance)
					} else {
						synkroniseringService.synkroniserAdresseEndring(intervjuObjektInstance.id)
					}
					synkroniseringService.synkroniserAdresseEndringV5(intervjuObjektInstance)
				}
				
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'adresse.label', default: 'Adresse'), params.id])}"
				
				redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'adresse.label', default: 'Adresse'), params.id])}"
				redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'adresse.label', default: 'Adresse'), params.id])}"
			redirect(controller: "intervjuObjekt", action: "edit", id: params.ioId)
		}
	}
}
