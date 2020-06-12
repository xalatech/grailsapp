package sivadm

import grails.plugin.springsecurity.annotation.Secured
import util.MailMessageFactory

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUER'])
class IntervjuerAdministrasjonController {

	def brukerService
	
	def list = { 
		
	}

	def egenmelding = {
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		[intervjuerInstance: Intervjuer.findByInitialer(brukerService.getCurrentUserName())]
	}

	def egenmeldingSave = {
		EgenmeldingCommand egenmeldingCommand = new EgenmeldingCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())

		if(!egenmeldingCommand.validate() ) {
			render(view: "egenmelding", model: [intervjuerInstance: intervjuer, egenmeldingCommand: egenmeldingCommand])
			return
		}
		
		def aarsak

		if(params.aarsak == "egenSykdom") {
			aarsak = "Egen sykdom"
		}
		else {
			aarsak = "Egen sykdom som antas å ha sin grunn i arbeidet."
		}

		Boolean ikkeRettPaaSykepenger = params.ikkeRettPaaSykepenger ? true : false

		def mottaker = grailsApplication.config.getProperty("admin.mail.adresse")
		def avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
		def ccStr = intervjuer?.epostJobb
		def replyToStr = intervjuer?.epostJobb
		def subjectStr = "Egenmelding fra intervjuer " + intervjuer?.navn + " (" + intervjuer?.initialer + ")"
		def mailBody = MailMessageFactory.lagSykdomMelding (intervjuer, egenmeldingCommand.fraDato, egenmeldingCommand.tilDato, egenmeldingCommand.merknad, aarsak, ikkeRettPaaSykepenger)

		try {
			sendMail {
				to mottaker
				from avsender
				cc ccStr
				replyTo replyToStr
				subject subjectStr
				body mailBody
			}
			flash.message = "Egenmeldingen din er registrert og sendt til administrasjonen."
		}
		catch (Exception e) {
			log.error ("Noe gikk feil under under innsending av egenmelding fra intervjuer.")
			log.error (e.getMessage())
			flash.errorMessage = "Noe gikk feil under innsending av egemelding. Ta kontakt med administrasjonen."
		}

		redirect( action:"list")
	}

	def pcTelefon = {
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if(!intervjuer) {
			redirect(controller: "login", action: "auth")
			return
		}
		
		[intervjuerInstance: intervjuer]
	}

	def pcTelefonSave = {
		PcTelefonCommand pcTelefonCommand = new PcTelefonCommand()

		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())

		if(!intervjuer) {
			redirect(controller: "login", action: "auth")
			return
		}
		
		if( !pcTelefonCommand.validate() ) {
			render(view: "pcTelefon", model: [intervjuerInstance: intervjuer, pcTelefonCommand: pcTelefonCommand])
			return
		}
		
		Calendar cal = Calendar.getInstance()
		cal.setTime pcTelefonCommand.fraDato
		cal.set Calendar.SECOND, 0
		cal.set Calendar.HOUR_OF_DAY, Integer.valueOf(pcTelefonCommand.fraKlokkeslett.substring(0,2)).intValue() 
		cal.set Calendar.MINUTE, Integer.valueOf(pcTelefonCommand.fraKlokkeslett.substring(3)).intValue()
		Date fra = cal.getTime() 
		
		cal.setTime pcTelefonCommand.tilDato
		cal.set Calendar.HOUR_OF_DAY, Integer.valueOf(pcTelefonCommand.tilKlokkeslett.substring(0,2)).intValue()
		cal.set Calendar.MINUTE, Integer.valueOf(pcTelefonCommand.tilKlokkeslett.substring(3)).intValue()
		Date til = cal.getTime()

		def mottaker = grailsApplication.config.getProperty("admin.mail.adresse")
		def avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
		def ccStr = intervjuer?.epostJobb
		def replyToStr = intervjuer?.epostJobb
		def subjectStr = "Rapport om teknisk problem fra intervjuer " + intervjuer?.navn + " (" + intervjuer?.initialer + ")"
		def mailBody = MailMessageFactory.lagPCProblemMelding(intervjuer, fra, til, pcTelefonCommand.merknad)

		try {
			sendMail {
				to mottaker
				from avsender
				cc ccStr
				replyTo replyToStr
				subject subjectStr
				body mailBody
			}

			flash.message = "Din rapport om teknisk problem er registrert og sendt til administrasjonen."
		}
		catch (Exception e) {
			log.error ("Noe gikk feil under under rapportering av teknisk problem fra intervjuer.")
			log.error (e.getMessage())
			flash.errorMessage = "Noe gikk feil under rapportering av teknisk problem. Ta kontakt med administrasjonen."
		}

		redirect( action:"list")
	}

	def permisjon = {
		[intervjuerInstance: Intervjuer.findByInitialer(brukerService.getCurrentUserName())]
	}

	def permisjonSave = {
		PermisjonCommand permisjonCommand = new PermisjonCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if(!permisjonCommand.validate() ) {
			render(view: "permisjon", model: [intervjuerInstance: intervjuer, permisjonCommand: permisjonCommand])
			return
		}

		def mottaker = grailsApplication.config.getProperty("admin.mail.adresse")
		def avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
		def ccStr = intervjuer?.epostJobb
		def replyToStr = intervjuer?.epostJobb
		def subjectStr = "Søknad om permisjon fra intervjuer " + intervjuer?.navn + " (" + intervjuer?.initialer + ")"
		def mailBody = MailMessageFactory.lagPermisjonSoknadMelding (intervjuer, permisjonCommand.permisjonType, permisjonCommand.fraDato, permisjonCommand.tilDato, permisjonCommand.merknad)

		try {
			sendMail {
				to mottaker
				from avsender
				cc ccStr
				replyTo replyToStr
				subject subjectStr
				body mailBody
			}

			flash.message = "Din søknad om permisjon er registrert og sendt til administrasjonen."
		}
		catch (Exception e) {
			log.error ("Noe gikk feil under under innsending av permisjonsoknad fra intervjuer.")
			log.error (e.getMessage())
			flash.errorMessage = "Noe gikk feil under innsending av permisjonsoknad. Ta kontakt med administrasjonen."
		}

		redirect( action:"list")
	}

	def permisjonBarn = {
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		[intervjuerInstance: Intervjuer.findByInitialer(brukerService.getCurrentUserName())]
	}

	def permisjonBarnSave = {
		PermisjonBarnCommand permisjonCommand = new PermisjonBarnCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())

		if(!permisjonCommand.validate() ) {
			render(view: "permisjonBarn", model: [intervjuerInstance: intervjuer, permisjonCommand: permisjonCommand])
			return
		}
		
		Boolean aleneMedOmsorg = params.alene ? true : false

		def mottaker = grailsApplication.config.getProperty("admin.mail.adresse")
		def avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
		def ccStr = intervjuer?.epostJobb
		def replyToStr = intervjuer?.epostJobb
		def subjectStr = "Egenmelding angående syke barn fra intervjuer " + intervjuer?.navn + " (" + intervjuer?.initialer + ")"
		def mailBody = MailMessageFactory.lagPermisjonBarnSoknadMelding (intervjuer, permisjonCommand.fraDato, permisjonCommand.tilDato, permisjonCommand.merknad, aleneMedOmsorg, permisjonCommand.antallBarn, permisjonCommand.fodselsdato)

		try {
			sendMail {
				to mottaker
				from avsender
				cc ccStr
				replyTo replyToStr
				subject subjectStr
				body mailBody
			}

			flash.message = "Din egenmelding angående hjemme med syke barn er registrert og sendt til administrasjonen."
		}
		catch (Exception e) {
			log.error ("Noe gikk feil under under innsending av egenmelding ang. syke barn fra intervjuer.")
			log.error (e.getMessage())
			flash.errorMessage = "Noe gikk feil under innsending av egenmelding. Ta kontakt med administrasjonen."
		}

		redirect( action:"list")
	}

	def ferie = {
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		[intervjuerInstance: Intervjuer.findByInitialer(brukerService.getCurrentUserName())]
	}

	def ferieSave = {
		FerieCommand ferieCommand = new FerieCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if(!ferieCommand.validate() ) {
			render(view: "ferie", model: [intervjuerInstance: intervjuer, ferieCommand: ferieCommand])
			return
		}

		def mottaker = grailsApplication.config.getProperty("admin.mail.adresse")
		def avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
		def ccStr = intervjuer?.epostJobb
		def replyToStr = intervjuer?.epostJobb
		def subjectStr = "Søknad om ferie fra intervjuer " + intervjuer?.navn + " (" + intervjuer?.initialer + ")"
		def mailBody = MailMessageFactory.lagFerieSoknadMelding (intervjuer, ferieCommand.fraDato, ferieCommand.tilDato, ferieCommand.merknad)

		try {
			sendMail {
				to mottaker
				from avsender
				cc ccStr
				replyTo replyToStr
				subject subjectStr
				body mailBody
			}

			flash.message = "Ferieønsket ditt er registrert og sendt til administrasjonen."
		}
		catch (Exception e) {
			log.error ("Noe gikk feil under under innsending av ferieonske fra intervjuer.")
			log.error (e.getMessage())
			flash.errorMessage = "Noe gikk feil under innsending av ferieønske. Ta kontakt med administrasjonen."
		}

		redirect( action:"list")
	}
	
	def avbryt = {
		redirect( action: "list" )
	}
}


class FerieCommand implements grails.validation.Validateable{
	Date fraDato
	Date tilDato
	String merknad
	
	static constraints = {
		fraDato(nullable: false)
		tilDato(nullable: false)
		merknad(blank: false)
	}
}

class EgenmeldingCommand implements grails.validation.Validateable{
	Date fraDato
	Date tilDato
	Boolean egenSykdom
	Boolean egenSykdomJobb
	boolean ikkeRettPaaSykepenger
	String merknad
	
	static constraints = {
		fraDato(nullable: false)
		tilDato(nullable: false)
		merknad(blank: false)
	}
}

class PermisjonCommand implements grails.validation.Validateable{
	int permisjonType
	Date fraDato
	Date tilDato
	String merknad
	
	static constraints = {
		merknad(blank: false, nullable: false)
		fraDato validator: { value, command ->
			def melding
			if(!value && command.tilDato) {
				melding = "permisjon.med.lonn.dato.feil"
			}
			return melding
		}
		tilDato validator: { value, command ->
			def melding
			if(!value && command.fraDato) {
				melding = "permisjon.med.lonn.dato.feil"
			}
			return melding
		}
	}
}

class PermisjonBarnCommand implements grails.validation.Validateable{
	Date fraDato
	Date tilDato
	Boolean alene
	Integer antallBarn
	Date fodselsdato
	String merknad
	
	static constraints = {
		merknad(blank: false, nullable: false)
		fraDato(nullable: false)
		tilDato(nullable: false)
		antallBarn(nullable: false)
		fodselsdato(nullable: false)		
	}
}

class PcTelefonCommand implements grails.validation.Validateable{
	Date fraDato
	Date tilDato
	String fraKlokkeslett
	String tilKlokkeslett
	String merknad
	
	static constraints = {
		fraDato(nullable: false)
		tilDato(nullable: false)
		merknad(blank: false, nullable: false)
		fraKlokkeslett nullable: false, blank: false, validator: { value ->
			def melding
			if(value) {
				if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "fra.tidspunkt.feil.format"
				}
			}
			return melding
		}
		tilKlokkeslett nullable: false, blank: false, validator: { value ->
			def melding
			if(value) {
				if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "til.tidspunkt.feil.format"
				}
			}
			return melding
		}
	}
}