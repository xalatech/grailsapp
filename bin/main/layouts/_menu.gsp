
<div class="sidebar-sticky">
	<ul class="nav flex-column">
		<li class="nav-item">
			<a class="nav-link active" href="/">
				<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
				Dashboard <span class="sr-only">(current)</span>
			</a>
		</li>
	</ul>
<sec:ifLoggedIn>
	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">

	<h2 class="sidebar-heading d-flex justify-content-between align-items-center px-3 py-2 mt-2 text-light bg-dark">
		<span>Undersøkelse</span>
	</h2>
	<ul class="nav flex-column mb-2">
		<li class="nav-item">
			<g:link class="nav-link" controller="prosjekt" action="list">
				Prosjekt
			</g:link>
		</li>

	</ul>
	</sec:ifAnyGranted>

</sec:ifLoggedIn>


     %{--  <ul class="nav">
		   <li class="dashboard-nav active ">
			   <a href="/sivadmin">
				   Dashboard
			   </a>
		   </li>
<sec:ifLoggedIn>
	<sec:ifAnyGranted roles="ROLE_PLANLEGGER, ROLE_INTERVJUERKONTAKT, ROLE_ADMIN">
		<div class="sidelist-header-name">
			<span>Undersøkelse</span>
		</div>
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
		<div class="sidelist-header-name">
			<span>Administrasjon</span>
		</div>

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
		<div class="sidelist-header-name">
			<span>Intervjuer</span>
		</div>

		<li><g:link controller="rapportEgneResultater" action="visRapport" params="[ikkeGenerer: 'true']">Egne Resultater</g:link></li>
		<li><g:link controller="intervjuerArbeidsflate" action="list">Mine CATI-skjema</g:link></li>
		<li><g:link controller="intervjuerArbeidsflate" action="listCapi">Mine CAPI-skjema</g:link></li>
		<li><g:link controller="intervjuerAdministrasjon" action="list">Administrasjon</g:link></li>
		<li><g:link controller="timeforing" action="velgDato">Timeføring</g:link></li>
		<li><g:link controller="intervjuerRapport" action="listIntervjuerRapporter">Rapporter</g:link></li>
	</sec:ifAnyGranted>

	<sec:ifAnyGranted roles="ROLE_SIL, ROLE_ADMIN">
		<div class="sidelist-header-name">
			<span>SIL</span>
		</div>
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
</ul>--}%
</div>

<script>
	$(function(){
		var current = location.pathname;
		var res = current.split("/");

		$('.nav li a').each(function(){
			var $this = $(this);
			$this.parent().removeClass('active');

			var href = $this.attr('href');
			var href_group = href.split("/");

			if(href_group[2] === undefined && res[2] === "") {
				$(".dashboard-nav").addClass('active');
			}else if(href_group[2] === res[2]) {
				$this.parent().addClass('active');
			}


		})
	})
</script>