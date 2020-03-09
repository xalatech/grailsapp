<%@ page import="siv.type.*" %>
<html>
<head>
<title>Massevalg</title>
<meta name="layout" content="main2" />
</head>

<body>
<h1>Massevalg</h1>
<g:hasErrors bean="${statusCommand}">
	<div class="errors">
		<g:renderErrors bean="${statusCommand}" as="list" />
	</div>
</g:hasErrors>

<g:form action="statusChangeResult" method="post">

<table>

<tr>
	<td>
	
	<div class="dialog">
	<h2>Intervjuobjekter</h2>
<table>
	<thead>
		<tr>
			<th/>
			
			<th>
				<g:message code="intervjuObjekt.intervjuObjektNummer.label" default="IO-nr" />
			</th>

			<g:sortableColumn property="navn" title="${message(code: 'intervjuObjekt.navn.label', default: 'Navn')}" />
			
			<th>
				<g:message code="intervjuObjekt.merknadFraInt" default="Merknad fra intervjuer"/>
			</th>
		</tr>
	</thead>

	<tbody>
		<g:each in="${ intervjuObjektInstanceList }" var="intervjuObjektInstance" status="i">
			<tr class="prop">
				<td valign="top" style="width: 20px; text-align: center;"><g:checkBox name="check" value="${intervjuObjektInstance.id}" /></td>
				<td style="width: 50px; text-align: right;">${fieldValue(bean: intervjuObjektInstance, field: "intervjuObjektNummer")}</td>
				<td>${fieldValue(bean: intervjuObjektInstance, field: "navn")}</td>
				<td>${fieldValue(bean: intervjuObjektInstance, field: "kommentar")}</td>
			</tr>
		</g:each>
	</tbody>
</table>
</div>
	</td>
	
	<td>
	
<div class="dialog">
	
		<h2>Endre valgte</h2>
		<table>
			<tr class="prop">
				<td>
					<label for="skjemaStatus">Skjemastatus</label>
				</td>
				<td>
					<g:set var="statusList" value="[SkjemaStatus.Ubehandlet, SkjemaStatus.Ferdig, SkjemaStatus.Paa_vent, SkjemaStatus.Utsendt_CATI, SkjemaStatus.Reut_CATI]" />
					<g:select name="skjemaStatus" from="${statusList}"
						optionKey="key"
						optionValue="guiName"
					 />
				</td>
				
			</tr>
			
			<tr class="prop">
				<td>
					<label for="intervjuStatus">Intervjustatus</label>
				</td>				
				<td valign="top" class="value ${hasErrors(bean: statusCommand, field: 'intervjuStatus', 'errors') ? "errors" : ""}">
                    <g:textField name="intervjuStatus" value="${statusCommand.intervjuStatus}" size="4" />
				</td>
			</tr>
			
			<tr class="prop">
				<td valign="top" class="name">
					<label for="meldingTilIntervjuer"><g:message code="intervjuObjekt.meldingTilIntervjuer.label" default="Melding til intervjuer" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'meldingTilIntervjuer', 'errors')}">
					<textArea name="meldingTilIntervjuer" id="meldingTilIntervjuer" style="height: 35px" cols="40">${intervjuObjektInstance?.meldingTilIntervjuer}</textArea>
				</td>
			</tr>
		</table>
		
		<input type="submit" />
		<g:actionSubmit value="Avbryt" action="searchResult" />
		
</div>
	</td>
</tr>

</table>

</g:form>




</body>
</html>