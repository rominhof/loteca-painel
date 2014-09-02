package cartolafc.controle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;


/**
 * Classe que contem operacoes e atributos padrao aos BackingBeans
 * 
 * @author luizsergioviana
 *
 */

@ManagedBean(name="bBDefault")
public class BBDefault implements Serializable{
	
	protected static final String MSG_PADRAO_OPERACAO_SUCESSO = "Operação realizada com sucesso!";
	protected static final String MSG_PADRAO_OPERACAO_FALHA = "Falha ao realizado Operação!";
	protected static final String MSG_PADRAO_OPERACAO_ERRO = "Ocorreu um erro ao realizar operação!";
	protected static final String MSG_PADRAO_OPERACAO_ALERTA = "Ocorreram falhas durante Operação!";
	
	
	protected void addInfo(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info: ", msg));
	}

	protected void addWarn(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Atencao: ", msg));
	}

	protected void addError(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro: ", msg));
	}

	protected void addFatal(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Falha: ", msg));
	}
	

	
	public void salvar(){
		addInfo(MSG_PADRAO_OPERACAO_SUCESSO);
	}
	
	public String novo(){
		return null;
	}
	
	public void limpar(){
		
	}
	
	public String voltar(){
		return null;
	}
	
	public String cancelar(){
		return null;
	}
	
	public void consultar(){
		
	}

	
	public String getPath(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();  
		String pathSIN = sc.getRealPath("/");	
		return pathSIN;
	}
	
	
	
	public void updateModel(ActionEvent ev){
		//System.out.println("atualizou modelo");
	}

}
