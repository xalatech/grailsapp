package sivadm;

import siv.type.ProsessNavn;
import siv.util.DatoUtil;

class BlaiseIntegrasjonJob {
	
	def blaiseMeldingInnService
	def blaiseEventInnService
	def synkroniseringService
	def intervjuObjektService
	def prosessLoggService
	def blaiseHentEventsService
	
	static triggers = {
		simple name: 'blaiseTrigger', startDelay: 90000, repeatInterval: 60000
		// repeatInterval var satt til 5 minutter, dvs 300000 millisekunder
	}

	static concurrent = false

	static group = "SivGroup"
	
	def gjorIngentingOmNattenMelding = "... gjor ingenting mellom kl 00:00 og kl 07:00"
	
	void execute() {
		
		// BLAISE TRIGGER SERVICE
		long start = System.currentTimeMillis()
		log.info "BlaiseTriggerJob starting..."
		
		if( DatoUtil.erDetNatt() ) {
			log.info gjorIngentingOmNattenMelding
		}
		else {
			blaiseEventInnService.behandleMeldingerBlaiseEventInn()
		}
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.BLAISE_TRIGGER_JOB)
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		log.info "BlaiseTriggerJob executed in " + total + " ms"

		// BLAISE HENT EVENT SERVICE
		start = System.currentTimeMillis()
		log.info "BlaiseHentEventsService starting..."

		blaiseHentEventsService.serviceMethod()

		prosessLoggService.registrerProsessKjoring(ProsessNavn.HENT_BLAISE_EVENTS_JOB)

		stop = System.currentTimeMillis()
		total = stop - start
		log.info "BlaiseHentEventsJob executed in " + total + " ms"

		
		// MELDING INN SERVICE
		start = System.currentTimeMillis()
		log.info "MeldingInnJob starting..."
		
		if( DatoUtil.erDetNatt() ) {
			log.info gjorIngentingOmNattenMelding
		}
		else {
			blaiseMeldingInnService.sjekkBlaiseMeldingInn()
		}
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.MELDING_INN_JOB)
		
		stop = System.currentTimeMillis()
		total = stop - start
		log.info "MeldingInnJob executed in " + total + " ms"
		
		// SEND MELDING UT SERVICE
		start = System.currentTimeMillis()
		log.info "SendMeldingUtJob starting..."
		
		if( DatoUtil.erDetNatt() ) {
			log.info gjorIngentingOmNattenMelding
		}
		else {
			synkroniseringService.sjekkMeldingUt()
		}
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.SEND_MELDING_UT_JOB)
		
		stop = System.currentTimeMillis()
		total = stop - start
		log.info "SendMeldingUtJob executed in " + total + " ms"
		
		
		// LAAS OPP IO SERVICE
		start = System.currentTimeMillis()
		log.info "LaasOppIoJob starting..."
		
		if( DatoUtil.erDetNatt() ) {
			log.info gjorIngentingOmNattenMelding
		}
		else {
			intervjuObjektService.sjekkLaastTidspunktIo()
		}
		
		prosessLoggService.registrerProsessKjoring(ProsessNavn.LAAS_OPP_IO_JOB)
		
		stop = System.currentTimeMillis()
		total = stop - start
		log.info "LaasOppIoJob executed in " + total + " ms"

		// HENT ENDRINGER I BLAISE 5.7
		start = System.currentTimeMillis()
		log.info "Hent endringer i kontaktopplysninger fra blaise 5.7 starting..."

		if( DatoUtil.erDetNatt() ) {
			log.info gjorIngentingOmNattenMelding
		}
		else {
			synkroniseringService.sjekkUtEndringer()
		}

		prosessLoggService.registrerProsessKjoring(ProsessNavn.HENT_BLAISE_ENDRINGER_JOB)
	}
}
