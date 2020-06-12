package sivadm

import siv.type.SkjemaStatus;

class StatHist {
	
	SkjemaStatus skjemaStatus
	Integer intervjuStatus
	String redigertAv
	Date dato
	
    static constraints = {
		skjemaStatus(nullable: true)
		intervjuStatus(nullable: true)
		redigertAv(nullable: true, blank: true)
		dato(nullable: false)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'stat_hist_seq'],  sqlType: 'integer'
		dato index: 'stat_hist_dato_indeks'

	}
}