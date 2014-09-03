package loteca.service;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.persistencia.dao.LotecaDAO;
import loteca.util.JPAUtil;
import loteca.util.LotecaUtil;

public class LotecaService {

	private EntityManager em;
	LotecaUtil lotecaUtil;
	LotecaDAO lotecaDAO;
	TimeService timeService;
	
	public LotecaService(){
		em = JPAUtil.getEntityManager();
		lotecaUtil = new LotecaUtil();
		lotecaDAO = new LotecaDAO();
		timeService = new TimeService();
	}
	
	public Loteca carregaLotecaAtual(){
		return lotecaDAO.findByStatus(Boolean.FALSE);
	}
	
	public void baixarArquivosJsonFI() throws Exception{
		String path = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
		lotecaUtil.baixarTodosArquivosAtualizados(path);
	}
	
	public void cadastrarLoteca(Loteca loteca){
		em.getTransaction().begin();
		preencheTimesLoteca(loteca);
		lotecaDAO.insert(loteca);
		em.getTransaction().commit();
		
	}
	
	public Loteca consultaLotecaPorNumeroConcurso(Integer numConcurso){
		return lotecaDAO.findByNumConcurso(numConcurso);
	}
	
	public void preencheTimesLoteca(Loteca loteca){
		for(Partida p: loteca.getPartidas()){
			p.setTime1(timeService.consultaTimePorNome(p.getTime1().getNome()));
			p.setTime2(timeService.consultaTimePorNome(p.getTime2().getNome()));
		}
		
	}

	public Loteca carregaLotecaAtualOficialCaixa() {
		return lotecaUtil.getLotecaAtualOficialCaixa();
	}
}
