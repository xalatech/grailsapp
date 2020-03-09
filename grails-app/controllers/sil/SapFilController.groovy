package sil

import grails.core.*
import grails.plugin.springsecurity.annotation.Secured
import sil.type.FilType
import sil.type.SapFilStatusType

@Secured(['ROLE_SIL', 'ROLE_ADMIN'])
class SapFilController {
	def config = ConfigurationHolder.config
	def sapFilService
	def brukerService
		
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

	def regenerer = {
		def sapFilInstance = SapFil.get(params.id)
		
		if(!sapFilInstance) {
            flash.errorMessage = "Fant ikke SAP fil med id " + params.identity { }+ " vennligst kontakt systemadministrator."
            redirect(action: "list")
			return
        }
		
		if(!sapFilInstance.kanRegenereres()) {
			flash.message = "Noen av kravene i denne SAP filen har ikke status Godkjent og kan da ikke skrives til ny SAP fil"
			redirect(action: "list")
			return
		}
		
		def krav = []
		
		sapFilInstance.getKravListe().each {
			krav << it
		}
			
		
		def sapFil = sapFilService.genererSapFil(krav, (sapFilInstance.filType == FilType.TIME ? true : false) ) 
	
		if(!sapFil) {
			flash.errorMessage = "Ingen krav å generere SAP fil for"
			redirect(action: "list")
			return
		}
				
		sapFilInstance.setStatus(SapFilStatusType.REGENERERT)
		def mld = ""
		if(sapFil?.status == SapFilStatusType.OK) {
			mld = "Krav regenerert i SAP fil " + sapFil.fil + " (id=" + sapFil.id + ")"
		}
		else {
			mld = "Krav forsøkt regenerert i SAP fil med id " + sapFil.id
		}
		
		sapFilInstance.setStatusmelding(mld)
		sapFilInstance.save(failOnError: true)
		
		
		if(sapFil?.status == SapFilStatusType.OK) {
			String mottaker = brukerService.getCurrentUserEmail()
			String avsender = config.avsender.sivadmin.epost
			
			log.info 'SapFilController -> Current user email: ' + mottaker

			String emne = "${message(code: 'sil.epost.sap.generert.emne', args: [])}"
			String bodyStr = "${message(code: 'sil.generert.sap.filer.ok', args: [1, sapFil.antallKrav, sapFil.antallLinjer])}"
			try {
				sendMail {
					to mottaker
					from avsender
					subject emne
					body bodyStr
				}
			}
			catch(Exception e) {
				//flash.errorMessage == "${message(code: 'sil.epost.feil.generere.sap.filer', default: 'Feil ved sending av epost om at det er generert SAP-filer')}"
				log.error("Kunne ikke sende epost om at det er generert SAP-filer: " + e.getMessage())
			}
			flash.message = "${message(code: 'sil.generert.sap.filer.ok', args: [1, sapFil.antallKrav, sapFil.antallLinjer])}"
		}
		else {
			flash.errorMessage = "${message(code: 'sil.generert.sap.filer.feilet', args: [1])}"
		}
		
		redirect(action: "list")
	}
	
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
				
        [sapFilInstanceList: SapFil.list(params), sapFilInstanceTotal: SapFil.count()]
    }

    def show = {
        def sapFilInstance = SapFil.get(params.id)
        if (!sapFilInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sapFil.label', default: 'SapFil'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sapFilInstance: sapFilInstance]
        }
    }
}
