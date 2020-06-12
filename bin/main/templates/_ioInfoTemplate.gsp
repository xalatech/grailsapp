<table id="oppsummering" class="infoBody">
	<tbody>
		<tr>
			<g:if test="${listeAntall && listePosisjon}">
				<td>
					IO ${listePosisjon} <g:message code="sivadm.io.av" default="av" /> ${listeAntall}
				</td>
			</g:if>
			<td>Skjema: ${intervjuObjektInstance.periode?.skjema?.skjemaKortNavn}</td>
			<td>Arbeidsordre nr: ${intervjuObjektInstance.periode?.skjema?.delProduktNummer}</td>
			<td>Periode: ${intervjuObjektInstance.periode?.periodeNummer}</td>
			<td>
				Status: ${intervjuObjektInstance?.katSkjemaStatus}
			</td>
			<td>
					Siste intervjuer: ${sisteIntervjuer?.navnOgInitialer}
				</td>
		</tr>
		
		<tr>
			
			<td>
				IO-nr: <g:formatNumber number="${intervjuObjektInstance?.intervjuObjektNummer}" format="#"/>
			</td>
			<td>
				${intervjuObjektInstance?.navn}
			</td>
			
			<g:if test="${intervjuObjektInstance?.tilhorerBedriftSkjema()}">
				<td>
					Org.nr b: ${intervjuObjektInstance?.fodselsNummer}
				</td>
			</g:if>
			<g:else>
				<td>
					F.Nr: ${intervjuObjektInstance?.fodselsNummer}
				</td>
			</g:else>
			
			
			<td>
				IO-id: <g:formatNumber number="${intervjuObjektInstance?.id}" format="#"/>
			</td>
			<g:if test="${visAdresse && intervjuObjektInstance.findGjeldendeBesokAdresse()}">
				<td>Adresse: ${intervjuObjektInstance.findGjeldendeBesokAdresse()?.gateAdresse}, ${intervjuObjektInstance.findGjeldendeBesokAdresse()?.postNummer} ${intervjuObjektInstance.findGjeldendeBesokAdresse()?.postSted}</td>
			</g:if>
			<td>
				Kommune: 
				<g:if test="${kommuneInstance}" >
					${kommuneInstance.kommuneNavn} (${kommuneInstance.kommuneNummer})
				</g:if>
			</td>
			
			
			
				
			
			
			<td>
				Sist endret: <g:formatDate date="${intervjuObjektInstance?.redigertDato}" format="dd.MM.yyyy HH:mm:ss" />
				<g:if test="${intervjuObjektInstance?.redigertAv}">&nbsp;(${intervjuObjektInstance?.redigertAv})</g:if>
			</td>
		</tr>
	</tbody>
</table>