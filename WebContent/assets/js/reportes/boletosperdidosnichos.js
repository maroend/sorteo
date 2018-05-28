var pag = 1;


var Listar=function(){"use strict";return{init:function(){ 
	
	
	reporteTalonariosNicho();
	
}}}()




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
    	
    	
     var show =	$("#data-elements-length").val();
     var search =	$("#searchtable").val();
     
     if(search!=""){ pag=1;}
	
	
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
	    cache: false,
	    url: "BoletosPerdidosNichos",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	               } 
	   
	           });
    	
    	
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
    
    
    
    
    
    
    
    
    
    /////////////////////TALONARIOS Y BOLETOS SORTEO ////////////
    var reporteTalonarios = function(){
    
   	$.ajax({ 
   	    type: "GET",  
   	    cache: false,
   	    url: "BoletosExtraviados",  
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
   	    	
   	    	
   	    	consultaBoletos();
   	    	
   	    	
   	               } 
   	   
   	           });
       	
       }   
    
   ////////////////////END BOLETOS Y TALONARIOS SORTEO///////////// 
    
    
    
    ////////////////BOLETOS Y TALONARIOS SECTOR /////////////////////
    
    function reporteTalonariosSector(){
        
       	$.ajax({ 
       	    type: "GET",
       	    cache: false,  
       	    url: "BoletosPerdidosSectores",  
       	    data: "view=getBoletosTalonariosSector",  
       	    success: function(msg){  
       	    	
       	    	handleListar();
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
       	    	
       	    	
       	    	
       	               } 
       	   
       	           });
           	
           }   
    
   
    ///////////////END BOLETOS Y TALONARIOS SECTOR////////////////
    
    
    
 ////////////////BOLETOS Y TALONARIOS NICHO /////////////////////
    
    function reporteTalonariosNicho(){
        
       	$.ajax({ 
       	    type: "GET",  
       	    cache: false,
       	    url: "BoletosPerdidosNichos",  
       	    data: "view=getBoletosTalonariosNicho",  
       	    success: function(msg){  
       	    	
       	 	handleListar();
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
       	    	
       	    	
       	               } 
       	   
       	           });
           	
           }   
    
   
    ///////////////END BOLETOS Y TALONARIOS NICHO////////////////
    
    
    
    
////////////////BOLETOS Y TALONARIOS COLABORADOR /////////////////////
    
    function reporteTalonariosColaborador(idsorteo,idsector,idnicho,idcolaborador){
        
       	$.ajax({ 
       	    type: "GET",  
       	    cache: false,
       	    url: "BoletosExtraviados",  
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
    

function ShowDetalleBoleto(idsorteo,pkboleto,boleto,pktalonario,talonario,abono,monto,pksector,pknicho,pkcolaborador){
	
	$('#modalDetalleTalonario').modal('hide')  
	
	$('#modalDetalleBoleto').modal({
		show : true,
		backdrop : 'static'
	});
	
	CancelarventaBoleto();
	BuscarComprador(idsorteo,pkboleto,boleto,pktalonario,talonario,abono,monto,pksector,pknicho,pkcolaborador);
	
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
	
	
	$('#titlesector').html(titulo);
	reporteTalonariosSector(sorteo,idsector);
}
 


function ShowDetalleNicho(sorteo,idsector,idnicho,titulo){
	
	$('#modalDetalleNicho').modal({
		show : true,
		backdrop : 'static'
	});
	
	$('#titlenicho').html(titulo);
	reporteTalonariosNicho(sorteo,idsector,idnicho);
}


function ShowDetalleColaborador(sorteo,idsector,idnicho,idcolaborador,titulo){
	
	$('#modalDetalleColaborador').modal({
		show : true,
		backdrop : 'static'
	});
	
	$('#titlecolaborador').html(titulo);
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
	    url: "BoletosExtraviados",  
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
	    url: "BoletosExtraviados",  
	    data: "view=GetComprador&sorteo="+idsorteo+"&boleto="+boleto+"&talonario="+talonario,  
	    success: function(msg){  
	    	
	    	//alert(msg);
	    	
	    	if(msg.trim() =="NULL"){
	    		
	    		$( "#estatusboleto" ).removeClass().addClass( "badge badge-inverse badge-square" ); $( "#estatusboleto" ).html('NO VENDIDO');
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
	    	     $('#iestado').val(res[10]);
	    	     $('#imunicipio').val(res[11]);
	    		
	    		 
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

function InsertarVentaComprador(){
	
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
    	var apellidos =$('#iapellidos').val();
    	var telefonofijo =$('#itelefonofijo').val();
    	var telefonomovil = $('#itelefonomovil').val();
    	var correo = $('#icorreo').val();
    	var calle = $('#icalle').val();
    	var numero = $('#inumero').val();
    	var colonia = $('#icolonia').val();
    	var estado = $('#iestado').val();
    	var municipio = $('#imunicipio').val();
    	
    	var estadoboleto = "";
    	
	/*
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
     }); */
	
    	if(abono!="" && abono!=0 && nombre!="" && apellidos!= "" && telefonofijo!="" && telefonomovil!="" && correo!="" && calle!="" && numero!="" && colonia!="" && estado!="" && municipio!=""){
   
    		  if(parseInt(abono)<=parseInt(costo)){
    		  
	$.ajax({ 
	    type: "POST",  
	    cache: false,
	    url: "BoletosExtraviados",  
	    data: { operacion: operacion, sorteo:idsorteo,talonario:talonario, boleto:boleto,costo:costo,abono:abono,nombre:nombre,apellidos:apellidos,telefonofijo:telefonofijo,telefonomovil:telefonomovil,correo:correo,calle:calle,numero:numero,colonia:colonia,estado:estado,municipio:municipio,pkboleto:pkboleto,pksector:pksector,pknicho:pknicho,pkcolaborador:pkcolaborador,pktalonario:pktalonario},
	    success: function(msg){  
	    	
	    	//alert(msg);
	    	/*
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	*/
	    	
	    	$('#comprador').html(nombre+" "+apellidos);
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
	    	$.gritter.add({title:"Venta de Boleto",text:"Se ha registrado la venta del boleto"});
	    	CancelarventaBoleto();
	    	Listar.init();           
	    
	    } 
	   
	           });
    		  }else{
    			  
    			  alert("Debe ingresar una cantidad menor o igual al costo del boleto: $"+costo);
    		  }
	
    	}else{
    		
    		if(abono==0){
    			//SE ELIMINA LA VENTA DEL BOLETO
    			if(confirm("Se eliminara la venta del boleto?")){
    			operacion = "eliminarVenta";
    			
    			$.ajax({ 
    			    type: "POST",  
    			    cache: false,
    			    url: "BoletosExtraviados",  
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
    			    	
    			    	$.gritter.add({title:"Venta de Boleto",text:"Se ha ELIMINADO la venta del boleto"});
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
    	    url: "BoletosExtraviados",  
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
    	    		
    	    		$.gritter.add({title:"ELIMINADA Incidencia de Boleto",text:"Se ha eliminada la incidencia del BOLETO"});
    	    	}else{
    	    		
    	    		
    	    		if(incidencia.trim()=="E"){ incidencia='<span class="label label-danger">EXTRAVIO DE BOLETO</span>'; }
    	    		if(incidencia.trim()=="R"){ incidencia='<span class="label label-inverse">ROBO DE BOLETO</span>'; }
    	    		
    	    		$('#tincidencia').html(incidencia);
    	    		$('#tformatoincidencia').html(formatofc8);
    	    		$('#tfoliomp').html(folioactamp);
    	    		$('#tdetallesincidencia').html(detallesincidencia);
    	    		$('#divincidencia').show();
    	    		
    	    		$.gritter.add({title:"Incidencia de Boleto",text:"Se ha registrado una incidencia de BOLETO"});
    	    	}
    	    		
    	    	CancelarventaBoleto();
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
	    url: "BoletosExtraviados",  
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
	    url: "BoletosExtraviados",  
	    data: "view=BuscarBoletosTalonarios&sorteo="+idsorteo+"&talonario="+talonario,  
	    success: function(msg){  
	    	
	    	$('#boletostalonario').html(msg);
	    	
	    } 
	   
        });
	
}


/**************END REGISTRO DE VENTA DE TALONARIOS***************/



////////////////////////////////////GREFICA//////////////////////////////////


function consultaBoletos() {

	$.ajax({ 
		type: "POST",  
		cache: false,
		url: "grafica",  
		data: "",
		success:
   	    	function(msg){
			
			handleListar();
			
   	    		var data = jQuery.parseJSON(msg);
   	    		
   	    		$('#grafica').highcharts('StockChart', {
   	    			
   	    			rangeSelector : {
   	    				selected : 1
   	    			},
   	    			
   	    			title : {
   	    			    text : 'Detalle de Venta de Boletos'
   	    			},
   	    			
   	    			series : [{
   	    				name : 'Boletos Vendidos',
   	    				data : data,
   	    				tooltip: {
   	    					/*valueDecimals: 0*/
   	    				}
   	    			}]
   	    		});
   	    	}
   	});
}






