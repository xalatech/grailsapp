<%@ page import="sil.Krav" %>
<%@ page import="sivadm.Klynge" %>
<%@ page import="sivadm.Intervjuer" %>
<%@ page import="sil.type.KravType"%>
<%@ page import="sil.type.KravStatus"%>
<%@ page import="siv.type.*"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <g:set var="entityName" value="${message(code: 'krav.label', default: 'Krav')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
		<g:javascript src="kravSok.js"/>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="xx" args="[entityName]" default="Krav liste" /></h1>
            <g:if test="${ flash.errorMessage }">
				<div class=errors>${flash.errorMessage}</div>
			</g:if>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <g:form action="searchResult" method="post">

				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name">
									<label for="kravType">
										<g:message code="xx" default="Type" />
									</label>
								</td>
								<td valign="top">
									<g:select name="kravType"
										from="${sil.type.KravType.values()}"
										optionKey="key"
										optionValue="guiName"
										noSelection="['':'-Velg type']" value="${searchDataKrav?.kravType?.key}"/>
								</td>
								<td rowspan="6" valign="top" class="name">
									<label for="kravStatus">
										<g:message code="xx" default="Status" />
									</label>
								</td>
								<td rowspan="7" valign="top">
									<g:select name="kravStatus"
										from="${sil.type.KravStatus.values()}"
										optionKey="key"
										optionValue="guiLongName"
										multiple="true"
										noSelection="['':'-Velg status(er)']" value="${searchDataKrav.kravStatus}"
										size="12"/>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for=klynge>
										<g:message code="xx" default="Klynge" />
									</label>
								</td>
								<td valign="top">
									<g:select name="klynge"
										from="${Klynge.list()}"
										noSelection="['':'-Velg klynge']" optionKey="id" value="${searchDataKrav.klynge}"/>
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for=intervjuer>
										<g:message code="xx" default="Intervjuer" />
									</label>
								</td>
								<td valign="top">
									<g:select name="intervjuer"
											  id="intervjuer"
										      from="${Intervjuer.listOrderByNavn()}"
											  noSelection="['':'-Velg intervjuer']"
											  optionKey="id"
											  value="${searchDataKrav.intervjuer}"
											  onchange="intervjuerListeEndret()"/>
								</td>
							</tr>

						<tr class="prop">
							<td valign="top" class="name">
								<label for=initialer>
									<g:message code="xx" default="Intervjuer initialer" />
								</label>
							</td>
							<td valign="top">
								<g:textField name="initialer"
									         id="initialer"
											 size="3"
											 maxlength="3"
											 value="${searchDataKrav.initialer}"
											 onchange="intervjuerInitialerEndret()" />
							</td>
						</tr>


							<tr class="prop">
								<td valign="top" class="name">
									<label for=fraDato>
										<g:message code="xx" default="Fra dato" />
									</label>
								</td>
								<td valign="top">
									<g:datoVelger id="dp-1" name="fraDato" value="${searchDataKrav.fraDato}" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for=tilDato>
										<g:message code="xx" default="Til dato" />
									</label>
								</td>
								<td valign="top">
									<g:datoVelger id="dp-2" name="tilDato" value="${searchDataKrav.tilDato}" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for=produktNummer>
										<g:message code="xx" default="Produktnummer" />
									</label>
								</td>
								<td valign="top">
									<g:textField name="produktNummer" value="${searchDataKrav.produktNummer}" />
								</td>
							</tr>
							<tr class="prop">
								<td valign="top" class="name">
									<label for="bilagsnummer">
										<g:message code="xx" default="Bilagsnr." />
									</label>
								</td>
								<td valign="top">
									<g:textField name="bilagsnummer" value="${searchDataKrav.bilagsnummer}" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<g:hiddenField name="sokPerfomed" value="true" />
				<g:actionSubmit value="${message(code: 'sil.sok', default: 'Søk')}" action="searchResult" />
				<g:actionSubmit value="${message(code: 'sil.nullstill.felter', default: 'Nullstill felter')}" action="searchResultNullstill" />
			</g:form>
			
			<h1> </h1>
            
            <g:if test="${pageTitleInfo}"><h4><g:message code="${pageTitleInfo}" /></h4><br><br></g:if>
            <g:form>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'kupong.id.label', default: 'Id')}" />
                            <g:sortableColumn property="intervjuer" title="${message(code: 'kupong.navn.label', default: 'Intervjuer')}" />
                            <g:sortableColumn property="dato" title="${message(code: 'kupong.dato.label', default: 'Dato')}" />
                            <g:sortableColumn property="kravType" title="${message(code: 'kupong.navn.label', default: 'Type')}" />
                            <g:sortableColumn property="kravStatus" title="${message(code: 'kupong.dato.label', default: 'Status')}" />
                            <g:sortableColumn property="antall" title="${message(code: 'kupong.dato.label', default: 'Verdi')}" />
                        </tr>
                    </thead>
                    <tbody>
                    	<g:each in="${kravInstanceList}" status="i" var="kravInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                            <td>
	                            	<g:link action="behandleIntervjuerKrav" params="${[id: kravInstance.intervjuer?.id, kravId: kravInstance.id]}">${fieldValue(bean: kravInstance, field: "id")}</g:link>
	                            </td>
	                        	<td><g:link action="behandleIntervjuerKrav" id="${kravInstance.intervjuer?.id}">${fieldValue(bean: kravInstance, field: "intervjuer")}</g:link></td>
	                        	<td><g:formatDate format="dd.MM.yyyy" date="${kravInstance.dato}" /></td>
	                            <td>
	                            	<g:if test="${kravInstance.kravType == KravType.U}">
	                            		${fieldValue(bean: kravInstance, field: "kravType")} / ${kravInstance?.utlegg?.utleggType?.guiName}
	                            	</g:if>
	                            	<g:else>
	                            		${fieldValue(bean: kravInstance, field: "kravType")}
	                            	</g:else>
	                            </td>
	                        	<td>
	                        		<g:if test="${kravInstance.kravStatus == KravStatus.FEILET_AUTOMATISK_KONTROLL}">
	                        			<g:message code="sil.kontroll.status.feilet.automatisk" args="${[kravInstance.automatiskeKontroller.size()]}" />
	                        		</g:if>
	                        		<g:else>${fieldValue(bean: kravInstance, field: "kravStatus")}</g:else>
	                        	</td>
	                            <td style="text-align: right;">
		                            <g:if test="${kravInstance.kravType == KravType.U}">
		                            	
		                            	<g:if test="${kravInstance?.utlegg?.utleggType == UtleggType.KOST_GODT}">
		                            		---
		                            	</g:if>
		                            	<g:else>
		                            		<g:formatNumber number="${kravInstance?.antall}" format="0.00" /> <g:message code="sil.kr" default="kr" />
		                            	</g:else>
		                            </g:if>
	                            	<g:else>
		                            	<g:formatNumber number="${kravInstance?.antall}" format="#" />
		                            	<g:if test="${kravInstance.kravType == KravType.T}"> <g:message code="sil.min" /></g:if>
	    	                        	<g:elseif test="${kravInstance.kravType == KravType.K && kravInstance.kjorebok?.erKm()}">
	    	                        		<g:message code="sil.km" />
	    	                        	</g:elseif>
	    	                        	<g:elseif test="${kravInstance.kravType == KravType.K && (kravInstance.kjorebok?.transportmiddel == TransportMiddel.GIKK || kravInstance.kjorebok?.transportmiddel == TransportMiddel.LEIEBIL)}">
	    	                        		<g:message code="sil.min" />
	    	                        	</g:elseif>
	    	                        	<g:else>
		    	                        	<g:message code="sil.kr" />
	    	                        	</g:else>
	                            	</g:else>
	                            </td>
	                        </tr>
                    	</g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
            	<g:paginate	total="${kravInstanceTotal}" action="searchResult"
					params="${params}" />
			</div>
			
			</g:form>
		</div>
    </body>
</html>