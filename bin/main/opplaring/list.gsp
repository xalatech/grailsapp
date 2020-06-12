
<%@ page import="sivadm.Opplaring" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opplaring.label', default: 'Opplæring')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'opplaring.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="beskrivelse" title="${message(code: 'opplaring.beskrivelse.label', default: 'Beskrivelse')}" />
                        
                            <g:sortableColumn property="gjennomfortDato" title="${message(code: 'opplaring.gjennomfortDato.label', default: 'Gjennomfort Dato')}" />
                        
                            <g:sortableColumn property="gjennomfortSted" title="${message(code: 'opplaring.gjennomfortSted.label', default: 'Gjennomfort Sted')}" />
                        
                            <g:sortableColumn property="hendelseType" title="${message(code: 'opplaring.hendelseType.label', default: 'Hendelse Type')}" />
                        
                            <g:sortableColumn property="obligatorisk" title="${message(code: 'opplaring.obligatorisk.label', default: 'Obligatorisk')}" />
                        
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${opplaringInstanceList}" status="i" var="opplaringInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>
                            	<g:link action="edit" id="${opplaringInstance.id}"><g:formatNumber number="${opplaringInstance?.id}" format="#" /></g:link>
                            </td>
                        
                            <td>${fieldValue(bean: opplaringInstance, field: "beskrivelse")}</td>
                        
                            <td><g:formatDate date="${opplaringInstance.gjennomfortDato}" /></td>
                        
                            <td>${fieldValue(bean: opplaringInstance, field: "gjennomfortSted")}</td>
                        
                            <td>${fieldValue(bean: opplaringInstance, field: "hendelseType")}</td>
                        
                            <td><g:formatBoolean boolean="${opplaringInstance.obligatorisk}" /></td>
                        
                        	<td>
								<g:link action="edit" id="${opplaringInstance.id}"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${opplaringInstance.id})"><g:slettIkon /></a>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${opplaringInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="opplæring" controller="opplaring" />
        </div>
    </body>
</html>
