package sil

import sil.type.KravType
import siv.type.TransportMiddel

class AutomatiskKontroll {
	String kontrollNavn
	String beskrivelse
	Long grenseVerdi
	KravType kravType
	TransportMiddel transportmiddel
	String produktNummer
	String feilmelding
	Boolean returnereTilIntervjuerVedIkkeBestatt = false
	Date gyldigFra
	Date gyldigTil
	Date datoEndret
	String endretAv
	
    static constraints = {
		kontrollNavn(blank: false, nullable: false)
		beskrivelse(nullable: true, blank: true)
		feilmelding(blank: false, nullable: false)
		kravType(nullable: false)
		produktNummer(nullable: true, blank: true)
		grenseVerdi(nullable: false)
		gyldigTil(nullable: true)
		endretAv(nullable: true, blank: true)
		
		transportmiddel nullable: true, validator: { value, command ->
			def melding
			
			if(!value && command.kravType == KravType.K) {
				melding = 'sil.automatisk.kontroll.transportmiddel'
			}
			else if(value == '') {
				melding = 'lkasfdlkjasdflkjasfd'
			}
			
			return melding
		}
		
		gyldigFra nullable: true, validator: { value, command ->
			def melding
			
			if(value && command.gyldigTil) {
				if(value.getTime() > command.gyldigTil.getTime()) {
					melding = 'sil.automatisk.kontroll.dato.feil'
				}
			}
			
			return melding
		}
    }
		
	static mapping = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'auto_kont_seq'],  sqlType: 'integer'
		returnereTilIntervjuerVedIkkeBestatt column: 'returnere_til_intervjuer'
	}
		
	def beforeInsert = {
		datoEndret = new Date()
	}
	
	def beforeUpdate = {
		datoEndret = new Date()
	}
	
	public String toString() {
		return kontrollNavn
	}
}