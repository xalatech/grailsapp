<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Indikator rapport</title>
</head>
<body>

	<div class="body">

		<h1>Indikatorer</h1>
		
		<br>
		
		<div class="list">

			<table>

				<tr>
					<th>Unders�kelse</th>
					<th>Brutto svar (%)</th>
					<th>Netto svar (%)</th>
					<th>Kontaktrate (%)</th>
					<th>Rekrutteringsrate (%)</th>
					<th>Andel ferdigstilt (%)</th>
				</tr>

				<g:each in="${skjemaIndikatorList}" var="skjemaIndikator" status="i">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>
							${skjemaIndikator.skjemaNavn}
						</td>
						<td><g:formatNumber number="${skjemaIndikator.bruttoSvar}"
								maxFractionDigits="1" />
						</td>
						<td><g:formatNumber number="${skjemaIndikator.nettoSvar}"
								maxFractionDigits="1" />
						</td>
						<td><g:formatNumber number="${skjemaIndikator.kontaktRate}"
								maxFractionDigits="1" />
						</td>
						<td><g:formatNumber
								number="${skjemaIndikator.rekrutteringsRate}"
								maxFractionDigits="1" />
						</td>
						<td><g:formatNumber
								number="${skjemaIndikator.andelFerdigstilt}"
								maxFractionDigits="1" />
						</td>
					</tr>
				</g:each>

			</table>

		<br>
		<g:link action="visUtregning">Vis utregning</g:link>

		</div>
		
	

	</div>
</body>
</html>