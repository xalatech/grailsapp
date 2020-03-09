<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <title><g:message code="rapport.intervjuer.lonn" default="Arbeidstidrapport" /></title>
    </head>
    <body>
        <div class="body">
            <h1>Arbeidstid-rapport</h1>
            <br>
           	<g:message code="rapport.intervjuer.lonn.beskrivelse" /><br>
           	(Obs. tar også med timeføringer som er avvist i kontroll. Oversikt over avviste timeføringer finner du <g:link controller="timeforing" action="visAvviste">her</g:link>)
           	<br><br>
           	<g:if test="${flash.message}">
            	<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            <g:if test="${flash.erroMessage}">
            	<div class="errors">&nbsp;${flash.errorMessage}</div>
            </g:if>
            
			<g:form action="arbeidstidRapport" method="post">
				<table style="width: 300px;">
					<tr class="prop">
						<td valign="top" class="name">
							<label for="fra"><g:message code="rapport.intervjuer.fra" default="Fra:" /></label>
						</td>
						<td valign="top">
							<g:datoVelger id="dp-1" name="fra" value="${arbeidstidRapportCommand?.fra}" />
						</td>
						<td>
							<label for="til"><g:message code="rapport.intervjuer.fra" default="Til:" /></label>
						</td>
						<td>
							<g:datoVelger id="dp-2" name="til" value="${arbeidstidRapportCommand?.til}" />
						</td>
					</tr>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="produktNummer"><g:message code="rapport.intervjuer.prosjekt" default="Prosjekt:" /></label>
						</td>
						<td valign="top">
							<g:select name="produktNummer"
								from="${produktNummerListe}"
								optionKey="produktNummer"
								value="${arbeidstidRapportCommand?.produktNummer}"
								noSelection="['': '----- Alle produktnummer -----']" />
						</td>
						<td>
							<label for="arbeidstype"><g:message code="rapport.intervjuer.arbeidstype" default="Arbeidstype:" /></label>
						</td>
						<td>
							<g:select name="arbeidstype"
								from="${arbTypeListe}" 
								optionKey="key"
								optionValue="guiName"
								noSelection="['':'---------- Alle arbeidstyper ----------']"
								value="${arbeidstidRapportCommand?.arbeidstype}" />
						</td>
					</tr>
				</table>
				<g:submitButton name="sok" value="Søk"/>
            	<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="nullstillSok" />
			</g:form>
			<br><br>
			<g:if test="${arbeidDatoListe}">
				<h2><g:message code="rapport.intervjuer.tid.dag" default="Tid brukt pr. dag" /></h2>
				<div class="list">
  					<table>
                    	<thead>
                        	<tr>
                        		<th>Dato</th>
                        		<th>Tid</th>
                        		<th>Antall Kilometer</th>
                        		<th>Kroner (utlegg)</th>
                        	</tr>
                        </thead>
                        <tbody>
							<g:each in="${arbeidDatoListe}" status="i" var="datoInstance">
		                    	<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		                    		<td>
		                    			<g:link controller="timeforing" action="listTotal" params="${[valgtDato: datoInstance.dato.format('yyyy.MM.dd')]}"><g:formatDate date="${datoInstance.dato}" format="dd.MM.yyyy" /></g:link>
		                    		</td>
		                    		<td>
										${datoInstance.timer} timer og ${datoInstance.minutter} minutter
		                    		</td>
		                    		<td>
		                    			${datoInstance.antKm}
		                    		</td>
		                    		<td>
		                    			<g:formatNumber number="${datoInstance.belop}" format="0.00" /> <g:message code="sil.kr" default="kr" />
		                    		</td>
		                    	</tr>
							</g:each>
						</tbody>
  					</table>
  				</div>
			</g:if>
			<br><br>
			<g:if test="${arbeidProsjektListe}">
				<h2><g:message code="rapport.intervjuer.tid.prosjekt" default="Tid brukt pr. prosjekt" /></h2>
				<div class="list">
  					<table>
                    	<thead>
                        	<tr>
                        		<th>Prosjekt</th>
                        		<th>Tid</th>
                        		<th>Antall Kilometer</th>
                        		<th>Kroner (utlegg)</th>
                        	</tr>
                        </thead>
                        <tbody>
							<g:each in="${arbeidProsjektListe}" status="i" var="prodInstance">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		                    		<td>
		                    			${prodInstance.produktNummer}
		                    			<g:if test="${prodInstance.navn}">&nbsp;(${prodInstance.navn})</g:if>
		                    		</td>
		                    		<td>
										${prodInstance.timer} timer og ${prodInstance.minutter} minutter
		                    		</td>
		                    		<td>
		                    			${prodInstance.antKm}
		                    		</td>
		                    		<td>
		                    			<g:formatNumber number="${prodInstance.belop}" format="0.00" /> <g:message code="sil.kr" default="kr" />
		                    		</td>
		                    	</tr>
							</g:each>
						</tbody>
  					</table>
  				</div>
			</g:if>
			<br><br>
			<g:if test="${arbeidTypeListe}">
				<h2><g:message code="rapport.intervjuer.tid.type" default="Tid brukt pr. arbeidstype" /></h2>
				<div class="list">
  					<table>
                    	<thead>
                        	<tr>
                        		<th>Arbeidstype</th>
                        		<th>Tid</th>
                        	</tr>
                        </thead>
                        <tbody>
							<g:each in="${arbeidTypeListe}" status="i" var="typeInstance">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		                    		<td>
		                    			${typeInstance.arbeidstype}
		                    		</td>
		                    		<td>
										${typeInstance.timer} timer og ${typeInstance.minutter} minutter
		                    		</td>
		                    	</tr>
							</g:each>
						</tbody>
  					</table>
  				</div>
			</g:if>
        </div>
    </body>
</html>