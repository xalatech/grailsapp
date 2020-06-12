package sil

import java.util.Date;

import util.TimeUtil
import sil.rapport.data.UkeRapportDetaljerData
import sil.type.KravType
import siv.rapport.data.DetaljertUkeRapportResult;
import siv.type.ArbeidsType
import siv.type.IntervjuerStatus
import siv.util.DatoUtil
import sivadm.Intervjuer
import sivadm.Klynge
import sivadm.Skjema

class DetaljertTidsrapportService {

    static transactional = true
	
	final SUMMER_FORTEKST = "Summer"
	
	
	public List<UkeRapportDetaljerData> hentRapportForIntervjuer(startDato, sluttDato, intervjuer, delProduktNummer){

		List detaljertUkeRapportResultList = hentDetaljertUkeRapportResultList(startDato, sluttDato, intervjuer, delProduktNummer)
		
		lagDetaljertTidsrapportList(detaljertUkeRapportResultList, startDato)
	}

	public List<UkeRapportDetaljerData> hentRapportForKlynge(startDato, sluttDato, klynge, iStatus, delProduktNummer){
		
		List detaljertUkeRapportResultList = hentDetaljertUkeRapportResultList(startDato, sluttDato, klynge, iStatus, delProduktNummer)
		
		lagDetaljertTidsrapportList(detaljertUkeRapportResultList, startDato)
	}
	
	public List<UkeRapportDetaljerData> hentRapportForIntervjuerStatus(startDato, sluttDato, iStatus, delProduktNummer){
		
		List detaljertUkeRapportResultList = hentDetaljertUkeRapportResultList(startDato, sluttDato, iStatus, delProduktNummer)
		
		lagDetaljertTidsrapportList(detaljertUkeRapportResultList, startDato)
	}
	
	public List<UkeRapportDetaljerData> hentRapportForDelProduktNummer(startDato, sluttDato, delProduktNummer){
		
		List detaljertUkeRapportResultList = hentDetaljertUkeRapportResultList(startDato, sluttDato, delProduktNummer)
		
		lagDetaljertTidsrapportList(detaljertUkeRapportResultList, startDato)
	}
	
	public List<UkeRapportDetaljerData> hentRapportForAlleIntervjuere(startDato, sluttDato){
		
		List detaljertUkeRapportResultList = hentDetaljertUkeRapportResultList(startDato, sluttDato)
		
		lagDetaljertTidsrapportList(detaljertUkeRapportResultList, startDato)
	}
	
	private lagDetaljertTidsrapportList(List detaljertUkeRapportResultList, startDato) {
		List ukeDetaljerRapportList = behandleRapportResult(detaljertUkeRapportResultList)

		ukeDetaljerRapportList = settInnSkjemaNavn( ukeDetaljerRapportList, startDato )
		ukeDetaljerRapportList = settInnTotalTid( ukeDetaljerRapportList )

		return ukeDetaljerRapportList
	}
	
    
	/**
	 * Itererer liste og beregner og setter inn totaltid i rapport.
	 * 
	 * @param ukeDetaljerRapportList
	 */
	private List settInnTotalTid( List ukeDetaljerRapportList ) {
		ukeDetaljerRapportList.each { ukeDetaljerRapport ->
			ukeDetaljerRapport.totalTid = ukeDetaljerRapport.arbeidsTid + ukeDetaljerRapport.reiseTid + ukeDetaljerRapport.ovelseTid
		}
		return ukeDetaljerRapportList
	}
	/**
	 * Itererer liste og setter inn skjemanavn i rapport.
	 * 
	 * @param ukeDetaljerRapportList
	 */
	private List settInnSkjemaNavn( List ukeDetaljerRapportList, Date forsteDagIUka ) {	
		HashMap skjemaNavnMap = []
		boolean funnet;
		
		ukeDetaljerRapportList.each { ukeDetaljerRapport ->
			String delProduktNummer = ukeDetaljerRapport.delProduktNummer
			
			if (delProduktNummer == SUMMER_FORTEKST ) return true // siste linje med summer, break
			
			String skjemaNavn = skjemaNavnMap.get(delProduktNummer)
			
			if(skjemaNavn == null) {
				funnet = false;
				List skjemaList = Skjema.findAllByDelProduktNummer(delProduktNummer)
				skjemaList.each { skjema ->
					if (! funnet && (skjema.sluttDato == null || skjema.sluttDato >= forsteDagIUka)) {
						skjemaNavn = skjema.skjemaNavn
						skjemaNavnMap.put(delProduktNummer, skjemaNavn)
						funnet = true
					}
				}
				
				if (!funnet) {
					Skjema skjema = Skjema.findByDelProduktNummer(delProduktNummer)
					if (skjema == null) {
						skjemaNavn = "**Finner ikke delprodukt"
					}
					else {
						skjemaNavn = "**" + skjema.skjemaNavn
					}
					skjemaNavnMap.put(delProduktNummer, skjemaNavn)
				}
//				if( skjema != null ) {
//					skjemaNavn = skjema.skjemaNavn
//					skjemaNavnMap.put(delProduktNummer, skjemaNavn)
//				}
			}
			
			if( skjemaNavn != null ) {
				ukeDetaljerRapport.skjemaNavn = skjemaNavn
			}
		}
		
		return ukeDetaljerRapportList
	}
	
	
	private List hentDetaljertUkeRapportResultList(Date fra, Date til, Intervjuer i, String delProduktNummer ) {		
		
		def c = Krav.createCriteria()
		
		def resultSet = c {		
			projections {
				property("produktNummer")
				property("antall")
				
				timeforing {
					property("fra")
					property("arbeidsType")
				}
				
				intervjuer {
					property("navn")
					property("initialer")
					property("intervjuerNummer")
				}
			}
			
			intervjuer{
				eq("id", i.id)
			}
			
			timeforing {
				ge("fra", fra)
				le("til", til)
			}	
			
			if (delProduktNummer){
				eq("produktNummer", delProduktNummer)
			}
			eq("kravType", KravType.T)
		} 
		
		lagResultatListe(resultSet)
	}

	private List hentDetaljertUkeRapportResultList(Date fra, Date til, Klynge kl, IntervjuerStatus iStatus, String delProduktNummer) {
		List detaljertUkeRapportResultList = []
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				property("produktNummer")
				property("antall")
				
				timeforing {
					property("fra")
					property("arbeidsType")
				}
				
				intervjuer {
					property("navn")
					property("initialer")
					property("intervjuerNummer")
				}
			}
			
			intervjuer{
				klynge{
					eq("id", kl.id)
				}
				if (iStatus){
					eq("status", iStatus)
				}
			}
			
			timeforing {
				ge("fra", fra)
				le("til", til)
			}
			
			if (delProduktNummer){
				eq("produktNummer", delProduktNummer)
			}
			eq("kravType", KravType.T)
		}
		
		lagResultatListe(resultSet)
	}
	
	private List hentDetaljertUkeRapportResultList(Date fra, Date til, IntervjuerStatus iStatus, String delProduktNummer) {
		List detaljertUkeRapportResultList = []
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				property("produktNummer")
				property("antall")
				
				timeforing {
					property("fra")
					property("arbeidsType")
				}
				
				intervjuer {
					property("navn")
					property("initialer")
					property("intervjuerNummer")
				}
			}
			
			intervjuer{
				eq("status", iStatus)
			}
			
			timeforing {
				ge("fra", fra)
				le("til", til)
			}
			
			if (delProduktNummer){
				eq("produktNummer", delProduktNummer)
			}
			eq("kravType", KravType.T)
		}
		
		lagResultatListe(resultSet)
	}
	
	private List hentDetaljertUkeRapportResultList(Date fra, Date til, String delProduktNummer) {
		List detaljertUkeRapportResultList = []
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				property("produktNummer")
				property("antall")
				
				timeforing {
					property("fra")
					property("arbeidsType")
				}
				
				intervjuer {
					property("navn")
					property("initialer")
					property("intervjuerNummer")
				}
			}
			
			timeforing {
				ge("fra", fra)
				le("til", til)
			}
			
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.T)
		}
		
		lagResultatListe(resultSet)
	}

	
	private List hentDetaljertUkeRapportResultList(Date fra, Date til) {
		List detaljertUkeRapportResultList = []
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				property("produktNummer")
				property("antall")
				
				timeforing {
					property("fra")
					property("arbeidsType")
				}
				
				intervjuer {
					property("navn")
					property("initialer")
					property("intervjuerNummer")
				}
			}
			
			timeforing {
				ge("fra", fra)
				le("til", til)
			}
			
			eq("kravType", KravType.T)
		}
		
		lagResultatListe(resultSet)
	}
	
	private lagResultatListe(def resultSet) {
		
		List detaljertUkeRapportResultList = []
		resultSet.each {
			List result = it

			DetaljertUkeRapportResult data = new DetaljertUkeRapportResult()
			data.produktNummer = result.get(0)
			data.antall = result.get(1)
			data.datoJobbet = result.get(2)
			data.arbeidsType = result.get(3)
			data.intervjuerNavn = result.get(4)
			data.intervjuerInitialer = result.get(5)
			data.intervjuerNummer = result.get(6)

			detaljertUkeRapportResultList.add(data)
		}

		return detaljertUkeRapportResultList
	}
	
	private List behandleRapportResult( List detaljertUkeRapportResultList ) {	
		if( detaljertUkeRapportResultList == null || detaljertUkeRapportResultList.size() == 0 ) {
			return null
		}
		
		HashMap ukeDetaljerRapportMap = []
		
		detaljertUkeRapportResultList.each { DetaljertUkeRapportResult detaljertUkeRapportResult ->
			String key = genererKey(detaljertUkeRapportResult.datoJobbet, detaljertUkeRapportResult.intervjuerNummer, detaljertUkeRapportResult.produktNummer)
			
			UkeRapportDetaljerData ukeRapportDetaljerData = ukeDetaljerRapportMap.get(key)
			
			if( ukeRapportDetaljerData == null ) {
				ukeRapportDetaljerData = opprettUkeRapportDetaljerData(detaljertUkeRapportResult)
				leggTilDetaljerOmTid(ukeRapportDetaljerData, detaljertUkeRapportResult)
				ukeDetaljerRapportMap.put(key, ukeRapportDetaljerData)
			} 
			else {
				leggTilDetaljerOmTid(ukeRapportDetaljerData, detaljertUkeRapportResult)
			}
		}
		
		return leggTilTidsSummer(new ArrayList(ukeDetaljerRapportMap.values()));
	}
	
	
	private UkeRapportDetaljerData opprettUkeRapportDetaljerData( DetaljertUkeRapportResult detaljertUkeRapportResult ) {
		UkeRapportDetaljerData ukeRapportDetaljerData = new UkeRapportDetaljerData()
		ukeRapportDetaljerData.arbeidsDato = detaljertUkeRapportResult.datoJobbet
		ukeRapportDetaljerData.delProduktNummer = detaljertUkeRapportResult.produktNummer
		ukeRapportDetaljerData.initialer = detaljertUkeRapportResult.intervjuerInitialer
		ukeRapportDetaljerData.intervjuerNummer = detaljertUkeRapportResult.intervjuerNummer
		ukeRapportDetaljerData.navn = detaljertUkeRapportResult.intervjuerNavn
		
		return ukeRapportDetaljerData
	}
	
	
	private UkeRapportDetaljerData leggTilDetaljerOmTid( UkeRapportDetaljerData ukeRapportDetaljerData, DetaljertUkeRapportResult detaljertUkeRapportResult ) {	
		
		ArbeidsType arbeidsType =  detaljertUkeRapportResult.arbeidsType
		
		if( arbeidsType == ArbeidsType.REISE ) {
			ukeRapportDetaljerData.reiseTid += detaljertUkeRapportResult.antall
		} 
		else if( arbeidsType == ArbeidsType.TRENTE_LESTE_INSTRUKS || arbeidsType == ArbeidsType.KURS ) {
			ukeRapportDetaljerData.ovelseTid += detaljertUkeRapportResult.antall
		}
		else {
			ukeRapportDetaljerData.arbeidsTid = ukeRapportDetaljerData.arbeidsTid + detaljertUkeRapportResult.antall
		}
		
		return ukeRapportDetaljerData
	}
	
	private String genererKey( Date datoJobbet, Long intervjuerNummer, String produktNummer ) {
		Calendar c = Calendar.getInstance()
		c.setTime(datoJobbet)
		int aar = c.get(Calendar.YEAR)
		int maaned = c.get(Calendar.MONTH) + 1
		int dag = c.get(Calendar.DAY_OF_MONTH)
		
		return aar + "_" + maaned + "_" + dag + "_" + intervjuerNummer + "_" + produktNummer
	}
	
	private List leggTilTidsSummer(List ukeRapportDetaljerDataList) {
		UkeRapportDetaljerData tidsSummer = new UkeRapportDetaljerData()
		tidsSummer.delProduktNummer = SUMMER_FORTEKST
		ukeRapportDetaljerDataList.each { UkeRapportDetaljerData ukeRapportDetaljerData ->
			tidsSummer.arbeidsTid	+= ukeRapportDetaljerData.arbeidsTid
			tidsSummer.reiseTid     += ukeRapportDetaljerData.reiseTid
			tidsSummer.ovelseTid    += ukeRapportDetaljerData.ovelseTid
		}
		ukeRapportDetaljerDataList.add(tidsSummer)
		return ukeRapportDetaljerDataList
	}
}
