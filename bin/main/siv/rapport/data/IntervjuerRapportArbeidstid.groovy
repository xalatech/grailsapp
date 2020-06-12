package siv.rapport.data

import java.util.Date;

class IntervjuerRapportArbeidstid {
	Date dato
	String arbeidstype
	String produktNummer
	String navn
	Long timer = 0
	Long minutter = 0
	Integer antKm
	Double belop
	

	static constraints = {
		dato nullable: true
		arbeidstype nullable: true
		produktNummer nullable: true
		navn nullable: true
		timer nullable: true
		minutter nullable: true
		antKm nullable: true
		belop nullable: true
	}

	
	/**
	 * Sjekker at minutter ikke er over 59, og hvis så gjør om til timer, slik at
	 * timer og minutter blir korrekt.
	 * @return 
	 */
	IntervjuerRapportArbeidstid sjekkTid() {
		if(this.minutter > 59L) {
			this.timer += this.minutter/60
			this.minutter = this.minutter%60
		}
		return this
	}
}
