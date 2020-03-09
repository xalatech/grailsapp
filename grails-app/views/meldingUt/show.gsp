
<%@ page import="sivadm.MeldingUt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'meldingUt.label', default: 'MeldingUt')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.tidSendt.label" default="Tid Sendt" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${meldingUtInstance?.tidSendt}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.deadLetter.label" default="Dead Letter" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingUtInstance?.deadLetter}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.responseText.label" default="Response Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "responseText")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.feilType.label" default="Feil Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "feilType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.antallForsok.label" default="Antall Forsok" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "antallForsok")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.intervjuObjektId.label" default="Intervju Objekt Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "intervjuObjektId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.melding.label" default="Melding" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "melding")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.meldingType.label" default="Melding Type" /></td>
                            
                            <td valign="top" class="value">${meldingUtInstance?.meldingType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.sendtAv.label" default="Sendt Av" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "sendtAv")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.sendtOk.label" default="Sendt Ok" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingUtInstance?.sendtOk}" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.deaktivert.label" default="Deaktivert" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingUtInstance?.deaktivert}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.skjemaKortNavn.label" default="Skjema Kort Navn" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingUtInstance, field: "skjemaKortNavn")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingUt.tidRegistrert.label" default="Tid Registrert" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${meldingUtInstance?.tidRegistrert}" /></td>
                            
                        </tr>
                        
                        
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${meldingUtInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="list" value="${message(code: 'default.button.cancel.label', default: 'Avbryt')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
