package gerenciamentodefrota.controller;

import java.util.ArrayList;
import java.util.List;

import gerenciamentodefrota.annotation.Transacional;
import gerenciamentodefrota.dao.FuncionarioDAO;
import gerenciamentodefrota.model.Funcionario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;

@Resource
public class FuncionarioController {

	private FuncionarioDAO funcionarioDAO;
	private Validator validator;
	private Result result;

	public FuncionarioController(FuncionarioDAO funcionarioDAO,
			Validator validator, Result result) {
		this.funcionarioDAO = funcionarioDAO;
		this.validator = validator;
		this.result = result;
	}

	@Get
	@Path(value = "/funcionario/novo", priority = Path.HIGHEST)
	public void novo() {
	}

	@Transacional
	@Post("/funcionario/salvar")
	public void salva(final Funcionario funcionario) {
		validator.validate(funcionario);

		validator.checking(new Validations() {
			{
				that(funcionarioDAO.buscaPorCadastro(funcionario.getCadastro()) == null,
						"funcionario.cadastro",
						"Já existe um funcionário com este cadastro");
			}
		});

		validator.onErrorRedirectTo(this).novo();

		funcionarioDAO.adiciona(funcionario);
		result.redirectTo(this).lista();
	}

	@Get("/funcionario")
	public List<Funcionario> lista() {
		try {
			return funcionarioDAO.lista();
		} catch (Exception e) {
			return new ArrayList<Funcionario>();
		}
	}

}