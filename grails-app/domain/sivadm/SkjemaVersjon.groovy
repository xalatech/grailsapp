package sivadm

class SkjemaVersjon {

	Long versjonsNummer
	Date gyldigFom
	Date gyldigTom
	Date skjemaGodkjentDato
	String skjemaGodkjentInitialer
	Date webskjemaGodkjentDato
	String webskjemaGodkjentInitialer
	String kommentar
	Skjema skjema
	
	static belongsTo = Skjema
	
    static constraints = {
		versjonsNummer(nullable: false)
		gyldigFom(nullable: false)
		gyldigTom(nullable: true)
		skjemaGodkjentDato(nullable: true)
		skjemaGodkjentInitialer(blank: true, nullable: true, minSize: 3, maxSize: 3)
		webskjemaGodkjentDato(nullable: true)
		webskjemaGodkjentInitialer(blank: true, nullable: true, minSize: 3, maxSize: 3)
		kommentar(blank: true, nullable: true)
		skjema(nullable: false)
    }
		
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'skjema_versjon_seq'],  sqlType: 'integer'
	}
	
	public String toString() {
		return versjonsNummer;
	}
}