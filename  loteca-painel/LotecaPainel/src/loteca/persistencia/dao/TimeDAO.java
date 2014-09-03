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
		if(nome!=null){
			query.setParameter("nome", nome.toUpperCase());
		}else{
			return null;
		}
		return (Time)query.getSingleResult();
	}
	

}
