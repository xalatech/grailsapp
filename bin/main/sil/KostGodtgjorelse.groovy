package sil

import siv.type.UtleggType;

class KostGodtgjorelse {

	Date godkjent
	String godkjentAv
	Date kostGodtDato
	String navn
	String initialer
	Long intervjuerNummer
	String produktNummer
	String utleggType
	String klynge
	String finansiering
	String tilSted
	Date fraTid
	Date tilTid
	
	boolean behandlet
	
    static constraints = {
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'kost_seq'],  sqlType: 'integer'
	}
}
