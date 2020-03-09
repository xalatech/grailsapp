package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_SIL', 'ROLE_ADMIN', 'ROLE_INTERVJUER', 'ROLE_INTERVJUERKONTAKT', 'ROLE_PLANLEGGER', 'ROLE_SPORINGSPERSON', 'ROLE_CAPITILDELING', 'ROLE_SUPERVISOR'])
class HjemController {
    def index = { }
}
