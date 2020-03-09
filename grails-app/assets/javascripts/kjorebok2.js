/**
 *  Endringer i kjorebok.js virka ikke ved overføring til serveren ktli-jboss-t01, så jeg oppretta denne
 *  kopien av kjorebok.js for å få det til. Da gikk alt greit....
 *  Hvis jeg en dag finner ut av hvorfor kjorebok.js ikke virka, kan denne fjernes.
 */
function ioEndret(e) {
	var ioAdr = eval("(" + e.responseText + ")")	// evaluate JSON
	
	if(ioAdr) {
		var adrFelt = document.getElementById("tilAdresse");
		var postFelt = document.getElementById("tilPoststed");
		var prodSelect = document.getElementById("produktNummer");
		
		if(adrFelt) {
			adrFelt.value = ioAdr.adresse;
		}
		if(postFelt) {
			postFelt.value = ioAdr.poststed;
		}
		
		if(ioAdr.produktNummer && prodSelect) {
			for(i=0; i<prodSelect.length; i++) {
				if(prodSelect[i].value == ioAdr.produktNummer) {
					prodSelect[i].selected = true;
					break;
				}
			}
		}
	}
}


function initAdresse() {
	var io = document.getElementById("intervjuobjekt.id");
	
	if(io && io.options.length > 0) {
		var ioId = io.options[io.selectedIndex].value;
		
		if(ioId) {
			new Ajax.Request('/sivadmin/intervjuObjekt/ajaxGetIntervjuObjektAdresse',{asynchronous:true,evalScripts:true,onComplete:function(e){ioEndret(e)},parameters:'id=' + ioId});
		}
	}
}


function transportmiddelEndret() {
	var transport = document.getElementById("transportmiddel");
	if(transport) {
		var antPass = document.getElementById("antallPassasjerer");
		var km = document.getElementById("kjorteKilometer");
		var belop = document.getElementById("belop");
		var ferje = document.getElementById("ferje");
		if(transport.value == "EGEN_BIL") {
			antPass.disabled = false;
			antPass.readOnly = false;
			km.readOnly = false;
			belop.value = "";
			belop.readOnly = true;
			belop.disabled = true;
			belop.value = "";
		}
		else if(transport.value == "MOTOR_BAAT"
			|| transport.value == "MOTORSYKKEL"
			|| transport.value == "SNOSCOOTER"
			|| transport.value == "MOPED_SYKKEL") {

			km.readOnly = false;
			antPass.value = 0;
			antPass.readOnly = true;
			antPass.disabled = true;
			belop.value = "";
			belop.readOnly = true;
			belop.disabled = true;
			belop.value = "";
		}
		else {
			// LEIEBIL, BUSS_TRIKK, TOG, FERJE, TAXI
			km.value = 0;
			km.readOnly = true;
			antPass.value = 0;
			antPass.readOnly = true;
			antPass.disabled = true;
			if(transport.value == "FERJE") {
				ferje.value = 0;
				ferje.readOnly = false;
				ferje.disabled = false;
			}
			else {
				ferje.value = "";
				ferje.readOnly = true;
				ferje.disabled = true;
			}
			if(transport.value == "LEIEBIL" || transport.value == "GIKK" || transport.value == "FERJE") {
				belop.value = "";
				belop.readOnly = true;
				belop.disabled = true;
			}
			else {
				belop.value = 0;
				belop.readOnly = false;
				belop.disabled = false;
			}
		}
	}
}	

function utgifterEndret() {
	var utgifter = document.getElementById("utgifter");
	if(!utgifter) {
		return;
	}
	var ferje = document.getElementById("ferje");
	var parkering = document.getElementById("parkering");
	var bompenger = document.getElementById("bompenger");
	if(utgifter.checked) {
		ferje.disabled = false;
		ferje.readOnly = false;
		bompenger.disabled = false;
		bompenger.readOnly = false;
		parkering.disabled = false;
		parkering.readOnly = false;
	}
	else {
		ferje.value = "";
		ferje.disabled = true;
		ferje.readOnly = true;
		bompenger.value = "";
		bompenger.disabled = true;
		bompenger.readOnly = true;
		parkering.value = "";
		parkering.disabled = true;
		parkering.readOnly = true;
	}
}

function returEndret() {
	var retur = document.getElementById("kjorteHjem");
	if(!retur) {
		return;
	}
	var fra = document.getElementById("fraTidRetur");
	var til = document.getElementById("tilTidRetur");
	
	if(retur.checked) {
		fra.disabled = false;
		fra.readOnly = false;
		til.disabled = false;
		til.readOnly = false;
	}
	else {
		fra.disabled = true;
		fra.readOnly = true;
		til.disabled = true;
		til.readOnly = true;
	}
}

function disableBelop(disable) {
	var belop = document.getElementById("belop");
	if(belop) {
		belop.disabled = disable;
		belop.readOnly = disable;
		if(disable) {
			belop.value = "";
		}
	}
}

function disableFerge(disable) {
	var ferje = document.getElementById("ferje");
	if(ferje) {
		ferje.disabled = disable;
		ferje.readOnly = disable;
		if(!disable) {
			ferje.value = "";
		}
	}
}

function validateTime(feltNavn, feilmelding, celleId) {
	var felt = document.getElementById(feltNavn);
	var celle = document.getElementById(celleId);
	
	// Satt inn av Thomas 03.12.2012
	var streng = felt.value;
	streng = fullforKlokkeslett(streng);
	felt.value = streng;
	// Innsatt slutt
	
	if (! isTime(felt.value)) {
		alert("FEIL: " + feilmelding);
		//var celle = document.getElementById(celleId);
		if (celle) {
			celle.className = "errors";
		}
		felt.focus()
	}
	else if (celle) {
		celle.className = "";
	}
}

function isTime(stringToValidate) {
	var regex=/^(2[0-3])|[01][0-9]:[0-5][0-9]$/;
	if (stringToValidate!="") {
		if(!regex.test(stringToValidate)) {
			return false;
		}
	}
	return true;
}
