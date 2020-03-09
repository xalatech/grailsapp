

<%@ page import="sivadm.Logg" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'logg.label', default: 'Logg')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${loggInstance}">
            <div class="errors">
                <g:renderErrors bean="${loggInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tittel"><g:message code="logg.tittel.label" default="Tittel" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'tittel', 'errors')}">
                                    <g:textField name="tittel" value="${loggInstance?.tittel}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="endretAv"><g:message code="logg.endretAv.label" default="Endret Av" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'endretAv', 'errors')}">
                                    <g:textField name="endretAv" value="${loggInstance?.endretAv}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ferdig"><g:message code="logg.ferdig.label" default="Ferdig" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'ferdig', 'errors')}">
                                    <g:checkBox name="ferdig" value="${loggInstance?.ferdig}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="loggType"><g:message code="logg.loggType.label" default="Logg Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'loggType', 'errors')}">
                                    <g:select name="loggType" from="${siv.type.LoggType?.values()}" keys="${siv.type.LoggType?.values()*.name()}" value="${loggInstance?.loggType?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="opprettet"><g:message code="logg.opprettet.label" default="Opprettet" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'opprettet', 'errors')}">
                                    <g:datePicker name="opprettet" precision="day" value="${loggInstance?.opprettet}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sistOppdatert"><g:message code="logg.sistOppdatert.label" default="Sist Oppdatert" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: loggInstance, field: 'sistOppdatert', 'errors')}">
                                    <g:datePicker name="sistOppdatert" precision="day" value="${loggInstance?.sistOppdatert}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
