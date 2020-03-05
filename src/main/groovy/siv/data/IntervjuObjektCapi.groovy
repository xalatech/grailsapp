package siv.data

import sivadm.IntervjuObjekt

class IntervjuObjektCapi {

	Long intervjuObjektId
	String delProduktNummer
	String skjemaNavn
	String intervjuObjektNummer
	String intervjuObjektNavn
	String adresse
	String postNummer
	String postSted
	String kommunenummer
	String tildeltIntervjuer
	Date sisteFrist
	String tidligereIntervjuer1
	String tidligereIntervjuer2
	String tidligereIntervjuer3
	String onsketIntervjuer
	Long oppdragId

	private void settIntervjuObjektFelter(IntervjuObjekt io) {
		this.intervjuObjektId = io.id
		this.intervjuObjektNavn = io.navn
		this.intervjuObjektNummer = io.intervjuObjektNummer
		this.adresse = io.findGjeldendeBesokAdresse()?.gateAdresse
		this.postNummer = io.findGjeldendeBesokAdresse()?.postNummer
		this.postSted = io.findGjeldendeBesokAdresse()?.postSted
		this.kommunenummer = io.findGjeldendeBesokAdresse()?.kommuneNummer
	}
}
