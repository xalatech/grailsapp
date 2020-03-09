function handterKomma(feltet) {
	var kmFelt = document.getElementById(feltet);
	var streng = kmFelt.value;
	
	var posisjon = streng.indexOf(",");
	if (posisjon < 0) {
		posisjon = streng.indexOf(".");
	}
	
	if (posisjon >= 0) {
		streng = streng.substring(0,posisjon);
	}

	kmFelt.value = streng;
}