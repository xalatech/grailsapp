package sivadm


import util.TimeUtil
import org.hibernate.Criteria
import org.hibernate.HibernateException
import org.hibernate.criterion.CriteriaQuery
import org.hibernate.criterion.Order
import siv.type.AdresseType
import siv.type.AvtaleType
import siv.type.Kilde
import siv.type.ResultatStatus
import siv.type.SkjemaStatus

import java.text.SimpleDateFormat

// import grails.core.ConfigurationHolder

class IntervjuObjektService {

	boolean transactional = true
	def grailsApplication

	/**
	 * Sjekker om det er behov for å legge til en status historikk på intervjuobjektet.
	 *  
	 * @param intervjuObjekt
	 * @param skjemaStatus
	 * @param intervjuStatus
	 * @param initialer
	 * @return nytt StatHist-objekt hvis det er behov. Hvis ikke null.
	 */
	public StatHist sjekkStatusEndring(IntervjuObjekt intervjuObjekt, SkjemaStatus skjemaStatus, Integer intervjuStatus, String initialer) {
		if(intervjuObjekt.statusChanged(skjemaStatus, intervjuStatus)) {
			def historikk = new StatHist(skjemaStatus: skjemaStatus, intervjuStatus: intervjuStatus, redigertAv: initialer, dato: new Date())
			return historikk
		}

		return null
	}

	/**
	 * Finner og returnerer klyngen som intervjuobjektet hører hjemme i.
	 * 
	 * @param intervjuObjekt
	 * @return intervjuobjektets klynge
	 */
	public Klynge finnIntervjuObjektKlynge(IntervjuObjekt intervjuObjekt) {
		Adresse adresse = getGyldigBesokAdresse(intervjuObjekt)

		if(!adresse) {
			return null
		}

		Kommune kommune = Kommune.findByKommuneNummer(adresse.kommuneNummer)

		if(!kommune) {
			return null
		}

		Klynge k

		Klynge.list().each { klynge ->
			if(klynge.kommuner?.contains(kommune)) {
				k = klynge
			}
		}

		return k
	}

	/**
	 * Finner og returnerer intervjobjektets gyldige besøksadresse.
	 * 
	 * @param intervjuObjekt
	 * @return Adresse
	 */
	public Adresse getGyldigBesokAdresse(IntervjuObjekt intervjuObjekt) {
		def adresser = intervjuObjekt.adresser

		def gyldigAdresse

		adresser.each {
			Adresse adresse = it

			if(adresse.gjeldende == true && adresse.adresseType == AdresseType.BESOK) {
				gyldigAdresse = adresse
			}
		}

		return gyldigAdresse
	}

	/**
	 * Utfører et komplett intervjuobjektsøk basert på gitte kriterier.
	 * 
	 * @param search Et dataobjekt med søkekriterier
	 * @return en liste med IntervjuObjekt (uten hensyn til paginering og max antall)
	 */
	public List searchAll( IntervjuObjektSearch search ) {
		return this.search( search , new HashMap() )
	}


	/**
	 * Utfører et intervjuobjektsøk og returnerer en liste med intervjuobjekter. Tar hensyn
	 * til max antall og paginering.
	 * 
	 * NB! Merk at metoden bruker IntervjuObjekt.getAll() og dermed ikke vil fungere mot Oracle
	 * med resultatsett større enn 1000 intervjuobjekter. Men så lenge vi bruker paginering og 
	 * offsett så går dette bra.
	 * 
	 * @param search
	 * @param pagination
	 * @return en liste med intervjuobjekter
	 */
	public List search( IntervjuObjektSearch search, Map params ) {
		Integer max = params.max
		Integer first = params.offset? Integer.parseInt(params.offset) : 0

		def intervjuObjektList = searchCriteria(search, first, max, false, params.sort, params.order, null)
		intervjuObjektList
	}


	/**
	 * Utfører et intervjuobjektsøk og returnerer antall treff.
	 * 
	 * @param search
	 * @return antall intervjuobjekter i søkeresultatet
	 */
	public int countSearch( IntervjuObjektSearch search) {
		List ioCount = searchCriteria( search, null, null, true, null, null, null )
		return ioCount.get(0)
	}
	
	
	/**
	 * Gjør et søk men henter kun ut en liste med intervjuobjekt id'r
	 * @param search
	 * @param params
	 * @return en liste med io id
	 */
	public List<Long> idSearch( IntervjuObjektSearch search, Map params ) {
		List ioIdList = searchCriteria(search, null, null, false, params.sort, params.order, true)
		ioIdList
	}


	/**
	 * Hovedsøkemetoden for intervjuobjektsøk. Inneholder nødvendig GORM-kode
	 * for å søke frem intervjuobjekter basert på gitte kriterier.
	 * 
	 * NB! Metoden returnerer en liste med intervjuobjekt id, og ikke selve intervjuobjektet.
	 * 
	 * @param search		inneholder søkekriterier (IntervjuObjektSearch)
	 * @param first			offset ifm paginering
	 * @param max			maks antall som ønskes i resultatet
	 * @param countAll 	settes til true hvis man kun er ute etter antall
	 * 
	 * @return en liste med intervjuobjekter
	 */
	def searchCriteria = { search, first, max, countAll, sortParam, orderParam, idList ->
		def c = IntervjuObjekt.createCriteria()

		def intervjuObjektList = c.listDistinct {
			
			// Vi skal som regel ikke returnere alle intervjuobjektene men kun et utsnitt som 
			// passer inn under pagineringen.
			if(first )	{
				firstResult(first)
			}

			if(max) {
				maxResults(max)
			}
			
			if( countAll ) {
				projections { countDistinct("id") }
			}
			else {
				if( !sortParam ) {
					addOrder(new Order("intervjuObjektNummer", true) {
						@Override
						public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
							return "cast(intervju_objekt_nummer as int) asc";
						}
					});
				}
				else {
					
					if( sortParam == "gateAdresse") {
						adresser {
							order( "gateAdresse", orderParam )
						}
					}
					else if ( sortParam == "postNummer") {
						adresser {
							order( "postNummer", orderParam )
						}
					}
					else if ( sortParam == "skjemaNavn") {
						periode {
							skjema {
								order( "skjemaNavn", orderParam )
							}
						}
					}
					else if ( sortParam == "delProduktNummer") {
						periode {
							skjema {
								order( "delProduktNummer", orderParam )
							}
						}
					}
					else if (sortParam == "periodeNummer") {
						periode {
							order( "periodeNummer", orderParam )
						}
					}
					else if (sortParam == "intervjuObjektNummer") {
						if (orderParam == 'asc') {
							addOrder(new Order("intervjuObjektNummer", true) {
								@Override
								public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
									return "cast(intervju_objekt_nummer as int) asc";
								}
							});
						} else {
							addOrder(new Order("intervjuObjektNummer", true) {
								@Override
								public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
									return "cast(intervju_objekt_nummer as int) desc";
								}
							});
						}
					}
					else {
						order( sortParam, orderParam )
					}
					
				}
	
			}

			if( idList ) {
				projections { property("id") }
			}

			if(search.navn) {
				ilike("navn", search.navn)
			}
			
			if( search.initialer ) {
				ilike("intervjuer", search.initialer)
			}
			
			if(search.intervjuObjektNummer) {
				like("intervjuObjektNummer", search.intervjuObjektNummer )
			}

			if(search.skjemaStatus) {
				'in'("katSkjemaStatus", this.commaSeparertTilSkjemaStatuser(search.skjemaStatus))
			}

			if(search.avtaleType) {
				def avtaleTyper = this.commaSeparertTilAvtaleTyper(search.avtaleType)
				avtaleTyper.each {
					switch (it) {
						case AvtaleType.HARD:
							avtale {
								eq("avtaleType", AvtaleType.HARD)
								gt("dateStart", new Date())
							}
							break
						case AvtaleType.LOS:
							avtale {
								eq("avtaleType", AvtaleType.LOS)
								gt("dateStart", new Date())
							}
							break
						case AvtaleType.HAR_HATT_HARD:
							avtale {
								eq("avtaleType", AvtaleType.HARD)
								lt("dateStart", new Date())
							}
							break
						case AvtaleType.HAR_HATT_LOS:
							avtale {
								eq("avtaleType", AvtaleType.LOS)
								lt("dateStart", new Date())
							}
							break
						default:
							break
					}

				}
			}
			
			if(search.kilde) {
				eq("kilde", search.kilde)
			}

			if(search.skjema != null) {
				periode {
					skjema {
						eq( "id", search.skjema)
					}
				}
			}


			if(search.assosiertSkjema != null) {


				def query =
				"""
					exists (
					select null
					from INTERVJU_OBJEKT io, PERIODE pe, SKJEMA sk
					where io.INTERVJU_OBJEKT_NUMMER = this_.INTERVJU_OBJEKT_NUMMER
					and io.PERIODE_ID = pe.ID
					and pe.SKJEMA_ID = sk.ID
					and sk.ID = ${search.assosiertSkjema}
				"""


				if(search.intervjuStatusAssSkj != null && search.intervjuStatusBlankAssSkj == true) {
					query += " and ( io.INTERVJU_STATUS in ( ${search.intervjuStatusAssSkj} ) or io.INTERVJU_STATUS IS NULL )"
				} else if(search.intervjuStatusAssSkj != null) {
					query += " and io.INTERVJU_STATUS in ( ${search.intervjuStatusAssSkj} ) "
				} else if(search.intervjuStatusBlankAssSkj == true) {
					query += " and io.INTERVJU_STATUS IS NULL"
				}

				if (search.kontaktperiodeAssSkj){
					def kontaktPerioder = "'" + search.kontaktperiodeAssSkj.replaceAll(",", "','")  + "'"
					query += " and io.KONTAKTPERIODE in ( ${kontaktPerioder} ) "
				}

				query += ')'

				sqlRestriction query.stripIndent()
			}


			if(search.typeBesok != null ) {
				periode {
					skjema {
						eq( 'intervjuTypeBesok', search.typeBesok)
					}
				}
			}
			
			if(search.periodeNummer) {
				periode {
					'in'("periodeNummer", this.konverterStrengArrayTilLongArray(search.periodeNummer.split(',')))
				}
			}

			if(search.adresse) {
				adresser {
					ilike( "gateAdresse", ("%" + search.adresse + "%"))
				}
			}

			if(search.postNummer) {
				adresser {
					eq( "postNummer", search.postNummer)
				}
			}
			
			
			if(search.postSted) {
				adresser {
					ilike( "postSted", search.postSted)
				}
			}

			if(search.kommuneNummer) {
				adresser {
					eq( "kommuneNummer", search.kommuneNummer)
				}
			}

			if(search.boligNummer) {
				adresser {
					ilike( "boligNummer", search.boligNummer)
				}
			}

			if(search.telefonNummer) {
				telefoner {
					eq("telefonNummer", search.telefonNummer)
				}
			}

			if(search.husBruk) {
				adresser {
					eq("husBruksNummer", search.husBruk)
				}
			}


			if(search.kjonn) {
				like("kjonn", search.kjonn)
			}

			if(search.alderFra) {
				ge("alder", search.alderFra)
			}

			if(search.alderTil) {
				le("alder", search.alderTil)
			}
			
			if(search.fullforingMin != null) {
				ge("fullforingsGrad", search.fullforingMin)
			}

			if(search.fullforingMax != null) {
				or {
					le("fullforingsGrad", search.fullforingMax)
					isNull("fullforingsGrad")
				}
			}

			if(search.fullforingsStatus != null) {
				'in'("fullforingsStatus", this.konverterStrengArrayTilLongArray(search.fullforingsStatus.split(',')))
			}

			if(search.aargang) {
				periode {
					skjema {
						prosjekt {
							eq("aargang", search.aargang)
						}
					}
				}
			}

			if(search.fodselsNummer) {
				eq("fodselsNummer", search.fodselsNummer)
			}

			if(search.epost) {
				ilike("epost", search.epost)
			}

			if(search.familienummer) {
				eq("familienummer", search.familienummer)
			}

			if(search.klynge) {

				def kommuneNummerList = Klynge.get(search.klynge).getKommuneNummerList()

				if(kommuneNummerList != null && kommuneNummerList.size() > 0 ) {
					adresser {
						'in'( "kommuneNummer", kommuneNummerList)
					}
					 
				}
				else {
					adresser {
						eq( "kommuneNummer", "-1")
					}
				}
			}

			if(search.intervjuObjektId != null ) {
				eq("id", search.intervjuObjektId )
			}

			if(search.intervjuStatus != null && search.intervjuStatusBlank == true) {
				or {
					'in'("intervjuStatus", this.konverterStrengArrayTilIntegerArray(search.intervjuStatus.split(',')))
					isNull("intervjuStatus")
				}
			} else if(search.intervjuStatus != null) {
				'in'("intervjuStatus", this.konverterStrengArrayTilIntegerArray(search.intervjuStatus.split(',')))
			} else if(search.intervjuStatusBlank == true) {
				isNull("intervjuStatus")
			}

			if(search.resultatStatus) {
				if(ResultatStatus.NEKTERE == search.resultatStatus) {
					'in'("intervjuStatus", [11, 12, 13, 14, 15])
				}
				else if(ResultatStatus.NEKTERE_TIL_REUTSENDING == search.resultatStatus) {
					'in'("intervjuStatus", [11, 12, 13, 14])
				}
				else if(ResultatStatus.TIL_SPORING == search.resultatStatus) {
					'in'("intervjuStatus", [33, 34, 35, 36, 37])
				}
				else if(ResultatStatus.KLARERT_FOR_REUTSENDING == search.resultatStatus) {				
					eq("katSkjemaStatus", SkjemaStatus.Reut_CATI )

					ne ("intervjuStatus", 11)
					ne ("intervjuStatus", 12)
					ne ("intervjuStatus", 13)
					ne ("intervjuStatus", 14)
				}
				else if(ResultatStatus.OVERFORING == search.resultatStatus) {
					'in'("intervjuStatus", [80, 81, 82, 83])
				}
				else if(ResultatStatus.AVGANG == search.resultatStatus) {
					'in'("intervjuStatus", [
						90,
						91,
						92,
						93,
						94,
						95,
						96,
						97,
						98,
						99
					])
				}
				else if(ResultatStatus.SPRAK_PROBLEMER == search.resultatStatus) {
					eq("intervjuStatus", 24)
				}
				else if(ResultatStatus.LANGVARIG_SYK == search.resultatStatus) {
					eq("intervjuStatus", 22)
				}
				else if(ResultatStatus.ANNET_FRAFALL == search.resultatStatus) {
					eq("intervjuStatus", 41)
				}
				else if(ResultatStatus.PAA_VENT == search.resultatStatus) {
					eq("katSkjemaStatus", SkjemaStatus.Paa_vent )
					'in'("intervjuStatus", [21, 23, 31, 32])
				}
				else if(ResultatStatus.PARKERTE == search.resultatStatus) {
					or {
						'in'("intervjuStatus", [25, 39, 83])
						eq("parkert", true)
					}
				}
				else if(ResultatStatus.FERDIGE == search.resultatStatus) {
					eq("katSkjemaStatus", SkjemaStatus.Ferdig )
				}
				else if(ResultatStatus.ALLE_IKKE_FERDIGE == search.resultatStatus) {
					ne("katSkjemaStatus", SkjemaStatus.Ferdig )
				}
				else if(ResultatStatus.INTERVJU == search.resultatStatus) {
					eq("intervjuStatus", 0 )
				}
			}
			
			if(search.kontaktperiode) {
				'in'("kontaktperiode", search.kontaktperiode.split(','))
			}

			if(search.delutvalg) {
						'in'("delutvalg", search.delutvalg.split(','))
			}

			if (search.intStatDatoIntervallFra && search.intStatDatoIntervallTil) {
				if (search.intStatDatoIntervallFra.time <= search.intStatDatoIntervallTil.time) {

					SimpleDateFormat sdf = new SimpleDateFormat(grailsApplication.config.database.datomaske)
					Calendar cal = Calendar.getInstance()

					cal.setTime(search.intStatDatoIntervallFra)
					cal.set(Calendar.HOUR_OF_DAY, 0)
					cal.set(Calendar.MINUTE, 0)
					cal.set(Calendar.SECOND, 0)
					def datoFra = sdf.format(cal.getTime())

					cal.setTime(search.intStatDatoIntervallTil)
					cal.set(Calendar.HOUR_OF_DAY, 23)
					cal.set(Calendar.MINUTE, 59)
					cal.set(Calendar.SECOND, 59)
                    def datoTil = sdf.format(cal.getTime())

					def query =
							"""
                      exists (
                        select null
                        from intervju_objekt_stat_hist iosh
                        join stat_hist sh on sh.id = iosh.stat_hist_id
                        where iosh.io_status_historikk_id = this_.id
                        and sh.dato = (select max(sh2.dato)
                                       from stat_hist sh2
                                       join intervju_objekt_stat_hist iosh2 on iosh2.stat_hist_id = sh2.id
                                       where iosh2.io_status_historikk_id = this_.id)
                        and sh.dato between cast ('${datoFra}' as timestamp) and cast ('${datoTil}' as timestamp)
                      )
				"""

                    sqlRestriction query.stripIndent()
                }


            }

			if (search.maalform) {
				eq("maalform", search.maalform)
			}
		}

		return intervjuObjektList
	}

	
	/**
	 * Metode som brukes for å flytte intervjuobjekter med status innlastet til utsendt_cati status. Denne tjenesten
	 * brukes av en kontinuerlig bakgrunnsjobb som går hver natt. 
	 * 
	 * Merk: Det sendes ingen synk-melding til Blaise her for Blaise trenger ikke det. De vil ligge i utsendt_cati status
	 * i Blaise med en gang de blir importert der.
	 */
	def sjekkIntervjuObjektUtsendtCati() {
		log.info("Sjekker oppstartDataInnsamling for periode mot dato og setter UtsendtCati på IO der innsamling skal starte")

		def ioListe = IntervjuObjekt.findAllByKatSkjemaStatus(SkjemaStatus.Innlastet)
		int cnt = 0
		ioListe.each {
			if(it.periode) {
				// Sjekker oppstartDataInnsamling dato på periode, hvis denne
				// ikke er fram i tid kan skjemastatus endres til SkjemaStatus.Innlastet
				// hvis andre kriterier også innfris
				if(TimeUtil.getStartOfDay(it.periode.oppstartDataInnsamling).compareTo(new Date()) < 0) {
					if(it.periode?.skjema?.intervjuTypeTelefon || it.periode?.skjema?.intervjuTypeWeb) {
						if(it.periode?.skjema?.intervjuTypeTelefon && it.periode?.skjema?.intervjuTypeWeb) {
							it.katSkjemaStatus = SkjemaStatus.Utsendt_CATI_WEB
						} else if(it.periode?.skjema?.intervjuTypeTelefon) {
							it.katSkjemaStatus = SkjemaStatus.Utsendt_CATI
						} else if(it.periode?.skjema?.intervjuTypeWeb) {
							it.katSkjemaStatus = SkjemaStatus.Utsendt_WEB
						}
						

						def historikk = new StatHist(skjemaStatus: it.katSkjemaStatus, intervjuStatus: it.intervjuStatus, redigertAv: "batch jobb", dato: new Date())
						historikk.save()
						
						it.addToStatusHistorikk(historikk)

						if(!it.save()) {
							log.error("Kunne ikke lagre IO " + it.navn + "(id " + it.id + ") etter endrig til skjemastatus")
						}
						else {
							cnt++
						}
					}
				}
			}
		}

		log.info("Har satt skjemastatus Utsendt_CATI, Utsendt_CATI_WEB eller Utsendt_WEB på " + cnt + " intervjuobjekter")
	}

	/**
	 * Metode som brukes for å sette intervjuobjekter som har vært på vent tilbake til ubehandlet status. Denne metoden
	 * brukes av en bakgrunnsjobb som går hver natt.
	 */
	def sjekkIntervjuObjektPaaVent() {
		log.info("Sjekker intervjuobjekter som har status på vent")

		def crit = IntervjuObjekt.createCriteria()

		Calendar cal = Calendar.getInstance()
		cal.set Calendar.HOUR_OF_DAY, 23
		cal.set Calendar.MINUTE, 59
		cal.set Calendar.SECOND, 59

		def ioListe = crit {
			'eq'("katSkjemaStatus", SkjemaStatus.Paa_vent)
			'lt'("paVentDato", cal.getTime())
		}

		int cnt = 0

		ioListe.each { io ->
			log.info("Endrer status på IO-nr " + io.intervjuObjektNummer + " fra På vent til Ubehandlet")
			io.setKatSkjemaStatus SkjemaStatus.Ubehandlet
			io.setPaVentDato null

			def historikk = new StatHist(skjemaStatus: SkjemaStatus.Ubehandlet, intervjuStatus: io.intervjuStatus, redigertAv: "batch jobb", dato: new Date())
			historikk.save()
			io.addToStatusHistorikk(historikk)

			if(!io.save()) {
				log.error("Kunne ikke lagre IO " + io.navn + "(id " + io.id + ") etter endring til skjemastatus Ubehandlet")
			}
			else {
				cnt++
			}
		}

		log.info("Har satt skjemastatus Ubehandlet på " + cnt + " intervjuobjekter som hadde På vent status")
	}
	
	/**
	 * Låser et intervjuobjekt for redigering. Dette knyttes da til en bruker. Ingen andre brukere vil da kunne få opp
	 * redigeringsmuligheter for dette intervjuobjektet.
	 * 
	 * @param id
	 * @param bruker
	 * @return true hvis intervjuobjektet blir låst
	 */
	public boolean laasIo(Long id, String bruker) {
		if(!id) {
			log.warn("Kan ikke låse IO for redigering når id gitt er NULL, returnerer false")
			return false
		}

		if(!bruker) {
			log.warn("Kan ikke låse IO for redigering når bruker gitt er NULL, returnerer false")
			return false
		}

		IntervjuObjekt io = IntervjuObjekt.get(id)

		return laasIo(io, bruker)
	}

	/**
	 * Låser et intervjuobjekt for redigering. Dette knyttes da til en bruker. Ingen andre brukere vil da kunne få opp
	 * redigeringsmuligheter for dette intervjuobjektet.
	 * 
	 * @param intervjuObjekt
	 * @param bruker
	 * @return true hvis intervjuobjektet blir låst
	 */
	public boolean laasIo(IntervjuObjekt intervjuObjekt, String bruker) {
		if(!intervjuObjekt) {
			log.warn("Kan ikke låse IO for redigering når intervjuObjekt gitt er NULL, returnerer false")
			return false
		}

		if(intervjuObjekt.laastAv && intervjuObjekt.laastAv != bruker) {
			log.warn("Kan ikke låse IO for redigering når intervjuObjekt er låst av en annen bruker " + intervjuObjekt.laastAv + ", returnerer false")
			return false
		}

		if(!bruker) {
			log.warn("Kan ikke låse IO for redigering når bruker gitt er NULL, returnerer false")
			return false
		}

		intervjuObjekt.setLaastAv(bruker)
		intervjuObjekt.setLaastTidspunkt(new Date())

		if(!intervjuObjekt.save(failOnError: true, flush: true)) {
			log.warn("Kan ikke låse IO for redigering fikk ikke lagret IO med id " + intervjuObjekt.id + ", returnerer false")
			return false
		}

		return true
	}

	
	/**
	 * Låser opp et intervjuobjekt. Dermed vil det bli tilgjengelig for andre brukere igjen.
	 * 
	 * @param id
	 * @return true hvis intervjuobjekt blir låst opp
	 */
	public boolean laasOppIo(Long id) {
		if(!id) {
			log.warn("Kan ikke låse opp IO for redigering når id gitt er NULL, returnerer false")
			return false
		}

		IntervjuObjekt io = IntervjuObjekt.get(id)

		return laasOppIo(io)
	}

	
	/**
	 * Låser opp et intervjuobjekt. Dermed vil det bli tilgjengelig for andre brukere igjen.
	 * 
	 * @param intervjuObjekt
	 * @return true hvis intervjuobjekt blir låst opp
	 */
	public boolean laasOppIo(IntervjuObjekt intervjuObjekt) {
		if(!intervjuObjekt) {
			log.warn("Kan ikke låse opp IO for redigering når intervjuObjekt gitt er NULL, returnerer false")
			return false
		}

		intervjuObjekt.setLaastAv(null)
		intervjuObjekt.setLaastTidspunkt(null)

		if(!intervjuObjekt.save(failOnError: true, flush: true)) {
			log.warn("Kan ikke låse opp IO for redigering fikk ikke lagret IO med id " + intervjuObjekt.id + ", returnerer false")
			return false
		}

		return true
	}

	
	/**
	 * Metode som brukes for å låse opp intervjuobjekter som er blitt stående
	 * i låsemodus. Kriterier for slike intervjuobjekter er at de har vært låst
	 * lenger enn det som er oppført som maks låsetid i konfigurasjonen.
	 * 
	 * Metoden brukes av en bakgrunnsjobb som kjører jevnlig.
	 */
	public void sjekkLaastTidspunktIo() {
		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.MINUTE, (grailsApplication.config.io.laas.minutter * -1))

		def laasOppIoListe = IntervjuObjekt.createCriteria().list() {
			isNotNull("laastTidspunkt")
			lt("laastTidspunkt", cal.getTime())
		}

		laasOppIoListe.each { laasOppIo(it) }
	}
	
	public List hentUnikeKontaktperioder(Long skjemaId) {
		def crit = IntervjuObjekt.createCriteria()
		def kontaktperioder = crit.listDistinct {
			periode {
				skjema {
					eq( "id", skjemaId )
				}
			}
			isNotNull("kontaktperiode")
			projections {
				distinct("kontaktperiode")
			}
		}
		return kontaktperioder.sort();
	}

	public List lagUtvidetIntervjuObjektListe(def intervjuObjektList ) {
		def utvidetIntervjuObjektList = []
		intervjuObjektList.each {
			utvidetIntervjuObjektList.add(
					['io': it,
					 'assIntStatus': ''
					]
			)
		}
		utvidetIntervjuObjektList
	}

	public List lagUtvidetIntervjuObjektListe(def intervjuObjektList, def assosiertSkjemaId ) {

		def ioNummerListe = intervjuObjektList.collect {it.intervjuObjektNummer}.unique()  // Bør vi tillatte dubletter?

		def c = IntervjuObjekt.createCriteria()

		def assosiertIntervjuObjektList = c.list { // hva med c.listDistinct ?
			projections {
				property('intervjuObjektNummer')
				property('intervjuStatus')
			}
			periode {
				skjema {
					eq("id", assosiertSkjemaId)
				}
			}
			'in'("intervjuObjektNummer", ioNummerListe)
		}

		def utvidetIntervjuObjektList = []
		intervjuObjektList.each {

			def ioNummer = it.intervjuObjektNummer

			def array = assosiertIntervjuObjektList.find {
				it[0] == ioNummer
			}
			def intervjuStatus = array[1]

            utvidetIntervjuObjektList.add(
                    ['io': it,
                     'assIntStatus': intervjuStatus
                    ]
            )
		}

		utvidetIntervjuObjektList
	}


	public List hentUnikeDelutvalg(Long skjemaId) {
		def crit = IntervjuObjekt.createCriteria()
		def delutvalg = crit.listDistinct {
			periode {
				skjema {
					eq( "id", skjemaId )
				}
			}
			isNotNull("delutvalg")
			projections {
				distinct("delutvalg")
			}
		}
		return delutvalg;
	}

	def antallIntervjuObjekterMedTidligereIntervjustatus(Long skjemaId, Long periodeNummer, Integer intervjustatusFra, Integer intervjustatusTil, String gruppering) {

		def c = IntervjuObjekt.createCriteria()

		def liste = c {

            projections {
                distinct('navn')
            }

			periode {
				skjema {
					eq('id', skjemaId)
				}
			}

			def query =
					"""
                      exists (
                        select null
  						from intervju_objekt_stat_hist iosh
  						join stat_hist sh on sh.id = iosh.stat_hist_id
  						where iosh.io_status_historikk_id = this_.id
  						and sh.intervju_status is not null
  						and sh.intervju_status >= '${intervjustatusFra}'
  						and sh.intervju_status <= '${intervjustatusTil}'
  						and sh.intervju_status != this_.intervju_status
                      )
				"""

			sqlRestriction query.stripIndent()


			if (periodeNummer) {
				periode {
					eq('periodeNummer', periodeNummer)
				}
			}
			switch (gruppering) {
				case 'alle':
					break
				case 'frafall':
					gt("intervjuStatus", 0)
					le("intervjuStatus", 90)
					eq("katSkjemaStatus", SkjemaStatus.Ferdig)
					break
                case 'avgang':
                    gt("intervjuStatus", 90)
                    eq("katSkjemaStatus", SkjemaStatus.Ferdig)
                    break
                case 'ubehandlede':
					eq("katSkjemaStatus", SkjemaStatus.Ubehandlet)
					break
				case 'innlastet':
					eq("katSkjemaStatus", SkjemaStatus.Innlastet)
					break
				case 'paabegynt':
					eq("katSkjemaStatus", SkjemaStatus.Pabegynt)
					break
				case 'paaVent':
					eq("katSkjemaStatus", SkjemaStatus.Paa_vent)
					break
				case 'nekterePaaVent':
					eq("katSkjemaStatus", SkjemaStatus.Reut_CATI)
					break
				case 'utsendtCati':
					eq("katSkjemaStatus", SkjemaStatus.Utsendt_CATI)
					break
				case 'utsendtCatiWeb':
					eq("katSkjemaStatus", SkjemaStatus.Utsendt_CATI_WEB)
					break
				case 'utsendtCapi':
					eq("katSkjemaStatus", SkjemaStatus.Utsendt_CAPI)
					break
				case 'utsendtWeb':
					eq("katSkjemaStatus", SkjemaStatus.Utsendt_WEB)
					break
				case 'intervju':
					eq("intervjuStatus", 0)
					eq("katSkjemaStatus", SkjemaStatus.Ferdig)
					break

			}
		}

		liste.size()
	}

	private Integer[] konverterStrengArrayTilIntegerArray(String [] strenger) {
		return strenger.collect {
			if(it.isNumber()) {
				return Integer.parseInt(it)
			} else {
				return null
			}
		}
	}
	
	private Long[] konverterStrengArrayTilLongArray(String [] strenger) {
		return strenger.collect {
			if(it.isNumber()) {
				return Long.parseLong(it)
			} else {
				return null
			}
		}
	}
	
	private List<SkjemaStatus> commaSeparertTilSkjemaStatuser(String statuser) {
		String [] statuserArray = statuser.split(',')
		List<SkjemaStatus> enumer = []
		for(String statusStreng : statuserArray) {
			enumer << SkjemaStatus.valueOf(statusStreng)
		}
		return enumer;
	}

	private List<AvtaleType> commaSeparertTilAvtaleTyper(String avtalerTyper) {
		String [] avtaleTypeArray = avtalerTyper.split(',')
		List<AvtaleType> enumer = []
		for(String avtaleTypeStreng : avtaleTypeArray) {
			enumer << AvtaleType.valueOf(avtaleTypeStreng)
		}
		return enumer;
	}

	def oppdaterSkjemaStatus(BlaiseEvent blaiseEvent) {
		if (!blaiseEvent.ioId || !blaiseEvent.skjemaStatus) {
			//Skal her bare behandle events som inneholder ioId og skjemastatus
			log.info("IoId: " + blaiseEvent.ioId + " eventname: " + blaiseEvent.eventName)
			return
		}
		if (blaiseEvent.skjemaStatus == SkjemaStatus.Ferdig && blaiseEvent.reason != "Completed") {
			//Dette skjer hvis vi har EndSessionEvent men annen "reason" enn "Completed". Returner uten logging av feil.
			log.info("IoId: " + blaiseEvent.ioId + " reason: " + blaiseEvent.reason)
			return
		}
		IntervjuObjekt intervjuObjekt = IntervjuObjekt.get(blaiseEvent.ioId)
		if (!intervjuObjekt) {
			log.error("Fant ikke intervjuobjekt med ioId " + blaiseEvent.ioId)
			return
		}
		if (intervjuObjekt.periode.skjema.instrumentId != blaiseEvent.instrumentId) {
			log.error("InstrumentId " + blaiseEvent.instrumentId +
					" må matche skjema tilknyttet intervjuobjektet " + blaiseEvent.ioId)
			return
		}

		//TODO: Skal vi ta høyde for paVentDato? Hvor kommer i så fall den fra?

		if (blaiseEvent.skjemaStatus == SkjemaStatus.Ferdig && !harStatusHistorikkPabegynt(intervjuObjekt)) {
			log.warn("Intervjuobjekt " + blaiseEvent.ioId + " skal avsluttes og har ikke status PÅBEGYNT ")
		}
		if (blaiseEvent.skjemaStatus == SkjemaStatus.Pabegynt && harStatusHistorikkFerdig(intervjuObjekt)) {
			log.error("Intervjuobjekt " + blaiseEvent.ioId + " er avsluttet og kan ikke settes til status PÅBEGYNT ")
			return
		}
		if (duplicateStatusIHistorikk(intervjuObjekt, blaiseEvent)) {
			log.error("Intervjuobjekt " + blaiseEvent.ioId
					+ " har allerede status " + blaiseEvent.skjemaStatus
					+ "/" + blaiseEvent.resolveIntervjustatus()
                    + " eventname: " + blaiseEvent.eventName)
			return
		}
		oppdaterIntervjuObjektMedBlaisedata(intervjuObjekt, blaiseEvent)
		intervjuObjekt.addToStatusHistorikk(createStatHistorikk(blaiseEvent))
		saveIntervjuObjekt(blaiseEvent.ioId, intervjuObjekt)
	}

	def saveIntervjuObjekt(String ioId, IntervjuObjekt intervjuObjekt) {
		try {
			if(IntervjuObjekt.get(ioId).version == intervjuObjekt.version) {
				if(intervjuObjekt.save(flush: true)) {
					return intervjuObjekt.id
				} else {
					log.error("Feil oppsto for " + ioId + ", klarte ikke å lagre skjemastatus ")
				}
			} else {
				log.error("Konflikt ved forsøk på lagring av ioId " + ioId + ". Objektet blir operert på av en annen.")
			}
		} catch(Throwable sose) {
			log.error("Feil ved forsøk på lagring av ioId " + ioId + " - " + sose.printStackTrace())
		}
	}

	private StatHist createStatHistorikk(BlaiseEvent blaiseEvent) {
		new StatHist(skjemaStatus: blaiseEvent.skjemaStatus,
				intervjuStatus: blaiseEvent.resolveIntervjustatus(),
				redigertAv: blaiseEvent.interviewer ?: "BlaiseWeb",
				dato: new Date())
	}

	def harStatusHistorikkPabegynt(IntervjuObjekt intervjuObjekt) {
		return intervjuObjekt.statusHistorikk.find {
			it.skjemaStatus == SkjemaStatus.Pabegynt
		}
	}

	def harStatusHistorikkFerdig(IntervjuObjekt intervjuObjekt) {
		return intervjuObjekt.statusHistorikk.find {
			it.skjemaStatus == SkjemaStatus.Ferdig
		}
	}

	def duplicateStatusIHistorikk(IntervjuObjekt intervjuObjekt, BlaiseEvent blaiseEvent) {
		return intervjuObjekt.statusHistorikk.find {
			it.skjemaStatus == blaiseEvent.skjemaStatus && it.intervjuStatus == blaiseEvent.resolveIntervjustatus()
		}
	}

	void oppdaterIntervjuObjektMedBlaisedata(IntervjuObjekt intervjuObjekt, BlaiseEvent blaiseEvent) {
		intervjuObjekt.intervjuStatus = blaiseEvent.resolveIntervjustatus()
		intervjuObjekt.katSkjemaStatus = blaiseEvent.skjemaStatus
		intervjuObjekt.kilde = blaiseEvent.getKilde() == null ? Kilde.WEB : Kilde.valueOf(blaiseEvent.getKilde())
		if (blaiseEvent.getMeldingFraIntervjuer() != null) {
			intervjuObjekt.statusKommentar = blaiseEvent.getMeldingFraIntervjuer()
		}
		if (blaiseEvent.interviewer != null) {
			intervjuObjekt.intervjuer = blaiseEvent.interviewer
		}
	}
}