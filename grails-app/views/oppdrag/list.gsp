<%@ page import="sivadm.Intervjuer"%>
<%@ page import="siv.type.IntervjuerStatus"%>
<%@ page import="sivadm.Oppdrag" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'oppdrag.label', default: 'Oppdrag')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    	<div class="body">
	    	<h1><g:message code="sivadm.oppdrag.sok" default="Oppdrag søk" /></h1>
			<g:form action="list" method="post">
				<table>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="ioNr"><g:message code="oppdrag.ionr.label" default="IO-nr.:" /></label>
						</td>
						<td valign="top">
							<g:textField name="ioNr" value="${oppdragSok?.ioNr}" />
						</td>
						<td valign="top" class="name">
							<label for="ioId"><g:message code="oppdrag.ioid.label" default="IO-id.:" /></label>
						</td>
						<td valign="top">
							<g:textField name="ioId" value="${oppdragSok?.ioId}" />
						</td>
						<td valign="top" class="name">
							<label for="skjemaKortNavn" title="Kan bruke % i søk"><g:message code="oppdrag.skjemaKortNavn.label" default="Skjema kortnavn:" /></label>
						</td>
						<td valign="top">
							<g:textField name="skjemaKortNavn" value="${oppdragSok?.skjemaKortNavn}" title="Kan bruke % i søk" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="klynge"><g:message code="oppdrag.intervjuer.label" default="Intervjuer:" /></label>
						</td>
						<td>
							<g:select name="intervjuerId"
								from="${Intervjuer.findAllByStatus(IntervjuerStatus.AKTIV).sort{it.navn}}"
								optionKey="id"
								noSelection="['':'-Velg intervjuer']"
								value="${oppdragSok?.intervjuerId}" />
						</td>
						<td valign="top" class="name">
							<label for="klarTilSynk"><g:message code="oppdrag.klarTilSynk.label" default="Blaise synk:" /></label>
						</td>
						<td valign="top">
							<g:set var="klarTilSynkVerdi" value="alle" />
							<g:if test="${oppdragSok?.klarTilSynk}">
								<g:set var="klarTilSynkVerdi" value="${oppdragSok?.klarTilSynk}" />
							</g:if>
							<g:radioGroup name="klarTilSynk" labels="['Alle', 'Klar til synk', 'Ikke klar til synk']" values="['alle', 'klar', 'ikkeKlar']" value="${klarTilSynkVerdi}">
								${it.radio}&nbsp;${it.label}&nbsp;&nbsp;&nbsp;
							</g:radioGroup>
						</td>
						<td/>
						<td/>
					</tr>
				</table>
				<div class="buttonStyle">
					<g:submitButton name="list" value="Søk"/>
					<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="nullstillSok" />
				</div>
			</g:form>
            
            <h1>Oppdragliste (${oppdragInstanceTotal})</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'oppdrag.id.label', default: 'Id')}" />
                        
                            <th><g:message code="oppdrag.intervjuObjekt.label" default="Intervjuobjekt" /></th>
                        
	                        <g:sortableColumn property="skjemaKortNavn" title="${message(code: 'oppdrag.skjemaKortNavn.label', default: 'Skjema')}" />
                        
	                        <g:sortableColumn property="klarTilSynk" title="${message(code: 'oppdrag.klarTilSynk.label', default: 'Klar til synk')}" />
	                        
	                        <g:sortableColumn property="intervjuer" title="${message(code: 'oppdrag.intervjuer.label', default: 'Intervjuer')}" />
                        
                            <g:sortableColumn property="intervjuStatus" title="${message(code: 'oppdrag.intervjuStatus.label', default: 'Intervjustatus')}" />
                        
                            <g:sortableColumn property="intervjuType" title="${message(code: 'oppdrag.intervjuType.label', default: 'Intervjutype')}" />
                        
                            <g:sortableColumn property="intervjuStatusDato" title="${message(code: 'oppdrag.intervjuStatusDato.label', default: 'Intervjustatus dato')}" />
                        
                            <g:sortableColumn property="intervjuStatusKommentar" title="${message(code: 'oppdrag.intervjuStatusKommentar.label', default: 'Intervjustatus kommentar')}" />
                        
                        	<th/>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${oppdragInstanceList}" status="i" var="oppdragInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${oppdragInstance.id}"><g:formatNumber number="${oppdragInstance.id}" format="#"/></g:link></td>
                        
                            <td>${fieldValue(bean: oppdragInstance, field: "intervjuObjekt")}</td>
                        
                           	<td>${fieldValue(bean: oppdragInstance, field: "skjemaKortNavn")}</td>
                            
                            <td><g:formatBoolean boolean="${oppdragInstance.klarTilSynk}" false="Nei" true="Ja" /></td>
                            
                            <td>${fieldValue(bean: oppdragInstance, field: "intervjuer")}</td>
                            
                            <td>${fieldValue(bean: oppdragInstance, field: "intervjuStatus")}</td>
                        
                            <td>${fieldValue(bean: oppdragInstance, field: "intervjuType")}</td>
                        
                            <td><g:formatDate date="${oppdragInstance.intervjuStatusDato}" /></td>
                        
                            <td>${fieldValue(bean: oppdragInstance, field: "intervjuStatusKommentar")}</td>
                        
                        	<td>
								<g:link action="edit" id="${oppdragInstance.id}"><g:redigerIkon /></g:link>
								&nbsp;&nbsp;
								<a href="#" onclick="return apneSlettDialog(${oppdragInstance.id})"><g:slettIkon /></a>
							</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${oppdragInstanceTotal}" />
            </div>
            <g:slettDialog domeneKlasse="oppdrag" />
        </div>
    </body>
</html>
