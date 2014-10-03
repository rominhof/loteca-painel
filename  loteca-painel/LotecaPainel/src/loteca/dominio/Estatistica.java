package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@NamedQueries({ @NamedQuery(name = "Estatistica.findByLotecaEGrupoUsuario", query = "select e from loteca.dominio.Estatistica e where e.loteca.numConcurso = :numConcurso and e.grupoCartela.id = :idGrupo") })
public class Estatistica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ESTATISTICA")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "NUMCONCURSO")
	private Loteca loteca;

	@ManyToOne
	@JoinColumn(name = "ID_GRUPO_CARTELA")
	private GrupoCartela grupoCartela;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EstatisticaPalpites> estatisticaPalpites;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Loteca getLoteca() {
		return loteca;
	}

	public void setLoteca(Loteca loteca) {
		this.loteca = loteca;
	}

	public GrupoCartela getGrupoCartela() {
		return grupoCartela;
	}

	public void setGrupoCartela(GrupoCartela grupoCartela) {
		this.grupoCartela = grupoCartela;
	}

	public List<EstatisticaPalpites> getEstatisticaPalpites() {
		return estatisticaPalpites;
	}

	public void setEstatisticaPalpites(
			List<EstatisticaPalpites> estatisticaPalpites) {
		this.estatisticaPalpites = estatisticaPalpites;
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
		Estatistica other = (Estatistica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}