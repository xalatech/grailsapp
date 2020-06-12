package sivadm


import siv.type.IntervjuType;

class Oppdrag {
	
	IntervjuObjekt intervjuObjekt
	Integer intervjuStatus
	IntervjuType intervjuType
	Date intervjuStatusDato
	String intervjuStatusKommentar
	String intervjuObjektNummer
	String skjemaKortNavn
	Long periodeNummer
	Long oppdragsKilde
	Date hentesTidligst
	String intervjuerInitialer
	Intervjuer intervjuer
	String intervjuStart
	Long bruktTid
	String returStatus
	String overfortLokaltTidspunkt
	String sisteSynkTidspunkt
	String tildelingsType
	boolean oppdragFullfort
	Boolean endringIntervjuObjekt
	Boolean endringAvtale
	Boolean endringKontakt
	Boolean endringData
	String endringTidspunkt
	boolean merketSlettHosIntervjuer
	boolean slettetHosIntervjuer
	boolean selvplukk
	String generertAv
	Date generertDato
	String operatorId
	Date gyldighetsDato
	Date kansellertDato
	String kansellertAv
	String kansellertKommentar
	boolean klarTilSynk = false
	
	static constraints = {
		intervjuObjekt(nullable: false)
		intervjuStatus(nullable: true)
		intervjuType(nullable: true)
		intervjuStatusDato(nullable: true)
		intervjuStatusKommentar(nullable: true)
		intervjuObjektNummer(nullable: true)
		skjemaKortNavn(nullable: false)
		periodeNummer(nullable: true)
		oppdragsKilde(nullable: true)
		hentesTidligst(nullable: true)
		intervjuerInitialer(nullable: true, maxSize: 3, minSize: 3)
		intervjuer(nullable: true)
		intervjuStart(nullable: true)
		bruktTid(nullable: true)
		returStatus(nullable: true)
		overfortLokaltTidspunkt(nullable: true, blank: true, maxSize: 19)
		sisteSynkTidspunkt(nullable: true, blank: true, maxSize: 19)
		tildelingsType(nullable: true)
		endringIntervjuObjekt(nullable: true)
		endringAvtale(nullable: true)
		endringKontakt(nullable: true)
		endringData(nullable: true)
		endringTidspunkt(nullable: true, blank: true, maxSize: 19, matches: "\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d")
		merketSlettHosIntervjuer(nullable: true)
		slettetHosIntervjuer(nullable: true)
		klarTilSynk(nullable: true)
		selvplukk(nullable: true)
		generertAv(nullable: true)
		generertDato(nullable: true)
		operatorId(nullable: true)
		gyldighetsDato(nullable: true)
		kansellertDato(nullable: true)
		kansellertAv(nullable: true)
		kansellertKommentar(nullable: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'oppdrag_seq'],  sqlType: 'integer'
		slettetHosIntervjuer column: 'slettet_hos_int'
		merketSlettHosIntervjuer column: 'merk_slett_hos_int'
		intervjuObjekt column: 'intervjuobjekt_id'
		endringIntervjuObjekt column: 'endring_io'
	}
	
	public String toString() {
		return intervjuObjekt?.navn
	}
	
	public void setStrSisteSynkTidspunkt() {
		
	}
	
	public void setIntervjuerInitialer(String initialer) {
		this.intervjuerInitialer = initialer?.toUpperCase()
	}
	
	public void setSkjemaKortNavn(String skjemaKortNavn) {
		this.skjemaKortNavn = skjemaKortNavn?.toUpperCase()
	}
	
	//	private Long oppdragId;
	//	private KatIntervjuType katIntervjuType;
	//	String redigertAv
	//	Date redigertDato
	
}