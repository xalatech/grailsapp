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
            <g:if test="${flash.errorMessage}">
          		<div class="errors">${flash.errorMessage}</div>
            </g:if>
			<h1><g:message code="xxx" default="Kopiere timer, kjørebok og utlegg" /></h1>
           
           	<g:hasErrors bean="${fraTilKopierCommand}">
           	<div class="errors">
                <g:renderErrors bean="${fraTilKopierCommand}" as="list" />
           	</div>
           	</g:hasErrors>
           
           	<g:form action="kopierTimeforinger" method="post">
            	<table>
            		<tr class="prop">
                         <td valign="top" class="name">
                           <label for="fraDato"><g:message code="xx" default="Fra:" /></label>
                         </td>
                         <td valign="top">
	                        <g:datoVelger id="dp-1" name="fraDato" value="${fraTilKopierCommand?.fraDato}" />
                         </td>
                    </tr>
                    <tr class="prop">
                         <td valign="top" class="name">
                           <label for="tilDato"><g:message code="xx" default="Til:" /></label>
                         </td>
                         <td valign="top">
	                        <g:datoVelger id="dp-2" name="tilDato" value="${fraTilKopierCommand?.tilDato}" />
                         </td>
                    </tr>
                    <tr class="prop">
                         <td valign="top" class="name">
                           <label for="intervjuer"><g:message code="xx" default="Intervjuer:" /></label>
                         </td>
                         <td valign="top">
	                     	<g:select name="intervjuer"
								from="${Intervjuer.list()}"
								noSelection="['':'--- Velg intervjuer ---']" optionKey="id" value="${fraTilKopierCommand?.intervjuer}"/>
                         </td>
                    </tr>
            	</table>
            	<g:submitButton name="list" value="${message(code: 'xx', default: 'Kopiere')}"/>
            </g:form>
       	</div>
    </body>
</html>