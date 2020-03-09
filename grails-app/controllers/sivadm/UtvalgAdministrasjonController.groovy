package sivadm

import grails.plugin.springsecurity.annotation.Secured
import siv.util.SorteringsUtil


@Secured(['ROLE_ADMIN', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER'])
class UtvalgAdministrasjonController {
	
	def utvalgImportService
	def brukerService
	
	
	def selectSkjema = {
		List skjemaer = SorteringsUtil.sorterPaaOppstartsDatoSkjemaSomIkkeErAvsluttet(Skjema.list())
		def utvalgImportInstanceList = UtvalgImport.list().sort { it.importDato }.reverse()
			
		['skjemaer': skjemaer, 'utvalgImportInstanceList': utvalgImportInstanceList]
	}
	
	
	def saveUtvalg = {			
		def importFil = request.getFile("file")
		def reader = new BufferedReader(new InputStreamReader(importFil.getInputStream(), "cp1252"))
		
		String utvalgTekst = reader.getText()
		
		try {
			def importertAv = brukerService.getCurrentUserName()
			
			// oppretter en bakgrunnsjobb for innlasting av utvalg
			UtvalgImportJob.triggerNow([utvalgTekst: utvalgTekst, importertAv: importertAv, skjemaId: params.skjema])
			
			flash.message = "Innlasting startet. Klikk på knappen Oppdater liste for å se status. "
		}
		catch (Exception e) {
			flash.errorMessage = e.getMessage()
		}
				
		redirect(action:"selectSkjema")
	}
	
	
	def downloadUtvalg = {	
		UtvalgImport utvalgImport = UtvalgImport.get( params.id )
		String utvalgText = utvalgImportService.getUtvalgAsString(utvalgImport)
		
		byte[] bytes = utvalgText.getBytes("cp1252");
		
		response.setContentType "application/octet-stream"
		response.setHeader "Content-disposition", "filename=eksport.txt"
		response.outputStream << bytes
	}
	
	
	def skrivUtvalgTilFil = {
		UtvalgImport utvalgImport = UtvalgImport.get( params.id )
		// Linja under må da være totalt unødvendig?
		// String utvalgText = utvalgImportService.getUtvalgAsString(utvalgImport)
		
		def filPath = grailsApplication.config.utvalg.eksport.fil.path

		def filNavn = utvalgImport.skjema.skjemaKortNavn + "_" + System.currentTimeMillis()
		
		def fil = filPath + filNavn + ".txt"
		
		UtvalgEksportJob.triggerNow([utvalgImportId: params.id, fil: fil ])
		
		flash.message = "Jobben med å eksportere utvalgsfil er starta. Fila vil bli tilgjengelig her om en liten stund: " + fil
		
		redirect( action:"selectSkjema")
	}
	

	def deleteUtvalg = {
		UtvalgImport utvalgImport = UtvalgImport.get( params.id )
		
		if( utvalgImport != null) {
			UtvalgSlettJob.triggerNow( [ utvalgImportId: utvalgImport.id ] )
		}
		
		flash.message = "Jobben med å slette utvalg og tilhørende intervjuobjekter er startet. Dette kan ta lang tid hvis utvalget er stort. Intervjuobjekter blir slettet gradvis, og man kan sjekke hvor mange som er igjen ved å gå inn på intervjuobjekt og søke etter intervjuobjekter for aktuelt skjema. "
		
		redirect( action:"selectSkjema")
	}
	
	def godkjennUtvalg = {
		UtvalgImport utvalgImport = UtvalgImport.get( params.id )
		flash.message = "Jobben med å godkjenne utvalget er ikke startet, så gå bare videre. "
		redirect( action:"selectSkjema")
	}
}
