
<%@ page import="sivadm.Prosjekt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'prosjekt.label', default: 'Prosjekt')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    
        <div class="body">
        	
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prosjektInstance}">
            <div class="errors">
                <g:renderErrors bean="${prosjektInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${prosjektInstance?.id}" />
                <g:hiddenField name="version" value="${prosjektInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosjektNavn"><g:message code="prosjekt.prosjektNavn.label" default="Prosjekt Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prosjektNavn', 'errors')}">
                                    <g:textField name="prosjektNavn" value="${prosjektInstance?.prosjektNavn}" size="40" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="produktNummer"><g:message code="prosjekt.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'produktNummer', 'errors')}">
                                    <g:textField name="produktNummer" value="${prosjektInstance?.produktNummer}" size="5" maxlength="5" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="aargang"><g:message code="prosjekt.aargang.label" default="Årgang" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'aargang', 'errors')}">
                                    <g:textField name="aargang" value="${fieldValue(bean: prosjektInstance, field: 'aargang')}" size="4" maxlength="4" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="oppstartDato"><g:message code="prosjekt.oppstartDato.label" default="Oppstartdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'oppstartDato', 'errors')}">
                                    <g:datoVelger id="od" name="oppstartDato" value="${prosjektInstance?.oppstartDato}"/>
                                    
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="avslutningsDato"><g:message code="prosjekt.avslutningsDato.label" default="Avslutningsdato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'avslutningsDato', 'errors')}">
                                    <g:datoVelger id="ad" name="avslutningsDato" value="${prosjektInstance?.avslutningsDato}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="registerNummer"><g:message code="prosjekt.registerNummer.label" default="Registernummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'registerNummer', 'errors')}">
                                    <g:textField name="registerNummer" value="${prosjektInstance?.registerNummer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="panel"><g:message code="prosjekt.panel.label" default="Panel" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'panel', 'errors')}">
                                    <g:checkBox name="panel" value="${prosjektInstance?.panel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="modus"><g:message code="prosjekt.modus.label" default="Modus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'modus', 'errors')}">
                                    <g:select name="modus" from="${siv.type.ProsjektModus?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.modus?.key}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
								<td valign="top" class="name"><label for="skjemaer"><g:message
									code="prosjekt.skjemaer.label" default="Skjemaer" /></label></td>
								<td valign="top"
									class="value ${hasErrors(bean: prosjektInstance, field: 'skjemaer', 'errors')}">
				
								<ul>
									<g:each in="${prosjektInstance?.skjemaer?.sort{it.delProduktNummer}}" var="s">
										<li><g:link controller="skjema" action="edit" id="${s.id}" params="['prosjekt.id': prosjektInstance?.id]">
											${s?.encodeAsHTML()}
										</g:link></li>
									</g:each>
								</ul>
								<g:link controller="skjema" action="create"
									params="['prosjekt.id': prosjektInstance?.id]">
									${message(code: 'default.add.label', args: [message(code: 'skjema.label', default: 'Skjema')])}
								</g:link></td>
							</tr>
				
							<tr class="prop">
								<td valign="top" class="name">
								 <label for="prosjektDeltagere"><g:message code="prosjekt.prosjektDeltagere.label" default="Prosjektdeltagere" /></label>
								</td>
								<td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prosjektDeltagere', 'errors')}">
									<ul>
										<g:each in="${prosjektInstance?.prosjektDeltagere?}" var="p">
											<li><g:link controller="prosjektDeltager" action="edit" id="${p.id}" params="${['prosjekt.id': prosjektInstance?.id]}">${p?.encodeAsHTML()}</g:link></li>
										</g:each>
									</ul>
									<g:link controller="prosjektDeltager" action="create" params="['prosjekt.id': prosjektInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager')])}</g:link>
								</td>
							</tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="finansiering"><g:message code="prosjekt.finansiering.label" default="Finansiering" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'finansiering', 'errors')}">
                                    <g:select name="finansiering" from="${siv.type.ProsjektFinansiering?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.finansiering?.key}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosentStat"><g:message code="prosjekt.prosentStat.label" default="Stat %" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prostentStat', 'errors')}">
                                    <g:textField name="prosentStat" value="${prosjektInstance?.prosentStat}" size="3" maxlength="3" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosentMarked"><g:message code="prosjekt.prosentMarked.label" default="Marked %" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prostentMarked', 'errors')}">
                                    <g:textField name="prosentMarked" value="${prosjektInstance?.prosentMarked}" size="3" maxlength="3" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosjektLeder"><g:message code="prosjekt.prosjektLeder.label" default="Prosjektleder" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prosjektLeder', 'errors')}">
                                    <g:select name="prosjektLeder.id" from="${sivadm.ProsjektLeder.list()}" optionKey="id" value="${prosjektInstance?.prosjektLeder?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosjektStatus"><g:message code="prosjekt.prosjektStatus.label" default="Prosjektstatus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'prosjektStatus', 'errors')}">
                                    <g:select name="prosjektStatus" from="${siv.type.ProsjektStatus?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.prosjektStatus?.key}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommentar"><g:message code="prosjekt.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: prosjektInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${prosjektInstance?.kommentar}" size="40" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="menuButton"><g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
