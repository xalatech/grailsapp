<%@ page import="sivadm.VelgTidligereIntervjustatusCommand" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.js')}"></script>
		<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.pie.js')}"></script>
		<meta name="layout" content="main" />

		<title>Statusrapport</title>
	</head>
	<body>
		<g:form name="theform" action="visRapport" method="post">
	    <div class="nav">
			<span class="menuButton">
				<g:if test="${params.intervjuer == 'true'}">
					<span class="menuButton"><g:link controller="intervjuerRapport" action="listSkjema">Tilbake til skjemaliste</g:link></span>
                </g:if>
				<g:else>
					<span class="menuButton"><g:link controller="skjema" action="list">Tilbake til skjemaliste</g:link></span>
				</g:else>
			</span>
        </div>

		<div class="body">
			<h1>Statusrapport for ${skjema.skjemaNavn} (${skjema.skjemaKortNavn})</h1>

			<br>

			<h2>Vis statusrapport for</h2>

			<br>

			<g:radio name="intervjustatuskoder" value="alle" checked="true"/> alle intervjuobjekter

			<br>

			<g:radio name="intervjustatuskoder" value="intervall"/> intervjuobjekter med tidligere intervjustatuskode (f.o.m. - t.o.m.):
			<g:textField name="intervjuStatusFra" id="intervjuStatusFra" value="${velgTidligereIntervjustatusCommand.intervjuStatusFra}" maxlength="3" size="2" /> -
			<g:textField name="intervjuStatusTil" id="intervjuStatusTil" value="${velgTidligereIntervjustatusCommand.intervjuStatusTil}" maxlength="3" size="2" />

			<br>
			<br>
			<br>

			<g:hiddenField name="id" value="${skjema.id}" />
			<div class="buttonStyle" >
				<g:actionSubmit value="Vis rapport" action="visRapport" />
			</div>

		</div>
		</g:form>
	</body>
</html>