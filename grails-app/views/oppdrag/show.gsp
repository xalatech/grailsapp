
<%@ page import="sivadm.Oppdrag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'oppdrag.label', default: 'Oppdrag')}" />
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
                            <td valign="top" class="name"><g:message code="oppdrag.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "id")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuObjekt.label" default="Intervjuobjekt" /></td>
                            
                            <td valign="top" class="value"><g:link controller="intervjuObjekt" action="show" id="${oppdragInstance?.intervjuObjekt?.id}">${oppdragInstance?.intervjuObjekt?.encodeAsHTML()}</g:link></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuStatus.label" default="Intervjustatus" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "intervjuStatus")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuType.label" default="Intervjutype" /></td>
                            
                            <td valign="top" class="value">${oppdragInstance?.intervjuType?.encodeAsHTML()}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuStatusDato.label" default="Intervjustatus dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${oppdragInstance?.intervjuStatusDato}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuStatusKommentar.label" default="Intervjustatus kommentar" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "intervjuStatusKommentar")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuObjektNummer.label" default="Intervjuobjektnummer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "intervjuObjektNummer")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.skjemaKortNavn.label" default="Skjema kortnavn" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "skjemaKortNavn")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.periodeNummer.label" default="Periodenummer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "periodeNummer")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.oppdragsKilde.label" default="Oppdragskilde" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "oppdragsKilde")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.hentesTidligst.label" default="Hentes tidligst" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${oppdragInstance?.hentesTidligst}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuerInitialer.label" default="Intervjuer initialer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "intervjuerInitialer")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuer.label" default="Intervjuer" /></td>
                            
                            <td valign="top" class="value"><g:link controller="intervjuer" action="show" id="${oppdragInstance?.intervjuer?.id}">${oppdragInstance?.intervjuer?.encodeAsHTML()}</g:link></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.intervjuStart.label" default="Intervju start" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "intervjuStart")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.bruktTid.label" default="Brukt yid" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "bruktTid")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.returStatus.label" default="Returstatus" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "returStatus")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.overfortLokaltTidspunkt.label" default="Overfort lokalt tidspunkt" /></td>
                            
                            <td valign="top" class="value">${oppdragInstance?.overfortLokaltTidspunkt}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.sisteSynkTidspunkt.label" default="Siste synktidspunkt" /></td>
                            
                            <td valign="top" class="value">${oppdragInstance?.sisteSynkTidspunkt}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.tildelingsType.label" default="Tildelingstype" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "tildelingsType")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.oppdragFullfort.label" default="Oppdrag fullfort" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${oppdragInstance?.oppdragFullfort}" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.endringIntervjuObjekt.label" default="Endring intervjuobjekt" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "endringIntervjuObjekt")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.endringAvtale.label" default="Endring avtale" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "endringAvtale")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.endringKontakt.label" default="Endring kontakt" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "endringKontakt")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.endringData.label" default="Endring data" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "endringData")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.endringTidspunkt.label" default="Endring tidspunkt" /></td>
                            
                            <td valign="top" class="value">${oppdragInstance?.endringTidspunkt}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.merketSlettHosIntervjuer.label" default="Merket slett hos intervjuer" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${oppdragInstance?.merketSlettHosIntervjuer}" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.slettetHosIntervjuer.label" default="Slettet hos intervjuer" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${oppdragInstance?.slettetHosIntervjuer}" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.selvplukk.label" default="Selvplukk" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${oppdragInstance?.selvplukk}" /></td>
                        </tr>
                    
                    	<tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.klarTilSynk.label" default="Klar til synk" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${oppdragInstance?.klarTilSynk}" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.generertAv.label" default="Generert av" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "generertAv")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.generertDato.label" default="Generert dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${oppdragInstance?.generertDato}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.operatorId.label" default="Operator id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "operatorId")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.gyldighetsDato.label" default="Gyldighets dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${oppdragInstance?.gyldighetsDato}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.kansellertDato.label" default="Kansellert dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${oppdragInstance?.kansellertDato}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.kansellertAv.label" default="Kansellert av" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "kansellertAv")}</td>
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="oppdrag.kansellertKommentar.label" default="Kansellert kommentar" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: oppdragInstance, field: "kansellertKommentar")}</td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${oppdragInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="list" value="${message(code: 'default.button.cancel.label', default: 'Avbryt')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
