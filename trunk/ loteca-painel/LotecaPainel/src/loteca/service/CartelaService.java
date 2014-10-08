package loteca.service;

import java.util.List;

import loteca.dominio.Cartela;
import loteca.dominio.ChanceCartelaEnum;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.dominio.Partida;
import loteca.dominio.Resultado;
import loteca.dominio.StatusJogo;
import loteca.dominio.Time;
import loteca.job.PalpiteJob;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.CartelaDAO;
import loteca.persistencia.api.PalpiteDAO;

public class CartelaService {
	private CartelaDAO cartelaDAO;
	private PalpiteDAO palpiteDAO;

	public CartelaService() {
		// Vazio
	}

	private CartelaDAO getCartelaDAO() {
		if (this.cartelaDAO == null) {
			this.cartelaDAO = DAOFactory.getRepository(CartelaDAO.class);
		}
		return this.cartelaDAO;
	}

	private PalpiteDAO getPalpiteDAO() {
		if (this.palpiteDAO == null) {
			this.palpiteDAO = DAOFactory.getRepository(PalpiteDAO.class);
		}
		return this.palpiteDAO;
	}

	public List<Cartela> carregaCartelasDeConcursoGrupoCartela(
			Integer numConcurso, Long idgc) {
		return getCartelaDAO().findByLotecaGrupoCartela(numConcurso, idgc);
	}

	public List<Cartela> carregaCartelasDeConcurso(Integer numConcurso) {
		return getCartelaDAO().findByLoteca(numConcurso);
	}

	public void refresh(Cartela cartela) {
		getCartelaDAO().refresh(cartela);
	}

	public void salvar(Cartela cartela) {
		getCartelaDAO().insertOrUpdate(cartela);
	}

	public void atualizarCartela(Cartela cartela) {
		getCartelaDAO().atualizarCartela(cartela);
	}

	public void atualizaPalpite(Palpite palpite) {
		getPalpiteDAO().insertOrUpdate(palpite);
	}

	public Palpite atualizarPalpite(Palpite palpite) {
		return getPalpiteDAO().atualizarPalpite(palpite);
	}

	public void remove(Cartela cartela) {
		getCartelaDAO().remove(cartela);
	}

	public void compararCartelas(Loteca lotecaAtual) {
		List<Cartela> cartelas = carregaCartelasDeConcurso(lotecaAtual
				.getNumConcurso());

		int sequencia = 0;
		System.out.println(PalpiteJob.SYSTEM_PREFIX + "Total de "
				+ cartelas.size() + " cartelas a serem comparadas");

		for (Cartela cartela : cartelas) {
			sequencia++;
			System.out.println(PalpiteJob.SYSTEM_PREFIX
					+ "Comparando cartela = " + sequencia);
			int erros = 0;
			int acertos = 0;
			int sequenciaPalpite = 0;
			for (Palpite palpite : cartela.getPalpites()) {
				sequenciaPalpite++;
				System.out.println(PalpiteJob.SYSTEM_PREFIX
						+ "Comparando palpite = " + sequenciaPalpite
						+ " da cartela = " + sequencia);
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
						System.out.println(PalpiteJob.SYSTEM_PREFIX
								+ "Palpite finalizado!");
						jogoFinalizado = true;
					}

					palpite.setJogoFinalizado(jogoFinalizado);
					palpite.setAcerto(acerto);
					palpite.setResultado(particaGabarito.getResultado());

					// Atuaizar dados do palpite
					atualizarPalpite(palpite);
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
			atualizarCartela(cartela);
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
}