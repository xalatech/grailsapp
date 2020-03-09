<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
    </head>
    <body>
        
        <div class="body">
        
        	<h1>Husk å kjøre synkronisering for å sende intervjuresultater</h1>
        	
        	<applet code="view.applet.CapiApplet.class"
                        width="500" height="150"
                        archive="${resource(dir:'applet',file:'sAppletLibrary.jar')}">
                    <param name="intervjuerCapiSkjemaVersionList" value="${intervjuerCapiSkjemaVersionList}" />
                    <param name="blaiseSkjemaPath" value="${blaiseCapiSkjemaPath}" />
                    <param name="localSkjemaPath" value="${localSkjemaPath}"/>
                    <param name="blaiseApplicationPath" value="${blaiseApplicationPath}"/>
                    <param name="localSystemKommandoPath" value="${localSystemKommandoPath}"/>
                    <param name="serverSystemKommandoPath" value="${serverSystemKommandoPath}"/>
                    <param name="systemKommandoList" value="${systemKommandoList}"/>
                    <param name="intervjuerInitialer" value="${intervjuerInitialer}"/>
                    <param name="serverSystemKommandoHttpPath" value="${serverSystemKommandoHttpPath}"/>
            </applet>
            
            <g:if env="development">
            	<br/>
     			NB! Kjører i development-miljø. Input parametre til applet:
     			<br/><br/>
     			Inputparametre til applet er: 
     			<br/>
     			- blaiseApplicationPath = "${blaiseApplicationPath}"
     			<br/>
     			- blaiseCapiSkjemaPath = "${blaiseCapiSkjemaPath}"
     			<br/>
     			- localSkjemaPath = "${localSkjemaPath}" 
     			<br/>
     			- intervjuerCapiSkjemaVersionList = "${intervjuerCapiSkjemaVersionList}"
     			
			</g:if>
            
            <h1>Mine personlige undersokelser</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">
            	
            	<table>
                    
                    <thead>
                        <tr>
                        	
                        	<g:sortableColumn property="skjemaNavn" title="${message(code: 'utvalgtSkjema.skjemaNavn.label', default: 'Skjemanavn')}" />
                            
                            <g:sortableColumn property="skjemaKortNavn" title="${message(code: 'utvalgtSkjema.skjemaKortNavn.label', default: 'Skjema kortnavn')}" />
                            
                            <g:sortableColumn property="skjemaVersjon" title="${message(code: 'utvalgtSkjema.skjemaKortNavn.label', default: 'Versjon')}" />
                            
                            <g:sortableColumn property="antallIoTilordnet" title="${message(code: 'utvalgtSkjema.antallIoTilordnet.label', default: 'Antall IO tilordnet')}" />
                            
                            <th>Skjema lokalt</th>
                            
                            <th>Detaljer</th>
                            
                        </tr>
                    </thead>
                    
                    <tbody>
                    	
                    	<g:each in="${utvalgtCapiSkjemaInstanceList}" status="i" var="utvalgtCapiSkjemaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: utvalgtCapiSkjemaInstance, field: "skjemaNavn")}</td>
                            
                            <td>${fieldValue(bean: utvalgtCapiSkjemaInstance, field: "skjemaKortNavn")}</td>
                            
                            <td>${fieldValue(bean: utvalgtCapiSkjemaInstance, field: "skjemaVersjon")}</td>
                            
                            <td>${fieldValue(bean: utvalgtCapiSkjemaInstance, field: "antallIntervjuObjekterTilordnet")}</td>
                            
                            <td></td>
                            
                            <td>
                            	<g:link action="listCapiIntervjuObjekter" id="${utvalgtCapiSkjemaInstance.skjemaId}">Vis intervjuobjekter</g:link>
                            </td>
                        </tr>
                        </g:each>
                    	
                    </tbody>
                </table>
              
                            
            
            </div>
            
            
        </div>
    </body>
</html>
