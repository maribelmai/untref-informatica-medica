package ar.edu.untref.ingcomputacion.infmedica.tpimagenes;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ManipuladorDeImagenes {

	private static final String JAI_OPERADOR_CARGA_IMAGEN = "fileload";

	static {
		inicializar();
	}

	private static void inicializar() {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	public static String aplicarFiltro(String ruta, String filtro) throws IOException {

		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, ruta);
		PlanarImage imagenFiltrada = JAI.create(filtro, imagenOriginal);
		BufferedImage bufferedImage = imagenFiltrada.getAsBufferedImage();
		
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        byte[] bytes = out.toByteArray();

        return Base64.encode(bytes);
	}

	public static Histogram crearHistograma(String ruta) {
		
		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, ruta);
		RenderedOp op = JAI.create("histogram", imagenOriginal, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");
        
        return histogram;
	}
	
	/**
	 * Indica si los dos histogramas dados son idénticos
	 */
	public static boolean compararHistogramas(Histogram primero, Histogram segundo) {
		
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

	public static double[] obtenerColorPromedio(String ruta) {

		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, ruta);
		RenderedOp op = JAI.create("histogram", imagenOriginal, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");

        return histogram.getMean();
	}
}
