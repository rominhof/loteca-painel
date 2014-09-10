package loteca.service;

import java.util.List;

import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.GrupoCartelaDAO;
import loteca.persistencia.api.UsuarioDAO;

public class GrupoCartelaService {

	private GrupoCartelaDAO grupoCartelaDAO;
	private UsuarioDAO usuarioDAO;

	public GrupoCartelaService() {
		// Vazio
	}

	private GrupoCartelaDAO getGrupoCartelaDAO() {
		if (this.grupoCartelaDAO == null) {
			this.grupoCartelaDAO = DAOFactory
					.getRepository(GrupoCartelaDAO.class);
		}
		return this.grupoCartelaDAO;
	}

	private UsuarioDAO getUsuarioDAO() {
		if (this.usuarioDAO == null) {
			this.usuarioDAO = DAOFactory.getRepository(UsuarioDAO.class);
		}
		return this.usuarioDAO;
	}

	public List<GrupoCartela> consultarGruposCartelasPorUsuario(Usuario u) {
		return getGrupoCartelaDAO().findByUsuario(u);
	}

	public List<GrupoCartela> consultarGruposCartelasPorUsuarioConcurso(
			Usuario u, Integer numConcurso) {
		return getGrupoCartelaDAO().findByUsuarioLoteca(u, numConcurso);
	}

	public GrupoCartela consultarPorId(Long id) {
		return getGrupoCartelaDAO().findById(id);
	}

	public void refresh(GrupoCartela gc) {
		getGrupoCartelaDAO().refresh(gc);
	}

	public GrupoCartela salvar(GrupoCartela grupoCartela) {
		getGrupoCartelaDAO().insertOrUpdate(grupoCartela);

		return grupoCartela;
	}

	public void associarUsuarios(GrupoCartela grupoCartela,
			List<Usuario> usuarios) {
		for (Usuario usuario : usuarios) {
			usuario.getGruposCartelas().add(grupoCartela);
			getUsuarioDAO().insertOrUpdate(usuario);
		}
	}

}
