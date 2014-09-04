package loteca.service;

import java.util.List;

import javax.persistence.EntityManager;

import loteca.dominio.Cartela;
import loteca.dominio.Palpite;
import loteca.persistencia.dao.CartelaDAO;
import loteca.persistencia.dao.PalpiteDAO;
import loteca.util.JPAUtil;

public class CartelaService {
	private CartelaDAO cartelaDAO;
	private PalpiteDAO palpiteDAO;
	private EntityManager em;

	public CartelaService() {
		em = JPAUtil.getEntityManager();
		cartelaDAO = new CartelaDAO();
		palpiteDAO = new PalpiteDAO();
	}

	public List<Cartela> carregaCartelasDeConcurso(Integer numConcurso) {
		return cartelaDAO.findByLoteca(numConcurso);
	}

	public void atualizaCartela(Cartela cartela) {
		em.getTransaction().begin();
		cartelaDAO.insertOrUpdate(cartela);
		em.getTransaction().commit();
	}

	public void atualizaPalpite(Palpite palpite) {
		em.getTransaction().begin();
		palpiteDAO.insertOrUpdate(palpite);
		em.getTransaction().commit();
	}
}