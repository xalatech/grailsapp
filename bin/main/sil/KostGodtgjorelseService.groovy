package sil

class KostGodtgjorelseService {

    static transactional = true

    def hentKostGodtgjorelser(max, offset) {
		
		// tmp test data
//		KostGodtgjorelse k1 = new KostGodtgjorelse()
//		k1.behandlet = false
//		k1.initialer = "spo"
//		k1.intervjuerNummer = 1234
//		k1.klynge = "sør"
//		k1.kostGodtDato = new Date()
//		k1.navn = "Pål Søreng"
//		k1.godkjent = new Date()
//		k1.godkjentAv = "spo"
//		k1.produktNummer = 1234
//		k1.utleggType = "Over 12 timer"
//		k1.save(failOnError: true)
		
		def kostGodtGjorelser
		
		if(!max || !offset) {
			kostGodtGjorelser = KostGodtgjorelse.list(offset:0, max:500, sort:"godkjent", order:"desc")
		}
		else {
			kostGodtGjorelser = KostGodtgjorelse.list(offset:offset, max:max) 
		}
		
		return kostGodtGjorelser
    }
	
	def hentAntallKostGodtgjorelser() {
		return KostGodtgjorelse.count()
	}
}
