package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Time.findByNome", query="select t from Time t where lower(t.nome) = :nome ")})
public class Time implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String nome;
	


	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToMany(mappedBy="times")
	private List<Campeonato> campeonato;
	
	public Time(){
		
	}
	
	public Time(String nome){
		this.nome=nome;
	}
	
	public String getNome() {
		return nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Campeonato> getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(List<Campeonato> campeonato) {
		this.campeonato = campeonato;
	}
	
	
	
}
