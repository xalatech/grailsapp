package sivadm

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class LoggLinjeController {
    static scaffold = LoggLinje
}
