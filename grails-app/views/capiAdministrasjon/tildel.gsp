<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Capi administrasjon</title>
    </head>
    <body>
        <div class="body">
        
        <g:if test="${flash.errorMessage}">
			<div class="errors">
				${flash.errorMessage}
			</div>
		</g:if>
        
        <h1>Capi tilordning</h1>
        
        <h2>Tildel intervjuer til intervjuobjekt</h2>
        
        <g:if test="${tildelIntervjuerCommand}">
            <g:hasErrors bean="${tildelIntervjuerCommand}">
            	<div class="errors">
               		<g:renderErrors bean="${tildelIntervjuerCommand}" as="list" />
            	</div>
            </g:hasErrors>	
		</g:if>
        
        <g:form action="lagreTildel" method="post">
        
        <g:hiddenField name="redir" value="${redir}"/>
        
        <g:render template="/templates/ioInfoTemplate" model="[intervjuObjektInstance: intervjuObjekt, visAdresse: true]" />
     
     	<br/>
        
        <div class="dialog">
        	<table>
        		<tbody>
        			<tr class="prop">
        				<td valign="top" class="name">
        					<label for="intervjuerKlynge"><g:message code="capi.intervjubobjektnavn.label" default="Velg intervjuer (klynge)" /></label> 
        				</td>
        					
        				<td valign="top" class="value ${(hasErrors(bean: tildelIntervjuerCommand, field: "intervjuer" , "errors"))}">
        					<g:select name="intervjuerKlynge"
        						noSelection="${['' : '--- Velg intervjuer ---']}"
        						from="${intervjuerListKlynge?.sort{it.navn}}"
        						optionKey="id"
        						value="${tildelIntervjuerCommand?.intervjuerKlynge}">
        					</g:select>
        				</td>
        			</tr>
        			
        			<tr class="prop">
        				<td valign="top" class="name">
        					<label for="intervjuer"><g:message code="capi.intervjubobjektnavn.label" default="Velg intervjuer (alle aktive)" /></label> 
        				</td>
        					
        				<td valign="top" class="value ${(hasErrors(bean: tildelIntervjuerCommand, field: "intervjuer" , "errors"))}">
        					<g:select name="intervjuer"
	        					noSelection="${['' : '--- Velg intervjuer ---']}"
        						from="${intervjuerList?.sort{it.navn}}"
        						optionKey="id"
        						value="${tildelIntervjuerCommand?.intervjuer}">
        					</g:select>
        					<g:link action="finnNaboIntervjuer" id="${intervjuObjekt.id}" params="${[redir: redir]}" title="${message(code: 'sivadm.intervjuobjekt.tildel.finn.naermeste.tooltip', default: '')}"><g:message code="sivadm.intervjuobjekt.tildel.finn.naermeste" default="Finn nærmeste" /></g:link>
        				</td>
        			</tr>
        			
        			<tr class="prop">
        				<td valign="top" class="name">
        					<label for="sisteFrist"><g:message code="capi.sistefrist.label" default="Siste frist" /></label> 
        				</td>
        					
        				<td valign="top" class="value ${(hasErrors(bean: tildelIntervjuerCommand, field: "sisteFrist" , "errors"))}">
        					<g:datoVelger id="dp-1" name="sisteFrist" value="${dato}"  />
        				</td>
        			</tr>
        				
        		</tbody>
        	</table>
        </div>
        
        	<g:hiddenField name="intervjuObjektId" value="${intervjuObjekt.id}"/>
        
        	<div class="buttons">
        		<span class="button"><g:submitButton name="lagreTildel" class="save" value="Tildel" /></span>
        		<span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
        	</div>
        </g:form>
        
        </div>
    </body>
</html>