/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
*/

var pag = 1;
var pag2 = 1;

var page2 = 1;


var Listar=function(){"use strict";return{init:function(){
    
	
	handleListar();
	
}}}()

function onkeyup_colfield_check(e) {
	var enterKey = 13;
	if (e.which == enterKey) {
		pag2 = 1;
		handleListar();
	}
}
function btnBuscar_click() {
	pag2 = 1;
	handleListar();
}

function changeShow() {

	handleListar();
}

function getPag(page) {
	
	pag2 = page;
	
	page2 = page;
	
	handleListar();
}


//
    
    var ListarModalSector=function(){"use strict";return{init:function(){ 
        
    	
    	handleListarModalSector();
    	
    }}}()




    function asignarboletosfc1(){
    	
    	
    	
    	$('#myModalConfiguracion').modal('hide');
    	
    	AsignarTalonario();
    }
    


    function onkeyup_colfield_checkModalSector(e){
        var enterKey = 13;
            if (e.which == enterKey){
            	handleListarModalSector()
            }
    }

        function changeShowModalSector(){
        	
        	handleListarModalSector()
        }

        function getPagModalSector(page){
    	
    	pag = page;
    	handleListarModalSector()
        }
        
        
        var handleListarModalSector = function(){
        	
        	 var show =	$("#data-elements-lengthMSector").val();
             var search =	$("#searchtableMSector").val();
             
           
             if(search!=""){ pag=1;}  
             
             
       	  $('div.block').block({ 
       		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
       	        overlayCSS: {backgroundColor: '#FFF'},
       	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
             }); 
       	
           
       	$.ajax({ 
       	    type: "GET",  
       	    cache: false,
       	    url: "SectoresAsignados",  
       	    data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,  
       	    success: function(msg){  
       	    	
       	    	
       	    	$('div.block').unblock(); 
       	    	$("#listresultados2").html(msg);
       	    	
       	    	
       	           	    	
       	    	
       	               } 
       	   
       	           });
           	
           }  
        

    
    function AsignarSector(confirm){ 
   
    	$("#aceptarmodal").attr("onClick", "javascript:enviar();return false;");
    	$('#searchtableMSector').val("");
		pag=1;
        $('#myModal').modal('show');
        setTimeout("ListarModalSector.init()",0);
        $("#listresultados2").html("");
     
       
        
    	
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
    
    
    
    function enviar(){
    	
    	var arr = $("input:checked").getCheckboxValues();

    	 if(arr.length==0){
    		alert("Debe elegir un sector");
    	 }else{
    		//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
    		 $('#myModal').modal('hide');
    		 $('#idsectores').val(arr);
    		 $("input:checkbox").prop('checked', false);    		 
    		 
    		 GuardarAsignacionSorteos();
    		 
    		
    	 }
     }
    
    
    
    
    
    
    
    
function GuardarAsignacionSorteos (){
    	
    	var idsectores = $('#idsectores').val();   
    	var idsorteo = gup('idsorteo');
    	
    	
    	
    	$('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
       }); 
 	
    
    	
    	$.ajax({ 
    	    type: "POST",  
    	    cache: false,
    	    url: "SectoresAsignados",  
    	    data: { idsectores: idsectores, idsorteo: idsorteo},  
    	    success: function(msg){  	    
    	    	
    	    	 pag2=1;
    	    	$('#idsectores').val(""); 
    	    	 Listar.init();
    	    	 $('div.block').unblock();  
    	    	
        	    	
    	    	if(msg.trim()==""){    	 
     	    	 
     	    	   
    	    	
    	    		$.gritter.add({title:"ASIGNAR SECTOR:",text:"Se ha asignado con exito el Sector!"});
    		        	
    		        	
    		        	
    			}else{    					
    					    $('html, body').animate({scrollTop:0}, 10);
    	 					$("#alerta").fadeIn();
    	 					$("#alerta").removeClass();
    	 	                $("#alerta").addClass("alert alert-error");
    	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
    	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se asigno el sector, por favor intentalo nuevamente.");     					
    					
    					
    			   }   	    	
    	    	
    	    	
    	            } 
    	           });
	
	
     }
    
    
    
    
    

var handleListar = function(){
    	
	// var show =	$("#data-elements-length").val();
	var show = 10;
	var search =	$("#searchtable").val();
	var idsorteo = gup('idsorteo');
	
	//if(search!=""){ pag=1;}  

	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	});
	
    
	$.ajax({ 
		type: "GET",  
		cache: false,
		url: "SectoresAsignados",  
		data: "view=Buscar&pg="+pag2+"&show="+show+"&search="+search+"&idsorteo="+idsorteo,//CAMBIO
		success: function(msg){  
	    	
	    	var response = msg.split('#%#');  
				
			$("#listresultados").html(response[0]);
			$("#numTal").html(response[1]);
	    	
	    	$('div.block').unblock(); 
	    	
	    	
	    	$("[data-toggle=tooltip]").tooltip();
	    	$("[data-toggle=popover]").popover();
	    	
	    	
	               } 
	   
	           });
    	
    } 


/*
function eliminarSectores(idsector,sector,idsorteo){
	
	var eliminar = true;
	
	if(confirm("Esta seguro de eliminar este sector?")){
		
	
  	    	
  	    $.ajax({ 
  	  	type: "GET",  
  	  	url: "SectoresAsignados",  
  	  	data: "view=EliminarSector&idsector="+idsector+"&sector="+sector+"&eliminar="+eliminar+"&idsorteo="+idsorteo,  
  	  	success: function(msg){  
  	  		
  	  	alert(msg);
  	    	
  	    	     $('#sector'+idsector).hide('slow', function(){ $('#sector'+idsector).remove(); });  
  	    	     $.gritter.add({title:"SECTOR ELIMINADO",text:"El sector sido eliminados con exito"});
  	            } 
  	           });
	
	}
	
	
}
*/

/*function refresh(){
	
    var show =	"10";
    var search =	$("#searchtable").val();
   
	$.ajax({ 
	    type: "GET",  
	    url: "SectoresAsignados",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
	    success: function(msg){  
	    	
	    	
	    	
	    	$("#listresultados").html(msg);
	    	
	    	$("[data-toggle=tooltip]").tooltip();
	    	$("[data-toggle=popover]").popover();
	    	
	    	
	               } 
	   
	           });
   	
   } */





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

 
//-------------- CONFIGURACION

function showOptions(idSector) {
	
	$('#idSector').val(idSector);

	var tmp_sector = idSector;

	$('#formConfig').show();
	//$('#cargaBoletos').hide();
	$('#formEliminarCargaSector').hide();
	$('#formEliminarSector').hide();
	$("#formeliminarsucces").hide();
	
	$('#intervalo_1').val('');
	$('#intervalo_2').val('');
	$('#bBuscaIntervalo').show();
	$('#span_paso_2').hide();
	$('#span_paso_3').hide();
	$('#img_process').hide();
	boleto_electronico='';

	$('#myModalConfiguracion').modal({
		show : true,
		backdrop : 'static'
	});

	$.ajax({
		type : "GET",
		cache: false,
		url : "SectoresAsignados",
		data : "view=ExisteCarga&idSector=" + tmp_sector,
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

function showFormatoElimina() {

	$('#formConfig').hide();

	$('#formEliminarCargaSector').show();
	$('#deletecargasorteoconfirm').val('');

}

function showFormatoEliminaSector() {

	$('#formConfig').hide();

	$('#formEliminarSector').show();
	$('#inputPalabraELIMINAR_2_id').val('');
}

function eliminarBoletosYTalonarios() {

	var inputEliminar = $('#deletecargasorteoconfirm').val();

	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminaran todos los talonarios y boletos")) {
			$("#content_process").show();
			$("#content_sucess").hide();

			$('#formEliminarCargaSector').hide();
			$("#formeliminarsucces").show();

			$.ajax({
				type : "GET",
				cache: false,
				url : "SectoresAsignados",
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

function eliminarSector() {
	var inputEliminar = $('#inputPalabraELIMINAR_2_id').val();

	if (inputEliminar == "ELIMINAR") {
		if (confirm("Se eliminara el sector")) {
			$("#content_process").show();
			$("#content_sucess").hide();
	
			$('#formEliminarSector').hide();
			$("#formeliminarsucces").show();
	
			$.ajax({
				type : "GET",
				cache: false,
				url : "SectoresAsignados",
				data : "view=EliminaSector",
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
	pag=1;

	$.ajax({
		type : "GET",
		cache: false,
		url : "SectoresAsignados",
		data : "view=Buscar&pg=" + pag + "&show=" + show + "&search=" + search,
		success : function(msg) {

		//	$("#listresultados").html(msg);
			
			var response = msg.split('#%#');  
			
			$("#listresultados").html(response[0]);
			$("#numTal").html(response[1]);

			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();

		}

	});
}




/////////////////ASIGNACION TALONARIOS Y BOLETOS FC1//////////////////

function AsignarTalonario(confirm)
{
	
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
	
	
	
	$('#myModalfc1').modal({
		show: true,
		backdrop: 'static'
		})
	
	
	$('#aceptarmodalfc1').removeAttr( "onClick" );
	$("#aceptarmodalfc1").attr("onClick","mostrarFormato()");
	$('#folio').val("");
	
	
	
}


function mostrarFormato(){
	
	
	$('#instrucciones').hide();
	$('#formato').show();
	$('#formtalonarios').hide();
	$('#asignacion').hide();
	
	$('#aceptarmodalfc1').removeAttr( "onClick" );
	$("#aceptarmodalfc1").attr("onClick","AsignarTalonarioboleto()");
	

	
}


function AsignarTalonarioboleto(){
	
    var folio = $('#folio').val();
	
    if(folio==""){
		alert("Debe ingresar el folio");
	 }else{
	
	$('#instrucciones').hide();
	$('#formato').hide();
	$('#formtalonarios').hide();
	//$('#asignacion').show();
	
	$('#searchtableMBoletos').val(""); 
	$("#searchtableM").val('');
	$("#aceptarmodalfc1").attr("onClick", "javascript:enviarasignaciontalonarios();return false;");
	
	$('#asignacion').show();	
	
	$("#Habilitados input[type=checkbox]").prop('checked', false);
	pag = 1;
	ListarModal.init()
	//ListarModalBoletos.init();
	
	
	
	 }
	
	
}








jQuery.fn.getCheckboxValues = function()
{
	var values = [];
	var i = 0;
	this.each(function(){
	    // guarda los valores en un array
	    values[i++] = this.id;
	});
	// devuelve un array con los checkboxes seleccionados
	return values;
}

function enviarasignaciontalonarios()
{
	var arr = $("#Habilitados input:checked").getCheckboxValues();

	if(arr.length==0){
		alert("Debe elegir un talonario");
	}else{
		//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
		$('#myModalfc1').modal('hide');
		$('#idsTalonarios').val(arr);
		// alert("balabla "+arr);
		$("input:checkbox").prop('checked', false);
		 
		GuardarAsignacionTalonarios();
	}
}

var arrIntervaloTalonarios=[];

function enviaIntervaloTalonarios(){
	var arr = arrIntervaloTalonarios;
	//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
	$('#myModalfc1').modal('hide');
	$('#idsTalonarios').val(arr);
	// alert("balabla "+arr);
	$("input:checkbox").prop('checked', false);
	
	GuardarAsignacionTalonarios();
}

function buscaIntervaloTalonarios() {
	var limiteInf = $('#intervalo_1').val();
	var limiteSup = $('#intervalo_2').val();
	
	$('#bBuscaIntervalo').prop('disabled', true);
	$('#span_paso_2').hide();
	$('#span_paso_3').hide();
	$('#img_process').show();
	arrIntervaloTalonarios=[];

	$.ajax({
		type : "GET",
		cache : false,
		url : "AsignacionTalonarios",
		data : "view=BuscaIntervaloTalonarios&limiteInf=" + limiteInf + "&limiteSup=" + limiteSup,
		success : function(data) {
			data = jQuery.parseJSON(data);
			// Si no hay error
			if (data.ERROR == 0) {
				arrIntervaloTalonarios = data.Disponibles;
				
				$('#eNTalonarios').html('' + data.Disponibles.length);
				// Si todos los talonarios estan disponbles.
				if (data.Asignados.length == 0 && data.Disponibles.length > 0) {
					// Correcto
					$('#bEnviaIntervalo').removeClass('btn-warning');
					$('#bEnviaIntervalo').addClass('btn-primary');
					$('#span_paso_2').show();
				} else {
					if (data.Disponibles.length > 0) {
						alert('Se encontraron ' + data.Asignados.length + ' talonarios ya asignados.');

						$('#bEnviaIntervalo').removeClass('btn-primary');
						$('#bEnviaIntervalo').addClass('btn-warning');
						$('#span_paso_2').show();
					} else
						alert('No hay talonarios disponibles en el intervalo especificado.');
				}
				
			} else {
				alert('Ocurrio un error de conexion. Vuelve a intentar la busqueda.');
			}
		},
		error : function(data) {
			alert('Ocurrio un error de conexion. Vuelve a intentar la busqueda.');
		},
		complete: function(data){
			$('#img_process').hide();
			$('#bBuscaIntervalo').prop('disabled', false);
		}
	});
}

var boleto_electronico='';
function SeleccionaElectronico(electronico){
	boleto_electronico = electronico ? 'ELECTRONICO' : 'FISICO';
	$('#bBuscaIntervalo').prop('disabled', false);
	$('#bBuscaIntervalo').show();
	$('#span_paso_2').hide();
	$('#span_paso_3').show();
}

function GuardarAsignacionTalonarios ()
{
	var idsTalonarios = $('#idsTalonarios').val();
	var idsorteo = gup('idsorteo');
	var idsector = $('#idSector').val();
	var folio = $('#folio').val();
	
	$('div.block').block({
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
	});
	
	$.ajax({
		type: "POST",
		cache: false,
		url: "AsignacionTalonarios",
		data: { idsTalonarios: idsTalonarios, idsector: idsector, idsorteo: idsorteo, folio: folio, electronico: boleto_electronico},
		success: function(msg){
			
			$('#idsTalonarios').val("");
			
			pag2 = page2; //CAMBIO pag = 1;
			
			Listar.init();
			
			if(msg.trim()=="")
			{
				$.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Se han asignado con exito los talonario(s)!"});
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
			alert('Ocurrio un error de conexion. Vuelve a intentar la asignacion.');
		},
		complete: function(msg){
			$('div.block').unblock();
		}
	});
}


var ListarModal=function(){"use strict";return{init:function()
	{
		handleListarModal();
		
	}}}()


	function onkeyup_colfield_checkModal(e)
	{
		var enterKey = 13;
		if (e.which == enterKey){
			handleListarModal()
		}
	}

	function intervalo_1_onkeyup(e)
	{
		if (e.which == 13){
			document.getElementById('intervalo_2').focus();
		}
	}

	function intervalo_2_onkeyup(e)
	{
		if (e.which == 13){
			document.getElementById('bBuscaIntervalo').focus();
			buscaIntervaloTalonarios();
		}
	}
	

	/***************************************CHECAR*****************************************/
	

	function changeShowModal()
	{
		handleListarModal()
	}
	
	

	function getPagModal(page)
	{
		pag = page;
		handleListarModal()
	}

	var handleListarModal = function()
	{
		var show =	$("#data-elements-lengthM").val();
		var search =	$("#searchtableM").val();
		
		
		 if(search!=""){ pag=1;}  
		 
		$('div.block').block({
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
		});

		$.ajax({ 
			type: "GET",
			cache: false,
			url: "AsignacionTalonarios",
			data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,
			success: function(msg){  
				$('div.block').unblock(); 
				$("#listresultados2fc1").html(msg);
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
			//var idsector = gup('idsector');
			var idsector = $('#idSector').val();
			
			 if(search!=""){ pag=1;}  
			

			$('div.block').block({ 
				css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
				overlayCSS: {backgroundColor: '#FFF'},
				message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
			}); 

			$.ajax({ 
				type: "GET",
				cache: false,
				url: "AsignacionTalonarios",
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
	        // alert(arr);
			 if(arr.length==0){
				alert("Debe elegir un boleto");
			 }else{
				
				 $('#myModalfc1').modal('hide');
				 $('#idboletos').val(arr);
				
				GuardarAsignacionBoletos();
				 
				
			 }
		 }
		
		
		function GuardarAsignacionBoletos ()
		{
			var idboletos = $('#idboletos').val();
			var folio = $('#folio').val();
			var idsorteo = gup('idsorteo');
			//var idsector = gup('idsector');
			var idsector = $('#idSector').val();
			var accion = 'aboletos';
			
			//alert(""+(idsTalonarios==null?"null":"vacio"));

			$('div.block').block({ 
				css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
				overlayCSS: {backgroundColor: '#FFF'},
				message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
			}); 


			
		/*	$.ajax({ 
				type: "GET",  
				url: "AsignacionTalonarios",  
				data: "view=AsignarBoletos&idboletos="+idboletos+"&idsorteo="+idsorteo+"&idsector="+idsector+"&folio="+folio,
				success: function(msg){*/
			
			
			
			$.ajax({
				type: "POST",
				cache: false,
				url: "AsignacionTalonarios",
				data: { idboletos: idboletos, idsorteo: idsorteo, idsector: idsector,folio: folio,accion:accion},
				success: function(msg){
					
					
					$('#idboletos').val("");
				
				//	pag2 = 1;//checar
					
					pag2 = page2; //CAMBIO pag = 1;
					
					Listar.init();					
				
					$('div.block').unblock();
					
					if(msg.trim()=="")
					{
						
						 $.gritter.add({title:"BOLETOS ASIGNADOS AL SECTOR",text:"Se ha asignado con exito los boletos!"});
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

		
		//SELECCIONAR TODO
		
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
		
		
		/****************************INICIO****************************************/


		function DevolucionBoletosMasivaModal(confirm)
		{
			
			$('#myModalConfiguracion').modal('hide');
			
			
			$('#instruccionesheader3').show();
			$('#instrucciones3').show();
			$('#formatoFC5-FC5B').hide();
			
			
			/*//bol
			$('#boletosTab').removeAttr( "class" );
			$("#bollink").attr("aria-expanded","false");	
			$('#default-tab-2').removeAttr( "tab-pane fade active in" );
			$("#default-tab-2").attr("class","tab-pane fade");	
			//tal
			$("#talonariosTab").attr("class","active");
			$("#tallink").attr("aria-expanded","true");
			$("#default-tab-1").attr("class","tab-pane fade active in");*/	
			
			
			
			$('#myModalFC5-FC5B').modal({
				show: true,
				backdrop: 'static'
				})
			
			
			$('#aceptarmodalFC3').removeAttr( "onClick" );
			$("#aceptarmodalFC3").attr("onClick","mostrarFormatoFC5_FC5B()");
			$('#foliofcs').val("");	
			
			
		}



		function mostrarFormatoFC5_FC5B(){
			
			$('#instruccionesheader3').hide();
			$('#instrucciones3').hide();
			$('#formatoFC5-FC5B').show();	
			
			
			$('#aceptarmodalFC3').removeAttr( "onClick" );
			$("#aceptarmodalFC3").attr("onClick","DevolucionBoletosMasiva()");	
			
			
		}




		function DevolucionBoletosMasiva ()
		{
					  
			var idsector =  $('#idSector').val();
	    	var idsorteo = gup('idsorteo');
			
		//	var idsorteo = gup('idsorteo');	
		//	var idsector = gup('idsector');
		//	var idnicho = $('#nichoactual').val();//ya viene	

			
			var folio = $('#foliofcs').val();	
			var accion = 'DevolucionMasiva';
			
			 $('#myModalFC5-FC5B').modal('hide');
			
			
			$('div.block').block({ 
				css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
				overlayCSS: {backgroundColor: '#FFF'},
				message: '<img src="assets/img/load-search.gif" /><br><h2> Procesando...</h2>'
			}); 


			
			$.ajax({
				type: "POST",
				cache: false,
				url: "AsignacionTalonarios",
				data: { idsector: idsector, idsorteo: idsorteo,accion: accion,foliofc5:folio},
				success: function(msg){	
					
					
					
					$('#idsector').val(""); 
			    	pag2 = 1;
			    	 Listar.init();
					$('div.block').unblock();
					
					if(msg.trim()==""){
						
						 $.gritter.add({title:"TALONARIOS DEVUELTOS",text:"Se ha devuelto con exito los talonarios!"});
					}
					else if(msg.trim()=="NO_HAY"){
						
						 $.gritter.add({title:"TALONARIOS DEVUELTOS",text:"No exiten talonarios electr&oacutenicos en este Sector!"});
						
						
					}
					else{
							$('html, body').animate({scrollTop:0}, 10);
		 					$("#alerta").fadeIn();
		 					$("#alerta").removeClass();
		 	                $("#alerta").addClass("alert alert-error");
		 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
		 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se devolvieron los talonarios, por favor intentalo nuevamente.");     					
					}
					
				},
				error: function(msg) {
					//alert("Error");
				}
			});
		}
		
		
	
	

		/****************************FIN****************************************/
		