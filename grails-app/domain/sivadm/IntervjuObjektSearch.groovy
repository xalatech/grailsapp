package sivadm

import siv.type.Kjonn
import siv.type.ResultatStatus
import siv.type.SkjemaStatus
import siv.type.Kilde
import siv.type.UndersokelsesType
import sivadm.Intervjuer
import java.util.Date

class IntervjuObjektSearch {

	// Verdier ved lagring
	String sokeNavn
	Date lagret
	String lagretAv
	
	// Verdier i skjema
	String navn
	String intervjuObjektNummer
	Long intervjuObjektId
	String skjemaStatus
	String avtaleType
	Kilde kilde
	ResultatStatus resultatStatus
	Long skjema

	Long assosiertSkjema
	String intervjuStatusAssSkj
	Boolean intervjuStatusBlankAssSkj
	String kontaktperiodeAssSkj

	String periodeNummer
	String adresse
	String initialer	
	String fodselsNummer
	String familienummer
	Kjonn kjonn
	Long alderFra
	Long alderTil
	Long fullforingMin
	Long fullforingMax
	String fullforingsStatus

	Boolean typeBesok
	String aargang
	String intervjuStatus

	Boolean intervjuStatusBlank

	String postSted
	String postNummer
	String kommuneNummer
	String boligNummer
	String telefonNummer
	String epost
	String husBruk
	Long klynge
	String skjemaKortNavn
	String kontaktperiode

	String delutvalg

	Date  intStatDatoIntervallFra
	Date  intStatDatoIntervallTil
	Boolean persisterSokeResultat
	String intervjuObjektSearchResult
	Long meldingsheaderMalId

	String maalform

	static constraints = {
		sokeNavn(nullable:true)
		lagret(nullable:true)
		lagretAv(nullable:true)
		navn(nullable:true)
		intervjuObjektNummer(nullable:true)
		intervjuObjektId(nullable:true)
		skjemaStatus(nullable:true)
		avtaleType(nullable:true)
		kilde(nullable:true)
		resultatStatus(nullable:true)
		skjema(nullable:true)
		assosiertSkjema(nullable:true)
		periodeNummer(nullable:true)
		adresse(nullable:true)
		initialer(nullable:true)
		fodselsNummer(nullable:true)
		familienummer(nullable:true)
		kjonn(nullable:true)
		alderFra(nullable:true)
		alderTil(nullable:true)
		fullforingsStatus(nullable:true)
		fullforingMin(nullable:true)
		fullforingMax(nullable:true)
		typeBesok(nullable:true)
		aargang(nullable:true)
		intervjuStatus(nullable:true)
		intervjuStatusAssSkj(nullable:true)
		intervjuStatusBlank(nullable:true)
		intervjuStatusBlankAssSkj(nullable:true)
		postSted(nullable:true)
		postNummer(nullable:true)
		kommuneNummer(nullable:true)
		boligNummer(nullable:true)
		telefonNummer(nullable:true)
		epost(nullable:true)
		husBruk(nullable:true)
		klynge(nullable:true)
		skjemaKortNavn(nullable:true)
		kontaktperiode(nullable:true)
		kontaktperiodeAssSkj(nullable:true)
		delutvalg(nullable:true)
		intStatDatoIntervallFra(nullable:true)
		intStatDatoIntervallTil(nullable:true)
		persisterSokeResultat(nullable:false)
		intervjuObjektSearchResult(nullable:true)
		maalform(nullable:true)
		meldingsheaderMalId(nullable: true)
	}
	
	static mapping = {
		id column: 'ID', generator: 'sequence',  params:[sequence:'intervju_objekt_search_seq'],  sqlType: 'integer'
		intervjuObjektSearchResult sqlType: 'text'
	}
	
	public void kopier (IntervjuObjektSearch ios) {
		sokeNavn			 	= ios.sokeNavn
		lagret				 	= ios.lagret
		lagretAv			 	= ios.lagretAv
		navn                 	= ios.navn
		intervjuObjektNummer 	= ios.intervjuObjektNummer
		intervjuObjektId     	= ios.intervjuObjektId
		skjemaStatus         	= ios.skjemaStatus
		avtaleType				= ios.avtaleType
		kilde					= ios.kilde
		resultatStatus       	= ios.resultatStatus
		skjema               	= ios.skjema
		assosiertSkjema         = ios.assosiertSkjema
		periodeNummer        	= ios.periodeNummer
		adresse              	= ios.adresse
		initialer	         	= ios.initialer
		fodselsNummer        	= ios.fodselsNummer
		familienummer        	= ios.familienummer
		kjonn                	= ios.kjonn
		alderFra             	= ios.alderFra
		alderTil             	= ios.alderTil
		fullforingMin			= ios.fullforingMin
		fullforingMax			= ios.fullforingMax
		fullforingsStatus		= ios.fullforingsStatus
		typeBesok            	= ios.typeBesok
		aargang              	= ios.aargang
		intervjuStatus       	= ios.intervjuStatus
		intervjuStatusAssSkj	= ios.intervjuStatusAssSkj
		intervjuStatusBlank	 	= ios.intervjuStatusBlank
		intervjuStatusBlankAssSkj = ios.intervjuStatusBlankAssSkj
		postSted             	= ios.postSted
		postNummer           	= ios.postNummer
		kommuneNummer        	= ios.kommuneNummer
		boligNummer          	= ios.boligNummer
		telefonNummer        	= ios.telefonNummer
		epost        		 	= ios.epost
		husBruk              	= ios.husBruk
		klynge               	= ios.klynge
		skjemaKortNavn       	= ios.skjemaKortNavn
		kontaktperiode		 	= ios.kontaktperiode
		kontaktperiodeAssSkj	= ios.kontaktperiodeAssSkj
		delutvalg			 	= ios.delutvalg
		intStatDatoIntervallFra = ios.intStatDatoIntervallFra
		intStatDatoIntervallTil = ios.intStatDatoIntervallTil
		persisterSokeResultat = ios.persisterSokeResultat
		intervjuObjektSearchResult = ios.intervjuObjektSearchResult
		maalform				= ios.maalform
		meldingsheaderMalId		= ios.meldingsheaderMalId
	}
}
