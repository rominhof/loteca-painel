package loteca.persistencia.dao;

import javax.persistence.EntityManager;

import loteca.dominio.Partida;
import loteca.util.JPAUtil;

public class PartidaDAO {

	private EntityManager em;

	public PartidaDAO() {
		em = JPAUtil.getEntityManager();
	}

	public Partida insertOrUpdate(Partida partida) {
		em.getTransaction().begin();
		Partida partidaExiste = null;
		if (partida.getId() != null) {
			partidaExiste = em.find(Partida.class, partida.getId());
		}
		if (partidaExiste == null) {
			em.persist(partida);
		} else {
			em.merge(partida);
		}
		partidaExiste = em.find(Partida.class, partida.getId());
		em.getTransaction().commit();

		return partidaExiste;
	}

}
