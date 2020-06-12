package sivadm

import siv.type.InfoHendelseType;

class SkjemaInfoHendelse {

	InfoHendelseType hendelseType
	Date gjennomfortDato
	String gjennomfortSted
	Boolean obligatorisk
	String beskrivelse
	Skjema skjema
	
	static hasMany = [intervjuere:Intervjuer]
	
    static constraints = {
    }
	
	static belongsTo = Skjema
	
	static mapping = {
		table 'skjema_ih'
		intervjuere column: 'sih_intervjuer_id'
		skjema column: 'skjema_id'
		id column: 'ID', generator: 'sequence',  params:[sequence:'skjema_info_hendelse_seq'],  sqlType: 'integer'
	}
		
	public String toString() {
		return beskrivelse
	}
}