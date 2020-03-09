<h1>Sivadm <g:meta name="app.version"/>${grails.util.Environment.current.name}</h1>
<sec:ifLoggedIn>
	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">
		<h2>Undersøkelse</h2>
		<li><g:link controller="prosjekt" action="list">Prosjekt</g:link></li>
		<li><g:link controller="skjema" action="list">Skjema</g:link></li>
		<li><g:link controller="utvalgAdministrasjon" action="selectSkjema">Utvalg</g:link></li>
		<li><g:link controller="catiGruppe" action="list">CATI-tilordning</g:link></li>
		<li><g:link controller="capiAdministrasjon" action="listKlynger">CAPI-tilordning</g:link></li>
		<li><g:link controller="oppdrag" action="list">Oppdrag</g:link></li>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">
		<li><g:link controller="rapportIntervjuer" action="visRapport" params="[ikkeGenerer: 'true']">Intervjuerrapport</g:link></li>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN, ROLE_SPORINGSPERSON, ROLE_CAPITILDELING, ROLE_SUPERVISOR">
		<h2>Administrasjon</h2>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN, ROLE_SPORINGSPERSON, ROLE_CAPITILDELING, ROLE_SUPERVISOR">
		<li><g:link controller="meldingsheaderMal" action="list">DigiKorr metadata</g:link></li>
		<li><g:link controller="intervjuObjekt" action="searchResult">Intervjuobjekt søk</g:link></li>
		<li><g:link controller="intervjuObjektSearch" action="list">Lagrede søk</g:link></li>
	</sec:ifAnyGranted>

	<sec:ifAnyGranted roles="ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">
		<li><g:link controller="intervjuer" action="list">Intervjuer</g:link></li>
		<li><g:link controller="opplaring" action="list">Opplæring</g:link></li>
	</sec:ifAnyGranted>

	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">
		<li><g:link controller="prosjektLeder" action="list">Prosjektleder</g:link></li>
	</sec:ifAnyGranted>
	
	<sec:ifAnyGranted roles="ROLE_ADMIN">
		<li><g:link controller="klynge" action="list">Klynge</g:link></li>
		<li><g:link controller="kommune" action="list">Kommune</g:link></li>
		<li><g:link controller="logg" action="list">Hendelseslogg</g:link></li>
	</sec:ifAnyGranted>
	
	<sec:ifAllGranted roles="ROLE_ADMIN">
		<li><g:link controller="meldingUt" action="list">Meldinger til Blaise</g:link></li>
		<li><g:link controller="meldingInn" action="list">Meldinger fra Blaise</g:link></li>
		<li><g:link controller="bruker" action="list">Brukere</g:link></li>
		<li><g:link controller="produkt" action="list">Produkt</g:link></li>
		<li><g:link controller="systemKommando" action="list">Systemkommando</g:link></li>
	</sec:ifAllGranted>

	<sec:ifAnyGranted roles="ROLE_INTERVJUER, ROLE_ADMIN">
		<h2>Intervjuer</h2>
		<li><g:link controller="rapportEgneResultater" action="visRapport" params="[ikkeGenerer: 'true']">Egne Resultater</g:link></li>
		<li><g:link controller="intervjuerArbeidsflate" action="list">Mine CATI-skjema</g:link></li>
		<li><g:link controller="intervjuerArbeidsflate" action="listCapi">Mine CAPI-skjema</g:link></li>
		<li><g:link controller="intervjuerAdministrasjon" action="list">Administrasjon</g:link></li>
		<li><g:link controller="timeforing" action="velgDato">Timeføring</g:link></li>
		<li><g:link controller="intervjuerRapport" action="listIntervjuerRapporter">Rapporter</g:link></li>
	</sec:ifAnyGranted>

	<sec:ifAnyGranted roles="ROLE_SIL, ROLE_ADMIN">
		<h2>SIL</h2>
		<li><g:link controller="krav" action="administrasjon"  title="${message(code: 'meny.sil.administrasjon.tooltip', default: 'Administrasjon')}"> <g:message code="meny.sil.administrasjon" default="Administrasjon" /></g:link></li>
		<li><g:link controller="krav" action="intervjuerKontroll" title="${message(code: 'meny.sil.intervjuere.manuell.kontroll.tooltip', default: 'Intervjuere til kontroll')}"><g:message code="meny.sil.intervjuere.manuell.kontroll" default="Intervjuere til kontroll" /></g:link></li>
		<li><g:link controller="krav" action="searchResult" title="${message(code: 'meny.sil.sok.krav.tooltip', default: '')}"><g:message code="meny.sil.sok.krav" /></g:link></li>
		<li><g:link controller="automatiskKontroll" action="list">Automatiske kontroller</g:link></li>
		<li><g:link controller="lonnart" action="list">Lønnart</g:link></li>
		<li><g:link controller="sapFil" action="list">SAP-filer</g:link></li>
		<li><g:link controller="kostGodtgjorelse" action="list">Kostgodtgjørelse</g:link></li>
		<li><g:link controller="rapportUke" action="visRapport">Ukerapport</g:link></li>
		<li><g:link controller="detaljertTidsrapport" action="visRapport">Detaljert tidsrapport</g:link></li>
		<li><g:link controller="rapportProsjekt" action="velgProsjekt" params="[settDato: 'true']">Prosjektrapport</g:link></li>
	</sec:ifAnyGranted>

</sec:ifLoggedIn>