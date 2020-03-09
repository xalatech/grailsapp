<%@ page import="sivadm.IntervjuObjekt" %>
<%@ page import="siv.type.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main2" />
	<g:set var="entityName" value="${message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
	<g:javascript library="prototype" />
	<g:javascript src="intervjuObjekt.js"/>
	<script type="text/javascript">
		jQuery(document).keydown(function(e) {
			if(e.keyCode == 37 && e.ctrlKey) {
				var form = document.getElementById('dummyForrigeForm');
				if(form) {
					form.submit();
				}
			}
			else if(e.keyCode == 39 && e.ctrlKey) {
				var form = document.getElementById('dummyNesteForm');
				if(form) {
					form.submit();
				}
			}
		});
	</script>
</head>
<body>
<div class="body">
	
	<g:set var="bedrift" value="${intervjuObjektInstance?.tilhorerBedriftSkjema()}" />
	
	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>
	<g:if test="${flash.errorMessage}">
		<div class="errors">
			${flash.errorMessage}
		</div>
	</g:if>
	<g:hasErrors bean="${intervjuObjektInstance}">
		<div class="errors">
			<g:renderErrors bean="${intervjuObjektInstance}" as="list" />
		</div>
	</g:hasErrors>
	
	
	<g:if test="${readOnly && laastAv}">
		<div class="errors">
			&nbsp;IO låst for redigering av bruker ${intervjuObjektInstance?.laastAv}
		</div>
		<br>
	</g:if>
	
	<g:form method="post">
		<g:hiddenField name="id" value="${intervjuObjektInstance?.id}" />
		<g:hiddenField name="version" value="${intervjuObjektInstance?.version}" />
	
	<div class="dialog">
		
	<g:if test="${!readOnly}">
		<g:render template="/templates/ioKnapperTemplate" model="[intervjuObjektInstance: intervjuObjektInstance]" />
		<br/>
	</g:if>
	<g:else>
		<g:render template="/templates/ioKnapperSePaaTemplate" model="[intervjuObjektInstance: intervjuObjektInstance, laastAv: laastAv]" />
		<br/>
	</g:else>
	
	<g:render template="/templates/ioInfoTemplate" model="[listeAntall: listeAntall, listePosisjon: listePosisjon, intervjuObjektInstance: intervjuObjektInstance, kommuneInstance: kommuneInstance]" />
	
	<g:if test="${bedrift == true}">
		<h2>Bedriftsinformasjon</h2>
	</g:if>
	<g:else>
		<h2>Personalia</h2>
	</g:else>
	
	<table id="personalia">
		<tbody>
			<tr>
				<td valign="top" class="name">
					<label for="navn"><g:message code="intervjuObjekt.navn.label" default="Navn" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'navn', 'errors')}">
					<g:textField name="navn" value="${intervjuObjektInstance?.navn}" size="50" />
				</td>
				
				<g:if test="${bedrift == true}">
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
				</g:if>
				<g:else>
					<td valign="top" class="name"><label for="kjonn">
						<g:message code="intervjuObjekt.kjonn.label" default="Kjønn" /></label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'kjonn', 'errors')}">
						<g:hiddenField name="kjonn" value="${intervjuObjektInstance?.kjonn?.key}" />${intervjuObjektInstance?.kjonn}
					</td>
				</g:else>
				
				<td valign="top" class="name">
					<g:if test="${bedrift == true}">
						<label for="fodselsNummer"><g:message code="intervjuObjekt.fodselsNummer.label"	default="Org. nr. bedrift" /></label>
					</g:if>
					<g:else>
						<label for="fodselsNummer"><g:message code="intervjuObjekt.fodselsNummer.label"	default="Fødselsnummer" /></label>
					</g:else>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'fodselsNummer', 'errors')}">
					<g:hiddenField name="fodselsNummer" value="${intervjuObjektInstance?.fodselsNummer}" />${intervjuObjektInstance?.fodselsNummer}
				</td>
				
			</tr>
			
			<tr>
				<g:if test="${bedrift == true}">
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
				</g:if>
				<g:else>
					<td valign="top" class="name">
						<label for="personKode"><g:message code="intervjuObjekt.personKode.label" default="Personkode" /></label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'personKode', 'errors')}">
						<g:textField name="personKode" value="${intervjuObjektInstance?.personKode}" size="1" maxlength="1" />
						<g:message code="sivadm.intervjuobjekt.personkode.info" default="(1=Ref.person, 2=Ektefelle, 3=Barn)"/>
					</td> 
					
					<td valign="top" class="name">
						<label for="sivilstand"><g:message code="intervjuObjekt.sivilstand.label" default="Sivilstand" /></label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'sivilstand', 'errors')}">
						<g:textField name="sivilstand" value="${intervjuObjektInstance?.sivilstand}" size="1"/>
					</td>
				</g:else>
				
				<td valign="top" class="name">
					<g:if test="${bedrift == true}">
						<label for="familienummer"><g:message code="intervjuObjekt.familienummer.label" default="Org. nr. foretak" /></label>
					</g:if>
					<g:else>
						<label for="familienummer"><g:message code="intervjuObjekt.familienummer.label" default="Familienummer" /></label>
					</g:else>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'familienummer', 'errors')}">
					<g:hiddenField name="familienummer" value="${intervjuObjektInstance?.familienummer}" />${intervjuObjektInstance?.familienummer}
				</td>
			</tr>
			
			<tr>
				<g:if test="${bedrift == true}">
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
				</g:if>
				<g:else>
					<td valign="top" class="name">
						<label for="alder"><g:message code="intervjuObjekt.alder.label" default="Alder" /></label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'alder', 'errors')}">
						<g:textField name="alder" value="${fieldValue(bean: intervjuObjektInstance, field: 'alder')}" size="3"/>
					</td>
				</g:else>
				
				
				
				<td valign="top" class="name">
					<label for="epost"><g:message code="intervjuObjekt.epost.label" default="Epost" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'alder', 'errors')}">
					<g:textField name="epost" value="${fieldValue(bean: intervjuObjektInstance, field: 'epost')}" size="38"/>
				</td>
				
				<td valign="top" class="name">
					<label for="kontaktperiode"><g:message code="intervjuObjekt.kontaktperiode.label" default="Kontaktperiode" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'kontaktperiode', 'errors')}">
					<g:textField name="kontaktperiode" value="${intervjuObjektInstance?.kontaktperiode}" size="20" maxlength="40" />
				</td>
			</tr>
			
			<tr>
				
				<g:if test="${bedrift == true}">
					<td valign="top" class="name"></td>
					<td valign="top" class="name"></td>
				</g:if>
				<g:else>
					<td valign="top" class="name">
						<label for="statsborgerskap"><g:message code="intervjuObjekt.statsborgerskap.label" default="Statsborgerskap" /></label>
					</td>
					<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'statsborgerskap', 'errors')}">
						<g:textField name="statsborgerskap" value="${fieldValue(bean: intervjuObjektInstance, field: 'statsborgerskap')}" size="3" readonly="true" />
					</td>
				</g:else>
				
				
				
				<td valign="top" class="name">
					<label for="delutvalg"><g:message code="intervjuObjekt.delutvalg.label" default="Delutvalg" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'delutvalg', 'errors')}">
					<g:textField name="delutvalg" value="${fieldValue(bean: intervjuObjektInstance, field: 'delutvalg')}" size="5" readonly="true"/>
				</td>
				
				<td valign="top" class="name">
					<label for="referansePerson"><g:message code="intervjuObjekt.referansePerson.label" default="Referanseperson" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'referansePerson', 'errors')}">
					<g:textField name="referansePerson" value="${intervjuObjektInstance?.referansePerson}" size="38" maxlength="40" />
				</td>
			
			</tr>

			<tr>
				<td valign="top" class="name"><label for="kommentar">
					<g:message code="intervjuObjekt.kommentar.label" default="Intern kommentar" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'kommentar', 'errors')}">
					<textArea name="kommentar" id="kommentar" cols="40" style="height: 35px">${intervjuObjektInstance?.kommentar}</textArea>
				</td>
				
				<td valign="top" class="name">
					<label for="statusKommentar"><g:message code="intervjuObjekt.statusKommentar.label" default="Melding fra intervjuer" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'statusKommentar', 'errors')}">
					<textArea name="statusKommentar" id="statusKommentar" style="height: 35px" cols="40" >${intervjuObjektInstance?.statusKommentar}</textArea>
				</td>
				
				<td valign="top" class="name">
					<label for="meldingTilIntervjuer"><g:message code="intervjuObjekt.meldingTilIntervjuer.label" default="Melding til intervjuer" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'meldingTilIntervjuer', 'errors')}">
					<textArea name="meldingTilIntervjuer" id="meldingTilIntervjuer" style="height: 35px" cols="40">${intervjuObjektInstance?.meldingTilIntervjuer}</textArea>
				</td>
			</tr>
			
			<tr>
			
				<td valign="top" class="name">
					<label for="passordWeb"><g:message code="intervjuObjekt.passordWeb.label" default="Passord web" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'passordWeb', 'errors')}">
					<g:textField name="passordWeb" value="${fieldValue(bean: intervjuObjektInstance, field: 'passordWeb')}" size="8"/>
				</td>

				<td valign="top" class="name">
					<label for="reservasjon"><g:message code="intervjuObjekt.reservasjon.label" default="Reservasjon" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'reservasjon', 'errors')}">
					<g:formatBoolean boolean="${fieldValue(bean: intervjuObjektInstance, field: 'reservasjon').toBoolean()}" true="Ja" false="Nei" />
				</td>

				<td valign="top" class="small">
					<label for="maalform"><g:message code="intervjuObjekt.maalform.label" default="Målform" /></label>
				</td>
				<td>
					<g:select name="maalform"
							  from="${siv.type.Maalform.values()}"
							  optionKey="key" optionValue="guiName"
							  noSelection="['':'Uoppgitt']"
							  value="${intervjuObjektInstance?.maalform}"/>

				</td>

			</tr>
			<tr>
				<td></td>
				<td></td>

				<td valign="top" class="name">
					<label for="varslingsstatus"><g:message code="intervjuObjekt.varslingsstatus.label" default="Varslingsstatus" /></label>
				</td>
				<td valign="top" class="value ${hasErrors(bean: intervjuObjektInstance, field: 'varslingsstatus', 'errors')}">
					<g:hiddenField name="varslingsstatus" value="${intervjuObjektInstance?.varslingsstatus}" />${intervjuObjektInstance?.varslingsstatus}
				</td>

				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
	
	
	
	<h2>Status</h2>
	<table id="io_status">
		<tbody>
			<tr class="prop">
				<td valign="top" class="small">
					<label for="katSkjemaStatus"><g:message code="intervjuObjekt.katSkjemaStatus.label" default="Skjemastatus" /></label>
				</td>
					<g:if test="${intervjuObjektInstance?.katSkjemaStatus == SkjemaStatus.Utsendt_CAPI}">
						<td valign="top" class="small ${hasErrors(bean: intervjuObjektInstance, field: 'katSkjemaStatus', 'errors')}" bgcolor="yellow">
						<nobr>${intervjuObjektInstance?.katSkjemaStatus}</nobr>
						<g:hiddenField name="katSkjemaStatus" value="${intervjuObjektInstance?.katSkjemaStatus?.key}" />
					</g:if>
					<g:else>
						<td valign="top" class="small ${hasErrors(bean: intervjuObjektInstance, field: 'katSkjemaStatus', 'errors')}">
						<g:set var="statusList" value="${intervjuObjektInstance?.katSkjemaStatus?.values()}" />
						<g:select name="katSkjemaStatus"
							from="${statusList}"
							value="${intervjuObjektInstance?.katSkjemaStatus?.key}"
							optionKey="key"
							optionValue="guiName" />
							 
					</g:else>
				</td>
				<td valign="top" class="small">
					<label for="intervjuStatus"><g:message code="intervjuObjekt.intervjuStatus.label" default="Intervjustatus" /></label>
				</td>
				<td valign="top" class="big ${hasErrors(bean: intervjuObjektInstance, field: 'intervjuStatus', 'errors')}">
					<g:textField name="intervjuStatus" value="${fieldValue(bean: intervjuObjektInstance, field: 'intervjuStatus')}" size="4" title="${intervjuStatusKoder}"></g:textField>	
				</td>
				<td valign="top" class="small">
					<g:message code="intervjuObjekt.assosiertIntervjuStatus.label" default="Assosiert intervjustatus" />
				</td>
				<td valign="top" class="big">
					${assosiertIntervjuStatus}
				</td>
				<td	rowspan="3">
					<g:if test="${intervjuObjektInstance?.statusHistorikk}">
						<div class="list" style="width: 450px; height:95px; overflow: auto;">
					        <table>
            					<thead>
                   					<g:sortableColumn property="skjemaStatus" title="${message(code: 'statusHistorikk.xx.label', default: 'Skjemastatus')}" />
                					<g:sortableColumn property="intervjuStatus" title="${message(code: 'statusHistorikk.xx.label', default: 'Intervjustatus')}" />
                					<g:sortableColumn property="redigertAv" title="${message(code: 'statusHistorikk.xx.label', default: 'Redigert av')}" />
                					<g:sortableColumn property="dato" title="${message(code: 'statusHistorikk.xx.label', default: 'Dato')}" />
                				</thead>
                				<tbody>
						        	<g:each in="${intervjuObjektInstance?.statusHistorikk?.sort{it.dato}.reverse()}" status="i" var="historikkInstance">
						       			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">	
							       			<td>${fieldValue(bean: historikkInstance, field: "skjemaStatus")}</td>
							       			<td>${fieldValue(bean: historikkInstance, field: "intervjuStatus")}</td>
							       			<td>${fieldValue(bean: historikkInstance, field: "redigertAv")}</td>
							       			<td><g:formatDate date="${historikkInstance.dato}" format="dd.MM.yyyy HH:mm:ss" /></td>
						       			</tr>
						       		</g:each>
						       	</tbody>
                			</table>
						</div>
					</g:if>					
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="small">
					<label for="paVentDato" title="Dato for når skjemastatus endres fra På vent til Ubehandlet"><g:message code="intervjuObjekt.paVentDato.label" default="På vent til" /></label>
				</td>
				<td valign="top" class="small ${hasErrors(bean: intervjuObjektInstance, field: 'paVentDato', 'errors')}">
					<g:datoVelger id="dp-1" onchange="paaVentDatoEndret()" name="paVentDato" value="${intervjuObjektInstance?.paVentDato}" />
				</td>
				<td valign="top" class="small">
					<g:message code="intervjuObjekt.internStatus.label" default="Internstatus" />
				</td>
				<td valign="top" class="big">
					${intervjuObjektInstance?.internStatus}
				</td>

				<td valign="top" class="small">
					<label for="fullforingsStatus"><g:message code="intervjuObjekt.fullforingsStatus.label" default="Fullføringsstatus" /></label>
				</td>
				<td valign="top" class="small">
					<g:textField disabled="disabled" name="fullforingsStatus" value="${fieldValue(bean: intervjuObjektInstance, field: 'fullforingsStatus')}" size="4"></g:textField>
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="small">
					<label for="parkert"><g:message code="intervjuObjekt.parkert.label" default="Parkert" /></label>
				</td>
				<td valign="top" class="small ${hasErrors(bean: intervjuObjektInstance, field: 'parkert', 'errors')}">
					<g:checkBox name="parkert" value="${intervjuObjektInstance?.parkert}" />
				</td>
				<td valign="top" class="small">
					<label for="parkertDato"><g:message code="intervjuObjekt.parkertDato.label" default="Parkert dato" /></label>
				</td>
				<td valign="top" class="big ${hasErrors(bean: intervjuObjektInstance, field: 'parkertDato', 'errors')}">
					<g:datoVelger id="dp-2" name="parkertDato" value="${intervjuObjektInstance?.parkertDato}" />
				</td>
			</tr>
			
			<tr class="prop">
				<td valign="top" class="small">
					<g:message code="intervjuObjekt.kilde.label" default="Kilde" />
				</td>
				<td valign="top" class="big">
					${intervjuObjektInstance?.kilde}
				</td>
				
				<td valign="top" class="small">
					<label for="fullforingsGrad"><g:message code="intervjuObjekt.fullforingsGrad.label" default="Fullføringsgrad" /></label>
				</td>
				<td valign="top" class="big ${hasErrors(bean: intervjuObjektInstance, field: 'fullforingsGrad', 'errors')}">
					<g:textField name="fullforingsGrad" value="${fieldValue(bean: intervjuObjektInstance, field: 'fullforingsGrad')}" size="4"></g:textField>
				</td>
				
				<td valign="top" class="small"></td>
				<td></td>
			</tr>
		</tbody>
	</table>
	
	
		<g:if test="${intervjuObjektInstance?.avtale != null}">


		<h2>Avtale</h2>
		<table id="io_avtale">
			<tbody>
			<tr class="prop">
				<td valign="top" class="medium">
					<label for="avtale.avtaleType"><g:message code="intervjuObjekt.avtale.avtaleType.label" default="Avtale type" /></label>
				</td>
					<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.avtaleType', 'errors')}">
					<g:set var="avtaleTyper" value="${AvtaleType.values()}" />
					<g:select name="avtale.avtaleType"
							  disabled="disabled"
							  from="${avtaleTyper}"
							  value="${intervjuObjektInstance?.avtale?.avtaleType}"
							  optionKey="key"
							  optionValue="guiName" />

			</td>

				<td valign="top" class="medium">
					<label for="avtale.whoMade"><g:message code="intervjuObjekt.avtale.whoMade.label" default="Hvem avtalt" /></label>
				</td>
				<td valign="top" class="medium">
					<g:textField disabled="disabled" name="avtale.whoMade" value="${fieldValue(bean: intervjuObjektInstance, field: 'avtale.whoMade')}"></g:textField>
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="medium">
					<label for="avtale.dateStart"><g:message code="intervjuObjekt.avtale.dateStart.label" default="Start dato" /></label>
				</td>
				<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.dateStart', 'errors')}">
					<g:textField disabled="disabled" id="dp-avtale1" name="avtale.dateStart" value="${intervjuObjektInstance?.avtale.dateStart}" />
				</td>

				<td valign="top" class="medium">
					<label for="avtale.timeStart"><g:message code="intervjuObjekt.avtale.timeStart.label" default="Start tid" /></label>
				</td>
				<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.timeStart', 'errors')}">
					<g:textField disabled="disabled" name="avtale.timeStart" value="${fieldValue(bean: intervjuObjektInstance, field: 'avtale.timeStart')}"></g:textField>
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="medium">
					<label for="avtale.dateEnd"><g:message code="intervjuObjekt.avtale.dateEnd.label" default="Slutt dato" /></label>
				</td>
				<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.dateEnd', 'errors')}">
					<g:textField disabled="disabled" id="dp-avtale2" name="avtale.dateEnd" value="${intervjuObjektInstance?.avtale.dateEnd}" />
				</td>

				<td valign="top" class="medium">
					<label for="avtale.timeEnd"><g:message code="intervjuObjekt.avtale.timeEnd.label" default="Slutt tid" /></label>
				</td>
				<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.timeEnd', 'errors')}">
					<g:textField disabled="disabled" name="avtale.timeEnd" value="${fieldValue(bean: intervjuObjektInstance, field: 'avtale.timeEnd')}"></g:textField>
				</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="medium">
					<label for="avtale.weekDays"><g:message code="intervjuObjekt.avtale.weekDays.label" default="Uke dager" /></label>
				</td>
				<td valign="top" class="big ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.weekDays', 'errors')}">
					<g:textArea disabled="disabled" name="avtale.weekDays" rows="2" cols="20" value="${fieldValue(bean: intervjuObjektInstance, field: 'avtale.weekDays')}" />
				</td>

				<td valign="top" class="medium">
					<label for="avtale.avtaleMelding"><g:message code="intervjuObjekt.avtale.avtaleMelding.label" default="Avtale Melding" /></label>
				</td>
				<td valign="top" class="medium ${hasErrors(bean: intervjuObjektInstance, field: 'avtale.avtaleMelding', 'errors')}">
					<g:textArea disabled="disabled" name="avtale.avtaleMelding" rows="2" cols="20" value="${fieldValue(bean: intervjuObjektInstance, field: 'avtale.avtaleMelding')}"/>
				</td>

			</tr>

			</tbody>
		</table>

	</g:if>
		</div>

	<h2>Telefon</h2>
	
	<div class="list">
	
        <table>
            <thead>
                <tr>
                	<g:sortableColumn property="telefonNummer" title="${message(code: 'telefon.beskrivelse.label', default: 'Telefonnummer')}" />
                
                	<g:sortableColumn property="kommentar" title="${message(code: 'telefon.beskrivelse.label', default: 'Kommentar')}" />
                	
                	<g:sortableColumn property="kilde" title="${message(code: 'telefon.kilde.label', default: 'Kilde')}" />
                
                    <g:sortableColumn property="gjeldende" title="${message(code: 'telefon.gjeldende.label', default: 'Gjeldende')}" />
                                        
              		<g:sortableColumn property="redigertAv" title="${message(code: 'tlf.redigert.av', default: 'Redigert av')}" />
              	
               		<g:sortableColumn property="redigertDato" title="${message(code: 'tlf.redigert.dato', default: 'Sist lagret')}" />
                    
                    <g:if test="${!readOnly}">
	                    <th/>
	                </g:if> 
                </tr>
            </thead>
            <tbody>
            <g:each in="${intervjuObjektInstance?.telefoner?.sort{it.id}}" status="i" var="telefonInstance">
                <g:if test="${visAlleTelefon?.equals('true') || telefonInstance.gjeldende}">
                
                	<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                
                    	<td style="width: 90px; text-align: right;">
                    		<g:link controller="telefon" action="edit" id="${telefonInstance.id}" params="[ioId:intervjuObjektInstance.id]">
                    			${fieldValue(bean: telefonInstance, field: "telefonNummer")}
                    		</g:link>
                    	</td>
                    
                    	<td>${fieldValue(bean: telefonInstance, field: "kommentar")}</td>
                    	
                    	<td>${fieldValue(bean: telefonInstance, field: "kilde")}</td>
                
                    	<td style="width: 75px;"><g:formatBoolean boolean="${telefonInstance.gjeldende}" true="Ja" false="Nei" /></td>
               
                 		<td>${fieldValue(bean: telefonInstance, field: "redigertAv")}</td>
                 
                 		<td style="width: 125px;"><g:formatDate date="${telefonInstance?.redigertDato}" format="dd.MM.yyyy HH:mm:ss" /></td>
                
                		<g:if test="${!readOnly}">
	                    	<td style="text-align: right; width: 55px;">
    	                		<g:link controller="telefon" action="edit" id="${telefonInstance.id}" params="[ioId:intervjuObjektInstance.id]"><g:redigerIkon/></g:link>
        	            		&nbsp;&nbsp;
            	        		<a href="#" onclick="return apneSlettTelefonDialog(${telefonInstance.id})"><g:slettIkon /></a>
                	    	</td>
                	    </g:if>
                	</tr>
                </g:if>
            </g:each>
            </tbody>
        </table>
        <br/>
        
        <div class="slink">
        	<g:if test="${!readOnly}">
      		  	<g:link class="create" controller="telefon" action="create" params="${[ioId:intervjuObjektInstance.id, visAlleTelefon: visAlleTelefon, visAlleAdresse: visAlleAdresse]}"><g:message code="sivadm.intervjuobjekt.legg.til.telefon" default="Legg til telefon" /></g:link>
      		  	|
        	</g:if>
        	
        	<g:if test="${!visAlleTelefon || visAlleTelefon.equals('false')}">
        		<g:link class="list" action="edit" id="${intervjuObjektInstance.id}" params="${[visAlleTelefon: 'true', visAlleAdresse: visAlleAdresse]}">
					<g:message code="sivadm.intervjuobjekt.telefon.vis.alle" default="Vis alle telefonnumre" />
				</g:link>
        	</g:if>
        	<g:else>
	        	<g:link class="list" action="edit" id="${intervjuObjektInstance.id}" params="${[visAlleTelefon: 'false', visAlleAdresse: visAlleAdresse]}">
					<g:message code="sivadm.intervjuobjekt.telefon.sjul.alle" default="Skjul ikke gjeldende telefonnumre" />
				</g:link>
        	</g:else>
        </div>
    </div>


	<h2>Adresse</h2>
	
	<div class="list">
	
	<table>
		<thead>
			<tr>
				<g:sortableColumn property="gateAdresse" title="${message(code: 'adresse.gateAdresse.label', default: 'Gateadresse')}" />
				
				<g:sortableColumn property="gateAdresse" title="${message(code: 'adresse.gateAdresse.label', default: 'Tilleggsadresse')}" />
				
				<g:sortableColumn property="boligNummer" title="${message(code: 'adresse.boligNummer.label', default: 'Bolignr.')}" />
				
				<g:sortableColumn property="postNummer"	title="${message(code: 'adresse.postNummer.label', default: 'Postnr.')}" />
				
				<g:sortableColumn property="postSted" title="${message(code: 'adresse.postSted.label', default: 'Poststed')}" />
									
				<g:sortableColumn property="kommuneNummer" title="${message(code: 'adresse.kommuneNummer.label', default: 'Kommunenr.')}" />
								
				<g:sortableColumn property="adresseType" title="${message(code: 'adresse.postSted.label', default: 'Type')}" />
					
				<g:sortableColumn property="gjeldende" title="${message(code: 'adresse.postSted.label', default: 'Gjeldende')}" />
				
				<g:sortableColumn property="redigertAv" title="${message(code: 'adresse.redigert.av', default: 'Redigert av')}" />
                      	
	            <g:sortableColumn property="redigertDato" title="${message(code: 'adresse.redigert.dato', default: 'Sist lagret')}" />
								
				<g:if test="${!readOnly}">
					<th/>
				</g:if>
			</tr>
		</thead>
		<tbody>
			<g:each in="${intervjuObjektInstance?.adresser?.sort{it.id}}" status="i" var="adresseInstance">
				<g:if test="${visAlleAdresse?.equals('true') || adresseInstance.gjeldende}">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	
						<td>
							<g:link action="edit" controller="adresse" id="${adresseInstance.id}" params="[ioId:intervjuObjektInstance.id]">
								${fieldValue(bean: adresseInstance, field: "gateAdresse")}
							</g:link>
						</td>
						
						<td>
							${fieldValue(bean: adresseInstance, field: "tilleggsAdresse")}
						</td>
						
						<td style="width: 55px; text-align: right;">
							${fieldValue(bean: adresseInstance, field: "boligNummer")}
						</td>
						
						<td style="width: 60px; text-align: right;">
							${fieldValue(bean: adresseInstance, field: "postNummer")}
						</td>
						
						<td>
							${fieldValue(bean: adresseInstance, field: "postSted")}
						</td>
						
						<td style="width: 75px; text-align: right;">
							${fieldValue(bean: adresseInstance, field: "kommuneNummer")}
						</td>
						
						<td style="width: 75px;">
							${fieldValue(bean: adresseInstance, field: "adresseType")}
						</td>
		
						<td style="width: 75px;">
							<g:formatBoolean boolean="${adresseInstance.gjeldende}" true="Ja" false="Nei" />
						</td>
						
						<td>
							${fieldValue(bean: adresseInstance, field: "redigertAv")}
						</td>
	                        
	                     <td style="width: 125px;">
	                     	<g:formatDate date="${adresseInstance?.redigertDato}" format="dd.MM.yyyy HH:mm:ss" />
	                     </td>
                        
						<g:if test="${!readOnly}">
							<td style="text-align: right; width: 55px;">
								<g:link action="edit" controller="adresse" id="${adresseInstance.id}" params="[ioId:intervjuObjektInstance.id]"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${adresseInstance.id})"><g:slettIkon /></a>
							</td>
						</g:if>
					</tr>
				</g:if>
			</g:each>
		</tbody>
	</table>
	<br/>
	
	<div class="slink">
		<g:if test="${!readOnly}">
			<g:link class="create" controller="adresse" action="create" params="${[ioId:intervjuObjektInstance.id]}"><g:message code="sivadm.intervjuobjekt.legg.til.adresse" default="Legg til ny adresse" /></g:link>
			|
		</g:if>
		
		<g:if test="${!visAlleAdresse || visAlleAdresse?.equals('false')}">
			<g:link class="list" action="edit" id="${intervjuObjektInstance.id}" params="${[visAlleAdresse: 'true', visAlleTelefon: visAlleTelefon]}">
				<g:message code="sivadm.intervjuobjekt.adresse.vis.alle" default="Vis alle adresser" />
			</g:link>
		</g:if>
		<g:else>
			<g:link class="list" action="edit" id="${intervjuObjektInstance.id}" params="${[visAlleAdresse: 'false', visAlleTelefon: visAlleTelefon]}">
				<g:message code="sivadm.intervjuobjekt.adresse.sjul.alle" default="Skjul ikke gjeldende adresser" />
			</g:link>
		</g:else>
	</div>
	
	</div>
	
	<g:if test="${bedrift == true}">
		<h2>Ekstra bedriftsopplysninger</h2>
	</g:if>
	<g:else>
		<h2>Husholdning</h2>	
	</g:else>
	
	<div class="list">
		<table>
			<thead>
				<tr>
					<g:sortableColumn property="husholdNummer" title="${message(code: 'husholding.husholdNummer.label', default: 'Nr.')}" />
					<g:sortableColumn property="navn" title="${message(code: 'husholdning.navn.label', default: 'Navn')}" />
					<g:if test="${bedrift == false}">
						<g:sortableColumn property="fodselsDato" title="${message(code: 'husholdning.fodselsDato.label', default: 'Født')}" />
						<g:sortableColumn property="personKode" title="${message(code: 'husholdning.personKode.label', default: 'Personkode')}" />
						<g:sortableColumn property="fodselsNummer" title="${message(code: 'husholdning.fodselsNummer.label', default: 'Fødselsnummer')}" />
					</g:if>
					<g:if test="${!readOnly}">
						<th/>
					</g:if> 
				</tr>
			</thead>
			<tbody>
				<g:each in="${intervjuObjektInstance?.husholdninger?.sort{it.husholdNummer}}" status="i" var="husholdning">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td style="width: 25px; text-align: center;">
							<g:link action="edit" controller="husholdning" id="${husholdning.id}" params="[ioId:intervjuObjektInstance.id]">${fieldValue(bean: husholdning, field: "husholdNummer")}</g:link>					
						</td>
						<td>
							${fieldValue(bean: husholdning, field: "navn")}
						</td>
						
						<g:if test="${bedrift == false}">
						
							<td>
								<g:formatDate date="${husholdning?.fodselsDato}" format="dd.MM.yyyy" /> 
							</td>
							<td>
								${fieldValue(bean: husholdning, field: "personKode")}
							</td>
							<td>
								${fieldValue(bean: husholdning, field: "fodselsNummer")}
							</td>
						
						</g:if>
						
						<g:if test="${!readOnly}">
							<td style="text-align: right; width: 55px;">
								<g:link action="edit" controller="husholdning" id="${husholdning.id}" params="[ioId:intervjuObjektInstance.id]"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettHusholdningDialog(${husholdning.id})"><g:slettIkon /></a>
							</td>
						</g:if>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
	
	<g:if test="${!readOnly}">
		<br/>
		<div class="slink">
			<g:if test="${bedrift == true}">
				<g:link class="create" controller="husholdning" action="create" params="[ioId:intervjuObjektInstance.id]"><g:message code="sivadm.intervjuobjekt.legg.til.husholdning" default="Legg til ny bedriftsinformasjon" /></g:link>
			</g:if>
			<g:else>
				<g:link class="create" controller="husholdning" action="create" params="[ioId:intervjuObjektInstance.id]"><g:message code="sivadm.intervjuobjekt.legg.til.husholdning" default="Legg til nytt husholdningsmedlem" /></g:link>
			</g:else>
			
		</div>
	</g:if>
	
	<g:if test="${!readOnly}">
		<br/><br/>
		<g:render template="/templates/ioKnapperTemplate" model="[intervjuObjektInstance: intervjuObjektInstance]" />
	</g:if>
</g:form></div>
<g:slettDialog domeneKlasse="adresse" controller="adresse" action="delete" paramsNavn="['ioId']" paramsVerdier="${[intervjuObjektInstance.id]}" />
<g:slettDialog domeneKlasse="telefon" controller="telefon" action="delete" formId="_slettTelefonSkjema_" dialogId="dialog-slett-telefon" openDialogFunctionName="apneSlettTelefonDialog" paramsNavn="['ioId']" paramsVerdier="${[intervjuObjektInstance.id]}" />
<g:slettDialog domeneKlasse="husholdning" controller="husholdning" action="delete" formId="_slettHusholdningSkjema_" dialogId="dialog-slett-husholdning" openDialogFunctionName="apneSlettHusholdningDialog" paramsNavn="['ioId']" paramsVerdier="${[intervjuObjektInstance.id]}" />
<g:form name="dummyNesteForm" action="nextIntervjuObjekt"></g:form>
<g:form name="dummyForrigeForm" action="previousIntervjuObjekt"></g:form>
</body>
</html>