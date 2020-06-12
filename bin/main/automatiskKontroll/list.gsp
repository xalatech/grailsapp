<%@ page import="sil.type.*" %>
<%@ page import="siv.type.*" %>
<%@ page import="sil.AutomatiskKontroll" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll')}" />
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
            <br>
            <g:link controller="krav" action="administrasjon" id="admin">&lt;&lt; <g:message code="sil.adm.tilbake.til.administrasjon" default="Tilbake til SIL administrasjon" /></g:link>
            <br><br>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'automatiskKontroll.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="kontrollNavn" title="${message(code: 'automatiskKontroll.kontrollNavn.label', default: 'Kontrollnavn')}" />
                        
                            <g:sortableColumn property="beskrivelse" title="${message(code: 'automatiskKontroll.beskrivelse.label', default: 'Beskrivelse')}" />
                        
                            <g:sortableColumn property="kravType" title="${message(code: 'automatiskKontroll.kravType.label', default: 'Kravtype')}" />
                            
                            <g:sortableColumn property="transportmiddel" title="${message(code: 'automatiskKontroll.transportmiddel.label', default: 'Transportmiddel')}" />
                        
                            <g:sortableColumn property="produktNummer" title="${message(code: 'automatiskKontroll.produktNummer.label', default: 'Produktnummer')}" />
                        
                            <g:sortableColumn property="grenseVerdi" title="${message(code: 'automatiskKontroll.grenseVerdi.label', default: 'Grenseverdi')}" />
                        
							<g:sortableColumn property="gyldigFra" title="${message(code: 'automatiskKontroll.gyldigFra.label', default: 'Gyldig fra')}" />
					
							<g:sortableColumn property="gyldigTil" title="${message(code: 'automatiskKontroll.gyldigTil.label', default: 'Gyldig til')}" />
							
							<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${automatiskKontrollInstanceList}" status="i" var="automatiskKontrollInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${automatiskKontrollInstance.id}"><g:formatNumber number="${automatiskKontrollInstance?.id}" format="#" /></g:link></td>
                        
                            <td>${fieldValue(bean: automatiskKontrollInstance, field: "kontrollNavn")}</td>
                        
                            <td>${fieldValue(bean: automatiskKontrollInstance, field: "beskrivelse")}</td>
                        
                            <td>${fieldValue(bean: automatiskKontrollInstance, field: "kravType")}</td>
                        
                        	<td>${fieldValue(bean: automatiskKontrollInstance, field: "transportmiddel")}</td>
                        
                            <td>${fieldValue(bean: automatiskKontrollInstance, field: "produktNummer")}</td>
                        
                            <td style="text-align: right;">
                            	<g:if test="${automatiskKontrollInstance?.kravType == KravType.U}">
      		                      	<g:formatNumber number="${automatiskKontrollInstance.grenseVerdi}" format="0.00" /> <g:message code="sil.kr" default="kr" />
                            	</g:if>
                            	<g:elseif test="${automatiskKontrollInstance?.kravType == KravType.T}">
      		                      	${fieldValue(bean: automatiskKontrollInstance, field: "grenseVerdi")} <g:message code="sil.min" />
                            	</g:elseif>
                            	<g:elseif test="${automatiskKontrollInstance?.kravType == KravType.K}">
      		                      	<g:if test="${automatiskKontrollInstance?.transportmiddel == TransportMiddel.GIKK || automatiskKontrollInstance?.transportmiddel == TransportMiddel.LEIEBIL}" >
      		                      		${fieldValue(bean: automatiskKontrollInstance, field: "grenseVerdi")} <g:message code="sil.min" />
      		                      	</g:if>
      		                      	<g:elseif test="${automatiskKontrollInstance?.transportmiddel == TransportMiddel.BUSS_TRIKK || automatiskKontrollInstance?.transportmiddel == TransportMiddel.TOG || automatiskKontrollInstance?.transportmiddel == TransportMiddel.TAXI || automatiskKontrollInstance?.transportmiddel == TransportMiddel.FERJE}">
      		                      		<g:formatNumber number="${automatiskKontrollInstance.grenseVerdi}" format="0.00" /> <g:message code="sil.kr" default="kr" />
      		                      	</g:elseif>
      		                      	<g:else>
	      		                      	${fieldValue(bean: automatiskKontrollInstance, field: "grenseVerdi")} <g:message code="sil.km" />
      		                      	</g:else>  		                      	
                            	</g:elseif>
                            </td>
                            
							<td>
								<g:formatDate format="dd.MM.yyyy" date="${automatiskKontrollInstance?.gyldigFra}" />
							</td>
							                            
							<td>
								<g:formatDate format="dd.MM.yyyy" date="${automatiskKontrollInstance?.gyldigTil}" />
							</td>
                        
                        	<td>
								<g:link action="edit" id="${automatiskKontrollInstance.id}"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${automatiskKontrollInstance.id})"><g:slettIkon /></a>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${automatiskKontrollInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="automatisk kontroll" controller="automatiskKontroll" />
        </div>
    </body>
</html>
