package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;

public class AdministradorImagenesMedicas {

	public void guardar(ImagenMedica imagenMedica) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(imagenMedica);
		em.getTransaction().commit();
//		em.close();
	}

	public ImagenMedica obtener(long idImagen) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		
		Query query = em.createQuery("select i from ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica i where i.id = :idImagen")
				.setParameter("idImagen", idImagen);
		
		ImagenMedica imagenRecuperada = (ImagenMedica) query.getSingleResult();
		
		em.getTransaction().commit();
//		em.close();
		
		return imagenRecuperada;
	}

}
