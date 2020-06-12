package siv

/**
 * Created by vak on 29.11.2016.
 */
class MapsTest {


    static void main(String[] args) {

        int skjemaId = 21
        int assosiertSkjemaId = 31

        def io1 = new IntervjuObjekt(id:1, ioNummer:11, skjema:skjemaId)
        def io2 = new IntervjuObjekt(id:2, ioNummer:12, skjema:skjemaId)
        def io3 = new IntervjuObjekt(id:3, ioNummer:13, skjema:skjemaId)

        def io4 = new IntervjuObjekt(id:4, ioNummer:11, skjema:assosiertSkjemaId)
        def io5 = new IntervjuObjekt(id:5, ioNummer:12, skjema:assosiertSkjemaId)
        def io6 = new IntervjuObjekt(id:6, ioNummer:13, skjema:assosiertSkjemaId)

        def ioList = [io1, io2, io3, io4, io5, io6]

        // Skriv kode her

        println 'her kommer koden'






        // koden må transformere ioList til expected struktur (map)
        //
        // key = ioNummer
        // value = Liste med id'er til IO'er som har samme ioNummer som key
        // id til IO som er knyttet til skjemaId 21 skal komme først i listen.
        //

        def expected =
                [
                        11 : [ 1, 4 ],
                        12 : [ 2, 5 ],
                        13 : [ 3, 6 ]
                ]
    }
}

class IntervjuObjekt{
    int id
    int ioNummer
    int skjema
}
