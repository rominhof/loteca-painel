package loteca.controle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import loteca.dominio.Usuario;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


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
	
	public String realPath = ((ServletContext)FacesContext.getCurrentInstance()
			.getExternalContext().getContext()).getRealPath("/");
	
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
	
	
	protected Usuario getUsuarioLogado() {
		return (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
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
	
	public String gerarPDFFromObjetosDataSource(Map map, String nomeRel, List dados ){
		try { 
			
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext sc = (ServletContext) fc.getExternalContext().getContext();  
			String path = sc.getRealPath("/");			
		    JasperPrint impressao;
		    map.put("SUBREPORT_DIR", path+"jasper/");
		        try {
		        	JRBeanCollectionDataSource dadosBean = new JRBeanCollectionDataSource(dados);
		            impressao = JasperFillManager.fillReport(path+"jasper/"+nomeRel+".jasper", map, dadosBean);
		            JasperExportManager.exportReportToPdfFile(impressao,path + "/jasper/"+nomeRel+".pdf");
		            HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		            rp.setHeader("Cache-Control", "no-cache");
		            rp.setHeader("Content-Disposition", "attachment; filename="+nomeRel+".pdf"); 
		            rp.setHeader("Pragma", "no-cache");
		            rp.sendRedirect("/LotecaPainel/jasper/"+nomeRel+".pdf");
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		            
		        }       
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
	
	
	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public void updateModel(ActionEvent ev){
		//System.out.println("atualizou modelo");
	}

}
