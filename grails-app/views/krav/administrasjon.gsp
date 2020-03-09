<%@ page import="sil.Krav" %>
<%@ page import="sil.type.KravStatus" %>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        
        <resource:tooltip />
        <title>SIL Administrasjon</title>
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
						"${message(code: 'sil.adm.send.inn')}": function() {
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
	            <div class="message">${flash.message}</div>
	        </g:if>
	        <g:if test="${flash.errorMessage}">
	            <div class="errors">${flash.errorMessage}</div>
	        </g:if>
	        
	        <g:form method="post" action="skrivTilSapFiler" name="sendInnDummyForm"></g:form>	
	        
	        <g:form method="post">
	        
	        <div class="buttons">
		        <div style="margin-top: 5px;">
			        <span class="menuButton"><g:link class="list" action="importerKravKjorKontrollerOgUtvalgManuellKontroll" title="${message(code: 'sil.adm.import.kontroll.utvalg.tooltip', default: '')}"><g:message code="sil.adm.import.kontroll.utvalg" default="Import m/kontroll" /></g:link></span>
			        <span class="menuButton"><g:link controller="krav" action="importerKrav" id="importKrav" title="${message(code: 'sil.adm.importer.krav.tooltip', default: '')}"><g:message code="sil.adm.importer.krav" /></g:link></span>	       		
		       		<span class="menuButton"><g:link controller="krav" action="kjorAutoKontroller" id="autoKontroll" title="${message(code: 'sil.adm.kjor.auto.kontroller.tooltip', default: '')}"><g:message code="sil.adm.kjor.auto.kontroller" /></g:link></span>	       		
		       		<span class="menuButton"><g:link controller="krav" action="finnKravTilManuellKontroll" id="tilManKontroll" title="${message(code: 'sil.adm.generer.utvalg.tooltip', default: '')}"><g:message code="sil.adm.generer.utvalg" /></g:link></span>	       		
		       		<span class="button"><g:actionSubmit class="save" action="godkjennResten" value="${message(code: 'sil.adm.godkjenn.resten', default: 'Godkjenn resten')}" title="${message(code: 'sil.adm.godkjenn.resten.tooltip', default: 'Godkjenn alle krav som har status bestått automatisk kontroll')}" /></span>	       		
		       		<span class="menuButton"><g:link class="create" action="skrivTilSapFiler"  onclick="return apneSendInnDialog();"  title="${message(code: 'sil.adm.sap.eksport.tooltip', default: 'Eksporter godkjente krav til SAP filer')}" ><g:message code="sil.adm.sap.eksport" default="SAP" /></g:link></span>
		       		<span class="menuButton"><g:link class="create" action="skrivTilKostGodtgjorelseRapport"  title="Eksporter godkjente krav om kostgodtgjørelse" >Kostgodtgjørelse</g:link></span>
		        </div>
	        </div>
	        <br>
	        
	        <h1><g:message code="meny.sil.administrasjon" /></h1>
	        
	        </g:form>
	        <br><br>
	        <h3><g:message code="sil.adm.antall.krav" args="[Krav.list().size()]"/></h3><br>
	        
	        <div style="width: 300px;">
	        
			<table>
				<thead>
					<tr>
						<th><g:message code="sil.adm.status" default="Status" /></th>
						<th><g:message code="sil.adm.antall" default="Antall" /></th>
					</tr>
				</thead>
				<tbody>
					<tr class="even">
						<td>${KravStatus.OPPRETTET}</td>
						<td>${Krav.countByKravStatus(KravStatus.OPPRETTET)}</td>
					</tr>
					<tr class="odd">
						<td>${KravStatus.BESTOD_AUTOMATISK_KONTROLL}</td>
						<td>${Krav.countByKravStatus(KravStatus.BESTOD_AUTOMATISK_KONTROLL)}</td>
					</tr>
					<tr class="even">
						<td>${KravStatus.FEILET_AUTOMATISK_KONTROLL}</td>
						<td>${Krav.countByKravStatus(KravStatus.FEILET_AUTOMATISK_KONTROLL)}</td>
					</tr>
					<tr class="odd">
						<td>${KravStatus.TIL_MANUELL_KONTROLL}</td>
						<td>${Krav.countByKravStatus(KravStatus.TIL_MANUELL_KONTROLL)}</td>
					</tr>
					<tr class="even">
						<td>${KravStatus.SENDES_TIL_INTERVJUER}</td>
						<td>${Krav.countByKravStatus(KravStatus.SENDES_TIL_INTERVJUER)}</td>
					</tr>
					<tr class="odd">
						<td>${KravStatus.TIL_RETTING_INTERVJUER}</td>
						<td>${Krav.countByKravStatus(KravStatus.TIL_RETTING_INTERVJUER)}</td>
					</tr>
					<tr class="even">
						<td>${KravStatus.RETTET_AV_INTERVJUER}</td>
						<td>${Krav.countByKravStatus(KravStatus.RETTET_AV_INTERVJUER)}</td>
					</tr>
					<tr class="odd">
						<td>${KravStatus.INAKTIV}</td>
						<td>${Krav.countByKravStatus(KravStatus.INAKTIV)}</td>
					</tr>
					<tr class="even">
						<td>${KravStatus.GODKJENT}</td>
						<td>${Krav.countByKravStatus(KravStatus.GODKJENT)}</td>
					</tr>
					<tr class="odd">
						<td>${KravStatus.AVVIST}</td>
						<td>${Krav.countByKravStatus(KravStatus.AVVIST)}</td>
					</tr>
					<tr class="even">
						<td>${KravStatus.OVERSENDT_SAP}</td>
						<td>${Krav.countByKravStatus(KravStatus.OVERSENDT_SAP)}</td>
					</tr>
				</tbody>
			</table>
			</div>
       </div>
       
       <div id="dialog-confirm" title="Generere SAP-filer">
			<table style="border: 0;">
				<tr>
					<td>
						<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;" ></span>
					</td>
					<td>
						Er du helt sikker på at du vil generere SAP-filer?
					</td>
				</tr>
			</table>
		</div>
		
    </body>
</html>