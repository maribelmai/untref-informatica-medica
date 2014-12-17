package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.Estudio;

public class AdministradorEstudios {

	public void guardar(Estudio estudio) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(estudio);
		em.getTransaction().commit();
	}

	public List<Estudio> obtenerEstudios(long idPaciente) {
		
		System.out.println("Obteniendo estudios del paciente: " + idPaciente);
		
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		Query query = em.createQuery("select e from Estudio e where e.paciente.id = :pacienteId");
		query.setParameter("pacienteId", idPaciente);
		
		@SuppressWarnings("unchecked")
		List<Estudio> estudios = (List<Estudio>) query.getResultList();
		return estudios;
	}
}
