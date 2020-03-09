<%@ page import="sil.Krav" %>
<%@ page import="sil.data.IntervjuerKontroll" %>
<%@ page import="sivadm.Klynge" %>
<%@ page import="sivadm.Intervjuer" %>
<%@ page import="sil.type.KravType" %>
<%@ page import="sil.type.KravStatus" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="sil.intervjuer.kontroll" default="Intervjuer kontroll" /></title>
    </head>
    <body>
    	<g:javascript>
    		function markerAlleIntervjuere() {
    			var divAlle = document.getElementById("mAlle");
    			var b = false;
    			if(divAlle.innerHTML == "Marker alle") {
    				divAlle.innerHTML = "Fjern markering";
    				b = true;
    			}
    			else {
	    			divAlle.innerHTML = "Marker alle";
    			}
    			
    			var iForm = document.getElementById("intForm");
    			var elem  = iForm.elements
    			
    			for(var i in elem) {
    				if(elem[i].type == "checkbox") {
    					elem[i].checked = b;
    				}
    			}
    		}
    	</g:javascript>
        <div class="body">
            <h1><g:message code="sil.intervjuer.kontroll" default="Intervjuer kontroll" /></h1>
            <g:if test="${ flash.errorMessage }">
				<div class=errors>${flash.errorMessage}</div>
			</g:if>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <g:form action="intervjuerKontroll" method="post">

				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name">
									<label for="navn">
										<g:message code="xx" default="Navn" />
									</label>
								</td>
								<td valign="top">
									<g:textField name="navn" value="${searchDataIntervjuer.navn}" />
								</td>
								<td valign="top" class="name">
									<label for="intervjuer">
										<g:message code="xx" default="Intervjuer" />
									</label>
								</td>
								<td valign="top">
									<g:select name="intervjuer"
										from="${Intervjuer.list()}"
										noSelection="['':'-Velg intervjuer']" optionKey="id" value="${searchDataIntervjuer.intervjuer}"/>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for=klynge>
										<g:message code="xx" default="Klynge" />
									</label>
								</td>
								<td valign="top">
									<g:select name="klynge"
										from="${Klynge.list()}"
										noSelection="['':'-Velg klynge']" optionKey="id" value="${searchDataIntervjuer.klynge}"/>
								</td>
								<td valign="top" class="name">
									&nbsp;
								</td>
								<td valign="top">
									&nbsp;
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<g:hiddenField name="sokPerformed" value="true" />
				<g:actionSubmit value="${message(code: 'sil.sok', default: 'Søk')}" action="intervjuerKontroll" />
				<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="intervjuerKontrollNullstill" />
			</g:form>
			<br><br>
			
			<g:form name="intForm">
			<a href="#" onClick="javascript: markerAlleIntervjuere();" id="mAlle">Marker alle</a><br><br>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        	<th/>
                            <g:sortableColumn property="id" title="${message(code: 'xx', default: 'Id')}" />
                            <g:sortableColumn property="navn" title="${message(code: 'xx', default: 'Intervjuer')}" />
                       		<th><g:message code="sil.Krav" default="Krav" /></th>
                       		<th><g:message code="sil.tid" default="Tid" /></th>
                       		<th><g:message code="sil.Km" default="Km" /></th>
                       		<th><g:message code="sil.belop" default="Beløp" /></th>
                       </tr>
                    </thead>
                    <tbody>
                    	<g:each in="${intervjuerKontrollInstanceList}" status="i" var="intervjuerInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                            <td><g:checkBox name="check" value="${intervjuerInstance.intervjuer.id}" checked="false" /></td>
	                            <td><g:link action="behandleIntervjuerKrav" id="${intervjuerInstance.intervjuer.id}">${fieldValue(bean: intervjuerInstance.intervjuer, field: "id")}</g:link></td>
	                        	<td><g:link action="behandleIntervjuerKrav" id="${intervjuerInstance.intervjuer.id}">${fieldValue(bean: intervjuerInstance.intervjuer, field: "navn")}</g:link></td>
	                        	<td><g:message code="sil.kontroll.krav.info" args="${[intervjuerInstance.antallKrav, intervjuerInstance.antallFeiletAutomatiskeTester]}" default="" /></td>
	                        	<td style="text-align: right;"><g:message code="sil.kontroll.tid.info" args="${[intervjuerInstance.totalTimer, intervjuerInstance.totalMinutter]}"/></td>
	                        	<td style="text-align: right;"><g:message code="sil.kontroll.km.info" args="${[intervjuerInstance.totalKm]}" /></td>
	                        	<td style="text-align: right;">
	                        		<g:formatNumber number="${intervjuerInstance.totalBelop}" format="0.00" /> <g:message code="sil.kr" />
	                        	</td>
	                        </tr>
                    	</g:each>
                    </tbody>
                </table>
            </div>
			<g:if test="${intervjuerKontrollInstanceList}">
           		<g:if test="${intervjuerInstanceTotal > 25}">
           			<div class="paginateButtons">
            			<g:paginate	total="${intervjuerInstanceTotal}" action="intervjuerKontroll"
							params="${params}" />
					</div>
				</g:if>
				<div class="buttons">
					<!--
					<span class="menubutton"><g:link action="behandleIntervjuere" title="${message(code: 'sil.kontroll.behandle.intervjuere.tooltip', default: '')}"><g:message code="sil.kontroll.behandle.intervjuere" default="Behandle" /></g:link></span>
					-->
					<span class="button"><g:actionSubmit class="save" action="behandleIntervjuere" value="${message(code: 'sil.kontroll.behandle.intervjuere', default: 'Behandle valgte')}" title="${message(code: 'sil.kontroll.behandle.intervjuere.tooltip', default: '')}" /></span>
			       	<span class="button"><g:actionSubmit class="save" action="godkjennIntervjuere" value="${message(code: 'sil.kontroll.godkjenn.intervjuere', default: 'Godkjenn valgte')}" title="${message(code: 'sil.kontroll.godkjenn.intervjuere.tooltip', default: '')}" /></span>
				</div>
			</g:if>
			</g:form>
		</div>
    </body>
</html>