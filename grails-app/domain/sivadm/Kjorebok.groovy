package sivadm

import util.DateUtil
import siv.type.*
import sil.*

class Kjorebok {
	Intervjuer intervjuer
	TransportMiddel transportmiddel
	TimeforingStatus timeforingStatus
	Date fraTidspunkt
	Date tilTidspunkt
	String fraAdresse
	String tilAdresse
	String fraPoststed
	String tilPoststed
	String produktNummer
	Long kjorteKilometer = 0
	String redigertAv
	Date redigertDato
	String merknad // Brukes (og er obligatorisk) naar intervjuobjekt er null
	Boolean kjorteHjem = false
	IntervjuObjekt intervjuobjekt
	SilMelding silMelding
	Long antallPassasjerer = 0
	Timeforing timeforing
	Utlegg utleggBom
	Utlegg utleggParkering
	Utlegg utleggFerge
	Utlegg utleggBelop
		
	static constraints = {
		kjorteKilometer max: 2550L
		antallPassasjerer max: 9L
		utleggBelop nullable: true
		utleggBom nullable: true
		utleggParkering nullable: true
		utleggFerge nullable: true
		timeforing nullable: false
		intervjuer nullable: true
		intervjuobjekt nullable: true
		redigertAv nullable: true
		redigertDato nullable: true
		timeforingStatus nullable: true
		merknad nullable: true
		
		fraAdresse nullable: true
		tilAdresse nullable: true
		fraPoststed blank: false, nullable: false
		tilPoststed blank: false, nullable: false
		produktNummer blank: false, nullable: false
		
		silMelding(nullable: true)
		
		fraTidspunkt nullable: false, validator: { value, command ->
			if(value) {
				Date til = command.tilTidspunkt
				Date fra = command.fraTidspunkt
				
				if(fra.after(til)) {
					return 'sivadm.error.kjorebok.fra.til'
				}
				
				TimeforingService timeforingService = new TimeforingService()
				
				def kjoreboker = timeforingService.getKjorebokForIntervjuer(command.intervjuer, fra)
				
				def melding
				
				kjoreboker.each {
					Kjorebok kjorebok = it
					
					if( kjorebok.id != command.id)
					{
						if( value == kjorebok.fraTidspunkt) {
							melding = 'sivadm.error.kjorebok.krasj'
						}
						else if( value.after(kjorebok.fraTidspunkt) && value.before(kjorebok.tilTidspunkt)) {
							melding = 'sivadm.error.kjorebok.krasj'
						}
					}
				}
				
				return melding
			}
		}
		
		tilTidspunkt nullable: false, validator: { value, command ->
			if(value) {
				Date til = command.tilTidspunkt
				Date fra = command.fraTidspunkt
				
				if(fra.after(til)) {
					return 'sivadm.error.kjorebok.fra.til'
				}
				
				if(fra.equals(til)) {
					return 'sivadm.error.timeregistrering.fra.til.lik'
				}
				
				TimeforingService timeforingService = new TimeforingService()
				
				def kjoreboker = timeforingService.getKjorebokForIntervjuer(command.intervjuer, fra)
				
				def melding
				
				kjoreboker.each {
					Kjorebok kjorebok = it
					
					if( kjorebok.id != command.id)
					{
						if( value == kjorebok.tilTidspunkt) {
							melding = 'sivadm.error.timeregistrering.krasj'
						}
						
						if( value.after(kjorebok.fraTidspunkt) && value.before(kjorebok.tilTidspunkt)) {
							melding = 'sivadm.error.kjorebok.krasj'
						}
						
						if( fra.before(kjorebok.fraTidspunkt) && value.after(kjorebok.tilTidspunkt)) {
							melding = 'sivadm.error.kjorebok.krasj'
						}
					}
					
				}
				
				return melding
			}
		}
		
		antallPassasjerer validator: { value, command ->
			if(value && value > 0) {
				def melding
				
				if(command.transportmiddel != TransportMiddel.EGEN_BIL) {
					melding = 'sivadm.error.kjorebok.passasjerer.egen.bil'
				}
				
				return melding
			}
		}
		
		kjorteKilometer validator: { value, command ->
			if(value && value > 0) {
				def melding
				
				if(command.transportmiddel == TransportMiddel.LEIEBIL
					|| command.transportmiddel == TransportMiddel.BUSS_TRIKK
					|| command.transportmiddel == TransportMiddel.TOG
					|| command.transportmiddel == TransportMiddel.FERJE
					|| command.transportmiddel == TransportMiddel.TAXI) {
					
					melding = 'sivadm.error.kjorebok.kilometer'
				}
				
				return melding
			}
		}
		
		transportmiddel validator: { value, command ->
			if(value && command.kjorteKilometer && command.kjorteKilometer > 0) {
				def melding
								
				if(value == TransportMiddel.LEIEBIL
					|| value == TransportMiddel.BUSS_TRIKK
					|| value == TransportMiddel.TOG
					|| value == TransportMiddel.FERJE
					|| value == TransportMiddel.TAXI) {
					
					melding = 'sivadm.error.kjorebok.kilometer'
				}
				
				return melding
			}
		}
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'kjorebok_seq'],  sqlType: 'integer'
		sort fraTidspunkt:"desc"
		timeforing cascade: "all"
		utleggBelop cascade: "all"
		utleggBom cascade: "all"
		utleggParkering cascade: "all"
		utleggFerge cascade: "all"
	}
	
	public boolean godkjent() {
		if( timeforingStatus == TimeforingStatus.GODKJENT ) {
			return true
		}
		else {
			return false
		}
	}
	
	public boolean avvist() {
		if( timeforingStatus == TimeforingStatus.AVVIST ) {
			return true
		}
		else {
			return false
		}
	}
		
	public String toString() {
		if(erKm()) {
			return kjorteKilometer + " km"
		}
		
		if(this.transportmiddel == TransportMiddel.FERJE) {
			return this.utleggFerge?.belop + " kr"
		}
		else if(this.transportmiddel == TransportMiddel.LEIEBIL || this.transportmiddel == TransportMiddel.LEIEBIL) {
			return "" + DateUtil.getTimeOnDate(fraTidspunkt) + " - " + DateUtil.getTimeOnDate(tilTidspunkt)
		}
		else {
			return this.utleggBelop?.belop + " kr"
		}
	}
	
	/**
	 * @return Hvis transportmiddel for denne kjøreboken er km-basert(Egen bil,
	 * båt, motorsykkel, snøscooter og moped/sykkel) true, hvis ikke false
	 */
	private boolean erKm() {
		if(this.transportmiddel && this.transportmiddel.isKm()) {
			return true
		}
		
		return false
	}
			
	private Kjorebok lagRetur(String fraTid, String tilTid) {
		Kjorebok k = new Kjorebok()
		k.intervjuer = this.intervjuer
		k.transportmiddel = this.transportmiddel
		k.timeforingStatus = this.timeforingStatus
		k.fraAdresse = this.tilAdresse
		k.tilAdresse = this.fraAdresse
		k.fraPoststed = this.tilPoststed
		k.tilPoststed = this.fraPoststed
		k.produktNummer = this.produktNummer
		k.kjorteKilometer = this.kjorteKilometer
		k.redigertAv = this.redigertAv
		k.redigertDato = this.redigertDato
		k.merknad = this.merknad
		k.antallPassasjerer = this.antallPassasjerer
		
		k.fraTidspunkt = DateUtil.getDateWithTime(this.fraTidspunkt, fraTid)
		k.tilTidspunkt = DateUtil.getDateWithTime(this.tilTidspunkt, tilTid)
		
		return k
	}
	
	private Timeforing opprettTimeforing() {
		Timeforing time = new Timeforing()
		
		time.fra = this.fraTidspunkt
		time.til = this.tilTidspunkt
		time.arbeidsType = ArbeidsType.REISE
		time.intervjuer = this.intervjuer
		time.produktNummer = this.produktNummer
		time.timeforingStatus = TimeforingStatus.IKKE_GODKJENT
				
		return time
	}
}
