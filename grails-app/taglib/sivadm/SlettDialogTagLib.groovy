package sivadm

class SlettDialogTagLib {
	def slettDialog =  { attrs, body ->
		def domeneKlasse = attrs['domeneKlasse']
		def action = attrs['action']
		def melding = attrs['melding']

		def postAction

		if(action) {
			postAction = domeneKlasse ? ("/sivadmin/" + domeneKlasse + "/" + action) : "delete"
		}
		else {
			postAction = domeneKlasse ? ("/sivadmin/" + domeneKlasse + "/delete") : "delete"
		}
		
		def script = ""

		script += "<script>"
		script += "\$(\".slettModal\").click(function () {"
		script += "var elementId = \$(this).data('id');"
		script += "\$(\"#elementId\").val( elementId );});"
		script += "\$(\"#slettForm\").submit(function() {"
	//	script += "window.location.reload();"
		script += "});"
		script += "</script>"

		def msg
		
		if(!melding) {
			msg = message(code: "sivadm.slett.bekreftelse", args: [domeneKlasse], default: "Er du sikker på at du vil slette?")
		}
		else {
			msg = melding
		}
		
		def html = ""

		html += "<div class=\"modal fade\" id=\"slettModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"slettModalLabel\" aria-hidden=\"true\">\n"
		html += "<div class=\"modal-dialog\" role=\"document\">\n"
		html += "<div class=\"modal-content\">\n"
		html += "<div class=\"modal-header\">\n"
		html += "<h5 class=\"modal-title\" id=\"slettModalLabel\">${message(code: 'sivadm.bekreft.sletting', default: 'Bekreft sletting')}</h5>\n"
		html += " <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
				"          <span aria-hidden=\"true\">&times;</span>\n" +
				"        </button></div>\n"
		html += "<form name=\"slettForm\" method=\"post\" action=\"" + postAction + "\">\n"
		html += "<div class=\"modal-body\">\n" + msg + "</div>"
		html += "<input name=\"id\" id=\"elementId\" type=\"hidden\" value=\"0\" />\n"
		html += " <div class=\"modal-footer\">\n" +
				"        <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Avbryt</button>\n" +
				"        <button type=\"submit\" class=\"btn btn-danger\">Bekreft sletting</button>\n" +
				"      </div>\n"
		html += "</form>\n"
		html += "</div>\n</div>\n</div>\n"

		out << script
		out << html
	}
}