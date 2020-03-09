<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main2" />
<title>Rapport om skjevheter</title>
</head>
<body>
	<div class="body">

		<h1>Skjevheter for ${skjema.skjemaNavn} (${skjema.skjemaKortNavn})</h1>

		<br/>
		
		<g:form action="visRapport" method="post">
        	Rapporttype: 
        	<g:select name="rapportType" from="${['Prosent', 'Absolutte tall']}" value="${rapportType}" />
        	<g:hiddenField name="id" value="${skjemaId}"/>
			<g:submitButton name="visRapport" value="Vis rapport" />
		</g:form>
		
		<div class="list">
			
			<h2>Kjønn</h3>
			<table>
				<tr>
					<th>Kjønn</th>
					<th>Bruttoutvalg</th>
					<th>Intervju</th>
					<th>Frafall</th>
					<th>Til sporing</th>
					<th>Netto - brutto</th>
				</tr>

				<g:each in="${skjevhetKjonnList}" var="skjevhet" status="i">
					
					<tr>
					
						<td width="150">
							${skjevhet.kriterieNavn}
						</td>
						<td>
							<g:formatNumber number="${skjevhet.bruttoUtvalg}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.intervju}" maxFractionDigits="1"/> 
						</td>
						<td>
							<g:formatNumber number="${skjevhet.frafall}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.tilSporing}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.nettoBrutto}" maxFractionDigits="1"/>
						</td>
					</tr>
				</g:each>

			</table>
			
			<br>
			<br>
			
			<h2>Alder</h3>
			<table>
				<tr>
					<th>Aldersgrupper</th>
					<th>Bruttoutvalg</th>
					<th>Intervju</th>
					<th>Frafall</th>
					<th>Til sporing</th>
					<th>Netto - brutto</th>
				</tr>

				<g:each in="${skjevhetAlderList}" var="skjevhet" status="i">
					
					<tr>
						<td width="150">
							${skjevhet.kriterieNavn}
						</td>
						<td>
							<g:formatNumber number="${skjevhet.bruttoUtvalg}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.intervju}" maxFractionDigits="1"/> 
						</td>
						<td>
							<g:formatNumber number="${skjevhet.frafall}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.tilSporing}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.nettoBrutto}" maxFractionDigits="1"/>
						</td>
					</tr>
				</g:each>

			</table>
			
			<br>
			<br>
			
			<h2>Landsdel</h3>
			<table>
				<tr>
					<th>Landsdeler</th>
					<th>Bruttoutvalg</th>
					<th>Intervju</th>
					<th>Frafall</th>
					<th>Til sporing</th>
					<th>Netto - brutto</th>
				</tr>

				<g:each in="${skjevhetLandsdelList}" var="skjevhet" status="i">
					
					<tr>
						<td width="150">
							${skjevhet.kriterieNavn}
						</td>
						<td>
							<g:formatNumber number="${skjevhet.bruttoUtvalg}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.intervju}" maxFractionDigits="1"/> 
						</td>
						<td>
							<g:formatNumber number="${skjevhet.frafall}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.tilSporing}" maxFractionDigits="1"/>
						</td>
						<td>
							<g:formatNumber number="${skjevhet.nettoBrutto}" maxFractionDigits="1"/>
						</td>
					</tr>
				</g:each>

			</table>
			
		</div>

	</div>
</body>
</html>