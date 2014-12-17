package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.Paciente;

public class AdministradorPacientes {

	public void guardar(Paciente paciente) {
	
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		em.persist(paciente);
		em.getTransaction().commit();
	}

	public List<Paciente> obtenerPacientes() {
		
		EntityManager em = EntityManagerProvider.getEntityManager();
		
		em.getTransaction().begin();
		
		Query query = em.createQuery("select p from Paciente p");
		
		@SuppressWarnings("unchecked")
		List<Paciente> pacientes = (List<Paciente>) query.getResultList();
		
		em.getTransaction().commit();
		return pacientes;
	}
}
