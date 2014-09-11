package loteca.persistencia;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceFactory {

	private static Map<String, EntityManagerFactory> map = new HashMap<String, EntityManagerFactory>();

	private static EntityManagerFactory getEmf(String persistenceUnit) {
		EntityManagerFactory emf = map.get(persistenceUnit);
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			map.put(persistenceUnit, emf);
		}
		return emf;
	}

	public static EntityManagerFactory getEntityManagerFactory(
			String persistenceUnit) {
		return getEmf(persistenceUnit);
	}

	public static void closeEntityManagerFactory(String persistenceUnit) {
		EntityManagerFactory emf = getEmf(persistenceUnit);
		emf.close();
		map.put(persistenceUnit, null);
	}

}
