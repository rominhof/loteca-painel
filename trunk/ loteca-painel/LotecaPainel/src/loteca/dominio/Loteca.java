package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="Loteca.findAll", query="select l from loteca.dominio.Loteca l "),
	@NamedQuery(name="Loteca.findByNumConcurso", query="select l from loteca.dominio.Loteca l where l.numConcurso = :numConcurso"),
	@NamedQuery(name="Loteca.findByStatus", query="select l from loteca.dominio.Loteca l where l.finalizado = :finalizado")})
public class Loteca implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer numConcurso;

	@OneToMany(cascade=CascadeType.PERSIST, mappedBy="loteca")
	private List<Partida> partidas;
	
	private Boolean finalizado;
	

	public Integer getNumConcurso() {
		return numConcurso;
	}
	public void setNumConcurso(Integer numConcurso) {
		this.numConcurso = numConcurso;
	}
	public List<Partida> getPartidas() {
		return partidas;
	}
	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}
	public Boolean getFinalizado() {
		return finalizado;
	}
	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	
	
	
}
