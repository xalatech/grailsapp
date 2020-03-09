<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Sil prosjektrapport</title>
</head>
<body>
	<div class="body">

		<h1>Prosjektrapport</h1>
		
		<br/>

		<div class="list">
			<table>
				<tr>
					<g:sortableColumn property="skjemaNavn" title="Skjemanavn"/>
					<g:sortableColumn property="delProduktNummer" title="Arbeidsordrenr."/>
					<g:sortableColumn property="aar" title="Årgang"/>
					<g:sortableColumn property="arbeidsTid" title="Arbeidstid"/>
					<g:sortableColumn property="reiseTid" title="Reisetid"/>
					<g:sortableColumn property="totalTid" title="Totaltid"/>					
					<g:sortableColumn property="intervjuTid" title="Intervju"/>
					<g:sortableColumn property="sporingTid" title="Sporing"/>
					<g:sortableColumn property="treningsTid" title="Trening"/>
					<g:sortableColumn property="kursTid" title="Kurs"/>
					<g:sortableColumn property="testTid" title="Test"/>
					<g:sortableColumn property="arbeidsLedelseTid" title="Arbeidsledelse"/>
					<g:sortableColumn property="annetTid" title="Annet"/>					
					<g:sortableColumn property="antallKm" title="Antall km"/>
					<g:sortableColumn property="reiseUtgifter" title="Reiseutgifter"/>
					<g:sortableColumn property="andreUtgifter" title="Andre utgifter"/>
				</tr>

				<g:each in="${prosjektRapportDataList}" var="prosjektRapportData" status="i">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>${prosjektRapportData.skjemaNavn}</td>
						<td>${prosjektRapportData.delProduktNummer}</td>
						<td>${prosjektRapportData.aar}</td>
						<td>${prosjektRapportData.arbeidsTidFormatert}</td>						
						<td>${prosjektRapportData.reiseTidFormatert}</td>
						<td>${prosjektRapportData.totalTidFormatert}</td>						
						<td>${prosjektRapportData.intervjuTidFormatert}</td>
						<td>${prosjektRapportData.sporingTidFormatert}</td>
						<td>${prosjektRapportData.treningsTidFormatert}</td>						
						<td>${prosjektRapportData.kursTidFormatert}</td>
						<td>${prosjektRapportData.testTidFormatert}</td>
						<td>${prosjektRapportData.arbeidsLedelseTidFormatert}</td>
						<td>${prosjektRapportData.annetTidFormatert}</td>						
						<td>${prosjektRapportData.antallKm}</td>						
						<td>${prosjektRapportData.reiseUtgifter}</td>
						<td>${prosjektRapportData.andreUtgifter}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>
</body>
</html>