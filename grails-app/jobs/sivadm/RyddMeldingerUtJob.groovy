package sivadm

import siv.type.ProsessNavn


class RyddMeldingerUtJob {
	def synkroniseringService
	def prosessLoggService

	static triggers = {
		cron name: "ryddMeldingUtTrigger", cronExpression: "0 30 2 * * ?"
	}
	
	static concurrent = false
	
	static group = "SivGroup"

	void execute() {
		long start = System.currentTimeMillis()
		log.info "RyddMeldingerUtJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.RYDD_MELDING_UT_JOB)
		synkroniseringService.ryddMeldingUt()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "RyddMeldingerUtJob executed in " + total + " ms"
	}
}