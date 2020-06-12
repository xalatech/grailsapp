<%@ page import="java.util.Calendar" %>
<%@ page import="sil.Krav" %>
<%@ page import="sil.type.*" %>
<%@ page import="siv.type.*" %>
<%@ page import="sil.data.IntervjuerKontroll" %>
<%@ page import="sivadm.Skjema" %>
<%@ page import="sivadm.Utlegg" %>
<%@ page import="sivadm.Timeforing" %>
<%@ page import="sivadm.Kjorebok" %>
<html>
    <head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <meta name="layout" content="main" />
		<title><g:message code="sil.behandle.intervjuer" default="Behandle intervjuer" /></title>
		<script type="text/javascript" src="${resource(dir:'js/',file:'behandleIntervjuerKrav.js')}"></script>
		<script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.resizable.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.position.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.mouse.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.draggable.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.dialog.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.core.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.widget.js')}"></script>
        <script>
			jQuery(function() {
				jQuery( "#dialog-til-retting-retur" ).dialog({
					autoOpen: false,
					resizable: false,
					height:200,
					width: 300,
					modal: true,
					buttons: {
						"${message(code: 'sil.avbryt')}": function() {
							jQuery( this ).dialog( "close" );
						},
						"${message(code: 'sil.behandle.krav.tilbakekall')}": function() {
							submitForm('tilbakestillDummyForm');
							jQuery( this ).dialog( "close" );
						}
					}
				});
			});
			
			function apneTilbakestillDialog() {
				jQuery( "#dialog-til-retting-retur" ).dialog( "open" );
				return false;
			}

			var skjemaToCommit = ""

			jQuery(function() {
				jQuery( "#dialog-slett-utlegg-confirm" ).dialog({
					autoOpen: false,
					resizable: false,
					height:170,
					width: 300,
					modal: true,
					buttons: {
						"${message(code: 'sil.avbryt')}": function() {
							jQuery( this ).dialog( "close" );
						},
						"${message(code: 'sil.bekreft.slett', default: 'Slett')}": function() {
							submitForm(skjemaToCommit);
							jQuery( this ).dialog( "close" );
						}
					}
				});
			});
			
			function apneSlettUtleggDialog(id, skjemaNavn) {
				skjemaToCommit = skjemaNavn
				var dummyForm = document.getElementById(skjemaNavn);
				if(dummyForm) {
					var el = document.createElement("input");
					el.type = "hidden";
					el.id = "utleggId";
					el.name = "utleggId";
					el.value = id;
					dummyForm.appendChild(el);
				}
				jQuery( "#dialog-slett-utlegg-confirm" ).dialog( "open" );
				return false;
			}
		</script>
    </head>
    <body>
		<g:set var="currentIndex" value="-1" />
	    	    
	   	<div class="body">
			
			<div class="buttons" style="height: 22px; padding-top: 8px; ">
				<span class="menuButton"><g:link action="godkjennForIntervjuer" id="${intervjuerInstance.id}" title="${message(code: 'sil.behandle.godkjenn.alle.tooltip', default: 'Godkjenn alle krav')}"><g:message code="sil.behandle.godkjenn.alle" default="Godkjenn alle krav" /></g:link></span>
				<span class="menuButton"><g:link action="sendTilRettingForIntervjuer" id="${intervjuerInstance.id}" title="${message(code: 'sil.behandle.send.tilbake.til.intervjuer.tooltip', default: 'Send tilbake til intervjuer')}"><g:message code="sil.behandle.send.tilbake" defalut="Send tilbake til intervjuer" /></g:link></span>
				<span class="menuButton"><g:link action="forrigeIntervjuer" title="${message(code: 'sil.behandle.forrige.tooltip', default: 'Behandle forrige intervjuer i listen')}"><g:message code="sil.behandle.forrige" default="Forrige" /></g:link></span>
				<span class="menuButton"><g:link action="nesteIntervjuer" title="${message(code: 'sil.behandle.neste.tooltip', default: 'Behandle neste intervjuer i listen')}"><g:message code="sil.behandle.neste" default="Neste" /></g:link></span>			
			</div>
	
            <h1>Behandle intervjuerkrav for: 
            	${intervjuerInstance.navn} (${intervjuerInstance.klynge})
      				<g:if test="${Krav.findAllByKravStatusAndIntervjuer(KravStatus.SENDES_TIL_INTERVJUER, intervjuerInstance).size() > 0}">
            			<span style="color: orange;">&nbsp;<g:message code="sil.krav.til.tilbakesending" args="${[Krav.findAllByKravStatusAndIntervjuer(KravStatus.SENDES_TIL_INTERVJUER, intervjuerInstance).size()]}" default="Behandle intervjuer" /></span>
            		</g:if>
            </h1>
            
            <g:if test="${ flash.errorMessage }">
				<div class=errors>&nbsp;${flash.errorMessage}</div>
			</g:if>
            <g:if test="${flash.message}">
            	<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            
            <g:if test="${intervjuerInstance}">
            	<br>           	
            	<br>
            	<div id="tabs">
					<ul>
						<li><a href="#tabs-1"><g:message code="sil.behandle.sammendrag" default="Sammendrag" /></a></li>
						<li><a href="#tabs-2"><g:message code="sil.behandle.feilet.automatiske.kontroller" default="Feilet automatiske kontroller" /></a></li>
						<li><a href="#tabs-3"><g:message code="sil.behandle.krav.krav" default="Krav" /></a></li>
					</ul>
					<div id="tabs-1">
						<g:if test="${intervjuerKontrollInstance.antallKrav > 0}">
	            			<g:set var="cnt" value="${0}" />
							<table style="border: 0px;">
								<tbody>
									<tr>
										<td style="width:350px">
											<table style="width:350px">
			                    				<tbody>
							                        <tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
							                           	<td><g:message code="sil.behandle.antall.til.kontroll" />:</td>
							                            <td>${intervjuerKontrollInstance.antallKrav}</td>
							                        </tr>
													<g:set var="cnt" value="${cnt + 1}" />
							                        <tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
							                            <td><g:message code="sil.behandle.feilet.automatiske.kontroller" />:</td>
							                            <td>${intervjuerKontrollInstance.antallFeiletAutomatiskeTester}</td>
							                        </tr>
													<g:set var="cnt" value="${cnt + 1}" />
							                        <tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
							                            <td><g:message code="sil.behandle.tid" />:</td>
							                            <td><g:message code="sil.kontroll.tid.info" args="${[intervjuerKontrollInstance.totalTimer, intervjuerKontrollInstance.totalMinutter]}" /> <g:message code="sil.behandle.antall.krav" args="${[intervjuerKontrollInstance.timeKrav]}" /></td>
							                        </tr>
													<g:set var="cnt" value="${cnt + 1}" />
													<tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
							                            <td><g:message code="sil.behandle.km" />:</td>
							                            <td><g:message code="sil.kontroll.km.info" args="${[intervjuerKontrollInstance.totalKm]}"/> <g:message code="sil.behandle.antall.krav" args="${[intervjuerKontrollInstance.kjorebokKrav]}" /></td>
							                        </tr>
													<g:set var="cnt" value="${cnt + 1}" />
							                        <tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
							                            <td><g:message code="sil.behandle.utlegg" />:</td>
							                            <td><g:formatNumber number="${intervjuerKontrollInstance.totalBelop}" format="0.00" /> <g:message code="sil.kr" /> <g:message code="sil.behandle.antall.krav" args="${[intervjuerKontrollInstance.utleggKrav]}" /></td>
							                        </tr>
													<g:if test="${intervjuerKontrollInstance.antallBestodAutomatiskeTester > 0}">
													<g:set var="cnt" value="${cnt + 1}" />
													<tr class="${(cnt % 2) == 0 ? 'odd' : 'even'}">
														<td><g:message code="sil.behandle.bestod.automatiske.kontroller" />:</td>
														<td>${intervjuerKontrollInstance.antallBestodAutomatiskeTester}</td>
													</tr>
												</g:if>
			  				                  </tbody>
			                				</table>
										</td>
										<td>
											<g:if test="${kravProduktNummerListe}">
												<table>
													<thead>
														<tr>
															<th><g:message code="x" default="Produktnummer" /></th>
															<th><g:message code="x" default="Ant. krav" /></th>
															<th><g:message code="x" default="Ant. timekrav" /></th>
															<th><g:message code="x" default="Sum tid" /></th>
															<th><g:message code="x" default="Ant. kjorebok" /></th>
															<th><g:message code="x" default="Sum km" /></th>
															<th><g:message code="x" default="Ant. utlegg" /></th>
															<th><g:message code="x" default="Sum utlegg" /></th>
														</tr>
													</thead>
													<tbody>
														<g:each in="${kravProduktNummerListe}" status="i" var="kravProduktInstance">
															<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
																<td style="text-align: right;">${fieldValue(bean: kravProduktInstance, field: "produktNummer")}</td>
																<td style="text-align: center;">${fieldValue(bean: kravProduktInstance, field: "antallKrav")}</td>
																<td style="text-align: center;">${fieldValue(bean: kravProduktInstance, field: "antallTimeKrav")}</td>
																<td style="text-align: right;"><g:message code="sil.kontroll.tid.info" args="${[kravProduktInstance.totaltTimer, kravProduktInstance.totaltMinutter]}"/></td>
																<td style="text-align: center;">${fieldValue(bean: kravProduktInstance, field: "antallKjorebokKrav")}</td>
																<td style="text-align: right;"><g:message code="sil.kontroll.km.info" args="${[kravProduktInstance.totaltKm]}" /></td>
																<td style="text-align: center;">${fieldValue(bean: kravProduktInstance, field: "antallUtleggKrav")}</td>
																<td style="text-align: right;"><g:formatNumber number="${kravProduktInstance.totaltUtlegg}" format="0.00" /> <g:message code="sil.kr" /></td>
															</tr>
														</g:each>
													</tbody>
												</table>
											</g:if>
										</td>
									</tr>
								</tbody>
							</table>
            			</g:if>
            			<g:else>
            				<g:message code="xx" default="Ingen krav til kontroll" />
            			</g:else>
					</div>
					<div id="tabs-2">
						<g:if test="${feiletAutomatiskeKontrollerListe}">							
							<table>
								<tr>
									<td>
										<h2><g:message code="sil.behandle.feilet.automatiske.tabell.info" args="${[feiletAutomatiskeKontrollerListe.size()]}" /></h2>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>
										<a href="#" onClick="javascript: markerAlleFeilet();" id="mAlleFeilet">Marker alle</a>
										&nbsp;&nbsp;&nbsp;(<g:message code="sil.behandle.velg.krav.info" default="" />)
									</td>
									<td>
										<g:if test="${behandleFeiletKravInstance}">
											<g:form method="post" action="visFeiletKrav" name="visFeiletKravForm">
												<g:hiddenField name="id" value="${behandleFeiletKravInstance.id}" />
												<g:hiddenField name="idForrige" value="${behandleFeiletKravInstance.id}" />
												<g:hiddenField name="idNeste" value="${behandleFeiletKravInstance.id}" />
												<a href="#" onClick="javascript: seKrav('visFeiletKravForm', true);" title="${message(code: 'sil.forrige.krav.tooltip')}"><g:message code="sil.forrige.krav" /></a>
												&nbsp;&nbsp;&nbsp;
												<a href="#" onClick="javascript: seKrav('visFeiletKravForm', false);" title="${message(code: 'sil.neste.krav.tooltip')}"><g:message code="sil.neste.krav" /></a>
											</g:form>
										</g:if>
									</td>
								</tr>
								<tr>
									<td>
										<g:form name="failedForm">
																					
										<table style="width:710px">
											<thead>
												<tr>
													<th style="width:15px" />
													<th style="width:70px"><g:message code="sil.Id" default="Id" /></th>
													<th style="width:70px"><g:message code="sil.Dato" default="Dato" /></th>
													<th style="width:110px"><g:message code="sil.Tidspunkt" default="Tidspunkt" /></th>
													<th style="width:70px"><g:message code="sil.Type" default="Type" /></th>
													<th style="width:130px"><g:message code="sil.Produktnummer" default="Produktnummer" /></th>
													<th style="width:150px"><g:message code="x" default="Kontroll" /></th>
													<th style="width:70px"><g:message code="sil.Verdi" default="Verdi" /></th>
											   </tr>
											</thead>
											
											<tbody>
												<g:each in="${feiletAutomatiskeKontrollerListe}" status="i" var="feiletKravInstance">
													<g:if test="${behandleFeiletKravInstance?.id == feiletKravInstance.id}">
														<g:set var="currentIndex" value="${i}" />
													</g:if>
													
													<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
														<td><g:checkBox name="checkFailed" value="${feiletKravInstance.id}" checked="false" /></td>
														<td><g:link action="visFeiletKrav" id="${feiletKravInstance.id}">${fieldValue(bean: feiletKravInstance, field: "id")}</g:link></td>
														<td><g:formatDate format="dd.MM.yyyy" date="${feiletKravInstance.dato}" /></td>
														<td>
															<g:if test="${feiletKravInstance.kravType == KravType.T}">
																<g:formatDate format="HH:mm" date="${feiletKravInstance?.timeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${feiletKravInstance?.timeforing?.til}" />
															</g:if>
															<g:if test="${feiletKravInstance.kravType == KravType.K}">
																<g:formatDate format="HH:mm" date="${feiletKravInstance?.kjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${feiletKravInstance?.kjorebok?.tilTidspunkt}" />
															</g:if>
														</td>
														<td>
															<g:if test="${feiletKravInstance.kravType == KravType.T}">
																<g:if test="${feiletKravInstance.timeforing?.arbeidsType}">
																	<nobr>${feiletKravInstance.timeforing?.arbeidsType.getGuiShortName()}</nobr>
																</g:if>
																<g:else>
																	<g:message code="sil.time" />
																</g:else>
															</g:if>
															<g:else>
																${feiletKravInstance?.kravType}
															</g:else>
														</td>
														<td>${fieldValue(bean: feiletKravInstance, field: "produktNummer")}</td>
														<td>	
															<g:if test="${feiletKravInstance.automatiskeKontroller?.size() == 1}">
																${feiletKravInstance?.automatiskeKontroller[0]?.kontrollNavn?.encodeAsHTML()}
															</g:if>
															<g:else>
																<g:message code="sil.behandle.feilet.automatiske" args="${[feiletKravInstance.automatiskeKontroller?.size()]}" />
															</g:else>
														</td>
														<td style="text-align: right;">
															<g:if test="${feiletKravInstance.kravType == KravType.U}">
																<g:formatNumber number="${feiletKravInstance.antall}" format="0.00" /> <g:message code="sil.kr" default="kr" />
															</g:if>														
															<g:else>
																<g:if test="${feiletKravInstance.kravType == KravType.T}"> 
																	${fieldValue(bean: feiletKravInstance, field: "antall")} <g:message code="sil.min" />
																</g:if>
																<g:elseif test="${feiletKravInstance.kravType == KravType.K && feiletKravInstance.kjorebok?.erKm()}"> 
																	${fieldValue(bean: feiletKravInstance, field: "antall")}
																	<g:message code="sil.km" />
																</g:elseif>
																<g:elseif test="${feiletKravInstance.kravType == KravType.K && (feiletKravInstance.kjorebok?.transportmiddel == TransportMiddel.LEIEBIL || feiletKravInstance.kjorebok?.transportmiddel == TransportMiddel.GIKK)}">
																	${fieldValue(bean: feiletKravInstance, field: "antall")} <g:message code="sil.min" />
																</g:elseif>
																<g:else>
																	<g:formatNumber number="${feiletKravInstance.antall}" format="0.00" /> <g:message code="sil.kr" default="kr" />
																</g:else>
															</g:else>
														</td>
													</tr>
												</g:each>
											</tbody>
										</table>
										<div class="buttons" style="width:710px">
											<g:hiddenField name="isFailed" value="true" />
									       	<span class="button"><g:actionSubmit class="save" action="godkjennValgteKrav" value="${message(code: 'sil.behandle.godkjenn.valgte', default: 'Godkjenn valgte krav')}" title="${message(code: 'sil.behandle.godkjenn.valgte.tooltip', default: '')}" /></span>
										</div>
										</g:form>
									</td>
									<td>
						              <g:render template="/templates/behandleKravSkjemaTemplate" model="[erFeiletAutomatiskKontroll: true, skjemaNavn: 'kravFeiletSkjema', kravInstance: behandleFeiletKravInstance, opprinneligKravInstance: opprinneligKravInstance, kontrollerFeiletListe: kontrollerFeiletListe, dummySkjemaNavn: 'feiletDummySlettUtlegg']" />
									</td>
								</tr>
							</table>
						</g:if>
						<g:else>
							<g:message code="xx" default="Ingen krav feilet automatiske kontroller" />
						</g:else>
					</div>
					<div id="tabs-3">
						<g:if test="${kravListe}">
							<table style="border: 0px;">
								<tr>
									<td>
										<h2><g:message code="sil.behandle.krav.tabell.info" args="${[kravListe.size()]}" /></h2>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>
										<a href="#" onClick="javascript: markerAlle();" id="mAlle">Marker alle</a>
										&nbsp;&nbsp;&nbsp;(<g:message code="sil.behandle.velg.krav.info" default="" />)
									</td>
									<td>
										<g:if test="${behandleKravInstance}">
											<g:form method="post" action="visKrav" name="visKravForm">
												<g:hiddenField name="id" value="${behandleKravInstance.id}" />
												<g:hiddenField name="idForrige" value="${behandleKravInstance.id}" />
												<g:hiddenField name="idNeste" value="${behandleKravInstance.id}" />
												<a href="#" onClick="javascript: seKrav('visKravForm', true);" title="${message(code: 'sil.forrige.krav.tooltip')}"><g:message code="sil.forrige.krav" /></a>
												&nbsp;&nbsp;&nbsp;
												<a href="#" onClick="javascript: seKrav('visKravForm', false);" title="${message(code: 'sil.neste.krav.tooltip')}"><g:message code="sil.neste.krav" /></a>
											</g:form>
										</g:if>
									</td>
								</tr>
								<tr>
									<td>			
										<g:form name="kravForm">
										
										<table style="width:750px">
											<thead>
												<tr>
													<th style="width:15px" />
													<th style="width:70px"><g:message code="sil.Id" default="Id" /></th>
													<th style="width:70px"><g:message code="sil.Dato" default="Dato" /></th>
													<th style="width:110px"><g:message code="sil.Tidspunkt" default="Tidspunkt" /></th>
													<th style="width:70px"><g:message code="sil.Type" default="Type" /></th>
													<th style="width:110px"><g:message code="sil.Produktnr" default="Produktnr" /></th>
													<th style="width:150px"><g:message code="sil.Status" default="Status" /></th>
													<th style="width:70px"><g:message code="sil.Verdi" default="Verdi" /></th>
											   </tr>
											</thead>
											<tbody>
												<g:set var="currentDate" value="${kravListe[0].dato?.format('ddMMyy')}" />	
												<g:set var="cal" value="${Calendar.getInstance()}"/>
												<g:set var="erHelg" value="false" /> 
												<g:each in="${kravListe}" status="i" var="kravInstance">
													<g:if test="${behandleKravInstance?.id == kravInstance.id}">
														<g:set var="currentIndex" value="${i}" />
													</g:if>
													<g:set var="erEndret" value="false" />
													<g:if test="${kravInstance.opprinneligKrav && kravInstance.kravStatus == KravStatus.TIL_MANUELL_KONTROLL}">
														<g:set var="erEndret" value="true" />
													</g:if>
													<g:if test="${currentDate != kravInstance.dato?.format('ddMMyy') || i == 0}">
														<g:set var="currentDay" value="sil.Mandag" />
														<g:set var="erHelg" value="false" />	
														<g:set var="dummy" value="${cal.setTime(kravInstance.dato)}" />
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY}"><g:set var="currentDay" value="sil.Tirsdag" /></g:if>
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY}"><g:set var="currentDay" value="sil.Onsdag" /></g:if>
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY}"><g:set var="currentDay" value="sil.Torsdag" /></g:if>
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY}"><g:set var="currentDay" value="sil.Fredag" /></g:if>
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY}"><g:set var="currentDay" value="sil.Lordag" /><g:set var="erHelg" value="true" /></g:if>
														<g:if test="${cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY}"><g:set var="currentDay" value="sil.Sondag" /><g:set var="erHelg" value="true" /></g:if>
														<tr>
															<td colspan="9"><h3><g:formatDate format="dd.MM.yyyy" date="${kravInstance.dato}" /> - <g:message code="${currentDay}" /></h3></td>	
														</tr>
													</g:if>
													<g:set var="currentDate" value="${kravInstance.dato?.format('ddMMyy')}" />
													<g:set var="aktiv" value="true" />
													
													<g:if test="${kravInstance.kravType == KravType.U && (kravInstance.utlegg?.utleggType == UtleggType.BOMPENGER || kravInstance.utlegg?.utleggType == UtleggType.TAXI || kravInstance.utlegg?.utleggType == UtleggType.PARKERING || kravInstance.utlegg?.utleggType == UtleggType.BILLETT)}">
														<g:set var="aktiv" value="false" />
													</g:if>												
													<g:if test="${kravInstance.kravType == KravType.T && kravInstance.timeforing?.arbeidsType == ArbeidsType.REISE}">
														<g:set var="aktiv" value="false" />
													</g:if>
													
													<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
														<td>
															<g:if test="${aktiv == 'true'}">
																<g:checkBox name="check" value="${kravInstance.id}" checked="false" />
															</g:if>
														</td>
														<td>
															<g:if test="${aktiv == 'true'}">
																<g:link action="visKrav" id="${kravInstance.id}"><g:formatNumber number="${kravInstance.id}" format="#"/></g:link>
															</g:if>
															<g:else>
																<g:formatNumber number="${kravInstance.id}" format="#"/>
															</g:else>
														</td>
														<td><g:formatDate format="dd.MM.yyyy" date="${kravInstance.dato}" /></td>
														<td>
															<g:if test="${kravInstance.kravType == KravType.T}">
																<g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.til}" />
															</g:if>
															<g:if test="${kravInstance.kravType == KravType.K}">
																<g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.tilTidspunkt}" />
															</g:if>
														</td>
														<td>
															<g:if test="${kravInstance.kravType == KravType.T}">
																<g:if test="${kravInstance.timeforing?.arbeidsType}">
																	<nobr>${kravInstance.timeforing?.arbeidsType.getGuiShortName()}</nobr>
																</g:if>
																<g:else>
																	<g:message code="sil.time" />
																</g:else>
															</g:if>
															<g:elseif test="${kravInstance.kravType == KravType.U}">
																${kravInstance?.kravType} / ${kravInstance?.utlegg?.utleggType?.guiName}
															</g:elseif>
															<g:else>
																${kravInstance?.kravType}
															</g:else>
														</td>
														<td>
															${fieldValue(bean: kravInstance, field: "produktNummer")}
															<g:if test="${erHelg == 'true'}">
																<g:set var="skjema" value="${Skjema.findByDelProduktNummer(kravInstance.produktNummer)}" />
																<g:if test="${kravInstance.kravType == KravType.T && skjema?.overtid}"> (<g:message code="sil.overtid"/>)</g:if>
															</g:if>
														</td>
														<td>
															<g:if test="${erEndret == 'true'}">
																<div style="color: red;"><g:message code="sil.behandle.endret.av.intervjuer" /></div>
															</g:if>
															<g:else>
																<div style="${kravInstance.kravStatus == KravStatus.GODKJENT ? 'color: green;' : (kravInstance.kravStatus == KravStatus.SENDES_TIL_INTERVJUER ? 'color: orange;' : '')}" >${fieldValue(bean: kravInstance, field: "kravStatus")}</div>	
															</g:else>
														</td>
														<td style="text-align: right;">
															<g:if test="${kravInstance.kravType == KravType.U}">																
																<g:if test="${kravInstance.utlegg?.utleggType == UtleggType.KOST_GODT}">
																	---
																</g:if>
																<g:else>
																	<g:formatNumber number="${kravInstance.antall}" format="0.00" /> <g:message code="sil.kr" default="kr" />
																</g:else>																								
															</g:if>														
															<g:else>
																<g:if test="${kravInstance.kravType == KravType.T}"> 
																	${fieldValue(bean: kravInstance, field: "antall")} <g:message code="sil.min" />
																</g:if>
																<g:elseif test="${kravInstance.kravType == KravType.K && kravInstance.kjorebok?.erKm()}"> 
																	${fieldValue(bean: kravInstance, field: "antall")}
																	<g:message code="sil.km" />
																</g:elseif>
																<g:elseif test="${kravInstance.kravType == KravType.K && (kravInstance.kjorebok?.transportmiddel == TransportMiddel.LEIEBIL || kravInstance.kjorebok?.transportmiddel == TransportMiddel.GIKK)}">
																	${fieldValue(bean: kravInstance, field: "antall")} <g:message code="sil.min" />
																</g:elseif>
																<g:else>
																	<g:formatNumber number="${kravInstance.antall}" format="0.00" /> <g:message code="sil.kr" default="kr" />
																</g:else>
															</g:else>
														</td>
													</tr>
												</g:each>
											</tbody>
										</table>
										<div class="buttons" style="width:750px">
									   		<span class="button"><g:actionSubmit class="save" action="godkjennValgteKrav" value="${message(code: 'sil.behandle.godkjenn.valgte', default: 'Godkjenn valgte krav')}" title="${message(code: 'sil.behandle.godkjenn.valgte.tooltip', default: '')}" /></span>
										</div>
										</g:form>
									</td>
									<td>
										<g:render template="/templates/behandleKravSkjemaTemplate" model="[erFeiletAutomatiskKontroll: false, skjemaNavn: 'kravSkjema', kravInstance: behandleKravInstance, opprinneligKravInstance: opprinneligKravInstance, kontrollerFeiletListe: kontrollerFeiletListe, dummySkjemaNavn: 'dummySlettUtlegg']" />
									</td>
								</tr>
							</table>		
						</g:if>
					</div>
            	</div>
            	            	
            	<g:if test="${behandleKravInstance}">
					<script>settForrigeNeste(false, ${behandleKravInstance.id});</script>
				</g:if>
            	
				<g:if test="${behandleFeiletKravInstance}">
					<script>settForrigeNeste(true, ${behandleFeiletKravInstance.id});</script>
				</g:if>
            	            	
            	<script>
	            	<g:if test="${behandleFeiletKravInstance}">
	            		
	     	       		jQuery(function() {
		        			var t = jQuery( "#tabs" ).tabs();
		        			t.tabs('select',1);
		    			});
		    		</g:if>
	            	<g:if test="${behandleKravInstance || intervjuerKontrollInstance.antallKrav < 0}">
	            		
            			jQuery(function() {
		        			var t = jQuery( "#tabs" ).tabs();
		        			t.tabs('select',2);
		    			});
            		</g:if>
            	</script>
            </g:if>
		</div>
		<g:form method="post" name="tilbakestillDummyForm" action="tilbakestillKravFraRettingIntervjuer">
			<g:hiddenField name="id" value="${behandleKravInstance?.id}" />
		</g:form>
		<div id="dialog-til-retting-retur" title="${message(code: 'sil.behandle.krav.tilbakestill.til.retting', default: 'Tilbakekall fra retting intervjuer')}">
			<g:set var="kravDato" value="${behandleKravInstance ? behandleKravInstance.dato.format('dd.MM.yyyy') : ''}" />
			<table style="border: 0;">
				<tr>
					<td>
						<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;" ></span>
					</td>
					<td>
						<g:message code="sil.behandle.krav.tilbakestill.til.retting.info.tekst"  args="${[kravDato]}"/>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="dialog-slett-utlegg-confirm" title="${message(code: 'sil.slett.utlegg.dialog.tittel', default: 'Slett utlegg')}">
			<table style="border: 0;">
				<tr>
					<td>
						<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;" ></span>
					</td>
					<td style="color: red;">
						<g:message code="sil.slett.utlegg.info" default="Er du sikker på at du vil slette kravet og utlegget fra kjøreboken?" />
						<br><br>
						<g:message code="sil.slett.utlegg.info" default="E-post med informasjon om at dette er gjort vil bli sendt til intervjueren." />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>