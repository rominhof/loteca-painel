package loteca.persistencia.dao;

import loteca.dominio.Partida;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.PartidaDAO;

public class PartidaDAOImpl implements PartidaDAO {

	private JpaHelper jpaHelper;

	public PartidaDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	@Transactional
	public Partida insertOrUpdate(Partida partida) {
		Partida partidaExiste = null;
		if (partida.getId() != null) {
			partidaExiste = jpaHelper.getEntityManager().find(Partida.class,
					partida.getId());
		}
		if (partidaExiste == null) {
			jpaHelper.getEntityManager().persist(partida);
		} else {
			jpaHelper.getEntityManager().merge(partida);
		}
		partidaExiste = jpaHelper.getEntityManager().find(Partida.class,
				partida.getId());

		return partidaExiste;
	}

}
