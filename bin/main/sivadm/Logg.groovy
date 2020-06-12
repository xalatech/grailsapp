package sivadm

import siv.type.LoggType;

class Logg {

	String tittel
	LoggType loggType
	Date sistOppdatert	
	Date opprettet = new Date()
	boolean ferdig = false
	String endretAv
	
	static hasMany = [loggLinjer: LoggLinje]
          
	static constraints = {
		tittel (blank:false)
		endretAv (nullable:true, blank: true)
	}
	
	static mapping = {
		sort opprettet: "desc"
		id column: 'ID', generator: 'sequence',  params:[sequence:'logg_seq'],  sqlType: 'integer'
	}
}
