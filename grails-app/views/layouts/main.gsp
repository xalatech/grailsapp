<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
	<title><g:layoutTitle default="SivAdm" /></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
	<asset:stylesheet src="application.css"/>
	<asset:javascript src="application.js"/>
    <g:layoutHead />
</head>
<body class="">
	<div class="wrapper ">
		<div class="sidebar">
			<g:render template="/layouts/menu"/>
		</div>
		<div class="main-panel">
			<nav class="navbar navbar-expand-lg navbar-absolute fixed-top navbar-transparent">
				<div class="container-fluid">
					<div class="col-3">
						<p style="text-transform: uppercase;font-weight: bold;">${pageTitle}</p>
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
	</body>
</html>