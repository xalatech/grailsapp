package sivadm

class BrukerService {

	def springSecurityService
	
	public String getCurrentUserName() {
		if(!springSecurityService.isLoggedIn()) {
			return null
		}
		else {
			return springSecurityService.authentication.name
		}
	}

	public String getCurrentUserEmail() {
		String curretnUser = this.getCurrentUserName()
		if (curretnUser==null){
			return null
		}
		return curretnUser + '@ssb.no'
	}

	public boolean intervjuerErIdentiskMedInnloggaBruker(Intervjuer intervjuer) {
		def innloggaIntervjuer = Intervjuer.findByInitialer(getCurrentUserName())
		return (intervjuer.intervjuerNummer == innloggaIntervjuer.intervjuerNummer)
	}
	
	
	public boolean kanBrukerEndreLonnsRecord(Intervjuer intervjuer) {
		boolean kanEndre = false
		
		// Sjekker først om bruker også eier recorden. I så fall er alt ok
		def innloggaBruker = getCurrentUserName()
		kanEndre = (intervjuer.initialer == innloggaBruker)

		// Ny sjanse: Admin-brukere kan også endre andres records
		return (kanEndre || erAdminBruker())
	}
	
	
	public void oppdaterBrukerHvisIntervjuerInitialerErEndret(Intervjuer intervjuer) {
		
		if( intervjuer.isDirty('initialer')) {
			
			def forrigeInitialer = intervjuer.getPersistentValue('initialer')
			
			def bruker = Bruker.findByUsername( forrigeInitialer )
			
			if( bruker ) {
				bruker.username = intervjuer.initialer
				bruker.navn = intervjuer.navn
				bruker.save()
			}
		}
	}
	
	
	/**
	 * Sjekker om innlogget bruker er admin eller ikke
	 * 
	 * @return true hvis innlogget bruker er admin
	 */
	public boolean erAdminBruker() {
		boolean erAdmin = false
		
		def roller = springSecurityService.getAuthentication().getAuthorities()
		
		roller.each { 			
			if( it == 'ROLE_SIL' || it == 'ROLE_ADMIN') {
				erAdmin = true
			}
		}
		
		return erAdmin
	}
	
	/**
	 * Sjekker om innlogget bruker er sil-bruker eller ikke
	 * 
	 * @return true hvis innlogget bruker er sil-bruker
	 */
	public boolean erSILBruker() {
		boolean erSIL = false
		
		def roller = springSecurityService.getAuthentication().getAuthorities()
		
		roller.each { 			
			if( it == 'ROLE_SIL') {
				erSIL = true
			}
		}
		
		return erSIL
	}
	
}