package sivadm

import util.DateUtil
import sil.SilMelding
import siv.type.ArbeidsType
import siv.type.TimeforingStatus

class Timeforing {
	
	ArbeidsType arbeidsType
	TimeforingStatus timeforingStatus
	Intervjuer intervjuer
	String produktNummer
	Date fra
	Date til
	SilMelding silMelding
	String redigertAv
	Date redigertDato
	
	static constraints = {
		intervjuer(nullable: true)
		redigertAv(nullable: true)
		redigertDato(nullable: true)
		timeforingStatus(nullable: true)
		silMelding(nullable: true)
		
		produktNummer blank: false, nullable: false
		
		fra nullable: false, matches: "\\d\\d:\\d\\d", validator: { value, command ->
			if(value) {
				Date til = command.til
				Date fra = command.fra
				
				TimeforingService timeforingService = new TimeforingService()
				
				def timeforinger = timeforingService.getTimeforingerForIntervjuer(command.intervjuer, fra)
				
				def melding
				
				timeforinger.each {
					Timeforing timeforing = it
					
					if( timeforing.id != command.id ) {
						if( value == timeforing.fra) {
							println "Her kræsjer det 1"
							melding = 'sivadm.error.timeregistrering.krasj'
						}
						
						if( value.after(timeforing.fra) && value.before(timeforing.til)) {
							println "Her kræsjer det 2"
							melding = 'sivadm.error.timeregistrering.krasj'
						}
					}
				}
				
				return melding
			}
		}
		
		til nullable: false, validator: { value, command ->
			if(value) {
				Date til = command.til
				Date fra = command.fra
				
				if(fra.after(til)) {
					return 'sivadm.error.timeregistrering.fra.til'
				}
				
				if(fra.equals(til)) {
					return 'sivadm.error.timeregistrering.fra.til.lik'
				}
				
				TimeforingService timeforingService = new TimeforingService()
				
				def timeforinger = timeforingService.getTimeforingerForIntervjuer (command.intervjuer, fra)
				
				def melding
				
				timeforinger.each {
					Timeforing timeforing = it
					if( timeforing.id != command.id ) {
						if( value == timeforing.til ) {
							melding = 'sivadm.error.timeregistrering.krasj'
						}
						
						if( value.after(timeforing.fra) && value.before(timeforing.til)) {
							melding = 'sivadm.error.timeregistrering.krasj'
						}
						
						if( fra.before(timeforing.fra) && value.after(timeforing.til)) {
							melding = 'sivadm.error.timeregistrering.krasj'
						}
					}
				}
				
				return melding
			}
		}
	}

	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'timeforing_seq'],  sqlType: 'integer'
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
		return "" + DateUtil.getTimeOnDate(fra) + " - " + DateUtil.getTimeOnDate(til)
	}
}