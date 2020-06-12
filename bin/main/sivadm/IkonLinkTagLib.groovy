package sivadm

class IkonLinkTagLib {

	def slettIkon =  { attrs, body ->
		out << "<img src=\"${resource(dir:'images',file:'delete.png')}\" style=\"border: none;\" title=\"${message(code: 'sivadm.slett', default: 'Slett')}\"/>"
	}
	
	def redigerIkon =  { attrs, body ->
		out << "<img src=\"${resource(dir:'images',file:'edit.png')}\" style=\"border: none;\" title=\"${message(code: 'sivadm.rediger', default: 'Rediger')}\"/>"
	}
	
	def seIkon = { attrs, body ->
		out << "<img src=\"${resource(dir:'images',file:'show.png')}\" style=\"border: none;\" title=\"${message(code: 'sivadm.se', default: 'Se på')}\"/>"
	}
	
	def godkjennIkon =  { attrs, body ->
		def tit = attrs['title']
		def title = "${message(code: 'sivadm.godkjenn', default: 'Godkjenn')}"
		if(tit) {
			title = tit
		}
		
		out << "<img src=\"${resource(dir:'images',file:'check.png')}\" style=\"border: none;\" title=\"${title}\"/>"
	}
}