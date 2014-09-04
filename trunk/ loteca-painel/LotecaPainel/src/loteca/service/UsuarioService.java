package loteca.service;

import javax.persistence.EntityManager;

import loteca.dominio.Usuario;
import loteca.persistencia.dao.UsuarioDAO;
import loteca.util.JPAUtil;

public class UsuarioService {

	private EntityManager em;
	UsuarioDAO usuarioDAO;

	public UsuarioService() {
		em = JPAUtil.getEntityManager();
		usuarioDAO = new UsuarioDAO();
	}

	public Usuario login(Usuario usuario) {
		return usuarioDAO.findUsuarioByLoginSenha(usuario);
	}
	
	public Usuario salvar(Usuario usuario) {
		em.getTransaction().begin();
		Usuario u = usuarioDAO.insertOrUpdate(usuario);
		em.getTransaction().commit();
		return u;
	}
	
	public Usuario finById(Long id){
		Usuario u = usuarioDAO.findById(id);
		return u;
	}


}
