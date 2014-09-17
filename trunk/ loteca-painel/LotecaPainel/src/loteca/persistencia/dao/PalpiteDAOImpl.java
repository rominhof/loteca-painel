package loteca.persistencia.dao;

import loteca.dominio.Palpite;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.PalpiteDAO;

public class PalpiteDAOImpl implements PalpiteDAO {

	private JpaHelper jpaHelper;

	public PalpiteDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	@Transactional
	public Palpite insertOrUpdate(Palpite palpite) {
		Palpite palpiteExiste = null;
		if (palpite.getId() != null) {
			palpiteExiste = jpaHelper.getEntityManager().find(Palpite.class,
					palpite.getId());
		}
		if (palpiteExiste == null) {
			jpaHelper.getEntityManager().persist(palpite);
		} else {
			jpaHelper.getEntityManager().merge(palpite);
		}
		palpiteExiste = jpaHelper.getEntityManager().find(Palpite.class,
				palpite.getId());

		return palpiteExiste;
	}

	@Transactional
	public Palpite atualizarPalpite(Palpite palpite) {
		return jpaHelper.getEntityManager().merge(palpite);
	}

}
