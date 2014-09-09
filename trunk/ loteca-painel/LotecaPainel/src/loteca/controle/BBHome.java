package loteca.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="bBHome")
@RequestScoped
public class BBHome extends BBDefault {
	
	private static final String PAGINA_LOTECA = "loteca_painel";
	private static final String PAGINA_LOTECA_M = "pm:loteca";
	
	public BBHome(){

	}
	
	
	public String gerenciarMinhaLoteca(){
	
		return PAGINA_LOTECA;
	}
	
	public String gerenciarMinhaLoteca2(){
		
		return PAGINA_LOTECA_M;
	}
	
	
	
	
}
