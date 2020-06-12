package siv.data.blaise

class IntervjuStatusData {

	Long intervjuObjektId
	String intervjuObjektNummer
	String skjemaKortNavn
	Integer intervjuStatus

	boolean validate() {
		if( intervjuObjektId == null || intervjuStatus == null ) {
			return false
		}
		else {
			return true
		}
	}
}
