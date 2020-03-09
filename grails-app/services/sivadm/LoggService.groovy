package sivadm

import siv.type.LoggType;

class LoggService {

    
	static transactional = true
	    
	
	public Logg startProsess(LoggType loggType, String tittel, String linje, String brukerInitialer) {
		LoggLinje loggLinje = new LoggLinje(linje: linje)
		
		Logg logg = new Logg(loggType: loggType, tittel: tittel, endretAv: brukerInitialer, sistOppdatert: new Date())
		logg.addToLoggLinjer(loggLinje)
		logg.save(failOnError: true, flush: true)
		
		return logg	
	}
	
	
	public void stoppProsess(Logg logg, String linje, boolean feil) {
		LoggLinje loggLinje = new LoggLinje(linje: linje, feil: feil)
		logg.addToLoggLinjer(loggLinje)
		
		logg.ferdig = true
		logg.sistOppdatert = new Date()
		logg.save(failOnError: true, flush: true)
	}
	
	
	public boolean sjekkOmProsessErAktiv(LoggType loggType) {
		def logg = Logg.findByLoggType(loggType, [max: 1, sort: "opprettet", order: "desc"] )
		
		if(logg != null && logg.ferdig == false) {
			return true
		}
		else {
			return false
		}
	}
}
