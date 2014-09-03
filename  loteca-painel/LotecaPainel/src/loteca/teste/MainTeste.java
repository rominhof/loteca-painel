package loteca.teste;

import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.util.JPAUtil;
import loteca.util.LotecaUtil;



public class MainTeste {

	public static void main (String args[]){
		/**
		//ParciaisUtil.baixarTodosArquivosAtualizados();
		
		String paginaToda = HttpUtil.conteudoPagina("http://globoesporte.globo.com/cartola-fc/");
		
		String jogosDaRodadaHtml = paginaToda.split("<div id=\"jogos-rodada\">")[1].split("<div id=\"top-maiores-pontuadores\">")[0];
			
		System.out.println(jogosDaRodadaHtml);
		**/
		
		//JPAUtil.getEntityManager();

		//ParciaisUtil pa = new ParciaisUtil();
		
		//pa.baixarArquivosAtualizados(Times.PICAPAUSFC);
		
		/**LotecaUtil lu = new LotecaUtil();
		
		Loteca loteca = lu.getLotecaAtual();
		
		System.out.println("Concurso numero "+loteca.getNumConcurso());
		System.out.println("JOGOS: ");
		for(Partida p: loteca.getPartidas()){
			System.out.println(p.getSequencialJogo()+" - "+p.getTime1().getNome().trim() +" x " +p.getTime2().getNome().trim());
		}**/
		
		JPAUtil.getEntityManager();

		
	}
	
}
