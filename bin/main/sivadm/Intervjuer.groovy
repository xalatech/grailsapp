package sivadm

import java.util.Date;
import java.util.List;
import siv.type.*;

class Intervjuer {

	Klynge klynge
	String initialer
	Long intervjuerNummer
	String navn
	Kjonn kjonn
	Date fodselsDato
	String gateAdresse
	String gateAdresse2
	String postNummer
	String postSted
	String kommuneNummer
	String epostPrivat
	String epostJobb
	String mobil
	String telefonHjem
	String telefonJobb
	IntervjuerStatus status
	Date ansattDato
	Long avtaltAntallTimer
	IntervjuerArbeidsType arbeidsType
	Date sluttDato
	Boolean pensjonistLonn = false
	Boolean lokal = false
	String kommentar
		
	static constraints = {
	    fravaer(nullable:true)
		mobil(nullable:true, blank: true)
		telefonHjem(nullable:true, blank: true)
		telefonJobb(nullable:true, blank: true)
		postSted(nullable: true, blank: true)
		postNummer(nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		kommuneNummer(nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		ansattDato(nullable: false)
		avtaltAntallTimer(nullable: false)
		epostJobb(nullable: false, blank: false)
		epostPrivat(nullable:true, blank: true)
		initialer(nullable: false, blank: false, unique: true, maxSize: 3, minSize:3)
		intervjuerNummer(nullable: false, unique: true)
		fodselsDato(nullable: false)
		gateAdresse(nullable: true, blank: true)
		gateAdresse2(nullable:true, blank: true)
		klynge(nullable: false)
		navn(nullable: false, blank: false)
		sluttDato(nullable:true)
		kommentar(nullable:true, blank: true)
	}

	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'intervjuer_seq'],  sqlType: 'integer'
	}
	
	static hasMany = [fravaer:Fravaer]

	public String toString() {
		return navn + " (" + initialer + ")"
	}
	
	def getInitialerOgNavn() {
		return initialer + " - " + navn
	}	
	
	def getNavnOgInitialer() {
		return navn + " (" + initialer + ")"
	}	
}