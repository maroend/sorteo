/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
 */

var pag = 1;

var Listar = function() {
	"use strict";
	return {
		init : function() {

			handleListar()

		}
	}
}()

function onkeyup_colfield_check(e) {
	var enterKey = 13;
	if (e.which == enterKey) {
		
		pag=1;
		handleListar()
	}
}

function changeShow() {

	handleListar()
}

function getPag(page) {

	pag = page;
	handleListar()
}

//

var ListarModal = function() {
	"use strict";
	return {
		init : function() {

			handleListarModal();

		}
	}
}()

function onkeyup_colfield_checkModal(e) {
	var enterKey = 13;
	if (e.which == enterKey) {
		pag = 1;
		handleListarModal()
	}
}

function changeShowModal() {

	pag = 1;
	handleListarModal()
}

function getPagModal(page) {

	pag = page;
	handleListarModal()
}

var handleListarModal = function() {

	var show = $("#data-elements-lengthM").val();
	var search = $("#searchtableM").val();
	var idnicho = gup('idnicho');
	
	 //if(search!=""){ pag=1;}

	$('div.block').block(
					{
						css : {
							backgroundColor : 'transparent',
							color : '#336B86',
							border : "null"
						},
						overlayCSS : {
							backgroundColor : '#FFF'
						},
						message : '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
					});
	
	//var b64 = btoa('search:' + search);
	
	$.ajax({
		type : "GET",
		cache: false,
		url : "AsignacionColaboradores",
		data : "view=BuscarModal&pg=" + pag + "&show=" + show + "&search=" + btoa(search),
		success : function(msg) {
			$('div.block').unblock();
			$("#listresultados2").html(msg);
		}
	});

}

function AsignarColaborador(confirm) {

	$("#aceptarmodal").attr("onClick", "javascript:enviar();return false;");
	$('#idcolaboradores').val("");
	$('#myModal').modal('show');
	$("#searchtableM").val('');
	pag = 1;
	setTimeout("ListarModal.init()", 0);
	$("#listresultados2").html("");
	// $("#data-elements-lengthM").val();
	

}

jQuery.fn.getCheckboxValues = function() {
	var values = [];
	var i = 0;
	this.each(function() {
	
		values[i++] = this.id;
	});
	// devuelve un array con los checkboxes seleccionados
	return values;
}

function enviar() {

	var arr = $("input:checked").getCheckboxValues();

	if (arr.length == 0) {
		alert("Debe elegir un Colaborador");
	} else {

		$('#myModal').modal('hide');
		$('#idcolaboradores').val(arr);
		$("input:checkbox").prop('checked', false);

		GuardarAsignacion();

	}
}

function GuardarAsignacion() {

	var idcolaboradores = $('#idcolaboradores').val();
	var idnicho = gup('idnicho');

	$('div.block')
			.block(
					{
						css : {
							backgroundColor : 'transparent',
							color : '#336B86',
							border : "null"
						},
						overlayCSS : {
							backgroundColor : '#FFF'
						},
						message : '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
					});

	$.ajax({
				type : "POST",
				cache: false,
				url : "AsignacionColaboradores",
				data : {
					idcolaboradores : idcolaboradores,
					idnicho : idnicho
				},
				success : function(msg) {

					$('#idcolaboradores').val("");
					pag = 1;
					Listar.init();
					$('div.block').unblock();

					if (msg.trim() == "") {

						$.gritter.add({
							title : "ASIGNAR COLABORADOR",
							text : "Se ha asignado con exito el Colaborador!"
						});

					} else {
						$('html, body').animate({
							scrollTop : 0
						}, 10);
						$("#alerta").fadeIn();
						$("#alerta").removeClass();
						$("#alerta").addClass("alert alert-error");
						// $("#headAlerta").html("<font size=\"3\">¡Ups! ha
						// ocurrido un Error</font>");
						$("#bodyAlerta")
								.html(
										"<strong>No te alarmes</strong>!! al parecer no se asigno el Colaborador, por favor intentalo nuevamente.");

					}

				}
			});

}

var handleListar = function() {	


	// var show = $("#data-elements-length").val();
	var show = 10;
	var search = $("#searchtable").val();
	
	
	
	var idsorteo = gup('idsorteo');
	var idparalelo = gup('idparalelo');   
	var idnicho = gup('idnicho');
	var idsector = gup('idsector');
	
	 //if(search!=""){ pag=1;}  

	$('div.block')
			.block(
					{
						css : {
							backgroundColor : 'transparent',
							color : '#336B86',
							border : "null"
						},
						overlayCSS : {
							backgroundColor : '#FFF'
						},
						message : '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
					});
	
	
	//btoa(search)

	$.ajax({
		type : "GET",
		cache: false,
		url : "SorteosParalelosColaboradores",
		data : "view=Buscar&pg=" + pag + "&show=" + show + "&search=" + search + "&idsorteo=" + idsorteo+ "&idparalelo=" + idparalelo+ "&idnicho=" + idnicho+ "&idsector=" + idsector,
		success : function(msg) {
			
	         var response = msg.split('#%#');	         
	         
	       
			$("#listresultados").html(response[0]);
			$("#numColab").html(response[1]);			
		

			$('div.block').unblock();
			

			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();

		}

	});

}

function gup(name) {

	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var tmpURL = window.location.href;
	var results = regex.exec(tmpURL);
	if (results == null)
		return "";
	else
		return results[1];
}

function showOptions(idColaborador) {

	$('#idColaborador').val(idColaborador);

	var sorteo = idColaborador;

	$('#formOptions').show();
	//$('#formeliminarsorteo').hide();
	$('#cargaBoletos').hide();
	$('#formEliminarCarga').hide();
	$('#formEliminarColaborador').hide();
	$("#formeliminarsucces").hide();

	$('#myModalConfiguracion').modal({
		show : true,
		backdrop : 'static'
	});

	$.ajax({
		type : "GET",
		cache: false,
		url : "AsignacionColaboradores",
		data : "view=ExisteCarga&idColaborador=" + sorteo,
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

function showFormatoEliminaTalonariosDeColaborador() {

	$('#formOptions').hide();

	$('#formEliminarCarga').show();
	$('#deletecargasorteoconfirm').val('');

}

function showFormatoEliminaColaborador() {

	$('#formOptions').hide();

	$('#formEliminarColaborador').show();
	$('#inputPalabraELIMINAR_2_id').val('');

}

function eliminarBoletosYTalonarios() {

	var inputEliminar = $('#deletecargasorteoconfirm').val();

	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminaran todos los talonarios y boletos")) {
			$("#content_process").show();
			$("#content_sucess").hide();
	
			$('#formEliminarCarga').hide();
			$("#formeliminarsucces").show();
	
			$.ajax({
				type : "GET",
				cache: false,
				url : "AsignacionColaboradores",
				data : "view=EliminaCarga",
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
					handleListar();
					//refresh();
				}
			});
		}

	} else {
		alert("Debes ingresar correctamente la palabra ELIMINAR");
	}
}

function eliminarColaborador() {
	var inputEliminar = $('#inputPalabraELIMINAR_2_id').val();

	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminara el colaborador")) {
			$("#content_process").show();
			$("#content_sucess").hide();
	
			$('#formEliminarColaborador').hide();
			$("#formeliminarsucces").show();
	
			$.ajax({
				type : "GET",
				cache: false,
				url : "AsignacionColaboradores",
				data : "view=EliminaColaborador",
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
					handleListar();
					//refresh();
				}
			});
			
		}
	} else {
		alert("Debes ingresar correctamente la palabra ELIMINAR");
	}
}
/*
function refresh() {
	var show = "10";
	var search = $("#searchtable").val();
	pag = 1;

	$.ajax({
		type : "GET",
		cache: false,
		url : "AsignacionColaboradores",
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
//*/

//////////ASIGNACION DE TALONARIOS Y BOLETOS COLABORADOR//////////

var ListarModalTalonarios=function(){"use strict";return{init:function()
	{
		handleListarModalTalonarios();
		
	}}}()


	function onkeyup_colfield_checkModalTalonarios(e)
	{
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
			url: "AsignacionTalonariosColaboradores",
			data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,
			success: function(msg){  
				$('div.block').unblock(); 
				$("#listresultadosfc3").html(msg);
			}
		});
	}  


function asignaciontalonariosfc3(option){
	
	
	
	$('#opcionformatof').val(option);
	
	$('#myModalConfiguracion').modal('hide');
	
	if(option == 'I'){
		
		$('#txtformato1').html('<strong>FC3B; Formato de control 3B</strong>');
		$('#txtformato2').html('Se realizar&aacute; una asignaci&oacute;n de de talonarios incompletos a un colaborador.');
		$('#txtformato3').html('FC3B; Formato de control 3B  ');
		
		$('#boletosTab').show();
		
		
	}else{
		
		$('#txtformato1').html('<strong>FC3; Formato de control 3</strong>');
		$('#txtformato2').html('Se realizar&aacute; una asignaci&oacute;n de talonarios completos a un colaborador.');
		$('#txtformato3').html('FC3; Formato de control 3  ');
		
		$('#boletosTab').hide();
		
		
	}
	
	
	
	AsignarTalonario();
	
}



function AsignarTalonario()
{
	
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
	
	
	
	$('#myModalfc3').modal({
		show: true,
		backdrop: 'static'
		})
	
	
	$('#aceptarmodalfc3').removeAttr( "onClick" );
	$("#aceptarmodalfc3").attr("onClick","mostrarFormato()");
	$('#folio').val("");
	
	
	
	
	
}




function mostrarFormato(){
	
	
	$('#instrucciones').hide();
	$('#formato').show();
	$('#formtalonarios').hide();
	$('#asignacion').hide();
	
	$('#aceptarmodalfc3').removeAttr( "onClick" );
	$("#aceptarmodalfc3").attr("onClick","AsignarTalonarioboleto()");
	
		
}


function AsignarTalonarioboleto(){
	
    var folio = $('#folio').val();
	
    if(folio==""){
		alert("Debe ingresar el folio");
	 }else{
	
	$('#instrucciones').hide();
	$('#formato').hide();
	$('#formtalonarios').hide();
	$('#asignacion').show();
	
	$("#aceptarmodalfc3").attr("onClick", "javascript:enviarTalonarios();return false;");
	
	$('#asignacion').show();
	pag = 1;
	$("#searchtableMTalonarios").val('');
	ListarModalTalonarios.init()
	//ListarModalBoletos.init();
	
	
	
	 }
	
	
}


function enviarTalonarios()
{
	var arr = $("input:checked").getCheckboxValues();

	 if(arr.length==0){
		alert("Debe elegir un talonario");
	 }else{
		//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
		 $('#myModalfc3').modal('hide');
		 $('#idsTalonarios').val(arr);
		// alert("balabla "+arr);
		 $("input:checkbox").prop('checked', false);    		 
		 
		 GuardarAsignacionTalonariosColaboradores();
		 
		
	 }
 }



function GuardarAsignacionTalonariosColaboradores()
{
	var idsTalonarios = $('#idsTalonarios').val();	
	var opcionformatof = $('#opcionformatof').val();
	
	var idsorteo = gup('idsorteo');
	var idsector = gup('idsector');
    var idnicho = gup('idnicho');	
	var idcolaborador = $('#idColaborador').val();
	
		
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
		url: "AsignacionTalonariosColaboradores",
		data: { idsTalonarios: idsTalonarios,folio: folio, opcionformatof: opcionformatof,idsorteo:idsorteo,idsector:idsector,idnicho:idnicho,idcolaborador:idcolaborador},
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





//boletos



var ListarModalBoletos=function(){"use strict";return{init:function()
	{
		handleListarModalBoletos();
		
	}}}()


	function onkeyup_colfield_checkModalBoletos(e)
	{
		var enterKey = 13;
		if (e.which == enterKey){
			handleListarModalBoletos()
		}
	}

	function changeShowModalBoletos()
	{
		handleListarModalBoletos()
	}

	function getPagModalBoletos(page)
	{
		pag = page;
		handleListarModalBoletos()
	}

	var handleListarModalBoletos = function()
	{
		var show =	$("#data-elements-lengthMBoletos").val();
		var search =	$("#searchtableMBoletos").val();
		var idsorteo = gup('idsorteo');
		var idsector = gup('idsector');
		
		if(search!=""){ pag=1;}  
		 
		$('div.block').block({ 
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
		}); 

		$.ajax({ 
			type: "GET",
			cache: false,
			url: "AsignacionTalonariosColaboradores",
			data: "view=BuscarModalBoletos&pg="+pag+"&show="+show+"&search="+search+"&idsorteo="+idsorteo+"&idsector="+idsector,
			success: function(msg){  
				
				
				$('div.block').unblock(); 
				$("#listresultados3").html(msg);
			}
		});
	}  
	
	function enviarBoletos()
	{
		var arr = $("input:checked.cboletos").getCheckboxValues();
         //alert(arr);
		 if(arr.length==0){
			alert("Debe elegir un boleto");
		 }else{
			
			 $('#myModalfc3').modal('hide');
			 $('#idboletos').val(arr);
			
			GuardarAsignacionBoletos();
			 
			
		 }
	 }
	
	
	function GuardarAsignacionBoletos ()
	{
		var idboletos = $('#idboletos').val();		
		var folio = $('#folio').val();		
		
		var idsorteo = gup('idsorteo');
	    var idsector = gup('idsector');
		var idnicho = gup('idnicho');
		var idcolaborador = $('#idColaborador').val();
		
				
		var accion = 'aboletos';
		
		//alert(""+(idsTalonarios==null?"null":"vacio"));

		$('div.block').block({ 
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
		}); 

		
		$.ajax({
			type: "POST",
			cache: false,
			url: "AsignacionTalonariosColaboradores",
			data: { idboletos: idboletos,folio: folio,accion:accion,idsorteo:idsorteo,idsector:idsector,idnicho:idnicho,idcolaborador:idcolaborador},
			success: function(msg){
				
				
				$('#idboletos').val("");
				pag = 1;
				Listar.init();
				$('div.block').unblock();
				
				if(msg.trim()=="")
				{
					
					 $.gritter.add({title:"BOLETOS ASIGNADOS AL COLABORADOR",text:"Se ha asignado con exito los boletos!"});
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
	
	
	/*function AsignarBoletos(confirm)
	{
		
		$('#instruccionesheader').show();
		$('#instrucciones').show();
		$('#formato').hide();
		$('#formtalonarios').hide();
		$('#asignacion').hide();
		
		$('#myModal').modal({
			show: true,
			backdrop: 'static'
			})
		
		
	//	$('#aceptarmodal').removeAttr( "onClick" );
		//$("#aceptarmodal").attr("onClick","mostrarFormato()");
	//	$('#folio').val("");
		
		
		
		
		
	}*/
	
	//fin boletos
	
	
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
	
	
	
	/********************ELIMINAR******************/
	function borrarParalelosColaboradores(idColabortador,idsortoparalelo,idsorteonicho,idsector,idnicho) {
		
		if (confirm('Confirmar que desea eliminar el colaborador con sus boletos') == false ) {
			return;
		}
		
			
		
		$('div.block').block({ 
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> Eliminando..</h2>'
		}); 

		$.ajax({ 
			type: "GET",
			url: "SorteosParalelosColaboradores",
			data: "view=deleteColaboradorBoletos&idColabortador="+idColabortador+"&idsortoparalelo="+idsortoparalelo+"&idsorteonicho="+idsorteonicho+"&idsector="+idsector+"&idnicho="+idnicho,
			success: function(msg){  
			//$.gritter.add({title:"Sorteo Especial Nicho:",text:"No se puede borrar el Nicho porque Existen Colaboradores asignados al Nicho!"});			
				
				 handleListar();		
			}
		});		
		
	
	
	}
	
	
	
	
