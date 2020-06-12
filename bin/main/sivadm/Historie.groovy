package sivadm

class Historie {

	String resultat
	Integer historieNummer
	// IntervjuObjekt intervjuObjekt
	
    static constraints = {
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'historie_seq'],  sqlType: 'integer'
	}
	
	static belongsTo = IntervjuObjekt
	
	public String toString() {
		return "nr: " + historieNummer + " resultat:" + resultat
	}
	
}
