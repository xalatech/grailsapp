function intervjuStatusDatoIntervallEndret() {
	var datoFraField = document.getElementById("dp-1");
	var datoTilField = document.getElementById("dp-2");
	if (datoFraField && datoTilField) {
		var datoFraStr = datoFraField.value
		var datoTilStr = datoTilField.value
		if (datoFraStr && datoTilStr) {
			var datoFraArray = datoFraStr.split('.')
			var datoTilArray = datoTilStr.split('.')
			var datoFra = new Date(datoFraArray[2],datoFraArray[1],datoFraArray[0])
			var datoTil = new Date(datoTilArray[2],datoTilArray[1],datoTilArray[0])
			if (datoFra > datoTil) {
				window.alert('Feil: f.o.m.-dato er etter t.o.m.-dato.')
			}
		}
	}
}

function paaVentDatoEndret() {
	var datoField = document.getElementById("dp-1");
	var statusSelect = document.getElementById("katSkjemaStatus");
	
	if(datoField && statusSelect) {
		var setPaaVent = true;
		if(datoField.value == "") {
			setPaaVent = false;
		}
		
		for(i=0; i<statusSelect.length; i++) {
			if(statusSelect[i].value == "Paa_vent" && setPaaVent) {
				statusSelect[i].selected = true;
				break;
			}
			else if(statusSelect[i].value == "Ubehandlet" && !setPaaVent) {
				statusSelect[i].selected = true;
				break;
			}
		}
	}
}

function skjemaStatusEndret() {
	var statusSelect = document.getElementById("katSkjemaStatus");
	var intervjuStatus = document.getElementById("intervjuStatus");
	
	if(statusSelect && intervjuStatus) {
		for(i=0; i<statusSelect.length; i++) {
			if(statusSelect[i].selected && (statusSelect[i].value == "Utsendt_CATI" || statusSelect[i].value == "Utsendt_CATI_WEB" || statusSelect[i].value == "Utsendt_CAPI")) {
				intervjuStatus.value = "";
				break;
			}
		}
	}
}

function skjemaValgEndret() {
	if($("kontaktperiode") && $("kontaktperiodeSelect") && $("skjema")) {
		textInputTilSelectSync($("kontaktperiode"), $("kontaktperiodeSelect"), $("skjema"), '/sivadmin/intervjuObjekt/hentUnikeKontaktperioder');
	}
	if($("delutvalg") && $("delutvalgSelect") && $("skjema")) {
		textInputTilSelectSync($("delutvalg"), $("delutvalgSelect"), $("skjema"), '/sivadmin/intervjuObjekt/hentUnikeDelutvalg');
	}

	var e = document.getElementById("skjema");

	if (e) {

		var skjemaId = e.options[e.selectedIndex].value;

		if (skjemaId) {
			document.getElementById("assosiertSkjema").disabled = false;
		} else {

			document.getElementById("assosiertSkjema").selectedIndex = 0;
			document.getElementById("assosiertSkjema").disabled = true;
			document.getElementById("intervjuStatusBlankAssSkj").checked = false;
			document.getElementById("intervjuStatusBlankAssSkj").disabled = true;
			document.getElementById("intervjuStatusAssSkj").value = '';
			document.getElementById("intervjuStatusAssSkj").disabled = true;

			document.getElementById("kontaktperiodeAssSkj").value = '';
			document.getElementById("kontaktperiodeAssSkj").writeAttribute('disabled', true);
			document.getElementById("kontaktperiodeAssSkj").removeClassName('hidden');
			document.getElementById("kontaktperiodeSelectAssSkjema").writeAttribute('disabled', 'disabled');
			document.getElementById("kontaktperiodeSelectAssSkjema").addClassName('hidden');

		}
	}
}

function assosiertSkjemaValgEndret() {

	if($("kontaktperiodeAssSkj") && $("kontaktperiodeSelectAssSkjema") && $("assosiertSkjema")) {
		textInputTilSelectSync(	$("kontaktperiodeAssSkj"),
								$("kontaktperiodeSelectAssSkjema"),
								$("assosiertSkjema"),
								'/sivadmin/intervjuObjekt/hentUnikeKontaktperioder');
	}

	var e = document.getElementById("assosiertSkjema");

	if (e) {

		var skjemaId = e.options[e.selectedIndex].value;

		if (skjemaId) {
			document.getElementById("intervjuStatusBlankAssSkj").disabled = false;
			document.getElementById("intervjuStatusAssSkj").disabled = false;
		} else {
			document.getElementById("intervjuStatusBlankAssSkj").checked = false;
			document.getElementById("intervjuStatusBlankAssSkj").disabled = true;
			document.getElementById("kontaktperiodeAssSkj").disabled = true;
			document.getElementById("intervjuStatusAssSkj").value = '';
			document.getElementById("intervjuStatusAssSkj").disabled = true;
		}
	}
}

function textInputTilSelectSync(textInput, selectBox, skjemaSelectBox, valuesUrl) {
	var valgteTextInputVerdier = textInput.value.split(',');
	var skjemaId;
	for(i = 0; i < skjemaSelectBox.length; i++) {
		if(skjemaSelectBox[i].selected) {
			skjemaId = skjemaSelectBox[i].value;
			break;
		}
	}
	if(skjemaId) {
		selectBox.childElements().forEach(function(element) {
			element.remove();
		});
		textInput.writeAttribute('disabled', 'disabled');
		textInput.addClassName('hidden');
		selectBox.writeAttribute('disabled', false);
		selectBox.removeClassName('hidden');
		new Ajax.Request(valuesUrl, {
			method: 'get',
			parameters: {
				skjemaId: skjemaId
			},
			onSuccess: function(transport) {
				console.log(transport.responseJSON);
				var brukteVerdierArray = transport.responseJSON.data;
				textInput.value = '';
				for(i = 0; i < brukteVerdierArray.length; i++) {
					var valgVerdi = brukteVerdierArray[i];
					var valg = new Element('option', {
						value: valgVerdi
					});
					if(valgteTextInputVerdier.indexOf(valgVerdi) > -1) {
						valg.writeAttribute({selected: true});
						if(textInput.value) {
							textInput.value += ',';
						}
						textInput.value += valgVerdi;
					}
					valg.appendChild(document.createTextNode(brukteVerdierArray[i]));
					selectBox.appendChild(valg);
				}
				textInput.insert({
					after: selectBox
				});
			}
		});
	} else {
		textInput.writeAttribute('disabled', false);
		textInput.removeClassName('hidden');
		selectBox.writeAttribute('disabled', 'disabled');
		selectBox.addClassName('hidden');
	}
}

jQuery(document).ready(function() {
	skjemaValgEndret();
	assosiertSkjemaValgEndret();
});