<%@ page import="siv.data.ProduktKode" %>
<%@ page import="sivadm.Kjorebok" %>
<%@ page import="siv.type.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'kjorebok.label', default: 'Kjorebok')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript library="prototype" />
        <g:javascript src="kjorebok2.js"/>
        <g:javascript src="kolon.js"/>
        <g:javascript src="fullforKlokkeslett.js"/>
        <g:javascript src="handterKommaIKilometertall.js"/>
     </head>
    <body>
        
        <div class="body">
            <h1>Opprett kjørebok</h1>
            <g:if test="${flash.message}">
            	<div class="message">&nbsp;${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">&nbsp;${flash.errorMessage}</div>
            </g:if>
                        
            <g:hasErrors bean="${kjorebokInstance}">
            	<div class="errors">
                	<g:renderErrors bean="${kjorebokInstance}" as="list" />
            	</div>
            </g:hasErrors>
            
            <g:if test="${createCommand}">
            	<g:hasErrors bean="${createCommand}">
            		<div class="errors">
                		<g:renderErrors bean="${createCommand}" as="list" />
            		</div>
            	</g:hasErrors>	
            </g:if>
                       
            <g:hasErrors bean="${fraTilCommand}">
	            <div class="errors">
	                <g:renderErrors bean="${fraTilCommand}" as="list" />
	            </div>
            </g:hasErrors>
            
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fraTid"><g:message code="sil.Tidspunkt" default="Tidspunkt" /></label>
                                </td>                                                                
                                <td valign="top" class="value ${(hasErrors(bean: kjorebokInstance, field: 'fraTidspunkt', 'errors')  || hasErrors(bean: fraTilCommand, field: 'fraTid', 'errors')) ? "errors" : ""}">
                                    <g:textField onchange="kolon('fraTid')" name="fraTid" value="${fraTid}" size="5" title="Eksempel: 09:00" /> - <span class="errors">
                                    <g:textField onchange="kolon('tilTid')" name="tilTid" value="${tilTid}" size="5" title="Eksempel: 10:00"/></span>
                                	(Eksempel: 09:00 - 10:00)
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fraAdresse"><g:message code="kjorebok.fraAdresse.label" default="Fra adresse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'fraAdresse', 'errors')}">
                                    <g:textField name="fraAdresse" value="${kjorebokInstance?.fraAdresse}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fraPoststed"><g:message code="kjorebok.fraPoststed.label" default="Fra poststed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'fraPoststed', 'errors')}">
                                    <g:textField name="fraPoststed" value="${kjorebokInstance?.fraPoststed}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="intervjuobjekt"><g:message code="kjorebok.intervjuobjekt.label" default="Intervjuobjekt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'intervjuobjekt', 'errors')}">
                                    <g:select name="intervjuobjekt.id" from="${intervjuObjektList}" optionKey="id" noSelection="${['':'-- Intervjuobjekt ikke valgt --']}"
                                    	onchange="${remoteFunction(
	            							controller:'intervjuObjekt', 
	            							action:'ajaxGetIntervjuObjektAdresse', 
	            							params:'\'id=\' + escape(this.value)', 
	            							onComplete:'ioEndret(e)')}" 
                                    />
                                </td>
                            </tr>
                        
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="merknad"><g:message code="kjorebok.merknad.label" default="Merknad" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'merknad', 'errors')}">
                                    <g:textField name="merknad" value="${kjorebokInstance?.merknad}" />
                                </td>
                            </tr>
                        
 	                        <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tilAdresse"><g:message code="kjorebok.tilAdresse.label" default="Til adresse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'tilAdresse', 'errors')}">
                                    <g:textField name="tilAdresse" value="${kjorebokInstance?.tilAdresse}" size="35" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tilPoststed"><g:message code="kjorebok.tilPoststed.label" default="Til poststed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'tilPoststed', 'errors')}">
                                    <g:textField name="tilPoststed" value="${kjorebokInstance?.tilPoststed}" size="35" />
                                </td>
                            </tr>
                                                    
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="kjorebok.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'produktNummer', 'errors')}">
                                    <g:select id="produktNummer" name="produktNummer" from="${produktNummerListe}" optionKey="produktNummer" value="${kjorebokInstance?.produktNummer}" noSelection="['':'-Velg produktkode-']"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="transportmiddel"><g:message code="kjorebok.transportmiddel.label" default="Transportmiddel" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'transportmiddel', 'errors')}">
                                    <g:select name="transportmiddel"
										from="${siv.type.TransportMiddel?.values()}"
										optionKey="key"
										optionValue="guiName"
										value="${kjorebokInstance?.transportmiddel?.key}" 
										onChange="transportmiddelEndret();" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kjorteKilometer"><g:message code="sil.Kilometer" default="Kilometer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'kjorteKilometer', 'errors')}">
                                    <g:textField name="kjorteKilometer" onChange="handterKomma('kjorteKilometer')" value="${fieldValue(bean: kjorebokInstance, field: 'kjorteKilometer')}" size="5" /> <g:message code="sil.km" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="antallPassasjerer"><g:message code="kjorebok.antallPassasjerer.label" default="Passasjerer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'antallPassasjerer', 'errors')}">
                                    <g:textField name="antallPassasjerer" value="${kjorebokInstance?.antallPassasjerer}" size="5" />
                                </td>
                            </tr>
                                                       
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kjorteHjem"><g:message code="kjorebok.xx.label" default="Retur" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: kjorebokInstance, field: 'kjorteHjem', 'errors')}">
                                    <g:checkBox name="kjorteHjem" value="${kjorebokInstance?.kjorteHjem}" title="${message(code: 'kjorebok.kjorte.hjem.hjelpetekst')}" onChange="returEndret()" />
                                </td>
                            </tr>
                        	
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fraTidRetur"><g:message code="xx" default="Retur tidspunkt" /></label>
                                </td>
                                
                                
                                <td valign="top" id="returTider" class="value ${(hasErrors(bean: createCommand, field: 'tilTidRetur', 'errors')  || hasErrors(bean: createCommand, field: 'fraTidRetur', 'errors')) ? "errors" : ""}">
                                    <g:textField name="fraTidRetur" value="${createCommand?.fraTidRetur}" size="5" onChange="validateTime('fraTidRetur', 'Fra-tidspunkt for retur er ikke i korrekt tidsformat tt:mm', 'returTider')" /> - <g:textField name="tilTidRetur" value="${createCommand?.tilTidRetur}" size="5" onChange="validateTime('tilTidRetur', 'Til-tidspunkt for retur er ikke i korrekt tidsformat tt:mm', 'returTider')" />
                                </td>
                            </tr>
                        	
                        	 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="utgifter"><g:message code="kjorebok.utgifter" default="Utgifter" /></label>
                                </td>
                                <td valign="top">
                                    <g:checkBox name="utgifter"
                                    	value="${createCommand?.utgifter}"
                                    	title="${message(code: 'kjorebok.utgifter.tooltip')}"
                                    	onChange="utgifterEndret();" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="bompenger"><g:message code="kjorebok.bompenger" default="Bompenger" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: createCommand, field: 'bompenger', 'errors')) ? "errors" : ""}">
                                    <g:textField value="${createCommand?.bompenger}" name="bompenger" size="5" /> <g:message code="sil.kr" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parkering"><g:message code="kjorebok.parkering" default="Parkering" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: createCommand, field: 'parkering', 'errors')) ? "errors" : ""}">
                                    <g:textField value="${createCommand?.parkering}" name="parkering" size="5" /> <g:message code="sil.kr" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ferje"><g:message code="kjorebok.ferje" default="Ferje" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: createCommand, field: 'ferje', 'errors')) ? "errors" : ""}">
                                    <g:textField value="${createCommand?.ferje}" name="ferje" size="5" /> <g:message code="sil.kr" />
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="belop"><g:message code="kjorebok.belop" default="Beløp" /></label>
                                </td>
                                <td valign="top" class="value ${(hasErrors(bean: createCommand, field: 'belop', 'errors')) ? "errors" : ""}">
                                    <g:textField value="${createCommand?.belop}" name="belop" size="5" title="${message(code:  'kjorebok.belop.tooltip')}" style="disabled: true" /> <g:message code="sil.kr" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
                </div>
            </g:form>
        </div>
       	<script type="text/javascript">returEndret();utgifterEndret();initAdresse();</script>
		<g:if test="${kjorebokInstance?.transportmiddel == TransportMiddel.BUSS_TRIKK || kjorebokInstance?.transportmiddel == TransportMiddel.TOG || kjorebokInstance?.transportmiddel == TransportMiddel.TAXI}">
			<script type="text/javascript">disableBelop(false);</script>
		</g:if>
		<g:else>
			<script type="text/javascript">disableBelop(true);</script>
		</g:else>
    </body>
</html>
