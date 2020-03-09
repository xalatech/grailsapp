class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/rest/intervjuobjekt/skjemastatus/$ioId"(controller: "intervjuObjektRest") {
			action = [PUT: "oppdaterSkjemaStatus"]
		}
		
		"/rest/intervjuobjekt/fullforingsgrad/$ioId"(controller: "intervjuObjektRest") {
			action = [PUT: "oppdaterFullforingsGrad"]
		}
		
		"/rest/intervjuobjekt/kontaktperioder"(controller: "intervjuObjektRest") {
			action = [POST: "hentKontaktperioder"]
		}
		
		"/rest/intervjuobjekt/kontaktperiode"(controller: "intervjuObjektRest") {
			action = [GET: "hentKontaktperiode"]
		}
		
		"/rest/intervjuobjektsok/$id"(controller: "intervjuObjektSearchRest") {
			action = [GET: "hentSokListe"]
		}

		"/rest/ping"(controller: "intervjuObjektSearchRest") {
			action = [GET: "ping"]
		}

		"/" {
			controller = "hjem"
			action = "index"
		}		
  
		"500"(view:'/error')
	}
}
