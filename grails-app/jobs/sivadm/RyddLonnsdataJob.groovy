package sivadm

import siv.type.ProsessNavn


class RyddLonnsdataJob {

	def prosessLoggService
	def kjorebokService
	def sapFilService
	def utleggService
	def timeforingService
	def kravService

	static triggers = {
        cron name: "ryddLonnsdataTrigger", cronExpression: "0 5 3 ? * SAT"
	}
	
	def concurrent = false
	
	def group = "SivGroup"

	def execute() {
		long start = System.currentTimeMillis()
		log.info "RyddLonnsdataJob starting..."

		prosessLoggService.registrerProsessKjoring(ProsessNavn.RYDD_LONNSDATA_JOB)

		sapFilService.ryddSapFil()
		kravService.slettSilMeldinger()
		kravService.ryddKravUtenKjorebok()
		kjorebokService.ryddKjoreboker()
		utleggService.ryddUtlegg()
		timeforingService.ryddTimeforinger()

		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "RyddLonnsdataJob executed in " + total + " ms"
	}
}
