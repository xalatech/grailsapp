<%@ page import="sivadm.Timeforing" %>
<%@ page import="siv.type.ArbeidsType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'timeforing.label', default: 'Timeforing')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.resizable.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.position.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.mouse.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.draggable.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.dialog.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.core.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.widget.js')}"></script>
        <script>
			jQuery(function() {
				// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
				jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
	
				jQuery( "#dialog-confirm" ).dialog({
					autoOpen: false,
					resizable: false,
					height:230,
					modal: true,
					buttons: {
						"${message(code: 'sivadm.timeregistrering.send.inn')}": function() {
							submitForm("sendInnDummyForm");
							jQuery( this ).dialog( "close" );
						},
						"${message(code: 'sil.avbryt')}": function() {
							jQuery( this ).dialog( "close" );
						}
					}
				});
			});

			function apneSendInnDialog() {
				jQuery( "#dialog-confirm" ).dialog( "open" );
				return false;
			}
		</script>
    </head>
    <body>
        
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            <g:if test="${ flash.errorMessage }">
				<div class=errors>&nbsp;${flash.errorMessage}</div>
			</g:if>
            
            <br/>
            
            <g:link action="velgDato"><< Tilbake til hovedsiden</g:link>
            <br/>
            <br/>
            
            
            <table id="oppsummering" class="infoBody">
				<tbody>
					<tr>
						<td>
							<g:link action="velgForrigeDato"><< Forrige</g:link>
						</td>
						<td>Initialer: ${intervjuerInstance?.initialer}</td>
						<td>Dato:  <g:formatDate date="${dato}" locale="no" type="date" style="MEDIUM"/></td>
						<td>Totalt antall timer: ${antallTimer}
						</td>
						<td>Totalt antall km: ${antallKilometer}
						</td>
						<td>Totalt utlegg: <g:formatNumber number="${belop}" format="0.00" /> <g:message code="sil.kr" default="kr" />
						</td>
						<td>
						Sendt inn: <g:formatBoolean boolean="${sendtInn}" true="Ja" false="Nei"/>
						</td>
						<td>
							<g:link action="velgNesteDato">Neste &gt;&gt;</g:link>
						</td>
					</tr>
				</tbody>
			</table>
            
            <h1>Kjørebok</h1>
            
            <div class="list">
            
            <g:if test="${kjorebokInstanceList.size() == 0}">
            	<br/>
            	<p>Ingen kjørebokregistreringer er lagt inn.</p>
            	<br/>
            	<br/>
            </g:if>
            <g:else>
            	
                <table>
                    <thead>
                        <tr>
                        	<g:if test="${erReturFraKontroll}">
                     			<th><g:message code="timeforing.melding.fra.kontroll" default="Melding fra kontroll" /></th>
                     		</g:if>
                        	                        	
                        	<g:sortableColumn property="fraTidspunkt" title="${message(code: 'kjorebok.fraTidspunkt.label', default: 'Fra')}" />
                        	
                        	<g:sortableColumn property="tilTidspunkt" title="${message(code: 'kjorebok.fraTidspunkt.label', default: 'Til')}" />
                        	
                            <g:sortableColumn property="fraAdresse" title="${message(code: 'kjorebok.fraAdresse.label', default: 'Fra adresse')}" />
                        
                            <g:sortableColumn property="fraPoststed" title="${message(code: 'kjorebok.fraPoststed.label', default: 'Fra poststed')}" />
                            
                            <g:sortableColumn property="intervjuobjekt" title="${message(code: 'kjorebok.intervjuobjekt.label', default: 'Intervjuobjekt')}" />
                            
                            <g:sortableColumn property="merknad" title="${message(code: 'kjorebok.merknad.label', default: 'Merknad')}" />
                            
                            <g:sortableColumn property="tilAdresse" title="${message(code: 'kjorebok.tilAdresse.label', default: 'Til adresse')}" />
                        
                            <g:sortableColumn property="tilPoststed" title="${message(code: 'kjorebok.tilPoststed.label', default: 'Til poststed')}" />
                            
                            <g:sortableColumn property="produktNummer" title="${message(code: 'kjorebok.produktNummer.label', default: 'Produktnr')}" />
                            
                            <g:sortableColumn property="kjorteKilometer" title="${message(code: 'kjorebok.kjorteKilometer.label', default: 'Km')}" />
                            
                            <g:sortableColumn property="transportmiddel" title="${message(code: 'kjorebok.transportmiddel.label', default: 'Transportmiddel')}" />
                            
                            <g:sortableColumn property="timeforingStatus" title="${message(code: 'kjorebok.timeforingStatus.label', default: 'Status')}" />
                     
                     		<g:if test="${!sendtInn}">        
                            	<th/>
                     		</g:if>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${kjorebokInstanceList}" status="i" var="kjorebokInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<g:if test="${erReturFraKontroll}">
                     			<td>
                     				<g:if test="${kjorebokInstance.silMelding}">
                     					<span style="color: red;" title="${fieldValue(bean: kjorebokInstance.silMelding, field: "melding")}">${fieldValue(bean: kjorebokInstance.silMelding, field: "tittel")}</span>
                     				</g:if>
                     			</td>
                     		</g:if>
                     		
                        	<td><g:formatDate date="${kjorebokInstance.fraTidspunkt}" format="HH:mm"/></td>
                        	
                        	<td><g:formatDate date="${kjorebokInstance.tilTidspunkt}" format="HH:mm"/></td>
                        	
                            <td>${fieldValue(bean: kjorebokInstance, field: "fraAdresse")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "fraPoststed")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "intervjuobjekt")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "merknad")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "tilAdresse")}</td>
                        
                            <td>${fieldValue(bean: kjorebokInstance, field: "tilPoststed")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "produktNummer")}</td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "kjorteKilometer")}</td>
                            
                            <td>
                            	${fieldValue(bean: kjorebokInstance, field: "transportmiddel")}
                            	<g:if test="${kjorebokInstance.antallPassasjerer > 0}">
                            		<g:message code="sivadm.kjorebok.passasjer.forkortet" args="${[kjorebokInstance.antallPassasjerer]}"  />
                            	</g:if>
                            </td>
                            
                            <td>${fieldValue(bean: kjorebokInstance, field: "timeforingStatus")}</td>
                     		
                  	 <g:if test="${!sendtInn}">          
                  		 <g:if test="${kjorebokInstance.avvist()}">
                            	<td />
                            </g:if>
                            <g:else>
	                  	 	
	                            <td>
	                            	<g:link controller="kjorebok" action="edit" id="${kjorebokInstance.id}"><g:redigerIkon /></g:link>
	                            	&nbsp;&nbsp;
	                            	<g:link controller="kjorebok" action="delete" id="${kjorebokInstance.id}"><g:slettIkon /></g:link>
	                            	                            
	                            	<g:if test="${!kjorebokInstance.godkjent()}">
	                            		&nbsp;&nbsp;
	                            		<g:link controller="kjorebok" action="godkjenn" id="${kjorebokInstance.id}"><g:godkjennIkon /></g:link>
                            		</g:if>
                            	</td>
                            </g:else>
                   </g:if>     
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <br/>
                
                </g:else>
             
             <g:if test="${!sendtInn}">    
                <span class="menuButton">
                	<g:link class="create" controller="kjorebok" action="create">Legg til kjørebok</g:link>
                </span>
             </g:if>   
                
            </div>	
            
            
            
            
            <br/>
            
            <h1>Utlegg / kostgodtgjørelse</h1>
            <div class="list">
            	<g:if test="${utleggInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen registrerte utlegg er lagt inn.</p>
            		<br/>
            		<br/>
            	</g:if>
            	
            	<g:else>
            	
            		<table>
                    <thead>
                        <tr>
                       		<g:if test="${erReturFraKontroll}">
                     			<th><g:message code="timeforing.melding.fra.kontroll" default="Melding fra kontroll" /></th>
                     		</g:if>
                     		
                        	<th>Bilagsnr.</th>
                        	
                        	<g:sortableColumn property="produktNummer" title="${message(code: 'utlegg.produktNummer.label', default: 'Produktnummer')}" />
                        	
                        	<g:sortableColumn property="utleggType" title="${message(code: 'utlegg.utleggType.label', default: 'Utleggstype')}" />
                        	
                        	<g:sortableColumn property="spesifisering" title="${message(code: 'utlegg.utleggType.label', default: 'Spesifisering')}" />
                        	
                        	<g:sortableColumn property="merknad" title="${message(code: 'utlegg.merknad.label', default: 'Merknad')}" />
                        	
                            <g:sortableColumn property="belop" title="${message(code: 'utlegg.belop.label', default: 'Beløp')}" />
                            
                            <g:sortableColumn property="timeforingStatus" title="${message(code: 'utlegg.timeforingStatus.label', default: 'Status')}" />
                    
                    		<g:if test="${!sendtInn}">     
                            	<th/>
                    		</g:if>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${utleggInstanceList}" status="i" var="utleggInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<g:if test="${erReturFraKontroll}">
                     			<td>
                     				<g:if test="${utleggInstance.silMelding}">
                     					<span style="color: red;" title="${fieldValue(bean: utleggInstance.silMelding, field: "melding")}">${fieldValue(bean: utleggInstance.silMelding, field: "tittel")}</span>
                     				</g:if>
                     			</td>
                     		</g:if>
                        	<td>${fieldValue(bean: utleggInstance, field: "id")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "produktNummer")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "utleggType")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "spesifisering")}</td>
                        	
                        	<td>${fieldValue(bean: utleggInstance, field: "merknad")}</td>
                        	
                            <g:if test="${utleggInstance.belop == 0}">
                            	<td>---</td>		
                            </g:if>
                            <g:else>
                            	<td><g:formatNumber number="${utleggInstance.belop}" format="0.00" /> <g:message code="sil.kr" default="kr" /></td>
                            </g:else>
                            	
                            
                            
                            <td>${fieldValue(bean: utleggInstance, field: "timeforingStatus")}</td>                            
                    
                    <g:if test="${!sendtInn}">    
                    		<g:if test="${utleggInstance.avvist()}">
                            	<td />
                            </g:if>
                            <g:elseif test="${!utleggInstance.harKjorebok}">
	                            <td>
	                            	<g:link controller="utlegg" action="edit" id="${utleggInstance.id}"><g:redigerIkon /></g:link>
	                        		&nbsp;&nbsp;
	                        		<g:link controller="utlegg" action="delete" id="${utleggInstance.id}"><g:slettIkon /></g:link>
	                        	
	                        		<g:if test="${!utleggInstance.godkjent()}">
	                        			&nbsp;&nbsp;
	                        			<g:link controller="utlegg" action="godkjenn" id="${utleggInstance.id}"><g:godkjennIkon /></g:link>
	                        		</g:if>
	                        	</td>
                        	</g:elseif>
                        	<g:else>
                        		<td />
                        	</g:else>
                    </g:if>
                    
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            	
            	</g:else>
            	
                
                <br/>
                <g:if test="${!sendtInn}"> 
                	<span class="menuButton"><g:link class="create" controller="utlegg" action="create">Legg til utlegg / kostgodtgjørelse</g:link></span>
                </g:if> 
            </div>
            
            <br/>
            
            <h1>Arbeidstid</h1>
            <div class="list">
            	
            	<g:if test="${timeforingInstanceList.size() == 0}">
            		<br/>
            		<p>Ingen registrert arbeidstid er lagt inn.</p>
            		<br/>
            		<br/>
            	</g:if>
            	
            	<g:else>
            	
            	<table>
                    <thead>
                        <tr>
                        	<g:if test="${erReturFraKontroll}">
                     			<th><g:message code="timeforing.melding.fra.kontroll" default="Melding fra kontroll" /></th>
                     		</g:if>
                     		
                            <g:sortableColumn property="fra" title="${message(code: 'timeforing.fra.label', default: 'Fra')}" />
                            
                            <g:sortableColumn property="til" title="${message(code: 'timeforing.til.label', default: 'Til')}" />
                        
                            <g:sortableColumn property="produktNummer" title="${message(code: 'timeforing.produktNummer.label', default: 'Produktnummer')}" />
                                
                            <g:sortableColumn property="arbeidsType" title="${message(code: 'timeforing.arbeidsType.label', default: 'Arbeidstype')}" />
                            
                            <g:sortableColumn property="timeforingStatus" title="${message(code: 'timeforing.timeforingStatus.label', default: 'Status')}" />
                        
                        	<g:if test="${!sendtInn}">    
                            	<th/>
                        	</g:if>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${timeforingInstanceList}" status="i" var="timeforingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        	<g:if test="${erReturFraKontroll}">
                     			<td><g:if test="${timeforingInstance.silMelding}"><span style="color: red;" title="${fieldValue(bean: timeforingInstance.silMelding, field: "melding")}">${fieldValue(bean: timeforingInstance.silMelding, field: "tittel")}</span></g:if></td>
                     		</g:if>
                            
                            <td><g:formatDate date="${timeforingInstance.fra}" format="HH:mm"/></td>
                            
                            <td><g:formatDate date="${timeforingInstance.til}" format="HH:mm"/></td>
                        
                            <td>${fieldValue(bean: timeforingInstance, field: "produktNummer")}</td>
                         
                            <td>${fieldValue(bean: timeforingInstance, field: "arbeidsType")}</td>
                            
                            <td>${fieldValue(bean: timeforingInstance, field: "timeforingStatus")}</td>
                        
                        	<g:if test="${!sendtInn}">    
                            	<g:if test="${timeforingInstance.avvist()}">
                            		<td />
                            	</g:if>
                            <g:else>
	                            <td>
	                            	<g:if test="${timeforingInstance.arbeidsType != ArbeidsType.REISE}">
		                            	<g:link action="edit" id="${timeforingInstance.id}"><g:redigerIkon /></g:link>
		                        		&nbsp;&nbsp;
		                        		<g:link action="delete" id="${timeforingInstance.id}"><g:slettIkon /></g:link>
		                        		
		                        		<g:if test="${!timeforingInstance.godkjent()}">
		                        			&nbsp;&nbsp;
		                        			<g:link action="godkjenn" id="${timeforingInstance.id}"><g:godkjennIkon /></g:link>
		                        		</g:if>
	                        		</g:if>
	                        	</td>
                        	</g:else>
                        </g:if>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            	
            	</g:else>
            	
                
                <br/>
                <g:if test="${!sendtInn}"> 
                <span class="menuButton"><g:link class="create" controller="timeforing" action="create"><g:message code="sivadm.timeregistreirng.legg.til.timeforing" default="Legg til timeføring" /></g:link></span>
                </g:if>
            </div>
            
            <br/>
            <br/>
         <g:if test="${!sendtInn && registreringerFinnes}">
	         <div class="buttons" style="height: 20px;">
	         	<g:if test="${!alleGodkjent}"><span class="menuButton"><g:link style="vertical-align: middle;" class="create" action="godkjennAlle">Godkjenn alle denne dagen</g:link></span></g:if>
				<g:if test="${alleGodkjent}">
					<span class="menuButton"><g:link style="vertical-align: middle;"  class="create" action="sendInnAlle" onclick="return apneSendInnDialog();"  >Send inn for denne dagen</g:link></span>
					<g:form method="post" action="sendInnAlle" name="sendInnDummyForm"></g:form>
				</g:if>
			</div> 
		</g:if>
            
        </div>
        <div id="dialog-confirm" title="${message(code: 'sivadm.timeregistrering.send.inn.dialog.tittel')}">
			<table style="border: 0;">
				<tr>
					<td>
						<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;" ></span>
					</td>
					<td>
						<g:message code="sivadm.timeregistrering.send.inn.confirm" />
					</td>
				</tr>
			</table>
		</div>
    </body>
</html>
