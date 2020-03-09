package sivadm

import siv.type.TelefonType;

class Telefon {
	
	String telefonNummer
	TelefonType telefonType // ikke lenger i bruk
	String kommentar
	String kilde
	Boolean resepsjon
	Boolean original
	Boolean gjeldende
	String redigertAv
	Date redigertDato
	
	static constraints = {
		kommentar(nullable: true, blank: true)
		kilde(nullable: true, blank: true)
		resepsjon(nullable: true, blank: true)
		original(nullable: true, blank: true)
		gjeldende(nullable: true, blank: true)
		telefonType(nullable: true)
		telefonNummer(blank: false, nullable: false, matches: "\\d\\d\\d\\d\\d\\d\\d\\d")
		redigertAv(nullable: true, blank: true)
		redigertDato(nullable: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'telefon_seq'],  sqlType: 'integer'
	}
		
	static belongsTo = IntervjuObjekt
	
	def beforeInsert() {
		redigertDato = new Date()
	}
	
	def beforeUpdate() {
		redigertDato = new Date()
	}
	
	public String toString() {
		return telefonNummer
	}
}