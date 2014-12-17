<!DOCTYPE html>
<html>

<link rel="stylesheet" type="text/css" href="themes/jquery.dataTables.css">
<link rel="stylesheet" type="text/css" href="themes/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="themes/jquery-ui.structure.min.css">
<link rel="stylesheet" type="text/css" href="themes/jquery-ui.theme.min.css">
<link rel="stylesheet" type="text/css" href="themes/jquery.dataTables.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/estudios.js"></script>


<body>
	<h2>Estudios</h2>

	<br>
	<button id="nuevoEstudio">Cargar nuevo estudio</button>
	<br>
	<br>

	<div id="abmEstudio" style="display: none;">

		Fecha: <input type="text" id="fecha"> <br>
		Tipo de estudio: <input type="text" id="tipo"> <br> 
		
		<div id="rutasDeLaImagenDiv">
		
			<button id="agregarImagen">Agregar imagen</button>  <br> 
		</div>

 		<br><br> 
		<button id="guardarEstudio">Guardar</button>
	</div>

	<br>
	
	<img id="cargando" style="display: none;" alt="cargando"
		src="img/cargando.gif" />
	<br>

	<div id="listadoEstudios">

		<table id="estudiosTable" class="display" cellspacing="0" width="100%">

			<thead>
				<tr>
					<th>Fecha</th>
					<th>Tipo de estudio</th>
					<th>Cantidad de imágenes</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

</body>

</html>