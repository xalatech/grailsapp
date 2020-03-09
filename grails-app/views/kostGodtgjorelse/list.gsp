<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main2" />
<title>Rapport - Kostgodtgjørelse</title>
</head>
<body>
	
	<div class="body">
		
		<h1>Liste over godkjente kostgodtgjørelser som manuelt skal legges inn i SAP</h1>
		
		<p>Dette er en liste med krav om kostgodtgjørelse som allerede er godkjent. Listen er sortert etter godkjentdato, med de nyeste øverst. Klikk på den grønne
		haken til høyre når en kostgodtgjørelse er lagt inn i SAP. Linjen vil da bli grønn for å vise at kravet allerede er lagt inn i SAP. Det røde krysset vil svitsje
		tilbake til ubehandlet (hvis man skulle trykke på den grønne haken ved en feil).</p>
		
		<br>
		
		<div class="list">
			
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="id" title="Id" />
						<g:sortableColumn property="navn" title="Navn" />
						<g:sortableColumn property="intervjuerNummer" title="Intervjuer nr" />
						<g:sortableColumn property="klynge" title="Klynge" />
						<g:sortableColumn property="produktNummer" title="Produkt nr" />
						<g:sortableColumn property="kostGodtDato" title="Kostgodtdato" />
						<g:sortableColumn property="finansiering" title="Finansiering" />
						<g:sortableColumn property="fraTid" title="Fra"/>
						<g:sortableColumn property="tilTid" title="Til"/>
						<g:sortableColumn property="tilSted" title="Sted"/>
						<g:sortableColumn property="godkjent" title="Godkjent" />
						<g:sortableColumn property="godkjentAv" title="Godkjent av" />
						<th/>
						<th/>
					</tr>
				</thead>
				<g:each in="${kostGodtgjorelser}" status="i" var="kostGodt">
				
				<g:if test="${kostGodt.behandlet}" >
					<tr class="greencolor">
				</g:if>
				<g:else>
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				</g:else>
					<td>${kostGodt.id}</td>
					<td>${kostGodt.navn}</td>					
					<td>${kostGodt.intervjuerNummer}</td>
					<td>${kostGodt.klynge}</td>
					<td>${kostGodt.produktNummer}</td>					
					<td><g:formatDate date="${kostGodt.kostGodtDato}" format="dd-MM-yyyy"/></td>
					<td>${kostGodt.finansiering}</td>
					<td><g:formatDate date="${kostGodt.fraTid}" format="kk:mm dd-MM-yyyy"/></td>
					<td><g:formatDate date="${kostGodt.tilTid}" format="kk:mm dd-MM-yyyy"/></td>
					<td>${kostGodt.tilSted}</td>
					<td><g:formatDate date="${kostGodt.godkjent}" format="dd-MM-yyyy"/></td>
					<td>${kostGodt.godkjentAv}</td>					
					<td><g:link action="behandle" id="${kostGodt.id}"><g:godkjennIkon/></g:link></td>
					<td><g:link action="ikkeBehandle" id="${kostGodt.id}"><g:slettIkon/></g:link></td>
				</tr>
				</g:each>
			</table>
			
		</div>
		<div class="paginateButtons">
        	<g:paginate total="${totaltAntallKostGodtgjorelser}" max="500" />
        </div>

	</div>
</body>
</html>