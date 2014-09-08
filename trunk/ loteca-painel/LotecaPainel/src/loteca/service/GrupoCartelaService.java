package loteca.service;

import java.util.List;

import javax.persistence.EntityManager;

import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;
import loteca.persistencia.dao.GrupoCartelaDAO;
import loteca.persistencia.dao.UsuarioDAO;
import loteca.util.JPAUtil;

public class GrupoCartelaService {

	private EntityManager em;
	GrupoCartelaDAO grupoCartelaDAO;
	UsuarioDAO usuarioDAO;

	public GrupoCartelaService() {
		em = JPAUtil.getEntityManager();
		grupoCartelaDAO = new GrupoCartelaDAO();
		usuarioDAO = new UsuarioDAO();
	}

	public List<GrupoCartela> consultarGruposCartelasPorUsuario(Usuario u) {
		return grupoCartelaDAO.findByUsuario(u);
	}

	public List<GrupoCartela> consultarGruposCartelasPorUsuarioConcurso(
			Usuario u, Integer numConcurso) {
		return grupoCartelaDAO.findByUsuarioLoteca(u, numConcurso);
	}

	public GrupoCartela consultarPorId(Long id) {
		return grupoCartelaDAO.findById(id);
	}

	public void refresh(GrupoCartela gc) {
		grupoCartelaDAO.refresh(gc);
	}

	public GrupoCartela salvar(GrupoCartela grupoCartela) {
		em.getTransaction().begin();
		grupoCartelaDAO.insertOrUpdate(grupoCartela);

		em.getTransaction().commit();
		return grupoCartela;
	}

	public void associarUsuarios(GrupoCartela grupoCartela,
			List<Usuario> usuarios) {
		em.getTransaction().begin();
		for (Usuario usuario : usuarios) {
			usuario.getGruposCartelas().add(grupoCartela);
			usuarioDAO.insertOrUpdate(usuario);
		}

		em.getTransaction().commit();
	}

}
