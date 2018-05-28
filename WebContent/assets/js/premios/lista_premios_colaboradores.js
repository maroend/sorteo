
function initialize()
{
	$(consultaPremios);
}

function consultaPremios() {

	$.ajax({
		type: 'GET',
		url: 'PremiosColaboradores',
		data: 'view=Buscar&pg=1&show=100',
		async: false,
		success:
   	    	function(msg){
				$('#gallery').html(msg);
   	    	}
   	});
}

var PREMIOS_idPremio;
function showModal(idPremio) {
	
	PREMIOS_idPremio = idPremio;
	$('#myModalConfirmar').modal({
		show: true,
		backdrop: 'static'
	});
}

function borrarPremio() {
	
	window.location.assign('PremiosColaboradores?view=Borrar&idPremio=' + PREMIOS_idPremio);
}

function cerrarModal() {
	$('#myModalConfirmar').modal({
		show: false,
		backdrop: 'static'
	});
}
