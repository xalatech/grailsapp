package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ScriptController {
	def scriptService
	def intervjuerService
	
	def index = {
		
	}
	
	def slettAlleKravOgTimeforinger = {
		scriptService.slettAlleKravOgTimeforinger()	
		render("Alt slettet ok")
	}
	
	
}
