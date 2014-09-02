package cartolafc.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import cartolafc.dominio.Atleta;
import cartolafc.dominio.Time;
import cartolafc.dominio.Times;
import cartolafc.service.TimeService;
import cartolafc.util.ParciaisUtil;

@ManagedBean(name="bBParciais")
@SessionScoped
public class BBParciais extends BBDefault {
	
	private List<Time> times = new ArrayList<Time>();
	private List<Atleta> atletas = new ArrayList<Atleta>();
	private Times[] timesEnum;
	private Times timeSelecionado1;
	private Times timeSelecionado2 = null;
	
	private Time timesMaisParecidos1;
	private Time timesMaisParecidos2;
	private String jogadorMaisEscalado;
	private Time timeComMaisExclusivos;
	
	private Time timeComparado;
	
	private List<Time> timesComparar;
	private ParciaisUtil parciasUtil;
	private String confrontosDaRodadaHTML;
	private TimeService timeService;
	private List<Time> timesClassificacao;

	public BBParciais(){
		parciasUtil = new ParciaisUtil();
		timesEnum = Times.values();
		timeService = new TimeService();
		carregarParciais();
	}
	
	public void carregarPainel(){
		try{
			atletas=parciasUtil.getAllAtletas();
			addInfo("Dados atualizados com sucesso!");
		}catch(Exception e){
			addError("Falha ao atualizar dados. Servidor Oficial do Cartola indisponivel!");
		}
	}
	
	public String carregarParciais(){
		try{
			timesClassificacao = timeService.consultarTimesPorRodada(12);
			times=parciasUtil.getTimes(Boolean.TRUE);
			
			Collections.sort(times,new Comparator<Time>(){  
	            public int compare(Time t1, Time t2){  
	                return Double.compare(t1.getPontuacao(),t2.getPontuacao());  
	            }  
	        }); 
			Collections.reverse(times);
		}catch(Exception e){
			e.printStackTrace();
			addError("Falha ao atualizar dados. Servidor Oficial do Cartola indisponivel!");
		}
		return "/pages/parciais";
	}

	public List<Time> getTimes() {
		return times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}

	public List<Atleta> getAtletas() {
		return atletas;
	}

	public void setAtletas(List<Atleta> atletas) {
		this.atletas = atletas;
	}
	
	public Times[] getTimesEnum(){
		return timesEnum;
	}
	
	
	public Times getTimeSelecionado1() {
		return timeSelecionado1;
	}

	public void setTimeSelecionado1(Times timeSelecionado1) {
		this.timeSelecionado1 = timeSelecionado1;
	}
	
	
	public Times getTimeSelecionado2() {
		return timeSelecionado2;
	}

	public void setTimeSelecionado2(Times timeSelecionado2) {
		this.timeSelecionado2 = timeSelecionado2;
	}

	
	public void analisarRodada(){
		int maiorVlrIgual = 0;
		int maiorVlrExclusivo = 0;
		for(Times timeEnum: Times.values()){
			Time timeAnalisado = parciasUtil.getTimeComAtletas(Boolean.TRUE, timeEnum.getSlug());
			timeComparado = parciasUtil.getTimeComAtletas(Boolean.TRUE, timeSelecionado1.getSlug());
			List<Time> times = compararTimes();
			
			if(times.get(0).getQtdAtletasIguais()>maiorVlrIgual){
				timesMaisParecidos1=timeAnalisado;
				timesMaisParecidos2=times.get(0);
				maiorVlrIgual = times.get(0).getQtdAtletasIguais();
			}
			
			if(timeAnalisado.getQtdAtletasExclusivos()>maiorVlrExclusivo){
				timeComMaisExclusivos = timeAnalisado;
				maiorVlrExclusivo = timeAnalisado.getQtdAtletasExclusivos();
			}
		}
		
		timesComparar = compararTimes();
	}
	
	
	private List<Time> compararTimes(){
		List<Time> timesCompararLocal = new ArrayList<Time>();
		if(timeSelecionado2==null){
			timesCompararLocal=parciasUtil.getTimes(Boolean.TRUE);
			timesCompararLocal.remove(timeComparado);
		}else{
			Time timeComparar = parciasUtil.getTimeComAtletas(Boolean.TRUE, timeSelecionado2.getSlug());
			timesCompararLocal.add(timeComparar);
		}
		for(Time timeComp: timesCompararLocal){
			parciasUtil.compararTimes(timeComparado, timeComp);
		}
		
		Collections.sort(timesCompararLocal,new Comparator<Time>(){  
            public int compare(Time t1, Time t2){  
                return Double.compare(t1.getQtdAtletasIguais(),t2.getQtdAtletasIguais());  
            }  
        }); 
		Collections.reverse(timesCompararLocal);
		
		return timesCompararLocal;
	}
	
	public void baixarArquivosJsonsAtualizados(){
		try{
			String path = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
			parciasUtil.baixarTodosArquivosAtualizados(path);
			List<Time> times = parciasUtil.getTimes(Boolean.TRUE);
			
			timeService.persisteTimesRodada(times);
			
			addInfo("Sincronização com servidor do CartolaFC realizada com sucesso!");
		}catch (Exception e) {
			e.printStackTrace();
			addError("Falha ao sincronizar dados com servidores do CartolaFC!");
		}
		
	}
	
	public void baixarTime(){
		ParciaisUtil pa = new ParciaisUtil();
		String path = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
		pa.baixarArquivosAtualizados(Times.PICAPAUSFC, path);
	}
	
	public List<Time> getTimesComparar() {
		return timesComparar;
	}

	public void setTimesComparar(List<Time> timesComparar) {
		this.timesComparar = timesComparar;
	}

	public String irCompararTime(){
		return "/pages/comparartimes";
	}
	
	public String irPainel(){
		this.carregarPainel();
		return "/pages/painelcartoleiro";
	}

	public Time getTimeComparado() {
		return timeComparado;
	}

	public void setTimeComparado(Time timeComparado) {
		this.timeComparado = timeComparado;
	}

	public Time getTimesMaisParecidos1() {
		return timesMaisParecidos1;
	}

	public void setTimesMaisParecidos1(Time timesMaisParecidos1) {
		this.timesMaisParecidos1 = timesMaisParecidos1;
	}

	public Time getTimesMaisParecidos2() {
		return timesMaisParecidos2;
	}

	public void setTimesMaisParecidos2(Time timesMaisParecidos2) {
		this.timesMaisParecidos2 = timesMaisParecidos2;
	}

	public String getJogadorMaisEscalado() {
		return jogadorMaisEscalado;
	}

	public List<Time> getTimesClassificacao() {
		return timesClassificacao;
	}

	public void setTimesClassificacao(List<Time> timesClassificacao) {
		this.timesClassificacao = timesClassificacao;
	}

	public void setJogadorMaisEscalado(String jogadorMaisEscalado) {
		this.jogadorMaisEscalado = jogadorMaisEscalado;
	}

	public Time getTimeComMaisExclusivos() {
		return timeComMaisExclusivos;
	}

	public void setTimeComMaisExclusivos(Time timeComMaisExclusivos) {
		this.timeComMaisExclusivos = timeComMaisExclusivos;
	}

	public String getConfrontosDaRodadaHTML(){
		confrontosDaRodadaHTML = parciasUtil.getConfrontosDaRodadaHtml();
		return confrontosDaRodadaHTML;
	}
	
	public void setConfrontosDaRodadaHTML(String confrontosDaRodadaHTML) {
		this.confrontosDaRodadaHTML = confrontosDaRodadaHTML;
	}


	
	
	
	
}
