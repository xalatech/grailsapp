package sivadm

class FravaerService {

    def grailsApplication

    static transactional = false

    def ryddFravaer() {
        log.info("Kjører ryddFravaer, sletter fravær med sluttdato eldre enn " + grailsApplication.config.getProperty("behold.fravaer.antall.aar") + " år")

        Calendar cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, (-1 * grailsApplication.config.getProperty("behold.fravaer.antall.aar")))

        slettFravaerMedSluttdatoEldreEnnDato(cal.getTime())
    }

    public int slettFravaerMedSluttdatoEldreEnnDato(Date dato) {
        def fravaerTilSlettingListe = Fravaer.findAllByTilTidspunktLessThan(dato)
        int cnt = 0
        fravaerTilSlettingListe.each {
            try {
                it.delete(flush: true)
                cnt++
            }
            catch(Exception ex) {
                log.error("Kunne ikke slette Fravaer med id " + it.id + ", " + ex.getMesssage())
            }
        }

        log.info("Har slettet " + cnt + " Fravaer med sluttdato eldre enn " + dato)

        return cnt
    }

}
