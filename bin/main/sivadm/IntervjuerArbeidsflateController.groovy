package sivadm

import siv.data.UtvalgtCapiIntervjuObjekt;
import siv.data.UtvalgtCapiSkjema;
import grails.core.GrailsApplication

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUER'])
class IntervjuerArbeidsflateController {
	
	def intervjuerService
	def brukerService
	def systemKommandoService
	GrailsApplication grailsApplication

	def index = {
	}
	
	// list.gsp is programmed to be dynamic with applet start or not. This is controlled by request params.
	
	// Request flow for applet params: list.form -> startIntervju -> redirect -> list
	
	def list = {
		
		String intervjuerInitialer = brukerService.getCurrentUserName()
		
		def utvalgtSkjemaList = intervjuerInitialer ? intervjuerService.getUtvalgteSkjemaForIntervjuer(intervjuerInitialer) : []
		utvalgtSkjemaList.sort { it.skjemaKortNavn.toUpperCase() }

		def blaiseApplicationPath = grailsApplication.config.getProperty("blaiseApplicationPath")
		def blaiseSkjemaPath = grailsApplication.config.getProperty("blaiseSkjemaPath")
		
		[
			utvalgtSkjemaInstanceList: utvalgtSkjemaList, 
			utvalgtSkjemaInstanceTotal: utvalgtSkjemaList.size(), 
			startIntervju: params.startIntervju,
			blaiseApplicationPath: blaiseApplicationPath,
			blaiseSkjemaPath: blaiseSkjemaPath,
			skjemaKortNavn: params.skjemaKortNavn,
			skjemaVersjon: params.skjemaVersjon,
			appletModus: params.appletModus
		]
	}
	
	def startIntervju = {
		flash.message = "Du starter nå å intervju på skjema: " + params.skjemaKortNavn
		
		def startIntervju = "true"
		def blaiseApplicationPath = grailsApplication.config.getProperty("blaiseApplicationPath")
		def blaiseSkjemaPath = grailsApplication.config.getProperty("blaiseSkjemaPath")
		def skjemaKortNavn = params.skjemaKortNavn
		def skjemaVersjon = params.skjemaVersjon
		def appletModus = params.appletModus // BLAISE, BLAISE_TEST eller RETNINGSLINJER
		
		flash.start = "true"
		
		redirect( action:"list", params:[appletModus: appletModus, startIntervju: startIntervju, blaiseSkjemaPath:blaiseSkjemaPath, blaiseApplicationPath: blaiseApplicationPath, skjemaKortNavn: skjemaKortNavn, skjemaVersjon: skjemaVersjon] )
	}
	
	
	def listCapi = {
		String intervjuerInitialer = brukerService.getCurrentUserName()
		
		def blaiseApplicationPath = grailsApplication.config.getProperty("blaiseApplicationPath")
		def blaiseCapiSkjemaPath = grailsApplication.config.getProperty("blaiseCapiSkjemaPath")
		def localSkjemaPath = grailsApplication.config.getProperty("localSkjemaPath")
		
		def localSystemKommandoPath = grailsApplication.config.getProperty("localSystemKommandoPath")
		def serverSystemKommandoPath = grailsApplication.config.getProperty("serverSystemKommandoPath")
		def serverSystemKommandoHttpPath = grailsApplication.config.getProperty("serverSystemKommandoHttpPath")
		
		List<UtvalgtCapiSkjema> utvalgtCapiSkjemaInstanceList = intervjuerService.getUtvalgteCapiSkjemaForIntervjuer(intervjuerInitialer)
		
		String intervjuerCapiSkjemaVersionList = intervjuerService.getIntervjuerCapiSkjemaVersionList(utvalgtCapiSkjemaInstanceList)
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(intervjuerInitialer)
		
		String systemKommandoList = null
		
		if(intervjuer) {
			systemKommandoList = systemKommandoService.hentAlleSystemKommandoerSomAppletString(intervjuer)
		}
		
		[
			utvalgtCapiSkjemaInstanceList:utvalgtCapiSkjemaInstanceList,
			blaiseApplicationPath: blaiseApplicationPath,
			blaiseCapiSkjemaPath: blaiseCapiSkjemaPath,
			localSkjemaPath: localSkjemaPath,
			intervjuerCapiSkjemaVersionList: intervjuerCapiSkjemaVersionList,
			localSystemKommandoPath: localSystemKommandoPath,
			serverSystemKommandoPath: serverSystemKommandoPath,
			systemKommandoList: systemKommandoList,
			intervjuerInitialer: intervjuerInitialer,
			serverSystemKommandoHttpPath: serverSystemKommandoHttpPath
		]
	}
	
	def listCapiIntervjuObjekter = {
		String intervjuerInitialer = brukerService.getCurrentUserName()
		
		Skjema skjema = Skjema.get( params.id)
		
		List<UtvalgtCapiIntervjuObjekt> utvalgtCapiIntervjuObjektInstanceList = intervjuerService.getUtvalgteCapiIntervjuObjekter(intervjuerInitialer, skjema)
		
		[utvalgtCapiIntervjuObjektInstanceList:utvalgtCapiIntervjuObjektInstanceList]	
	}
	
}
