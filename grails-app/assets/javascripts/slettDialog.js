jQuery(function() {
	jQuery( "#dialog-slett" ).dialog({
		autoOpen: false,
		resizable: true,
		height:165,
		width: 400,
		modal: true,
		buttons: {
			"Ja": function() {
				submitForm('_slettSkjema_');
				jQuery( this ).dialog( "close" );
			},
			"Nei": function() {
				jQuery( this ).dialog( "close" );
			}
		}
	});
});

function apneSlettDialog(slettId) {
	var form = document.getElementById("_slettSkjema_");
	if(form) {
		for(var i in form) {
			if(form[i] != null && form[i].id == "id") {
				form[i].value = slettId;
				break; 
			}
		}
	}
	jQuery( "#dialog-slett" ).dialog( "open" );
	return false;
}