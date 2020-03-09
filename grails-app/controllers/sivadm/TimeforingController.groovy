package sivadm

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import util.DateUtil
import sil.Krav
import siv.data.ProduktKode;
import siv.type.*
import grails.core.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUER', 'ROLE_SIL'])
class TimeforingController {
	def config = ConfigurationHolder.config
	TimeforingService timeforingService
	def brukerService
	
	def index = {
        redirect(action: "velgDato", params: params)
    }

	def listTotal = { 
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if( intervjuer == null ) {
			redirect ( controller: "login", action: "login")
		}
		
		if(params.valgtDato) {		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd")
			Date d = sdf.parse( params.valgtDato)
			session.timeforingDato = d
		}
		
		def valgtDato = session.timeforingDato
		
		if(!valgtDato) {
			redirect( action: "velgDato")
		}
		
		def timeforingInstanceList = timeforingService.getTimeforingerForIntervjuer(intervjuer, valgtDato)
		
		def utleggInstanceList = timeforingService.getUtleggForIntervjuer(intervjuer, valgtDato)
		
		def kjorebokInstanceList = timeforingService.getKjorebokForIntervjuer (intervjuer, valgtDato)
		
		def sendtInn = timeforingService.isAllRegistreringerSendtInn (intervjuer, valgtDato)
		
		def alleGodkjent = timeforingService.isAllRegistreringerGodkjent (intervjuer, valgtDato)
		
		def registreringerFinnes = timeforingService.isRegistreringer (intervjuer, valgtDato)
		
		def erReturFraKontroll = timeforingService.isReturnDate(intervjuer, valgtDato)
		
		def belop = timeforingService.getTotalBelopForIntervjuer (intervjuer, valgtDato) 
		
		
		
		[
			timeforingInstanceList: timeforingInstanceList, 
			utleggInstanceList: utleggInstanceList, 
			kjorebokInstanceList: kjorebokInstanceList,
			intervjuerInstance: intervjuer,
			dato: valgtDato,
			antallTimer: timeforingService.getTotaltAntallTimerForIntervjuer(intervjuer, valgtDato),
			antallKilometer: timeforingService.getAntallKilometerForIntervjuer (intervjuer, valgtDato),
			belop: belop ? belop : 0,
			sendtInn: sendtInn,
			alleGodkjent: alleGodkjent,
			registreringerFinnes: registreringerFinnes,
			erReturFraKontroll: erReturFraKontroll
		]
	}

	def create = {
        def timeforingInstance = new Timeforing()
        timeforingInstance.properties = params
		
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		def valgtDato = session.timeforingDato
		
		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
		
		def timeforingInstanceList = timeforingService.getTimeforingerForIntervjuer(intervjuer, valgtDato)
		
		def arbeidsTypeListe = hentArbeidsTyper()
				 
		return [timeforingInstance: timeforingInstance, fraTid: "", tilTid: "", timeforingInstanceList: timeforingInstanceList, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe]
    }
    
	def save = {
		FraTilCommand fraTilCommand = new FraTilCommand()
		
		def timeforingInstance = new Timeforing(params)
		
		def fraTid = params.fraTid.trim()
		
		def tilTid = params.tilTid.trim()
		
		Date dato = session.timeforingDato
		
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		if(!intervjuer) {
			redirect(controller: "login", action: "auth")
		}
				
		if(!fraTilCommand.validate()) {
			def timeforingInstanceList = timeforingService.getTimeforingerForIntervjuer(intervjuer, dato)
			
			List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
			render(view: "create", model: [timeforingInstance: timeforingInstance, fraTid: fraTid, tilTid: tilTid, timeforingInstanceList: timeforingInstanceList, fraTilCommand: fraTilCommand, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe])
			return
		}
		
		timeforingInstance.fra = dato
		timeforingInstance.til = dato
		timeforingInstance.intervjuer = intervjuer
		timeforingInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
			
		// henter timer og minutter fra form, og setter paa fra-dato
		timeforingInstance.fra = DateUtil.getDateWithTime(timeforingInstance.fra, fraTid)
		
		// henter timer og minutter fra form, og setter paa til-dato
		timeforingInstance.til = DateUtil.getDateWithTime(timeforingInstance.til, tilTid)
		
		Date maksDato = DateUtil.getWithMinutesAdded(new Date(), 15)
		
		//Sjekk om tidspunkt er fram i tid
		if(timeforingInstance.til.after(maksDato)) {
			def timeforingInstanceList = timeforingService.getTimeforingerForIntervjuer(intervjuer, dato)
			List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
			flash.errorMessage = "${message(code: 'timeforing.til.max.exceeded')}"
			render(view: "create", model: [timeforingInstance: timeforingInstance, fraTid: fraTid, tilTid: tilTid, timeforingInstanceList: timeforingInstanceList, fraTilCommand: fraTilCommand, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe])
			return
		}
		
		if (timeforingInstance.save(flush: true)) {	
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), timeforingInstance.id])}"
			redirect(action: "create")
        }
        else {
			def timeforingInstanceList = timeforingService.getTimeforingerForIntervjuer(intervjuer, dato)
			List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
            render(view: "create", model: [timeforingInstance: timeforingInstance, fraTid: fraTid, tilTid: tilTid, timeforingInstanceList: timeforingInstanceList, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe])
        }
    }
    
	def edit = {
        
		def timeforingInstance = Timeforing.get(params.id)
		
		if (! timeforingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
            redirect(action: "listTotal")
			return
        }
		
		if (! brukerKanRedigereDenne(timeforingInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}
		
		def fraTid = DateUtil.getTimeOnDate(timeforingInstance.fra)
		
		def tilTid = DateUtil.getTimeOnDate(timeforingInstance.til)
        
		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
		
		def arbeidsTypeListe = hentArbeidsTyper()
        return [timeforingInstance: timeforingInstance, fraTid: fraTid, tilTid: tilTid, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe]
    }
    
	def update = {
		FraTilCommand fraTilCommand = new FraTilCommand()
		
		def timeforingInstance = Timeforing.get(params.id)
		Date dato = session.timeforingDato
		
		if (! timeforingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
            redirect(action: "listTotal")
			return
        }
		
		if (! brukerKanRedigereDenne(timeforingInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

        if (params.version) {
            def version = params.version.toLong()
           
			if (timeforingInstance.version > version) {          
                timeforingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timeforing.label', default: 'Timeforing')] as Object[], "Another user has updated this Timeforing while you were editing")
				List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
				def arbeidsTypeListe = hentArbeidsTyper()
				render(view: "edit", model: [timeforingInstance: timeforingInstance, produktNummerListe: prodListe, arbeidsTypeListe: arbeidsTypeListe])
                return
            }
        }
		
		timeforingInstance.properties = params
					
		def fraTid = params.fraTid.trim()
		def tilTid = params.tilTid.trim()
					
		if(!fraTilCommand.validate()) {
			List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
			render(view: "edit", model: [timeforingInstance: timeforingInstance, produktNummerListe: prodListe, fraTid: fraTid, tilTid: tilTid, fraTilCommand: fraTilCommand, arbeidsTypeListe: arbeidsTypeListe])
			return
		}
		
		timeforingInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
		
		// henter timer og minutter fra form, og setter paa fra-dato
		timeforingInstance.fra = DateUtil.getDateWithTime(timeforingInstance.fra, fraTid)
		
		// henter timer og minutter fra form, og setter paa til-dato
		timeforingInstance.til = DateUtil.getDateWithTime(timeforingInstance.til, tilTid)
		
		if(params.kravId) {
			timeforingInstance.timeforingStatus = TimeforingStatus.SENDT_INN
		}
		
		Date maksDato = DateUtil.getWithMinutesAdded(new Date(), 15)
		
		//Sjekk om tidspunkt er fram i tid
		if(timeforingInstance.til.after(maksDato)) {
			flash.errorMessage = "${message(code: 'timeforing.til.max.exceeded')}"
			List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
			render(view: "edit", model: [timeforingInstance: timeforingInstance, produktNummerListe: prodListe, fraTid: fraTid, tilTid: tilTid, arbeidsTypeListe: arbeidsTypeListe])
			return
		}
		
        if(!timeforingInstance.hasErrors() && timeforingInstance.save(flush: true)) {
            if(params.kravId) {
				def rParams = null
				if(params.isFailed == true) {
					rParams = [feiletId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}
				else {
					rParams = [kravId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}
				
				flash.message = "${message(code: 'sil.behandle.krav.time.endret', args: [timeforingInstance.id, params.kravId])}"
				redirect(controller: "krav", action: "behandleIntervjuerKrav", id: timeforingInstance.intervjuer?.id, params: rParams)
			}
			else {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), timeforingInstance.id])}"
				redirect(action: "listTotal")
			}
        }
        else {
			List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
			def arbeidsTypeListe = hentArbeidsTyper()
            render(view: "edit", model: [timeforingInstance: timeforingInstance, produktNummerListe: prodListe, fraTid: fraTid, tilTid: tilTid, arbeidsTypeListe: arbeidsTypeListe])
            }
    }
    
	def delete = {
		def timeforingInstance = Timeforing.get(params.id)
		
		if (! timeforingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
            redirect(action: "listTotal")
			return
        }
		
		if (! brukerKanRedigereDenne(timeforingInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

        List<Krav> kList = Krav.findAllByTimeforing(timeforingInstance)
		kList.each { krav ->
			krav.setTimeforing(null)
			krav.save(failOnError: true, flush: true)
		}
		
		try {
            timeforingInstance.delete(flush: true)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
            redirect(action: "listTotal")
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
            redirect(action: "listTotal")
        }
    }
		
	def avbryt = {
		def timeforingInstance = Timeforing.get(params.id)
		
		if(params.kravId) {
			def rParams = null
			if(params.isFailed == true) {
				rParams = [feiletId: params.kravId]
			}
			else {
				rParams = [kravId: params.kravId]
			}
			redirect(controller: "krav", action: "behandleIntervjuerKrav", id: timeforingInstance.intervjuer?.id, params: rParams)
		}
		else {
			redirect(action: "listTotal")
		}
	}
		
	def velgDato = {
		VelgDatoCommand command = new VelgDatoCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		if(!request.post) {
			// show the form
			Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
			def timeregistreringList = timeforingService.getTimeregistreringsList (intervjuer)
			def fraKontrollDatoListe = timeforingService.getDatesOpenedForEditing(intervjuer)
						
			return [timeregistreringList: timeregistreringList, fraKontrollDatoListe: fraKontrollDatoListe]
		}
		
		Date date = command.timeforingDato
		
		if(config.timeforing.frist.antall.dager) {
			Calendar cal = Calendar.getInstance() 
			cal.add(Calendar.DAY_OF_YEAR, (config.timeforing.frist.antall.dager * -1))
			if(date.before(cal.getTime())) {
				Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
				def timeregistreringList = timeforingService.getTimeregistreringsList (intervjuer)
				def fraKontrollDatoListe = timeforingService.getDatesOpenedForEditing(intervjuer)
				flash.errorMessage = "Kan ikke føre timer lengre tilbake i tid enn " + config.timeforing.frist.antall.dager + " dager"
				render(view: "velgDato", model: [timeregistreringList: timeregistreringList, fraKontrollDatoListe: fraKontrollDatoListe])
				return
			}
		}
		
		Calendar cal = Calendar.getInstance()
		cal.set Calendar.HOUR_OF_DAY, 23
		cal.set Calendar.MINUTE, 59
		cal.set Calendar.SECOND, 59
		
		if(cal.getTime().before(date)) {
			Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
			def timeregistreringList = timeforingService.getTimeregistreringsList (intervjuer)
			def fraKontrollDatoListe = timeforingService.getDatesOpenedForEditing(intervjuer)
			flash.errorMessage = "Kan ikke føre timer fram i tid."
			render(view: "velgDato", model: [timeregistreringList: timeregistreringList, fraKontrollDatoListe: fraKontrollDatoListe])
			return
		}
		
		session.timeforingDato = date
		
		redirect(action: "listTotal")
	}
	
	def velgNesteDato = {
		session.timeforingDato = session.timeforingDato + 1
		Calendar cal = Calendar.getInstance()
		cal.set Calendar.HOUR_OF_DAY, 23
		cal.set Calendar.MINUTE, 59
		cal.set Calendar.SECOND, 59
		
		if(cal.getTime().before(session.timeforingDato)) {
			flash.errorMessage = "Kan ikke føre timer fram i tid."
			session.timeforingDato = session.timeforingDato - 1
		}
		
		redirect (action: "listTotal")
	}
	
	def velgForrigeDato = {
		session.timeforingDato = session.timeforingDato - 1
		
		if(config.timeforing.frist.antall.dager) {
			Calendar cal = Calendar.getInstance()
			cal.add(Calendar.DAY_OF_YEAR, (config.timeforing.frist.antall.dager * -1))
			if(session.timeforingDato.before(cal.getTime())) {
				flash.errorMessage = "Kan ikke føre timer lengre tilbake i tid enn " + config.timeforing.frist.antall.dager + " dager"
				session.timeforingDato = session.timeforingDato + 1
			}
		}
		
		redirect(action: "listTotal")
	}
	
	def godkjenn = {
		def timeforingInstance = Timeforing.get(params.id)
		
		if (! timeforingInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
			redirect(action: "listTotal")
			return
		}

		if (! brukerKanRedigereDenne(timeforingInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		timeforingInstance.timeforingStatus = TimeforingStatus.GODKJENT
		timeforingInstance.save( flush: true)
		redirect(controller: "timeforing", action: "listTotal")
	}
	
	def godkjennAlle = {
		
		Date date = session.timeforingDato
		
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		timeforingService.godkjennAlle (intervjuer, date)
		
		redirect(action: "listTotal")
	}
	
	def sendInnAlle = {
		Date date = session.timeforingDato
		
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		boolean alleGodkjent = timeforingService.isAllRegistreringerGodkjent (intervjuer, date)
		
		if(!alleGodkjent) {
			flash.errorMessage = "Kan ikke sende inn før alle registreringer er godkjent."
			redirect(action: "listTotal")
			return
		}
		
		timeforingService.sendInnAlle(intervjuer, date)
		
		redirect(action: "listTotal")
	}
	
	def konverterKjorebok = {
		
		Date date = session.timeforingDato
		
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		def kjorebokList = timeforingService.getKjorebokForIntervjuer (intervjuer, date)
		
		int antallFeilUnderKonvertering = 0
		
		kjorebokList.each {
			Kjorebok kjorebok = it
			
			Timeforing timeforing = new Timeforing()
			
			timeforing.fra = kjorebok.fraTidspunkt
			timeforing.til = kjorebok.tilTidspunkt
			
			timeforing.arbeidsType = ArbeidsType.REISE
			
			timeforing.intervjuer = intervjuer
			
			timeforing.produktNummer = kjorebok.produktNummer
			
			timeforing.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
			
			if(!timeforing.save(flush: true)) {
				antallFeilUnderKonvertering ++
			}
			
			if(antallFeilUnderKonvertering == kjorebokList.size()) {
				flash.errorMessage = "Problemer oppstod under konvertering. Sjekk om du allerede har registrert arbeidstid som tidsmessig kolliderer med kjøreboken"
				redirect(action: "listTotal")
				return
			}
			else if( antallFeilUnderKonvertering > 0 && (antallFeilUnderKonvertering < kjorebokList.size())) {
				flash.errorMessage = "Noen registreringer ble ikke konvertert. Sjekk om du allerede har registrert arbeidstid som tidsmessig kolliderer med kjøreboken"
				redirect(action: "listTotal")
				return
			}
			
			redirect(action: "listTotal")
			return
		}
	}
	
	def visAvviste = {
		FraTilAvvisteCommand command = new FraTilAvvisteCommand()
		Intervjuer intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		
		Date fraDato = command.fraDato
		Date tilDato = command.tilDato
		
		if(!fraDato) {
			Calendar cal = Calendar.getInstance()
			cal.add(Calendar.MONTH, -2)
			fraDato = cal.getTime()
		}
		
		if(!tilDato) {
			tilDato = new Date()
		}
		
		if(intervjuer == null) {
			redirect(controller: "login", action: "login")
		}
		
		def timeforingInstanceList = timeforingService.getAvvisteTimeforingForIntervjuer(intervjuer, fraDato, tilDato)
		def kjorebokInstanceList = timeforingService.getAvvisteKjorebokForIntervjuer(intervjuer, fraDato, tilDato)
		def utleggInstanceList = timeforingService.getAvvisteUtleggForIntervjuer(intervjuer, fraDato, tilDato)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd")
		return [
			timeforingInstanceList: timeforingInstanceList,
			kjorebokInstanceList: kjorebokInstanceList,
			utleggInstanceList: utleggInstanceList,
			intervjuerInstance: intervjuer,
			fraDato: fraDato,
			tilDato: tilDato
		]
	}


	def kopiere = {
		
	}


	def kopierTimeforinger = {
		FraTilKopierCommand fraTilKopierCommand = new FraTilKopierCommand()
		if(!fraTilKopierCommand.validate() ) {
			render(view: "kopiere", model: [fraTilKopierCommand: fraTilKopierCommand])
			return
		}
		
		def intervjuerInstance = Intervjuer.get(fraTilKopierCommand.intervjuer)
		
		def fraTimer = timeforingService.getTimeforingerForIntervjuer(intervjuerInstance, fraTilKopierCommand.fraDato)
		def fraUtlegg = timeforingService.getUtleggForIntervjuer(intervjuerInstance, fraTilKopierCommand.fraDato)
		def fraKjore = timeforingService.getKjorebokForIntervjuer(intervjuerInstance, fraTilKopierCommand.fraDato)
		
		if(!fraTimer && !fraUtlegg && !fraKjore) {
			flash.errorMessage = "Det finnes ingen timer, utlegg eller kjørebøker fra gitt fra dato så kan ikke kopiere"
			render(view: "kopiere", model: [fraTilKopierCommand: fraTilKopierCommand])
			return
		}
		
		def tilTimer = timeforingService.getTimeforingerForIntervjuer(intervjuerInstance, fraTilKopierCommand.tilDato)
		def tilUtlegg = timeforingService.getUtleggForIntervjuer(intervjuerInstance, fraTilKopierCommand.tilDato)
		def tilKjore = timeforingService.getKjorebokForIntervjuer(intervjuerInstance, fraTilKopierCommand.tilDato)
		
		if(tilTimer || tilUtlegg || tilKjore) {
			flash.errorMessage = "Det finnes timer, utlegg eller kjørebøker for gitt til dato så kan ikke kopiere"
			render(view: "kopiere", model: [fraTilKopierCommand: fraTilKopierCommand])
			return
		}
		
		Calendar fromCal = Calendar.getInstance()
		Calendar toCal = Calendar.getInstance()
		toCal.setTime(fraTilKopierCommand.tilDato)
		
		fraTimer.each { 
			def timeforing = new Timeforing()
			timeforing.properties = it.properties
			
			//TODO: fiks tid og dato
			fromCal.setTime(it.fra)
			toCal.set(Calendar.HOUR_OF_DAY, fromCal.get(Calendar.HOUR_OF_DAY))
			toCal.set(Calendar.MINUTE, fromCal.get(Calendar.MINUTE))
			toCal.set(Calendar.SECOND, fromCal.get(Calendar.SECOND))
			
			timeforing.fra = toCal.getTime()
			
			fromCal.setTime(it.til)
			
			toCal.set(Calendar.HOUR_OF_DAY, fromCal.get(Calendar.HOUR_OF_DAY))
			toCal.set(Calendar.MINUTE, fromCal.get(Calendar.MINUTE))
			toCal.set(Calendar.SECOND, fromCal.get(Calendar.SECOND))
			
			timeforing.til = toCal.getTime() 
			
			timeforing.save(failOnError: true)
		}
		
		toCal.set(Calendar.HOUR_OF_DAY, 12)
		fraUtlegg.each {
			def utlegg = new Utlegg()
			utlegg.properties = it.properties
			
			utlegg.dato = toCal.getTime()
			
			utlegg.save(failOnError: true)
		}
		
		fraKjore.each {
			def kjore = new Kjorebok()
			kjore.properties = it.properties
			
			//TODO: fiks tid og dato
			fromCal.setTime(it.fraTidspunkt)
			toCal.set(Calendar.HOUR_OF_DAY, fromCal.get(Calendar.HOUR_OF_DAY))
			toCal.set(Calendar.MINUTE, fromCal.get(Calendar.MINUTE))
			toCal.set(Calendar.SECOND, fromCal.get(Calendar.SECOND))
			
			kjore.fraTidspunkt = toCal.getTime()
			
			fromCal.setTime(it.tilTidspunkt)
			
			toCal.set(Calendar.HOUR_OF_DAY, fromCal.get(Calendar.HOUR_OF_DAY))
			toCal.set(Calendar.MINUTE, fromCal.get(Calendar.MINUTE))
			toCal.set(Calendar.SECOND, fromCal.get(Calendar.SECOND))
			
			kjore.tilTidspunkt = toCal.getTime()
			
			kjore.save(failOnError: true)
		}
		
		redirect(action: "kopiere")
	}
	
	def hentArbeidsTyper = {
		def arbTypeListe = ArbeidsType.values()
		def arbeidsTypeListe = []
				
		arbTypeListe.each {
			if(it != ArbeidsType.REISE) {
				arbeidsTypeListe << it
			}
		}
		
		return arbeidsTypeListe
	}
	
	private boolean brukerKanRedigereDenne(Timeforing timeforingInstance) {
		if (timeforingInstance.arbeidsType == ArbeidsType.REISE) {
			flash.message = "${message(code: 'sil.invalid.editing.operation.attempted1', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
			return false
		}
		else {
			// Sjekker at intervjueren som "eier" timeføringa == innlogga bruker, eller bruker = admin
			def instanceEier = timeforingInstance.intervjuer
		
			if (! brukerService.kanBrukerEndreLonnsRecord(instanceEier)) {
	            flash.message = "${message(code: 'default.wrong.object.owner.message', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
				return false
			}
			else if (! brukerService.erAdminBruker() && !timeforingService.wageClaimStatusAllowsEditing(timeforingInstance.timeforingStatus)) {
				flash.message = "${message(code: 'sil.invalid.editing.operation.attempted2', args: [message(code: 'timeforing.label', default: 'Timeforing'), params.id])}"
				return false
			}
		}
		return true
	}
}

class FraTilKopierCommand implements grails.validation.Validateable {
	Date fraDato
	Date tilDato
	String intervjuer
	
	static constraints = {
		fraDato(nullable: false)
		tilDato(nullable: false)
		intervjuer(blank: false, nullable: false)
	}
}

class FraTilAvvisteCommand implements grails.validation.Validateable  {
	Date fraDato
	Date tilDato
}

class FraTilCommand implements grails.validation.Validateable {
	String fraTid
	String tilTid
	
	static constraints = {
		fraTid nullable: false, blank: false, minSize: 5, maxSize: 5, validator: { value ->
			def melding
			if(value) {
				if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "timeforing.fra.tidspunkt.feil.format"
				}
			}
			return melding
		}
		tilTid nullable: false, blank: false, minSize: 5, maxSize: 5, validator: { value ->
			def melding
			if(value) {
				if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "timeforing.til.tidspunkt.feil.format"
				}
			}
			return melding
		}
	}
}

class VelgDatoCommand implements grails.validation.Validateable {
	Date timeforingDato
}