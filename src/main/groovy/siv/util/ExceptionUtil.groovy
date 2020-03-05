package siv.util

import siv.util.xml.RestResponseData;

class ExceptionUtil {
	
	static RestResponseData getResponseDataForError( Exception e) {
		RestResponseData restResponse = new RestResponseData()
		restResponse.suksess = false
		restResponse.feilType = e.getClass().toString()
		restResponse.melding = e.getMessage()
		return restResponse
	}
	
}
