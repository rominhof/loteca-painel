package loteca.persistencia.dao;

import java.util.List;

import javax.persistence.Query;

import loteca.dominio.GrupoCartela;
import loteca.dominio.Usuario;
import loteca.persistencia.JpaHelper;
import loteca.persistencia.Transactional;
import loteca.persistencia.api.GrupoCartelaDAO;

public class GrupoCartelaDAOImpl implements GrupoCartelaDAO {

	private JpaHelper jpaHelper;

	public GrupoCartelaDAOImpl(JpaHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}

	public GrupoCartela findById(Long id) {
		return jpaHelper.getEntityManager().find(GrupoCartela.class, id);
	}

	public List<GrupoCartela> findByUsuario(Usuario u) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"GrupoCartela.findByUsuario");
		query.setParameter("id", u.getId());
		return (List<GrupoCartela>) query.getResultList();
	}

	public void refresh(GrupoCartela gc) {
		jpaHelper.getEntityManager().refresh(gc);
	}

	public List<GrupoCartela> findByUsuarioLoteca(Usuario u, Integer numConcurso) {
		Query query = jpaHelper.getEntityManager().createNamedQuery(
				"GrupoCartela.findByUsuarioLoteca");
		query.setParameter("id", u.getId());
		query.setParameter("numConcurso", numConcurso);
		return (List<GrupoCartela>) query.getResultList();
	}

	@Transactional
	public GrupoCartela insertOrUpdate(GrupoCartela grupoCartela) {
		GrupoCartela grupoCartelaExiste = null;
		if (grupoCartela.getId() != null) {
			grupoCartelaExiste = jpaHelper.getEntityManager().find(
					GrupoCartela.class, grupoCartela.getId());
		}
		if (grupoCartelaExiste == null) {
			jpaHelper.getEntityManager().persist(grupoCartela);
		} else {
			jpaHelper.getEntityManager().merge(grupoCartela);
		}
		grupoCartelaExiste = jpaHelper.getEntityManager().find(
				GrupoCartela.class, grupoCartela.getId());
		return grupoCartelaExiste;

	}

	@Transactional
	public void associarUsuarioAoGrupo(GrupoCartela grupoCartela,
			Usuario usuario) throws Exception {
		if (findById(grupoCartela.getId()) == null) {
			throw new Exception("Grupo loteca não existe");
		}

	}

}
