package sivadm

import java.util.Date;

import org.hibernate.Criteria;

import siv.type.AdresseType;

class Adresse {
	
	AdresseType adresseType
	String kommentar
	Date gyldigFom
	Date gyldigTom
	String gateAdresse
	String tilleggsAdresse
	String husBruksNummer
	String underNummer
	String bokstavFeste
	String boligNummer
	String postNummer
	String postSted
	String kommuneNummer
	String gateGaardNummer
	Boolean original
	String redigertAv
	Date redigertDato
	Boolean gjeldende
	
	static constraints = {
		kommentar(nullable: true, blank: true)
		gyldigTom(nullable: true)
		underNummer(nullable: true, blank: true, maxSize: 3)
		bokstavFeste(nullable: true, blank: true, maxSize: 4)
		boligNummer(nullable: true, blank: true, size: 5..5, maxSize: 5)
		postSted(nullable: true, blank: true, maxSize: 20)
		postNummer(nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		gateGaardNummer(nullable: true, blank: true, maxSize: 5)
		original(nullable: true)
		husBruksNummer(nullable: true, blank: true, maxSize: 4)
		gateAdresse(nullable: true, blank: true, maxSize: 40)
		tilleggsAdresse(nullable: true, blank: true, maxSize: 40)
		kommuneNummer(nullable: true, blank: true, size: 4..4, matches: "\\d\\d\\d\\d")
		gjeldende(nullable: true)
		redigertAv(nullable: true, blank: true)
		redigertDato(nullable: true)
	}
	
	static belongsTo = IntervjuObjekt
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'adresse_seq'],  sqlType: 'integer'
		gateAdresse index: 'adr_gate_indeks'
		postNummer index: 'adr_postnr_indeks'
		postSted index: 'adr_poststed_indeks'
	}
		
	def beforeInsert() {
		redigertDato = new Date()
	}
	
	def beforeUpdate() {
		redigertDato = new Date()
	}
	
	public String toString() {
		return gateAdresse + ", " + postSted + " "  + postNummer ; 
	}

	public void setGateAdresse(String gateAdresse) {
		this.gateAdresse = gateAdresse ? gateAdresse.toUpperCase() : null
	}
	
	public void setPostSted(String postSted) {
		this.postSted = postSted ? postSted.toUpperCase() : null
	}
		
	public static Adresse findByGateAdresse(String gateAdresse) {
		def c = Adresse.createCriteria()
		
		def list = c {
			ilike("gateAdresse", gateAdresse)
		}
		
		if(list) {
			return list.get(0)
		}
		else {
			return null
		}
	}
}