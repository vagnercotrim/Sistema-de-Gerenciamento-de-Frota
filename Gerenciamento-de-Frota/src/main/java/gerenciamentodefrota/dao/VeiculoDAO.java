package gerenciamentodefrota.dao;

import java.util.List;

import javax.persistence.EntityManager;

import gerenciamentodefrota.model.Veiculo;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class VeiculoDAO {

	private DAO<Veiculo> dao;
	private EntityManager em;
	
	public VeiculoDAO(EntityManager em) {
		this.em = em;
		this.dao = new DAO<Veiculo>(em, Veiculo.class);
	}
	
	public void adiciona(Veiculo veiculo) {
		try {
			em.getTransaction().begin();
			dao.adiciona(veiculo);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}
	
	public List<Veiculo> lista() {
		return dao.lista();
	}
	
}
