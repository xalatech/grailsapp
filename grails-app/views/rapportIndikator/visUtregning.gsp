<%@ page contentType="text/html;charset=ISO-8859-1" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main2"/>
<title>Indikatorutregning</title>
</head>
<body>
  <div class="body">
  	<h1>Indikator utregning</h1>
  	
  	<br>
  	
  	<div class="list">
  		<table>
  			<tr>
  				<th>Felt</th>
  				<th>Formel</th>
  				<th>Detaljer</th>
  			</tr>
  			
  			<tr>
  				<td>Bruttoutvalg</td>
  				<td>Antall intervjuobjekter for skjema (alle perioder) - antall avganger</td>
  				<td></td>
  			</tr>
  			
  			<tr>
  				<td>Antall intervju</td>
  				<td></td>
  				<td>Skjemastatus = FERDIG & IOStatus = 0</td>
  			</tr>
  			
  			<tr>
  				<td>Bruttosvar</td>
  				<td>Antall intervju / Bruttoutvalg</td>
  				<td></td>
  			</tr>
  			
  			<tr>
  				<td>Antall frafall</td>
  				<td></td>
  				<td>(Skjemastatus = FERDIG eller UBEHANDLET) & IOStatus = 11..41</td>
  			</tr>
  			
  			<tr>
  				<td>Nettosvar</td>
  				<td>Antall intervju / Antall statuser</td>
  				<td></td>
  			</tr>
  			
  			<tr>
  				<td>Antall nektere</td>
  				<td></td>
  				<td>(Skjemastatus = FERDIG eller UBEHANDLET) & IOStatus = 11..19</td>
  			</tr>
  			
  			<tr>
  				<td>Antall forhindret</td>
  				<td></td>
  				<td>(Skjemastatus = FERDIG eller UBEHANDLET) & IOStatus = 21..32</td>
  			</tr>

  			<tr>
  				<td>Kontaktrate</td>
  				<td>(Antall intervju + Antall nektere og Antall forhindret) / Bruttoutvalg</td>
  				<td></td>
  			</tr>
  			
  			<tr>
  				<td>Rekrutteringsrate</td>
  				<td>Antall intervju / (Antall intervju + Antall nektere + Antall forhindret)</td>
  				<td></td>
  			</tr>
  			
  			<tr>
  				<td>Andel ferdigstilt</td>
  				<td>(Antall intervju + Antall frafall) / Bruttoutvalg</td>
  				<td></td>
  			</tr>
  			
  			
  		</table>
  	</div>
  	
  </div>
</body>
</html>