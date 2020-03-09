package sil

import java.util.Date;
import sil.type.*

class Lonnart {
	String lonnartNummer
	String navn
	String beskrivelse
	String kmKode
	String konto
	KontoTekstType kontoTekst
	MarkedType markedType
	String redigertAv
	Date redigertDato = new Date()
	
    static constraints = {
		lonnartNummer(nullable: false, size: 4..4)
		navn(blank: false, nullable: false)
		beskrivelse(blank: true, nullable: true)
		kmKode(blank: true, nullable: true)
		konto(blank: false, nullable: false, size: 10..10)
		kontoTekst(nullable: false)
		markedType(nullable: false)
		redigertDato(nullable: false)
		redigertAv(blank: true, nullable: true)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'lonnart_seq'],  sqlType: 'integer'
	}
		
	def beforeInsert = {
		redigertDato = new Date()
	}
	
	def beforeUpdate = {
		redigertDato = new Date()
	}
}