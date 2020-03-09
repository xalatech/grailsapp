
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main2" />
    <g:javascript src="kolon.js"/>
    <g:javascript src="fullforKlokkeslett.js"/>
</head>
<body>

<div class="body"><g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>

<h1>Tekniske problemer</h1>
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


<g:form action="pcTelefonSave" method="post">
	
	<div class="dialog">

	<h2>Tidsrom</h2>
	
	<g:hasErrors bean="${pcTelefonCommand}">
		<div class="errors">
			<g:renderErrors bean="${pcTelefonCommand}" as="list" />
		</div>
	</g:hasErrors>
	
	<table style="width: 250px">
		<tbody>
			<tr class="prop2">
				<td valign="top" class="name">
					<label for="pcTelefonCommand.fraDato">
						<g:message code="egenmelding.med.fraDato.label" default="Fra" />
					</label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: pcTelefonCommand, field: 'fraDato', 'errors')}">
					<g:datoVelger id="dp-1" name="fraDato" value="${pcTelefonCommand?.fraDato}" />
				</td>
				<td valign="top" class="space"></td>
				<td valign="top" class="name">
					<label for="pcTelefonCommand.fraKlokkeslett">
						<g:message code="sivadm.kl" default="kl." />
					</label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: pcTelefonCommand, field: 'fraKlokkeslett', 'errors')}">
					<g:textField onchange="kolon('fraKlokkeslett')" name="fraKlokkeslett" size="5" title="Eksempel: 09" maxlength="5" value="${pcTelefonCommand?.fraKlokkeslett}" />
				</td>
			</tr>

			<tr class="prop2">
				<td valign="top" class="name"><label for="pcTelefonCommand.tilDato"><g:message
					code="egenmelding.med.fraDato.label" default="Til" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: pcTelefonCommand, field: 'tilDato', 'errors')}">
					<g:datoVelger id="dp-2" name="tilDato" value="${pcTelefonCommand?.tilDato}"/>
				</td>
				<td valign="top" class="space"></td>
				<td valign="top" class="name">
					<label for="pcTelefonCommand.tilKlokkeslett">
						<g:message code="sivadm.kl" default="kl." />
					</label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: pcTelefonCommand, field: 'tilKlokkeslett', 'errors')}">
					<g:textField onchange="kolon('tilKlokkeslett')" name="tilKlokkeslett" size="5" title="Eksempel: 1145" maxlength="5" value="${pcTelefonCommand?.tilKlokkeslett}" />
				</td>
			</tr>
		</tbody>
	</table>

	

	<h2>Merknad</h2>

	<g:textArea name="merknad" value="${pcTelefonCommand?.merknad}" rows="5" cols="40" />		

	<div class="buttons"  style="width: 250px">
		<span class="button"><g:submitButton name="egenmeldingSave" class="save" value="Send inn" /></span>
		<span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span></div>
	</div>

</g:form></div>
</body>
</html>
