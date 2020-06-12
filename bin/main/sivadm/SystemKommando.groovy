package sivadm

class SystemKommando {

	String filnavn
	Long maksSekunder
	String beskrivelse
	String redigertAv
	Date redigertDato
	
    static constraints = {
		beskrivelse maxSize: 250
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'sys_kommando_seq'],  sqlType: 'integer'
	}
	
}
