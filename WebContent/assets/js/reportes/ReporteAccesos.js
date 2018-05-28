var pag = 1;


var Listar=function(){"use strict";return{init:function(){ 

	$("#fecha_ini").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'yy-mm-dd'
	});

	$("#fecha_fin").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'yy-mm-dd'
	});

	handleListar();
	
}}}()




function onkeyup_colfield_check(e){
    var enterKey = 13;
	if (e.which == enterKey){
		pag=1;
		handleListar()
	}
}

function changeShow(){
	pag=1;
	handleListar();
}

function getPag(page){
	
	$("#searchtable").val('');
	pag = page;
	handleListar();
}


function fecha_ini_onchange(){
	handleListar();
}
function fecha_fin_onchange(){
	handleListar();
}
/*
function Buscar(){
	handleListar();
}
*/
function RemoveFechaIni(){
	$('#fecha_ini').val('');
	handleListar();
}

function RemoveFechaFin(){
	$('#fecha_fin').val('');
	handleListar();
}


var handleListar = function(){

	var show = $("#data-elements-length").val();
	var search = $("#searchtable").val();
	var fecha_ini = $('#fecha_ini').val();
	var fecha_fin = $('#fecha_fin').val();

	
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	


	    
	$.ajax({
		type : "GET",
		cache : false,
		url : "ReporteAccesos",
		data : "view=Buscar&pg=" + pag + "&show=" + show + "&search=" + search + "&fecha_ini=" + fecha_ini + "&fecha_fin=" + fecha_fin,
		success : function(msg) {

			$('div.block').unblock();
			$("#listresultados").html(msg);

		},
		error : function(msg) {
			$('div.block').unblock();
		}

	});

}



function ExportExcel(){

	var show =	 $("#data-elements-length").val();
	var search =	$("#searchtable").val();
	var fecha_ini = $('#fecha_ini').val();
	var fecha_fin = $('#fecha_fin').val();

	location.href = "ReporteAccesos?view=ExportExcel&search=" + search + "&fecha_ini=" + fecha_ini + "&fecha_fin=" + fecha_fin;

}


