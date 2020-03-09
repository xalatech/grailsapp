package sivadm

import siv.type.ProsessNavn;
import siv.type.SkjemaStatus


class PaaVentJob {

	def prosessLoggService
	def intervjuObjektService

	static triggers = {
		cron name: "paaVentTrigger", cronExpression: "0 0 1 * * ?"
	}
	
	def concurrent = false
	
	def group = "SivGroup"

	def execute() {
		long start = System.currentTimeMillis()
		log.info "PaaVentJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.PAA_VENT_JOB)
		intervjuObjektService.sjekkIntervjuObjektPaaVent()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "PaaVentJob executed in " + total + " ms"
	}
}