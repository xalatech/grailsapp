package sil

import grails.core.GrailsApplication
import sil.type.SapFilStatusType
import siv.type.LoggType

class OpprettSapFilerJob {
	
	def concurrent = false
	
	def config = GrailsApplication.getConfig()
	
	def loggService
	def sapFilService
	def mailService
	
	static triggers = {
	}
	
	def execute(context) {

		long start = System.currentTimeMillis()
		log.info "OpprettSapFilerJob starting..."
		
		def logg
		
		def bruker
		
		try	{
			bruker = context.mergedJobDataMap.get('bruker')
			
			logg = loggService.startProsess(LoggType.SIL_BAKGRUNN_PROSESS, "Eksporterer SAP-filer", "Starter eksport", bruker)
			
			def sapFilListe
			
			try {
				sapFilListe = sapFilService.skrivGodkjenteKravTilSapFiler()
			}
			catch( Exception e ) {
				loggService.stoppProsess(logg, "Prosessen med å skrive SAP-filer har stanset på grunn av en feil: " + e.getMessage(), true)
				return
			}
			
			if(!sapFilListe) {
				loggService.stoppProsess(logg, "Eksport ferdig, fant ingen krav å skrive SAP-filer for.", false)
				return
			}
			
			int cnt = 0
			int linjer = 0
			int feiletKravCnt = 0
			int feiletCnt = 0
			
			sapFilListe.each {
				if(it.status == SapFilStatusType.OK) {
					cnt += it.antallKrav
					linjer += it.antallLinjer
				}
				else {
					feiletCnt++
					feiletKravCnt += it.antallKrav
				}
			}
			
			boolean feiletAlle = false
			
			def melding
			
			if(feiletCnt == 0) {
				melding = "Har skrevet ${sapFilListe.size()} filer til SAP. Tilsammen ${cnt} krav fordelt på ${linjer} linjer"
			}
			else if(sapFilListe.size() == feiletCnt) {
				feiletAlle = true
				melding = "Har forsøkt å skrive ${feiletCnt} filer til SAP, men noe gikk galt, vennligst se under SAP filer for mer informasjon"
			}
			else {
				melding = "Har skrevet ${(sapFilListe.size()-feiletCnt)} filer til SAP. Tilsammen ${cnt} krav fordelt på ${linjer} linjer"
			}
			
			if(!feiletAlle) {				
				String mottaker = bruker + '@ssb.no'
				String avsender = config.avsender.sivadmin.epost
				
				log.info 'OpprettSapFilerJob -> Current user email: ' + mottaker

				String emne = "SAP filer generert"
				String bodyStr = "Har skrevet ${sapFilListe.size()} filer til SAP. Tilsammen ${cnt} krav fordelt på ${linjer} linjer"
				
				if(feiletCnt > 0) {
					bodyStr = "Har skrevet ${(sapFilListe.size()-feiletCnt)} filer til SAP. Tilsammen ${cnt} krav fordelt på ${linjer} linjer"
				}
				
				try {
					mailService.sendMail {
						to mottaker
						from avsender
						subject emne
						body bodyStr
					}
				}
				catch(Exception e) {
					log.error("Kunne ikke sende epost om at det er generert SAP-filer: " + e.getMessage())
				}
			}
			
			loggService.stoppProsess(logg, melding, false)
		}
		catch(Exception e) {
			log.error( e.getMessage() )
			loggService.stoppProsess(logg, "Importer krav jobb feilet: " + e.getMessage(), true)
		}
		
		long stop = System.currentTimeMillis()
		long total = stop - start
		
		log.info "OpprettSapFilerJob executed in " + total + " ms"
	}
}