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
		    	mostrarCodigoBase64(data);
		    },
		    error: function(data) {
		    	
		    	$("#camposCargaImagen").show();
		    	$("#cargando").hide();
		    	
		    	alert("Ocurrio un error cargando la imagen. Compruebe la ruta y el tipo del archivo.");
		    }
		});
	});
	
	$("#renderizarBase64").click(function() {
		
		$("#imagenMedica").attr('src', 'data:image/png;base64,' + $("#base64").val());
		
	});
	
});

function mostrarImagenGuardada(data) {
	
	$("#imagenMedica").attr('src', 'data:image/png;base64,' + data.replace("\n",""));
}

function mostrarCodigoBase64(data) {
	
	$("#base64").val(data.replace("\n",""));
	$("#base64").show();
	$("#renderizarBase64").show();
}