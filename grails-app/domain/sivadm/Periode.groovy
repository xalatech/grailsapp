package sivadm

import siv.type.PeriodeType;

class Periode {
	String aar
	Long periodeNummer
	PeriodeType periodeType
	Date oppstartDataInnsamling
	Date hentesTidligst
	Date planlagtSluttDato
	Date sluttDato
	String incentiver
	String kommentar
	Long delregisterNummer
	Skjema skjema
	
	static hasMany = [intervjuObjekter:IntervjuObjekt]
	
	static belongsTo = Skjema
	
	static constraints = {
		aar(nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		periodeNummer(nullable: false, max: 99L)
		periodeType(nullable: false)
		oppstartDataInnsamling(nullable: false)
		hentesTidligst(nullable: true)
		planlagtSluttDato(nullable: true)
		sluttDato(nullable: true)
		incentiver(nullable: true, blank: true)
		kommentar(nullable: true, blank: true)
		delregisterNummer(nullable: true)
		skjema(nullable: false)
	}

	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'periode_seq'],  sqlType: 'integer'
	}
		
	public String toString() {
		int antall = finnAntallIntervjuObjekter()
		return periodeNummer + " (" + antall + " IO)"
	}
	
	private int finnAntallIntervjuObjekter() {
		def c = IntervjuObjekt.createCriteria()
		
		def l = c {
			projections { countDistinct("id") }
			eq("periode", this)
		}
		
		return l.get(0)
	}
	
	static public Periode findPeriodeBySkjemaAndPNr(Skjema skjema, Long periodeNummer) {
		Periode[] perioder = skjema.getPerioder()
		
		for(Periode p : perioder)	{
			if(p.periodeNummer == periodeNummer)
				return p
		}
		
		return null
	}
}