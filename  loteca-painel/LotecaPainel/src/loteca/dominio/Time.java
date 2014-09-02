package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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

	@ManyToMany
	private List<Campeonato> campeonato;
	
	public Time(){
		
	}
	
	public Time(String nome){
		this.nome=nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
