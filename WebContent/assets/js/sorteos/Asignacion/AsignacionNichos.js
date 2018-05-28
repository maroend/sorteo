/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
*/

var pag = 1;

var pag2 = 1;
var page2 = 1;


var Listar=function(){"use strict";return{init:function(){ 
    
	
	handleListar()
	
}}}()





function onkeyup_colfield_check(e){
    var enterKey = 13;
        if (e.which == enterKey){
        	pag2 = 1;
        	handleListar()
        }
}

    function changeShow(){
    	
    	handleListar()
    }

    function getPag(page){
	
	pag2 = page;
	
	page2 = page;
	 handleListar()
    }

    
    // ***************************************************modal FC2 TALONARIOS
    var ListarModalTalonarios=function(){"use strict";return{init:function(){ 
        
    	
    	handleListarModalTalonarios()
    	
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
    
 /* *** fin modal talonarios */
    
    
// ***************************************************modal FC2 BOLETOS
	
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
	
	
	
		 // ***************************************************modal OTRO
    
    var ListarModal=function(){"use strict";return{init:function(){ 
        
    	
    	handleListarModal();
    	
    }}}()






    function onkeyup_colfield_checkModal(e){
        var enterKey = 13;
            if (e.which == enterKey){
            	handleListarModal()
            }
    }

        function changeShowModal(){
        	
        	handleListarModal()
        }

        function getPagModal(page){
    	
    	pag = page;
    	 handleListarModal()
        }
        
        
        var handleListarModal = function(){
        	
        	 var show =	$("#data-elements-lengthM").val();
             var search =	$("#searchtableM").val();
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
       	    url: "AsignacionNichos",  
       	    data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search+"&idsector="+idsector,  
       	    success: function(msg){  
       	    	
       	    	
       	    	$('div.block').unblock(); 
       	    	$("#listresultados2").html(msg);
       	    	
       	    	
       	           	    	
       	    	
       	               } 
       	   
       	           });
           	
           }  
        

    
    function AsignarSector(confirm){ 
   
    	$("#aceptarmodal").attr("onClick", "javascript:enviar();return false;");
    	$('#idsectores').val("");    
        $('#myModal').modal('show');
		 $("#searchtableM").val('');
		 pag = 1;
        setTimeout("ListarModal.init()",0);
        $("#listresultados2").html("");
      // $("#data-elements-lengthM").val();
       
        
    	
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
    		alert("Debe elegir un nicho");
    	 }else{
    		//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
    		 $('#myModal').modal('hide');
    		 $('#idnichos').val(arr);
    		 $("input:checkbox").prop('checked', false);    		 
    		 
    		 GuardarAsignacion();
    		 
    		
    	 }
     }
    
    
        
        
    
function GuardarAsignacion (){
    	
    	var idnichos = $('#idnichos').val();   
    	var idsector = gup('idsector');
    	
    
    	
    	$('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
       }); 
 	
    
    	
    	$.ajax({ 
    	    type: "POST",  
    	    cache: false,
    	    url: "AsignacionNichos",  
    	    data: { idnichos: idnichos, idsector: idsector},  
    	    success: function(msg){  
    	    	
    	    	
    	    	
    	    	$('#idnichos').val(""); 
    	    	pag2 = 1; 	    	
    	    	
    	    	
    	    	 Listar.init();
    	    	 $('div.block').unblock();  
    	    	
        	    	
    	    	if(msg.trim()==""){    	 
     	    	 
     	    	   
    	    		
    	    		 $.gritter.add({title:"ASIGNAR NICHO",text:"Se ha asignado con exito el nicho!"});
    		        	
    		        	
    		        	
    			}else{    					
    					    $('html, body').animate({scrollTop:0}, 10);
    	 					$("#alerta").fadeIn();
    	 					$("#alerta").removeClass();
    	 	                $("#alerta").addClass("alert alert-error");
    	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
    	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se asigno el nicho, por favor intentalo nuevamente.");     					
    					
    					
    			   }   	    	
    	    	
    	    	
    	            } 
    	           });
	
	
     }
    
    
    
    
    

var handleListar = function(){
    	
	// var show =	$("#data-elements-length").val();
	 var show = 10;
     var search =	$("#searchtable").val();
     var idsector = gup('idsector');
     
     if(search!=""){ pag2=1;}  
    
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
	    cache: false,
	    url: "AsignacionNichos",  
	    data: "view=Buscar&pg="+pag2+"&show="+show+"&search="+search+"&idsector="+idsector,  
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
	 }else{
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




/**********************************new*********/


function AsignarTalonarioFc3Colaborador(confirm)
{
		
	$("#aceptarmodalFC3-colab").attr("onClick", "javascript:mostrarFormatoColaborador();return false;");
	
	$( "#foliofcscolab" ).focus();
	$('#formatoFC3-Colab').show();				
	$('#formtalonariosColabortadores').hide();	
	
	
	$('#myModalConfiguracion').modal('hide');

	$('#myModalFC3Colabo').modal({
		show : true,
		backdrop : 'static'
	});	
	
}

// mostrarTalonariosColaboradores

function mostrarFormatoColaborador()
{
			
	 var folio = $('#foliofcscolab').val();
		
	    if(folio==""){
			alert("Debe ingresar el folio");
		 }else{			 
			 
				$('#formatoFC3-Colab').hide();				
				$('#formtalonariosColabortadores').show();	
				$("#aceptarmodalFC3-colab").attr("onClick", "javascript:mostrarTalonariosColaboradores();return false;");
			 
			 
		 }	
	
}


function limpiarColab(){
	$("#aceptarmodalFC3-colab").attr("onClick", "javascript:mostrarFormatoColaborador();return false;");	
	
}



function mostrarTalonariosColaboradores()
{		
	    var folio = $('#foliofcscolab').val();
	    var numtal_colab = $('#numtal_col').val();
	    var tipo_talonario = $('#tipo_talonario').val();	
		var idsorteo = gup('idsorteo');
		var idsector = gup('idsector');
	    var idnicho = $('#nichoactual').val();	
		//var idcolaborador = $('#idColaborador').val();		    
		var tipotalonario = "";	
		if(tipo_talonario == 0)
			tipotalonario = "Fisicos";
		else
			tipotalonario = "Electronicos";	
		 
		$('div.block').block({ 
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> espera por favor..</h2>'
		}); 
		

		$.ajax({ 
			type: "GET",
			async: false,
			cache: false,
			url: "AsignacionTalonariosColaboradores",
			data: "view=mostrarTalonariosColaboradores&idsorteo="+idsorteo+"&idsector="+idsector+"&idnicho="+idnicho+"&numtal_colab="+numtal_colab+"&tipo_talonario="+tipo_talonario,
			success: function(msg){				
				
				$('div.block').unblock(); 				
				var strmnum_talonarios_colab = msg.split("#%#");
				var numTalonarios_disponibles = strmnum_talonarios_colab[0];
				var numColaboradores = strmnum_talonarios_colab[1];		
				
				if(parseInt(numTalonarios_disponibles) == 0 || parseInt(numColaboradores)== 0 )
				{   
					 alert("No se puede asignar talonarios ya que:\nEl numero de talonarios "+ tipotalonario + " completos disponibles es: "+numTalonarios_disponibles+" \nY el numero de colaboradores en este nicho es: "+numColaboradores);					
				     return false;			 
					
				}else if(parseInt(numTalonarios_disponibles) != 0 && parseInt(numColaboradores) != 0 )
				{				
					var r = confirm("El numero de talonarios "+ tipotalonario + " completos disponibles es: "+ numTalonarios_disponibles+" \nY el numero de colaboradores en este nicho es: "+numColaboradores+"\nDeseas Continuar?");
					if (r == true) {				
						
						if($("#numtal_col").val()==""){
							alert('Debes ingresar el numero de Talonarios a asignar')							
							return false							
						}
						
						AsignacionTalonarioCompleto_Colaborador_SP(idsorteo,idsector,idnicho,tipo_talonario,numtal_colab,folio);						
					  
					}else {}					
					 
				}
				else{ valida = false; }						
				
			}				
				
		});	
	
}
	


function AsignacionTalonarioCompleto_Colaborador_SP(idsorteo,idsector,idnicho,tipo_talonario,numtal_colab,folio)
{

	
	var accion = 'asignacion_colaboradores';	
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
		}); 

	
	$.ajax({
		type: "POST",
		cache: false,
		url: "AsignacionTalonariosColaboradores",
		data: {  idsorteo: idsorteo, idsector: idsector, idnicho: idnicho, folio: folio, accion: accion, tipo_talonario: tipo_talonario, numtal_colab: numtal_colab},
		success: function(msg){
			
						
			var arrTalonariosColab = msg.split("#%#");
			var numeroTalonarios = arrTalonariosColab[0];
			var numeroColaboradores = arrTalonariosColab[1];			
			
			$('#myModalFC3Colabo').modal('hide');			
			$('div.block').unblock();			
			
			
			if(numeroTalonarios!=0&&numeroColaboradores!=0){
				$.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Se han asignado " + numeroTalonarios + " talonarios en " + numeroColaboradores+" Colaboradores"});
			}else{
				$.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Hubo un problema a la hora de asignar, favor de volver a intentar"});
			}
			 
			 //$.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Se ha asignado con exito los talonarios!"});
			 
			 
			
		},
		error: function(msg) {
			//alert("Error");
		}
	});



}	

/***********************************/




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
	var arr = $("#Habilitados input:checked").getCheckboxValues();

	 if(arr.length==0){
		alert("Debe elegir un talonario");
	 }else{
		
		 $('#myModalFC2').modal('hide');
		 $('#idsTalonarios').val(arr);		
		 $("input:checkbox").prop('checked', false);    		 
		 
		 GuardarAsignacionTalonarios();
		 
		
	 }
 }



//Nuevo

var arrIntervaloTalonarios=[];

function enviaIntervaloTalonarios(){
	var arr = arrIntervaloTalonarios;
	//alert(arr); // esto muestra un pop-up con los checkboxes seleccionados
	$('#myModalFC2').modal('hide');
	$('#idsTalonarios').val(arr);
	// alert("balabla "+arr);
	$("input:checkbox").prop('checked', false);
	
	GuardarAsignacionTalonarios();
}

function buscaIntervaloTalonarios() {
	var limiteInf = $('#intervalo_1').val();
	var limiteSup = $('#intervalo_2').val();
	
	var idsector = gup('idsector');
	var idsorteo = gup('idsorteo');
	
	$('#bBuscaIntervalo').prop('disabled', true);
	$('#span_paso_2').hide();
	$('#span_paso_3').hide();
	$('#img_process').show();
	arrIntervaloTalonarios=[];

	$.ajax({
		type : "GET",
		cache : false,
		url : "AsignacionTalonariosNichos",
		data : "view=BuscaIntervaloTalonarios&limiteInf=" + limiteInf + "&limiteSup=" + limiteSup+ "&idsorteo=" + idsorteo+ "&idsector=" + idsector,
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
					//$('#span_paso_2').show();
					$('#span_paso_3').show();
				} else {
					if (data.Disponibles.length > 0) {
						alert('Se encontraron ' + data.Asignados.length + ' talonarios ya asignados.');

						$('#bEnviaIntervalo').removeClass('btn-primary');
						$('#bEnviaIntervalo').addClass('btn-warning');
						//$('#span_paso_2').show();					
						$('#span_paso_3').show();
						
						
						
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




/*****************************/







function GuardarAsignacionTalonarios ()
{
	var idsTalonarios = $('#idsTalonarios').val();
	var idsorteo = gup('idsorteo');
	var idnicho = $('#nichoactual').val();
	
	var folio = $('#folio').val();
	

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
			//pag = 1;
			
			pag2 = page2;//CAMBIO
			
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
			//pag = 1;
			
			pag2 = page2;//CAMBIO
			
			
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
	
	var idsorteo = gup('idsorteo');	
	var idsector = gup('idsector');
	var idnicho = $('#nichoactual').val();//ya viene	

	
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
		url: "AsignacionTalonariosNichos",
		data: { idsector: idsector, idnicho: idnicho, idsorteo: idsorteo,accion: accion,foliofc5:folio},
		success: function(msg){	
			
			
			$('#idnichos').val(""); 
	    	pag2 = 1;
	    	 Listar.init();
			$('div.block').unblock();
			
			if(msg.trim()==""){
				
				 $.gritter.add({title:"TALONARIOS DEVUELTOS",text:"Se ha devuelto con exito los talonarios!"});
			}
			else if(msg.trim()=="NO_HAY"){
				
				 $.gritter.add({title:"TALONARIOS DEVUELTOS",text:"No exiten talonarios electr&oacutenicos en este Nicho!"});
				
				
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

/****************/
    $("#numtal_col").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            // Allow: Ctrl+A, Command+A
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
            // Allow: home, end, left, right, down, up
            (e.keyCode >= 35 && e.keyCode <= 40)) {
            // let it happen, don't do anything
            return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });


/****************************FIN****************************************/






