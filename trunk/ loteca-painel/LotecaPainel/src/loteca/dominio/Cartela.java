package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cartela implements Serializable{
	
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
	
	
	
	
}
