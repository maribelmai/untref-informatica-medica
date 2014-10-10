package ar.edu.untref.ingcomputacion.infmedica.tpimagenes;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia.AdministradorImagenesMedicas;

public class Programa {

	private static final String RUTA_IMAGEN = "resources/radiografia.jpg";
	private static final String JAI_OPERADOR_CARGA_IMAGEN = "fileload";
	private static final String OUTPUT_RUTA = "resources/output";
	private static final String OUTPUT_EXTENSION_IMAGEN = "jpg";

	// Filtros
	private static final String JAI_OPERADOR_DETECCION_DE_BORDES = "GradientMagnitude";

	static {
		inicializar();
	}

	private static void inicializar() {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	public static void main(String[] args) {

		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, RUTA_IMAGEN);
		
		try {
		
			guardarEnBaseDeDatos(imagenOriginal);
			
			recuperarImagenDeLaBaseDeDatos();

		} catch (IOException e) {
		}
		
		aplicarFiltro(imagenOriginal, JAI_OPERADOR_DETECCION_DE_BORDES);
	}

	private static void guardarEnBaseDeDatos(RenderedImage imagenOriginal)
			throws IOException {

		ImagenMedica imagenMedica = new ImagenMedica();
		imagenMedica.setId(System.currentTimeMillis());
		imagenMedica.setDescripcion("La descripcion");

		File imagen = new File("resources/radiografia.jpg");
		BufferedImage buffer = ImageIO.read(imagen);
		WritableRaster raster = buffer.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		String base64 = Base64.encode(data.getData());
		imagenMedica.setImagenBase64(base64);

		AdministradorImagenesMedicas administrador = new AdministradorImagenesMedicas();
		administrador.guardar(imagenMedica);
	}

	private static void recuperarImagenDeLaBaseDeDatos() {

		
	}

	
	private static void aplicarFiltro(RenderedImage imagenOriginal,
			String filtro) {

		System.out.println("Aplicando filtro...");
		PlanarImage imagenInvertida = JAI.create(filtro, imagenOriginal);
		BufferedImage bufferedImage = imagenInvertida.getAsBufferedImage();

		guardarImagen(bufferedImage);
	}

	private static void guardarImagen(BufferedImage bufferedImage) {

		System.out.println("Guardando imagen...");

		String nombreImagen = obtenerNombreArchivoOutput();

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				nombreImagen);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream)) {
			ImageIO.write(bufferedImage, OUTPUT_EXTENSION_IMAGEN,
					fileOutputStream);
			System.out.println("Imagen guardada exitosamente con nombre: "
					+ nombreImagen);

		} catch (IOException e) {
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
