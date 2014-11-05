package ar.edu.untref.ingcomputacion.infmedica.tpimagenes.servicios;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.ws.soap.AddressingFeature.Responses;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia.AdministradorImagenesMedicas;

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

	private ImagenMedica guardar(File imagen, String descripcion) throws IOException {
		
		ImagenMedica imagenMedica = new ImagenMedica();
		imagenMedica.setDescripcion(descripcion);

		BufferedImage buffer = ImageIO.read(imagen);
		WritableRaster raster = buffer.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
		imagenMedica.setImagenBase64(Base64.encode(data.getData()));
		
		administrador.guardar(imagenMedica);
		return imagenMedica;
	}
}
