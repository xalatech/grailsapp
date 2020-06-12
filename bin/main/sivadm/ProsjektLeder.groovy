package sivadm

class ProsjektLeder {
	
	String navn
	String initialer
	String epost
	
	static constraints = {
		navn(blank: false, nullable: false)
		initialer(blank: false, nullable: false, maxSize: 3, minSize: 3)
		epost(blank: false, nullable: false)
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'prosjekt_leder_seq'],  sqlType: 'integer'
	}
	
	
	
	public String toString( ) {
		return navn + " (" + initialer + ")";
	}
}