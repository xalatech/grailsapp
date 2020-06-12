package sivadm

import siv.type.AvtaleType

class Avtale {
    AvtaleType avtaleType
    Date dateStart
    String timeStart
    Date dateEnd
    String timeEnd
    String weekDays
    String whoMade
    String avtaleMelding
    IntervjuObjekt intervjuObjekt

    static belongsTo = [intervjuObjekt: IntervjuObjekt]

    static constraints = {
        dateStart(nullable: true)
        timeStart(nullable: true)
        dateEnd(nullable: true)
        timeEnd(nullable: true)
        weekDays(nullable: true)
        whoMade(nullable: true, blank: true)
        avtaleMelding(nullable: true)
        intervjuObjekt(nullable: false)
    }

    static mapping  = {
        id column: 'ID', generator: 'sequence',  params:[sequence:'avtale_seq'],  sqlType: 'integer'
        avtaleType index: 'avt_type_indeks'
        whoMade index: 'avt_whoMade_indeks'
        weekDays index: 'avt_weekDays_indeks'
    }

    String toString() {
        return avtaleType.toString() + ", " + dateStart + " "  + timeStart + " " + dateEnd + " " + timeEnd + " " + weekDays + " " + whoMade + " " + avtaleMelding + " " + intervjuObjekt
    }
}
