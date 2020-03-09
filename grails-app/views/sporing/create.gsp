

<%@ page import="sivadm.Sporing" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'sporing.label', default: 'Sporing')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sporingInstance}">
            <div class="errors">
                <g:renderErrors bean="${sporingInstance}" as="list" />
            </div>
            </g:hasErrors>            
            
            
            
            <div class="list">
            <h2>Sporingsresultater for ${intervjuObjektInstance}</h2>
            <g:if test="${sporingInstanceList?.size() == 0}">
            	<br/>
            	<p>Ingen tidligere sporinger for intervjuobjekt funnet.</p>
            	<br/>
            </g:if>
            
            <g:else>
                        
                <table>
                    <thead>
                        <tr>
                        
                            <th>Resultat</th>
                            
                            <th>Kilde</th>
                            
                            <th>Kommentar</th>
                            
                            <th>Dato</th>
                            
                            <th>Av</th>
                            
                             <th/>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sporingInstanceList}" status="i" var="sporingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${sporingInstance.hentResultatListe()}</td>
                            
                            <td>${sporingInstance.hentKildeListe()}</td>
                            
                            <td>${sporingInstance.kommentar}</td>
                                                                           
                            <td style="width: 120px;"><g:formatDate date="${sporingInstance.redigertDato}" format="dd.MM.yyyy hh:mm:ss" /></td>
                            
                            <td>${sporingInstance.redigertAv}</td>
                            
                            <td>
                            	<a href="#" onclick="return apneSlettDialog(${sporingInstance.id})"><g:slettIkon /></a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <g:slettDialog domeneKlasse="sporing" controller="sporing" action="delete"  paramsNavn="['ioId']" paramsVerdier="${[intervjuObjektInstance?.id]}" />
                
                </g:else>
                
            </div>
            
            <g:form action="save" method="post" >
            	<g:hiddenField name="intervjuObjektId" value="${intervjuObjektInstance?.id}"/>
                <div class="dialog">
                
                	<h2>Legg til ny sporing</h2>
                    <table>
                        <tbody>
                        
                        	<tr class="prop2">
                        		<td>
                        			RESULTAT
                        		</td>
                        		<td></td>
                        		
                        		<td class="space"/>
                        		
                        		<td>
                        			KILDE
                        		</td>
                        		<td></td>
                        		
                        	</tr>
                        
                        	<tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="nyttTelefonnr"><g:message code="sporing.nyttTelefonnr.label" default="Nytt telefonnr" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'nyttTelefonnr', 'errors')}">
                                    <g:checkBox name="nyttTelefonnr" value="${sporingInstance?.nyttTelefonnr}" />
                                </td>
                                
                                <td class="space"/>
                                
                                <td valign="top" class="name">
                                    <label for="guleSider"><g:message code="sporing.guleSider.label" default="Gule Sider/1880" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'guleSider', 'errors')}">
                                    <g:checkBox name="guleSider" value="${sporingInstance?.guleSider}" />
                                </td>
                            </tr>
                        
                            <tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="adresseEndring"><g:message code="sporing.adresseEndring.label" default="Adresseendring" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'adresseEndring', 'errors')}">
                                    <g:checkBox name="adresseEndring" value="${sporingInstance?.adresseEndring}" />
                                </td>
                                
                                <td class="space"/>
                                
                                <td valign="top" class="name">
                                    <label for="opplysningen"><g:message code="sporing.opplysningen.label" default="Opplysningen 1881" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'opplysningen', 'errors')}">
                                    <g:checkBox name="opplysningen" value="${sporingInstance?.opplysningen}" />
                                </td>
                            </tr>
                            
                             <tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="navnEndring"><g:message code="sporing.navnEndring.label" default="Navneendring" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'navnEndring', 'errors')}">
                                    <g:checkBox name="navnEndring" value="${sporingInstance?.navnEndring}" />
                                </td>
                                
                                <td class="space"/>
                                
                                 <td valign="top" class="name">
                                    <label for="google"><g:message code="sporing.google.label" default="Google" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'google', 'errors')}">
                                    <g:checkBox name="google" value="${sporingInstance?.google}" />
                                </td>
                            </tr>
                            
                            <tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="nyBeboer"><g:message code="sporing.nyBeboer.label" default="Ny beboer" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'nyBeboer', 'errors')}">
                                    <g:checkBox name="nyBeboer" value="${sporingInstance?.nyBeboer}" />
                                </td>
                                
                                <td class="space"/>
                                
                                 <td valign="top" class="name">
                                    <label for="bereg"><g:message code="sporing.bereg.label" default="Bereg/BOF" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'bereg', 'errors')}">
                                    <g:checkBox name="bereg" value="${sporingInstance?.bereg}" />
                                </td>
                            </tr>
                            
                            <tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="husholdningEndring"><g:message code="sporing.husholdningEndring.label" default="Husholdningsendring" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'husholdningEndring', 'errors')}">
                                    <g:checkBox name="husholdningEndring" value="${sporingInstance?.husholdningEndring}" />
                                </td>
                                
                                <td class="space"/>
                                
                                 <td valign="top" class="name">
                                    <label for="telefonbrev"><g:message code="sporing.telefonbrev.label" default="Telefonbrev" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'telefonbrev', 'errors')}">
                                    <g:checkBox name="telefonbrev" value="${sporingInstance?.telefonbrev}" />
                                </td>
                            </tr>
                        
                            <tr class="prop2">
                                <td valign="top" class="name">
                                    <label for="ingenting"><g:message code="sporing.ingenting.label" default="Ingenting" /></label>
                                </td>
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'ingenting', 'errors')}">
                                    <g:checkBox name="ingenting" value="${sporingInstance?.ingenting}" />
                                </td>
                                
                                <td class="space"/>
                                
                                <td valign="top" class="name">
                                    <label for="annet"><g:message code="sporing.annet.label" default="Annet" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sporingInstance, field: 'annet', 'errors')}">
                                    <g:checkBox name="annet" value="${sporingInstance?.annet}" />
                                </td>
                            </tr>
                         </tbody>   
                        </table>
                   
                   <br/>
                    
                    <table>
                    	<tbody>
                    		<tr class="prop2">
                    			<td>KOMMENTAR</td>
                    		</tr>
                    	
                    		<tr class="prop2">
                                <td valign="top" class="name ${hasErrors(bean: sporingInstance, field: 'kommentar', 'errors')}">
                                    <g:textArea name="kommentar" value="${sporingInstance?.kommentar}" rows="4" cols="50"/>
                                </td>
                            </tr> 
                    	
                    	</tbody>
                    
                    </table>
                    </div>
                
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Opprett" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="avbryt" value="Avbryt" /></span>
                </div>
                
            </g:form>
        </div>
    </body>
</html>
