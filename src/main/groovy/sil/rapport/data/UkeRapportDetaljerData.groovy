package sil.rapport.data

import no.ssb.sivadm.util.DateUtil;

class UkeRapportDetaljerData {	
	Long intervjuerNummer
	String navn
	String initialer
	String skjemaNavn
	String delProduktNummer
	Date arbeidsDato
	int arbeidsTid
	int reiseTid
	int ovelseTid
	int totalTid
	
	public String getOvelseTidFormatert() {
		return DateUtil.formatMinutes(ovelseTid)
	}
	
	public String getArbeidsTidFormatert() {
		return DateUtil.formatMinutes(arbeidsTid)
	}
	
	public String getReiseTidFormatert() {
		return DateUtil.formatMinutes(reiseTid)
	}
	
	public String getTotalTidFormatert() {
		return DateUtil.formatMinutes(totalTid)
	}
}
