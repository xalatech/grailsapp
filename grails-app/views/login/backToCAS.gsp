<head>
<meta name='layout' content='main2' />
<title>Login</title>
<style type='text/css' media='screen'>
#login {
	margin:15px 0px; padding:0px;
	text-align:center;
}
#login .inner {
	width:260px;
	margin:0px auto;
	text-align:left;
	padding:10px;
	border-top:1px dashed #499ede;
	border-bottom:1px dashed #499ede;
	background-color:#EEF;
}
#login .inner .fheader {
	padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
}
#login .inner .login_message {color:red;}
#login .inner .text_ {width:120px;}
#login .inner .chk {height:12px;}
</style>
</head>

<body>
	<div id='login'>
		<div class='inner'>
			<div class='fheader'>Ditt brukernavn er godkjent i CAS men ikke kjent i SIV </div>
			<h2>Ta kontakt med brukeransvarlig i SIV for å bli lagt inn i brukerdatabasen</h2>
			<h2>...eller</h2>
			<h2>Logg ut fra CAS og logg inn igjen med et annet brukernavn og passord</h2>
			<br>&nbsp;<br> 
			<g:link controller='logout'> Log ut fra CAS</g:link>
		</div>
	</div>
</body>
