function fullforKlokkeslett(strengen) {
	var streng = strengen;
	if (streng.length == 1) {
		streng = "0" + streng + ":00";
	}
	else if (streng.length == 2) {
		streng = streng + ":00";
	}
	else if (streng.length == 3) {
		//if (streng.substring(0,2) > "09" && streng.substring(0,2) < "24") {
		// THH 14.11.2013: Mer naturlig på denne maaten, tror jeg.
		if (streng.substring(0,2) < "24") {
			streng = streng.substring(0,2) + ":" + streng.substring(2) + "0";
		}
		else {
			streng = "0" + streng.substring(0,1) + ":" + streng.substring(1);
		}
	}
	else if (streng.length == 4) {
		streng = streng.substring(0,2) + ":" + streng.substring(2);
	}

	return streng;
}