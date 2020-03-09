package sivadm

import grails.plugin.springsecurity.annotation.Secured
import util.TimeUtil
import siv.search.MeldingSok

@Secured(['ROLE_ADMIN'])
class MeldingUtController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def synkroniseringService

    def index = {
        redirect(action: "list", params: params)
    }
	
	def nullstillSok = {
		session.meldingUtSok = null
		redirect(action: "list")
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		
		def meldingUtSok
				
		if(request.post) {
			meldingUtSok = new MeldingSok()
			bindData(meldingUtSok, params)
			session.meldingUtSok = meldingUtSok
		}
		else {
			if(session.meldingUtSok) {
				meldingUtSok = session.meldingUtSok
			}
			else {
				meldingUtSok = new MeldingSok()
			}
		}
			
		if(params.fraIo) {
			// Kommer fra link i IO-skjema vis bare meldinger tilhørende
			// IO med ioId angitt i params
			meldingUtSok = new MeldingSok()
			meldingUtSok.fra = null
			meldingUtSok.til = null
			meldingUtSok.ioId = Long.parseLong(params.ioId)
		}
		
		def meldingUtListe = synkroniseringService.sokMeldingUt(meldingUtSok, params)
		def meldingUtTotal = synkroniseringService.tellSokMeldingUt(meldingUtSok)
		
		[
			meldingUtListe: meldingUtListe,
			meldingUtTotal: meldingUtTotal,
			meldingUtSok: meldingUtSok
		]
	}

    def create = {
        def meldingUtInstance = new MeldingUt()
        meldingUtInstance.properties = params
        return [meldingUtInstance: meldingUtInstance]
    }

    def save = {
        def meldingUtInstance = new MeldingUt(params)
        if (meldingUtInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), meldingUtInstance.id])}"
            redirect(action: "show", id: meldingUtInstance.id)
        }
        else {
            render(view: "create", model: [meldingUtInstance: meldingUtInstance])
        }
    }

    def show = {
        def meldingUtInstance = MeldingUt.get(params.id)
        if (!meldingUtInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
            redirect(action: "list")
        }
        else {
            [meldingUtInstance: meldingUtInstance]
        }
    }

    def edit = {
        def meldingUtInstance = MeldingUt.get(params.id)
        if (!meldingUtInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [meldingUtInstance: meldingUtInstance]
        }
    }

    def update = {
        def meldingUtInstance = MeldingUt.get(params.id)
        if (meldingUtInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (meldingUtInstance.version > version) {
                    
                    meldingUtInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'meldingUt.label', default: 'MeldingUt')] as Object[], "Another user has updated this MeldingUt while you were editing")
                    render(view: "edit", model: [meldingUtInstance: meldingUtInstance])
                    return
                }
            }
            meldingUtInstance.properties = params
            if (!meldingUtInstance.hasErrors() && meldingUtInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), meldingUtInstance.id])}"
                redirect(action: "show", id: meldingUtInstance.id)
            }
            else {
                render(view: "edit", model: [meldingUtInstance: meldingUtInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def meldingUtInstance = MeldingUt.get(params.id)
        if (meldingUtInstance) {
            try {
                meldingUtInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingUt.label', default: 'MeldingUt'), params.id])}"
			redirect(action: "list")
        }
    }
	
	def slettMeldinger = {
		def meldingUtSok
		
		if(session.meldingUtSok) {
			meldingUtSok = session.meldingUtSok
		}
		else {
			meldingUtSok = new MeldingSok()
		}
		
		def meldingUtListe = synkroniseringService.sokMeldingUtAlle(meldingUtSok)
			
		int cnt = 0
		
		meldingUtListe.each {
			try {
				it.delete(flush: true)
				cnt++
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				
			}
		}
		
		flash.message = "${message(code: 'sivadm.meldinger.slett.info', args: [cnt] , default: 'Har slettet meldinger')}"
		redirect(action: "list")
	}
			
	def sendPaaNytt = {
		MeldingUt meldingUt = MeldingUt.get( params.id )
		
		if(meldingUt) {
			meldingUt.antallForsok = 0
			meldingUt.feilType = ""
			meldingUt.responseText = ""
			meldingUt.save()
		}
		
		redirect(action: "list")
	}
}