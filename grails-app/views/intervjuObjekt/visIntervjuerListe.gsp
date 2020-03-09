<%@ page contentType="text/html;charset=ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main2" />
<title>Insert title here</title>
</head>
<body>
	<div class="body">
		
		<h1>Velg intervjuer</h1>
		<div class="list">
			<table>
				<thead>
					<tr>

						<g:sortableColumn property="initialer"
							title="${message(code: 'intervjuer.initialer.label', default: 'Initialer')}" />

						<g:sortableColumn property="navn"
							title="${message(code: 'intervjuer.navn.label', default: 'Navn')}" />

						<g:sortableColumn property="klynge"
							title="${message(code: 'intervjuer.klynge.label', default: 'Klynge')}" />
					</tr>
				</thead>
				
				<tbody>
					<g:each in="${intervjuerInstanceList}" status="i" var="intervjuerInstance">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><g:link action="velgIntervjuer" id="${intervjuerInstance.initialer}">${fieldValue(bean: intervjuerInstance, field: "initialer")}</g:link></td>
                        	
                        	<td><g:link action="velgIntervjuer" id="${intervjuerInstance.initialer}">${fieldValue(bean: intervjuerInstance, field: "navn")}</g:link></td>
                        	
                        	<td>${fieldValue(bean: intervjuerInstance, field: "klynge")}</td>
						</tr>
					</g:each>
				</tbody>
				
			</table>

		</div>
</body>
</html>