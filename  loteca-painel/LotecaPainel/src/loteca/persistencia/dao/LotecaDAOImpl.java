package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Loteca;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.LotecaDAO;

public class LotecaDAOImpl implements LotecaDAO {

	private JpaHelper jpaHelper;

	public LotecaDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	@Transactional
	public Loteca insert(Loteca loteca) {
		Loteca lotecaExiste = jpaHelper.getEntityManager().find(Loteca.class,
				loteca.getNumConcurso());

		if (lotecaExiste == null) {
			jpaHelper.getEntityManager().persist(loteca);
			lotecaExiste = jpaHelper.getEntityManager().find(Loteca.class,
					loteca.getNumConcurso());
		}

		return lotecaExiste;

	}

	public void refresh(Loteca loteca) {
		jpaHelper.getEntityManager().refresh(loteca);
	}

	@Transactional
	public Loteca insertOrUpdate(Loteca loteca) {
		Loteca lotecaExiste = null;
		if (loteca.getNumConcurso() != null) {
			lotecaExiste = jpaHelper.getEntityManager().find(Loteca.class,
					loteca.getNumConcurso());
		}
		if (lotecaExiste == null) {
			jpaHelper.getEntityManager().persist(loteca);
		} else {
			jpaHelper.getEntityManager().merge(loteca);
		}
		lotecaExiste = jpaHelper.getEntityManager().find(Loteca.class,
				loteca.getNumConcurso());

		return lotecaExiste;
	}

	public List<Loteca> findAll() {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"Loteca.findAll");
		return (List<Loteca>) query.getResultList();
	}

	public Loteca findByNumConcurso(Integer numConcurso) {
		Loteca loteca = null;
		try {
			Query query = jpaHelper.getEntityManager().createNamedQuery(
					"Loteca.findByNumConcurso");
			query.setParameter("numConcurso", numConcurso);
			query.setFlushMode(FlushModeType.AUTO);
			loteca = (Loteca) query.getSingleResult();
		} catch (NoResultException e) {
			return loteca;
		}
		return loteca;
	}

	public Loteca findByStatus(Boolean finalizado) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"Loteca.findByStatus");
		query.setParameter("finalizado", finalizado);
		Loteca loteca = null;
		try {
			loteca = (Loteca) query.getSingleResult();
		} catch (NoResultException e) {
			return loteca;
		}
		return loteca;
	}

}
