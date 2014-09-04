package loteca.service;

import java.util.List;

import javax.persistence.EntityManager;

import loteca.dominio.Cartela;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;
import loteca.persistencia.dao.GrupoCartelaDAO;
import loteca.util.JPAUtil;

public class GrupoCartelaService {

	private EntityManager em;
	GrupoCartelaDAO grupoCartelaDAO;

	public GrupoCartelaService() {
		em = JPAUtil.getEntityManager();
		grupoCartelaDAO = new GrupoCartelaDAO();
	}

	public List<GrupoCartela> consultarGruposCartelasPorUsuario(Usuario u) {
		return grupoCartelaDAO.findByUsuario(u);
	}
	
	public List<GrupoCartela> consultarGruposCartelasPorUsuarioConcurso(Usuario u, Integer numConcurso) {
		return grupoCartelaDAO.findByUsuarioLoteca(u, numConcurso);
	}
	
	public GrupoCartela consultarPorId(Long id) {
		return grupoCartelaDAO.findById(id);
	}
	
	public GrupoCartela salvar(GrupoCartela grupoCartela){
		em.getTransaction().begin();
		grupoCartelaDAO.insertOrUpdate(grupoCartela);
		
		em.getTransaction().commit();
		return grupoCartela;
	}
	

}
