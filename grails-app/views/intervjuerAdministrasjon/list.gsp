
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main2" />
        
        <script type="text/javascript">
        		function showEgen() {
        			jQuery.noConflict();
        			jQuery('#e').show('slow')
        			jQuery('#se').hide('slow')
        			jQuery('#he').show('slow')
        		}

        		function hideEgen() {
        			jQuery.noConflict();
        			jQuery('#e').hide('slow')
        			jQuery('#he').hide('slow')
        			jQuery('#se').show('slow')
        		}

        		function showPc() {
        			jQuery.noConflict();
        			jQuery('#p').show('slow')
        			jQuery('#sp').hide('slow')
        			jQuery('#hp').show('slow')
        		}

        		function hidePc() {
        			jQuery.noConflict();
        			jQuery('#p').hide('slow')
        			jQuery('#hp').hide('slow')
        			jQuery('#sp').show('slow')
        		}

        		function showPerm() {
        			jQuery.noConflict();
        			jQuery('#l').show('slow')
        			jQuery('#sl').hide('slow')
        			jQuery('#hl').show('slow')
        		}

        		function hidePerm() {
        			jQuery.noConflict();
        			jQuery('#l').hide('slow')
        			jQuery('#hl').hide('slow')
        			jQuery('#sl').show('slow')
        		}

        		function showBarn() {
        			jQuery.noConflict();
        			jQuery('#b').show('slow')
        			jQuery('#sb').hide('slow')
        			jQuery('#hb').show('slow')
        		}

        		function hideBarn() {
        			jQuery.noConflict();
        			jQuery('#b').hide('slow')
        			jQuery('#hb').hide('slow')
        			jQuery('#sb').show('slow')
        		}

        		function showFerie() {
        			jQuery.noConflict();
        			jQuery('#f').show('slow')
        			jQuery('#sf').hide('slow')
        			jQuery('#hf').show('slow')
        		}

        		function hideFerie() {
        			jQuery.noConflict();
        			jQuery('#f').hide('slow')
        			jQuery('#hf').hide('slow')
        			jQuery('#sf').show('slow')
        		}
        		
        	</script>
        
    </head>
    <body>
        
        <div class="body">
            
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${ flash.errorMessage }">
				<div class=errors>${flash.errorMessage}</div>
			</g:if>
            
            <h1>Skjemaer til utfylling for permisjon og ferie</h1>
            <br/>
			
			<div style="width: 60em;">
			<p>
				Under ser du en liste med linker du kan klikke på for å få frem forskjellige skjemaer for utfylling. 
				Etter at du har fylt ut et slik skjema må du klikke på send inn knappen nederst i skjermbildet. Da vil skjemaet bli
				sendt inn til administrasjonen, og du vil også motta en kopi på mail.
			</p>
			</div>
			
			<br/>
		
			

				<div class="dialog">
            	
            	<h2>Egenmelding sykdom</h2>
            	<g:link action="egenmelding">Klikk her for å gå til skjema</g:link>
            	<a id="se" href="" OnClick="javascript:showEgen();return false;">| Mer info </a>
            	<a id="he" href="" OnClick="javascript:hideEgen();return false;">| Skjul info</a>
            	
            	<br/>
            	
            	<div id="e" style="width: 60em;">	
            		<br/>
					Når intervjuer er syk, gis beskjed til Feltadministrasjonen snarest og senest innen kl. 15.00 første fraværsdag. En slik melding skal bekreftes på egenmeldingsskjema og sendes til Seksjon for intervjuundersøkelser. 
					<br />
					<br />
					<i>Skjemaet generer en e-post som sendes feltadministrasjonen, som så kvitterer inn meldingen og fraværets varighet.</i>
					<br />
					<br />
					<strong>Utfylling av skjema</strong>
					<ul>
					<li>Opplysninger om navn, intervjuernummer, klynge, seksjon og dato preutfylles.</li> 
				
					<li>En fyller ut felter for fra og med – til og med datoer.</li>
				
					<li>En skal så merke av for om det er ordinært sykefravær, eller om fraværet skyldes forhold i arbeidet eller forholdene på arbeidsplassen; jf. Arb.m.l. § 5-1.</li>
					
					<li>Intervjuere som mottar en form for pensjon/stønad og ikke har rett på sykepenger fra NAV, merk av for dette. Disse har ikke rett på sykepenger etter arbeidsgiverperioden (16 kalenderdager).</li>
					
					<li>En har så mulighet til å skrive kommentar til meldingen.</li>
					</ul>
            	</div>
            	
            	<br/>
            	
            	<h2>Egenmelding PC- og telefonproblemer</h2>
            	<g:link action="pcTelefon">Klikk her for å gå til skjema</g:link>
            	<a id="sp" href="" OnClick="javascript:showPc();return false;">| Mer info </a>
            	<a id="hp" href="" OnClick="javascript:hidePc();return false;">| Skjul info</a>
            	
            	<br/>
            	
            	<div id="p" style="width: 60em;">	
            		<br/>
					Dersom <b>lokale intervjuere</b> blir forhindret i å utføre intervjuarbeid pga. problemer med  PC-en, og/eller telefonen en hel eller flere dager, godtgjøres den dagen (eventuelt dagene). Godtgjøring beregnes som når du selv er syk. 
					<br />
					<b>Sentrale intervjuere </b>skal ikke fremme  krav for hele dager de er forhindret fra å jobbe på grunn av tekniske  problemer. Dette legges inn automatisk av SSB.
					<br />
					<br />
					<i>Skjemaet generer en e-post som sendes feltadministrasjonen, som så kvitterer inn meldingen og fraværets varighet.</i>
					<br />
					<br />
					<strong>Utfylling av skjema</strong>
					<ul>
					<li>Opplysninger om navn, intervjuernummer, klynge, seksjon og dato preutfylles.</li> 
				
					<li>En fyller ut felter for fra og med – til og med datoer.</li>
				
					<li>En har så mulighet til å skrive kommentar til meldingen.</li>
					</ul>
            	</div>
            	
            	<br/>
            	
            	<h2>Permisjon med og uten lønn</h2>
            	<g:link action="permisjon">Klikk her for å gå til skjema</g:link>
            	<a id="sl" href="" OnClick="javascript:showPerm();return false;">| Mer info </a>
            	<a id="hl" href="" OnClick="javascript:hidePerm();return false;">| Skjul info</a>
            	
            	<br/>
            	
            	<div id="l" style="width: 60em;">	
            		<br/>
					Fylles ut for å søke om permisjon etter retninglinjer gitt i Overenskomsten pkt 4. 3  
					<br />
					<br />
					<i>Skjemaet generer en e-post som sendes feltadministrasjonen, som så kvitterer inn meldingen og fraværets varighet.</i>
					<br />
					<br />
					<strong>Utfylling av skjema</strong>
					<ul>
					<li>Opplysninger om navn, intervjuernummer, klynge, seksjon og dato preutfylles.</li> 
				
					<li>En fyller ut felter for fra og med – til og med datoer for aktuell permisjonstype.</li>
				
					<li>Hvis fravær bare deler av dag, angis dette i merknadsfeltet.</li>
					
					<li>En oppgir årsak til permisjon i merknadsfeltet neders.</li>
					</ul>
            	</div>
            	
            	<br/>
            	
            	<h2>Permisjon på grunn av barns/barnepassers sykdom</h2>
            	
            	<g:link action="permisjonBarn">Klikk her for å gå til skjema</g:link>
            	<a id="sb" href="" OnClick="javascript:showBarn();return false;">| Mer info </a>
            	<a id="hb" href="" OnClick="javascript:hideBarn();return false;">| Skjul info</a>
            	
            	<br/>
            	
            	<div id="b" style="width: 60em;">	
            		<br/>
					Permisjon i henhold til Overenskomsten pkt. 4.3.2. Dersom du har omsorg for barn under 12 år har du rett til inntil 10 dagers permisjon (15 dager dersom du har tre eller flere barn under 12 år) med lønn pr. kalenderår for nødvendig tilsyn av barnet/barna når det er sykt, eller dersom den som har det daglige tilsynet med barnet/barna er syk.
					<br />
					Egenmelding ved barns sykdom kan brukes inntil tre fraværsdager.
					<br />
					<br />
					Dersom du er alene om omsorgen for barn, har du rett til 20 dager permisjon med lønn pr. kalenderår (30 dager dersom du har tre eller flere barn under 12 år).
					<br />
					<br />
					Ved fravær gis beskjed til Feltadministrasjonen snarest. En slik melding skal bekreftes på dette skjemaet og sendes til Seksjon for intervjuundersøkelser.
					<br />
					<br />
					<i>Skjemaet generer en e-post som sendes feltadministrasjonen, som så kvitterer inn meldingen og fraværets varighet.</i>
					<br />
					<br />
					<strong>Utfylling av skjema</strong>
					<ul>
					<li>Opplysninger om navn, intervjuernummer, klynge, seksjon og dato preutfylles.</li> 
				
					<li>En fyller så ut felter for fra og med – til og med datoer. Videre besvares følgende spørsmål:
						<ul>
						<li>Er du alene om omsorgen?</li>
						<li>Antall barn under 12 år:</li>
						<li>Barnets fødseslsdato (dd.mm.åååå)</li> 
						</ul>
					</li>
					
					<li>En har så mulighet til å skrive kommentar til meldingen.</li>
					</ul>
            	</div>
            	
            	
            	<br/>
            	
            	<h2>Ferie</h2>
            	<g:link action="ferie">Klikk her for å gå til skjema</g:link>
            	<a id="sf" href="" OnClick="javascript:showFerie();return false;">| Mer info </a>
            	<a id="hf" href="" OnClick="javascript:hideFerie();return false;">| Skjul info</a>
            	
            	<br/>
            	
            	<div id="f" style="width: 60em;">	
            		<br/>
					Intervjuere i SSB skal følge en ferieordning tilsvarende bestemmelsene om ferie i den til enhver tid gjeldende Hovedtariffavtale i staten.
					<br />
					<br />
					<strong>Utfylling av skjema</strong>
					<ul>
					<li>Opplysninger om navn, intervjuernummer, klynge, seksjon og dato preutfylles.</li> 
				
					<li>En fyller ut felter for fra og med – til og med datoer.</li>
				
					<li>En har så mulighet til å skrive kommentar til meldingen.</li>
					</ul>
            	</div>
            	
            </div>
            
            <script type="text/javascript">
        		jQuery.noConflict();
        		jQuery('#e').hide()
        		jQuery('#he').hide()
        		
        		jQuery('#p').hide()
        		jQuery('#hp').hide()	
        		
        		jQuery('#l').hide()
        		jQuery('#hl').hide()
        		
        		jQuery('#b').hide()
        		jQuery('#hb').hide()
        		
        		jQuery('#f').hide()
        		jQuery('#hf').hide()
        	</script>
                        
        </div>
    </body>
</html>
