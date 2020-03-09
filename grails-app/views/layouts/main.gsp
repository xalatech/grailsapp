<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
	<title><g:layoutTitle default="SivAdm" /></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
	<asset:stylesheet src="application.css"/>
	<asset:stylesheet src="redmond/jquery-ui-1.8.10.custom.css"/>
	<asset:javascript src="application.js"/>
	<asset:javascript src="jquery/jquery.ui.datepicker-no.js"/>
	<asset:javascript src="jquery/jquery-ui-1.8.10.custom.min.js"/>
    <g:layoutHead />
</head>
<body class="">
	<div class="wrapper ">
		<div class="sidebar" data-color="yellow" data-active-color="success">
			<g:render template="/layouts/menu"/>
		</div>
		<div class="main-panel">
			<nav class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
				<div class="container-fluid">
					<div class="col-3">
						<p style="text-transform: uppercase;">${pageTitle}</p>
					</div>
					<div class="col-3">
						Miljøet: ${grails.util.Environment.current.name}
					</div>
					<div class="navbar-nav ml-auto">
						<g:render template="/layouts/userinfo"/>
					</div>
				</div>
			</nav>
			<div class="content">
				<div id="spinner" class="spinner" style="display:none;">
					<asset:image src="spinner.gif" alt="Spinner"/>
				</div>
				<g:layoutBody />
			</div>
		</div>
	</div>

%{--	<asset:javascript src="jquery/jquery.ui.resizable.js"/>
    <asset:javascript src="jquery/jquery.ui.position.js"/>
    <asset:javascript src="jquery/jquery.ui.mouse.js"/>
    <asset:javascript src="jquery/jquery.ui.draggable.js"/>
    <asset:javascript src="jquery/jquery.ui.dialog.js"/>
    <asset:javascript src="jquery/jquery.ui.core.js"/>
    <asset:javascript src="jquery/jquery.ui.widget.js"/>
    <asset:javascript src="jquery/jquery.ui.resizable.js"/>
    <asset:javascript src="jquery/jquery.ui.resizable.js"/>
    <asset:javascript src="slettDialog.js"/>--}%

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