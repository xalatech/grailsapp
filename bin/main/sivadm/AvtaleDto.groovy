package sivadm

class AvtaleDto {
    String avtaleType
    String dateStart
    String timeStart
    String dateEnd
    String timeEnd
    String weekDays
    String whoMade
    String avtaleMelding

    static constraints = {
        dateStart(nullable: true)
        timeStart(nullable: true)
        dateEnd(nullable: true)
        timeEnd(nullable: true)
        weekDays(nullable: true)
        whoMade(nullable: true, blank: true)
        avtaleMelding(nullable: true)
    }

    static belongsTo = IntervjuObjekt

    static mapping  = {
        id column: 'ID', generator: 'sequence',  params:[sequence:'avtale_seq'],  sqlType: 'integer'
        avtaleType index: 'avt_type_indeks'
        whoMade index: 'avt_whoMade_indeks'
        weekDays index: 'avt_weekDays_indeks'
    }

    String toString() {
        return avtaleType.toString() + ", " + dateStart + " "  + timeStart + " " + dateEnd + " " + timeEnd + " " + weekDays + " " + whoMade + " " + avtaleMelding
    }

    static boolean isHardAvtale(AvtaleDto avtale) {
        if(avtale.dateStart != null && !avtale.dateStart.isEmpty() && avtale.avtaleType == "2") {
            return true
        }

        return false
    }
}
