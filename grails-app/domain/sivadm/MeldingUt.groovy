package sivadm

import siv.type.MeldingType;

class MeldingUt {

	Date tidRegistrert
	Date tidSendt
	Long intervjuObjektId
	String intervjuObjektNummer
	String skjemaKortNavn
	MeldingType meldingType
	String melding
	Boolean sendtOk
	Integer antallForsok
	String sendtAv
	Boolean deadLetter
	String responseText
	String feilType
	Boolean deaktivert
	
	// TODO add meldingsmottager
	
    static constraints = {
		melding(maxSize: 2000)
		responseText(maxSize: 512)
		tidSendt nullable: true
		deadLetter nullable: true
		responseText nullable: true
		feilType nullable: true
		deaktivert nullable: true
		sendtAv nullable: true
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'melding_seq'],  sqlType: 'integer'
		deaktivert index: 'mld_ut_deakt_indeks'
		sendtOk index: 'mld_ut_sendt_indeks'
		intervjuObjektId index: 'mld_ut_ioid_indeks'
	}
}