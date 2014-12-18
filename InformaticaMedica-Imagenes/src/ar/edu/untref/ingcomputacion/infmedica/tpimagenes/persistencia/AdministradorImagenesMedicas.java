package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import javax.media.jai.Histogram;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.ManipuladorDeImagenes;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;

public class AdministradorImagenesMedicas {

	public void guardar(ImagenMedica imagenMedica) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(imagenMedica);
		em.getTransaction().commit();
//		em.close();
	}
	
	public void guardar(String ruta, ImagenMedica imagen) {
		
		EntityManager em = EntityManagerProvider.getEntityManager();
		em.getTransaction().begin();

		double[] colores = ManipuladorDeImagenes.obtenerColorPromedio(ruta);
		String colorPromedio = "rgb(" + Math.round(colores[0]) + "," + Math.round(colores[1]) + "," + Math.round(colores[2]) + ")";
		
		
		String queryString = "INSERT INTO imagenes (id, objeto) VALUES ("
		+  System.currentTimeMillis()
				+ ", ('"+
		ruta + "', '" +
		imagen.getImagenBase64() + "', '" +
		colorPromedio + "'))";
		
		System.out.println(queryString);
		
		Query query = em.createNativeQuery(queryString);
		
		
		query.executeUpdate();
		em.getTransaction().commit();
		
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
