package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="GrupoCartela.findByUsuario", query="select gc from GrupoCartela gc join gc.usuarios u where u.id = :id"),
	@NamedQuery(name="GrupoCartela.findByUsuarioLoteca", query="select distinct gc from GrupoCartela gc join gc.usuarios u left join gc.cartelas c where u.id = :id and (c.loteca.numConcurso = :numConcurso or c.loteca.numConcurso is null)")
	})
public class GrupoCartela implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_GRUPO_CARTELA")
	private Long id;
	
	private String nome;
	
	@OneToMany(mappedBy="grupoCartela", cascade=CascadeType.ALL)
	private List<Cartela> cartelas;

	@ManyToMany(mappedBy="gruposCartelas", cascade=CascadeType.ALL)
	private List<Usuario> usuarios;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Cartela> getCartelas() {
		return cartelas;
	}
	public void setCartelas(List<Cartela> cartelas) {
		this.cartelas = cartelas;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoCartela other = (GrupoCartela) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
 
	
	
	
	
	
	
	
}
