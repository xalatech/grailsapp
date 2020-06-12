package sivadm

import siv.type.ProsjektFinansiering;

class Produkt {
	String navn
	String beskrivelse
	String produktNummer
	ProsjektFinansiering finansiering
	Long prosentStat
	Long prosentMarked
	
    static constraints = {
    	navn(blank: false, nullable: false)
		beskrivelse(blank: true, nullable: true)
		produktNummer(blank: false, nullable: false, maxSize: 7, minSize: 7)
		
		finansiering validator: { value, command ->
			def melding
			
			if(value == ProsjektFinansiering.STAT_MARKED) {
				if((command.prosentStat + command.prosentMarked) != 100) {
					melding = 'sivadm.error.prosjekt.prosent'
				}
			}
			else if(value == ProsjektFinansiering.STAT) {
				command.prosentStat = 100
				command.prosentMarked = 0
			}
			else {
				command.prosentStat = 0
				command.prosentMarked = 100
			}
			
			return melding
		}
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'produkt_seq'],  sqlType: 'integer'
	}
}