package sivadm

import siv.rapport.data.skjevhet.SkjevhetData;
import siv.rapport.data.skjevhet.SkjevhetKriterie;
import siv.rapport.data.skjevhet.SkjevhetSokeKriterie;
import siv.rapport.data.skjevhet.SkjevhetSvarType;
import siv.type.AdresseType;
import siv.type.Kjonn;
import siv.type.Landsdel;
import siv.type.SkjemaStatus;

class RapportSkjevhetKjonnService {

	static transactional = true
	
	def rapportStatusService
	def rapportFrafallService

	/**
	* Henter skjevhetsrapport med prosentverdier basert på totalt utvalg (alle intervjuobjekter for skjema)
	*
	* @param skjemaId
	* @return
	*/
   public List<SkjevhetData> hentSkjevhetRapportKjonnIProsent(Long skjemaId) {
	   List<SkjevhetData> skjevhetKjonnDataList = hentSkjevhetRapportKjonn(skjemaId)
	   return transformerTilProsentProsent( skjemaId, skjevhetKjonnDataList )
   }
   
   
   
	/**
	 * Henter en rapport om skjevheter for kjønn.
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjevhetData> hentSkjevhetRapportKjonn(Long skjemaId) {

		List<SkjevhetData> skjevhetDataList = new ArrayList<SkjevhetData>()

		List<Boolean> alleKjonn = [
			new SkjevhetKriterie( kriterieNavn: "Mann", kriterie: Boolean.TRUE),
			new SkjevhetKriterie( kriterieNavn: "Kvinne", kriterie: Boolean.FALSE)
		]
		
		alleKjonn.each { SkjevhetKriterie skjevhetKriterie ->
			
			SkjevhetSokeKriterie sok = new SkjevhetSokeKriterie()
			
			sok.mann = (Boolean) skjevhetKriterie.kriterie

			SkjevhetData skjevhetData = new SkjevhetData()
			skjevhetData.kriterieNavn = skjevhetKriterie.kriterieNavn
			
			skjevhetData = hentSkjevhetData( skjemaId, sok, skjevhetData)

			skjevhetDataList.add( skjevhetData )
		}

		return skjevhetDataList
	}
	
	
	/**
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjevhetData> hentSkjevhetRapportAlderIProsent(Long skjemaId) {
		List<SkjevhetData> skjevhetKjonnDataList = hentSkjevhetRapportAlder(skjemaId)
		
		return transformerTilProsentProsent( skjemaId, skjevhetKjonnDataList )
	}
	
	/**
	 * Henter en rapport om skjevheter for alder
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjevhetData> hentSkjevhetRapportAlder(Long skjemaId) {
		List<SkjevhetData> skjevhetDataList = new ArrayList<SkjevhetData>()

		List alleAldre = [
			new SkjevhetKriterie( kriterieNavn: "9-15", kriterie: [9, 15]),
			new SkjevhetKriterie( kriterieNavn: "16-20", kriterie: [16, 20]),
			new SkjevhetKriterie( kriterieNavn: "21-34", kriterie: [21, 34]),
			new SkjevhetKriterie( kriterieNavn: "35-44", kriterie: [35, 44]),
			new SkjevhetKriterie( kriterieNavn: "45-65", kriterie: [45, 65]),
			new SkjevhetKriterie( kriterieNavn: "66-100", kriterie: [66, 100])
		]

		alleAldre.each { SkjevhetKriterie skjevhetKriterie ->

			SkjevhetSokeKriterie sok = new SkjevhetSokeKriterie()
			
			List mapMinAlder = (List) skjevhetKriterie.kriterie
			
			if( mapMinAlder ) {
				sok.minAlder = new Long ( mapMinAlder.get(0) )
				sok.maksAlder = new Long ( mapMinAlder.get(1) )
			}
			
			SkjevhetData skjevhetData = new SkjevhetData()
			skjevhetData.kriterieNavn = skjevhetKriterie.kriterieNavn

			skjevhetData = hentSkjevhetData( skjemaId, sok, skjevhetData)

			skjevhetDataList.add( skjevhetData )
		}

		return skjevhetDataList
	}
	
	
	/**
	 * Henter rapport om landsdeler i prosent
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjevhetData> hentSkjevhetRapportLandsdelIProsent(Long skjemaId) {
		List<SkjevhetData> skjevhetDataList = hentSkjevhetRapportLandsdel(skjemaId)
		
		return transformerTilProsentProsent(skjemaId, skjevhetDataList)
	}
	
	
	
	/**
	 * Henter en rapport om skjevheter for landsdel
	 * 
	 * @param skjemaId
	 * @return
	 */
	public List<SkjevhetData> hentSkjevhetRapportLandsdel(Long skjemaId) {
		List<SkjevhetData> skjevhetDataList = new ArrayList<SkjevhetData>()

		List alleLandsdeler = [
			new SkjevhetKriterie( kriterieNavn: "Oslo og Akershus", kriterie: Landsdel.OSLO_AKERSHUS),
			new SkjevhetKriterie( kriterieNavn: "Hedmark og Oppland", kriterie: Landsdel.HEDMARK_OPPLAND),
			new SkjevhetKriterie( kriterieNavn: "Østlandet", kriterie: Landsdel.OSTLANDET),
			new SkjevhetKriterie( kriterieNavn: "Agder og Rogaland", kriterie: Landsdel.AGDER_ROGALAND),
			new SkjevhetKriterie( kriterieNavn: "Trøndelag", kriterie: Landsdel.TRONDELAG),
			new SkjevhetKriterie( kriterieNavn: "Vestlandet", kriterie: Landsdel.VESTLANDET),
			new SkjevhetKriterie( kriterieNavn: "Nord-Norge", kriterie: Landsdel.NORDNORGE)
		]

		alleLandsdeler.each { SkjevhetKriterie skjevhetKriterie ->
			
			SkjevhetSokeKriterie sok = new SkjevhetSokeKriterie()
			
			if( skjevhetKriterie.kriterie ) {
				sok.landsdel = skjevhetKriterie.kriterie
			}
			
			SkjevhetData skjevhetData = new SkjevhetData()
			skjevhetData.kriterieNavn = skjevhetKriterie.kriterieNavn

			skjevhetData = hentSkjevhetData( skjemaId, sok, skjevhetData)

			skjevhetDataList.add( skjevhetData )
		}

		return skjevhetDataList
	}
	
	
	
	/**
	 * 
	 * @param skjemaId
	 * @param sok
	 * @return
	 */
	private SkjevhetData hentSkjevhetData( Long skjemaId, SkjevhetSokeKriterie sok,  SkjevhetData skjevhetData) {

		skjevhetData.bruttoUtvalg = hentAntallIoForKriterie( skjemaId, null, sok)

		sok.skjevhetSvarType = SkjevhetSvarType.INTERVJU
		skjevhetData.intervju = hentAntallIoForKriterie( skjemaId, null, sok)

		sok.skjevhetSvarType = SkjevhetSvarType.FRAFALL
		skjevhetData.frafall = hentAntallIoForKriterie( skjemaId, null, sok)

		sok.skjevhetSvarType = SkjevhetSvarType.AVGANG
		skjevhetData.avgang = hentAntallIoForKriterie( skjemaId, null, sok)

		sok.skjevhetSvarType = SkjevhetSvarType.SPORING
		skjevhetData.tilSporing = hentAntallIoForKriterie( skjemaId, null, sok)

		// bruttoutvalg = brutto - avganger
		skjevhetData.bruttoUtvalg = skjevhetData.bruttoUtvalg - skjevhetData.avgang

		// brutto - netto
		skjevhetData.nettoBrutto =  skjevhetData.intervju - skjevhetData.bruttoUtvalg
		
		return skjevhetData
	}
	
	
	/**
	 * Transformerer til prosent.
	 * 
	 * 
	 * @param skjemaId
	 * @param skjevhetKjonnDataList
	 * @return
	 */
	private List<SkjevhetData> transformerTilProsentProsent(Long skjemaId, List<SkjevhetData> skjevhetKjonnDataList) {
		
		// finn totalt antall
		
		int totaltAntallAvganger = rapportStatusService.finnAntallAvganger(skjemaId, null)
		
		int totaltAntall = rapportStatusService.finnTotaltAntall(skjemaId, null) - totaltAntallAvganger
		
		double antAvganger = hentTotaltAntallAvganger(skjevhetKjonnDataList)
		double antIntervju = hentTotaltAntallIntervju(skjevhetKjonnDataList)
		double antFrafall = hentTotaltAntallFrafall(skjevhetKjonnDataList)
		double antSporing = hentTotaltAntallTilSporing(skjevhetKjonnDataList)
		
		// transformer over til prosentverdier
		skjevhetKjonnDataList.each { SkjevhetData skjevhetData ->
			
			double bruttoUtvalgProsent = finnProsent(skjevhetData.bruttoUtvalg, totaltAntall)	
			double avgangProsent = finnProsent(skjevhetData.avgang, antAvganger)
			double intervjuProsent = finnProsent(skjevhetData.intervju, antIntervju)
			double frafallProsent = finnProsent(skjevhetData.frafall, antFrafall)
			double tilSporingProsent = finnProsent(skjevhetData.tilSporing, antSporing)
			double nettoBrutto = intervjuProsent - bruttoUtvalgProsent
			
			// fyll endrede verdier inn tilbake i objekt og returner
			skjevhetData.bruttoUtvalg = bruttoUtvalgProsent
			skjevhetData.avgang = avgangProsent
			skjevhetData.intervju = intervjuProsent
			skjevhetData.frafall = frafallProsent
			skjevhetData.tilSporing = tilSporingProsent
			skjevhetData.nettoBrutto = nettoBrutto
		}
		
		return skjevhetKjonnDataList
	}
	
	/**
	 * 
	 * @param skjevhetKjonnDataList
	 * @return
	 */
	private double hentTotaltAntallAvganger( List<SkjevhetData> skjevhetKjonnDataList ) {
		
		double totaltAntall = 0
		
		skjevhetKjonnDataList.each { SkjevhetData skjevhetData ->
			totaltAntall = totaltAntall + skjevhetData.avgang
		}
		
		return totaltAntall
	}
	
	
	/**
	*
	* @param skjevhetKjonnDataList
	* @return
	*/
   private int hentTotaltAntallIntervju( List<SkjevhetData> skjevhetKjonnDataList ) {
	   
	   double totaltAntall = 0
	   
	   skjevhetKjonnDataList.each { SkjevhetData skjevhetData ->
		   totaltAntall += skjevhetData.intervju
	   }
	   
	   return totaltAntall
   }
   
   /**
   *
   * @param skjevhetKjonnDataList
   * @return
   */
  private int hentTotaltAntallFrafall( List<SkjevhetData> skjevhetKjonnDataList ) {
	  
	  double totaltAntall = 0
	  
	  skjevhetKjonnDataList.each { SkjevhetData skjevhetData ->
		  totaltAntall += skjevhetData.frafall
	  }
	  
	  return totaltAntall
  }
  
  
  /**
  *
  * @param skjevhetKjonnDataList
  * @return
  */
 private int hentTotaltAntallTilSporing( List<SkjevhetData> skjevhetKjonnDataList ) {
	 
	 double totaltAntall = 0
	 
	 skjevhetKjonnDataList.each { SkjevhetData skjevhetData ->
		 totaltAntall += skjevhetData.tilSporing
	 }
	 
	 return totaltAntall
 }
	
	
	/**
	 * Bergner prosent.
	 *  
	 * @param tall1
	 * @param tall2
	 * @return tall 1 delt på tall 2
	 */
	private double finnProsent( double verdi, double total ) {
		if( total == 0 ) {
			return 0
		}
		
		return  verdi * 100 / total
	}
	


	
	/**
	 * Søker i databasen etter antall io som tilfredstiller kriteriene.
	 * 
	 * @return antall io
	 */
	private int hentAntallIoForKriterie( Long skjemaId, Long periodeNummer, SkjevhetSokeKriterie sokeKriterie ) {

		def criteria = IntervjuObjekt.createCriteria()

		def ioList = criteria.listDistinct {
			
			projections { countDistinct("id") }

			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}

			// kjønn
			if( sokeKriterie.mann != null ) {
				if( sokeKriterie.mann == true ) {
					eq("kjonn", Kjonn.MANN)
				}
				else {
					eq("kjonn", Kjonn.KVINNE)
				}
			}

			// minimum alder
			if( sokeKriterie.minAlder != null ) {
				ge( "alder", sokeKriterie.minAlder)
			}

			// maks alder
			if( sokeKriterie.maksAlder != null ) {
				le("alder", sokeKriterie.maksAlder)
			}

			// landsdel
			if( sokeKriterie.landsdel != null ) {
				
				adresser {
					and {
						eq("adresseType", AdresseType.BESOK)
						eq("gjeldende", true)
					}
				}
				
				// Landsdel OSLO_AKERSHUS
				if( sokeKriterie.landsdel == Landsdel.OSLO_AKERSHUS) {
					adresser {
						or {
							like("kommuneNummer", "02%")
							like("kommuneNummer", "03%")
						}
					}
				}
				
				// Landsdel HEDMARK_OPPLAND
				if( sokeKriterie.landsdel == Landsdel.HEDMARK_OPPLAND) {
					adresser {
						or {
							like("kommuneNummer", "04%")
							like("kommuneNummer", "05%")
						}
					}
				}
				
				// Landsdel OSTLANDET
				if( sokeKriterie.landsdel == Landsdel.OSTLANDET) {
					adresser {
						or {
							like("kommuneNummer", "01%")
							like("kommuneNummer", "06%")
							like("kommuneNummer", "07%")
							like("kommuneNummer", "08%")
						}
					}
				}
				
				// Landsdel AGDER_ROGALAND
				if( sokeKriterie.landsdel == Landsdel.AGDER_ROGALAND) {
					adresser {
						or {
							like("kommuneNummer", "09%")
							like("kommuneNummer", "10%")
							like("kommuneNummer", "11%")
						}
					}
				}
				
				// Landsdel TRONDELAG
				if( sokeKriterie.landsdel == Landsdel.TRONDELAG) {
					adresser {
						or {
							like("kommuneNummer", "16%")
							like("kommuneNummer", "17%")
							like("kommuneNummer", "50%")
						}
					}
				}
				
				// Landsdel VESTLANDET
				if( sokeKriterie.landsdel == Landsdel.VESTLANDET) {
					adresser {
						or {
							like("kommuneNummer", "12%")
							like("kommuneNummer", "14%")
							like("kommuneNummer", "15%")
						}
					}
				}
				
				// Landsdel NORDNORGE
				if( sokeKriterie.landsdel == Landsdel.NORDNORGE) {
					adresser {
						or {
							like("kommuneNummer", "18%")
							like("kommuneNummer", "19%")
							like("kommuneNummer", "20%")
						}
					}
				}
			}

			// svartype
			if( sokeKriterie.skjevhetSvarType != null ) {

				if( sokeKriterie.skjevhetSvarType == SkjevhetSvarType.INTERVJU ) {
					eq("intervjuStatus", 0)
					eq("katSkjemaStatus", SkjemaStatus.Ferdig)
				}
				else if ( sokeKriterie.skjevhetSvarType == SkjevhetSvarType.FRAFALL ) {
					'in'("intervjuStatus", 11..41)
					or {
						eq("katSkjemaStatus", SkjemaStatus.Ferdig)
						eq("katSkjemaStatus", SkjemaStatus.Ubehandlet)
					}
				}
				else if( sokeKriterie.skjevhetSvarType == SkjevhetSvarType.AVGANG ) {
					'in'("intervjuStatus", 91..99)
					eq("katSkjemaStatus", SkjemaStatus.Ferdig)
				}
				else if( sokeKriterie.skjevhetSvarType == SkjevhetSvarType.SPORING ) {
					'in'("intervjuStatus", [33, 34, 35, 36, 37])
				}
				
				
			}
		}

		return ioList?.get(0)
	}
}







