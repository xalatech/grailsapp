

<%@ page import="sivadm.Fravaer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'fravaer.label', default: 'Fravaer')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:javascript>
            function setDatepickerDefaultDate() {
                var datoFra = document.getElementById("dp-1").value;
                jQuery('#dp-2').datepicker("option", "defaultDate", datoFra)
            }
        </g:javascript>
        <div class="body">
            <h1>Opprett fravær for intervjuer: ${intervjuerInstance}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${fravaerInstance}">
            <div class="errors">
                <g:renderErrors bean="${fravaerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
            
            <g:hiddenField name="intervjuerId" value="${intervjuerInstance.id}"></g:hiddenField>
            
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fraTidspunkt"><g:message code="fravaer.fraTidspunkt.label" default="Fra" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: fravaerInstance, field: 'fraTidspunkt', 'errors')}">
                                    <g:datoVelger id="dp-1" name="fraTidspunkt" value="${fravaerInstance?.fraTidspunkt}" onChange="setDatepickerDefaultDate()" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tilTidspunkt"><g:message code="fravaer.tilTidspunkt.label" default="Til" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: fravaerInstance, field: 'tilTidspunkt', 'errors')}">
                                    <g:datoVelger id="dp-2" name="tilTidspunkt" value="${fravaerInstance?.tilTidspunkt}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fravaerType"><g:message code="fravaer.fravaerType.label" default="Fraværstype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: fravaerInstance, field: 'fravaerType', 'errors')}">
                                    <g:select name="fravaerType" from="${siv.type.FravaerType?.values()}" optionKey="key" optionValue="guiName" value="${fravaerInstance?.fravaerType?.key}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="prosent"><g:message code="fravaer.prosent.label" default="Prosent" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: fravaerInstance, field: 'prosent', 'errors')}">
                                    <g:textField name="prosent" value="${fieldValue(bean: fravaerInstance, field: 'prosent')}" size="3" maxlength="3" /> %
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kommentar"><g:message code="fravaer.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: fravaerInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${fravaerInstance?.kommentar}" size="60"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="menuButton"><g:link class="delete" controller="intervjuer" action="edit" id="${intervjuerInstance.id}"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
