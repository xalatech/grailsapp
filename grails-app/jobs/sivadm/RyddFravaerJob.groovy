package sivadm

import siv.type.ProsessNavn


class RyddFravaerJob {

	FravaerService fravaerService
	def prosessLoggService

	static triggers = {
        cron name: "ryddFravaerTrigger", cronExpression: "0 0 4 * * ?"
	}
	
	static concurrent = false
	
	static group = "SivGroup"

	void execute() {
		long start = System.currentTimeMillis()
		log.info "RyddFravaerJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.RYDD_FRAVAER_JOB)
		fravaerService.ryddFravaer()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "RyddFravaerJob executed in " + total + " ms"
	}
}