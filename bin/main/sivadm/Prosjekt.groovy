package sivadm

import siv.type.ProsjektFinansiering;
import siv.type.ProsjektModus
import siv.type.ProsjektStatus;

class Prosjekt {
	
	String prosjektNavn
	String produktNummer
	String aargang
	String registerNummer
	ProsjektStatus prosjektStatus
	ProsjektModus modus
	ProsjektFinansiering finansiering
	Long prosentStat
	Long prosentMarked
	Boolean panel
	Date oppstartDato
	Date avslutningsDato
	String kommentar
	ProsjektLeder prosjektLeder
	
	static hasMany = [skjemaer:Skjema, prosjektDeltagere:ProsjektDeltager]
		
	static constraints = {
		avslutningsDato(nullable: false)
		oppstartDato(nullable: true)
		aargang(nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		prosjektNavn(nullable: false, blank: false)
		produktNummer(nullable: false, blank: false, size: 5..5, matches: "\\d{5}")
		kommentar(nullable: true, blank: true)
		registerNummer(nullable: true, blank: true)
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
		id column: 'ID', generator: 'sequence',  params:[sequence:'prosjekt_seq'],  sqlType: 'integer'
	}
		
	public String toString( ) {
		return prosjektNavn + " (" + aargang + ")";
	}
}