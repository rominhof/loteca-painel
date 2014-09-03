package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Campeonato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String nome;
	@ManyToMany
	@JoinTable(name="Campeonato_Time")
	private List<Time> times;
	
	//Valores do Json do FI
	private int id_ano;
	private int id_fase;
	
	public int getId_ano() {
		return id_ano;
	}
	public void setId_ano(int id_ano) {
		this.id_ano = id_ano;
	}
	public int getId_fase() {
		return id_fase;
	}
	public void setId_fase(int id_fase) {
		this.id_fase = id_fase;
	}
/**	public List<Confronto> getTabela() {
		return tabela;
	}
	public void setTabela(List<Confronto> tabela) {
		this.tabela = tabela;
	}
	**/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Time> getTimes() {
		return times;
	}
	public void setTimes(List<Time> times) {
		this.times = times;
	}
	
	
	
}
