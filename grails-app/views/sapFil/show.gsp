
<%@ page import="sil.SapFil" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'sapFil.label', default: 'SapFil')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="sapFil.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sapFilInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="sapFil.fil.label" default="Fil" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sapFilInstance, field: "fil")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="sapFil.filType.label" default="Fil Type" /></td>
                            
                            <td valign="top" class="value">${sapFilInstance?.filType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="sapFil.dato.label" default="Dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${sapFilInstance?.dato}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
