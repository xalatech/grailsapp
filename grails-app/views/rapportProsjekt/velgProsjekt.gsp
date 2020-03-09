<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name="layout" content="main" />
		<title>Sil prosjektrapport</title>
	</head>
	<body>
		<g:form method="post" action="visRapport">
			<div class="body">
				<h1>Velg prosjekter for prosjektrapport</h1>
				<br><br>
				Startdato for føring:
				<g:datoVelger id="dp-1" name="periodeStart" value="${periodeStart}"/>
				&nbsp; &nbsp;
				Sluttdato for føring:
				<g:datoVelger id="dp-2" name="periodeSlutt" value="${periodeSlutt}"/>
				<br><br>
				Prosjekt:
				<br><br>
				<g:select name="prosjektListe"
						  size="20"
						  style="width:432px"
						  from="${prosjektListe}"
						  optionKey="prosjektId"
						  optionValue="listeTekst"
						  multiple="true"/>
				<br><br>
				<g:actionSubmit value="Vis rapport" action="visRapport"/>

				</div>
		</g:form>
		

</body>
</html>