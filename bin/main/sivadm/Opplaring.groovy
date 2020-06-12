package sivadm

import siv.type.InfoHendelseType;

class Opplaring {

	InfoHendelseType hendelseType
	Date gjennomfortDato
	String gjennomfortSted
	Boolean obligatorisk
	String beskrivelse
	Skjema skjema

	static hasMany = [intervjuere:Intervjuer]

	static constraints = {
		gjennomfortDato(nullable: false)
		gjennomfortSted(blank: true, nullable: true)
		beskrivelse(blank: true, nullable: true)
	}

	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'opplaring_seq'],  sqlType: 'integer'
	}
		
	static belongsTo = Skjema

	public String toString() {
		return beskrivelse
	}
}