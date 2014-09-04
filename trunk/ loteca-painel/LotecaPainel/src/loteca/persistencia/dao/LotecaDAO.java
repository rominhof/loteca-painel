package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Loteca;
import loteca.util.JPAUtil;

public class LotecaDAO {

	private EntityManager em;

	public LotecaDAO() {
		em = JPAUtil.getEntityManager();
	}

	public Loteca insert(Loteca loteca) {
		em.getTransaction().begin();
		Loteca lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());

		if (lotecaExiste == null) {
			em.persist(loteca);
			lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());
		}
		em.getTransaction().commit();

		return lotecaExiste;

	}

	public Loteca insertOrUpdate(Loteca loteca) {
		em.getTransaction().begin();
		Loteca lotecaExiste = null;
		if (loteca.getNumConcurso() != null) {
			lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());
		}
		if (lotecaExiste == null) {
			em.persist(loteca);
		} else {
			em.merge(loteca);
		}
		lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());
		em.getTransaction().commit();

		return lotecaExiste;
	}

	public List<Loteca> findAll() {
		Query query = em.createNamedQuery("Loteca.findAll");
		return (List<Loteca>) query.getResultList();
	}

	public Loteca findByNumConcurso(Integer numConcurso) {
		Loteca loteca = null;
		try {
			Query query = em.createNamedQuery("Loteca.findByNumConcurso");
			query.setParameter("numConcurso", numConcurso);
			loteca = (Loteca) query.getSingleResult();
		} catch (NoResultException e) {
			return loteca;
		}
		return loteca;
	}

	public Loteca findByStatus(Boolean finalizado) {
		Query query = em.createNamedQuery("Loteca.findByStatus");
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
