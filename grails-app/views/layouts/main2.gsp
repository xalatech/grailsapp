<html>
    <head>
        <title><g:layoutTitle default="SivAdm" /></title>

        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        
        <link rel="stylesheet" href="${resource(dir:'css/redmond',file:'jquery-ui-1.8.10.custom.css')}" />
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery-1.4.4.min.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery.ui.datepicker-no.js')}"></script>
        <script type="text/javascript" src="${resource(dir:'js/jquery',file:'jquery-ui-1.8.10.custom.min.js')}"></script> 
        
        <script type="text/javascript">
        	jQuery.noConflict();

	        jQuery(function() {
	        	jQuery( "#tabs" ).tabs();
	    	});

	    	function submitForm(formName) {
				var form = document.getElementById(formName);
				if(form) {
					form.submit()
				}
			}	    	    	
        </script>
        
        <g:layoutHead />
        <g:javascript library="application" />
        <r:layoutResources/>
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        
        <div id="menu"  class="menuBody">
        	<g:render template="/layouts/menu"/>
		</div>
		
		<div id="logininfo" style="float: right; margin-top: 5px; margin-right: 15px;">
			<g:render template="/layouts/userinfo"/>
		</div>

		<div id="content" class="restBody">
			<g:layoutBody />
		</div>
        <r:layoutResources/>
    </body>
</html>