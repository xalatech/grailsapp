
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'meldingsheaderMal.label', default: 'Meldingsheader-mal')}" />
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
            	<g:if test="${meldingsheaderMalInstanceTotal}">(${meldingsheaderMalInstanceTotal})</g:if>
            </h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'meldingsheaderMal.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="malNavn" title="${message(code: 'meldingsheaderMal.malNavn.label', default: 'Navn på meldingsheader-mal')}" />

                            <g:sortableColumn property="meldingsheader" title="${message(code: 'meldingsheaderMal.meldingsheader.label', default: 'Meldingsheader-tekst')}" />

                            <g:sortableColumn property="opprettet" title="${message(code: 'meldingsheaderMal.opprettet.label', default: 'Opprettet dato')}" />

                            <g:sortableColumn property="opprettetAv" title="${message(code: 'meldingsheaderMal.opprettetAv.label', default: 'Opprettet av')}" />

                            <g:sortableColumn property="sistEndret" title="${message(code: 'meldingsheaderMal.sistEndret.label', default: 'Sist endret dato')}" />

                            <g:sortableColumn property="sistEndretAv" title="${message(code: 'meldingsheaderMal.sistEndretAv.label', default: 'Sist endret av')}" />

                        	<th />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${meldingsheaderMalInstanceList}" status="i" var="meldingsheaderMalInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${meldingsheaderMalInstance.id}"><g:formatNumber number="${meldingsheaderMalInstance?.id}" format="#" /></g:link></td>
                        
                            <td>${fieldValue(bean: meldingsheaderMalInstance, field: "malNavn")}</td>
                                                    
                            <td>${fieldValue(bean: meldingsheaderMalInstance, field: "meldingsheader")}</td>

                            <td>${fieldValue(bean: meldingsheaderMalInstance, field: "opprettet")}</td>

                            <td>${meldingsheaderMalInstance.opprettetAv?.username}</td>

                            <td>${fieldValue(bean: meldingsheaderMalInstance, field: "sistEndret")}</td>

                            <td>${meldingsheaderMalInstance.sistEndretAv?.username}</td>

                        	<td>
                            	<g:link action="edit" id="${meldingsheaderMalInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${meldingsheaderMalInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        <g:slettDialog domeneKlasse="meldingsheaderMal" />
    </body>
</html>
