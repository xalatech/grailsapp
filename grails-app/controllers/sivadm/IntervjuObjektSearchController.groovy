package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_PLANLEGGER', 'ROLE_INTERVJUERKONTAKT', 'ROLE_SPORINGSPERSON', 'ROLE_SUPERVISOR'])
class IntervjuObjektSearchController {

	def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        [searchInstanceList: IntervjuObjektSearch.list(sort: "id", order: "desc"), searchInstanceTotal: IntervjuObjektSearch.count()]
    }

	def visUtfortSok = {

        params.max = 30
        params.offset = params.offset ? params.int('offset') : 0

        if (params.searchId) {
            session.searchId = params.searchId
        }

		session.searchName = params.searchName

		def utfortSokXml = IntervjuObjektSearch.findById(session.searchId).intervjuObjektSearchResult
		def utsending = new XmlSlurper().parseText(utfortSokXml)
		def antall = utsending.metadata.treff
		def intervjuObjektList = []
		utsending.mottakerliste.mottaker.each { mottaker ->
			intervjuObjektList.add( [
					intervjuObjektNummer: mottaker.data.ioNr ? new Long (mottaker.data.ioNr.toString()) : null,
					id: mottaker.data.intervjuObjektId ? new Long (mottaker.data.intervjuObjektId.toString()) : null,
					skjemaNavn: mottaker.data.skjemaNavn.toString(),
					periodeNr: mottaker.data.periodeNr ? new Long (mottaker.data.periodeNr.toString()) : null,
					skjemaStatus: mottaker.data.skjemaStatus.toString(),
					intervjuStatus: mottaker.data.intervjuStatus.toString() ? new Long (mottaker.data.intervjuStatus.toString()) : null,
					fNummer: mottaker.data.fNummer.toString() ? new Long (mottaker.data.fNummer.toString()) : null,
					navn: mottaker.data.navn.toString(),
					smsNummer: mottaker.data.smsNummer.toString() ? new Long (mottaker.data.smsNummer.toString()) : null,
					epostadresse: mottaker.data.epostadresse.toString(),
					kontaktPeriode: mottaker.data.kPeriode.toString(),
					delutvalg: mottaker.data.delutvalg.toString()
			])
		}

		if (params.sort) {
            intervjuObjektList.sort { a, b ->
                if (params.order == 'asc') {
                    a."${params.sort}" <=> b."${params.sort}"
                } else {
                    b."${params.sort}" <=> a."${params.sort}"
                }
            }
		} else {
			intervjuObjektList.sort { it.intervjuObjektNummer }
		}

		def fra = params.offset
		def til = params.offset+params.max < intervjuObjektList.size() ? params.offset+params.max-1 : intervjuObjektList.size()-1
        def intervjuObjektListPaginated = intervjuObjektList[fra..til]

		[ antall: antall, intervjuObjektList: intervjuObjektListPaginated ]
	}
	
	def delete = {
		def searchInstance = IntervjuObjektSearch.get(params.id)
		if (searchInstance) {
			try {
				searchInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'intervjuObjektSearch.label', default: 'IntervjuObjektSøk'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'intervjuObjektSearch.label', default: 'IntervjuObjektSøk'), params.id])}"
				redirect(action: "list")
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'intervjuObjektSearch.label', default: 'IntervjuObjektSøk'), params.id])}"
			redirect(action: "list")
		}
	}
}