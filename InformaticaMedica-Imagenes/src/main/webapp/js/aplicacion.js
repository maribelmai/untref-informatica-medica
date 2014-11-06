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

				alert("Ocurrio un error invirtiendo la imagen.");
			}
		});
	});

	$("#generarHistograma").click(function() {

		$("#cargando").show();
		var ruta = $("#ruta").val();

		$.ajax({
			url: "http://localhost:8080/ImagenesMedicas/rest/generarHistograma?ruta=" + escape(ruta),
			type: 'GET',

			success: function(data){ 

				$("#cargando").hide();
				$("#generarHistograma").hide();
				mostrarHistograma(data);
			},
			error: function(data) {

				alert("Ocurrio un error generando histograma.");
			}
		});
	});

	$("#comparar").click(function() {

		var ruta = $("#ruta").val();
		var rutaImagenAComparar = $("#rutaImagenAComparar").val();

		$.ajax({
			url: "http://localhost:8080/ImagenesMedicas/rest/comparar?ruta=" + escape(ruta) + "&rutaImagenAComparar=" + escape(rutaImagenAComparar),
			type: 'GET',

			success: function(data){ 

				alert(data);
			},
			error: function(data) {

				alert("Ocurrio un error comparando las imagenes.");
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

function mostrarHistograma(data) {

	var histogram = JSON.parse(data);

	var red = histogram[0];
	var green = histogram[1];
	var blue = histogram[2];

	Highcharts.theme = {
		colors: ['#FF0000', '#008000', '#0000FF', '#DDDF00', '#24CBE5', '#64E572', 
			         '#FF9655', '#FFF263', '#6AF9C4'],
	};
	
	Highcharts.setOptions(Highcharts.theme);

	$('#histograma').highcharts({
		chart: {
			type: 'area'
		},
		title: {
			text: 'Histograma'
		},
		subtitle: {
			text: 'RGB'
		},
		xAxis: {
			allowDecimals: false,
			labels: {
				formatter: function () {
					return this.value;
				}
			}
		},
		yAxis: {
			allowDecimals: false,
			title: {
				text: 'Pixeles'
			},
			labels: {
				formatter: function () {
					return this.value / 1000 + 'k';
				}
			}
		},
		plotOptions: {
			area: {
				pointStart: 0,
			}
		},
		series: [{
			name: 'Red',
			data: red
		}, {
			name: 'Green',
			data: green
		}, {
			name: 'Blue',
			data: blue
		}]
	});
}