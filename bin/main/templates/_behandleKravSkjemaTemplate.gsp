<%@ page import="java.util.Calendar" %>
<%@ page import="sil.Krav" %>
<%@ page import="sil.type.*" %>
<%@ page import="siv.type.*" %>
<%@ page import="sil.data.IntervjuerKontroll" %>
<%@ page import="sivadm.Skjema" %>

<g:form name="${skjemaNavn}">
	<table>
		<thead>
			<tr>
				<th colspan="2">
					<g:message code="sil.Krav" default="Krav" />
					<g:if test="${kravInstance}">
						${fieldValue(bean: kravInstance, field: "id")}
						-
	    				<g:if test="${kravInstance.kravType == KravType.T}">
	    					<g:message code="sil.time"/>
						</g:if>
						<g:if test="${kravInstance.kravType == KravType.K}">
							<g:message code="sil.kjorebok"/>	
						</g:if>
						<g:if test="${kravInstance.kravType == KravType.U}">
							<g:message code="sil.utlegg"/>
						</g:if>
					</g:if>
					<g:if test="${opprinneligKravInstance}">
						- <g:message code="sil.behandle.krav.endret.info"/>
					</g:if>
				</th>
			</tr>
		</thead>
		<tbody>
			<g:if test="${opprinneligKravInstance?.silMelding}">
				<tr class="prop">
      				<td valign="top" class="name"><g:message code="sil.behandle.krav.melding.til.intervjuer" default="Melding sendt" />:</td>
					<td valign="top" class="value">
						<b>${opprinneligKravInstance?.silMelding?.tittel}</b><br>
						${opprinneligKravInstance?.silMelding?.melding}
   					</td>
				</tr>
			</g:if>					                        
			<tr class="prop">
				<td valign="top" class="name"><g:message code="krav.dato.label" default="Dato" />:</td>
				<td valign="top" class="value"><g:formatDate date="${kravInstance?.dato}" format="dd.MM.yyyy" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="krav.kravStatus.label" default="Krav Status" />:</td>
				<td valign="top" class="value">
					<g:if test="${kravInstance?.kravStatus == KravStatus.AVVIST}">
						<span style="color: red;">${kravInstance?.kravStatus?.encodeAsHTML()}</span>
					</g:if>
					<g:else>
						${kravInstance?.kravStatus?.encodeAsHTML()}
					</g:else>				
				</td>	
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="krav.produktNummer.label" default="Produktnummer" />:</td>
				<td valign="top" class="value">${kravInstance?.produktNummer}</td>
			</tr>
            <g:if test="${opprinneligKravInstance}">
            	<tr class="prop">
                	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                    <td valign="top" class="value">${opprinneligKravInstance?.produktNummer}</td>
                </tr>
            </g:if>
            <g:if test="${kravInstance}">
            	<g:if test="${kravInstance.kravType == KravType.T}">
                	<tr class="prop">
                    	<td valign="top" class="name"><b><g:message code="sil.time"/>:</b></td>
                     	<td>&nbsp;</td>
                    </tr>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="timeforing.arbeidsType.label" default="Arbeids Type" /></td>
						<td valign="top" class="value">${kravInstance?.timeforing?.arbeidsType?.encodeAsHTML()}</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">${opprinneligKravInstance?.opprinneligTimeforing?.arbeidsType?.encodeAsHTML()}</td>
                     	</tr>
                    </g:if>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="sil.Tidspunkt" default="Tidspunkt" />:</td>
						<td valign="top" class="value">
							<g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.til}" />		 
						</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">
								<g:formatDate format="HH:mm" date="${opprinneligKravInstance?.opprinneligTimeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${opprinneligKravInstance?.opprinneligTimeforing?.til}" />		 
							</td>
                     	</tr>
                     </g:if>
                </g:if>
                <g:if test="${kravInstance.kravType == KravType.K}">
                	<tr class="prop">
                		<td valign="top" class="name"><b><g:message code="sil.kjorebok"/>:</b></td>
                		<td>&nbsp;</td>
                	</tr>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="kjorebok.transportmiddel.label" default="Transportmiddel" />:</td>
						<td valign="top" class="value">${kravInstance?.kjorebok?.transportmiddel?.encodeAsHTML()}</td>
					</tr>
                	<g:if test="${kravInstance?.kjorebok?.erKm()}">
	                	<tr class="prop">
	                		<td valign="top" class="name"><g:message code="sil.Distanse"/>:</td>
	                    	<td>
	                    		${fieldValue(bean: kravInstance, field: "antall")}
	                     		<g:message code="sil.km" default="km"/>
	                     	</td>
	                     </tr>
                	</g:if>
                	<g:else>
	                	<tr class="prop">
	                		<td valign="top" class="name"><g:message code="xx" default="Beløp" />:</td>
	                    	<td>
	                    		<g:if test="${kravInstance?.kjorebok?.transportmiddel == TransportMiddel.FERJE}">
	                    			<g:formatNumber number="${kravInstance?.kjorebok?.utleggFerge?.belop}" format="0.00" />
	                    		</g:if>
	                    		<g:else>
	                    			<g:formatNumber number="${kravInstance?.kjorebok?.utleggBelop?.belop}" format="0.00" />
	                    		</g:else>
	                     		<g:message code="sil.kr" default="kr"/>
	                     	</td>
	                     </tr>
                	</g:else>
                    <g:if test="${opprinneligKravInstance}">
	                   <g:if test="${opprinneligKravInstance?.kjorebok?.erKm()}">
	                   		<tr class="prop">
	                       		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
	                       		<td valign="top" class="value">
									${opprinneligKravInstance?.opprinneligKjorebok?.kjorteKilometer} <g:message code="sil.km" default="km"/>
								</td>
	                   		</tr>
	                   </g:if>
	                   <g:else>
	                   		<tr class="prop">
	                       		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
	                       		<td valign="top" class="value">
									<g:if test="${opprinneligKravInstance?.kjorebok?.transportmiddel == TransportMiddel.FERJE}">
	                    				<g:formatNumber number="${opprinneligKravInstance?.kjorebok?.utleggFerge?.belop}" format="0.00" />
	                    			</g:if>
	                    			<g:else>
	                    				<g:formatNumber number="${opprinneligKravInstance?.kjorebok?.utleggBelop?.belop}" format="0.00" />
	                    			</g:else>
	                     			<g:message code="sil.kr" default="kr"/>
								</td>
	                   		</tr>
	                   </g:else>
	                </g:if>
	                <g:if test="${kravInstance?.kjorebok?.transportmiddel == TransportMiddel.EGEN_BIL || opprinneligKravInstance?.opprinneligKjorebok?.transportmiddel == TransportMiddel.EGEN_BIL}">
	                	<tr class="prop">
		                	<td valign="top" class="name"><g:message code="sil.antall.passasjerer" default="Passasjerer" />:</td>
		                    <td>
		                    	${fieldValue(bean: kravInstance?.kjorebok, field: "antallPassasjerer")}
		                    </td>
		                </tr>
		                <g:if test="${opprinneligKravInstance}">
	                    	<tr class="prop">
	                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
	                        	<td valign="top" class="value">
									${opprinneligKravInstance?.opprinneligKjorebok?.antallPassasjerer} 
								</td>
	                     	</tr>
	                    </g:if>
                    </g:if>
                    <tr class="prop">
						<td valign="top" class="name"><g:message code="sil.Fra" default="Fra" />:</td>
						<td valign="top" class="value">
							${kravInstance?.kjorebok?.fraAdresse?.encodeAsHTML()}, ${kravInstance?.kjorebok?.fraPoststed?.encodeAsHTML()} 
						</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">
								${opprinneligKravInstance?.opprinneligKjorebok?.fraAdresse}, ${opprinneligKravInstance?.opprinneligKjorebok?.fraPoststed} 
							</td>
                     	</tr>
                    </g:if>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="sil.Til" default="Til" />:</td>
						<td valign="top" class="value">
							${kravInstance?.kjorebok?.tilAdresse?.encodeAsHTML()}, ${kravInstance?.kjorebok?.tilPoststed?.encodeAsHTML()} 
						</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">
								${opprinneligKravInstance?.opprinneligKjorebok?.tilAdresse}, ${opprinneligKravInstance?.opprinneligKjorebok?.tilPoststed} 
							</td>
                     	</tr>
                    </g:if>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="sil.Tidspunkt" default="Tidspunkt" />:</td>
						<td valign="top" class="value">
							<g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.tilTidspunkt}" />		 
						</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                    		<td valign="top" class="value">
								<g:formatDate format="HH:mm" date="${opprinneligKravInstance?.opprinneligKjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${opprinneligKravInstance?.opprinneligKjorebok?.tilTidspunkt}" />		 
							</td>
                    	</tr>
                    </g:if>
					<tr class="prop">
						<td valign="top" class="name"><g:message code="kjorebok.merknad.label" default="Merknad" />:</td>
						<td valign="top" class="value">${kravInstance?.kjorebok?.merknad?.encodeAsHTML()}</td>
					</tr>
					<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                    		<td valign="top" class="value">${opprinneligKravInstance?.opprinneligKjorebok?.merknad?.encodeAsHTML()}</td>
                    	</tr>
                    </g:if>
                    <tr class="prop">
                		<td valign="top" class="name"><b><g:message code="xx" default="Utlegg" />:</b></td>
                		<td>&nbsp;</td>
                	</tr>
                	<tr class="prop">
                    	<td valign="top" class="name"><g:message code="xx" default="Bompenger" />:</td>
                    	<g:set var="uBom" value="0" />
                    	<g:if test="${kravInstance?.kjorebok?.utleggBom}">
                    		<g:set var="uBom" value="${kravInstance?.kjorebok?.utleggBom?.belop}" />
                    	</g:if>	
                    	<td valign="top" class="value">
                    		<g:formatNumber number="${uBom}" format="0.00" />
                    		<g:message code="sil.kr" default="kr"/>
                    		<g:if test="${kravInstance?.kjorebok?.utleggBom}">
	                    		(bilagsnr. ${kravInstance?.kjorebok?.utleggBom?.id})
	                    	</g:if>
                    		<g:if test="${kravInstance?.kjorebok?.utleggBom && kravInstance?.kjorebok?.utleggBom?.timeforingStatus != TimeforingStatus.AVVIST}">
                    			&nbsp-&nbsp<g:link onclick="return apneSlettUtleggDialog(${kravInstance?.kjorebok?.utleggBom?.id}, '${dummySkjemaNavn}');" action="avvisUtlegggg" id="${kravInstance?.kjorebok?.utleggBom?.id}" params="${[kravId: kravInstance.id, erFeiletAutomatiskKontroll: erFeiletAutomatiskKontroll]}" title="Slett krav og tilhørende utlegg, e-post om dette blir sendt til intervjueren.">Slett utlegg</g:link>
                    		</g:if>
                    		<g:elseif test="${kravInstance?.kjorebok?.utleggBom?.timeforingStatus == TimeforingStatus.AVVIST}">
                    			&nbsp-&nbsp<span style="color: red;">Kravet er avvist</span>
                    		</g:elseif>
                    	</td>
                    </tr>
                    <tr class="prop">
                    	<td valign="top" class="name"><g:message code="xx" default="Parkering" />:</td>
                    	<g:set var="uPark" value="0" />
                    	<g:if test="${kravInstance?.kjorebok?.utleggParkering}">
                    		<g:set var="uPark" value="${kravInstance?.kjorebok?.utleggParkering?.belop}" />
                    	</g:if>	
                    	<td valign="top" class="value">
                    		<g:formatNumber number="${uPark}" format="0.00" />
                    		<g:message code="sil.kr" default="kr"/>
                    		<g:if test="${kravInstance?.kjorebok?.utleggParkering}">
	                    		(bilagsnr. ${kravInstance?.kjorebok?.utleggParkering?.id})
	                    	</g:if>
                    		<g:if test="${kravInstance?.kjorebok?.utleggParkering && kravInstance?.kjorebok?.utleggParkering?.timeforingStatus != TimeforingStatus.AVVIST}">
                    			&nbsp-&nbsp<g:link onclick="return apneSlettUtleggDialog(${kravInstance?.kjorebok?.utleggParkering?.id}, '${dummySkjemaNavn}');" action="avvisUtleggg" id="${kravInstance?.kjorebok?.utleggParkering?.id}" params="${[kravId: kravInstance.id, erFeiletAutomatiskKontroll: erFeiletAutomatiskKontroll]}" title="Slett krav og tilhørende utlegg, e-post om dette blir sendt til intervjueren.">Slett utlegg</g:link>
                    		</g:if>
                    		<g:elseif test="${kravInstance?.kjorebok?.utleggParkering?.timeforingStatus == TimeforingStatus.AVVIST}">
                    			&nbsp-&nbsp<span style="color: red;">Kravet er avvist</span>
                    		</g:elseif>
                    	</td>
                    </tr>
                    <tr class="prop">
                    	<td valign="top" class="name"><g:message code="xx" default="Ferge" />:</td>
                    	<g:set var="uFerge" value="0" />
                    	<g:if test="${kravInstance?.kjorebok?.erKm() && kravInstance?.kjorebok?.utleggFerge}">
                    		<g:set var="uFerge" value="${kravInstance?.kjorebok?.utleggFerge?.belop}" />
                    	</g:if>	
                    	<td valign="top" class="value">
                    		<g:formatNumber number="${uFerge}" format="0.00" />
                    		<g:message code="sil.kr" default="kr"/>
                    		<g:if test="${kravInstance?.kjorebok?.erKm() && kravInstance?.kjorebok?.utleggFerge}">
	                    		(bilagsnr. ${kravInstance?.kjorebok?.utleggFerge?.id})
	                    	</g:if>
                    		<g:if test="${kravInstance?.kjorebok?.utleggFerge && kravInstance?.kjorebok?.transportmiddel != TransportMiddel.FERJE  && kravInstance?.kjorebok?.utleggFerge?.timeforingStatus != TimeforingStatus.AVVIST}">
                    			&nbsp-&nbsp<g:link onclick="return apneSlettUtleggDialog(${kravInstance?.kjorebok?.utleggFerge?.id}, '${dummySkjemaNavn}');" action="avvisUtleggg" id="${kravInstance?.kjorebok?.utleggFerge?.id}" params="${[kravId: kravInstance.id, erFeiletAutomatiskKontroll: erFeiletAutomatiskKontroll]}" title="Slett krav og tilhørende utlegg, e-post om dette blir sendt til intervjueren.">Slett utlegg</g:link>
                    		</g:if>
                    		<g:elseif test="${kravInstance?.kjorebok?.utleggFerge?.timeforingStatus == TimeforingStatus.AVVIST}">
                    			<g:if test="${kravInstance?.kjorebok?.erKm() && kravInstance?.kjorebok?.utleggFerge}">
                    				&nbsp-&nbsp<span style="color: red;">Kravet er avvist</span>
                    			</g:if>
                    		</g:elseif>
                    	</td>
                    </tr>
                 </g:if>
                 <g:if test="${kravInstance.kravType == KravType.U}">
                 	<tr class="prop">
                    	<td valign="top" class="name"><b><g:message code="sil.utlegg"/>:</b></td>
                    	<td>Bilagsnr. ${kravInstance?.utlegg?.id}</td>
                    </tr>
                    <tr class="prop">
                    	<td valign="top" class="name"><g:message code="sil.belop"/>:</td>
                    	<td>
                    		<g:formatNumber number="${kravInstance?.antall}" format="0.00" />
                    		<g:message code="sil.kr" default="kr"/>
                    	</td>
                    </tr>
                    <g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">
                        		<g:formatNumber number="${opprinneligKravInstance?.opprinneligUtlegg?.belop}" format="0.00" />
                        		<g:message code="sil.kr" default="kr"/>
                        	</td>
                     	</tr>
                    </g:if>
                    <tr class="prop">
                 		<td valign="top" class="name"><g:message code="utlegg.utleggType.label" default="Utlegg Type" />:</td>
                    	<td valign="top" class="value">${kravInstance?.utlegg?.utleggType?.encodeAsHTML()}</td>
              		</tr>
              		<g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                    		<td valign="top" class="value">${opprinneligKravInstance?.opprinneligUtlegg?.utleggType?.encodeAsHTML()}</td>
                    	</tr>
                    </g:if>
              		<tr class="prop">
                  		<td valign="top" class="name"><g:message code="utlegg.spesifisering.label" default="Spesifisering" />:</td>
                    	<td valign="top" class="value">${fieldValue(bean: kravInstance?.utlegg, field: "spesifisering")}</td>
                    </tr>
                    <g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                    		<td valign="top" class="value">${opprinneligKravInstance?.opprinneligUtlegg?.spesifisering}</td>
                    	</tr>
                    </g:if>
					<tr class="prop">
                  		<td valign="top" class="name"><g:message code="utlegg.merknad.label" default="Merknad" />:</td>
                        <td valign="top" class="value">${fieldValue(bean: kravInstance?.utlegg, field: "merknad")}</td>
                    </tr>
                    <g:if test="${opprinneligKravInstance}">
                    	<tr class="prop">
                        	<td valign="top" class="name"><g:message code="sil.behandle.krav.opprinnelig.verdi" default="Opprinnelig verdi" />:</td>
                        	<td valign="top" class="value">${opprinneligKravInstance?.opprinneligUtlegg?.merknad}</td>
                     	</tr>
                     </g:if>
              	</g:if>
				
				<g:if test="${kontrollerFeiletListe}">
					<tr class="prop">
						<td valign="top" class="name"><g:message code="krav.automatiskeKontroller.label" default="Automatiske Kontroller" />:</td>
						<td valign="top" style="text-align: left;" class="value">
							<ul>
								<g:each in="${kontrollerFeiletListe}" var="a">
									<li><g:link controller="automatiskKontroll" action="edit" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
								</g:each>
							</ul>
						</td>
					</tr>
				</g:if>
				                   		
                <tr class="prop">
                	<td valign="top" class="name"><b><g:message code="xxx" default="Til intervjuer" />:</b></td>
                </tr>
                <tr class="prop">
                	<td valign="top" class="name"><g:message code="silMelding.tittel.label" default="Tittel" />:</td>
                	<td valign="top" class="value">
                		<g:textField name="tittel" value="${kravInstance?.silMelding?.tittel}"/>
                    </td>
                </tr>
                <tr class="prop">
                	<td valign="top" class="name"><g:message code="silMelding.melding.label" default="Melding" />:</td>
                    <td valign="top" class="value">
                    	<textArea name="melding" id="melding" cols="40" style="height: 45px">${kravInstance?.silMelding?.melding}</textArea>
                    </td>
                </tr>
          </g:if>
		</tbody>
	</table>
	<g:if test="${kravInstance}">
		<div class="buttons" >
			<g:if test="${erFeiletAutomatiskKontroll}">
				<g:hiddenField name="idFailed" value="${kravInstance?.id}" />
				<span class="button"><g:actionSubmit class="save" action="godkjennKravTilBehandling" value="${message(code: 'sil.behandle.krav.godkjenn', default: 'Godkjenn')}" title="${message(code: 'sil.behandle.krav.godkjenn.tooltip', default: '')}" /></span>
	       		<span class="button"><g:actionSubmit class="save" action="endreKravTilBehandling" value="${message(code: 'sil.behandle.krav.endre', default: 'Endre')}" title="${message(code: 'sil.behandle.krav.endre.tooltip', default: '')}" /></span>
	       		<span class="button"><g:actionSubmit class="save" action="sendTilbakeKravTilBehandling" value="${message(code: 'sil.behandle.krav.send.tilbake', default: 'Send tilbake')}" title="${message(code: 'sil.behandle.krav.send.tilbake.tooltip', default: '')}" /></span>
	       		<span class="button"><g:actionSubmit class="save" action="avviseKravTilBehandling" value="${message(code: 'sil.behandle.krav.avvis', default: 'Avvis krav')}" title="${message(code: 'sil.behandle.krav.avvis.tooltip', default: '')}" /></span>
			</g:if>
			<g:else>
				<g:hiddenField name="id" value="${kravInstance?.id}" />
				<g:if test="${kravInstance?.kravStatus == KravStatus.AVVIST}">
					<span class="button"><g:actionSubmit class="save" action="endreKravTilBehandling" value="${message(code: 'sil.behandle.krav.endre', default: 'Endre')}" title="${message(code: 'sil.behandle.krav.endre.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="godkjennKravTilBehandling" value="${message(code: 'sil.behandle.krav.godkjenn', default: 'Godkjenn')}" title="${message(code: 'sil.behandle.krav.godkjenn.tooltip', default: '')}" /></span>
				</g:if>
				<g:elseif test="${kravInstance?.kravStatus == KravStatus.GODKJENT}">
					<span class="button"><g:actionSubmit class="save" action="endreKravTilBehandling" value="${message(code: 'sil.behandle.krav.endre', default: 'Endre')}" title="${message(code: 'sil.behandle.krav.endre.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="avviseKravTilBehandling" value="${message(code: 'sil.behandle.krav.avvis', default: 'Avvis krav')}" title="${message(code: 'sil.behandle.krav.avvis.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="angreGodkjent" value="Angre godkjent" title="Setter status tilbake til manuell kontroll" /></span>
				</g:elseif>
				<g:elseif test="${kravInstance?.kravStatus == KravStatus.BESTOD_AUTOMATISK_KONTROLL}">
					<span class="button"><g:actionSubmit class="save" action="endreKravTilBehandling" value="${message(code: 'sil.behandle.krav.endre', default: 'Endre')}" title="${message(code: 'sil.behandle.krav.endre.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="godkjennKravTilBehandling" value="${message(code: 'sil.behandle.krav.godkjenn', default: 'Godkjenn')}" title="${message(code: 'sil.behandle.krav.godkjenn.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="sendTilbakeKravTilBehandling" value="${message(code: 'sil.behandle.krav.send.tilbake', default: 'Send tilbake')}" title="${message(code: 'sil.behandle.krav.send.tilbake.tooltip', default: '')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="avviseKravTilBehandling" value="${message(code: 'sil.behandle.krav.avvis', default: 'Avvis krav')}" title="${message(code: 'sil.behandle.krav.avvis.tooltip', default: '')}" /></span>
				</g:elseif>
				<g:elseif test="${kravInstance?.kravStatus == KravStatus.INAKTIV}">
					
				</g:elseif>
				<g:elseif test="${kravInstance?.kravStatus != KravStatus.TIL_RETTING_INTERVJUER}">
					<g:if test="${kravInstance?.kravStatus == KravStatus.SENDES_TIL_INTERVJUER}">
						<span class="button"><g:actionSubmit class="save" action="tilbakestillKravTilBehandling" value="${message(code: 'sil.behandle.krav.tilbakestill', default: 'Tilbakestill')}" title="${message(code: 'sil.behandle.krav.tilbakestill.tooltip', default: 'Tilbakestill krav til opprinnelig status')}" /></span>
					</g:if>
					<g:else>
						<span class="button"><g:actionSubmit class="save" action="godkjennKravTilBehandling" value="${message(code: 'sil.behandle.krav.godkjenn', default: 'Godkjenn')}" title="${message(code: 'sil.behandle.krav.godkjenn.tooltip', default: '')}" /></span>
						<span class="button"><g:actionSubmit class="save" action="endreKravTilBehandling" value="${message(code: 'sil.behandle.krav.endre', default: 'Endre')}" title="${message(code: 'sil.behandle.krav.endre.tooltip', default: '')}" /></span>
						<span class="button"><g:actionSubmit class="save" action="sendTilbakeKravTilBehandling" value="${message(code: 'sil.behandle.krav.send.tilbake', default: 'Send tilbake')}" title="${message(code: 'sil.behandle.krav.send.tilbake.tooltip', default: '')}" /></span>
						<span class="button"><g:actionSubmit class="save" action="avviseKravTilBehandling" value="${message(code: 'sil.behandle.krav.avvis', default: 'Avvis krav')}" title="${message(code: 'sil.behandle.krav.avvis.tooltip', default: '')}" /></span>
					</g:else>
				</g:elseif>
				<g:elseif test="${kravInstance?.kravStatus == KravStatus.TIL_RETTING_INTERVJUER}">
					<span class="button"><g:actionSubmit class="save" action="xxx" onclick="return apneTilbakestillDialog();" title="${message(code: 'sil.behandle.krav.tilbakestill.til.retting.tooltip')}" value="${message(code: 'sil.behandle.krav.tilbakestill.til.retting')}" /></span>
				</g:elseif>
			</g:else>
		</div>
	</g:if>
</g:form>

<!-- Dummy form som brukes til sletting av utlegg fra kjørebok commites fra bekreft slett utlegg dialog -->
<g:form method="post" name="${dummySkjemaNavn}" action="slettKjorebokUtlegg" >
	<g:hiddenField name="kravId" value="${kravInstance?.id}"/>
	<g:hiddenField name="erFeiletAutomatiskKontroll" value="${erFeiletAutomatiskKontroll}" />
</g:form>