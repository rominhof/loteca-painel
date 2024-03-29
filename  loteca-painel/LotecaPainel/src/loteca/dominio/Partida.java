package loteca.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Partida implements Serializable, Comparable<Partida> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PARTIDA")
	private Long id;

	private Integer sequencialJogo;
	@ManyToOne
	private Time time1;
	@ManyToOne
	private Time time2;
	private Integer golTime1;
	private Integer golTime2;

	@ManyToOne
	@JoinColumn(name = "NUMCONCURSO")
	private Loteca loteca;

	// ENUM
	private Resultado resultado;
	// ENUM
	private StatusJogo statusJogo;

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

	public Loteca getLoteca() {
		return loteca;
	}

	public void setLoteca(Loteca loteca) {
		this.loteca = loteca;
	}

	@Override
	public int compareTo(Partida o) {
		Partida p = (Partida) o;
		return sequencialJogo.compareTo(p.getSequencialJogo());
	}

}
