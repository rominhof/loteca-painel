package loteca.service;

import loteca.dominio.Time;
import loteca.persistencia.dao.TimeDAO;
import loteca.util.LotecaUtil;

public class TimeService {

	LotecaUtil lotecaUtil = new LotecaUtil();
	TimeDAO timeDAO = new TimeDAO();
	
	
	public Time consultaTimePorNome(String nome){

		return timeDAO.findByNome(nome);
	}
	

}
