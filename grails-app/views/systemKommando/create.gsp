

<%@ page import="sivadm.SystemKommando" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'systemKommando.label', default: 'SystemKommando')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${systemKommandoInstance}">
            <div class="errors">
                <g:renderErrors bean="${systemKommandoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="filnavn"><g:message code="systemKommando.filnavn.label" default="Filnavn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'filnavn', 'errors')}">
                                    <g:textField name="filnavn" value="${systemKommandoInstance?.filnavn}" size="80"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="beskrivelse"><g:message code="systemKommando.beskrivelse.label" default="Beskrivelse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'beskrivelse', 'errors')}">
                                    <g:textField name="beskrivelse" value="${systemKommandoInstance?.beskrivelse}" size="80"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maksSekunder"><g:message code="systemKommando.maksSekunder.label" default="Maks sekunder" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'maksSekunder', 'errors')}">
                                    <g:textField name="maksSekunder" value="${fieldValue(bean: systemKommandoInstance, field: 'maksSekunder')}"  size="3"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:actionSubmit value="Avbryt" action="list" class="delete"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
