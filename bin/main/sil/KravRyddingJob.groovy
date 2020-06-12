package sil

import sil.type.KravStatus
import sil.type.KravType
import siv.type.ProsessNavn;

class KravRyddingJob {
	static triggers = {
		cron name: "kravRyddingTrigger", cronExpression: "0 0 6 * * ?"
	}

	def group = "MyGroup"
	def prosessLoggService

	def execute() {
		long start = System.currentTimeMillis()
		log.info "KravRyddingJob starting..."
		
		prosessLoggService.registrerProsessKjoring( ProsessNavn.KRAV_RYDDING_JOB )
		
		// Finn Krav som er sendt tilbake til intervjuere som intervjueren har slettet.

		def statusListe = [
			KravStatus.TIL_RETTING_INTERVJUER,
			KravStatus.INAKTIV
		]

		List<Krav> kravListe = Krav.createCriteria().list() { 'in'("kravStatus", statusListe) }

		kravListe.each { krav ->
			if(krav.kravType == KravType.T && !krav.timeforing) {
				log.info("Sletter krav med id " + krav.id + ", tilhørende timeføring er slettet av intervjuer")
				krav.delete(flush: true)
			}
			if(krav.kravType == KravType.K && !krav.kjorebok) {
				log.info("Sletter krav med id " + krav.id + ", tilhørende kjørebok er slettet av intervjuer")
				krav.delete(flush: true)
			}
			if(krav.kravType == KravType.U && !krav.utlegg) {
				log.info("Sletter krav med id " + krav.id + ", tilhørende utlegg er slettet av intervjuer")
				krav.delete(flush: true)
			}
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "KravRyddingJob executed in " + total + " ms"
	}
}
