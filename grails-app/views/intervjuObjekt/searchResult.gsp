<%@ page import="sivadm.Intervjuer"%>
<%@ page import="sivadm.IntervjuObjekt"%>
<%@ page import="sivadm.Klynge"%>
<%@ page import="sivadm.MeldingsheaderMal"%>
<%@ page import="siv.type.IntervjuerStatus"%>
<%@ page import="siv.type.SkjemaStatus"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main2" />
	<g:set var="entityName"	value="${message(code: 'intervjuObjekt.label', default: 'IntervjuObjekt')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
	<g:javascript library="prototype" />
	<g:javascript src="intervjuObjekt.js"/>
</head>
<body>

<div class="body">
<h1>
	Intervjuobjekt søk
	<g:if test="${ioCnt}"> (antall IO totalt er <g:formatNumber number="${ioCnt}" format="#" />)</g:if>
</h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>
	<g:form action="searchResult" method="post">
	<div style="visibility: hidden; height: 1px; width: 1px;">
		<g:actionSubmit value="Søk" action="searchResult" />
	</div>
	
		<div class="dialog">
			<table>
				<tbody>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="intervjuObjektNummer"><g:message code="intervjuObjekt.nummer.label" default="IO-nr" /></label>
						</td>
						<td valign="top">
							<g:textField name="intervjuObjektNummer" value="${searchData?.intervjuObjektNummer}" size="10"/>
						</td>
						
						<td valign="top" class="name">
							<label for="navn" title="${message(code: 'sivadm.intervjuobjekt.navn.sok.tooltip', default: '')}"><g:message code="intervjuObjekt.navn.label" default="Navn" /></label>
						</td>
						<td valign="top">
							<g:textField name="navn" value="${searchData?.navn}" title="${message(code: 'sivadm.intervjuobjekt.navn.sok.tooltip', default: '')}" size="50"/>
						</td>
																			
						<td valign="top" class="name">
							<label for="fodselsNummer"><g:message code="intervjuObjekt.aargang.label" default="Fødselsnummer" /></label>
						</td>
						
						<td valign="top">
							<g:textField name="fodselsNummer" value="${searchData?.fodselsNummer}" size="11" maxlength="11" />
						</td>
					</tr>
					
					<tr class="prop">
						<td valign="top" class="name">
							<label for="kjonn"><g:message code="intervjuObjekt.kjonn.label" default="Kjonn" /></label>
						</td>
						<td valign="top">
							<g:select name="kjonn"
								from="${siv.type.Kjonn.values()}"
								optionKey="key" optionValue="guiName"
								noSelection="['':'-Velg kjønn']"
								value="${searchData?.kjonn?.key}"/>
						</td>
						<td valign="top" class="name">
							<label for="alderFra"><g:message code="intervjuObjekt.kjonn.label" default="Alder (fra/til)" /></label>
						</td>
						<td valign="top">
							<g:textField name="alderFra" value="${searchData?.alderFra}" size="3"/>
							<g:textField name="alderTil" value="${searchData?.alderTil}" size="3"/>
						</td>
						
						<td valign="top" class="name">
							<label for="familienummer"><g:message code="intervjuObjekt.familienummer.label" default="Familienummer" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="familienummer" value="${searchData?.familienummer}" maxlength="11" size="11" />
						</td>
					</tr>
					
					<tr class="prop">
						<td valign="top" class="name">
							<label for="adresse"><g:message code="intervjuObjekt.adresse.label" default="Adresse" /></label>
						</td>
							
						<td valign="top">
							<g:textField name="adresse" value="${searchData?.adresse}" size="50"/>
						</td>
						
						<td valign="top" class="name">
							<label for="husBruk"><g:message code="intervjuObjekt.husBruk.label" default="Hus/bruksnr" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="husBruk" value="${searchData?.husBruk}" size="8"/>
						</td>
						
						<td valign="top" class="name">
							<label for="boligNummer"><g:message code="intervjuObjekt.boligNummer.label" default="Bolignummer" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="boligNummer" value="${searchData?.boligNummer}" size="5"/>
						</td>
					</tr>
					
					<tr class="prop">
						<td valign="top" class="name">
							<label for="postNummer"><g:message code="intervjuObjekt.postNummer.label" default="Postnummer" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="postNummer" value="${searchData?.postNummer}" size="4" maxlength="4" />
						</td>
							
						<td valign="top" class="name">
							<label for="postSted"><g:message code="intervjuObjekt.postSted.label" default="Poststed" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="postSted" value="${searchData?.postSted}" />
						</td>
						
						<td valign="top" class="name">
							<label for="kommuneNummer"><g:message code="intervjuObjekt.kommuneNummer.label" default="Kommunenummer" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="kommuneNummer" value="${searchData?.kommuneNummer}" maxlength="4" size="4"/>
						</td>
					</tr>
					
					<tr class="prop">
						<td valign="top" class="name">
							<label for="intervjuObjektId"><g:message code="intervjuObjekt.intervjuobjektid.label" default="Intervjuobjekt id" /></label>
						</td>
							
						<td valign="top">
							<g:textField name="intervjuObjektId" value="${searchData?.intervjuObjektId}" size="10"/>
						</td>
					
						<td valign="top" class="name">
							<label for="telefonNummer"><g:message code="intervjuObjekt.telefonNummer.label" default="Telefon" /></label>
						</td>
								
						<td valign="top">
							<g:textField name="telefonNummer" value="${searchData?.telefonNummer}" />
						</td>
						
						<td valign="top" class="name">
							<label for="klynge"><g:message code="intervjuObjekt.klynge.label" default="Klynge" /></label>
						</td>
						
						<td valign="top">
							<g:select name="klynge"
								from="${Klynge.list()}"
								noSelection="['':'-Velg klynge']"
								optionKey="id"
								value="${searchData?.klynge}"/>
						</td>
					</tr>
										
					<tr class="prop">
						<td valign="top" class="name">
							<label for="aargang"><g:message code="intervjuObjekt.aargang.label" default="Prosjektår" /></label>
						</td>
						
						<td valign="top">
							<g:textField name="aargang" value="${searchData?.aargang}" size="5"/>
						</td>
						
						<td valign="top" class="name">
							<label for="epost"><g:message code="intervjuObjekt.epost.label" default="Epost" /></label>
						</td>
						
						<td valign="top">
							<g:textField name="epost" value="${searchData?.epost}" size="50"/>
						</td>
												
						<td valign="top" class="name">
							<label for="periodeNummer"><g:message code="intervjuObjekt.periode.label" default="Periodenr. (x,y,z)" /></label>
						</td>
							
						<td valign="top">
							<g:textField name="periodeNummer" value="${searchData?.periodeNummer}" size="27"/>
						</td>
					</tr>

					<tr class="prop">
					
						<td valign="top" class="name">
							<label for="intervjuStatus">
                                <g:message code="intervjuObjekt.intervjuStatus.label" default="Intervjustatus (x,y,z)" />
                            </label>
						</td>
							
						<td valign="top">
							<g:textField name="intervjuStatus"
                                         value="${searchData?.intervjuStatus}"
                                         size="50"
                                         title="${intervjuStatusKoder}"/>
						</td>	
					
						<td valign="top" class="name">
							<label for="skjema"><g:message code="intervjuObjekt.skjema.label" default="Skjema" /></label>
						</td>
						
						<td>
							<g:select name="skjema"
								onchange="skjemaValgEndret()" 
								from="${skjemaList.sort{it.oppstartDataInnsamling}.reverse()}"
								optionKey="id" 
								value="${searchData?.skjema }"
								noSelection="['':'-Velg skjema']"/>
						</td>
						
						<td valign="top" class="name">
							<label for="resultatStatus"><g:message code="intervjuObjekt.nummer.label" default="Resultatstatus" /></label>
						</td>
						
						<td valign="top">
							<g:select name="resultatStatus"
								from="${resultatStatusList}"
								optionKey="key"
								optionValue="guiName"
								noSelection="['':'-Velg resultatstatus']"
								value="${searchData?.resultatStatus?.key}"/>
						</td>
					</tr>
					
					<tr class="prop">
					
						<td valign="top" class="name">
							<label for="intervjuStatus"><g:message code="intervjuObjekt.intervjuStatusBlank.label" default="Inkluder blank intervjustatus" /></label>
						</td>
							
						<td valign="top">
							<g:checkBox name="intervjuStatusBlank" value="${searchData?.intervjuStatusBlank}"/>
						</td>	
					
						<td valign="top" class="name">
							<label for="maalform"><g:message code="intervjuObjekt.maalform.label" default="Målform" /></label>
						</td>
						
						<td>
							<g:select name="maalform"
									  from="${siv.type.Maalform.values()}"
									  optionKey="key" optionValue="guiName"
									  noSelection="['':'-Velg målform']"
									  value="${searchData?.maalform}"/>
						</td>
						
						<td valign="top" class="name">
						</td>
						
						<td valign="top">
						</td>
					</tr>

				<tr class="prop">

					<td valign="top" class="name">
						<label for="intervjuStatusDatoIntervall"><g:message code="intervjuObjekt.intervjuStatusDatoIntervall.label" default="Intervjustatus endret i tidsrommet (f.o.m - t.o.m)" /></label>
					</td>

					<td valign="top">
						<g:datoVelger id="dp-1" onchange="intervjuStatusDatoIntervallEndret()" name="intStatDatoIntervallFra" value="${searchData?.intStatDatoIntervallFra}" />
						-
						<g:datoVelger id="dp-2" onchange="intervjuStatusDatoIntervallEndret()" name="intStatDatoIntervallTil" value="${searchData?.intStatDatoIntervallTil}" />
					</td>

					<td valign="top" class="name">
						<label for="fullforingsStatus"><g:message code="intervjuObjekt.fullforingsstatus.label" default="Fullføringsstatus"/></label>

					</td>

					<td valign="top">
						<g:textField name="fullforingsStatus" value="${searchData?.fullforingsStatus}"  />
					</td>

					<td valign="top" class="name">
					</td>

					<td valign="top">
					</td>
				</tr>


				<tr>
					<td valign="top" class="name">
						<label for="initialer" title="${message(code: 'sivadm.intervjuobjekt.intervjuer.sok.tooltip', default: '')}"><g:message code="intervjuObjekt.intervjuer.initialer" default="Intervjuer initialer" /></label>
					</td>
					<td valign="top">
						<g:textField name="initialer" value="${searchData?.initialer}" size="3" maxlength="3" title="${message(code: 'sivadm.intervjuobjekt.intervjuer.sok.tooltip', default: '')}" />
						<g:actionSubmit value="Velg fra liste..." action="visIntervjuerListe" />
					</td>

					<td valign="top" class="name">
						<label for="kilde"><g:message code="intervjuObjekt.kilde.label" default="Kilde" /></label>
					</td>
					<td valign="top">
						<g:select name="kilde"
								  from="${kildeList}"
								  optionKey="key"
								  optionValue="guiName"
								  value="${searchData?.kilde?.key}"
								  noSelection="['':'-Velg kilde']" />
					</td>

					<td valign="top" class="name">
						<label for="fullforingsGrad"><g:message code="intervjuObjekt.fullforingsGrad.label" default="Fullføringsgrad" /></label>
					</td>
					<td valign="top">
						<g:textField name="fullforingMin" value="${searchData?.fullforingMin}" size="5"/>
						<g:textField name="fullforingMax" value="${searchData?.fullforingMax}" size="5"/>
					</td>
				</tr>


				<tr class="prop">

					<td valign="top" class="name">
						<label for="kontaktperiode">
							<g:message code="intervjuObjekt.searchResult.kontaktperiode.label" default="Kontaktperiode" />
						</label>
					</td>

					<td valign="top">
						<g:textField name="kontaktperiode" value="${searchData?.kontaktperiode}" size="10"/>
						<select name="kontaktperiode" class="hidden searchSelect" id="kontaktperiodeSelect" multiple="true" disabled="disabled" size="6" />
					</td>

					<td valign="top" class="name">
						<label for="assosiertSkjema"><g:message code="intervjuObjekt.searchResult.assosiertSkjema.label" default="Assosiert Skjema" /></label>
					</td>

					<td>
						<g:select name="assosiertSkjema"
								  onchange="assosiertSkjemaValgEndret()"
								  from="${skjemaList.sort{it.oppstartDataInnsamling}.reverse()}"
								  optionKey="id"
								  value="${searchData?.assosiertSkjema}"
								  noSelection="['':'-Velg skjema']"
								  disabled="true" />
					</td>

					<td valign="top" class="name">
						<label for="delutvalg">
							<g:message code="intervjuObjekt.searchResult.delutvalg.label" default="Delutvalg" />
						</label>
					</td>

					<td valign="top">
						<g:textField name="delutvalg" value="${searchData?.delutvalg}" size="10"/>
						<select name="delutvalg" class="hidden searchSelect" id="delutvalgSelect" multiple="true" disabled="disabled" size="6" />
					</td>

				</tr>
				<tr class="prop">
					<td valign="top" class="name">
						<label for="kontaktperiode">
							<g:message code="intervjuObjekt.searchResult.avtale.label" default="Avtale" />
						</label>
					</td>

					<td valign="top">
						<g:select name="avtaleType"
								  size="6"
								  from="${avtaleTyper}"
								  optionKey="key"
								  optionValue="guiName"
								  multiple="false"
								  noSelection="['':'-Velg avtale type']" />
					</td>

					<td valign="top" class="name">
						<label for="skjemaStatus"><g:message code="intervjuObjekt.nummer.label" default="Skjemastatus" /></label>
					</td>

					<td valign="top">
						<g:select name="skjemaStatus"
								  size="6"
								  from="${skjemaStatusList}"
								  optionKey="key"
								  optionValue="guiName"
								  multiple="true"
								  value="${searchData?.skjemaStatus?.split(',') as List}"
								  noSelection="['':'-Velg skjemastatus']" />
					</td>
				</tr>

				<tr class="prop">

					<td valign="top" class="small"> <!-- name -->
						<label for="intervjuStatusAssSkj">
							<g:message code="intervjuObjekt.searchResult.intervjuStatusAssosiertSkjema.label"
									   default="Intervjustatus (x,y,z) Assosiert Skjema"
							/>
						</label>
					</td>

					<td valign="top">
						<g:textField name="intervjuStatusAssSkj"
									 value="${searchData?.intervjuStatusAssSkj}"
									 size="50"
									 title="${intervjuStatusKoder}"
									 disabled="true"/>
					</td>



					<td valign="top" class="name">
					</td>

					<td valign="top">
					</td>

				</tr>


				<tr class="prop">

					<td valign="top" class="small">
						<label for="intervjuStatusBlankAssSkj">
							<g:message code="intervjuObjekt.searchResult.intervjuStatusBlank.assosiertSkjema.label"
									   default="Inkluder blank intervjustatus Assosiert Skjema"
							/>
						</label>
					</td>

					<td valign="top">
						<g:checkBox name="intervjuStatusBlankAssSkj" value="${searchData?.intervjuStatusBlankAssSkj}" disabled="true" />
					</td>


					<td valign="top" class="small">
						<label for="kontaktperiodeAssSkj">
							<g:message code="intervjuObjekt.searchResult.kontaktperiode.assosiertSkjema.label" default="Kontaktperiode Assosiert Skjema" />
						</label>
					</td>

					<td valign="top">
						<g:textField name="kontaktperiodeAssSkj" value="${searchData?.kontaktperiodeAssSkj}" size="10" disabled="true" />
						<select name="kontaktperiodeAssSkj" class="hidden searchSelect" id="kontaktperiodeSelectAssSkjema" multiple="true" disabled="disabled" size="6" />
					</td>

					<td valign="top" class="name">
					</td>

					<td valign="top">
					</td>
				</tr>

				</tbody>
			</table>	
		</div>

		<g:hiddenField name="sokPerfomed" value="true" />
		<div class="buttonStyle">
			<g:actionSubmit value="Søk" action="searchResult" />
			<g:actionSubmit value="Nullstill felter" action="searchResultNullstill" />
		</div>
		
		<h1>
			Lagring av Intervjuobjekt søk til DigiKorr
		</h1>
		<div class="dialog">
			<table>
				<tbody>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="sokeNavn"><g:message code="intervjuObjektSearch.name" default="Navn på søk" /></label>
						</td>
						<td valign="top">
							<g:hiddenField name="searchId" value="${searchId}" />
							<g:textField name="sokeNavn" value="${searchData?.sokeNavn}" size="40"/>
						</td>
						<td valign="top" class="name"></td>
						<td></td>
						<td valign="top" class="name"></td>
						<td></td>
					</tr>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="persisterSokeResultat"><g:message code="intervjuObjektSearch.persisterSokeResultat" default="Utfør søk nå" /></label>
						</td>
						<td valign="top">
							<g:checkBox name="persisterSokeResultat" value="${searchData?.persisterSokeResultat}"/>
						</td>
						<td valign="top" class="name"></td>
						<td></td>
						<td valign="top" class="name"></td>
						<td></td>
					</tr>
					<tr class="prop">
						<td valign="top" class="name">
							<label for="meldingsheaderMalId"><g:message code="intervjuObjektSearch.meldingsheaderMalId" default="Digikorr Meldingsheader" /></label>
						</td>
						<td valign="top">
							<g:select name="meldingsheaderMalId"
									  from="${MeldingsheaderMal.list().sort {it.malNavn.toUpperCase()}}"
									  optionKey="id"
									  optionValue="malNavn"
									  value="${searchData?.meldingsheaderMalId}"
									  noSelection="['':'Ingen']" />
						</td>
						<td valign="top" class="name"></td>
						<td></td>
						<td valign="top" class="name"></td>
						<td></td>
					</tr>
					<tr class="prop">
						<td valign="top" class="name">
							<g:if test="${searchId != null}">
								Nåværende id
							</g:if>
						</td>
						<td>
							${searchId}
						</td>
						<td valign="top" class="name"></td>
						<td></td>
						<td valign="top" class="name"></td>
						<td></td>
					</tr>
				</tbody>
			</table>	
		</div>
		
		<div class="buttonStyle">
			<g:actionSubmit value="Lagre nytt søk" action="persistSearch" />
			<g:if test="${searchId != null}">
				<g:actionSubmit value="Lagre eksisterende søk" action="editSearch" />
			</g:if>
		</div>
	

<h1>Resultat (${intervjuObjektInstanceTotal})</h1>

<div class="list">
<table>
	<thead>
		<tr>
			<g:sortableColumn property="intervjuObjektNummer" title="${message(code: 'intervjuObjekt.intervjuObjektNummer.label', default: 'IO-nr')}" />
			
			<g:sortableColumn property="skjemaNavn" title="Skjema"/>
			
			<g:sortableColumn property="delProduktNummer" title="Arbeidsordrenr"/>
			
			<g:sortableColumn property="periodeNummer" title="Per."/>
						
			<g:sortableColumn property="id" title="${message(code: 'intervjuObjekt.id.label', default: 'IO Id')}" />
			
			<g:sortableColumn property="navn" title="Navn" />
			
			<g:sortableColumn property="gateAdresse" title="Adresse" />
			
			<g:sortableColumn property="postNummer" title="Poststed" />
				
			<g:sortableColumn property="katSkjemaStatus" title="${message(code: 'intervjuObjekt.katSkjemaStatus.label', default: 'Skjemastatus')}" />
			
			<g:sortableColumn property="intervjuStatus" title="${message(code: 'intervjuObjekt.intervjuStatus.label', default: 'Int.stat.')}" />

			<g:sortableColumn property="intervjuAssStatus" title="${message(code: 'intervjuObjekt.intervjuAssStatus.label', default: 'Int.Ass.stat.')}" />
			
			<g:sortableColumn property="delutvalg" title="${message(code: 'intervjuObjekt.delutvalg.label', default: 'Delutvalg')}" />
				
			<th />

		</tr>
	</thead>
	<tbody>
		<g:each in="${intervjuObjektInstanceList}" status="i" var="intervjuObjektInstance">
			<g:if test="${intervjuObjektInstance.io.katSkjemaStatus==siv.type.SkjemaStatus.Utsendt_CAPI}">
				<tr bgcolor="yellow">
			</g:if>
			<g:else>
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			</g:else>
				<td style="text-align: right; width: 40px;">
					<g:link action="edit" id="${intervjuObjektInstance.io.id}" params="[assosiertIntervjuStatus: intervjuObjektInstance.assIntStatus ]" >
						<g:formatNumber number="${intervjuObjektInstance?.io.intervjuObjektNummer}" format="#" />
					</g:link>
				</td>
				
				<td>
					${intervjuObjektInstance?.io.periode?.skjema?.skjemaNavn}
				</td>
				
				<td style="text-align: right; width: 70px">
					${intervjuObjektInstance?.io.periode?.skjema?.delProduktNummer}
				</td>
				
				<td style="text-align: right; width: 30px">
					${intervjuObjektInstance?.io.periode?.periodeNummer}
				</td>
				
				<td style="text-align: right; width: 40px;">
					<g:formatNumber number="${intervjuObjektInstance?.io.id}" format="#" />
				</td>
				
				<td>
					${fieldValue(bean: intervjuObjektInstance?.io, field: "navn")}
				</td>
				
				<td>
					${intervjuObjektInstance.io.findGjeldendeBesokAdresse()?.gateAdresse}
				</td>
				
				<td style="text-align: left;  width: 150px">
					<nobr>${intervjuObjektInstance.io.findGjeldendeBesokAdresse()?.postNummer}&nbsp;${intervjuObjektInstance.io.findGjeldendeBesokAdresse()?.postSted}</nobr>
				</td>
				
				<td style="width: 80px">
					${fieldValue(bean: intervjuObjektInstance.io, field: "katSkjemaStatus")}
				</td>

				<td style="text-align: right;  width: 55px">
					${fieldValue(bean: intervjuObjektInstance.io, field: "intervjuStatus")}
				</td>

				<td style="text-align: right;  width: 55px">
					${intervjuObjektInstance.assIntStatus}
				</td>

				<td>
					${fieldValue(bean: intervjuObjektInstance.io, field: "delutvalg")}
				</td>

				<td nowrap="nowrap">
                   	<g:link action="edit" id="${intervjuObjektInstance.io.id}" params="[assosiertIntervjuStatus: intervjuObjektInstance.assIntStatus ]">
						<g:redigerIkon />
					</g:link>
                   	<g:link action="edit" id="${intervjuObjektInstance.io.id}" params="${[se: true, assosiertIntervjuStatus: intervjuObjektInstance.assIntStatus ]}">
						<g:seIkon />
					</g:link>
                   	<!--
                   	Fjernet mulighet for sletting av IO etter ønske fra bla. Jan i møte 29.april 2011. Var
                   	redd dette kunne skape mismatch med utvalg i blaise. 
                   	&nbsp;&nbsp;
                  	<a href="#" onclick="return apneSlettDialog(${intervjuObjektInstance.io.id})"><g:slettIkon /></a>
                  	 -->
                </td>
			</tr>
		</g:each>
	</tbody>
</table>
</div>
<div class="paginateButtons">
	<g:paginate total="${intervjuObjektInstanceTotal}" action="searchResult" params="${params}" />
</div>
<br/>
<g:if test="${intervjuObjektInstanceTotal > 0}">
	<div class="buttonStyle">
		<g:actionSubmit value="  Last ned lista i csv-format   " action="searchResultLastNed" />
	</div>
</g:if>
<g:if test="${intervjuObjektInstanceTotal < 501}">
	<div class="buttonStyle">
		<g:actionSubmit value="Behandle alle i søkeresultat" action="statusChange" />
	</div>
</g:if>
<g:else>
	<g:message code="sivadm.intervjuobjekt.behandle.all.melding" default="Kan ikke sette statuser/behandle alle for søkeresultat som er større enn 500" />
</g:else>
</g:form>

</div>
<!--
 Fjernet mulighet for sletting av IO etter ønske fra bla. Jan i møte 29.april 2011. Var
 redd dette kunne skape mismatch med utvalg i blaise. 
<g:slettDialog domeneKlasse="intervjuobjekt" />
 -->
</body>
</html>
