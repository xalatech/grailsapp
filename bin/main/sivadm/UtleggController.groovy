package sivadm

import grails.plugin.springsecurity.annotation.Secured
import sil.Krav
import siv.data.ProduktKode
import siv.type.TimeforingStatus
import siv.type.UtleggType
import siv.util.DatoUtil
import util.DateUtil

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUER', 'ROLE_SIL'])
class UtleggController {

	//TimeforingService timeforingService
	def brukerService
	def timeforingService

	def create = {
        def utleggInstance = new Utlegg()
        utleggInstance.properties = params
		
		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
		
		def utleggTypeListe = hentUtleggsTyper()
		
        return [utleggInstance: utleggInstance, fraTid: "09:00", tilTid: "09:00", produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe]
    }
    
	
	
	def save = {
		UtleggFraTilCommand utleggFraTilCommand = new UtleggFraTilCommand()
		
        def utleggInstance = new Utlegg(params) 
		
		Date dato = session.timeforingDato
		utleggInstance.dato = DateUtil.getWithOneHourAdded( dato )  
		
		if (! brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())
		utleggInstance.intervjuer = intervjuer
		
		utleggInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
		
		// belop ikke fylt ut naar utleggtype er kostgodtgjorelse
		if (params.utleggType == UtleggType.KOST_GODT.getKey() ) {
				
			/* 
			  	validering av antall kostgodtgjorelser - kun en stk. er lov pr dag 
			*/
			int antallKostGodtgjorelser = timeforingService.hentAntallKostGodtgjorelserForIntervjuer(intervjuer, dato)
			
			if ( antallKostGodtgjorelser > 0 ) {
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				utleggInstance.errors.rejectValue('utleggType', 'sivadm.error.utlegg.antall.kostgodtgjorelse')
				render(view: "create", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
				return
			}
			
			/*
			  	validering av antall kjoreboker for denne dagen - det maa minst være en for at man skal kunne registrere en kostgodtgjorelse
			*/
			def kjorebokList = timeforingService.getKjorebokForIntervjuer(intervjuer, dato)
			
			if ( kjorebokList == null || kjorebokList.size() == 0 ) {
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				utleggInstance.errors.rejectValue('utleggType', 'sivadm.error.utlegg.antall.kjoreboker')
				render(view: "create", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
				return
			}
			
			/*
			  	validering av antall timer foert for denne dagen - det maa minst være 5 timer for at man skal kunne registrere en kostgodtgjørelse
			*/
			int sumMinutter = timeforingService.getTotaltAntallMinutterForIntervjuer(intervjuer, dato)
			if ( sumMinutter < (5*60)) {
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				utleggInstance.errors.rejectValue('utleggType', 'sivadm.error.utlegg.antall.timer')
				render(view: "create", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
				return
			}
			
			// setter dette i koden fordi man ikke oppgir belop naar det er snakk om kostgodtgjorelse
			params.belop = "0"
			utleggInstance.belop = new Double(0)
			
			if (! utleggFraTilCommand.validate()) {
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				render(view: "create", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe, utleggFraTilCommand: utleggFraTilCommand])
				return
			}
			
			utleggInstance.kostFraTid = DateUtil.getDateWithTime(session.timeforingDato, params.fraTid)
			utleggInstance.kostTilTid = DateUtil.getDateWithTime(session.timeforingDato, params.tilTid)
		}
		
		if (params.belop.contains(",")) {
			// Hvis ',' (komma) er brukt som desimal tegn byttes det
			// til '.' (punktum) for at beløpet skal bli rett
			utleggInstance.belop = new Double(params.belop.replaceAll(",", "."))	
		}
		
		// lagre
        if (utleggInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), utleggInstance.id])}"
            redirect(controller: "timeforing", action: "listTotal")
        }
        else {
			List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
			def utleggTypeListe = hentUtleggsTyper()
            render(view: "create", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
        }
    }

	
	//default.wrong.object.owner.message
    def edit = {
        def utleggInstance = Utlegg.get(params.id)
        if (! utleggInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
            redirect(controller: "timeforing", action: "listTotal")
			return
        }
		
		if (! brukerKanRedigereDenne(utleggInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
		def utleggTypeListe = hentUtleggsTyper()
        return [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe]
    }

	
	
    def update = {
		UtleggFraTilCommand utleggFraTilCommand = new UtleggFraTilCommand()
     
		def utleggInstance = Utlegg.get(params.id)
        
		if (! utleggInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		if (! brukerKanRedigereDenne(utleggInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

        if (params.version) {
            def version = params.version.toLong()
            if (utleggInstance.version > version) {
                
                utleggInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'utlegg.label', default: 'Utlegg')] as Object[], "Another user has updated this Utlegg while you were editing")
                
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(utleggInstance.dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				
				render(view: "edit", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
                return
            }
        }
		
		// validering av antall kostgodtgjorelser - kun ett stk. er lov pr dag
		// her maa vi sjekke om brukeren har endret en utleggstype fra noe annet til kostgodtgjorelse
		// det er ikke lov hvis det allerede finnes en kostgodtgjorelse for denne dagen
		if (params.utleggType == UtleggType.KOST_GODT.getKey() ) {
			if (utleggInstance.utleggType != UtleggType.KOST_GODT  ) {
				int antallKostGodtgjorelser = timeforingService.hentAntallKostGodtgjorelserForIntervjuer(utleggInstance.intervjuer, utleggInstance.dato)
				
				if ( antallKostGodtgjorelser > 0 ) {
					List<ProduktKode> prodListe = timeforingService.hentProduktKode(utleggInstance.dato, true)
					def utleggTypeListe = hentUtleggsTyper()
					utleggInstance.errors.rejectValue('utleggType', 'sivadm.error.utlegg.antall.kostgodtgjorelse')
					render(view: "edit", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
					return
				}
			}
		}
		
        utleggInstance.properties = params
		
		if (utleggInstance.utleggType == UtleggType.KOST_GODT  ) {
			if (! utleggFraTilCommand.validate()) {
				List<ProduktKode> prodListe = timeforingService.hentProduktKode(utleggInstance.dato, true)
				def utleggTypeListe = hentUtleggsTyper()
				render(view: "edit", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe, utleggFraTilCommand:utleggFraTilCommand])
				return
			}
			
			utleggInstance.kostFraTid = DateUtil.getDateWithTime(session.timeforingDato, params.fraTid)
			utleggInstance.kostTilTid = DateUtil.getDateWithTime(session.timeforingDato, params.tilTid)
		}
		
		// belop ikke fylt ut naar utleggtype er kostgodtgjorelse
		if (params.utleggType == UtleggType.KOST_GODT.getKey()) {
			params.belop = "0"
			utleggInstance.belop = new Double(0)
		}
		
		utleggInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
					
		if (params.belop.contains(",")) {
			// Hvis ',' (komma) er brukt som desimal tegn byttes det
			// til '.' (punktum) for at beløpet skal bli rett
			utleggInstance.belop = new Double(params.belop.replaceAll(",", "."))
		}
		
		if (params.kravId) {
			utleggInstance.timeforingStatus = TimeforingStatus.SENDT_INN
		}
					
        if (! utleggInstance.hasErrors() && utleggInstance.save(flush: true)) {
			if (params.kravId) {
				def rParams = null
				if (params.isFailed == true) {
					rParams = [feiletId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}
				else {
					rParams = [kravId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}
				
				flash.message = "${message(code: 'sil.behandle.krav.utlegg.endret', args: [utleggInstance.id, params.kravId])}"
				redirect(controller: "krav", action: "behandleIntervjuerKrav", id: utleggInstance.intervjuer?.id, params: rParams)
			}
			else {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), utleggInstance.id])}"
				redirect(controller: "timeforing", action: "listTotal")
			}
        }
        else {
			Date dato = session.timeforingDato
			List<ProduktKode> prodListe = timeforingService.hentProduktKode(dato, true)
			def utleggTypeListe = hentUtleggsTyper()
            render(view: "edit", model: [utleggInstance: utleggInstance, produktNummerListe: prodListe, utleggTypeListe: utleggTypeListe])
        }
    }

	
	
    def delete = {
        def utleggInstance = Utlegg.get(params.id)
        if (! utleggInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		if (! brukerKanRedigereDenne(utleggInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		List<Krav> kList = Krav.findAllByUtlegg(utleggInstance)
		kList.each { krav ->
			krav.setUtlegg(null)
			krav.save(failOnError: true, flush: true)
		}
		try {
            utleggInstance.delete(flush: true)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
            redirect(controller: "timeforing", action: "listTotal")
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
            redirect(controller: "timeforing", action: "listTotal")
        }
    }
	
	
	
	def avbryt = {
		def utleggInstance = Utlegg.get(params.id)
		
		if (params.kravId) {
			def rParams = null
			if( params.isFailed == true) {
				rParams = [feiletId: params.kravId]
			}
			else {
				rParams = [kravId: params.kravId]
			}
			redirect(controller: "krav", action: "behandleIntervjuerKrav", id: utleggInstance.intervjuer?.id, params: rParams)
		}
		else {
			redirect(controller: "timeforing", action: "listTotal")
		}
	}
	
	
	
	def godkjenn = {
		def utleggInstance = Utlegg.get(params.id)
		if (! utleggInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		if (! brukerKanRedigereDenne(utleggInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		utleggInstance.timeforingStatus = TimeforingStatus.GODKJENT
		utleggInstance.save( flush: true)
		redirect(controller: "timeforing", action: "listTotal")
	}
	
	
	
	protected List hentUtleggsTyper() {
		def utleggsTypeListe = [UtleggType.FRIMERKER, UtleggType.TELEFON, UtleggType.KART, UtleggType.KOST_GODT, UtleggType.ANNET]
		return utleggsTypeListe
	}
	
	private boolean brukerKanRedigereDenne(Utlegg utleggInstance) {
		if (utleggInstance.harKjorebok) {
			flash.message = "${message(code: 'sil.invalid.editing.operation.attempted1', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
			return false
		}
		else {
			// Sjekker at intervjueren som "eier" utlegget == innlogga bruker, eller bruker = admin
			def instanceEier = utleggInstance.intervjuer
		
			if (! brukerService.kanBrukerEndreLonnsRecord(instanceEier)) {
	            flash.message = "${message(code: 'default.wrong.object.owner.message', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
				return false
			}
			else if (! brukerService.erAdminBruker() && !timeforingService.wageClaimStatusAllowsEditing(utleggInstance.timeforingStatus)) {
				flash.message = "${message(code: 'sil.invalid.editing.operation.attempted2', args: [message(code: 'utlegg.label', default: 'Utlegg'), params.id])}"
				return false
			}
		}
		return true
	}
}



class UtleggFraTilCommand {
	String fraTid
	String tilTid
	String kostTilSted
	
	private static final long femTimer = (1000 * 60 * 60 * 5) // 1000 ms * 60 sec * 60 min * 5 timer = fem timer
	
	static constraints = {
		
		fraTid nullable: false, blank: false, minSize: 5, maxSize: 5, validator: { value ->
			def melding
			if (value) {
				if (! value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "timeforing.fra.tidspunkt.feil.format"
				}
			}
			return melding
		}
		
		tilTid nullable: false, blank: false, minSize: 5, maxSize: 5, validator: { value, obj ->
			def melding
			if (value) {
				if (! value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
					melding = "timeforing.til.tidspunkt.feil.format"
				}else {
					def differanse = DatoUtil.finnTidsDifferanse(obj.fraTid, value)
					if (differanse.toMilliseconds() < femTimer) {
						melding = "timeforing.til.tidspunkt.mindre.enn.fem.timer"
					}
				}
			}
			return melding
		}
		
		kostTilSted nullable: false, blank: false
	}
	
}