package loteca.service;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.persistencia.dao.LotecaDAO;
import loteca.persistencia.dao.PartidaDAO;
import loteca.util.JPAUtil;
import loteca.util.LotecaUtil;

public class LotecaService {

	private EntityManager em;
	LotecaUtil lotecaUtil;
	LotecaDAO lotecaDAO;
	PartidaDAO partidaDAO;
	TimeService timeService;

	public LotecaService() {
		em = JPAUtil.getEntityManager();
		lotecaUtil = new LotecaUtil();
		lotecaDAO = new LotecaDAO();
		partidaDAO = new PartidaDAO();
		timeService = new TimeService();
	}
	
	public void refresh(Loteca loteca){
		lotecaDAO.refresh(loteca);
	}

	public Loteca carregaLotecaAtual() {
		return lotecaDAO.findByStatus(Boolean.FALSE);
	}

	public void baixarArquivosJsonFI() throws Exception {
		String path = ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext()).getRealPath("/");
		lotecaUtil.baixarTodosArquivosAtualizados(path);
	}

	public void cadastrarLoteca(Loteca loteca) {
		em.getTransaction().begin();
		preencheTimesLoteca(loteca);
		lotecaDAO.insert(loteca);
		em.getTransaction().commit();

	}

	public Loteca consultaLotecaPorNumeroConcurso(Integer numConcurso) {
		return lotecaDAO.findByNumConcurso(numConcurso);
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
		em.getTransaction().begin();
		lotecaDAO.insertOrUpdate(loteca);
		em.getTransaction().commit();
	}

	public void atualizaPartida(Partida partida) {
		em.getTransaction().begin();
		partidaDAO.insertOrUpdate(partida);
		em.getTransaction().commit();
	}
}
