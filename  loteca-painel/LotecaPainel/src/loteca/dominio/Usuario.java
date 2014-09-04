package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.findByLoginSenha", query="select u from Usuario u where u.login =:login and u.senha=:senha ")})
public class Usuario implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	
	private String login;
	
	private String senha;
	
	private String email;
	
	@ManyToMany
	@JoinTable(name="Usuario_GrupoCartelas")
	private List<GrupoCartela> gruposCartelas;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<GrupoCartela> getGruposCartelas() {
		return gruposCartelas;
	}

	public void setGruposCartelas(List<GrupoCartela> gruposCartelas) {
		this.gruposCartelas = gruposCartelas;
	}

	
	
	
	
	
	
}
