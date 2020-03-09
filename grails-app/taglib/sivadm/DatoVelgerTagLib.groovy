package sivadm

import java.text.SimpleDateFormat

class DatoVelgerTagLib {
	
	def datoVelger =  { attrs, body ->
		def datePattern = "dd.MM.yyyy"
		def name = attrs['name']
		def date = attrs['value'] ? new SimpleDateFormat(datePattern).format(attrs['value']) : ""
		def id = attrs['id']
		def onChange
		if(attrs['onChange']) {
			onChange = attrs['onChange']
		}
		if(attrs['onchange']) {
			onChange = attrs['onchange']
		}
				
		def script = "<script type=\"text/javascript\"> jQuery(function() {jQuery( '[id|=\"${id}\"]' ).datepicker({firstDay: 1}); }); </script>"
		def html = "<input type=\"text\" id=\"${id}\" name=\"${name}\" value=\"${date}\" size=\"10\" "
		if(onChange) {
			html = html + "onChange=\"" + onChange + "\" "
		}
		html = html + ">"

		out << script
		out << html
	}
}
