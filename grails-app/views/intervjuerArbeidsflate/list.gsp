
<%@ page import="siv.data.UtvalgtSkjema" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
    </head>
    <body>
        
        <div class="body">
            <h1>Mine telefonundersokelser</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="skjema" title="${message(code: 'utvalgtSkjema.skjema.label', default: 'Skjemanavn')}" />
                            
                            <g:sortableColumn property="skjemaVersjon" title="${message(code: 'utvalgtSkjema.skjemaVersjon.label', default: 'Skjemaversjon')}" />
                        
                            <g:sortableColumn property="oppstart" title="${message(code: 'utvalgtSkjema.oppstart.label', default: 'Oppstart')}" />
                            
                            <th>Blaise ok</th>
                            
                            <th></th>
                            
                            <th></th>
                            
                            <th></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${utvalgtSkjemaInstanceList}" status="i" var="utvalgtSkjemaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                         
                            <td>${fieldValue(bean: utvalgtSkjemaInstance, field: "skjemaKortNavn")}</td>
                            
                            <td>${fieldValue(bean: utvalgtSkjemaInstance, field: "skjemaVersjon")}</td>
                        
                            <td><g:formatDate date="${utvalgtSkjemaInstance.oppstart}" /></td>
                            
                            <td>
                            	<applet code="view.applet.FileCheckApplet.class"
									width="15" height="15" archive="${resource(dir:'applet',file:'sAppletLibrary.jar')}">
									<param name="blaiseApplicationPath" value="${blaiseApplicationPath}" />
									<param name="blaiseSkjemaPath" value="${blaiseSkjemaPath}" />
									<param name="skjemaKortNavn" value="${utvalgtSkjemaInstance.skjemaKortNavn}" />
									<param name="skjemaVersjon" value="${utvalgtSkjemaInstance.skjemaVersjon}" />
	                                <param name="checkType" value="CATI"/>
								</applet>
                            </td>
                            
                            <td><g:link action="startIntervju" params="[appletModus: 'BLAISE', skjemaKortNavn: utvalgtSkjemaInstance.skjemaKortNavn, skjemaVersjon: utvalgtSkjemaInstance.skjemaVersjon]" >Start intervju</g:link></td>
                            
                            <td><g:link action="startIntervju" params="[appletModus: 'BLAISE_TEST', skjemaKortNavn: utvalgtSkjemaInstance.skjemaKortNavn, skjemaVersjon: utvalgtSkjemaInstance.skjemaVersjon]" >Test intervju</g:link></td>
                            
                            <td><g:link action="startIntervju" params="[appletModus: 'RETNINGSLINJER', skjemaKortNavn: utvalgtSkjemaInstance.skjemaKortNavn, skjemaVersjon: utvalgtSkjemaInstance.skjemaVersjon]" >Øv på intervju</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
            <g:if env="development" test="${flash.start == 'true'}">
            	
     			<br/><br/>
     			Inputparametre til applet er: 
     			<br/>
     			- blaiseApplicationPath = "${blaiseApplicationPath}"
     			<br/>
     			- blaiseSkjemaPath = "${blaiseSkjemaPath}"
     			<br/>
     			- skjemaKortNavn = "${skjemaKortNavn}" 
     			<br/>
     			- skjemaVersjon = "${skjemaVersjon}"
     			<br/>
     			- appletModus = "${appletModus}" 
			</g:if>
			
			<g:if test="${flash.start == 'true'}">
     			<applet code="view.applet.BlaiseApplet.class"
				width="1" height="1" archive="${resource(dir:'applet',file:'sAppletLibrary.jar')}">
				
				<param name="blaiseApplicationPath" value="${blaiseApplicationPath}" />
				<param name="blaiseSkjemaPath" value="${blaiseSkjemaPath}" />
				<param name="skjemaKortNavn" value="${skjemaKortNavn}" /> 
				<param name="skjemaVersjon" value="${skjemaVersjon}" />
				<param name="whatToDo" value="${appletModus}" />
				</applet>
			</g:if>
			
			
			
            
            
            
            
        </div>
    </body>
</html>
