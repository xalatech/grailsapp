
<%@ page import="sivadm.Prosjekt" %>
<html>
    <head>
        <g:set var="pageTitle" value="Endre Prosjekt" scope="request"/>
        <meta name="layout" content="main" />
        <title>${pageTitle}</title>
    </head>
    <body>
    <g:form method="post" class="form form-horizontal" >
    <div class="card" style="width: 100%;">
        <div class="card-body">
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${prosjektInstance}">
                <div class="errors">
                    <g:renderErrors bean="${prosjektInstance}" as="list" />
                </div>
            </g:hasErrors>

                <g:hiddenField name="id" value="${prosjektInstance?.id}" />
                <g:hiddenField name="version" value="${prosjektInstance?.version}" />
                <div class="row">
                    <div class="col-sm-6">
                        <label class="control-label" for="prosjektNavn"><g:message code="prosjekt.prosjektNavn.label" default="Prosjekt Navn" /></label>
                        <g:textField name="prosjektNavn" class="form-control" value="${prosjektInstance?.prosjektNavn}" size="40" />
                    </div>
                    <div class="col-sm-3">
                        <label class="control-label" for="prosjektNavn"><g:message code="prosjekt.produktNummer.label" default="Produktnummer" /></label>
                        <g:textField name="produktNummer" class="form-control" value="${prosjektInstance?.produktNummer}" size="5" maxlength="5" />
                    </div>
                    <div class="col-sm-3">
                        <label class="control-label" for="aargang"><g:message code="prosjekt.aargang.label" default="Årgang" /></label>
                        <g:textField name="aargang" class="form-control" value="${prosjektInstance?.aargang}" size="4" maxlength="4" />
                    </div>
                </div>
                <div class="row mt-3">
                <div class="col-sm-6">
                    <label class="control-label" for="registerNummer"><g:message code="prosjekt.registerNummer.label" default="Registernummer" /></label>
                    <g:textField name="registerNummer" class="form-control" value="${prosjektInstance?.registerNummer}" />
                </div>
                <div class="col-sm-3">
                    <label class="control-label" for="oppstartDato"><g:message code="prosjekt.oppstartDato.label" default="Oppstartdato" /></label>
                    <g:datoVelger id="od" name="oppstartDato" value="${prosjektInstance?.oppstartDato}" size="5" maxlength="5" />
                </div>
                <div class="col-sm-3">
                    <label class="control-label" for="avslutningsDato"><g:message code="prosjekt.avslutningsDato.label" default="Avslutningsdato" /></label>
                    <g:datoVelger id="ad" name="avslutningsDato" class="form-control" value="${prosjektInstance?.avslutningsDato}" />
                </div>
            </div>
                <hr class="mb-3 mt-3" />

                <div class="row mt-3">
                    <div class="col-sm-6">
                        <label class="control-label" for="prosjektLeder.id"><g:message code="prosjekt.prosjektLeder.label" default="ProsjektLeder" /></label>
                        <g:select name="prosjektLeder.id" class="form-control" from="${sivadm.ProsjektLeder.list()}" optionKey="id" value="${prosjektInstance?.prosjektLeder?.id}"  />
                    </div>
                    <div class="col-sm-1">
                        <label class="control-label" for="panel"><g:message code="prosjekt.panel.label" default="Panel" /></label>
                        <g:checkBox name="panel" class="form-control-checkbox" value="${prosjektInstance?.panel}" />
                    </div>
                    <div class="col-sm-2">
                        <label class="control-label" for="modus"><g:message code="prosjekt.modus.label" default="Modus" /></label>
                        <g:select name="modus" class="form-control" from="${siv.type.ProsjektModus?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.modus?.key}" />

                    </div>
                    <div class="col-sm-3">
                        <label class="control-label" for="prosjektStatus"><g:message code="prosjekt.prosjektStatus.label" default="Prosjektstatus" /></label>
                        <g:select name="prosjektStatus" class="form-control" from="${siv.type.ProsjektStatus?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.prosjektStatus?.key}"  />

                    </div>

                </div>

                <div class="row mt-3">
                    <div class="col-sm-6">
                        <label class="control-label" for="kommentar"><g:message code="prosjekt.kommentar.label" default="Prosjekt kommentar" /></label>
                        <g:textField name="kommentar" class="form-control" value="${prosjektInstance?.kommentar}" size="40" />
                    </div>
                    <div class="col-sm-2">
                        <label class="control-label" for="prosentStat"><g:message code="prosjekt.prosentStat.label" default="Stat %" /></label>
                        <g:textField name="prosentStat" class="form-control" value="${prosjektInstance?.prosentStat}" size="3" maxlength="3" />
                    </div>
                    <div class="col-sm-2">
                        <label class="control-label" for="prosentMarked"><g:message code="prosjekt.prosentMarked.label" default="Marked %" /></label>
                        <g:textField name="prosentMarked" class="form-control" value="${prosjektInstance?.prosentMarked}" size="3" maxlength="3" />

                    </div>
                    <div class="col-sm-2">
                        <label class="control-label" for="finansiering"><g:message code="prosjekt.finansiering.label" default="Finansiering" /></label>
                        <g:select name="finansiering" class="form-control" from="${siv.type.ProsjektFinansiering?.values()}" optionKey="key" optionValue="guiName" value="${prosjektInstance?.finansiering?.key}"  />
                    </div>

                </div>
                <hr class="mb-3 mt-3" />
                <div class="row">
                    <div class="col">
                        <h5><g:message code="prosjekt.skjemaer.label" default="Skjemaer" /></h5>
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Skjemanavn</th>
                                <th scope="col">Arbeidsordrenummer</th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${prosjektInstance?.skjemaer?.sort{it.delProduktNummer}}" var="s">
                                <tr>
                                    <td><g:link controller="skjema" action="edit" id="${s.id}" params="['prosjekt.id': prosjektInstance?.id]">
                                        ${s?.id}
                                    </g:link></td>
                                    <td><g:link controller="skjema" action="edit" id="${s.id}" params="['prosjekt.id': prosjektInstance?.id]">
                                        ${s?.skjemaKortNavn}
                                    </g:link></td>
                                    <td><g:link controller="skjema" action="edit" id="${s.id}" params="['prosjekt.id': prosjektInstance?.id]">
                                        ${s?.delProduktNummer}
                                    </g:link></td>
                                </tr>


                            </g:each>


                            </tbody>
                        </table>
                        <g:link class="btn btn-sm btn-outline-light" controller="skjema" action="create"
                                params="['prosjekt.id': prosjektInstance?.id]">
                            ${message(code: 'default.add.label', args: [message(code: 'skjema.label', default: 'Skjema')])}
                        </g:link>

                    </div>
                    <div class="col">
                        <h5><g:message code="prosjekt.prosjektDeltagere.label" default="Prosjektdeltagere" /></h5>

                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Navn</th>
                                <th scope="col">Initial</th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${prosjektInstance?.prosjektDeltagere?}" var="p">
                                <tr>
                                    <td><g:link controller="prosjektDeltager" action="edit" id="${p.id}" params="${['prosjekt.id': prosjektInstance?.id]}">${p?.id}</g:link></td>
                                    <td><g:link controller="prosjektDeltager" action="edit" id="${p.id}" params="${['prosjekt.id': prosjektInstance?.id]}">${p?.deltagerNavn}</g:link></td>
                                    <td><g:link controller="prosjektDeltager" action="edit" id="${p.id}" params="${['prosjekt.id': prosjektInstance?.id]}">${p?.deltagerInitialer}</g:link></td>
                                </tr>

                            </g:each>


                            </tbody>
                        </table>
                        <g:link controller="prosjektDeltager" class="btn btn-sm btn-outline-light" action="create" params="['prosjekt.id': prosjektInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'prosjektDeltager.label', default: 'Prosjektdeltager')])}</g:link>

                    </div>
                </div>

        </div>

        <div class="card-footer">
            <div class="row">
                <div class="col-sm-12 text-center">
                    <g:actionSubmit class="save btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    <g:link class="delete btn btn-danger ml-2" action="list"><g:message code="sil.avbryt" /></g:link>

                </div>
            </div>
                   </div>
    </div>
            </g:form>


    </body>
</html>
