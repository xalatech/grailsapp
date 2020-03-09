<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main2" />
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
                           Velg startdato:
                         </td>
                         <td valign="top">
                             <g:datoVelger id="dp-1" name="startDato" value="${startDato}"/>
                         </td>
                         <td>
                    		Velg sluttdato:
                    	</td>
                    	<td>
                        	<g:datoVelger id="dp-2" name="sluttDato" value="${sluttDato}"/>
						</td>
                    </tr>
                    
            		<tr class="prop">
                        <td>
                    		Velg intervjuer (initialer):
                    	</td>
                    	<td>
                        	<g:textField id="initialer" name="initialer" value="${initialer}" size="5"/>
						</td>
                        <td>
                    		Velg klynge:
                    	</td>
                    	<td>
                        	<g:select id="klyngeVelger" from="${klyngeList}" name="klynge" value="${klyngeId}" optionKey="id" noSelection="['':'-- Velg klynge --']"/>
						</td>
                    </tr>
                    
            		<tr class="prop">
                        <td>
                    		Velg intervjuerstatus:
                    	</td>
                    	<td>
                        	<g:select name="intervjuerStatus" optionKey="key" optionValue="guiName" from="${intervjuerStatusList}" value="${status?.key}" noSelection="['':'-- Velg intervjuerstatus --']"/>
						</td>
                        <td>
                    		Velg arbeidsordrenummer:
                    	</td>
                    	<td>
                        	<g:textField id="delProduktNummer" name="delProduktNummer" value="${delProduktNummer}" size="5"/>
						</td>
                    </tr>
                    
                     
            	</table>






			
		
		<%-- 


			Velg startdato: 	
			<g:datoVelger id="dp-1" name="startDato" value="${startDato}"/>
			
			Velg sluttdato:
			<g:datoVelger id="dp-2" name="sluttDato" value="${sluttDato}"/>
			
			Velg intervjuer (initialer):
			<g:textField id="initialer" name="initialer" value="${initialer}" size="5"/>
			
			Velg intervjuerstatus:
      		<g:select name="intervjuerStatus" optionKey="key" optionValue="guiName" from="${intervjuerStatusList}" value="${status?.key}" noSelection="['':'-- Velg intervjuerstatus --']"/>

			Velg klynge:
			<g:select id="klyngeVelger" from="${klyngeList}" name="klynge" value="${klyngeId}" optionKey="id" noSelection="['':'-- Velg klynge --']"/>

			Velg delproduktnummer:
			<g:textField id="delProduktNummer" name="delProduktNummer" value="${delProduktNummer}" size="5"/>
		--%>
		
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

				<g:each in="${ukeRapportDetaljerDataList}" var="ukeRapportDetaljerData" status="i">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					
						<td>${ukeRapportDetaljerData.intervjuerNummer}</td>
						
						<td>${ukeRapportDetaljerData.navn}</td>
						
						<td>${ukeRapportDetaljerData.initialer}</td>
						
						<td>${ukeRapportDetaljerData.skjemaNavn}</td>
						
						<td>${ukeRapportDetaljerData.delProduktNummer}</td>
						
						<td><g:formatDate date="${ukeRapportDetaljerData.arbeidsDato}" format="dd-MM-yyyy HH:mm"/></td>
						
						<td>${ukeRapportDetaljerData.arbeidsTidFormatert}</td>
						
						<td>${ukeRapportDetaljerData.reiseTidFormatert}</td>
						
						<td>${ukeRapportDetaljerData.ovelseTidFormatert}</td>
						
						<td>${ukeRapportDetaljerData.totalTidFormatert}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>
</body>
</html>