function kolon(hvilket) {
	var tidfelt = document.getElementById(hvilket);
	var streng = tidfelt.value;
	streng = fullforKlokkeslett(streng);
	tidfelt.value = streng;
}