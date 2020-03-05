package sil.rapport.data

import no.ssb.sivadm.util.DateUtil;
import no.ssb.sivadm.util.TimeUtil;

class ProsjektRapportData {
	String skjemaNavn
	String delProduktNummer
	String aar
	int arbeidsTid
	int reiseTid
	int totalTid
	int intervjuTid
	int sporingTid
	int treningsTid
	int kursTid
	int testTid
	int arbeidsLedelseTid
	int annetTid
	int antallKm
	int reiseUtgifter
	int andreUtgifter
	
	String getArbeidsTidFormatert() {
		return formatertTid(arbeidsTid)
	}
	
	String getReiseTidFormatert() {
		return formatertTid(reiseTid)
	}
	
	String getTotalTidFormatert() {
		return formatertTid(totalTid)
	}
	
	String getIntervjuTidFormatert() {
		return formatertTid(intervjuTid)
	}
	
	String getSporingTidFormatert() {
		return formatertTid(sporingTid)
	}
	
	String getTreningsTidFormatert() {
		return formatertTid(treningsTid)
	}
	
	String getKursTidFormatert() {
		return formatertTid(kursTid)
	}
	
	String getTestTidFormatert() {
		return formatertTid(testTid)
	}
	
	String getArbeidsLedelseTidFormatert() {
		return formatertTid(arbeidsLedelseTid)
	}
	
	String getAnnetTidFormatert() {
		return formatertTid(annetTid)
	}
	
	private String formatertTid(int minutter) {
		int timer = minutter / 60;
		int restMinutter = minutter % 60;
		String returTid = timer + ":";
		if (timer <= 9) returTid = "0" + returTid;
		if (restMinutter <= 9) returTid += "0";
		returTid += restMinutter;
		return returTid; 
	}
}
