package loteca.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Palpite implements Serializable, Comparable<Palpite> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "ID_PARTIDA")
	private Partida partida;

	@ManyToOne
	@JoinColumn(name = "ID_CARTELA")
	private Cartela cartela;

	private boolean c1;
	private boolean cx;
	private boolean c2;
	private boolean jogoFinalizado;
	private Resultado resultado;
	private Boolean acerto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public boolean getC1() {
		return c1;
	}

	public void setC1(boolean c1) {
		this.c1 = c1;
	}

	public boolean getCx() {
		return cx;
	}

	public void setCx(boolean cx) {
		this.cx = cx;
	}

	public boolean getC2() {
		return c2;
	}

	public void setC2(boolean c2) {
		this.c2 = c2;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

	public Boolean getAcerto() {
		return acerto;
	}

	public void setAcerto(Boolean acerto) {
		this.acerto = acerto;
	}

	public Cartela getCartela() {
		return cartela;
	}

	public void setCartela(Cartela cartela) {
		this.cartela = cartela;
	}

	public boolean isJogoFinalizado() {
		return jogoFinalizado;
	}

	public void setJogoFinalizado(boolean jogoFinalizado) {
		this.jogoFinalizado = jogoFinalizado;
	}

	@Override
	public int compareTo(Palpite arg0) {
		Palpite p = (Palpite) arg0;
		return this.getPartida().getSequencialJogo()
				.compareTo(p.getPartida().getSequencialJogo());
	}

}
