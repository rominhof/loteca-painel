package loteca.persistencia.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Usuario;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.UsuarioDAO;

public class UsuarioDAOImpl implements UsuarioDAO {

	private JpaHelper jpaHelper;

	public UsuarioDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	public Usuario findUsuarioByLoginSenha(Usuario usuario) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"Usuario.findByLoginSenha");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		Usuario usuarioCadastrado = null;
		try {
			usuarioCadastrado = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return usuarioCadastrado;
		}
		return usuarioCadastrado;
	}

	public Usuario findById(Long id) {
		return jpaHelper.getEntityManager().find(Usuario.class, id);
	}

	@Transactional
	public Usuario insertOrUpdate(Usuario usuario) {
		Usuario usuarioExiste = null;
		if (usuario.getId() != null) {
			usuarioExiste = jpaHelper.getEntityManager().find(Usuario.class,
					usuario.getId());
		}
		if (usuarioExiste == null) {
			jpaHelper.getEntityManager().persist(usuario);
		} else {
			jpaHelper.getEntityManager().merge(usuario);
		}
		usuarioExiste = jpaHelper.getEntityManager().find(Usuario.class,
				usuario.getId());

		return usuarioExiste;
	}

}
