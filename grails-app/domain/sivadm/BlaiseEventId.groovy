package sivadm

class BlaiseEventId {

    Integer id
    Long blaiseId
    Date opprettet
    Bruker opprettetAv
    Date sistEndret
    Bruker sistEndretAv

    static constraints = {
        blaiseId nullable:false
    }

    @Override
    public String toString() {
        return "BlaiseEventId{" +
                "id=" + id +
                ", blaiseId=" + blaiseId +
                '}';
    }
}
