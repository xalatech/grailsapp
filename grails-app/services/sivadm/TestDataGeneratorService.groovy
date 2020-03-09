package sivadm

import util.TimeUtil;
import siv.type.ArbeidsType;
import siv.type.TimeforingStatus;

class TestDataGeneratorService {

    static transactional = true

    
	public void genererTimeforinger() {
		
		Calendar calFra = Calendar.getInstance()
		Calendar calTil = Calendar.getInstance()
		calTil.add(Calendar.MINUTE, 30)
		
		for( int i=0; i<1000; i++) {
			calFra.add(Calendar.DAY_OF_YEAR, -1)
			calTil.add(Calendar.DAY_OF_YEAR, -1)
			
			Timeforing timeforing = new Timeforing()
			timeforing.arbeidsType = ArbeidsType.INTERVJUE
			timeforing.fra = calFra.getTime()
			timeforing.til = calTil.getTime()
			timeforing.produktNummer = "5776-0"
			timeforing.timeforingStatus = TimeforingStatus.SENDT_INN
			timeforing.intervjuer = Intervjuer.findByInitialer("si4")
			
			timeforing.save(failOnError: true)
			
			println "Created timeforing: " + i
		}
		
	}
}
