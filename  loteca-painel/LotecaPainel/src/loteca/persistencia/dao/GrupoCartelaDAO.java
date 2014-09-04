package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;
import loteca.util.JPAUtil;

public class GrupoCartelaDAO {
	
	private EntityManager em;
	
	public GrupoCartelaDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public GrupoCartela findById(Long id) {
		return em.find(GrupoCartela.class, id);
	}

	public List<GrupoCartela> findByUsuario(Usuario u) {
		Query query = em.createNamedQuery("GrupoCartela.findByUsuario");
		query.setParameter("id", u.getId());
		return (List<GrupoCartela>)query.getResultList();
	}
	
	public List<GrupoCartela> findByUsuarioLoteca(Usuario u, Integer numConcurso) {
		Query query = em.createNamedQuery("GrupoCartela.findByUsuarioLoteca");
		query.setParameter("id", u.getId());
		query.setParameter("numConcurso", numConcurso);
		return (List<GrupoCartela>)query.getResultList();
	}
	
	public GrupoCartela insertOrUpdate(GrupoCartela grupoCartela){
		em.getTransaction().begin();
		GrupoCartela grupoCartelaExiste = null;
		if(grupoCartela.getId()!=null){
			grupoCartelaExiste = em.find(GrupoCartela.class, grupoCartela.getId());
		}
		if(grupoCartelaExiste==null){
			em.persist(grupoCartela);
		}else{
			em.merge(grupoCartela);
		}
		grupoCartelaExiste = em.find(GrupoCartela.class, grupoCartela.getId());
		em.getTransaction().commit();
		
		return grupoCartelaExiste;
	
	}

}
