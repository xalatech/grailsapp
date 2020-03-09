package sil

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_SIL'])
class KostGodtgjorelseController {
	
	def kostGodtgjorelseService
	
    def list = { 
		def kostGodtgjorelser = kostGodtgjorelseService.hentKostGodtgjorelser(params.max, params.offset)
		
		int totaltAntallKostGodtgjorelser = kostGodtgjorelseService.hentAntallKostGodtgjorelser()
			
		return [
			kostGodtgjorelser:kostGodtgjorelser,
			totaltAntallKostGodtgjorelser:totaltAntallKostGodtgjorelser
		]
	}
	
	def behandle = {
		def kostGodtGjorelse = KostGodtgjorelse.get(params.id)
		kostGodtGjorelse.behandlet = true
		kostGodtGjorelse.save()
		redirect(action: "list")
	}
	
	def ikkeBehandle = {
		def kostGodtGjorelse = KostGodtgjorelse.get(params.id)
		kostGodtGjorelse.behandlet = false
		kostGodtGjorelse.save()
		redirect(action: "list")
	}
}
