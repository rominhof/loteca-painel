package loteca.persistencia.api;

import java.util.List;

import loteca.dominio.Loteca;

public interface LotecaDAO {

	Loteca insert(Loteca loteca);

	void refresh(Loteca loteca);

	Loteca insertOrUpdate(Loteca loteca);

	List<Loteca> findAll();

	Loteca findByNumConcurso(Integer numConcurso);

	Loteca findByStatus(Boolean finalizado);
}
