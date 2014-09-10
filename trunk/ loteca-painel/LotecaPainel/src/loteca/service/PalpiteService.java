package loteca.service;

import loteca.dominio.Palpite;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.PalpiteDAO;

public class PalpiteService {

	PalpiteDAO palpiteDAO;

	public PalpiteService() {
		// Vazio
	}

	private PalpiteDAO getPalpiteDAO() {
		if (this.palpiteDAO == null) {
			this.palpiteDAO = DAOFactory.getRepository(PalpiteDAO.class);
		}
		return this.palpiteDAO;
	}

	public void salvar(Palpite p) {
		getPalpiteDAO().insertOrUpdate(p);
	}
}
