package sivadm

import java.util.Date;

import siv.data.IntervjuerSystemKommando;
import siv.type.IntervjuerStatus;

class SystemKommandoService {

	static transactional = true
	
	public static String COMMA = ",";
	public static String DOUBLE_UNDERSCORE = "__";

	public void opprettSystemKommando( String filnavn, Long maksSekunder, String beskrivelse ) {
	}

	/**
	 * Legger til en intervjuer for en systemKommando
	 * 
	 * @param intervjuer
	 * @param systemKommando
	 */
	public void leggTilIntervjuereForSystemKommando(List<Intervjuer> intervjuere, SystemKommando systemKommando ) {
		intervjuere.each { Intervjuer intervjuer -> 
			leggTilIntervjuer(intervjuer, systemKommando)
		}
	}

	/**
	 * Legger til alle aktive intervjuere for en systemkommando
	 * 
	 * @param systemKommando
	 */
	public void leggTilAlleIntervjuereForSystemKommando( SystemKommando systemKommando ) {
		
		hentAlleIntervjuereSommIkkeErValgtForEnSystemKommando(systemKommando).each { Intervjuer intervjuer -> 
			leggTilIntervjuer(intervjuer, systemKommando)
		}
	}

	/**
	 * Henter alle intervjuere som er registrert for en systemKommando
	 * 
	 * @param systemKommando
	 * @return en liste med registrerte intervjuere for systemkommandoen
	 */
	public List<IntervjuerSystemKommando> hentAlleIntervjuereForEnSystemKommando(SystemKommando systemKommando) {

		List intervjuerSystemKommandoList = new ArrayList<IntervjuerSystemKommando>()

		List skIntervjuerList = SKIntervjuer.findAllBySystemKommando( systemKommando )

		skIntervjuerList.each { 
			IntervjuerSystemKommando intervjuerSystemKommando = new IntervjuerSystemKommando()
			
			intervjuerSystemKommando.initialer = it.intervjuer.initialer
			intervjuerSystemKommando.navn = it.intervjuer.navn
			intervjuerSystemKommando.intervjuerId = it.intervjuer.id
			intervjuerSystemKommando.suksess = it.suksess
			intervjuerSystemKommando.utfortDato = it.utfortDato
			intervjuerSystemKommando.systemKommandoId = it.systemKommando.id
			
			intervjuerSystemKommandoList.add(intervjuerSystemKommando)
		}

		return intervjuerSystemKommandoList
	}
	
	
	/**
	 * Henter alle intervjuere som ikke er valgt for en systemkommando
	 * 
	 * @param systemKommando
	 * @return en liste intervjuere som ikke er valgt
	 */
	public List<Intervjuer> hentAlleIntervjuereSommIkkeErValgtForEnSystemKommando(SystemKommando systemKommando) {
		
		List<IntervjuerSystemKommando> intervjuerSystemKommandoList = hentAlleIntervjuereForEnSystemKommando(systemKommando)
		
		def valgteIntervjuereIdList = intervjuerSystemKommandoList.collect {it.intervjuerId}
		
		def ikkeValgteIntervjuere = finnAktiveIntervjuere()
		
		Intervjuer.list().each {
			if( valgteIntervjuereIdList.contains(it.id) ) {
				ikkeValgteIntervjuere.remove(it)
			}
		}
		
		return ikkeValgteIntervjuere
	}


	/**
	 * Henter alle systemkommandoer som en intervjuer er satt opp til å være med på. Vi
	 * tar ikke med de som allerede er kjørt (suksess == null).
	 * 
	 * @param intervjuer
	 */
	public List<SystemKommando> hentAlleSystemKommandoerForIntervjuer( Intervjuer intervjuer ) {
		return SKIntervjuer.findAllByIntervjuerAndSuksessIsNull(intervjuer)?.collect { it.systemKommando }?.sort { it.redigertDato }
	}
	
	/**
	 * Returnerer systemkommandoer som en string for å sende den inn i en applet.
	 * 
	 * Format: [filnavn1]_[maksSekunder1],[filnavn2]_[maksSekunder2] osv.
	 * 
	 * @param intervjuer
	 * @return 
	 */
	public String hentAlleSystemKommandoerSomAppletString( Intervjuer intervjuer ) {
		List systemKommandoList = hentAlleSystemKommandoerForIntervjuer(intervjuer)
		
		String alleSystemKommandoer = null
		
		if( systemKommandoList && systemKommandoList.size() > 0) {
			StringBuffer skBuffer = new StringBuffer()
			
			systemKommandoList.each {
				skBuffer.append it.id + DOUBLE_UNDERSCORE + it.filnavn + DOUBLE_UNDERSCORE + it.maksSekunder
				skBuffer.append COMMA
			}
			
			// tar vekk komma på slutten
			skBuffer.deleteCharAt(skBuffer.length() - 1)
			
			alleSystemKommandoer = skBuffer.toString()
		}
		
		log.info alleSystemKommandoer
		
		return alleSystemKommandoer
	}

	
	/**
	 * Registrerer en systemkommando-kjøring for en intervjuer. Det kan være vellykket eller ikke.
	 * 
	 * @param intervjuer intervjueren som kjørte systemkommandoen
	 * @param systemKommando systemkommandoen som ble utført
	 * @param suksess ble systemkommandoen vellykket utført
	 */
	public void registrerSystemKommandoUtfortForIntervjuer( Intervjuer intervjuer, SystemKommando systemKommando, Boolean suksess ) {
		
		try {
			def systemKommandoIntervjuer = SKIntervjuer.findByIntervjuerAndSystemKommando(intervjuer, systemKommando)
			
			systemKommandoIntervjuer.suksess = suksess
			systemKommandoIntervjuer.setUtfortDato( now() )
			
			systemKommandoIntervjuer.save(failOnError: true)
		}
		catch (Exception e) {
			log.error(e.getMessage())	
		}
	}
	
	
	
	/**
	 * Fjerner en intervjuer fra systemkommando listen.
	 * 
	 * @param systemKommando
	 * @param intervjuer
	 */
	public void taVekkIntervjuer(SystemKommando systemKommando, Intervjuer intervjuer) {
		SKIntervjuer.findBySystemKommandoAndIntervjuer(systemKommando, intervjuer)?.delete()
	}
	
	/**
	 * Sletter alle intervjuere for en systemkommando
	 * 
	 * @param systemKommando
	 */
	public void taVekkAlleIntervjuere(SystemKommando systemKommando) {
		
		SKIntervjuer.findAllBySystemKommando( systemKommando ).each {
			it.delete()
		}
	}
	
	/**
	 * Sletter en systemKommando (og alle koblinger mot intervjuere)
	 * 
	 * @param systemKommando
	 */
	public void slettSystemKommando( SystemKommando systemKommando ) {
		taVekkAlleIntervjuere(systemKommando)	
		systemKommando.delete()
	}
	
	/**
	 * Henter alle aktive intervjuere.
	 * 
	 * @return
	 */
	public List<Intervjuer> finnAktiveIntervjuere() {
		return Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV)
	}

	/**
	 * Legger til en intervjuer for en systemKommando
	 *
	 * @param intervjuer
	 * @param systemKommando
	 */
	private void leggTilIntervjuer(Intervjuer intervjuer, SystemKommando systemKommando) {

		SKIntervjuer systemKommandoIntervjuer = new SKIntervjuer()

		systemKommandoIntervjuer.intervjuer = intervjuer
		systemKommandoIntervjuer.systemKommando = systemKommando
		systemKommandoIntervjuer.suksess = null
		systemKommandoIntervjuer.save(failOnError: true)
	}
	
	/**
	* Hent dato
	* @return
	*/
   private Date now() {
	   return new Date()
   }
}
