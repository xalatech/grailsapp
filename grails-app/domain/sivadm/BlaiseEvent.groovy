package sivadm


import siv.type.SkjemaStatus

class BlaiseEvent {
    Integer id
    Integer blaiseEventId
    String ioId
    String eventName
    String sessionId
    String resumedSessionID
    String instrumentId
    String mainInstrumentId
    String primaryKeyValue
    String interviewer
    Integer intervjustatus
    String reason
    String status
    String rowStatus
    String kilde
    String meldingFraIntervjuer
    String intervjuer
    String timeStamp
    Boolean active

    Integer resolveIntervjustatus() {
        if (eventName == "StartSessionEvent" || eventName == "KeyvalueDeterminedEvent") {
            return null
        }

        if (intervjustatus != null) {
            if (intervjustatus == 1) {
                //Status 1 i Blaise skal for tiden kodes som 0 i Sivadmin.
                return 0;
            } else {
                return intervjustatus
            }
        } else {
            return eventName == "EndSessionEvent" && reason == "Completed" ? 0 : null
        }
    }

    SkjemaStatus getSkjemaStatus() {
        if (eventName.equals("StartSessionEvent")) {
            return SkjemaStatus.Pabegynt
        } else if (eventName.equals("EndSessionEvent")) {
            return  (10..89).contains(intervjustatus.intValue()) ? SkjemaStatus.Ubehandlet : SkjemaStatus.Ferdig
        } else if (eventName.equals("KeyvalueDeterminedEvent")) {
            return SkjemaStatus.Pabegynt
        } else {
            return null
        }
    }

    static constraints = {

    }
}
