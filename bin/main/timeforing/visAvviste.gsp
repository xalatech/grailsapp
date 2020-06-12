<%@ page import="sivadm.Intervjuer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'intervjuer.label', default: 'Intervjuer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
          	  <div class="message">${flash.message}</div>
            </g:if>
			<h1><g:message code="xxx" default="Avviste krav" /></h1>
           
           <g:form action="visAvviste" method="post">
            	<table>
            		<tr class="prop">
                         <td valign="top" class="name">
                           <label for="fraDato"><g:message code="xx" default="Fra:" /></label>
                         </td>
                         <td valign="top">
	                        <g:datoVelger id="dp-1" name="fraDato" value="${fraDato}" />
                         </td>
                    </tr>
                    <tr class="prop">
                         <td valign="top" class="name">
                           <label for="tilDato"><g:message code="xx" default="Til:" /></label>
                         </td>
                         <td valign="top">
	                        <g:datoVelger id="dp-2" name="tilDato" value="${tilDato}" />
                         </td>
                         
                    </tr>
            	</table>
            	<g:submitButton name="list" value="${message(code: 'xx', default: 'Finn krav')}"/>
            	<g:hiddenField name="id" value="${intervjuerInstance?.id}" />
            </g:form>
            
            <h1>Arbeidstid</h1>
            <g:if test="${timeforingInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen avviste timeføringer</p>
            		<br/>
            		<br/>
            </g:if>
            	
            <g:else>
            <table>
            	<thead>
                	<tr>
                    	<th>Dato</th>
                    	<g:sortableColumn property="fra" title="${message(code: 'timeforing.fra.label', default: 'Fra')}" />
                        <g:sortableColumn property="til" title="${message(code: 'timeforing.til.label', default: 'Til')}" />
                        <g:sortableColumn property="produktNummer" title="${message(code: 'timeforing.produktNummer.label', default: 'Produktnummer')}" />
                        <g:sortableColumn property="arbeidsType" title="${message(code: 'timeforing.arbeidsType.label', default: 'Arbeidstype')}" />
                        <th>Melding fra kontroll</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${timeforingInstanceList}" status="i" var="timeforingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td><g:formatDate date="${timeforingInstance.fra}" format="dd.MM.yyyy"/></td>
                        
                            <td><g:formatDate date="${timeforingInstance.fra}" format="HH:mm"/></td>
                            
                            <td><g:formatDate date="${timeforingInstance.til}" format="HH:mm"/></td>
                        
                            <td>${fieldValue(bean: timeforingInstance, field: "produktNummer")}</td>
   	                      
                            <td>${fieldValue(bean: timeforingInstance, field: "arbeidsType")}</td>
    	                    <td><g:if test="${timeforingInstance.silMelding}">${fieldValue(bean: timeforingInstance.silMelding, field: "tittel")} - ${fieldValue(bean: timeforingInstance.silMelding, field: "melding")}</g:if></td>
                        </tr>
                    </g:each>
                </tbody>
           </table> 
           </g:else>
           <br>
          <h1>Kjørebok</h1> 
           <g:if test="${kjorebokInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen avviste kjørebøker</p>
            		<br/>
            		<br/>
            </g:if>
            	
            <g:else>
            
            <table>
                    <thead>
                        <tr>
                        	<th>Dato</th>
                        	<g:sortableColumn property="fraTidspunkt" title="${message(code: 'kjorebok.fraTidspunkt.label', default: 'Fra')}" />
                        	
                        	<g:sortableColumn property="tilTidspunkt" title="${message(code: 'kjorebok.fraTidspunkt.label', default: 'Til')}" />
                        	
                            <g:sortableColumn property="fraAdresse" title="${message(code: 'kjorebok.fraAdresse.label', default: 'Fra adresse')}" />
                        
                            <g:sortableColumn property="fraPoststed" title="${message(code: 'kjorebok.fraPoststed.label', default: 'Fra poststed')}" />
                            
                            <g:sortableColumn property="intervjuobjekt" title="${message(code: 'kjorebok.intervjuobjekt.label', default: 'Intervjuobjekt')}" />
                            
                            <g:sortableColumn property="merknad" title="${message(code: 'kjorebok.merknad.label', default: 'Merknad')}" />
                            
                            <g:sortableColumn property="tilAdresse" title="${message(code: 'kjorebok.tilAdresse.label', default: 'Til adresse')}" />
                        
                            <g:sortableColumn property="tilPoststed" title="${message(code: 'kjorebok.tilPoststed.label', default: 'Til poststed')}" />
                            
                            <g:sortableColumn property="produktNummer" title="${message(code: 'kjorebok.produktNummer.label', default: 'Produktnr')}" />
                            
                            <g:sortableColumn property="kjorteKilometer" title="${message(code: 'kjorebok.kjorteKilometer.label', default: 'Km')}" />
                            
                            <g:sortableColumn property="transportmiddel" title="${message(code: 'kjorebok.transportmiddel.label', default: 'Transportmiddel')}" />
                            
                     		  <th>Melding fra kontroll</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${kjorebokInstanceList}" status="i" var="kjorebokInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<td><g:formatDate date="${kjorebokInstance.fraTidspunkt}" format="dd.MM.yyyy"/></td>
                        	
                        	<td><g:formatDate date="${kjorebokInstance.fraTidspunkt}" format="HH:mm"/></td>
                        	
                        	<td><g:formatDate date="${kjorebokInstance.tilTidspunkt}" format="HH:mm"/></td>
                        	
                            <td>${fieldValue(bean: kjorebokInstance, field: "fraAdresse")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "fraPoststed")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "intervjuobjekt")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "merknad")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "tilAdresse")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "tilPoststed")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "produktNummer")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "kjorteKilometer")}</td>
                            
                            <td>
                            	${fieldValue(bean: kjorebokInstance, field: "transportmiddel")}
                            	<g:if test="${kjorebokInstance.antallPassasjerer > 0}">
                            		<g:message code="sivadm.kjorebok.passasjer.forkortet" args="${[kjorebokInstance.antallPassasjerer]}"  />
                            	</g:if>
                            </td>
                            
                            <td><g:if test="${kjorebokInstance.silMelding}">${fieldValue(bean: kjorebokInstance.silMelding, field: "tittel")} - ${fieldValue(bean: kjorebokInstance.silMelding, field: "melding")}</g:if></td>
                  
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:else>
            
            <br/>
            
            <h1>Utlegg</h1>
            <g:if test="${utleggInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen avviste utlegg</p>
            		<br/>
            		<br/>
            	</g:if>
            	
            	<g:else>
            		<table>
                    <thead>
                        <tr>
                        	<th>Dato</th>
                        	<g:sortableColumn property="produktNummer" title="${message(code: 'utlegg.produktNummer.label', default: 'Produktnummer')}" />
                        	
                        	<g:sortableColumn property="utleggType" title="${message(code: 'utlegg.utleggType.label', default: 'Utleggstype')}" />
                        	
                        	<g:sortableColumn property="spesifisering" title="${message(code: 'utlegg.utleggType.label', default: 'Spesifisering')}" />
                        	
                        	<g:sortableColumn property="merknad" title="${message(code: 'utlegg.merknad.label', default: 'Merknad')}" />
                        	
                            <g:sortableColumn property="belop" title="${message(code: 'utlegg.belop.label', default: 'Beløp')}" />
                             <th>Melding fra kontroll</th>
                               
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${utleggInstanceList}" status="i" var="utleggInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                       		<td><g:formatDate date="${utleggInstance.dato}" format="dd.MM.yyyy"/></td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "produktNummer")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "utleggType")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "spesifisering")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "merknad")}</td>
                        	
                            <td>${fieldValue(bean: utleggInstance, field: "belop")}</td>
                            
                           <td><g:if test="${utleggInstance.silMelding}">${fieldValue(bean: utleggInstance.silMelding, field: "tittel")} - ${fieldValue(bean: utleggInstance.silMelding, field: "melding")}</g:if></td>
                    
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            	
            	</g:else>
        </div>
    </body>
</html>