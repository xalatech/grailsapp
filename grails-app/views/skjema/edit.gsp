
<%@ page import="sivadm.Skjema" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'skjema.label', default: 'Skjema')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        
        <div class="body">
            <h1>Rediger skjema</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${skjemaInstance}">
            <div class="errors">
                <g:renderErrors bean="${skjemaInstance}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:form method="post" >
                <g:hiddenField name="id" value="${skjemaInstance?.id}" />
                <g:hiddenField name="version" value="${skjemaInstance?.version}" />
                                
                <div class="dialog">
                
        <table>
        
		<tbody>
			<tr class="prop">
				<td valign="top" class="small"><label for="skjemaNavn"><g:message
					code="skjema.skjemaNavn.label" default="Skjemanavn" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'skjemaNavn', 'errors')}">
				<g:textField name="skjemaNavn" value="${skjemaInstance?.skjemaNavn}" size="30"/>
				</td>

				<td class="space" />

				<td valign="top" class="small"><label for="skjemaKortNavn"><g:message
					code="skjema.skjemaKortNavn.label" default="Kortnavn" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'skjemaKortNavn', 'errors')}">
				<g:textField name="skjemaKortNavn"
					value="${skjemaInstance?.skjemaKortNavn}" /></td>
				
				<td class="space" />

				<td valign="top" class="small"><label for="delProduktNummer"><g:message
					code="skjema.delProduktNummer.label" default="Arbeidsordrenummer" /></label>
				</td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'delProduktNummer', 'errors')}">
				<g:textField name="delProduktNummer"
					value="${skjemaInstance?.delProduktNummer}" size="10"/></td>


			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label
					for="oppstartDataInnsamling"><g:message
					code="skjema.oppstartDataInnsamling.label" default="Oppstartsdato" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'oppstartDataInnsamling', 'errors')}">
				<g:datoVelger id="d1" name="oppstartDataInnsamling" value="${skjemaInstance?.oppstartDataInnsamling}"/></td>
				
				<td class="space" />

				<td valign="top" class="small"><label for="planlagtSluttDato">
					<nobr><g:message code="skjema.planlagtSluttDato.label" default="Planlagt sluttdato" /></label></nobr>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'planlagtSluttDato', 'errors')}">
				<g:datoVelger id="d2" name="planlagtSluttDato" value="${skjemaInstance?.planlagtSluttDato}"/></td>

				<td class="space" />
				
				<td valign="top" class="small"><label for="sluttDato"><g:message
					code="skjema.sluttDato.label" default="Sluttdato" /></label></td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'sluttDato', 'errors')}">
				<g:datoVelger id="d3" name="sluttDato" value="${skjemaInstance.sluttDato}"/></td>

			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="prosjekt"><g:message
					code="skjema.prosjekt.label" default="Prosjekt" /></label></td>
				<td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'prosjekt', 'errors')}">
					<g:hiddenField name="prosjekt.id" value="${skjemaInstance?.prosjekt?.id}" />
					${skjemaInstance?.prosjekt?.prosjektNavn?.encodeAsHTML()}
				</td>
				
				<td class="space" />

				<td valign="top" class="small"><label for="adminTid"><g:message
					code="skjema.adminTid.label" default="Admintid" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'adminTid', 'errors')}">
				<g:textField name="adminTid"
					value="${fieldValue(bean: skjemaInstance, field: 'adminTid')}" />
				</td>
				
				<td class="space" />

				<td valign="top" class="small"><label for="status"><g:message
					code="skjema.status.label" default="Status" /></label></td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'status', 'errors')}">
				<g:textField name="status" value="${skjemaInstance?.status}" size="10"/></td>
			</tr>



			<tr class="prop">
				<td valign="top" class="small"><label for="kommentar"><g:message
					code="skjema.kommentar.label" default="Kommentar" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'kommentar', 'errors')}">
				<g:textField name="kommentar" value="${skjemaInstance?.kommentar}" size="30"/>
				</td>
				
				<td class="space" />

				<td valign="top" class="small"><label
					for="aktivertForIntervjuing"><g:message
					code="skjema.aktivertForIntervjuing.label"
					default="Aktivert" /></label></td>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'aktivertForIntervjuing', 'errors')}">
				<g:checkBox name="aktivertForIntervjuing"
					value="${skjemaInstance?.aktivertForIntervjuing}" title="Kryss av for denne dersom intervjuere skal kunne starte opp skjema."/></td>
					
				<td class="space" />
				
				<td valign="top" class="small">
					<label for="malVersjon"><g:message code="skjema.malVersjon.label" default="Mal versjon" /></label>
				</td>
				<td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'malVersjon', 'errors')}">
					<g:select name="malVersjon"
						from="${malVersjoner}"
						value="${skjemaInstance?.malVersjon}" 
						/>
				</td>

			</tr>
			
			<tr class="prop">
				<td valign="top" class="small">
                                  <label for="skjemaVersjoner"><g:message code="skjema.skjemaVersjoner.label" default="Skjemaversjoner" /></label>
                                </td>
                                <td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'skjemaVersjoner', 'errors')}">
									<ul>
										<g:each in="${skjemaInstance?.skjemaVersjoner?.sort{it.versjonsNummer}}" var="s">
											<li>
												<g:link controller="skjemaVersjon" action="edit" id="${s.id}">${s?.encodeAsHTML()}</g:link>
											</li>
										</g:each>
									</ul>
									
									<g:link controller="skjemaVersjon" action="create"
										params="['skjemaId': skjemaInstance?.id]">
										${message(code: 'default.add.label', args: [message(code: 'skjemaVersjon.label', default: 'Skjemaversjon')])}
									</g:link>
								</td>
								
								<td class="space"/>
								
								<td valign="top" class="small">
                                  <label for="perioder"><g:message code="skjema.perioder.label" default="Perioder" /></label>
                                </td>
                                
                                
                                <td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'perioder', 'errors')}">
									<ul>
										<g:each in="${skjemaInstance?.perioder?.sort{it.periodeNummer}}" var="s">
											<li>
												<g:link controller="periode" action="edit" id="${s.id}">${s?.encodeAsHTML()}</g:link>
											</li>
										</g:each>
									</ul>
									
									<g:link controller="periode" action="create"
										params="['skjemaId': skjemaInstance?.id]">
										${message(code: 'default.add.label', args: [message(code: 'periode.label', default: 'Periode')])}
									</g:link>
								</td>
								
								<td class="space"/>
								
								<td valign="top" class="small"><label for="instrumentId"><g:message
									code="skjema.instrumentId.label" default="InstrumentId" /></label></td>
								<td valign="top"
									class="small ${hasErrors(bean: skjemaInstance, field: 'instrumentId', 'errors')}">
								<g:textField name="instrumentId" value="${skjemaInstance?.instrumentId}" size="30"/>
								</td>
			</tr>

			<tr class="prop">

				<td class="space"/>
				<td class="space"/>
				<td class="space"/>
				<td class="space"/>
				<td class="space"/>
				<td class="space"/>

				<td valign="top" class="small">
					<label for="altIBlaise5"><g:message code="skjema.altIBlaise5.label" default="Alt i Blaise 5" /></label>
				</td>
				<td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'altIBlaise5', 'errors')}">
					<g:checkBox name="altIBlaise5" value="${skjemaInstance?.altIBlaise5}" />
				</td>

			</tr>

		</tbody>
	</table>

	<h2>Administrativt</h2>

	<table>
		<tbody>
			<tr class="prop">

				<td valign="top" class="small"><label for="intervjuTypeBesok"><g:message
					code="skjema.intervjuTypeBesok.label" default="Besøk" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'intervjuTypeBesok', 'errors')}">
				<g:checkBox name="intervjuTypeBesok"
					value="${skjemaInstance?.intervjuTypeBesok}" /></td>
					
				<td class="space"/>

				<td valign="top" class="small"><label for="undersokelseType"><g:message
					code="skjema.undersokelseType.label" default="Undersøkelsestype" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'undersokelseType', 'errors')}">
				<g:select name="undersokelseType"
					from="${siv.type.UndersokelsesType?.values()}"
					optionKey="key"
					optionValue="guiName"
					value="${skjemaInstance?.undersokelseType?.key}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="onsketSvarProsent"><g:message
					code="skjema.onsketSvarProsent.label" default="Ønsket svarprosent" /></label>
				</td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'onsketSvarProsent', 'errors')}">
				<g:textField name="onsketSvarProsent"
					value="${fieldValue(bean: skjemaInstance, field: 'onsketSvarProsent')}" size="4"/>
				</td>
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="small"><label for="intervjuTypeTelefon"><g:message
					code="skjema.intervjuTypeTelefon.label"
					default="Telefon" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'intervjuTypeTelefon', 'errors')}">
				<g:checkBox name="intervjuTypeTelefon"
					value="${skjemaInstance?.intervjuTypeTelefon}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label
					for="ioBrevGodkjentInitialer">
					<nobr>
						<g:message code="skjema.ioBrevGodkjentInitialer.label" default="IO-brev godkjent initialer" />
					</nobr>
					</label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'ioBrevGodkjentInitialer', 'errors')}">
				<g:textField name="ioBrevGodkjentInitialer"	value="${skjemaInstance?.ioBrevGodkjentInitialer}" maxlength="3" size="4" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="ioBrevGodkjentDato"><g:message
					code="skjema.ioBrevGodkjentDato.label"
					default="IO-brev godkjent dato" /></label></td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'ioBrevGodkjentDato', 'errors')}">
				<g:datoVelger id="d4" name="ioBrevGodkjentDato" value="${skjemaInstance?.ioBrevGodkjentDato}"/></td>
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="small"><label for="intervjuTypePapir"><g:message
					code="skjema.intervjuTypePapir.label" default="Papir" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'intervjuTypePapir', 'errors')}">
				<g:checkBox name="intervjuTypePapir"
					value="${skjemaInstance?.intervjuTypePapir}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="intervjuVarighet"><g:message
					code="skjema.intervjuVarighet.label" default="Intervjuvarighet" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'intervjuVarighet', 'errors')}">
				<g:textField name="intervjuVarighet"
					value="${fieldValue(bean: skjemaInstance, field: 'intervjuVarighet')}"
					size="4" /></td>

				
			</tr>
			
			
			<tr class="prop">
				<td valign="top" class="small"><label for="intervjuTypeWeb"><g:message
					code="skjema.intervjuTypeWeb.label" default="Web" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'intervjuTypeWeb', 'errors')}">
				<g:checkBox name="intervjuTypeWeb"
					value="${skjemaInstance?.intervjuTypeWeb}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="regoverforingDato"><g:message
					code="skjema.regoverforingDato.label"
					default="Registrert overføringsdato" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'regoverforingDato', 'errors')}">
				<g:datoVelger id="d5" name="regoverforingDato" value="${skjemaInstance?.regoverforingDato}"/></td>
				
				<td class="space"/>

				<td valign="top" class="small">
					<nobr>
					<label for="regoverforingSeksjon">
						<g:message
						code="skjema.regoverforingSeksjon.label"
						default="Registrert overføring seksjon" />
					</label></nobr>
				</td>
				
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'regoverforingSeksjon', 'errors')}">
				<g:textField name="regoverforingSeksjon"
					value="${skjemaInstance?.regoverforingSeksjon}" /></td>


			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="overtid"><g:message
					code="skjema.overtid.label" default="Overtid" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'overtid', 'errors')}">
				<g:checkBox name="overtid" value="${skjemaInstance?.overtid}" /></td>
				
				<td class="space"/>
				
				<td valign="top" class="small">
					<label for="regoverforingInitialer">
						<g:message code="skjema.regoverforingInitialer.label" default="Registrert overføring initialer" />
					</label>
				</td>
				<td valign="top" class="small ${hasErrors(bean: skjemaInstance, field: 'regoverforingInitialer', 'errors')}">
					<g:textField name="regoverforingInitialer" value="${skjemaInstance?.regoverforingInitialer}" maxlength="3" size="4" />
				</td>

			</tr>

		</tbody>
	</table>

	<h2>IT-oppgaver</h2>

	<table>
		<tbody>
			<tr class="prop">
				<td valign="top" class="small">
					<label for="klarTilGenerering">
						<nobr><g:message code="skjema.klarTilGenerering.label" default="Klar til generering" /></nobr>
					</label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'klarTilGenerering', 'errors')}">
				<g:checkBox name="klarTilGenerering"
					value="${skjemaInstance?.klarTilGenerering}" /></td>
				
				<td class="space"/>

				<td class="small"></td>

				<td class="small">Dato</td>
				
				<td class="space"/>
				
				<td class="small">Epost sendt</td>

			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="klarTilUtsending"><g:message
					code="skjema.klarTilUtsending.label" default="Klar til utsending" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'klarTilUtsending', 'errors')}">
				<g:checkBox name="klarTilUtsending"
					value="${skjemaInstance?.klarTilUtsending}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="krypteringDato"><g:message
					code="skjema.krypteringDato.label" default="Kryptering" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'krypteringDato', 'errors')}">
				<g:datoVelger id="d6" name="krypteringDato" value="${skjemaInstance?.krypteringDato}"/></td>
				
				<td class="space"/>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'krypteringMailSendt', 'errors')}">
				<g:datoVelger id="d7" name="krypteringMailSendt" value="${skjemaInstance?.krypteringMailSendt}"/></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="slettesEtterRetur"><g:message
					code="skjema.slettesEtterRetur.label" default="Slettes etter retur" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'slettesEtterRetur', 'errors')}">
				<g:checkBox name="slettesEtterRetur"
					value="${skjemaInstance?.slettesEtterRetur}" /></td>

				<td class="space"/>

				<td valign="top" class="small"><label for="anonymDato"><g:message
					code="skjema.anonymDato.label" default="Anonymisering" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'anonymDato', 'errors')}">
				<g:datoVelger id="d8" name="anonymDato" value="${skjemaInstance?.anonymDato}"/></td>
				
				<td class="space"/>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'anonymMailSendt', 'errors')}">
				<g:datoVelger id="d82" name="anonymMailSendt"
					value="${skjemaInstance?.anonymMailSendt}" /></td>

			</tr>
			<tr class="prop">
				<td valign="top" class="small"><label for="kanSlettesLokalt"><g:message
					code="skjema.kanSlettesLokalt.label" default="Kan slettes lokalt" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'kanSlettesLokalt', 'errors')}">
				<g:checkBox name="kanSlettesLokalt"
					value="${skjemaInstance?.kanSlettesLokalt}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="ryddDato"><g:message
					code="skjema.ryddDato.label" default="Rydde" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'ryddDato', 'errors')}">
				<g:datoVelger id="d10" name="ryddDato" value="${skjemaInstance?.ryddDato}" /></td>
				
				<td class="space"/>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'ryddMailSendt', 'errors')}">
				<g:datoVelger id="d11" name="ryddMailSendt"
					value="${skjemaInstance?.ryddMailSendt}" /></td>

			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="hentAlleOppdrag"><g:message
					code="skjema.hentAlleOppdrag.label" default="Hent alle oppdrag" /></label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'hentAlleOppdrag', 'errors')}">
				<g:checkBox name="hentAlleOppdrag"
					value="${skjemaInstance?.hentAlleOppdrag}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="papirMakuleringDato"><g:message
					code="skjema.papirMakuleringDato.label" default="Papirmak" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'papirMakuleringDato', 'errors')}">
				<g:datoVelger id="d12" name="papirMakuleringDato"
					value="${skjemaInstance?.papirMakuleringDato}" /></td>
				
				<td class="space"/>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'papirMakuleringMailSendt', 'errors')}">
				<g:datoVelger id="d13" name="papirMakuleringMailSendt"
					value="${skjemaInstance?.papirMakuleringMailSendt}" /></td>

			</tr>

			<tr class="prop">
				<td class="small"/>
			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label for="dataUttaksDato"><g:message
					code="skjema.dataUttaksDato.label" default="Data uttaksdato" /></label></td>

				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'dataUttaksDato', 'errors')}">
				<g:datoVelger id="d9" name="dataUttaksDato"
					value="${skjemaInstance?.dataUttaksDato}" /></td>
				
				<td class="space"/>
					
				<td valign="top" class="small"><label for="langtidsLagretDato"><g:message
					code="skjema.langtidsLagretDato.label"
					default="Langtidslagret dato" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'langtidsLagretDato', 'errors')}">
				<g:datoVelger id="d14" name="langtidsLagretDato"
					value="${skjemaInstance?.langtidsLagretDato}" /></td>
				
				<td class="space"/>

				<td valign="top" class="small"><label for="langtidsLagretAv"><g:message
					code="skjema.langtidsLagretAv.label" default="Langtidslagret av" /></label>
				</td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'langtidsLagretAv', 'errors')}">
				<g:textField name="langtidsLagretAv"
					value="${skjemaInstance?.langtidsLagretAv}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="small"><label
					for="antallIntervjuObjekterLagret"><g:message
					code="skjema.antallIntervjuObjekterLagret.label"
					default="Antall IO lagret" /></label></td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'antallIntervjuObjekterLagret', 'errors')}">
				<g:textField name="antallIntervjuObjekterLagret"
					value="${fieldValue(bean: skjemaInstance, field: 'antallIntervjuObjekterLagret')}" size="10"/>
				</td>
				
				<td class="space"/>

				<td valign="top" class="small">
					<label for="antallOppdragLagret">
						<nobr><g:message code="skjema.antallOppdragLagret.label" default="Antall oppdrag lagret" /></nobr>
					</label>
				</td>
				<td valign="top"
					class="small ${hasErrors(bean: skjemaInstance, field: 'antallOppdragLagret', 'errors')}">
				<g:textField name="antallOppdragLagret"
					value="${fieldValue(bean: skjemaInstance, field: 'antallOppdragLagret')}" size="10"/>
				</td>
				
				<td class="space"/>
				
				<td valign="top" class="small"><label
					for="maxAntIntervjuObjekterKontakt"><nobr><g:message
					code="skjema.maxAntIntervjuObjekterKontakt.label"
					default="Max antall IO kontakt" /></nobr></label></td>
				<td valign="top"
					class="big ${hasErrors(bean: skjemaInstance, field: 'maxAntIntervjuObjekterKontakt', 'errors')}">
				<g:textField name="maxAntIntervjuObjekterKontakt"
					value="${fieldValue(bean: skjemaInstance, field: 'maxAntIntervjuObjekterKontakt')}" size="10"/>
				</td>

			</tr>

			

		</tbody>
	</table>
                
                <h2>Kurs / retningslinjer</h2>
                
                <table>
                	<tbody>
                		<tr class="prop">
                			<td valign="top" class="name">
                                  <label for="opplaringer"><g:message code="skjema.opplaringer.label" default="Opplæring" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: skjemaInstance, field: 'opplaringer', 'errors')}">
                                    <g:select name="opplaringer" from="${sivadm.Opplaring.list()}" multiple="yes" optionKey="id" size="5" value="${skjemaInstance?.opplaringer}" />
                                </td>
                		</tr>
                	</tbody>
                </table>
                
                
                
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <g:if test="${prosjektId}">
                    	<span class="menuButton"><g:link class="delete" controller="prosjekt" action="edit" id="${prosjektId}"><g:message code="sil.avbryt" /></g:link></span>
                    	<g:hiddenField name="prosjektId" value="${prosjektId}" />
                    </g:if>
                    <g:elseif test="${fraProsjektListe}">
	                    <span class="menuButton"><g:link class="delete" controller="prosjekt" action="list"><g:message code="sil.avbryt" /></g:link></span>
                    	<g:hiddenField name="fraProsjektListe" value="${fraProsjektListe}" />
                    </g:elseif>
                    <g:else>
	                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
	                </g:else>
                </div>
            </g:form>
        </div>
    </body>
</html>
