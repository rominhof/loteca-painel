package loteca.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


public class JpaHelper {

	private EntityManagerFactory emf;
	private ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();
	private ThreadLocal<EntityTransaction> transaction = new ThreadLocal<EntityTransaction>();

	public JpaHelper(String persistenceUnit) {
		this.emf = PersistenceFactory.getEntityManagerFactory(persistenceUnit);
	}

	public EntityManager getEntityManager() {
		EntityManager result = em.get();
		if (result == null) {
			result = emf.createEntityManager();
			em.set(result);
		}
		return result;
	}

	public void beginTransaction() {
		transaction.set(getEntityManager().getTransaction());
		transaction.get().begin();
	}

	public void commitTransaction() {
		EntityTransaction entityTransaction = transaction.get();
		if (entityTransaction != null) {
			entityTransaction.commit();
			transaction.set(null);
		}
	}

	public void rollbackTransaction() {
		EntityTransaction entityTransaction = transaction.get();
		if (entityTransaction != null) {
			entityTransaction.rollback();
			transaction.set(null);
		}
	}

	public void closeEntityManager() {
		EntityManager entityManager = em.get();
		if (entityManager != null) {
			entityManager.close();
			em.set(null);
		}
	}

}
