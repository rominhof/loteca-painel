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


}
