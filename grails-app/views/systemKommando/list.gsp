
<%@ page import="sivadm.SystemKommando" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'systemKommando.label', default: 'SystemKommando')}" />
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'systemKommando.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="filnavn" title="${message(code: 'systemKommando.filnavn.label', default: 'Filnavn')}" />
                        
                            <g:sortableColumn property="beskrivelse" title="${message(code: 'systemKommando.beskrivelse.label', default: 'Beskrivelse')}" />
                        
                            <g:sortableColumn property="maksSekunder" title="${message(code: 'systemKommando.maksSekunder.label', default: 'Maks sekunder')}" />
                        
                            <g:sortableColumn property="redigertAv" title="${message(code: 'systemKommando.redigertAv.label', default: 'Redigert av')}" />
                        
                            <g:sortableColumn property="redigertDato" title="${message(code: 'systemKommando.redigertDato.label', default: 'Redigert dato')}" />
                            
                            <th />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${systemKommandoInstanceList}" status="i" var="systemKommandoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${systemKommandoInstance.id}">${fieldValue(bean: systemKommandoInstance, field: "id")}</g:link></td>
                        
                        	<td>${fieldValue(bean: systemKommandoInstance, field: "filnavn")}</td>
                        	
                            <td>${fieldValue(bean: systemKommandoInstance, field: "beskrivelse")}</td>
                        
                            <td>${fieldValue(bean: systemKommandoInstance, field: "maksSekunder")}</td>
                        
                            <td>${fieldValue(bean: systemKommandoInstance, field: "redigertAv")}</td>
                        
                            <td><g:formatDate date="${systemKommandoInstance.redigertDato}" /></td>
                            
                            <td>
                            	<g:link action="edit" id="${systemKommandoInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${systemKommandoInstance.id})"><g:slettIkon /></a>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${systemKommandoInstanceTotal}" />
            </div>
        </div>
        <g:slettDialog domeneKlasse="systemKommando" />
    </body>
</html>
