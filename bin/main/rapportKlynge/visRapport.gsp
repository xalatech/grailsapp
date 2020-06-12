<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.js')}"></script>
	<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.pie.js')}"></script>
	<meta name="layout" content="main" />
	<title>Resultatrapport klynger</title>
	<g:javascript src="rapportIntervjuer.js"/>
	<g:javascript>
		function skjulAlleRaderMedTotalLikNull() {
			var tabell = document.getElementById("tabell1");
			var antallRader = tabell.rows.length;

			for(var i=0; i<antallRader; i++) {
				var rad=tabell.rows[i];
				// var antallCellerDenneRad = rad.cells.length;
				var sisteTall=rad.cells[3].innerHTML;
				
				if( sisteTall == 0 ){
					rad.style.display = 'none';
				}else {
					rad.style.display = '';
				}
			}
			
			var sisteRad=tabell.rows[antallRader-1];
			sisteRad.style.display = '';
			
			document.getElementById("skjulKnapp").style.display = 'none';
			document.getElementById("visKnapp").style.display = '';
		}
		
		function visAlleRaderMedTotalLikNull() {
			var tabell = document.getElementById("tabell1");
			var antallRader = tabell.rows.length;
		
			for(var i=0; i<antallRader; i++) {
				tabell.rows[i].style.display = '';
			}
			
			document.getElementById("skjulKnapp").style.display = '';
			document.getElementById("visKnapp").style.display = 'none';

		}
	</g:javascript>	
</head>

<body>
	<div class="body">

		<h1>Resultatrapport klynger</h1>
		
		<p>Datagrunnlaget for denne rapporten baserer seg på alle oppdateringer som kommer fra intervjusystemet (Blaise). Denne rapporten viser mer
		enn bare sanntidsbildet.</p>		
		
		<br/>
			
		<g:form action="visRapport">
			Velg startdato: 	
			<g:datoVelger id="dp-1" 
						  name="startDato" 
						  value="${startDato}"/>
			
			Velg sluttdato:
			<g:datoVelger id="dp-2" 
						  name="sluttDato" 
						  value="${sluttDato}"/>

			Velg intervjuer (initialer):
			<g:textField id="initialer"
						 name="initialer"
						 value="${initialer}"
						 size="5"/>

			Velg klynge:
			<g:select id="klyngeVelger" 
					  from="${klyngeList}" 
					  name="klynge" 
					  value="${klyngeId==null || klyngeId.equals('') ? '' : klyngeId.toInteger()}" 
					  optionKey="id" 
					  noSelection="['' : '-- Velg klynge --']"/>
			
			Velg skjema:
			<g:select id="skjemaVelger" 
					  from="${skjemaList}" 
					  name="skjema" 
					  value="${skjemaId==null || skjemaId.equals('') ? '' : skjemaId.toInteger()}" 
					  optionKey="id" 
					  noSelection="['' : '-- Velg skjema --']"/>
			
			<div class="buttonStyle">
			<%--
				<g:submitButton name="Vis rapport" onchange="visSkjulKnapp()"/>
			 --%>	
				<g:submitButton name="Vis rapport"/>
			</div>
			
		</g:form>
		
		<br/>
		
		<div class="buttonStyle"> 
			<g:submitButton id="skjulKnapp" onclick="skjulAlleRaderMedTotalLikNull()" name="Skjul rader med total=0" style="display:none"/>
			<g:submitButton id="visKnapp" onclick="visAlleRaderMedTotalLikNull()" name="Vis rader med total=0" style="display:none"/>
		</div>
		
		<br/>
		<br/>
		
		<div class="list">
		
			<table name="resultatTabell" id="tabell1">
				<tr>
					<th>Klyngenavn</th>
					<th>Skjema</th>
					<th>Total</th>
					<th>Intervju</th>
					<th>Nektere</th>
					<th>Forhindret</th>
					<th>Ikke truffet</th>
					<th>Andre årsaker</th>
					<th>Avganger</th>
					<th>Overføringer</th>
				</tr>
				
				<g:each in="${klyngeResultatList}" status="i" var="klyngeResultat">
					<tr id="rad" class="${klyngeResultat.klyngenavn == 'Summer' ? 'sum2' : (klyngeResultat.totaltAntall > 0 ? 'greencolor' : 'odd')}">
						<td>${klyngeResultat.klyngenavn}</td>
						<td>${klyngeResultat.skjemaNavn}</td>
						<td>${klyngeResultat.totaltAntall}</td>
						<td>${klyngeResultat.antallIntervju}</td>
						<td>${klyngeResultat.antallNektere}</td>
						<td>${klyngeResultat.antallForhindret}</td>
						<td>${klyngeResultat.antallIkkeTruffet}</td>
						<td>${klyngeResultat.antallAndreAarsaker}</td>
						<td>${klyngeResultat.antallAvganger}</td>
						<td>${klyngeResultat.antallOverforinger}</td>
					</tr>
				</g:each>	
			</table>
		</div>
	</div>
	<script type="text/javascript">
			var tabell = document.getElementById("tabell1");
			var antallRader = tabell.rows.length;
		
			var teller=0;
			
			if (antallRader>0) {
				for(var i=0; i<antallRader; i++) {
					if (tabell.rows[i].cells[2].innerHTML == 0){
						teller++;
					}
				}
			}
			
			if (antallRader>0 && teller>0){
				document.getElementById("skjulKnapp").style.display = '';
			}else{
				document.getElementById("skjulKnapp").style.display = 'none';
			}	
	</script>	
</body>
</html>