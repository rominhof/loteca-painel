package loteca.service;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.LotecaDAO;
import loteca.persistencia.api.PartidaDAO;
import loteca.util.LotecaUtil;

public class LotecaService {

	private LotecaUtil lotecaUtil;
	private LotecaDAO lotecaDAO;
	private PartidaDAO partidaDAO;
	private TimeService timeService;

	public LotecaService() {
		lotecaUtil = new LotecaUtil();
		timeService = new TimeService();
	}

	private LotecaDAO getLotecaDAO() {
		if (this.lotecaDAO == null) {
			this.lotecaDAO = DAOFactory.getRepository(LotecaDAO.class);
		}
		return this.lotecaDAO;
	}

	private PartidaDAO getPartidaDAO() {
		if (this.partidaDAO == null) {
			this.partidaDAO = DAOFactory.getRepository(PartidaDAO.class);
		}
		return this.partidaDAO;
	}

	public void refresh(Loteca loteca) {
		getLotecaDAO().refresh(loteca);
	}

	public Loteca carregaLotecaAtual() {
		return getLotecaDAO().findByStatus(Boolean.FALSE);
	}

	public List<Loteca> consultaTodasLotecas() {
		return getLotecaDAO().findAll();
	}

	public void baixarArquivosJsonFI() throws Exception {
		String path = ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext()).getRealPath("/");
		lotecaUtil.baixarTodosArquivosAtualizados(path);
	}

	public void cadastrarLoteca(Loteca loteca) {
		preencheTimesLoteca(loteca);
		getLotecaDAO().insert(loteca);

	}

	public Loteca consultaLotecaPorNumeroConcurso(Integer numConcurso) {
		Loteca loteca = getLotecaDAO().findByNumConcurso(numConcurso);
		return loteca;
	}

	public void preencheTimesLoteca(Loteca loteca) {
		for (Partida p : loteca.getPartidas()) {
			p.setTime1(timeService.consultaTimePorNome(p.getTime1().getNome()));
			p.setTime2(timeService.consultaTimePorNome(p.getTime2().getNome()));
			p.setLoteca(loteca);
		}
	}

	public Loteca carregaLotecaAtualOficialCaixa() {
		return lotecaUtil.getLotecaAtualOficialCaixa();
	}

	public void atualizaLoteca(Loteca loteca) {
		getLotecaDAO().insertOrUpdate(loteca);
	}

	public void atualizaPartida(Partida partida) {
		getPartidaDAO().insertOrUpdate(partida);
	}
}
