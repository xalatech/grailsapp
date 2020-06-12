package sil

import sil.type.KravStatus
import sil.type.KravType
import sivadm.Intervjuer
import sivadm.Kjorebok
import sivadm.Timeforing
import sivadm.Utlegg

class Krav {
	Intervjuer intervjuer
	Timeforing timeforing
	Kjorebok kjorebok
	Utlegg utlegg
	String produktNummer
	Date dato = new Date()
	Date importertDato = new Date()
	Double antall  //vil være minutter ved KravType.T, kilometer ved KravType.K og kroner ved KravType.U
	KravType kravType
	KravStatus kravStatus = KravStatus.OPPRETTET
	KravStatus forrigeKravStatus
	Krav opprinneligKrav
	Timeforing opprinneligTimeforing
	Kjorebok opprinneligKjorebok
	Utlegg opprinneligUtlegg
	SilMelding silMelding
	List<AutomatiskKontroll> automatiskeKontroller = []
	
	static hasMany = [automatiskeKontroller: AutomatiskKontroll]
	
    static constraints = {
		timeforing(nullable: true)
		kjorebok(nullable: true)
		utlegg(nullable: true)
		produktNummer(blank: false, nullable: false)
		forrigeKravStatus(nullable: true)
		intervjuer(nullable: false)
		antall(nullable: false)
		opprinneligKrav (nullable: true)
		silMelding(nullable: true)
		kravStatus(nullable: false)
		opprinneligTimeforing(nullable: true)
		opprinneligKjorebok(nullable: true)
		opprinneligUtlegg(nullable: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'krav_seq'],  sqlType: 'integer'
		intervjuer index: 'krav_int_index' 
	}
}
