package loteca.dominio;

public enum StatusJogo {
	
	AGENDADO,
	/*TODO:Conferir quando um jogo em andamento
	   N�o sei o status real correspondente ao JSON do Futebol Interior para este caso. */
	EM_ANDAMENTO,
	INTERVALO,
	FINALIZADO,
	OUTRO;

}
