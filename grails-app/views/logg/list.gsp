
<%@ page import="sivadm.Logg" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'logg.label', default: 'Logg')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'logg.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="tittel" title="${message(code: 'logg.tittel.label', default: 'Tittel')}" />
                        
                            <g:sortableColumn property="endretAv" title="${message(code: 'logg.endretAv.label', default: 'Endret Av')}" />
                        
                            <g:sortableColumn property="ferdig" title="${message(code: 'logg.ferdig.label', default: 'Ferdig')}" />
                        
                            <g:sortableColumn property="loggType" title="${message(code: 'logg.loggType.label', default: 'Logg Type')}" />
                        
                            <g:sortableColumn property="opprettet" title="${message(code: 'logg.opprettet.label', default: 'Opprettet')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${loggInstanceList}" status="i" var="loggInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${loggInstance.id}">${fieldValue(bean: loggInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: loggInstance, field: "tittel")}</td>
                        
                            <td>${fieldValue(bean: loggInstance, field: "endretAv")}</td>
                        
                            <td><g:formatBoolean boolean="${loggInstance.ferdig}" /></td>
                        
                            <td>${fieldValue(bean: loggInstance, field: "loggType")}</td>
                        
                            <td><g:formatDate date="${loggInstance.opprettet}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${loggInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
