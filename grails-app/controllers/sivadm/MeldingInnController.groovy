package sivadm

import grails.plugin.springsecurity.annotation.Secured
import siv.search.MeldingSok

@Secured(['ROLE_ADMIN'])
class MeldingInnController {
    static scaffold = MeldingInn
	
	def blaiseMeldingInnService
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def nullstillSok = {
		session.meldingInnSok = null
		redirect(action: "list")
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
		
		def meldingInnSok
				
		if(request.post) {
			meldingInnSok = new MeldingSok()
			bindData(meldingInnSok, params)
			session.meldingInnSok = meldingInnSok
		}
		else {
			if(session.meldingInnSok) {
				meldingInnSok = session.meldingInnSok
			}
			else {
				meldingInnSok = new MeldingSok()
			}
		}
			
		if(params.fraIo) {
			// Kommer fra link i IO-skjema vis bare meldinger tilhørende
			// IO med ioId angitt i params
			meldingInnSok = new MeldingSok()
			meldingInnSok.fra = null
			meldingInnSok.til = null
			meldingInnSok.ioId = Long.parseLong(params.ioId)
		}
		
		def meldingInnListe = blaiseMeldingInnService.sokMeldingInn(meldingInnSok, params)
		def meldingInnTotal = blaiseMeldingInnService.tellSokMeldingInn(meldingInnSok)
		
		[
			meldingInnListe: meldingInnListe,
			meldingInnTotal: meldingInnTotal,
			meldingInnSok: meldingInnSok
		]
	}
	
	def slettMeldinger = {
		def meldingInnSok
		
		if(session.meldingInnSok) {
			meldingInnSok = session.meldingInnSok
		}
		else {
			meldingInnSok = new MeldingSok()
		}
		
		def meldingInnListe = blaiseMeldingInnService.sokMeldingInnAlle(meldingInnSok)
			
		int cnt = 0
		
		meldingInnListe.each {
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
}