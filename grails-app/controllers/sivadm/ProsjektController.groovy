package sivadm

import siv.type.ProsjektStatus;
import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured
import grails.orm.*

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class ProsjektController {

	def utvalgImportService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		int pmax
		int poffs
		
		def prosjektInstanceList  
		def prosjektInstanceTotal
		def prosjektStatusDefault
		
		if (request.post){  // Nytt søk
			session.inSearch = 1
			session.offset   = 0
			session.produktNummer   = params.produktNummer
			session.prosjektNavn    = params.prosjektNavn
			session.prosjektStatus  = params.prosjektStatus
			session.prosjektAargang = params.prosjektAargang
		}
		else { 
			/* Tre muligheter for å komme hit:
			 *   1 Vi har klikka lenka for Prosjekt i hovedmenyen (initiering av ny liste)
			 *   2 Vi har klikka en knapp for blaing i Paginate
			 *   3 Vi kommer tilbake hit fra def update/edit
			 */
			if (params.offset || params.sort) {  // Vi har bladd i paginate
				if (params.offset) {
					session.offset = params.int('offset')
				}
				if (params.sort) {
					session.sortering = params.sort
					session.order = params.order
				}
			}
			else { 
				// To muligheter her: Vi ønsker å initiere en hel liste (poffs = 0)
				// eller vi kommer hit fra def update/edit (poffs = session.offset)
				// I det siste tilfellet skal alle lagra parametre beholdes.
				if (! session.fraUpdate) {  // Initiering av en hel liste
					session.inSearch = 0
					session.offset   = 0
					session.produktNummer   = null
					session.prosjektNavn    = null
					session.prosjektStatus  = null
					session.prosjektAargang = null
					session.sortering = "prosjektNavn"
					session.order = "asc"
				}
			}
		}

		if (session.inSearch) {  // Vi arbeider med å vise resultatet av et søk
			params.produktNummer   = session.produktNummer
			params.prosjektNavn    = session.prosjektNavn
			params.prosjektStatus  = session.prosjektStatus
			params.prosjektAargang = session.prosjektAargang
		}
		
		if (request.post || session.inSearch) {
			// Nytt søk eller traversering av gammelt. Vi er uansett i et søk
			def c = Prosjekt.createCriteria()
			prosjektInstanceList = c {
				if(params.produktNummer) {
					eq("produktNummer", params.produktNummer)
				}
				if(params.prosjektNavn) {
					ilike("prosjektNavn", ("%" + params.prosjektNavn + "%"))
				}
				if(params.prosjektStatus?.size() > 0) {
					eq("prosjektStatus", ProsjektStatus.valueOf(params.prosjektStatus))
				}
				if(params.prosjektAargang?.size() == 4 && params.prosjektAargang.isInteger()) {
					eq("aargang", params.prosjektAargang)
				}
				order(session.sortering, session.order)
			}
			prosjektInstanceTotal = prosjektInstanceList.size()
			
			if(params.prosjektStatus?.size() > 0) {
				prosjektStatusDefault = ProsjektStatus.valueOf(params.prosjektStatus )
			}
		}
		else {
			prosjektInstanceList = Prosjekt.createCriteria().list {
				'eq'("prosjektStatus", ProsjektStatus.AKTIV)
				order(session.sortering, session.order)
			}
			prosjektInstanceTotal = prosjektInstanceList?.size()
			prosjektStatusDefault = ProsjektStatus.AKTIV
		}

		poffs = session.offset
		params.offset = poffs
		pmax = Math.min(params.int('max'), prosjektInstanceTotal-poffs)  // Vi kan ikke vise records utafor arrayet
		session.fraUpdate = 0
		params.sort = session.sortering
		params.order = session.order

        render (
			view: "list", 
			model: [
				prosjektInstanceList: prosjektInstanceList.subList(poffs, poffs+pmax), 
				prosjektInstanceTotal: prosjektInstanceTotal,
				prosjektStatusList: ProsjektStatus.values(),
				prosjektStatusDefault: prosjektStatusDefault,
				produktNummer: params.produktNummer,
				prosjektNavn: params.prosjektNavn,
				prosjektAargang: params.prosjektAargang
				]
			)
			
		return
    }

    def create = {
        def prosjektInstance = new Prosjekt()
        prosjektInstance.properties = params
        return [prosjektInstance: prosjektInstance]
    }

    def save = {
        def prosjektInstance = new Prosjekt(params)
        if (prosjektInstance.save(flush: true)) {
			flash.message = "${message(code: 'sivadm.prosjekt.opprettet', args: [prosjektInstance.prosjektNavn, prosjektInstance.id])}"
            redirect(action: "edit", id: prosjektInstance.id)
        }
        else {
            render(view: "create", model: [prosjektInstance: prosjektInstance])
        }
    }

    def edit = {
        def prosjektInstance = Prosjekt.get(params.id)
		session.fraUpdate = 1
        if (!prosjektInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [prosjektInstance: prosjektInstance]
        }
    }

    def update = {
        def prosjektInstance = Prosjekt.get(params.id)
        if (prosjektInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (prosjektInstance.version > version) {
                    
                    prosjektInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'prosjekt.label', default: 'Prosjekt')] as Object[], "Another user has updated this Prosjekt while you were editing")
                    render(view: "edit", model: [prosjektInstance: prosjektInstance])
                    return
                }
            }
            prosjektInstance.properties = params
			
			if( prosjektInstance.isDirty('produktNummer')) {
				flash.errorMessage = "${message(code: 'produktNummer.endring.advarsel')}"
			}
			
            if (!prosjektInstance.hasErrors() && prosjektInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), prosjektInstance.prosjektNavn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [prosjektInstance: prosjektInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def prosjektInstance = Prosjekt.get(params.id)
		
		def skjemaList = Skjema.findAllByProsjekt( prosjektInstance )
		
		// sletter utvalg ogsaa
		skjemaList.each { Skjema skjema ->
			def utvalgImportList = UtvalgImport.findAllBySkjema(skjema)
			
			utvalgImportList.each { UtvalgImport utvalgImport ->
				utvalgImportService.deleteUtvalg(utvalgImport)
			}
		}
		
        if (prosjektInstance) {
            try {
                prosjektInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'prosjekt.label', default: 'Prosjekt'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def ajaxGetProsjekt = {
		def prosjekt = Prosjekt.get(params.id)
		
		if(prosjekt) {
			render prosjekt as JSON
		}
	}
}
