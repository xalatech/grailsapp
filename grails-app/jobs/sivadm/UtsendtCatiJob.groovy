package sivadm

import siv.type.ProsessNavn;
import siv.type.SkjemaStatus
import util.TimeUtil


class UtsendtCatiJob {

	def prosessLoggService
	def intervjuObjektService

	static triggers = {
		cron name: "utsendtCatiTrigger", cronExpression: "0 0 5 * * ?"
	}
	
	def concurrent = false
	
	def group = "SivGroup"

	def execute() {
		long start = System.currentTimeMillis()
		log.info "UtsendtCatiJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.UTSENDT_CATI_JOB)
		intervjuObjektService.sjekkIntervjuObjektUtsendtCati()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "UtsendtCatiJob executed in " + total + " ms"
	}
}
