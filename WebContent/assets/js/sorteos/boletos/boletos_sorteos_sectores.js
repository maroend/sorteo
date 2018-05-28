var pag = 1;
var vendido=0;
var parcial=0;
var novendido=0;
var extraviados=0;
var robados=0;
var ultimoFolio='';

var Listar = function() {
	"use strict";
	return {
		init : function() {
			filtrar(0);
		}
	}
}()


function filtrar(option) {

	vendido = 0;
	novendido = 0;
	extraviados = 0;
	robados = 0;
	
	$('#filtro-t').html('<i class="fa '+(option==0?'fa-check-square-o':'fa-square-o')+'"></i> Todos');
	$('#filtro-v').html('<i class="fa '+(option==1?'fa-check-square-o':'fa-square-o')+'"></i> Vendidos');
	$('#filtro-n').html('<i class="fa '+(option==2?'fa-check-square-o':'fa-square-o')+'"></i> No Vendidos');
	$('#filtro-e').html('<i class="fa '+(option==3?'fa-check-square-o':'fa-square-o')+'"></i> Extraviados');
	$('#filtro-r').html('<i class="fa '+(option==4?'fa-check-square-o':'fa-square-o')+'"></i> Robados');
	
	switch(option) {
	case 0: $('#nombre_filtro').html('Todos'); break;
	case 1: vendido = 1;     $('#nombre_filtro').html('Vendidos'); break;
	case 2: novendido = 1;   $('#nombre_filtro').html('No Vendidos'); break;
	case 3: extraviados = 1; $('#nombre_filtro').html('Extraviados'); break;
	case 4: robados = 1;     $('#nombre_filtro').html('Robados'); break;
	}
	handleListar()
}
/*
    function filtrar(obj){
	
	var option = obj;
	
	 vendido=0;
     parcial=0;
     novendido=0;
     extraviados=0;
     robados=0;
	
	switch(obj){

	case "filtrovendidos":
	if($("#filtrovendidos").is(':checked')) { vendido=1; $('#filtroparcial').prop('checked', false); $('#filtronovendidos').prop('checked', false); $('#filtroextraviados').prop('checked', false); $('#filtrorobados').prop('checked', false); }else{ vendido=0;}
	break;
	
	case "filtroparcial":
	if($("#filtroparcial").is(':checked')) { parcial=1; $('#filtrovendidos').prop('checked', false); $('#filtronovendidos').prop('checked', false); $('#filtroextraviados').prop('checked', false); $('#filtrorobados').prop('checked', false);}else{ parcial=0; }
	break;
	
	case "filtronovendidos":
	if($("#filtronovendidos").is(':checked')) { novendido=1; $('#filtrovendidos').prop('checked', false); $('#filtroparcial').prop('checked', false); $('#filtroextraviados').prop('checked', false); $('#filtrorobados').prop('checked', false); }else{ novendido=0; }
	break;
	
	
	case "filtroextraviados":
	if($("#filtroextraviados").is(':checked')) { extraviados=1; $('#filtrovendidos').prop('checked', false); $('#filtronovendidos').prop('checked', false); $('#filtroparcial').prop('checked', false); $('#filtrorobados').prop('checked', false);}else{ extraviados=0; }
	break;
	
	
	case "filtrorobados":
	if($("#filtrorobados").is(':checked')) { robados=1; $('#filtrovendidos').prop('checked', false); $('#filtronovendidos').prop('checked', false); $('#filtroparcial').prop('checked', false); $('#filtroextraviados').prop('checked', false); }else{ robados=0; }
	break;
	
	}
	
	handleListar()
   }
*/

function onkeyup_colfield_check(e){
    var enterKey = 13;
        if (e.which == enterKey){
        	pag=1;
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

var handleListar = function() {

	var id_sorteo = $('#id_sorteo').val();
	var id_sector = $('#id_sector').val();
	var show = $("#data-elements-length").val();
	var search = $("#searchtable").val();

	$('div.block').block({
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 

	$.ajax({
		type : "GET",
		cache : false,
		url : "BoletosSorteosSectores",
		data : {
			"view":"Buscar",
			"pg":pag,
			"show":show,
			"filtervendido":vendido,
			"filternovendido":novendido,
			"filterextraviados":extraviados,
			"filterrobados":robados,
			"id_sorteo":id_sorteo,
			"id_sector":id_sector,
			"search": btoa(search),
		},
		success : function(msg) {
			$("#listresultados").html(msg);
		},
		error: function(){
	    	$('#listresultados').html(__msg_sin_conexion_2);
		},
		complete: function(){
			$('div.block').unblock();
		}
	});
}


/*    var handleListar = function(){
    	
    	
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
	    url: "BoletosSorteosSectores",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search+"&filtervendido="+vendido+"&filterparcial="+parcial+"&filternovendido="+novendido+"&filterextraviados="+extraviados+"&filterrobados="+robados,  
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               },
	       	    error: function(msg){
	    	    	$('div.block').unblock();
	    	    	$('#listresultados').html(__msg_sin_conexion_2);
	    		}
	   
	           });
    	
    	
    }   */
    
    
    
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
    
    /*
    function enviar()
    {
    	$('#formulario2').parsley().reset();
    	var arr = $("input:checked").getCheckboxValues();
    	 $("#abono").val('');   
    	 if(arr.length==0){
    		alert("Debe elegir un boleto");
    	 }else{
    		
    		 
			 $("#aceptarmodal").attr("onClick", "javascript:actualizarEstatusBoletoAbono('A');return false;");			 
			 $('#myModal').modal('show');
    		 $('#idboletos').val(arr);    		
    		 $("input:checkbox").prop('checked', false);    		 
    		 
    		    		
    	 }
     }
    
    
    function actualizarEstatusBoletoAbono(estatus){  	
        	
        
        	var idboletos = $("#idboletos").val();
        	var abono = $("#abono").val();
        	
     if( $('#formulario2').parsley().validate() ){  	
        	
        	$('div.block').block({ 
     		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
     	        overlayCSS: {backgroundColor: '#FFF'},
     	         message: '<img src="assets/img/load-search.gif" /><br><h2> Actualizando Estatus..</h2>'
           }); 
     	
        
        	
        	$.ajax({ 
        	    type: "POST",  
        	    url: "BoletosSorteosSectores",  
        	    data: { estatus: estatus,idboletos: idboletos,abono: abono},  
        	    success: function(msg){  	    
        	    	
        	    	$('#idboletos').val(""); 
        	    	 $('#myModal').modal('hide');
        	    	 $('div.block').unblock();  
        	    	
            	    	
        	    	if(msg.trim()==""){    	 
         	    	 
         	    	   
        	    	
        	    		$.gritter.add({title:"ACTUALIZAR ESTATUS BOLETO:",text:"Se ha actualizado con exito!"});
        		        	
        		        	
        		        	
        			}else{    					
        					    $('html, body').animate({scrollTop:0}, 10);
        	 					$("#alerta").fadeIn();
        	 					$("#alerta").removeClass();
        	 	                $("#alerta").addClass("alert alert-error");
        	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
        	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se actualizo, por favor intentalo nuevamente.");     					
        					
        					
        			   }  
        	    	
        	    	 Listar.init();
        	    	
        	    	
        	            } 
        	           });
        	
           }
          return false;    	
    	
       }
    
    
    
    
    function enviarEstatus(estatus){
    	
    	var arr = $("input:checked").getCheckboxValues();

    	 if(arr.length==0){
    		alert("Debe elegir un boleto");
    	 }else{   		
    		
    		 $('#idboletos').val(arr);
    		 $("input:checkbox").prop('checked', false);  
    		 
    		 actualizarEstatusBoleto(estatus,abono);
    		 
    		
    	 }
     }
    
    
    
        
    
function actualizarEstatusBoleto (estatus){  	
    	
    
    	var idboletos = $("#idboletos").val();
    	
    	
    	
    	$('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Actualizando Estatus..</h2>'
       }); 
 	
    
    	
    	$.ajax({ 
    	    type: "POST",  
    	    url: "BoletosSorteosSectores",  
    	    data: { estatus: estatus,idboletos: idboletos},  
    	    success: function(msg){  	    
    	    	
    	    	$('#idboletos').val("");     	    	
    	    	 $('div.block').unblock();  
    	    	
        	    	
    	    	if(msg.trim()==""){    	 
     	    	 
     	    	   
    	    	
    	    		$.gritter.add({title:"ACTUALIZAR ESTATUS BOLETO:",text:"Se ha actualizado con exito!"});
    		        	
    		        	
    		        	
    			}else{    					
    					    $('html, body').animate({scrollTop:0}, 10);
    	 					$("#alerta").fadeIn();
    	 					$("#alerta").removeClass();
    	 	                $("#alerta").addClass("alert alert-error");
    	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
    	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se actualizo, por favor intentalo nuevamente.");     					
    					
    					
    			   }  
    	    	
    	    	 Listar.init();
    	    	
    	    	
    	            } 
    	           });
	
	
     }
    
    */
    
    
/////////////////////TALONARIOS Y BOLETOS SORTEO ////////////
    var reporteTalonarios = function(){
    
   	$.ajax({ 
   	    type: "GET",  
        cache: false,
   	    url: "BoletosSorteosSectores",  
   	    data: "view=getBoletosTalonarios",  
   	    success: function(msg){  
   	    
   	    	
   	    	
   	    	var res = msg.split("|");
   	    	
   	    	$('#totaldetalonarios').html(res[0]);
   	    	$('#totaldetalonariosasignados').html(res[1]);
   	    	$('#totaldetalonariosparcialvendidos').html(res[2]);
   	    	$('#totaldetalonariosvendidos').html(res[3]);
   	    	
   	    	
   	    	$('#totaldeboletos').html(res[4]);
   	    	$('#totaldeboletosasignados').html(res[5]);
   	    	$('#totaldeboletosparcialvendidos').html(res[6]);
   	    	$('#totaldeboletosvendidos').html(res[7]);
   	    	$('#totaldeboletosextraviados').html(res[8]);
   	    	$('#totaldeboletosrobados').html(res[9]);
   	    	
   	    	
   	    	
   	    	handleListar();
   	    	
   	               } 
   	   
   	           });
       	
       }   
    
   ////////////////////END BOLETOS Y TALONARIOS SORTEO///////////// 
    
    
    
    ////////////////BOLETOS Y TALONARIOS SECTOR /////////////////////
    
    function reporteTalonariosSector(idsorteo,idsector){
        
       	$.ajax({ 
       	    type: "GET",  
            cache: false,
       	    url: "BoletosSorteo",  
       	    data: "view=getBoletosTalonariosSector&sorteo="+idsorteo+"&sector="+idsector,  
       	    success: function(msg){  
       	    	
       	    
       	    	var res = msg.split("|");
       	    	
       	    	$('#totaldetalonariossector').html(res[0]);
       	    	$('#totaldetalonariosasignadossector').html(res[1]);
       	    	$('#totaldetalonariosparcialvendidossector').html(res[2]);
       	    	$('#totaldetalonariosvendidossector').html(res[3]);
       	    	
       	    	
       	    	$('#totaldeboletossector').html(res[4]);
       	    	$('#totaldeboletosasignadossector').html(res[5]);
       	    	$('#totaldeboletosparcialvendidossector').html(res[6]);
       	    	$('#totaldeboletosvendidossector').html(res[7]);
       	    	$('#totaldeboletosextraviadossector').html(res[8]);
       	    	$('#totaldeboletosrobadossector').html(res[9]);
       	    	
       	    	
       	               } 
       	   
       	           });
           	
           }   
    
   
    ///////////////END BOLETOS Y TALONARIOS SECTOR////////////////
    
    
    
 ////////////////BOLETOS Y TALONARIOS NICHO /////////////////////
    
    function reporteTalonariosNicho(idsorteo,idsector,idnicho){
        
       	$.ajax({ 
       	    type: "GET", 
            cache: false, 
       	    url: "BoletosSorteo",  
       	    data: "view=getBoletosTalonariosNicho&sorteo="+idsorteo+"&sector="+idsector+"&nicho="+idnicho,  
       	    success: function(msg){  
       	    	
       	    	var res = msg.split("|");
       	    	
       	    	$('#totaldetalonariosnicho').html(res[0]);
       	    	$('#totaldetalonariosasignadosnicho').html(res[1]);
       	    	$('#totaldetalonariosparcialvendidosnicho').html(res[2]);
       	    	$('#totaldetalonariosvendidosnicho').html(res[3]);
       	    	
       	    	
       	    	$('#totaldeboletosnicho').html(res[4]);
       	    	$('#totaldeboletosasignadosnicho').html(res[5]);
       	    	$('#totaldeboletosparcialvendidosnicho').html(res[6]);
       	    	$('#totaldeboletosvendidosnicho').html(res[7]);
       	    	$('#totaldeboletosextraviadosnicho').html(res[8]);
       	    	$('#totaldeboletosrobadosnicho').html(res[9]);
       	    	
       	               } 
       	   
       	           });
           	
           }   
    
   
    ///////////////END BOLETOS Y TALONARIOS NICHO////////////////
    
    
    
    
////////////////BOLETOS Y TALONARIOS COLABORADOR /////////////////////
    
    function reporteTalonariosColaborador(idsorteo,idsector,idnicho,idcolaborador){
        
       	$.ajax({ 
       	    type: "GET",  
            cache: false,
       	    url: "BoletosSorteo",  
       	    data: "view=getBoletosTalonariosColaborador&sorteo="+idsorteo+"&sector="+idsector+"&nicho="+idnicho+"&colaborador="+idcolaborador,  
       	    success: function(msg){  
       	    	
       	    	var res = msg.split("|");
       	    	
       	    	$('#totaldetalonarioscolaborador').html(res[0]);
       	    	$('#totaldetalonariosasignadoscolaborador').html(res[1]);
       	    	$('#totaldetalonariosparcialvendidoscolaborador').html(res[2]);
       	    	$('#totaldetalonariosvendidoscolaborador').html(res[3]);
       	    	
       	    	
       	    	$('#totaldeboletoscolaborador').html(res[4]);
       	    	$('#totaldeboletosasignadoscolaborador').html(res[5]);
       	    	$('#totaldeboletosparcialvendidoscolaborador').html(res[6]);
       	    	$('#totaldeboletosvendidoscolaborador').html(res[7]);
       	    	$('#totaldeboletosextraviadoscolaborador').html(res[8]);
       	    	$('#totaldeboletosrobadoscolaborador').html(res[9]);
       	    	
       	               } 
       	   
       	           });
           	
           }   
    
   
    ///////////////END BOLETOS Y TALONARIOS NICHO////////////////
    
    
    

////////////////VENTA DE BOLETOS//////////
var detalles_de_venta_rapida = null;

function ShowDetalleBoleto(idsorteo, pkboleto, boleto, pktalonario, talonario, abono, costo, pksector, pknicho, pkcolaborador) {
	
	$('#modalDetalleTalonario').modal('hide')  
	
	$('#modalDetalleBoleto').modal({
		show : true,
		backdrop : 'static'
	});
	ultimoFolio = boleto;
	
	CancelarventaBoleto();
	BuscarComprador(idsorteo, pkboleto, boleto, pktalonario, talonario, abono, costo, pksector, pknicho, pkcolaborador);
	
	detalles_de_venta_rapida={
		idsorteo:idsorteo, pkboleto:pkboleto, boleto:boleto, pktalonario:pktalonario, talonario:talonario,
		abono:abono, costo:costo, pksector:pksector, pknicho:pknicho, pkcolaborador:pkcolaborador,
	};
}


function ShowDetalleTalonario(sorteo,pk_talonario,idtalonario){
	
	$('#modalDetalleTalonario').modal({
		show : true,
		backdrop : 'static'
	});
	
	$('#foliotalonariomodal').html(idtalonario);
	$('#boletostalonario').html('');
	
	BuscarMontoAbonoTalonario(sorteo,pk_talonario,idtalonario);
	
}



function ShowDetalleSector(sorteo,idsector,titulo){
	
	$('#modalDetalleSector').modal({
		show : true,
		backdrop : 'static'
	});
	
	
	var link = '<a href="BoletosSorteosSectores?idsector='+idsector+'">'+titulo+'</a>';
  $('#titlesector').html(link);
	reporteTalonariosSector(sorteo,idsector);
}
 


function ShowDetalleNicho(sorteo,idsector,idnicho,titulo){
	
	$('#modalDetalleNicho').modal({
		show : true,
		backdrop : 'static'
	});
	
	var link = '<a href="BoletosSorteosNichos?idnicho='+idnicho+'">'+titulo+'</a>';
  $('#titlenicho').html(link);

	reporteTalonariosNicho(sorteo,idsector,idnicho);
}


function ShowDetalleColaborador(sorteo,idsector,idnicho,idcolaborador,titulo){
	
	$('#modalDetalleColaborador').modal({
		show : true,
		backdrop : 'static'
	});
	
	 var link = '<a href="BoletosSorteosColaboradores?idcolaborador='+idcolaborador+'">'+titulo+'</a>';
  $('#titlecolaborador').html(link);
	reporteTalonariosColaborador(sorteo,idsector,idnicho,idcolaborador);
}
    

function AbonarBoleto(){
	
	$('#boleto-extravio').hide();
	$('#boleto-info').hide();
	$('#boleto-venta').show();
}


function CancelarventaBoleto(){
	
	$('#boleto-extravio').hide();
	$('#boleto-venta').hide();
	$('#boleto-info').show();
	
	
}


function ReportedeExtravioBoleto(){
	
	$('#boleto-info').hide();
	$('#boleto-venta').hide();
	$('#boleto-extravio').show();
	
}

/****************REGISTRO DE VENTA BOLETOS******************/

function BuscarIncidenciaBoleto(idsorteo,boleto,talonario){
	
	var idsorteo =	idsorteo;
    var boleto =	boleto;
    var talonario = talonario;
    
    
    $.ajax({ 
	    type: "GET",  
      cache: false,
	    url: "BoletosSorteo",  
	    data: "view=GetIncidenciaBoleto&sorteo="+idsorteo+"&boleto="+boleto+"&talonario="+talonario,  
	    success: function(msg){  
	    	
	    
	    	
	    	if(msg.trim() =="NULL"){
	    		
	    		$('#incidencia').val('N');
	    		$('#formatofc8').val('');
	    		$('#folioactamp').val('');
	    		$('#detallesincidencia').val('');
	    		$('#divincidencia').hide();
	    		
	    	}else{
	    		
	    		var res = msg.split("|");
	    		var incidencia ="";
	    	
	    		
	    		
	    		if(res[2].trim()=="E"){ incidencia='<span class="label label-danger">EXTRAVIO DE BOLETO</span>'; }
	    		if(res[2].trim()=="R"){ incidencia='<span class="label label-inverse">ROBO DE BOLETO</span>'; }
	    		
	    		$('#divincidencia').show();
	    		$('#tincidencia').html(incidencia);
	    		$('#tformatoincidencia').html(res[1]);
	    		$('#tfoliomp').html(res[3]);
	    		$('#tdetallesincidencia').html(res[4]);
	    		
	    		
	    		
	    		
	    		$('#incidencia').val(res[2].trim());
	    		$('#formatofc8').val(res[1]);
	    		$('#folioactamp').val(res[3]);
	    		$('#detallesincidencia').val(res[4]);
	    		
	    		
	    		 
	    	}
	    	
	    	/*
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	*/
	    	
	               } 
	   
	           });
    
    
}

function BuscarComprador(idsorteo,pkboleto,boleto,pktalonario,talonario,abono,monto,pksector,pknicho,pkcolaborador){
	
	var idsorteo =	idsorteo;
    var boleto =	boleto;
    var talonario = talonario;
    var abono = abono;
    var monto = monto;
    
    var urlseguimientoboleto = "SeguimientoBoletos?idsorteo="+idsorteo+"&idboleto="+pkboleto+"&menu=2";
    $("#linkseguimientoboleto").attr("href", urlseguimientoboleto);
    
    $('#divincidencia').hide();
    $('#sorteo').val(idsorteo);
    $('#pkboleto').val(pkboleto);
    $('#pktalonario').val(pktalonario);
    $('#boleto').val(boleto);
    $('#talonario').val(talonario);
    $('#costo').val(monto);
    
    $('#pksector').val(pksector);
    $('#pknicho').val(pknicho);
    $('#pkcolaborador').val(pkcolaborador);
    
    $('#pkabono').val(abono);
    $('#pkcosto').val(monto);
    
    $('#boletofolio').html(boleto);
    $('#talonariofolio').html(talonario);
    $('#boletofolioabono').html(boleto);
    $('#talonariofolioabono').html(talonario);
    
    $('#incidenciaboletofolio').html(boleto);
    $('#incidenciatalonariofolio').html(talonario);
    
    
    $('#montoboleto').html("$"+monto);
    $('#abonoboleto').html("$"+abono);
    
    
    /*
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
     }); */
	
   
	$.ajax({ 
	    type: "GET",
	    cache: false,
	    url: "BoletosSorteo",  
	    data: "view=GetComprador&sorteo="+idsorteo+"&boleto="+boleto+"&talonario="+talonario,  
	    success: function(msg){  
	    	
	    	//alert(msg);
	    	
	    	if(msg.trim() =="NULL"){
	    		
	    		$( "#estatusboleto" ).removeClass().addClass( "badge badge-inverse badge-square" );
	    		$( "#estatusboleto" ).html('NO VENDIDO');
	    		$('#comprador').html('----------------------');
	    		$('#telefonofijo').html('---------');
	    		$('#telefonomovil').html('---------');
	    		$('#correo').html('---------');
	    		
	    		$('#calle').html('---------');
	    		$('#numero').html('----');
	    		
	    		$('#colonia').html('---------');
	    		$('#estado').html('---------');
	    		$('#municipio').html('---------');
	    		
	    		$('#iabono').val("0");
	    	     $('#inombre').val("");
	    	     $('#iapellidos').val("");
	    	     $('#itelefonofijo').val("");
	    	     $('#itelefonomovil').val("");
	    	     $('#icorreo').val("");
	    	     $('#icalle').val("");
	    	     $('#inumero').val("");
	    	     $('#icolonia').val("");
	    	     $('#iestado').val("");
	    	     $('#imunicipio').val("");
	    		
	    	}else{
	    		
	    		var res = msg.split("|");
	    		
	    		if(parseInt(abono)<parseInt(monto)){ $( "#estatusboleto" ).removeClass().addClass( "badge badge-default badge-square" ); $( "#estatusboleto" ).html('VENDIDO (P)');  }
	    		if(parseInt(abono)==parseInt(monto)){ $( "#estatusboleto" ).removeClass().addClass( "badge badge-success badge-square" ); $( "#estatusboleto" ).html('VENDIDO'); }
	    		if(parseInt(abono)==0){ $( "#estatusboleto" ).removeClass().addClass( "badge badge-inverse badge-square" ); $( "#estatusboleto" ).html('NO VENDIDO'); }
	    		
	    		$('#abonoboleto').html("$"+res[1]);
	    		$('#comprador').html(res[2]+" "+res[3]);
	    		$('#telefonofijo').html(res[4]);
	    		$('#telefonomovil').html(res[5]);
	    		$('#correo').html(res[6]);
	    		
	    		$('#calle').html(res[7]);
	    		$('#numero').html(res[8]);
	    		
	    		$('#colonia').html(res[9]);
	    		$('#estado').html(res[10]);
	    		$('#municipio').html(res[11]);
	    		
	    		
	    		 $('#iabono').val(res[1]);
	    	     $('#inombre').val(res[2]);
	    	     $('#iapellidos').val(res[3]);
	    	     $('#itelefonofijo').val(res[4]);
	    	     $('#itelefonomovil').val(res[5]);
	    	     $('#icorreo').val(res[6]);
	    	     $('#icalle').val(res[7]);
	    	     $('#inumero').val(res[8]);
	    	     $('#icolonia').val(res[9]);
	    	     
	    	     //$('#iestado').val(res[10]);
	    	     
	    	     $('#iestado option:contains("'+res[10].trim()+'")').attr('selected', 'selected');
	    	     getMunicipios(
	    	    	function(municipio)
	    	    	{
	    	    		$('#imunicipio option:contains("'+municipio+'")').attr('selected', 'selected');
	    	     	}, res[11].trim()
	    	     );
	    	}
	    	
	    	//BUSCAMOS INCIDENCIAS DEL BOLETO A MOSTRAR
	    	BuscarIncidenciaBoleto(idsorteo,boleto,talonario);
	    	
	    	/*
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	*/
	    	
		}
	});
}

function AbonarBoletoRapido()
{
	if (detalles_de_venta_rapida != null)
	{
		$('#sorteo').val(detalles_de_venta_rapida.idsorteo);
		$('#boleto').val(detalles_de_venta_rapida.boleto);
		$('#talonario').val(detalles_de_venta_rapida.talonario);
		$('#costo').val(detalles_de_venta_rapida.costo);
		var operacion = "InsertComprador";
		$('#pktalonario').val(detalles_de_venta_rapida.pktalonario);
		$('#pkboleto').val(detalles_de_venta_rapida.pkboleto);
		$('#pksector').val(detalles_de_venta_rapida.pksector);
		$('#pknicho').val(detalles_de_venta_rapida.pknicho);
		$('#pkcolaborador').val(detalles_de_venta_rapida.pkcolaborador);
		$('#iabono').val(detalles_de_venta_rapida.costo);
		
		$('#inombre').val("XXXXX");
		$('#iapellidos').val("XXXXX");
		$('#itelefonofijo').val("XXXXX");
		$('#itelefonomovil').val("XXXXX");
		$('#icorreo').val("@");
		$('#icalle').val("XXXXX");
		$('#inumero').val("11111");
		$('#icolonia').val("XXXXX");
		
    	$('#iestado').val("XXXXX");
    	$('#imunicipio').val("XXXXX");
    	
		InsertarVentaComprador('venta_rapida');
	}
}

function InsertarVentaComprador(venta_rapida){
	
	var idsorteo = $('#sorteo').val();
    var boleto = $('#boleto').val();
    var talonario = $('#talonario').val();
    var costo = $('#costo').val();
    var operacion = "InsertComprador";
    
    var pktalonario = $('#pktalonario').val();
    var pkboleto = $('#pkboleto').val();
    var pksector = $('#pksector').val();
    var pknicho = $('#pknicho').val();
    var pkcolaborador = $('#pkcolaborador').val();
    
	var abono = $('#iabono').val();
	var nombre = $('#inombre').val();
	var apellidos = $('#iapellidos').val();
	var telefonofijo = $('#itelefonofijo').val();
	var telefonomovil = $('#itelefonomovil').val();
	var correo = $('#icorreo').val();
	var calle = $('#icalle').val();
	var numero = $('#inumero').val();
	var colonia = $('#icolonia').val();
	var estado = $('#iestado').find('option:selected').text();
	var municipio = $('#imunicipio').find('option:selected').text();
	
	var estadoboleto = "";
	
	if(venta_rapida=='venta_rapida'){
		estado='x';
		municipio='x';
	}
	
	if(abono!="" && abono!=0 && nombre!="" && apellidos!= "" && telefonofijo!="" && telefonomovil!="" && correo!="" && calle!="" && numero!="" && colonia!="" && estado!="" && municipio!="")
	{
		if (parseInt(abono) <= parseInt(costo))
		{
			$.ajax({
				type: "POST",  
				cache: false,
				url: "BoletosSorteo",  
				data: { operacion: operacion, sorteo:idsorteo,talonario:talonario, boleto:boleto,costo:costo,abono:abono,nombre:nombre,apellidos:apellidos,telefonofijo:telefonofijo,telefonomovil:telefonomovil,correo:correo,calle:calle,numero:numero,colonia:colonia,estado:estado,municipio:municipio,pkboleto:pkboleto,pksector:pksector,pknicho:pknicho,pkcolaborador:pkcolaborador,pktalonario:pktalonario},
				success: function(msg){
					
					$('#comprador').html(nombre + " " + apellidos);
					$('#telefonofijo').html(telefonofijo);
					$('#telefonomovil').html(telefonomovil);
					$('#correo').html(correo);

					$('#calle').html(calle);
					$('#numero').html(numero);

					$('#colonia').html(colonia);
					$('#estado').html(estado);
					$('#municipio').html(municipio);
					$('#pkabono').val(abono);
    		
    		
					if(parseInt(abono)<parseInt(costo)){ $( "#estatusboleto" ).removeClass().addClass( "badge badge-default badge-square" ); $( "#estatusboleto" ).html('VENDIDO (P)');  }
					if(parseInt(abono)==parseInt(costo)){ $( "#estatusboleto" ).removeClass().addClass( "badge badge-success badge-square" ); $( "#estatusboleto" ).html('VENDIDO'); }

	    	
			    	$('#abonoboleto').html("$"+abono);
			    	$.gritter.add({title:"Venta de Boleto",text:'Se ha registrado la venta del boleto <b style="color:#DED9BF;">'+ultimoFolio+'</b>'});
			    	CancelarventaBoleto();
			    	Listar.init();
			    }
			});
		}
		else{
			alert("Debe ingresar una cantidad menor o igual al costo del boleto: $"+costo);
		}
    }
	else{
		if(abono==0)
		{
    		//SE ELIMINA LA VENTA DEL BOLETO
    		if(confirm("Se eliminara la venta del boleto?"))
    		{
    			operacion = "eliminarVenta";
    			
				$.ajax({ 
					type: "POST",
					cache: false,
					url: "BoletosSorteo",  
					data: { operacion: operacion, sorteo:idsorteo,talonario:talonario, boleto:boleto,costo:costo,pkboleto:pkboleto,pksector:pksector,pknicho:pknicho,pkcolaborador:pkcolaborador,pktalonario:pktalonario},
					success: function(msg){
						
						//alert(msg);
						/*
						$('div.block').unblock(); 
						$("#listresultados").html(msg);
						*/
						
						$('#pkabono').val('0');
						$( "#estatusboleto" ).removeClass().addClass( "badge badge-inverse badge-square" ); $( "#estatusboleto" ).html('NO VENDIDO');
						$('#abonoboleto').html("$0.00");
						$('#comprador').html('----------------------');
						$('#telefonofijo').html('---------');
						$('#telefonomovil').html('---------');
						$('#correo').html('---------');
						
						$('#calle').html('---------');
						$('#numero').html('----');
						
						$('#colonia').html('---------');
						$('#estado').html('---------');
						$('#municipio').html('---------');
						
						$('#iabono').val("0");
						$('#inombre').val("");
						$('#iapellidos').val("");
						$('#itelefonofijo').val("");
						$('#itelefonomovil').val("");
						$('#icorreo').val("");
						$('#icalle').val("");
						$('#inumero').val("");
						$('#icolonia').val("");
						$('#iestado').val("");
						$('#imunicipio').val("");
						
						$.gritter.add({title:"Venta de Boleto",text:'Se ha ELIMINADO la venta del boleto <b style="color:#DED9BF;">'+ultimoFolio+'</b>'});
						CancelarventaBoleto();
						Listar.init();
					}
				});
			}
    			
		}else{
			alert("Debes de llenar todos los campos.");	
		}
	}
}

function RegistrarIncidenciaBoleto(){
	
	var idsorteo = $('#sorteo').val();
    var boleto = $('#boleto').val();
    var talonario = $('#talonario').val();
    
    var pkboleto = $('#pkboleto').val();
    var pksector = $('#pksector').val();
    var pknicho = $('#pknicho').val();
    var pkcolaborador = $('#pkcolaborador').val();
    
    var abono = $('#pkabono').val();
    var costo = $('#pkcosto').val();
    
    
    var deleteincicencia = false;
   
        var operacion = "InsertIncidencia";
        var incidencia = $('#incidencia').val();
        
        if(incidencia.trim()=="N"){
        	operacion = "EliminarIncidencia";
        	
        	if(confirm("Desea eliminar la incidencia?")){
        		deleteincicencia =  true;
        	}
        }
        
    	var formatofc8 = $('#formatofc8').val();
    	var folioactamp =$('#folioactamp').val();
    	var detallesincidencia = $('#detallesincidencia').val();
    	
    	
    	
         if((deleteincicencia==false && incidencia.trim()!="N") | (deleteincicencia==true && incidencia.trim()=="N") ){ 
    	       
        	 
    	$.ajax({ 
    	    type: "POST",  
          cache: false,
    	    url: "BoletosSorteo",  
    	    data: { operacion: operacion, sorteo:idsorteo,talonario:talonario, boleto:boleto,incidencia:incidencia,formatofc8:formatofc8,folioactamp:folioactamp,detallesincidencia:detallesincidencia,pksector:pksector,pknicho:pknicho,pkcolaborador:pkcolaborador,pkboleto:pkboleto,abono:abono,costo:costo},
    	    success: function(msg){  
    	    	
    	    	/*
    	    	$('div.block').unblock(); 
    	    	$("#listresultados").html(msg);
    	    	*/
    	    	if(deleteincicencia!=false){
    	    		$('#formatofc8').val('');
    	    		$('#folioactamp').val('');
    	    		$('#detallesincidencia').val('');
    	    		$('#divincidencia').hide();
    	    		
    	    		$.gritter.add({title:"ELIMINADA Incidencia de Boleto",text:'Se ha eliminada la incidencia del BOLETO <b style="color:#DED9BF;">'+ultimoFolio+'</b>'});
    	    	}else{
    	    		
    	    		
    	    		if(incidencia.trim()=="E"){ incidencia='<span class="label label-danger">EXTRAVIO DE BOLETO</span>'; }
    	    		if(incidencia.trim()=="R"){ incidencia='<span class="label label-inverse">ROBO DE BOLETO</span>'; }
    	    		
    	    		$('#tincidencia').html(incidencia);
    	    		$('#tformatoincidencia').html(formatofc8);
    	    		$('#tfoliomp').html(folioactamp);
    	    		$('#tdetallesincidencia').html(detallesincidencia);
    	    		$('#divincidencia').show();
    	    		
    	    		$.gritter.add({title:"Incidencia de Boleto",text:'Se ha registrado una incidencia al BOLETO <b style="color:#DED9BF;">'+ultimoFolio+'</b>'});
    	    	}
    	    		
    	    	CancelarventaBoleto();
            Listar.init();
    	               } 
    	   
    	           });
         }
        
	
}
  

/**************END REGISTRO DE VENTA DE BOLETOS***************/


/**************REGISTRO DE VENTA DE TALONARIOS***************/


function BuscarMontoAbonoTalonario(sorteo,pk_talonario,idtalonario){
		
	

    var idsorteo =	sorteo;
    var talonario = idtalonario;
    
    
    var urlseguimientotalonario = "SeguimientoTalonarios?idsorteo="+idsorteo+"&idtalonario="+pk_talonario+"&menu=2";
    $("#linkseguimientotalonario").attr("href", urlseguimientotalonario);
     
    
    $.ajax({ 
	    type: "GET",  
      cache: false,
	    url: "BoletosSorteo",  
	    data: "view=BuscarMontoAbonoTalonario&sorteo="+idsorteo+"&talonario="+talonario,
	    success: function(msg){  
	    	
	    	
	    	if(msg.trim() =="NULL"){

	    	}else{
	    		    	
	           var res = msg.split("|");	
	    	
	           $('#montototalonario').html("$"+res[1]);
	           $('#abonototaltalonario').html("$"+res[2]);
	           $('#totalboletostalonario').html(res[3]);
	           
	           BuscarBoletosTalonarios(sorteo,pk_talonario,idtalonario);
	    	}
	    	
	    } 
	   
        });
	
}



function BuscarBoletosTalonarios(sorteo,pk_talonario,idtalonario){
		
	
    var idsorteo =	sorteo;
    var talonario = idtalonario;
    
    
    $.ajax({ 
	    type: "GET",  
      cache: false,
	    url: "BoletosSorteo",  
	    data: "view=BuscarBoletosTalonarios&sorteo="+idsorteo+"&talonario="+talonario,  
	    success: function(msg){  
	    	
	    	$('#boletostalonario').html(msg);
	    	
	    } 
	   
        });
	
}


/**************END REGISTRO DE VENTA DE TALONARIOS***************/


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



function retornarBoletos()
{
	var arr = $("input:checked.boletoschecked").getCheckboxValues();
 
	 if(arr.length==0){
		alert("Debe elegir un boleto");
	 }else{
		
		 $('#myModalRetornoB').modal('show');
		 $('#idboletosr').val(arr);
		
		    $('#aceptarmodalRetornoB').removeAttr( "onClick" );
			$("#aceptarmodalRetornoB").attr("onClick","RetornoBoletosVendidos()");
		//	$('#folio').val("");
			
		 		
	 }
}


function RetornoBoletosVendidos ()
{
	var idboletos = $('#idboletosr').val();
	//var folio = $('#folio').val();	
	var operacion = 'RetornoBoletosV';
	
	//alert(idboletos);
	
	

	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
	}); 


	
	$.ajax({
		type: "POST",
    cache: false,
		url: "BoletosSorteo",
		data: { idboletos: idboletos,operacion:operacion/*,folio: folio*/},
		success: function(msg){
			
			 $('#myModalRetornoB').modal('hide');
			$('#idboletosr').val("");
			Listar.init();
			$('div.block').unblock();
			
			if(msg.trim()=="")
			{
				
				 $.gritter.add({title:"BOLETOS RETORNADOS",text:"Se ha retornado con exito los boletos!"});
			}
			else{
					$('html, body').animate({scrollTop:0}, 10);
					$("#alerta").fadeIn();
					$("#alerta").removeClass();
	                $("#alerta").addClass("alert alert-error");
					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se retorno los boletos, por favor intentalo nuevamente.");     					
			}
			
		},
		error: function(msg) {
			//alert("Error");
		}
	});
}


function getMunicipios(f_success, f_success_parametros) {

	var idestado = $('#iestado').find('option:selected').val();
	var estado = $('#iestado').find('option:selected').text();

	$.ajax({
		type : "GET",
		cache : false,
		url : "BoletosSorteo",
		data : "view=getMunicipios&idestado=" + idestado + "&estado=" + estado,
		success : function(msg) {

			$('#imunicipio').html(msg);
			
			if(f_success!=null)
				f_success(f_success_parametros);
		},
		error : function(msg){
			console.log('getMunicipios/ajax/error');
		}

	});

}


    
    
    