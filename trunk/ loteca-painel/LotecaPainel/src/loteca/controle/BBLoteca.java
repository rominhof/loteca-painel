package loteca.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import loteca.dominio.Cartela;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.service.CartelaService;
import loteca.service.LotecaService;

@ManagedBean(name="bBLoteca")
@SessionScoped
public class BBLoteca extends BBDefault {
	
	private Loteca loteca;
	private GrupoCartela grupoCartela;
	private List<GrupoCartela> gruposCartelas;
	private LotecaService lotecaService;
	private CartelaService cartelaService;
	private List<Cartela> cartelas;
	
	public BBLoteca(){
		cartelas = new ArrayList<Cartela>();
		lotecaService = new LotecaService();
		cartelaService = new CartelaService();
		carregaLotecaAtual();
	}
	
	public void sincronizaLotecaAtual(){
		loteca = lotecaService.carregaLotecaAtualOficialCaixa();
		loteca.setFinalizado(Boolean.FALSE);
		Loteca lotecaExistenteBase = lotecaService.consultaLotecaPorNumeroConcurso(loteca.getNumConcurso());
		if(lotecaExistenteBase!=null){
			addError("Loteca já existe cadastrada na base!");
		}else{
			addInfo("Loteca sincronizada com sucesso!");
			lotecaService.cadastrarLoteca(loteca);
		}
	}
	
	public void salvarCartelas(){
		cartelaService.salvarCartelas(cartelas);
	}
	
	public void carregaLotecaAtual(){
		loteca = lotecaService.carregaLotecaAtual();
		Collections.reverse(loteca.getPartidas());
		cartelas = cartelaService.consultarCartelasPorConcurso(loteca.getNumConcurso());
		Collections.reverse(cartelas);
		for (Cartela c : cartelas) {
			Collections.reverse(c.getPalpites());
		}
		if(loteca == null){
			loteca = new Loteca();
		}
	}
	
	public void novaCartela(){
		Cartela cartela = new Cartela();
		cartela.setSeqCartela(cartelas.size()+1);
		cartela.setLoteca(loteca);
		List<Palpite> palpites = new ArrayList<Palpite>();
		for(Partida p: loteca.getPartidas()){
			Palpite pt = new Palpite();
			pt.setPartida(p);
			pt.setCartela(cartela);
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
	
	
	
	public GrupoCartela getGrupoCartela() {
		return grupoCartela;
	}

	public void setGrupoCartela(GrupoCartela grupoCartela) {
		this.grupoCartela = grupoCartela;
	}

	public List<GrupoCartela> getGruposCartelas() {
		return gruposCartelas;
	}

	public void setGruposCartelas(List<GrupoCartela> gruposCartelas) {
		this.gruposCartelas = gruposCartelas;
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
