<%@ page import="sivadm.Opplaring" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'opplaring.label', default: 'Opplæring')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${opplaringInstance}">
            <div class="errors">
                <g:renderErrors bean="${opplaringInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${opplaringInstance?.id}" />
                <g:hiddenField name="version" value="${opplaringInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="skjema"><g:message code="opplaring.skjema.label" default="Skjema" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'skjema', 'errors')}">
                                    <g:select name="skjema.id" from="${sivadm.Skjema.list()}" optionKey="id" value="${opplaringInstance?.skjema?.id}"  />
                                </td>
                            </tr>
                            
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="hendelseType"><g:message code="opplaring.hendelseType.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'hendelseType', 'errors')}">
                                    <g:select name="hendelseType" from="${siv.type.InfoHendelseType?.values()}" optionKey="key" optionValue="guiName" value="${opplaringInstance?.hendelseType?.key}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="beskrivelse"><g:message code="opplaring.beskrivelse.label" default="Beskrivelse" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'beskrivelse', 'errors')}">
                                    <g:textField name="beskrivelse" value="${opplaringInstance?.beskrivelse}" size="50"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="obligatorisk"><g:message code="opplaring.obligatorisk.label" default="Obligatorisk" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'obligatorisk', 'errors')}">
                                    <g:checkBox name="obligatorisk" value="${opplaringInstance?.obligatorisk}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gjennomfortDato"><g:message code="opplaring.gjennomfortDato.label" default="Gjennomført dato" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'gjennomfortDato', 'errors')}">
                                    <g:datoVelger id="dp1" name="gjennomfortDato"  value="${opplaringInstance?.gjennomfortDato}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="gjennomfortSted"><g:message code="opplaring.gjennomfortSted.label" default="Gjennomført sted" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'gjennomfortSted', 'errors')}">
                                    <g:textField name="gjennomfortSted" value="${opplaringInstance?.gjennomfortSted}" size="30"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="intervjuere"><g:message code="opplaring.intervjuere.label" default="Intervjuere" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: opplaringInstance, field: 'intervjuere', 'errors')}">
                                    <g:select name="intervjuere" from="${sivadm.Intervjuer.list()}" multiple="yes" optionKey="id" size="25" value="${opplaringInstance?.intervjuere}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="list" value="Avbryt"/></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
