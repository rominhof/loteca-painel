package cartolafc.service;

import java.util.List;

import javax.persistence.EntityManager;

import cartolafc.dominio.Time;
import cartolafc.persistencia.dao.TimeDAO;
import cartolafc.util.JPAUtil;

public class TimeService {
	
	private EntityManager em;
	private TimeDAO timeDAO;
	
	public TimeService(){
		em = JPAUtil.getEntityManager();
		timeDAO = new TimeDAO();
	}
	
	public Time persisteTimeRodada(Time time){
		em.getTransaction().begin();
		timeDAO.insertTime(time);
		em.getTransaction().commit();
	
		return time;
	}
	
	public List<Time> persisteTimesRodada(List<Time> times){
		//em.getTransaction().begin();
		for(Time time: times){
			timeDAO.insertTime(time);
		}
		//em.getTransaction().commit();
	//	em.close();
	
		return times;
	}
	
	public List<Time> consultarTimesPorRodada(Integer rodada){
		List<Time> times = timeDAO.findAllByRodada(rodada);
		return times;
	}
	

}
