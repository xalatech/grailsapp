package sivadm

class UtleggService {

    static transactional = true

    def grailsApplication

    def ryddUtlegg = {

        String antallAar = grailsApplication.config.behold.utlegg.antall.aar
        log.info("Kjører ryddUtlegg, sletter utlegg eldre enn " + antallAar + " år")

        Calendar cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, (-1 * Integer.parseInt(antallAar)))

        slettUtleggEldreEnn(cal.getTime())
    }

    def slettUtleggEldreEnn(Date dato)  {
        try {
            Utlegg.executeUpdate("delete from Utlegg where dato < :dato", [dato: dato])
        } catch (Exception e) {
            log.error('Kunne ikke slette utlegg eldre enn dato ' + dato);
        }
        log.info('Har slettet utlegg med dato eldre enn ' + dato)
    }
}
