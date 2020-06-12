package siv.util.xml

class RestResponseData {
	
	Boolean suksess
	String feilType
	String melding
	
	public RestResponseData() {
	}

	public RestResponseData(Boolean suksess, String melding) {
		this.suksess = suksess;
		this.melding = melding;
	}
	
	public RestResponseData(Boolean suksess, String feilType, String melding) {
		this.suksess = suksess;
		this.feilType = feilType;
		this.melding = melding;
	}
}
