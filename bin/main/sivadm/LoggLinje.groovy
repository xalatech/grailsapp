package sivadm

import java.util.Date;

class LoggLinje {

    String linje
	boolean feil = false	
	Date opprettet = new Date()
	
	static belongsTo = [logg:Logg]
	                    
	static constraints = {
		linje blank:false, maxSize: 10000
		logg nullable:true
	}
	
	static mapping = {
		sort opprettet: "asc"
		id column: 'ID', generator: 'sequence',  params:[sequence:'logg_linje_seq'],  sqlType: 'integer'
	}
	
	public String toString() {
		return linje + "(" + opprettet + ")"
	}
}
