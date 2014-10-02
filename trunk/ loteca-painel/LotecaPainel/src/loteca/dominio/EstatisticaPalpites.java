package loteca.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class EstatisticaPalpites implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_PARTIDA")
	private Partida partida;

	private float porcentagemC1;
	private float porcentagemCX;
	private float porcentagemC2;

	@Transient
	private int totalC1;

	@Transient
	private int totalC2;

	@Transient
	private int totalCX;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public float getPorcentagemC1() {
		return porcentagemC1;
	}

	public void setPorcentagemC1(float porcentagemC1) {
		this.porcentagemC1 = porcentagemC1;
	}

	public float getPorcentagemCX() {
		return porcentagemCX;
	}

	public void setPorcentagemCX(float porcentagemCX) {
		this.porcentagemCX = porcentagemCX;
	}

	public float getPorcentagemC2() {
		return porcentagemC2;
	}

	public void setPorcentagemC2(float porcentagemC2) {
		this.porcentagemC2 = porcentagemC2;
	}

	public void adicionaC1() {
		this.totalC1++;
	}

	public void adicionaC2() {
		this.totalC2++;
	}

	public void adicionaCX() {
		this.totalCX++;
	}

	/**
	 * Gerar porcentagem de acordo com o total de palpites
	 */
	public void gerarPorcentagem() {
		int total = totalC1 + totalC2 + totalCX;

		this.porcentagemC1 = (totalC1 * 100) / total;
		this.porcentagemC2 = (totalC2 * 100) / total;
		this.porcentagemCX = (totalCX * 100) / total;
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
		EstatisticaPalpites other = (EstatisticaPalpites) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}