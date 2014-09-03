package loteca.service;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import loteca.dominio.Loteca;
import loteca.util.LotecaUtil;

public class LotecaService {

	LotecaUtil lotecaUtil = new LotecaUtil();
	
	public Loteca carregaLotecaAtual(){
		
		return lotecaUtil.getLotecaAtual();
	}
	
	public void baixarArquivosJsonFI() throws Exception{

			String path = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
			lotecaUtil.baixarTodosArquivosAtualizados(path);
			
		
	}
}
