


function RedireccionarDashboardSorteo(idsorteo) {

	$.ajax({
		type : "GET",
		cache : false,
		url : "Dashboard.do",
		data : "view=Predeterminado&psorteo=" + idsorteo,

		success : function(msg) {

			location.href = "Dashboard.do?sorteo=" + idsorteo;

		}

	});

}

function RedireccionarDashboardSector(idsorteo, idsector) {

	$.ajax({
		type : "GET",
		cache : false,
		url : "Dashboard.do",
		data : "view=Predeterminado&psorteo=" + idsorteo + "&psector=" + idsector,

		success : function(msg) {

			location.href = "Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector;

		}

	});

}


function RedireccionarDashboardNicho(idsorteo, idsector, idnicho) {

	$.ajax({
		type : "GET",
		cache : false,
		url : "Dashboard.do",
		data : "view=Predeterminado&psorteo=" + idsorteo + "&psector=" + idsector + "&pnicho=" + idnicho,

		success : function(msg) {

			location.href = "Dashboard.do?sorteo=" + idsorteo + "&sector=" + idsector + "&nicho=" + idnicho;

		}

	});

}





