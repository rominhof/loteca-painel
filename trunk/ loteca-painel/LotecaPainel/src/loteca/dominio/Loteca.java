package loteca.dominio;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Loteca implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer numConcurso;
	private Integer sequencialJogo;
	private Time time1;
	private Time time2;
	private Integer golTime1;
	private Integer golTime2;
	//ENUM
	private Resultado resultado;
	//ENUM
	private StatusJogo statusJogo;

	
	
}
