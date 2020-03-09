<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.js')}"></script>
<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.flot.pie.js')}"></script>

<meta name="layout" content="main2" />

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
						<span class="menuButton"><g:link controller="rapportStatus" action="velgTidligereIntervjustatus" id="${skjema.id}">Tilbake til valg av intervjustatuskoder</g:link></span>
						<g:if test="${intervjustatuskoder && intervjustatuskoder == 'alle'}">
                        	<span class="menuButton"><g:link controller="rapportFrafall" action="visRapport" id="${skjema.id}">Vis frafallrapport</g:link></span>
						</g:if>
                    </g:else>
                </span>
        </div>
	<div class="body">

		<h1>Statusrapport for ${skjema.skjemaNavn} (${skjema.skjemaKortNavn}) - antall intervjuobjekter
			<g:if test="${intervjustatuskoder && intervjuStatusFra && intervjuStatusTil && intervjustatuskoder == 'intervall'}">
				(med tidligere intervjustatuskode: ${intervjuStatusFra} - ${intervjuStatusTil})
			</g:if>
			: ${totaltAntall}
		</h1>


		<g:if test="${periodeList && periodeList.size() > 0}">
		Se kakediagram for periode: 
		</g:if>
		
		<g:each in="${periodeList}" var="periode" status="i">
			<g:link action="visRapport" id="${skjema.id}" params="[periode: periode.periodeNummer, intervjuer: params.intervjuer]">${periode.periodeNummer}</g:link>
			
			<g:if test="${ i < periodeList.size()-1}">
			, 
			</g:if>
			<g:else>
				<g:link action="visRapport" id="${skjema.id}" params="[intervjuer: params.intervjuer]">, Alle perioder</g:link>
			</g:else>
		</g:each>
		
		<br/>
		
		<br/>
		
		<g:if test="${totaltAntall == 0}">
				<p>Ingen intervjuobjekter funnet for periode ${periodeNummer}. Sjekk om utvalg er lastet inn.</p>
		</g:if>
		<g:else>
			<br/>
			<g:if test="${periodeNummer}">
			Periode: ${periodeNummer}
			</g:if>
			
			<br/>
			<br/>
		</g:else>
		
		<br/>
		
		<div id="g1" style="margin-bottom: 30px;  float:left; width: 130px; height: 130px;"></div>

		<div id="g2" style="margin-left:20px; border: none; float:left; width: 210px; height: 350px;">
		</div>

		<g:if test="${totaltAntall > 0}">
		
		<script type="text/javascript"> 
				jQuery(function () {

					jQuery.noConflict();
							
			    	// data
			    	var data = [
						{ label: "Innlastet (<g:formatNumber number="${antallInnlastet*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallInnlastet}},
						{ label: "Ubehandlet (<g:formatNumber number="${antallUbehandlet*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallUbehandlet}},
						{ label: "Påbegynt (<g:formatNumber number="${antallPabegynt*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallPabegynt}},
						{ label: "På vent (<g:formatNumber number="${antallPaaVent*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallPaaVent}},
						{ label: "Nektere på vent (<g:formatNumber number="${antallNekterePaaVent*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallNekterePaaVent}},
						{ label: "Utsendt CATI (<g:formatNumber number="${antallUtsendtCati*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallUtsendtCati}},
						{ label: "Utsendt CATI web (<g:formatNumber number="${antallUtsendtCatiWeb*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallUtsendtCatiWeb}},
						{ label: "Utsendt CAPI (<g:formatNumber number="${antallUtsendtCapi*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallUtsendtCapi}},
						{ label: "Utsendt web (<g:formatNumber number="${antallUtsendtWeb*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallUtsendtWeb}},
			    		{ label: "Ferdig (intervju) (<g:formatNumber number="${antallFerdigIntervju*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallFerdigIntervju}},
			    		{ label: "Ferdig (frafall) (<g:formatNumber number="${antallFerdigFrafall*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallFerdigFrafall}},
						{ label: "Ferdig (avganger) (<g:formatNumber number="${antallFerdigAvganger*100/totaltAntall}" maxFractionDigits="1" /> %)",  data: ${antallFerdigAvganger}}
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
				});
	  	 </script>
		
		<div class="list" style="clear: both;">
		
			<table>
				<tr>
					<th>Periodenummer</th>
					<th>Total</th>
					<th>Innlastet</th>
					<th>Ubehandlet</th>
					<th>Påbegynt</th>
					<th>På vent</th>
					<th>Nektere på vent</th>
					<th>Utsendt CATI</th>
					<th>Utsendt CATI web</th>
					<th>Utsendt CAPI</th>
					<th>Utsendt web</th>
					<th>Ferdig (intervju)</th>
					<th>Ferdig (frafall)</th>
					<th>Ferdig (avganger)</th>
				</tr>
				
				<g:each in="${periodeRapportList}" status="i" var="periode">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td>${periode.periodeNummer}</td>
						<td>${periode.totaltAntall}</td>
						<td>${periode.antallInnlastet}</td>
						<td>${periode.antallUbehandlet}</td>
						<td>${periode.antallPabegynt}</td>
						<td>${periode.antallPaaVent}</td>
						<td>${periode.antallNekterePaaVent}</td>
						<td>${periode.antallUtsendtCati}</td>
						<td>${periode.antallUtsendteCatiWeb}</td>
						<td>${periode.antallUtsendtCapi}</td>
						<td>${periode.antallUtsendtWeb}</td>
						<td>${periode.antallFerdigIntervju}</td>
						<td>${periode.antallFerdigFrafall}</td>
						<td>${periode.antallFerdigAvganger}</td>
					</tr>
				</g:each>
				
				<tr class="sum">
					<td>${periodeSumRapport.periodeNummer}</td>
					<td>${periodeSumRapport.totaltAntall}</td>
					<td>${periodeSumRapport.antallInnlastet}</td>
					<td>${periodeSumRapport.antallUbehandlet}</td>
					<td>${periodeSumRapport.antallPabegynt}</td>
					<td>${periodeSumRapport.antallPaaVent}</td>
					<td>${periodeSumRapport.antallNekterePaaVent}</td>
					<td>${periodeSumRapport.antallUtsendtCati}</td>
					<td>${periodeSumRapport.antallUtsendteCatiWeb}</td>
					<td>${periodeSumRapport.antallUtsendtCapi}</td>
					<td>${periodeSumRapport.antallUtsendtWeb}</td>
					<td>${periodeSumRapport.antallFerdigIntervju}</td>
					<td>${periodeSumRapport.antallFerdigFrafall}</td>
					<td>${periodeSumRapport.antallFerdigAvganger}</td>
				</tr>
				
			</table>
		</div>
		
		</g:if>

	</div>
</body>
</html>