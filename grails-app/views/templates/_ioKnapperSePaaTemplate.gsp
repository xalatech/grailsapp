<div class="buttons">
	<span class="menuButton"><g:link action="searchResult"><g:message code="sivadm.intervjuobjekt.tilbake.til.sok" default="Tilbake til søk" /></g:link></span>
	<g:if test="${!laastAv}">
		<span class="menuButton"><g:link class="lock" action="edit" id="${intervjuObjektInstance.id}"><g:message code="sivadm.intervjuobjekt.laas.opp" default="Lås/Rediger" /></g:link></span>
	</g:if>

	<sec:ifAllGranted roles="ROLE_ADMIN">
		<span class="menuButton"><g:link controller="meldingInn" action="list" params="${[fraIo: true, ioId: intervjuObjektInstance.id, se: true]}" title="Se meldinger inn (fra blaise) for dette intervjuobjektet"><g:message code="sivadm.intervjuer.meldinger.inn" default="Meldinger inn" /></g:link></span>
		<span class="menuButton"><g:link controller="meldingUt" action="list" params="${[fraIo: true, ioId: intervjuObjektInstance.id, se: true]}" title="Se meldinger ut (til blaise) for dette intervjuobjektet"><g:message code="sivadm.intervjuer.meldinger.ut" default="Meldinger ut" /></g:link></span>
	</sec:ifAllGranted>
	<span class="menuButton"><g:link action="previousIntervjuObjekt" params="${[se: true]}" title="Hurtigtast CTRL + piltast-venstre"><g:message code="sivadm.forrige" default="Forrige" /></g:link></span>
	<span class="menuButton"><g:link action="nextIntervjuObjekt" params="${[se: true]}" title="Hurtigtast CTRL + piltast-høyre"><g:message code="sivadm.neste" default="Neste" /></g:link></span>
</div>