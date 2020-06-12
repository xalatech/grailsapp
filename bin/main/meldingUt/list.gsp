
<%@ page import="sivadm.MeldingUt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'meldingUt.label', default: 'MeldingUt')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
   
        <div class="body">
            <g:hasErrors bean="${searchCommand}">
				<div class="errors">
					<g:renderErrors bean="${searchCommand}" as="list" />
				</div>
			</g:hasErrors>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <g:form method="post" >
	            <h1>Søk</h1>
	            <div class="dialog">
	            	<table>
	            		<tr class="prop">
	            			<td valign="top" class="small">
	            				<label>IO-nr: </label>
	            				<g:textField name="ioNr" size="15" value="${meldingUtSok?.ioNr}"></g:textField> 
	            			</td>
	            		
	            			<td valign="top" class="small">
	            				<label>Intervjuobjekt id: </label>
	            				<g:textField name="ioId" size="15" value="${meldingUtSok?.ioId}"></g:textField> 
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Status: </label>
	            				<g:select name="status" from="['Alle', 'Sendt ok', 'Skal sendes', 'Feilede', 'De-aktivert']" value="${meldingUtSok?.status}" />
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Fra dato: </label>
	            				<g:datoVelger id="dp-1" name="fra" value="${meldingUtSok?.fra}" />
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Til dato: </label>
	            				<g:datoVelger id="dp-2" name="til" value="${meldingUtSok?.til}" />
	            			</td>
	            		</tr>
	            	</table>
	            	<div class="buttonStyle">
		            	<g:actionSubmit value="Søk" action="list" />
		            	<g:actionSubmit value="Nullstill felter" action="nullstillSok" />
	            	</div>
	            </div>
            </g:form>
            
            <h1>
            	Utgående meldinger / synkronisering
            	<g:if test="${meldingUtTotal}"> (<g:formatNumber number="${meldingUtTotal}" format="#" /> meldinger)</g:if>
            </h1>
            
            <g:form name="slettForm">
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'meldingUt.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="intervjuObjektId" title="${message(code: 'meldingUt.intervjuObjektId.label', default: 'IoId')}" />
                        
                        	<g:sortableColumn property="meldingType" title="${message(code: 'meldingUt.meldingType.label', default: 'Type')}" />
                        
                            <g:sortableColumn property="antallForsok" title="${message(code: 'meldingUt.antallForsok.label', default: 'Antall forsøk')}" />
                            
                            <g:sortableColumn property="sendtOk" title="${message(code: 'meldingUt.sendtOk.label', default: 'Sendt ok')}" />
                            
                            <g:sortableColumn property="deaktivert" title="${message(code: 'meldingUt.deaktivert.label', default: 'Deaktivert')}" />
                            
                            <g:sortableColumn property="responseText" title="${message(code: 'meldingUt.responseText.label', default: 'Retur melding')}" />
                            
                            <g:sortableColumn property="tidRegistrert" title="${message(code: 'meldingUt.tidRegistrert.label', default: 'Tid registrert')}" />
                            
                            <g:sortableColumn property="tidSendt" title="${message(code: 'meldingUt.tidSendt.label', default: 'Tid sendt')}" />
                        	
                        	<th/>
                        	
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${meldingUtListe}" status="i" var="meldingUtInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>
                            	<g:link action="show" id="${meldingUtInstance.id}" title="${meldingUtInstance.melding}"><g:formatNumber number="${meldingUtInstance.id}" format="#" /></g:link>
                            	<g:hiddenField name="slettId" value="${meldingUtInstance.id}" />
                            </td>
                        
                        	<td>
	                        	<g:link controller="intervjuObjekt" action="edit" id="${meldingUtInstance.intervjuObjektId}">
                        			<g:formatNumber number="${meldingUtInstance.intervjuObjektId}" format="#" />
                        		</g:link>
                            </td>
                            
                            <td>${fieldValue(bean: meldingUtInstance, field: "meldingType")}</td>
                        
                            <td style="width: 90px; text-align: right;">
                            	${fieldValue(bean: meldingUtInstance, field: "antallForsok")}
                            </td>
                            
                            <td>
                            	<g:formatBoolean boolean="${meldingUtInstance?.sendtOk}" true="Ja" false="Nei" />
                            </td>
                            
                            <td>
                            	<g:formatBoolean boolean="${meldingUtInstance?.deaktivert}" true="Ja" false="Nei" />
                            </td>
                            
                            <td>
                            	${fieldValue(bean: meldingUtInstance, field: "responseText")}
                            </td>
                            
                            <td style="width: 125px">
                            	<g:formatDate date="${meldingUtInstance.tidRegistrert}" format="dd.MM.yyyy HH:mm:ss" />
                            </td>
                            
                            <td style="width: 125px">
                            	<g:formatDate date="${meldingUtInstance.tidSendt}" format="dd.MM.yyyy HH:mm:ss" />
                            </td>
                            
                            <td nowrap="nowrap">
                            	<g:if test="${!meldingUtInstance.sendtOk && !meldingUtInstance.deaktivert && meldingUtInstance.antallForsok > 0}">
                            		<g:link action="sendPaaNytt" id="${meldingUtInstance.id}">Send igjen</g:link>
                            	</g:if>
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${meldingUtTotal}" />
            </div>
            <br/>
            <div class="buttonStyle">
            	<g:actionSubmit value="Slett meldinger i søkeresultat" action="slettMeldinger" />
            </div>
            </g:form>
        </div>
    </body>
</html>
