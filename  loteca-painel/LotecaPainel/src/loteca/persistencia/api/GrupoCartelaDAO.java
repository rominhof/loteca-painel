package loteca.persistencia.api;

import java.util.List;

import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;

public interface GrupoCartelaDAO {

	GrupoCartela findById(Long id);

	List<GrupoCartela> findByUsuario(Usuario u);

	void refresh(GrupoCartela gc);

	List<GrupoCartela> findByUsuarioLoteca(Usuario u, Integer numConcurso);

	GrupoCartela insertOrUpdate(GrupoCartela grupoCartela);

	void associarUsuarioAoGrupo(GrupoCartela grupoCartela, Usuario usuario)
			throws Exception;
}
