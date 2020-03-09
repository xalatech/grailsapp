package sil

import sil.rapport.data.ProsjektRapportData;
import sil.type.KravType;
import siv.type.ArbeidsType;
import siv.type.UtleggType;
import sivadm.Prosjekt;
import sivadm.Skjema;

class RapportProsjektService {

    static transactional = true
	
	private Date startDato
	private Date sluttDato

	def hentProsjektRapportForProsjektListe( List prosjektListe, Date periodeStart, Date periodeSlutt  ) {
		this.startDato = periodeStart
		this.sluttDato = periodeSlutt

		List skjemaList = Skjema.findAllByProsjektInList(prosjektListe)

		return hentProsjektRapport(skjemaList)
	}

	def hentProsjektRapportForProsjekt( Prosjekt prosjekt, Date periodeStart, Date periodeSlutt  ) {
		this.startDato = periodeStart
		this.sluttDato = periodeSlutt
		
		List skjemaList = Skjema.findAllByProsjekt( prosjekt )
		return hentProsjektRapport(skjemaList)
	}
	
	def hentProsjektRapportForAlle( Date periodeStart, Date periodeSlutt ) {
		this.startDato = periodeStart
		this.sluttDato = periodeSlutt

		List skjemaList = Skjema.list()
		return hentProsjektRapport(skjemaList)
	}
    
	def hentProsjektRapport( List skjemaList ) {
		
		List prosjektRapportDataList = []
		
		skjemaList.each { Skjema skjema ->
			ProsjektRapportData data = new ProsjektRapportData()
			
			data.skjemaNavn = skjema.skjemaNavn
			data.delProduktNummer = skjema.delProduktNummer 
			data.aar = skjema.prosjekt.aargang
			data.arbeidsTid	= finnArbeidsTidForSkjema(skjema.id)
			data.reiseTid = finnReiseTidForSkjema(skjema.id)
			data.totalTid = data.arbeidsTid + data.reiseTid
			data.intervjuTid = finnIntervjuTidForSkjema(skjema.id)
			data.sporingTid = finnSporingsTidForSkjema(skjema.id)
			data.treningsTid = finnTreningTidForSkjema(skjema.id)
			data.kursTid = finnKursTidForSkjema(skjema.id)
			data.testTid = finnTestTidForSkjema(skjema.id)
			data.arbeidsLedelseTid = finnArbeidsLedelseTidForSkjema(skjema.id)
			data.annetTid = finnAnnetTidForSkjema(skjema.id)
			data.antallKm = finnAntallKmForSkjema(skjema.id)
			data.reiseUtgifter = finnReiseUtgifterForSkjema(skjema.id)
			data.andreUtgifter = finnAndreUtgifterForSkjema(skjema.id)
			
			prosjektRapportDataList.add(data)
		}

		return prosjektRapportDataList
    }
	
	
	protected String hentDelProduktNummerForSkjema( long skjemaId ) {
		def skjema = Skjema.get(skjemaId)
		return skjema.delProduktNummer
	}
	
	
	protected int finnArbeidsTidForSkjema( long skjemaId ) {	
		def delProduktNummer = hentDelProduktNummerForSkjema(skjemaId)

		def c = Krav.createCriteria() 
		
		def resultSet = c {
			projections {
				property("antall")
			}
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.T)
			
			if (startDato) {
				ge("importertDato", startDato)
			}
			if (sluttDato) {
				le("importertDato", sluttDato)
			}

			timeforing{
				ne("arbeidsType", ArbeidsType.REISE)
			}
		}
		
		int arbeidsTidIMinutter = 0
		
		if( resultSet != null && resultSet.size() > 0 ) {
			arbeidsTidIMinutter = resultSet.sum()
		}
		
		return arbeidsTidIMinutter
	} 
	
	
	protected int finnReiseTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.REISE)
	}
	
	
	protected int finnIntervjuTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.INTERVJUE)
	}
	
	
	protected int finnSporingsTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.SPORING)
	}
	
	protected int finnTreningTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.TRENTE_LESTE_INSTRUKS)
	}
	
	protected int finnKursTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.KURS)
	}
	
	
	protected int finnTestTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.TESTET_SKJEMA)
	}
	
	
	protected int finnArbeidsLedelseTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.ARBEIDSLEDELSE)
	}
	
	
	protected int finnAnnetTidForSkjema( long skjemaId ) {
		return finnTidForSkjemaOgArbeidsType(skjemaId, ArbeidsType.ANNET)
	}
	
	
	protected int finnTidForSkjemaOgArbeidsType(long skjemaId, ArbeidsType arbeidsType) {
		def delProduktNummer = hentDelProduktNummerForSkjema(skjemaId)
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				property("antall")
			}
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.T)

			if (startDato) {
				ge("importertDato", startDato)
			}
			if (sluttDato) {
				le("importertDato", sluttDato)
			}

			timeforing{
				eq("arbeidsType", arbeidsType)
			}
		}
		
		int tid = 0
		
		if( resultSet != null && resultSet.size() > 0 ) {
			tid = resultSet.sum()
		}
		
		return tid
	}
	
	
	protected int finnAntallKmForSkjema( long skjemaId ) {
		def delProduktNummer = hentDelProduktNummerForSkjema(skjemaId)
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				
				kjorebok {
					property("kjorteKilometer")
				}
			}
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.K)

			if (startDato) {
				ge("importertDato", startDato)
			}
			if (sluttDato) {
				le("importertDato", sluttDato)
			}

		}
		
		int antallKm = 0
		
		if( resultSet != null && resultSet.size() > 0 ) {
			antallKm = resultSet.sum()
		}
		
		return antallKm
	}
	
	
	protected int finnReiseUtgifterForSkjema( long skjemaId ) {
		def delProduktNummer = hentDelProduktNummerForSkjema(skjemaId)
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				utlegg {
					property("belop")
				}
			}
			
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.U)

			if (startDato) {
				ge("importertDato", startDato)
			}
			if (sluttDato) {
				le("importertDato", sluttDato)
			}

			utlegg {
				ne("utleggType", UtleggType.FRIMERKER)
				ne("utleggType", UtleggType.KART)
				ne("utleggType", UtleggType.TELEFON)
				ne("utleggType", UtleggType.KOST_GODT)
				ne("utleggType", UtleggType.ANNET)
			}
		}
		
		int reiseUtgifter = 0
		
		if( resultSet != null && resultSet.size() > 0 ) {
			reiseUtgifter = resultSet.sum()
		}
		
		return reiseUtgifter
	}
	
	protected int finnAndreUtgifterForSkjema( long skjemaId ) {
		def delProduktNummer = hentDelProduktNummerForSkjema(skjemaId)
		
		def c = Krav.createCriteria()
		
		def resultSet = c {
			projections {
				utlegg {
					property("belop")
				}
			}
			
			eq("produktNummer", delProduktNummer)
			eq("kravType", KravType.U)

			if (startDato) {
				ge("importertDato", startDato)
			}
			if (sluttDato) {
				le("importertDato", sluttDato)
			}

			utlegg {
				or {
					eq("utleggType", UtleggType.FRIMERKER)
					eq("utleggType", UtleggType.KART)
					eq("utleggType", UtleggType.TELEFON)
					eq("utleggType", UtleggType.KOST_GODT)
					eq("utleggType", UtleggType.ANNET)
				}
			}
		}
		
		int andreUtgifter = 0
		
		if( resultSet != null && resultSet.size() > 0 ) {
			andreUtgifter = resultSet.sum()
		}
		
		return andreUtgifter
	}
	
	
	
	
}
