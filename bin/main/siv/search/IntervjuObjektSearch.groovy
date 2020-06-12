package siv.search

import siv.type.AvtaleType
import siv.type.Kjonn;
import siv.type.ResultatStatus;
import siv.type.SkjemaStatus;
import siv.type.Kilde;
import siv.type.UndersokelsesType;
import sivadm.Intervjuer

class IntervjuObjektSearch {

	String navn
	String intervjuObjektNummer
	Long intervjuObjektId
	List<SkjemaStatus> skjemaStatus
	List<AvtaleType> avtaleTyper
	Kilde kilde
	ResultatStatus resultatStatus
	Long skjema
	Long periodeNummer
	String adresse
	String initialer	
	String fodselsNummer
	String familienummer
	Kjonn kjonn
	Long alderFra
	Long alderTil
	Long fullforingMin
	Long fullforingMax
	Boolean typeBesok
	String aargang
	Integer intervjuStatus
	String postSted
	String postNummer
	String kommuneNummer
	String boligNummer
	String telefonNummer
	String epost
	String husBruk
	Long klynge
	String skjemaKortNavn
	
	public void kopier (IntervjuObjektSearch ios) {
		navn                 = ios.navn
		intervjuObjektNummer = ios.intervjuObjektNummer
		intervjuObjektId     = ios.intervjuObjektId
		skjemaStatus         = ios.skjemaStatus
		avtaleTyper          = ios.avtaleTyper
		resultatStatus       = ios.resultatStatus
		skjema               = ios.skjema
		periodeNummer        = ios.periodeNummer
		adresse              = ios.adresse
		initialer	         = ios.initialer
		fodselsNummer        = ios.fodselsNummer
		familienummer        = ios.familienummer
		kjonn                = ios.kjonn
		alderFra             = ios.alderFra
		alderTil             = ios.alderTil
		typeBesok            = ios.typeBesok
		aargang              = ios.aargang
		intervjuStatus       = ios.intervjuStatus
		postSted             = ios.postSted
		postNummer           = ios.postNummer
		kommuneNummer        = ios.kommuneNummer
		boligNummer          = ios.boligNummer
		telefonNummer        = ios.telefonNummer
		epost        		 = ios.epost
		husBruk              = ios.husBruk
		klynge               = ios.klynge
		skjemaKortNavn       = ios.skjemaKortNavn
	}
}
