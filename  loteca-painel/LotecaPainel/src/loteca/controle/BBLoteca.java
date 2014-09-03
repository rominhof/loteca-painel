package loteca.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import loteca.dominio.Cartela;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.service.LotecaService;

@ManagedBean(name="bBLoteca")
@SessionScoped
public class BBLoteca extends BBDefault {
	
	private Loteca loteca;
	private LotecaService lotecaService;
	private List<Cartela> cartelas;
	
	public BBLoteca(){
		loteca = new Loteca();
		cartelas = new ArrayList<Cartela>();
		lotecaService = new LotecaService();
	}
	
	public void carregaLotecaAtual(){
		
		loteca = lotecaService.carregaLotecaAtual();
	}
	
	public void novaCartela(){
		Cartela cartela = new Cartela();
		cartela.setSeqCartela(cartelas.size()+1);
		List<Palpite> palpites = new ArrayList<Palpite>();
		for(Partida p: loteca.getPartidas()){
			Palpite pt = new Palpite();
			pt.setPartida(p);
			palpites.add(pt);
		}
		cartela.setPalpites(palpites);
		cartelas.add(cartela);
	}

	public Loteca getLoteca() {
		return loteca;
	}

	public void setLoteca(Loteca loteca) {
		this.loteca = loteca;
	}

	public List<Cartela> getCartelas() {
		return cartelas;
	}

	public void setCartelas(List<Cartela> cartelas) {
		this.cartelas = cartelas;
	}
	
	public void baixarArquivosJsonsFutebolInterior(){
		try{
			lotecaService.baixarArquivosJsonFI();
			addInfo("Arquivos atualizados com sucesso!");
	}catch (Exception e) {
		e.printStackTrace();
		addError("Falha ao baixar arquivos!");
	}
		
		
	}
	
	
	
	
	
}
