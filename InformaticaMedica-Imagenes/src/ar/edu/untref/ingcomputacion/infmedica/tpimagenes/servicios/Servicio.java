package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.media.jai.Histogram;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.ManipuladorDeImagenes;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia.AdministradorImagenesMedicas;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Path("/")
public class Servicio {
	
	private AdministradorImagenesMedicas administrador = new AdministradorImagenesMedicas();

	@Path("seleccionarArchivo")
	@GET
	public Response seleccionarArchivo(@QueryParam(value = "ruta") String ruta, @QueryParam(value = "descripcion") String descripcion) {
		
		ResponseBuilder response = Response.status(Response.Status.CREATED);
		
		File imagen = new File(ruta);
		
		if (imagen.exists()) {
			
			try {
				
				ImagenMedica imagenMedica = guardar(imagen, descripcion);
				response.entity(imagenMedica.getImagenBase64());
			} catch (IOException e) {
				response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ocurrió un problema al guardar la imagen");
			}
		}
		else {
			response = Response.status(Response.Status.PRECONDITION_FAILED).entity("La imagen no existe");
		}
		
		return response.build();
	}
	
	@Path("aplicarFiltro")
	@GET
	public Response aplicarFiltro(@QueryParam(value = "ruta") String ruta,
										@QueryParam(value= "filtro") String filtro) {
		
		ResponseBuilder response = Response.status(Response.Status.OK);
		
		File imagen = new File(ruta);
		
		if (imagen.exists()) {
			
			String base64 = null;
			
			try {
				base64 = ManipuladorDeImagenes.aplicarFiltro(ruta, filtro);
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			response.entity(base64);
		}
		else {
			response = Response.status(Response.Status.PRECONDITION_FAILED).entity("La imagen no existe");
		}
		
		return response.build();
	}
	
	@Path("obtenerColorPromedio")
	@GET
	public Response obtenerColorPromedio(@QueryParam(value = "ruta") String ruta) {
		
		ResponseBuilder response = Response.status(Response.Status.OK);
		
		File imagen = new File(ruta);
		
		if (imagen.exists()) {
			
			double[] colores = ManipuladorDeImagenes.obtenerColorPromedio(ruta);
			response.entity("rgb(" + Math.round(colores[0]) + "," + Math.round(colores[1]) + "," + Math.round(colores[2]) + ")");
		}
		else {
			response = Response.status(Response.Status.PRECONDITION_FAILED).entity("La imagen no existe");
		}
		
		return response.build();
	}
	
	@Path("comparar")
	@GET
	public Response comparar(@QueryParam(value = "ruta") String ruta,
							@QueryParam(value = "rutaImagenAComparar") String rutaImagenAComparar) {
		
		ResponseBuilder response = Response.status(Response.Status.OK);
		
		File imagen = new File(ruta);
		
		if (imagen.exists()) {
			
			Histogram histograma1 = ManipuladorDeImagenes.crearHistograma(ruta);
			Histogram histograma2 = ManipuladorDeImagenes.crearHistograma(rutaImagenAComparar);
			
			response.entity(ManipuladorDeImagenes.compararHistogramas(histograma1, histograma2));
		}
		else {
			response = Response.status(Response.Status.PRECONDITION_FAILED).entity("La imagen no existe");
		}
		
		return response.build();
	}
	
	@Path("generarHistograma")
	@GET
	public Response generarHistograma(@QueryParam(value = "ruta") String ruta) {
		
		ResponseBuilder response = Response.status(Response.Status.OK);
		
		File imagen = new File(ruta);
		
		if (imagen.exists()) {
			
			Histogram histograma = ManipuladorDeImagenes.crearHistograma(ruta);
			String json = new Gson().toJson(Arrays.asList(histograma.getBins()));
			response.entity(json);
		}
		else {
			response = Response.status(Response.Status.PRECONDITION_FAILED).entity("La imagen no existe");
		}
		
		return response.build();
	}

	private ImagenMedica guardar(File imagen, String descripcion) throws IOException {
		
		ImagenMedica imagenMedica = new ImagenMedica();
		imagenMedica.setDescripcion(descripcion);

        FileInputStream fis = new FileInputStream(imagen);
        byte imageData[] = new byte[(int) imagen.length()];
        fis.read(imageData);
        String imagenBase64 = Base64.encode(imageData);
        fis.close();
		imagenMedica.setImagenBase64(imagenBase64);
		
		administrador.guardar(imagenMedica);
		return imagenMedica;
	}
}
