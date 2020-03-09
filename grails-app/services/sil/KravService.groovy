package sil


import org.hibernate.criterion.CriteriaSpecification;

import sil.search.*
import sil.type.*
import sil.data.*
import siv.type.*;
import sivadm.Intervjuer
import sivadm.Kjorebok
import sivadm.Timeforing
import sivadm.Utlegg

class KravService {
	
	def grailsApplication

	def utilService
	def sivAdmService
	def automatiskKontrollService
	
    boolean transactional = true
	
	public int countSearchIntervjuer(IntervjuerKontrollSearch search) {
		List iCount = searchCriteriaIntervjuer( search, null, null, true )
		return iCount.get(0)
	}
	
	public List searchIntervjuer(IntervjuerKontrollSearch search, Map pagination) {
		Integer max = pagination.max
		Integer first = pagination.offset? Integer.parseInt(pagination.offset) : 0
		
		def intervjuerIds = searchCriteriaIntervjuer(search, first, max, false)
		
		if( intervjuerIds.size() > 0 ) {
			return genererIntervjuerKontrollListe(intervjuerIds, search.kravStatus)
		}
		else {
			return new ArrayList()
		}
	}
	
	private List genererIntervjuerKontrollListe(List idListe, KravStatus kravStatus) {
		List<Intervjuer> intListe = Intervjuer.getAll(idListe)
	
		if(!intListe) {
			return new ArrayList()
		}
		
		def intKontListe = []
		
		intListe.each { i ->
			IntervjuerKontroll ik = new IntervjuerKontroll(intervjuer: i)
			def intervjuerKontrollSammendrag = finnIntervjuerKontrollSammendrag(i, kravStatus)
			List<Krav> bestodAutoListe = Krav.findAllByIntervjuerAndKravStatus(i, KravStatus.BESTOD_AUTOMATISK_KONTROLL)
			if(intervjuerKontrollSammendrag) {
				ik.antallKrav = intervjuerKontrollSammendrag["antall"]
				ik.antallFeiletAutomatiskeTester = intervjuerKontrollSammendrag["feiletAuto"]
				ik.antallBestodAutomatiskeTester = bestodAutoListe.size()
				ik.totalTimer = intervjuerKontrollSammendrag["timer"] ?: 0
				ik.totalMinutter = intervjuerKontrollSammendrag["minutter"] ?: 0
				ik.totalKm = intervjuerKontrollSammendrag["km"]
				ik.totalBelop = intervjuerKontrollSammendrag["belop"]
				ik.timeKrav = intervjuerKontrollSammendrag["antallTime"]
				ik.kjorebokKrav = intervjuerKontrollSammendrag["antallKjorebok"]
				ik.utleggKrav = intervjuerKontrollSammendrag["antallUtlegg"]
			}
			intKontListe << ik
		}
		
		return intKontListe
	}
	
	/**
	 * Metode for å finne sammendrag for en Intervjuer. Returnerer et
	 * IntervjuerKontroll (intervjuer, antallKrav, antallFeiletAutomatiskeTester,
	 * totalTimer, totalMinutter, totalKm, totalBelop, timeKrav, kjorebokKrav, utleggKrav)
	 * objekt.
	 * @param intervjuer Intervjueren å finne sammedrag for
	 * @return et IntervjuerKontroll objekt.
	 */
	IntervjuerKontroll finnKontrollSammendragForIntervjuer(Intervjuer intervjuer) {
		IntervjuerKontroll ik = new IntervjuerKontroll(intervjuer: intervjuer)
		def intervjuerKontrollSammendrag = finnIntervjuerKontrollSammendrag(intervjuer, null)
		List<Krav> bestodAutoListe = Krav.findAllByIntervjuerAndKravStatus(intervjuer, KravStatus.BESTOD_AUTOMATISK_KONTROLL)
		ik.antallBestodAutomatiskeTester = bestodAutoListe.size()
		if(intervjuerKontrollSammendrag) {
			ik.antallKrav = intervjuerKontrollSammendrag["antall"]
			ik.antallFeiletAutomatiskeTester = intervjuerKontrollSammendrag["feiletAuto"] 
			ik.totalTimer = intervjuerKontrollSammendrag["timer"] ?: 0
			ik.totalMinutter = intervjuerKontrollSammendrag["minutter"] ?: 0
			ik.totalKm = intervjuerKontrollSammendrag["km"]
			ik.totalBelop = intervjuerKontrollSammendrag["belop"]
			ik.timeKrav = intervjuerKontrollSammendrag["antallTime"]
			ik.kjorebokKrav = intervjuerKontrollSammendrag["antallKjorebok"]
			ik.utleggKrav = intervjuerKontrollSammendrag["antallUtlegg"]
		}
		
		return ik
	}
	
	/**
	 * Metode for å finne sammendrag for Intervjueren.
	 * @param i
	 * @param kravStatus
	 * @return 
	 */
	Map finnIntervjuerKontrollSammendrag(Intervjuer i, KravStatus kravStatus) {
		
		def crit = Krav.createCriteria()
		
		def krav = crit {
			
			createAlias "kjorebok", "k", CriteriaSpecification.LEFT_JOIN
			createAlias "utlegg", "u", CriteriaSpecification.LEFT_JOIN
			createAlias "intervjuer", "ivr", CriteriaSpecification.LEFT_JOIN
			
			projections {
				rowCount()
				sum("antall")
				sum("k.kjorteKilometer")
				sum("u.belop")
				groupProperty("kravStatus")
				count("timeforing")
				count("kjorebok")
				count("utlegg")
			}

			eq("ivr.id", i.id)

			def kravList = [KravStatus.FEILET_AUTOMATISK_KONTROLL, KravStatus.TIL_MANUELL_KONTROLL]
			if(kravStatus) {
				kravList = [kravStatus]
			}
			'in'("kravStatus", kravList)
		}
		

		// TODO: Andre navn paa variable + legg inn final / konstanter istedet for tall
		if(krav) {
			Integer a = 0 // antall
			Integer f = 0 // feilet
			Integer t = 0 // timer
			Integer m = 0 // minutter
			Long k = 0 // kilometer
			Long b = 0 // belop
			Long ct = 0 // antall timeforinger
			Long ck = 0 // antall kjorebok
			Long cu = 0 // antall utlegg
			
			// baade automatisk og manuell kontroll
			if(krav.size() == 2) {
				a = krav[0][0] + krav[1][0]
				Integer min = (krav[0][1] + krav[1][1])
				min = min - (krav[0][2] ?: 0 + (krav[1][2] ?: 0))
				min = min - (krav[0][3] ?: 0 + (krav[1][3] ?: 0))
				t = finnTimer(min)
				m = finnMinutter(min)
				k = krav[0][2] ?: 0 + (krav[1][2] ?: 0)
				b = (krav[0][3] ?: 0 + (krav[1][3] ?: 0))
				if(krav[0][4] == KravStatus.FEILET_AUTOMATISK_KONTROLL) {
					f = krav[0][0]
				}
				else if(krav[1][4] == KravStatus.FEILET_AUTOMATISK_KONTROLL) {
					f = krav[1][0]
				}
				ct = (krav[0][5] ?: 0) + (krav[1][5] ?: 0)
				ck = (krav[0][6] ?: 0) + (krav[1][6] ?: 0)
				cu = (krav[0][7] ?: 0) + (krav[1][7] ?: 0)
			}
			else {
				a = krav[0][0]
				Integer min = (krav[0][1] - (krav[0][2] ?: 0) - (krav[0][3] ?: 0) )
				t = finnTimer(min)
				m = finnMinutter(min)
				k = krav[0][2] ?: 0
				b = krav[0][3] ?: 0
				if(krav[0][4] == KravStatus.FEILET_AUTOMATISK_KONTROLL) {
					f = krav[0][0]
				}
				ct = krav[0][5] ?: 0
				ck = krav[0][6] ?: 0
				cu = krav[0][7] ?: 0
			}
			
			return [antall: a, feiletAuto: f, timer: t, minutter: m, km: k, belop: b, antallTime: ct, antallKjorebok: ck, antallUtlegg: cu]
		}
		else {
			return null
		}
	}
	
	/**
	 * Metode for finne alle krav tilhørende gitt intervjer (@param intervjuerInstance)
	 * som har en av kravstatusene (OPPRETTET, BESTOD_AUTOMATISK_KONTROLL, 
	 * FEILET_AUTOMATISK_KONTROLL, TIL_MANUELL_KONTROLL, SENDES_TIL_INTERVJUER,
	 * TIL_RETTING_INTERVJUER, INAKTIV, AVVIST, GODKJENT)
	 * @param intervjuerInstance Intervjueren det skal finnes krav for
	 * @return En liste med id'er på de kravene som tilhører intervjueren som har
	 * de kravstatusene nevnt over, eller null hvis intervjuerInstance er NULL
	 */
	public List finnKravForIntervjuer(Intervjuer intervjuerInstance) {
		if(!intervjuerInstance) {
			log.error("finnKravForIntervjuer: kan ikke finne krav tilhørende intervjuer når intervjuerInstance gitt er null, returnerer null")	
			return null
		}
		
		def crit = Krav.createCriteria()
		
		def kravIds = crit {			
			intervjuer {
				eq("id", intervjuerInstance.id)
			}
			
			def kravList = [ KravStatus.OPPRETTET,
				KravStatus.BESTOD_AUTOMATISK_KONTROLL,
				KravStatus.FEILET_AUTOMATISK_KONTROLL,
				KravStatus.TIL_MANUELL_KONTROLL,
				KravStatus.SENDES_TIL_INTERVJUER,
				KravStatus.TIL_RETTING_INTERVJUER,
				KravStatus.INAKTIV,
				KravStatus.AVVIST,
				KravStatus.GODKJENT]
			
			'in'("kravStatus", kravList)
			order("dato", "asc")
		}
		return kravIds
	}
	
	/**
	* Metode for å finne informasjon om kravene tilhørende en Intervjuer gruppert på
	* produktNummer. Returnerer en liste med KravProduktSammendrag (produktNummer, antallKrav,
	* antallTimeKrav, antallKjorebokKrav, antallUtleggKrav, totaltTimer, totaltMinutter,
	* totaltKm, totaltUtlegg)
	* @param i Intervjueren
	* @return En liste med KravProduktSammendrag
	*/
	public List finnKravSammendragProduktNummer(Intervjuer i) {
		def returnList = []
		
		def crit = Krav.createCriteria()
		def krav = crit {
			projections {
				groupProperty("produktNummer")
				rowCount()
				sum("antall")
				kjorebok {
					sum("kjorteKilometer")
				}
				utlegg {
					sum("belop")
				}	
				count("timeforing")
				count("kjorebok")
				count("utlegg")
			}
			
			intervjuer {
				eq("id", i.id)
			}
			
			def kravList = [KravStatus.BESTOD_AUTOMATISK_KONTROLL, KravStatus.FEILET_AUTOMATISK_KONTROLL, KravStatus.TIL_MANUELL_KONTROLL, KravStatus.SENDES_TIL_INTERVJUER, KravStatus.TIL_RETTING_INTERVJUER, KravStatus.INAKTIV, KravStatus.AVVIST, KravStatus.GODKJENT]	
			
			'in'("kravStatus", kravList)
		}
				
		krav.each { k ->
			KravProduktSammendrag kps = new KravProduktSammendrag(produktNummer: k[0])
			
			if(k[1]) {
				kps.antallKrav = k[1]
			}
			if(k[2]) {
				Integer tot = k[2] - (k[3] ?: 0) - (k[4] ?: 0)
				kps.totaltTimer = finnTimer(tot) ?: 0
				kps.totaltMinutter = finnMinutter(tot) ?: 0
			}
			if(k[3]) {
				kps.totaltKm = k[3]
			}
			if(k[4]) {
				kps.totaltUtlegg = k[4]
			}
			if(k[5]) {
				kps.antallTimeKrav = k[5]
			}
			if(k[6]) {
				kps.antallKjorebokKrav = k[6]
			}
			if(k[7]) {
				kps.antallUtleggKrav = k[7]
			}
			
			returnList << kps
		}
		
		return returnList
	}
	
	public List searchAllIntervjuer(IntervjuerKontrollSearch search) {
		return this.searchIntervjuer( search , new HashMap() )
	}
	
	public List<Long> getAllIdsIntervjuer(IntervjuerKontrollSearch search ) {
		def intervjuerList = this.searchAllIntervjuer(search)
		
		def ids = new ArrayList<Long>()
		
		intervjuerList.each {
			Intervjuer i = it.intervjuer
			ids.add i.id
		}
		
		return ids
	}
	
	/**
	 * Metode for å oppdatere et krav med endringer når en timeføring, kjørebok eller
	 * utlegg har vært oppdatert/redigert fra SIL. Hvis det er snakk om oppdatering
	 * av en kjørebok må også eventuelt tilhørende krav (time, utlegg bom, utlegg 
	 * parkering osv) oppdateres eller slettes.
	 * @param krav Kravet som har vært til redigering og må oppdateres
	 * @return En liste med Krav som er oppdatert
	 */
	public List<Krav> oppdaterKravEtterRetting(Krav krav) {
		def kravListe = []
		
		if(!krav) {
			log.error "oppdaterKravEtterRetting: Kan ikke oppdatert krav som er null, returnerer tom kravListe"
			return kravListe
		}
		
		if(krav.kravType == KravType.T) {
			if(!krav.timeforing) {
				log.error "oppdaterKravEtterRetting: Kan ikke oppdatert krav av typen time når krav.timeforing er null, returnerer tom kravListe"
				return kravListe
			}
			
			kravListe << oppdaterTimeforingKravEtterRetting(krav.timeforing)
		}
		else if(krav.kravType == KravType.U) {
			if(!krav.utlegg) {
				log.error "oppdaterKravEtterRetting: Kan ikke oppdatert krav av typen utlegg når krav.utlegg er null, returnerer tom kravListe"
				return kravListe
			}
						
			kravListe << oppdaterUtleggKravEtterRetting(krav.utlegg)
		}
		else if(krav.kravType == KravType.K) {
			if(!krav.kjorebok) {
				log.error "oppdaterKravEtterRetting: Kan ikke oppdatert krav av typen kjorebok når krav.kjorebok er null, returnerer tom kravListe"
				return kravListe
			}
			
			if(krav.kjorebok.erKm()) {
				krav.antall = krav.kjorebok.kjorteKilometer
			}
			else if(krav.kjorebok.transportmiddel == TransportMiddel.FERJE) {
				krav.antall = krav.kjorebok.utleggFerge?.belop
			}
			else if(krav.kjorebok.transportmiddel == TransportMiddel.LEIEBIL || krav.kjorebok.transportmiddel == TransportMiddel.GIKK) {
				krav.antall = utilService.beregnTidMinutter(krav.kjorebok.fraTidspunkt, krav.kjorebok.tilTidspunkt)
			}
			else {
				krav.antall = krav.kjorebok.utleggBelop?.belop
			}
			
			// Det kan forekomme kjørebøker der antall er null,
			// f.eks. ved leiebil, gikk mm., kjøreboken er da
			// ført for å få betalt for tidbrukt og antall vil
			// da være 0
			if(!krav.antall) {
				krav.antall = new Double(0)
			}
			
			krav.produktNummer = krav.kjorebok.produktNummer
									
			kravListe << krav
			
			if(krav.kjorebok.timeforing) {
				// Oppdater til og fra i timeforing til å være tilsvarende kjorebok
				krav.kjorebok.timeforing.fra = krav.kjorebok.fraTidspunkt
				krav.kjorebok.timeforing.til = krav.kjorebok.tilTidspunkt
				// Oppdater produktNummer i timeforing til å være tilsvarende kjorebok
				krav.kjorebok.timeforing.produktNummer = krav.kjorebok.produktNummer
				
				def kravTime = oppdaterTimeforingKravEtterRetting(krav.kjorebok.timeforing)
				if(kravTime) {
					kravListe << kravTime
				}
			}
			else {
				log.error "Fant kjørebok uten tilhørende timeføring, kjørebok.id = " + krav.kjorebok.id
			}
						
			if(krav.kjorebok.utleggBom) {
				def kravUtlegg = oppdaterUtleggKravEtterRetting(krav.kjorebok.utleggBom)
				if(kravUtlegg) {
					kravListe << kravUtlegg
				}
			}
			
			if(krav.kjorebok.utleggParkering) {
				def kravUtlegg = oppdaterUtleggKravEtterRetting(krav.kjorebok.utleggParkering)
				if(kravUtlegg) {
					kravListe << kravUtlegg
				}
			}
			
			if(krav.kjorebok.utleggFerge) {
				def kravUtlegg = oppdaterUtleggKravEtterRetting(krav.kjorebok.utleggFerge)
				if(kravUtlegg) {
					kravListe << kravUtlegg
				}
			}
			
			if(krav.kjorebok.utleggBelop) {
				def kravUtlegg = oppdaterUtleggKravEtterRetting(krav.kjorebok.utleggBelop)
				if(kravUtlegg) {
					kravListe << kravUtlegg
				}
			}
		}
		
		// Lagre de oppdaterte kravene
		kravListe.each {
			it?.save(failOnError: true, flush: true)	
		}
		
		return kravListe
	}
	
	/**
	 * Metode for å oppdatere kravet tilhørende en timeføring, når timeføringen
	 * er endret fra SIL. Finner kravet vha. timeforingen og oppdaterer antall
	 * og produktNummer.
	 * @param timeforing Timeføringen som er endret
	 * @return Returerer det oppdaterte kravet.
	 */
	private Krav oppdaterTimeforingKravEtterRetting(Timeforing timeforing) {
		if(!timeforing) {
			log.error "oppdatereTimeforingKravEtterRetting: Kan ikke oppdatert krav når timeforing er null, returnerer NULL"
			return null
		}

		Krav krav = Krav.findByTimeforing(timeforing)
		
		if(!krav) {
			log.error "oppdatereTimeforingKravEtterRetting: Fant ikke krav tilhørende timeforing med id " + timeforing.id + " kan ikke oppdatere krav, returerer NULL"
			return null
		}
		
		krav.antall = utilService.beregnTidMinutter(timeforing.fra, timeforing.til)
		krav.produktNummer = timeforing.produktNummer
		
		return krav
	}
	
	/**
	* Metode for å oppdatere kravet tilhørende et utlegg, når utlegget
	* er endret fra SIL. Finner kravet vha. utlegget og oppdaterer antall
	* og produktNummer.
	* @param utlegg Utlegget som er endret
	* @return Returerer det oppdaterte kravet.
	*/
	private Krav oppdaterUtleggKravEtterRetting(Utlegg utlegg) {
		if(!utlegg) {
			log.error "oppdatereUtleggKravEtterRetting: Kan ikke oppdatert krav når utlegg er null, returnerer NULL"
			return null
		}

		Krav krav = Krav.findByUtlegg(utlegg)
		
		if(!krav) {
			log.error "oppdatereUtleggKravEtterRetting: Fant ikke krav tilhørende utlegg med id " + utlegg.id + " kan ikke oppdatere krav, returerer NULL"
			return null
		}
		
		krav.antall = utlegg.belop
		krav.produktNummer = utlegg.produktNummer
		
		return krav
	}
	
	/**
	 * Metode for å finne ut om et krav er et underliggende krav
	 * til et kjørebok-krav. Med underliggende krav menes timeføring
	 * eller utlegg (bompenger, parkering osv.) knyttet opp til kjørebok.
	 * @param krav Kravet som skal sjekkes om det er underliggende et kjørebok-krav.
	 * @return Returnerer NULL hvis kravet ikke har tilhørende kjørebok-krav eller
	 * kjørebok-kravet hvis det finnes tilhørende kjørebok-krav.
	 */
	private Krav finnTilhorendeKjorebokKrav(Krav krav) {
		if(!krav) {
			log.error "finnTilhorendeKjorebokKrav: Kan ikke finne tilhørende krav når krav gitt er null, returnerer NULL"
			return null
		}
		
		Krav kr
		
		if(krav.kravType == KravType.T && krav.timeforing?.arbeidsType == ArbeidsType.REISE) {
			Kjorebok kjore = Kjorebok.findByTimeforing(krav.timeforing)
			kr = Krav.findByKjorebok(kjore)
		}
		else if(krav.kravType == KravType.U && 	krav.utlegg?.utleggType == UtleggType.BOMPENGER) {
			Kjorebok kjore = Kjorebok.findByUtleggBom(krav.utlegg)
			kr = Krav.findByKjorebok(kjore)
		}
		else if(krav.kravType == KravType.U && 	krav.utlegg?.utleggType == UtleggType.PARKERING) {
			Kjorebok kjore = Kjorebok.findByUtleggParkering(krav.utlegg)
			kr = Krav.findByKjorebok(kjore)
		}
		else if(krav.kravType == KravType.U && 	krav.utlegg?.utleggType == UtleggType.TAXI) {
			Kjorebok kjore = Kjorebok.findByUtleggBelop(krav.utlegg)
			kr = Krav.findByKjorebok(kjore)
		}
		else if(krav.kravType == KravType.U && 	krav.utlegg?.utleggType == UtleggType.BILLETT) {
			Kjorebok kjore = Kjorebok.findByUtleggFerge(krav.utlegg)
			
			if(!kjore) {
				kjore = Kjorebok.findByUtleggBelop(krav.utlegg)
			}
			
			if(kjore) {
				kr = Krav.findByKjorebok(kjore)
			}
		}
		
		return kr
	}
	
	def searchCriteriaIntervjuer = {
		
		search, first, max, countAll ->
		
		def c = Intervjuer.createCriteria()
		
		def intervjuerIds = c {
			if( first ) {
				firstResult(first)
			}
			
			if(max) {
				maxResults(max)
			}
						
			if(countAll) {
				projections { countDistinct("id") }
			}
			else {
				projections { distinct("id") }
			}
			
			if(search.klynge) {
				klynge {
					eq("id", search.klynge)
				}
			}
			
			if(search.navn) {
				ilike("navn", "%"+search.navn+"%")
			}
			
			if(search.intervjuer) {
				eq("id", search.intervjuer)
			}
			
			def kravList = [KravStatus.FEILET_AUTOMATISK_KONTROLL, KravStatus.TIL_MANUELL_KONTROLL]
			
			if(search.kravStatus) {
				kravList = [search.kravStatus]
			}
			
			def kravCrit = Krav.createCriteria()
			def intIdList = kravCrit {
				intervjuer {
					projections { distinct("id") }
				}
				'in'("kravStatus", kravList)
			}
			
			if(!intIdList) {
				Long l = -1
				intIdList = [l]
			}
			'in'("id", intIdList)
		}
		
		return intervjuerIds
	}
	
	public int countSearch(KravSearch search) {
		List ioCount = searchCriteria( search, null, null, true )
		return ioCount.get(0)
	}
	
	public List search(KravSearch search, Map pagination) {
		Integer max = pagination.max
		Integer first = pagination.offset? Integer.parseInt(pagination.offset) : 0
		
		def kravList = searchCriteria(search, first, max, false)
		
		return kravList
	}
	
	public List searchAll(KravSearch search) {
		return this.search( search , new HashMap() )
	}
	
	public List<Long> hentKravIdListe(KravSearch search ) {
		def kravList = this.searchAll(search)
		
		def ids = new ArrayList<Long>()
		
		kravList.each {
			Krav io = it
			ids.add io.id
		}
		
		return ids
	}
	
	def searchCriteria = {
		
		search, first, max, countAll ->
		
		def c = Krav.createCriteria()
		
		def kravIds = c {
			if( first ) {
				firstResult(first)
			}
			
			if(max) {
				maxResults(max)
			}
						
			if(countAll) {
				projections { countDistinct("id") }
			}
			
			if(search.kravStatus) {
				'in'("kravStatus", search.kravStatus)
			}
			
			if(search.kravType) {
				eq("kravType", search.kravType)
			}
						
			if(search.produktNummer) {
				eq("produktNummer", search.produktNummer)
			}
						
			if(search.klynge) {
				intervjuer {
					klynge {
						eq("id", search.klynge)
					}
				}
			}
			
			if(search.bilagsnummer) {
				utlegg {
					eq("id", search.bilagsnummer)	
				}
			}
			
			if(search.intervjuer) {
				intervjuer {
					eq("id", search.intervjuer)
				}
			}

			if(search.initialer) {
				intervjuer {
					ilike("initialer", search.initialer)
				}
			}
			
			if(search.fraDato && search.tilDato) {
				between("dato", utilService.getFromDate(search.fraDato), utilService.getToDate(search.tilDato))
			}
			else if(search.fraDato) {
				gt("dato", utilService.getFromDate(search.fraDato))
			}
			else if(search.tilDato) {
				lt("dato", utilService.getToDate(search.tilDato))
			}
		}
		
		return kravIds
	}
	
	
	public List<Krav> finnKravTilManuellKontroll() {
		def kravList = finnKravTilManuellKontroll(finnIntervjuereTilManuellKontroll()) 
		return kravList 
	}
	
	
	List<Krav> finnKravTilManuellKontroll(int prosent) {
		return finnKravTilManuellKontroll(finnIntervjuereTilManuellKontroll(prosent))
	}
	
	List<Krav> finnKravTilManuellKontroll(List<Intervjuer> intervjuerListe) {
		if(!intervjuerListe) {
			return []
		}
				
		List<Krav> manKonListe = Krav.createCriteria().list() {
			and {
				eq("kravStatus", KravStatus.BESTOD_AUTOMATISK_KONTROLL)
				'in'("intervjuer", intervjuerListe)
			}
		}
		
		manKonListe.each { k ->
			k.kravStatus = KravStatus.TIL_MANUELL_KONTROLL
		}
		
		return manKonListe
	}
	
	List<Intervjuer> finnIntervjuereTilManuellKontroll() {
		return finnIntervjuereTilManuellKontroll(grailsApplication.config.sil.intervjuer.kontroll.prosent)
	}
	
	List<Intervjuer> finnIntervjuereTilManuellKontroll(int prosent) {
		List<Intervjuer> intervjuerListe = Intervjuer.list()
		
		if(!intervjuerListe) {
			return []
		}
		
		// Finn ut hvor mange intervjuere som skal til plukkes ut til manuell kontroll
		int cnt = Math.round(intervjuerListe.size() * (prosent/100))
		
		// Organiser listen i tilfeldig rekkefølge
		Collections.shuffle(intervjuerListe)
		
		// Plukk ut det antallet som skal til manuell kontroll fra listen, de ${cnt} første i listen
		List intervjuereTilManKonListe = intervjuerListe[0..(cnt-1)]
		
		return intervjuereTilManKonListe
	}
	
	Krav konverterTimeforingTilKrav(Timeforing t) {
		if(!t) {
			log.error "Kan ikke konvertere Timeforing som er NULL"
			return null;
		}
		Krav krav = new Krav()
		krav.dato = t.fra
		krav.intervjuer = t.intervjuer
		krav.timeforing = t
		krav.antall = utilService.beregnTidMinutter(t.fra, t.til)
		
		krav.kravType = KravType.T
		krav.produktNummer = t.produktNummer
		
		return krav
	}
	
	Krav konverterKjorebokTilKrav(Kjorebok k){
		if(!k) {
			log.error "Kan ikke konvertere Kjorebok som er NULL"
			return null;
		}
		Krav krav = new Krav()	
		krav.dato = k.fraTidspunkt
		krav.intervjuer = k.intervjuer
		if(k.erKm()) {
			krav.antall = k.kjorteKilometer
		}
		else if(k.transportmiddel == TransportMiddel.FERJE) {
			krav.antall = k.utleggFerge?.belop
		}
		else if(k.transportmiddel == TransportMiddel.LEIEBIL || k.transportmiddel == TransportMiddel.GIKK) {
			krav.antall = utilService.beregnTidMinutter(k.fraTidspunkt, k.tilTidspunkt)
		}
		else {
			krav.antall = k.utleggBelop?.belop
		}
		
		// Det kan forekomme kjørebøker der antall er null,
		// f.eks. ved leiebil, gikk mm., kjøreboken er da
		// ført for å få betalt for tidbrukt og antall vil
		// da være 0
		if(!krav.antall) {
			krav.antall = new Double(0)
		}
		
		krav.kjorebok = k
		krav.kravType = KravType.K
		krav.produktNummer = k.produktNummer
			
		return krav
	}
	
	Krav konverterUtleggTilKrav(Utlegg u) {
		if(!u) {
			log.error "Kan ikke konvertere Utlegg som er NULL"
			return null;
		}
		Krav krav = new Krav()
		krav.dato = u.dato
		krav.intervjuer = u.intervjuer
		krav.utlegg = u
		krav.antall = u.belop
		krav.kravType = KravType.U
		krav.produktNummer = u.produktNummer
		
		return krav
	}
	
	int setKravForIntervjuerForDatoInaktiv(Krav krav) {
		if(!krav?.dato || !krav?.intervjuer) {
			return 0
		}
		
		Date fraDato = utilService.getFromDate(krav.dato)
		Date tilDato = utilService.getToDate(krav.dato)
		
		List<Krav> kravListe = Krav.createCriteria().list() {
			and {
				ne("id", krav.id)
				eq("intervjuer", krav.intervjuer)
				between("dato", fraDato, tilDato)
				ne("kravStatus", KravStatus.RETTET_AV_INTERVJUER)
			}
		}
		
		kravListe.each { k -> 
			k.setForrigeKravStatus  k.kravStatus
			k.setKravStatus KravStatus.INAKTIV
			k.save(failOnError: true)
		}
	
		return kravListe.size()	
	}
	
	int godkjennAlleForIntervjuer(Intervjuer intervjuer) {
		List<Krav> kravListe = Krav.createCriteria().list() {
			eq("intervjuer", intervjuer)
			'in'("kravStatus", [KravStatus.FEILET_AUTOMATISK_KONTROLL, KravStatus.TIL_MANUELL_KONTROLL, KravStatus.BESTOD_AUTOMATISK_KONTROLL])
		}
		
		kravListe.each { k ->
			k.setKravStatus KravStatus.GODKJENT
			k.save(failOnError: true, flush: true)
		}
		
		return kravListe.size()
	}
	
	boolean avvisKrav(Krav krav) {
		return sivAdmService.avvisKrav(krav)
	}
	
	/**
	 * Metode for å endre krav status fra SENDES_TIL_INTERVJUER til TIL_RETTING_INTERVJUER for
	 * en gitt intervjuer. Tar også "kopi" av nåværende krav (timeføring, kjørebok eller utlegg)
	 * og lagrer dette på kravet for å kunne vite hva som er endret når kravet kommer tilbake
	 * fra retting hos intervjueren. I tillegg blir datoene som de kravene som endres tilhører
	 * åpnet for føring av timer, kjørebok og utlegg (har vært "låst" siden intervjueren har
	 * "sendt inn" disse føringene).
	 * 
	 * @param intervjuer Intervjueren det skal sendes tilbake krav til retting for
	 * @return En liste med krav som er har fått status KravStatus.TIL_RETTING_INTERVJUER
	 */
	List<Krav> sendTilbakeForIntervjuer(Intervjuer intervjuer) {
		List<Krav> kravListe = Krav.findAllByKravStatusAndIntervjuer(KravStatus.SENDES_TIL_INTERVJUER, intervjuer)
		
		if(!kravListe) {
			return null
		}
		List<Date> datoListe = []
		kravListe.each { k ->
			k.setKravStatus KravStatus.TIL_RETTING_INTERVJUER
			if(k.kravType == KravType.T) {
				Timeforing t = new Timeforing(arbeidsType: k.timeforing.arbeidsType, produktNummer: k.timeforing.produktNummer, fra: k.timeforing.fra, til: k.timeforing.til)
				t.save(failOnError: true, flush: true)
				k.setOpprinneligTimeforing t
			}
			else if(k.kravType == KravType.K) {
				Kjorebok kj = new Kjorebok()
				kj.transportmiddel = k.kjorebok.transportmiddel
				kj.produktNummer = k.kjorebok.produktNummer
				kj.fraTidspunkt = k.kjorebok.fraTidspunkt
				kj.tilTidspunkt = k.kjorebok.tilTidspunkt
				kj.fraAdresse = k.kjorebok.fraAdresse
				kj.tilAdresse = k.kjorebok.tilAdresse
				kj.fraPoststed = k.kjorebok.fraPoststed
				kj.tilPoststed = k.kjorebok.tilPoststed
				kj.kjorteKilometer = k.kjorebok.kjorteKilometer
				kj.merknad = k.kjorebok.merknad
				kj.antallPassasjerer = k.kjorebok.antallPassasjerer
				kj.timeforing = k.kjorebok.timeforing				
				kj.save(failOnError: true, flush: true)
				k.setOpprinneligKjorebok kj
			}
			else if(k.kravType == KravType.U) {
				Utlegg u = new Utlegg()
				u.produktNummer = k.utlegg.produktNummer
				u.utleggType = k.utlegg.utleggType
				u.spesifisering = k.utlegg.spesifisering
				u.merknad = k.utlegg.merknad
				u.belop = k.utlegg.belop
				u.dato = k.utlegg.dato
								
				u.save(failOnError: true, flush: true)
				k.setOpprinneligUtlegg u
			}
			k.save(failOnError: true, flush: true)
			datoListe << k.dato
		}
		
		datoListe.each { d ->
			Date fraDato = utilService.getFromDate(d)
			Date tilDato = utilService.getToDate(d)
					
			def statusListe = [KravStatus.TIL_RETTING_INTERVJUER, KravStatus.AVVIST]
			
			List<Krav> kList = Krav.createCriteria().list() {
				and {
					eq("intervjuer", intervjuer)
					between("dato", fraDato, tilDato)
					not {
						'in'("kravStatus", statusListe)	
					}
				}
			}
			kList.each { kr ->
				if(kr.kravStatus != KravStatus.INAKTIV) {
					kr.setForrigeKravStatus  kr.kravStatus
					kr.setKravStatus KravStatus.INAKTIV
					kr.save(failOnError: true, flush: true)
				}
			}
		}
		
		boolean apne = sivAdmService.apneDagForTimeforingOgSettMelding(kravListe)
				
		return kravListe
	}
	
	int godkjennAlleForIntervjuer(Long id) {
		return godkjennAlleForIntervjuer(Intervjuer.get(id))
	}
	
	/**
	 * Metode for å godkjenne et gitt krav. Godkjenner også tilhørende krav.
	 * Tilhørende krav er utlegg til bom, parkering, ferge, billett mm. i
	 * kjørebok og timeføring tilhørende kjørebok.
	 * @param krav Kravet som skal godkjennes
	 * @return Returnerer kravet etter godkjenning eller null hvis kravet til
	 * godkjenning har en status som gjør at det ikke er lov å status til GODKJENT
	 */
	Krav godkjennKrav(Krav krav) {
		// Det er ikke lov å godkjenne krav som har status
		// TIL_RETTING_INTERVJUER, SENDES_TIL_INTERVJUER eller
		// OVERSENDT_SAP
		if(krav.kravStatus == KravStatus.TIL_RETTING_INTERVJUER || 
			krav.kravStatus == KravStatus.OVERSENDT_SAP ||
			krav.kravStatus == KravStatus.SENDES_TIL_INTERVJUER) {
		
			return null
		}
		
		if(krav.kravStatus == KravStatus.INAKTIV) {
			krav.setForrigeKravStatus KravStatus.GODKJENT
		}
		else {
			krav.setKravStatus KravStatus.GODKJENT
		}
		
		krav.save(failOnError: true, flush: true)
	
		def kravListe = finnTilhorendeKrav(krav)
		
		kravListe.each { Krav k ->
			// Hvis det tilhørende kravet er timeføring skal det
			// godkjennes uansett, men hvis det gjelder et utlegg
			// som er avvist blir det ikke automatisk godkjent da
			// dette kan være avvist separat og må eventuelt godkjennes
			// vha egen link i skjema
			if(k.kravType == KravType.T || k.kravStatus != KravStatus.AVVIST) {
				if(k.kravStatus == KravStatus.INAKTIV) {
					k.setForrigeKravStatus KravStatus.GODKJENT
				}
				else {
					k.setKravStatus KravStatus.GODKJENT
				}
			
				k.save(failOnError: true, flush: true)
			}
		}
		
		return krav
	}
	
	/**
	 * Metode for å godkjenne alle krav som har status
	 * BESTOD_AUTOMATISK_KONTROLL
	 * @return antallet krav som er satt til KravStatus.GODKJENT
	 */
	public int godkjennAlleBestodAutomatiskKontroll() {
		
		List<Krav> kravListe = Krav.findAllByKravStatus(KravStatus.BESTOD_AUTOMATISK_KONTROLL)
		
		kravListe.each { krav ->
			krav.setKravStatus KravStatus.GODKJENT
			krav.save(failOnError: true)
		}
		
		return kravListe.size()
	}
	
	/**
	 * Metode for å importere timeføringer, kjørebøker og utlegg som har status
	 * Sendt inn fra SivAdmin og generere Krav av disse, kjøre disse kravene gjennom
	 * automatiske kontroller og plukket ut en prosentandel (settes i konfigurasjonsfil)
	 * av intervjuerene som har levert inn krav til manuell kontroll.
	 * 
	 * @return Returnerer en liste med 2 Integere den første forteller hvor mange Krav
	 * som er importert og kjørt gjennom automatiske kontroller og det andre fortelle
	 * hvor mange intervjuere som har blitt trukket ut til manuell kontroll
	 */
	List<Integer> importerKravKjorAutoKontrollerGenererTilManuellKontroll() {

		List<Timeforing> timeforinger = sivAdmService.hentInnsendteTimeforinger()
		List<Kjorebok> kjoreboker = sivAdmService.hentInnsendteKjoreboker()
		List<Utlegg> utlegg = sivAdmService.hentInnsendteUtlegg()

		// Hent lister med nye krav første liste av krav og krav som allerede
		// eksisterer og skal "aktiveres" igjen (liste nr 2)
		List<List<Krav>> liste = konverterTilKrav(timeforinger, kjoreboker, utlegg)

		def tilKontListe = []

		// Loop gjennom krav og hvis kravet har et opprinnelig krav dvs. har vært
		// endret skal dette til kravet uansett til manuell kontroll, hvis ikke
		// legg det til listen av krav som skal kontrolleres av de automatiske
		// kontrollene
		liste[0].each { krav ->
			if(krav.opprinneligKrav) {
				krav.setKravStatus KravStatus.TIL_MANUELL_KONTROLL
				krav.save(failOnError:true, flush: true)
			}
			else {
				tilKontListe << krav
			}
		}

		// Kjør automatiske kontroller på krav som ikke er satt til manuell kontroll
		List<Krav> bestodListe = automatiskKontrollService.kjorAutomatiskeKontroller(tilKontListe, false)

		// Finn liste over intervjuere hvis krav skal til manuell kontroll
		// Dette er x antall prosent (settes i config)
		List<Intervjuer> intervjuerListe =  finnIntervjuereTilManuellKontroll()

		// Finn alle krav tilknyttet intervjuere som er valgt ut til manuell kontroll
		List<Krav> manKontrollListe = finnKravTilManuellKontroll(intervjuerListe)

		List<Integer> intList = [
			liste[0].size(),
			intervjuerListe.size()
		]

		return intList
	}
	
	/*
	 * Metode for å hente tilbake krav som er sendt tilbake til intervjueren for retting. Alle timeføringer,
	 * kjørebøker og utlegg intervjueren har for denne datoen får status SENDT_INN og blir så importert inn
	 * til krav i SIL. 
	 */
	boolean hentTilbakeKravFraRettingIntervjuer(Intervjuer intervjuer, Date dato) {
		sivAdmService.sendInnForingerForIntervjuerForDato(intervjuer, dato)
		
		List<Timeforing> timeforinger = sivAdmService.hentInnsendteTimeforinger(intervjuer, dato)
		List<Kjorebok> kjoreboker = sivAdmService.hentInnsendteKjoreboker(intervjuer, dato)
		List<Utlegg> utlegg = sivAdmService.hentInnsendteUtlegg(intervjuer, dato)
		
		List<List<Krav>> liste = konverterTilKrav(timeforinger, kjoreboker, utlegg)
		
		def tilKontListe = []
		
		liste[0].each { krav ->
			if(krav.opprinneligKrav) {
				krav.setKravStatus KravStatus.TIL_MANUELL_KONTROLL
				krav.save(failOnError:true, flush: true)
			}
			else {
				tilKontListe << krav
			}
		}
		
		List<Krav> bestodListe = automatiskKontrollService.kjorAutomatiskeKontroller(tilKontListe, false)
		
		return true
	}
	
	public List finnKontrollerSomFeilet(Krav krav) {
		if(!krav) {
			return null
		}
		
		List<AutomatiskKontroll> liste = new ArrayList<AutomatiskKontroll>()
		
		if(krav.automatiskeKontroller) {
			liste.addAll(krav.automatiskeKontroller)
		}
		
		def kravListe = finnTilhorendeKrav(krav)
		
		kravListe.each {
			if(it?.automatiskeKontroller) {
				liste.addAll(it.automatiskeKontroller)
			}	
		}
			
		return liste
	}
	
	
	/**
	 * Metode for å finne tilhørende krav til kravet gitt. Tilhørende krav
	 * er f.eks. timeføring-krav for en kjørebok, eller utlegg i forbindelse
	 * med en kjørebok når transportmiddel ikke er "km-basert" (ferge, taxi, buss,
	 * trikk eller tog).
	 * @param krav Kravet som skal sjekkes for tilhørende krav
	 * @return Returnerer en liste med krav som tilhører kravet gitt.
	 */
	public List finnTilhorendeKrav(Krav krav) {
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
		}
								
		return kravListe
	}
	
	/**
	 * Metode for å finne utlegg som er knyttet opp til en kjørebok.
	 * Dette er utlegg for f.eks. bompenger, parkering mm.
	 * @param kjorebok Kjøreboken som skal sjekkes
	 * @return En list med tilhørende utlegg, eller null hvis ingen utlegg finnes.
	 */
	private List finnUtleggKrav(Kjorebok kjorebok) {
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
	 * Metode for å konvertere timføringer, kjørebøker og utlegg til krav. Hvis timeføringen,
	 * kjøreboken eller utlegget allerede har et krav knyttet opp mot seg, blir dette blir det
	 * lagd et nytt krav hvis det er snakk om endring eller kravet blir aktivert hvis det
	 * bare har vært satt til inaktiv pga at dagen det tilhører har vært åpnet for føring av
	 * timer, kjørebøker eller utlegg igjen. Hvis det ikke allerede finnes et krav tilhørende
	 * timeføringne, kjørebokene eller utlegget blir det generert et krav.
	 * 
	 * @param timeforinger Liste med timeføringer som skal konverteres til Krav
	 * @param kjoreboker Liste med kjørebøker som skal konverteres til Krav
	 * @param utlegg Liste med utlegg som skal konverteres til Krav
	 * @return Returnerer en liste med 2 lister av Krav, den første listen består av
	 * nye krav (dvs. timeføringen, kjøreboken eller utlegget har ikke noe krav knyttet opp
	 * til seg allerede) og den andre listen består av krav der timeføringen, kjøreboken eller
	 * utlegget allerede har et krav knyttet opp mot seg, og kravet blir da "aktivert" igjen.
	 */
	List<List<Krav>> konverterTilKrav(List<Timeforing> timeforinger, List<Kjorebok> kjoreboker, List<Utlegg> utlegg) {
		
		List<List<Krav>> retList = []
		List<Krav> kravList = []
		List<Krav> gamleKrav = []
		
		if(!timeforinger) {
			//log.info "Listen med Timeforing er tom"
		}
		else {
			timeforinger.each { t ->
				
				List<Krav> kList = Krav.findAllByTimeforing(t)
				
				if(kList) {
					if(kList[-1].kravStatus == KravStatus.TIL_RETTING_INTERVJUER || timeforingForandret(t, kList[-1].timeforing)) {
						kList[-1].setKravStatus KravStatus.RETTET_AV_INTERVJUER
						kList[-1].save(failOnError: true, flush: true)
						
						Krav kr = konverterTimeforingTilKrav(t)
						kr.setOpprinneligKrav kList[-1]
						kr.save(failOnError: true, flush: true)
						
						kravList.add kr
					}
					else if(kList[-1].kravStatus == KravStatus.INAKTIV) {
						kList[-1].setKravStatus kList[-1].forrigeKravStatus
						kList[-1].setForrigeKravStatus null
						
						kList[-1].save(failOnError: true, flush: true)
						gamleKrav.add kList[-1]
					}
				}
				else {
					kravList.add konverterTimeforingTilKrav(t)
				}
				
				t.timeforingStatus = TimeforingStatus.BEHANDLET
				t.save(failOnError: true, flush: true)
			}
		}
		
		if(!kjoreboker) {
			//log.info "Listen med Kjorebok er tom"
		}
		else {
			kjoreboker.each { k ->
				List<Krav> kList = Krav.findAllByKjorebok(k)
				if(kList) {
					if(kList[-1].kravStatus == KravStatus.TIL_RETTING_INTERVJUER || kjorebokForandret(k, kList[-1].kjorebok)) {
						kList[-1].setKravStatus KravStatus.RETTET_AV_INTERVJUER
						kList[-1].save(failOnError: true, flush: true)
						
						Krav kr = konverterKjorebokTilKrav(k)
						kr.setOpprinneligKrav kList[-1]
						
						kravList.add kr
					}
					else if(kList[-1].kravStatus == KravStatus.INAKTIV) {
						kList[-1].setKravStatus kList[-1].forrigeKravStatus
						kList[-1].setForrigeKravStatus null
						
						kList[-1].save(failOnError: true, flush: true)
						gamleKrav.add kList[-1]
					}
				}
				else {
					kravList.add konverterKjorebokTilKrav(k)
				}
				
				k.timeforingStatus = TimeforingStatus.BEHANDLET
				k.save(failOnError: true, flush: true)
			}
		}
		
		if(!utlegg) {
			//log.info "Listen med Utlegg er tom"
		}
		else {
			utlegg.each { u ->
				List<Krav> kList = Krav.findAllByUtlegg(u)
				if(kList) {
					if(kList[-1].kravStatus == KravStatus.TIL_RETTING_INTERVJUER || utleggForandret(u, kList[-1].utlegg)) {
						kList[-1].setKravStatus KravStatus.RETTET_AV_INTERVJUER
						kList[-1].save(failOnError: true, flush: true)
						
						Krav kr = konverterUtleggTilKrav(u)
						kr.setOpprinneligKrav kList[-1]
						kr.save(failOnError: true, flush: true)
																
						kravList.add kr
					}
					else if(kList[-1].kravStatus == KravStatus.INAKTIV) {
						kList[-1].setKravStatus kList[-1].forrigeKravStatus
						kList[-1].setForrigeKravStatus null
						
						kList[-1].save(failOnError: true, flush: true)
						gamleKrav.add kList[-1]
					}
				}
				else {
					kravList.add konverterUtleggTilKrav(u)
				}
				
				u.timeforingStatus = TimeforingStatus.BEHANDLET
				u.save(failOnError: true, flush: true)
			}
		}
		
		retList << kravList
		retList << gamleKrav
		
		return retList
	}
	
	/**
	* Metode for å sjekk om felter i et utlegg som gjør at
	* et krav i SIL må endres er endret.
	* @param u1 Utlegg en
	* @param u2 Utlegg to
	* @return true hvis felter i u1 ikke er lik felter i u2, false ellers
	*/
	boolean utleggForandret(Utlegg u1, Utlegg u2) {
		if(!u1 || !u2) {
			return true;
		}
		
		if(u1.dato != u2.dato)
			return true
		
		if(u1.belop != u2.belop)
			return true
			
		if(u1.produktNummer != u2.produktNummer)
			return true
		
		return false
	}
	
	/**
	* Metode for å sjekk om felter i en kjørebok som gjør at
	* et krav i SIL må endres er endret.
	* @param k1 Kjørebok en
	* @param k2 Kjørebok to
	* @return true hvis felter i k1 ikke er lik felter i k2, false ellers
	*/
	boolean kjorebokForandret(Kjorebok k1, Kjorebok k2) {
		if(!k1 || !k2) {
			return true;
		}
		
		if(k1.tilTidspunkt != k2.tilTidspunkt)
			return true
		
		if(k1.fraTidspunkt != k2.fraTidspunkt)
			return true
		
		if(k1.kjorteKilometer != k2.kjorteKilometer)
			return true
			
		if(k1.transportmiddel != k2.transportmiddel)
			return true
			
		if(k1.produktNummer != k2.produktNummer)
			return true
			
		return false
	}
	
	/**
	 * Metode for å sjekk om felter i en timeføring som gjør at
	 * et krav i SIL må endres er endret.
	 * @param t1 Timeføring en
	 * @param t2 Timeføring to
	 * @return true hvis felter i t1 ikke er lik felter i t2, false ellers
	 */
	boolean timeforingForandret(Timeforing t1, Timeforing t2) {
		if(!t1 || !t2) {
			return true;
		}
		
		if(t1.fra != t2.fra)
			return true
			
		if(t1.til != t2.til)
			return true
			
		if(t1.produktNummer != t2.produktNummer)
			return true
		
		return false;
	}
	
	/**
	* Metode for å finne antall hele timer fra minutter angitt.
	* Input minutter på 150, vil returnere 2 (120 min).
	* @param minutter Antall minutter som skal sjekkes for hele timer.
	* @return
	*/
	public Integer finnTimer(Integer minutter) {
		if(!minutter) {
			return null
		}
		return Math.abs(minutter/60)
	}
	
	/**
	 * Metode for å finne minuttene i en time minutt fordeling av minutter.
	 * Input minutter på 150, vil returnere 30, de resterende 120 er hele timer.
	 * @param minutter
	 * @return
	 */
	public Integer finnMinutter(Integer minutter) {
		if(!minutter) {
			return null	
		}
		return minutter%60
	}

	def slettSilMeldinger = {
		String antallAar = grailsApplication.config.behold.fravaer.antall.aar
		log.info("Kjører slettSilMeldinger, nuller referanser til silmelding eldre enn " + (antallAar - 1) + " år og 11 måneder")

		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))
		cal.add(Calendar.MONTH, 1)

		Krav.executeUpdate("update Krav set silMelding = null where dato < :dato and silMelding is not null",
				[dato: cal.getTime()])
		Timeforing.executeUpdate("update Timeforing set silMelding = null where fra < :dato and silMelding is not null",
				[dato: cal.getTime()])
		Kjorebok.executeUpdate("update Kjorebok set silMelding = null where fraTidspunkt < :dato and silMelding is not null",
				[dato: cal.getTime()])
		Utlegg.executeUpdate("update Utlegg set silMelding = null where dato < :dato and silMelding is not null",
				[dato: cal.getTime()])

		SilMelding.executeUpdate("delete from SilMelding where dato < :dato",[dato: cal.getTime()])

		log.info("Har slettet silMeldinger eldre enn " + (antallAar - 1) + " år og 11 måneder")
	}

	def ryddKravUtenKjorebok = {

		String antallAar = grailsApplication.config.behold.fravaer.antall.aar
		log.info("Kjører ryddKravUtenKjorebok, sletter krav eldre enn " + antallAar + " år")

		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))

		Krav.executeUpdate("delete from Krav where dato < :dato and kjorebok is null", [dato: cal.getTime()])
		log.info('Har slettet krav uten kjørebok eldre enn ' + cal.getTime());
	}

}