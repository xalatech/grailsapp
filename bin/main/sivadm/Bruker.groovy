package sivadm

class Bruker {

	String navn
	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username nullable: false, blank: false, unique: true, minSize: 3, maxSize: 3
		password nullable: false, blank: false
		navn nullable: true, blank: true
	}

	static mapping = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'bruker_seq'],  sqlType: 'integer'
		password column: '`password`'
	}
		
	public String toString() {
		if(navn) {
			return navn + "(" + username + ")"
		}	
		else {
			return username	
		}
	}

	Set<Rolle> getAuthorities() {
		BrukerRolle.findAllByBruker(this).collect { it.rolle } as Set
	}
	
	Set<Rolle> setAuthorities(List roller) {
		def brukerRolleListe = BrukerRolle.findAllByBruker(this)
		
		brukerRolleListe.each { br ->
			try {
				br.delete(flush: true)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				// TODO: Hva skal skje her?
				//flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bruker.label', default: 'Bruker'), params.id])}"
				//redirect(action: "list")
			}
		}
		
		
		roller.each {
			new BrukerRolle(bruker: this, rolle: it).save(failOnError: true, flush: true)
		}
				
		BrukerRolle.findAllByBruker(this).collect { it.rolle } as Set
	}
}