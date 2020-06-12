<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.pie.js')}"></script>

<meta name="layout" content="main" />

<title>Rapport</title>

</head>
<body>
		<div class="nav">            
            <span class="menuButton">
				<g:if test="${params.intervjuer == 'true'}">
					<span class="menuButton"><g:link controller="intervjuerRapport" action="listSkjema">Tilbake til skjemaliste</g:link></span>
				</g:if>
				<g:else>
					<span class="menuButton"><g:link controller="skjema" action="list">Tilbake til skjemaliste</g:link></span>
					<span class="menuButton"><g:link controller="rapportStatus" action="visRapport" id="${skjema.id}">Vis statusrapport</g:link></span>
				</g:else>
            </span>
        </div>
        
	<div class="body">

		<h1>Fordeling av frafall for ${skjema.skjemaNavn} (${skjema.skjemaKortNavn})</h1>
		
		<g:if test="${periodeList && periodeList.size() > 0}">
		Se kakediagram for periode: 
		</g:if>
		
		<g:each in="${periodeList}" var="periode" status="i">
			<g:if test="${params.intervjuer == 'true'}">
				<g:link action="visRapport" id="${skjema.id}" params="[periode: periode.periodeNummer, intervjuer: 'true']">${periode.periodeNummer}</g:link>
			</g:if>
			<g:else>
				<g:link action="visRapport" id="${skjema.id}" params="[periode: periode.periodeNummer]">${periode.periodeNummer}</g:link>
			</g:else>
			<g:if test="${ i < periodeList.size()-1}">
			, 
			</g:if>
			<g:else>
				<g:if test="${params.intervjuer == 'true'}">
					<g:link action="visRapport" id="${skjema.id}" params="[intervjuer: 'true']">, Alle perioder</g:link>
				</g:if>
				<g:else>
					<g:link action="visRapport" id="${skjema.id}">, Alle perioder</g:link>
				</g:else>
			</g:else>
		</g:each>
		
		<br/>
		
		<br/>
		
		<g:if test="${totaltAntall == 0}">
				<p>Ingen intervju eller frafall funnet for periode ${periodeNummer}.</p>
		</g:if>
		<g:else>
			<br/>
			<g:if test="${periodeNummer}">
			Periode: ${periodeNummer}
			</g:if>
			<br/>
			<br/>
			<table  style="border: none;">
				<tbody>

				<tr class="prop">
					<td valign="top" class="name">
						<b>Innkomne statuser</b>
					</td>
					<td valign="top" class="name">
						<b>Brutto</b>
					</td>
				</tr>
				</tbody>
			</table>
			<br/>
			<br/>
		</g:else>
		
		<br/>
		
		<div id="g1" style="margin-bottom: 30px;  float:left; width: 130px; height: 130px;"></div>

		<div id="g2"
			style="margin-left:20px; border: none; float:left; width: 200px; height: 200px;">
		</div>

		<div id="g3" style="margin-bottom: 30px; margin-left: 45px; float:left; width: 130px; height: 130px;"></div>

		<div id="g4"
			 style="margin-left:20px; margin-bottom: 30px; border: none; float:left; width: 200px; height: 200px;">
		</div>

		<g:if test="${totaltAntall > 0}">
		
		<script type="text/javascript"> 
				jQuery(function () {

					jQuery.noConflict();
							
			    	// data
			    	var data = [
			    		{ label: "Intervju (<g:formatNumber number="${antallIntervju*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallIntervju}},
			    		{ label: "Nektere (<g:formatNumber number="${antallNektere*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallNektere}},
			    		{ label: "Forhindret (<g:formatNumber number="${antallForhindret*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallForhindret}},
			    		{ label: "Ikke truffet (<g:formatNumber number="${antallIkkeTruffet*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallIkkeTruffet}},
			    		{ label: "Andre årsaker (<g:formatNumber number="${antallAndreAarsaker*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallAndreAarsaker}},
			    		{ label: "Avganger (<g:formatNumber number="${antallAvganger*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallAvganger}},
			    		{ label: "Overføringer (<g:formatNumber number="${antallOverganger*100/totaltAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallOverganger}}
			    	];

			    	var options = 
				    {
			    		series: 
				    	{
			    			pie: { 
			   					show: true
			    			}   					
			   			}
		   				,
		    			legend: 
			    		{
		    				show: true,
		    				container: jQuery("#g2")
		    			}
			    	};

			    	jQuery.plot(jQuery("#g1"), data, options);

					// data
					var dataBrutto = [
						{ label: "Intervju (<g:formatNumber number="${antallIntervju*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallIntervju}},
						{ label: "Nektere (<g:formatNumber number="${antallNektere*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallNektere}},
						{ label: "Forhindret (<g:formatNumber number="${antallForhindret*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallForhindret}},
						{ label: "Ikke truffet (<g:formatNumber number="${antallIkkeTruffet*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallIkkeTruffet}},
						{ label: "Andre årsaker (<g:formatNumber number="${antallAndreAarsaker*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallAndreAarsaker}},
						{ label: "Avganger (<g:formatNumber number="${antallAvganger*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallAvganger}},
						{ label: "Overføringer (<g:formatNumber number="${antallOverganger*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)",  data: ${antallOverganger}},
						{ label: "Ingen status (<g:formatNumber number="${(bruttoAntall-totaltAntall)*100/bruttoAntall}" maxFractionDigits="1"></g:formatNumber> %)", color: '#EFEFEF',  data: ${(bruttoAntall-totaltAntall)}}

					];

					var optionsBrutto =
					{
						series:
						{
							pie: {
								show: true
							}
						}
						,
						legend:
						{
							show: true,
							container: jQuery("#g4")
						}
					};

					jQuery.plot(jQuery("#g3"), dataBrutto, optionsBrutto);

				});
	  	 </script>
		
		<div class="list" style="clear: both;">
		
			<table>
				<tr>
					<th>Periodenummer</th>
					<th>Brutto</th>
					<th>Intervju</th>
					<th>Nektere</th>
					<th>Forhindret</th>
					<th>Ikke truffet</th>
					<th>Andre årsaker</th>
					<th>Avganger</th>
					<th>Overføringer</th>
					<th>Total</th>
				</tr>
				
				<g:each in="${periodeRapportList}" status="i" var="periode">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>${periode.periodeNummer}</td>
						<td>${periode.bruttoAntall}</td>
						<td>${periode.antallIntervju}</td>
						<td>${periode.antallNektere}</td>
						<td>${periode.antallForhindret}</td>
						<td>${periode.antallIkkeTruffet}</td>
						<td>${periode.antallAndreAarsaker}</td>
						<td>${periode.antallAvganger}</td>
						<td>${periode.antallOverganger}</td>
						<td>${periode.totaltAntall}</td>
					</tr>
				</g:each>
				
				<tr class="sum">
					<td>${periodeSumRapport.periodeNummer}</td>
					<td>${periodeSumRapport.bruttoAntall}</td>
					<td>${periodeSumRapport.antallIntervju}</td>
					<td>${periodeSumRapport.antallNektere}</td>
					<td>${periodeSumRapport.antallForhindret}</td>
					<td>${periodeSumRapport.antallIkkeTruffet}</td>
					<td>${periodeSumRapport.antallAndreAarsaker}</td>
					<td>${periodeSumRapport.antallAvganger}</td>
					<td>${periodeSumRapport.antallOverganger}</td>
					<td>${periodeSumRapport.totaltAntall}</td>
				</tr>
				
			</table>
		</div>

			<br/>
			<br/>
			<br/>

			<div class="list" style="width: 350px; float: left; margin-right: 30px;">

				<table>
					<tr>
						<th>Periodenummer</th>
						<th>Intervju</th>
						<th>CAPI</th>
						<th>CATI</th>
						<th>WEB</th>
					</tr>

					<g:each in="${periodeRapportMedKildeList}" status="i" var="periode">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td>${periode.periodeNummer}</td>
							<td>${periode.antallIntervju}</td>
							<td>${periode.antallCAPI}</td>
							<td>${periode.antallCATI}</td>
							<td>${periode.antallWEB}</td>
						</tr>
					</g:each>

					<tr class="sum">
						<td>${periodeSumRapportMedKilde.periodeNummer}</td>
						<td>${periodeSumRapportMedKilde.antallIntervju}</td>
						<td>${periodeSumRapportMedKilde.antallCAPI}</td>
						<td>${periodeSumRapportMedKilde.antallCATI}</td>
						<td>${periodeSumRapportMedKilde.antallWEB}</td>
					</tr>

				</table>
			</div>

		</g:if>

	</div>
</body>
</html>