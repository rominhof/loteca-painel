package loteca.service;

import javax.persistence.EntityManager;

import loteca.dominio.Palpite;
import loteca.persistencia.dao.PalpiteDAO;
import loteca.util.JPAUtil;

public class PalpiteService {

	private EntityManager em;
	PalpiteDAO palpiteDAO;

	public PalpiteService() {
		em = JPAUtil.getEntityManager();
		palpiteDAO = new PalpiteDAO();
	}

	public void salvar(Palpite p){
		palpiteDAO.insertOrUpdate(p);
	}
}
