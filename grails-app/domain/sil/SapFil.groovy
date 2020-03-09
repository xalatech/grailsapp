package sil

import sil.type.FilType
import sil.type.KravStatus
import sil.type.SapFilStatusType

class SapFil {
	String fil
	Date dato = new Date()
	FilType filType
	Integer antallKrav
	Integer antallLinjer
	SapFilStatusType status
	String statusmelding
	
    static constraints = {
    	fil(blank: true, nullable: true)
		filType(nullable: false)
		antallKrav(nullable: true)
		antallLinjer(nullable: true)
		statusmelding(blank: true, nullable: true)
	}
	
	static hasMany = [kravListe: Krav]
		
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'sap_fil_seq'],  sqlType: 'integer'
	}
	
	public boolean kanRegenereres() {
		boolean b = true
		this.kravListe?.each {
			if(it.kravStatus != KravStatus.GODKJENT) {
				b = false	
			}
		}
		return b
	}
}