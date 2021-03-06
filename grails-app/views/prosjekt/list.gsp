<%@ page import="siv.type.ProsjektFinansiering" %>
<%@ page import="sivadm.Prosjekt"%>
<html>
<head>
	<g:set var="pageTitle" value="Prosjektsøk" scope="request"/>
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'prosjekt.label', default: 'Prosjekt')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="card">
	<div class="card-header">
		<h5 class="card-title">${pageTitle}</h5>
		<g:form method="post">
			<div class="form-row">
				<div class="col-3">
					<g:textField name="produktNummer" placeholder="Produktnummer" class="form-control" id="produktnummer" value="${produktNummer}" />
				</div>
				<div class="col-4">
					<g:textField  name="prosjektNavn"  placeholder="Navn" class="form-control" id="prosjektNavn" value="${prosjektNavn}" />
				</div>
				<div class="col-2">
					<g:select name="prosjektStatus"
							  from="${prosjektStatusList}"
							  optionKey="key"
							  optionValue="guiName"
							  placeholder="Status"
							  class="form-control"
							  value="${prosjektStatusDefault?.key}"
							  noSelection="['' : 'Alle']" />
				</div>
				<div class="col-2">
					<g:textField placeholder="Årsgang" class="form-control" name="prosjektAargang" value="${prosjektAargang}" size="4" maxlength="4" />
				</div>

			</div>
			<div class="form-row mt-2">
				<div class="col-2">
					<g:actionSubmit class="btn btn-primary" action="list" value="Søk prosjekter"></g:actionSubmit>
				</div>
				<div class="col-3">
					<g:link class="btn btn-secondary" action="create"><g:message code="sivadm.prosjekt.nytt" default="Opprett ny prosjekt" /></g:link>
				</div>

			</div>
		</g:form>

	</div>
	<div class="card-body">
		<h5 class="card-title"><g:message code="sivadm.prosjekt.liste" default="Prosjektliste" />
			<g:if test="${prosjektInstanceTotal}"> (${prosjektInstanceTotal})</g:if></h5>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>

		<table class="table table-bordered">
			<thead>
			<tr>
				<g:sortableColumn property="produktNummer"
								  title="${message(code: 'prosjekt.id.label', default: 'Produktnummer')}" />
				<g:sortableColumn property="prosjektNavn"
								  title="${message(code: 'prosjekt.prosjektNavn.label', default: 'Prosjektnavn')}" />

				<g:sortableColumn property="aargang"
								  title="${message(code: 'prosjekt.aargang.label', default: 'Årgang')}" />

				<th>Skjema</th>
				<g:sortableColumn property="prosjektStatus"
								  title="${message(code: 'prosjekt.prosjektStatus.label', default: 'Prosjektstatus')}" />

				<g:sortableColumn property="finansiering"
								  title="${message(code: 'prosjekt.finansiering.label', default: 'Finansiering')}" />
			<th></th>
			</tr>
			</thead>
			<tbody>
			<g:each in="${prosjektInstanceList}" status="i" var="prosjektInstance">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td width="10%">
						<g:link action="edit" id="${prosjektInstance.id}">${fieldValue(bean: prosjektInstance, field: "produktNummer")}</g:link>
					</td>

					<td  width="20%">
						<g:link action="edit" id="${prosjektInstance.id}">${fieldValue(bean: prosjektInstance, field: "prosjektNavn")}</g:link>

					</td>

					<td>
						${fieldValue(bean: prosjektInstance, field: "aargang")}
					</td>

					<td valign="top" style="text-align: left;" class="value">
						<ul class="list-unstyled">
							<g:each in="${prosjektInstance.skjemaer?.sort{it.delProduktNummer}}" var="s">
								<li><g:link controller="skjema" action="edit" id="${s.id}"  params="['fraProsjektListe': true]">
									${s?.encodeAsHTML()}
								</g:link></li>
							</g:each>
						</ul>
					</td>

					<td>
						${fieldValue(bean: prosjektInstance, field: "prosjektStatus")}
					</td>

					<td>
						${fieldValue(bean: prosjektInstance, field: "finansiering")}
						<g:if test="${prosjektInstance?.finansiering == ProsjektFinansiering.STAT_MARKED}">
							(${fieldValue(bean: prosjektInstance, field: "prosentStat")}% <g:message code="sivadm.Stat" default="Stat" /> / ${fieldValue(bean: prosjektInstance, field: "prosentMarked")}% <g:message code="sivadm.Marked" default="Marked" />)
						</g:if>
					</td>
					<td align="center" width="10%">
						<g:link action="edit" id="${prosjektInstance.id}"><g:redigerIkon /></g:link>
						<g:link action="edit" id="${prosjektInstance.id}"><g:redigerIkon /></g:link>
						<button type="button" class="btn btn-sm btn-outline-neutral slettModal" data-toggle="modal" data-target="#slettModal" data-id="${prosjektInstance.id}"><g:slettIkon /></button>
					</td>
				</tr>
			</g:each>
			</tbody>
		</table>
		<div class="paginateButtons">
			<g:paginate	total="${prosjektInstanceTotal}" /></div>
		</div>

	<g:slettDialog formId="0" domeneKlasse="prosjekt" melding="Er du sikker på at du vil slette prosjekt? Dette kan ha negative følger hvis intervjuere har ført timer på dette prosjektet, og disse ennå ikke er sendt til SAP." />
</div>


<g:if test="${flash.message}">
	<div class="card mt-2 mb-2 userMessage">
		<div class="card-body bg-success text-white">
			${flash.message}
		</div>
	</div>

</g:if>
</div>
</body>
</html>
