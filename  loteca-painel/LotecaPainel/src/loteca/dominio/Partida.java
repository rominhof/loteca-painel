package loteca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Partida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="ID_PARTIDA")
	private Long id;

	private Integer sequencialJogo;
	@ManyToOne
	private Time time1;
	@ManyToOne
	private Time time2;
	private Integer golTime1;
	private Integer golTime2;
	//ENUM
	private Resultado resultado;
	//ENUM
	private StatusJogo statusJogo;
	
	@ManyToOne
	@JoinColumn(name="NUMCONCURSO")
	private Loteca loteca;
	
	@OneToMany(mappedBy="partida")
	private List<Palpite> palpites;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSequencialJogo() {
		return sequencialJogo;
	}
	public void setSequencialJogo(Integer sequencialJogo) {
		this.sequencialJogo = sequencialJogo;
	}
	public Time getTime1() {
		return time1;
	}
	public void setTime1(Time time1) {
		this.time1 = time1;
	}
	public Time getTime2() {
		return time2;
	}
	public void setTime2(Time time2) {
		this.time2 = time2;
	}
	public Integer getGolTime1() {
		return golTime1;
	}
	public void setGolTime1(Integer golTime1) {
		this.golTime1 = golTime1;
	}
	public Integer getGolTime2() {
		return golTime2;
	}
	public void setGolTime2(Integer golTime2) {
		this.golTime2 = golTime2;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	public StatusJogo getStatusJogo() {
		return statusJogo;
	}
	public void setStatusJogo(StatusJogo statusJogo) {
		this.statusJogo = statusJogo;
	}
	public List<Palpite> getPalpites() {
		return palpites;
	}
	public void setPalpites(List<Palpite> palpites) {
		this.palpites = palpites;
	}
	public Loteca getLoteca() {
		return loteca;
	}
	public void setLoteca(Loteca loteca) {
		this.loteca = loteca;
	}
	
	
	
	
}
