<%@ page import="sil.type.*" %>
<%@ page import="siv.type.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'krav.label', default: 'Krav')}" />
        <title><g:message code="sil.krav.tittel" default="Vis krav" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="sil.krav.tittel" default="Vis krav" /></h1>
            
            <table style="width: 600px;">
            	<thead>
	            	<tr>
	            		<th colspan="3">
	            			<g:message code="sil.Krav" default="Krav" />
	            			<g:if test="${kravInstance}">
	            				${fieldValue(bean: kravInstance, field: "id")} -
	            				${kravInstance?.kravType}
	            				<g:if test="${nyttKrav}">
	            					- <g:message code="sil.behandle.krav.endret.info"/>
	            					(Nytt krav har id ${nyttKrav.id})
	            				</g:if>
	            			</g:if>
	            		</th>
	            	</tr>
	            	<g:if test="${nyttKrav}">
		            	<tr>
		            		<th/>
		            		<th>Verdi opprinnelig krav</th>
		            		<th>Verdi rettet krav</th>
		            	</tr>
	            	</g:if>
	          	</thead>
           		<tbody>
           			<g:if test="${kravInstance?.silMelding}">
                   		<tr class="prop">
                           	<td valign="top" class="name"><g:message code="sil.behandle.krav.melding.til.intervjuer" default="Melding sendt" />:</td>
                           	<td valign="top">
                            	<b>${kravInstance?.silMelding?.tittel}</b><br>
								${kravInstance?.silMelding?.melding}
                           	</td>
                           	<g:if test="${nyttKrav}">
		            			<td valign="top">
                            		<b>${nyttKrav?.silMelding?.tittel}</b><br>
									${nyttKrav?.silMelding?.melding}
                           		</td>
	            			</g:if>
                       	</tr>
                   	</g:if>
                   	<tr class="prop">
						<td valign="top" class="name"><g:message code="krav.dato.label" default="Dato" />:</td>
						<td valign="top">
							<g:formatDate date="${kravInstance?.dato}" format="dd.MM.yyyy" />
						</td>
						<g:if test="${nyttKrav}">
		            		<td valign="top">&nbsp;</td>
	            		</g:if>
					</tr>
                   	<tr class="prop">
                       	<td valign="top" class="name"><g:message code="krav.kravStatus.label" default="Krav Status" />:</td>
                       	<td valign="top">${kravInstance?.kravStatus?.encodeAsHTML()}</td>
                       	<g:if test="${nyttKrav}">
		            		<td valign="top">${nyttKrav?.kravStatus?.encodeAsHTML()}</td>
	            		</g:if>
                    </tr>
                    <tr class="prop">
                       	<td valign="top" class="name"><g:message code="krav.produktNummer.label" default="Produktnummer" />:</td>
                       	<td valign="top">${kravInstance?.produktNummer}</td>
                       	<g:if test="${nyttKrav}">
	                       	<td valign="top">${nyttKrav?.produktNummer}</td>
	           			</g:if>
                  	</tr>
                   	<g:if test="${kravInstance.kravType == KravType.T}">
                    	<tr class="prop">
                    		<td valign="top" class="name"><b><g:message code="sil.time"/>:</b></td>
                    		<td>&nbsp;</td>
                    		<g:if test="${nyttKrav}">
	                       		<td>&nbsp;</td>
	           				</g:if>
                    	</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="timeforing.arbeidsType.label" default="Arbeids Type" /></td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									${kravInstance?.opprinneligTimeforing?.arbeidsType?.encodeAsHTML()}
								</g:if>
								<g:else>
									${kravInstance?.timeforing?.arbeidsType?.encodeAsHTML()}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">${nyttKrav?.timeforing?.arbeidsType?.encodeAsHTML()}</td>
	           				</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.Tidspunkt" default="Tidspunkt" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									<g:formatDate format="HH:mm" date="${kravInstance?.opprinneligTimeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.opprinneligTimeforing?.til}" />
								</g:if>
								<g:else>
									<g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.timeforing?.til}" />
								</g:else>		 
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">
									<g:formatDate format="HH:mm" date="${nyttKrav?.timeforing?.fra}" /> - <g:formatDate format="HH:mm" date="${nyttKrav?.timeforing?.til}" />		 
								</td>
	           				</g:if>
						</tr>
					</g:if>
					<g:if test="${kravInstance.kravType == KravType.K}">
						<tr class="prop">
                    		<td valign="top" class="name"><b><g:message code="sil.kjorebok"/>:</b></td>
                    		<td>&nbsp;</td>
                    		<g:if test="${nyttKrav}">
	                       		<td>&nbsp;</td>
	           				</g:if>
                    	</tr>
                    	<tr class="prop">
							<td valign="top" class="name"><g:message code="kjorebok.transportmiddel.label" default="Transportmiddel" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
	                       			${kravInstance?.opprinneligKjorebok?.transportmiddel?.encodeAsHTML()}
	           					</g:if>
								<g:else>
									${kravInstance?.kjorebok?.transportmiddel?.encodeAsHTML()}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">${nyttKrav?.kjorebok?.transportmiddel?.encodeAsHTML()}</td>
	           				</g:if>
						</tr>
						
						<g:if test="${kravInstance?.kjorebok?.erKm()}">
		                	<tr class="prop">
		                		<td valign="top" class="name"><g:message code="sil.distanse.kr" default="Distanse/beløp" />:</td>
		                    	<td>
		                    		${fieldValue(bean: kravInstance, field: "antall")}
		                     		<g:message code="sil.km" default="km"/>
		                     	</td>
		                     	<g:if test="${nyttKrav}">
			                     	<g:if test="${nyttKrav?.kjorebok?.erKm()}">
					                    <td>
					                    	${fieldValue(bean: nyttKrav, field: "antall")}
					                     	<g:message code="sil.km" default="km"/>
					                     </td>
				                	</g:if>
				                	<g:else>
					                    <td>
					                    	<g:if test="${nyttKrav?.kjorebok?.transportmiddel == TransportMiddel.FERJE}">
					                    		<g:formatNumber number="${nyttKrav?.kjorebok?.utleggFerge?.belop}" format="0.00" />
					                    	</g:if>
					                    	<g:else>
					                    		<g:formatNumber number="${nyttKrav?.kjorebok?.utleggBelop?.belop}" format="0.00" />
					                    	</g:else>
					                    		<g:message code="sil.kr" default="kr"/>
					                    </td>
				                	</g:else>
		                     	</g:if>
		                     </tr>
	                	</g:if>
	                	<g:else>
		                	<tr class="prop">
		                		<td valign="top" class="name"><g:message code="xx" default="Distanse/beløp" />:</td>
		                    	<td>
		                    		<g:if test="${kravInstance?.kjorebok?.transportmiddel == TransportMiddel.FERJE}">
		                    			<g:formatNumber number="${kravInstance?.kjorebok?.utleggFerge?.belop}" format="0.00" />
		                    		</g:if>
		                    		<g:else>
		                    			<g:formatNumber number="${kravInstance?.kjorebok?.utleggBelop?.belop}" format="0.00" />
		                    		</g:else>
		                     		<g:message code="sil.kr" default="kr"/>
		                     	</td>
		                     	<g:if test="${nyttKrav}">
		                     		<g:if test="${nyttKrav?.kjorebok?.erKm()}">
				                    	<td>
				                    		${fieldValue(bean: nyttKrav, field: "antall")}
				                     		<g:message code="sil.km" default="km"/>
				                     	</td>
				                	</g:if>
				                	<g:else>
					                    <td>
					                    	<g:if test="${nyttKrav?.kjorebok?.transportmiddel == TransportMiddel.FERJE}">
					                    		<g:formatNumber number="${nyttKrav?.kjorebok?.utleggFerge?.belop}" format="0.00" />
					                    	</g:if>
					                    	<g:else>
					                    		<g:formatNumber number="${nyttKrav?.kjorebok?.utleggBelop?.belop}" format="0.00" />
					                    	</g:else>
					                    	<g:message code="sil.kr" default="kr"/>
					                    </td>
				                	</g:else>
		                     	</g:if>
		                     </tr>
	                	</g:else>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.Fra" default="Fra" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									${kravInstance?.opprinneligKjorebok?.fraAdresse?.encodeAsHTML()}, ${kravInstance?.opprinneligKjorebok?.fraPoststed?.encodeAsHTML()}
								</g:if>
								<g:else>
									${kravInstance?.kjorebok?.fraAdresse?.encodeAsHTML()}, ${kravInstance?.kjorebok?.fraPoststed?.encodeAsHTML()}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">
									${nyttKrav?.kjorebok?.fraAdresse?.encodeAsHTML()}, ${nyttKrav?.kjorebok?.fraPoststed?.encodeAsHTML()}
	                       		</td>
	           				</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.Til" default="Til" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									${kravInstance?.opprinneligKjorebok?.tilAdresse?.encodeAsHTML()}, ${kravInstance?.opprinneligKjorebok?.tilPoststed?.encodeAsHTML()}
								</g:if>
								<g:else>
									${kravInstance?.kjorebok?.tilAdresse?.encodeAsHTML()}, ${kravInstance?.kjorebok?.tilPoststed?.encodeAsHTML()}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">
									${nyttKrav?.kjorebok?.tilAdresse?.encodeAsHTML()}, ${nyttKrav?.kjorebok?.tilPoststed?.encodeAsHTML()}
	                       		</td>
	           				</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.Tidspunkt" default="Tidspunkt" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									<g:formatDate format="HH:mm" date="${kravInstance?.opprinneligKjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.opprinneligKjorebok?.tilTidspunkt}" />
								</g:if>
								<g:else>
									<g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${kravInstance?.kjorebok?.tilTidspunkt}" />
								</g:else>	
							</td>
							<g:if test="${nyttKrav}">
								<td valign="top">
									<g:formatDate format="HH:mm" date="${nyttKrav?.kjorebok?.fraTidspunkt}" /> - <g:formatDate format="HH:mm" date="${nyttKrav?.kjorebok?.tilTidspunkt}" />		 
								</td>
							</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.AntallPassasjerer" default="Antall passasjerer"/>:</td>
							<td>
								<g:if test="${nyttKrav}">
									${kravInstance.opprinneligKjorebok?.antallPassasjerer}
								</g:if>
								<g:else>
									${kravInstance?.kjorebok?.antallPassasjerer}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
	                       		<td valign="top">
	                       			${nyttKrav?.kjorebok?.antallPassasjerer}
	                       		</td>
	           				</g:if>
						</tr>
						
						<tr class="prop">
							<td valign="top" class="name"><g:message code="kjorebok.merknad.label" default="Merknad" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									${kravInstance?.opprinneligKjorebok?.merknad?.encodeAsHTML()}
								</g:if>
								<g:else>
									${kravInstance?.kjorebok?.merknad?.encodeAsHTML()}
								</g:else>				
							</td>
							<g:if test="${nyttKrav}">
								<td valign="top">
									${nyttKrav?.kjorebok?.merknad?.encodeAsHTML()}		 
								</td>
							</g:if>
						</tr>
					</g:if>
					<g:if test="${kravInstance.kravType == KravType.U}">
						<tr class="prop">
							<td valign="top" class="name"><b><g:message code="sil.utlegg"/>:</b></td>
							<td>&nbsp;</td>
							<g:if test="${nyttKrav}">
								<td>&nbsp;</td>
							</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="sil.belop"/>:</td>
							<td>
								${fieldValue(bean: kravInstance, field: "antall")}
								<g:message code="sil.kr" default="kr"/>
							</td>
							<g:if test="${nyttKrav}">
								<td>
									${fieldValue(bean: nyttKrav, field: "antall")}
									<g:message code="sil.kr" default="kr"/>
								</td>
							</g:if>
						</tr>
						<tr class="prop">
                        	<td valign="top" class="name"><g:message code="utlegg.utleggType.label" default="Utlegg Type" />:</td>
							<td valign="top">
								<g:if test="${nyttKrav}">
									${kravInstance?.opprinneligUtlegg?.utleggType?.encodeAsHTML()}
								</g:if>
								<g:else>
									${kravInstance?.utlegg?.utleggType?.encodeAsHTML()}
								</g:else>
							</td>
							<g:if test="${nyttKrav}">
								<td valign="top">
									${nyttKrav?.utlegg?.utleggType?.encodeAsHTML()}
								</td>	
							</g:if>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><g:message code="utlegg.spesifisering.label" default="Spesifisering" />:</td>
	                        <td valign="top">
	                        	<g:if test="${nyttKrav}">
									${fieldValue(bean: kravInstance?.opprinneligUtlegg, field: "spesifisering")}
								</g:if>
								<g:else>
									${fieldValue(bean: kravInstance?.utlegg, field: "spesifisering")}
								</g:else>
	                        </td>
	                        <g:if test="${nyttKrav}">
								<td valign="top">
									${fieldValue(bean: nyttKrav?.utlegg, field: "spesifisering")}
								</td>	
							</g:if>
	                    </tr>
	                    <tr class="prop">
							<td valign="top" class="name"><g:message code="utlegg.merknad.label" default="Merknad" />:</td>
							<td valign="top">
	                        	<g:if test="${nyttKrav}">
									${fieldValue(bean: kravInstance?.opprinneligUtlegg, field: "merknad")}
								</g:if>
								<g:else>
									${fieldValue(bean: kravInstance?.utlegg, field: "merknad")}
								</g:else>
	                        </td>
	                        <g:if test="${nyttKrav}">
								<td valign="top">
									${fieldValue(bean: nyttKrav?.utlegg, field: "merknad")}
								</td>	
							</g:if>
						</tr>
					</g:if>
           		</tbody>
           	</table>
           	<br/>
            <g:link action="searchResult">&lt;&lt; <g:message code="sil.adm.tilbake.til.sok" default="Tilbake til søk" /></g:link>
        </div>
    </body>
</html>