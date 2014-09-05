package loteca.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import loteca.dominio.Cartela;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.dominio.Usuario;
import loteca.service.CartelaService;
import loteca.service.GrupoCartelaService;
import loteca.service.LotecaService;
import loteca.service.PalpiteService;
import loteca.service.UsuarioService;

@ManagedBean(name="bBLoteca")
@SessionScoped
public class BBLoteca extends BBDefault {
	
	private Loteca loteca;
	private GrupoCartela grupoCartela;
	private List<GrupoCartela> gruposCartelas;
	private LotecaService lotecaService;
	private UsuarioService usuarioService;
	private GrupoCartelaService grupoCartelaService;
	private CartelaService cartelaService;
	private PalpiteService palpiteService;
	

	public BBLoteca(){
		usuarioService = new UsuarioService();
		lotecaService = new LotecaService();
		grupoCartelaService = new GrupoCartelaService();
		cartelaService = new CartelaService();
		grupoCartela = new GrupoCartela();
		palpiteService = new PalpiteService();
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

			grupoCartelaService.salvar(grupoCartela);
			
			/**for(Cartela c: grupoCartela.getCartelas()){
				for(Palpite p: c.getPalpites()){
					p.setCartela(c);
					palpiteService.salvar(p);
				}
			}*/
			addInfo("Cartelas salvas com sucesso!");
		
	}
	
	public void carregaLotecaAtual(){
		loteca = lotecaService.carregaLotecaAtual();
		
		if(loteca!=null){
			gruposCartelas = grupoCartelaService.consultarGruposCartelasPorUsuarioConcurso(getUsuarioLogado(), loteca.getNumConcurso());
			if(gruposCartelas!=null && gruposCartelas.size()>0){
				grupoCartela = gruposCartelas.get(0);
			}
			if(loteca!=null && loteca.getPartidas()!=null)
				Collections.sort(loteca.getPartidas());
		}else{
			loteca = new Loteca();
		}
	}
	
	public void selecionaGrupoCartela(){
		System.out.println("selecionou: "+grupoCartela);
		/**Collections.sort(grupoCartela.getCartelas());
		Collections.reverse(grupoCartela.getCartelas());
		for(Cartela c: grupoCartela.getCartelas()){
			Collections.sort(c.getPalpites());
			Collections.reverse(c.getPalpites());
		}*/
	}
	
	
	public void novaCartela(){

		Cartela cartela = new Cartela();
		cartela.setSeqCartela(grupoCartela.getCartelas().size()+1);
		cartela.setLoteca(loteca);
		
		List<Palpite> palpites = new ArrayList<Palpite>();
		for(Partida p: loteca.getPartidas()){
			Palpite pt = new Palpite();
			pt.setPartida(p);
			pt.setCartela(cartela);
			palpites.add(pt);
		}
		cartela.setPalpites(palpites);
		grupoCartela.getCartelas().add(cartela);
		cartela.setGrupoCartela(grupoCartela);
		
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
