package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="Loteca.findAll", query="select l from Loteca l "),
	@NamedQuery(name="Loteca.findByNumeroConcurso", query="select l from Loteca l where l.numConcurso = :numConcurso")})
public class Loteca implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer numConcurso;

	@OneToMany
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
