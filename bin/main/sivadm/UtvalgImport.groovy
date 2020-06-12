package sivadm

class UtvalgImport {

	Date importDato
	int antallFil
	int antallImportert
	String importertAv
	Skjema skjema
	String melding
	
    static constraints = {
		melding(nullable: true, size: 0..255)
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'utvalg_import_seq'],  sqlType: 'integer'
	}
}