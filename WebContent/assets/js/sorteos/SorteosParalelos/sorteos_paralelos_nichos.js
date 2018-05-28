/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
*/

var pag = 1;

var Listar=function(){"use strict";return{init:function(){
	handleListar()
}}}();

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

// ***************************************************modal FC2 TALONARIOS
var ListarModalTalonarios=function(){"use strict";return{init:function(){
	handleListarModalTalonarios()
}}}()
    
function onkeyup_colfield_checkModalTalonarios(e) {
	var enterKey = 13;
	if (e.which == enterKey){
		handleListarModalTalonarios()
	}
}

function changeShowModalTalonarios()
{
	handleListarModalTalonarios()
}

function getPagModalTalonarios(page)
{
	pag = page;
	handleListarModalTalonarios()
}
    
jQuery.fn.getCheckboxValues = function(){
    var values = [];
    var i = 0;
    this.each(function(){
        // guarda los valores en un array
        values[i++] = this.id;
    });
    // devuelve un array con los checkboxes seleccionados
    return values;
}

var handleListar = function() {	
	// var show = $("#data-elements-length").val();
	var show = 10;
	var search = $("#searchtable").val();
	var idsorteo = gup('idsorteo');
	var idparalelo = gup('idparalelo');
	
	if(search!=""){ pag=1; }
	
	$('div.block').block({
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	});
	
	$.ajax({
		type: "GET",
		cache: false,
		url: "SorteosParalelosNichos",
		data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search+"&idsorteo="+idsorteo+"&idparalelo="+idparalelo,
		success: function(msg){
			
			var response = msg.split('#%#');

			$("#listresultados").html(response[0]);
			$("#numNichos").html(response[1]);	
			
		//	alert(response[2]);
		
			if( response[2].trim() == "EXISTE" ){
				//alert('entro:true');  
				//$("#crearboletosI").prop( 'disabled', true );
				$("#crearboletosI").attr('disabled','disabled');
			}
			else{
				//alert('entro:false'); 
				$("#crearboletosI").removeAttr('disabled');
			}
			
			

			$('div.block').unblock();

			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();
		}
	});
}

function gup(name){	
	var regexS = "[\\?&]"+name+"=([^&#]*)";
	var regex = new RegExp ( regexS );
	var tmpURL = window.location.href;
	var results = regex.exec( tmpURL );
	if( results == null )
		return"";
	else
		return results[1];
}
// -------------- CONFIGURACION

function showOptions(idNicho) {
	$('#nichoactual').val(idNicho);
	$('#idNicho').val(idNicho);

	var tmp_nicho = idNicho;

	$('#formConfig').show();
	//$('#formeliminarsorteo').hide();
	$('#cargaBoletos').hide();
	$('#formEliminarCargaNicho').hide();
	$('#formEliminarNicho').hide();
	$("#formeliminarsucces").hide();

	$('#myModalConfiguracion').modal({
		show : true,
		backdrop : 'static'
	});

	$.ajax({
		type : "GET",
		cache: false,
		url : "AsignacionNichos",
		data : "view=ExisteCarga&idNicho=" + tmp_nicho,
		success : function(msg) {

			if (msg.trim() == "TRUE") {

				$('#btncargaboletos').removeClass("btn btn-success btn-lg")
						.addClass("btn btn-success btn-lg disabled");
				$('#btndeletecargaboletos').removeClass(
						"btn btn-success btn-lg disabled").addClass(
						"btn btn-success btn-lg");
				$('#btneliminarsorteo').removeClass("btn btn-success btn-lg")
						.addClass("btn btn-success btn-lg disabled");

			} else {

				$('#btncargaboletos').removeClass(
						"btn btn-success btn-lg disabled").addClass(
						"btn btn-success btn-lg");
				$('#btndeletecargaboletos').removeClass(
						"btn btn-success btn-lg").addClass(
						"btn btn-success btn-lg disabled");
				$('#btneliminarsorteo').removeClass(
						"btn btn-success btn-lg disabled").addClass(
						"btn btn-success btn-lg");
			}
		}
	});
}

function showFormatoEliminaTalonariosDeNicho() {

	$('#formConfig').hide();
	$('#formEliminarCargaNicho').show();
	$('#inputPalabraELIMINAR_id').val('');
}

function showFormatoEliminaNicho() {

	$('#formConfig').hide();
	$('#formEliminarNicho').show();
	$('#inputPalabraELIMINAR_2_id').val('');
}

function eliminarBoletosYTalonarios() {

	var inputEliminar = $('#inputPalabraELIMINAR_id').val();
	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminaran todos los talonarios y boletos")) {
			$("#content_process").show();
			$("#content_sucess").hide();
			$('#formEliminarCargaNicho').hide();
			$("#formeliminarsucces").show();
	
			$.ajax({
				type : "GET",
				cache: false,
				url : "AsignacionNichos",
				data : "view=EliminaCarga",
				success : function(msg) {
					// Error
					if (msg.trim() == "0") {
						$("#titlesuccess").html('<h1>Ocurrio un problema en el proceso de borrado.</h1><br>');
						$("#content_process").hide();
						$("#content_sucess").hide();
					}
					else {
						$("#titlesuccess").html('<h1>Se ha eliminado corretamente.</h1><br>');
						$("#content_process").hide();
						$("#content_sucess").show();
					}
					refresh();
				}
			});
			
		}
	} else {
		alert("Debes ingresar correctamente la palabra ELIMINAR");
	}
}

function eliminarNicho() {
	var inputEliminar = $('#inputPalabraELIMINAR_2_id').val();

	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminara el nicho")) {
			$("#content_process").show();
			$("#content_sucess").hide();
	
			$('#formEliminarNicho').hide();
			$("#formeliminarsucces").show();
	
			$.ajax({
				type : "GET",
				cache: false,
				url : "AsignacionNichos",
				data : "view=EliminaNicho",
				success : function(msg) {
					// Error
					if (msg.trim() == "0"){
						$("#titlesuccess").html('<h1>Ocurrio un problema en el proceso de borrado.</h1><br>');
						$("#content_process").hide();
						$("#content_sucess").hide();
					}
					else {
						$("#titlesuccess").html('<h1>Se ha eliminado corretamente.</h1><br>');
						$("#content_process").hide();
						$("#content_sucess").show();
					}
					refresh();
				}
			});
			
		}
	} else {
		alert("Debes ingresar correctamente la palabra ELIMINAR");
	}
}

function refresh() {

	var show = "10";
	var search = $("#searchtable").val();
	pag = 1;

	$.ajax({
		type : "GET",
		cache: false,
		url : "AsignacionNichos",
		data : "view=Buscar&pg=" + pag + "&show=" + show + "&search=" + search,
		success : function(msg) {

			 var response = msg.split('#%#');  
				
				$("#listresultados").html(response[0]);
				$("#numTal").html(response[1]);

			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();

		}

	});
}

/* ***********************************************************MODAL ASIGNACION DE TALONARIOS NICHOS*********************************************** */

function AsignarTalonario(confirm)
{		
	$('#myModalConfiguracion').modal('hide');
	
	$('#instruccionesheader').show();
	$('#instrucciones').show();
	$('#formato').hide();
	$('#formtalonarios').hide();
	$('#asignacion').hide();
	
	//bol
	$('#boletosTab').removeAttr( "class" );
	$("#bollink").attr("aria-expanded","false");	
	$('#default-tab-2').removeAttr( "tab-pane fade active in" );
	$("#default-tab-2").attr("class","tab-pane fade");	
	//tal
	$("#talonariosTab").attr("class","active");
	$("#tallink").attr("aria-expanded","true");
	$("#default-tab-1").attr("class","tab-pane fade active in");	
	
	$('#myModalFC2').modal({
		show: true,
		backdrop: 'static'
		})
	
	$('#aceptarmodalFC2').removeAttr( "onClick" );
	$("#aceptarmodalFC2").attr("onClick","mostrarFormato()");
	$('#folio').val("");	
}

function mostrarFormato(){
	
	$('#instruccionesheader').hide();
	$('#instrucciones').hide();
	$('#formato').show();
	$('#formtalonarios').hide();
	$('#asignacion').hide();
	
	$('#aceptarmodalFC2').removeAttr( "onClick" );
	$("#aceptarmodalFC2").attr("onClick","AsignarTalonarioboleto()");		
}

function AsignarTalonarioboleto(){
	var folio = $('#folio').val();
	
    if(folio==""){
		alert("Debe ingresar el folio");
	} else {
		$('#instruccionesheader').hide();
		$('#instrucciones').hide();
		$('#formato').hide();
		$('#formtalonarios').hide();	
		$("#aceptarmodalFC2").attr("onClick", "javascript:enviarTalonarios();return false;");
		
		$("#searchtableMTalonarios").val('');
		pag = 1;
		$('#asignacion').show();
		ListarModalTalonarios.init()
		//ListarModalBoletos.init();
	}
}

var handleListarModalTalonarios = function()
{
	var show =	$("#data-elements-lengthMTalonarios").val();
	var search =	$("#searchtableMTalonarios").val();
	
	 if(search!=""){ pag=1;}  

	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 

	$.ajax({ 
		type: "GET",
		cache: false,
		url: "AsignacionTalonariosNichos",
		data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,
		success: function(msg){  
			$('div.block').unblock(); 
			$("#listresultadosFC2").html(msg);
		}
	});
} 

function enviarTalonarios()
{
	var arr = $("input:checked").getCheckboxValues();

	if(arr.length==0){
		alert("Debe elegir un talonario");
	} else {
		$('#myModalFC2').modal('hide');
		$('#idsTalonarios').val(arr);
		$("input:checkbox").prop('checked', false);
		
		GuardarAsignacionTalonarios();
	}
}


function GuardarAsignacionTalonarios ()
{
	var idsTalonarios = $('#idsTalonarios').val();
	var idsorteo = gup('idsorteo');
	var idnicho = $('#nichoactual').val();
	
	var folio = $('#folio').val();
	
	//alert(""+(idsTalonarios==null?"null":"vacio"));

	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
	}); 


	
	$.ajax({
		type: "POST",
		cache: false,
		url: "AsignacionTalonariosNichos",
		data: { idsTalonarios: idsTalonarios, idnicho: idnicho, idsorteo: idsorteo,folio: folio},
		success: function(msg){
			
			$('#idsTalonarios').val("");
			pag = 1;
			Listar.init();
			$('div.block').unblock();
			
			if(msg.trim()=="")
			{
				
				 $.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Se ha asignado con exito el talonario!"});
			}
			else{
					$('html, body').animate({scrollTop:0}, 10);
 					$("#alerta").fadeIn();
 					$("#alerta").removeClass();
 	                $("#alerta").addClass("alert alert-error");
 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se asigno el talonario, por favor intentalo nuevamente.");     					
			}
			
		},
		error: function(msg) {
			//alert("Error");
		}
	});
}

var handleListarModalBoletos = function()
{
	var show =	$("#data-elements-lengthMBoletos").val();
	var search =	$("#searchtableMBoletos").val();
	var idsorteo = gup('idsorteo');
	//var idnicho = gup('idnicho');
	
	 if(search!=""){ pag=1;}  
	 
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 

	$.ajax({ 
		type: "GET",
		cache: false,
		url: "AsignacionTalonariosNichos",
		data: "view=BuscarModalBoletos&pg="+pag+"&show="+show+"&search="+search+"&idsorteo="+idsorteo,
		success: function(msg){  
			
			
			$('div.block').unblock(); 
			$("#listresultados3").html(msg);
		}
	});
}  

function enviarBoletos()
{
	var arr = $("input:checked.cboletos").getCheckboxValues();
    // alert(arr);
	 if(arr.length==0){
		alert("Debe elegir un boleto");
	 }else{
		
		 $('#myModalFC2').modal('hide');
		 $('#idboletos').val(arr);
		
		GuardarAsignacionBoletos();
		 
		
	 }
 }


function GuardarAsignacionBoletos ()
{
	var idboletos = $('#idboletos').val();
	var folio = $('#folio').val();
	var idsorteo = gup('idsorteo');	
	var idnicho = $('#nichoactual').val();
	
	var accion = 'aboletos';	
	

	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
	}); 
	
	$.ajax({
		type: "POST",
		cache: false,
		url: "AsignacionTalonariosNichos",
		data: { idboletos: idboletos, idsorteo: idsorteo, idnicho: idnicho,folio: folio,accion:accion},
		success: function(msg){
			
			
			$('#idboletos').val("");
			pag = 1;
			Listar.init();
			$('div.block').unblock();
			
			if(msg.trim()=="")
			{
				
				 $.gritter.add({title:"BOLETOS ASIGNADOS AL NICHO",text:"Se ha asignado con exito los boletos!"});
			}
			else{
					$('html, body').animate({scrollTop:0}, 10);
 					$("#alerta").fadeIn();
 					$("#alerta").removeClass();
 	                $("#alerta").addClass("alert alert-error");
 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se asigno los boletos, por favor intentalo nuevamente.");     					
			}
			
		},
		error: function(msg) {
			//alert("Error");
		}
	});
}



function seleccionarTodo(id){
    
	 if(document.getElementById('checkboxall'+id).checked==1)
	   {
	   
	   for (i=0;i<document.forms.namedItem("formulario"+id).elements.length;i++){ 
	      if(document.forms.namedItem("formulario"+id).elements[i].type == "checkbox"){ 
	         document.forms.namedItem("formulario"+id).elements[i].checked=1 
			 }
			 }
	   }
	    else{
		
		for (i=0;i<document.forms.namedItem("formulario"+id).elements.length;i++) 
	      if(document.forms.namedItem("formulario"+id).elements[i].type == "checkbox") 
	         document.forms.namedItem("formulario"+id).elements[i].checked=0 
		}
	}


//_________________________________________________________________

var idParalelo = 0;

function modalEditarParalelo(id)
{
	idParalelo = id;
	$('#modalSorteos').modal('show');
	editarParalelo();
}

function editarParalelo()
{
	/*
	$.ajax({
		type: "GET",
		cache: false,
		url: "SorteosParalelos",
		data: { view: "editarParalelo", idparalelo: idParalelo },
		success: function(msg)
		{
			if (0 <= msg.indexOf('NOMBRE_PARALELO:')) {
				var response = msg.split(':');
				
				$('#nombre_id').html(response[1]);
				
				makeSectoresHTML(
					function() {
						selectSector_onchange();
					}
				);
			}
			else
				$('#modalSorteos').modal('hide');
		},
		error : function(data) { $('#modalSorteos').modal('hide'); }
	});
	//*/
	makeSectoresHTML(
		function() {
			selectSector_onchange();
		}
	);

}

var arrayCheckNichos = [[false,0],[false,0]];

function checkButton(index, check) {
	
	if (check==true){
		//$('#btn_nicho_'+index).removeClass('btn-white');
		//$('#btn_nicho_'+index).addClass('btn-inverse');
		$('#icon_nicho_'+index).removeClass('fa-square-o');
		$('#icon_nicho_'+index).addClass('fa-check-square-o');
	}
	else{
		//$('#btn_nicho_'+index).removeClass('btn-inverse');
		//$('#btn_nicho_'+index).addClass('btn-white');
		$('#icon_nicho_'+index).removeClass('fa-check-square-o');
		$('#icon_nicho_'+index).addClass('fa-square-o');
	}
}

function makeSectoresHTML(f_success)
{
	$.ajax({
		type: "GET",
		cache: false,
		url: 'SorteosParalelosNichos',
		data: { view: 'makeSectoresHTML' },
		success: function(msg)
		{
			$('#select_sector_id').html(msg);
			if (f_success != null && typeof f_success == 'function')
				f_success(msg);
		},
		error : function(data) { }
	});
}

function selectSector_onchange()
{
	var idsector = $('#select_sector_id').val();
	
	
	//alert(idsector);
	$.ajax({
		type: "GET",
		cache: false,
		url: "SorteosParalelosNichos",
		data: { view: "makeNichosHTML", idsector: idsector, idparalelo : idParalelo },
		success: function(msg)
		{
			$('#divNichos_id').html(msg);
		},
		error : function(data) { }
	});
}

function marcarNicho(index) {
	if (arrayCheckNichos[index][0] == false)
		arrayCheckNichos[index][0] = true;
	else
		arrayCheckNichos[index][0] = false;
	
	checkButton(index, arrayCheckNichos[index][0]);
}

function btnGuardarNichos() {
	
	var iscasilla = false;
	var idsector = $('#select_sector_id').val();
	var arrNichos = new Array();
	for(var i=0; i<arrayCheckNichos.length; i++)
		if (arrayCheckNichos[i][0] == true){
			arrNichos.push(arrayCheckNichos[i][1])
			iscasilla = true;
		}
		
			
		if (!iscasilla) {
	            alert('Debes seleccionar una casilla para el nicho');
	            return;
	     }	
		
	//	alert(arrNichos.join());
		
	var b64 = btoa(
			"GUARDAR:"
			+ ";idparalelo:" + idParalelo
			+ ";idsector:" + idsector
			+ ";nichos:" + arrNichos.join()
			);
	
	
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> ejecutandose..</h2>'
	}); 
	
	
		
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelosNichos",
		data: { q: b64 },
		success: function(msg) {
			
			$('div.block').unblock(); 
			selectSector_onchange();
			
			$.gritter.add({
				title : "Correcto",
				text : '<div style="font-family:Calibri; color: green;"> Nichos asignados con exito! <div>',
			});
			
			
			handleListar();
			
		},
	});
}


$( "#crearboletosI" ).click(function() {
	$("#checkboxNC").val('0');
	ejecutarSP();    
});

$( "#crearboletosF" ).click(function() {
	$("#checkboxNC").val('1');
	ejecutarSP();    
});


function ejecutarSP()
{	
	
	//if( $("#checkboxNC").is(':checked') ){		
	if( $("#checkboxNC").val() == '1' ){
		//$("#checkboxNC").val('1');
		if( confirm('Se genera el folio de los boletos apartir del ultimo folio generado en el proceso anterior.') == false  )
			return false;	
		
	}else{
		
		//$("#checkboxNC").val('0');
		if( confirm('Estas seguro de ejecutar el proceso para crear los folios de los boletos desde el inicio.') == false  )
			return false;
		
			
	}		
	
	
	var bandera = $("#checkboxNC").val();
	
	//alert(bandera);
	
	 var IDparalelo = gup('idparalelo');	
	
	
	var b64 = btoa(
			"EJECUTAR:"
			+ ";idparalelo:" + IDparalelo
			+ ";flag:" + bandera
			);
	
	
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> ejecutandose..</h2>'
	}); 
	
	
		
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelosNichos",
		data: { q: b64 },
		success: function(msg) {	
			
			
			$('div.block').unblock(); 
			
			$.gritter.add({
				title : "Correcto",
				text : '<div style="font-family:Calibri; color: green;">Se han creado los boletos con exito! <div>',
			});
			
		},
	});



}




function borrarParalelosNichos(id,nicho,idsector,idnicho) {
	if (confirm('Confirmar que desea eliminar el sorteo paralelo nicho: ' + nicho + '') == false ) {
		return;
	}
	
	//var idParalelo = gup('idparalelo');
	
	/*var b64 = btoa(
			"DELETE:"
			+ ";idparalelo:" + idParalelo
			+ ";idparaleloNicho:" + id			
			);*/
	
	
	var b64 = btoa("DELETE:" + id);
	
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelosNichos",
		data: { q: b64 },
		success: function(msg)
		{	
			if(msg.trim() == "EXISTECOLABO"){				
				 $.gritter.add({title:"Sorteo Especial Nicho:",text:"No se puede borrar el Nicho porque Existen Colaboradores asignados al Nicho!"});			
			}
			else{
					selectSector_onchange();
					handleListar();
		   }			
			
		},
		error : function(data) { }
	});
}




function ActualizarColaboradores_SPNichos(id,nicho,idsector,idnicho) {
	
	
	if (confirm('Confirmar que desea actualizar los colaboradores del sorteo paralelo en el nicho: ' + nicho + '') == false ) {
		return;
	}	
	
	    var arrNichosA = new Array();	
	    arrNichosA.push(idnicho)	
	
	
	var IDparalelo = gup('idparalelo');	
	    
	   // alert(idparalelo);
	
	var b64 = btoa(
			"ACTUALIZAR:"
			+ ";idparalelo:" + IDparalelo
			+ ";idparalelonicho:" + id
			+ ";idsector:" + idsector
			+ ";nichos:" + arrNichosA.join()
			);
	
	
		
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Actualizando...</h2>'
	}); 
	
	
		
	$.ajax({
		type: "POST",
		cache: false,
		url: "SorteosParalelosNichos",
		data: { q: b64 },
		success: function(msg) {
			
			$('div.block').unblock(); 		
			
			$.gritter.add({
				title : "Correcto",
				text : '<div style="font-family:Calibri; color: green;"> Colaboradores Actualizados con exito! <div>',
			});
			
			
			handleListar();
			
		},
	});
}





