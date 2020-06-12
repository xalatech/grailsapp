<g:form action="automatiskTildeling" method="post">
	<br/>
	<div class="dialog">
   		<table style="width: 450px;">
   			<tbody>
   				<tr class="prop">
   					<td valign="top" class="name">
   						<g:message code="capi.tildel.max.io" default="Maksimalt antall IO pr. intervjuer" />
   					</td>
    					
   					<td valign="top" class="value">
   						<g:textField name="maxIO" size="5" value="${maxIO}" />
   					</td>
   				
   					<td valign="top" class="name">
   						<g:message code="capi.tildel.siste.frist" default="Siste frist" />
   					</td>
    					
   					<td valign="top" class="value">
   						<g:datoVelger id="dp-1" name="sisteFrist" value="${sisteFrist}" />
   					</td>
   					
   					<td valign="top" class="name">
   						<g:message code="capi.tildel.familiehensyn" default="Ta hensyn til familienummer" />
   					</td>
    					
   					<td valign="top" class="value">
   						<g:checkBox name="familieNummer" checked="false" />
   					</td>
   				</tr>
   				<tr>
   					<td>
   						<g:submitButton class="save" name="automatiskTildeling" value="Start automatisk tildeling" />
   					</td>
   				</tr>
   			</tbody>
   		</table>
   		
		
   	</div>
	
	
	
</g:form>