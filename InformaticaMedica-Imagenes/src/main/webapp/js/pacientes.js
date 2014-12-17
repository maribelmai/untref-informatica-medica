$(document).ready(function() {
	
	$('#pacientesTable').dataTable( {
	    paging: false,
	    searching: false,
	    ordering: false,
	    info: false
	} );
	
	$("#nuevoPaciente").click(function() {

		$("#abmPaciente").show();
	});
	
	$("#guardarPaciente").click(function() {

		guardarPaciente();
	});
	
    cargarPacientes();
    
} );

function cargarPacientes() {
	
	$("#cargando").show();
	
	$.ajax({
		url: "http://localhost:8080/ImagenesMedicas/rest/pacientes",
		type: 'GET',
		
		success: function(data){ 
			
			$("#cargando").hide();
			mostrarPacientes(data);
			
		},
		error: function(data) {

			alert("Ocurrio un error obteniendo datos de pacientes.");
		}
	});
}

function mostrarPacientes(data) {
	
	$('#pacientesTable').DataTable().draw();
	
	var pacientes = JSON.parse(data);
	var length = pacientes.length;
	
	if (length > 0) {
		$(".dataTables_empty").remove();
	}
	
	for (var i = 0; i < length; i++) {
		
		var nombre = pacientes[i].nombre;
		var apellido = pacientes[i].apellido;
		var dni = pacientes[i].dni;
		var id = pacientes[i].id;

		var tableRow = '<tr id="' + id + '"><td>' + nombre + '</td><td>' + apellido + '</td><td>' + dni + '</td><td><a href="http://localhost:8080/ImagenesMedicas/paciente/' + id + '">Ver estudios</a></td></tr>';
		
		$("#pacientesTable").find('tbody').append(tableRow);
	}
}

function guardarPaciente() {
	
	var nombre = $("#nombre").val();
	var apellido = $("#apellido").val();
	var dni = $("#dni").val();
	
	$("#nombre").val("");
	$("#apellido").val("");
	$("#dni").val("");
	
	$("#cargando").show();
	
	$.ajax({
		url: "http://localhost:8080/ImagenesMedicas/rest/guardarPaciente?nombre=" + nombre + "&apellido=" + apellido + "&dni="+ dni,
		type: 'GET',
		
		success: function(data){ 
			
			$("#cargando").hide();
		    cargarPacientes();
			
		},
		error: function(data) {

			alert("Ocurrio un error guardando paciente.");
		}
	});

}