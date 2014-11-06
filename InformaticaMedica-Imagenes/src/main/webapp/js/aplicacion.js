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
		    	
		    	mostrarImagenGuardada(data);
		    	mostrarCodigoBase64(data);
		    	mostrarColorPromedio();


		    	$("#cargando").hide();
		    	$("#camposImagenCargada").show();
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

	$("#detectarBordes").click(function() {

    	$("#imagenFiltrada").hide();
		var ruta = $("#ruta").val();

		$.ajax({
		    url: "http://localhost:8080/ImagenesMedicas/rest/aplicarFiltro?ruta=" + escape(ruta) + "&filtro=GradientMagnitude",
		    type: 'GET',
		    
		    success: function(data){ 

		    	$("#imagenFiltrada").attr('src', 'data:image/png;base64,' + data.replace("\n",""));
		    	$("#imagenFiltrada").show();
		    },
		    error: function(data) {
		    	
		    	alert("Ocurrio un error detectando los bordes de la imagen.");
		    }
		});
	});
	

	$("#invertir").click(function() {

    	$("#imagenFiltrada").hide();
		var ruta = $("#ruta").val();

		$.ajax({
		    url: "http://localhost:8080/ImagenesMedicas/rest/aplicarFiltro?ruta=" + escape(ruta) + "&filtro=Invert",
		    type: 'GET',
		    
		    success: function(data){ 

		    	$("#imagenFiltrada").attr('src', 'data:image/png;base64,' + data.replace("\n",""));
		    	$("#imagenFiltrada").show();
		    },
		    error: function(data) {
		    	
		    	alert("Ocurrio un error detectando los bordes de la imagen.");
		    }
		});
	});

});

function mostrarImagenGuardada(data) {
	
	$("#imagenMedica").attr('src', 'data:image/png;base64,' + data.replace("\n",""));
}

function mostrarCodigoBase64(data) {
	
	$("#base64").val(data.replace("\n",""));
}

function mostrarColorPromedio() {
	
	var ruta = $("#ruta").val();
	
	$.ajax({
	    url: "http://localhost:8080/ImagenesMedicas/rest/obtenerColorPromedio?ruta=" + escape(ruta),
	    type: 'GET',
	    
	    success: function(data){ 
	    	
	    	$("#colorPromedio").css("background-color", data);
	    },
	    error: function(data) {
	    	
	    	alert("Ocurrio un error calculando el color promedio.");
	    }
	});
}