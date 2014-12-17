var cantidadImagenes = 1;
var idPaciente;

$(document).ready(function() {
	
	idPaciente = getURLParameter("idPaciente");
	
	$("#fecha").datepicker()
	
	$('#estudiosTable').dataTable( {
	    paging: false,
	    searching: false,
	    ordering: false,
	    info: false
	} );
	
	$("#agregarImagen").click(function() {
        $("#rutasDeLaImagenDiv").append('Ruta de la imagen: <input type="text" id="ruta' + cantidadImagenes + '"> <br>');
        cantidadImagenes++;
	});
	
	$("#nuevoEstudio").click(function() {

		$("#abmEstudio").show();
	});
	
	$("#guardarEstudio").click(function() {

		guardarEstudio();
	});
	
    cargarEstudios();
    
} );

function cargarEstudios() {
	
	$("#cargando").show();
	
	$.ajax({
		url: "http://localhost:8080/ImagenesMedicas/rest/estudios?idPaciente=" + idPaciente,
		type: 'GET',
		
		success: function(data){ 
			
			$("#cargando").hide();
			mostrarEstudios(data);
			
		},
		error: function(data) {

			alert("Ocurrio un error obteniendo estudios.");
		}
	});
}

function mostrarEstudios(data) {
	
	$('#estudiosTable').DataTable().draw();
	
	var estudios = JSON.parse(data);
	var length = estudios.length;
	
	if (length > 0) {
		$(".dataTables_empty").remove();
	}
	
	for (var i = 0; i < length; i++) {
		
		var fecha = estudios[i].fecha;
		var tipo = estudios[i].tipo;
		var cantidadImagenes = estudios[i].cantidadImagenes;
		var id = estudios[i].id;

		var tableRow = '<tr id="' + id + '"><td>' + fecha + '</td><td>' + tipo + '</td><td>' + cantidadImagenes + '</td><td><a href="http://localhost:8080/ImagenesMedicas/estudio?id=' + id + '">Detalle</a></td></tr>';
		
		$("#estudiosTable").find('tbody').append(tableRow);
	}
}

function guardarEstudio() {
	
	var fecha = $("#fecha").val();
	var tipo = $("#tipo").val();
	//var dni = $("#dni").val();
	
	$("#fecha").val("");
	$("#tipo").val("");
	//$("#dni").val("");
	
	$("#cargando").show();
	
	$.ajax({
		url: "http://localhost:8080/ImagenesMedicas/rest/guardarEstudio?paciente=" + idPaciente + "&fecha=" + fecha + "&tipo="+ tipo,
		type: 'GET',
		
		success: function(data){ 
			
			$("#cargando").hide();
		    cargarEstudios();
			
		},
		error: function(data) {

			alert("Ocurrio un error guardando estudio.");
		}
	});

}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}