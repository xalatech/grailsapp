
<%@ page import="sil.Lonnart" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lonnart.label', default: 'Lønnart')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
           	<br>
            <g:link controller="krav" action="administrasjon" id="admin">&lt;&lt; <g:message code="sil.adm.tilbake.til.administrasjon" default="Tilbake til SIL administrasjon" /></g:link>
            <br><br>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'lonnart.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="lonnartNummer" title="${message(code: 'lonnart.lonnartNummer.label', default: 'Lonnart Nummer')}" />
                        
                            <g:sortableColumn property="navn" title="${message(code: 'lonnart.navn.label', default: 'Navn')}" />
                        
                            <g:sortableColumn property="beskrivelse" title="${message(code: 'lonnart.beskrivelse.label', default: 'Beskrivelse')}" />
                        
                            <g:sortableColumn property="kmKode" title="${message(code: 'lonnart.kmKode.label', default: 'Km Kode')}" />
                        
                            <g:sortableColumn property="konto" title="${message(code: 'lonnart.konto.label', default: 'Konto')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lonnartInstanceList}" status="i" var="lonnartInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${lonnartInstance.id}">${fieldValue(bean: lonnartInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: lonnartInstance, field: "lonnartNummer")}</td>
                        
                            <td>${fieldValue(bean: lonnartInstance, field: "navn")}</td>
                        
                            <td>${fieldValue(bean: lonnartInstance, field: "beskrivelse")}</td>
                        
                            <td>${fieldValue(bean: lonnartInstance, field: "kmKode")}</td>
                        
                            <td>${fieldValue(bean: lonnartInstance, field: "konto")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${lonnartInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
