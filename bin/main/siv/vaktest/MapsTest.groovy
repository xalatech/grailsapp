package siv.vaktest

/**
 * Created by vak on 30.11.2016.
 */
class MapsTest {

    // koden må transformere ioList til expected struktur (map)
    //
    // key = ioNummer
    // value = Liste med id'er til IO'er som har samme ioNummer som key
    // id til IO som er knyttet til skjemaId 21 skal komme først i listen.
    //

    // SE http://mrhaki.blogspot.no/2015/03/groovy-goodness-new-methods-to-sort-and.html

    def transform(def ioList, int skjemaId){

        def result = ioList.collectEntries {

            def ioNummer = it.ioNummer
            def sortedIdListBySkjema = ( ( ioList.findAll{ it.ioNummer == ioNummer }
                    .sort{ a, b -> a.skjema == skjemaId ? -1 : 1 }
                    .id ) )

            [ ( ioNummer )  : ( sortedIdListBySkjema ) ]
        }
        result.sort { it.key }
    }

    static void main(String[] args) {


        int skjemaId = 21
        int assosiertSkjemaId = 31

        def io1 = new IntervjuObjekt(id:1, ioNummer:11, skjema:skjemaId)
        def io2 = new IntervjuObjekt(id:2, ioNummer:12, skjema:skjemaId)
        def io3 = new IntervjuObjekt(id:3, ioNummer:13, skjema:skjemaId)

        def io4 = new IntervjuObjekt(id:4, ioNummer:11, skjema:assosiertSkjemaId)
        def io5 = new IntervjuObjekt(id:5, ioNummer:12, skjema:assosiertSkjemaId)
        def io6 = new IntervjuObjekt(id:6, ioNummer:13, skjema:assosiertSkjemaId)

        def expected =
                [
                        11 : [ 1, 4 ],
                        12 : [ 2, 5 ],
                        13 : [ 3, 6 ]
                ]


        MapsTest m = new MapsTest()

        def ioList
        def result

        ioList = [io1, io2, io3, io4, io5, io6]
        result = m.transform(ioList, skjemaId)
        println result
        assert result == expected

        ioList = [io1, io4, io3, io2, io6, io5]
        result = m.transform(ioList, skjemaId)
        println result
        assert result == expected


        def xio1 = new IntervjuObjekt(id:6, ioNummer:13, skjema:assosiertSkjemaId)
        def xio2 = new IntervjuObjekt(id:5, ioNummer:12, skjema:assosiertSkjemaId)
        def xio3 = new IntervjuObjekt(id:4, ioNummer:11, skjema:assosiertSkjemaId)

        def xio4 = new IntervjuObjekt(id:3, ioNummer:13, skjema:skjemaId)
        def xio5 = new IntervjuObjekt(id:2, ioNummer:12, skjema:skjemaId)
        def xio6 = new IntervjuObjekt(id:1, ioNummer:11, skjema:skjemaId)

        ioList = [xio1, xio2, xio3, xio4, xio5, xio6]
        result = m.transform(ioList, skjemaId)
        println result
        assert result == expected

    }

}

class IntervjuObjekt{
    int id
    int ioNummer
    int skjema
}




