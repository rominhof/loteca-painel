package loteca.service;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.persistencia.dao.LotecaDAO;
import loteca.util.LotecaUtil;

public class LotecaService {

	LotecaUtil lotecaUtil = new LotecaUtil();
	LotecaDAO lotecaDAO = new LotecaDAO();
	TimeService timeService = new TimeService();
	
	public Loteca carregaLotecaAtual(){
		
		return lotecaUtil.getLotecaAtual();
	}
	
	public void baixarArquivosJsonFI() throws Exception{

			String path = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
			lotecaUtil.baixarTodosArquivosAtualizados(path);
			
		
	}
	
	public void cadastrarLoteca(Loteca loteca){
		preencheTimesLoteca(loteca);
		lotecaDAO.insert(loteca);
	}
	
	public void preencheTimesLoteca(Loteca loteca){
		for(Partida p: loteca.getPartidas()){
			p.setTime1(timeService.consultaTimePorNome(p.getTime1().getNome()));
			p.setTime2(timeService.consultaTimePorNome(p.getTime2().getNome()));
		}
		
	}
}
