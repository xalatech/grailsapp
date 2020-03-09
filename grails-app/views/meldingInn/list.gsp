
<%@ page import="sivadm.MeldingInn" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'meldingInn.label', default: 'MeldingInn')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
           		<div class="message">${flash.message}</div>
            </g:if>
            
            <g:hasErrors bean="${searchCommand}">
				<div class="errors">
					<g:renderErrors bean="${searchCommand}" as="list" />
				</div>
			</g:hasErrors>
            
            <g:form method="post" >
	            <h1>Søk</h1>
	            <div class="dialog">
	            	<table>
	            		<tr class="prop">
	            			<td valign="top" class="small">
	            				<label>IO-nr: </label>
	            				<g:textField name="ioNr" size="15" value="${meldingInnSok?.ioNr}"></g:textField> 
	            			</td>
	            		
	            			<td valign="top" class="small">
	            				<label>Intervjuobjekt id: </label>
	            				<g:textField name="ioId" size="15" value="${meldingInnSok?.ioId}"></g:textField> 
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Status: </label>
	            				<g:select name="status" from="['Alle', 'Mottatt ok', 'Feilede']" value="${meldingInnSok?.status}" />
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Fra dato: </label>
	            				<g:datoVelger id="dp-1" name="fra" value="${meldingInnSok?.fra}" />
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Til dato: </label>
	            				<g:datoVelger id="dp-2" name="til" value="${meldingInnSok?.til}" />
	            			</td>
	            			
	            			<td valign="top" class="small">
	            				<label>Meldingstype: </label>
	            				<g:select name="meldingInnType" from="${siv.type.MeldingInnType.values()}"
									optionKey="key" optionValue="guiName"
									noSelection="['':'Alle']"
									value="${meldingInnSok?.meldingInnType?.key}"/>
					
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
            	Innkommende meldinger / synkronisering
            	<g:if test="${meldingInnTotal}"> (<g:formatNumber number="${meldingInnTotal}" format="#" /> meldinger)</g:if>
            </h1>
            
            <g:form name="slettForm">
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'meldingInn.id.label', default: 'Id')}" />
                        
                        	<g:sortableColumn property="tidRegistrert" title="${message(code: 'meldingInn.melding.label', default: 'Tidspunkt')}" />
                        
	                        <g:sortableColumn property="intervjuObjektId" title="${message(code: 'meldingInn.id.label', default: 'IO Id')}" />
                        
     	                  	<g:sortableColumn property="intervjuObjektNummer" title="${message(code: 'meldingInn.id.label', default: 'IO-nr')}" />
                        
                            <g:sortableColumn property="melding" title="${message(code: 'meldingInn.melding.label', default: 'Melding')}" />
                        
                            <g:sortableColumn property="meldingInnType" title="${message(code: 'meldingInn.deadLetter.label', default: 'Meldingstype')}" />
                        
                       		<g:sortableColumn property="intervjuStatus" title="${message(code: 'meldingInn.deadLetter.label', default: 'Intervjustatus')}" />
                        
                        	<g:sortableColumn property="kommentar" title="${message(code: 'meldingInn.deadLetter.label', default: 'Kommentar')}" />
                        
                        	<g:sortableColumn property="sendtAv" title="${message(code: 'meldingInn.deadLetter.label', default: 'Initialer')}" />
                        
                            <g:sortableColumn property="responseText" title="${message(code: 'meldingInn.responseText.label', default: 'Response Text')}" />
                        
                            <g:sortableColumn property="feilType" title="${message(code: 'meldingInn.feilType.label', default: 'Feil Type')}" />
                        
	                        <g:sortableColumn property="mottattOk" title="${message(code: 'meldingInn.deaktivert.label', default: 'Mottatt ok')}" />
                        
                            <g:sortableColumn property="deaktivert" title="${message(code: 'meldingInn.deaktivert.label', default: 'Deaktivert')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${meldingInnListe?.sort{it.tidRegistrert}.reverse()}" status="i" var="meldingInnInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${meldingInnInstance.id}"><g:formatNumber number="${meldingInnInstance.id}" format="#" /></g:link></td>
                        	<g:hiddenField name="slettId" value="${meldingInnInstance.id}" />
                        	<td><g:formatDate date="${meldingInnInstance.tidRegistrert}" format="dd.MM.yyyy HH:mm:ss" /></td>
                        
                        	<td style="text-align: right;">
	                        	<g:link controller="intervjuObjekt" action="edit" id="${meldingInnInstance.intervjuObjektId}">
		                        	<g:formatNumber number="${meldingInnInstance.intervjuObjektId}" format="#" />
		                        </g:link>
                        	</td>
                        	
                        	<td>${fieldValue(bean: meldingInnInstance, field: "intervjuObjektNummer")}</td>
                        	
                            <td style="width: 300px;">${fieldValue(bean: meldingInnInstance, field: "melding")}</td>
                        
                            <td>${fieldValue(bean: meldingInnInstance, field: "meldingInnType")}</td>
                            
                            <td style="text-align: right;"><g:formatNumber number="${meldingInnInstance.intervjuStatus}" format="#" /></td>
                            
                            <td>${fieldValue(bean: meldingInnInstance, field: "kommentar")}</td>
                            
                            <td>${fieldValue(bean: meldingInnInstance, field: "sendtAv")}</td>
                        
                            <td>${fieldValue(bean: meldingInnInstance, field: "responseText")}</td>
                        
                            <td>${fieldValue(bean: meldingInnInstance, field: "feilType")}</td>
                        
	                        <td><g:formatBoolean boolean="${meldingInnInstance.mottattOk}" /></td>
                        
                            <td><g:formatBoolean boolean="${meldingInnInstance.deaktivert}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${meldingInnTotal}" />
            </div>
            <br/>
            <g:actionSubmit value="Slett meldinger i søkeresultat" action="slettMeldinger" />
            
            </g:form>
        </div>
    </body>
</html>
