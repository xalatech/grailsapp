<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Sil ukedetaljer rapport</title>
</head>
<body>
	<div class="body">


		<h1>Detaljert tidsrapport</h1>
		
		<br>
		
		<div class="dialog">
			<g:form action="visRapport">
	           	<table>
	           		<tr class="prop">
                        <td valign="top" class="name">
                        	<g:message code="sil.detaljert.tidsrapport.startdato" />
                        </td>
                        <td valign="top">
                            <g:datoVelger id="dp-1" name="startDato" value="${startDato}"/>
                        </td>
                        <td>
	                   		<g:message code="sil.detaljert.tidsrapport.sluttdato" />
	                   	</td>
	                   	<td>
	                       	<g:datoVelger id="dp-2" name="sluttDato" value="${sluttDato}"/>
						</td>
	                   </tr>
	                   
	           		<tr class="prop">
	                    <td>
	                   		<g:message code="sil.detaljert.tidsrapport.initialer" />
	                   	</td>
	                   	<td>
	                       	<g:textField id="initialer" name="initialer" value="${initialer}" size="5"/>
						</td>
	                       <td>
	                   		<g:message code="sil.detaljert.tidsrapport.klynge" />
	                   	</td>
	                   	<td>
	                       	<g:select id="klyngeVelger" from="${klyngeList}" name="klynge" value="${klyngeId}" optionKey="id" noSelection="['':'-- Velg klynge --']"/>
						</td>
	                   </tr>
	                   
	           		<tr class="prop">
	                    <td>
	                   		<g:message code="sil.detaljert.tidsrapport.intervjuerstatus" />
	                   	</td>
	                   	<td>
	                       	<g:select name="intervjuerStatus" optionKey="key" optionValue="guiName" from="${intervjuerStatusList}" value="${status?.key}" noSelection="['':'-- Velg intervjuerstatus --']"/>
						</td>
	                       <td>
	                   		<g:message code="sil.detaljert.tidsrapport.delproduktnummer" />
	                   	</td>
	                   	<td>
	                       	<g:textField id="delProduktNummer" name="delProduktNummer" value="${delProduktNummer}" size="5"/>
						</td>
	                   </tr>
	           	</table>
				<div class="buttonStyle">
					<g:submitButton name="Vis rapport"/>
				</div>
			</g:form>
		</div>
		<br/>
		<br/>

		<div class="list">
			<table>
				<tr>
					<g:sortableColumn property="intervjuerNummer" title="Intervjuernummer"/>
					<g:sortableColumn property="navn" title="Navn"/>
					<g:sortableColumn property="initialer" title="Initialer"/>
					<g:sortableColumn property="skjemaNavn" title="Skjemanavn"/>
					<g:sortableColumn property="delProduktNummer" title="Arbeidsordrenummer"/>
					<g:sortableColumn property="arbeidsDato" title="Arbeidsdato"/>
					<g:sortableColumn property="arbeidsTid" title="Arbeidstid"/>
					<g:sortableColumn property="reiseTid" title="Reisetid"/>
					<g:sortableColumn property="ovelseTid" title="Øvelsetid"/>
					<g:sortableColumn property="totalTid" title="Totaltid"/>
				</tr>

				<g:each in="${detaljertTidsrapportDataList}" var="detaljertTidsrapportData" status="i">
					<tr class="${detaljertTidsrapportData.delProduktNummer == 'Summer' ? 'sum2' : ((i % 2) == 0 ? 'odd' : 'even')}"  >
					
						<td>${detaljertTidsrapportData.intervjuerNummer}</td>
						
						<td>${detaljertTidsrapportData.navn}</td>
						
						<td>${detaljertTidsrapportData.initialer}</td>
						
						<td>${detaljertTidsrapportData.skjemaNavn}</td>
						
						<td>${detaljertTidsrapportData.delProduktNummer}</td>
						
						<td><g:formatDate date="${detaljertTidsrapportData.arbeidsDato}" format="dd-MM-yyyy HH:mm"/></td>
						
						<td>${detaljertTidsrapportData.arbeidsTidFormatert}</td>
						
						<td>${detaljertTidsrapportData.reiseTidFormatert}</td>
						
						<td>${detaljertTidsrapportData.ovelseTidFormatert}</td>
						
						<td>${detaljertTidsrapportData.totalTidFormatert}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>
</body>
</html>