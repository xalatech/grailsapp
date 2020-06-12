
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
    </head>
    <body>
        
        <div class="body">
            
            <h1>Mine intervjuobjekter for skjema: </h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">	
            	<table>
                    
                    <thead>
                        <tr>
                        	
                        	<g:sortableColumn property="id" title="${message(code: 'utvalgtIntervjuObjekt.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="intervjuObjektNummer" title="${message(code: 'utvalgtIntervjuObjekt.intervjuobjektnummer.label', default: 'IO-nr')}" />
                            
                            <g:sortableColumn property="navn" title="${message(code: 'utvalgtIntervjuObjekt.navn.label', default: 'Navn')}" />
                            
                        </tr>
                    </thead>
                    
                    <tbody>
                    	
                    	<g:each in="${utvalgtCapiIntervjuObjektInstanceList}" status="i" var="utvalgtCapiIntervjuObjektInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: utvalgtCapiIntervjuObjektInstance, field: "id")}</td>
                            
                            <td>${fieldValue(bean: utvalgtCapiIntervjuObjektInstance, field: "intervjuObjektNummer")}</td>
                            
                            <td>${fieldValue(bean: utvalgtCapiIntervjuObjektInstance, field: "navn")}</td>
                            
                        </tr>
                        </g:each>
                    	
                    </tbody>
                </table>
            </div>
            
            <div class="dialog">
            <g:form method="post">
             	<div class="buttons">
             		<span class="button"><g:actionSubmit class="save" action="listCapi"
						value="Tilbake" />
					</span>
            		
            	</div>
            </g:form>
            </div>
           
            
        </div>
    </body>
</html>
