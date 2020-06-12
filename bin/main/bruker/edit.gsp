

<%@ page import="sivadm.Bruker" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bruker.label', default: 'Bruker')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${brukerInstance}">
            <div class="errors">
                <g:renderErrors bean="${brukerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${brukerInstance?.id}" />
                <g:hiddenField name="version" value="${brukerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
	                        <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="navn"><g:message code="bruker.username.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'navn', 'errors')}">
                                    <g:textField name="navn" value="${brukerInstance?.navn}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="username"><g:message code="bruker.username.label" default="Brukernavn/initialer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${brukerInstance?.username}" maxlength="3" size="4" />
                                </td>
                            </tr>
                                                         
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="authorities"><g:message code="bruker.authorities.label" default="Roller" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'authorities', 'errors')}">
                                    
                                </td>
                            </tr>
                            
                             <g:each in="${rolleListe}" status="i" var="rolleInstance">
                             	<g:set var="fieldName" value="${rolleInstance.authority}" />
                             	<g:set var="fieldTitle" value="${fieldName?.substring(fieldName?.indexOf('_')+1)}" />
                             	<g:set var="harRolle" value="${false}" />
                            	<g:if test="${brukerInstance?.authorities?.contains(rolleInstance)}">
	                             	<g:set var="harRolle" value="${true}" />
                             	</g:if>
	                             <tr class="prop">
	                                <td valign="top" class="name">
	                                  &nbsp;
	                                </td>
	                                <td valign="top">
	                                   <g:checkBox name="${fieldName}" value="${harRolle}" /> ${fieldTitle}
	                                </td>
	                            </tr>
                             </g:each>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="enabled"><g:message code="bruker.enabled.label" default="Aktiv" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${brukerInstance?.enabled}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="accountExpired"><g:message code="bruker.accountExpired.label" default="Utgått" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'accountExpired', 'errors')}">
                                    <g:checkBox name="accountExpired" value="${brukerInstance?.accountExpired}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="accountLocked"><g:message code="bruker.accountLocked.label" default="Låst" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'accountLocked', 'errors')}">
                                    <g:checkBox name="accountLocked" value="${brukerInstance?.accountLocked}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="passwordExpired"><g:message code="bruker.passwordExpired.label" default="Passord utgått" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: brukerInstance, field: 'passwordExpired', 'errors')}">
                                    <g:checkBox name="passwordExpired" value="${brukerInstance?.passwordExpired}" />
                                </td>
                            </tr>
                                                
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
                <g:hiddenField name="password" value="${brukerInstance?.password}" />
            </g:form>
        </div>
    </body>
</html>
