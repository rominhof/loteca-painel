package loteca.persistencia.api;

import loteca.dominio.Time;

public interface TimeDAO {

	Time findByNome(String nome);

}
