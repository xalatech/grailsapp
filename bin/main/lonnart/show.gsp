
<%@ page import="sil.Lonnart" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lonnart.label', default: 'Lonnart')}" />
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
                            <td valign="top" class="name"><g:message code="lonnart.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.lonnartNummer.label" default="Lonnart Nummer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "lonnartNummer")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.navn.label" default="Navn" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "navn")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.beskrivelse.label" default="Beskrivelse" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "beskrivelse")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.kmKode.label" default="Km Kode" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "kmKode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.konto.label" default="Konto" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "konto")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.kontoTekst.label" default="Konto Tekst" /></td>
                            
                            <td valign="top" class="value">${lonnartInstance?.kontoTekst?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.markedType.label" default="Marked Type" /></td>
                            
                            <td valign="top" class="value">${lonnartInstance?.markedType?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.redigertDato.label" default="Redigert Dato" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${lonnartInstance?.redigertDato}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="lonnart.redigertAv.label" default="Redigert Av" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: lonnartInstance, field: "redigertAv")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${lonnartInstance?.id}" />
                </g:form>
            </div>
        </div>
    </body>
</html>
