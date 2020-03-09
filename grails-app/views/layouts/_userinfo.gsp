
<sec:ifLoggedIn>
	<span>Logget inn som <sec:username />
	(<g:link controller='logout'>Logg ut</g:link>)</span>
</sec:ifLoggedIn> <sec:ifNotLoggedIn>
	<span><g:link controller="hjem" action="index">
		Logg inn
	</g:link></span>
</sec:ifNotLoggedIn>
