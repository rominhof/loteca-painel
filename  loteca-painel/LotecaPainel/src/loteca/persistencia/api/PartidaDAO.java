package loteca.persistencia.api;

import loteca.dominio.Partida;

public interface PartidaDAO {

	Partida insertOrUpdate(Partida partida);
}
