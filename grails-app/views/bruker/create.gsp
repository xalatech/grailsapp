

<%@ page import="sivadm.Bruker" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'bruker.label', default: 'Bruker')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${brukerInstance}">
            <div class="errors">
                <g:renderErrors bean="${brukerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                <td valign="top">
                                    &nbsp;
                                </td>
                            </tr>
                            
                             <g:each in="${rolleListe}" status="i" var="rolleInstance">
                             	<g:set var="fieldName" value="${rolleInstance.authority}" />
                             	<g:set var="fieldTitle" value="${fieldName?.substring(fieldName?.indexOf('_')+1)}" />
                             	<g:set var="harRolle" value="${false}" />
                            	
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
