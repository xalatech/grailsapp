package sivadm

import java.util.Date;

import siv.type.MeldingInnType;
import siv.type.MeldingType;

class MeldingInn {

	Date tidRegistrert
	Long intervjuObjektId
	String intervjuObjektNummer
	String skjemaKortNavn
	MeldingInnType meldingInnType
	String melding
	String kommentar
	Integer intervjuStatus
	Integer dayBatchKode
	Boolean mottattOk
	Integer antallForsok
	String sendtAv
	Boolean deadLetter
	String responseText
	String feilType
	Boolean deaktivert
	String internStatus
	
    static constraints = {
		melding(nullable: true, maxSize: 2000)
		deadLetter nullable: true
		responseText nullable: true
		feilType nullable: true
		deaktivert nullable: true
		intervjuObjektId nullable: false
		intervjuObjektNummer nullable: true
		skjemaKortNavn nullable: true
		meldingInnType nullable: false
		mottattOk nullable: true
		antallForsok nullable: true
		sendtAv nullable: true
		kommentar nullable: true
		intervjuStatus nullable: true
		dayBatchKode nullable: true
		internStatus nullable:true
    }
	
	static mapping  = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'melding_inn_seq'],  sqlType: 'integer'
		deaktivert index: 'mld_inn_deakt_indeks'
		intervjuObjektId index: 'mld_inn_ioid_indeks'
	}
}