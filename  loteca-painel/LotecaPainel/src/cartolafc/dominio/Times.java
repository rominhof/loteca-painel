package cartolafc.dominio;

public enum Times {

	BREGAFC("Tércio","BREGA FC","http://api.cartola.globo.com/time_adv/brega--fc.json","brega--fc"),
	PICAPAUSFC("Luiz Sérgio","PicaPaus.F.C","http://api.cartola.globo.com/time_adv/picapausfc.json","picapausfc"),
	RMRFC("Rodrigo Medeiros","RMRFC","http://api.cartola.globo.com/time_adv/rmrfc.json","rmrfc"),
	AINEGAFC("Allex","AI NEGA F.C","http://api.cartola.globo.com/time_adv/ai-nega-fc.json","ai-nega-fc"),
	BRITOJRTEAM("Marcello","Brito Jr Team", "http://api.cartola.globo.com/time_adv/brito-jr-team.json","brito-jr-team"),
	MELUCOSSOCCER("Gabriel","Meluco*s Soccer","http://api.cartola.globo.com/time_adv/melucos-soccer.json","melucos-soccer"),
	SANTOSCRATOFC("Michel","Santos Crato FC.","http://api.cartola.globo.com/time_adv/santos-crato-fc.json","santos-crato-fc"),
	AGFC("André","AG F. C.","http://api.cartola.globo.com/time_adv/ag-f-c.json","ag-f-c"),
	TIOXICO("Felipe","TioXico", "http://api.cartola.globo.com/time_adv/tioxico.json","tioxico"),
	DUDUSFC("Eduardo Dudu","dudusFC", "http://api.cartola.globo.com/time_adv/dudusfc.json","dudusfc"),
	PRISONWITHOUTBARS("Eduardo Bilu","Prison Without Bars","http://api.cartola.globo.com/time_adv/prison-without-bars.json","prison-without-bars");

	String dono;
	String nome;
	String url;
	String slug;
	
	Times(String dono, String nome, String url, String slug){
		this.dono=dono;
		this.nome=nome;
		this.url=url;
		this.slug=slug;
	}

	public String getDono() {
		return dono;
	}

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return url;
	}

	public String getSlug() {
		return slug;
	}
	
	
	
}
