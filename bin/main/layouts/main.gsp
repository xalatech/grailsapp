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
<body>
			<nav class="navbar topNav navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">

				<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">SIV Admin</a>
						<p class="pagetitle">${pageTitle}</p>
						<p>Miljøet: ${grails.util.Environment.current.name}</p>
				<ul class="navbar-nav px-3">
					<li class="nav-item text-nowrap">
						<g:render template="/layouts/userinfo"/>
					</li>
				</ul>

			</nav>
			<div class="container-fluid">
				<div class="row">
					<nav class="col-md-2 d-none d-md-block bg-light sidebar">
						<g:render template="/layouts/menu"/>
					</nav>
					<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
						<div id="spinner" class="spinner" style="display:none;">
							<asset:image src="spinner.gif" alt="Spinner"/>
						</div>
						<g:layoutBody />
					</main>
				</div>

			</div>

	</body>
</html>