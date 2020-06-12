package sivadm

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured
import org.hibernate.validator.internal.metadata.facets.Validatable

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

import siv.util.SorteringsUtil
import siv.data.IntervjuObjektCapi
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus
import siv.type.ResultatStatus
import siv.type.SkjemaStatus;

import sivadm.IntervjuObjektSearch

@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER', 'ROLE_CAPITILDELING'])
class CapiAdministrasjonController {

	def intervjuObjektService
	def brukerService
	def oppdragService
	def capiAdministrasjonService
	SynkroniseringService synkroniseringService

	def listKlynger = {
		def klyngeList = Klynge.list()
		
		def prosjektList = Prosjekt.findAllByAvslutningsDatoGreaterThanEquals(new Date()).sort { it.oppstartDato }.reverse()
		
		def intervjuerGruppeList = CatiGruppe.list()
		
		def search
		
		if(session.capiSearch) {
			search = session.capiSearch 
		}
		
		[
			klyngeList:klyngeList, 
			prosjektList: prosjektList, 
			search: search, 
			intervjuerGruppeList: intervjuerGruppeList,
			skjemaStatusList: SkjemaStatus.Utsendt_CATI
		]
	}
	
	def nullstillSok = {
		session.capiSearch = null
		redirect(action: "listKlynger")
	}
	
	def lagreSok = {
		IntervjuObjektCapiSearchCommand command = new IntervjuObjektCapiSearchCommand()
		session.capiOffset = null
		session.capiSearch = command
		redirect(action: "list")
	}
	    
	def list = {
				
		params.max = Math.min(params.max ? params.int('max') : 100, 100)
			
		// sokekriterier og pagineringsinfo settes i session for å gjøre det enklere for brukeren			
		if(params.offset) {
			session.capiOffset = params.offset
		}
		else if(session.capiOffset) {
			params.offset = session.capiOffset
		}
		
		if(!session.capiSearch) {
			redirect(action: "listKlynger")
			return
		}
		
		def capiSearch = session.capiSearch
				
		def intervjuObjektInstanceList  = capiAdministrasjonService.finnIntervjuObjekterForTildeling(
				capiSearch.skjema, capiSearch.periode, capiSearch.intervjuObjektNummer, 
				capiSearch.tildelt, capiSearch.skjemaStatus, capiSearch.klynge, params)
		
		def total = capiAdministrasjonService.hentAntallIntervjuObjekterForTildeling(capiSearch.skjema, capiSearch.periode, 
			capiSearch.intervjuObjektNummer, capiSearch.tildelt, capiSearch.skjemaStatus, capiSearch.klynge)
		
		if(!intervjuObjektInstanceList) {
			def klyngeList = Klynge.list()
			def prosjektList = Prosjekt.list()
			flash.message = "${message(code: 'sivadm.tildel.capi.ingen.intervjuobjekter', default: 'Fant ingen intervjuobjekter for tildeling')}"
			redirect( action: "listKlynger")
		}
		
		def intervjuObjektCapiList = new ArrayList<IntervjuObjektCapi>()
				
		intervjuObjektInstanceList.each { 
			
			IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(it.id)
			Skjema sk = intervjuObjekt.periode.skjema
			
			IntervjuObjektCapi ioc = new IntervjuObjektCapi()
			
			def historikk = intervjuObjekt?.tildeltHistorikk?.sort{it.dato}.reverse()
			
			if(historikk) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
				if(historikk.size() > 1) {
					ioc.tidligereIntervjuer1 = sdf.format(historikk[1].dato) + " - " + historikk[1].intervjuer?.initialer
				}
				if(historikk.size() > 2) {
					ioc.tidligereIntervjuer2 = sdf.format(historikk[2].dato) + " - " + historikk[2].intervjuer?.initialer
				}
				if(historikk.size() > 3) {
					ioc.tidligereIntervjuer3 = sdf.format(historikk[3].dato) + " - " + historikk[3].intervjuer?.initialer
				}
			}
			
			ioc.settIntervjuObjektFelter(intervjuObjekt)
			ioc.delProduktNummer = sk?.delProduktNummer
			ioc.skjemaNavn = sk?.skjemaNavn
			
			Oppdrag sisteOppdrag = oppdragService.findLatestOppdragByIntervjuObjekt( intervjuObjekt )
			
			ioc.tildeltIntervjuer = sisteOppdrag?.intervjuer?.initialer
			ioc.sisteFrist = sisteOppdrag?.gyldighetsDato
			ioc.oppdragId = sisteOppdrag?.id
						
			intervjuObjektCapiList.add(ioc)
		}
						
		def sisteFrist
		
		if(params.sisteFrist) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
			sisteFrist = sdf.parse(params.sisteFrist)
		}
		
		def skjema
		def klynge
		def prosjekt
		def periode
				
		if(capiSearch?.prosjekt) {
			prosjekt = Prosjekt.get(capiSearch?.prosjekt)?.prosjektNavn
		}
		
		if(capiSearch?.periode) {
			periode = Periode.get(capiSearch?.periode)?.periodeNummer
		}
		
		if(capiSearch?.skjema) {
			skjema = Skjema.get(capiSearch?.skjema)
		}
		
		if(capiSearch?.klynge) {
			klynge = Klynge.get(capiSearch?.klynge)?.klyngeNavn
		}
				
		[
			intervjuObjektCapiList: intervjuObjektCapiList,
			intervjuObjektInstanceTotal: total,
			capiSearch: capiSearch,
			prosjekt: prosjekt,
			skjema: skjema,
			klynge: klynge,
			periode: periode,
			maxIO: params.maxIO,
			sisteFrist: sisteFrist,
			intervjuerGruppe: CatiGruppe.get(capiSearch.catiGruppe)?.navn
		]		
	}
	
	
	def tildel = {
		def intervjuObjekt = IntervjuObjekt.get(params.id)
		
		def oppdrag = oppdragService.findLatestOppdragByIntervjuObjekt(intervjuObjekt)
		
		if( oppdrag?.oppdragFullfort == false && oppdrag?.overfortLokaltTidspunkt ) {
			flash.errorMessage = "Advarsel! Dette intervjuobjektet er allerede ute hos en intervjuer. Hvis du fortsetter vil intervjuobjektet kunne havne hos to intervjuere!"		
		}
				
		def intervjuerList = Intervjuer.createCriteria().list() {
			'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
			'eq'("status", IntervjuerStatus.AKTIV)
		}
		
		def ioKlynge = intervjuObjektService.finnIntervjuObjektKlynge(intervjuObjekt)
		
		def intervjuerListKlynge = Intervjuer.createCriteria().list() {
			'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
			eq("status", IntervjuerStatus.AKTIV)
			if(ioKlynge) {
				eq("klynge", ioKlynge)
			}
		}
				
		// brukes for aa bestemme hvor vi skal redirecte etter tildeling (tilbake til liste, eller tilbake til io-edit)
		def redir = params.redir

		if(!intervjuObjekt?.periode?.skjema?.intervjuTypeBesok) {
			flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.tildel.ikke.capi.feilmelding', default: 'Dette intervjuobjektet tilhører et skjema som ikke er valgt som besøk, kan ikke tildele for CAPI')}"
			redirect(controller: "intervjuObjekt", action: "edit", params: [id: params.id])
			return
		}
				
		def valgtIntervjuerId
		def valgtIntervjuerIdKlynge
		
		def tildelIntervjuerCommand = new TildelIntervjuerCommand()


		if(params.intervjuerId != null) {
			tildelIntervjuerCommand.intervjuer = params.intervjuerId
		}

		// Finn forslag til sisteFrist dato basert på periode.sluttDato eller periode.planlagtSluttDato
		def forslagSisteFrist

		if(intervjuObjekt.periode?.sluttDato) {
			forslagSisteFrist = intervjuObjekt.periode?.sluttDato
		}
		else if(intervjuObjekt.periode?.planlagtSluttDato) {
			forslagSisteFrist = intervjuObjekt.periode?.planlagtSluttDato
		}

		[
			intervjuObjekt: intervjuObjekt,
			intervjuerList: intervjuerList,
			intervjuerListKlynge: intervjuerListKlynge,
			tildelIntervjuerCommand: tildelIntervjuerCommand,
			redir: redir,
			dato: forslagSisteFrist
		]
	}


	def finnNaboIntervjuer = {
		Long intervjuObjektId = Long.parseLong(params.id)

		def intervjuer = oppdragService.findNaboIntervjuer( IntervjuObjekt.get(intervjuObjektId))

		def intervjuerId = intervjuer.id

		redirect( action:"tildel", id:intervjuObjektId, params:[intervjuerId:intervjuerId, redir: params.redir])
	}


	def lagreTildel = {
		TildelIntervjuerCommand tildelIntervjuerCommand = new TildelIntervjuerCommand()
		def intervjuObjekt = IntervjuObjekt.get(params.intervjuObjektId)

		def intervjuerId

		if(!tildelIntervjuerCommand.validate()) {
			def intervjuerList = Intervjuer.createCriteria().list() {
				'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
				'eq'("status", IntervjuerStatus.AKTIV)
			}

			def ioKlynge = intervjuObjektService.finnIntervjuObjektKlynge(intervjuObjekt)

			def intervjuerListKlynge = Intervjuer.createCriteria().list() {
				'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
				eq("status", IntervjuerStatus.AKTIV)
				if(ioKlynge) {
					eq("klynge", ioKlynge)
				}
			}

			render(view: "tildel", model: [intervjuObjekt: intervjuObjekt, intervjuerList: intervjuerList, intervjuerListKlynge: intervjuerListKlynge, redir: params.redir, tildelIntervjuerCommand: tildelIntervjuerCommand, dato: tildelIntervjuerCommand.sisteFrist])
			return
		}

		def intervjuer

		if(params.intervjuer) {
			intervjuer = Intervjuer.get(params.intervjuer)
		}
		else if(params.intervjuerKlynge) {
			intervjuer = Intervjuer.get(params.intervjuerKlynge)
		}

		Date sisteFrist = tildelIntervjuerCommand.sisteFrist

		oppdragService.tildelIntervjuer( intervjuer, intervjuObjekt, sisteFrist, brukerService.getCurrentUserName())

		flash.message = "Tildelt intervjuer '" + intervjuer.initialer + "' til intervjuobjekt " + intervjuObjekt.navn + "."
		log.info "intervjuobjektet ${intervjuObjekt.id} er tildelt til ${intervjuer.initialer}"

		def altIBlaise5 = intervjuObjekt.periode?.skjema?.altIBlaise5 ?: false

		log.info "Blaise5 skjema: ${altIBlaise5}"
		if (!altIBlaise5) {
			synkroniseringService.synkroniserIntervjuObjektTildelCAPI(intervjuObjekt, intervjuer.initialer)
		}

		// hvis tildeling trigges fra intervjuObjekt.edit siden, skal vi tilbake dit!
		if(params.redir && params.redir == "io_edit" ) {
			redirect(controller: "intervjuObjekt", action: "edit", id: intervjuObjekt.id)
			return
		}
		else {
			redirect(action: "list")
			return
		}
	}

	def avbryt = {
		if(params.redir && params.redir == "io_edit" ) {
			redirect(controller: "intervjuObjekt", action: "edit", id: params.intervjuObjektId)
			return
		}
		else {
			redirect(action: "list")
			return
		}
	}

	def ajaxGetSkjemaList = {
		def prosjekt = Prosjekt.get(params.id)
		def sortertSkjemaList = SorteringsUtil.sorterPaaOppstartsDatoSkjemaSomIkkeErAvsluttet(prosjekt?.skjemaer)
		render sortertSkjemaList as JSON
	}

	def ajaxGetPeriodeList = {
		def skjema = Skjema.get(params.id)

		def periodeList = skjema?.perioder?.sort {it.periodeNummer}

		if( periodeList && periodeList.size() > 0 ) {
			render skjema?.perioder?.sort {it.periodeNummer} as JSON
		}
		else {
			render new ArrayList()
		}
	}


	def automatiskTildeling = {
		TildelAutomatiskCommand tildelAutomatiskCommand = new TildelAutomatiskCommand()
		if(!session.capiSearch) {
			redirect(action: "listKlynger")
		}

		def capiSearch = session.capiSearch

		// hvis validering feiler
		if(!tildelAutomatiskCommand.validate()) {
			def mIO
			def sFrist

			if(tildelAutomatiskCommand.errors.hasFieldErrors("sisteFrist") && tildelAutomatiskCommand.errors.hasFieldErrors("maxIO")) {
				flash.errorMessage = "${message(code: 'tildelAutomatiskCommand.sisteFrist.maxIo.feil', default: 'Dato for siste frist må være en gyldig dato (dd.mm.åååå) fram i tid og maksimalt antall IO pr. intervjuer må være et heltall')}"
			}
			else if(tildelAutomatiskCommand.errors.hasFieldErrors("sisteFrist")) {
				flash.errorMessage = "${message(code: 'tildelAutomatiskCommand.sisteFrist.feil', default: 'Dato for siste frist må være en gyldig dato (dd.mm.åååå) fram i tid')}"
				mIO = params.maxIO
			}
			else if(tildelAutomatiskCommand.errors.hasFieldErrors("maxIO")) {
				flash.errorMessage = "${message(code: 'tildelAutomatiskCommand.maxIo.feil', default: 'Maksimalt antall IO pr. intervjuer må være et heltall')}"

			}

			if(tildelAutomatiskCommand.sisteFrist) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
				sFrist = sdf.format(tildelAutomatiskCommand.sisteFrist)
			}

			redirect(action: "list", params: [maxIO: mIO, sisteFrist: sFrist])
			return
		}

		// io sok
		def intervjuObjekter  = capiAdministrasjonService.finnIntervjuObjekterForTildeling(
			capiSearch.skjema, capiSearch.periode, capiSearch.intervjuObjektNummer,
			capiSearch.tildelt, capiSearch.skjemaStatus, capiSearch.klynge, null)

		List intervjuere

		def klynge

		if(capiSearch.klynge) {
			klynge = Klynge.get(capiSearch.klynge)
		}

		// hvis spesiell gruppe intervjuere er valgt - velg intervjuere herfra
		if( capiSearch.catiGruppe != null ) {
			def catiGruppe = CatiGruppe.get(capiSearch.catiGruppe)

			intervjuere = new ArrayList()

			catiGruppe.intervjuere.each { Intervjuer intervjuer ->
				intervjuere.add(intervjuer)
			}
		}
		// eller gjor et intervju sok basert paa kriterier
		else {
			intervjuere = Intervjuer.createCriteria().list {
				if(klynge) {
					'eq'("klynge", klynge)
				}
				'eq'("status", IntervjuerStatus.AKTIV)
				'in'("arbeidsType", [IntervjuerArbeidsType.BEGGE, IntervjuerArbeidsType.BESOK])
			}
		}

		// hvis ingen intervjuere blir funnet
		if(!intervjuere) {
			def mIO = params.maxIO
			def sFrist

			if(tildelAutomatiskCommand.sisteFrist) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
				sFrist = sdf.format(tildelAutomatiskCommand.sisteFrist)
			}

			flash.errorMessage = "${message(code: 'tildel.automatisk.ingen.intervjuere', default: 'Fant ingen aktive intervjuere å tildele intervjuobjektene til')}"

			redirect(action: "list", params: [maxIO: mIO, sisteFrist: sFrist])
			return
		}

		// sett igang automatisk tildelingsprosess
		oppdragService.tildelIntervjuereAutomatisk(intervjuObjekter, intervjuere, tildelAutomatiskCommand.sisteFrist, tildelAutomatiskCommand.maxIO, tildelAutomatiskCommand.familieNummer, brukerService.getCurrentUserName())

		capiSearch.tildelt = "tildelt"

		session.capiSearch = capiSearch

		redirect(action: "list")
	}
}

class TildelIntervjuerCommand implements grails.validation.Validateable {
	Date sisteFrist
	String intervjuer
	String intervjuerKlynge

	static constraints = {
		sisteFrist(nullable: false, min: new Date())
		intervjuer validator: { value, command ->
			def melding
			if(!value && !command.intervjuerKlynge) {
				melding = "tildelIntervjuerCommand.intervjuerKlynge.blank"
			}
			else if(value && command.intervjuerKlynge) {
				melding = "tildelIntervjuerCommand.begge.valgt"
			}
			return melding
		}
	}
}

class TildelAutomatiskCommand implements grails.validation.Validateable {
	Integer maxIO
	Date sisteFrist
	boolean familieNummer
	
	static constraints = {
		maxIO nullable: true
		sisteFrist(nullable: true, min: new Date())
	}
}

class IntervjuObjektCapiSearchCommand implements grails.validation.Validateable {
	Long prosjekt
	Long skjema
	Long periode
	String intervjuObjektNummer
	Long klynge
	String tildelt
	Long catiGruppe
	SkjemaStatus skjemaStatus
}
