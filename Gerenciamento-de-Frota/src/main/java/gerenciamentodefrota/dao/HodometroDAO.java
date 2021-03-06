package gerenciamentodefrota.dao;

import java.math.BigDecimal;
import java.util.List;

import gerenciamentodefrota.infra.persistence.DAO;
import gerenciamentodefrota.model.Hodometro;
import gerenciamentodefrota.model.Veiculo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class HodometroDAO {

	private DAO<Hodometro, Long> dao;
	
	public HodometroDAO(EntityManager em) {
		this.dao = new DAO<Hodometro, Long>(em, Hodometro.class);
	}
	
	public void adiciona(Hodometro hodometro) {
		dao.create(hodometro);
	}
	
	public Hodometro busca(Long id) {
		return dao.find(id);
	}
	
	public List<Hodometro> lista() {
		return dao.list();
	}
	
	public BigDecimal ultimaQuilometragem(Veiculo veiculo) {
		try {
			return this.ultimoRegistroDoVeiculo(veiculo).getQuilometragem();
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}
	
	public Hodometro ultimoRegistroDoVeiculo(Veiculo veiculo) {
		if(veiculo == null)
			return null;
		
		StringBuilder builder = new StringBuilder("select o from " + Hodometro.class.getName() + " o ");
		builder.append("where o.veiculo.id = :id order by o.quilometragem desc");
		
		Query query = dao.getEntityManager().createQuery(builder.toString())
											.setParameter("id", veiculo.getId())
											.setMaxResults(1);

		try {
			return (Hodometro) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Hodometro> ultimoRegistroCadaVeiculo() {
		String sql = "SELECT h FROM " + Hodometro.class.getName() + " h where h.dataLeitura in (SELECT max(i.dataLeitura) FROM " + Hodometro.class.getName() + " i group by i.veiculo)";
		Query q = dao.getEntityManager().createQuery(sql);
		return q.getResultList();
	}
	
}
