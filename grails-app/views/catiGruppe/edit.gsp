
<%@ page import="sivadm.CatiGruppe"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main2" />
<g:set var="entityName"
	value="${message(code: 'catiGruppe.label', default: 'CatiGruppe')}" />
<title><g:message code="default.edit.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton">
			<g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
		</span>
		<span class="menuButton">
			<g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
		</span>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:if test="${ flash.errorMessage }">
			<div class=errors>
				${flash.errorMessage}
			</div>
		</g:if>
		<g:hasErrors bean="${catiGruppeInstance}">
			<div class="errors">
				<g:renderErrors bean="${catiGruppeInstance}" as="list" />
			</div>
		</g:hasErrors>
		
		<g:form method="post">
			<g:hiddenField name="id" value="${catiGruppeInstance?.id}" />
			<g:hiddenField name="version" value="${catiGruppeInstance?.version}" />
			<div class="dialog">
				<table style="border: 0px">
					<tbody>
						<tr class="prop">
							<td valign="top" class="name">
								<label for="navn"><g:message code="catiGruppe.navn.label" default="Navn" /></label>
							</td>
							<td valign="top" class="value ${hasErrors(bean: catiGruppeInstance, field: 'navn', 'errors')}">
								<g:textField name="navn" value="${catiGruppeInstance?.navn}" />
							</td>
						</tr>
						
						<tr>
							<td valign="top" class="name">
								<label for="klynge">Klynge</label>
							</td>
							<td valign="top" class="value">
								<g:select name="klynge" from="${klynger}" noSelection="['alle':'Alle klynger']" optionKey="id" value="${klynge}"></g:select>
								<g:actionSubmit action="edit" value="Oppdater intervjuliste" title="Oppdaterer intervjuerlisten med intervjuere fra valgt klynge"></g:actionSubmit>
							</td>
						</tr>

					</tbody>
				</table>

				<div style="width: 700px">
					<table style="border: 0px">
						<tbody>
							<tr class="prop">
								<td valign="top" class="name">
									<label for="skjemaer"><g:message code="catiGruppe.skjemaer.label" default="Valgte skjema" /></label>
								</td>
								<td valign="top">
									<label><g:message code="catiGruppe.intervjuere.label" default="Velg fra listen" /></label>
								</td>
								<td></td>
								<td>Valgte intervjuere</td>
							</tr>
							
							<tr class="prop">
								<td valign="top" class="value ${hasErrors(bean: catiGruppeInstance, field: 'skjemaer', 'errors')}">
									<g:select name="skjemaer"
										from="${sortertSkjemaList}"
										multiple="yes"
										optionKey="id"
										size="15"
										value="${catiGruppeInstance?.skjemaer}" />
								</td>
								<td valign="top">
									<g:select name="intervjuer"
										from="${ikkeValgteIntervjuere}"
										optionValue="initialerOgNavn"
										optionKey="id"
										size="15"
										style="width: 200px;" />
								</td>

								<td>
									<g:actionSubmit value="&gt;" action="addIntervjuer" />
									<br><g:actionSubmit value="&gt;&gt;" action="addAllIntervjuere" />
									<br><g:actionSubmit	value="&lt;" action=" removeIntervjuer" />
									<br><g:actionSubmit value="&lt;&lt;" action=" removeAllIntervjuer" />
								</td>

								<td>
									<g:select name="valgtIntervjuer"
										from="${valgteIntervjuere}"
										optionValue="initialerOgNavn"
										optionKey="id"
										size="15"
										style="width: 200px;" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="buttons">
				<span class="button">
					<g:actionSubmit class="save" action="update" value="Oppdater gruppe" />
				</span> 
			</div>
		</g:form>
	</div>
</body>
</html>