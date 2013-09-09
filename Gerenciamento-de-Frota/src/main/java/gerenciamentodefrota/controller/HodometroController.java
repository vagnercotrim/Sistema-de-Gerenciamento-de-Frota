package gerenciamentodefrota.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;

import gerenciamentodefrota.annotation.Permission;
import gerenciamentodefrota.annotation.Transacional;
import gerenciamentodefrota.dao.HodometroDAO;
import gerenciamentodefrota.dao.VeiculoDAO;
import gerenciamentodefrota.model.Hodometro;
import gerenciamentodefrota.model.Perfil;
import gerenciamentodefrota.model.UsuarioSession;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

@Resource
public class HodometroController {

	private Result result;
	private Validator validator;
	private VeiculoDAO veiculoDAO;
	private HodometroDAO hodometroDAO;
	private UsuarioSession usuarioSession;

	public HodometroController(Result result, Validator validator, VeiculoDAO veiculoDAO, HodometroDAO hodometroDAO, UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.veiculoDAO = veiculoDAO;
		this.hodometroDAO = hodometroDAO;
		this.usuarioSession = usuarioSession;
	}
	
	@Permission({Perfil.ADMINISTRADOR, Perfil.USUARIO})
	@Get("/veiculo/registrarquilometragem")
	public void novoRegistro() {

	}

	@Transacional
	@Post("/veiculo/registrarquilometragem")
	public void novoRegistro(Hodometro hodometro) {
		validaNovoHodometro(hodometro);
		
		hodometro.setUsuario(usuarioSession.getUsuario());
		hodometroDAO.adiciona(hodometro);
		result.redirectTo(this).lista();		
	}
	
	private void validaNovoHodometro(Hodometro hodometro) {
		hodometro.setVeiculo(veiculoDAO.buscaPorPlaca(hodometro.getVeiculo().getPlaca()));
		
		if (hodometro.getVeiculo() == null) {
			validator.add(new ValidationMessage("Não existe veículo com esta placa nos registros.","veiculo.placa"));
		}
		
		validator.onErrorUsePageOf(this).novoRegistro();
		
		Hodometro registroAnterior = hodometroDAO.ultimoRegistroDoVeiculo(hodometro.getVeiculo());
		BigDecimal quilometragemAnterior = registroAnterior == null ? BigDecimal.ZERO : registroAnterior.getQuilometragem();
		LocalDateTime dataAnterior = registroAnterior == null ? new LocalDateTime() : registroAnterior.getDataLeitura();

		if (!hodometro.getDataLeitura().isAfter(dataAnterior)) {
			validator.add(new ValidationMessage("A data da leitura deve ser maior que o registro anterior.","hodometro.dataLeitura"));
		}
		
		if (hodometro.getQuilometragem().compareTo(quilometragemAnterior) != 1) {
			validator.add(new ValidationMessage("A quilometragem deve ser maior que o registro anterior.","hodometro.quilometragem"));
		}
		
		validator.onErrorUsePageOf(this).novoRegistro();
		
	}

	@Get("/hodometro")
	public List<Hodometro> lista() {
		try {
			return hodometroDAO.lista();
		} catch (Exception e) {
			return new ArrayList<Hodometro>();
		}
	}
	
}
