function prosjektEndret(e) {
	var produkt = eval("(" + e.responseText + ")")	// evaluate JSON
	
	if(produkt) {
		var delProdukt = document.getElementById("delProduktNummer");
		
		if(delProdukt) {
			delProdukt.value = produkt.produktNummer + "-";
		}
	}
}

function initDelProduktNummer() {
	var prosjekt = document.getElementById("prosjekt.id");
	var delProdukt = document.getElementById("delProduktNummer");

	if(prosjekt && delProdukt) {
		var produktId = prosjekt.options[prosjekt.selectedIndex].value;
		new Ajax.Request('/sivadmin/prosjekt/ajaxGetProsjekt',{asynchronous:true,evalScripts:true,onComplete:function(e){prosjektEndret(e)},parameters:'id=' + produktId});
	}
}