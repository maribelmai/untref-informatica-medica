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
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.modelo.ImagenMedica;
import ar.edu.untref.ingcomputacion.infmedica.tpimagenes.persistencia.AdministradorImagenesMedicas;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Programa {

	private static final String RUTA_IMAGEN = "resources/prueba.png";
	private static final String JAI_OPERADOR_CARGA_IMAGEN = "fileload";
	private static final String OUTPUT_RUTA = "resources/output";
	private static final String OUTPUT_EXTENSION_IMAGEN = "jpg";

	// Filtros
	private static final String JAI_OPERADOR_DETECCION_DE_BORDES = "GradientMagnitude";
	private static String base64APersistir;

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
		crearHistograma();
	}

	private static void guardarEnBaseDeDatos(RenderedImage imagenOriginal)
			throws IOException {

		ImagenMedica imagenMedica = new ImagenMedica();
		imagenMedica.setDescripcion("La descripcion");

		File imagen = new File("resources/radiografia.jpg");
		BufferedImage buffer = ImageIO.read(imagen);
		WritableRaster raster = buffer.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		base64APersistir = Base64.encode(data.getData());
		imagenMedica.setImagenBase64(base64APersistir);

		AdministradorImagenesMedicas administrador = new AdministradorImagenesMedicas();
		administrador.guardar(imagenMedica);
	}

	private static void recuperarImagenDeLaBaseDeDatos() {
//
//		AdministradorImagenesMedicas administrador = new AdministradorImagenesMedicas();
//		
//		ImagenMedica imagenRecuperada = administrador.obtener(ID_IMAGEN);
//		
//		if (imagenRecuperada.getImagenBase64().equals(base64APersistir)) {
//			System.out.println("La imágen recuperada es idéntica a la persistida");
//			
//		} else {
//			System.out.println("Existen diferencias entre la imágen recuperada de la BBDD y la persistida");
//		}
		
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
	
	private static void crearHistograma() {
		
		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, RUTA_IMAGEN);
		RenderedOp op = JAI.create("histogram", imagenOriginal, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");

        RenderedImage imagenOriginalRadiografia = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, "resources/radiografia.jpg");
		RenderedOp opRadiografia = JAI.create("histogram", imagenOriginalRadiografia, null);
        Histogram histogramRadiografia = (Histogram) opRadiografia.getProperty("histogram");
        
        System.out.println("Comparo la imagen con si misma: " + compararHistogramas(histogram, histogram));
        
        System.out.println("Comparo dos imagenes distintas: " + compararHistogramas(histogram, histogramRadiografia));
        
        double[] mean = histogram.getMean();
        double[] mean2 = histogramRadiografia.getMean();
        
        System.out.println("Holis");
	}
	
	/**
	 * Indica si los dos histogramas dados son id�nticos
	 */
	private static boolean compararHistogramas(Histogram primero, Histogram segundo) {
		
		// Tienen la misma cantidad de contenedores
		boolean sonIguales = primero.getBins().length == segundo.getBins().length;
		
		for (int contenedor = 0 ; contenedor < primero.getBins().length && sonIguales ; contenedor++) {
			
			// Tienen la misma cantidad de bandas
			sonIguales = primero.getBins(contenedor).length == segundo.getBins(contenedor).length;
			
			for (int banda = 0 ; banda < primero.getBins(contenedor).length && sonIguales ; banda++) {
			
				// Tienen la misma cantidad de pixeles para esa banda, en ese contenedor
				sonIguales = primero.getBins(contenedor)[banda] == segundo.getBins(contenedor)[banda];
			}
			
		}

		return sonIguales;
	}
	
}
