package loteca.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loteca.dominio.Cartela;
import loteca.dominio.Estatistica;
import loteca.dominio.EstatisticaPalpites;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;
import loteca.dominio.Palpite;
import loteca.persistencia.DAOFactory;
import loteca.persistencia.api.EstatisticaDAO;

public class EstatisticaService {

	private EstatisticaDAO estatisticaDAO;
	private CartelaService cartelaService;

	public EstatisticaService() {
		// Vazio
	}

	private EstatisticaDAO getEstatisticaDAO() {
		if (this.estatisticaDAO == null) {
			this.estatisticaDAO = DAOFactory
					.getRepository(EstatisticaDAO.class);
		}
		return this.estatisticaDAO;
	}

	private CartelaService getCartelaService() {
		if (this.cartelaService == null) {
			this.cartelaService = new CartelaService();
		}
		return this.cartelaService;
	}

	public Estatistica consultarEstatisticaPorConcursoEGrupoUsuario(
			Loteca loteca, GrupoCartela grupoCartela) {
		return getEstatisticaDAO().findByLotecaEGrupoUsuario(loteca,
				grupoCartela);
	}

	public Estatistica atualizarEstatistica(Loteca loteca,
			GrupoCartela grupoCartela) {
		Estatistica estatistica = consultarEstatisticaPorConcursoEGrupoUsuario(
				loteca, grupoCartela);

		if (estatistica == null) {
			estatistica = new Estatistica();
			estatistica.setLoteca(loteca);
			estatistica.setGrupoCartela(grupoCartela);
		}

		List<Cartela> cartelas = getCartelaService()
				.carregaCartelasDeConcursoGrupoCartela(loteca.getNumConcurso(),
						grupoCartela.getId());

		Map<Integer, EstatisticaPalpites> estatisticaPalpitesMap = new HashMap<Integer, EstatisticaPalpites>();
		inicializarEstatisticaPalpites(estatisticaPalpitesMap);

		for (Cartela cartela : cartelas) {
			for (Palpite palpite : cartela.getPalpites()) {
				EstatisticaPalpites estatisticaPalpites = estatisticaPalpitesMap
						.get(palpite.getPartida().getSequencialJogo());

				if (estatisticaPalpites.getPartida() == null) {
					estatisticaPalpites.setPartida(palpite.getPartida());
				} 

				if (palpite.getC1()) {
					estatisticaPalpites.adicionaC1();
				}
				if (palpite.getC2()) {
					estatisticaPalpites.adicionaC2();
				}
				if (palpite.getCx()) {
					estatisticaPalpites.adicionaCX();
				}
			}
		}

		Collection<EstatisticaPalpites> estatisticaPalpitesList = estatisticaPalpitesMap
				.values();
		for (EstatisticaPalpites estatisticaPalpites : estatisticaPalpitesList) {
			estatisticaPalpites.gerarPorcentagem();
		}

		estatistica.setEstatisticaPalpites(new ArrayList<EstatisticaPalpites>(
				estatisticaPalpitesList));

		return getEstatisticaDAO().insertOrUpdate(estatistica);
	}

	private void inicializarEstatisticaPalpites(
			Map<Integer, EstatisticaPalpites> estatisticaPalpitesMap) {
		for (int i = 1; i < 15; i++) {
			EstatisticaPalpites estatisticaPalpites = new EstatisticaPalpites();
			estatisticaPalpitesMap.put(i, estatisticaPalpites);
		}

	}
}