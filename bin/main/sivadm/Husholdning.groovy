package sivadm

class Husholdning {
	String navn
	Date fodselsDato
	String personKode
	String fodselsNummer
	Integer husholdNummer
	
    static constraints = {
		navn(nullable: false, blank: false, maxSize: 50)
		fodselsDato(nullable: true)
		personKode(nullable: true, blank: true, maxSize: 1, inList: ["1", "2", "3"])
		fodselsNummer(nullable: true, blank: true, size: 11..11)
		husholdNummer(nullable: true)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'husholdning_seq'],  sqlType: 'integer'
	}
	
	static belongsTo = IntervjuObjekt

	public String toString() {
		return navn
	}
	
	public void setNavn(String navn) {
		this.navn = navn ? navn.toUpperCase() : null	
	}
}
