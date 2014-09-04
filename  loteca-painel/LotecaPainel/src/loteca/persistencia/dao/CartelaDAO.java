package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Cartela;
import loteca.dominio.Loteca;
import loteca.util.JPAUtil;

public class CartelaDAO {

	private EntityManager em;
	
	public CartelaDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public Cartela insertOrUpdate(Cartela cartela){
		em.getTransaction().begin();
		Cartela cartelaExiste = em.find(Cartela.class, cartela.getId());
		
		if(cartelaExiste==null){
			em.persist(cartela);
		}else{
			em.merge(cartela);
		}
		cartelaExiste = em.find(Cartela.class, cartela.getId());
		em.getTransaction().commit();
		
		return cartelaExiste;
	
	}
	
}
