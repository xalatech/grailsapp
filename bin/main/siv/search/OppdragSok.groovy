package siv.search

class OppdragSok {
	String ioNr
	String skjemaKortNavn
	String klarTilSynk
	Long ioId
	Long intervjuerId
	
	static constraints = {
		ioNr nullable: true
		skjemaKortNavn nullable: true
		klarTilSynk nullable: true
		ioId nullable: true
		intervjuerId nullable: true
	}
}
