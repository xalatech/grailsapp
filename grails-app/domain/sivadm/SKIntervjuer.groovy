package sivadm


/**
 * Kobling mellom systemkommando og intervjuer
 * @author spo
 *
 */
class SKIntervjuer {

	SystemKommando systemKommando
	Intervjuer intervjuer
	
	Date utfortDato
	Boolean suksess
	
    static constraints = {
		utfortDato(nullable: true)
		suksess(nullable: true)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'sk_intervjuer_seq'],  sqlType: 'integer'
	}
	
}
