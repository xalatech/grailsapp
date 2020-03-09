<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
    </head>
    <body>
        
        <div class="body">
        
        	<h1>Lage avtale for intervju objekt i Blaise</h1>
        	
            <applet code="view.applet.BlaiseApplet.class"
				width="1280" height="180"
				archive="${resource(dir:'applet',file:'sAppletLibrary.jar')}">
							
				<param name="blaiseApplicationPath" value="${blaiseApplicationPath}"/>
				<param name="blaiseSkjemaPath" value="${blaiseCapiSkjemaPath}" />
				<param name="intervjuObjektId" value="${intervjuobjektId}" />
				<param name="skjemaKortNavn" value="${skjemaKortNavn}" />
				<param name="skjemaVersjon" value="${skjemaVersjon}" />
				<param name="whatToDo" value="BLAISE_WITH_IO"/>
			</applet>
            
            <g:if env="development">
            	<br/>
     			NB! Kjører i development-miljø. Input parametre til applet:
     			<br/><br/>
     			Inputparametre til applet er: 
     			<br/>
     			- blaiseApplicationPath = "${blaiseApplicationPath}"
     			<br/>
     			- intervjuObjektId = "${intervjuobjektId}"
     			<br />
				- skjemaKortNavn = "${skjemaKortNavn}"
				<br />
				- skjemaVersjon = "${skjemaVersjon}"    			
			</g:if>
            <br/><br/>     
            <g:link action="edit" id="${intervjuobjektId}">Tilbake</g:link>
                       
        </div>
    </body>
</html>
