<%@ page import="siv.type.ProsjektFinansiering" %>
<%@ page import="sivadm.Prosjekt"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'prosjekt.label', default: 'Prosjekt')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton">
			<g:link class="create" action="create"><g:message code="sivadm.prosjekt.nytt" default="Nytt prosjekt" /></g:link>
		</span>
	</div>
	<div class="body">
	
	<h1><g:message code="sivadm.prosjekt.sok" default="Prosjektsøk" /></h1>
	
	<g:if test="${flash.errorMessage}">
		<div class="errors">${flash.errorMessage}</div>
	</g:if>
	
	<g:form method="post">
		<div class="dialog">
			<table>
				<tbody>
					<tr class="prop2">
						<td class="name"><g:message code="sivadm.produktnummer" default="Produktnummer" /></td>
						<td class="name">
							<g:textField name="produktNummer" size="5" value="${produktNummer}"/>
						</td>
						
						<td class="space"/>
						
						<td class="name">Navn</td>
						<td class="name">
							<g:textField name="prosjektNavn" value="${prosjektNavn}" size="40"/>
						</td>
						
						<td class="space"/>
						
						<td class="name">Status</td>
						<td class="name">
							<g:select name="prosjektStatus"
								from="${prosjektStatusList}"
								optionKey="key"
								optionValue="guiName"
								value="${prosjektStatusDefault?.key}"
								noSelection="['' : 'Alle']" />				
						</td>
						
						<td class="space"/>
						
						<td class="name">Årgang</td>
						<td class="name">
							<g:textField name="prosjektAargang" value="${prosjektAargang}" size="4" maxlength="4" />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="buttonStyle">
				<g:actionSubmit action="list" value="Søk"></g:actionSubmit>
			</div>
		</div>
	</g:form>
		
	<h1>
		<g:message code="sivadm.prosjekt.liste" default="Prosjektliste" />
		<g:if test="${prosjektInstanceTotal}"> (${prosjektInstanceTotal})</g:if>
	</h1>
	<g:if test="${flash.message}">
		<div class="message">
		${flash.message}
		</div>
	</g:if>
	<div class="list">
	<table>
		<thead>
			<tr>
	
				<g:sortableColumn property="id"
					title="${message(code: 'prosjekt.id.label', default: 'Id')}" />
	
				<g:sortableColumn property="prosjektNavn"
					title="${message(code: 'prosjekt.prosjektNavn.label', default: 'Prosjektnavn')}" />
	
				<g:sortableColumn property="aargang"
					title="${message(code: 'prosjekt.aargang.label', default: 'Årgang')}" />
	
				<th>Skjema</th>	
				<g:sortableColumn property="prosjektStatus"
					title="${message(code: 'prosjekt.prosjektStatus.label', default: 'Prosjektstatus')}" />
	
				<g:sortableColumn property="finansiering"
					title="${message(code: 'prosjekt.finansiering.label', default: 'Finansiering')}" />
	
				<th/>
			</tr>
		</thead>
		<tbody>
			<g:each in="${prosjektInstanceList}" status="i" var="prosjektInstance">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	
					<td>
						<g:link action="edit" id="${prosjektInstance.id}"><g:formatNumber number="${prosjektInstance?.id}" format="#"/></g:link>
					</td>
	
					<td>
						<g:link action="edit" id="${prosjektInstance.id}">${fieldValue(bean: prosjektInstance, field: "prosjektNavn")}</g:link>
						
					</td>
	
					<td>
						${fieldValue(bean: prosjektInstance, field: "aargang")}
					</td>
	
					<td valign="top" style="text-align: left;" class="value">
						<ul>
							<g:each in="${prosjektInstance.skjemaer?.sort{it.delProduktNummer}}" var="s">
								<li><g:link controller="skjema" action="edit" id="${s.id}"  params="['fraProsjektListe': true]">
									${s?.encodeAsHTML()}
								</g:link></li>
							</g:each>
						</ul>
					</td>
					
					<td>
						${fieldValue(bean: prosjektInstance, field: "prosjektStatus")}
					</td>
					
					<td>
		            	${fieldValue(bean: prosjektInstance, field: "finansiering")}
		            	<g:if test="${prosjektInstance?.finansiering == ProsjektFinansiering.STAT_MARKED}">
	                		(${fieldValue(bean: prosjektInstance, field: "prosentStat")}% <g:message code="sivadm.Stat" default="Stat" /> / ${fieldValue(bean: prosjektInstance, field: "prosentMarked")}% <g:message code="sivadm.Marked" default="Marked" />)
	                	</g:if>
	                </td>
	                <td>
						<g:link action="edit" id="${prosjektInstance.id}"><g:redigerIkon /></g:link>
						&nbsp;&nbsp;
						<a href="#" onclick="return apneSlettDialog(${prosjektInstance.id})"><g:slettIkon /></a>
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
	</div>
	<div class="paginateButtons">
		<g:paginate	total="${prosjektInstanceTotal}" /></div>
	</div>
	<g:slettDialog domeneKlasse="prosjekt" melding="Er du sikker på at du vil slette prosjekt? Dette kan ha negative følger hvis intervjuere har ført timer på dette prosjektet, og disse ennå ikke er sendt til SAP." />
</body>
</html>
