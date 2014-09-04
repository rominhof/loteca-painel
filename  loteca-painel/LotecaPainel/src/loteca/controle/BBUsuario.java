package loteca.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import loteca.dominio.Usuario;
import loteca.service.UsuarioService;

@ManagedBean(name="bBUsuario")
@SessionScoped
public class BBUsuario extends BBDefault {
	
	private Usuario usuarioLogin;
	private Usuario usuarioLogado;
	private static final String PAGINA_LOGIN_SUCESSO = "home";
	private UsuarioService usuarioService;

	
	public BBUsuario(){
		usuarioService = new UsuarioService();
		usuarioLogin = new Usuario();
	}
	
	public String login(){
		usuarioLogado = usuarioService.login(usuarioLogin);
		if(usuarioLogado!=null){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
			return PAGINA_LOGIN_SUCESSO;
		}else{
			addError("Falha na autenticação! Login e/ou Senha inválidos.");
			return null;
		}
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	
	
	
	
	
	
}
