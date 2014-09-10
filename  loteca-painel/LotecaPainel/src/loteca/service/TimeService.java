package loteca.service;

import loteca.dominio.Time;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.TimeDAO;

public class TimeService {

	private TimeDAO timeDAO;

	public TimeService() {
		// Vazio
	}

	private TimeDAO getTimeDAO() {
		if (this.timeDAO == null) {
			this.timeDAO = DAOFactory.getRepository(TimeDAO.class);
		}
		return this.timeDAO;
	}

	public Time consultaTimePorNome(String nome) {

		return getTimeDAO().findByNome(nome);
	}

}
