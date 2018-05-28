/*   
App Sorteo Anáhuac
Version: 1.0
Author: Jose Carlos Ruiz Garcia
Website: http://www.redanahuac.mx/app/sorteo
*/


var pag = 1;

var Listar=function(){"use strict";return{init:function(){ 
	
	handleListar()
	
	
}}}()



function onkeyup_colfield_check(e)
{
    var enterKey = 13;
    if (e.which == enterKey)
    	handleListar();
}

function changeShow()
{
	handleListar()
}

function getPag(page)
{
	pag = page;
	handleListar();
}



var handleListar = function()
{
	var show =	$("#data-elements-length").val();
	var search =	$("#searchtable").val();
    
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 
	
    
	$.ajax({
		type: "GET",
		cache: false,
		url: "InformesDeError.do",
		data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,
		success: function(msg)
		{
			$('div.block').unblock();
			$("#listresultados").html(msg);
		},
		error: function(e) { }
	});
}

function muestraContenido(fileName) {
	/*
	$('#div_file_id').html("  <object style=\"width: 100%; height: 100%; background-color:#DDDDDD;\" type=\"text/plain\" data=\"http://localhost:8080/sorteo/LOG_EXCEPTIONS/"+fileName+"\"></object> ");
	*/
	$.ajax({
		type: "GET",
		cache: false,
		url: "InformesDeError.do",
		data: "view=Leer&file="+fileName+"&noused="+Math.random(),
		success: function(msg)
		{
			$('#div_file_id').html(msg);
		},
		error: function(e) { }
	});

	$('#modalContenido').modal({
		show : true,
		backdrop : 'static'
	});

}




