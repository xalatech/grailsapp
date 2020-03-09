

<%@ page import="sivadm.ProsjektDeltager" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        	<br/>        	
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prosjektDeltagerInstance}">
            <div class="errors">
                <g:renderErrors bean="${prosjektDeltagerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prosjektDeltagerInstance?.id}" />
                <g:hiddenField name="version" value="${prosjektDeltagerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deltagerNavn"><g:message code="prosjektDeltager.deltagerNavn.label" default="Navn *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektDeltagerInstance, field: 'deltagerNavn', 'errors')}">
                                    <g:textField name="deltagerNavn" value="${prosjektDeltagerInstance?.deltagerNavn}" size="40"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deltagerInitialer"><g:message code="prosjektDeltager.deltagerInitialer.label" default="Initialer *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektDeltagerInstance, field: 'deltagerInitialer', 'errors')}">
                                    <g:textField name="deltagerInitialer" value="${prosjektDeltagerInstance?.deltagerInitialer}" maxlength="3" size="4" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deltagerEpost"><g:message code="prosjektDeltager.deltagerEpost.label" default="Epost" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektDeltagerInstance, field: 'deltagerEpost', 'errors')}">
                                    <g:textField name="deltagerEpost" value="${prosjektDeltagerInstance?.deltagerEpost}" size="40"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                	<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                	<span class="menuButton"><g:link class="delete" controller="prosjekt" action="edit" id="${prosjektId}"><g:message code="sil.avbryt" /></g:link></span>
                </div>
                <g:hiddenField name="prosjektId" value="${prosjektId}" />
                <g:hiddenField name="prosjekt.id" value="${prosjektId}" />
            </g:form>
        </div>
    </body>
</html>
