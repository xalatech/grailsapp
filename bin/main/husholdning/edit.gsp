

<%@ page import="sivadm.Husholdning" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'husholdning.label', default: 'Husholdning')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${husholdningInstance}">
            <div class="errors">
                <g:renderErrors bean="${husholdningInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${husholdningInstance?.id}" />
                <g:hiddenField name="version" value="${husholdningInstance?.version}" />
                <g:hiddenField name="ioId" value="${ioId}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="navn"><g:message code="husholdning.navn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: husholdningInstance, field: 'navn', 'errors')}">
                                    <g:textField name="navn" value="${husholdningInstance?.navn}" size="30" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="personKode"><g:message code="husholdning.personKode.label" default="Personkode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: husholdningInstance, field: 'personKode', 'errors')}">
  	                            	<g:textField name="personKode" value="${husholdningInstance?.personKode}" size="1" maxlength="1" />
									<g:message code="sivadm.intervjuobjekt.personkode.info" default="(1=Ref.person, 2=Ektefelle, 3=Barn)"/>
                                </td>
                            </tr>
                                                
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fodselsDato"><g:message code="husholdning.fodselsDato.label" default="Fødselsdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: husholdningInstance, field: 'fodselsDato', 'errors')}">
                                    <g:datoVelger name="fodselsDato" value="${husholdningInstance?.fodselsDato}" />
                                </td>
                            </tr>
                                 
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fodselsNummer"><g:message code="husholdning.fodselsNummer.label" default="Fødselsnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: husholdningInstance, field: 'fodselsNummer', 'errors')}">
                                    <g:textField name="fodselsNummer" maxlength="11" value="${husholdningInstance?.fodselsNummer}" size="11" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="husholdNummer"><g:message code="husholdning.husholdNummer.label" default="Husholdningsummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: husholdningInstance, field: 'husholdNummer', 'errors')}">
                                    ${fieldValue(bean: husholdningInstance, field: 'husholdNummer')}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="cancelCreate" id="${ioId}"><g:message code="sil.avbryt" default="Avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
