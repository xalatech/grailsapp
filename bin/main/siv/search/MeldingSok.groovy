package siv.search

import siv.type.MeldingInnType;

public class MeldingSok {
	String ioNr
	Long ioId
	Date fra = new Date()
	Date til = new Date()
	String status
	MeldingInnType meldingInnType	
	
	static constraints = {
		ioNr nullable: true
		ioId nullable: true
		fra nullable: true
		til nullable: true
		status nullable: true
		meldingInnType nullable: true
	}
}