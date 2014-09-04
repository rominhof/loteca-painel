package loteca.persistencia.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import loteca.dominio.GrupoCartela;
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

	public Usuario findById(Long id) {
		// TODO Auto-generated method stub
		return em.find(Usuario.class, id);
	}
	
	public Usuario insertOrUpdate(Usuario usuario){
		em.getTransaction().begin();
		Usuario usuarioExiste = null;
		if(usuario.getId()!=null){
			usuarioExiste = em.find(Usuario.class, usuario.getId());
		}
		if(usuarioExiste==null){
			em.persist(usuario);
		}else{
			em.merge(usuario);
		}
		usuarioExiste = em.find(Usuario.class, usuario.getId());
		em.getTransaction().commit();
		
		return usuarioExiste;
	
	}
	
}
