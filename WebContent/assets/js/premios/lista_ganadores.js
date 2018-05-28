var pag = 1;

function initialize()
{
	$(handleListar);
}

var GANADORES_idGanador;
function showModal(idGanador) {
	
	GANADORES_idGanador = idGanador;
	$('#myModalConfirmar').modal({
		show: true,
		backdrop: 'static'
	});
}

function borrarGanador() {
	
	window.location.assign('Ganadores?view=Borrar&idGanador=' + GANADORES_idGanador);
}

function cerrarModal() {
	$('#myModalConfirmar').modal({
		show: false,
		backdrop: 'static'
	});
}

// ----------- PAGINACION ----------
function onkeyup_colfield_check(e){
	var enterKey = 13;
	if (e.which == enterKey){
		handleListar()
	}
}

function changeShow(){
	
	handleListar()
}

function getPag(page){
	
    $("#searchtable").val('');
	pag = page;
	handleListar()
}

var handleListar = function(){
	
	var show =	10;//$("#data-elements-length").val();
	var search =	$("#searchtable").val();
	
	if(search!=""){ pag=1; }

	$.ajax({
		type: 'GET',
		url: 'Ganadores',
		data: 'view=Buscar&pg='+pag+'&show='+show+'&search='+search+'&noused='+Math.random(),  
		success: function(msg){
			$('#gallery').html(msg);
		}
	});
}


