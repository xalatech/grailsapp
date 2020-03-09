
<%@ page import="sivadm.Skjema" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'skjema.label', default: 'Skjema')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">            
            <span class="menuButton">
            	<g:link class="create" action="create"><g:message code="sivadm.skjema.nytt" default="Nytt skjema" /></g:link>
            	<g:link controller="rapportIndikator" action="velgSkjema">Indikatorrapport</g:link>
            </span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
			
            <g:if test="${flash.message}">
           		<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            
            <g:if test="${flash.errorMessage}">
           		<div class="errors">&nbsp;${flash.errorMessage}</div>
            </g:if>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'skjema.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="skjemaNavn" title="${message(code: 'skjema.skjemaNavn.label', default: 'Skjemanavn')}" />
                        
                            <g:sortableColumn property="skjemaKortNavn" title="${message(code: 'skjema.skjemaKortNavn.label', default: 'Kortnavn')}" />
                            
                            <g:sortableColumn property="oppstartDataInnsamling" title="${message(code: 'skjema.oppstartDataInnsamling.label', default: 'Oppstart')}" />
                        
                            <g:sortableColumn property="delProduktNummer" title="${message(code: 'skjema.delProduktNummer.label', default: 'Arbeidsordrenummer')}" />
                        
                            <g:sortableColumn property="skjemaVersjon" title="${message(code: 'skjema.skjemaVersjon.label', default: 'Skjemaversjon')}" />
                        
                            <g:sortableColumn property="periode" title="${message(code: 'skjema.periode.label', default: 'Periode')}" />
                            
                            <th>Rapporter</th>
                        
                        	<th />
                        </tr>
                    </thead>
                    <tbody>
                    
                    <g:each in="${skjemaInstanceList}" status="i" var="skjemaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${skjemaInstance.id}"><g:formatNumber number="${skjemaInstance?.id}" format="#" /></g:link></td>
                        
                            <td>${fieldValue(bean: skjemaInstance, field: "skjemaNavn")}</td>
                        
                            <td>${fieldValue(bean: skjemaInstance, field: "skjemaKortNavn")}</td>
                            
                            <td>${fieldValue(bean: skjemaInstance, field: "oppstartDataInnsamling")}</td>
                        
                            <td>${fieldValue(bean: skjemaInstance, field: "delProduktNummer")}</td>
                        
                        	<td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${skjemaInstance?.skjemaVersjoner?.sort{it.versjonsNummer}}" var="s">
                                    <li><g:link controller="skjemaVersjon" action="edit" id="${s.id}" params="['fraSkjemaListe': true]" >${s?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${skjemaInstance?.perioder?.sort{it.periodeNummer}}" var="p">
                                    <li><g:link controller="periode" action="edit" id="${p.id}" params="['fraSkjemaListe': true]">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                            <td>
                            	<g:link controller="rapportStatus" action="velgTidligereIntervjustatus" id="${skjemaInstance.id}">Status</g:link>
                            	|
                            	<g:link controller="rapportFrafall" action="visRapport" id="${skjemaInstance.id}">Frafall</g:link>
                            	|
                            	<g:link controller="rapportSkjevhetKjonn" action="visRapport" id="${skjemaInstance.id}">Skjevheter</g:link>
                            </td>
                        	
                        	<td>
                            	<g:link action="edit" id="${skjemaInstance.id}"><g:redigerIkon /></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${skjemaInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${skjemaInstanceTotal}" />
            </div>
        </div>
        <g:slettDialog domeneKlasse="skjema" melding="Er du sikker på at du vil slette skjema? Dette kan ha negative følger hvis intervjuere har ført timer på dette prosjektet, og disse ennå ikke er sendt til SAP." />
    </body>
</html>
