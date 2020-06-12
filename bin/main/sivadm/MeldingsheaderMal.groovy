package sivadm

class MeldingsheaderMal {

    String malNavn
    String meldingsheader
    Date opprettet
    Bruker opprettetAv
    Date sistEndret
    Bruker sistEndretAv

    static constraints = {
        malNavn(blank: false, nullable: false)
        meldingsheader(blank: false, nullable: false, maxSize: 150)
        opprettet(blank: false, nullable: false)
        opprettetAv(blank:false, nullable: false)
        sistEndret(blank: true, nullable: true)
        sistEndretAv(blank: true, nullable: true)
    }

    static mapping  = {
        id column: 'ID', generator: 'sequence',  params:[sequence:'meldingsheader_mal_seq'],  sqlType: 'integer'
    }

}
