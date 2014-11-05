var dataTest;

$(document).ready(function() {
	
	//CARGAR IMAGEN
	
	$("#aceptar").click(function() {
		
		var ruta = $("#ruta").val();
		var descripcion = $("#descripcion").val();
		
		$("#camposCargaImagen").hide();
		$("#cargando").show();
		
		$.ajax({
		    url: "http://localhost:8080/ImagenesMedicas/rest/seleccionarArchivo?ruta=" + escape(ruta) + "&descripcion=" + descripcion,
		    type: 'GET',
		    
		    success: function(data){ 
		    	
		    	$("#cargando").hide();
		    	mostrarImagenGuardada(data);
		    },
		    error: function(data) {
		    	
		    	$("#camposCargaImagen").show();
		    	$("#cargando").hide();
		    	
		    	alert("Ocurrio un error cargando la imagen. Compruebe la ruta y el tipo del archivo.");
		    }
		});
	});
});

function mostrarImagenGuardada(data) {
	
	dataTest = data.replace("\n","");
	
	$("#imagenMedica")
		.attr('src', 'data:image/png;base64,' + dataTest);
}
