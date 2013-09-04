package gerenciamentodefrota.controller;

import java.util.ArrayList;
import java.util.List;

import gerenciamentodefrota.annotation.Permission;
import gerenciamentodefrota.annotation.Transacional;
import gerenciamentodefrota.dao.FuncionarioDAO;
import gerenciamentodefrota.dao.UsuarioDAO;
import gerenciamentodefrota.infra.Notice;
import gerenciamentodefrota.model.Perfil;
import gerenciamentodefrota.model.Usuario;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;

@Resource
public class UsuarioController {

	private UsuarioDAO usuarioDAO;
	private Validator validator;
	private FuncionarioDAO funcionarioDAO;
	private Result result;
	private Notice notice;

	public UsuarioController(UsuarioDAO usuarioDAO, FuncionarioDAO funcionarioDAO, Validator validator, Result result, Notice notice) {
		this.usuarioDAO = usuarioDAO;
		this.funcionarioDAO = funcionarioDAO;
		this.validator = validator;
		this.result = result;
		this.notice = notice;
	}

	@Get
	@Path(value = "/funcionario/{id}/usuario/novo", priority = Path.HIGHEST)
	public void novo(Long id) {
		result.include("funcionario", funcionarioDAO.busca(id));
	}

	@Transacional
	@Post("/usuario/salvar")
	public void salva(final Usuario usuario) {
		usuario.setFuncionario(funcionarioDAO.busca(usuario.getFuncionario().getId()));
		validator.validate(usuario);
		
		result.include("funcionario", usuario.getFuncionario());
		validator.onErrorUsePageOf(this).novo(usuario.getFuncionario().getId());
		
		usuarioDAO.adiciona(usuario);
		result.redirectTo(this).lista();
	}

	@Get("/usuario")
	public List<Usuario> lista() {
		try {
			return usuarioDAO.lista();
		} catch (Exception e) {
			return new ArrayList<Usuario>();
		}
	}

	@Permission(Perfil.ADMINISTRADOR)
	@Transacional
	@Get("/usuario/{id}/bloquear")
	public void bloquear(Long id) {
		try {
			usuarioDAO.bloquear(id);
			notice.addSuccess("Usuário bloqueado com sucesso.");
		} catch (Exception e) {
			notice.addWarning("Usuário não encontrado");
		}
		
		result.redirectTo(this).lista();
	}
	
	@Permission(Perfil.ADMINISTRADOR)
	@Transacional
	@Get("/usuario/{id}/desbloquear")
	public void desbloquear(Long id) {
		try {
			usuarioDAO.desbloquear(id);
			notice.addSuccess("Usuário desbloqueado com sucesso.");
		} catch (Exception e) {
			notice.addWarning("Usuário não encontrado");
		}
		
		result.redirectTo(this).lista();
	}
	
}
