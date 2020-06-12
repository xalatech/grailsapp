<html>

<head>
<title>Utvalgsimport</title>
<meta name="layout" content="main" />
</head>

<body>

<g:form controller="utvalgAdministrasjon" method="post"
	action="saveUtvalg" enctype="multipart/form-data">

	<g:if test="${ flash.errorMessage }">
		<div class=errors>${flash.errorMessage}</div>
	</g:if>
	<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
	</g:if>
	<h1>Utvalg</h1>

	<div class="dialog">
		<h2>Importer fra fil</h2>
		
		<br/>
		<table>
			<tbody>
				<tr class="prop">
					<td class="name">
						<label for="skjemanavn"><g:message code="utvalgimport.skjemaNavn.label" default="Velg skjema:" /></label> 
					</td>
					<td class="value">
						<g:select name="skjema" from="${skjemaer}" optionKey="id" noSelection="['':'-Velg skjema']" />
					</td>
				</tr>
	
				<tr class="prop">
					<td class="name">
						<label for="file"><g:message code="utvalgimport.filNavn.label" default="Velg utvalgsfil:" /></label>
					</td>
					<td class="value">
						<input type="file" name="file" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="buttonStyle">
		<g:actionSubmit action="saveUtvalg" value="Last inn utvalg"/>
		<g:actionSubmit action="selectSkjema" value="Oppdater liste"/>
	</div>
	
	
	<h2>Allerede importerte utvalg</h2>
	<div class="list">
		<table>
			<thead>
				<tr>
					<g:sortableColumn property="id"	title="${message(code: 'utvalgImport.id.label', default: 'Id')}" />
					
					<g:sortableColumn property="skjema"	title="${message(code: 'utvalgImport.skjema.label', default: 'Skjema')}" />
						
					<g:sortableColumn property="importDato"	title="${message(code: 'utvalgImport.importDato.label', default: 'Import dato')}" style="width: 130px" />
						
					<g:sortableColumn property="antallFil" title="${message(code: 'utvalgImport.antallFil.label', default: 'Antall i fil')}" style="width: 70px" />
	
					<g:sortableColumn property="antallImportert" title="${message(code: 'utvalgImport.antallImportert.label', default: 'Antall importert')}" style="width: 100px" />
	
					<g:sortableColumn property="importertAv" title="${message(code: 'utvalgImport.importertAv.label', default: 'Importert av')}" style="width: 75px" />
						
					<g:sortableColumn property="melding" title="${message(code: 'utvalgImport.melding.label', default: 'Lastestatus')}" />
					
					<th>Slett</th>
					
					<th>Delreg-ID</th>
					
					<th>Godkjenn</th>
					
					<th/>
					
					<th/>
				</tr>
			</thead>
			<tbody>
				<g:each in="${utvalgImportInstanceList}" status="i"
					var="utvalgImportInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	
						<td>
							${fieldValue(bean: utvalgImportInstance, field: "id")}
						</td>
	
						<td>
							<nobr>${fieldValue(bean: utvalgImportInstance, field: "skjema")}</nobr>
						</td>
						
						<td><g:formatDate date="${utvalgImportInstance.importDato}" format="dd.MM.yyyy  HH:mm:ss" /></td>
						
						<td style="text-align: right">
							<g:formatNumber number="${utvalgImportInstance?.antallFil}" format="#" />
						</td>
						
						<td style="text-align: right">
							<g:formatNumber number="${utvalgImportInstance?.antallImportert}" format="#" />
						</td>
	
						<td>
							${fieldValue(bean: utvalgImportInstance, field: "importertAv")}
						</td>
						
						<td>
							${fieldValue(bean: utvalgImportInstance, field: "melding")}
						</td>
						
						<td>
							<g:if test="${utvalgImportInstance?.antallImportert > 0}">
		                       	<a href="#" onclick="return apneSlettDialog(${utvalgImportInstance.id})"><g:slettIkon /></a>
							</g:if>						
						</td>
						
						<td>
							<g:if test="${utvalgImportInstance?.antallImportert > 0}">
		                       	<a href="#" onclick="return apneGodkjennDialog(${utvalgImportInstance.id})"><g:godkjennIkon /></a>
							</g:if>						
						</td>
										
						<td>
							<g:if test="${utvalgImportInstance?.antallImportert > 0}">
								<g:link action="downloadUtvalg" id="${utvalgImportInstance.id}">Last ned</g:link>
							</g:if>						
						</td>
						
						<td>
							<g:if test="${utvalgImportInstance?.antallImportert > 0}">
								<g:link action="skrivUtvalgTilFil" id="${utvalgImportInstance.id}">Skriv til filområde</g:link>
							</g:if>						
						</td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
	
	

</g:form>

<g:slettDialog domeneKlasse="utvalg" controller="utvalgAdministrasjon" action="deleteUtvalg" melding="Er du helt sikker på at du vil slette utvalget? Dette vil også medføre sletting av intervjuobjektene."/>
<g:godkjennDialog domeneKlasse="utvalg" controller="utvalgAdministrasjon" action="godkjennUtvalg" melding="Er du helt sikker på at du vil godkjenne utvalget? Dette medfører at intervjuobjektene kopieres til SFU."/>

</body>

</html>