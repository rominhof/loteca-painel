package loteca.dominio;

import java.io.Serializable;


public enum CampeonatoEnum implements Serializable {

	BRASILEIRO_SERIE_A("Brasileiro Serie A"), 
	BRASILEIRO_SERIE_B("Brasileiro Serie B"), 
	BRASILEIRO_SERIE_C("Brasileiro Serie C"),
	BRASILEIRO_SERIE_D("Brasileiro Serie D"),
	BRASILEIRO_SERIE_A_FEMININO("Brasileiro Serie A Feminino");
	
	
	private String nome;
	CampeonatoEnum(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
}
