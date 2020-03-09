<%@ page import="sivadm.Intervjuer" %>
<%@ page import="sivadm.Klynge" %>
<%@ page import="siv.type.IntervjuerArbeidsType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'intervjuer.label', default: 'Intervjuer')}" />
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
            
            <h1><g:message code="sivadm.intervjuer.sok" default="Intervjuer søk" /></h1>
           	<g:form action="list" method="post">
            	<table>
            		<tr class="prop">
                         <td valign="top" class="name">
                           <label for="initialer"><g:message code="intervjuer.initialer.label" default="Initialer / Navn:" /></label>
                         </td>
                         <td valign="top">
                             <g:textField name="initialer" value="${initialer}" />
                         </td>
                         <td>
                    		<label for="klynge"><g:message code="intervjuer.klynge.label" default="Klynge:" /></label>
                    	</td>
                    	<td>
                        	<g:select name="klynge"
								from="${Klynge.list()}"
								noSelection="['':'-Velg klynge-']"
								optionKey="id"
								value="${klynge?.id}"/>
						</td>
                    </tr>
                    
                    <tr>
                    	<td>
                    		<label for="status"><g:message code="intervjuer.status.label" default="Status:" /></label>
                    	</td>
                    	<td>
                    		<g:select name="intervjuerStatus"
                    			optionKey="key"
                    			optionValue="guiName"
                    			from="${intervjuerStatusList}"
                    			value="${status?.key}" />
                    	</td>
                    	<td>
                    		<label for="arbeidsType"><g:message code="intervjuer.arbeidsType.label" default="Arbeidstype:" /></label>
                    	</td>
                    	<td>
                        	<g:select name="arbeidsType"
								from="${IntervjuerArbeidsType.values()}"
								noSelection="['':'-Velg arbeidstype']"
								optionKey="key"
								optionValue="guiName"
								value="${arbeidsType?.key}"/>
						</td>
                    </tr>
                    
                    <tr>
                    	<td>
                    		<label for="lokalSentral"><g:message code="intervjuer.status.label" default="Lokal/sentral:" /></label>
                    	</td>
                    	<td>
                    		<g:radioGroup name="lokalSentral" labels="['Begge', 'Lokal', 'Sentral']" values="[1,2,3]" value="${lokalSentral}" >
                    			${it.radio}&nbsp;${it.label}&nbsp;&nbsp;
                    		</g:radioGroup>
                    	</td>
                    	<td>
                    	</td>
                    	<td>
						</td>
                    </tr>
                    
            	</table>
            	
            	<div class="buttonStyle">
	            	<g:submitButton name="list" value="Søk"/>
	            	<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="nullstillSok" />
            	</div>
           	</g:form>
           	
           	<h1>
           		<g:message code="sivadm.intervjuer.liste" default="Intervjuerliste" />
           		<g:if test="${intervjuerInstanceTotal}">(${intervjuerInstanceTotal})</g:if>
           	</h1>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'intervjuer.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="initialer" title="${message(code: 'intervjuer.initialer.label', default: 'Initialer')}" />
                            
                            <g:sortableColumn property="navn" title="${message(code: 'intervjuer.navn.label', default: 'Navn')}" />
                            
                            <g:sortableColumn property="klynge" title="${message(code: 'intervjuer.klynge.label', default: 'Klynge')}" />
                        
                            <g:sortableColumn property="mobil" title="${message(code: 'intervjuer.mobil.label', default: 'Mobil')}" />
                        
                            <g:sortableColumn property="telefonJobb" title="${message(code: 'intervjuer.telefonJobb.label', default: 'Telefon jobb')}" />
                            
                            <g:sortableColumn property="telefonHjem" title="${message(code: 'intervjuer.telefonHjem.label', default: 'Telefon hjem')}" />
                        
                            <g:sortableColumn property="epostPrivat" title="${message(code: 'intervjuer.epostPrivat.label', default: 'Epost jobb')}" />
                        
                            <g:sortableColumn property="arbeidsType" title="${message(code: 'intervjuer.arbeidsType.label', default: 'Arbeidstype')}" />
                        	
                        	<th/>
                        	
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${intervjuerInstanceList}" status="i" var="intervjuerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	
                        	<td style="text-align: right;">
                        		<g:link action="edit" id="${intervjuerInstance.id}" params="[fraIntervjuerListe: 'true']"><g:formatNumber number="${intervjuerInstance?.id}" format="#" /></g:link>
                        	</td>
                        	
                        	<td>${fieldValue(bean: intervjuerInstance, field: "initialer")}</td>
                        	
                        	<td>${fieldValue(bean: intervjuerInstance, field: "navn")}</td>
                        	
                        	<td>${fieldValue(bean: intervjuerInstance, field: "klynge")}</td>
                        
                            <td style="text-align: right;">${fieldValue(bean: intervjuerInstance, field: "mobil")}</td>
                        
                            <td style="text-align: right;">${fieldValue(bean: intervjuerInstance, field: "telefonJobb")}</td>
                            
                            <td style="text-align: right;">${fieldValue(bean: intervjuerInstance, field: "telefonHjem")}</td>
                        
                            <td><a href="mailto:${intervjuerInstance.epostJobb}">${intervjuerInstance.epostJobb}</a></td>
                        
                            <td>${fieldValue(bean: intervjuerInstance, field: "arbeidsType")}</td>
                        
  		                    <td>
  		                    	<g:link action="apneDatoForTimeforing" id="${intervjuerInstance.id}" title="${message(code: 'sivadm.intervjuer.apne.timeforing.tooltip')}"><g:message code="sivadm.intervjuer.apne.timeforing" default="Åpne timeføring" /></g:link>
  		                    </td>
  		                    
  		                    <td nowrap="nowrap">
                            	<g:link action="edit" id="${intervjuerInstance.id}"><g:redigerIkon /></g:link>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
        
    </body>
</html>
