
<%@ page import="sivadm.Adresse" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'adresse.label', default: 'Adresse')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${flash.errorMessage}">
            	<div class="errors">&nbsp;${flash.errorMessage}</div>
            </g:if>
                        
            <g:hasErrors bean="${adresseInstance}">
            <div class="errors">
                <g:renderErrors bean="${adresseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${adresseInstance?.id}" />
                <g:hiddenField name="version" value="${adresseInstance?.version}" />
                <g:hiddenField name="ioId" value="${ioId}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gateAdresse"><g:message code="adresse.gateAdresse.label" default="Gateadresse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'gateAdresse', 'errors')}">
                                    <g:textField name="gateAdresse" value="${adresseInstance?.gateAdresse}" size="50" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tilleggsAdresse"><g:message code="adresse.tilleggsAdresse.label" default="Tilleggsadresse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'tilleggsAdresse', 'errors')}">
                                    <g:textField name="tilleggsAdresse" value="${adresseInstance?.tilleggsAdresse}" size="50" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="boligNummer"><g:message code="adresse.boligNummer.label" default="Bolignummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'boligNummer', 'errors')}">
                                    <g:textField name="boligNummer" value="${adresseInstance?.boligNummer}" size="6"  maxlength="5" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="postNummer"><g:message code="adresse.postNummer.label" default="Postnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'postNummer', 'errors')}">
                                    <g:textField name="postNummer" value="${adresseInstance?.postNummer}" size="4" maxlength="4" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="postSted"><g:message code="adresse.postSted.label" default="Poststed" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'postSted', 'errors') || poststedError ? "errors" : ""}">
                                    <g:textField name="postSted" value="${adresseInstance?.postSted}" size="40"/>
                                </td>
                            </tr>
                            
                    	    <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="adresseType"><g:message code="adresse.adresseType.label" default="Adressetype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'adresseType', 'errors')}">
                                    <g:select name="adresseType" from="${siv.type.AdresseType?.values()}" optionKey="key" optionValue="guiName" value="${adresseInstance?.adresseType?.key}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="husBruksNummer"><g:message code="adresse.husBruksNummer.label" default="Hus-/bruksnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'husNummer', 'errors')}">
                                    <g:textField name="husBruksNummer" value="${adresseInstance?.husBruksNummer}" size="6" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gateGaardNummer"><g:message code="adresse.gateGaardNummer.label" default="Gate-/Gårdsnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'gateGaardNummer', 'errors')}">
                                    <g:textField name="gateGaardNummer" value="${adresseInstance?.gateGaardNummer}" size="6" />
                                </td>
                            </tr>
                        
                       		<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommuneNummer"><g:message code="adresse.kommuneNummer.label" default="Kommunenummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'kommuneNummer', 'errors')}">
                                    <g:textField name="kommuneNummer" value="${adresseInstance?.kommuneNummer}"  size="4" maxlength="4" />
                                </td>
                            </tr>
                                                                           
                             <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gjeldende"><g:message code="adresse.gjeldende.label" default="Gjeldende" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'gjeldende', 'errors')}">
                                    <g:checkBox name="gjeldende" value="${adresseInstance?.gjeldende}" />
                                </td>
                            </tr>
                                                                                                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gyldigFom"><g:message code="adresse.gyldigFom.label" default="Gyldig f.o.m." /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'gyldigFom', 'errors')}">
                                    <g:datoVelger id="dp-1" name="gyldigFom" value="${adresseInstance?.gyldigFom}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gyldigTom"><g:message code="adresse.gyldigTom.label" default="Gyldig t.o.m." /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'gyldigTom', 'errors')}">
                                    <g:datoVelger id="dp-2" name="gyldigTom" value="${adresseInstance?.gyldigTom}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="kommentar"><g:message code="adresse.kommentar.label" default="Kommentar" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: adresseInstance, field: 'kommentar', 'errors')}">
                                    <g:textField name="kommentar" value="${adresseInstance?.kommentar}" size="50"/>
                                </td>
                            </tr>
                               
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="Lagre" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
