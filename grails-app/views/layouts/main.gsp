<html>
    <head>
        <title><g:layoutTitle default="SivAdm" /></title>
		<asset:stylesheet src="application.css"/>
		<asset:stylesheet src="redmond/jquery-ui-1.8.10.custom.css"/>
		<asset:javascript src="application.js"/>
		<asset:javascript src="jquery/jquery.ui.datepicker-no.js"/>
		<asset:javascript src="jquery/jquery-ui-1.8.10.custom.min.js"/>
	   <g:layoutHead />
        <r:layoutResources/>
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
			<asset:image src="spinner.gif" alt="Spinner"/>
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

		<asset:javascript src="jquery/jquery.ui.resizable.js"/>
		<asset:javascript src="jquery/jquery.ui.position.js"/>
		<asset:javascript src="jquery/jquery.ui.mouse.js"/>
		<asset:javascript src="jquery/jquery.ui.draggable.js"/>
		<asset:javascript src="jquery/jquery.ui.dialog.js"/>
		<asset:javascript src="jquery/jquery.ui.core.js"/>
		<asset:javascript src="jquery/jquery.ui.widget.js"/>
		<asset:javascript src="jquery/jquery.ui.resizable.js"/>
		<asset:javascript src="jquery/jquery.ui.resizable.js"/>
		<asset:javascript src="slettDialog.js"/>

	<script type="text/javascript">
		function submitForm(formName) {
			console.log(formName);

		/*	var form = document.getElementById(formName);
			if(form) {
				form.submit()
			}*/
		}
	</script>

	</body>
</html>