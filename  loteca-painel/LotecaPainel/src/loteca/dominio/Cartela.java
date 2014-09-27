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
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@NamedQueries({
	@NamedQuery(name="Cartela.findByLotecaGrupoCartela", query="select c from Cartela c where c.grupoCartela.id = :grupoCartela and c.loteca.numConcurso = :numConcurso"),
	@NamedQuery(name="Cartela.findByLoteca", query="select c from Cartela c where c.loteca.numConcurso = :numConcurso")
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
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="cartela",orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Palpite> palpites;
	
	@ManyToOne
	@JoinColumn(name="NUMCONCURSO")
	private Loteca loteca;
	
	@ManyToOne
	@JoinColumn(name="ID_GRUPO_CARTELA")
	private GrupoCartela grupoCartela;
	
	private Boolean concluida;
	
	@Transient
	private Boolean selecionado;
	
	private ChanceCartelaEnum chance;
	
	private Integer qtdAcertos;
	
	
	
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
	
	public Boolean getConcluida() {
		return concluida;
	}
	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}
	public GrupoCartela getGrupoCartela() {
		return grupoCartela;
	}
	public void setGrupoCartela(GrupoCartela grupoCartela) {
		this.grupoCartela = grupoCartela;
	}
	
	
	
	public Integer getQtdAcertos() {
		return qtdAcertos;
	}
	public void setQtdAcertos(Integer qtdAcertos) {
		this.qtdAcertos = qtdAcertos;
	}
	public Boolean getSelecionado() {
		return selecionado;
	}
	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}
	
	
	public ChanceCartelaEnum getChance() {
		return chance;
	}
	public void setChance(ChanceCartelaEnum chance) {
		this.chance = chance;
	}
	
	@Override
	public int compareTo(Cartela o) {
		Cartela c =(Cartela)o; 
		return seqCartela.compareTo(c.getSeqCartela());
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
		Cartela other = (Cartela) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
