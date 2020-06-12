

<%@ page import="sivadm.Klynge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'klynge.label', default: 'Klynge')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${klyngeInstance}">
            <div class="errors">
                <g:renderErrors bean="${klyngeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="klyngeNavn"><g:message code="klynge.klyngeNavn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'klyngeNavn', 'errors')}">
                                    <g:textField name="klyngeNavn" value="${klyngeInstance?.klyngeNavn}" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="klyngeSjef"><g:message code="klynge.klyngeSjef.label" default="Klyngesjef" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'klyngeSjef', 'errors')}">
                                    <g:textField name="klyngeSjef" value="${klyngeInstance?.klyngeSjef}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="epost"><g:message code="klynge.epost.label" default="Epost" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'epost', 'errors')}">
                                    <g:textField name="epost" value="${klyngeInstance?.epost}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                    	<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                    	<g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link>
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>
