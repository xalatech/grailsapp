/**
 * 
 */

function updateSkjema(e) {
	// The response comes back as a bunch-o-JSON
	
	var skjemaList = eval("(" + e.responseText + ")")	// evaluate JSON
	
	if (skjemaList) {
		var rselect = document.getElementById('skjema')	// Clear all
														// previous options
		var l = rselect.length			
		
		while (l > 0) {
			l--
			rselect.remove(l)
		}			// Rebuild the select
		
		for (var i=0; i < skjemaList.length; i++) {
			var skjema = skjemaList[i]
			var opt = document.createElement('option');
			opt.text = skjema.skjemaNavn
			opt.value = skjema.id
		  	try {
		    	rselect.add(opt, null) // standards compliant; doesn't work
										// in IE
		  	}
	  		catch(ex) {
	    		rselect.add(opt) // IE only
	  		}
		}
		
		var sindx = rselect.value;
		
		new Ajax.Request('/sivadmin/capiAdministrasjon/ajaxGetPeriodeList',{asynchronous:true,evalScripts:true,onComplete:function(e){updatePeriode(e)},parameters:'id=' + sindx});
	}
}	


function updatePeriode(e) {
	// The response comes back as a bunch-o-JSON
	
	var periodeList = eval("(" + e.responseText + ")")	// evaluate JSON
	
	if (periodeList) {
		var rselect = document.getElementById('periode')	// Clear all
														// previous options
		var l = rselect.length			
		
		while (l > 0) {
			l--
			rselect.remove(l)
		}			// Rebuild the select
		
		for (var i=0; i < periodeList.length; i++) {
			var periode = periodeList[i]
			var opt = document.createElement('option');
			opt.text = periode.periodeNummer
			opt.value = periode.id
			
		  	try {
		    	rselect.add(opt, null) // standards compliant; doesn't work
										// in IE
		  	}
	  		catch(ex) {
	    		rselect.add(opt) // IE only
	  		}
		}
		
	}
}	

