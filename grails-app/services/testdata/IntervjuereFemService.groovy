package testdata

import sivadm.Bruker
import sivadm.BrukerRolle
import sivadm.Intervjuer
import sivadm.Klynge
import sivadm.Rolle

class IntervjuereFemService {
	
	def sessionFactory
	
	def opprettIntervjuereFem() {
		Klynge klynge1 = getKlynge1()
		Klynge klynge2 = getKlynge2()
		Klynge klynge3 = getKlynge3()
		Klynge klynge4 = getKlynge4()
		Klynge klynge5 = getKlynge5()
		Klynge klynge6 = getKlynge6()
		Klynge klynge7 = getKlynge7()
		Klynge klynge8 = getKlynge8()
		Klynge klynge9 = getKlynge9()
		
		Rolle rolleIntervjuer = getRolleIntervjuer()
		
		lagreIntervjuer01(klynge1 , rolleIntervjuer)
		
		lagreIntervjuer02(klynge3 , rolleIntervjuer)
		
		lagreIntervjuer03(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer04(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer05(klynge7 , rolleIntervjuer)
		
		lagreIntervjuer06(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer07(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer08(klynge4 , rolleIntervjuer)
		
		lagreIntervjuer09(klynge3 , rolleIntervjuer)
		
		lagreIntervjuer10(klynge1 , rolleIntervjuer)
		
		lagreIntervjuer11(klynge6 , rolleIntervjuer)
		
		lagreIntervjuer12(klynge1 , rolleIntervjuer)
		
		lagreIntervjuer13(klynge2 , rolleIntervjuer)
		
		lagreIntervjuer14(klynge3 , rolleIntervjuer)
		
		lagreIntervjuer15(klynge6 , rolleIntervjuer)
		
		lagreIntervjuer16(klynge7 , rolleIntervjuer)
		
		lagreIntervjuer17(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer18(klynge2 , rolleIntervjuer)
		
		lagreIntervjuer19(klynge5 , rolleIntervjuer)
		
		lagreIntervjuer20(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer21(klynge1 , rolleIntervjuer)
		
		lagreIntervjuer22(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer23(klynge2 , rolleIntervjuer)
		
		lagreIntervjuer24(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer25(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer26(klynge1 , rolleIntervjuer)
		
		lagreIntervjuer27(klynge3 , rolleIntervjuer)
		
		lagreIntervjuer28(klynge4 , rolleIntervjuer)
		
		lagreIntervjuer29(klynge4 , rolleIntervjuer)
		
		lagreIntervjuer30(klynge4 , rolleIntervjuer)
		
		lagreIntervjuer31(klynge7 , rolleIntervjuer)
		
		lagreIntervjuer32(klynge6 , rolleIntervjuer)
		
		lagreIntervjuer33(klynge5 , rolleIntervjuer)
		
		lagreIntervjuer34(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer35(klynge2 , rolleIntervjuer)
		
		lagreIntervjuer36(klynge6 , rolleIntervjuer)
		
		lagreIntervjuer37(klynge3 , rolleIntervjuer)
		
		lagreIntervjuer38(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer39(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer40(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer41(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer42(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer43(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer44(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer45(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer46(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer47(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer48(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer49(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer50(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer51(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer52(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer53(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer54(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer55(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer56(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer57(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer58(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer59(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer60(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer61(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer62(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer63(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer64(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer65(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer66(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer67(klynge9 , rolleIntervjuer)
		
		lagreIntervjuer68(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer69(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer70(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer71(klynge8 , rolleIntervjuer)
		
		lagreIntervjuer72(klynge3 , rolleIntervjuer)
		
	}

	private Rolle getRolleIntervjuer() {
		Rolle rolleIntervjuer = Rolle.findByAuthority("ROLE_INTERVJUER")
		return rolleIntervjuer
	}

	private Klynge getKlynge9() {
		Klynge klynge9 = Klynge.findByKlyngeNavn("Klynge Ringesenter Kongsvinger")
		return klynge9
	}

	private Klynge getKlynge8() {
		Klynge klynge8 = Klynge.findByKlyngeNavn("Klynge Ringesenter Oslo")
		return klynge8
	}

	private Klynge getKlynge7() {
		Klynge klynge7 = Klynge.findByKlyngeNavn("Klynge Nord")
		return klynge7
	}

	private Klynge getKlynge6() {
		Klynge klynge6 = Klynge.findByKlyngeNavn("Klynge Midt-Norge")
		return klynge6
	}

	private Klynge getKlynge5() {
		Klynge klynge5 = Klynge.findByKlyngeNavn("Klynge Vest")
		return klynge5
	}

	private Klynge getKlynge4() {
		Klynge klynge4 = Klynge.findByKlyngeNavn("Klynge Sør-Vest")
		return klynge4
	}

	private Klynge getKlynge3() {
		Klynge klynge3 = Klynge.findByKlyngeNavn("Klynge Innlandet")
		return klynge3
	}

	private Klynge getKlynge2() {
		Klynge klynge2 = Klynge.findByKlyngeNavn("Klynge Østlandet")
		return klynge2
	}

	private Klynge getKlynge1() {
		Klynge klynge1 = Klynge.findByKlyngeNavn("Klynge Oslo")
		return klynge1
	}
	
	private void lagreIntervjuer01(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer201 = new Intervjuer(initialer: "sok", intervjuerNummer: 104985, navn: "Sommerfelt Knut", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "01.01.1970"), gateAdresse: "Grefsenkollen 7 B", postNummer: "0490", kommuneNummer: "0301", epostPrivat: "knut@sommerfelt.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "19.04.2010"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer201.save(flush: true)
		Bruker bruker1  = new Bruker(navn: intervjuer201.navn, username: intervjuer201.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer201.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer201.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker1 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer02(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer205 = new Intervjuer(initialer: "ttb", intervjuerNummer: 104976, navn: "Breien Torkel Thams", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "28.10.1949"), gateAdresse: "Krabberødstrand 33", postNummer: "3960", kommuneNummer: "0814", epostJobb: "ttb@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.04.2010"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer205.save(flush: true)
		Bruker bruker2  = new Bruker(navn: intervjuer205.navn, username: intervjuer205.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer205.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer205.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker2 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer03(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer207 = new Intervjuer(initialer: "aaa", intervjuerNummer: 123456, navn: "aa", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "10.01.1987"), gateAdresse: "aaa", gateAdresse2: "aa", postNummer: "0987", kommuneNummer: "0301", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer207.save(flush: true)
		Bruker bruker3  = new Bruker(navn: intervjuer207.navn, username: intervjuer207.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer207.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer207.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker3 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer04(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer208 = new Intervjuer(initialer: "taj", intervjuerNummer: 300003, navn: "Tajama Zara", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "27.03.1991"), gateAdresse: "Pionerstien 10", postNummer: "1062", kommuneNummer: "0301", epostPrivat: "zara.jii@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer208.save(flush: true)
		Bruker bruker4  = new Bruker(navn: intervjuer208.navn, username: intervjuer208.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer208.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer208.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker4 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer05(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer209 = new Intervjuer(initialer: "spj", intervjuerNummer: 101634, navn: "Spjell Audun", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "09.09.1942"), gateAdresse: "Lagårdveien 21", postNummer: "8012", kommuneNummer: "1804", epostJobb: "spj@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "28.10.2002"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer209.save(flush: true)
		Bruker bruker5  = new Bruker(navn: intervjuer209.navn, username: intervjuer209.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer209.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer209.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker5 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer06(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer210 = new Intervjuer(initialer: "jen", intervjuerNummer: 101566, navn: "Jensen Bjørn", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "06.09.1968"), gateAdresse: "John Collets allè 110", gateAdresse2: " IPB 27", postNummer: "0870", kommuneNummer: "0301", epostJobb: "jen@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "10.02.2003"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer210.save(flush: true)
		Bruker bruker6  = new Bruker(navn: intervjuer210.navn, username: intervjuer210.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer210.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer210.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker6 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer07(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer211 = new Intervjuer(initialer: "ruw", intervjuerNummer: 101654, navn: "Wilhelmsen Rune", kjonn: Kjonn.MANN, fodselsDato: new Date(), gateAdresse: "Garver Ytterborgs vei 107", postNummer: "0977", kommuneNummer: "0301", epostJobb: "ruw@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "10.02.2003"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer211.save(flush: true)
		Bruker bruker7  = new Bruker(navn: intervjuer211.navn, username: intervjuer211.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer211.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer211.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker7 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer08(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer212 = new Intervjuer(initialer: "vik", intervjuerNummer: 102238, navn: "Bjørgvik Andre", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "15.07.1949"), gateAdresse: "Fidjeåsen 18 leil 301", postNummer: "4639", kommuneNummer: "1001", epostJobb: "vik@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "01.02.2007"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer212.save(flush: true)
		Bruker bruker8  = new Bruker(navn: intervjuer212.navn, username: intervjuer212.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer212.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer212.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker8 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer09(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer213 = new Intervjuer(initialer: "hig", intervjuerNummer: 101539, navn: "Guttormsen Hans I", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "11.10.1941"), gateAdresse: "Nybergkroken 9", postNummer: "3740", kommuneNummer: "0806", epostJobb: "hig@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "24.03.2003"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK, sluttDato: new Date())
		intervjuer213.save(flush: true)
		Bruker bruker9  = new Bruker(navn: intervjuer213.navn, username: intervjuer213.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer213.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer213.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker9 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer10(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer214 = new Intervjuer(initialer: "rse", intervjuerNummer: 101617, navn: "Risto Svein-Erik", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "20.08.1951"), gateAdresse: "Brolandsveien 32", postNummer: "0980", kommuneNummer: "0301", epostJobb: "rse@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "24.03.2003"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer214.save(flush: true)
		Bruker bruker10 = new Bruker(navn: intervjuer214.navn, username: intervjuer214.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer214.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer214.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker10, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer11(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer215 = new Intervjuer(initialer: "soi", intervjuerNummer: 101656, navn: "Åhjem Solveig I", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "05.02.1967"), gateAdresse: "Øvre Remaveg 11", postNummer: "6057", kommuneNummer: "1504", epostJobb: "soi@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "24.03.2003"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer215.save(flush: true)
		Bruker bruker11 = new Bruker(navn: intervjuer215.navn, username: intervjuer215.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer215.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer215.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker11, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer12(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer216 = new Intervjuer(initialer: "jmh", intervjuerNummer: 101571, navn: "Jonassen Helen", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "26.04.1957"), gateAdresse: "Biskop Heuchs vei 34", postNummer: "0871", kommuneNummer: "0301", epostJobb: "jmh@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "22.06.2004"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer216.save(flush: true)
		Bruker bruker12 = new Bruker(navn: intervjuer216.navn, username: intervjuer216.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer216.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer216.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker12, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer13(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer217 = new Intervjuer(initialer: "lah", intervjuerNummer: 101587, navn: "Langø Hans", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "12.12.1940"), gateAdresse: "Lilleteigen 21", postNummer: "1406", kommuneNummer: "0213", epostJobb: "lah@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "22.06.2004"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK, sluttDato: new Date())
		intervjuer217.save(flush: true)
		Bruker bruker13 = new Bruker(navn: intervjuer217.navn, username: intervjuer217.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer217.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer217.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker13, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer14(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer218 = new Intervjuer(initialer: "sgu", intervjuerNummer: 101636, navn: "Støen Guttorm", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "25.09.1944"), gateAdresse: "Gjøstivegen 14", postNummer: "2614", kommuneNummer: "0501", epostJobb: "sgu@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "22.06.2004"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer218.save(flush: true)
		Bruker bruker14 = new Bruker(navn: intervjuer218.navn, username: intervjuer218.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer218.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer218.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker14, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer15(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer219 = new Intervjuer(initialer: "lte", intervjuerNummer: 101642, navn: "Tessem Leif Kr", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "25.09.1946"), gateAdresse: "H O Christiansens v 56", postNummer: "7048", kommuneNummer: "1601", epostJobb: "lte@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "22.06.2004"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer219.save(flush: true)
		Bruker bruker15 = new Bruker(navn: intervjuer219.navn, username: intervjuer219.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer219.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer219.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker15, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer16(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer220 = new Intervjuer(initialer: "soo", intervjuerNummer: 101633, navn: "Solheim Olav", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "08.06.1944"), gateAdresse: "Bataljonsveien 19", postNummer: "7290", kommuneNummer: "1848", epostJobb: "soo@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "22.06.2004"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer220.save(flush: true)
		Bruker bruker16 = new Bruker(navn: intervjuer220.navn, username: intervjuer220.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer220.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer220.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker16, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer17(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer221 = new Intervjuer(initialer: "aej", intervjuerNummer: 101517, navn: "Ejaz Ayisha", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "16.07.1980"), gateAdresse: "Storgata 61", gateAdresse2: "leilighet 4615", postNummer: "0182", kommuneNummer: "0301", epostJobb: "aej@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "25.10.2004"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer221.save(flush: true)
		Bruker bruker17 = new Bruker(navn: intervjuer221.navn, username: intervjuer221.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer221.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer221.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker17, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer18(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer222 = new Intervjuer(initialer: "sno", intervjuerNummer: 101603, navn: "Nordli Sverre", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "06.11.1944"), gateAdresse: "Elingårdsvn 41", postNummer: "1626", kommuneNummer: "0106", epostJobb: "sno@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "25.10.2004"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer222.save(flush: true)
		Bruker bruker18 = new Bruker(navn: intervjuer222.navn, username: intervjuer222.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer222.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer222.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker18, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer19(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer223 = new Intervjuer(initialer: "jok", intervjuerNummer: 101577, navn: "Kleppe Jorunn", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "14.06.1948"), gateAdresse: "", postNummer: "6843", kommuneNummer: "1431", epostJobb: "jok@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "24.10.1996"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer223.save(flush: true)
		Bruker bruker19 = new Bruker(navn: intervjuer223.navn, username: intervjuer223.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer223.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer223.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker19, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer20(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer224 = new Intervjuer(initialer: "leh", intervjuerNummer: 101559, navn: "Hovde Leif", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "11.06.1958"), gateAdresse: "Smedg. 34", postNummer: "0651", kommuneNummer: "0301", epostJobb: "leh@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "01.02.2005"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer224.save(flush: true)
		Bruker bruker20 = new Bruker(navn: intervjuer224.navn, username: intervjuer224.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer224.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer224.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker20, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer21(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer225 = new Intervjuer(initialer: "kom", intervjuerNummer: 101607, navn: "Ny Karl", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "24.05.1950"), gateAdresse: "Arnstein Arnebergs vei 5", postNummer: "0274", kommuneNummer: "0301", epostJobb: "kom@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "28.02.2005"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK, sluttDato: new Date())
		intervjuer225.save(flush: true)
		Bruker bruker21 = new Bruker(navn: intervjuer225.navn, username: intervjuer225.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer225.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer225.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker21, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer22(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer226 = new Intervjuer(initialer: "aku", intervjuerNummer: 999970, navn: "AKU spesial", kjonn: Kjonn.KVINNE, fodselsDato: new Date(), gateAdresse: "Samtykkeveien 1", postNummer: "0100", kommuneNummer: "0301", epostJobb: "aku@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "28.02.2005"), avtaltAntallTimer: 0, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer226.save(flush: true)
		Bruker bruker22 = new Bruker(navn: intervjuer226.navn, username: intervjuer226.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer226.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer226.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker22, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer23(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer227 = new Intervjuer(initialer: "pap", intervjuerNummer: 101611, navn: "Paulsen Paul Åge", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "11.10.1944"), gateAdresse: "Sundbekkvegen 4", postNummer: "2008", kommuneNummer: "0228", epostJobb: "pap@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.1998"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer227.save(flush: true)
		Bruker bruker23 = new Bruker(navn: intervjuer227.navn, username: intervjuer227.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer227.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer227.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker23, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer24(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer228 = new Intervjuer(initialer: "her", intervjuerNummer: 101678, navn: "Herredsvela Heidi", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "Lunderbye Gård", postNummer: "2235", kommuneNummer: "0420", epostJobb: "her@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "14.05.2007"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON, sluttDato: new Date())
		intervjuer228.save(flush: true)
		Bruker bruker24 = new Bruker(navn: intervjuer228.navn, username: intervjuer228.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer228.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer228.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker24, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer25(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer229 = new Intervjuer(initialer: "trs", intervjuerNummer: 101685, navn: "Sjaastad Trine", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "01.05.1965"), gateAdresse: "Etterstadgaten 30", postNummer: "0658", kommuneNummer: "0301", epostJobb: "trs@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "11.05.2005"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer229.save(flush: true)
		Bruker bruker25 = new Bruker(navn: intervjuer229.navn, username: intervjuer229.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer229.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer229.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker25, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer26(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer230 = new Intervjuer(initialer: "sio", intervjuerNummer: 101868, navn: "Ødegård Sigbjørn", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "03.09.1941"), gateAdresse: "Holmboes gate 1", gateAdresse2: "H0503", postNummer: "0357", kommuneNummer: "0301", epostJobb: "sio@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer230.save(flush: true)
		Bruker bruker26 = new Bruker(navn: intervjuer230.navn, username: intervjuer230.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer230.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer230.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker26, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer27(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer231 = new Intervjuer(initialer: "lga", intervjuerNummer: 101848, navn: "Andresen Lars", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "19.02.1970"), gateAdresse: "Ringkollgrenda 10c", postNummer: "3227", kommuneNummer: "0706", epostPrivat: "l-andres@online.no", epostJobb: "lga@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer231.save(flush: true)
		Bruker bruker27 = new Bruker(navn: intervjuer231.navn, username: intervjuer231.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer231.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer231.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker27, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer28(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer232 = new Intervjuer(initialer: "hmp", intervjuerNummer: 101860, navn: "Prebensen Helge", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "10.03.1950"), gateAdresse: "Livollen 5", gateAdresse2: "c/o Hunsbedt", postNummer: "4484", kommuneNummer: "1037", epostJobb: "hmp@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer232.save(flush: true)
		Bruker bruker28 = new Bruker(navn: intervjuer232.navn, username: intervjuer232.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer232.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer232.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker28, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer29(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer233 = new Intervjuer(initialer: "erg", intervjuerNummer: 101854, navn: "Jensen Greta", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "01.10.1947"), gateAdresse: "Grønedalen 20", postNummer: "4370", kommuneNummer: "1101", epostJobb: "erg@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer233.save(flush: true)
		Bruker bruker29 = new Bruker(navn: intervjuer233.navn, username: intervjuer233.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer233.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer233.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker29, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer30(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer234 = new Intervjuer(initialer: "sbo", intervjuerNummer: 101865, navn: "Steinsbø Anne", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "18.02.1977"), gateAdresse: "", postNummer: "5427", kommuneNummer: "1219", epostJobb: "sbo@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer234.save(flush: true)
		Bruker bruker30 = new Bruker(navn: intervjuer234.navn, username: intervjuer234.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer234.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer234.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker30, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer31(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer235 = new Intervjuer(initialer: "njf", intervjuerNummer: 101863, navn: "Schjelderup Nils-Jacob", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "02.02.1964"), gateAdresse: "Hålogalands gate 33", postNummer: "9405", kommuneNummer: "1901", epostJobb: "njs@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.10.2005"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer235.save(flush: true)
		Bruker bruker31 = new Bruker(navn: intervjuer235.navn, username: intervjuer235.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer235.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer235.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker31, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer32(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer236 = new Intervjuer(initialer: "fjp", intervjuerNummer: 101988, navn: "Fjeldsæter Per", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "08.08.1947"), gateAdresse: "Brundalsgrenda 16", postNummer: "7058", kommuneNummer: "1601", epostJobb: "fjp@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.03.2006"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer236.save(flush: true)
		Bruker bruker32 = new Bruker(navn: intervjuer236.navn, username: intervjuer236.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer236.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer236.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker32, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer33(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer237 = new Intervjuer(initialer: "edh", intervjuerNummer: 101991, navn: "Hope Edle", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "21.03.1962"), gateAdresse: "Jacob Sæthres veg 2", postNummer: "5232", kommuneNummer: "1201", epostJobb: "edh@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.03.2006"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer237.save(flush: true)
		Bruker bruker33 = new Bruker(navn: intervjuer237.navn, username: intervjuer237.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer237.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer237.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker33, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer34(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer238 = new Intervjuer(initialer: "ktu", intervjuerNummer: 101994, navn: "Kaarby Turid", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "Aurskoggt 5", postNummer: "0655", kommuneNummer: "0301", epostJobb: "ktu@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "29.03.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer238.save(flush: true)
		Bruker bruker34 = new Bruker(navn: intervjuer238.navn, username: intervjuer238.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer238.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer238.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker34, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer35(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer239 = new Intervjuer(initialer: "rab", intervjuerNummer: 102000, navn: "Rasch Bjørn", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "03.07.1950"), gateAdresse: "Elgv 16", postNummer: "2340", kommuneNummer: "0415", epostJobb: "rab@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.03.2006"), avtaltAntallTimer: 700, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer239.save(flush: true)
		Bruker bruker35 = new Bruker(navn: intervjuer239.navn, username: intervjuer239.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer239.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer239.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker35, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer36(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer240 = new Intervjuer(initialer: "chv", intervjuerNummer: 101649, navn: "Vikan Christian", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "03.12.1946"), gateAdresse: "Laukberget", postNummer: "7500", kommuneNummer: "1714", epostJobb: "chv@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.04.2002"), avtaltAntallTimer: 500, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer240.save(flush: true)
		Bruker bruker36 = new Bruker(navn: intervjuer240.navn, username: intervjuer240.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer240.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer240.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker36, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer37(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer241 = new Intervjuer(initialer: "hpr", intervjuerNummer: 101616, navn: "Prydz Hilde", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "Aslakveien 16 A", postNummer: "0753", kommuneNummer: "0301", epostPrivat: "hprydz@online.no", epostJobb: "hpr@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "10.01.1995"), avtaltAntallTimer: 0, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK, sluttDato: new Date())
		intervjuer241.save(flush: true)
		Bruker bruker37 = new Bruker(navn: intervjuer241.navn, username: intervjuer241.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer241.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer241.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker37, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer38(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer242 = new Intervjuer(initialer: "krm", intervjuerNummer: 102134, navn: "Mucha Karina R", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "Ljøner", postNummer: "2233", kommuneNummer: "0420", epostJobb: "krm@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "27.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer242.save(flush: true)
		Bruker bruker38 = new Bruker(navn: intervjuer242.navn, username: intervjuer242.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer242.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer242.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker38, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer39(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer243 = new Intervjuer(initialer: "een", intervjuerNummer: 102118, navn: "Engen Else", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "11.09.1952"), gateAdresse: "", postNummer: "2219", kommuneNummer: "0402", epostJobb: "een@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer243.save(flush: true)
		Bruker bruker39 = new Bruker(navn: intervjuer243.navn, username: intervjuer243.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer243.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer243.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker39, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer40(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer244 = new Intervjuer(initialer: "jet", intervjuerNummer: 102153, navn: "Torp Jenny", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "02.08.1982"), gateAdresse: "Granli gård", postNummer: "2210", kommuneNummer: "0402", epostJobb: "jet@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON, sluttDato: new Date())
		intervjuer244.save(flush: true)
		Bruker bruker40 = new Bruker(navn: intervjuer244.navn, username: intervjuer244.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer244.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer244.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker40, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer41(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer245 = new Intervjuer(initialer: "csk", intervjuerNummer: 102149, navn: "Skårer Cecilie", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "22.01.1976"), gateAdresse: "Ullern", postNummer: "2100", kommuneNummer: "0419", epostJobb: "csk@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer245.save(flush: true)
		Bruker bruker41 = new Bruker(navn: intervjuer245.navn, username: intervjuer245.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer245.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer245.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker41, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer42(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer246 = new Intervjuer(initialer: "tar", intervjuerNummer: 102147, navn: "Rønning Turid Anneth", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "23.11.1976"), gateAdresse: "Gavstadsæter", postNummer: "2240", kommuneNummer: "0420", epostJobb: "tar@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "27.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer246.save(flush: true)
		Bruker bruker42 = new Bruker(navn: intervjuer246.navn, username: intervjuer246.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer246.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer246.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker42, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer43(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer247 = new Intervjuer(initialer: "cmg", intervjuerNummer: 102121, navn: "Giske Cia Maria", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "05.11.1979"), gateAdresse: "Rastaveien 1", postNummer: "2211", kommuneNummer: "0402", epostJobb: "cmg@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer247.save(flush: true)
		Bruker bruker43 = new Bruker(navn: intervjuer247.navn, username: intervjuer247.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer247.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer247.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker43, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer44(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer248 = new Intervjuer(initialer: "aho", intervjuerNummer: 102122, navn: "Hofossbråten Anita", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "01.01.1972"), gateAdresse: "Ekornveien 2", postNummer: "2213", kommuneNummer: "0402", epostJobb: "aho@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "27.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON, sluttDato: new Date())
		intervjuer248.save(flush: true)
		Bruker bruker44 = new Bruker(navn: intervjuer248.navn, username: intervjuer248.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer248.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer248.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker44, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer45(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer249 = new Intervjuer(initialer: "hmn", intervjuerNummer: 102138, navn: "Nygårdseter Hanne M", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "12.01.1962"), gateAdresse: "Kongevegen 89 A", postNummer: "2211", kommuneNummer: "0402", epostJobb: "hmn@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer249.save(flush: true)
		Bruker bruker45 = new Bruker(navn: intervjuer249.navn, username: intervjuer249.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer249.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer249.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker45, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer46(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer250 = new Intervjuer(initialer: "pvm", intervjuerNummer: 102133, navn: "Martinsen Vegard", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "23.04.1980"), gateAdresse: "Bjørketun", postNummer: "2116", kommuneNummer: "0419", epostJobb: "pvm@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer250.save(flush: true)
		Bruker bruker46 = new Bruker(navn: intervjuer250.navn, username: intervjuer250.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer250.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer250.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker46, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer47(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer251 = new Intervjuer(initialer: "ord", intervjuerNummer: 102136, navn: "Nordli Tor", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "26.01.1950"), gateAdresse: "Sæter", postNummer: "2213", kommuneNummer: "0402", epostJobb: "ord@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "27.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer251.save(flush: true)
		Bruker bruker47 = new Bruker(navn: intervjuer251.navn, username: intervjuer251.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer251.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer251.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker47, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer48(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer252 = new Intervjuer(initialer: "sje", intervjuerNummer: 102119, navn: "Engen Silje-Anett Aasen", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "27.12.1985"), gateAdresse: "Fiolbakken 7", postNummer: "2208", kommuneNummer: "0402", epostJobb: "sje@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer252.save(flush: true)
		Bruker bruker48 = new Bruker(navn: intervjuer252.navn, username: intervjuer252.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer252.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer252.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker48, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer49(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer253 = new Intervjuer(initialer: "saf", intervjuerNummer: 102120, navn: "Flinterud Siw", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "05.01.1970"), gateAdresse: "", postNummer: "2223", kommuneNummer: "0419", epostJobb: "saf@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "29.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer253.save(flush: true)
		Bruker bruker49 = new Bruker(navn: intervjuer253.navn, username: intervjuer253.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer253.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer253.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker49, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer50(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer254 = new Intervjuer(initialer: "tan", intervjuerNummer: 102115, navn: "Andreassen Terje", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "21.07.1949"), gateAdresse: "Øggarn av Berger", postNummer: "2230", kommuneNummer: "0420", epostJobb: "tan@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "27.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer254.save(flush: true)
		Bruker bruker50 = new Bruker(navn: intervjuer254.navn, username: intervjuer254.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer254.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer254.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker50, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer51(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer255 = new Intervjuer(initialer: "muh", intervjuerNummer: 102142, navn: "Hamidi Mustafa el", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "17.07.1980"), gateAdresse: "Hovseterveien 82 A", postNummer: "0768", kommuneNummer: "0301", epostJobb: "muh@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "21.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer255.save(flush: true)
		Bruker bruker51 = new Bruker(navn: intervjuer255.navn, username: intervjuer255.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer255.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer255.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker51 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer52(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer256 = new Intervjuer(initialer: "ago", intervjuerNummer: 102144, navn: "Ouren Agnethe", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "02.12.1986"), gateAdresse: "Monrad Johansens vei 6", postNummer: "1410", kommuneNummer: "0217", epostJobb: "ago@ssb.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "21.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer256.save(flush: true)
		Bruker bruker52 = new Bruker(navn: intervjuer256.navn, username: intervjuer256.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer256.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer256.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker52 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer53(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer257 = new Intervjuer(initialer: "frt", intervjuerNummer: 102161, navn: "Thomassen Frode", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "Thereses gate 38 B", postNummer: "0168", kommuneNummer: "0301", epostJobb: "frt@ssb.no", klynge: klynge , status: IntervjuerStatus.SLUTTET, ansattDato: Date.parse("dd.MM.yyyy", "21.09.2006"), avtaltAntallTimer: 1, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer257.save(flush: true)
		Bruker bruker53 = new Bruker(navn: intervjuer257.navn, username: intervjuer257.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer257.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer257.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker53 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer54(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer258 = new Intervjuer(initialer: "olw", intervjuerNummer: 300015, navn: "Wam Ola", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "01.12.1979"), gateAdresse: "Valhallveien 11 B", postNummer: "0196", kommuneNummer: "0301", epostPrivat: "ola_wam@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer258.save(flush: true)
		Bruker bruker54 = new Bruker(navn: intervjuer258.navn, username: intervjuer258.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer258.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer258.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker54 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer55(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer259 = new Intervjuer(initialer: "ksu", intervjuerNummer: 300004, navn: "Saupstad Kjetil", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "05.10.1985"), gateAdresse: "Vesteng 46", postNummer: "1182", kommuneNummer: "0301", epostPrivat: "kjetil.saupstad@gmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer259.save(flush: true)
		Bruker bruker55 = new Bruker(navn: intervjuer259.navn, username: intervjuer259.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer259.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer259.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker55 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer56(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer260 = new Intervjuer(initialer: "aeg", intervjuerNummer: 300001, navn: "Egeberg Anders Holth", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "10.01.1987"), gateAdresse: "Gunnar Schjeldrups vei 13 a", gateAdresse2: "leil 910", postNummer: "0485", kommuneNummer: "0301    ", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer260.save(flush: true)
		Bruker bruker56 = new Bruker(navn: intervjuer260.navn, username: intervjuer260.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer260.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer260.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker56 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer57(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer261 = new Intervjuer(initialer: "ibr", intervjuerNummer: 300013, navn: "Bragason Iris", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "28.09.1968"), gateAdresse: "Bølerlia 71", postNummer: "0691", kommuneNummer: "0301", epostPrivat: "glimre@gmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer261.save(flush: true)
		Bruker bruker57 = new Bruker(navn: intervjuer261.navn, username: intervjuer261.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer261.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer261.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker57 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer58(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer262 = new Intervjuer(initialer: "bse", intervjuerNummer: 300005, navn: "Eilertsen Benedicte Sofie", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.10.1970"), gateAdresse: "", postNummer: "0354", kommuneNummer: "0301", epostPrivat: "benedicte_sofie@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer262.save(flush: true)
		Bruker bruker58 = new Bruker(navn: intervjuer262.navn, username: intervjuer262.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer262.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer262.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker58 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer59(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer263 = new Intervjuer(initialer: "mes", intervjuerNummer: 300006, navn: "Staib Martine", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "14.08.1984"), gateAdresse: "Ivan Bjørndalsg 13A", postNummer: "0472", kommuneNummer: "0301", epostPrivat: "martinestaib@me.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer263.save(flush: true)
		Bruker bruker59 = new Bruker(navn: intervjuer263.navn, username: intervjuer263.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer263.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer263.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker59 , rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer60(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer264 = new Intervjuer(initialer: "nog", intervjuerNummer: 300007, navn: "Gjermundrød Norunn", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "23.12.1989"), gateAdresse: "Langelandsvei 60", postNummer: "2214", kommuneNummer: "0402", epostPrivat: "norunn@gjermundrod.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer264.save(flush: true)
		Bruker bruker60 = new Bruker(navn: intervjuer264.navn, username: intervjuer264.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer264.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer264.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker60, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer61(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer265 = new Intervjuer(initialer: "kbl", intervjuerNummer: 300008, navn: "Brekke Lauvrak Benedcite", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "23.07.1990"), gateAdresse: "Torshovgt. 10 a", postNummer: "0476", kommuneNummer: "0301", epostPrivat: "karoline.labre@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer265.save(flush: true)
		Bruker bruker61 = new Bruker(navn: intervjuer265.navn, username: intervjuer265.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer265.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer265.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker61, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer62(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer266 = new Intervjuer(initialer: "mne", intervjuerNummer: 300009, navn: "Olsen Marianne", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "21.11.1978"), gateAdresse: "Frydenlundgata 4", postNummer: "0169", kommuneNummer: "0301", epostPrivat: "miramaya78@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer266.save(flush: true)
		Bruker bruker62 = new Bruker(navn: intervjuer266.navn, username: intervjuer266.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer266.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer266.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker62, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer63(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer267 = new Intervjuer(initialer: "itr", intervjuerNummer: 300010, navn: "Zishan Itrat", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "19.10.1981"), gateAdresse: "Refstadsvingen 21", postNummer: "0589", kommuneNummer: "0301", epostPrivat: "Pzishan@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer267.save(flush: true)
		Bruker bruker63 = new Bruker(navn: intervjuer267.navn, username: intervjuer267.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer267.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer267.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker63, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer64(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer268 = new Intervjuer(initialer: "fui", intervjuerNummer: 300011, navn: "Ulvåen Isaksen Feliks Kristoffer", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "28.07.1991"), gateAdresse: "Oscar bråthensgt 11 B", postNummer: "0474", kommuneNummer: "0301", epostPrivat: "f.ulvaaen.isaksen@gmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer268.save(flush: true)
		Bruker bruker64 = new Bruker(navn: intervjuer268.navn, username: intervjuer268.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer268.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer268.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker64, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer65(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer269 = new Intervjuer(initialer: "mns", intervjuerNummer: 300014, navn: "Næss Skrede Maria Charlotte", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "11.11.1989"), gateAdresse: "Rosenborggata 13 c", postNummer: "0356", kommuneNummer: "0301", epostPrivat: "maria.skrede@gmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer269.save(flush: true)
		Bruker bruker65 = new Bruker(navn: intervjuer269.navn, username: intervjuer269.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer269.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer269.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker65, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer66(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer270 = new Intervjuer(initialer: "sgo", intervjuerNummer: 765432, navn: "Agourram Salua", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "04.06.1989"), gateAdresse: "Ammerudveien 62", postNummer: "0958", kommuneNummer: "0301    ", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer270.save(flush: true)
		Bruker bruker66 = new Bruker(navn: intervjuer270.navn, username: intervjuer270.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer270.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer270.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker66, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer67(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer271 = new Intervjuer(initialer: "ram", intervjuerNummer: 300016, navn: "Amundsen Ranveig", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "15.03.1950"), gateAdresse: "ornagatan 11 C", postNummer: "2212", kommuneNummer: "0402", epostPrivat: "raneig.amundsen@yahoo.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer271.save(flush: true)
		Bruker bruker67 = new Bruker(navn: intervjuer271.navn, username: intervjuer271.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer271.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer271.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker67, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer68(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer272 = new Intervjuer(initialer: "ril", intervjuerNummer: 300017, navn: "Langlo Risten", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "12.10.1967"), gateAdresse: "Vangenspissen 36", postNummer: "2212", kommuneNummer: "0402", epostPrivat: "ristenl@yahoo.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer272.save(flush: true)
		Bruker bruker68 = new Bruker(navn: intervjuer272.navn, username: intervjuer272.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer272.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer272.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker68, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer69(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer273 = new Intervjuer(initialer: "mee", intervjuerNummer: 300018, navn: "Andersen Mette", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "12.11.1968"), gateAdresse: "Brattvold", postNummer: "2110", kommuneNummer: "0419", epostPrivat: "mar-a6@online.no", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer273.save(flush: true)
		Bruker bruker69 = new Bruker(navn: intervjuer273.navn, username: intervjuer273.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer273.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer273.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker69, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer70(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer274 = new Intervjuer(initialer: "rua", intervjuerNummer: 300019, navn: "Ruud Albert Antje", kjonn: Kjonn.MANN, fodselsDato: Date.parse("dd.MM.yyyy", "27.09.1955"), gateAdresse: "Duåsen", postNummer: "2100", kommuneNummer: "0419", epostJobb: "alberjeruud1@hotmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer274.save(flush: true)
		Bruker bruker70 = new Bruker(navn: intervjuer274.navn, username: intervjuer274.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer274.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer274.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker70, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer71(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer275 = new Intervjuer(initialer: "dea", intervjuerNummer: 300020, navn: "Delerud Annie", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "10.03.1958"), gateAdresse: "Nypevegen 13", postNummer: "2209", kommuneNummer: "0402", epostPrivat: "anniedele@gmail.com", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "11.01.2011"), avtaltAntallTimer: 500, lokal: false, arbeidsType: IntervjuerArbeidsType.TELEFON)
		intervjuer275.save(flush: true)
		Bruker bruker71 = new Bruker(navn: intervjuer275.navn, username: intervjuer275.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer275.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer275.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker71, rolle: rolleIntervjuer).save(flush: true)
	}
	 
	private void lagreIntervjuer72(Klynge klynge, Rolle rolleIntervjuer) {
		Intervjuer intervjuer276 = new Intervjuer(initialer: "aee", intervjuerNummer: 333654, navn: "Ellingsvold Anne", kjonn: Kjonn.KVINNE, fodselsDato: Date.parse("dd.MM.yyyy", "30.07.1957"), gateAdresse: "Østrengveien 18 B", postNummer: "1405", kommuneNummer: "0213    ", klynge: klynge , status: IntervjuerStatus.AKTIV, ansattDato: Date.parse("dd.MM.yyyy", "16.09.1975"), avtaltAntallTimer: 1, lokal: true, arbeidsType: IntervjuerArbeidsType.BESOK)
		intervjuer276.save(flush: true)
		Bruker bruker72 = new Bruker(navn: intervjuer276.navn, username: intervjuer276.initialer, password: springSecurityService.encodePassword('S1vtest00'), accountExpired: (intervjuer276.status == IntervjuerStatus.SLUTTET ?: false), enabled: (intervjuer276.status == IntervjuerStatus.SLUTTET ? false : true) ).save(flush: true)
		new BrukerRolle(bruker: bruker72, rolle: rolleIntervjuer).save(flush: true)
	}
	
	
	

}
