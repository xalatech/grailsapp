<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main2" />
<title>Sil ukerapport</title>
</head>
<body>
	<div class="body">

		<h1>Timeregistrering pr uke - rapport</h1>
		
		<p>Denne rapporten viser antall timer intervjuerne har ført både totalt dette året, og for de siste fire ukene. Tallene hentes fra registrerte krav. Det betyr i praksis at intervjuerne må ha godkjent og sendt timeføringene inn. Det er derimot ikke noe krav at kravene skal være godkjent.</p>
		<br>
		
		<div class="dialog">
		
		<g:form action="visRapport">
			Velg dato: 	
			<g:datoVelger id="dp-1" name="dato" value="${dato}"/>
			<g:submitButton name="Vis rapport"/>
		</g:form>
		
			
		</div>
		<br/>
		<br/>

		<div class="list">
			<table>
				<tr>
					<th>Intervjuernummer</th>
					<th>Navn</th>
					<th>Initialer</th>
					<th>Avtalt antall timer</th>
					<th>Timer totalt ${aarstall}</th>
					<th>Uke ${ukeNumre.ukeNummerEnUkeTilbake}</th>
					<th>Uke ${ukeNumre.ukeNummerToUkerTilbake}</th>
					<th>Uke ${ukeNumre.ukeNummerTreUkerTilbake}</th>
					<th>Uke ${ukeNumre.ukeNummerFireUkerTilbake}</th>
				</tr>

				<g:each in="${ukeRapportDataList}" var="ukeRapportData" status="i">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>${ukeRapportData.intervjuerNummer}</td>
						
						<td>${ukeRapportData.navn}</td>
						
						<td>${ukeRapportData.initialer}</td>
						
						<td>${ukeRapportData.avtaltAntallTimer}</td>
						
						<td>${ukeRapportData.timerTotalt}</td>
						
						<td>${ukeRapportData.uke1Tilbake}</td>
						
						<td>${ukeRapportData.uke2Tilbake}</td>
						
						<td>${ukeRapportData.uke3Tilbake}</td>
						
						<td>${ukeRapportData.uke4Tilbake}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>
</body>
</html>