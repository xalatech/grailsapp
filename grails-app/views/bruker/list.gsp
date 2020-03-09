
<%@ page import="sivadm.Bruker" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'bruker.label', default: 'Bruker')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
         <script>
        	function nullstillFelter() {
				var feltNavn = document.getElementById("initialer");
				var feltTaMedInter = document.getElementById("taMedIntervjuere");
	
				if(feltNavn) {
					feltNavn.value = "";
				}

				if(feltTaMedInter) {
					feltTaMedInter.checked = false;
				}
							
				return false;
            }
        </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <h1><g:message code="sivadm.bruker.sok" default="Bruker søk" /></h1>
           	<g:form action="list" method="post">
            	<table>
            		<tr class="prop">
                         <td valign="top" class="name">
                           <label for="initialer"><g:message code="intervjuer.initialer.label" default="Initialer / Navn:" /></label>
                         </td>
                         <td valign="top">
                             <g:textField name="initialer" value="${initialer}" />
                         </td>
                    </tr>
                    
                    <tr>
                    	<td>
                    		<label for="taMedIntervjuere"><g:message code="intervjuer.status.label" default="Vis intervjuere:" /></label>
                    	</td>
                    	<td>
                    		<g:checkBox name="taMedIntervjuere" value="${taMedIntervjuere}" />
                    	</td>
                    </tr>
            	</table>
            	<g:submitButton name="list" value="Søk"/>
            	<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="searchNullstill" onclick="return nullstillFelter();" />         
           	</g:form>
            
            
            <h1>
            	<g:message code="default.list.label" args="[entityName]" />
            	<g:if test="${brukerInstanceTotal}">(${brukerInstanceTotal})</g:if>
            </h1>
                                    
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'bruker.id.label', default: 'Id')}" />
                        
	                        <g:sortableColumn property="navn" title="${message(code: 'bruker.navn.label', default: 'Navn')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'bruker.username.label', default: 'Brukernavn/initialer')}" />
                        
                        	<th><g:message code="sivadm.roller" default="Rolle(r)" /></th>
                        
	                        <g:sortableColumn property="enabled" title="${message(code: 'bruker.enabled.label', default: 'Aktiv')}" />
                        
                            <g:sortableColumn property="accountExpired" title="${message(code: 'bruker.accountExpired.label', default: 'Utgått')}" />
                        
                            <g:sortableColumn property="accountLocked" title="${message(code: 'bruker.accountLocked.label', default: 'Låst')}" />
                            
                            <g:sortableColumn property="passwordExpired" title="${message(code: 'bruker.passwordExpired.label', default: 'Passord utgått')}" />
                        
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${brukerInstanceList}" status="i" var="brukerInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${brukerInstance.id}"><g:formatNumber number="${brukerInstance?.id}" format="#" /></g:link></td>
                        
	                        <td>${fieldValue(bean: brukerInstance, field: "navn")}</td>
                        
                            <td style="width: 120px;">${fieldValue(bean: brukerInstance, field: "username")}</td>
                        
                        	<td>
                        		<g:if test="${brukerInstance?.authorities}">
                        			<g:each in="${brukerInstance?.authorities}" status="j" var="rolle"><g:if test="${j != 0}">,&nbsp</g:if>${rolle}</g:each>
                        		</g:if>
                        		<g:else>
                        			&nbsp;
                        		</g:else>
                        	</td>
                        
	                        <td><g:formatBoolean boolean="${brukerInstance.enabled}" true="Ja" false="Nei" /></td>
                        
                            <td><g:formatBoolean boolean="${brukerInstance.accountExpired}" true="Ja" false="Nei" /></td>
                        
                            <td><g:formatBoolean boolean="${brukerInstance.accountLocked}" true="Ja" false="Nei" /></td>
                            
                            <td><g:formatBoolean boolean="${brukerInstance.passwordExpired}" true="Ja" false="Nei" /></td>
                            
                        	<td>
                            	<g:link action="edit" id="${brukerInstance.id}"><g:redigerIkon/></g:link>
                            	&nbsp;&nbsp;
                            	<a href="#" onclick="return apneSlettDialog(${brukerInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${brukerInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="bruker" />
        </div>
    </body>
</html>
