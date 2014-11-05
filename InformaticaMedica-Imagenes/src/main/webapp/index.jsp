<!DOCTYPE html>
<html>

	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/aplicacion.js"></script>
		
	<body>
		<h2>Carga de imágenes médicas</h2>
		
		<div id="camposCargaImagen">
			Ruta de la imagen: <input type="text" id="ruta">
			<br>
			Descripción: <input type="text" id="descripcion">
			<br>
			<br>
			<button id="aceptar">Aceptar</button>
		</div>

		<img id="cargando" alt="cargando" src="img/cargando.gif" style="display:none;" />
		
		<br>		
		<br>
		
		<img id="imagenMedica" style="height: 400px; width: 400px" />
		
	</body>

</html>