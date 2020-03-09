package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class meldingsheaderMalController {

    def brukerService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 200, 200)
        if (!params.sort) {
            params.sort = 'malNavn'
        }
        [meldingsheaderMalInstanceList: MeldingsheaderMal.list(params), meldingsheaderMalInstanceTotal: MeldingsheaderMal.count()]
    }

    def create = {
        def meldingsheaderMalInstance = new MeldingsheaderMal()
        meldingsheaderMalInstance.properties = params
        return [meldingsheaderMalInstance: meldingsheaderMalInstance]
    }

    def save = {
        def meldingsheaderMalInstance = new MeldingsheaderMal(params)
        Bruker bruker = Bruker.findByUsername(brukerService.currentUserName)
        meldingsheaderMalInstance.setOpprettetAv(bruker)
        meldingsheaderMalInstance.setOpprettet(new Date())
        if (meldingsheaderMalInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'meldingsheaderMal.label', default: 'meldingsheaderMal'), meldingsheaderMalInstance.malNavn])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [meldingsheaderMalInstance: meldingsheaderMalInstance])
        }
    }

    def edit = {
        def meldingsheaderMalInstance = MeldingsheaderMal.get(params.id)
        if (!meldingsheaderMalInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingsheaderMal.label', default: 'meldingsheaderMal'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [meldingsheaderMalInstance: meldingsheaderMalInstance]
        }
    }

    def update = {
        def meldingsheaderMalInstance = MeldingsheaderMal.get(params.id)
        if (meldingsheaderMalInstance) {
            meldingsheaderMalInstance.properties = params
            Bruker bruker = Bruker.findByUsername(brukerService.currentUserName)
            meldingsheaderMalInstance.setSistEndretAv(bruker)
            meldingsheaderMalInstance.setSistEndret(new Date())
            if (!meldingsheaderMalInstance.hasErrors() && meldingsheaderMalInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'meldingsheaderMal.label', default: 'MeldingsheaderMal'), meldingsheaderMalInstance.malNavn])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [meldingsheaderMalInstance: meldingsheaderMalInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingsheaderMal.label', default: 'MeldingsheaderMal'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def meldingsheaderMalInstance = MeldingsheaderMal.get(params.id)
        if (meldingsheaderMalInstance) {
            try {
                meldingsheaderMalInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'meldingsheaderMal.label', default: 'MeldingsheaderMal'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'meldingsheaderMal.label', default: 'MeldingsheaderMal'), params.id])}"
                redirect(action: "edit", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'meldingsheaderMal.label', default: 'MeldingsheaderMal'), params.id])}"
            redirect(action: "list")
        }
    }
}
