<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        <title>Utført søk</title>
    </head>
    <body>
        <div class="body">
            <h1>Utført søk</h1>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <b>Søk-Id:</b> ${params.searchId}
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <b>Navn:</b> ${params.searchName}
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <b>Søk utført:</b> ${params.searchSaved}
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">
                                <b>Lagret av:</b> ${params.searchSavedBy}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <h1>Resultat (${antall})</h1>
            <div class="list">
                <table>
                    <thead>
                    <tr>
                        <g:sortableColumn property="intervjuObjektNummer" title="${message(code: 'intervjuObjekt.intervjuObjektNummer.label', default: 'IO-nr')}" params="${params}"/>
                        <g:sortableColumn property="id" title="${message(code: 'intervjuObjekt.id.label', default: 'IO Id')}" params="${params}"/>
                        <g:sortableColumn property="skjemaNavn" title="Skjema" params="${params}"/>
                        <g:sortableColumn property="periodeNr" title="Per." params="${params}"/>
                        <g:sortableColumn property="skjemaStatus" title="${message(code: 'intervjuObjekt.katSkjemaStatus.label', default: 'Skjemastatus')}" params="${params}"/>
                        <g:sortableColumn property="intervjuStatus" title="${message(code: 'intervjuObjekt.intervjuStatus.label', default: 'Int.stat.')}" params="${params}"/>
                        <g:sortableColumn property="fNummer" title="${message(code: 'intervjuObjekt.fNummer.label', default: 'Fødselsnummer')}" params="${params}"/>
                        <g:sortableColumn property="navn" title="Navn" params="${params}"/>
                        <g:sortableColumn property="smsNummer" title="SMS-nr" params="${params}"/>
                        <g:sortableColumn property="epostadresse" title="e-postadresse" params="${params}"/>
                        <g:sortableColumn property="kontaktPeriode" title="Kontaktperiode" params="${params}"/>
                        <g:sortableColumn property="delutvalg" title="${message(code: 'intervjuObjekt.delutvalg.label', default: 'Delutvalg')}" params="${params}"/>
                    </tr>
                    </thead>
                    <tbody>
                        <g:each in="${intervjuObjektList}" status="i" var="intervjuObjekt">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                <td style="text-align: center; width: 40px;">
                                    ${intervjuObjekt.intervjuObjektNummer}
                                </td>
                                <td style="text-align: center; width: 40px;">
                                    ${intervjuObjekt.id}
                                </td>
                                <td>
                                    ${intervjuObjekt.skjemaNavn}
                                </td>
                                <td style="text-align: center; width: 35px;">
                                    ${intervjuObjekt.periodeNr}
                                </td>
                                <td>
                                    ${intervjuObjekt.skjemaStatus}
                                </td>
                                <td style="text-align: center; width: 60px;">
                                    ${intervjuObjekt.intervjuStatus}
                                </td>
                                <td style="text-align: center; width: 105px;">
                                    ${intervjuObjekt.fNummer}
                                </td>
                                <td>
                                    ${intervjuObjekt.navn}
                                </td>
                                <td>
                                    ${intervjuObjekt.smsNummer}
                                </td>
                                <td>
                                    ${intervjuObjekt.epostadresse}
                                </td>
                                <td style="text-align: left; width: 100px;">
                                    ${intervjuObjekt.kontaktPeriode}
                                </td>
                                <td style="text-align: left; width: 70px;">
                                    ${intervjuObjekt.delutvalg}
                                </td>
                            </tr>
                        </g:each>
                    </tbody>

                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${antall}" action="visUtfortSok" params="${params}"/>
            </div>
            <br>
            <div class="buttons">
                <span class="menuButton">
                    <g:link action="list">
                        <g:message code="sivadm.intervjuobjekt.tilbake.til.lagretSok" default="Tilbake til lagrede søk" />
                    </g:link>
                </span>
            </div>
        </div>
    </body>
</html>