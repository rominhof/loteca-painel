package cartolafc.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Clube implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long idLocal;
		
		public Long getIdLocal() {
			return idLocal;
		}

		public void setIdLocal(Long idLocal) {
			this.idLocal = idLocal;
		}

		private Integer id;
		private String abreviacao;
		private String slug;

		public Clube(){
			
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getAbreviacao() {
			return abreviacao;
		}

		public void setAbreviacao(String abreviacao) {
			this.abreviacao = abreviacao;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}


		
		

		
	
}
