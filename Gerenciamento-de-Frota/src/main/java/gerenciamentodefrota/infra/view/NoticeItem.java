package gerenciamentodefrota.infra.view;

import java.io.Serializable;

public class NoticeItem implements Serializable {

	private static final long serialVersionUID = -2578018246413801063L;

	private String mensagem;
	private NoticeEnum tipo;
	
	public NoticeItem(String mensagem, NoticeEnum tipo){
		this.setMensagem(mensagem);
		this.setTipo(tipo);
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public NoticeEnum getTipo() {
		return tipo;
	}

	public void setTipo(NoticeEnum tipo) {
		this.tipo = tipo;
	}
	
}
