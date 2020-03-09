import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.web.WebAttributes

@Secured('permitAll')
class LoginController {

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: auth, params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {	
		
		if( ! Environment.getCurrent().equals(Environment.DEVELOPMENT) && ! Environment.getCurrent().getName() == "selenium") {
			redirect (controller: "hjem", action: "index")
		}	

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}
		
		if (Environment.getCurrent().equals(Environment.PRODUCTION)){
			render view: 'backToCAS'
		}else {
			String view = 'auth'
			String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
			render view: view, model: [postUrl: postUrl,
			                           rememberMeParameter: config.rememberMe.parameter]
		}
		
//		redirect(uri: '/j_spring_security_logout')
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'auth', params: params
		}

	}
}
