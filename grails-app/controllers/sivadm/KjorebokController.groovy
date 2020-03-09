package sivadm

import java.util.Date;

// TODO: remove this if not used
import org.apache.naming.java.javaURLContextFactory;

import sil.*;
import sil.type.*;
import util.DateUtil;
import siv.data.ProduktKode;
import siv.type.*;

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUER', 'ROLE_SIL'])
class KjorebokController {

	def intervjuerService
	def timeforingService
	def kjorebokService
	def brukerService
	def kravService
	
	
	def create = {
		def kjorebokInstance = new Kjorebok()
		kjorebokInstance.properties = params

		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}

		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())

		kjorebokInstance.fraAdresse = intervjuer.gateAdresse

		kjorebokInstance.fraPoststed = intervjuer.postSted

		def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())

		Date dato = session.timeforingDato

		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
		
		def forrigeKjorebokSammeDag = timeforingService.hentForrigeKjorebokSammeDag(intervjuer, dato)
		
		if( forrigeKjorebokSammeDag ) {
			kjorebokInstance.fraAdresse = forrigeKjorebokSammeDag.tilAdresse
			kjorebokInstance.fraPoststed = forrigeKjorebokSammeDag.tilPoststed
		}
		
		return [kjorebokInstance: kjorebokInstance,
			fraTid: "", tilTid: "",
			intervjuObjektList: intervjuObjektList,
			produktNummerListe: prodListe]
	}

	
	def save = {
		KjorebokCreateCommand createCommand = new KjorebokCreateCommand()
		KjorebokFraTilCommand fraTilCommand = new KjorebokFraTilCommand()

		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}

		def kjorebokInstance = new Kjorebok(params)

		def fraTid = params.fraTid.trim()
		def tilTid = params.tilTid.trim()

		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
		
		if(!fraTilCommand.validate() || !createCommand.validate()) {
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, fraTilCommand: fraTilCommand, produktNummerListe: prodListe])
			return
		}

		kjorebokInstance.fraTidspunkt = dato
		kjorebokInstance.tilTidspunkt = dato

		def intervjuer = Intervjuer.findByInitialer(brukerService.getCurrentUserName())

		kjorebokInstance.intervjuer = intervjuer

		kjorebokInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT

		// henter timer og minutter fra form, og setter paa fra-dato
		kjorebokInstance.fraTidspunkt = DateUtil.getDateWithTime(kjorebokInstance.fraTidspunkt, fraTid)

		// henter timer og minutter fra form, og setter paa til-dato
		kjorebokInstance.tilTidspunkt = DateUtil.getDateWithTime(kjorebokInstance.tilTidspunkt, tilTid)
		
		Date maksDato = DateUtil.getWithMinutesAdded(new Date(), 15)
		
		//Sjekk om tidspunkt for kjørebok er fram i tid
		if(kjorebokInstance.tilTidspunkt.after(maksDato)) {	
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			flash.errorMessage = "${message(code: 'kjorebok.fraTidspunkt.max.exceeded')}"
			render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTilCommand: fraTilCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
			return
		}
		
		//Sjekk om produktnummer er angitt
		if( ! kjorebokInstance.produktNummer ) {
			kjorebokInstance.errors.rejectValue('produktNummer', 'kjorebok.produktNummer.blank')
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTilCommand: fraTilCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
			return
		}

		// Oppretter timeforing for denne reisen
		Timeforing time = kjorebokInstance.opprettTimeforing()
		if(!time.validate()) {
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			flash.errorMessage = "${message(code: 'sivadm.error.timeregistrering.krasj')}"
			render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
			return
		}

		kjorebokInstance.timeforing = time
		
		boolean isRetur = false
		boolean returFeilet = false
		
		def retur
		
		if(kjorebokInstance.kjorteHjem) {
			// Lag retur kjorebok
			retur = kjorebokInstance.lagRetur(createCommand.fraTidRetur, createCommand.tilTidRetur)
			def returTime = retur.opprettTimeforing()
			retur.timeforing = returTime
			isRetur = true
			
			//Sjekk om til tidspunkt for returen er før fra tidspunkt
			if(retur.tilTidspunkt.before(retur.fraTidspunkt) || retur.tilTidspunkt.equals(retur.fraTidspunkt)) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.fra.foer.til')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return
			}
			
			//Sjekk om retur er satt til fram i tid
			if(retur.tilTidspunkt.after(new Date())) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.framtid')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return
			}
						
			// Sjekk om det er retur tid er før til/fra tidspunkt
			if(retur.tilTidspunkt.before(kjorebokInstance.fraTidspunkt)) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.feil')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return
			}
			
			// Sjekk om det er overlapp mellom til/fra tid og returtid
			if(retur.fraTidspunkt.before(kjorebokInstance.tilTidspunkt)) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.konflikt')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return	
			}
									
			// Sjekk om kjørebok for returen validerer
			if(!retur.validate()) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.feilmelding')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return
			}
			
			// Sjekk om timeføringen for returen validerer
			if(!returTime.validate()) {
				def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
				flash.errorMessage = "${message(code: 'kjorebok.retur.time.feilmelding')}"
				render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
				return	
			}
		}
				
		if(kjorebokInstance.save(flush: true)) {
			if(retur) {
				if(retur.save(flush: true)) {
					flash.message = "${message(code: 'kjorebok.retur.opprettet', args: [], default: 'Kjørebok og retur opprettet')}"
				}
				else {
					returFeilet = true
					flash.message = "${message(code: 'kjorebok.retur.feilmelding', args: [], default: 'Kjørebok opprettet, men fikk ikke opprettet retur reise')}"
				}
			}
			else {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), kjorebokInstance.id])}"
			}
					
			int cntUtlegg = 0
			int cntUtleggFeilet = 0
						
			// Må sjekke params vs createCommand for utlegg beløpene (bompenger, parkering,
			// ferje og belop) for å sjekke bruk av desimaltegn '.' vs ',', for at
			// Double tallet skal bli rett må det være ','
			if(params.bompenger?.contains(",")) {
				createCommand.bompenger = new Double(params.bompenger.replaceAll(",", "."))
			}
			if(params.parkering?.contains(",")) {
				createCommand.parkering = new Double(params.parkering.replaceAll(",", "."))
			}
			if(params.ferje?.contains(",")) {
				createCommand.ferje = new Double(params.ferje.replaceAll(",", "."))
			}
			if(params.belop?.contains(",")) {
				createCommand.belop = new Double(params.belop.replaceAll(",", "."))
			}
			
			kjorebokService.oppdaterUtleggKjorebok(false, kjorebokInstance.fraTidspunkt, createCommand.bompenger, createCommand.parkering, createCommand.ferje, createCommand.belop, kjorebokInstance, cntUtlegg, cntUtleggFeilet, brukerService.getCurrentUserName())
			
			if(cntUtlegg > 0 && cntUtleggFeilet == 0 && !isRetur) {
				// Utlegg opprettet, ingen utlegg feilet og det er ikke snakk om retur
				flash.message = "${message(code: 'kjorebok.opprettet.utlegg', args: [cntUtlegg])}"
			}
			else if(cntUtleggFeilet > 0 && !isRetur) {
				// x utlegg feilet og det er ikke snakk om retur
				flash.message = "${message(code: 'kjorebok.opprettet.utlegg.feilet', args: [cntUtlegg, cntUtleggFeilet])}"
			}
			else if(cntUtlegg > 0 && cntUtleggFeilet == 0 && isRetur && returFeilet) {
				// Retur feilet, utlegg opprettet (ingen feilet)
				flash.message = "${message(code: 'kjorebok.retur.feilmelding.utlegg', args: [cntUtlegg])}"
			}
			else if(cntUtleggFeilet > 0 && isRetur && returFeilet) {
				// Retur feilet og x utlegg feilet
				flash.message = "${message(code: 'kjorebok.retur.feilmelding.utlegg.feilet', args: [cntUtlegg, cntUtleggFeilet])}"
			}
			else if(isRetur && !returFeilet && cntUtlegg > 0 && cntUtleggFeilet == 0) {
				// Kjørebok, retur og utlegg opprettet
				flash.message = "${message(code: 'kjorebok.retur.utlegg.opprettet', args: [cntUtlegg])}"
			}
			else if(isRetur && !returFeilet && cntUtleggFeilet > 0) {
				// Kjørebok og retur opprettet. x utlegg feilet
				flash.message = "${message(code: 'kjorebok.retur.utlegg.feil', args: [cntUtlegg, cntUtleggFeilet])}"
			}

			redirect(controller: "timeforing", action: "listTotal")
		}
		else {
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			render(view: "create", model: [kjorebokInstance: kjorebokInstance, createCommand: createCommand, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe])
		}
	}

	
	def edit = {
		def kjorebokInstance = Kjorebok.get(params.id)
		
		if (! kjorebokInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing" , action: "listTotal")
			return
		}

		if (! brukerKanRedigereDenne(kjorebokInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		def fraTid = DateUtil.getTimeOnDate(kjorebokInstance.fraTidspunkt)

		def tilTid = DateUtil.getTimeOnDate(kjorebokInstance.tilTidspunkt)

		Date dato = session.timeforingDato

		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)
		def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())

		def kjorebokCreateCommand = hentKjorebokCreateCommand(kjorebokInstance)

		[
					kjorebokInstance: kjorebokInstance,
					fraTid: fraTid,
					tilTid: tilTid,
					produktNummerListe: prodListe,
					intervjuObjektList: intervjuObjektList,
					createCommand: kjorebokCreateCommand
				]
	}

	
	def update = {
		KjorebokCreateCommand createCommand = new KjorebokCreateCommand()
		KjorebokFraTilCommand fraTilCommand = new KjorebokFraTilCommand()

		if(! brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}

		def kjorebokInstance = Kjorebok.get(params.id)

		if(! kjorebokInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing" , action: "listTotal")
			return
		}
		
		if (! brukerKanRedigereDenne(kjorebokInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		Date dato = session.timeforingDato
		List<ProduktKode> prodListe = timeforingService.hentSorterteProduktKoder(dato, true)

		if (params.version) {
			def version = params.version.toLong()
			if (kjorebokInstance.version > version) {

				kjorebokInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
					message(code: 'kjorebok.label', default: 'Kjorebok')]
				as Object[], "Another user has updated this Kjorebok while you were editing")
				def kjorebokCreateCommand = hentKjorebokCreateCommand(kjorebokInstance)
				render(view: "edit", model: [kjorebokInstance: kjorebokInstance, produktNummerListe: prodListe, createCommand: kjorebokCreateCommand])
				return
			}
		}

		kjorebokInstance.properties = params

		def fraTid = params.fraTid.trim()
		def tilTid = params.tilTid.trim()

		if(! fraTilCommand.validate()) {
			render(view: "edit", model: [kjorebokInstance: kjorebokInstance, fraTid: fraTid, tilTid: tilTid, fraTilCommand: fraTilCommand, produktNummerListe: prodListe, createCommand: createCommand])
			return
		}

		kjorebokInstance.timeforingStatus = TimeforingStatus.IKKE_GODKJENT

		// henter timer og minutter fra form, og setter paa fra-dato
		kjorebokInstance.fraTidspunkt = DateUtil.getDateWithTime(kjorebokInstance.fraTidspunkt, fraTid)

		// henter timer og minutter fra form, og setter paa til-dato
		kjorebokInstance.tilTidspunkt = DateUtil.getDateWithTime(kjorebokInstance.tilTidspunkt, tilTid)

		if(params.kravId) {
			kjorebokInstance.timeforingStatus = TimeforingStatus.SENDT_INN
		}

		// Oppdater tilhørende timeføring med tidspunkter og produktNummer fra kjøreboken
		kjorebokInstance.timeforing?.fra = kjorebokInstance.fraTidspunkt
		kjorebokInstance.timeforing?.til = kjorebokInstance.tilTidspunkt
		kjorebokInstance.timeforing?.produktNummer = kjorebokInstance.produktNummer

		Date maksDato = DateUtil.getWithMinutesAdded(new Date(), 15)
		
		//Sjekk om tidspunkt for kjørebok er fram i tid
		if(kjorebokInstance.tilTidspunkt.after(maksDato)) {				
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())				
			flash.errorMessage = "${message(code: 'kjorebok.fraTidspunkt.max.exceeded')}"				
			render(view: "edit", model: [kjorebokInstance: kjorebokInstance, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe, createCommand: createCommand])
			return
		}
		
		if(! kjorebokInstance.timeforing?.validate()) {
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			flash.errorMessage = "${message(code: 'sivadm.error.timeregistrering.krasj')}"
			render(view: "edit", model: [kjorebokInstance: kjorebokInstance, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe, createCommand: createCommand])
			return
		}

		int cntUtlegg = 0
		int cntUtleggFeilet = 0
		
		// Er dette en update som gjøres fra SIL av kontroller?
		boolean silRetting = params.kravId ? true : false
								
		// Må sjekke params vs createCommand for utlegg beløpene (bompenger, parkering,
		// ferje og belop) for å sjekke bruk av desimaltegn '.' vs ',', for at
		// Double tallet skal bli rett må det være ','
		log.info("params: " + params)
		if(params.bompenger?.contains(",")) {
			log.info("params.bompenger: " + params.bompenger)
			createCommand.bompenger = new Double(params.bompenger.replaceAll(",", "."))
			log.info("createCommand.bompenger: " + createCommand.bompenger)
		}
		if(params.parkering?.contains(",")) {
			createCommand.parkering = new Double(params.parkering.replaceAll(",", "."))
		}
		if(params.ferje?.contains(",")) {
			createCommand.ferje = new Double(params.ferje.replaceAll(",", "."))
		}
		if(params.belop?.contains(",")) {
			createCommand.belop = new Double(params.belop.replaceAll(",", "."))
		}
		
		def utleggTilSletting = kjorebokService.oppdaterUtleggKjorebok(silRetting, kjorebokInstance.fraTidspunkt, createCommand.bompenger, createCommand.parkering, createCommand.ferje, createCommand.belop, kjorebokInstance, cntUtlegg, cntUtleggFeilet, brukerService.getCurrentUserName())
		
		// Slett utlegg som er fjernet fra kjøreboken
		utleggTilSletting.each {
			try {
				// Slett krav tilhørende utlegg som skal slettes
				Krav krav = Krav.findByUtlegg(it)
				if(krav) {
					krav.delete(flush: true)
				}
				
				it.delete(flush: true)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				log.error(e.getMessage())
			}
		}
		
		if(! kjorebokInstance.hasErrors() && kjorebokInstance.save(flush: true)) {
			if(params.kravId) {
				def rParams = null
				if(params.isFailed == true) {
					rParams = [feiletId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}
				else {
					rParams = [kravId: params.kravId, oppdaterKrav: true, tittel: params.tittel, melding: params.melding]
				}

				flash.message = "${message(code: 'sil.behandle.krav.kjorebok.endret', args: [kjorebokInstance.id, params.kravId])}"
				redirect(controller: "krav", action: "behandleIntervjuerKrav", id: kjorebokInstance.intervjuer?.id, params: rParams)
			}
			else {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), kjorebokInstance.id])}"
				redirect(controller: "timeforing", action: "listTotal")
			}
		}
		else {
			def intervjuObjektList = intervjuerService.getAlleUtvalgteCapiIntervjuObjekter(brukerService.getCurrentUserName())
			render(view: "edit", model: [kjorebokInstance: kjorebokInstance, fraTid: fraTid, tilTid: tilTid, intervjuObjektList: intervjuObjektList, produktNummerListe: prodListe, createCommand: createCommand])
		}
	}

	
	def delete = {
		def kjorebokInstance = Kjorebok.get(params.id)

		if (! kjorebokInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing" , action: "listTotal")
			return
		}
		
		if (! brukerKanRedigereDenne(kjorebokInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		List<Krav> kList = Krav.findAllByKjorebok(kjorebokInstance)
		kList.each { krav ->
			krav.setKjorebok(null)
			krav.save(failOnError: true, flush: true)
		}
		try {
			kjorebokInstance.delete(flush: true)
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing", action: "listTotal")
		}
		catch (org.springframework.dao.DataIntegrityViolationException e) {
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing" , action: "listTotal")
		}
	}

	
	def avbryt = {
		def kjorebokInstance = Kjorebok.get(params.id)

		if(params.kravId) {
			def rParams = null
			if(params.isFailed == true) {
				rParams = [feiletId: params.kravId]
			}
			else {
				rParams = [kravId: params.kravId]
			}
			redirect(controller: "krav", action: "behandleIntervjuerKrav", id: kjorebokInstance.intervjuer?.id, params: rParams)
		}
		else {
			redirect(controller: "timeforing", action: "listTotal")
		}
	}

	
	def godkjenn = {
		def kjorebokInstance = Kjorebok.get(params.id)

		if (! kjorebokInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			redirect(controller: "timeforing" , action: "listTotal")
			return
		}
		
		if (! brukerKanRedigereDenne(kjorebokInstance)) {
			redirect(controller: "timeforing", action: "listTotal")
			return
		}

		kjorebokInstance.timeforing?.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.utleggBelop?.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.utleggBom?.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.utleggParkering?.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.utleggFerge?.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.timeforingStatus = TimeforingStatus.GODKJENT
		kjorebokInstance.save(flush: true)
		redirect(controller: "timeforing", action: "listTotal")
	}

	
	protected KjorebokCreateCommand hentKjorebokCreateCommand( Kjorebok kjorebokInstance ) {
		def kjorebokCreateCommand = new KjorebokCreateCommand()

		def utgifter = false

		if(kjorebokInstance.utleggBelop) {
			kjorebokCreateCommand.belop = kjorebokInstance.utleggBelop.belop
		}
		if(kjorebokInstance.utleggBom) {
			kjorebokCreateCommand.bompenger = kjorebokInstance.utleggBom.belop
			utgifter = true
		}
		if(kjorebokInstance.utleggFerge) {
			kjorebokCreateCommand.ferje = kjorebokInstance.utleggFerge.belop
			if(kjorebokInstance.transportmiddel != TransportMiddel.FERJE) {
				utgifter = true
			}
		}
		if(kjorebokInstance.utleggParkering) {
			kjorebokCreateCommand.parkering = kjorebokInstance.utleggParkering.belop
			utgifter = true
		}

		kjorebokCreateCommand.utgifter = utgifter

		return kjorebokCreateCommand
	}
	
	
	private boolean brukerKanRedigereDenne(Kjorebok kjorebokInstance) {
		// Sjekker at intervjueren som "eier" kjøreboka == innlogga bruker, eller bruker = admin
		def instanceEier = kjorebokInstance.intervjuer
	
		if (! brukerService.kanBrukerEndreLonnsRecord(instanceEier)) {
            flash.message = "${message(code: 'default.wrong.object.owner.message', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			return false
		}
		else if (! brukerService.erAdminBruker() && !timeforingService.wageClaimStatusAllowsEditing(kjorebokInstance.timeforingStatus)) {
			flash.message = "${message(code: 'sil.invalid.editing.operation.attempted2', args: [message(code: 'kjorebok.label', default: 'Kjorebok'), params.id])}"
			return false
		}
		return true
	}
}

class KjorebokCreateCommand implements grails.validation.Validateable {
	String fraTidRetur
	String tilTidRetur
	Boolean utgifter
	Double parkering
	Double ferje
	Double bompenger
	Double belop
	Boolean kjorteHjem

	static constraints = {
		fraTidRetur nullable: true, blank: true, minSize: 5, maxSize: 5, validator: { value, command ->
			if(command.kjorteHjem) {
				def melding
				if(value) {
					if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
						melding = "kjorebok.fra.retur.tidspunkt.feil.format"
					}
				}
				else {
					melding = "kjorebok.kjorte.hjem.tidspunkt.mangler"
				}

				return melding
			}
		}
		tilTidRetur nullable: true, blank: true, minSize: 5, maxSize: 5, validator: { value, command ->
			if(command.kjorteHjem) {
				def melding
				if(value) {
					if(!value.matches("(([0]|[1])[0-9]|[2][0-3]):[0-5][0-9]")) {
						melding = "kjorebok.til.retur.tidspunkt.feil.format"
					}
				}
				else {
					melding = "kjorebok.kjorte.hjem.tidspunkt.mangler"
				}

				return melding
			}
		}
	}
}


class KjorebokFraTilCommand implements grails.validation.Validateable {

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