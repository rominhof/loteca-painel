package loteca.service;

import loteca.dominio.Loteca;
import loteca.util.LotecaUtil;

public class LotecaService {

	LotecaUtil lotecaUtil = new LotecaUtil();
	
	public Loteca carregaLotecaAtual(){
		
		return lotecaUtil.getLotecaAtual();
	}
}
