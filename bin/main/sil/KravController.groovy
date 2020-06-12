package sil

import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured

import java.lang.reflect.UndeclaredThrowableException;
import java.text.SimpleDateFormat

import exception.FinnerIkkeProduktNummerException;

import grails.core.*
import sil.data.*
import sil.search.*
import sil.type.*
import siv.type.*
import sivadm.*

@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class KravController {
	
	def utilService
	def sivAdmService
	def automatiskKontrollService
	def kravService
	def sapFilService
	def brukerService
	def loggService
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm")
	
	def administrasjon = {	
	}
							
	def importerKrav = {
		
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		ImporterKravJob.triggerNow( [ bruker: brukerService.getCurrentUserName() ] )
		
		flash.message = "${message(code: 'sil.bakgrunnsjobb.startet', args: [])}"
		
		redirect(action: "administrasjon")
	}
	
	def kjorAutoKontroller = {
		
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		KjorAutomatiskeKontrollerJob.triggerNow( [ bruker: brukerService.getCurrentUserName() ])
		
		flash.message = "${message(code: 'sil.bakgrunnsjobb.startet', args: [])}"
		
		redirect(action: "administrasjon")
	}
	
	def finnKravTilManuellKontroll = {
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		def logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Genererer utvalg til manuell kontroll", "Starter generering", brukerService.getCurrentUserName())
		
		List<Krav> kravListe = kravService.finnKravTilManuellKontroll()
		
		if(!kravListe) {
			flash.message = "${message(code: 'sil.generer.utvalg.til.man.kontroll.tom.liste', args: [])}"
		}
		else {
			flash.message = "${message(code: 'sil.generer.utvalg.til.man.kontroll.info.melding', args: [kravListe.size(), 'Intervjuere til kontroll'])}"
		}
		
		loggService.stoppProsess(logg, "Generering ferdig.", false)
		
		redirect(action: "administrasjon")
	}
			
	/**
	 * Fjern søke kriteria fra session
	*/
	def searchResultNullstill = {
		session.removeAttribute("searchDataKrav")
		redirect(action: "searchResult")
	}
	
	/**
	 * Søk i Krav. Hvis søk knappen i searchResult.gsp (sokPerformed == true) er
	 * brukt settes søke kriteriaene i session og søket gjennomføres, hvis
	 * søke kriteria fra session finnes brukes disse, hvis ikke blir
	 * gjort et "blankt" søk.
	*/
	def searchResult = {
		params.max = Math.min(params.max ? params.int('max') : 20, 500)
		
		def searchDataKrav
		def pageTitleInfo
		def kravInstanceList
		def kravIdList
		
		if(params.sokPerfomed && params.sokPerfomed == "true" ) {
			searchDataKrav = new KravSearch();
			bindData(searchDataKrav, params)
			session.searchDataKrav = searchDataKrav
			if(searchDataKrav.fraDato && searchDataKrav.tilDato) {
				if(searchDataKrav.tilDato.time < searchDataKrav.fraDato.time) {
					flash.errorMessage = "${message(code: 'sil.til.dato.for.fra.dato', args: [], default: 'Til dato er før fra dato')}"
				}
			}
		}
		else {
			searchDataKrav = session.searchDataKrav
		}

		if( searchDataKrav == null) {
			searchDataKrav = new KravSearch()
		} else {
			kravInstanceList = kravService.search(searchDataKrav, params)
			kravIdList = kravService.hentKravIdListe(searchDataKrav)
		}
		session.kravIdList = kravIdList
		
		def kravInstanceTotal
		
		if( kravInstanceList == null ) {
			kravInstanceTotal = 0
		}
		else {
			kravInstanceTotal = kravService.countSearch(searchDataKrav)
		}
				
		[
		'pageTitleInfo': pageTitleInfo,
		'kravInstanceList':kravInstanceList,
		'kravInstanceTotal': kravInstanceTotal,
		'searchDataKrav':searchDataKrav
		]
	}
	
	
	def skrivTilKostGodtgjorelseRapport = {
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		def logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Eksporterer kostgodtgjørelserapport", "Starter eksport", brukerService.getCurrentUserName())
		
		def kostGodtListe
		
		try {
			kostGodtListe = sapFilService.skrivGodkjenteKravTilKostGodtgjorelseRapport()
		}
		catch(Exception e) {
			e.printStackTrace()
			loggService.stoppProsess(logg, "Prosessen med å opprette kostgodtgjørelsesrapport har stanset på grunn av en feil: " + e.getMessage(), true)
			flash.errorMessage = "Prosessen med å opprette kostgodtgjørelsesrapport har stanset på grunn av en feil: " + e.getMessage()
			redirect(action: "administrasjon")
			return
		}
		
		
		if(!kostGodtListe) {
			flash.message = "Fant ingen krav om kostgodtgjørelse med status Godkjent. Ingen krav sendt til rapport."
		}
		else {
			flash.message = "Fant " + kostGodtListe.size() + " krav om kostgodtgjørelse med status Godkjent. Disse er nå tilgjengelige i rapporten om kostgodtgjørelse."
		}
		
		loggService.stoppProsess(logg, "Eksport ferdig.", false)
		
		redirect(action: "administrasjon")
		return
	}
	
	
	def skrivTilSapFiler = {
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		OpprettSapFilerJob.triggerNow([ bruker: brukerService.getCurrentUserName() ])
		
		flash.message = "${message(code: 'sil.bakgrunnsjobb.startet', args: [])}"
		
		redirect(action: "administrasjon")
	}
	
	
	def godkjennResten = {
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		GodkjennAlleSomBestodAutomatiskKontrollJob.triggerNow([ bruker: brukerService.getCurrentUserName() ])
		
		flash.message = "${message(code: 'sil.bakgrunnsjobb.startet', args: [])}"
		
		redirect(action: "administrasjon")
	}
	
	
	def importerKravKjorKontrollerOgUtvalgManuellKontroll = {
		boolean prosessAlleredeStartet = loggService.sjekkOmProsessErAktiv(LoggType.SIL_BAKGRUNN_PROSESS)
		
		if( prosessAlleredeStartet ) {
			flash.errorMessage = "${message(code: 'sil.bakgrunnsjobb.allerede.startet', args: [])}"
			redirect(action: "administrasjon")
			return
		}
		
		ImporterKravKjorKontrollerJob.triggerNow( [ bruker: brukerService.getCurrentUserName() ] )
		
		flash.message = "${message(code: 'sil.bakgrunnsjobb.startet', args: [])}"
				
		redirect(action: "administrasjon")
	}
	
	
	def intervjuerKontrollNullstill = {
		session.removeAttribute("searchDataIntervjuer")
		redirect(action: "intervjuerKontroll")
	}
	
	
	def intervjuerKontroll = {
		params.max = Math.min(params.max ? params.int('max') : 25, 500)
		
		def searchDataIntervjuer
		
		if(params.sokPerformed && params.sokPerformed == "true" ) {
			searchDataIntervjuer = new IntervjuerKontrollSearch();
			bindData(searchDataIntervjuer, params)
			session.searchDataIntervjuer = searchDataIntervjuer
		}
		else {
			searchDataIntervjuer = session.searchDataIntervjuer
			if( searchDataIntervjuer == null) {
				searchDataIntervjuer = new IntervjuerKontrollSearch()
			}
		}
		
		def intervjuerInstanceList = []
		def intervjuerKontrollInstanceList = kravService.searchIntervjuer(searchDataIntervjuer, params)
		def intervjuerIdList = kravService.getAllIdsIntervjuer(searchDataIntervjuer)
		session.intervjuerIdList = intervjuerIdList
		
		def intervjuerInstanceTotal
		
		if( intervjuerInstanceList == null ) {
			intervjuerInstanceTotal = 0
		}
		else {
			intervjuerInstanceTotal = kravService.countSearchIntervjuer(searchDataIntervjuer)
		}
		
		
		[
		'intervjuerKontrollInstanceList': intervjuerKontrollInstanceList,
		'intervjuerInstanceList': intervjuerInstanceList,
		'intervjuerInstanceTotal': intervjuerInstanceTotal,
		'searchDataIntervjuer': searchDataIntervjuer
		]
	}
	
	def visKrav = {
		Krav krav = Krav.get(params.id)
		def intId = krav.intervjuer.id
		redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
	}
	
	def visFeiletKrav = {
		Krav krav = Krav.get(params.id)
		
		// Hvis krav er kjørebok - vis kravet i kravskjema
		// Hvis ikke sjekk om kravet er time eller utlegg
		// som er knyttet opp mot kjørebok, i så fall vis 
		// kjørebokkravet
		if(krav.kravType != KravType.K) {
			// Hvis krav er time eller utlegg knyttet opp mot
			// kjørebok, vis kjørebok kravet i skjema
			
			// Sjekk om kravet er underliggende et kjørebok-krav og hvis så
			// er det dette kravet som skal vises i skjema
			Krav kr = kravService.finnTilhorendeKjorebokKrav(krav)
			if(kr) {
				krav = kr
			}
		}
		
		def intId = krav.intervjuer.id
		redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: krav.id])
	}
	
	def show = {
		def kravInstance = Krav.get(params.id)
		def nyttKrav
		
		if(kravInstance.kravStatus == KravStatus.RETTET_AV_INTERVJUER) {
			// Todo: finn nytt krav som er erstatning for dette
			nyttKrav = Krav.findByOpprinneligKrav(kravInstance)
		}
		
		[
			kravInstance: kravInstance,
			nyttKrav: nyttKrav
		]
	}
	
	def behandleIntervjuerKrav = {
		Long currentIntervjuerId = new Long(params.id)
		
		def kravInstance = null
		def opprinneligKravInstance = null
		
		boolean isFailed = false
		
		if(params.kravId) {
			kravInstance = Krav.get(params.kravId)
		}
		
		if(params.feiletId) {
			kravInstance = Krav.get(params.feiletId)
			isFailed = true
		}
				
		if(kravInstance?.kravStatus == KravStatus.RETTET_AV_INTERVJUER || kravInstance?.kravStatus == KravStatus.OVERSENDT_SAP) {
			redirect(action: "show",  params: [id: kravInstance.id])
			return
		}
				
		if(kravInstance?.opprinneligKrav) {
			opprinneligKravInstance = kravInstance.opprinneligKrav
		}
		
		// Kommer tilbake fra editering av time, kjorebok eller utlegg
		// -> kravet (og eventuelt tilhørende krav ved kjørebok) må oppdateres
		if(kravInstance && params.oppdaterKrav) {
			log.info("Skal oppdatere krav etter endring")
			
			kravService.oppdaterKravEtterRetting(kravInstance)
						
			// TODO: Hva skal skje hvis kravet har status INAKTIV,
			// AVVIST eller TIL_RETTING_INTERVJUER ????
			// Kanskje best å sette til status TIL_MANUELL_KONTROLL, eller skal det ikke
			// være mulig å gjøre noe med disse kravene???
			
			if(kravInstance.intervjuer.epostJobb || kravInstance.intervjuer.epostPrivat) {
				String mottaker = kravInstance.intervjuer.epostJobb ?: kravInstance.intervjuer.epostPrivat
				String avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")

				String emne = ""
				String bodyStr = (params.tittel ?  (params.tittel + "\n\n") : "") + (params.melding ? params.melding : "")
				def d = sdf.format(kravInstance.dato)
				if(kravInstance.kravType == KravType.T) {
					emne = "${message(code: 'sil.epost.emne.endret.timeforing', args: [d, kravInstance.timeforing.id])}"
				}
				else if(kravInstance.kravType == KravType.K) {
					emne = "${message(code: 'sil.epost.emne.endret.kjorebok', args: [d, kravInstance.kjorebok.id])}"
				}
				else if(kravInstance.kravType == KravType.U) {
					emne = "${message(code: 'sil.epost.emne.endret.utlegg', args: [d, kravInstance.utlegg.id])}"
				}
				try {
					sendMail {
						to mottaker
						from avsender
						subject emne
						body bodyStr
					}
				}
				catch(Exception e) {
					log.error("Kunne ikke sende epost til intervjuer " + kravInstance.intervjuer + " om krav er endret: " + e.getMessage())
				}
			}
			
			if(!kravInstance.save(flush: true)) {
				// TODO: fiks error
			}
		}
		
		Intervjuer intervjuerInstance = Intervjuer.get( currentIntervjuerId )
				
		List<Long> intervjuerIdList = session.intervjuerBehandleListe
				
		if(intervjuerIdList) {
			// algoritme for aa sette neste og forrige id verdier for frem og tilbake knapp
			for(int i=0; i < intervjuerIdList.size(); i++) {
				Long id = Long.parseLong(intervjuerIdList[i])
				if(currentIntervjuerId == id) {
					session.currentIntervjuerPosition = i
				}
			}
		}
		else {
			// Kommer rett inn paa behandle med intervjuer id lager "liste" med currentId
			session.intervjuerBehandleListe = [params.id]
			session.currentIntervjuerPosition = 0
		}
		
		if (!intervjuerInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuer.label', default: 'Intervjuer'), params.id])}"
			redirect(action: "intervjuerKontroll")
		}
		else {
			def intervjuerKontrollInstance = kravService.finnKontrollSammendragForIntervjuer(intervjuerInstance)
			def feiletAutomatiskeKontrollerListe = Krav.findAllByIntervjuerAndKravStatus(intervjuerInstance, KravStatus.FEILET_AUTOMATISK_KONTROLL)
			def kravProduktNummerListe = kravService.finnKravSammendragProduktNummer(intervjuerInstance)
			def kravListe = kravService.finnKravForIntervjuer(intervjuerInstance)
			def behandleKravInstance = (isFailed == false ? kravInstance : null)
			def behandleFeiletKravInstance = (isFailed == true ? kravInstance: null)
			def kontrollerFeiletListe = kravService.finnKontrollerSomFeilet(kravInstance)
			[
				'intervjuerInstance': intervjuerInstance,
				'intervjuerKontrollInstance': intervjuerKontrollInstance,
				'feiletAutomatiskeKontrollerListe': feiletAutomatiskeKontrollerListe,
				'kravProduktNummerListe': kravProduktNummerListe,
				'kravListe': kravListe,
				'behandleKravInstance': behandleKravInstance,
				'behandleFeiletKravInstance': behandleFeiletKravInstance,
				'opprinneligKravInstance': opprinneligKravInstance,
				'kontrollerFeiletListe': kontrollerFeiletListe
			]
		}
	}
	
	def behandleIntervjuere = {
		def checkIds = request.getParameterValues("check")
		
		if(!checkIds) {
			flash.errorMessage = "${message(code: 'sil.ingen.intervjuere.valgt.info.melding', args: ['behandle'])}"
			redirect(action: "intervjuerKontroll")
		}
		else {
			List<Long> idListLong = checkIds
			session.intervjuerBehandleListe = idListLong
			redirect(action: "behandleIntervjuerKrav", id: checkIds[0])
		}
	}
	
	/**
	* Godkjenn kravet hvis id er gitt i params.id eller params.idFailed.
	* Trigges av "Godkjenn" knappen i knapperaden under krav skjeam i
	* Behandle intervjuer (behandleIntervjuerKrav.gsp) bildet.
	*/
	def godkjennKravTilBehandling = {
		boolean isFailed = false
		def cId = -1
		Krav krav = null
		if(params.idFailed) {
			krav = Krav.get(params.idFailed)
			isFailed = true
		}
		else if(params.id) {
			krav = Krav.get(params.id)
		}
		
		def intId = krav?.intervjuer?.id
		
		if(krav) {
			Krav kTmp = kravService.godkjennKrav(krav)
			
			//TODO: Håndtere til retting osv
			
			flash.message = "${message(code: 'sil.krav.godkjent', args: [krav.id], default: 'Krav godkjent')}"
			
			if(isFailed == true) {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
			}
			else {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
			}
		}
		else {
			flash.message = "Fant ikke krav"
			redirect(action: "behandleIntervjuerKrav", id: intId)
		}
	}
	
	/**
	* Endre kravet hvis id er gitt i params.id eller params.idFailed.
	* Kravet åpnes i timeføring, kjørebok eller utlegg skjema.
	* Trigges av "Endre" knappen i knapperaden under krav skjeam i
	* Behandle intervjuer (behandleIntervjuerKrav.gsp) bildet.
	*/
	def endreKravTilBehandling = {
		boolean isFailed = false
		def cId = -1
		Krav krav = null
		
		if(params.idFailed) {
			krav = Krav.get(params.idFailed)
			isFailed = true
		}
		else if(params.id) {
			krav = Krav.get(params.id)
		}

		def intId = krav?.intervjuer?.id
		
		if(!krav) {
			flash.errorMessage = "${message(code: 'sil.krav.endre.fant.ikke.krav')}"
			redirect(action: "behandleIntervjuerKrav",  params: [id: intId])
		}
		else if(!params.tittel && !params.melding) {
			flash.errorMessage = "${message(code: 'sil.til.intervjuer.melding.mangler')}"
			if(isFailed == true) {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
			}
			else {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
			}
		}
		else {
			if(krav.kravType == KravType.T) {
				redirect(controller: "timeforing", action: "edit", id: krav.timeforing.id, params: [kravId: krav.id, isFailed: isFailed, tittel: params.tittel, melding: params.melding])
			}
			else if(krav.kravType == KravType.K) {
				redirect(controller: "kjorebok", action: "edit", id: krav.kjorebok.id, params: [kravId: krav.id, isFailed: isFailed, tittel: params.tittel, melding: params.melding])
			}
			else if(krav.kravType == KravType.U) {
				redirect(controller: "utlegg", action: "edit", id: krav.utlegg.id, params: [kravId: krav.id, isFailed: isFailed, tittel: params.tittel, melding: params.melding])
			}
		}		
	}
	
	/**
	* Tilbakestill status for kravet hvis id er gitt i params.id eller params.idFailed.
	* Trigges av "Tilbakestill" knappen i knapperaden under krav skjeam i
	* Behandle intervjuer (behandleIntervjuerKrav.gsp) bildet, vises
	* hvis status er TIL_RETTING_INTERVJUER eller SENDES_TIL_INTERVJUER.
	*/
	def tilbakestillKravTilBehandling = {
		Krav krav = null
		if(params.id) {
			krav = Krav.get(params.id)
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
		def intId = krav?.intervjuer?.id
		
		if(krav) {
			krav.setKravStatus krav.forrigeKravStatus
			krav.setForrigeKravStatus null
					
			krav.save(failOnError: true, flush: false)
		
			flash.message = "${message(code: 'sil.krav.tilbakestilt', args: [krav.id], default: 'Krav satt tilbake til opprinnelig status')}"
		
			redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
	}
	
	/**
	* Tilbakestill/hent tilbake krav som er sendt til retting av intervjuer til SIL
	*/
	def tilbakestillKravFraRettingIntervjuer = {
		Krav krav = null
		if(params.id) {
			krav = Krav.get(params.id)
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
		
		
		def intId = krav?.intervjuer?.id
		
		if(krav) {
			if(kravService.hentTilbakeKravFraRettingIntervjuer(krav.intervjuer, krav.dato)) {
				String mottaker = krav.intervjuer.epostJobb ?: krav.intervjuer.epostPrivat
				String avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")

				def tittel = "${message(code: 'sil.behandle.krav.tilbakestill.epost.default.tittel')}"
				def melding = "${message(code: 'sil.behandle.krav.tilbakestill.epost.default.melding', args: [sdf.format(krav.dato)])}"
				
				try {
					sendMail {
						to mottaker
						from avsender
						subject tittel
						body melding
					}
				}
				catch(Exception e) {
					log.error("Kunne ikke sende epost til intervjuer " + krav.intervjuer + " om krav er tilbakekalt og føring for dato er låst: " + e.getMessage())
				}
				
				flash.message = "${message(code: 'sil.behandle.krav.tilbakekalt.info.melding', args: [krav.id])}"
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId])
			}
			else {
				// ERROR	
			}
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
	}
	
	def sendTilbakeKravTilBehandling = {
		boolean isFailed = false
		def cId = -1
		Krav krav = null
		if(params.idFailed) {
			krav = Krav.get(params.idFailed)
			isFailed = true
		}
		else if(params.id) {
			krav = Krav.get(params.id)
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
		
		def intId = krav?.intervjuer?.id
		
		if(!params.tittel && !params.melding) {
			flash.errorMessage = "${message(code: 'sil.til.intervjuer.melding.mangler')}"
			if(isFailed == true) {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
			}
			else {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
			}
		}
		else if(krav) {
			if(!krav.silMelding) {
				SilMelding silMld = new SilMelding(tittel: (params.tittel ?: "Til endring"), melding: (params.melding ?: "Kravet må endres"), krav: krav, intervjuer: krav.intervjuer)
			
				if(brukerService.getCurrentUserName()) {
					silMld.setSkrevetAv brukerService.getCurrentUserName()
				}
				
				silMld.save()
				krav.setSilMelding silMld
			}
			else {
				krav.silMelding.setTittel(params.tittel ?: "Til endring")
				krav.silMelding.setMelding(params.melding ?: "Kravet må endres")
				if(brukerService.getCurrentUserName()) {
					krav.silMelding.setSkrevetAv brukerService.getCurrentUserName()
				}
			}
			
			krav.setForrigeKravStatus krav.kravStatus
			krav.setKravStatus KravStatus.SENDES_TIL_INTERVJUER
			
			krav.save(failOnError: true, flush: false)
			
			flash.message = "${message(code: 'sil.krav.send.tilbake.til.intervjuer', args: [krav.id], default: 'Krav satt til tilbakesending til intervjuer')}"
			
			if(isFailed == true) {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
			}
			else {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
			}
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
	}
	
	/**
	 * Metode for å slette utlegg og tilhørende krav for utleggBom,
	 * utleggParkering eller utleggFerge tilknyttet kjørebok. Disse
	 * blir slettet og ikke avvist (som skjer når hele kjøreboken blir
	 * avvist). Det er implementert bekreft dialog boks i gui'et før
	 * denne action'en blir kalt.
	 */
	def slettKjorebokUtlegg = {
		Utlegg utlegg = Utlegg.get(params.utleggId)
				
		Krav kravUtlegg = Krav.findByUtlegg(utlegg)
		Krav kravKjorebok = Krav.get(params.kravId)
		
		def intId = kravUtlegg?.intervjuer?.id
		
		boolean fantUtlegg = false
		
		def type = ""
		
		// Fjern utlegg fra kjorebok
		if(kravKjorebok?.kjorebok?.utleggBom == utlegg) {
			kravKjorebok.kjorebok.utleggBom = null
			fantUtlegg = true
			type = "bompenger"
		}
		else if(kravKjorebok?.kjorebok?.utleggParkering == utlegg) {
			kravKjorebok.kjorebok.utleggParkering = null
			fantUtlegg = true
			type = "parkering"
		}
		else if(kravKjorebok?.kjorebok?.utleggFerge == utlegg) {
			kravKjorebok.kjorebok.utleggFerge = null
			fantUtlegg = true
			type = "ferge"
		}
		
		boolean error = false
		if(fantUtlegg) {
			//Lagre kjorebok
			if(!kravKjorebok.kjorebok.save(flush: true)) {
				error = true
				log.error("Kunne ikke lagre kjørebok etter fjerning av utlegg")
			}
						
			//Fjern utlegg fra kravUtlegg før disse slettes
			kravUtlegg.utlegg = null
			if(!kravUtlegg.save(flush: true)) {
				error = true
				log.error("Kunne ikke lagre krav etter fjerning av utlegg")
			}
			
			String utleggInfo = (", tilhørende kjørebok fra " + sdf.format(kravKjorebok?.kjorebok?.fraTidspunkt)
				+ "(" + timeFormat.format(kravKjorebok?.kjorebok?.fraTidspunkt) + "-"
				+ timeFormat.format(kravKjorebok?.kjorebok?.tilTidspunkt) + ")");
			
			String utleggInfoTilEpost = ("Krav og utlegg som gjaldt " + type + "(" + utlegg
				+ ") med id " + utlegg.id + utleggInfo + ", har blitt slettet.");
			
			String utleggInfoTilLog = ("Bruker med initaler " + brukerService.getCurrentUserName() 
				+ " har slettet krav og utlegg som gjaldt " + type + "(" + utlegg + ") med id " 
				+ utlegg.id + " for intervjuer " + kravKjorebok?.intervjuer + utleggInfo); 
			
			boolean slettet = false
			
			try {
				utlegg.delete(flush: true)
				kravUtlegg.delete(flush: true)
				log.info(utleggInfoTilLog)
				slettet = true
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				error = true
				log.error("Noe gikk galt ved sletting av utlegg eller det tilhørende kravet")
			}
			if(slettet) {
				// Send epost til intervjueren om at krav er blitt slettet
				String mottaker = kravKjorebok?.intervjuer?.epostJobb ?: kravKjorebok.intervjuer.epostPrivat
				String avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")

				String emne = "${message(code: 'sil.epost.emne.krav.slettet', default: 'Krav for utlegg tilhørende kjørebok er slettet')}"
				try {
					sendMail {
						to mottaker
						from avsender
						subject emne
						body utleggInfoTilEpost
					}
				}
				catch(Exception e) {
					log.warn("Kunne ikke sende epost til intervjuer " + kravKjorebok?.intervjuer + " om krav er slettet: " + e.getMessage())
				}
			}
		}
		else {
			// ERROR - fant ikke utlegg i kjorebok
			error = true
			log.error("Fant ikke utlegg tilknyttet kjørebok, kunne ikke slette")
		}
		
		if(error) {
			flash.errorMessage = "Kunne ikke avvise/fjerne utlegg fra Kjørebok"
		}
		
		if(params.erFeiletAutomatiskKontroll == "true") {
			redirect(action: "behandleIntervjuerKrav", flash: flash, params: [id: intId, feiletId: params.kravId])
		}
		else {
			redirect(action: "behandleIntervjuerKrav", flash: flash, params: [id: intId, kravId: params.kravId])
		}
	}
	
	def avviseKravTilBehandling = {
		boolean isFailed = false
		def cId = -1
		Krav krav = null
		if(params.idFailed) {
			krav = Krav.get(params.idFailed)
			isFailed = true
		}
		else if(params.id) {
			krav = Krav.get(params.id)
		}
		else {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
	
		def intId = krav?.intervjuer?.id
				
		if(!krav) {
			flash.message = "${message(code: 'sil.behandle.fant.ikke.krav', args: [params.id])}"
			redirect(action: "intervjuerKontroll")
		}
		
		if(!params.tittel && !params.melding) {
			flash.errorMessage = "${message(code: 'sil.til.intervjuer.melding.mangler')}"
			if(isFailed == true) {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
			}
			else {
				redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
			}
			return
		}
		else {
			String uName = brukerService.getCurrentUserName() ?: null 
						
			if(krav.silMelding) {
				krav.silMelding.setTittel(params.tittel ?: "Krav avvist")
				krav.silMelding.setMelding(params.melding ?: "Krav avvist")
				krav.silMelding.setSkrevetAv(uName) 
				krav.silMelding.save(failOnError: true, flush: true)
			}
			else {
				SilMelding silMld = new SilMelding(tittel: params.tittel, melding: params.melding, krav: krav, intervjuer: krav.intervjuer, setSkrevetAv: uName)
				silMld.save(failOnError: true, flush: true)
				krav.setSilMelding silMld
			}
		}
				
		
		def kravTilAvvisning = finnTilhorendeKravForAvvsing(krav)
				
		krav.setKravStatus KravStatus.AVVIST
				
		if(krav.save(failOnError: true, flush: false)) {
			if(!kravService.avvisKrav(krav)) {
				// TODO: error melding
			}
			
			// Avvis tilhørende krav
			kravTilAvvisning.each { Krav kr ->
				kr.setKravStatus KravStatus.AVVIST
				kr.setSilMelding(krav.silMelding)
				kr.save()
				sivAdmService.avvisKrav(kr)
			}
			
			if(krav.intervjuer.epostJobb || krav.intervjuer.epostPrivat) {
				String mottaker = krav.intervjuer.epostJobb ?: krav.intervjuer.epostPrivat
				String avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
				
				String emne = "${message(code: 'sil.epost.emne.krav.avvist', args: [])}"
					
				String mld = ""
				if(krav.silMelding?.tittel != null && krav.silMelding?.tittel != "") {
					mld = krav.silMelding?.tittel 	
				}
				
				if(krav.silMelding?.melding != null && krav.silMelding?.melding != "") {
					mld += "\n" + krav.silMelding?.melding 	
				}
				
				String bodyStr = mld 
				
				bodyStr += "\n\n" + "${message(code: 'sil.epost.krav.avvist')}"
								
				// Legg til info i mail om det avviste kravet
				bodyStr += finnKravInfoTekst(krav)
								
				// Legg til info i mail om de tilhørende krav som også er avvist
				kravTilAvvisning.each { Krav kr ->
					bodyStr += finnKravInfoTekst(kr)
				}
								
				try {
					sendMail {
						to mottaker
						from avsender
						subject emne
						body bodyStr
					}
				}
				catch(Exception e) {
					log.error("Kunne ikke sende epost til intervjuer " + krav.intervjuer + " om krav er avvist: " + e.getMessage())
				}
			}
		}
		else {
			// TODO: error melding
		}
		
		if(kravTilAvvisning) {
			if(kravTilAvvisning.size() == 1) {
				flash.message = "${message(code: 'sil.krav.avvist.med.tilhorende', args: [krav.id, kravTilAvvisning[0]], default: 'Kravet er avvist med tilhørende krav, og epost er sendt til intervjueren')}"
			}
			else {
				flash.message = "${message(code: 'sil.krav.avvist.med.tilhorende.flere', args: [krav.id, kravTilAvvisning.size()], default: 'Kravet er avvist med tilhørende krav, og epost er sendt til intervjueren')}"
			} 
		}
		else {
			flash.message = "${message(code: 'sil.krav.avvist', args: [krav.id], default: 'Kravet er avvist, og epost er sendt til intervjueren')}"
		}
		
		
		if(isFailed == true) {
			redirect(action: "behandleIntervjuerKrav",  params: [id: intId, feiletId: params.idFailed])
		}
		else {
			redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
		}
	}
	
	def angreGodkjent = {	
		Krav krav = null
		
		if(params.id) {
			krav = Krav.get(params.id)
			
			if( krav != null ) {
				krav.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
				krav.save()
				
				kravService.finnTilhorendeKrav(krav).each {
					it.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
					it.save()
				}
			}
		}
		
		def intId = krav?.intervjuer?.id
		
		redirect(action: "behandleIntervjuerKrav",  params: [id: intId, kravId: params.id])
	}
	
	
	String finnKravInfoTekst (Krav krav) {
		String d = sdf.format(krav.dato)
		
		String str
		
		if(krav.kravType == KravType.T) {
			String fra = timeFormat.format(krav.timeforing?.fra)
			String til = timeFormat.format(krav.timeforing?.til)
			str = "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.timeforing.linje', args: [d, fra, til, krav.timeforing.arbeidsType])}"
		}
		else if(krav.kravType == KravType.K) {
			String fra = timeFormat.format(krav.kjorebok?.fraTidspunkt)
			String til = timeFormat.format(krav.kjorebok?.tilTidspunkt)
			str = "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.kjorebok.linje', args: [d, fra, til, krav.kjorebok?.transportmiddel])}"
		}
		else if(krav.kravType == KravType.U) {
			str = "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.utlegg.linje', args: [d, krav.antall, krav.utlegg?.utleggType])}"
		}
		
		return str
	}
	
	/**
	 * Metode for finne utlegg tilhørende en kjørebok.
	 * (bompenger, parkering, ferge mm.)
	 * Returnerer en liste med Utlegg.
	 */	
	List finnUtleggKrav( Kjorebok kjorebok ) {
		if(!kjorebok) {
			return null
		}
		
		def utleggListe = []
		
		if(kjorebok.utleggBom) {
			utleggListe <<  Krav.findByUtlegg(kjorebok.utleggBom)
		}
		if(kjorebok.utleggParkering) {
			utleggListe <<  Krav.findByUtlegg(kjorebok.utleggParkering)
		}
		if(kjorebok.utleggFerge) {
			utleggListe <<  Krav.findByUtlegg(kjorebok.utleggFerge)
		}
		if(kjorebok.utleggBelop) {
			utleggListe <<  Krav.findByUtlegg(kjorebok.utleggBelop)
		}
		
		if(utleggListe.size() > 0) {
			return utleggListe
		}
		
		return null
	}

	
	/**
	 * Metode for å finn tilhørende krav for et gitt krav. Dette er f.eks.
	 * et timeføringskrav som tilhører en kjørebok, eller utlegg krav som
	 * tilhører utlegg som er i forbindelse med en kjørebok (bompenger, parkering osv.)
	 * Returnerer en liste med Krav.
	 */
	List finnTilhorendeKravForAvvsing( Krav krav ) { 
		def kravListe = []
		if(krav.kravType == KravType.T && krav.timeforing?.arbeidsType == ArbeidsType.REISE) {
			Kjorebok kjore = Kjorebok.findByTimeforing(krav.timeforing)
			kravListe << Krav.findByKjorebok(kjore)
			def uList = finnUtleggKrav(kjore)
			if(uList) {
				kravListe.addAll(uList)
			}
		}
		else if(krav.kravType == KravType.K) {
			kravListe << Krav.findByTimeforing(krav.kjorebok.timeforing)
			def uList = finnUtleggKrav(krav.kjorebok)
			if(uList) {
				kravListe.addAll(uList)
			}
		}
		else if(krav.kravType == KravType.U && (krav.utlegg.utleggType == UtleggType.TAXI || krav.utlegg.utleggType == UtleggType.BILLETT))  { 
			// Hvis reise utlegg er buss,trikk,tog,ferge (BILLETT) eller taxi(TAXI) avvis kjørebok og time også
			// Hvis utlegg er i forbindelse med "km-reise" avvis bare utlegget og "fjern" kobling
			Kjorebok kjore = null
			kjore = Kjorebok.findByUtleggBelop(krav.utlegg)
			
			if(!kjore) {
				kjore = Kjorebok.findByUtleggFerge(krav.utlegg)	
			}
							
			if(kjore && (
				kjore.transportmiddel == TransportMiddel.FERJE ||
				kjore.transportmiddel == TransportMiddel.TAXI ||
				kjore.transportmiddel == TransportMiddel.BUSS_TRIKK ||
				kjore.transportmiddel == TransportMiddel.TOG)) {
				
				kravListe << Krav.findByKjorebok(kjore)
				kravListe << Krav.findByTimeforing(kjore.timeforing)
			}
			else if(kjore) {
				// Utlegg er av type billett (ferge) og tilknyttet en "km-kjørebok"
				// Fjern kobling mellom utlegg og kjørebok da kravet avvises
				kjore.setUtleggFerge(null)
				kjore.save()
			}
		}
		else if(krav.kravType == KravType.U && (krav.utlegg.utleggType == UtleggType.BOMPENGER || krav.utlegg.utleggType == UtleggType.PARKERING)) {
			// Utlegg er av type bom eller parkering og tilknyttet en "km-kjørebok"
			// Fjern kobling mellom utlegg og kjørebok da kravet avvises
			Kjorebok kjore = null
			kjore = Kjorebok.findByUtleggBom(krav.utlegg)
			
			if(kjore) {
				kjore.setUtleggBom(null)	
			}
			else {
				kjore = Kjorebok.findByUtleggParkering(krav.utlegg)
				if(kjore) {
					kjore.setUtleggParkering(null)
				}
			}
			
			if(kjore) {
				kjore.save()
			}
		}
				
		return kravListe
	}
	
	def godkjennIntervjuere = {
		def checkIds = request.getParameterValues("check")
		
		if(!checkIds) {
			flash.errorMessage = "${message(code: 'sil.ingen.intervjuere.valgt.info.melding', args: ['godkjenne'])}"
			redirect(action: "intervjuerKontroll")
		}
		else {
			int cntInt = 0
			int cntKrav = 0
			checkIds.each { i ->
				cntInt++
				Long l = Long.parseLong(i)
				cntKrav = cntKrav + kravService.godkjennAlleForIntervjuer(l)
			}
			flash.message = "${message(code: 'sil.kontroll.godkjenn.info.melding', args: [cntInt, cntKrav], default: '')}"
			redirect(action: "intervjuerKontroll")
		}
	}
	
	/**
	* Godkjenn alle krav hvor sjekkboksen for kravet er valgt i listen
	* over alle krav eller i listen over krav som feilet automatiske
	* kontroller i behandle intervjuer.
	* Trigges av "Godkjenn valgte krav" knappen i behandle intervjuer
	* (behandleIntervjuerKrav.gsp) bildet.
	*/
   def godkjennValgteKrav = {
	   def checkIds
	   
	   // Er knappen trykket for krav som feilet automatiske
	   // kontroller (isFailed) eller for listen over alle krav
	   if(params.isFailed) {
		   // Henter ut id'er for de krav som er sjekket av i sjekkboksen
		   checkIds = request.getParameterValues("checkFailed")
	   }
	   else {
		   // Henter ut id'er for de krav som er sjekket av i sjekkboksen
		   checkIds = request.getParameterValues("check")
	   }
	   def intId
	   if(checkIds) {
		   int cnt = 0
		   // Loop gjennom krav id'er
		   checkIds.each {
			   Krav k = Krav.get(new Long( "${it}" ))
			   
			   if(!intId) {
				   intId = k?.intervjuer?.id
			   }
			   
			   Krav kTmp = kravService.godkjennKrav(k)
			   
			   if(kTmp) {
				   cnt++
			   }
		   }
		   
		   flash.message = "${message(code: 'sil.sett.status.info.melding', args: [KravStatus.GODKJENT, cnt])}"
	   }
	   else {
		   flash.message = "${message(code: 'sil.ingen.krav.valgt.info.melding')}"
	   }
	   
	   if(!intId) {
		   intId = session.intervjuerBehandleListe.get(session.currentIntervjuerPosition)
	   }
	   redirect(controller: "krav", action: "behandleIntervjuerKrav", id: intId)
   }
	
	/**
	* Send alle krav som er merket for tilbakesending til intervjuer
	* (status KravStatus.SENDES_TIL_INTERVJUER), dvs. endre status til
	* KravStatus.TIL_RETTING_INTERVJUER og send epost til intervjuer
	* med informasjon om hvilke krav som må rettes.
	* Trigges av "Send tilbake til intervjuer" knappen i Behandle intervjuer
	* (behandleIntervjuerKrav.gsp) bildet.
	*/
	def sendTilRettingForIntervjuer = {
		Intervjuer intervjuer = Intervjuer.get(params.id)
	
		// Endre krav status på krav som har status SENDES_TIL_INTERVJUER
		// for intervjueren
		def kravListe = kravService.sendTilbakeForIntervjuer(intervjuer)
		
		// Ingen krav har endret status
		if(!kravListe) {
			flash.message = "${message(code: 'sil.kontroll.send.tilbake.info.melding.ingen.krav', args: [intervjuer.navn], default: '')}"
		}
		else {
			// Finn epost adressen til intervjueren
			String mottaker = intervjuer.epostJobb ?: intervjuer.epostPrivat
			String avsender = grailsApplication.config.getProperty("avsender.sivadmin.epost")
			String emne = "${message(code: 'sil.epost.emne.tilbake.til.intervjuer', args: [kravListe.size()], default: '')}"
			String bodyStr = ""
			
			// Loop gjennom kravene som er endret og legg til informasjon i epost om
			// hver av disse
			kravListe.each { krav ->
				String d = sdf.format(krav.dato)
				String t = krav.silMelding?.tittel
				String m = krav.silMelding?.melding
				
				if(krav.kravType == KravType.T) {
					String fra = timeFormat.format(krav.timeforing?.fra)
					String til = timeFormat.format(krav.timeforing?.til)
					bodyStr += "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.timeforing.linje', args: [d, fra, til, krav.timeforing.arbeidsType, t, m])}"
				}
				else if(krav.kravType == KravType.K) {
					String fra = timeFormat.format(krav.kjorebok?.fraTidspunkt)
					String til = timeFormat.format(krav.kjorebok?.tilTidspunkt)
					bodyStr += "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.timeforing.linje', args: [d, fra, til, krav.kjorebok?.transportmiddel, t, m])}"
				}
				else if(krav.kravType == KravType.U) {
					bodyStr += "\n" + "${message(code: 'sil.epost.tilbake.til.intervjuer.utlegg.linje', args: [d, krav.antall, krav.utlegg?.utleggType, t, m])}"
				}
			}
			
			// Send epost til intervjueren om at det er krav som er satt til endring
			try {
				sendMail {
					to mottaker
					from avsender
					subject emne
					body bodyStr
				}
			}
			catch(Exception e) {
				log.error("Kunne ikke sende epost til intervjuer " + intervjuer + " om krav er sendt tilbake til retting: " + e.getMessage())
			}
			flash.message = "${message(code: 'sil.kontroll.sendt.tilbake.info.melding', args: [kravListe.size(), intervjuer.navn], default: '')}"
		}
		
		redirect(controller: "krav", action: "behandleIntervjuerKrav",  params: [id: params.id])
	}
	
	/**
	* Godkjenn alle krav tilhørende nåværende intervjuer.
	* Trigges av "Godkjenn alle krav" knappen i Behandle intervjuer
	* (behandleIntervjuerKrav.gsp) bildet.
	*/
	def godkjennForIntervjuer = {
		Intervjuer intervjuer = Intervjuer.get(params.id)
		int cntKrav = kravService.godkjennAlleForIntervjuer(intervjuer)
	      
		// Fjern intervjuer fra liste til behandling?
		// Vis neste intervjuer til behandling?
   
		flash.message = "${message(code: 'sil.kontroll.godkjenn.intervjuer.info.melding', args: [cntKrav, intervjuer.navn], default: '')}"
		redirect(controller: "krav", action: "behandleIntervjuerKrav", id: params.id)
	}
	
	/**
	* Finner forrige intervjuer i liste over intervjuere valgt til
	* behandling i SIL -> Intervjuere til kontroll -> Behandle valgte
	* Trigges av "Forrige intervjuer" knappen i Behandle intervjuer
	* (behandleIntervjuerKrav.gsp) bildet.
	*/
	def forrigeIntervjuer = {
		def forrigeId
	   
		if(session.intervjuerBehandleListe && session.currentIntervjuerPosition > 0 ) {
			forrigeId = session.intervjuerBehandleListe.get(session.currentIntervjuerPosition-1)
		}
		else {
			forrigeId = session.intervjuerBehandleListe.get(0)
		}
	   
		redirect(controller: "krav", action: "behandleIntervjuerKrav", id: forrigeId)
	}
   
	/**
	* Finner neste intervjuer i liste over intervjuere valgt til
	* behandling i SIL -> Intervjuere til kontroll -> Behandle valgte
	* Trigges av "Neste intervjuer" knappen i Behandle intervjuer
	* (behandleIntervjuerKrav.gsp) bildet.
	*/
	def nesteIntervjuer = {
		def nesteId
	   
		if(session.intervjuerBehandleListe && session.currentIntervjuerPosition < session.intervjuerBehandleListe.size() - 1) {
			nesteId = session.intervjuerBehandleListe.get(session.currentIntervjuerPosition + 1)
		}
		else {
			nesteId = session.intervjuerBehandleListe.get( session.intervjuerBehandleListe.size() - 1)
		}
	   
		redirect(controller: "krav", action: "behandleIntervjuerKrav", id: nesteId)
	}
	
	/**
	 * Metode for å hente ut et Krav med gitt id (params.id) og
	 * returnere dette kravet som JSON
	 */
	def ajaxGetKrav = {
		def krav = Krav.get(params.id)
		render krav as JSON
	}
}
