<!DOCTYPE html>
<html>

<link rel="stylesheet" type="text/css"
	href="themes/jquery.dataTables.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/pacientes.js"></script>

<body>
	<h2>Pacientes</h2>

	<br>
	<button id="nuevoPaciente">Nuevo paciente</button>
	<br>
	<br>

	<div id="abmPaciente" style="display: none;">

		Nombre: <input type="text" id="nombre"> <br> 
		Apellido: <input type="text" id="apellido"> <br> 
		DNI: <input type="text" id="dni"> <br><br>

		<button id="guardarPaciente">Guardar</button>
	</div>

	<br>
	
	<img id="cargando" style="display: none;" alt="cargando"
		src="img/cargando.gif" />
	<br>

	<div id="listadoPacientes">

		<table id="pacientesTable" class="display" cellspacing="0" width="100%">

			<thead>
				<tr>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>DNI</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

</body>

</html>