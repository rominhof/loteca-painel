package loteca.persistencia.dao;

import javax.persistence.EntityManager;

import loteca.dominio.Palpite;
import loteca.util.JPAUtil;

public class PalpiteDAO {

	private EntityManager em;

	public PalpiteDAO() {
		em = JPAUtil.getEntityManager();
	}

	public Palpite insertOrUpdate(Palpite palpite) {
		em.getTransaction().begin();
		Palpite palpiteExiste = null;
		if (palpite.getId() != null) {
			palpiteExiste = em.find(Palpite.class, palpite.getId());
		}
		if (palpiteExiste == null) {
			em.persist(palpite);
		} else {
			em.merge(palpite);
		}
		palpiteExiste = em.find(Palpite.class, palpite.getId());
		em.getTransaction().commit();

		return palpiteExiste;
	}

}
