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
	
	/** default bin sizes for color histograms used when comparing images. */
	public static final int RED_BINS=6, GREEN_BINS=6, BLUE_BINS=5;
	
	static {
		inicializar();
	}

	private static void inicializar() {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	public static String aplicarFiltro(String ruta, String filtro) throws IOException {

		RenderedImage imagenOriginal = obtenerImagen(ruta);
		PlanarImage imagenFiltrada = JAI.create(filtro, imagenOriginal);
		BufferedImage bufferedImage = imagenFiltrada.getAsBufferedImage();
		
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", out);
        byte[] bytes = out.toByteArray();

        return Base64.encode(bytes);
	}

	public static Histogram crearHistograma(String ruta) {
		
		RenderedImage imagenOriginal = obtenerImagen(ruta);
		
		RenderedOp op = JAI.create("histogram", imagenOriginal, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");
        
        return histogram;
	}

	private static RenderedImage obtenerImagen(String ruta) {
		RenderedImage imagenOriginal = JAI.create(JAI_OPERADOR_CARGA_IMAGEN, ruta);
		return imagenOriginal;
	}
	
	/**
	 * Indica si los dos histogramas dados son id�nticos
	 */
	public static boolean compararHistogramasIdenticamente(Histogram primero, Histogram segundo) {
		
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
	
	/**
	 * Calcula la distancia euclídea entre dos histogramas 
	 */
	
	public static double calcularDistanciaEuclidea(String rutaPrimeraImagen, String rutaSegundaImagen) {
		
		Histogram primero = crearHistograma(rutaPrimeraImagen);
		Histogram segundo = crearHistograma(rutaSegundaImagen);
		
		RenderedImage a = obtenerImagen(rutaPrimeraImagen);
		RenderedImage b = obtenerImagen(rutaSegundaImagen);
		
		int[] numBins = new int[] {RED_BINS, GREEN_BINS, BLUE_BINS};
		
		int[][] ha = primero.getBins();
		double na = a.getWidth()*a.getHeight();
		int[][] hb = segundo.getBins();
		double nb = b.getWidth()*b.getHeight();
		
		double sum = 0;
		for (int band=0;band<3;band++)
		for (int bin=0;bin<numBins[band];bin++) {
			double f=((double)ha[band][bin]/na-(double)hb[band][bin]/nb);
			sum+=f*f;
		}
		return Math.sqrt(sum);
	}

	public static double[] obtenerColorPromedio(String ruta) {

		RenderedImage imagenOriginal = obtenerImagen(ruta);
		RenderedOp op = JAI.create("histogram", imagenOriginal, null);
        Histogram histogram = (Histogram) op.getProperty("histogram");

        return histogram.getMean();
	}
}
