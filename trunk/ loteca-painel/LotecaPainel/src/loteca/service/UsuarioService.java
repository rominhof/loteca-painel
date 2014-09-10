package loteca.service;

import loteca.dominio.Usuario;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.UsuarioDAO;

public class UsuarioService {

	private UsuarioDAO usuarioDAO;

	public UsuarioService() {
		// Vazio
	}

	private UsuarioDAO getUsuarioDAO() {
		if (this.usuarioDAO == null) {
			this.usuarioDAO = DAOFactory.getRepository(UsuarioDAO.class);
		}
		return this.usuarioDAO;
	}

	public Usuario login(Usuario usuario) {
		return getUsuarioDAO().findUsuarioByLoginSenha(usuario);
	}

	public Usuario salvar(Usuario usuario) {
		Usuario u = getUsuarioDAO().insertOrUpdate(usuario);
		return u;
	}

	public Usuario finById(Long id) {
		Usuario u = getUsuarioDAO().findById(id);
		return u;
	}

}
