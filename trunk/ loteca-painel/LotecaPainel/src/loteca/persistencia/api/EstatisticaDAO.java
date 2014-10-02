package loteca.persistencia.api;

import loteca.dominio.Estatistica;
import loteca.dominio.GrupoCartela;
import loteca.dominio.Loteca;

public interface EstatisticaDAO {

	Estatistica insertOrUpdate(Estatistica estatistica);

	Estatistica findByLotecaEGrupoUsuario(Loteca loteca,
			GrupoCartela grupoCartela);
}
