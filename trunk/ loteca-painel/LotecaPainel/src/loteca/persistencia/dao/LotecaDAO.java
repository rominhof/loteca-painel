package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import loteca.dominio.Loteca;
import loteca.util.JPAUtil;

public class LotecaDAO {

	private EntityManager em;
	
	public LotecaDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public Loteca insert(Loteca loteca){
		em.getTransaction().begin();
		Loteca lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());
		
		if(lotecaExiste==null){
			em.persist(lotecaExiste);
			lotecaExiste = em.find(Loteca.class, loteca.getNumConcurso());
		}
		em.getTransaction().commit();
		
		return lotecaExiste;
	
	}
	
	public List<Loteca> findAll(){
		Query query = em.createNamedQuery("Loteca.findAll");
		return (List<Loteca>)query.getResultList();
	}
	
	public Loteca findByNumConcurso(Integer numConcurso){
		Query query = em.createNamedQuery("Loteca.findByNumConcurso");
		query.setParameter("numConcurso", numConcurso);
		return (Loteca)query.getSingleResult();
	}

}
