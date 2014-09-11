package loteca.persistencia.api;

import java.util.List;

import loteca.dominio.Cartela;

public interface CartelaDAO {

	void refresh(Cartela cartela);

	Cartela insertOrUpdate(Cartela cartela);

	List<Cartela> findByLotecaGrupoCartela(Integer numConcurso, Long idgc);

	List<Cartela> findByLoteca(Integer numConcurso);
	
	void remove(Cartela cartela);
}
