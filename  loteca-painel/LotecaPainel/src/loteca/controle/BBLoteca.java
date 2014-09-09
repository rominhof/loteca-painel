package loteca.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import loteca.dominio.Cartela;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.dominio.Usuario;
import loteca.service.CartelaService;
import loteca.service.GrupoCartelaService;
import loteca.service.LotecaService;
import loteca.service.UsuarioService;

@ManagedBean(name="bBLoteca")
@ViewScoped
public class BBLoteca extends BBDefault {
	
	private Loteca loteca;
	private GrupoCartela grupoCartela;
	private List<GrupoCartela> gruposCartelas;
	private LotecaService lotecaService;
	private UsuarioService usuarioService;
	private GrupoCartelaService grupoCartelaService;
	private CartelaService cartelaService;
	private List<Cartela> cartelas;
	private List<Loteca> lotecas;
	

	public BBLoteca(){
		usuarioService = new UsuarioService();
		lotecaService = new LotecaService();
		grupoCartelaService = new GrupoCartelaService();
		cartelaService = new CartelaService();
		grupoCartela = new GrupoCartela();
		carregaLotecaAtual();
		carregaListaDeLotecas();
	}
	
	private void carregaListaDeLotecas() {
		lotecas = lotecaService.consultaTodasLotecas();
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
	
	public void atualizaEntidadesBanco(){
		/*
		lotecaService.refresh(loteca);
		grupoCartelaService.refresh(grupoCartela);
		for(Cartela cartela: cartelas){
			cartelaService.refresh(cartela);
		}*/
		carregaLotecaAtual();
		System.out.println("atualizando tela");
	}
	
	public void salvarCartelas(){
			
			for(Cartela c: cartelas){
				cartelaService.salvar(c);
				/**for(Palpite p: c.getPalpites()){
					p.setCartela(c);
					palpiteService.salvar(p);
				}*/
			}
			addInfo("Cartelas salvas com sucesso!");
		
	}
	
	public void removerCartelasSelecionadas(){
		for(Cartela cartela: cartelas){
			if(cartela.getSelecionado()){
				grupoCartela.getCartelas().remove(cartela);
			}
		}
		
		grupoCartelaService.salvar(grupoCartela);
		addInfo("Cartelas removidas com sucesso!");
	}
	
	public void removerCartela(ActionEvent ev){
		Cartela cartela = (Cartela)ev.getComponent().getAttributes().get("cartela");
		cartelaService.remove(cartela);
		addInfo("Cartela removida com sucesso!");
		cartelas.remove(cartela);
	}
	
	public void concluirCartelas(){
		for(Cartela c: cartelas){
			c.setConcluida(Boolean.TRUE);
			cartelaService.salvar(c);
		}
		addInfo("Cartelas salvas com sucesso!");
	}
	
	
	public void carregaLotecaAtual(){
		loteca = lotecaService.carregaLotecaAtual();
		carregaGrupoCartelasEPalpites();

	}
	
	private void carregaGrupoCartelasEPalpites(){
		if(loteca!=null){
			gruposCartelas = grupoCartelaService.consultarGruposCartelasPorUsuarioConcurso(getUsuarioLogado(), loteca.getNumConcurso());
			if(gruposCartelas!=null && gruposCartelas.size()>0){
				grupoCartela = gruposCartelas.get(0);
				cartelas=grupoCartela.getCartelas();
				ordenaCartelaEpalpites();
			}else{
				cartelas = new ArrayList<Cartela>();
			}
			if(loteca!=null && loteca.getPartidas()!=null)
				Collections.sort(loteca.getPartidas());
			
		}else{
			loteca = new Loteca();
		}
	}
	
	private void ordenaCartelaEpalpites(){
		Collections.sort(cartelas);
		for(Cartela c: cartelas){
			if(c.getPalpites()!=null)
				Collections.sort(c.getPalpites());
		}
	}
	
	public void selecionaGrupoCartela(){
		System.out.println("selecionou: "+grupoCartela);
		cartelas = grupoCartela.getCartelas();
		ordenaCartelaEpalpites();
	}
	
	public void selecionaLoteca(){
		loteca = lotecaService.consultaLotecaPorNumeroConcurso(loteca.getNumConcurso());
		carregaGrupoCartelasEPalpites();
	}
	
	
	public void novaCartela(){

		Cartela cartela = new Cartela();
		if(cartelas==null){
			cartelas = new ArrayList<Cartela>();
		}
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
		
		cartela.setGrupoCartela(grupoCartela);
		cartelas.add(cartela);
		
		cartelaService.salvar(cartela);

	}
	
	public void preparaCadastraGrupoCartela(){
		grupoCartela = new GrupoCartela();
	}
	
	public void salvaGrupoCartela(){
		carregaGruposCartelasUsuario();
		Usuario u = getUsuarioLogado();
		gruposCartelas.add(grupoCartela);
		u.setGruposCartelas(gruposCartelas);
		usuarioService.salvar(u);
		carregaGruposCartelasUsuario();
		
	}

	private void carregaGruposCartelasUsuario() {
		gruposCartelas = grupoCartelaService.consultarGruposCartelasPorUsuario(getUsuarioLogado());
		
	}

	public Loteca getLoteca() {
		return loteca;
	}

	public void setLoteca(Loteca loteca) {
		this.loteca = loteca;
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

	public List<Cartela> getCartelas() {
		return cartelas;
	}

	public void setCartelas(List<Cartela> cartelas) {
		this.cartelas = cartelas;
	}

	public List<Loteca> getLotecas() {
		return lotecas;
	}

	public void setLotecas(List<Loteca> lotecas) {
		this.lotecas = lotecas;
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
