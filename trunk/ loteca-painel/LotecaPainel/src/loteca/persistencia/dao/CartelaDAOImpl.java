package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.Query;

import loteca.dominio.Cartela;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.CartelaDAO;

public class CartelaDAOImpl implements CartelaDAO {

	private JpaHelper jpaHelper;

	public CartelaDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	public void refresh(Cartela cartela) {
		jpaHelper.getEntityManager().refresh(cartela);
	}

	@Transactional
	public Cartela insertOrUpdate(Cartela cartela) {
		Cartela cartelaExiste = null;
		if (cartela.getId() != null) {
			cartelaExiste = jpaHelper.getEntityManager().find(Cartela.class,
					cartela.getId());
		}
		if (cartelaExiste == null) {
			jpaHelper.getEntityManager().persist(cartela);
		} else {
			jpaHelper.getEntityManager().merge(cartela);
		}
		cartelaExiste = jpaHelper.getEntityManager().find(Cartela.class,
				cartela.getId());

		return cartelaExiste;
	}
	
	public List<Cartela> findByLotecaGrupoCartela(Integer numConcurso, Long idgc){
		Query query = jpaHelper.getEntityManager().createNamedQuery("Cartela.findByLotecaGrupoCartela");
		query.setParameter("numConcurso", numConcurso);
		query.setParameter("grupoCartela", idgc);
		return (List<Cartela>)query.getResultList();
	}

	public List<Cartela> findByLoteca(Integer numConcurso) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"Cartela.findByLoteca");
		query.setParameter("numConcurso", numConcurso);
		return (List<Cartela>) query.getResultList();
	}

	@Transactional
	public void remove(Cartela cartela) {
		cartela = jpaHelper.getEntityManager().merge(cartela);
		jpaHelper.getEntityManager().remove(cartela);
	}

}
