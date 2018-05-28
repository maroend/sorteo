/*   
App Sorteo Anáhuac
Version: 1.0
Author: Jose Carlos Ruiz Garcia
Website: http://www.redanahuac.mx/app/sorteo
*/




var pag = 1;


var Listar=function(){"use strict";return{init:function(){ 
	
	
	handleListar();
	
}}}()



//
var guardar=function(){"use strict";return{init:function(){ 
	
	handlecontrollersave();
	
}}}()



$(function(){
	
	
	$('#alerta').hide();
	
	
	
	
	$(window).load(function() {
		
		Listar.init();

		});
		    
	
	

});//End function jquery

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
	    url: "Menus",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               } 
	   
	           });
    	
    	
    }    
    
    
    
    
    
    //SECCION AGREGAR 
   
    
    function Guardar(){
    	
    	$('#alerta').hide();
    	
    	if( $('#formulario').parsley().validate() ){    		 
            
   		 guardar.init();
        }
        return false;
    	
    	
    	
    	
    }  
    
 
    
    
function BuscarEditar(id){ 
	
	
	$('#alerta').hide();
	$("#guardar").text('Editar');    	
	$("#guardar").attr("onClick", "javascript:Editar('"+id+"');return false;");
	
	
		
    	 $('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
       }); 
 	
     
 	$.ajax({ 
 	    type: "GET",  
        cache: false,
 	    url: "Menus",  
 	    data: "view=BuscarEditar&id="+id,  
 	    success: function(msg){ 	
 	    	
 	    	$('div.block').unblock(); 	    	
 	    	
 	    	var contenido = msg.split('#%#');
 	    	
 	    	$('#nombre').val(contenido[0]);
 	    	$('#url').val(contenido[1]); 	    
 	    	$("#padre option[value="+ contenido[2] +"]").attr("selected",true); 	    
 	      	$("#orden option[value="+ contenido[3] +"]").attr("selected",true); 
 	      	
 	   //  alert(contenido[4]);
 	      	
 	    	$("#disponible option[value="+ contenido[4] +"]").attr("selected",true); 
 	    	
 	    	//alert(contenido[4]);
 	     	// $('#disponible').val(contenido[4]); 		  
 	     	$("#permiso option[value="+ contenido[5] +"]").attr("selected",true); 	    
 	    	 
 	    	
 	    	
 	        } 
 	   
 	   });
    	
    	
    	
    	
    }
    
    
    
    
    
    
    function Editar(id){
    	
    	
    	
    	$('#alerta').hide();
    	
    	
    if( $('#formulario').parsley().validate() ){    		 
            
      		
            var nombre = $('#nombre').val();    	
	    	var url = $('#url').val();
	    	var padre = $('#padre').val();
	    	var orden = $('#orden').val();
	    	var permiso = $('#permiso').val();    
	    	var disponible = $('#disponible').val();
   
    
    	
    	 $('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Editando..</h2>'
       }); 
 	
     
 	$.ajax({ 
 	    type: "GET",  
        cache: false,
 	    url: "Menus",  
 	    data: "view=Editar&id="+id+"&nombre="+nombre+"&url="+url+"&padre="+padre+"&orden="+orden+"&permiso="+permiso+"&disponible="+disponible,  
 	    success: function(msg){  
 	    	
 	    	
 	   	    $("#guardar").attr("onClick", "javascript:Guardar();return false;");  
 	    	$("#guardar").text('Guardar'); 
 	    	$('div.block').unblock();    	
 	    	Listar.init();
 	    	
 	  	 $('#nombre').val("");
    	 $('#url').val("");
    	 $('#padre').val("");
    	 $('#orden').val("");
    	 $('#permiso').val("");
    	 $('#disponible').val("");
    	 
 	         if(msg.trim()==""){ 
 	    		    
 	        	     $('html, body').animate({scrollTop:0}, 10);
 	    	    	 $("#alerta").fadeIn();
 			      	 $("#alerta").removeClass();
                     $("#alerta").addClass("alert alert-success");
 			      // $("#headAlerta").html("¡Correcto!");
 				     $("#bodyAlerta").html("Se ha editado con exito el menu"); 	
 	    		
 	    	   }else{
 	    		   
 	    		   $('html, body').animate({scrollTop:0}, 10);
 					$("#alerta").fadeIn();
 					$("#alerta").removeClass();
 	                $("#alerta").addClass("alert alert-error");
 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se edito el menu correctamente, por favor intentalo editar nuevamente."); 
 	    		   
 	    		   
 	    	   }
 	    	   
	        	
 	    	
 	    	
 	        } 
 	   
 	   });
    	
    }
    return false;
    	
    	
  }
    
    
    
    
    
    

var handlecontrollersave = function(){
    	
    	var nombre = $('#nombre').val();    	
    	var url = $('#url').val();
    	var padre = $('#padre').val();
    	var orden = $('#orden').val();
    	var permiso = $('#permiso').val();
    	var disponible = $('#disponible').val();
    	
    	
    	
    	
    	var action = "INSERT";
    	
    	$('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
       }); 
 	
    
    	
    	$.ajax({ 
    	    type: "POST",  
            cache: false,
    	    url: "Menus",  
    	    data: { nombre: nombre, url: url, padre : padre, orden : orden, permiso : permiso,action:action, disponible: disponible},  
    	    success: function(msg){  
    	    	
    	    	Listar.init();
    	    	
    	    	 $('#nombre').val("");
    	    	 $('#url').val("");
    	    	 $('#padre').val("");
    	    	 $('#orden').val("");
    	    	 $('#permiso').val("");
    	    	 $('#disponible').val("");
    	    	 
    	    	 
    	    	 
        
    	    	 $('div.block').unblock();  
    	    	
    	    	if(msg.trim()==""){     	        			    
    	  	       
    	    	 
     	    	 
     	    	        $('html, body').animate({scrollTop:0}, 10);
    	    		    $("#alerta").fadeIn();
    					$("#alerta").removeClass();
    	                $("#alerta").addClass("alert alert-success");
    				//	$("#headAlerta").html("¡Correcto!");
    					$("#bodyAlerta").html("Se ha guardado con exito el menu"); 	
    		        	
    		        	
    		        	
    				}else{
    					
    					
    					    $('html, body').animate({scrollTop:0}, 10);
    	 					$("#alerta").fadeIn();
    	 					$("#alerta").removeClass();
    	 	                $("#alerta").addClass("alert alert-error");
    	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
    	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se guardo el menu, por favor intentalo nuevamente."); 
    					
    					
    					
    			   }    	    	
    	    	
    	    	
    	    	
    	            } 
    	           });
	
	
     }


function Borrar(id){ 
	
	  var confirm=window.confirm("Esta seguro de Borrar el Menu?");
	    if (confirm==true) {	
	    	
    	 $('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Borrando..</h2>'
       }); 
 	
     
 	$.ajax({ 
 	    type: "GET", 
        cache: false, 
 	    url: "Menus",  
 	    data: "view=Borrar&id="+id,  
 	    success: function(msg){  	    	
 	    	
 	    	$('div.block').unblock();   	 	    	
 	    	
 	    	
 	        if(msg.trim()==""){ 
 	        	
 	        	     $('html, body').animate({scrollTop:0}, 10);
 	    	   	     $("#alerta").fadeIn();
 			      	 $("#alerta").removeClass();
                     $("#alerta").addClass("alert alert-success");
 			      // $("#headAlerta").html("¡Correcto!");
 				     $("#bodyAlerta").html("Se ha eliminado con exito el menu"); 	
 	    		
 	    	   }else{
 	    		   
 	    		  $('html, body').animate({scrollTop:0}, 10);
 					$("#alerta").fadeIn();
 					$("#alerta").removeClass();
 	                $("#alerta").addClass("alert alert-error");
 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se elimino el menu, por favor intentalo eliminar nuevamente."); 
 	    		   
 	    		   
 	    	   }
 	       pag = 1; 
 	       Listar.init();
 	       
 	    	   
	        	
 	    	
 	    	
 	        } 
 	   
 	   });
    	
	 }	
    	
    	
   }



function Comision(idsector)
{
	    $("#comision").val('');
	    $('#formulario2').parsley().reset();		 
		$("#aceptarmodal").attr("onClick", "javascript:actualizarComision('"+idsector+"');return false;");			 
		$('#myModal').modal('show');		
		    		
	 
 }

function actualizarComision(idsector){  	
    
	
	var comision = $("#comision").val();
	
if( $('#formulario2').parsley().validate() ){  	
	
	$('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Actualizando Estatus..</h2>'
   }); 
	

	
	/*$.ajax({ 
	    type: "POST",  
	    url: "Sectores",  
	    data: { estatus: estatus,idboletos: idboletos,abono: abono},  
	    success: function(msg){  */
	
	$.ajax({ 
 	    type: "GET",  
        cache: false,
 	    url: "Sectores",  
 	    data: "view=actualizarComision&idsector="+idsector+"&comision="+comision,  
 	    success: function(msg){ 	    	
	    	
	    	 $('#myModal').modal('hide');
	    	 $('div.block').unblock();  
	    	  Listar.init();
    	    	
	    	if(msg.trim()==""){    	 
 	    	 
 	    	   
	    	
	    		$.gritter.add({title:"ACTUALIZAR COMISIÓN:",text:"Se ha actualizado con exito!"});
		        	
		        	
		        	
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



//OBTENER VALORES
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



function EliminarSectores(){
	
	var arr = $("input:checked").getCheckboxValues();

	 if(arr.length==0){
		alert("Debe elegir un sector");
	 }else{
		 
		 $('#idsectores').val(arr); 
		 EliminarSectores2();
		 
		
	 }
 }
    




function EliminarSectores2()
{
	var idsectores = $('#idsectores').val();
	var action = "DELETE";
	
	var procede = confirm("Se aliminaran los sectores seleccionados, así como nichos y colaboradores: esta seguro?");

	if(procede){
		
		$('div.block').block({ 
			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			overlayCSS: {backgroundColor: '#FFF'},
			message: '<img src="assets/img/load-search.gif" /><br><h2> Eliminado..</h2>'
		}); 
	
		$.ajax({
		type: "POST",
		cache: false,
		url: "Sectores",
		data: { idsectores: idsectores,action:action},
		success: function(msg){
			
			
			 $('div.block').unblock();
			 Listar.init();
			
			
		},
		error: function(msg) {
			alert(msg);
		}
	});
		
	}
}


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

    
    

function ExportExcel(){
	
	var show =	 $("#data-elements-length").val();
    var search =	$("#searchtable").val();
    
    
    if(search!=""){ pag=1;}
	
	
	location.href="Sectores?view=ExportExcel&pg="+pag+"&show="+show+"&search="+search;
	
	
	
}

