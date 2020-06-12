<%@ page import="sivadm.Timeforing" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timeforing.label', default: 'Timeforing')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript src="kolon.js"/>
        <g:javascript src="fullforKlokkeslett.js"/>
    </head>
    <body>
        
        <div class="body">
        
        <h1>Arbeidstid - <g:if test="${session.timeforingDato}"><g:formatDate date="${session.timeforingDato}" format="${message(code: 'default.date.format.short', default: 'dd.MM.yyyy')}"/></g:if></h1>
            
            <div class="list">
            	
            	<g:if test="${timeforingInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen registrert arbeidstid er lagt inn.</p>
            		<br/>
            		<br/>
            	</g:if>
            	
            	<g:else>
            	
            	<table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="fra" title="${message(code: 'timeforing.fra.label', default: 'Fra')}" />
                            
                            <g:sortableColumn property="til" title="${message(code: 'timeforing.til.label', default: 'Til')}" />
                        
                            <g:sortableColumn property="produktNummer" title="${message(code: 'timeforing.produktNummer.label', default: 'Produktnummer')}" />
                                
                            <g:sortableColumn property="arbeidsType" title="${message(code: 'timeforing.arbeidsType.label', default: 'Arbeidstype')}" />
                            
                            <g:sortableColumn property="timeforingStatus" title="${message(code: 'timeforing.timeforingStatus.label', default: 'Status')}" />
                        	
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${timeforingInstanceList}" status="i" var="timeforingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:formatDate date="${timeforingInstance.fra}" format="HH:mm"/></td>
                            
                            <td><g:formatDate date="${timeforingInstance.til}" format="HH:mm"/></td>
                        
                            <td>${fieldValue(bean: timeforingInstance, field: "produktNummer")}</td>
                         
                            <td>${fieldValue(bean: timeforingInstance, field: "arbeidsType")}</td>
                            
                            <td>${fieldValue(bean: timeforingInstance, field: "timeforingStatus")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            	
            	</g:else>
            	
            </div>
            
            <br/>
            <br/>
       
            <h1>Legg til ny arbeidstid - <g:if test="${session.timeforingDato}"><g:formatDate date="${session.timeforingDato}" format="${message(code: 'default.date.format.short', default: 'dd.MM.yyyy')}"/></g:if></h1>
            
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
            
            
            <g:form action="save" method="post" >
            
                <div class="dialog">
                    <table>
                        <tbody>
                        
                           <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fraTid"><g:message code="timeforing.fra.label" default="Fra" /></label>
                                </td>         
                                <td valign="top" class="value ${(hasErrors(bean: timeforingInstance, field: 'fra', 'errors') || hasErrors(bean: fraTilCommand, field: 'fraTid', 'errors')) ? "errors" : ""}">
                                    <g:textField onchange="kolon('fraTid')" name="fraTid" value="${fraTid}" size="5" title="Eksempel: 09:00"/>
                                    (Eksempel: 09:00)
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tilTid"><g:message code="timeforing.til.label" default="Til" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: timeforingInstance, field: 'til', 'errors') || hasErrors(bean: fraTilCommand, field: 'tilTid', 'errors')) ? "errors" : ""}">
                                    <g:textField onchange="kolon('tilTid')" name="tilTid" value="${tilTid}" size="5" title="Eksempel: 10:00"/>
                                    (Eksempel: 10:00)
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="kjorebok.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timeforingInstance, field: 'produktNummer', 'errors')}">
                                    <g:select name="produktNummer" from="${produktNummerListe}" optionKey="produktNummer" value="${timeforingInstance?.produktNummer}" noSelection="['':'-Velg produktkode-']" />
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
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" title="${message(code: 'timeforing.avbryt.tooltip', default: 'Gå tilbake til oversikt for valgt dato', args: [session.timeforingDato?.format('dd.MM.yyyy')])}" /></span>
                    <span class="menuButton"><g:link action="avbryt"  title="${message(code: 'timeforing.avbryt.tooltip', default: 'Gå tilbake til oversikt for valgt dato', args: [session.timeforingDato?.format('dd.MM.yyyy')])}"><g:message code="timeforing.tilbake" default="Tilbake til oversikt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
