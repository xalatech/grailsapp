

<%@ page import="sivadm.Utlegg" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'utlegg.label', default: 'Utlegg')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript library="prototype" />
        <g:javascript src="utlegg.js"/>   
        <g:javascript src="kolon.js"/>   
        <g:javascript src="fullforKlokkeslett.js"/>
    </head>
    <body>
        <div class="body">
            <h1>Opprett utlegg / kostgodtgjørelse</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${utleggInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${utleggInstance}" as="list" />
	                <g:if test="${flash.errors}">
	                	${flash.errors}
	                </g:if>
	            </div>
            </g:hasErrors>
            <g:hasErrors bean="${utleggFraTilCommand}">
	            <div class="errors">
	                <g:renderErrors bean="${utleggFraTilCommand}" as="list" />
	                <g:if test="${flash.errors}">
	                	${flash.errors}
	                </g:if>
	            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="produktNummer"><g:message code="kjorebok.produktNummer.label" default="Produktnummer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggInstance, field: 'produktNummer', 'errors')}">
                                    <g:select name="produktNummer" from="${produktNummerListe}" optionKey="produktNummer" value="${utleggInstance?.produktNummer}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="utleggType"><g:message code="utlegg.utleggType.label" default="Utleggstype" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggInstance, field: 'utleggType', 'errors')}">
                                    <g:select name="utleggType" from="${utleggTypeListe}" optionKey="key" 
                                    	optionValue="guiName" value="${utleggInstance?.utleggType?.key}" onChange="utleggTypeEndret();" />
                                </td>
                            </tr>
                            
                            
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="spesifisering"><g:message code="utlegg.spesifisering.label" default="Spesifisering" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggInstance, field: 'spesifisering', 'errors')}">
                                    <g:textField name="spesifisering" value="${utleggInstance?.spesifisering}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="merknad"><g:message code="utlegg.merknad.label" default="Merknad" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggInstance, field: 'merknad', 'errors')}">
                                    <g:textField name="merknad" value="${utleggInstance?.merknad}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                	<div id="belopdivlabel">	
                                    	<label for="belop"><g:message code="utlegg.belop.label" default="Beløp" /></label>
                                    </div>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggInstance, field: 'belop', 'errors')}">
                                	<div id="belopdiv">	
                                    	<g:textField name="belop" value="${fieldValue(bean: utleggInstance, field: 'belop')}" size="5" /> <g:message code="sil.kr" />
                                	</div>    
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                	<div id="fraTidLabel">
                                		<label for="fraTid">Fra</label>
                                	</div>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggFraTilCommand, field: 'fraTid', 'errors')}">
                                	<div id="fraTid">
                                		<g:textField name="fraTid" id="fraTidtF" onchange="kolon('fraTidtF')" value="${fraTid}" size="5"/>
                                	</div>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                	<div id="tilTidLabel">
                                		<label for="tilTid">Til</label>
                                	</div>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggFraTilCommand, field: 'tilTid', 'errors')}">
                                	<div id="tilTid">
                                		<g:textField name="tilTid" id="tilTidtF" onchange="kolon('tilTidtF')" value="${tilTid}" size="5"/>
                                	</div>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                	<div id="kostTilSted">
                                		<label for="kostTilSted">Til sted</label>
                                	</div>  
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: utleggFraTilCommand, field: 'kostTilSted', 'errors')}">
                                	<div id="kostTilStedLabel">
                                		<g:textField name="kostTilSted" value="${utleggInstance?.kostTilSted}" />
                                	</div>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt"  /></span>
                </div>
            </g:form>
        </div>
        <script type="text/javascript">
        utleggTypeEndret();
        </script>
    </body>
</html>
