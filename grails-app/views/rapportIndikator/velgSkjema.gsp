<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name="layout" content="main2" />
		<title>Indikator rapport</title>
	</head>
	<body>
		<g:form method="post" action="visRapport">
			<div class="body">
				<h1>Velg skjema for indikatorrapport</h1>
				<br><br>
				<h4>Planlagt sluttdato - Arbeidsordrenummer - Skjemanavn</h4>
				<br>
				<g:select name="skjemaListe"
						  size="30"
						  style="width:600px"
						  from="${skjemaListe}"
						  optionKey="skjemaId"
						  optionValue="listeTekst"
						  multiple="true"/>
				<br><br>
				<div class="buttonStyle">
					<g:actionSubmit value="Vis indikatorrapport" action="visRapport"/>
				</div>
			</div>
		</g:form>
	</body>
</html>