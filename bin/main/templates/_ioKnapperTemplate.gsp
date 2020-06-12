<div class="buttons">
	<span class="menuButton"><g:link action="searchResult"><g:message code="sivadm.intervjuobjekt.tilbake.til.sok" default="Tilbake til søk" /></g:link></span>

	<span class="button">
		<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	</span>

	<span class="menuButton">
		<g:link class="unlock" action="laasOppIo" id="${intervjuObjektInstance.id}" title="IO er låst for redigering av andre enn deg, lås opp vil gjøre dette IO et redigerbar for andre">
			<g:message code="sivadm.intervjuobjekt.laas.opp" default="Lås opp" />
		</g:link>
	</span>

	<span class="menuButton">
		<g:link class="create" controller="capiAdministrasjon" action="tildel" id="${intervjuObjektInstance.id}" params="[redir: 'io_edit']">
			<g:message code="sivadm.intervjuobjekt.tildel.intervjuer" default="Tildel intervjuer for CAPI" />
		</g:link>
	</span>

	<span class="menuButton">
		<g:link class="create" action="lagAvtale" id="${intervjuObjektInstance.id}">
			<g:message code="sivadm.intervjuobjekt.lag.avtale" default="Lag avtale" />
		</g:link>
	</span>

<%--
Sporingsresultater må fjernes,
se  https://jira.ssb.no/browse/SF-83
<span class="menuButton"><g:link controller="sporing" action="create" id="${intervjuObjektInstance.id}"><g:message code="sivadm.intervjuer.sporingsresultater" default="Sporingsresultater" /></g:link></span>
 --%>

	<sec:ifAllGranted roles="ROLE_ADMIN">
		<span class="menuButton">
			<g:link controller="meldingInn" action="list" params="${[fraIo: true, ioId: intervjuObjektInstance.id]}" title="Se meldinger inn (fra blaise) for dette intervjuobjektet">
				<g:message code="sivadm.intervjuer.meldinger.inn" default="Meldinger inn" />
			</g:link>
		</span>

		<span class="menuButton">
			<g:link controller="meldingUt" action="list" params="${[fraIo: true, ioId: intervjuObjektInstance.id]}" title="Se meldinger ut (til blaise) for dette intervjuobjektet">
				<g:message code="sivadm.intervjuer.meldinger.ut" default="Meldinger ut" />
			</g:link>
		</span>
	</sec:ifAllGranted>

	<span class="menuButton">
		<g:link action="previousIntervjuObjekt" title="Hurtigtast CTRL + piltast-venstre">
			<g:message code="sivadm.forrige" default="Forrige" />
		</g:link>
	</span>

	<span class="menuButton">
		<g:link action="nextIntervjuObjekt" title="Hurtigtast CTRL + piltast-høyre">
			<g:message code="sivadm.neste" default="Neste" />
		</g:link>
	</span>

</div>