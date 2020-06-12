package sil

import sivadm.Intervjuer

class SilMelding {
	String tittel
	String melding
	Krav krav
	Intervjuer intervjuer
	Date dato = new Date()
	String skrevetAv
    
	static belongsTo = [krav:Krav]
	
	static constraints = {
    	tittel(blank: false, nullable: false)
		melding(blank: false,nullable: false)
		krav(nullable: false)
		intervjuer(nullable: false)
		skrevetAv(nullable: true, blank: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'sil_melding_seq'],  sqlType: 'integer'
	}
	
	def beforeSave = {
		dato = new Date()	
	}
	
	String toString() {
		return tittel + " - " + melding
	}
	
	SilMelding kopierSilMelding() {
		SilMelding sm = new SilMelding(tittel: this.tittel, melding: this.melding, krav: this.krav, intervjuer: this.intervjuer, skrevetAv: this.skrevetAv)
		return sm
	}
}