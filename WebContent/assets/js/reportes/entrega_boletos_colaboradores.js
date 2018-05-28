/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
*/

var Listar=function(){"use strict";return{init:function(){ 
	
	
}}}()


$(document).ready(function()
{
	$('#alerta').hide();
	$('#div_img_loading_id').hide();
	handlebuscarSectoresInicio();
});


//busca sectores al comienzo de cargar la pagina    

function handlebuscarSectoresInicio(){
	
	var usuario = $("#usuario").val();
	var idsectorU = $("#idsectorU").val();
	
	
	$('#idsectorb').prop('disabled',true);
	$('#idnichob').prop('disabled',true);
	$('#btnExport').hide();
	
    
	$.ajax({
    	    type: "GET",
    	    url: "Colaboradores.do",
    	    data: "view=BuscarSectorInicio"+"&idsectorU="+idsectorU+"&usuario="+usuario,
    	    complete: function(msg){
				$('#idsectorb').prop('disabled',false);
				$('#idnichob').prop('disabled',false);
				$('#btnExport').show();
    	    },
    	    success: function(msg){
    	    	
    	    	$("#idsectorb").html(msg);
    	    	handlebuscarNichosSectorBuscar();
    	    	$('div.block').unblock();
    	    	
    	    }
	});

}



$('#idsectorb').change(function (e) {
	e.preventDefault();
	handlebuscarNichosSectorBuscar();
});

$('#idnichob').change(function (e) {
	e.preventDefault();
});



function handlebuscarNichosSectorBuscar(){
	
	idsector = $('#idsectorb').val();
	
	if (idsector != "") {
		
		$('#idsectorb').prop('disabled',true);
		$('#idnichob').prop('disabled',true);
		$('#btnExport').hide();
		
		
		$.ajax({
			type: "GET",
			url: "Colaboradores.do",
			data: "view=buscarNichosSectorInicio&idsector="+idsector,
			complete:
			function(msg){
				$('#idsectorb').prop('disabled',false);
				$('#idnichob').prop('disabled',false);
				$('#btnExport').show();
			},
			success: function(msg){
				
				$("#idnichob").html(msg);
			}
		});
	
	} else {
		
		$("#idnichob").html("<option value=\"\">Todos</option>");
		
	}
	
}


//OBTENER VALORES
jQuery.fn.getCheckboxValues = function(){
	var values = [];
	var i = 0;
	this.each(function() {
		// guarda los valores en un array
		values[i++] = this.id;
	});
	// devuelve un array con los checkboxes seleccionados
	return values;
}


function ExportExcel()
{
	var idsectorb = $("#idsectorb").val();
	var idnichob = $("#idnichob").val();
	
	$('#div_combos_id').hide();
	$('#div_img_loading_id').show();
	$('#div_porcentaje_id').html("0%");
	$('#progressbar_loading').val(0);
	$('#alerta').hide();
	resetTimer();
	
	location.href="EntregaBoletosColaboradores.do?view=ExportExcel&idsectorb="+idsectorb+"&idnichob="+idnichob;
}


//___________________________________________________________________________
var timerProgressBar=null;
function resetTimer(){
	closeTimer();
	timerProgressBar = setInterval(timerProgressBar_Tick, 1000);
}
function closeTimer(){
	if(timerProgressBar!=null){
		clearTimeout(timerProgressBar);
		timerProgressBar=null;
	}
}
function timerProgressBar_Tick(){
	
	closeTimer();
	$.ajax({
 	    type: 'GET',
 	    url: 'ProgressBar',
 	    data: '',
		complete: function(msg) { },
 	    success: function(msg)
		{
			if(msg != null && msg.length<5){
				var porcentaje = parseInt(msg, 10);
				
				$('#div_porcentaje_id').html(porcentaje+" %");
				$('#progressbar_loading').val(porcentaje);
				
				if(porcentaje==100){
					$('#div_combos_id').show();
					$('#div_img_loading_id').hide();
					$('#alerta').show();
					closeTimer();
				}
				else{
					resetTimer();
				}
			}
			else{
				$('#div_combos_id').show();
				$('#div_img_loading_id').hide();
			}
		},
		error: function(msg) {
			$('#div_combos_id').show();
			$('#div_img_loading_id').hide();
		}
	});
}
// ___________________________________________________________________________

