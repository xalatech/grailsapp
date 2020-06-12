package sivadm

import siv.type.UndersokelsesType;

class Skjema {
	
	boolean aktivertForIntervjuing
	Prosjekt prosjekt
	UndersokelsesType undersokelseType
	String delProduktNummer
	String skjemaNavn
	String skjemaKortNavn
	Boolean intervjuTypeBesok
	Boolean intervjuTypeTelefon
	Boolean intervjuTypePapir
	Boolean intervjuTypeWeb
	Boolean slettesEtterRetur
	Date oppstartDataInnsamling
	Boolean klarTilGenerering
	Boolean klarTilUtsending
	String status
	Date planlagtSluttDato
	Date sluttDato
	Boolean overtid
	Long onsketSvarProsent
	Date dataUttaksDato
	Boolean hentAlleOppdrag
	Boolean kanSlettesLokalt
	String langtidsLagretAv
	Date langtidsLagretDato
	Long antallIntervjuObjekterLagret
	Long antallOppdragLagret
	Long intervjuVarighet
	Long adminTid
	Date regoverforingDato
	String regoverforingInitialer
	String regoverforingSeksjon
	Date ioBrevGodkjentDato
	String ioBrevGodkjentInitialer
	Date krypteringDato
	Date krypteringMailSendt
	Date anonymDato
	Date anonymMailSendt
	Date ryddDato
	Date ryddMailSendt
	Date papirMakuleringDato
	Date papirMakuleringMailSendt
	Long maxAntIntervjuObjekterKontakt
	Long malVersjon
	String kommentar
	String instrumentId
	Boolean altIBlaise5
	
	static hasMany = [perioder:Periode, skjemaVersjoner:SkjemaVersjon, opplaringer:Opplaring]
	
	static belongsTo = Prosjekt
	
	static constraints = {
		 aktivertForIntervjuing(nullable:true, blank:true)
		 undersokelseType(nullable:false)
		 skjemaNavn(nullable:false, blank:false)
		 skjemaKortNavn(nullable:false, blank:false, unique:true)
		 intervjuTypeBesok(nullable:true, blank:true)
		 intervjuTypeTelefon(nullable:true, blank:true)
		 intervjuTypePapir(nullable:true, blank:true)
		 intervjuTypeWeb(nullable:true, blank:true)
		 slettesEtterRetur(nullable:true, blank:true)
		 oppstartDataInnsamling(nullable:false)
		 klarTilGenerering(nullable:true, blank:true)
		 klarTilUtsending(nullable:true, blank:true)
		 status(nullable:true, blank:true)
		 planlagtSluttDato(nullable:true)
		 sluttDato(nullable:true)
		 overtid(nullable:true, blank:true)
		 onsketSvarProsent(nullable:true, range: 1..100)
		 dataUttaksDato(nullable:true)
		 hentAlleOppdrag(nullable:true, blank:true)
		 kanSlettesLokalt(nullable:true, blank:true)
		 langtidsLagretAv(nullable:true, blank:true)
		 langtidsLagretDato(nullable:true)
		 antallIntervjuObjekterLagret(nullable:true)
		 antallOppdragLagret(nullable:true)
		 intervjuVarighet(nullable:true)
		 adminTid(nullable:true)
		 regoverforingDato(nullable:true)
		 regoverforingInitialer(nullable:true, blank:true, minSize: 3, maxSize: 3)
		 regoverforingSeksjon(nullable:true, blank:true)
		 ioBrevGodkjentDato(nullable:true)
		 ioBrevGodkjentInitialer(nullable:true, blank:true, minSize: 3, maxSize: 3)
		 krypteringDato(nullable:true)
		 krypteringMailSendt(nullable:true)
		 anonymDato(nullable:true)
		 anonymMailSendt(nullable:true)
		 ryddDato(nullable:true)
		 ryddMailSendt(nullable:true)
		 papirMakuleringDato(nullable:true)
		 papirMakuleringMailSendt(nullable:true)
		 maxAntIntervjuObjekterKontakt(nullable:true)
		 malVersjon(nullable:false)
		 kommentar(nullable:true, blank:true)
		 instrumentId(nullable:true, blank:true)
		 delProduktNummer nullable:false, blank:false, minSize: 7, maxSize: 7, validator: { value, command ->
			if(value) {
				Prosjekt prosjekt = command.prosjekt
				if (prosjekt) {
					String produktNummer = value.substring(0,5)
					if(!produktNummer.equals(prosjekt.produktNummer)) {
						return 'sivadm.skjema.delproduktnummer.error'
					}
				}
				def melding
				return melding
			}
		}
		altIBlaise5(nullable: true, blank: true)
	}
	
	static mapping = {
		antallIntervjuObjekterLagret column: 'antall_io_lagret'
		maxAntIntervjuObjekterKontakt column: 'max_ant_io_kontakt'
		id column: 'ID', generator: 'sequence',  params:[sequence:'skjema_seq'],  sqlType: 'integer'
	}
		
	public String toString() {
		return delProduktNummer + " - " + skjemaNavn;
	}
	

	def getTestId() {
		return id + "hei"
	}
}