package sivadm

import siv.type.ArbeidsType
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import siv.data.ProduktKode;
import siv.data.TimeregistreringListItem;
import siv.type.TimeforingStatus;
import siv.type.UtleggType;
import util.DateUtil;
import util.TimeUtil;

class TimeforingService {

	def grailsApplication

	boolean transactional = true
	
	public boolean wageClaimStatusAllowsEditing(TimeforingStatus timeforingStatus) {
		// Restriksjonene gjelder i utgangspunktet bare for intervjuerne.
		// Dette håndteres der metoden brukes.
		return !(timeforingStatus in [TimeforingStatus.AVVIST, TimeforingStatus.SENDT_INN, TimeforingStatus.BEHANDLET]
				 || !timeforingStatus)
	}
	
	public List getTimeforingerForIntervjuer(Intervjuer intervjuer, Date date) {
		Date startOfDay = TimeUtil.getStartOfDay(date)
		Date startOfNextDay = TimeUtil.getStartOfNextDay(date)
		
		def c = Timeforing.createCriteria() 
		
		def timeforingList = c {
			between("fra", startOfDay, startOfNextDay)
			eq( "intervjuer", intervjuer )
			
			order( "fra", "asc")
		}
		
		return timeforingList
	}
	
	
	public List getUtleggForIntervjuer(Intervjuer intervjuer, Date date) {
		
		def startOfDay = TimeUtil.getStartOfDay(date)
		def endOfDay = TimeUtil.getEndOfDay(date)
		
		def c = Utlegg.createCriteria()
		
		def utleggList = c {
			between("dato", startOfDay, endOfDay)
			eq( "intervjuer", intervjuer )
		}
		
		return utleggList
	}

	public List getUtleggForIntervjuer(Intervjuer intervjuer, String produktNummer, Date fra, Date til) {
		def startOfDay = null
		def endOfDay = null

		if (fra) {
			startOfDay = TimeUtil.getStartOfDay(fra)
		}
		if (til) {
			endOfDay = TimeUtil.getEndOfDay(til)
		}

		def c = Utlegg.createCriteria()

		def utleggList = c {
			eq( "intervjuer", intervjuer )
			if (produktNummer) {
				eq('produktNummer', produktNummer)
			}
			if (startOfDay) {
				ge('dato', startOfDay)
			}
			if (endOfDay) {
				le('dato', endOfDay)
			}
		}

		return utleggList
	}

	public List getUtleggForIntervjuer(Intervjuer intervjuer, String produktNummer) {
		
		def c = Utlegg.createCriteria()
		
		def utleggList = c {
			eq( "intervjuer", intervjuer ) 
			eq( "produktNummer", produktNummer )
		}
		
		return utleggList
	}

	public List getUtleggForIntervjuer(Intervjuer intervjuer, String produktNummer, Date dato) {
		def startOfDay = null
		def endOfDay = null

		if (dato) {
			startOfDay = TimeUtil.getStartOfDay(dato)
			endOfDay = TimeUtil.getEndOfDay(dato)
		}

		def c = Utlegg.createCriteria()

		def utleggList = c {
			eq( "intervjuer", intervjuer )
			if (produktNummer) {
				eq( "produktNummer", produktNummer )
			}
			if (startOfDay) {
				ge('dato', startOfDay)
			}
			if (endOfDay) {
				le('dato', endOfDay)
			}
		}

		return utleggList
	}

	public Double getUtleggsBelopForIntervjuer(Intervjuer intervjuer, String produktNummer) {
		
		def utleggList = getUtleggForIntervjuer(intervjuer, produktNummer)
		
		Double sum=0
		utleggList.each {
			sum += it.belop
		}
		return sum
	}

	public Double getUtleggsBelopForIntervjuer(Intervjuer intervjuer, String produktNummer, Date fra, Date til) {

		def utleggList = getUtleggForIntervjuer(intervjuer, produktNummer, fra, til)

		Double sum=0
		utleggList.each {
			sum += it.belop
		}
		return sum
	}

	public Double getUtleggsBelopForIntervjuer(Intervjuer intervjuer, String produktNummer, Date dato) {

		def utleggList = getUtleggForIntervjuer(intervjuer, produktNummer, dato)

		Double sum=0
		utleggList.each {
			sum += it.belop
		}
		return sum
	}

	public int hentAntallKostGodtgjorelserForIntervjuer(Intervjuer intervjuer, Date date) {
	
		def startOfDay = TimeUtil.getStartOfDay(date)
		def endOfDay = TimeUtil.getEndOfDay(date)
		
		def c = Utlegg.createCriteria()
		
		def utleggList = c {
			between("dato", startOfDay, endOfDay)
			eq( "intervjuer", intervjuer )
			or {
				eq( "utleggType", UtleggType.KOST_GODT )
			}
		}
		
		return utleggList.size()
	}
	
	
	public List getKjorebokForIntervjuer(Intervjuer intervjuer, Date date) {
		def startOfDay = TimeUtil.getStartOfDay(date)
		def startOfNextDay = TimeUtil.getStartOfNextDay(date)
		
		def c = Kjorebok.createCriteria()
		
		def kjorebokList = c {
			between("fraTidspunkt", startOfDay, startOfNextDay)
			eq( "intervjuer", intervjuer )
			order( "fraTidspunkt", "asc")
		}
		
		return kjorebokList
	}
	
	public List getKjorebokForIntervjuer(Intervjuer intervjuer, String produktNummer) {
		
		def c = Kjorebok.createCriteria()
		
		def kjorebokList = c {
			eq( "intervjuer", intervjuer )
			eq( "produktNummer", produktNummer )
		}
		
		return kjorebokList
	}

	public List getKjorebokForIntervjuer(Intervjuer intervjuer, String produktNummer, Date dato) {
		def startOfDay = null
		def startOfNextDay = null

		if (dato) {
			startOfDay = TimeUtil.getStartOfDay(dato)
			startOfNextDay = TimeUtil.getStartOfNextDay(dato)
		}

		def c = Kjorebok.createCriteria()

		def kjorebokList = c {
			eq( "intervjuer", intervjuer )
			if (produktNummer) {
				eq( "produktNummer", produktNummer )
			}
			if (startOfDay) {
				ge('fraTidspunkt', startOfDay)
			}
			if (startOfNextDay) {
				lt('fraTidspunkt', startOfNextDay)
			}
		}

		return kjorebokList
	}

	public List getKjorebokForIntervjuer(Intervjuer intervjuer, String produktNummer, Date fra, Date til) {
		def startOfDay = null
		def startOfNextDay = null

		if (fra) {
			startOfDay = TimeUtil.getStartOfDay(fra)
		}
		if (til) {
			startOfNextDay = TimeUtil.getStartOfNextDay(til)
		}
		def c = Kjorebok.createCriteria()

		def kjorebokList = c {
			eq( "intervjuer", intervjuer )
			if (produktNummer) {
				eq( "produktNummer", produktNummer )
			}
			if (startOfDay) {
				ge('fraTidspunkt', startOfDay)
			}
			if (startOfNextDay) {
				lt('fraTidspunkt', startOfNextDay)
			}
		}

		return kjorebokList
	}

	public Kjorebok hentForrigeKjorebokSammeDag(Intervjuer intervjuer, Date date) {
		Kjorebok kjorebokMedHoyesteTilTidspunkt = null
		
		def kjoreboker = getKjorebokForIntervjuer(intervjuer, date)
		
		if( kjoreboker && kjoreboker.size() > 0 ) {
			kjorebokMedHoyesteTilTidspunkt = kjoreboker.max { it.tilTidspunkt }
		}
		
		return kjorebokMedHoyesteTilTidspunkt
	}
	
	
	public Integer getAntallKilometerForIntervjuer(Intervjuer intervjuer, Date date) {
		def kjoreboker = this.getKjorebokForIntervjuer( intervjuer, date)
		
		int sum = 0
		
		kjoreboker.each { 
			Kjorebok kjorebok = it
			
			int x = kjorebok.kjorteKilometer? kjorebok.kjorteKilometer : 0
			
			sum += x
		}
		
		return sum
	}

	public Integer getAntallKilometerForIntervjuer(Intervjuer intervjuer, String produktNummer) {
		def kjoreboker = this.getKjorebokForIntervjuer( intervjuer, produktNummer)
		
		int sum = 0
		
		kjoreboker.each {
			int x = it.kjorteKilometer? it.kjorteKilometer : 0
			sum += x
		}
		
		return sum
	}

	public Integer getAntallKilometerForIntervjuer(Intervjuer intervjuer, String produktNummer, Date dato) {
		def kjoreboker = this.getKjorebokForIntervjuer( intervjuer, produktNummer, dato)

		int sum = 0

		kjoreboker.each {
			int x = it.kjorteKilometer? it.kjorteKilometer : 0
			sum += x
		}

		return sum
	}

	public Integer getAntallKilometerForIntervjuer(Intervjuer intervjuer, String produktNummer, Date fra, Date til) {
		def kjoreboker = this.getKjorebokForIntervjuer( intervjuer, produktNummer, fra, til)

		int sum = 0

		kjoreboker.each {
			int x = it.kjorteKilometer? it.kjorteKilometer : 0
			sum += x
		}

		return sum
	}

	public Double getTotalBelopForIntervjuer(Intervjuer intervjuer, Date date) {
		def utlegg = this.getUtleggForIntervjuer(intervjuer, date)
		
		if( !utlegg || utlegg.size() == 0 ) {
			return new Double(0)
		}
		
		double totalt = 0
		
		utlegg.each {
			totalt += it.belop
		}
	
		return new Double(totalt)	
	}

	public Double getTotalBelopForIntervjuer(Intervjuer intervjuer, String produktNummer, Date date) {
		def utlegg = this.getUtleggForIntervjuer(intervjuer, produktNummer, date)

		if( !utlegg || utlegg.size() == 0 ) {
			return new Double(0)
		}

		double totalt = 0

		utlegg.each {
			totalt += it.belop
		}

		return new Double(totalt)
	}

	public String getTotaltAntallTimerForIntervjuer(Intervjuer intervjuer, Date date) {
		int sumMinutes = getTotaltAntallMinutterForIntervjuer(intervjuer, date)
		return DateUtil.formatMinutes(sumMinutes)
	}
	
	
	public int getTotaltAntallMinutterForIntervjuer(Intervjuer intervjuer, Date date) {
		def timeforinger = this.getTimeforingerForIntervjuer (intervjuer, date)
		
		int sumMinutes = 0
		
		timeforinger.each {
			Timeforing timeforing = it
			
			sumMinutes += DateUtil.getMinutesBetweenDates(timeforing.fra, timeforing.til)
		}
		
		return sumMinutes
	}
	
	
	public void godkjennAlle(Intervjuer intervjuer, Date date) {
		this.setTimeforingStatusOnAll (intervjuer, date, TimeforingStatus.GODKJENT)
	}
	
	
	public void sendInnAlle(Intervjuer intervjuer, Date date) {
		this.setTimeforingStatusOnAll (intervjuer, date, TimeforingStatus.SENDT_INN)
	}
	
	
	public boolean isAllRegistreringerGodkjent( Intervjuer intervjuer, Date date) {
		
		boolean godkjent = true
		
		def timeforingList = this.getTimeforingerForIntervjuer (intervjuer, date)
		
		timeforingList.each { timeforing ->
			if( timeforing.timeforingStatus != TimeforingStatus.GODKJENT && timeforing.timeforingStatus != TimeforingStatus.AVVIST) {
				godkjent = false
			}
		}
		
		def utleggList = this.getUtleggForIntervjuer (intervjuer, date)
		utleggList.each { utlegg ->
			if( utlegg.timeforingStatus != TimeforingStatus.GODKJENT && utlegg.timeforingStatus != TimeforingStatus.AVVIST) {
				godkjent = false
			}
		}
		
		def kjorebokList = this.getKjorebokForIntervjuer (intervjuer, date)
		kjorebokList.each { kjorebok ->
			if( kjorebok.timeforingStatus != TimeforingStatus.GODKJENT && kjorebok.timeforingStatus != TimeforingStatus.AVVIST) {
				godkjent = false
			}
		}
		
		if( timeforingList.size() == 0 && utleggList.size() == 0 && kjorebokList.size() == 0) {
			godkjent = false
		}
		
		return godkjent
	}
	
	public boolean isAllRegistreringerSendtInn( Intervjuer intervjuer, Date date) {
		
		boolean sendtInn = true
		
		def timeforingList = this.getTimeforingerForIntervjuer (intervjuer, date)
		
		timeforingList.each { Timeforing timeforing ->
			if(timeforing.timeforingStatus != TimeforingStatus.SENDT_INN && timeforing.timeforingStatus != TimeforingStatus.AVVIST && timeforing.timeforingStatus != TimeforingStatus.BEHANDLET ) {
				sendtInn = false
			}
		}
		
		def utleggList = this.getUtleggForIntervjuer (intervjuer, date)
		utleggList.each { Utlegg utlegg ->
			if(!utlegg.timeforingStatus || (utlegg.timeforingStatus != TimeforingStatus.SENDT_INN && utlegg.timeforingStatus != TimeforingStatus.AVVIST && utlegg.timeforingStatus != TimeforingStatus.BEHANDLET )) {
				sendtInn = false
			}
		}
		
		def kjorebokList = this.getKjorebokForIntervjuer (intervjuer, date)
		kjorebokList.each { Kjorebok kjorebok ->
			if(kjorebok.timeforingStatus != TimeforingStatus.SENDT_INN && kjorebok.timeforingStatus != TimeforingStatus.AVVIST && kjorebok.timeforingStatus != TimeforingStatus.BEHANDLET) {
				sendtInn = false
			}
		}
		
		if( timeforingList.size() == 0 && utleggList.size() == 0 && kjorebokList.size() == 0) {
			sendtInn = false
		}
		
		return sendtInn
	}
	
	public boolean isRegistreringer(Intervjuer intervjuer, Date date) {
		
		boolean registreringer = false
		
		def timeforingList = this.getTimeforingerForIntervjuer (intervjuer, date)
		def utleggList = this.getUtleggForIntervjuer (intervjuer, date)
		def kjorebokList = this.getKjorebokForIntervjuer (intervjuer, date)
		
		if( timeforingList.size() == 0 && utleggList.size() == 0 && kjorebokList.size() == 0) {
			registreringer = false
		}
		else {
			registreringer = true
		}
		
		return registreringer
	}
	
	
	public List getTimeregistreringsList(Intervjuer intervjuer)
	{
		def timeregistreringList = new ArrayList()
		
		Calendar cal = Calendar.getInstance()
		
		int d = cal.get(Calendar.DAY_OF_MONTH)
		
		for(int i=0; i<d; i++) {
			Calendar c = Calendar.getInstance()
			c.add (Calendar.DAY_OF_MONTH, (0-i))
			def dato = c.getTime()
			
			Integer km = getAntallKilometerForIntervjuer (intervjuer, dato)
			String timer = getTotaltAntallTimerForIntervjuer (intervjuer, dato)
			Double belop = getTotalBelopForIntervjuer( intervjuer, dato)
			Boolean sendtInn = this.isAllRegistreringerSendtInn (intervjuer, dato)
			
			if( (km && km > 0) || (timer && timer != "00:00") || belop != null )
			{
				TimeregistreringListItem item = new TimeregistreringListItem()
				item.kjorteKilometer = km
				item.totaleTimer = timer
				item.belop = belop
				item.sendtInn = sendtInn
				item.dato = dato
				
				timeregistreringList.add item
			}
		}	
		
		return timeregistreringList
	}
	
	public boolean isReturnDate(Intervjuer intervjuer, Date date) {
		def dateList = getDatesOpenedForEditing(intervjuer)
		
		Date d = getDateWithoutTime(date)
		
		return dateList.contains(d)
	}
	
	public List getDatesOpenedForEditing(Intervjuer intervjuer) {
		def dateList = []
		
		List<Timeforing> timeList = Timeforing.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				isNotNull("silMelding")
				not {
					'in'("timeforingStatus", [TimeforingStatus.SENDT_INN, TimeforingStatus.AVVIST])
				}
			}
		}
		
		List<Kjorebok> kjorebokList = Kjorebok.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				isNotNull("silMelding")
				not {
					'in'("timeforingStatus", [TimeforingStatus.SENDT_INN, TimeforingStatus.AVVIST])
				}
			}
		}
		
		List<Utlegg> utleggList = Utlegg.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				isNotNull("silMelding")
				not {
					'in'("timeforingStatus", [TimeforingStatus.SENDT_INN, TimeforingStatus.AVVIST])
				}
			}
		}
		
		timeList.each { t ->
			Date d = getDateWithoutTime(t.fra)
			if(!dateList.contains(d)) {
				dateList << d
			}
		}
		kjorebokList.each { k -> 
			Date d = getDateWithoutTime(k.fraTidspunkt)
			if(!dateList.contains(d)) {
				dateList << d
			}
		}
		utleggList.each { u ->
			Date d = getDateWithoutTime(u.dato)
			if(!dateList.contains(d)) {
				dateList << d
			}
		}
		
		return dateList
	}
	
	private Date getDateWithoutTime(Date d) {
		Calendar c = Calendar.getInstance()
		c.setTime d
		
		c.set(Calendar.HOUR_OF_DAY, 0)
		c.set(Calendar.MINUTE, 0)
		c.set(Calendar.SECOND, 0)
		c.set(Calendar.MILLISECOND, 0)
		
		return c.getTime()
	}
	
	private void setTimeforingStatusOnAll(Intervjuer intervjuer, Date date, TimeforingStatus timeforingStatus) {
		def timeforingList = this.getTimeforingerForIntervjuer (intervjuer, date)
		timeforingList.each { timeforing ->
			if(timeforing.timeforingStatus == TimeforingStatus.AVVIST) {
				log.info("Kan ikke sette status " + timeforingStatus + " naar timeforingen er avvist")
			}
			else {
				timeforing.timeforingStatus = timeforingStatus
			}
		}
		
		def utleggList = this.getUtleggForIntervjuer (intervjuer, date)
		utleggList.each { utlegg ->
			if(utlegg.timeforingStatus == TimeforingStatus.AVVIST) {
				log.info("Kan ikke sette status " + timeforingStatus + " naar utlegget er avvist")
			}
			else {
				utlegg.timeforingStatus = timeforingStatus
			}
		}
		
		def kjorebokList = this.getKjorebokForIntervjuer (intervjuer, date)
		kjorebokList.each { kjorebok ->
			if(kjorebok.timeforingStatus == TimeforingStatus.AVVIST) {
				log.info("Kan ikke sette status " + timeforingStatus + " naar kjorebok er avvist")
			}
			else {
				kjorebok.timeforingStatus = timeforingStatus
			}
		}
	}
	
	public List getAvvisteTimeforingForIntervjuer(Intervjuer intervjuer, Date fraDato, Date tilDato) {
		Date startOfDay = TimeUtil.getStartOfDay(fraDato)
		Date startOfNextDay = TimeUtil.getStartOfNextDay(tilDato)
		
		def list = Timeforing.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.AVVIST)
				between("fra", startOfDay, startOfNextDay)
				order( "fra", "desc")
			}
		}
		
		return list
		//return Timeforing.findAllByIntervjuerAndTimeforingStatus(intervjuer, TimeforingStatus.AVVIST)
	}
	
	public List getAvvisteKjorebokForIntervjuer(Intervjuer intervjuer, Date fraDato, Date tilDato) {
		Date startOfDay = TimeUtil.getStartOfDay(fraDato)
		Date startOfNextDay = TimeUtil.getStartOfNextDay(tilDato)
		
		def list = Kjorebok.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.AVVIST)
				between("fraTidspunkt", startOfDay, startOfNextDay)
				order( "fraTidspunkt", "desc")
			}
		}
		return list
		//return Kjorebok.findAllByIntervjuerAndTimeforingStatus(intervjuer, TimeforingStatus.AVVIST)
	}
	
	public List getAvvisteUtleggForIntervjuer(Intervjuer intervjuer, Date fraDato, Date tilDato) {
		Date startOfDay = TimeUtil.getStartOfDay(fraDato)
		Date startOfNextDay = TimeUtil.getStartOfNextDay(tilDato)
		
		def list = Utlegg.createCriteria().list() {
			and {
				eq("intervjuer", intervjuer)
				eq("timeforingStatus", TimeforingStatus.AVVIST)
				between("dato", startOfDay, startOfNextDay)
				order( "dato", "desc")
			}
		}
		return list
	}
	
	
	public List hentSorterteProduktKoder(Date dato, boolean taMedAdminProdukter) {
		this.hentProduktKoder(dato, taMedAdminProdukter, true)
	}
	
	
	public List hentProduktKode(Date dato, boolean taMedAdminProdukter) {
		this.hentProduktKoder(dato, taMedAdminProdukter, false) 
	}
	
	
	protected List hentProduktKoder(Date dato, boolean taMedAdminProdukter, boolean sorter) {
		def skjemaListe
		def produktListe
		
		if(taMedAdminProdukter) {
			produktListe = Produkt.list()
		}
		
		if(dato) {
			Date startOfNextDay = TimeUtil.getStartOfDay(dato)
			skjemaListe = Skjema.createCriteria().list() {
				or {
					isNull("sluttDato")
					ge("sluttDato", startOfNextDay)
				}
			}
		}
		else {
			skjemaListe = Skjema.list()
		}
		
		def skjemaProduktListe = []
		
		skjemaListe.each {
			ProduktKode ps = new ProduktKode(navn: (it.skjemaNavn + " (" + it.prosjekt.prosjektNavn + ")"), produktNummer: it.delProduktNummer)
			skjemaProduktListe << ps
		}
		
		def produktKodeList = []
		
		produktListe.each {
			ProduktKode ps = new ProduktKode(navn: it.navn, produktNummer: it.produktNummer)
			produktKodeList << ps
		}
		
		if( sorter ) {
			skjemaProduktListe = skjemaProduktListe.sort { it.navn }
			produktKodeList = produktKodeList.sort { it.navn }
		}
		
		def totalProduktListe = []
		
		if( taMedAdminProdukter ) {
			totalProduktListe =  skjemaProduktListe + produktKodeList
		}
		else {
			totalProduktListe = skjemaProduktListe
		}
		
		return totalProduktListe
	}

	public List getUtleggUtenTimeforteProsjekter(Intervjuer inter, Date fra, Date til, String produktNummer, String arbeidsType) {
		def returnList = []

			Date fraDato = null
			Date tilDato = null

			if(fra) {
				fraDato = TimeUtil.getStartOfDay(fra)
			}
			if(til) {
				tilDato = TimeUtil.getStartOfNextDay(til)
			}

			def crit = Timeforing.createCriteria()
			def prosjekterMedTimeforing = crit {

				projections {
					distinct('produktNummer')
				}

				eq('intervjuer', inter)

				if(fraDato) {
					ge("fra", fraDato)
				}

				if(tilDato) {
					lt("fra", tilDato)
				}

				if(produktNummer){
					eq("produktNummer", produktNummer)
				}

				if(arbeidsType){
					eq("arbeidsType", ArbeidsType.valueOf(arbeidsType))
				}
			}

		def critU = Utlegg.createCriteria()

		def alleUtlegg = critU {

			eq( "intervjuer", inter )

			if(produktNummer){
				eq( "produktNummer", produktNummer )
			}

			if(fraDato) {
				ge("dato", fraDato)
			}

			if(tilDato) {
				lt("dato", tilDato)
			}

			order('produktNummer')
		}

		alleUtlegg.each { utlegg ->
			if (!prosjekterMedTimeforing.contains(utlegg.produktNummer)) {
				returnList.add(utlegg)
			}
		}

		return returnList
	}

	public List getUtleggUtenTimeforing(Intervjuer inter, Date fra, Date til, String produktNummer, String arbeidsType) {
		def returnList = []

		Date fraDato = null
		Date tilDato = null

		if(fra) {
			fraDato = TimeUtil.getStartOfDay(fra)
		}
		if(til) {
			tilDato = TimeUtil.getStartOfNextDay(til)
		}

		def crit = Timeforing.createCriteria()
		def timeforinger = crit {

			eq('intervjuer', inter)

			if(fraDato) {
				ge("fra", fraDato)
			}

			if(tilDato) {
				lt("fra", tilDato)
			}

			if(produktNummer){
				eq("produktNummer", produktNummer)
			}

			if(arbeidsType){
				eq("arbeidsType", ArbeidsType.valueOf(arbeidsType))
			}
		}
		def datoliste = []
		Date dato
		timeforinger.each { Timeforing timeforing ->
			dato = TimeUtil.getStartOfDay(timeforing.fra)
			if (!datoliste.contains(dato)) {
				datoliste.add(dato)
			}
		}

		def critU = Utlegg.createCriteria()

		def alleUtlegg = critU {

			eq( "intervjuer", inter )

			if(produktNummer){
				eq( "produktNummer", produktNummer )
			}

			if(fraDato) {
				ge("dato", fraDato)
			}

			if(tilDato) {
				lt("dato", tilDato)
			}

			order('dato')
		}

		alleUtlegg.each { Utlegg utlegg ->
			if (!datoliste.contains(TimeUtil.getStartOfDay(utlegg.dato))) {
				returnList.add(utlegg)
			}
		}

		return returnList
	}

	def ryddTimeforinger = {
		String antallAar = grailsApplication.config.behold.timeforinger.antall.aar
		log.info("Kjører ryddTimeforinger, sletter timeforinger eldre enn " + antallAar + " år")

		Calendar cal = Calendar.getInstance()
		cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))

        Timeforing.executeUpdate("delete from Timeforing where fra < :dato", [dato: cal.getTime()])
		log.info('Har slettet timeforinger med dato eldre enn ' + cal.getTime())
	}

}
