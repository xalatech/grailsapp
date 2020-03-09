
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

<h1>Permisjon barn sykdom</h1>
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


<g:form action="permisjonBarnSave" method="post">
	
	<div class="dialog">
	
	<h2>Tidsrom</h2>
	
	<g:hasErrors bean="${permisjonCommand}">
		<div class="errors">
			<g:renderErrors bean="${permisjonCommand}" as="list" />
		</div>
	</g:hasErrors>
	
	<table>
		<tbody>
			<tr class="prop2">
				<td valign="top" class="name">
					<label for="fraDato"><g:message code="egenmelding.med.fraDato.label" default="Fra" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: permisjonCommand, field: 'fraDato', 'errors')}">
					<g:datoVelger id="dp-1" name="fraDato" value="${permisjonCommand?.fraDato}" />
				</td>
			</tr>

			<tr class="prop2">
				<td valign="top" class="name">
					<label for="tilDato"><g:message code="egenmelding.med.fraDato.label" default="Til" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: permisjonCommand, field: 'tilDato', 'errors')}">
					<g:datoVelger id="dp-2" name="tilDato" value="${permisjonCommand?.tilDato}" />
				</td>
			</tr>
			
			<tr class="prop2">
				<td class="name">
					Alene om omsorgen?
				</td>
				<td valign="top" class="value ${hasErrors(bean: permisjonCommand, field: 'alene', 'errors')}">
					<g:checkBox name="alene" checked="false" value="${permisjonCommand?.alene}" />
				</td>
			</tr>
			
			<tr class="prop2">
				<td class="name">
					Antall barn under 12 år
				</td>
				<td valign="top" class="value ${hasErrors(bean: permisjonCommand, field: 'antallBarn', 'errors')}">
					<g:textField name="antallBarn" size="3" value="${permisjonCommand?.antallBarn}" />
				</td>
			</tr>
			
			<tr class="prop2">
				<td class="name">
					Barnets fødselsdato (dd.mm.åååå)
				</td>
				<td valign="top" class="value ${hasErrors(bean: permisjonCommand, field: 'fodselsdato', 'errors')}">
					<g:datoVelger id="dp-3" name="fodselsdato" value="${permisjonCommand?.fodselsdato}"/>
				</td>
			</tr>
		</tbody>
	</table>

	

	<h2>Merknad</h2>
	<g:textArea name="merknad" value="${permisjonCommand?.merknad}" rows="5" cols="40" />

	<div class="buttons"><span class="button"><g:submitButton
		name="egenmeldingSave" class="save" value="Send inn" /></span>
		<span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span></div>
	</div>

</g:form></div>
</body>
</html>
