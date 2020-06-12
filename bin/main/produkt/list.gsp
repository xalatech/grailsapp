<%@ page import="siv.type.ProsjektFinansiering" %>
<%@ page import="sivadm.Produkt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produkt.label', default: 'Produkt')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<span class="menuButton"><g:link class="create" action="create"><g:message code="xx" default="Nytt produkt" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            
            <g:if test="${flash.errorMessage}">
				<div class="errors">${flash.errorMessage}</div>
			</g:if>
			
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'produkt.id.label', default: 'Id')}" />
                        
	                        <g:sortableColumn property="produktNummer" title="${message(code: 'produkt.produktNummer.label', default: 'Produktnr')}" />
                            
                            <g:sortableColumn property="navn" title="${message(code: 'produkt.navn.label', default: 'Navn')}" />
                        
                            <g:sortableColumn property="beskrivelse" title="${message(code: 'produkt.beskrivelse.label', default: 'Beskrivelse')}" />
                        	
                        	<g:sortableColumn property="finansiering" title="${message(code: 'produkt.finansiering.label', default: 'Finansiering')}" />
                        
                        	<th />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${produktInstanceList}" status="i" var="produktInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td style="text-align: right"><g:link action="edit" id="${produktInstance.id}"><g:formatNumber number="${produktInstance?.id}" format="#"/></g:link></td>
                        
                            <td style="width: 80px">${fieldValue(bean: produktInstance, field: "produktNummer")}</td>
                            
                            <td>${fieldValue(bean: produktInstance, field: "navn")}</td>
                        
                            <td>${fieldValue(bean: produktInstance, field: "beskrivelse")}</td>
                        	
                        	<td>
		            			${fieldValue(bean: produktInstance, field: "finansiering")}
		            			<g:if test="${produktInstance?.finansiering == ProsjektFinansiering.STAT_MARKED}">
	                				(${fieldValue(bean: produktInstance, field: "prosentStat")}% <g:message code="sivadm.Stat" default="Stat" /> / ${fieldValue(bean: produktInstance, field: "prosentMarked")}% <g:message code="sivadm.Marked" default="Marked" />)
	                			</g:if>
	                		</td>
                        	
                        	<td>
								<g:link action="edit" id="${produktInstance.id}"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${produktInstance.id})"><g:slettIkon /></a>
							</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${produktInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="produkt" melding="Er du sikker på at du vil slette produkt? Dette kan ha negative følger hvis intervjuere har ført timer på dette produktet, og disse ennå ikke er sendt til SAP." />
        </div>
    </body>
</html>
