package sivadm

import siv.type.MeldingType;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class TelefonController {

	SynkroniseringService synkroniseringService
	XmlService xmlService
	def brukerService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def create = {
        def telefonInstance = new Telefon()
        telefonInstance.properties = params
        return [telefonInstance: telefonInstance, ioId: params.ioId]
    }

    def save = {
        def telefonInstance = new Telefon(params)
        
		IntervjuObjekt intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
		Long ioId = intervjuObjektInstance.id
		
		if(brukerService.getCurrentUserName()) {
			telefonInstance.redigertAv = brukerService.getCurrentUserName()
		}
		
		if(telefonInstance.gjeldende) {
			// Hvis telefon er satt til i bruk sjekk at det ikke er flere enn
			// 3 andre som også er i bruk, og hvis så returner feilmelding
						
			if(intervjuObjektInstance?.telefoner?.size() > 2) {
				int gjeldendeCnt = 0
				
				intervjuObjektInstance.telefoner.each { t ->
					if(t.gjeldende) {
						gjeldendeCnt++
					}
				}
						
				if(gjeldendeCnt == 3) {
					flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.telefon.flere.gjeldende', default: 'Intervjuobjektet har allerede 3 telefonnumre som er i bruk')}"
					render(view: "create", model: [telefonInstance: telefonInstance, ioId: params.ioId])
					return
				}
			}
		}
		
		
		if (telefonInstance.save()) {
            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'telefon.label', default: 'Telefon'), telefonInstance.telefonNummer])}"
			
			intervjuObjektInstance.addToTelefoner(telefonInstance)
						
			if(intervjuObjektInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'telefon.label', default: 'Telefon'), telefonInstance.telefonNummer])}"
			}
			else {
				flash.errorMessage = "${message(code: 'default.invalid.max.size.message', args: ['telefonnumre','Intervjuobjekt','dummy','3'], default: 'Intervjuobjekt kan ikke ha flere enn 3 telefonnumre')}"
				render(view: "create", model: [telefonInstance: telefonInstance, ioId: intervjuObjektInstance.id])
			}

			//ny funksjonalitet for Blaise V5
			def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

			//ny funksjonalitet for Blaise V5
			if (altIBlaise5) {
				synkroniseringService.synkroniserTelefonEndringV5(intervjuObjektInstance)
			} else {
				synkroniseringService.synkroniserTelefonEndring(intervjuObjektInstance.id)
			}

            redirect(controller: "intervjuObjekt", action: "edit", id: params.ioId)
        }
        else {
            render(view: "create", model: [telefonInstance: telefonInstance, ioId: params.ioId])
        }
    }

	def edit = {
        def telefonInstance = Telefon.get(params.id)
        if (!telefonInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'telefon.label', default: 'Telefon'), params.id])}"
            redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
        else {
            return [telefonInstance: telefonInstance, ioId: params.ioId]
        }
    }

    def update = {
		def telefonInstance = Telefon.get(params.id)
		
        if (telefonInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (telefonInstance.version > version) {
                    
                    telefonInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'telefon.label', default: 'Telefon')] as Object[], "Another user has updated this Telefon while you were editing")
                    render(view: "edit", model: [telefonInstance: telefonInstance])
                    return
                }
            }
			
			IntervjuObjekt intervjuObjektInstance = IntervjuObjekt.get(params.ioId)
            
			telefonInstance.properties = params
			
			if(brukerService.getCurrentUserName()) {
				telefonInstance.redigertAv = brukerService.getCurrentUserName()
			}
			
			if(telefonInstance.gjeldende) {
				// Hvis telefon er satt til gjeldende sjekk at det ikke er flere enn
				// 3 andre som også er gjeldende, og hvis så returner feilmelding
							
				if(intervjuObjektInstance?.telefoner?.size() > 2) {
					int gjeldendeCnt = 0
					
					intervjuObjektInstance.telefoner.each { t ->
						if(telefonInstance.id == t.id) {
							//Ser på seg selv ikke gjør noe
						}
						else if(t.gjeldende) {
							gjeldendeCnt++
						}
					}
							
					if(gjeldendeCnt == 3) {
						flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.telefon.flere.gjeldende', default: 'Intervjuobjektet har allerede 3 telefonnumre som er i bruk')}"
						render(view: "edit", model: [telefonInstance: telefonInstance, ioId: params.ioId])
						return
					}
				}
			}
			            
			if (!telefonInstance.hasErrors() && telefonInstance.save(flush: true)) {

				def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

				//ny funksjonalitet for Blaise V5
				if(!intervjuObjektInstance.bareWebSkjema()) {
					if (altIBlaise5) {
						synkroniseringService.synkroniserTelefonEndringV5(intervjuObjektInstance)
					} else {
						synkroniseringService.synkroniserTelefonEndring(intervjuObjektInstance.id)
					}

					flash.message = "${message(code: 'telefon.oppdatert', args: [telefonInstance.telefonNummer])}"
				}else{
					flash.message = "${message(code: 'telefon.oppdatert.webonly', args: [telefonInstance.telefonNummer])}"
				}

                redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
            }
            else {
                render(view: "edit", model: [telefonInstance: telefonInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'telefon.label', default: 'Telefon'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
        }
    }
	
	
	def delete = {
		def telefonInstance = Telefon.get(params.id)
		
		if(telefonInstance) {
			def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByTelefon(telefonInstance)
			
			intervjuObjektInstance.removeFromTelefoner( telefonInstance )
			
			intervjuObjektInstance.save(flush: true)
			
			def info = telefonInstance.telefonNummer + " (" + telefonInstance.telefonType + ")"
			
			try {
				telefonInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'telefon.label', default: 'Telefon'), info])}"
				def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false

				//ny funksjonalitet for Blaise V5
				if (altIBlaise5) {
					synkroniseringService.synkroniserTelefonEndringV5(intervjuObjektInstance)
				} else {
					synkroniseringService.synkroniserTelefonEndring(intervjuObjektInstance.id)
				}

				redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'telefon.label', default: 'Telefon'), params.id])}"
				redirect(action: "edit", id: params.id, ioId: params.ioId)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'telefon.label', default: 'Telefon'), params.id])}"
			redirect(controller:"intervjuObjekt", action: "edit", id: params.ioId)
		}
	}
	
	def avbryt = {
		def intervjuObjektInstance = IntervjuObjekt.findIntervjuObjektByTelefon(Telefon.get(params.id))
		redirect( controller: "intervjuObjekt", action: "edit", id: intervjuObjektInstance.id)
	}
	
	def cancelCreate = {
		redirect( controller: "intervjuObjekt", action: "edit", id: params.id)
	}
}
