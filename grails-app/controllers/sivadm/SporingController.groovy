package sivadm

import util.DateUtil;

class SporingController {
	
	SporingService sporingService
	def brukerService

	def create = {
		def sporingInstance = new Sporing()
		sporingInstance.properties = params
		
		def intervjuObjekt = IntervjuObjekt.get(params.id)
		
		def sporingInstanceList = sporingService.hentSporingsListeForIntervjuObjekt( intervjuObjekt )
		
		[
			sporingInstance: sporingInstance,
			intervjuObjektInstance: intervjuObjekt,
			sporingInstanceList: sporingInstanceList
		]
	}
	
	def save = {
		def sporingInstance = new Sporing(params)
		
		sporingInstance.intervjuObjekt = IntervjuObjekt.get(params.intervjuObjektId)
		sporingInstance.redigertAv = brukerService.getCurrentUserName()
		sporingInstance.redigertDato = DateUtil.now()
		sporingInstance.tidspunkt = DateUtil.now()
		
		if(sporingInstance.save(flush: true)) {
			redirect(action: "create", id: params.intervjuObjektId)
		}
		else {
			def sporingInstanceList = sporingService.hentSporingsListeForIntervjuObjekt(sporingInstance.intervjuObjekt)
			render(view: "create", model: [sporingInstance: sporingInstance, intervjuObjektInstance: sporingInstance.intervjuObjekt, sporingInstanceList: sporingInstanceList])
		}
	}
	
	def delete = {
		def sporingInstance = Sporing.get(params.id)
		
		if(sporingInstance) {
			try {
			    sporingInstance.delete(flush: true)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
			    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sporing.label', default: 'Sporing'), params.id])}"
			}
			redirect(action: "create", id: params.ioId)
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sporing.label', default: 'Sporing'), params.id])}"
			redirect( controller: "intervjuObjekt", action: "edit", id: params.ioId)
		}
	}
	
	def avbryt = {
		redirect( controller: "intervjuObjekt", action: "edit", id: params.intervjuObjektId)
	}
}
