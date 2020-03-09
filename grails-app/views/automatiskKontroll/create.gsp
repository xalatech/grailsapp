<%@ page import="sil.AutomatiskKontroll" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'automatiskKontroll.label', default: 'AutomatiskKontroll')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <script>
	        function typeEndret() {
				var sel = document.getElementById("kravType");
				var type = document.getElementById("grenseType");
				var transport = document.getElementById("transportmiddel");
				
				if(sel && type) {
					if(sel.value == "T") {
						type.innerHTML = "min";
						if(transport) {
							transport.disabled = true;
			        	}
					}
					else if(sel.value == "K") {
		        		if(transport) {
							transport.disabled = false;
			        	}
						type.innerHTML = "km";
					}
					else if(sel.value == "U") {
						if(transport) {
							transport.disabled = true;
			        	}
						type.innerHTML = "kr";
					}
				}	
	        }


        	function transportmiddelEndret() {
        		var transport = document.getElementById("transportmiddel");
        		var type = document.getElementById("grenseType");
        		
        		if(transport && type) {
        			if(transport.value == "EGEN_BIL"
                		|| transport.value == "MOTOR_BAAT"
        				|| transport.value == "MOTORSYKKEL"
        				|| transport.value == "SNOSCOOTER"
        				|| transport.value == "MOPED_SYKKEL") {

        				type.innerHTML = "km";
        			}
        			else if(transport.value == "LEIEBIL" || transport.value == "GIKK" || transport.value == "FERJE") {
        				type.innerHTML = "min";
        			}
        			else {
        				// BUSS_TRIKK, TOG, FERJE, TAXI
        				type.innerHTML = "kr";
        			}
        		}
        	}
        </script>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${automatiskKontrollInstance}">
            <div class="errors">
                <g:renderErrors bean="${automatiskKontrollInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kontrollNavn"><g:message code="automatiskKontroll.kontrollNavn.label" default="Kontrollnavn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'kontrollNavn', 'errors')}">
                                    <g:textField name="kontrollNavn" value="${automatiskKontrollInstance?.kontrollNavn}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="beskrivelse"><g:message code="automatiskKontroll.beskrivelse.label" default="Beskrivelse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'beskrivelse', 'errors')}">
                                    <g:textField name="beskrivelse" value="${automatiskKontrollInstance?.beskrivelse}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kravType"><g:message code="automatiskKontroll.kravType.label" default="Kravtype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'kravType', 'errors')}">
                                    <g:select name="kravType" from="${sil.type.KravType?.values()}" optionKey="key" optionValue="guiName" value="${automatiskKontrollInstance?.kravType?.key}" onchange="typeEndret();" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                            	<td valign="top" class="name">
                                    <label for="transportmiddel"><g:message code="automatiskKontroll.transportmiddel.label" default="Transportmiddel" /></label>
                                </td>
                                <g:set var="selectDisabled" value="true" />
                                <g:if test="${automatiskKontrollInstance?.kravType == sil.type.KravType.K}">
                                	<g:set var="selectDisabled" value="false" />
                                </g:if>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'transportmiddel', 'errors')}">
                                	<g:select name="transportmiddel"
										from="${siv.type.TransportMiddel?.values()}"
										optionKey="key"
										optionValue="guiName"
										noSelection="['':'-- Velg transportmiddel --']"
										value="${automatiskKontrollInstance?.transportmiddel?.key}" 
										onChange="transportmiddelEndret();" 
										disabled="${selectDisabled}" />
                                </td>
                        	</tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="automatiskKontroll.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'produktNummer', 'errors')}">
                                    <g:select name="produktNummer" from="${produktNummerListe}" optionKey="produktNummer" value="${automatiskKontrollInstance?.produktNummer}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grenseVerdi"><g:message code="automatiskKontroll.grenseVerdi.label" default="Grenseverdi" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'grenseVerdi', 'errors')}">
                                    <g:textField name="grenseVerdi" value="${fieldValue(bean: automatiskKontrollInstance, field: 'grenseVerdi')}"  size="10" />
                                    &nbsp;<span id="grenseType">min</span>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="feilmelding"><g:message code="automatiskKontroll.feilmelding.label" default="Feilmelding" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'feilmelding', 'errors')}">
                                    <g:textField name="feilmelding" value="${automatiskKontrollInstance?.feilmelding}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gyldigFra"><g:message code="automatiskKontroll.gyldigFra.label" default="Gyldig fra" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'gyldigFra', 'errors')}">
                                    <g:datoVelger id="dp-1" name="gyldigFra" value="${automatiskKontrollInstance?.gyldigFra}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gyldigTil"><g:message code="automatiskKontroll.gyldigTil.label" default="Gyldig til" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: automatiskKontrollInstance, field: 'gyldigTil', 'errors')}">
                                    <g:datoVelger id="dp-2" name="gyldigTil" value="${automatiskKontrollInstance?.gyldigTil}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
