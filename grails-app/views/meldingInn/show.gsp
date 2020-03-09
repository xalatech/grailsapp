
<%@ page import="sivadm.MeldingInn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'meldingInn.label', default: 'MeldingInn')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.melding.label" default="Melding" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "melding")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.deadLetter.label" default="Dead Letter" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingInnInstance?.deadLetter}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.responseText.label" default="Response Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "responseText")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.feilType.label" default="Feil Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "feilType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.deaktivert.label" default="Deaktivert" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingInnInstance?.deaktivert}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.intervjuObjektId.label" default="Intervju Objekt Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "intervjuObjektId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.intervjuObjektNummer.label" default="Intervju Objekt Nummer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "intervjuObjektNummer")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.skjemaKortNavn.label" default="Skjema Kort Navn" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "skjemaKortNavn")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.meldingInnType.label" default="Melding Inn Type" /></td>
                            
                            <td valign="top" class="value">${meldingInnInstance?.meldingInnType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.mottattOk.label" default="Mottatt Ok" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${meldingInnInstance?.mottattOk}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.antallForsok.label" default="Antall Forsok" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "antallForsok")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.sendtAv.label" default="Sendt Av" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "sendtAv")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.kommentar.label" default="Kommentar" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "kommentar")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.intervjuStatus.label" default="Intervju Status" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "intervjuStatus")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.dayBatchKode.label" default="Day Batch Kode" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: meldingInnInstance, field: "dayBatchKode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="meldingInn.tidRegistrert.label" default="Tid Registrert" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${meldingInnInstance?.tidRegistrert}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${meldingInnInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
