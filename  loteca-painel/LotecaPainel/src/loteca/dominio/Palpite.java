package loteca.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Palpite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Long id;
	@OneToOne
	private Partida partida;
	
	private Boolean c1;
	private Boolean cx;
	private Boolean c2;
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
	public Boolean getC1() {
		return c1;
	}
	public void setC1(Boolean c1) {
		this.c1 = c1;
	}
	public Boolean getCx() {
		return cx;
	}
	public void setCx(Boolean cx) {
		this.cx = cx;
	}
	public Boolean getC2() {
		return c2;
	}
	public void setC2(Boolean c2) {
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
	
	
	
}
