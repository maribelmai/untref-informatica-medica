package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
	
	private static EntityManagerFactory factory;
	private static EntityManager entityManager;
	
	static {
		factory = Persistence.createEntityManagerFactory("ImagenesMedicasPersistenceProvider");
		entityManager = factory.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
