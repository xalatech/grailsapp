package sivadm

import siv.rapport.data.KlyngeResultat

class RapportKlyngeService {

    static transactional = true

    def hentRapportForAlleKlynger(Skjema skjema, Date startDato, Date sluttDato) {
        def klynger = Klynge.findAll().unique {
            it.klyngeNavn
        }
        return hentRapport(klynger, skjema, startDato, sluttDato)
    }

    def hentRapportForKlynge(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato) {
        def klynger = []
        klynger.add(klynge)
        return hentRapport(klynger, skjema, startDato, sluttDato)
    }

    def hentRapportForIntervjuer(Intervjuer intervjuer, Skjema skjema, Date startDato, Date sluttDato) {
        def klynger = []
        klynger.add(intervjuer.klynge)
        return hentRapport(klynger, skjema, startDato, sluttDato)
    }

    def hentRapport(List klyngeList, Skjema singleSkjema, Date startDato, Date sluttDato) {

        List resultatList = []

        KlyngeResultat resultatSummer = new KlyngeResultat()
        resultatSummer.klyngenavn = 'Summer'
        resultatSummer.skjemaNavn = ' '

        klyngeList.each { Klynge klynge ->
            List skjemaList = []

            if (singleSkjema) {
                skjemaList.add(singleSkjema)
            } else {
                skjemaList = finnSkjema(klynge, startDato, sluttDato)
                if (skjemaList.size() == 0) {
                    skjemaList.add(null)
                }
            }

            skjemaList.each { Skjema skjema ->
                KlyngeResultat skjemaResultat = new KlyngeResultat()
                skjemaResultat.klyngenavn = klynge.klyngeNavn
                skjemaResultat.skjemaNavn = skjema ? skjema.skjemaNavn : ' '

                int totaltAntall = finnAntallTotalt(klynge, skjema, startDato, sluttDato)

                if( totaltAntall > 0 ) {
                    skjemaResultat.totaltAntall        = totaltAntall
                    skjemaResultat.antallAndreAarsaker = finnAntallAndreAarsaker(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallAvganger      = finnAntallAvganger(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallForhindret    = finnAntallForhindret(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallIkkeTruffet   = finnAntallIkkeTruffet(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallIntervju      = finnAntallIntervju(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallNektere       = finnAntallNektere(klynge, skjema, startDato, sluttDato)
                    skjemaResultat.antallOverforinger  = finnAntallOverforinger(klynge, skjema, startDato, sluttDato)
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
            a.klyngenavn <=> b.klyngenavn ?: a.skjemaNavn <=> b.skjemaNavn
        }
        resultatList.add(resultatSummer)

        resultatList
    }

    def finnSkjema (Klynge klynge,  Date startDato, Date sluttDato ) {

        def c = IntHist.createCriteria()
        def skjemaList = c {
            intervjuObjekt {
                periode {
                    projections { distinct('skjema')}
                }
            }
            intervjuer {
                eq('klynge', klynge)
                }

            ge("registrertDato", startDato)
            le("registrertDato", sluttDato)
        }

        skjemaList
    }

    def finnAntallTotalt(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        def c = IntHist.createCriteria()
        def list = c {
            projections {
                countDistinct("id")
            }
            intervjuer {
                eq('klynge', klynge)
            }

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
        list.get(0)
    }

    def finnAntall( Klynge klynge, List intervjuStatusList, Skjema skjema, startDato, sluttDato ) {
        def c = IntHist.createCriteria()
        def list = c {
            projections {
                countDistinct("id")
            }
            intervjuer {
                eq('klynge', klynge)
            }

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
        list.get(0)
    }

    def finnAntallAvganger(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, 91..99, skjema, startDato, sluttDato)
    }

    def finnAntallAndreAarsaker (Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, [41], skjema, startDato, sluttDato)
    }

    def finnAntallForhindret(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, 21..32, skjema, startDato, sluttDato)
    }

    def finnAntallIntervju(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, [0], skjema, startDato, sluttDato)
    }

    def finnAntallIkkeTruffet(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, 33..39, skjema, startDato, sluttDato)
    }

    def finnAntallOverforinger(Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, 80..89, skjema, startDato, sluttDato)
    }

    def finnAntallNektere( Klynge klynge, Skjema skjema, Date startDato, Date sluttDato ) {
        return finnAntall(klynge, 11..19, skjema, startDato, sluttDato)
    }
}