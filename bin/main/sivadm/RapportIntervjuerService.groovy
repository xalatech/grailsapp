package sivadm

import siv.rapport.data.IntervjuerResultat;
import siv.type.IntervjuerStatus;

class RapportIntervjuerService {

	def intervjuerService
	
    static transactional = true

	public List hentRapportForIntervjuer(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato) {
		def intervjuere = []
		intervjuere.add(intervjuer)
		return hentRapport(intervjuere, skjema, startDato, sluttDato )
	}
	
	public List hentRapportForAlleIntervjuere(Skjema skjema, Date startDato, Date sluttDato) {
		def intervjuere = intervjuerService.finnAktiveIntervjuere()
		return hentRapport(intervjuere, skjema, startDato, sluttDato)
	}
	
	public List hentRapportForKlynge(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato) {
		def intervjuere = Intervjuer.findAllByKlyngeAndStatus(klynge, IntervjuerStatus.AKTIV)
		return hentRapport(intervjuere, skjema, startDato, sluttDato)
	}

	public List hentRapportForHeleLandet(Skjema skjema, Date startDato, Date sluttDato) {
		def intervjuere = Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV)
		return hentRapport(intervjuere, skjema, startDato, sluttDato)
	}
	
	public List hentRapport(List intervjuerList, Skjema singleSkjema, Date startDato, Date sluttDato) {
		List resultatList = []

		IntervjuerResultat resultatSummer = new IntervjuerResultat()
		resultatSummer.navn = 'Summer'
		resultatSummer.initialer = ' '
		resultatSummer.skjemaNavn = ' '
		
		intervjuerList.each { Intervjuer intervjuer ->
			List skjemaList = []

			if (singleSkjema) {
				skjemaList.add(singleSkjema)
			} else {
				skjemaList = finnSkjema(intervjuer, startDato, sluttDato)
				if (skjemaList.size() == 0) {
					skjemaList.add(null)
				}
			}

			skjemaList.each { Skjema skjema ->
				IntervjuerResultat skjemaResultat = new IntervjuerResultat()
				skjemaResultat.navn = intervjuer.navn
				skjemaResultat.initialer = intervjuer.initialer
				skjemaResultat.skjemaNavn = skjema ? skjema.skjemaNavn : ' '

				int totaltAntall = finnAntallTotalt(intervjuer, skjema, startDato, sluttDato)

				if( totaltAntall > 0 ) {
					skjemaResultat.totaltAntall        = totaltAntall
					skjemaResultat.antallAndreAarsaker = finnAntallAndreAarsaker(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallAvganger      = finnAntallAvganger(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallForhindret    = finnAntallForhindret(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallIkkeTruffet   = finnAntallIkkeTruffet(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallIntervju      = finnAntallIntervju(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallNektere       = finnAntallNektere(intervjuer, skjema, startDato, sluttDato)
					skjemaResultat.antallOverforinger  = finnAntallOverforinger(intervjuer, skjema, startDato, sluttDato)
					resultatSummer.totaltAntall           += skjemaResultat.totaltAntall
					resultatSummer.antallAndreAarsaker    += skjemaResultat.antallAndreAarsaker
					resultatSummer.antallAvganger         += skjemaResultat.antallAvganger
					resultatSummer.antallForhindret       += skjemaResultat.antallForhindret
					resultatSummer.antallIkkeTruffet      += skjemaResultat.antallIkkeTruffet
					resultatSummer.antallIntervju         += skjemaResultat.antallIntervju
					resultatSummer.antallNektere          += skjemaResultat.antallNektere
					resultatSummer.antallOverforinger     += skjemaResultat.antallOverforinger

				}
				resultatList.add(skjemaResultat)
			}
		}
		resultatList.sort { a,b ->
			a.navn <=> b.navn ?: a.skjemaNavn <=> b.skjemaNavn
		}
		resultatList.add(resultatSummer)
		
		return resultatList
    }
	
	
	protected int finnAntallNektere( Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {		
		return finnAntall(intervjuer, 11..19, skjema, startDato, sluttDato)	
	}	
	
	
	protected int finnAntallAndreAarsaker (Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, [41], skjema, startDato, sluttDato)
	}
	
	protected int finnAntallAvganger(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, 91..99, skjema, startDato, sluttDato)
	}
	
	protected int finnAntallForhindret(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, 21..32, skjema, startDato, sluttDato)
	}
	
	protected int finnAntallIntervju(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, [0], skjema, startDato, sluttDato)
	}
	
	protected int finnAntallIkkeTruffet(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, 33..39, skjema, startDato, sluttDato)
	}
	
	protected int finnAntallOverforinger(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		return finnAntall(intervjuer, 80..89, skjema, startDato, sluttDato)
	}
	
	protected int finnAntallTotalt(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato ) {
		def c = IntHist.createCriteria()
		def list = c {
			projections {
				countDistinct("id")
			}
			eq("intervjuer", intervjuer)
			
			ge("registrertDato", startDato)
			le("registrertDato", sluttDato)
			
			if( skjema != null ) {
				intervjuObjekt {
					periode {
						eq("skjema", skjema)
					}
				}
			}
		}
		return list.get(0)
	}
	
	protected int finnAntall( Intervjuer intervjuer, List intervjuStatusList, Skjema skjema, startDato, sluttDato ) {
		def c = IntHist.createCriteria()
		def list = c {
			projections {
				countDistinct("id")
			}
			eq("intervjuer", intervjuer)
			
			ge("registrertDato", startDato)
			le("registrertDato", sluttDato)
			
			'in'("intervjuStatus", intervjuStatusList)
			
			if( skjema ) {
			
			intervjuObjekt {
				periode {
					eq("skjema", skjema)
				}
			}
				
			}
		}
		return list.get(0)
	}

	protected List<Skjema> finnSkjema ( Intervjuer intervjuer,  Date startDato, Date sluttDato ) {
		def c = IntHist.createCriteria()
		def skjemaList = c {
			intervjuObjekt {
				periode {
					projections { distinct('skjema')}
				}
			}
			ge("registrertDato", startDato)
			le("registrertDato", sluttDato)
			eq('intervjuer', intervjuer)
		}

		return skjemaList
	}
}
