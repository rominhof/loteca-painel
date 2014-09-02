package cartolafc.dominio;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Atleta implements Comparable, Serializable{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idLocal;
	@Column(name="ID")
	private Integer id;
	private String apelido;
	private Double pontos;
	private String foto;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Posicao posicao;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Clube clube;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Clube clubeVisitante;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Clube clubeCasa;
	private String dataJogo;
	
	//Auxiliar
	@Transient
	private int indice;
	@Transient
	private Map<String, Object> times;
	@Transient
	private Integer qtdTimes;
	@Transient
	private boolean atletaIgual;
	@Transient
	private boolean atletaExclusivo;
	@Transient
	private int qtdAtletasIguais;
	
	public Atleta(){
		
	}
	
	

	public String getApelido() {
		return apelido;
	}



	public void setApelido(String apelido) {
		this.apelido = apelido;
	}



	public Double getPontos() {
		return pontos;
	}



	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}



	public String getFoto() {
		return foto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}

	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Map<String, Object> getTimes() {
		return times;
	}



	public void setTimes(Map<String, Object> times) {
		this.times = times;
	}



	@Override
	public String toString() {
		return apelido +": " +pontos;
	}
	
	


	public int getIndice() {
		return indice;
	}



	public void setIndice(int indice) {
		this.indice = indice;
	}

	
	


	public Posicao getPosicao() {
		return posicao;
	}



	public void setPosicao(Posicao posicao) {
		this.posicao = posicao;
	}



	public Clube getClube() {
		return clube;
	}



	public void setClube(Clube clube) {
		this.clube = clube;
	}



	public Clube getClubeVisitante() {
		return clubeVisitante;
	}



	public void setClubeVisitante(Clube clubeVisitante) {
		this.clubeVisitante = clubeVisitante;
	}



	public Clube getClubeCasa() {
		return clubeCasa;
	}



	public void setClubeCasa(Clube clubeCasa) {
		this.clubeCasa = clubeCasa;
	}
	
	public Integer getQtdTimes(){
		return times.size();
	}

	


	public String getDataJogo() {
		return dataJogo;
	}



	public void setDataJogo(String dataJogo) {
		this.dataJogo = dataJogo;
	}

	
	


	public Long getIdLocal() {
		return idLocal;
	}



	public void setIdLocal(Long idLocal) {
		this.idLocal = idLocal;
	}



	public int getQtdAtletasIguais() {
		return qtdAtletasIguais;
	}



	public void setQtdAtletasIguais(int qtdAtletasIguais) {
		this.qtdAtletasIguais = qtdAtletasIguais;
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
		Atleta other = (Atleta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public int compareTo(Object arg0) {
		Atleta atleta =(Atleta)arg0; 
		return id.compareTo(atleta.getId());
	}



	public Boolean getAtletaIgual() {
		return atletaIgual;
	}



	public void setAtletaIgual(Boolean atletaIgual) {
		this.atletaIgual = atletaIgual;
	}



	public Boolean getAtletaExclusivo() {
		return qtdAtletasIguais==0?Boolean.TRUE:Boolean.FALSE;
	}


	
	
	
	
}
