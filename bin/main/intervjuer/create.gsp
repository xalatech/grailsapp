
<%@ page import="sivadm.Intervjuer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'intervjuer.label', default: 'Intervjuer')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript library="prototype" />
        <script>
	        function poststedEndret(e) {
	        	var kom = eval("(" + e.responseText + ")")	// evaluate JSON
	        	
	        	if(kom) {
	        		var komNrFelt = document.getElementById("kommuneNummer");
	        		
	        		if(komNrFelt) {
	        			komNrFelt.value = kom.kommuneNummer;
	        		}
	        	}
	        }
        </script>
    </head>
    <body>
        <!-- 
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
         -->
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${intervjuerInstance}">
            <div class="errors">
                <g:renderErrors bean="${intervjuerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="small">
                                  <label for="navn"><g:message code="intervjuer.navn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'navn', 'errors')}">
                                    <g:textField name="navn" value="${intervjuerInstance?.navn}" size="40"/>
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="initialer"><g:message code="intervjuer.initialer.label" default="Initialer" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'initialer', 'errors')}">
                                    <g:textField name="initialer" value="${intervjuerInstance?.initialer}" maxlength="3" size="4" />
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="ansattDato"><g:message code="intervjuer.ansattDato.label" default="Ansattdato" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'ansattDato', 'errors')}">
                                    <g:datoVelger id="dp-1" name="ansattDato" value="${intervjuerInstance?.ansattDato}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="intervjuerNummer"><g:message code="intervjuer.intervjuerNummer.label" default="Intervjuernummer" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'intervjuerNummer', 'errors')}">
                                    <g:textField name="intervjuerNummer" value="${intervjuerInstance?.intervjuerNummer}" />
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                  
                                <td valign="top" class="small">
                                  <label for="kjonn"><g:message code="intervjuer.kjonn.label" default="Kjønn" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'kjonn', 'errors')}">
                                    <g:select name="kjonn" from="${siv.type.Kjonn?.values()}" optionKey="key" optionValue="guiName" value="${intervjuerInstance?.kjonn?.key}"  />
                                </td>
                            
                            	<td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="sluttDato"><g:message code="intervjuer.sluttDato.label" default="Sluttdato" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'sluttDato', 'errors')}">
                                    <g:datoVelger id="dp-2" name="sluttDato" value="${intervjuerInstance?.sluttDato}" />
                                </td>
                                
                            </tr>
                                                        
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="arbeidsType"><g:message code="intervjuer.arbeidsType.label" default="Arbeidstype" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'arbeidsType', 'errors')}">
                                    <g:select name="arbeidsType" from="${siv.type.IntervjuerArbeidsType?.values()}" optionKey="key" optionValue="guiName" value="${intervjuerInstance?.arbeidsType?.key}"  />
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                
                                <td valign="top" class="small">
                                  <label for="status"><g:message code="intervjuer.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${siv.type.IntervjuerStatus?.values()}" optionKey="key" optionValue="guiName" value="${intervjuerInstance?.status?.key}"  />
                                </td>
                            
                            	<td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="klynge"><g:message code="intervjuer.klynge.label" default="Klynge" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'klynge', 'errors')}">
                                    <g:select name="klynge.id" from="${sivadm.Klynge.list()}" optionKey="id" value="${intervjuerInstance?.klynge?.id}"  />
                                </td>
                                
                            </tr>
                            
                            
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="avtaltAntallTimer"><g:message code="intervjuer.avtaltAntallTimer.label" default="Avtalt antall timer" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'avtaltAntallTimer', 'errors')}">
                                    <g:textField name="avtaltAntallTimer" value="${intervjuerInstance?.avtaltAntallTimer}" size="10"/>
                                </td>
                                
                                <td valign="top" class="space"></td>
                                
                                <td valign="top" class="small">
                                  <label for="pensjonistLonn"><g:message code="intervjuer.pensjonistLonn.label" default="Pensjonistlønn" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'pensjonistLonn', 'errors')}">
                                    <g:checkBox name="pensjonistLonn" value="${intervjuerInstance?.pensjonistLonn}"  />
                                </td>
                                
                                <td valign="top" class="space"></td>
                                
                                <td valign="top" class="small">
                                  <label for="lokal"><g:message code="intervjuer.lokal.label" default="Lokal intervjuer" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'lokal', 'errors')}">
                                    <g:checkBox name="lokal" value="${intervjuerInstance?.lokal}"  />
                                </td>
                                
                            </tr>
                                                        
                            <tr class="prop">
	                             <td valign="top" class="small">
                                  <label for="fodselsDato"><g:message code="intervjuer.fodselsDato.label" default="Fødselsdato" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'fodselsDato', 'errors')}">
                                    <g:datoVelger id="dp-3" name="fodselsDato" value="${intervjuerInstance?.fodselsDato}" />
                                </td>
	                            
	                             <td valign="top" class="space"></td>
	                            
	                            <td valign="top" class="small">
	                            	<label for="epostJobb"><g:message code="intervjuer.epostJobb.label" default="Epost jobb" /></label>
	                            </td>
	                            <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'epostJobb', 'errors')}">
	                            	<g:textField name="epostJobb" value="${intervjuerInstance?.epostJobb}" />
	                            </td>
	                            <td valign="top" class="space"></td>
	                                
	                            <td valign="top" class="small">
	                            	<label for="epostPrivat"><g:message code="intervjuer.epostPrivat.label" default="Epost privat" /></label>
	                            </td>
	                            <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'epostPrivat', 'errors')}">
	                            	<g:textField name="epostPrivat" value="${intervjuerInstance?.epostPrivat}" size="30"/>
	                            </td>
                            	
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="telefonJobb"><g:message code="intervjuer.telefonJobb.label" default="Telefon jobb" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'telefonJobb', 'errors')}">
                                    <g:textField name="telefonJobb" value="${intervjuerInstance?.telefonJobb}" size="12"/>
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                
                                <td valign="top" class="small">
                                  <label for="telefonHjem"><g:message code="intervjuer.telefonHjem.label" default="Telefon hjem" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'telefonHjem', 'errors')}">
                                    <g:textField name="telefonHjem" value="${intervjuerInstance?.telefonHjem}" size="12"/>
                                </td>
                            
                            	<td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="mobil"><g:message code="intervjuer.mobil.label" default="Mobil" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'mobil', 'errors')}">
                                    <g:textField name="mobil" value="${intervjuerInstance?.mobil}" size="12"/>
                                </td>
                                
                            </tr>
                                                        
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="gateAdresse"><g:message code="intervjuer.gateAdresse.label" default="Gateadresse" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'gateAdresse', 'errors')}">
                                    <g:textField name="gateAdresse" value="${intervjuerInstance?.gateAdresse}" size="40"/>
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                
                                 <td valign="top" class="small">
                                  <label for="gateAdresse2"><g:message code="intervjuer.gateAdresse2.label" default="Gateadresse2" /></label>
                                </td>
                                <td colspan="4" valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'gateAdresse2', 'errors')}">
                                    <g:textField name="gateAdresse2" value="${intervjuerInstance?.gateAdresse2}" />
                                </td>
                            
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="small">
                                  <label for="postNummer"><g:message code="intervjuer.postNummer.label" default="Postnummer" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'postNummer', 'errors')}">
                                    <g:textField name="postNummer" value="${intervjuerInstance?.postNummer}" size="4" maxlength="4" />
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="postSted"><g:message code="intervjuer.gateAdresse.label" default="Poststed" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'postSted', 'errors')}">
                                    <g:textField name="postSted" value="${intervjuerInstance?.postSted}" size="30"
                                    	onchange="${remoteFunction(
	            							controller:'kommune', 
	            							action:'ajaxGetKommuneViaPoststed', 
	            							params:'\'poststed=\' + escape(this.value)', 
	            							onComplete:'poststedEndret(e)')}"
                                    	/>
                                </td>
                                
                                <td valign="top" class="space">
                                </td>
                                
                                <td valign="top" class="small">
                                  <label for="kommuneNummer"><g:message code="intervjuer.kommuneNummer.label" default="Kommunenr" /></label>
                                </td>
                                <td valign="top" class="big ${hasErrors(bean: intervjuerInstance, field: 'kommuneNummer', 'errors')}">
                                    <g:textField name="kommuneNummer" value="${intervjuerInstance?.kommuneNummer}"  size="4" maxlength="4" />
                                </td>
                                
                            </tr>
                            
                            <tr class="prop">
                             <td valign="top" class="small">
                                  <label for="kommentar"><g:message code="intervjuer.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: intervjuerInstance, field: 'kommentar', 'errors')}">
                                    <textArea name="kommentar" id="kommentar" style="height: 55px" cols="40">${intervjuerInstance?.kommentar}</textArea>
                                </td>
                            </tr>
                            
                        </tbody>
                      </table>
                     </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
