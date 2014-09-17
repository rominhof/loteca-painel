package loteca.service;

import java.util.List;

import loteca.dominio.Cartela;
import loteca.dominio.Palpite;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.CartelaDAO;
import loteca.persistencia.api.PalpiteDAO;

public class CartelaService {
	private CartelaDAO cartelaDAO;
	private PalpiteDAO palpiteDAO;

	public CartelaService() {
		// Vazio
	}

	private CartelaDAO getCartelaDAO() {
		if (this.cartelaDAO == null) {
			this.cartelaDAO = DAOFactory.getRepository(CartelaDAO.class);
		}
		return this.cartelaDAO;
	}

	private PalpiteDAO getPalpiteDAO() {
		if (this.palpiteDAO == null) {
			this.palpiteDAO = DAOFactory.getRepository(PalpiteDAO.class);
		}
		return this.palpiteDAO;
	}
	
	
	public List<Cartela> carregaCartelasDeConcursoGrupoCartela(Integer numConcurso, Long idgc) {
		return getCartelaDAO().findByLotecaGrupoCartela(numConcurso, idgc);
	}

	public List<Cartela> carregaCartelasDeConcurso(Integer numConcurso) {
		return getCartelaDAO().findByLoteca(numConcurso);
	}

	public void refresh(Cartela cartela) {
		getCartelaDAO().refresh(cartela);
	}

	public void salvar(Cartela cartela) {
		getCartelaDAO().insertOrUpdate(cartela);
	}

	public void atualizaPalpite(Palpite palpite) {
		getPalpiteDAO().insertOrUpdate(palpite);
	}
	
	public Palpite atualizarPalpite(Palpite palpite) {
		return getPalpiteDAO().atualizarPalpite(palpite);
	}
	

	public void remove(Cartela cartela) {
		getCartelaDAO().remove(cartela);

	}
}