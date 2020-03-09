
<%@ page import="sivadm.ProsjektLeder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'prosjektLeder.label', default: 'ProsjektLeder')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'prosjektLeder.id.label', default: 'Id')}" />
                        
                        	<g:sortableColumn property="navn" title="${message(code: 'prosjektLeder.navn.label', default: 'Navn')}" />
                                                                            
                            <g:sortableColumn property="initialer" title="${message(code: 'prosjektLeder.initialer.label', default: 'Initialer')}" />
                        
                        	<g:sortableColumn property="epost" title="${message(code: 'prosjektLeder.epost.label', default: 'Epost')}" />    
                        
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${prosjektLederInstanceList}" status="i" var="prosjektLederInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="edit" id="${prosjektLederInstance.id}"><g:formatNumber number="${prosjektLederInstance?.id}" format="#" /></g:link></td>
                        	
                        	<td>${fieldValue(bean: prosjektLederInstance, field: "navn")}</td>
                                                                                
                            <td>${fieldValue(bean: prosjektLederInstance, field: "initialer")}</td>
                        
	                        <td><a href="mailto:${prosjektLederInstance.epost}">${prosjektLederInstance.epost}</a></td>
	                        
	                        <td>
								<g:link action="edit" id="${prosjektLederInstance.id}"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${prosjektLederInstance.id})"><g:slettIkon /></a>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${prosjektLederInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="prosjektleder" />
        </div>
    </body>
</html>
