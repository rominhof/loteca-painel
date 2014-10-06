package loteca.job;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loteca.dominio.Campeonato;
import loteca.dominio.CampeonatoEnum;
import loteca.dominio.Cartela;
import loteca.dominio.ChanceCartelaEnum;
import loteca.dominio.ConfrontoNovo;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.dominio.Resultado;
import loteca.dominio.StatusJogo;
import loteca.dominio.Tabela;
import loteca.dominio.Time;
import loteca.service.CartelaService;
import loteca.service.LotecaService;
import loteca.service.TimeService;
import loteca.util.HttpUtil;
import loteca.util.LotecaUtil;
import loteca.util.MessageUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PalpiteJob implements Job {
	private static final String SYSTEM_PREFIX = "LOTECA::";

	private static final String STATUS_JOGO_FINALIZADO = "Finalizado";
	private static final String STATUS_JOGO_AGENDADO = "Agendado";
	private static final String STATUS_JOGO_ANDAMENTO = "Em Andamento";
	private static final String STATUS_JOGO_ANDAMENTO_1_TEMPO = "1º Tempo";
	private static final String STATUS_JOGO_ANDAMENTO_2_TEMPO = "2º Tempo";

	private static final String STATUS_JOGO_INTERVALO = "Intervalo";

	private LotecaUtil lotecaUtil;

	private static Map<CampeonatoEnum, ConfrontoNovo> confrontos = new HashMap<CampeonatoEnum, ConfrontoNovo>();
	private static Map<CampeonatoEnum, String> dadosJogos = new HashMap<CampeonatoEnum, String>();
	private static Map<CampeonatoEnum, String> arquivosJson = new HashMap<CampeonatoEnum, String>();

	private static final String JSON_URL_BASILEIRO_SERIE_A = MessageUtil
			.getInstance().getValue("url.json.serie.a");
	private static final String JSON_URL_BASILEIRO_SERIE_B = MessageUtil
			.getInstance().getValue("url.json.serie.b");
	private static final String JSON_URL_BASILEIRO_SERIE_C = MessageUtil
			.getInstance().getValue("url.json.serie.c");
	private static final String JSON_URL_BASILEIRO_SERIE_D = MessageUtil
			.getInstance().getValue("url.json.serie.d");

	private static final String PATH_PASTA_JSON = MessageUtil.getInstance()
			.getValue("pasta.json");

	private static final String JSON_ARQUIVO_BASILEIRO_SERIE_A = MessageUtil
			.getInstance().getValue("arquivo.serie.a");
	private static final String JSON_ARQUIVO_BASILEIRO_SERIE_B = MessageUtil
			.getInstance().getValue("arquivo.serie.b");
	private static final String JSON_ARQUIVO_BASILEIRO_SERIE_C = MessageUtil
			.getInstance().getValue("arquivo.serie.c");
	private static final String JSON_ARQUIVO_BASILEIRO_SERIE_D = MessageUtil
			.getInstance().getValue("arquivo.serie.d");

	private LotecaService lotecaService = null;
	private CartelaService cartelaService = null;
	private static TimeService timeService = new TimeService();

	static {
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_A,
				JSON_URL_BASILEIRO_SERIE_A);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_B,
				JSON_URL_BASILEIRO_SERIE_B);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_C,
				JSON_URL_BASILEIRO_SERIE_C);
		dadosJogos.put(CampeonatoEnum.BRASILEIRO_SERIE_D,
				JSON_URL_BASILEIRO_SERIE_D);

		arquivosJson.put(CampeonatoEnum.BRASILEIRO_SERIE_A,
				JSON_ARQUIVO_BASILEIRO_SERIE_A);
		arquivosJson.put(CampeonatoEnum.BRASILEIRO_SERIE_B,
				JSON_ARQUIVO_BASILEIRO_SERIE_B);
		arquivosJson.put(CampeonatoEnum.BRASILEIRO_SERIE_C,
				JSON_ARQUIVO_BASILEIRO_SERIE_C);
		arquivosJson.put(CampeonatoEnum.BRASILEIRO_SERIE_D,
				JSON_ARQUIVO_BASILEIRO_SERIE_D);
	}

	@Override
	public void execute(final JobExecutionContext ctx)
			throws JobExecutionException {
		System.out.println(SYSTEM_PREFIX
				+ "Iniciando execução do Job PalpiteJob::"
				+ System.currentTimeMillis());

		System.out.println(SYSTEM_PREFIX + "Recuperando a Loteca atual");
		Loteca lotecaAtual = getLotecaService().carregaLotecaAtual();

		if (lotecaAtual == null) {
			System.out
					.println("Loteria finalizada, aguardando próximo concurso");
			return;
		}

		try {
			// Atualizar arquivos Json
			// Realizar verificar apenas se os arquivos Json forem diferentes da
			// ultima versão baixada
			System.out.println(SYSTEM_PREFIX + "Atualizando arquivos Json");
			if (atualizarJson()) {
				// Remover Confrontos que não estão na loteca
				removerConfrontosForaDaLoteca(confrontos, lotecaAtual);

				// Popular a loteca de acordo com os resultados de momento
				System.out.println(SYSTEM_PREFIX
						+ "Populando loteria com resultado dos jogos");
				popularLoteria(lotecaAtual);

				// Comparar a loteca atualizada com as cartelas jogadas
				System.out
						.println("Atualizando as cartelas de jogos de acordo com o resultado de momento");
				compararCartelas(lotecaAtual);

				// Verificar se loteria foi encerrada
				System.out.println(SYSTEM_PREFIX
						+ "Checar se todos os jogos foram encerados");
				verificarLotecaEncerrada(lotecaAtual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Ocorreu erro na execução do Job PalpiteJob");
		}

		System.out.println(SYSTEM_PREFIX
				+ "Fim da execução do Job PalpiteJob::"
				+ System.currentTimeMillis());
	}

	private void removerConfrontosForaDaLoteca(
			Map<CampeonatoEnum, ConfrontoNovo> confrontos, Loteca lotecaAtual) {
		for (CampeonatoEnum campeonato : confrontos.keySet()) {
			ConfrontoNovo confronto = confrontos.get(campeonato);
			List<Tabela> jogos = new ArrayList<Tabela>();
			for (int i = confronto.getTabela().length - 1; i >= 0; i--) {
				if (temConfrontoNaCartela(confronto.getTabela()[i], lotecaAtual)) {
					jogos.add(confronto.getTabela()[i]);
				}
			}
			confronto.setTabela(jogos.toArray(new Tabela[jogos.size()]));
		}

	}

	private boolean temConfrontoNaCartela(Tabela tabela, Loteca lotecaAtual) {
		boolean toReturn = false;
		boolean erro = false;
		Time timeMandante = timeService.consultaTimePorNome(tabela
				.getMandante().replace('-', '/'));
		Time timeVisitante = timeService.consultaTimePorNome(tabela
				.getVisitante().replace('-', '/'));

		if (timeMandante == null) {
			System.out.println("Time não localizado:" + tabela.getMandante());
			erro = true;
		}

		if (timeVisitante == null) {
			System.out.println("Time não localizado:" + tabela.getVisitante());
			erro = true;
		}

		if (!erro) {
			for (Partida partida : lotecaAtual.getPartidas()) {
				if (partida.getTime1().getNome().equals(timeMandante.getNome())
						&& partida.getTime2().getNome()
								.equals(timeVisitante.getNome())) {
					toReturn = true;
					break;
				}
			}
		}
		return toReturn;
	}

	private LotecaUtil getLotecaUtil() {
		if (this.lotecaUtil == null) {
			lotecaUtil = new LotecaUtil();
		}
		return lotecaUtil;
	}

	private LotecaService getLotecaService() {
		if (this.lotecaService == null) {
			lotecaService = new LotecaService();
		}
		return lotecaService;
	}

	private CartelaService getCartelaService() {
		if (this.cartelaService == null) {
			cartelaService = new CartelaService();
		}
		return cartelaService;
	}

	private void verificarLotecaEncerrada(Loteca loteca) {
		boolean encerrado = true;
		for (Partida partida : loteca.getPartidas()) {
			if (partida.getStatusJogo() != StatusJogo.FINALIZADO) {
				encerrado = false;
				break;
			}
		}

		if (encerrado) {
			loteca.setFinalizado(true);
			getLotecaService().atualizarLoteca(loteca);
		}

	}

	private void popularLoteria(Loteca loteca) throws Exception {
		// Percorrer cada jogo da loteca em busca dos resultados
		int sequencia = 0;
		for (Partida partida : loteca.getPartidas()) {
			sequencia++;
			System.out.println(SYSTEM_PREFIX + "Populando jogo loteca = "
					+ sequencia);
			// Atualizar apenas os jogos em andamento
			if (partida.getStatusJogo() != StatusJogo.FINALIZADO) {
				// Recuperar o jogo no objeto Json de acordo com o confronto da
				// loteca
				Jogo jogo = getJogoFromConfronto(
						partida.getTime1(),
						partida.getTime2(),
						getConfrontoDeAcordoComCampeonato(partida.getTime1(),
								partida.getTime2()));

				// Se o jogo for encontrado, buscar pelo seu resultado
				if (jogo != null) {
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
					partida.setStatusJogo(jogo.getStatusJogo());

					// Atualizar valor da partida
					getLotecaService().atualizarPartida(partida);
				}
			}
		}
	}

	private boolean timeParticidaDeCampeonato(Time time,
			CampeonatoEnum campeonatoEnum) {
		boolean retorno = false;
		for (Campeonato campeonato : time.getCampeonato()) {
			if (campeonato.getNome().equals(campeonatoEnum.getNome())) {
				retorno = true;
				break;
			}
		}

		return retorno;
	}

	private ConfrontoNovo getConfrontoDeAcordoComCampeonato(Time time1,
			Time time2) {
		ConfrontoNovo retorno = null;
		for (CampeonatoEnum campeonato : confrontos.keySet()) {
			if (timeParticidaDeCampeonato(time1, campeonato)
					&& timeParticidaDeCampeonato(time2, campeonato)) {
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
	 * @throws Exception
	 */
	private Jogo getJogoFromConfronto(Time time1, Time time2,
			ConfrontoNovo confronto) throws Exception {
		Jogo retorno = null;
		if (confronto != null) {
			for (Tabela tabela : confronto.getTabela()) {
				Time timeMandante = timeService.consultaTimePorNome(tabela
						.getMandante().replace('-', '/'));
				Time timeVisitante = timeService.consultaTimePorNome(tabela
						.getVisitante().replace('-', '/'));

				if (timeMandante == null) {
					throw new Exception(
							"Não foi localizado na base o time mandante: "
									+ tabela.getMandante());
				}

				if (timeVisitante == null) {
					throw new Exception(
							"Não foi localizado na base o time visitante: "
									+ tabela.getVisitante());
				}

				if (timeMandante.getNome().equals(time1.getNome())
						&& timeVisitante.getNome().equals(time2.getNome())) {
					retorno = new Jogo();
					retorno.setTime1(time1);
					retorno.setTime2(time2);
					retorno.setGolsTime1(Integer.parseInt(tabela
							.getPtn_mandante()));
					retorno.setGolsTime2(Integer.parseInt(tabela
							.getPtn_visitante()));

					if (tabela.getStatus() != null
							&& tabela.getStatus()
									.equals(STATUS_JOGO_FINALIZADO)) {
						retorno.setStatusJogo(StatusJogo.FINALIZADO);
					} else if (tabela.getStatus() != null
							&& tabela.getStatus().equals(STATUS_JOGO_AGENDADO)) {
						retorno.setStatusJogo(StatusJogo.AGENDADO);
					} else if (tabela.getStatus() != null
							&& (tabela.getStatus()
									.equals(STATUS_JOGO_ANDAMENTO)
									|| tabela.getStatus().equals(
											STATUS_JOGO_ANDAMENTO_1_TEMPO) || tabela
									.getStatus().equals(
											STATUS_JOGO_ANDAMENTO_2_TEMPO))) {
						retorno.setStatusJogo(StatusJogo.EM_ANDAMENTO);
					} else if (tabela.getStatus() != null
							&& tabela.getStatus().equals(STATUS_JOGO_INTERVALO)) {
						retorno.setStatusJogo(StatusJogo.INTERVALO);
					} else {
						retorno.setStatusJogo(StatusJogo.OUTRO);
					}

					break;
				}
			}
		}
		return retorno;
	}

	private void compararCartelas(Loteca lotecaAtual) {
		List<Cartela> cartelas = getCartelaService().carregaCartelasDeConcurso(
				lotecaAtual.getNumConcurso());

		int sequencia = 0;
		System.out.println(SYSTEM_PREFIX + "Total de " + cartelas.size()
				+ " cartelas a serem comparadas");

		for (Cartela cartela : cartelas) {
			sequencia++;
			System.out.println(SYSTEM_PREFIX + "Comparando cartela = "
					+ sequencia);
			int erros = 0;
			int acertos = 0;
			int sequenciaPalpite = 0;
			for (Palpite palpite : cartela.getPalpites()) {
				sequenciaPalpite++;
				System.out.println(SYSTEM_PREFIX + "Comparando palpite = "
						+ sequenciaPalpite + " da cartela = " + sequencia);
				Partida particaPalpite = palpite.getPartida();
				Partida particaGabarito = getPartidaLoteca(lotecaAtual,
						particaPalpite.getTime1(), particaPalpite.getTime2());

				if (particaGabarito != null
						&& (particaGabarito.getStatusJogo() == StatusJogo.EM_ANDAMENTO
								|| particaGabarito.getStatusJogo() == StatusJogo.INTERVALO || (particaGabarito
								.getStatusJogo() == StatusJogo.FINALIZADO && !palpite
								.isJogoFinalizado()))) {
					boolean acerto = false;
					boolean jogoFinalizado = false;
					if (particaGabarito.getResultado() == Resultado.COLUNA_1
							&& palpite.getC1()) {
						acerto = true;
					} else if (particaGabarito.getResultado() == Resultado.COLUNA_2
							&& palpite.getC2()) {
						acerto = true;
					} else if (particaGabarito.getResultado() == Resultado.COLUNA_X
							&& palpite.getCx()) {
						acerto = true;
					}

					// Atualizar o contador de acertos e erros
					if (acerto) {
						acertos++;
					} else {
						erros++;
					}

					// Checar se o jogo foi finalizado
					if (particaGabarito.getStatusJogo() == StatusJogo.FINALIZADO) {
						System.out.println(SYSTEM_PREFIX
								+ "Palpite finalizado!");
						jogoFinalizado = true;
					}

					palpite.setJogoFinalizado(jogoFinalizado);
					palpite.setAcerto(acerto);
					palpite.setResultado(particaGabarito.getResultado());

					// Atuaizar dados do palpite
					getCartelaService().atualizarPalpite(palpite);
				} else if (palpite.isJogoFinalizado()) {
					if (palpite.getAcerto()) {
						acertos++;
					} else {
						erros++;
					}
				}
			}
			cartela.setQtdAcertos(acertos);
			cartela.setChance(erros == 0 ? ChanceCartelaEnum.CHANCES_14
					: (erros == 1 ? ChanceCartelaEnum.CHANCES_13
							: ChanceCartelaEnum.SEM_CHANCES));
			getCartelaService().atualizarCartela(cartela);
		}
	}

	private Partida getPartidaLoteca(Loteca lotecaAtual, Time time1, Time time2) {
		Partida partidaLoteca = null;
		for (Partida partida : lotecaAtual.getPartidas()) {
			if (partida.getTime1().getNome().equals(time1.getNome())
					&& partida.getTime2().getNome().equals(time2.getNome())) {
				partidaLoteca = partida;
				break;
			}
		}
		return partidaLoteca;
	}

	private boolean atualizarJson() throws Exception {
		boolean retorno = false;
		for (CampeonatoEnum campeonato : dadosJogos.keySet()) {
			System.out
					.println(SYSTEM_PREFIX + "Verificando Json do campeonato: "
							+ campeonato.getNome());
			String pathArquivo = PATH_PASTA_JSON;
			if (pathArquivo.endsWith("/")) {
				pathArquivo += arquivosJson.get(campeonato);
			} else {
				pathArquivo += "/" + arquivosJson.get(campeonato);
			}
			System.out.println(SYSTEM_PREFIX
					+ "Salvando arquivos Json na pasta: " + pathArquivo);

			String conteudoLocal = getLotecaUtil().lerConteudoJson(pathArquivo);
			String conteudoSite = HttpUtil.conteudoPagina(dadosJogos
					.get(campeonato));

			if (conteudoLocal == null || conteudoLocal.isEmpty()) {
				if (conteudoSite != null && !conteudoSite.isEmpty()) {
					System.out.println(SYSTEM_PREFIX
							+ "Não existe arquivo local, baixando");
					// Copiar o conteudo do Json para pasta local
					getLotecaUtil().salvarJsonParaPastaLocal(conteudoSite,
							pathArquivo);
					atualizarConfronto(campeonato, conteudoSite);
					retorno = true;
				}
			} else {
				System.out.println(SYSTEM_PREFIX + "Arquivo local localizado");
				String hashConteudoSite = getLotecaUtil().getHashFromString(
						conteudoSite);
				String hashConteudoLocal = getLotecaUtil().getHashFromString(
						conteudoLocal);

				// Comparar o hash do arquvo local com o baixado, caso seja
				// diferente, baixar Json e atualizar confronto
				if (!hashConteudoSite.equals(hashConteudoLocal)
						|| confrontos.get(campeonato) == null) {
					System.out.println(SYSTEM_PREFIX
							+ "Hash diferentes, atualizando arquivos");
					// Hash diferentes, tem que atualizar o arquivo
					getLotecaUtil().salvarJsonParaPastaLocal(conteudoSite,
							pathArquivo);
					atualizarConfronto(campeonato, conteudoSite);
					retorno = true;
				}
			}

		}
		return retorno;
	}

	private void atualizarConfronto(CampeonatoEnum campeonato,
			String jsonContent) {
		ConfrontoNovo confronto = parserJsonFromStrong(jsonContent);

		confrontos.put(campeonato, confronto);
	}

	public static ConfrontoNovo parserJsonFromStrong(String jsonContent) {
		ConfrontoNovo confronto = null;

		Gson gson = new GsonBuilder().create();
		ConfrontoNovo[] listConfrontos = gson.fromJson(jsonContent,
				ConfrontoNovo[].class);
		confronto = listConfrontos[0];

		return confronto;
	}

	public static ConfrontoNovo parserJson(String jsonFilePath) {
		Reader reader;
		ConfrontoNovo confronto = null;
		try {
			reader = new InputStreamReader(new FileInputStream(jsonFilePath),
					Charsets.ISO_8859_1);

			Gson gson = new GsonBuilder().create();
			ConfrontoNovo[] listConfrontos = gson.fromJson(reader,
					ConfrontoNovo[].class);
			confronto = listConfrontos[0];
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado " + jsonFilePath);
		}

		return confronto;
	}

	class Jogo {

		private Time time1;
		private Time time2;
		private int golsTime1;
		private int golsTime2;
		private StatusJogo statusJogo;

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

		public StatusJogo getStatusJogo() {
			return statusJogo;
		}

		public void setStatusJogo(StatusJogo statusJogo) {
			this.statusJogo = statusJogo;
		}

	}
}
