
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main2" />
</head>
<body>

<div class="body"><g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>

<h1>Egenmelding sykdom</h1>
<br />

<table id="oppsummering" class="infoBody">
	<tbody>
		<tr>
			<td>Navn: ${intervjuerInstance?.navn}
			</td>
			<td>Intervjuernummer: ${intervjuerInstance?.intervjuerNummer}
			</td>
			<td>Klynge: ${intervjuerInstance?.klynge}
			</td>
			<td>Epost: ${intervjuerInstance?.epostJobb}
			</td>
		</tr>
	</tbody>
</table>

<g:form action="egenmeldingSave">
	
	<div class="dialog">
	
	<h2>Tidsrom</h2>
	
	<g:hasErrors bean="${egenmeldingCommand}">
		<div class="errors">
			<g:renderErrors bean="${egenmeldingCommand}" as="list" />
		</div>
	</g:hasErrors>
		
	<table>
		<tbody>
			<tr class="prop2">
				<td valign="top" class="name">
					<label for="fraDato"><g:message code="egenmelding.med.fraDato.label" default="Fra" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: egenmeldingCommand, field: 'fraDato', 'errors')}">
					<g:datoVelger id="dp-1" name="fraDato" value="${egenmeldingCommand?.fraDato}" />	
				</td>
			</tr>

			<tr class="prop2">
				<td valign="top" class="name">
					<label for="tilDato"><g:message code="egenmelding.med.fraDato.label" default="Til" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: egenmeldingCommand, field: 'tilDato', 'errors')}">
					<g:datoVelger id="dp-2" name="tilDato" value="${egenmeldingCommand?.tilDato}" />
				</td>
			</tr>
		</tbody>
	</table>

	<h2>Fraværet skyldes</h2>
	
	<table>
		<tbody>
			<tr class="prop2">
				<td class="name"><g:radio name="aarsak" value="egenSykdom" checked="true"/></td>
				<td class="value">Egen sykdom</td>
			</tr>
			<tr class="prop2">
				<td class="name"><g:radio name="aarsak" value="egenSykdomJobb"/></td>
				<td class="value">Egen sykdom som antas å ha sin grunn i arbeidet.</td>
			</tr>
			<tr class="prop2">
				<td class="name"><g:checkBox name="ikkeRettPaaSykepenger" value="ikkeRettPaaSykepenger" checked="false"/></td>
				<td class="value">Dersom du mottar en form for pensjon/stønad og ikke har rett på sykepenger fra NAV, merk av her.</td>
			</tr>
		</tbody>
	</table>

	<h2>Merknad</h2>
	<g:textArea name="merknad" value="" rows="5" cols="40" />

	<div class="buttons">
		<span class="button"><g:submitButton name="egenmeldingSave" class="save" value="Send inn" /></span>
		<span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
	</div>
	
	</div>

</g:form></div>
</body>
</html>
