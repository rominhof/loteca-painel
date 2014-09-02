package cartolafc.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import cartolafc.dominio.Time;
import cartolafc.util.JPAUtil;

public class TimeDAO {

	private EntityManager em;
	
	public TimeDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public Time insertTime(Time time){
		em.getTransaction().begin();
		Time timeExiste = em.find(Time.class, time.getId());
		
		if(timeExiste!=null){
			em.merge(time);
		}else{
			em.persist(time);
		}
		em.getTransaction().commit();
		
		return time;
	
	}
	
	public List<Time> findAllByRodada(Integer rodada){
		Query query = em.createNamedQuery("Time.findByRodada");
		query.setParameter("rodada", rodada);
		return (List<Time>)query.getResultList();
	}

}
