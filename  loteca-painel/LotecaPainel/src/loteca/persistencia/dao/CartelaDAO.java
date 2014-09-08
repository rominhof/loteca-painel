package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import loteca.dominio.Cartela;
import loteca.dominio.Loteca;
import loteca.util.JPAUtil;

public class CartelaDAO {

	private EntityManager em;
	
	public CartelaDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public void refresh(Cartela cartela){
		em.refresh(cartela);
	}
	
	public Cartela insertOrUpdate(Cartela cartela){
		em.getTransaction().begin();
		Cartela cartelaExiste = null;
		if(cartela.getId()!=null){
			cartelaExiste = em.find(Cartela.class, cartela.getId());
		}
		if(cartelaExiste==null){
			em.persist(cartela);
		}else{
			em.merge(cartela);
		}
		cartelaExiste = em.find(Cartela.class, cartela.getId());
		em.getTransaction().commit();
		
		return cartelaExiste;
	}
	
	public List<Cartela> findByLoteca(Integer numConcurso){
		Query query = em.createNamedQuery("Cartela.findByLoteca");
		query.setParameter("numConcurso", numConcurso);
		return (List<Cartela>)query.getResultList();
	}
	
}
