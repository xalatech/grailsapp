package sil.data

class KravProduktSammendrag {
	String produktNummer
	Integer antallKrav = 0
	Integer antallTimeKrav = 0
	Integer antallKjorebokKrav = 0
	Integer antallUtleggKrav = 0
	Integer totaltTimer = 0
	Integer totaltMinutter = 0
	Integer totaltKm = 0
	Long totaltUtlegg = 0

	static constraints = {
		produktNummer(blank: false, nullable: false)
		antallKrav(nullable: false)
		antallTimeKrav(nullable: false)
		antallKjorebokKrav(nullable: false)
		antallUtleggKrav(nullable: false)
		totaltTimer(nullable: false)
		totaltMinutter(nullable: false)
		totaltKm(nullable: false)
		totaltUtlegg(nullable: false)
	}
}
