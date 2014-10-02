package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import javax.persistence.EntityManager;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;

public class AdministradorImagenesMedicas {

	public void guardar(ImagenMedica imagenMedica) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(imagenMedica);
		em.getTransaction().commit();
		em.close();
	}

}
