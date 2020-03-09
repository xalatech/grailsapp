

function utleggTypeEndret() {	
	var utleggType = document.getElementById("utleggType");
	
	if( utleggType.value == "KOST_GODT" ) {
		skjulBelop();
		visKostFelter();
	}
	else {
		visBelop();
		skjulKostFelter();
	}
}

function skjulBelop() {
	var belop = document.getElementById("belop");
	var belopdiv = document.getElementById("belopdiv");
	var belopdivlabel = document.getElementById("belopdivlabel");
	belop.disabled = true;
	belop.readOnly = true;
	belop.value = "0";
	belopdiv.hide();
	belopdivlabel.hide();
}

function visBelop() {
	var belop = document.getElementById("belop");
	var belopdiv = document.getElementById("belopdiv");
	var belopdivlabel = document.getElementById("belopdivlabel");
	belop.disabled = false;
	belop.readOnly = false;
	belopdiv.show();
	belopdivlabel.show();
}

function visKostFelter() {
	var fraTidLabel = document.getElementById("fraTidLabel");
	var fraTid = document.getElementById("fraTid");
	fraTidLabel.show();
	fraTid.show();
	
	var tilTidLabel = document.getElementById("tilTidLabel");
	var tilTid = document.getElementById("tilTid");
	tilTidLabel.show();
	tilTid.show();
	
	var kostTilStedLabel = document.getElementById("kostTilStedLabel");
	var kostTilStedid = document.getElementById("kostTilSted");
	kostTilStedLabel.show();
	kostTilStedid.show();
}

function skjulKostFelter() {
	var fraTidLabel = document.getElementById("fraTidLabel");
	var fraTid = document.getElementById("fraTid");
	fraTidLabel.hide();
	fraTid.hide();
	
	var tilTidLabel = document.getElementById("tilTidLabel");
	var tilTid = document.getElementById("tilTid");
	tilTidLabel.hide();
	tilTid.hide();
	
	var kostTilStedLabel = document.getElementById("kostTilStedLabel");
	var kostTilStedid = document.getElementById("kostTilSted");
	kostTilStedLabel.hide();
	kostTilStedid.hide();
}