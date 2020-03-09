<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title><g:message code="monitor.tittel" /></title>
        <style type="text/css">
	  		.applicationOK {
	  			background: #EEEEEE url(/sivadmin/images/accept.png) no-repeat scroll 2px 5px;	  			
	  			padding-left: 20px;
	  			border: solid 1px;
	  			margin-bottom: 5px;
	  			padding-top: 5px;
	  		}
	  		.applicationERROR {
	  			background: #EEEEEE url(/sivadmin/images/exclamation.png) no-repeat scroll 2px 5px;	  			
	  			padding-left: 20px;
	  			border: solid 1px;
	  			margin-bottom: 5px;
	  			padding-top: 5px;
	  		}
            .melding {
                padding: 5px;
                margin-left: -20px;
                background: #F7F7F7;
            }
            .seksjon {
                border-bottom:2px groove #EEEEEE;
                padding-bottom:5px;
				margin-left: 20px;
				margin-right: 20px;
            }
			h1 {
				margin-left: 20px;
			}
            h2 {
                margin: 5px 0;
            }
            ul {
                margin-top: 0;
                margin-bottom: 0;
            }
			.info{
				background: #EFEFEF;
				color: #555555;
				font-size: 0.8em;
				text-align: right;
				margin-left: 20px;
				margin-right: 20px;
				margin-top: 5px;
			}
			.info p {
				padding: 0 10px; 
				margin: 0;
				display: inline;
			}
	  		
	  </style>
    </head>
    <body>
		<h1><g:message code="monitor.tittel" /></h1>
		<div class="seksjon">
			<h2><g:message code="monitor.integrasjontest.overskrift" /></h2>
			<g:each var="monitor" in="${monitors}">
				<div class="${monitor.status ? 'applicationOK' : 'applicationERROR'}"><strong><g:message code="${monitor.navn}" />:</strong> ${monitor.beskrivelse}
					<g:if test="${!monitor.status}">
						<div class="melding">
							${monitor.feilmelding}
						</div>
					</g:if>
				</div>
			</g:each>
		</div>
				
		<div class="info">
			<p><g:message code="monitor.system.host" />: <strong>${hostname}</strong></p>
			<p><g:message code="monitor.system.time" />: <strong>${timestamp}</strong></p>
		</div>
		
		<br>
		<div class="seksjon">
			<g:link action="nullstillMonitors">Nullstill monitorer</g:link>&nbsp;(Nullstiller monitorer som gjelder Quartz-jobber)
		</div>
		<br>
		
		<div class="seksjon">
			<h2><g:message code="monitor.system.egenskaper.overskrift" /></h2>
			<ul>
				<li><g:message code="monitor.system.applikasjon" />: <g:meta name="app.version"></g:meta></li>
				<li><g:message code="monitor.system.grails" />: <g:meta name="app.grails.version"></g:meta></li>
				<li><g:message code="monitor.system.jvm" />: ${System.getProperty('java.version')}</li>
				<li><g:message code="monitor.system.kontrollere" />: ${grailsApplication.controllerClasses.size()}</li>
				<li><g:message code="monitor.system.domener" />: ${grailsApplication.domainClasses.size()}</li>
				<li><g:message code="monitor.system.servicer" />: ${grailsApplication.serviceClasses.size()}</li>
				<li><g:message code="monitor.system.taglibs" />: ${grailsApplication.tagLibClasses.size()}</li>
			</ul>
		</div>
		
		<div class="seksjon">			
			<h2><g:message code="monitor.kontrollere.overskrift" /></h2>
            <ul>
              <g:each var="c" in="${grailsApplication.controllerClasses.sort{it.fullName}}">
                    <li>${c.fullName}</li>
              </g:each>
            </ul>
	   </div>
	   <div class="seksjon">	
			<h2><g:message code="monitor.plugins.overskrift" /></h2>
			<ul>
				<g:set var="pluginManager" value="${applicationContext.getBean('pluginManager')}"></g:set>
				<g:each var="plugin" in="${pluginManager.allPlugins.sort{it.name}}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>
    </body>
</html>
