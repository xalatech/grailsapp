package sivadm

import siv.type.ProsessNavn


class RyddMeldingerInnJob {

	BlaiseMeldingInnService blaiseMeldingInnService
	def prosessLoggService

	static triggers = {
		cron name: "ryddMeldingInnTrigger", cronExpression: "0 0 2 * * ?"
	}
	
	static concurrent = false
	
	static group = "SivGroup"

	void execute() {
		long start = System.currentTimeMillis()
		log.info "RyddMeldingerInnJob starting..."
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.RYDD_MELDING_INN_JOB)
		blaiseMeldingInnService.ryddMeldingInn()
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "RyddMeldingerInnJob executed in " + total + " ms"
	}
}