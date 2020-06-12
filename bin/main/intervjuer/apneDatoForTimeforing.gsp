<%@ page import="sivadm.Intervjuer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'intervjuer.label', default: 'Intervjuer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
          	  <div class="message">${flash.message}</div>
            </g:if>

            <h1><g:message code="sivadm.intervjuer.aapne.overskrift" default="Åpne dato for timeføring" /></h1>
            <br><br>
            <h3><g:message code="sivadm.intervjuer.aapne.intervjuer" args="${[intervjuerInstance]}" /></h3>
            <g:form action="aapneDatoForIntervjuer" method="post">
            	<table>
            		<tr class="prop">
                         <td valign="top" class="name">
                           <label for="dato"><g:message code="xx" default="Dato" /></label>
                         </td>
                         <td valign="top">
	                        <g:datoVelger id="dp-1" name="dato"  />
                         </td>
                    </tr>
            	</table>
            	<g:submitButton name="sok" value="${message(code: 'sivadm.intervjuer.aapne', default: 'Åpne valgt dato')}"/>
            	<g:actionSubmit name="avbryt" action="list" value="${message(code: 'sil.avbryt', default: 'Avbryt')}" />
            	<g:hiddenField name="id" value="${intervjuerInstance?.id}" />
            </g:form>
        </div>
    </body>
</html>