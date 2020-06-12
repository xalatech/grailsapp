
<%@ page import="sivadm.Klynge" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'klynge.label', default: 'Klynge')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <h1>
            	<g:message code="default.list.label" args="[entityName]" />
            	<g:if test="${klyngeInstanceTotal}">(${klyngeInstanceTotal})</g:if>
            </h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'klynge.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="klyngeNavn" title="${message(code: 'klynge.klyngeNavn.label', default: 'Klyngenavn')}" />
                           
                            <g:sortableColumn property="klyngeSjef" title="${message(code: 'klynge.klyngeSjef.label', default: 'Klyngesjef')}" />
                            
                            <g:sortableColumn property="epost" title="${message(code: 'klynge.epost.label', default: 'Epost')}" />
                        
	                        <th><g:message code="sivadm.klynge.antall.intervjuere" default="Antall intervjuere" /></th>
                        
                        	<th><g:message code="sivadm.klynge.antall.kommuner" default="Antall kommuner" /></th>
                        	
                        	<th />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${klyngeInstanceList}" status="i" var="klyngeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${klyngeInstance.id}"><g:formatNumber number="${klyngeInstance?.id}" format="#" /></g:link></td>
                        
                            <td>${fieldValue(bean: klyngeInstance, field: "klyngeNavn")}</td>
                                                    
                            <td>${fieldValue(bean: klyngeInstance, field: "klyngeSjef")}</td>
                            
                            <td>${fieldValue(bean: klyngeInstance, field: "epost")}</td>
                        
                            <td style="text-align: right;">${klyngeInstance?.intervjuere?.size()}</td>
                            
                            <td style="text-align: right;">${klyngeInstance?.kommuneNummerList?.size()}</td>
                                                    
                        	<td>
                            	<g:link action="edit" id="${klyngeInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${klyngeInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <g:slettDialog domeneKlasse="klynge" />
    </body>
</html>
