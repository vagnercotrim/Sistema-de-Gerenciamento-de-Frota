package gerenciamentodefrota.infra.audit;

import gerenciamentodefrota.infra.UsuarioSession;

import org.hibernate.envers.RevisionListener;

import br.com.caelum.vraptor.ioc.spring.VRaptorRequestHolder;

public class CustomRevisionListener implements RevisionListener {

	private UsuarioSession usuarioSession;
	private String login;
	
	@Override
	public void newRevision(Object revisionEntity) {
		try {
			usuarioSession = (UsuarioSession) VRaptorRequestHolder.currentRequest().getRequest().getSession().getAttribute("usuarioSession");
			login = usuarioSession.getUsuario().getLogin();

			((CustomRevisionEntity) revisionEntity).setLogin(login);
		} catch (Exception e) {
		}
	}

}
