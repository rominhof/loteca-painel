package loteca.persistencia.api;

import loteca.dominio.Usuario;

public interface UsuarioDAO {

	public Usuario findUsuarioByLoginSenha(Usuario usuario);

	public Usuario findById(Long id);

	public Usuario insertOrUpdate(Usuario usuario);
}
