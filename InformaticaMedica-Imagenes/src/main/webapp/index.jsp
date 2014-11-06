<!DOCTYPE html>
<html>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/exporting.js"></script>
<script type="text/javascript" src="js/aplicacion.js"></script>

<body>
	<h2>Carga de imágenes médicas</h2>

	<div id="camposCargaImagen">
		Ruta de la imagen: <input type="text" id="ruta"> <br>
		Descripción: <input type="text" id="descripcion"> <br> <br>
		<button id="aceptar">Aceptar</button>
	</div>

	<img id="cargando" style="display: none;" alt="cargando"
		src="img/cargando.gif" />

	<div id="camposImagenCargada" style="display: none;">
		<br> <br> <img id="imagenMedica"
			style="height: 100%; width: 400px" />

		<textarea id="base64" rows="12" cols="100" style="float: right;"></textarea>
		<br>
		<button id="renderizarBase64" style="float: right;">Renderizar</button>

		<br> <br>
		<button id="detectarBordes" style="float: center;">Detectar
			Bordes</button>
		<button id="invertir" style="float: center;">Invertir</button>
		<br><br> 
		
		<img id="imagenFiltrada"
			style="display: none; height: 100%; width: 400px; float: center;" />

		<br> <br> Color promedio:
		<div id="colorPromedio"
			style="background-color: white; height: 20px; width: 50px;"></div>
		<br>
		<br>
		
		<button id="generarHistograma">Generar histograma</button>
		<div id="histograma" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
		<br>
		<br>

		<h3>Comparar mediante histogramas</h3>
		Ruta de la segunda imagen: <input type="text" id="rutaImagenAComparar"> <br>
		<button id="comparar">Comparar</button>
	</div>
</body>

</html>