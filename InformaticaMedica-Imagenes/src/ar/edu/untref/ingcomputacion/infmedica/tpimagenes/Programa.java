package ar.edu.untref.ingcomputacion.infmedica.tpimagenes;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia.AdministradorImagenesMedicas;

public class Programa {

	private static final String RUTA_IMAGEN = "resources/radiografia.jpg";
	private static final String JAI_OPERADOR_CARGA_IMAGEN = "fileload";
	private static final String OUTPUT_RUTA = "resources/output";
	private static final String OUTPUT_EXTENSION_IMAGEN = "jpg";
	
	//Filtros
	private static final String JAI_OPERADOR_DETECCION_DE_BORDES = "GradientMagnitude";

	static {
		inicializar();
	}

	private static void inicializar() {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}
	
	public static void main(String[] args) {
		
		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, RUTA_IMAGEN);
		guardarEnBaseDeDatos(imagenOriginal);
		aplicarFiltro(imagenOriginal, JAI_OPERADOR_DETECCION_DE_BORDES);
	}

	private static void guardarEnBaseDeDatos(RenderedImage imagenOriginal) {
		
		ImagenMedica imagenMedica = new ImagenMedica();
		imagenMedica.setId(18);
		imagenMedica.setDescripcion("La descripcion");
		imagenMedica.setImagenBase64("ABCDE");
		
		AdministradorImagenesMedicas administrador = new AdministradorImagenesMedicas();
		administrador.guardar(imagenMedica);
	}

	private static void aplicarFiltro(RenderedImage imagenOriginal, String filtro) {
		
		System.out.println("Aplicando filtro...");
		PlanarImage imagenInvertida = JAI.create(filtro, imagenOriginal);
		BufferedImage bufferedImage = imagenInvertida.getAsBufferedImage();
		
		guardarImagen(bufferedImage);
	}

	private static void guardarImagen(BufferedImage bufferedImage) {
		
		System.out.println("Guardando imagen...");
		
		String nombreImagen = obtenerNombreArchivoOutput();
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(nombreImagen);
			 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream))
		{
			ImageIO.write(bufferedImage, OUTPUT_EXTENSION_IMAGEN, fileOutputStream);
			System.out.println("Imagen guardada exitosamente con nombre: " + nombreImagen);
			
		}catch (IOException e) {
			System.err.println("Ocurri� un error guardando la imagen: " + e);
		}
	}

	private static String obtenerNombreArchivoOutput() {
		
		StringBuilder nombreArchivoBuilder = new StringBuilder();
		nombreArchivoBuilder.append(OUTPUT_RUTA);
		nombreArchivoBuilder.append("_");
		nombreArchivoBuilder.append(String.valueOf(System.currentTimeMillis()));
		nombreArchivoBuilder.append(".");
		nombreArchivoBuilder.append(OUTPUT_EXTENSION_IMAGEN);
		
		return nombreArchivoBuilder.toString();
	}
}
