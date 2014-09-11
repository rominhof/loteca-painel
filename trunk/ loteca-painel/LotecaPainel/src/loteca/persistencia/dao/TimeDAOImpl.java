package loteca.persistencia.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Time;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.api.PalpiteDAO;
import loteca.persistencia.api.TimeDAO;

public class TimeDAOImpl implements TimeDAO{

	private JpaHelper jpaHelper;

	public TimeDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	public Time findByNome(String nome) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"Time.findByNome");
		if (nome != null) {
			query.setParameter("nome", nome.toLowerCase());
		} else {
			return null;
		}
		Time time = null;
		try {
			time = (Time) query.getSingleResult();
		} catch (NoResultException e) {
			return time;
		}
		return time;
	}

}
