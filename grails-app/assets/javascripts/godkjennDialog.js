jQuery(function() {
	jQuery( "#dialog-godkjenn" ).dialog({
		autoOpen: false,
		resizable: true,
		height:165,
		width: 400,
		modal: true,
		buttons: {
			"Ja": function() {
				submitForm('_godkjennSkjema_');
				jQuery( this ).dialog( "close" );
			},
			"Nei": function() {
				jQuery( this ).dialog( "close" );
			}
		}
	});
});

function apneGodkjennDialog(godkjennId) {
	var form = document.getElementById("_godkjennSkjema_");
	if(form) {
		for(var i in form) {
			if(form[i] != null && form[i].id == "id") {
				form[i].value = godkjennId;
				break; 
			}
		}
	}
	jQuery( "#dialog-godkjenn" ).dialog( "open" );
	return false;
}