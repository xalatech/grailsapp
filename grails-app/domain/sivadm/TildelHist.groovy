package sivadm

class TildelHist {
	Intervjuer intervjuer
	Date dato
	
    static constraints = {
		intervjuer nullable: false
		dato nullable: false
	}
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'tildel_hist_seq'],  sqlType: 'integer'
	}
}