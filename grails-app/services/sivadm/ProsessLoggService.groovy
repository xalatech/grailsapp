package sivadm

import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;

import siv.type.ProsessNavn;

class ProsessLoggService {

    boolean transactional = true
	
	def registrerProsessKjoring(ProsessNavn prosessNavn) {
		
		def prosessLogg
		
		prosessLogg = ProsessLogg.findByProsessNavn(prosessNavn)
		
		if( !prosessLogg ) {
			prosessLogg = new ProsessLogg();
		}
		
		prosessLogg.prosessNavn = prosessNavn
		prosessLogg.tidStart = new Date()	
		prosessLogg.suksess = true
		prosessLogg.save(failOnError: true, flush: true)
	}
	
	def finnProsessKjoringFraDato( ProsessNavn prosessNavn, Date fraDato )
	{
		def c = ProsessLogg.createCriteria()
		
		def list = c {
			eq("prosessNavn", prosessNavn)
			gt("tidStart", fraDato)
		}
		
		if(list)
			return list.get(0)
		else 
			return null 
	}	
}
