<%@ page import="sivadm.Oppdrag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'oppdrag.label', default: 'Oppdrag')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${oppdragInstance}">
            <div class="errors">
                <g:renderErrors bean="${oppdragInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${oppdragInstance?.id}" />
                <g:hiddenField name="version" value="${oppdragInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuObjekt"><g:message code="oppdrag.intervjuObjekt.label" default="Intervjuobjekt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuObjekt', 'errors')}">
                                    <p>${oppdragInstance?.intervjuObjekt}</p>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuStatus"><g:message code="oppdrag.intervjuStatus.label" default="Intervjustatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuStatus', 'errors')}">
                                    <g:textField name="intervjuStatus" value="${fieldValue(bean: oppdragInstance, field: 'intervjuStatus')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuType"><g:message code="oppdrag.intervjuType.label" default="Intervjutype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuType', 'errors')}">
                                    <g:select name="intervjuType" from="${siv.type.IntervjuType?.values()}" optionKey="key" optionValue="guiName" value="${oppdragInstance?.intervjuType?.key}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuStatusDato"><g:message code="oppdrag.intervjuStatusDato.label" default="Intervjustatus dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuStatusDato', 'errors')}">
                                    <g:datoVelger id="dp-1" name="intervjuStatusDato" value="${oppdragInstance?.intervjuStatusDato}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuStatusKommentar"><g:message code="oppdrag.intervjuStatusKommentar.label" default="Intervjustatus kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuStatusKommentar', 'errors')}">
                                    <g:textField name="intervjuStatusKommentar" value="${oppdragInstance?.intervjuStatusKommentar}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuObjektNummer"><g:message code="oppdrag.intervjuObjektNummer.label" default="Intervjuobjektnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuObjektNummer', 'errors')}">
                                    <g:textField name="intervjuObjektNummer" value="${oppdragInstance?.intervjuObjektNummer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="skjemaKortNavn"><g:message code="oppdrag.skjemaKortNavn.label" default="Skjema kortnavn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'skjemaKortNavn', 'errors')}">
                                    <g:textField name="skjemaKortNavn" value="${oppdragInstance?.skjemaKortNavn}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="periodeNummer"><g:message code="oppdrag.periodeNummer.label" default="Periodenummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'periodeNummer', 'errors')}">
                                    <g:textField name="periodeNummer" value="${fieldValue(bean: oppdragInstance, field: 'periodeNummer')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="oppdragsKilde"><g:message code="oppdrag.oppdragsKilde.label" default="Oppdragskilde" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'oppdragsKilde', 'errors')}">
                                    <g:textField name="oppdragsKilde" value="${fieldValue(bean: oppdragInstance, field: 'oppdragsKilde')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hentesTidligst"><g:message code="oppdrag.hentesTidligst.label" default="Hentes tidligst" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'hentesTidligst', 'errors')}">
                                    <g:datoVelger id="dp-2" name="hentesTidligst" value="${oppdragInstance?.hentesTidligst}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuerInitialer"><g:message code="oppdrag.intervjuerInitialer.label" default="Intervjuer initialer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuerInitialer', 'errors')}">
                                    <g:textField name="intervjuerInitialer" value="${oppdragInstance?.intervjuerInitialer}" maxlength="3" size="4" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuer"><g:message code="oppdrag.intervjuer.label" default="Intervjuer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuer', 'errors')}">
                                    <g:select name="intervjuer.id" from="${sivadm.Intervjuer.list()}" optionKey="id" value="${oppdragInstance?.intervjuer?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuStart"><g:message code="oppdrag.intervjuStart.label" default="Intervjustart" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'intervjuStart', 'errors')}">
                                    <g:textField name="intervjuStart" value="${oppdragInstance?.intervjuStart}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bruktTid"><g:message code="oppdrag.bruktTid.label" default="Brukt tid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'bruktTid', 'errors')}">
                                    <g:textField name="bruktTid" value="${fieldValue(bean: oppdragInstance, field: 'bruktTid')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="returStatus"><g:message code="oppdrag.returStatus.label" default="Returstatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'returStatus', 'errors')}">
                                    <g:textField name="returStatus" value="${oppdragInstance?.returStatus}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="overfortLokaltTidspunkt"><g:message code="oppdrag.overfortLokaltTidspunkt.label" default="Overfort lokalt tidspunkt" /></label>
                                </td>
                                <td valign="top">
                                    <p>${oppdragInstance?.overfortLokaltTidspunkt}</p>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sisteSynkTidspunkt"><g:message code="oppdrag.sisteSynkTidspunkt.label" default="Siste synktidspunkt" /></label>
                                </td>
                                <td valign="top">
                                    <p>${oppdragInstance?.sisteSynkTidspunkt}</p>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tildelingsType"><g:message code="oppdrag.tildelingsType.label" default="Tildelingstype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'tildelingsType', 'errors')}">
                                    <g:textField name="tildelingsType" value="${oppdragInstance?.tildelingsType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="oppdragFullfort"><g:message code="oppdrag.oppdragFullfort.label" default="Oppdrag fullfort" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'oppdragFullfort', 'errors')}">
                                    <g:checkBox name="oppdragFullfort" value="${oppdragInstance?.oppdragFullfort}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endringIntervjuObjekt"><g:message code="oppdrag.endringIntervjuObjekt.label" default="Endring intervjuobjekt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'endringIntervjuObjekt', 'errors')}">
                                    <g:textField name="endringIntervjuObjekt" value="${oppdragInstance?.endringIntervjuObjekt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endringAvtale"><g:message code="oppdrag.endringAvtale.label" default="Endring avtale" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'endringAvtale', 'errors')}">
                                    <g:textField name="endringAvtale" value="${oppdragInstance?.endringAvtale}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endringKontakt"><g:message code="oppdrag.endringKontakt.label" default="Endring kontakt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'endringKontakt', 'errors')}">
                                    <g:textField name="endringKontakt" value="${oppdragInstance?.endringKontakt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endringData"><g:message code="oppdrag.endringData.label" default="Endring data" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'endringData', 'errors')}">
                                    <g:textField name="endringData" value="${oppdragInstance?.endringData}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endringTidspunkt"><g:message code="oppdrag.endringTidspunkt.label" default="Endring tidspunkt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'endringTidspunkt', 'errors')}">
                                     <g:textField name="endringTidspunkt" value="${oppdragInstance?.endringTidspunkt}" /> dd.MM.åååå tt:mm:ss
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="merketSlettHosIntervjuer"><g:message code="oppdrag.merketSlettHosIntervjuer.label" default="Merket slett hos intervjuer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'merketSlettHosIntervjuer', 'errors')}">
                                    <g:checkBox name="merketSlettHosIntervjuer" value="${oppdragInstance?.merketSlettHosIntervjuer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="slettetHosIntervjuer"><g:message code="oppdrag.slettetHosIntervjuer.label" default="Slettet hos intervjuer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'slettetHosIntervjuer', 'errors')}">
                                    <g:checkBox name="slettetHosIntervjuer" value="${oppdragInstance?.slettetHosIntervjuer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="selvplukk"><g:message code="oppdrag.selvplukk.label" default="Selvplukk" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'selvplukk', 'errors')}">
                                    <g:checkBox name="selvplukk" value="${oppdragInstance?.selvplukk}" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="klarTilSynk"><g:message code="oppdrag.klarTilSynk.label" default="Klar til synk" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'klarTilSynk', 'errors')}">
                                    <g:checkBox name="klarTilSynk" value="${oppdragInstance?.klarTilSynk}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="generertAv"><g:message code="oppdrag.generertAv.label" default="Generert av" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'generertAv', 'errors')}">
                                    <g:textField name="generertAv" value="${oppdragInstance?.generertAv}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="generertDato"><g:message code="oppdrag.generertDato.label" default="Generert dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'generertDato', 'errors')}">
                                    <g:datoVelger id="dp-6" name="generertDato" value="${oppdragInstance?.generertDato}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="operatorId"><g:message code="oppdrag.operatorId.label" default="Operator id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'operatorId', 'errors')}">
                                    <g:textField name="operatorId" value="${oppdragInstance?.operatorId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gyldighetsDato"><g:message code="oppdrag.gyldighetsDato.label" default="Gyldighetsdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'gyldighetsDato', 'errors')}">
                                    <g:datoVelger id="dp-7" name="gyldighetsDato" value="${oppdragInstance?.gyldighetsDato}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kansellertDato"><g:message code="oppdrag.kansellertDato.label" default="Kansellert dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'kansellertDato', 'errors')}">
                                    <g:datoVelger id="dp-8" name="kansellertDato" value="${oppdragInstance?.kansellertDato}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kansellertAv"><g:message code="oppdrag.kansellertAv.label" default="Kansellert av" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'kansellertAv', 'errors')}">
                                    <g:textField name="kansellertAv" value="${oppdragInstance?.kansellertAv}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kansellertKommentar"><g:message code="oppdrag.kansellertKommentar.label" default="Kansellert kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: oppdragInstance, field: 'kansellertKommentar', 'errors')}">
                                    <g:textField name="kansellertKommentar" value="${oppdragInstance?.kansellertKommentar}" />
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
