package sivadm

class GodkjennDialogTagLib {
	def godkjennDialog =  { attrs, body ->
		def domeneKlasse = attrs['domeneKlasse']
		def action = attrs['action']
		def cont = attrs['controller']
		def melding = attrs['melding']
		
		// Hvis formId, dialogId og openDialogFunctionName er satt er dette et tegn på
		// at tag'en har vært brukt en gang før på siden da med default id'er for skjema, dialog osv.
		def formId = attrs['formId']
		def dialogId = attrs['dialogId']
		def openDialogFunctionName = attrs['openDialogFunctionName']
		def paramsNavn = attrs['paramsNavn']
		def paramsVerdier = attrs['paramsVerdier']
		
		def postAction
		def controller
		
		if(cont) {
			controller = cont
		}
		
		if(action) {
			postAction = controller ? ("/sivadmin/" + controller + "/" + action) : "godkjenn"
		}
		else {
			postAction = controller ? ("/sivadmin/" + controller + "/godkjenn") : "godkjenn"
		}
		
		def script = ""
		
		if(formId && dialogId && openDialogFunctionName) {
			script += "<script>jQuery(function() { jQuery(\"#" + dialogId + "\").dialog({"
			script += "autoOpen: false, resizable: false, height:150, width: 350,modal: true,"
			script += "buttons: {\"Ja\": function() {submitForm('" + formId + "');	jQuery( this ).dialog( \"close\" );},"
			script += "\"Nei\": function() {jQuery( this ).dialog( \"close\" );}}});});"
			script += "function " + openDialogFunctionName + "(godkjennId) {"
			script += "var form = document.getElementById(\"" + formId + "\");"
			script += "jQuery( \"#" + dialogId + "\" ).dialog( \"open\" );"
			script += "if(form) { for(var i in form) { if(form[i].id == \"id\") {"
			script += "form[i].value = godkjennId;break;}}}return false;}</script>"
		}
		else {
			script = "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.resizable.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.position.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.mouse.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.draggable.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.dialog.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.core.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js/jquery',file:'jquery.ui.widget.js')}\"></script>\n"
			script += "<script type=\"text/javascript\" src=\"${resource(dir:'js',file:'godkjennDialog.js')}\"></script>\n"
		}
		
		def msg
		
		if(!melding) {
			msg = message(code: "sivadm.godkjenn.bekreftelse", args: [domeneKlasse], default: "Er du sikker på at du vil godkjenne?")
		}
		else {
			msg = melding
		}
		
		def html = ""
		def paramStr
		
		if(paramsNavn && paramsVerdier && paramsNavn.size() == paramsVerdier.size()) {
			paramStr = ""
			int i = 0
			paramsNavn.each {
				paramStr += "<input name=\"" + it + "\" id=\"" + it + "\" type=\"hidden\" value=\"" + paramsVerdier[i] + "\" />\n"
				i++
			}
		}
					
		if(formId && dialogId && openDialogFunctionName) {
			html += "<form name=\"" + formId + "\" id=\"" + formId + "\"method=\"post\" action=\"" + postAction + "\">\n"
			html += "<input name=\"id\" id=\"id\" type=\"hidden\" value=\"-1\" />\n"
			if(paramStr) {
				html += paramStr
			}
			html += "</form>\n"
			html += "<div id=\"" + dialogId + "\" title=\"${message(code: 'sivadm.bekreft.godkjenning', default: 'Bekreft godkjenning')}\">"
		}
		else {
			html = "<form name=\"_godkjennSkjema_\" id=\"_godkjennSkjema_\" method=\"post\" action=\"" + postAction + "\">\n"
			html += "<input name=\"id\" id=\"id\" type=\"hidden\" value=\"-1\" />\n"
			if(paramStr) {
				html += paramStr
			}
			html += "</form>\n"
			html += "<div id=\"dialog-godkjenn\" title=\"${message(code: 'sivadm.bekreft.godkjenning', default: 'Bekreft godkjenning')}\">"
		}
		
		html += "<table style=\"border: 0;\"><tr><td>"
		html += "<span class=\"ui-icon ui-icon-alert\" style=\"float:left; margin:0 7px 20px 0;\" />"
		html += "</td><td>" + msg + "</td></tr></table></div>"
				
		out << script
		out << html
	}
}
