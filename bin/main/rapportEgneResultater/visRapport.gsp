<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.js')}"></script>
	<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.pie.js')}"></script>
	<meta name="layout" content="main" />
	<title>Rapport for egne resultater</title>
	<g:javascript src="rapportIntervjuer.js"/>
</head>

<body>
	<div class="body">

		<h1>Rapport for egne resultater</h1>
		
		<p>Datagrunnlaget for denne rapporten baserer seg p� alle oppdateringer som kommer fra intervjusystemet (Blaise). Denne rapporten viser mer 
		enn bare sanntidsbildet.</p>		
		
		<br/>
			
		<g:form action="visRapport">
			Velg startdato: 	
			<g:datoVelger id="dp-1" name="startDato" value="${startDato}"/>
			
			Velg sluttdato:
			<g:datoVelger id="dp-2" name="sluttDato" value="${sluttDato}"/>
			
			Velg klynge:
			
			<g:select id="klyngeVelger" from="${klyngeMap}" name="klynge" optionKey="key" optionValue="value" value="${klyngeId}"
					noSelection="['':'-- Velg klynge --']"/>
			
			Velg skjema:
			<g:select id="skjemaVelger" from="${skjemaList}" name="skjema" value="${skjemaId}" optionKey="id" noSelection="['':'-- Velg skjema --']"/>
			
			<div class="buttonStyle">
				<g:submitButton name="Vis rapport"/>
			</div>
			
		</g:form>
		
		<br/>
		
		<div class="list">
		
		<h3>Navn      : ${navn}</h3>
		<h3>Initialer : ${initialer}</h3>
		
		<br/>
		
			<table>
				<tr>
					<th>Total</th>
					<th>Intervju</th>
					<th>Nektere</th>
					<th>Forhindret</th>
					<th>Ikke truffet</th>
					<th>Andre �rsaker</th>
					<th>Avganger</th>
					<th>Overf�ringer</th>
				</tr>
				<g:if test="${intervjuerResultat != null}">
					<tr class="${intervjuerResultat.navn == 'Summer' ? 'sum2' : (intervjuerResultat.totaltAntall > 0 ? 'greencolor' : 'odd')}">
						<td>${intervjuerResultat.totaltAntall}</td>
						<td>${intervjuerResultat.antallIntervju}</td>
						<td>${intervjuerResultat.antallNektere}</td>
						<td>${intervjuerResultat.antallForhindret}</td>
						<td>${intervjuerResultat.antallIkkeTruffet}</td>
						<td>${intervjuerResultat.antallAndreAarsaker}</td>
						<td>${intervjuerResultat.antallAvganger}</td>
						<td>${intervjuerResultat.antallOverforinger}</td>
					</tr>
				</g:if>
			</table>
		</div>
	</div>
</body>
</html>