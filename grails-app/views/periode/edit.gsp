<%@ page import="siv.type.PeriodeType" %>
<%@ page import="sivadm.Periode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'periode.label', default: 'Periode')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${periodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${periodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${periodeInstance?.id}" />
                <g:hiddenField name="version" value="${periodeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>  
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="periodeNummer"><g:message code="periode.periodeNummer.label" default="Periodenummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'periodeNummer', 'errors')}">
                                    <g:textField name="periodeNummer" value="${fieldValue(bean: periodeInstance, field: 'periodeNummer')}" size="3"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="periodeType"><g:message code="periode.periodeType.label" default="Periodetype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'periodeType', 'errors')}">
                                    <g:select name="periodeType"
										from="${PeriodeType.values()}"
										optionKey="key"
										optionValue="guiName"
										value="${periodeInstance?.periodeType?.key}"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="delregisterNummer"><g:message code="periode.delregisterNummer.label" default="Delregisternummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'delregisterNummer', 'errors')}">
                                    <g:textField name="delregisterNummer" value="${periodeInstance?.delregisterNummer}" size="6" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="aar"><g:message code="periode.aar.label" default="År" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'aar', 'errors')}">
                                    <g:textField name="aar" value="${periodeInstance?.aar}" maxlength="4" size="4"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="oppstartDataInnsamling"><g:message code="periode.oppstartDataInnsamling.label" default="Oppstart datainnsamling" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'oppstartDataInnsamling', 'errors')}">
                                    <g:datoVelger id="dp1" name="oppstartDataInnsamling" value="${periodeInstance?.oppstartDataInnsamling}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="planlagtSluttDato"><g:message code="periode.planlagtSluttDato.label" default="Planlagt sluttdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'planlagtSluttDato', 'errors')}">
                                    <g:datoVelger id="dp2" name="planlagtSluttDato" value="${periodeInstance?.planlagtSluttDato}"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sluttDato"><g:message code="periode.sluttDato.label" default="Sluttdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'sluttDato', 'errors')}">
                                    <g:datoVelger id="dp3" name="sluttDato" value="${periodeInstance?.sluttDato}"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hentesTidligst"><g:message code="periode.hentesTidligst.label" default="Hentes tidligst" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'hentesTidligst', 'errors')}">
                                    <g:datoVelger id="dp4" name="hentesTidligst" value="${periodeInstance?.hentesTidligst}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="incentiver"><g:message code="periode.incentiver.label" default="Incentiver" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'incentiver', 'errors')}">
                                    <g:textField name="incentiver" value="${periodeInstance?.incentiver}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kommentar"><g:message code="periode.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: periodeInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${periodeInstance?.kommentar}" size="40"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt"/></span>
                </div>
                <g:if test="${fraSkjemaListe}">
              	  <g:hiddenField name="fraSkjemaListe" value="${fraSkjemaListe}" />
                </g:if>
                <g:hiddenField name="skjemaId" value="${params.skjemaId}" />
            </g:form>
        </div>
    </body>
</html>
