package sivadm

import util.TimeUtil

class RapportKlyngeController {

    def skjemaService
    def rapportKlyngeService

    def visRapport = {
        ResultatrapportKlyngerDatoCommand resultatrapportKlyngerDatoCommand = new ResultatrapportKlyngerDatoCommand()
        def startDato = resultatrapportKlyngerDatoCommand.startDato
        def sluttDato = resultatrapportKlyngerDatoCommand.sluttDato

        // Setter dato til naa hvis ikke oppgitt
        if( !startDato ) {
            startDato = new Date()
        }

        // Setter dato til naa hvis ikke oppgitt
        if( !sluttDato ) {
            sluttDato = new Date()
        }

        startDato = TimeUtil.getStartOfDay(startDato)
        sluttDato = TimeUtil.getEndOfDay(sluttDato)

        def skjema = null

        if( params.skjema ) {
            skjema = Skjema.get(Long.parseLong( params.skjema))
        }

        def klyngeResultatList = null

        if( ! params.ikkeGenerer ) {
            if( params.initialer ) {
                Intervjuer intervjuer = Intervjuer.findByInitialer(params.initialer.toLowerCase())
                if (intervjuer) {
                    klyngeResultatList = rapportKlyngeService.hentRapportForIntervjuer(intervjuer, skjema, startDato, sluttDato)
                    params.klynge = null
                }
            }
            else if( params.klynge ) {
                Klynge klynge = Klynge.get(Long.parseLong( params.klynge ))
                klyngeResultatList = rapportKlyngeService.hentRapportForKlynge(klynge, skjema, startDato, sluttDato)
            }
            else {
                klyngeResultatList = rapportKlyngeService.hentRapportForAlleKlynger(skjema, startDato, sluttDato)
            }
        }

        def klyngeList = Klynge.list()
        def skjemaList = skjemaService.finnAlleSortertPaaOppstart()

        return [
                klyngeResultatList: klyngeResultatList,
                klyngeList: klyngeList,
                initialer: params.initialer,
                klyngeId: params.klynge,
                startDato: startDato,
                sluttDato: sluttDato,
                skjemaList: skjemaList,
                skjemaId: params.skjema
        ]
    }

}

class ResultatrapportKlyngerDatoCommand implements grails.validation.Validateable {
    Date startDato
    Date sluttDato
}