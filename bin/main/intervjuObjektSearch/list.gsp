
<%@ page import="sivadm.IntervjuObjektSearch" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'intervjuObjektSearch.label', default: 'IntervjuObjektSøk')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
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
                           <g:sortableColumn property="id" title="${message(code: 'intervjuObjektSearch.id.label', default: 'Id')}" />
                           <g:sortableColumn property="sokeNavn" title="${message(code: 'intervjuObjektSearch.sokeNavn.label', default: 'Navn på søk')}" />
                           <g:sortableColumn property="lagret" title="${message(code: 'intervjuObjektSearch.lagret.label', default: 'Lagret dato')}" />
                           <g:sortableColumn property="lagretAv" title="${message(code: 'intervjuObjektSearch.searchName.label', default: 'Lagret av')}" />
                           <g:sortableColumn property="resultatErLagret" title="${message(code: 'intervjuObjektSearch.persisterSokeResultat.label', default: 'Søk utført')}" />
                       	   <th />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${searchInstanceList}" status="i" var="searchInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="searchResult" controller="intervjuObjekt" params="[searchId: searchInstance?.id]"><g:formatNumber number="${searchInstance?.id}" format="#" /></g:link></td>
                            <td>${fieldValue(bean: searchInstance, field: "sokeNavn")}</td>
                            <td>${fieldValue(bean: searchInstance, field: "lagret")}</td>
                            <td>${fieldValue(bean: searchInstance, field: "lagretAv")}</td>
                            <td>
                                <g:if test="${searchInstance?.persisterSokeResultat}">
                                    Ja
                                </g:if>
                                <g:else>
                                    Nei
                                </g:else>
                            </td>
                        	<td>
                            	<a href="#" onclick="return apneSlettDialog(${searchInstance?.id})"><g:slettIkon /></a>
                                <g:if test="${searchInstance?.persisterSokeResultat}">
                                    <g:link action="visUtfortSok" params="${[searchId: searchInstance?.id,
                                                                             searchName: searchInstance.sokeNavn,
                                                                             searchSaved: searchInstance.lagret,
                                                                             searchSavedBy: searchInstance.lagretAv]}">
                                        <g:seIkon />
                                    </g:link>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <g:slettDialog domeneKlasse="intervjuObjektSearch" />
    </body>
</html>
