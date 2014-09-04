package loteca.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loteca.dominio.CampeonatoEnum;
import loteca.dominio.Cartela;
import loteca.dominio.ConfrontoNovo;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.dominio.Resultado;
import loteca.dominio.StatusJogo;
import loteca.dominio.Tabela;
import loteca.dominio.Time;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static final String filePath = "E:\\Projetos\\Loteca\\ loteca-painel\\exemplo.json";
	private static final String file = "exemplo.json";
	private static final String JSON_URL_BASILEIRO_SERIE_A = "http://www.futebolinterior.com.br/gerados/placar_445.json";
	private static final String JSON_URL_BASILEIRO_SERIE_B = "http://www.futebolinterior.com.br/gerados/placar_445.json";
	private static final String JSON_URL_BASILEIRO_SERIE_C = "http://www.futebolinterior.com.br/gerados/placar_445.json";
	private static final String JSON_URL_BASILEIRO_SERIE_D = "http://www.futebolinterior.com.br/gerados/placar_445.json";

	private static Map<CampeonatoEnum, ConfrontoNovo> confrontos = new HashMap<CampeonatoEnum, ConfrontoNovo>();
	private static Map<CampeonatoEnum, String> dadosJogos = new HashMap<CampeonatoEnum, String>();

	static {
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_A,
				JSON_URL_BASILEIRO_SERIE_A);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_B,
				JSON_URL_BASILEIRO_SERIE_B);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_C,
				JSON_URL_BASILEIRO_SERIE_C);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_D,
				JSON_URL_BASILEIRO_SERIE_D);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Inicio - Processo de atualização de cartelas");
		// Baixar Json
		System.out.println("Baixar Json e comparar");
		atualizarJson();

		// popular loteca - gabarito
		Loteca loteca = new JsonUtil().getCurrentLoteca();
		// Se a loteca já estiver fechada, não será necessário proceso de
		// atualização
		if (loteca.getFinalizado()) {
			return;
		}

		System.out.println("Popular gabarito");
		ConfrontoNovo confronto = parserJson(filePath);
		new JsonUtil().popularLoteria(loteca, confronto);

		// Comparar as cartelas
		new JsonUtil().compararCartelas();
		System.out.println("Comparar cartelas");

		// Verificar se loteria pode ser encerranda
		new JsonUtil().verificarLotecaEncerrada(loteca);

		System.out.println("Fim - Processo de atualização de cartelas");
	}

	private static void atualizarJson() throws NoSuchAlgorithmException {
		for (CampeonatoEnum campeontado : dadosJogos.keySet()) {
			String conteudoSite = HttpUtil.conteudoPagina(dadosJogos
					.get(campeontado));

			// TODO - Ler conteudo local
			String conteudoLocal = HttpUtil.conteudoPagina(dadosJogos
					.get(campeontado));

			String hashConteudoSite = getHashFromString(conteudoSite);
			String hashConteudoLocal = getHashFromString(conteudoLocal);

			if (!hashConteudoSite.equals(hashConteudoLocal)) {
				// TODO filePath para cada arquivo
				HttpUtil.downloadFile(dadosJogos.get(campeontado), filePath);
				ConfrontoNovo confronto = parserJson(filePath);

				confrontos.put(campeontado, confronto);
			}
		}

	}

	private static String getHashFromString(String conteudoSite)
			throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(conteudoSite.getBytes());
		String encryptedString = new String(messageDigest.digest());

		return encryptedString;
	}

	private void verificarLotecaEncerrada(Loteca loteca) {
		boolean encerrado = true;
		for (Partida partida : loteca.getPartidas()) {
			if (partida.getStatusJogo() == StatusJogo.EM_ANDAMENTO) {
				encerrado = false;
				break;
			}
		}

		if (encerrado) {
			loteca.setFinalizado(true);
			// TODO atualizar loteca no DAO
		}

	}

	private void popularLoteria(Loteca loteca, ConfrontoNovo confronto)
			throws Exception {
		// Percorrer cada jogo da loteca em busca dos resultados
		for (Partida partida : loteca.getPartidas()) {
			// Atualizar apenas os jogos em andamento
			if (partida.getStatusJogo() == StatusJogo.EM_ANDAMENTO) {
				// Recuperar o jogo no objeto Json de acordo com o confronto da
				// loteca
				Jogo jogo = getJogoFromConfronto(
						partida.getTime1(),
						partida.getTime2(),
						getConfrontoDeAcordoComCampeonato(partida.getTime1(),
								partida.getTime2()));

				if (jogo == null) {
					throw new Exception("Jogo não encontrado!");
				}

				// Atualizar apenas as partidas em andamento
				partida.setGolTime1(jogo.getGolsTime1());
				partida.setGolTime2(jogo.getGolsTime2());

				Resultado resultadoParcial = Resultado.COLUNA_1;
				if (partida.getGolTime1().equals(partida.getGolTime2())) {
					resultadoParcial = Resultado.COLUNA_X;
				} else if (partida.getGolTime1() < partida.getGolTime2()) {
					resultadoParcial = Resultado.COLUNA_2;
				}
				partida.setResultado(resultadoParcial);
			}
		}

		// Atualizar o object Loteca
		// update(loteca)
		// TODO
	}

	private ConfrontoNovo getConfrontoDeAcordoComCampeonato(Time time1,
			Time time2) {
		ConfrontoNovo retorno = null;
		for (CampeonatoEnum campeonato : confrontos.keySet()) {
			if (time1.getCampeonato().contains(campeonato)
					&& time2.getCampeonato().contains(campeonato)) {
				retorno = confrontos.get(campeonato);
				break;
			}
		}
		return retorno;
	}

	/**
	 * Recuperar o jogo atual de acordo com os times desejados.
	 * 
	 * @param time1
	 *            Time1 no confronto
	 * @param time2
	 *            Time2 no confronto
	 * @param confronto
	 *            todos os confrontos disponiveis
	 * @return O jogo atual de acordo com os times desejados
	 */
	private Jogo getJogoFromConfronto(Time time1, Time time2,
			ConfrontoNovo confronto) {
		Jogo retorno = null;
		for (Tabela tabela : confronto.getTabela()) {
			if (tabela.getMandante().equals(time1.getNome())
					&& tabela.getVisitante().equals(time2.getNome())) {
				retorno = new Jogo();
				retorno.setTime1(time1);
				retorno.setTime2(time2);
				retorno.setGolsTime1(Integer.parseInt(tabela.getPtn_mandante()));
				retorno.setGolsTime2(Integer.parseInt(tabela.getPtn_visitante()));
				break;
			}
		}
		return retorno;
	}

	private Loteca getCurrentLoteca() {
		// TODO
		return null;
	}

	private void compararCartelas() {
		// Recuperar o objeto referente ao concurso da loteca atual
		Loteca loteca = getCurrentLoteca();

		List<Cartela> cartelas = getCartelaDaLoteca(loteca.getNumConcurso());

		for (Cartela cartela : cartelas) {
			for (Palpite palpite : cartela.getPalpites()) {
				Partida particaPalpite = palpite.getPartida();
				Partida particaLoteca = getPartidaLoteca(loteca,
						particaPalpite.getTime1(), particaPalpite.getTime2());

				boolean acerto = false;
				if (particaLoteca.getResultado() == Resultado.COLUNA_1
						&& palpite.getC1()) {
					acerto = true;
				} else if (particaLoteca.getResultado() == Resultado.COLUNA_2
						&& palpite.getC2()) {
					acerto = true;
				} else if (particaLoteca.getResultado() == Resultado.COLUNA_X
						&& palpite.getCx()) {
					acerto = true;
				}
				palpite.setAcerto(acerto);
				palpite.setResultado(particaLoteca.getResultado());
			}
		}
	}

	private List<Cartela> getCartelaDaLoteca(Integer numConcurso) {
		// TODO Auto-generated method stub
		return null;
	}

	private Partida getPartidaLoteca(Loteca loteca, Time time1, Time time2) {
		// TODO
		return null;
	}

	public static ConfrontoNovo parserJson(String jsonFilePath) {

		Reader reader;
		ConfrontoNovo confronto = null;
		try {
			reader = new InputStreamReader(
					JsonUtil.class.getResourceAsStream("exemplo.json"), "UTF-8");

			Gson gson = new GsonBuilder().create();
			ConfrontoNovo[] listConfrontos = gson.fromJson(reader,
					ConfrontoNovo[].class);
			confronto = listConfrontos[0];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return confronto;
	}

	class Jogo {

		private Time time1;
		private Time time2;
		private int golsTime1;
		private int golsTime2;

		public Jogo() {
			// Vazio
		}

		public Time getTime1() {
			return time1;
		}

		public void setTime1(Time time1) {
			this.time1 = time1;
		}

		public Time getTime2() {
			return time2;
		}

		public void setTime2(Time time2) {
			this.time2 = time2;
		}

		public int getGolsTime1() {
			return golsTime1;
		}

		public void setGolsTime1(int golsTime1) {
			this.golsTime1 = golsTime1;
		}

		public int getGolsTime2() {
			return golsTime2;
		}

		public void setGolsTime2(int golsTime2) {
			this.golsTime2 = golsTime2;
		}

	}
}
