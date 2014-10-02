package loteca.persistencia.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Estatistica;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.EstatisticaDAO;

public class EstatisticaDAOImpl implements EstatisticaDAO {

	private JpaHelper jpaHelper;

	public EstatisticaDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	public Estatistica findByLotecaEGrupoUsuario(Loteca loteca,
			GrupoCartela grupoCartela) {
		Estatistica retorno = null;
		try {
			Query query = jpaHelper.getEntityManager().createNamedQuery(
					"Estatistica.findByLotecaEGrupoUsuario");
			query.setParameter("numConcurso", loteca.getNumConcurso());
			query.setParameter("idGrupo", grupoCartela.getId());

			retorno = (Estatistica) query.getSingleResult();
		} catch (NoResultException e) {
			// Vazio
		}

		return retorno;
	}

	@Transactional
	public Estatistica insertOrUpdate(Estatistica estatistica) {
		Estatistica estatisticaExiste = null;
		if (estatistica.getId() != null) {
			estatisticaExiste = jpaHelper.getEntityManager().find(
					Estatistica.class, estatistica.getId());
		}
		if (estatisticaExiste == null) {
			jpaHelper.getEntityManager().persist(estatistica);
		} else {
			jpaHelper.getEntityManager().merge(estatistica);
		}
		estatisticaExiste = jpaHelper.getEntityManager().find(
				Estatistica.class, estatistica.getId());
		return estatistica;

	}

}
