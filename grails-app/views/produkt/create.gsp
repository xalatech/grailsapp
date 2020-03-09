

<%@ page import="sivadm.Produkt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produkt.label', default: 'Produkt')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${produktInstance}">
            <div class="errors">
                <g:renderErrors bean="${produktInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="produkt.produktNummer.label" default="Produktnr" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'produktNummer', 'errors')}">
                                    <g:textField name="produktNummer" value="${produktInstance?.produktNummer}" size="7" maxlength="7" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="navn"><g:message code="produkt.navn.label" default="Navn" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'navn', 'errors')}">
                                    <g:textField name="navn" value="${produktInstance?.navn}" size="40" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="beskrivelse"><g:message code="produkt.beskrivelse.label" default="Beskrivelse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'beskrivelse', 'errors')}">
                                    <g:textField name="beskrivelse" value="${produktInstance?.beskrivelse}" size="40" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="finansiering"><g:message code="produkt.finansiering.label" default="Finansiering" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'finansiering', 'errors')}">
                                    <g:select name="finansiering" from="${siv.type.ProsjektFinansiering?.values()}" optionKey="key" optionValue="guiName" value="${produktInstance?.finansiering?.key}"  />
                                </td>
                            </tr>
                          
                        	<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosentStat"><g:message code="produkt.prosentStat.label" default="Stat %" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'prostentStat', 'errors')}">
                                    <g:textField name="prosentStat" value="${produktInstance?.prosentStat}" size="3" maxlength="3" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="prosentMarked"><g:message code="produkt.prosentMarked.label" default="Marked %" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produktInstance, field: 'prostentMarked', 'errors')}">
                                    <g:textField name="prosentMarked" value="${produktInstance?.prosentMarked}" size="3" maxlength="3" />
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
