package loteca.persistencia.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.Usuario;
import loteca.util.JPAUtil;

public class UsuarioDAO {

	private EntityManager em;
	
	public UsuarioDAO(){
		em = JPAUtil.getEntityManager();
	}
	
	public Usuario findUsuarioByLoginSenha(Usuario usuario){
		Query query = em.createNamedQuery("Usuario.findByLoginSenha");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		Usuario usuarioCadastrado = null;
		try{
			usuarioCadastrado =(Usuario)query.getSingleResult();
		}catch (NoResultException e) {
			return usuarioCadastrado;
		}
			return usuarioCadastrado;
	}
	
}
