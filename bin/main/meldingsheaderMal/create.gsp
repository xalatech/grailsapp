<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'meldingsheaderMal.label', default: 'Meldingsheader-mal')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${meldingsheaderMalInstance}">
            <div class="errors">
                <g:renderErrors bean="${meldingsheaderMalInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="malNavn"><g:message code="meldingsheaderMal.malNavn.label" default="Navn på meldingsheader-mal" /></label>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="value ${hasErrors(bean: meldingsheaderMalInstance, field: 'malNavn', 'errors')}">
                                    <g:textField name="malNavn" value="${meldingsheaderMalInstance?.malNavn}" size="60"/>
                                </td>
                            </tr>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="meldingsheader"><g:message code="meldingsheaderMal.meldingsheader.label" default="Meldingsheader-tekst" /></label>
                                </td>
                            </tr>
                            <tr class="prop">
                            <td valign="top" class="value ${hasErrors(bean: meldingsheaderMalInstance, field: 'meldingsheader', 'errors')}" width="80%">
                                    <g:textField name="meldingsheader" value="${meldingsheaderMalInstance?.meldingsheader}"maxlength="150" size="160"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
                    	<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                    	<g:link class="delete" action="list"><g:message code="sil.avbryt" /></g:link>
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>
