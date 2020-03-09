

<%@ page import="sivadm.SystemKommando" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'systemKommando.label', default: 'SystemKommando')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        
        <div class="body">
            
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${systemKommandoInstance}">
            <div class="errors">
                <g:renderErrors bean="${systemKommandoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${systemKommandoInstance?.id}" />
                <g:hiddenField name="version" value="${systemKommandoInstance?.version}" />
                
                <table>
                	<tr>
                		<td>
                			
               
                
	                <div class="dialog" style="width: 450px; float: left;">
	                    <table style="border: none;">
	                        <tbody>
	                        	
	                        	<tr class="prop">
	                                <td valign="top" class="name">
	                                  <label for="filnavn"><g:message code="systemKommando.filnavn.label" default="Filnavn" /></label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'filnavn', 'errors')}">
	                                    <g:textField name="filnavn" value="${systemKommandoInstance?.filnavn}" size="45"/>
	                                </td>
	                            </tr>
	                            
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                  <label for="beskrivelse"><g:message code="systemKommando.beskrivelse.label" default="Beskrivelse" /></label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'beskrivelse', 'errors')}">
	                                    <g:textArea name="beskrivelse" value="${systemKommandoInstance?.beskrivelse}" rows="5" cols="40"/>
	                                </td>
	                            </tr>
	                        
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                  <label for="maksSekunder"><g:message code="systemKommando.maksSekunder.label" default="Maks sekunder" /></label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean: systemKommandoInstance, field: 'maksSekunder', 'errors')}">
	                                    <g:textField name="maksSekunder" value="${fieldValue(bean: systemKommandoInstance, field: 'maksSekunder')}" />
	                                </td>
	                            </tr>
	                            <tr class="prop">
	                                <td valign="top" class="name">
	                                	<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                
	                <div class="dialog" style="width: 450px; float: left;">
	                	<table style="border: none;">
							<tbody>
								<tr class="prop">
									<td valign="top">
										<label>Legg til intervjuere som skal kjøre systemkommandoen:</label>
									</td>
						
									
								</tr>
								
								<tr class="prop">
									<td valign="top">
										<g:select name="intervjuere"
											from="${ikkeValgteIntervjuere}"
											optionValue="navnOgInitialer"
											optionKey="id"
											multiple="true"
											size="15"
											style="width: 200px;" />
										
									</td>
								</tr>
								<tr class="prop">
									<td valign="top">
										<g:actionSubmit value="Legg til intervjuer(e)" action="leggTilIntervjuere" />
										<g:actionSubmit value="Legg til alle" action="leggTilAlleIntervjuere" />
									</td>
								</tr>
							</tbody>
						</table>
	                	
	                </div>
	                
	                </td>
                	</tr>
                </table> 
            
            
            <h1>Status</h1>
            
            <div class="list" style="width: 350px; float: left; margin-right: 30px;">
	            <h2>Skal kjøre</h2>
	            <table id="skal_kjore">
	            	<thead>
	            		<tr>
	            			<th>Intervjuer</th>
	            			<th>Initialer</th>
	            			<th></th>
	            		</tr>
	            	</thead>
	            	<tbody>
	            		<g:each in="${intervjuerSystemKommandoList.findAll{it.utfortDato == null}}" status="i" var="intervjuerSystemKommandoInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        	<td>${intervjuerSystemKommandoInstance?.navn}</td>
	                        	<td>${intervjuerSystemKommandoInstance?.initialer}</td>
	                        	<td><g:link action="taVekkIntervjuer" id="${intervjuerSystemKommandoInstance?.systemKommandoId}" params="[valgtIntervjuer: intervjuerSystemKommandoInstance.intervjuerId]"><g:slettIkon/></g:link></td>
	                        </tr>
	                    </g:each>
	            	</tbody>
	            </table>
	            <br>
	            <g:actionSubmit value="Ta vekk alle intervjuere, og slett alle kjøringer" action="taVekkAlleIntervjuer" onclick="return confirm('Er du sikker?')" />
            </div>
            
            
            
            <div class="list" style="width: 420px; float: left; margin-right: 30px;">
	            <h2>Har kjørt</h2>
	            <table id="har_kjort">
	            	<thead>
	            		<tr>
	            			<th>Intervjuer</th>
	            			<th>Initialer</th>
	            			<th>Tidspunkt</th>
	            			<th></th>
	            		</tr>
	            	</thead>
	            	<tbody>
	            		<g:each in="${intervjuerSystemKommandoList.findAll{it.utfortDato != null && it.suksess == true}}" status="i" var="intervjuerSystemKommandoInstance">
		                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>${intervjuerSystemKommandoInstance?.navn}</td>
								<td>${intervjuerSystemKommandoInstance?.initialer}</td>
								<td>${intervjuerSystemKommandoInstance?.utfortDato}</td>
								<td><g:link action="taVekkIntervjuer" id="${intervjuerSystemKommandoInstance?.systemKommandoId}" params="[valgtIntervjuer: intervjuerSystemKommandoInstance.intervjuerId]"><g:slettIkon/></g:link></td>
							</tr>
						</g:each>
	            	</tbody>
	            </table>
            </div>
            
            <div class="list" style="width: 420px; float: left;">
	            <h2>Har feilet</h2>
	            <table id="feilet">
	            	<thead>
	            		<tr>
	            			<th>Intervjuer</th>
	            			<th>Initialer</th>
	            			<th>Tidspunkt</th>
	            			<th></th>
	            		</tr>
	            	</thead>
	            	<tbody>
	            		<g:each in="${intervjuerSystemKommandoList.findAll{it.utfortDato != null && it.suksess == false}}" status="i" var="intervjuerSystemKommandoInstance">
		                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>${intervjuerSystemKommandoInstance?.navn}</td>
								<td>${intervjuerSystemKommandoInstance?.initialer}</td>
								<td>${intervjuerSystemKommandoInstance?.utfortDato}</td>
								<td><g:link action="taVekkIntervjuer" id="${intervjuerSystemKommandoInstance?.systemKommandoId}" params="[valgtIntervjuer: intervjuerSystemKommandoInstance.intervjuerId]"><g:slettIkon/></g:link></td>
							</tr>
						</g:each>
	            	</tbody>
	            </table>
            </div>
            </g:form>
            
        </div>
    </body>
</html>
