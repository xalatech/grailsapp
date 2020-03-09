package sivadm

import grails.plugin.springsecurity.annotation.Secured
import siv.type.IntervjuerStatus
import siv.util.SorteringsUtil

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class CatiGruppeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def catiGruppeService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		session.removeAttribute 'catiTildelKlynge'
		
        [catiGruppeInstanceList: CatiGruppe.list(params), catiGruppeInstanceTotal: CatiGruppe.count()]
    }

    def create = {
        def catiGruppeInstance = new CatiGruppe()
        catiGruppeInstance.properties = params
        return [catiGruppeInstance: catiGruppeInstance]
    }

    def save = {
        def catiGruppeInstance = new CatiGruppe(params)
        if (catiGruppeInstance.save(flush: true)) {
            flash.message = "Velg skjema og hvilke intervjuere som skal være med"
            redirect(action: "edit", id: catiGruppeInstance.id)
        }
        else {
            render(view: "create", model: [catiGruppeInstance: catiGruppeInstance])
        }
    }

    def edit = {
        def catiGruppeInstance = CatiGruppe.get(params.id)
        if (!catiGruppeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), params.id])}"
            redirect(action: "list")
        }
        else {
			def klynger = Klynge.list()
			def ikkeValgteIntervjuere = (Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV) - catiGruppeInstance?.intervjuere).sort{ it.initialer}	
			def valgteIntervjuere = catiGruppeInstance?.intervjuere.sort{it.initialer}
            
			if(params.klynge && params.klynge == 'alle') {
				session.removeAttribute 'catiTildelKlynge'
			}
			else if(params.klynge) {
				session.catiTildelKlynge = params.klynge
			}
			
			if(session.catiTildelKlynge) {
				def klynge = Klynge.get(session.catiTildelKlynge)
				ikkeValgteIntervjuere = Intervjuer.findAllByStatusAndKlynge(IntervjuerStatus.AKTIV, klynge)
				
				ikkeValgteIntervjuere = (ikkeValgteIntervjuere - catiGruppeInstance?.intervjuere).sort{ it.initialer}
			}
			
			def sortertSkjemaList = SorteringsUtil.sorterPaaOppstartsDatoSkjemaSomIkkeErAvsluttet(sivadm.Skjema.list())
			
			return [
				catiGruppeInstance: catiGruppeInstance,
				sortertSkjemaList: sortertSkjemaList,
				ikkeValgteIntervjuere: ikkeValgteIntervjuere, 
				valgteIntervjuere: valgteIntervjuere,
				klynger: klynger,
				klynge: session.catiTildelKlynge]
        }
    }
	
    def update = {
        def catiGruppeInstance = CatiGruppe.get(params.id)
        if (catiGruppeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (catiGruppeInstance.version > version) {
                    
                    catiGruppeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'catiGruppe.label', default: 'CatiGruppe')] as Object[], "Another user has updated this CatiGruppe while you were editing")
                    render(view: "edit", model: [catiGruppeInstance: catiGruppeInstance])
                    return
                }
            }
            catiGruppeInstance.properties = params
            if (!catiGruppeInstance.hasErrors() && catiGruppeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), catiGruppeInstance.navn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [catiGruppeInstance: catiGruppeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def catiGruppeInstance = CatiGruppe.get(params.id)
        if (catiGruppeInstance) {
            try {
                catiGruppeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'catiGruppe.label', default: 'CatiGruppe'), params.id])}"
            redirect(action: "list")
        }
    }
	
	
	def addIntervjuer = {
		def catiGruppeInstance = CatiGruppe.get(params.id)
		def intervjuer = params.intervjuer
		
		if(!intervjuer)
		{
			flash.errorMessage = "Velg intervjuobjekt fra listen som du vil legge til Cati-gruppen."
			redirect(action: "edit", id: params.id)
		}
		
		catiGruppeInstance.addToIntervjuere(Intervjuer.get(intervjuer))
		
		catiGruppeInstance.properties = params
		
		redirect(action: "edit", id: params.id)
	}

	def addAllIntervjuere = {
		def catiGruppeInstance = CatiGruppe.get(params.id)
		
		def intervjuereSomSkalLeggesTil = Intervjuer.list()
		
		if(session.catiTildelKlynge) {
			def klynge = Klynge.get(session.catiTildelKlynge)
			intervjuereSomSkalLeggesTil = intervjuereSomSkalLeggesTil.findAll { it.klynge == klynge}
		}
		
		catiGruppeInstance.intervjuere += intervjuereSomSkalLeggesTil
		catiGruppeInstance.properties = params
		redirect(action: "edit", id: params.id)
	}

	def removeIntervjuer = {
		def catiGruppeInstance = CatiGruppe.get(params.id)
		def intervjuer = params.valgtIntervjuer
		
		if(!intervjuer)
		{
			flash.errorMessage = "Velg intervjuobjekt fra listen som du vil fjerne fra Cati-gruppen."
			redirect(action: "edit", id: params.id)
		}
		
		catiGruppeInstance.removeFromIntervjuere(Intervjuer.get(intervjuer))
		catiGruppeInstance.properties = params
		redirect(action: "edit", id: params.id)
	}

	def removeAllIntervjuer = {
		def catiGruppeInstance = CatiGruppe.get(params.id)
		catiGruppeInstance.intervjuere = new ArrayList()
		catiGruppeInstance.properties = params
		redirect(action: "edit", id: params.id)
	}
}
