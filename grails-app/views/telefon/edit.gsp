
<%@ page import="sivadm.Telefon" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'telefon.label', default: 'Telefon')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">${flash.errorMessage}</div>
            </g:if>
            
            <g:hasErrors bean="${telefonInstance}">
            <div class="errors">
                <g:renderErrors bean="${telefonInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${telefonInstance?.id}" />
                <g:hiddenField name="version" value="${telefonInstance?.version}" />
                <g:hiddenField name="ioId" value="${ioId}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="telefonNummer"><g:message code="telefon.telefonNummer.label" default="Telefonnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: telefonInstance, field: 'telefonNummer', 'errors')}">
                                    <g:textField name="telefonNummer" value="${telefonInstance?.telefonNummer}" size="10" maxlength="8" />
                                </td>
                            </tr>
                            
                          	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommentar"><g:message code="telefon.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: telefonInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${telefonInstance?.kommentar}" size="80" />
                                </td>
                            </tr>

                          	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kilde"><g:message code="telefon.kilde.label" default="Kilde" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: telefonInstance, field: 'kilde', 'errors')}">
                                    <g:textField name="kilde" value="${telefonInstance?.kilde}" size="50" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="resepsjon"><g:message code="telefon.resepsjon.label" default="Resepsjon" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: telefonInstance, field: 'resepsjon', 'errors')}">
                                    <g:checkBox name="resepsjon" value="${telefonInstance?.resepsjon}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gjeldende"><g:message code="telefon.gjeldende.label" default="Gjeldende" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: telefonInstance, field: 'gjeldende', 'errors')}">
                                    <g:checkBox name="gjeldende" value="${telefonInstance?.gjeldende}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Lagre" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
