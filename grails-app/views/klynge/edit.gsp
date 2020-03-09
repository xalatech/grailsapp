

<%@ page import="sivadm.Klynge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'klynge.label', default: 'Klynge')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${klyngeInstance}">
            <div class="errors">
                <g:renderErrors bean="${klyngeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${klyngeInstance?.id}" />
                <g:hiddenField name="version" value="${klyngeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="klyngeNavn"><g:message code="klynge.klyngeNavn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'klyngeNavn', 'errors')}">
                                    <g:textField name="klyngeNavn" value="${klyngeInstance?.klyngeNavn}" size="40" />
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
                                  <label for="epost"><g:message code="klynge.epost.label" default="E-post adresse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'epost', 'errors')}">
                                    <g:textField name="epost" value="${klyngeInstance?.epost}" size="40" />
                                </td>
                            </tr>
                                                
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommuner"><g:message code="klynge.kommuner.label" default="Kommuner" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'kommuner', 'errors')}">
                                    <g:select name="kommuner" from="${sivadm.Kommune.list().sort{it.kommuneNummer}}" multiple="yes" optionKey="id" size="10" title="${message(code: 'sivadm.klynge.kommune', default: '')}" value="${klyngeInstance?.kommuner}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuere"><g:message code="klynge.intervjuere.label" default="Intervjuere" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: klyngeInstance, field: 'intervjuere', 'errors')}">
									<g:if test="${klyngeInstance?.intervjuere}">
										${klyngeInstance?.intervjuere.size()} <g:message code="sivadm.intervjuere" default="intervjuere" />:
										<ul>
											<g:each in="${klyngeInstance?.intervjuere?}" var="i">
										    	<li><g:link controller="intervjuer" action="edit" id="${i.id}" params="['klyngeId': klyngeInstance?.id]">${i?.encodeAsHTML()}</g:link></li>
											</g:each>
										</ul>
									</g:if>
									<g:else>
										<g:message code="sivadm.klynge.ingen.intervjuere" default="Ingen intervjuere tilknyttet klynge" />
									</g:else>
									<!-- 
									<g:link controller="intervjuer" action="create" params="['klynge.id': klyngeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'intervjuer.label', default: 'Intervjuer')])}</g:link>
 									-->
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
