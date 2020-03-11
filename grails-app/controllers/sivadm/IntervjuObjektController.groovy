package sivadm

//import UtvalgImportService;
import grails.plugin.springsecurity.annotation.Secured
//import java.util.*
import siv.data.IntervjuObjektEndret
import siv.type.AvtaleType
import siv.type.ResultatStatus
import siv.type.SkjemaStatus
import siv.type.Kilde
import grails.converters.JSON

@Secured(['ROLE_ADMIN', 'ROLE_PLANLEGGER', 'ROLE_INTERVJUERKONTAKT', 'ROLE_SPORINGSPERSON', 'ROLE_CAPITILDELING', 'ROLE_SUPERVISOR'])
class IntervjuObjektController {
	
	// session variables: session.currentPosition, session.intervjuObjektIdList, session.searchData
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def intervjuObjektService
	def utvalgImportService
	def springSecurityService
	def brukerService
	XmlService xmlService
	SynkroniseringService synkroniseringService
	OppdragService oppdragService
	def intervjuerService


	def ajaxGetIntervjuObjektAdresse = {
		def io = IntervjuObjekt.get(params.id)
		def ioEndret = new IntervjuObjektEndret(adresse: "", poststed: "", produktNummer: "")
		
		if(io) {
			Adresse a = io.findGjeldendeBesokAdresse()
			if(a) {
				ioEndret.adresse = a.gateAdresse ?: ""
				ioEndret.poststed = a.postSted ?: ""
			}
			
			if(io.periode?.skjema?.delProduktNummer) {
				ioEndret.produktNummer = io.periode.skjema.delProduktNummer
			}
		}
		
		if(ioEndret) {
			render ioEndret as JSON
		}
	}
		
	def edit = {
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}

		Long currentIntervjuObjektId = new Long(params.id)
		
		def intervjuObjektInstance = IntervjuObjekt.get(currentIntervjuObjektId)
				
		def readOnly = false
		def laastAv
		
		if(params.se) {
			readOnly = true
		}
		else if(intervjuObjektInstance?.laastAv && intervjuObjektInstance?.laastAv != brukerService.getCurrentUserName()) {
			// IO er allerede låst -> kun se ikke lagre
			readOnly = true
			laastAv = intervjuObjektInstance?.laastAv
		}
		else {
			// Lås IO for redigering av andre brukere
			if(intervjuObjektService.laasIo(intervjuObjektInstance, brukerService.getCurrentUserName())) {
				session.laastIoId = intervjuObjektInstance.id
			}
		}
						
		def listePosisjon
		def listeAntall
		def visAlleTelefon = false
		def visAlleAdresse = false
		
		if(params.visAlleTelefon) {
			visAlleTelefon = params.visAlleTelefon 
		}
		
		if(params.visAlleAdresse) {
			visAlleAdresse = params.visAlleAdresse
		}
			
		List<Long> intervjuObjektIdList = session.intervjuObjektIdList
		
		if(intervjuObjektIdList) {
			// algoritme for aa sette neste og forrige id verdier for frem og tilbake knapp	
			for( int i=0; i<intervjuObjektIdList.size(); i++ ) {	
				Long id = intervjuObjektIdList.get(i)
				
				if(id.equals(currentIntervjuObjektId)) {
					session.currentPosition = i
				}
			}
		
			listeAntall = intervjuObjektIdList.size()
			if(session.currentPosition >= 0) {
				listePosisjon = session.currentPosition + 1
			}
		}
		
		if(!intervjuObjektInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
			redirect(action: "list")
		}
		else {
			
			def adresse = intervjuObjektInstance.findGjeldendeBesokAdresse()
			
			if(!adresse) {
				adresse = intervjuObjektInstance.findGjeldendePostAdresse()
			}
			
			def intervjuerInitialer = intervjuObjektInstance.intervjuer
			
			def sisteIntervjuer
			
			if( intervjuerInitialer ) {
				sisteIntervjuer = Intervjuer.findByInitialer( intervjuerInitialer )
			}
			
			def kommune
			
			def intervjuStatusKoder = hentIntervjuStatusKoder()
			
			if(adresse?.kommuneNummer) {
				kommune = Kommune.findByKommuneNummer(adresse.kommuneNummer)	
			}		

			[
				intervjuObjektInstance: intervjuObjektInstance,
				kommuneInstance: kommune,
				sisteIntervjuer: sisteIntervjuer,
				listePosisjon: listePosisjon,
				listeAntall: listeAntall,
				visAlleTelefon: visAlleTelefon,
				visAlleAdresse: visAlleAdresse,
				readOnly: readOnly,
				laastAv: laastAv,
				intervjuStatusKoder: intervjuStatusKoder,
				assosiertIntervjuStatus: params.assosiertIntervjuStatus
			]
		}
	}
	
	
	/**
	 * Returnerer en oversikt over intervjustatuser som blir brukt.
	 * 
	 * @return
	 */
	private String hentIntervjuStatusKoder() {
		StringBuffer intervjuStatusKoder = new StringBuffer()

		intervjuStatusKoder.append("0: Besvart, ")
		intervjuStatusKoder.append("11: Ikke tid, ")
		intervjuStatusKoder.append("12: Ønsker ikke å delta, ")
		intervjuStatusKoder.append("13: Deltar ikke av prinsipp, ")
		intervjuStatusKoder.append("14: Andre nekter for svarperson / IO, ")
		intervjuStatusKoder.append("15: Har ikke lest IO-brev, ")
		intervjuStatusKoder.append("21: Kortvarig sykdom, ")
		intervjuStatusKoder.append("22: Langvarig sykdom, svekkelse, ")
		intervjuStatusKoder.append("23: Sykdom / dødsfall i familien, annet uforutsett, ")
		intervjuStatusKoder.append("24: Språkproblemer, ")
		intervjuStatusKoder.append("25: Langvarig syk, uaktuelt med kontakt senere., ")
		intervjuStatusKoder.append("31: Midlertidig fraværende pga skolegang / arbeid, ")
		intervjuStatusKoder.append("32: Midlertidig fraværende pga ferie e.l., ")
		intervjuStatusKoder.append("33: Finner ikke adressen / boligen eller retur fra posten, ")
		intervjuStatusKoder.append("34: Ikke telefon - for kostbart / langt å reise, ")
		intervjuStatusKoder.append("35: Ikke å treffe av andre årsaker, ")
		intervjuStatusKoder.append("36: Tom bolig(lmh/lmu), ")
		intervjuStatusKoder.append("37: Finner ikke ny beboer (lmh/lmu), ")
		intervjuStatusKoder.append("39: Frafall - Finner ikke / får ikke tak i IO, parkeres, ")
		intervjuStatusKoder.append("41: Frafall - Andre Frafallsgrunner, ")
		intervjuStatusKoder.append("80: Flyttet til annet område, ")
		intervjuStatusKoder.append("81: Kjenner enheten, ")
		intervjuStatusKoder.append("82: Intervjuer kapasitetsproblemer, sykdom e.l., ")
		intervjuStatusKoder.append("83: IO skal fjernes fra IO-lista., ")
		intervjuStatusKoder.append("85: Io er allerede intervjuet (lmh), ")
		intervjuStatusKoder.append("91: Død, ")
		intervjuStatusKoder.append("92: Bosatt i utlandet i minst 6 mnd, ")
		intervjuStatusKoder.append("93: Bosatt i institusjon, ")
		intervjuStatusKoder.append("94: Utenfor målgruppa, ")
		intervjuStatusKoder.append("96: Boligen er ikke lenger en utleiebolig (lmh), ")
		intervjuStatusKoder.append("97: Boligen er en institusjon (lmh/lmu), ")
		intervjuStatusKoder.append("98: Spesifiser (lmh)")
	

		return intervjuStatusKoder.toString()
	}

	def update = {				
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
				
		def intervjuObjektInstance = IntervjuObjekt.get(params.id)
		
		if (intervjuObjektInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (intervjuObjektInstance.version > version) {
					def kommune
					def intervjuer
					def adresse = intervjuObjektInstance.findGjeldendePostAdresse()
					def oppdrag = Oppdrag.findByIntervjuObjekt(intervjuObjektInstance)
						
					if(adresse?.kommuneNummer) {
						kommune = Kommune.findByKommuneNummer(adresse.kommuneNummer)
					}
					
					intervjuer = oppdrag?.intervjuer ?: null
					
					intervjuObjektInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt')] as Object[], "Another user has updated this IntervjuObjekt while you were editing")
					render(view: "edit", model: [intervjuObjektInstance: intervjuObjektInstance, kommuneInstance: kommune, intervjuerInstance: intervjuer])
					return
				}
			}
			
			Integer intStat = null
			
			if(params.intervjuStatus && !params.intervjuStatus.equals("")) {
				intStat = new Integer(params.intervjuStatus)
			}
			
			def historikk = intervjuObjektService.sjekkStatusEndring(intervjuObjektInstance, SkjemaStatus.valueOf(params.katSkjemaStatus), intStat, brukerService.getCurrentUserName())
			
			// Disse brukes for å sjekke og eventuelt sett tilbake
			// katSkjemaStatus hvis denne er endret til Pa_vent i klient
			// som følge av feil i på vent dato
			def tempPaVentDato = intervjuObjektInstance.paVentDato
			def tempSkjemaStatus = intervjuObjektInstance.katSkjemaStatus			
			def tempIntervjuStatus = intervjuObjektInstance.intervjuStatus
			
			intervjuObjektInstance.properties = params
			
			intervjuObjektInstance.redigertAv = brukerService.getCurrentUserName()
									
			def statusFeilmelding
			def endret = intervjuStatusEndret(tempIntervjuStatus, intervjuObjektInstance.intervjuStatus)
						
//			if(endret && intervjuObjektInstance?.intervjuStatus?.value == 0) {
//				statusFeilmelding = "Kan ikke sette intervjustatus til 0, dette kan kun gjøres fra Blaise."
//				intervjuObjektInstance.intervjuStatus = tempIntervjuStatus
//			}
			// else 
			if(historikk) {
				// Brukeren har endret på IntervjuStatus eller SkjemaStatus
				statusFeilmelding = validerIntervjuOgSkjemaStatus(intervjuObjektInstance.katSkjemaStatus, intervjuObjektInstance.intervjuStatus, intervjuObjektInstance.paVentDato)
				if(statusFeilmelding) {
					intervjuObjektInstance.intervjuStatus = tempIntervjuStatus
				}
			}
									
			if(intervjuObjektInstance.hasErrors() || statusFeilmelding) {
				def kommune
				def intervjuer
				def adresse = intervjuObjektInstance.findGjeldendePostAdresse()
				def oppdrag = Oppdrag.findByIntervjuObjekt(intervjuObjektInstance)
				
				if(adresse?.kommuneNummer) {
					kommune = Kommune.findByKommuneNummer(adresse.kommuneNummer)
				}
								
				if(intervjuObjektInstance.errors.hasFieldErrors("paVentDato") && !tempPaVentDato) {
					intervjuObjektInstance.katSkjemaStatus = tempSkjemaStatus
				}
								
				intervjuer = oppdrag?.intervjuer ?: null
				
				if(statusFeilmelding) {
					intervjuObjektInstance.katSkjemaStatus = tempSkjemaStatus
					flash.errorMessage = statusFeilmelding
				}
											
				render(view: "edit", model: [intervjuObjektInstance: intervjuObjektInstance, kommuneInstance: kommune, intervjuerInstance: intervjuer])
				return	
			}
				
			if(historikk) {
				historikk.save()
				intervjuObjektInstance.addToStatusHistorikk(historikk)
			}
								
			if(intervjuObjektInstance.save(flush: true)) {
				def altIBlaise5 = intervjuObjektInstance.periode?.skjema?.altIBlaise5 ?: false
				log.info "Blaise5 skjema: ${altIBlaise5}"
				if (!altIBlaise5) {
					// kaller synk service
					synkroniseringService.synkroniserIntervjuObjektEndring(intervjuObjektInstance.id, false)

				} else {
					def isSkjemaStatusEndret = tempSkjemaStatus != intervjuObjektInstance.katSkjemaStatus
					if (isSkjemaStatusEndret) {
						synkroniseringService.synkroniserIntervjuObjektEndringSkjemastatus(intervjuObjektInstance)
					}

					if(!intervjuObjektInstance.bareWebSkjema()) {
						synkroniseringService.synkroniserIntervjuObjektEndringV5(intervjuObjektInstance)
					}

				}

				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), intervjuObjektInstance.navn])}"
				redirect(action: "edit", id: intervjuObjektInstance.id)
			}
			else {
				def kommune
				def intervjuer
				def adresse = intervjuObjektInstance.findGjeldendePostAdresse()
				def oppdrag = Oppdrag.findByIntervjuObjekt(intervjuObjektInstance)
								
				if(adresse?.kommuneNummer) {
					kommune = Kommune.findByKommuneNummer(adresse.kommuneNummer)
				}
				
				if(intervjuObjektInstance.errors.hasFieldErrors("paVentDato") && !tempPaVentDato && historikk) {
					intervjuObjektInstance.katSkjemaStatus = tempStatus
					intervjuObjektInstance.removeFromStatusHistorikk(historikk)
				}
				
				intervjuer = oppdrag?.intervjuer ?: null
												
				render(view: "edit", model: [intervjuObjektInstance: intervjuObjektInstance, kommuneInstance: kommune, intervjuerInstance: intervjuer])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def intervjuObjektInstance = IntervjuObjekt.get(params.id)
		if (intervjuObjektInstance) {
			try {
				intervjuObjektInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
				redirect(action: "searchResult")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
				redirect(action: "edit", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
			redirect(action: "searchResult")
		}
	}
	
	def lagAvtale = {
		def intervjuObjektInstance = IntervjuObjekt.get(params.id)
		
		if(intervjuObjektInstance) {
			def blaiseApplicationPath = grailsApplication.config.getProperty("blaiseApplicationPath")
			def blaiseCapiSkjemaPath = grailsApplication.config.getProperty("blaiseCapiSkjemaPath")
			
			def skjemaKortNavn
			def skjemaVersjon
		
			if(intervjuObjektInstance?.periode?.skjema) {
				skjemaKortNavn = intervjuObjektInstance?.periode?.skjema?.skjemaKortNavn
				skjemaVersjon = intervjuObjektInstance?.periode?.skjema?.skjemaVersjoner?.sort{it.versjonsNummer}.reverse()[0]
				
				if( skjemaVersjon == null ) {
					flash.errorMessage = "Det ser ut til at skjemaet mangler skjemaversjon. Dette må opprettes!"
					redirect(controller: "intervjuObjekt", action: "edit", params: [id: params.id])
				}
			}
			else {
				flash.errorMessage = "${message(code: 'sivadm.intervjuobjekt.lag.avtale.feilmelding', default: 'Finner ikke skjema tilhørende dette intervjuobjektet så kan ikke opprette avtale, vennligst kontakt administrator')}"
				redirect(controller: "intervjuObjekt", action: "edit", params: [id: params.id])
				return
			}
					
			[
				blaiseApplicationPath: blaiseApplicationPath,
				blaiseCapiSkjemaPath: blaiseCapiSkjemaPath,
				intervjuobjektId: intervjuObjektInstance.id,
				skjemaKortNavn: skjemaKortNavn,
				skjemaVersjon: skjemaVersjon?.versjonsNummer
			]
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt'), params.id])}"
			redirect(action: "searchResult")
		}
	}
	
	def persistSearch = {

		IntervjuObjektSearch intervjuObjektSearch = new IntervjuObjektSearch()
		bindData(intervjuObjektSearch, params)
		intervjuObjektSearch.lagret = new Date()
		intervjuObjektSearch.lagretAv = brukerService.getCurrentUserName()
		if (intervjuObjektSearch.persisterSokeResultat) {
			intervjuObjektSearch.intervjuObjektSearchResult = xmlService.getIntervjuObjektSearchResultXml(intervjuObjektSearch)
		}
		intervjuObjektSearch.save(flush: true, failOnError: true)
		session.searchData = null;
		redirect(action: "list", controller: "intervjuObjektSearch")
	}
	
	def editSearch = {
		def intervjuObjektSearch = IntervjuObjektSearch.get(params.searchId)
		bindData(intervjuObjektSearch, params)
		intervjuObjektSearch.lagret = new Date()
		intervjuObjektSearch.lagretAv = brukerService.getCurrentUserName()
		if (intervjuObjektSearch.persisterSokeResultat) {
			intervjuObjektSearch.intervjuObjektSearchResult = xmlService.getIntervjuObjektSearchResultXml(intervjuObjektSearch)
		} else {
			intervjuObjektSearch.intervjuObjektSearchResult = null
		}
		intervjuObjektSearch.save(flush: true, failOnError: true)
		session.searchData = null;
		redirect(action: "list", controller: "intervjuObjektSearch")
	}
		
	def searchResult = {

		// Hvis et IO er låst, lås opp dette
		if(session.laastIoId) {
			intervjuObjektService.laasOppIo(session.laastIoId)
			session.removeAttribute("laastIoId")
		}
		
		params.max = Math.min(params.max ? params.int('max') : 15, 100)
//		params.max = Math.min(params.max ? params.int('max') : 2, 100)
		
		def searchData		
		
		if( params.sokPerfomed && params.sokPerfomed == "true" ) {
			searchData = new IntervjuObjektSearch()
			bindData(searchData, params)
			session.searchData = searchData
			session.ioOffset = null
		} else {
			if(params.searchId) {
				session.searchData = IntervjuObjektSearch.get(params.searchId)
				session.searchId = params.searchId
			} else {
				session.searchId = null
				session.searchData?.sokeNavn = null
			}
			searchData = session.searchData
		}
		
		if(params.offset) {
			session.ioOffset = params.offset
		}
		else if(session.ioOffset) {
			params.offset = session.ioOffset
		}
		
		if(params.sort) {
			session.ioSort = params.sort
			session.ioOrder = params.order
		}
		else {
			params.sort = session.ioSort
			params.order = session.ioOrder
		}	
				
		def intervjuObjektInstanceList
		def intervjuObjektIdList
		def utvidetIntervjuObjektList
		
		session.listeData = searchData


		if(searchData != null) {

			intervjuObjektInstanceList = intervjuObjektService.search(searchData, params)

			if (intervjuObjektInstanceList.size() > 0) {

				if(searchData.assosiertSkjema) {
					utvidetIntervjuObjektList =
						intervjuObjektService.lagUtvidetIntervjuObjektListe(intervjuObjektInstanceList, searchData.assosiertSkjema)
				}else {
					utvidetIntervjuObjektList =
							intervjuObjektService.lagUtvidetIntervjuObjektListe(intervjuObjektInstanceList)
				}

			}

			intervjuObjektIdList = intervjuObjektService.idSearch(searchData, params)
		}
		
		session.intervjuObjektIdList = intervjuObjektIdList
		session.intervjuObjektInstanceList = utvidetIntervjuObjektList
		
		def intervjuObjektInstanceTotal
		

		if( intervjuObjektInstanceList == null ) {
			intervjuObjektInstanceTotal = 0
		}
		else {
			intervjuObjektInstanceTotal = intervjuObjektService.countSearch(searchData)
		}

		def intervjuStatusKoder = hentIntervjuStatusKoder()

		[
		'intervjuObjektInstanceList': utvidetIntervjuObjektList,
		'intervjuObjektInstanceTotal': intervjuObjektInstanceTotal,
		'skjemaStatusList': SkjemaStatus.values(),
		'avtaleTyper': AvtaleType.values(),
		'kildeList': Kilde.values(),
		'resultatStatusList': ResultatStatus.values(),
		'skjemaList': Skjema.list(), 
		'searchData': searchData,
		'ioCnt': IntervjuObjekt.count(),
		'intervjuStatusKoder': intervjuStatusKoder,
		'searchId': session.searchId
		]
	}

	def searchResultNullstill = {
		session.removeAttribute("searchData")
		session.removeAttribute("intervjuObjektIdList")
		session.removeAttribute("intervjuObjektInstanceList")
		session.removeAttribute("currentPosition")
		
		redirect( action:"searchResult")
	}
	
	def searchResultLastNed = {
		def filPath = grailsApplication.config.getProperty("utvalg.eksport.fil.path")
		def filNavn = "IntobjSok" + "_" + System.currentTimeMillis() + ".csv"
		def filPathOgNavn = filPath + filNavn
		
//		Vi har allerede en liste med intervjuobjekter fra søket i def searchResultLastNed
		if (session.listeData != null) {				
			IntervjuObjektSearch searchData = new IntervjuObjektSearch()
			searchData.kopier(session.listeData)
			try	{
				def intervjuObjektInstanceList = intervjuObjektService.searchAll(searchData)
				utvalgImportService.skrivUtvalgListeTilFil( intervjuObjektInstanceList, filPathOgNavn )
			}
			catch(Exception e) {
				log.error( e.getMessage())
			}
			File filTilNedlastning = new File(filPathOgNavn)
			if (filTilNedlastning!=null && filTilNedlastning.exists() && filTilNedlastning.canRead()){
				response.setContentType("text/csv")
				response.setHeader("Content-disposition", "filename=${filNavn}")
				response.outputStream << filTilNedlastning.getBytes() // kanskje getBytes("UTF8") ?
				response.outputStream.flush()
			}else {
				flash.message = "Generering av resultatfil feilet. Kontakt SIV ansvarlig."
			}
		}
		else {
			flash.message = "Finner ingen liste å skrive ut"
		}
	}	
	
	def visIntervjuerListe = {
		
		// sett utfylte sokefelter i session for ikke aa miste verdier der
		def searchData
		
		if( params.sokPerfomed && params.sokPerfomed == "true" ) {
			searchData = new IntervjuObjektSearch()
			bindData(searchData, params)
			session.searchData = searchData
			session.ioOffset = null
		}
		
		def intervjuerInstanceList = intervjuerService.finnAktiveIntervjuere(params)
		
		[ 'intervjuerInstanceList': intervjuerInstanceList]
	}
	
	def velgIntervjuer = {
		def initialer = params.id
		
		if( session.searchData == null ) {
			session.searchData = new IntervjuObjektSearch()
		}
		
		session.searchData.initialer = initialer
		
		redirect( action:"searchResult")
	}
	
	
	def laasOppIo = {
		if(session.laastIoId) {
			intervjuObjektService.laasOppIo(session.laastIoId)
			session.removeAttribute("laastIoId")
		}
		redirect(action: "edit", id: params.id, params: [se: true])
	}
	
	def previousIntervjuObjekt = {
		def preId
		
		// TODO; sjekk session hvis ikke satt -> gå til startside
		
		if(session.laastIoId) {
			intervjuObjektService.laasOppIo(session.laastIoId)
			session.removeAttribute("laastIoId")
		}
				
		if(session.currentPosition > 0 ) {
			preId = session.intervjuObjektIdList.get(session.currentPosition-1)
		} 
		else {
			preId = session.intervjuObjektIdList.get(0)
		}
		
		redirect(action: "edit", id: preId, params: [se: params.se])
	}
	
	def nextIntervjuObjekt = {
		def nextId 
		
		// TODO; sjekk session hvis ikke satt gå til startside
		
		if(session.laastIoId) {
			intervjuObjektService.laasOppIo(session.laastIoId)
			session.removeAttribute("laastIoId")
		}
		
		if( session.currentPosition < session.intervjuObjektIdList.size() - 1) {
			nextId = session.intervjuObjektIdList.get(session.currentPosition + 1)
		}
		else {
			nextId = session.intervjuObjektIdList.get(session.intervjuObjektIdList.size() - 1)
		}
		
		redirect(action: "edit", id: nextId, params: [se: params.se])
	}
	
	
	def statusChange = {
		IntervjuObjektSearch searchData = new IntervjuObjektSearch();
		bindData(searchData, params)
		
		def intervjuObjektInstanceList = intervjuObjektService.searchAll( searchData )
		
		[intervjuObjektInstanceList: intervjuObjektInstanceList, skjemaStatusList: SkjemaStatus.values(), statusCommand: new StatusCommand()]
	}
	
	def statusChangeResult = {
		StatusCommand statusCommand = new StatusCommand()
		if(!brukerService.getCurrentUserName()) {
			redirect(controller: "logout", action:"index")
			return
		}
		
		if(!statusCommand.validate() ) {
			if(session.searchData) {
				IntervjuObjektSearch searchData = session.searchData
				def intervjuObjektInstanceList = intervjuObjektService.searchAll( searchData )
				render(view: "statusChange", model: [intervjuObjektInstanceList: intervjuObjektInstanceList, skjemaStatusList: SkjemaStatus.values(), statusCommand: statusCommand])
				return
			}
			else {
				flash.errorMessage = "${message(code: 'xx', default: 'Kan ikke sette intervjustatus som ikke er et heltall, vennligst prøv igjen')}"
				redirect(action: "searchResult")
				return
			}
		}
		
		def checkValues = request.getParameterValues("check")
						
		int antallEndret = 0
		
		checkValues.each { 
			IntervjuObjekt io = IntervjuObjekt.get(new Long( "${it}" ))
			// Sjekk om ny status er endring av status
			StatHist historikk = intervjuObjektService.sjekkStatusEndring(io, SkjemaStatus.valueOf(params.skjemaStatus), statusCommand.intervjuStatus ?: io.intervjuStatus, brukerService.getCurrentUserName())
			// Hvis historikk ikke er null er ny status endring av status
			if(historikk) {
				// Sett ny status, legg til historikk og lagre IO
				historikk.save()
				
				io.addToStatusHistorikk(historikk)
				
				io.katSkjemaStatus = SkjemaStatus.valueOf(params.skjemaStatus)
				
				if( params.meldingTilIntervjuer ) {
					io.meldingTilIntervjuer = params.meldingTilIntervjuer					
				}
				
				if(statusCommand.intervjuStatus != null) {
					io.setIntervjuStatus statusCommand.intervjuStatus
				}
		
				if(io.save(flush: true )) {

					def altIBlaise5 = io.periode?.skjema?.altIBlaise5 ?: false
					if (altIBlaise5) {
						if(!io.bareWebSkjema()) {
							synkroniseringService.synkroniserIntervjuObjektEndringV5(io)
						}
					}else{
						synkroniseringService.synkroniserIntervjuObjektEndring(io.id, true)
					}
					// kaller synk service


					antallEndret++
				}		
			}
		}
		
		flash.message = "Endret status for " + antallEndret + " intervjuobjekter."
		
		redirect(action:"searchResult")
	}
	
	def hentUnikeKontaktperioder = {
		def skjemaId = Long.parseLong(params.skjemaId)
		def kontaktperioder = intervjuObjektService.hentUnikeKontaktperioder(skjemaId)
		Map responsMap = [
			'data': kontaktperioder
		]
		render responsMap as JSON
	}
	
	def hentUnikeDelutvalg = {
		def skjemaId = Long.parseLong(params.skjemaId)
		def delutvalg = intervjuObjektService.hentUnikeDelutvalg(skjemaId)
		Map responsMap = [
			'data': delutvalg
		]
		render responsMap as JSON
	}
	
	private String validerIntervjuOgSkjemaStatus(SkjemaStatus skjemaStatus,Integer intervjuStatus, Date paVent) {
		// Valideringsregler finnes i dokumentet Q:\DOK\Siv\2011\integrasjon\SivAdm io status.xls
		def msg
		
		if( (skjemaStatus == SkjemaStatus.Innlastet) && intervjuStatus) {
			msg = "${message(code: 'intervjuObjekt.skjemastatus.innlastet.feil', default: 'Intervjustatus må være blank når skjemastatus er innlastet')}"
		}
		else if( (skjemaStatus == SkjemaStatus.Ferdig) && intervjuStatus == null ) {
			msg = "${message(code: 'intervjuObjekt.skjemastatus.ferdig.feil', default: 'Intervjustatus må settes når skjemastatus settes til ferdig')}"
		}
		else if( (skjemaStatus == SkjemaStatus.Paa_vent) && !paVent) {
			msg = "${message(code: 'intervjuObjekt.paVentDato.ikke.satt', default: 'Kan ikke sette skjemastatus til På vent uten å sette På vent til dato.')}"
		}
				
		return msg	
	}
	
	private boolean intervjuStatusEndret(Integer statusEn, Integer statusTo) {
		if(statusEn == null && statusTo == null) {
			return false	
		}
		
		if((statusEn == null && statusTo != null) || (statusEn != null && statusTo == null)) {
			return true
		}
		
		if(statusEn?.value == statusTo?.value) {
			return false
		}
		return true
	}
}

class StatusCommand implements grails.validation.Validateable {
	Integer intervjuStatus
	
	static constraints = {
		intervjuStatus(nullable: true)
	}
}