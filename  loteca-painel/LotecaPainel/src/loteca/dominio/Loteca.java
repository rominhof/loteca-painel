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

	@OneToMany(cascade=CascadeType.ALL, mappedBy="loteca", orphanRemoval=true)
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((finalizado == null) ? 0 : finalizado.hashCode());
		result = prime * result
				+ ((numConcurso == null) ? 0 : numConcurso.hashCode());
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
		Loteca other = (Loteca) obj;
		if (finalizado == null) {
			if (other.finalizado != null)
				return false;
		} else if (!finalizado.equals(other.finalizado))
			return false;
		if (numConcurso == null) {
			if (other.numConcurso != null)
				return false;
		} else if (!numConcurso.equals(other.numConcurso))
			return false;
		return true;
	}


	
	
	
	
}
