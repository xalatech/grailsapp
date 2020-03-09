

<%@ page import="sivadm.MeldingUt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'meldingUt.label', default: 'MeldingUt')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${meldingUtInstance}">
            <div class="errors">
                <g:renderErrors bean="${meldingUtInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${meldingUtInstance?.id}" />
                <g:hiddenField name="version" value="${meldingUtInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tidSendt"><g:message code="meldingUt.tidSendt.label" default="Tid Sendt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'tidSendt', 'errors')}">
                                    <g:datoVelger id="dp-1" name="tidSendt" value="${meldingUtInstance?.tidSendt}" />
                                    
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deadLetter"><g:message code="meldingUt.deadLetter.label" default="Dead Letter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'deadLetter', 'errors')}">
                                    <g:checkBox name="deadLetter" value="${meldingUtInstance?.deadLetter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="responseText"><g:message code="meldingUt.responseText.label" default="Response Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'responseText', 'errors')}">
                                    <g:textField name="responseText" value="${meldingUtInstance?.responseText}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="feilType"><g:message code="meldingUt.feilType.label" default="Feil Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'feilType', 'errors')}">
                                    <g:textField name="feilType" value="${meldingUtInstance?.feilType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="antallForsok"><g:message code="meldingUt.antallForsok.label" default="Antall Forsok" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'antallForsok', 'errors')}">
                                    <g:textField name="antallForsok" value="${fieldValue(bean: meldingUtInstance, field: 'antallForsok')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuObjektId"><g:message code="meldingUt.intervjuObjektId.label" default="Intervju Objekt Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'intervjuObjektId', 'errors')}">
                                    <g:textField name="intervjuObjektId" value="${fieldValue(bean: meldingUtInstance, field: 'intervjuObjektId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="melding"><g:message code="meldingUt.melding.label" default="Melding" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'melding', 'errors')}">
                                    <g:textField name="melding" value="${meldingUtInstance?.melding}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="meldingType"><g:message code="meldingUt.meldingType.label" default="Melding Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'meldingType', 'errors')}">
                                    <g:select name="meldingType" from="${siv.type.MeldingType?.values()}" optionKey="key" optionValue="guiName" value="${meldingUtInstance?.meldingType?.key}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sendtAv"><g:message code="meldingUt.sendtAv.label" default="Sendt Av" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'sendtAv', 'errors')}">
                                    <g:textField name="sendtAv" value="${meldingUtInstance?.sendtAv}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sendtOk"><g:message code="meldingUt.sendtOk.label" default="Sendt Ok" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'sendtOk', 'errors')}">
                                    <g:checkBox name="sendtOk" value="${meldingUtInstance?.sendtOk}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="skjemaKortNavn"><g:message code="meldingUt.skjemaKortNavn.label" default="Skjema Kort Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'skjemaKortNavn', 'errors')}">
                                    <g:textField name="skjemaKortNavn" value="${meldingUtInstance?.skjemaKortNavn}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tidRegistrert"><g:message code="meldingUt.tidRegistrert.label" default="Tid Registrert" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'tidRegistrert', 'errors')}">
                                    <g:datoVelger id="dp-2" name="tidRegistrert" value="${meldingUtInstance?.tidRegistrert}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deaktivert"><g:message code="meldingUt.deaktivert.label" default="Deaktivert" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingUtInstance, field: 'deaktivert', 'errors')}">
                                    <g:checkBox name="deaktivert" value="${meldingUtInstance?.deaktivert}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="list" value="${message(code: 'default.button.cancel.label', default: 'Avbryt')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
