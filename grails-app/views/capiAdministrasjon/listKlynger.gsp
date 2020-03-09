<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main2" />

<g:javascript library="prototype" />
<g:javascript src="capi_project_ajax_select.js"/>

<title>Capi administrasjon</title>
</head>
<body>
<div class="body">

<h1>Søk intervjuobjekter for CAPI-tildeling</h1>
		
		<g:if test="${flash.message}">
            <div class="message">&nbsp;${flash.message}</div>
            <br/>
        </g:if>
		<g:if test="${flash.errorMessage}">
            <div class="errors">&nbsp;${flash.errorMessage}</div>
            <br/>
        </g:if>
        
		<g:form action="lagreSok" method="post">
			
			<div class="dialog">
			<table>
				<tr class="prop">
					<td class="name">
						Prosjekt
					</td>
					<td class="value">
						<g:select name="prosjekt"
							id="prosjekt"
							from="${prosjektList}"
							optionKey="id"
							optionValue="prosjektNavn" 
							value="${search?.prosjekt}"
							onchange="${remoteFunction(
	            			controller:'capiAdministrasjon', 
	            			action:'ajaxGetSkjemaList', 
	            			params:'\'id=\' + escape(this.value)', 
	            			onComplete:'updateSkjema(e)')}" 
	            		/>
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">
						Skjema
					</td>
					<td class="value">
						<g:select name="skjema"
							id="skjema"
							from="[]"
							optionKey="id"
							optionValue="skjemaNavn"
							value="${search?.skjema}"
							onchange="${remoteFunction(
	            			controller:'capiAdministrasjon', 
	            			action:'ajaxGetPeriodeList', 
	            			params:'\'id=\' + escape(this.value)', 
	            			onComplete:'updatePeriode(e)')}" 
						/>
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">
						Periode
					</td>
					<td class="value">
						<g:select name="periode" id="periode" from="[]" value="${search?.periode}" />
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">IO</td>
					<td class="value">
						<g:set var="verdiTildelt" value="ikkeTildelt" />
						<g:if test="${search?.tildelt}">
							<g:set var="verdiTildelt" value="${search?.tildelt}" />
						</g:if>
						<g:radioGroup name="tildelt" labels="['Alle', 'Tildelt', 'Ikke tildelt']" values="['alle', 'tildelt', 'ikkeTildelt']" value="${verdiTildelt}">
							${it.radio}&nbsp;${it.label}&nbsp;&nbsp;&nbsp;
						</g:radioGroup>
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">
						IO-nummer
					</td>
					<td class="value">
						<g:textField name="intervjuObjektNummer" size="10" value="${search?.intervjuObjektNummer}" />
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">
						IO skjemastatus
					</td>
					<td class="value">
						<g:select name="skjemaStatus" 
								from="${skjemaStatusList}"
								optionKey="key"
								optionValue="guiName"
								value="${search?.skjemaStatus?.key}"
								noSelection="['':'-Velg skjemastatus']" />		
					</td>
				</tr>
				
				
				
				
				<tr class="prop">
					<td class="name">
						IO fra klynge
					</td>
					<td class="value">
						<g:select name="klynge"
							from="${klyngeList}"
							optionKey="id"
							optionValue="klyngeNavn"
							value="${search?.klynge}"
							noSelection="['': 'Alle']"/>					
					</td>
				</tr>
				
				<tr class="prop">
					<td class="name">
						Intervjuergruppe
					</td>
					<td class="value">
						<g:select name="catiGruppe"
							from="${intervjuerGruppeList}"
							optionKey="id"
							optionValue="navn"
							value="${search?.catiGruppe}"
							noSelection="['': '- Velg en hvis du ønsker en spesiell gruppe intervjuere']"/>					
					</td>
				</tr>
				
				
			</table>

			</div>
			
			<div class="buttons">
				<span class="button">
					<g:submitButton name="lagreSok" class="save" value="Finn intervjuobjekter for tildeling" />
					<span class="menuButton"><g:link class="delete" action="nullstillSok"><g:message code="sil.nullstill.felter" default="Nullstill felter" /></g:link></span>
				</span>
			</div>
		</g:form>
		
		<br/>
		
			<g:javascript>
				var zselect = document.getElementById('prosjekt') ;
				var zopt = zselect.options[zselect.selectedIndex] ;
				${remoteFunction(controller:"capiAdministrasjon", action:"ajaxGetSkjemaList", params:"'id=' + zopt.value", onComplete:"updateSkjema(e)")}
			</g:javascript>
		
</div>
</body>
</html>