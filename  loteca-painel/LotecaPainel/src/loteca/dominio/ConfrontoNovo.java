package loteca.dominio;

public class ConfrontoNovo {

	private String id_ano;
	private String id_fase;

	private String id_news_comentario;
	private String news_comentario;
	private String grupo;
	private String nome_grupo;
	private String nome_fase;
	private String rodada;

	private Tabela[] tabela;

	public String getId_ano() {
		return id_ano;
	}

	public void setId_ano(String id_ano) {
		this.id_ano = id_ano;
	}

	public String getId_fase() {
		return id_fase;
	}

	public void setId_fase(String id_fase) {
		this.id_fase = id_fase;
	}

	public Tabela[] getTabela() {
		return tabela;
	}

	public void setTabela(Tabela[] tabela) {
		this.tabela = tabela;
	}

	public String getId_news_comentario() {
		return id_news_comentario;
	}

	public void setId_news_comentario(String id_news_comentario) {
		this.id_news_comentario = id_news_comentario;
	}

	public String getNews_comentario() {
		return news_comentario;
	}

	public void setNews_comentario(String news_comentario) {
		this.news_comentario = news_comentario;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNome_grupo() {
		return nome_grupo;
	}

	public void setNome_grupo(String nome_grupo) {
		this.nome_grupo = nome_grupo;
	}

	public String getNome_fase() {
		return nome_fase;
	}

	public void setNome_fase(String nome_fase) {
		this.nome_fase = nome_fase;
	}

	public String getRodada() {
		return rodada;
	}

	public void setRodada(String rodada) {
		this.rodada = rodada;
	}

}