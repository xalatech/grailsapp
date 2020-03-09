

<%@ page import="sivadm.Timeforing" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <title>Velg dato</title>
    </head>
    <body>
    	<div class="nav">
            <span class="menuButton"><g:link class="create" action="visAvviste"><g:message code="x" default="Vis avviste timeføringer"/></g:link></span>
        </div>
        
        <div class="body">
        
        <g:if test="${fraKontrollDatoListe}">
        	<h1><g:message code="sivadm.timeregistrering.tilbake.fra.sil.overskrift" /></h1>
        	<div class="list">
            	
            	<table>
                    <thead>
                        <tr> 
                            <th>Dato</th>
                       		<th/> 
                        </tr>
                    </thead>
                    <tbody>
                    	<g:each in="${fraKontrollDatoListe}" status="i" var="datoItem">
                        	<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        		<td><g:formatDate date="${datoItem}" locale="no" type="date" style="LONG"/></td>
                        	   	<td><g:link action="listTotal" params="[valgtDato: datoItem.format('yyyy.MM.dd')]">Endre</g:link></td>
                            </tr>
                        </g:each>
                    </tbody>
               	</table>
            </div>
        </g:if>
        
        	
            <h1>Velg dato</h1>
            <g:if test="${flash.message}">
            	<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">&nbsp;${flash.errorMessage}</div>
            </g:if>
            
            <g:form method="post" action="velgDato" name="velgDatoForm">
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timeforingDato"><g:message code="timeforing.timeforingDato.label" default="Velg dato du vil føre timer for" /></label>
                                </td>
                                <td valign="top">
                                	<g:datoVelger id="dp-1" name="timeforingDato" onChange="submitForm('velgDatoForm')" value="${new Date()}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                
                    <span class="button"><g:actionSubmit class="save" action="velgDato" value="Før timer for valgt dato" /></span>
                
            </g:form>
            
            <br>
            <h1>Registreringer</h1>
            <div class="list">
            	
            	<table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="dato" title="Dato" />
                            
                            <g:sortableColumn property="timer" title="Totale timer" />
                        
                            <g:sortableColumn property="kjorteKilometer" title="Totale kilometer" />
                            
                            <g:sortableColumn property="belop" title="Beløp" />
                                
                            <g:sortableColumn property="sendtInn" title="Sendt inn" />
                            
                            <th/>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${timeregistreringList}" status="i" var="timeregistreringItem">
                       
                        <g:if test="${!timeregistreringItem.sendtInn == true}">
                        	<tr class="redcolor">
                        </g:if>
                        <g:else>
                        	<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        </g:else>
                        
                            <td><g:formatDate date="${timeregistreringItem.dato}" locale="no" type="date" style="LONG"/></td>
                            
                            <td>${fieldValue(bean: timeregistreringItem, field: "totaleTimer")}</td>
                         
                            <td>${fieldValue(bean: timeregistreringItem, field: "kjorteKilometer")}</td>
                            
                            <td><g:formatNumber number="${timeregistreringItem?.belop}" format="0.00" /> <g:message code="sil.kr" default="kr" /></td>
                            
                            <td><g:formatBoolean boolean="${timeregistreringItem.sendtInn}" true="Ja" false="Nei"/></td>
                            
                            <td>
                            	<g:link action="listTotal" params="[valgtDato: timeregistreringItem.getFormattedDate()]">
                            		<g:if test="${timeregistreringItem.sendtInn}">Se på</g:if>
                            		<g:else>Endre</g:else>
                            	</g:link>
                            </td>
                        </tr>
                        
                    </g:each>
                    </tbody>
                </table>
            	
            </div>
        
            
        </div>
    </body>
</html>
