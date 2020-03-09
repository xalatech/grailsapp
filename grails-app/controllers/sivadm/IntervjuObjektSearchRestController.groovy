package sivadm

import groovy.xml.MarkupBuilder

class IntervjuObjektSearchRestController {
	
	def xmlService

	static allowedMethods = [ping: "GET", hentSokListe: "GET"]

	def ping = {
		def xmlResponse = xmlService.getPingXml()
		render(text: xmlResponse, contentType: "text/xml", encoding: "UTF-8")
	}

	def hentSokListe() {
		def xmlResponse
		try {
			def id = params.id
			IntervjuObjektSearch intervjuObjektSearch = IntervjuObjektSearch.get(id)
			if(!intervjuObjektSearch) {
				response.status = 404
				xmlResponse = this.lagErrorRespons("Fant ikke ioID")
				render(text: xmlResponse, contentType: "text/xml", encoding: "UTF-8")
				return
			}
			if (intervjuObjektSearch.persisterSokeResultat) {
				xmlResponse = intervjuObjektSearch?.intervjuObjektSearchResult
			} else {
				xmlResponse = xmlService.getIntervjuObjektSearchResultXml(intervjuObjektSearch)
			}
		} catch(Throwable t) {
			xmlResponse = this.lagErrorRespons(t.getMessage() + "\n" + t.getStackTrace())
			response.status = 500
		}
		render(text: xmlResponse, contentType: "text/xml", encoding: "UTF-8")
	}
	
	private String lagErrorRespons(meldingTekst) {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		xml.utsending() {
			error {
				melding(meldingTekst)
			}
		}
		return "<?xml version=\"1.1\"?>\n" + writer.toString()
	}
	
}