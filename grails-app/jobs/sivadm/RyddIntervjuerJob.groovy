package sivadm

import siv.type.ProsessNavn


class RyddIntervjuerJob {

	IntervjuerService intervjuerService
	def prosessLoggService

	static triggers = {
        cron name: "ryddIntervjuerTrigger", cronExpression: "0 30 4 * * ?"
	}
	
	def concurrent = false
	
	def group = "SivGroup"

	def execute() {
		long start = System.currentTimeMillis()
		log.info "RyddIntervjuerJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.RYDD_INTERVJUER_JOB)
		intervjuerService.ryddIntervjuer()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "RyddIntervjuerJob executed in " + total + " ms"
	}
}