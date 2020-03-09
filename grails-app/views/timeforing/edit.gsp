

<%@ page import="sivadm.Timeforing" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'timeforing.label', default: 'Timeforing')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript src="kolon.js"/>
        <g:javascript src="fullforKlokkeslett.js"/>
    </head>
    <body>
        <div class="body">
            <h1>Rediger timeføring - <g:if test="${timeforingInstance.fra}"><g:formatDate date="${timeforingInstance.fra}" format="${message(code: 'default.date.format.short', default: 'dd.MM.yyyy')}"/></g:if></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">${flash.errorMessage}</div>
            </g:if>
            <g:hasErrors bean="${timeforingInstance}">
            <div class="errors">
                <g:renderErrors bean="${timeforingInstance}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:hasErrors bean="${fraTilCommand}">
	            <div class="errors">
	                <g:renderErrors bean="${fraTilCommand}" as="list" />
	            </div>
            </g:hasErrors>
            <g:if test="${timeforingInstance?.silMelding}">
            	<br><br>
      			<h3><g:message code="timeforing.melding.fra.kontroll" default="Melding fra kontroll" /></h3><br>
            	<h4>${timeforingInstance?.silMelding?.tittel}</h4>
            	<span style="color: red"></span>
            	<p>${timeforingInstance?.silMelding?.melding}</p>
            	<br><br>
            </g:if>
            <g:form method="post" >
                <g:hiddenField name="id" value="${timeforingInstance?.id}" />
                <g:hiddenField name="version" value="${timeforingInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fraTid"><g:message code="timeforing.fra.label" default="Fra" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: timeforingInstance, field: 'fra', 'errors') || hasErrors(bean: fraTilCommand, field: 'fraTid', 'errors')) ? "errors" : ""}">
                                    <g:textField onchange="kolon('fraTid')" name="fraTid" value="${fraTid}" size="5" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tilTid"><g:message code="timeforing.til.label" default="Til" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: timeforingInstance, field: 'til', 'errors') || hasErrors(bean: fraTilCommand, field: 'tilTid', 'errors')) ? "errors" : ""}">
                                    <g:textField onchange="kolon('tilTid')" name="tilTid" value="${tilTid}" size="5" />
                                </td>
                            </tr>
                         
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="kjorebok.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeforingInstance, field: 'produktNummer', 'errors')}">
                                    <g:select name="produktNummer" from="${produktNummerListe}" optionKey="produktNummer" value="${timeforingInstance?.produktNummer}" />
                                </td>
                            </tr>
                        
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="arbeidsType"><g:message code="timeforing.arbeidsType.label" default="Arbeidstype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeforingInstance, field: 'arbeidsType', 'errors')}">
                                    <g:select name="arbeidsType"
                                    	from="${arbeidsTypeListe}" 
                                    	optionKey="key"
										optionValue="guiName"
                                    	value="${timeforingInstance?.arbeidsType?.key}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <g:if test="${params?.kravId}">
	           		<g:hiddenField name="kravId" value="${params?.kravId}" />
	           		<g:hiddenField name="isFailed" value="${params?.isFailed}" />
	           		<g:hiddenField name="tittel" value="${params?.tittel}" />
	           		<g:hiddenField name="melding" value="${params?.melding}" />
           		</g:if>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Lagre" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt"  /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
