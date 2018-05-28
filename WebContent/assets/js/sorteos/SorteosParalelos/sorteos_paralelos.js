/*
 * App Sorteo Anáhuac
 * Version: 1.0
 * Website: http://www.redanahuac.mx/app/sorteo
*/
var pag = 1;

var Listar=function(){"use strict";return{init:function(){
	handleListar();
}}}()

$(function () {
    $("#fecha_limite_pago").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'mm-dd-yy'
    });
});

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
	pag = page;
	handleListar()
}

var handleListar = function(){
	var show = 10;
	var search = $("#searchtable").val();
	var idsorteo = $('#sorteo_id').val();

	if(search != ""){ pag = 1; }

	$('div.block').block({
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: { backgroundColor: '#FFF' },
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	});

	$.ajax({
		type: "GET",
		cache: false,
		url: "SorteosParalelos",
		data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search+"&idsorteo="+idsorteo,
		success: function(msg) {
			var response = msg.split('#%#');
			$("#listresultados").html(response[0]);
			$("#numTal").html($('#numreg_id').val()); //response[1]);
			
		},
		complete: function(msg) {
			$('div.block').unblock();
			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();
		}
	});
}

var idParalelo = 0;

function modalAgregarParalelo(){
	idParalelo = 0;
	$('#nuevo_nombre_id').val('');
	$('#fecha_limite_pago').val('');
	$('#modalTitleSortParal').html('<h4 class="panel-title">Nuevo</h4>');
	$('#tipoGuardarEditar').val('0');
	$('#modalParalelo').modal('show');
}

function modalEditarParalelo(id){
	
	$.ajax({
		type: "GET",
		cache: false,
		url: "SorteosParalelos",
		data: "view=editarParalelo&idSorteoParalelo=" + id,
		success: function(msg)
		{
			var response = msg.split('#%#');
			//$('#fecha_limite_pago').val(response[0]);
			$('#modalTitleSortParal').html('<h4 class="panel-title">Editar</h4>');
			$('#tipoGuardarEditar').val('1');
			$('#sorteoIdParalelo').val(id);
			$("#fecha_limite_pago").datepicker('dateFormat', 'mm-dd-yy');
			$("#fecha_limite_pago").datepicker('setDate', response[0]);
			$('#nuevo_nombre_id').val(response[1]);
			$('#modalParalelo').modal('show');
		},
		error : function(data) { }		
	});
}

function btnGuardarParalelo()
{
	var sorteoId = "0";
	var tipo = $('#tipoGuardarEditar').val();
	var nombre = $('#nuevo_nombre_id').val();
	var fechaLimPago = $('#fecha_limite_pago').val();
	var b64;
	
	if (tipo == '1') {
		sorteoId = $('#sorteoIdParalelo').val();
		
		b64 = btoa(
				"EDIT:"
				+ ";sorteoId:" + sorteoId
				+ ";nombre:" + nombre
				+ ";fechaLimPago:" + fechaLimPago
				);
	} else {
		sorteoId = $('#sorteo_id').val();
		
		b64 = btoa(
				"INSERT:"
				+ ";nombre:" + nombre
				+ ";fechaLimPago:" + fechaLimPago
				);
	}

	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelos",
		data: { q: b64 },
		success: function(msg)
		{
			handleListar();
			$('#modalParalelo').modal('hide');
		},
		error : function(data) { }
	});	
}

function borrarParalelo(id, nombre) {
	if (confirm('Confirmar que desea eliminar el sorteo paralelo: \n"' + nombre + '"') == false ) {
		return;
	}
	var b64 = btoa("DELETE:" + id);
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelos",
		data: { q: b64 },
		success: function(msg)
		{
			
			if(msg.trim() == "EXISTENICHO"){				
				 $.gritter.add({title:"Sorteo Especial:",text:"No se puede borrar el Sorteo Especial porque Existen Nichos asignados al Sorteo Especial!"});			
			}
			else{ handleListar(); }	
			
			
		
		},
		error : function(data) { }
	});
}

function btnEditarSorteoParalelo(id, nombre, fechaLimPag) {

	var b64 = btoa("EDITAR:" + id);
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelos",
		data: { q: b64 },
		success: function(msg)
		{
			handleListar();
		},
		error : function(data) { }
	});
}

function ExportExcel() {	
	
	
	
	   var url = "SorteosParalelos?view=exportToExcel";
      
		var myWindow = window.open(url,'_blank');	
		myWindow.opener.document.focus(); 	
		//myWindow.document.write('<p>html to write...</p>');
	
    
}



function ActualizarColaboradores_SP(id,sorteoperalelo) {
	
	
	if (confirm('Confirmar que desea actualizar los colaboradores del sorteo paralelo : ' + sorteoperalelo + '') == false ) {
		return;
	}		
	    
	   // alert(idparalelo);
	
	var b64 = btoa(
			"ACTUALIZAR:"
			+ ";idparalelo:" + id
		//	+ ";idparalelonicho:" + id
		//	+ ";idsector:" + idsector
			//+ ";nichos:" + arrNichosA.join()
			);
	
	
		
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Actualizando...</h2>'
	}); 
	
	
		
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelos",
		data: { q: b64 },
		success: function(msg) {
			
			$('div.block').unblock(); 		
			
			$.gritter.add({
				title : "Correcto",
				text : '<div style="font-family:Calibri; color: green;"> Colaboradores Actualizados con exito! <div>',
			});
			
			
			//handleListar();
			
		},
	});
}


