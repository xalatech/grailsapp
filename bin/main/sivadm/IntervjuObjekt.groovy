package sivadm

import java.util.Date;
import java.util.List;
import siv.type.*;

class IntervjuObjekt {
	
	Periode periode
	SkjemaStatus katSkjemaStatus
	Kilde kilde
	String intervjuObjektNummer
	String kontaktPersonFodselsNummer
	String delutvalg
	String navn
	
	String sivilstand
	String familienummer
	String statsborgerskap
	String personKode
	String referansePerson
	String organisasjonsNummer
	String kontaktperiode
	String meldingTilIntervjuer
	String statusKommentar
	Boolean onskerKontaktMedProsjektleder
	Boolean onskerResultatEpost
	Intervjuer onsketIntervjuer
	Boolean harLestBrev
	
	Long fullforingsGrad
	Long fullforingsStatus
	Integer intervjuStatus
	String internStatus
	
	Integer dayBatchKode
	
	Boolean nekterBrevSendt
	Date postaltTilleggMottatt
	Long antallTilleggMottatt
	Boolean ferdig
	Date ferdigDato
	Date paVentDato
	String fodselsNummer
	Kjonn kjonn
		
	Boolean parkert
	Date parkertDato
	String kommentar

	Long alder
	String utvalgsOmraade
	String utvalgAvtaleDato
	
	UtvalgImport utvalgImport
	
	String overIoNummer
	
	String epost
	Boolean reservasjon
	
	String passordWeb
		
	String redigertAv
	Date redigertDato
	
	String intervjuer
	
	// Felter som brukes til låsing av IO for redigering
	String laastAv
	Date laastTidspunkt

	String maalform
	String varslingsstatus
	
	static hasMany = [
		husholdninger: Husholdning,
		historier: Historie,
		adresser: Adresse,
		telefoner: Telefon,
		statusHistorikk: StatHist,
		tildeltHistorikk: TildelHist
	]

	static hasOne = [
	        avtale: Avtale
	]
	
	static constraints = {
		periode(nullable:true)
		tildeltHistorikk(maxSize: 4)
		intervjuObjektNummer(nullable: false, blank: false, maxSize: 6)
		kontaktPersonFodselsNummer(nullable:true)
		delutvalg(nullable:true, maxSize: 2)
		navn(nullable:true, maxSize: 40)
		sivilstand(nullable:true, maxSize: 1)
		familienummer(nullable:true, maxSize: 11)
		statsborgerskap(nullable:true, maxSize: 3)
		personKode(nullable:true, blank: true, maxSize: 1, inList: ["1", "2", "3"])
		referansePerson(nullable: true, blank: true, maxSize: 40)
		organisasjonsNummer(nullable:true)
		kontaktperiode(nullable:true, maxSize: 15)
		meldingTilIntervjuer(nullable:true, maxSize: 80)
		statusKommentar(nullable:true)
		onskerKontaktMedProsjektleder(nullable:true)
		onskerResultatEpost(nullable:true)
		onsketIntervjuer(nullable:true)
		harLestBrev(nullable:true)
		intervjuStatus(nullable:true)
		internStatus(nullable:true)
		dayBatchKode(nullable:true)
		nekterBrevSendt(nullable:true)
		postaltTilleggMottatt(nullable:true)
		antallTilleggMottatt(nullable:true)
		ferdig(nullable:true)
		ferdigDato(nullable:true)
		paVentDato(nullable:true)
		fodselsNummer(nullable:true)
		kjonn(nullable:true)
		parkert(nullable:true)
		parkertDato(nullable:true)
		kommentar(nullable:true)
		alder(nullable:true, max: 999L)
		fullforingsGrad(nullable:true)
		fullforingsStatus(nullable:true)
		utvalgsOmraade(nullable:true, maxSize: 6)
		utvalgAvtaleDato(nullable:true, maxSize: 8)
		utvalgImport(nullable:true)
		redigertAv(nullable:true)
		redigertDato(nullable:true)
		overIoNummer(nullable:true, maxSize: 6)
		epost(nullable:true, maxSize: 50)
		reservasjon(nullable:true)
		passordWeb(nullable:true, maxSize: 8)
		laastAv(nullable: true)
		laastTidspunkt(nullable: true)
		intervjuer(nullable:true)
		katSkjemaStatus(nullable:true)
		avtale(nullable:true)
		kilde(nullable:true)
		husholdninger(maxSize: 10)
		maalform(nullable: true, maxSize: 1)
		varslingsstatus(nullable: true, maxSize: 20)
	}
		
	static mapping = {
		onskerKontaktMedProsjektleder column: 'onsker_kontakt_pl'
		husholdninger column: 'io_husholdninger_id'
		tildeltHistorikk column: 'io_tildelt_historikk_id'
		statusHistorikk column: 'io_status_historikk_id'
		id column: 'ID', generator: 'sequence',  params:[sequence:'intervju_objekt_seq'],  sqlType: 'integer'
		navn index: 'io_navn_indeks'
		intervjuObjektNummer index: 'io_ionr_indeks'
	}
	
	
	
	static namedQueries  = {
		medSkjemaIdOgPeriodeNummer { skjemaId, periodeNummer ->
			periode {
				skjema { eq("id", skjemaId) }

				if( periodeNummer != null) {
					eq("periodeNummer", periodeNummer)
				}
			}
		}
		
		intervju {
			eq("intervjuStatus", 0)
			eq("katSkjemaStatus", SkjemaStatus.Ferdig)
		}

		frafall {
			gt("intervjuStatus", 0)
			le("intervjuStatus", 90)
			eq("katSkjemaStatus", SkjemaStatus.Ferdig)
		}

		avgang {
			gt("intervjuStatus", 90)
			eq("katSkjemaStatus", SkjemaStatus.Ferdig)
		}
		
		ubehandlede {
			eq("katSkjemaStatus", SkjemaStatus.Ubehandlet)
		}
		
		innlastet {
			eq("katSkjemaStatus", SkjemaStatus.Innlastet)
		}
		
		utsendtCapi {
			eq("katSkjemaStatus", SkjemaStatus.Utsendt_CAPI)
		}
		
		utsendtCati {
			eq("katSkjemaStatus", SkjemaStatus.Utsendt_CATI)
		}

		utsendtCatiWeb {
			eq("katSkjemaStatus", SkjemaStatus.Utsendt_CATI_WEB)
		}

		utsendtWeb {
			eq("katSkjemaStatus", SkjemaStatus.Utsendt_WEB)
		}

		paabegynt {
			eq("katSkjemaStatus", SkjemaStatus.Pabegynt)
		}

		paaVent {
			eq("katSkjemaStatus", SkjemaStatus.Paa_vent)
		}

		nekterePaaVent {
			eq("katSkjemaStatus", SkjemaStatus.Reut_CATI)
		}
	}
		
	public boolean statusChanged(SkjemaStatus katSkjemaStatus, Integer intervjuStatus) {
		if(this.intervjuStatus?.intValue() != intervjuStatus?.intValue()) {
			return true
		}
		if(this.katSkjemaStatus != katSkjemaStatus) {
			return true
		}
		return false
	}
	
	public void setNavn(String navn) {
		this.navn = navn ? navn.toUpperCase() : null
	}
	
	public void setReferansePerson(String referansePerson) {
		this.referansePerson = referansePerson ? referansePerson.toUpperCase() : null
	}
		
	def beforeInsert() {
		redigertDato = new Date()
	}
	
	def beforeUpdate() {
		redigertDato = new Date()
	}
			
	public String toString() {
		return navn + " (" + intervjuObjektNummer + ")"
	}
	
	public Adresse findGjeldendeBesokAdresse() {
		def besokAdresse 
		
		adresser.each { 
			Adresse adresse = it
			if(adresse?.getGjeldende() && adresse.getAdresseType() == AdresseType.BESOK) {
				besokAdresse = adresse
			}
		}
		
		return besokAdresse
	}
		
	public Adresse findGjeldendePostAdresse() {
		def postAdresse
		
		adresser.each {
			Adresse adresse = it
			
			if(adresse?.getGjeldende() && adresse.getAdresseType() == AdresseType.POST) {
				postAdresse = adresse
			}
		}
		
		return postAdresse
	}
	
	public boolean tilhorerBedriftSkjema() {
		if( periode?.skjema?.undersokelseType == UndersokelsesType.BEDRIFT ) {
			return true
		}
		else {
			return false
		}
	}
	
	public void settRiktigKildeFraBlaiseSync() {
		this.kilde = Kilde.CATI
		statusHistorikk.each {
			StatHist statHist = it
			if(statHist.skjemaStatus == SkjemaStatus.Utsendt_CAPI) {
				this.kilde = Kilde.CAPI
			}
		}
	}
		
	public static IntervjuObjekt findIntervjuObjektByTelefon( Telefon telefon ) {
		def c = IntervjuObjekt.createCriteria()
		
		def list = c {
			telefoner {
				eq("id", telefon.id)
			}
		}
		
		if( list != null && list.size() == 1) {
			def intervjuObjekt = list.get(0)
			
			return intervjuObjekt.refresh()
		}
		else {
			return null
		}
	}
	
	public static IntervjuObjekt findIntervjuObjektByAdresse(Adresse adresse) {
		def c = IntervjuObjekt.createCriteria()
		
		def list = c {
			adresser {
				eq("id", adresse.id)
			}
		}
		
		if( list != null && list.size() == 1) {
			def intervjuObjekt = list.get(0)
			
			return intervjuObjekt.refresh()
		}
		else {
			return null
		}
	}
	
	public static IntervjuObjekt findIntervjuObjektByHusholdning(Husholdning husholdning) {
		def c = IntervjuObjekt.createCriteria()
		
		def list = c {
			husholdninger {
				eq("id", husholdning.id)
			}
		}
		
		if( list != null && list.size() == 1) {
			def intervjuObjekt = list.get(0)
			
			return intervjuObjekt.refresh()
		}
		else {
			return null
		}
	}
	
	public static IntervjuObjekt findByIoNummerAndPeriode(String ioNummer, Periode periode) {
		def crit = IntervjuObjekt.createCriteria()
		
		def list = crit {
			eq("intervjuObjektNummer", ioNummer)
			eq("periode", periode)
		}
		
		if(list != null && list.size() == 1) {
			def intervjuObjekt = list.get(0)
			
			return intervjuObjekt
		}
		
		return null
	}

	public boolean bareWebSkjema() {
		return this.periode?.skjema?.intervjuTypeWeb && !this.periode?.skjema?.intervjuTypeTelefon && !this.periode?.skjema?.intervjuTypeBesok;
	}
	
}
