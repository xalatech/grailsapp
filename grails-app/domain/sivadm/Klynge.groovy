package sivadm

class Klynge {
		
	String klyngeNavn
	String klyngeSjef
	String epost
	String beskrivelse
	
	static hasMany = [intervjuere:Intervjuer, kommuner:Kommune]
	
	static constraints = {
		klyngeNavn(blank: false)
		klyngeSjef(blank: true, nullable: true)
		epost(blank: true, nullable: true)
		beskrivelse(blank: true, nullable: true)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'klynge_seq'],  sqlType: 'integer'
	}
	
	public String toString() {
		return klyngeNavn
	}
	
	public List<String> getKommuneNummerList() {
		
		List kommuneNummerList = new ArrayList<String>()
		
		kommuner.each { kommune ->
			kommuneNummerList.add (kommune.kommuneNummer)
		}
		
		return kommuneNummerList
	}
}