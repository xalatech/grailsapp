package sivadm

import siv.type.FravaerType

class Fravaer {
	Date fraTidspunkt
	Date tilTidspunkt
	FravaerType fravaerType
	Long prosent
	String kommentar
	Intervjuer intervjuer
	String redigertAv
	Date redigertDato

	static belongsTo = Intervjuer
	
    static constraints = {
		tilTidspunkt(nullable: false)
		prosent(nullable: false, max: 100L)
		intervjuer(nullable: false)
		kommentar(nullable: true, blank: true)
		
		fraTidspunkt nullable: false, validator: { value, command ->
			if(value) {
				Date til = command.tilTidspunkt
				Date fra = command.fraTidspunkt
				def melding
				
				if(til && fra.after(til)) {
					melding = 'sivadm.intervjuer.fravaer.fra.til'
				}
								
				return melding
			}
		}
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'fravaer_seq'],  sqlType: 'integer'
	}
		
	public String toString() {	
		return fraTidspunkt
	}
}