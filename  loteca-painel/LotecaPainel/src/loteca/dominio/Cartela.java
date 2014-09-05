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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="Cartela.findByLoteca", query="select c from Cartela c where c.loteca.numConcurso = :numConcurso ")
	})
public class Cartela implements Serializable, Comparable<Cartela>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_CARTELA")
	private Long id;
	
	private Integer seqCartela;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cartela")
	private List<Palpite> palpites;
	
	@ManyToOne
	@JoinColumn(name="NUMCONCURSO")
	private Loteca loteca;
	
	@ManyToOne
	@JoinColumn(name="ID_GRUPO_CARTELA")
	private GrupoCartela grupoCartela;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public List<Palpite> getPalpites() {
		return palpites;
	}
	public void setPalpites(List<Palpite> palpites) {
		this.palpites = palpites;
	}
	public Integer getSeqCartela() {
		return seqCartela;
	}
	public void setSeqCartela(Integer seqCartela) {
		this.seqCartela = seqCartela;
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
	@Override
	public int compareTo(Cartela o) {
		Cartela c =(Cartela)o; 
		return seqCartela.compareTo(c.getSeqCartela());
	}
	
	
	
	
	
}
