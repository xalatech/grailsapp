

<%@ page import="sivadm.MeldingInn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'meldingInn.label', default: 'MeldingInn')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${meldingInnInstance}">
            <div class="errors">
                <g:renderErrors bean="${meldingInnInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="melding"><g:message code="meldingInn.melding.label" default="Melding" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'melding', 'errors')}">
                                    <g:textArea name="melding" cols="40" rows="5" value="${meldingInnInstance?.melding}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deadLetter"><g:message code="meldingInn.deadLetter.label" default="Dead Letter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'deadLetter', 'errors')}">
                                    <g:checkBox name="deadLetter" value="${meldingInnInstance?.deadLetter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="responseText"><g:message code="meldingInn.responseText.label" default="Response Text" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'responseText', 'errors')}">
                                    <g:textField name="responseText" value="${meldingInnInstance?.responseText}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="feilType"><g:message code="meldingInn.feilType.label" default="Feil Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'feilType', 'errors')}">
                                    <g:textField name="feilType" value="${meldingInnInstance?.feilType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deaktivert"><g:message code="meldingInn.deaktivert.label" default="Deaktivert" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'deaktivert', 'errors')}">
                                    <g:checkBox name="deaktivert" value="${meldingInnInstance?.deaktivert}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="intervjuObjektId"><g:message code="meldingInn.intervjuObjektId.label" default="Intervju Objekt Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'intervjuObjektId', 'errors')}">
                                    <g:textField name="intervjuObjektId" value="${fieldValue(bean: meldingInnInstance, field: 'intervjuObjektId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="intervjuObjektNummer"><g:message code="meldingInn.intervjuObjektNummer.label" default="Intervju Objekt Nummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'intervjuObjektNummer', 'errors')}">
                                    <g:textField name="intervjuObjektNummer" value="${meldingInnInstance?.intervjuObjektNummer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="skjemaKortNavn"><g:message code="meldingInn.skjemaKortNavn.label" default="Skjema Kort Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'skjemaKortNavn', 'errors')}">
                                    <g:textField name="skjemaKortNavn" value="${meldingInnInstance?.skjemaKortNavn}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="meldingInnType"><g:message code="meldingInn.meldingInnType.label" default="Melding Inn Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'meldingInnType', 'errors')}">
                                    <g:select name="meldingInnType" from="${siv.type.MeldingInnType?.values()}" optionKey="key" optionValue="guiName" value="${meldingInnInstance?.meldingInnType}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="mottattOk"><g:message code="meldingInn.mottattOk.label" default="Mottatt Ok" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'mottattOk', 'errors')}">
                                    <g:checkBox name="mottattOk" value="${meldingInnInstance?.mottattOk}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="antallForsok"><g:message code="meldingInn.antallForsok.label" default="Antall Forsok" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'antallForsok', 'errors')}">
                                    <g:textField name="antallForsok" value="${fieldValue(bean: meldingInnInstance, field: 'antallForsok')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sendtAv"><g:message code="meldingInn.sendtAv.label" default="Sendt Av" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'sendtAv', 'errors')}">
                                    <g:textField name="sendtAv" value="${meldingInnInstance?.sendtAv}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kommentar"><g:message code="meldingInn.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${meldingInnInstance?.kommentar}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="intervjuStatus"><g:message code="meldingInn.intervjuStatus.label" default="Intervju Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'intervjuStatus', 'errors')}">
                                    <g:textField name="intervjuStatus" value="${fieldValue(bean: meldingInnInstance, field: 'intervjuStatus')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dayBatchKode"><g:message code="meldingInn.dayBatchKode.label" default="Day Batch Kode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'dayBatchKode', 'errors')}">
                                    <g:textField name="dayBatchKode" value="${fieldValue(bean: meldingInnInstance, field: 'dayBatchKode')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tidRegistrert"><g:message code="meldingInn.tidRegistrert.label" default="Tid Registrert" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: meldingInnInstance, field: 'tidRegistrert', 'errors')}">
                                    <g:datePicker name="tidRegistrert" precision="day" value="${meldingInnInstance?.tidRegistrert}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
