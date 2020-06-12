package sivadm

class ProsjektDeltager {

	Prosjekt prosjekt
	String deltagerInitialer
	String deltagerNavn
	String deltagerEpost
	
    static constraints = {
		deltagerInitialer(blank: false, nullable: false, maxSize: 3, minSize: 3)
		deltagerNavn(blank: false, nullable: false, maxSize: 255)
		deltagerEpost(blank: true, nullable: true, maxSize: 255)
	}

	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'prosjekt_deltager_seq'],  sqlType: 'integer'
	}
		
	public String toString() {
		return deltagerNavn
	}
}