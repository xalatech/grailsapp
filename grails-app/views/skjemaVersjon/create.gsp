
<%@ page import="sivadm.SkjemaVersjon" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'skjemaVersjon.label', default: 'SkjemaVersjon')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
  
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${skjemaVersjonInstance}">
            <div class="errors">
                <g:renderErrors bean="${skjemaVersjonInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="versjonsNummer"><g:message code="skjemaVersjon.versjonsNummer.label" default="Versjonsnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'versjonsNummer', 'errors')}">
                                    <g:textField name="versjonsNummer" value="${fieldValue(bean: skjemaVersjonInstance, field: 'versjonsNummer')}" size="3"/>
                                </td>
                            </tr>
                        	
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gyldigFom"><g:message code="skjemaVersjon.gyldigFom.label" default="Gyldig fra og med" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'gyldigFom', 'errors')}">
                                    <g:datoVelger id="d1" name="gyldigFom" value="${skjemaVersjonInstance?.gyldigFom}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gyldigTom"><g:message code="skjemaVersjon.gyldigTom.label" default="Gyldig til og med" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'gyldigTom', 'errors')}">
                                    <g:datoVelger id="d2" name="gyldigTom" value="${skjemaVersjonInstance?.gyldigTom}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="skjemaGodkjentDato"><g:message code="skjemaVersjon.skjemaGodkjentDato.label" default="Skjema godkjent dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'skjemaGodkjentDato', 'errors')}">
                                    <g:datoVelger id="d3" name="skjemaGodkjentDato" value="${skjemaVersjonInstance?.skjemaGodkjentDato}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="skjemaGodkjentInitialer"><g:message code="skjemaVersjon.skjemaGodkjentInitialer.label" default="Skjema godkjent initialer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'skjemaGodkjentInitialer', 'errors')}">
                                    <g:textField name="skjemaGodkjentInitialer" value="${skjemaVersjonInstance?.skjemaGodkjentInitialer}" maxlength="3" size="4" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="webskjemaGodkjentDato"><g:message code="skjemaVersjon.webskjemaGodkjentDato.label" default="Webskjema godkjent dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'webskjemaGodkjentDato', 'errors')}">
                                    <g:datoVelger id="d4" name="webskjemaGodkjentDato" value="${skjemaVersjonInstance?.webskjemaGodkjentDato}"  />
                                </td>
                            </tr>
                        
                            
                        
                            
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="webskjemaGodkjentInitialer"><g:message code="skjemaVersjon.webskjemaGodkjentInitialer.label" default="Webskjema godkjent initialer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'webskjemaGodkjentInitialer', 'errors')}">
                                    <g:textField name="webskjemaGodkjentInitialer" value="${skjemaVersjonInstance?.webskjemaGodkjentInitialer}" maxlength="3" size="4" />
                                </td>
                            </tr>
                        
                            
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommentar"><g:message code="skjemaVersjon.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaVersjonInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${skjemaVersjonInstance?.kommentar}" size="40"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                
                <g:hiddenField name="skjemaId" value="${params.skjemaId}" />
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:actionSubmit action="avbryt" class="delete" value="Avbryt" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
