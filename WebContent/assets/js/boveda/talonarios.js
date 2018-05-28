/*   
App Sorteo Anáhuac
Version: 1.0
Author: Jose Carlos Ruiz Garcia
Website: http://www.redanahuac.mx/app/sorteo
*/

var pag = 1;

var boletosTalonario;
var totaldeboletos = 0;
var numboletostalon = 0;
var numtalonario = 1;
var boletos = []; 


var Listar=function(){"use strict";return{init:function(){ 
    
	handleListar()
	
}}}()



function onkeyup_colfield_check_ViewColumn(e){
    var enterKey = 13;
    if (e.which == enterKey){
    	ListarVistacolumna()
    }
}



    function changeShowViewColumn(){
    	
    	pag=1;
    	ListarVistacolumna()
    }

    function getPagViewColumn(page){
	
		pag = page;
		ListarVistacolumna()
    }
    




     function ListarVistacolumna(){  	 
   
    	 $("#btnbuscar").attr("onclick", "javascript:ListarVistacolumna();return false;");	    	 
    	 $("#selectMaxVisibleItems").attr("onchange", "javascript:changeShowViewColumn();return false;");	
    	 $("#searchtable").attr("onkeyup", "javascript:onkeyup_colfield_check_ViewColumn(event);return false;");	    	 
    	 
    	 
    	 
    			 
	var show = $("#selectMaxVisibleItems").val();
	var search = $("#searchtable").val();
	
     
	//alert('entro');
	
	 $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
	    cache: false,
	    url: "Talonarios",  
	    data: "view=BuscarVistaColumna&pg="+pag+"&show="+show+"&search="+search,  
	    success: function(msg){  
	    	
	    	//alert(msg);
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               } 
	   
	           });
    	
    	
    } 




function onkeyup_talonario_check(e){
    
	var enterKey = 13;
        if (e.which == enterKey){
        	
        	
        	$("#hnumtalonario").val($("#barcodetalonario").val());
        	$("#barcodetalonario").prop('disabled', true);
        	$("#btntalonario").prop('disabled', true);
        	
        	$("#colboletos").show();
        	$("#barcodeboleto").focus();
        	
        	boletosTalonario = $("#boletostal").val();
        	$("#totalnumboleto").html($("#boletostal").val());
        	
        }
}





function onkeyup_boleto_check(e){
    
	var enterKey = 13;
        if (e.which == enterKey){
        	//handleListar()
        	
        	if($("#barcodeboleto").val()!=""){
        	numboletostalon++;   
        	totaldeboletos++;
        	
        	if(numboletostalon==boletosTalonario){
        		
        		
        		 boletos.push($("#barcodeboleto").val());
        		
        		 if(numtalonario<$("#numtalonario").val()){   
        		numtalonario++;
        		numboletostalon = 0;
        		boletosTalonCompletos();
        		$("#numboleto").html('1');
        		$("#barcodeboleto").val('');
        		$("#spannumtalonario").html(numtalonario);
        		$("#numtalonariosadd").html(numtalonario-1);
        		 }else{
        			 
        			 boletosTalonCompletos();
        			 
        			 $("#formtalonarios").hide();
        			 $("#numboletosadd").hide();
        			 $("#cargacompleta").show();
        			 
        			 
        			 $("#talonarioscomplete").html(numtalonario);
        			 $("#boletoscomplete").html(boletosTalonario);
        			 $("#totalboletoscomplete").html(totaldeboletos);
        			 $("#folioformato").html($("#folio").val());
        			 

        		 }
        	
        	}else{
        		
        		boletos.push($("#barcodeboleto").val());
            	$("#numboleto").html(numboletostalon);
            	var spanboleto = '<span class="label label-primary">'+$("#barcodeboleto").val()+'</span> '
            	$("#numboletosadd").show();
            	$( "#btlnm" ).append( spanboleto );
            	$("#barcodeboleto").val('');
            	$("#barcodeboleto").focus();
        		
        	}
        	 }else{
        		 
        		 
        	 }
        	    	
        }
}


function boletosTalonCompletos(){
	
	//alert("Boletos Completos de talonario");
	
	$("#colboletos").hide();
	$("#numboletosadd").hide();
	
	$( ".location" ).html('');
	
	$("#barcodetalonario").prop('disabled', false);
	$("#btntalonario").prop('disabled', false);
	
	$("#barcodetalonario").val('');
	$("#barcodetalonario").focus();
	   
	if(revisarBoletos()){
	//saveBoletos();
	}
	
}




function saveTalonario(monto){
	

	
	var folio = $("#hnumtalonario").val();
	var numboletos = $("#boletostal").val();
	var numsorteo = $("#numsorteo").val();
	var formato = $("#folio").val();
	var monto = parseFloat(monto.trim());
	
	
	
	$.ajax({ 
  	    type: "POST", 
  	    cache: false, 
  	    url: "Talonarios", 
  	    data: { folio:folio,numboletos:numboletos,numsorteo:numsorteo,formato:formato,monto:monto},  
  	    success: function(msg){  
  	    	
  	    	      $.gritter.add({title:"TALONARIO GUARDADO",text:"El talonario y sus boletos han sido guardados con exito"});

  	  	          handleListar()
  	  	
            	  setTimeout(function() {
  	  		      $.gritter.add({title:"EMPIEZA CARGA NUEVA",text:"Puede empezar a capturar"});
  	  	          }, 3000);
           
  	            } 
  	           });

}


function revisarBoletos(){
	var aboletos = boletos.join(); 
	var talonario =  $('#hnumtalonario').val();
	var sorteo =  $('#numsorteo').val();
	var formato = $("#folio").val();
	
	
	
	
	$.ajax({ 
  	    type: "GET",  
  	    cache: false,
  	    url: "Boletos", 
  	    data: "view=RevisarBoletos&boletos="+boletos+"&talonario="+talonario+"&sorteo="+sorteo+"&formato="+formato,  
  	    success: function(msg){  
  	    	
  	    	
  	    	
  	    	alert(msg);
  	    	
  	    	      //$.gritter.add({title:"TALONARIO GUARDADO",text:"El talonario y sus boletos han sido guardados con exito"});

  	  	          //handleListar()
  	  	
            	  /*setTimeout(function() {
  	  		      $.gritter.add({title:"EMPIEZA CARGA NUEVA",text:"Puede empezar a capturar"});
  	  	          }, 3000);*/
           
  	            } 
  	           });
	
}


function saveBoletos(){
	
	var aboletos = boletos.join(); 
	var costo = parseFloat($('#costo').val());
		
	var talonario =  $('#hnumtalonario').val();
	var sorteo =  $('#numsorteo').val();
	var formato = $("#folio").val();
	
	
	$.ajax({ 
  	    type: "POST",  
  	    cache: false,
  	    url: "Boletos", 
  	    data: { boletos:aboletos,talonario:talonario,costo:costo,sorteo:sorteo,formato:formato},  
  	    success: function(monto){  
  	    	
  	    	
  	    	boletos = [];
  	    	saveTalonario(monto);
  	    	
  	    	      //$.gritter.add({title:"TALONARIO GUARDADO",text:"El talonario y sus boletos han sido guardados con exito"});

  	  	          //handleListar()
  	  	
            	  /*setTimeout(function() {
  	  		      $.gritter.add({title:"EMPIEZA CARGA NUEVA",text:"Puede empezar a capturar"});
  	  	          }, 3000);*/
           
  	            } 
  	           });
	
	
}



function eliminarTalonario(id,folio,numsorteo,formato){
	
	var operacion = "eliminar";
	
	if(confirm("Esta seguro de eliminar el talonario?, se eliminaran los boletos del talonario."))
	{
		$.ajax({
			type: "POST",  
			cache: false,
			url: "Talonarios",
			data: { id:id,folio:folio,numsorteo:numsorteo,formato:formato,operacion:operacion},  
			success: function(msg){
	  	    	var data = jQuery.parseJSON(msg);
	  	    	if (data.result=='OK') {
					//$('#talonario'+id).hide('slow', function(){ $('#talonario'+id).remove(); });  
					$.gritter.add({title:"TALONARIO ELIMINADO",text:data.msg});
	  	    	}
	  	    	else if (data.result=='ERROR') {
					$.gritter.add({title:"ERROR",text:data.msg});
	  	    	}
				
				refresh();
			}
		});
	}
}


function refresh(){
	
	var show = $("#selectMaxVisibleItems").val();
    var search = $("#searchtable").val();

	$.ajax({
		type: "GET",  
		cache: false,
		url: "Talonarios",  
		data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
		success: function(msg){
			
			var response = msg.split('#%#');
			$("#listresultados").html(response[0]);
			$("#numTal").html(response[1]);
	    	
			$("[data-toggle=tooltip]").tooltip();
			$("[data-toggle=popover]").popover();
	    }
	});
} 





function onkeyup_colfield_check(e){
    var enterKey = 13;
    if (e.which == enterKey){      	
    	
    		
    	handleListar(); 	
    	
    	
    	
    }
}



    function changeShow(){
    	
    	pag=1;
    	handleListar()
    	    	
    }

    function getPag(page){
	
		pag = page;		
		handleListar()
		
		
    }
    
    
    $('#myModal').on('hide.bs.modal', function (e) {
    	  // do something...
    	
    	
        confirm("Esta seguro de suspender la carga de Talonarios");	
    
    
    });
    
    
function agregarTalonario(){
	
	$('#instrucciones').show();
	$('#formato').hide();
	$('#formtalonarios').hide();
	$('#cargacompleta').hide();
	
	
	
	//$('#myModal').modal('show');
	
	$('#myModal').modal({
		show: true,
		backdrop: 'static'
		})
	
	
	$('#aceptarmodal').removeAttr( "onClick" );
	$("#aceptarmodal").attr("onClick","mostrarFormato()");
	
	
}



function mostrarFormato(){
	
	$('#instrucciones').hide();
	$('#formato').show();
	$('#formtalonarios').hide();
	
	$('#aceptarmodal').removeAttr( "onClick" );
	$("#aceptarmodal").attr("onClick","cargaTalonarios()");
	
	//$('#aceptarmodal').click(stopMoving);
	
}



function cargaTalonarios(){
	
	
	$('#instrucciones').hide();
	$('#formato').hide();
	$('#formtalonarios').show();
	
	$("#barcodetalonario").focus();
	
	$('#numtalonarios').html($('#numtalonario').val());
	$('#numboletos').html($('#boletostal').val());
	
}



var handleListar = function() {
	

	 $("#btnbuscar").attr("onclick", "javascript:Listar.init();return false;");	
	 $("#selectMaxVisibleItems").attr("onchange", "javascript:changeShow();return false;");	
	 $("#searchtable").attr("onkeyup", "javascript:onkeyup_colfield_check(event);return false;");	 
	
	
	var show = $("#selectMaxVisibleItems").val();
	var search = $("#searchtable").val();
	
	$('div.block').block({
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 
	
	$.ajax({ 
		type: "GET",
		cache: false,
		url: "Talonarios",  
		data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
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




