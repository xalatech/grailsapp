<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <title><g:message code="rapport.intervjuer.list" default="Intervjuer rapporter" /></title>
    </head>
    <body>
        <div class="body">
            <h1>Intervjuer rapporter</h1>
            <br>
            <g:message code="rapport.intervjuer.info" default="Vennligst velg en av rapportene listet nedenfor" />
            <br><br><br>
            <li>
            	<g:link action="arbeidstidRapport">Arbeidstid-rapport</g:link>
            </li>
            <br>
            <li>
                <g:link controller="RapportKlynge" action="visRapport" params="[ikkeGenerer: 'true']">Resultatrapport klynger</g:link>
            </li>
            <br>
            <li>
                <g:link action="listSkjema">Frafall på skjema/undersøkelse</g:link>
            </li>
        </div>
    </body>
</html>
