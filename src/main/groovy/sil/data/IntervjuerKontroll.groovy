package sil.data

import sivadm.Intervjuer

class IntervjuerKontroll {
	Intervjuer intervjuer
	Integer antallKrav
	Integer antallFeiletAutomatiskeTester
	Integer antallBestodAutomatiskeTester
	Integer totalTimer
	Integer totalMinutter
	Long totalKm
	Long totalBelop
	Long timeKrav
	Long kjorebokKrav
	Long utleggKrav

	static constraints = {
		intervjuer(nullable: false)
		antallKrav(nullable: true)
		antallFeiletAutomatiskeTester(nullable: true)
		antallBestodAutomatiskeTester(nullable: true)
		totalTimer(nullable: true)
		totalMinutter(nullable: true)
		totalKm(nullable: true)
		totalBelop(nullable: true)
		timeKrav(nullable: true)
		kjorebokKrav(nullable: true)
		utleggKrav(nullable: true)
	}
}
