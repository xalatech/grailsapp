package sivadm

import java.util.ArrayList
import java.util.Date

import util.DateUtil
import util.StringUtil
import util.TimeUtil
import siv.data.*
import siv.rapport.data.IntervjuerRapportArbeidstid
import siv.type.ArbeidsType
import siv.type.IntervjuerArbeidsType
import siv.type.IntervjuerStatus;

class IntervjuerService {

	def skjemaService
	def timeforingService

	boolean transactional = true

	
	public void oppdaterIntervjuerHvisBrukerErEndret(Bruker bruker) {	
		if( bruker.isDirty('username')) {			
			def forrigeInitialer = bruker.getPersistentValue('username')		
			def intervjuer = Intervjuer.findByInitialer( forrigeInitialer )
			
			if( intervjuer ) {
				intervjuer.initialer = bruker.username
				intervjuer.navn = bruker.navn
				intervjuer.save()
			}
		}
	}
	
	
	
	
	
	
	/**
	 * Finner intervjuer basert paa initialer. Er ikke case sensitiv.
	 * 
	 * @param initialer
	 */
	public Intervjuer finnIntervjuerForInitialer(String initialer) {
		
		def c = Intervjuer.createCriteria()
		
		def list = c {
			ilike("initialer", initialer.trim())
		}
	
		if(list != null && list.size() > 0){
			return list.get(0)
		}
		else{
			return null
		}
	}
	

	/**
	 * CATI:
	 * 
	 * Henter en liste med UtvalgtSkjema for en gitt intervjuer. Denne listen inneholder
	 * de CATI-skjemaene intervjueren er satt opp til aa vaere med paa. Metoden gjor ogsaa
	 * en filtrering paa noen parametre, som oppstartsdato, sluttdato, om skjema er
	 * aktivert for intervjuing eller ikke.
	 * 
	 * @param initialer
	 * @return en liste med UtvalgtSkjema
	 */
	public List getUtvalgteSkjemaForIntervjuer(String initialer) {
		Intervjuer intervjuer = Intervjuer.findByInitialer( initialer )

		List<CatiGruppe> catiGrupper = intervjuer ? CatiGruppe.getByIntervjuerId( intervjuer.id ) : []

		ArrayList utvalgteSkjema = new ArrayList()

		for( CatiGruppe catiGruppe : catiGrupper ) {
			for( Skjema skjema: catiGruppe.skjemaer ) {

				if( DateUtil.isExpired( skjema.oppstartDataInnsamling ) == false ) {
					log.info "Filtert vekk skjema (${skjema.skjemaNavn}. Oppstart datainnsamling ikke naadd ennaa."
				}
				else if ( skjema.sluttDato && DateUtil.isExpired( skjema.sluttDato ) == true ) {
					log.info "Filtert vekk skjema (${skjema.skjemaNavn}. Sluttdato er passert."
				}
				else if ( skjema.aktivertForIntervjuing == false ) {
					log.info "Filtert vekk skjema (${skjema.skjemaNavn}. Skjema ikke aktivert for intervjuing"
				}
				else {
					UtvalgtSkjema u = new UtvalgtSkjema()
					u.skjemaKortNavn = skjema ? skjema.skjemaKortNavn : null
					u.antallLoggetPaa = 0
					u.antallOnsket = 0
					u.oppstart = skjema?.oppstartDataInnsamling
					u.blaiseSkjemaFunnet = "APPLET"
					u.skjemaVersjon = skjemaService.findLatestSkjemaVersjonsNummer( skjema )

					utvalgteSkjema.add u
				}
			}
		}

		return utvalgteSkjema
	}


	/**
	 * CAPI:
	 *
	 * Henter en liste med UtvalgtCapiSkjema for en gitt intervjuer. Denne listen inneholder
	 * de CAPI-skjemaene intervjueren er satt opp til aa vaere med paa.
	 *
	 * @param initialer
	 * @return en liste med UtvalgtCapiSkjema
	 */
	List getUtvalgteCapiSkjemaForIntervjuer(String initialer) {

		def intervjuer = Intervjuer.findByInitialer( initialer )
		
		def oppdragList = hentAlleCapiSkjemaKortNavnForIntervjuer(intervjuer)

		def skjemaList = new ArrayList()

		oppdragList.each {
			skjemaList.add(Skjema.findBySkjemaKortNavnIlike(it))
		}

		def utvalgtCapiSkjemaList = new ArrayList<UtvalgtCapiSkjema>()

		skjemaList.each {
			Skjema skjema = it

			UtvalgtCapiSkjema ucSkjema = new UtvalgtCapiSkjema()

			ucSkjema.skjemaId = skjema.id
			ucSkjema.skjemaNavn = skjema.skjemaNavn
			ucSkjema.skjemaKortNavn = skjema.skjemaKortNavn
			ucSkjema.antallIntervjuObjekterTilordnet = getUtvalgteCapiIntervjuObjekter(initialer, skjema).size()
			ucSkjema.skjemaVersjon = skjemaService.findLatestSkjemaVersjonsNummer( skjema )

			utvalgtCapiSkjemaList.add ucSkjema
		}

		return utvalgtCapiSkjemaList
	}


	/**
	 * Konverterer en CAPI skjema liste over til en kommabasert liste med skjemanavn og skjemaversjoner
	 * for bruk i CAPI-applet.
	 * 
	 * @param utvalgtCapiSkjemaInstanceList
	 * @return en String med skjemaer og versjoner (lev__1,ferie__2) osv.
	 */
	public String getIntervjuerCapiSkjemaVersionList( List<UtvalgtCapiSkjema> utvalgtCapiSkjemaInstanceList  ) {

		StringBuffer skjemaList = new StringBuffer();

		if( utvalgtCapiSkjemaInstanceList == null )
			return null;

		for (UtvalgtCapiSkjema dto : utvalgtCapiSkjemaInstanceList) {
			skjemaList.append(dto.skjemaKortNavn)
					.append("__")
					.append(dto.skjemaVersjon)
					.append(",");
		}

		return StringUtil.trimCommaSeparatedString(skjemaList.toString());
	}


	/**
	 * Henter ut alle CAPI-intervjuobjekter for en gitt intervjuer
	 *  
	 * @param initialer
	 * @return 
	 */
	List getAlleUtvalgteCapiIntervjuObjekter(String initialer) {
		Intervjuer intervjuer = Intervjuer.findByInitialer(initialer)

		def oppdragList = hentAlleOppdragForIntervjuer(intervjuer)

		def intervjuObjektList = new ArrayList()

		oppdragList.each { oppdrag ->
			intervjuObjektList.add (oppdrag.intervjuObjekt)
		}

		return intervjuObjektList
	}


	/**
	 * Henter alle intervjuobjekter for en gitt intervjuer og Capi-skjema paa
	 * et format av typen UtvalgtCapiIntervjuObjekt. Dette er en dto med litt
	 * samlet informasjon.
	 * 
	 * @param initialer
	 * @param skjema
	 * @return en liste med alle intervjuobjekter.
	 */
	public List getUtvalgteCapiIntervjuObjekter(String initialer, Skjema skjema) {
		def intervjuer = Intervjuer.findByInitialer( initialer )

		def oppdragList = hentAlleOppdragForIntervjuerOgSkjema(intervjuer, skjema)

		def utvalgCapiIntervjuObjektList = new ArrayList()

		oppdragList.each {
			Oppdrag oppdrag = it

			def intervjuObjekt = new UtvalgtCapiIntervjuObjekt()

			intervjuObjekt.id = oppdrag.intervjuObjekt.id
			intervjuObjekt.intervjuObjektNummer = oppdrag.intervjuObjekt.intervjuObjektNummer
			intervjuObjekt.navn = oppdrag.intervjuObjekt.navn

			utvalgCapiIntervjuObjektList.add(intervjuObjekt)
		}

		return utvalgCapiIntervjuObjektList
	}

	public List intervjuerRapportArbeidstid(Intervjuer inter, Date fra, Date til, String produktNummer, String arbeidsType, String gruppertPaa) {
		
		def crit = Timeforing.createCriteria()
		Date fraDato = null
		Date tilDato = null
		
		if(fra) {
			fraDato = TimeUtil.getStartOfDay(fra)
		}
		if(til) {
			tilDato = TimeUtil.getStartOfNextDay(til)
		}
		
		def arbTid = crit {
						
			intervjuer {
				eq("id", inter.id)
			}
			
			if(fraDato) {
				ge("fra", fraDato)
			}
			
			if(tilDato) {
				lt("fra", tilDato)
			}
			
			if(produktNummer){
				eq("produktNummer", produktNummer)
			}
			
			if(arbeidsType){
				eq("arbeidsType", ArbeidsType.valueOf(arbeidsType))
			}
			
			if(gruppertPaa && gruppertPaa == "dato") {
				order("fra", "asc")
			}
			else if(gruppertPaa && gruppertPaa == "prosjekt") {
				order("produktNummer", "asc")
			}
			else {
				order("arbeidsType", "asc")
			}
		}
				
		def returnList = []
		IntervjuerRapportArbeidstid ira = null
		
		arbTid.each { arb ->
			
			int sumMinutes = DateUtil.getMinutesBetweenDates(arb.fra, arb.til)
			Date d = TimeUtil.getStartOfDay(arb.fra)
			if(gruppertPaa && gruppertPaa == "dato") {
				if(!ira || ira.dato != d) {
					if(ira) {
						returnList << ira.sjekkTid()
					}
					Integer antKm = timeforingService.getAntallKilometerForIntervjuer (arb.intervjuer, produktNummer, d)
					Double belop = timeforingService.getTotalBelopForIntervjuer (arb.intervjuer, produktNummer, d)
					ira = new IntervjuerRapportArbeidstid(dato: d, antKm: antKm, belop: belop)
				}
			}
			else if(gruppertPaa && gruppertPaa == "prosjekt") {
				if(!ira || ira.produktNummer != arb.produktNummer) {
					if(ira) {
						returnList << ira.sjekkTid()
					}
					
					Integer antKm = timeforingService.getAntallKilometerForIntervjuer(arb.intervjuer, arb.produktNummer, fra, til)
					Double belop = timeforingService.getUtleggsBelopForIntervjuer(arb.intervjuer, arb.produktNummer, fra, til)
					
					ira = new IntervjuerRapportArbeidstid(produktNummer: arb.produktNummer, antKm: antKm, belop: belop)
					ira.navn = finnNavn(arb.produktNummer)
				}
			}
			else {
				if(!ira || ira.arbeidstype != arb.arbeidsType.toString()) {
					if(ira) {
						returnList << ira.sjekkTid()
					}
					ira = new IntervjuerRapportArbeidstid(arbeidstype: arb.arbeidsType.toString())
				}
			}
			ira.timer += sumMinutes/60
			ira.minutter += sumMinutes%60
		}
		
		if(ira) {
			returnList << ira.sjekkTid()
		}

		// Ref. https://jira.ssb.no/browse/SF-14
		// Det kan forekomme utlegg som ikke har timeføringer.
		// Disse ønskes tatt med i rapporten, både under
		// "Tid prukt pr. dag" og "Tid brukt pr. prosjekt".
		ira = null
		def tilleggsliste = []

		if (gruppertPaa == 'dato') {

			def utleggListe = timeforingService.getUtleggUtenTimeforing(inter, fra, til, produktNummer, arbeidsType)

			utleggListe.each { Utlegg utlegg ->
				Date d = TimeUtil.getStartOfDay(utlegg.dato)
				if(!ira || ira.dato != d) {
					if(ira) {
						tilleggsliste.add(ira)
					}
					ira = new IntervjuerRapportArbeidstid(dato: d, belop: utlegg.belop, antKm: 0)
				} else {
					ira.belop += utlegg.belop
				}
			}

			if (ira) {
				tilleggsliste.add(ira)
			}

			if (tilleggsliste.size()>0) {
				tilleggsliste.each { IntervjuerRapportArbeidstid i ->
					returnList.add(i)
				}
				returnList.sort { IntervjuerRapportArbeidstid i ->
					i.dato
				}
			}

		} else if (gruppertPaa == 'prosjekt') {

            def utleggListe = timeforingService.getUtleggUtenTimeforteProsjekter(inter, fra, til, produktNummer, arbeidsType)

            utleggListe.each { utlegg ->
                if(!ira || ira.produktNummer != utlegg.produktNummer) {
                    if(ira) {
                        tilleggsliste.add(ira)
                    }
                    ira = new IntervjuerRapportArbeidstid(produktNummer: utlegg.produktNummer, belop: utlegg.belop, antKm: 0)
					ira.navn = finnNavn(utlegg.produktNummer)
                } else {
                        ira.belop += utlegg.belop
                }
            }

            if (ira) {
                tilleggsliste.add(ira)
            }

			if (tilleggsliste.size()>0) {
				tilleggsliste.each { IntervjuerRapportArbeidstid i ->
					returnList.add(i)
				}
				returnList.sort { IntervjuerRapportArbeidstid i ->
					i.produktNummer
				}
			}
		}

		return returnList
	}


	/**
	 * Henter aktive intervjuere
	 * 
	 * @param params
	 * @return
	 */
	public List<Intervjuer> finnAktiveIntervjuere( Map params ) {	
		return Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV, params)
	}
	
	
	public List<Intervjuer> finnAktiveIntervjuere() {
		return Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV)
	}
	
	
	
	/**
	 * Henter alle CAPI oppdrag for en gitt intervjuer.
	 *
	 * @param intervjuer
	 * @param skjema
	 * @return en liste med alle oppdrag
	 */
	private List<Oppdrag> hentAlleOppdragForIntervjuer(Intervjuer intervjuer) {
		return this.hentAlleOppdragForIntervjuerOgSkjema(intervjuer, null)
	}


	/**
	 * Henter alle CAPI oppdrag for en gitt intervjuer, og spesifisert skjema.
	 * 
	 * @param intervjuer
	 * @param skjema
	 * @return en liste med alle oppdrag
	 */
	private List<Oppdrag> hentAlleOppdragForIntervjuerOgSkjema(Intervjuer intervjuer, Skjema skjema) {

		def c = Oppdrag.createCriteria()
		
		def oppdragList = c {
			eq("intervjuer", intervjuer)
			eq("oppdragFullfort", false)
			eq("slettetHosIntervjuer", false)
			gt("gyldighetsDato", DateUtil.now())

			if(skjema != null) {
				eq("skjemaKortNavn", skjema.skjemaKortNavn.toUpperCase())
			}
		}

		return oppdragList
	}


	/**
	 * Henter alle CAPI skjema kortnavn for en gitt intervjuer.
	 * 
	 * @param intervjuer
	 * @return en liste med CAPI skjema kortnavn
	 */
	private List<String> hentAlleCapiSkjemaKortNavnForIntervjuer(Intervjuer intervjuer) {

		def c = Oppdrag.createCriteria()

		def oppdragList = c {

			projections { distinct("skjemaKortNavn") }

			eq("intervjuer", intervjuer)
			eq("oppdragFullfort", false)
			eq("slettetHosIntervjuer", false)
		}

		return oppdragList
	}
	
	/**
	 * Finner navn på skjema (skjemaNavn) eller produkt bastert på gitt produktNummer.
	 * @param produktNummer
	 * @return navn på skjema eller produkt
	 */
	private String finnNavn(String produktNummer) {
		String str = ""
		
		Skjema s = Skjema.findByDelProduktNummer(produktNummer)
		
		if(s) {
			str = s.skjemaNavn	
		}
		else {
			Produkt p = Produkt.findByProduktNummer(produktNummer)
			if(p) {
				str = p.navn	
			}
		}
		
		return str
	}

	def ryddIntervjuer() {
		log.info('Kjører ryddIntervjuer')
		oppdaterStatusTilSluttet()
	}

	private void oppdaterStatusTilSluttet() {
		Date now = Calendar.getInstance().getTime()
		log.info('Endrer status fra ' + IntervjuerStatus.AKTIV + ' til ' + IntervjuerStatus.SLUTTET + ' for intervjuere med sluttdato før ' + now)

		def intervjuerListe  = Intervjuer.findAllBySluttDatoLessThanEqualsAndStatus(now, IntervjuerStatus.AKTIV)
		int cnt = 0
		intervjuerListe.each {
			try {
				it.status = IntervjuerStatus.SLUTTET
				it.save(flush: true)
				cnt++
			} catch (Exception e) {
				if (cnt > 0) {
					log.info('Har endret status for ' + cnt + ' intervjuere.')
				}
				log.error('Kunne ikke endre status for intervjuer med id = ' + it.id + ': ' + e.getMessage())
			}
		}
		log.info('Har endret status for ' + cnt + ' intervjuere.')
	}
}
