package sivadm

import util.TimeUtil;
import sil.SilMelding
import siv.type.*
import sil.*

class Utlegg {
	
	Intervjuer intervjuer
	Date dato
	String produktNummer
	UtleggType utleggType
	String spesifisering
	String merknad
	Double belop
	String redigertAv
	Date redigertDato
	TimeforingStatus timeforingStatus
	SilMelding silMelding
	Boolean harKjorebok = Boolean.FALSE
	
	Date kostFraTid
	Date kostTilTid
	String kostTilSted
	
	static constraints = {
		belop max: new Double(90000)
		intervjuer nullable: true
		redigertDato nullable: true
		redigertAv nullable: true, blank: true
		merknad nullable: true, blank: true
		timeforingStatus nullable: true
		produktNummer blank: false, nullable: false
		spesifisering blank: true, nullable: true
		silMelding nullable: true
		kostFraTid nullable: true
		kostTilTid nullable: true
		kostTilSted nullable: true, blank: true
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'utlegg_seq'],  sqlType: 'integer'
	}
	
	public boolean godkjent() {
		if( timeforingStatus == TimeforingStatus.GODKJENT ) {
			return true
		}
		else {
			return false
		}
	}
	
	public boolean avvist() {
		if( timeforingStatus == TimeforingStatus.AVVIST ) {
			return true
		}
		else {
			return false
		}
	}
	
	public String toString() {
		return "" + belop + " kr"
	}
}