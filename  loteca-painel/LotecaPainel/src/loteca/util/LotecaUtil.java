package loteca.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import loteca.dominio.Campeonato;
import loteca.dominio.Loteca;
import loteca.dominio.Partida;
import loteca.dominio.Time;

public class LotecaUtil {

	private static final String URL_PROXIMO_CONCURSO_CAIXA = "http://www1.caixa.gov.br/loterias/loterias/loteca/loteca_programacao_new.asp";
	private static final String URL_FI_JSON = "";

	private String getHtmlLotecaCaixaProximoConcurso() {
		return HttpUtil.conteudoPagina(URL_PROXIMO_CONCURSO_CAIXA);

	}

	public void baixarTodosArquivosAtualizados(String path) {
		/**
		 * for(Campeonato camp: campeonatos){
		 * baixarArquivosAtualizados(timeEnum, path); }
		 */
	}

	public void baixarArquivosAtualizados(Campeonato camp, String path) {
		String timeJson = HttpUtil.conteudoPagina(URL_FI_JSON
				+ camp.getId_ano());
		System.out.println(timeJson);

		BufferedWriter br;

		/**
		 * try { //br = new BufferedWriter(new FileWriter(new
		 * File(path+"json/"+timeEnum.getSlug()+".json")));
		 * 
		 * //System.out.println("gravando arquivo em: "+path+"json/"+timeEnum.
		 * getSlug());
		 * 
		 * // br.write(timeJson); // br.close(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public Loteca getLotecaAtualOficialCaixa() {
		String htmlLotecaCaixa = getHtmlLotecaCaixaProximoConcurso();
		String[] confrontos = htmlLotecaCaixa
				.split("<td class=\"times_coluna1\">");
		Loteca loteca = new Loteca();
		loteca.setNumConcurso(Integer.parseInt(htmlLotecaCaixa
				.split("<span class=\"txt_nr_concurso\">")[1].split("</span>")[0]));
		List<Partida> partidas = new ArrayList<Partida>();
		int seq = 0;
		for (String time : confrontos) {
			if (time.contains("<td class=\"times_coluna2\">")) {
				String[] times = time.split("<td class=\"times_coluna2\">");
				Partida partida = new Partida();
				partida.setSequencialJogo(++seq);
				partida.setTime1(new Time(times[0].split("</td>")[0].trim()));
				partida.setTime2(new Time(times[1].split("</td>")[0].trim()));
				partidas.add(partida);
			}
		}

		loteca.setPartidas(partidas);
		return loteca;

	}

	public void baixarJsonParaPastaLocal(String conteudo, String filename) {
		FileWriter writer;
		try {
			writer = new FileWriter(filename, false);

			writer.write(conteudo);
			writer.close();
		} catch (IOException e1) {
			System.out.println("error");
		}
	}

	public String getHashFromString(String conteudoSite)
			throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(conteudoSite.getBytes());
		String encryptedString = new String(messageDigest.digest());

		return encryptedString;
	}

//	public String lerConteudoJson(String filename) throws Exception {
//		String retorno = "";
//		InputStream conteudoStream;
//		try {
//			File file = new File(filename);
//			if (file.exists()) {
//				conteudoStream = new FileInputStream(file);
//
//				retorno = HttpUtil.lerConteudoStream(conteudoStream);
//			}
//		} catch (IOException e) {
//			throw new Exception("Erro ao ler conteudo");
//		}
//		return retorno;
//	}
}
