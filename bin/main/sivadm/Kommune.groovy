package sivadm

class Kommune {

	String kommuneNummer
	String kommuneNavn
	
    static constraints = {
		kommuneNummer(unique: true, nullable: false, blank: false, size: 4..4, matches: "\\d\\d\\d\\d")
		kommuneNavn(nullable: false, blank: false)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'kommune_seq'],  sqlType: 'integer'
	}
	
	public String toString() {
		return kommuneNummer + " " + kommuneNavn 
	}
}
