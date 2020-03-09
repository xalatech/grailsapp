

<%@ page import="sivadm.Kommune" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'kommune.label', default: 'Kommune')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${kommuneInstance}">
            <div class="errors">
                <g:renderErrors bean="${kommuneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kommuneNavn"><g:message code="kommune.kommuneNavn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kommuneInstance, field: 'kommuneNavn', 'errors')}">
                                    <g:textField name="kommuneNavn" value="${kommuneInstance?.kommuneNavn}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kommuneNummer"><g:message code="kommune.kommuneNummer.label" default="Kommunenummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kommuneInstance, field: 'kommuneNummer', 'errors')}">
                                    <g:textField name="kommuneNummer" value="${kommuneInstance?.kommuneNummer}" size="4" maxlength="4" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
