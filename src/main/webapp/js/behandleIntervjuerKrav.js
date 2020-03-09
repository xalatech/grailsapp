function settId(feltNavn, id) {
	var felt = document.getElementById(feltNavn);
	if(felt) {
		felt.value = id;
	}
}

function seKrav(skjemaNavn, erForrige) {
	var skjema = document.getElementById(skjemaNavn);
	if(skjema) {
		var elem  = skjema.elements;
	
		var idField = null;
		var nyVerdi = null
		for(var i in elem) {
			if(elem[i].id == "id") {
				idField = elem[i];	
			}
			if( (erForrige && elem[i].id == "idForrige") || (!erForrige && elem[i].id == "idNeste") ) {
				nyVerdi = elem[i].value;
			}
		}
		idField.value = nyVerdi;
		skjema.submit();
	}
}

function settForrigeNeste(erFeilet, id) {
	var form = null;
	if(erFeilet) {
		form = document.getElementById("failedForm");
	}
	else {
		form = document.getElementById("kravForm");
	}

	if(form && id) {
		var idF = id;
		var idN = id;
		var settNeste = false;
		
		for(var i in form) {
			if(form[i] != null && form[i].type == "checkbox") {
				if(settNeste) {
					idN = form[i].value;
					break;
				}

				if(form[i].value == id) {
					settNeste = true;
				}
				else if(!settNeste) {
					idF = form[i].value;
				}
			}
		}
		settId("idForrige", idF);
		settId("idNeste", idN);
	}
}

function markerAlleFeilet() {
	var divAlle = document.getElementById("mAlleFeilet");
	var b = false;
	if(divAlle.innerHTML == "Marker alle") {
		divAlle.innerHTML = "Fjern markering";
		b = true;
	}
	else {
		divAlle.innerHTML = "Marker alle";
	}
	
	var iForm = document.getElementById("failedForm");
	var elem  = iForm.elements;
	
	for(var i in elem) {
		if(elem[i].type == "checkbox") {
			elem[i].checked = b;
		}
	}
}

function markerAlle() {
	var divAlle = document.getElementById("mAlle");
	var b = false;
	if(divAlle.innerHTML == "Marker alle") {
		divAlle.innerHTML = "Fjern markering";
		b = true;
	}
	else {
		divAlle.innerHTML = "Marker alle";
	}
	
	var iForm = document.getElementById("kravForm");
	var elem  = iForm.elements
	
	for(var i in elem) {
		if(elem[i].type == "checkbox") {
			elem[i].checked = b;
		}
	}
}