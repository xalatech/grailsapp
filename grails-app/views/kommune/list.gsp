
<%@ page import="sivadm.Kommune" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'kommune.label', default: 'Kommune')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table style="width: 500px;">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'kommune.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="kommuneNummer" title="${message(code: 'kommune.kommuneNummer.label', default: 'Kommunenr')}" />
                            
                            <g:sortableColumn property="kommuneNavn" title="${message(code: 'kommune.kommuneNavn.label', default: 'Navn')}" />
                        	
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${kommuneInstanceList?.sort{it.kommuneNummer}}" status="i" var="kommuneInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td style="width: 30px; text-align: right"><g:link action="edit" id="${kommuneInstance.id}">${fieldValue(bean: kommuneInstance, field: "id")}</g:link></td>
                        
	                        <td style="width: 75px; text-align: right">${fieldValue(bean: kommuneInstance, field: "kommuneNummer")}</td>
                        
                            <td>${fieldValue(bean: kommuneInstance, field: "kommuneNavn")}</td>
                            
                        	<td style="width: 50px;">
                            	<g:link action="edit" id="${kommuneInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${kommuneInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${kommuneInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="kommune" />
        </div>
    </body>
</html>
