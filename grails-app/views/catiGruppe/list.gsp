
<%@ page import="sivadm.CatiGruppe" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'catiGruppe.label', default: 'CatiGruppe')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'catiGruppe.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="navn" title="${message(code: 'catiGruppe.navn.label', default: 'CatiGruppeNavn')}" />
                            
                            <g:sortableColumn property="skjema" title="${message(code: 'catiGruppe.skjema.label', default: 'Skjemaer')}" />
                            
                            <g:sortableColumn property="intervjuer" title="${message(code: 'catiGruppe.intervjuer.label', default: 'Intervjuere')}" />
                            
                            <th/>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${catiGruppeInstanceList}" status="i" var="catiGruppeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${catiGruppeInstance.id}">${fieldValue(bean: catiGruppeInstance, field: "id")}</g:link></td>
                        
                            <td nowrap="nowrap">${fieldValue(bean: catiGruppeInstance, field: "navn")}</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${catiGruppeInstance.skjemaer.toList().sort { a,b-> b.oppstartDataInnsamling<=>a.oppstartDataInnsamling }}" var="s">
                                    <li><g:link controller="skjema" action="edit" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                            <td>
                            	<g:each in="${catiGruppeInstance.intervjuere}" var="intervjuer">${intervjuer.initialer}, </g:each>
                            </td>
                            
                            <td nowrap="nowrap">
                            	<g:link action="edit" id="${catiGruppeInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${catiGruppeInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${catiGruppeInstanceTotal}" />
            </div>
        </div>
        
        <g:slettDialog domeneKlasse="catiGruppe" />
        
    </body>
</html>
