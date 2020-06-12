package sivadm

class IntHist {

	Intervjuer intervjuer
	Integer intervjuStatus
	Date registrertDato
	IntervjuObjekt intervjuObjekt
	
	static mapping = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'inthist_seq'],  sqlType: 'integer'
	}
	
    static constraints = {
    }
}
