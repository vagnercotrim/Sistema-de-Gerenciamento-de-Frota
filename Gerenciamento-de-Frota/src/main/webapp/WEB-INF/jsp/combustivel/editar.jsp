<div class="box">
	<div class="box-head">
		<h2 class="left">Editar combustível</h2>
	</div>

<form action="${pageContext.request.contextPath}/combustivel/${combustivel.id}" method="post" name="form_combustivel" id="form_combustivel">
	<input type="hidden" name="_method" value="put" />

	<input type="hidden" name="combustivel.id" value="${combustivel.id}" />
	
	<div class="coluna">
		<label for="combustivel.descricao">Descrição:</label> 
		<input type="text" name="combustivel.descricao" value="${combustivel.descricao}" />
	</div>

	<div class="coluna">
		<label for="combustivel.preco">Preço:</label> 
		<input type="text" name="combustivel.preco" value='<fmt:formatNumber>${combustivel.preco}</fmt:formatNumber>' />
	</div>

	<div class="separator">&nbsp;</div>

	<br />
	<input type="submit" value="Salvar" />
</form>

</div>

<content tag="scripts">

	<script type="text/javascript">
	$(document).ready(function(){
		$('#form_combustivel').validate({
		    rules: {
		    	"combustivel.descricao": {
	                required: true
	            },
	            "combustivel.preco": {
	                required: true
	            }
	        },
	        messages: {
	            "combustivel.descricao": {
	                required: "O campo descrição é obrigatorio."
	           },
	           "combustivel.preco": {
	                required: "O campo preço é obrigatorio."
	           }
	       }
		});
		
		$("select, input").uniform();
	}); 
	</script>

</content>