package cartolafc.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name="Time.findAll", query="select t from Time t "),
	@NamedQuery(name="Time.findByRodada", query="select t from Time t where t.id.rodada = :rodada")})
public class Time implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		@EmbeddedId
		private TimeId id;
	
		private String nome;
		private String slug;
		private String nome_cartola;
		private Double pontuacao;
		private String img_escudo_32x32;
		private Double patrimonio;
		
		@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
		@JoinTable(
			      name="TIME_ATLETA",
			      joinColumns={@JoinColumn(name="ID_TIME", referencedColumnName="ID"), @JoinColumn(name="RODADA_TIME", referencedColumnName="RODADA")},
			      inverseJoinColumns={@JoinColumn(name="ID_ATLETA", referencedColumnName="IDLOCAL")})
		private List<Atleta> atletas = new ArrayList<Atleta>();
		
		@Transient
		private int qtdAtletasIguais;
		@Transient
		private int qtdAtletasExclusivos;

		

		public Time(){
			
		}
		
		
		public String getNome() {
			return nome;
		}



		public void setNome(String nome) {
			this.nome = nome;
		}



		public String getNome_cartola() {
			return nome_cartola;
		}



		public void setNome_cartola(String nome_cartola) {
			this.nome_cartola = nome_cartola;
		}



		public Double getPontuacao() {
			return pontuacao;
		}



		public void setPontuacao(Double pontuacao) {
			this.pontuacao = pontuacao;
		}



		public String getImg_escudo_32x32() {
			return img_escudo_32x32;
		}



		public void setImg_escudo_32x32(String img_escudo_32x32) {
			this.img_escudo_32x32 = img_escudo_32x32;
		}


		

		public List<Atleta> getAtletas() {
			return atletas;
		}


		public void setAtletas(List<Atleta> atletas) {
			this.atletas = atletas;
		}

		

		public String getSlug() {
			return slug;
		}


		public void setSlug(String slug) {
			this.slug = slug;
		}
		
		


		public int getQtdAtletasIguais() {
			return qtdAtletasIguais;
		}


		public void setQtdAtletasIguais(int qtdAtletasIguais) {
			this.qtdAtletasIguais = qtdAtletasIguais;
		}
		
		
	

		public int getQtdAtletasExclusivos() {
			qtdAtletasExclusivos=0;
			for(Atleta a: atletas){
				if(a.getQtdAtletasIguais()==0){
					qtdAtletasExclusivos++;
				}
			}
			return qtdAtletasExclusivos;
		}


		

		public TimeId getId() {
			return id;
		}


		public void setId(TimeId id) {
			this.id = id;
		}
		
		


		public Double getPatrimonio() {
			return patrimonio;
		}


		public void setPatrimonio(Double patrimonio) {
			this.patrimonio = patrimonio;
		}


		@Override
		public String toString() {
			return nome +"(" +nome_cartola+") - "+pontuacao;
		}
		


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((atletas == null) ? 0 : atletas.hashCode());
			result = prime
					* result
					+ ((img_escudo_32x32 == null) ? 0 : img_escudo_32x32
							.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			result = prime * result
					+ ((nome_cartola == null) ? 0 : nome_cartola.hashCode());
			result = prime * result
					+ ((pontuacao == null) ? 0 : pontuacao.hashCode());
			result = prime * result + ((slug == null) ? 0 : slug.hashCode());
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
			Time other = (Time) obj;
			if (atletas == null) {
				if (other.atletas != null)
					return false;
			} else if (!atletas.equals(other.atletas))
				return false;
			if (img_escudo_32x32 == null) {
				if (other.img_escudo_32x32 != null)
					return false;
			} else if (!img_escudo_32x32.equals(other.img_escudo_32x32))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			if (nome_cartola == null) {
				if (other.nome_cartola != null)
					return false;
			} else if (!nome_cartola.equals(other.nome_cartola))
				return false;
			if (pontuacao == null) {
				if (other.pontuacao != null)
					return false;
			} else if (!pontuacao.equals(other.pontuacao))
				return false;
			if (slug == null) {
				if (other.slug != null)
					return false;
			} else if (!slug.equals(other.slug))
				return false;
			return true;
		}
		
		

		
	
}
