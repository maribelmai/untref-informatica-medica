package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class Servicio {

	@GET
	public String holaMundo() {
		
		return "Hola mundo!";
	}
	
}
