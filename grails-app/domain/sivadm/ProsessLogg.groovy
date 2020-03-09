package sivadm

import siv.type.ProsessNavn;

class ProsessLogg {

	ProsessNavn prosessNavn
	boolean suksess
	Date tidStart
	Date tidStopp
	String melding
	
    static constraints = {
		prosessNavn(unique: true)
		suksess(nullable: true, blank: true)
		tidStart(nullable: true, blank: true)
		tidStopp(nullable: true, blank: true)
		melding(nullable: true, blank: true)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'prosess_logg_seq'],  sqlType: 'integer'
	}
}