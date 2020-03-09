
<%@ page import="sil.SapFil" %>
<%@ page import="sil.type.SapFilStatusType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sapFil.label', default: 'SapFil')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">${flash.errrorMessage}</div>
            </g:if>
            <br>
            <g:link controller="krav" action="administrasjon" id="admin">&lt;&lt; <g:message code="sil.adm.tilbake.til.administrasjon" default="Tilbake til SIL administrasjon" /></g:link>
            <br><br>
            <div class="list">
                <table style="width: 1030px">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'sapFil.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="fil" title="${message(code: 'sapFil.fil.label', default: 'Fil')}" />
                        
                            <g:sortableColumn property="filType" title="${message(code: 'sapFil.filType.label', default: 'Fil Type')}" />
                        
	                        <g:sortableColumn property="antallKrav" title="${message(code: 'sapFil.antallKrav.label', default: 'Antall krav')}" />
    
						    <g:sortableColumn property="antallLinjer" title="${message(code: 'sapFil.antallLinjer.label', default: 'Antall linjer')}" />
                        
	                        <g:sortableColumn property="status" title="${message(code: 'sapFil.status.label', default: 'Status')}" />
                        
	                        <g:sortableColumn property="statusmelding" title="${message(code: 'sapFil.feilmelding.label', default: 'Statusmelding')}" />
                        
                            <g:sortableColumn property="dato" title="${message(code: 'sapFil.dato.label', default: 'Dato')}" />
                        
                        	<th />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sapFilInstanceList}" status="i" var="sapFilInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td style="width: 30px; text-align: center;">
                            	<g:link action="show" id="${sapFilInstance.id}"><g:formatNumber number="${sapFilInstance?.id}" format="#" /></g:link>
                            </td>
                        
                            <td style="width: 150px">${fieldValue(bean: sapFilInstance, field: "fil")}</td>
                        
                            <td style="width: 60px">${fieldValue(bean: sapFilInstance, field: "filType")}</td>
                        
	                        <td style="width: 75px; text-align: right;">${fieldValue(bean: sapFilInstance, field: "antallKrav")}</td>
	                        
	                        <td style="width: 75px; text-align: right;">${fieldValue(bean: sapFilInstance, field: "antallLinjer")}</td>
	                        
	                        <td style="width: 50px">${fieldValue(bean: sapFilInstance, field: "status")}</td>
	                        
	                        <td style="width: 260px">${fieldValue(bean: sapFilInstance, field: "statusmelding")}</td>
                        
                            <td style="width: 130px"><g:formatDate date="${sapFilInstance.dato}" format="dd.MM.yyyy HH:mm:ss"/></td>
                        
                        	<td style="width: 100px; text-align: center;">
                        		<g:if test="${sapFilInstance.status == SapFilStatusType.FEILET && sapFilInstance.kanRegenereres()}">
                        			<g:link action="regenerer" id="${sapFilInstance.id}" title="Skriv ny SAP fil for krav i denne filen, hvis de fortsatt har status Godkjent">Regenerer</g:link>
                        		</g:if>
                        	</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sapFilInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
