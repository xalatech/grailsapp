package util

import javax.xml.bind.DatatypeConverter

class BasicAuthUtil {
	
	public static boolean erAutentisert(request, brukernavn, passord) {
		def authHeader = request.getHeader("Authorization");
		def authBrukernavn
		def authPassord
		if(authHeader) {
			String base64Credentials = authHeader.substring("Basic".length()).trim();
			String credentials = new String(DatatypeConverter.parseBase64Binary(base64Credentials));
			def creds = credentials.split(":",2);
			authBrukernavn = creds[0]
			authPassord = creds[1]
		}
		if(authBrukernavn == brukernavn && authPassord == passord) {
			return true
		} else {
			return false
		}
	}
}