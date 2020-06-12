package sil

import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class LonnartController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [lonnartInstanceList: Lonnart.list(params), lonnartInstanceTotal: Lonnart.count()]
    }

    def show = {
        def lonnartInstance = Lonnart.get(params.id)
        if (!lonnartInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'lonnart.label', default: 'Lonnart'), params.id])}"
            redirect(action: "list")
        }
        else {
            [lonnartInstance: lonnartInstance]
        }
    }
}
