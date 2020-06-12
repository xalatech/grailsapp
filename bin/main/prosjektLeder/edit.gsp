

<%@ page import="sivadm.ProsjektLeder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'prosjektLeder.label', default: 'ProsjektLeder')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prosjektLederInstance}">
            <div class="errors">
                <g:renderErrors bean="${prosjektLederInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prosjektLederInstance?.id}" />
                <g:hiddenField name="version" value="${prosjektLederInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
	                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="navn"><g:message code="prosjektLeder.navn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektLederInstance, field: 'navn', 'errors')}">
                                    <g:textField name="navn" value="${prosjektLederInstance?.navn}" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="initialer"><g:message code="prosjektLeder.initialer.label" default="Initialer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektLederInstance, field: 'initialer', 'errors')}">
                                    <g:textField name="initialer" value="${prosjektLederInstance?.initialer}" maxlength="3" size="4" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="epost"><g:message code="prosjektLeder.epost.label" default="Epost" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektLederInstance, field: 'epost', 'errors')}">
                                    <g:textField name="epost" value="${prosjektLederInstance?.epost}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
