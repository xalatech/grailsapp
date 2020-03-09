<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main2" />
<title>Capi administrasjon</title>
</head>
<body>
<div class="body">

<h1>CAPI tilordning</h1>

		<g:if test="${flash.message}">
            <div class="message">&nbsp;${flash.message}</div>
        </g:if>
		<g:if test="${flash.errorMessage}">
            <div class="errors">&nbsp;${flash.errorMessage}</div>
        </g:if>
		<br />
		
	<g:link action="listKlynger">&lt;&lt; Tilbake til søk</g:link>
	
	<br/>
	<br/>
	
	<table id="oppsummering" style="background-color: #eeeeee;">
		<tbody>
			<tr>
				<td>
					Prosjekt: ${prosjekt}
				</td>
				<td>
					Skjema: ${skjema?.skjemaNavn} (${skjema?.delProduktNummer})
				</td>
				<td>
					Periodenummer: ${periode}
				</td>
				<td>
					Antall IO: ${intervjuObjektInstanceTotal}
				</td>
				<td>
					Klynge: ${klynge}
				</td>
				<td>
					Intervjuergruppe: ${intervjuerGruppe}
				</td>
			</tr>
		</tbody>
	</table>

	<br/>
	
	<g:render template="/templates/capiTildelAutomatiskSkjemaTemplate" model="[maxIO: maxIO, sisteFrist: sisteFrist]" />

	<br/>

	<div class="list">
		<table>
			<thead>
				<tr>
					<g:sortableColumn property="intervjuObjektNummer" title="${message(code: 'intervjuObjekt.intervjuObjektNummer.label', default: 'IO-nr')}" />

					<th><g:message code="intervjuObjekt.navn.label" default="Navn" /></th>

					<th><g:message code="intervjuObjekt.gateAdresse.label" default="Adresse" /></th>
			
					<th><g:message code="intervjuObjekt.poststed.label" default="Poststed" /></th>
					
					<th><g:message code="intervjuObjekt.kommune.label" default="Kommunenr." /></th>
					
					<th><g:message code="intervjuObjekt.intervjuer.label" default="Ønsket intervjuer" /></th>

					<th><g:message code="intervjuObjekt.intervjuer.label" default="Tildelt intervjuer" /></th>
									
					<th><g:message code="sivadm.intervjuobjekt.tildelt.historikk" default="Tildelt historikk" /></th>
					
					<th><g:message code="sivadm.intervjuobjekt.siste.frist.label" default="Siste frist" /></th>
					
					<th />
				</tr>
			</thead>
			<tbody>
				<g:each in="${intervjuObjektCapiList}" status="i" var="intervjuObjektCapiInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">						
						<td style="width: 40px; text-align: right;">
							${fieldValue(bean: intervjuObjektCapiInstance, field: "intervjuObjektNummer")}
						</td>

						<td>
							<g:link controller="intervjuObjekt" action="edit" id="${intervjuObjektCapiInstance.intervjuObjektId}">${fieldValue(bean: intervjuObjektCapiInstance, field: "intervjuObjektNavn")}</g:link>
						</td>
						
						<td>
							${intervjuObjektCapiInstance?.adresse}
						</td>
				
						<td style="text-align: left;  width: 150px">
							<nobr>${intervjuObjektCapiInstance?.postNummer}&nbsp;${intervjuObjektCapiInstance?.postSted}</nobr>
						</td>

						<td style="width: 80px; text-align: right;">
							${intervjuObjektCapiInstance?.kommunenummer}
						</td>

						<td style="width: 105px">
							${fieldValue(bean: intervjuObjektCapiInstance, field: "onsketIntervjuer")}
						</td>

						<td>
							<g:if test="${intervjuObjektCapiInstance?.oppdragId}">
								<g:link controller="oppdrag" action="show" id="${intervjuObjektCapiInstance?.oppdragId}">${fieldValue(bean: intervjuObjektCapiInstance, field: "tildeltIntervjuer")}</g:link>
							</g:if>
							<g:elseif test="${intervjuObjektCapiInstance?.oppdragId == 0L}">
								<g:link controller="oppdrag" action="show" id="0">${fieldValue(bean: intervjuObjektCapiInstance, field: "tildeltIntervjuer")}</g:link>
							</g:elseif>
						</td>
						
						<td style="width: 160px">
							<g:if test="${intervjuObjektCapiInstance.tidligereIntervjuer1}">
								<li>${intervjuObjektCapiInstance.tidligereIntervjuer1}</li>
								<g:if test="${intervjuObjektCapiInstance.tidligereIntervjuer2}">
									<li>${intervjuObjektCapiInstance.tidligereIntervjuer2}</li>
									<g:if test="${intervjuObjektCapiInstance.tidligereIntervjuer3}">
										<li>${intervjuObjektCapiInstance.tidligereIntervjuer3}</li>
									</g:if>
								</g:if>
							</g:if>
						</td>
						
						<td style="width: 60px">
							<g:formatDate date="${intervjuObjektCapiInstance.sisteFrist}" format="dd.MM.yyyy" />
						</td>
							
						<td>
							<g:link action="tildel" id="${intervjuObjektCapiInstance.intervjuObjektId}">Tildel</g:link>
						</td>

					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
			
	<div class="paginateButtons">
		<g:paginate total="${intervjuObjektInstanceTotal}" />
	</div>

	<g:if test="${intervjuObjektCapiList?.size() > 20}" >
		<g:render template="/templates/capiTildelAutomatiskSkjemaTemplate" model="[maxIO: maxIO, sisteFrist: sisteFrist]" />
	</g:if>
</div>
</body>
</html>