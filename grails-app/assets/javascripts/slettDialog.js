$(function() {
	$( "#dialog-slett" ).dialog({
		autoOpen: false,
		resizable: true,
		show: true,
		height:165,
		width: 400,
		modal: true,
		buttons: {
			"Ja": function() {
				submitForm('_slettSkjema_');
				$( this ).dialog( "close" );
			},
			"Nei": function() {
				$( this ).dialog( "close" );
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
	$( "#dialog-slett" ).dialog( "open" );
	return false;
}