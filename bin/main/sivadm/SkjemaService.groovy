package sivadm

class SkjemaService {

    boolean transactional = true

	public List finnAlleSortertPaaOppstart() {
		def skjemaList = Skjema.list()
		return skjemaList.sort{it.oppstartDataInnsamling}.reverse()
	}
	
		
	public Skjema findByPeriode(Periode periode) {
		return periode?.skjema
	}
	
	public Skjema findByPeriode(Long id) {
		def periode = Periode.get(id)
		def skjema = periode?.skjema 
		return skjema
	}
	
	public Skjema findBySkjemaVersjon(SkjemaVersjon skjemaVersjon) {
		if(!skjemaVersjon) {
			return null
		}
		
		def c = Skjema.createCriteria()
		
		def skjemaList = c {
			skjemaVersjoner {
				eq("id", skjemaVersjon.id)
			}
		}
		
		return skjemaList?.size() == 1 ? skjemaList.get(0) : null
	}
	
	public Skjema findBySkjemaVersjonId(Long skjemaVersjonId) {
		if(!skjemaVersjonId) {
			return null
		}
		
		def c = Skjema.createCriteria()
		
		def skjemaList = c {
			skjemaVersjoner {
				eq("id", skjemaVersjonId)
			}
		}
		
		return skjemaList?.size() == 1 ? skjemaList.get(0) : null
	}
	
	public Long findLatestSkjemaVersjonsNummer(Skjema skjema) {
		if( skjema == null ) {
			return null
		}
		
		def skjemaVersjoner = skjema?.skjemaVersjoner
		
		Long latestVersjonsNummer = 1L
		
		skjemaVersjoner.each {
			if( it.versjonsNummer > latestVersjonsNummer ) {
				latestVersjonsNummer = it.versjonsNummer
			}
		}
		
		return latestVersjonsNummer
	}
}