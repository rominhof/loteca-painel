package loteca.service;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import loteca.dominio.Cartela;
import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.persistencia.dao.CartelaDAO;
import loteca.persistencia.dao.LotecaDAO;
import loteca.util.JPAUtil;
import loteca.util.LotecaUtil;

public class CartelaService {

	private EntityManager em;
	CartelaDAO cartelaDAO;

	public CartelaService() {
		em = JPAUtil.getEntityManager();
		cartelaDAO = new CartelaDAO();
	}

	public List<Cartela> consultarCartelasPorConcurso(Integer numConcurso) {
		return cartelaDAO.findByLoteca(numConcurso);
	}
	
	public List<Cartela> salvarCartelas(List<Cartela> cartelas){
		em.getTransaction().begin();
		for(Cartela cartela: cartelas){
			cartelaDAO.insertOrUpdate(cartela);
		}
		em.getTransaction().commit();
		return cartelas;
	}

}
