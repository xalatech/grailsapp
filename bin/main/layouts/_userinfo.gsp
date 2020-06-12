
<sec:ifLoggedIn>
	<span>Logget inn som <sec:username />
	(<g:link class="text-white-50" controller='logout'>Logg ut</g:link>)</span>
</sec:ifLoggedIn> <sec:ifNotLoggedIn>
	<span><g:link class="text-white-50" controller="hjem" action="index">
		Logg inn
	</g:link></span>
</sec:ifNotLoggedIn>
