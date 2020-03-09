package sivadm
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class BlaiseEventInnController {
	static scaffold = BlaiseEventInn
}
