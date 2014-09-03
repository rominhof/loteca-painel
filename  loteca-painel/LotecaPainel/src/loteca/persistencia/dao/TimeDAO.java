package loteca.persistencia.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import loteca.dominio.Time;
import loteca.util.JPAUtil;

public class TimeDAO {

	private EntityManager em;
	
	public TimeDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	
	public Time findByNome(String nome){
		Query query = em.createNamedQuery("Time.findByNome");
		query.setParameter("nome", nome);
		return (Time)query.getSingleResult();
	}
	

}
