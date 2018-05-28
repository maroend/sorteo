var pag = 1;

var global = 'false';
var elem = document.querySelector('.js-switch');
var init = new Switchery(elem);
var handleBootstrapWizards=function(){"use strict";$("#wizard").bwizard()};

var GANADORES_idPremio;
var GANADORES_idPremioColaborador;
var last_sender_id = null;

var GANADORES_idComprador;


var FormWizard=function(){"use strict";return{init:function(){
	handleBootstrapWizards();
	GANADORES_idPremio = -1;
	GANADORES_idPremioColaborador = -1;
	GANADORES_idComprador = -1;
	$(consultaPremios);
}}}()


var guardarPremio=function(){"use strict";return{init:function(){
	
	handlecontrollersave();
	
}}}()



$(function(){
	
	//$('previous').attr('aria-disabled',true);  
	
});//End function jquery




$("#wizard").bwizard({validating:function(e,t){

	if(t.index==0){
		if (GANADORES_idPremio == -1)
			return false;
		   
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-1"))
			return false;
		
		$(consultaCompradores);
	}
	else if(t.index==1){
		
		console.log('t.nextIndex = '+t.nextIndex);
		
		if(t.nextIndex == t.index + 1) {
			if(GANADORES_idComprador == -1)
				return false;
			
			if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-2"))
				return false;

			//console.log('guardarPremio.init()');
			guardarPremio.init();
		}
	}
}});

var handlecontrollersave = function(){
	
	$.ajax({
		type: 'POST',  
		url: 'Ganadores', 
		data: { accion: 'insertar',
			idPremio: GANADORES_idPremio,
			idComprador: GANADORES_idComprador,
			idPremioColaborador: GANADORES_idPremioColaborador},
		success: function(msg){
			//alert('success:' + msg);
		},
		error: function(msg){
			//alert('error:' + msg);
		},
		onerror: function(msg){
			//alert('onerror:' + msg);
		}
	});

}

function consultaPremios() {

	$.ajax({
		type: 'GET',
		url: 'Ganadores',
		data: 'view=BuscarPremios&pg=1&show=100',
		async: false,
		success:
   	    	function(msg){
			
				$('#list_resultados_premios').html(msg);
   	    	}
   	});
}

function selectPremio(idPremio, idPremioColaborador) {
	GANADORES_idPremio = idPremio;
	GANADORES_idPremioColaborador = idPremioColaborador;
	
	var clase = $('#selectPremio_'+idPremio).attr('class'); 
	$('#selectPremio_'+idPremio).removeClass( clase ).addClass("btn btn-warning m-r-5");
	
	if(last_sender_id != null) {
		var clase_2 = $(last_sender_id).attr('class'); 
		$(last_sender_id).removeClass( clase_2 ).addClass("btn btn-primary m-r-5");
	}
	
	last_sender_id = '#selectPremio_'+idPremio;
	
}

function consultaCompradores() {
	changeShow();
}

function seleccionaComprador(str_idComprador){
	GANADORES_idComprador = str_idComprador;
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
	GANADORES_idComprador = -1;
	
	var show =	$("#data-elements-length").val();
	var search =	$("#searchtable").val();
	
	if(search!=""){ pag=1;}

	$.ajax({
		type: "GET",
		url: "Ganadores",
		data: "view=BuscarCompradores&pg="+pag+"&show="+show+"&search="+search,  
		success: function(msg){  
			//$('div.block').unblock();
			
			$("#list_resultados_compradores").html(msg);
			
		}
	   
	});
}

