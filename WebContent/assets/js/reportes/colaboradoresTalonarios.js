/*   
App Sorteo Anáhuac
Version: 1.0
Website: http://www.redanahuac.mx/app/sorteo
*/

var pag = 1;

var Listar=function(){"use strict";return{init:function(){ 
	
	handleListar()
	
}}}()



/*var buscarSectores=function(){"use strict";return{init:function(){ 
	
	handlebuscarSectores();
	
}}}()*/




var guardarColaboradores=function(){"use strict";return{init:function(){ 
	
	handlecontrollersave();
	
}}}()


$(document).ready(function() {	
	
	        $('#alerta').hide();	
			handlebuscarSectoresBuscar();
			
			
		});

/*$(function(){		
	$('#alerta').hide();
	});//End function jquery*/



 function AgregarColaboradores(idusuario){        	
        	
	    $('#formulario').parsley().reset();
	    $("#formulario")[0].reset();
	    $('#clave').val(""); 
	    $('#colonia').html(""); 
	    
	    $("#idsector option").prop("selected", false); 
	    $("#idnicho").html("");     	    	 	    	 
 	    $('#descripcion').val("");
        $("#aceptarmodal").attr("onClick", "javascript:Guardar();return false;");  
	//    buscarSectores.init();
          handlebuscarSectores();
            $('#myModal').modal('show');       
         //   $("#listresultados2").html("");
         //   ListarModal.init();  
        //    setTimeout("ExisteRoleUsuario("+idusuario+")",900);	  
           
        	
 }  
        

//SECCION AGREGAR 
function Guardar(){
	
	$('#alerta').hide();
	
	var idsector = $('#idsector').val();	
	if(idsector == "S"){
		alert('Debes de seleccionar un sector. Gracias!');
		return false;
		}
	
	
	 if( $('#formulario').parsley().validate() ){    		 
         
		 guardarColaboradores.init();
		 
	     }
	     return false;
		
	
}  




var handlecontrollersave = function(){
	
	    var idsector = $('#idsector').val();  
    	var clave = $('#clave').val();    	
    	var idnicho = $('#idnicho').val();
    	//var colaborador = $('#colaborador').val();
    	var descripcion = $('#descripcion').val();
    	
    	
    	var rbancaria = $('#rbancaria').val();
    	
    	
    	var nombre = $('#nombre').val();    	
    	var apaterno = $('#apaterno').val();
    	var amaterno = $('#amaterno').val();
    	
    	var edad = $('#edad').val();
    	var rfc = $('#rfc').val();
    	
    
    	var telefonocasa = $('#telefonocasa').val();
    	var telefonooficina = $('#telefonooficina').val();
    	var telefonomovil = $('#telefonomovil').val();
    	
    	var correopersonal = $('#correopersonal').val();
    	var correotrabajo = $('#correotrabajo').val();
    	var correootro = $('#correootro').val();
	
    	var facebook = $('#facebook').val();
    	
    	var twitter = $('#twitter').val();
    	var otrared = $('#otrared').val();
    	
    	var cp = $('#cp').val();
    	var pais = $('#pais').val();
    	var estado = $('#estado').val();
    	var delmun = $('#delmun').val();
    	var colonia = $('#colonia').val();
    	var calle =  $('#calle').val();
    	var numero = $('#numero').val();
    	
    	var sec = $('input[name=sec]:checked').val();  
    	
    	
    	var action = "INSERT";
    	
    
    
    	$('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
       });
    	
    	
    	
    	$.ajax({ 
    	    type: "POST",  
    	    url: "ColaboradoresTalonarios",  
    	    data: { idsector: idsector,clave: clave, idnicho: idnicho, descripcion : descripcion,rbancaria: rbancaria, nombre: nombre, apaterno: apaterno, amaterno : amaterno, edad:edad, rfc:rfc,telefonocasa:telefonocasa,telefonooficina:telefonooficina,telefonomovil:telefonomovil,correopersonal:correopersonal,correotrabajo:correotrabajo,correootro:correootro,facebook:facebook,twitter:twitter,otrared:otrared,cp:cp,pais:pais,estado:estado,delmun:delmun,colonia:colonia,calle:calle,numero:numero,sec:sec,action:action},  
    	    success: function(msg){
    	    	
    	    	   	    
    	    	
    	    //	alert(msg.trim());
    	     	 
    	    	 $('div.block').unblock();	    
        
    	   
    	    	
    	    if(msg.trim()=="agregar"){     	        			    
    	  	       
    		        	
    	    	 $('#myModal').modal('hide');   
    	    	 
    	    		    $("#alerta").fadeIn();
    					$("#alerta").removeClass();
    	                $("#alerta").addClass("alert alert-success");
    					//$("#headAlerta").html(" ¡Correcto!");
    					$("#bodyAlerta").html("Se ha guardado con exito el colaborador"); 	
    		        	
    		        	
    		        	
    		}
    	    else if(msg.trim()=="editar"){     	        			    
 	  	       
    	    	 $('#myModal').modal('hide'); 
	        	
    		    $("#alerta").fadeIn();
				$("#alerta").removeClass();
                $("#alerta").addClass("alert alert-success");
				//$("#headAlerta").html(" ¡Correcto!");
				$("#bodyAlerta").html("Se ha editado con exito el colaborador"); 	
	        	
	        	
	        	
		  }  
          else if(msg.trim()=="existe"){     	
        	  
        	  alert("La clave del colaborador YA EXISTE!!");
 	  	       
        	 // $.gritter.add({title:"",text:"La clave del colaborador YA EXISTE!!"});
	        	
    		   /* $("#alerta").fadeIn();
				$("#alerta").removeClass();
                $("#alerta").addClass("alert alert-success");
				//$("#headAlerta").html(" ¡Correcto!");
				$("#bodyAlerta").html("Se ha editado con exito el colaborador"); */	
	        	
	        	
	        	
		  }  
    	    
          else if(msg.trim()=="borrar"){     	        			    
	  	       
 	    	/*  $('#myModal').modal('hide'); 
	        	
 		      $("#alerta").fadeIn();
				$("#alerta").removeClass();
              $("#alerta").addClass("alert alert-success");
				//$("#headAlerta").html(" ¡Correcto!");
				$("#bodyAlerta").html("Se ha borrado con exito!");*/ 	
	        	
	        	
	        	
		  }  
    	    
    	    else{
    	    	       $('#myModal').modal('hide'); 
    					
    					$('html, body').animate({scrollTop:0}, 10);
 	 					$("#alerta").fadeIn();
 	 					$("#alerta").removeClass();
 	 	                $("#alerta").addClass("alert alert-error");
 	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
 	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se guardo el colaborador, por favor intentalo nuevamente."); 
    					
    					
    					
    				}  	    	
    	    	
    		 
	     	
	     		
	    	
	    
	    	Listar.init();
    	    	
    	    	
    	    	
    	       } 
    	     });
	
	
     }


function BuscarEditar(idcolaborador){        	
	
    $('#formulario').parsley().reset();
    $("#formulario")[0].reset();
    $('#clave').val("");  
    $("#idsector option").prop("selected", false); 
    $("#idnicho option").html("");     	    	 	    	 
	$('#descripcion').val("");
   
	
    // buscarSectores.init();
     BuscarEdit(idcolaborador);
        $('#myModal').modal('show');       
    
    	
    }  
    


function BuscarEdit(idcolaborador){ 
	
	
	
	//$("#guardar").text('Editar');
	//$("#guardar").attr("onClick", "javascript:Editar('"+idcolaborador+"');return false;");
	 $("#aceptarmodal").attr("onClick", "javascript:Editar("+idcolaborador+");return false;");  
	
	
	
    	 $('div.block').block({ 
 		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
 	        overlayCSS: {backgroundColor: '#FFF'},
 	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
       }); 
 	
     
 	$.ajax({ 
 	    type: "GET",  
 	    url: "ColaboradoresTalonarios",  
 	    data: "view=BuscarEditar&idcolaborador="+idcolaborador,  
 	    success: function(msg){  
 	    	
 	    	//alert(msg);
 	    	//$('div.block').unblock();    	
 	    	
 	    	
 	    	var response = msg.split('#%#');
 	    	
 	    	
 	    	//COLABORADOR
 	    	$('#idcolaborador').val(response[0]);
 	    	$('#clave').val(response[1]);
 	    	$('#nombre').val(response[2]);
 	    	$('#apaterno').val(response[3]);    	
 	    	$('#amaterno').val(response[4]); 	    	
 	    	$('#rfc').val(response[5]); 	    	
 	    	$('#edad').val(response[6]);
 	   	    $('#descripcion').val(response[8]);
 	    	
 	    	
 	    	
 	    	if(response[7] == "I")
 	    	{
 	    		$("#interno").attr("checked",true);
 	    		$("#externo").attr("checked",false);
 	    	}else{
 	    		$("#externo").attr("checked",true);
 	    		$("#interno").attr("checked",false);
 	    		
 	    	}
 	    	
 	       	 	    	
 	    	//TELEFONO
 	    	
 	    	$('#telefonocasa').val(response[12]);  	    	
 	    	$('#telefonooficina').val(response[13]);    	
 	    	$('#telefonomovil').val(response[14]);
 	    	
 	    	
 	    	
 	    	//DIRECCION
 	    	$('#pais').val(response[15]);
 	    	$('#estado').val(response[16]);
 	    	$('#delmun').val(response[17]);
 	    	$('#colonia').html(response[18]);
 	    	
 	    	$('#calle').val(response[19]);
 	    	$('#numero').val(response[20]);
 	    	$('#cp').val(response[21]);
 	       //22 colonia
 	    	
 	    	//CORREO
 	    	$('#correopersonal').val(response[23]);
 	    	$('#correotrabajo').val(response[24]);
 	    	$('#correootro').val(response[25]);
 	    	//REDES SOCIALES
 	    	$('#facebook').val(response[26]);
 	    	$('#twitter').val(response[27]);
 	    	$('#otrared').val(response[28]);    	
 	    	
 	    	//arriba
 	    	$('#colonia option:contains("'+response[22]+'")').prop('selected', true);	    	
 	    	
 	    	
 	    	handlebuscarSectores(response[9],response[10]);  	
	     	$('#rbancaria').val(response[11]);   	
	     //	handlebuscarNichosSector(response[10],response[9]);	    	
	    	$('div.block').unblock();    
 	    	
 	    	
 	        } 
 	   
 	   });
    	
    	
    	
    	
    }
    
    
    
    
    
    
function Editar(idcolaborador){
	
	
	
	$('#alerta').hide();
	
	
if( $('#formulario').parsley().validate() ){    		 
        
  		
      
	
	var idsector = $('#idsector').val();  
  	var clave = $('#clave').val();    	
  	var idnicho = $('#idnicho').val();
  	//var colaborador = $('#colaborador').val();
  	var descripcion = $('#descripcion').val();
  	
  	
  	var rbancaria = $('#rbancaria').val();
  	
  	
  	var nombre = $('#nombre').val();    	
  	var apaterno = $('#apaterno').val();
  	var amaterno = $('#amaterno').val();
  	
  	var edad = $('#edad').val();
  	var rfc = $('#rfc').val();
  	
  
  	var telefonocasa = $('#telefonocasa').val();
  	var telefonooficina = $('#telefonooficina').val();
  	var telefonomovil = $('#telefonomovil').val();
  	
  	var correopersonal = $('#correopersonal').val();
  	var correotrabajo = $('#correotrabajo').val();
  	var correootro = $('#correootro').val();
	
  	var facebook = $('#facebook').val();
  	var twitter = $('#twitter').val();
  	var otrared = $('#otrared').val();
  	
  	var cp = $('#cp').val();
  	var pais = $('#pais').val();
  	var estado = $('#estado').val();
  	var delmun = $('#delmun').val();
  	var colonia = $('#colonia').val();
  	var calle =  $('#calle').val();
  	var numero = $('#numero').val();
  	
  	var sec = $('input[name=sec]:checked').val();  
  	
  	
  
  	var action = "UPDATE";
  	
  
  
  	$('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
     });
  	
  	
  	
  	$.ajax({ 
  	    type: "POST",  
  	    url: "ColaboradoresTalonarios",  
  	    data: { idcolaborador:idcolaborador,idsector: idsector,clave: clave, idnicho: idnicho, descripcion : descripcion,rbancaria: rbancaria, nombre: nombre, apaterno: apaterno, amaterno : amaterno, edad:edad, rfc:rfc,telefonocasa:telefonocasa,telefonooficina:telefonooficina,telefonomovil:telefonomovil,correopersonal:correopersonal,correotrabajo:correotrabajo,correootro:correootro,facebook:facebook,twitter:twitter,otrared:otrared,cp:cp,pais:pais,estado:estado,delmun:delmun,colonia:colonia,calle:calle,numero:numero,sec:sec,action:action},  
  	    success: function(msg){
  	    	
  	    	 $("#aceptarmodal").attr("onClick", "javascript:Guardar();return false;");
  	    	
  	    	/* 
  	    	$('#formulario').parsley().reset();
  	    	$("#formulario")[0].reset();
  	    	$('#clave').val("");  
  	    	$("#idsector option").prop("selected", false); 
  	    	$("#idnicho option").prop("selected", false);     	    	 	    	 
  	     	$('#descripcion').val("");
  	    	  $("#idsector option").prop("selected", false); 
  	    	 $("#idnicho option").prop("selected", false);  
  	    	 $('#clave').val("");    	    	 
  	     	$('#descripcion').val("");
  	     	 
  	     	 
  	     	 
  	         $('#rbancaria').val(); 	    	
  	         $('#nombre').val('');    	
  	    	 $('#apaterno').val('');
  	    	 $('#amaterno').val('');
  	    	
  	    	 $('#edad').val('');
  	    	 $('#rfc').val('');
  	    	
  	    
  	         $('#telefonocasa').val('');
  	    	 $('#telefonooficina').val('');
  	    	 $('#telefonomovil').val('');
  	    	
  	    	$('#correopersonal').val('');
  	    	$('#correotrabajo').val('');
  	    	$('#correootro').val('');
  		
  	    	$('#facebook').val('');
  	    	$('#twitter').val('');
  	    	$('#otrared').val('');
  	    	
  	    	$('#cp').val('');
  	    	$('#pais').val('');
  	    	$('#estado').val('');
  	    	$('#delmun').val('');
  	    	$('#colonia').val('');
  	    	$('#calle').val('');
  	    	$('#numero').val('');*/
  	    	
  	    
  	     	 
  	     	 
  	     	 $('#myModal').modal('hide'); 
  	     	 $('div.block').unblock();	    	
  	    	
  	    
  	    	Listar.init();
      
  	   
  	    	
  	    	 if(msg.trim()==""){     	        			    
  	  	       
  		        	
  		        	
  	    		    $("#alerta").fadeIn();
  					$("#alerta").removeClass();
  	                $("#alerta").addClass("alert alert-success");
  					//$("#headAlerta").html(" ¡Correcto!");
  					$("#bodyAlerta").html("Se ha guardado con exito el colaborador"); 	
  		        	
  		        	
  		        	
  				}else{
  					
  					
  					$('html, body').animate({scrollTop:0}, 10);
	 					$("#alerta").fadeIn();
	 					$("#alerta").removeClass();
	 	                $("#alerta").addClass("alert alert-error");
	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se guardo el colaborador, por favor intentalo nuevamente."); 
  					
  					
  					
  				}
  				
  	    	
  	    	
  	    	
  	    	
  	    	
  	    	
  	            } 
  	           });
	
}
return false;
	
	
}


function Borrar(idcolaborador){ 	
	
	
	var confirm=window.confirm("Esta seguro de Borrar el Colaborador?");
	if (confirm==true) {	
	    	
	
	 $('div.block').block({ 
	    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
        overlayCSS: {backgroundColor: '#FFF'},
         message: '<img src="assets/img/load-search.gif" /><br><h2> Borrando..</h2>'
  }); 

  	

$.ajax({ 
    type: "GET",  
    url: "ColaboradoresTalonarios",  
    data: "view=Borrar&idcolaborador="+idcolaborador,  
    success: function(msg){  	    	
    	
    	$('div.block').unblock();   	 	    	
    	
		
		//alert("entro");
    //	alert(msg.trim());
		
		if(msg.trim()=="existe"){ 
		
	          //alert('aqui');
        	
			  $.gritter.add({title:"",text:"El Colaborador esta asignado al sorteo: NO puede ser eliminado!"});
			//  $.gritter.add({title:"TALONARIOS ASIGNADOS",text:"Se ha asignado con exito el talonario!"});
		   
        	   
    	   }
		   else if(msg.trim()=="noexiste"){
		   
		     $('html, body').animate({scrollTop:0}, 10);
    	   	     $("#alerta").fadeIn();
		      	 $("#alerta").removeClass();
                $("#alerta").addClass("alert alert-success");
		      // $("#headAlerta").html("¡Correcto!");
			     $("#bodyAlerta").html("Se ha eliminado con exito el colaborador"); 	
    		
		   
                
		   }else{
    		   
    		  $('html, body').animate({scrollTop:0}, 10);
				$("#alerta").fadeIn();
				$("#alerta").removeClass();
                $("#alerta").addClass("alert alert-error");
				//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
				$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se elimino el colaborador, por favor intentalo eliminar nuevamente."); 
    		   
    		   
    	   }
		
		
		
		
       /* if(msg.trim()==""){ 
        	
        	     $('html, body').animate({scrollTop:0}, 10);
    	   	     $("#alerta").fadeIn();
		      	 $("#alerta").removeClass();
                $("#alerta").addClass("alert alert-success");
		      // $("#headAlerta").html("¡Correcto!");
			     $("#bodyAlerta").html("Se ha eliminado con exito el colaborador"); 	
    		
    	   }else{
    		   
    		  $('html, body').animate({scrollTop:0}, 10);
				$("#alerta").fadeIn();
				$("#alerta").removeClass();
                $("#alerta").addClass("alert alert-error");
				//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
				$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se elimino el colaborador, por favor intentalo eliminar nuevamente."); 
    		   
    		   
    	   }*/
        pag = 1;  
       Listar.init();
    	   
       	
    	
    	
        } 
   
   });


  }	
	
	
}








$('#idsector').change(function (e) {
	
	 e.preventDefault();
	
	// if( $('#formulario').parsley().validate() ){    		 
        
	// buscarNichosSector.init();
	 handlebuscarNichosSector();
  //  }
  //  return false;
	  
	  
	});



  function handlebuscarSectores(idsector,idnicho){
	
	
	  var usuario = $("#usuario").val();
  	  var idsectorU = $("#idsectorU").val();
	
	
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
     }); 
	
   
	$.ajax({ 
	    type: "GET",  
	    url: "ColaboradoresTalonarios",  
	    data: "view=BuscarSector&idsectorU="+idsectorU+"&usuario="+usuario,  
	    success: function(msg){
	    	
	    	    	
	    	$("#idsector").html(msg);	    	
	    	$("#idsector option[value="+ idsector +"]").attr("selected",true);
	    	handlebuscarNichosSector(idnicho);	
	    	$('div.block').unblock();  
	    	
	    	
	               } 
	   
	           });
   	
   	
   } 




 function handlebuscarNichosSector(idnicho,idsector){
	
	 if(idsector==null||idsector==""){idsector = $('#idsector').val();}else{}
	 
	if(idsector != "S"){
		
			
			  $('div.block').block({ 
				    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
			        overlayCSS: {backgroundColor: '#FFF'},
			         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
		     }); 
			
		   
			$.ajax({ 
			    type: "GET",  
			    url: "ColaboradoresTalonarios",  
			    data: "view=buscarNichosSector&idsector="+idsector,  
			    success: function(msg){    	
			    	
			    		    	
			    	$('div.block').unblock(); 		    	
			    	$("#idnicho").html(msg);			    	
			    	$("#idnicho option[value="+ idnicho +"]").attr("selected",true);
			    	
			    	
			               } 
			   
			           });
		   	
		
		
	}else{
		
		//alert("Seleccionar un Sector");
		$("#idnicho").html("");
		
	}
	 
	
   	
   } 






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
	
	pag = page;
	handleListar()
    }



    var handleListar = function(){
    	
    	
     var show =	$("#data-elements-length").val();
     var search =	$("#searchtable").val();
     
     var idsectorb =	$("#idsectorb").val();    
     var idnichob =	$("#idnichob").val();
     
     if(search!=""){ }
	
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
	    url: "ColaboradoresTalonarios",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search+"&idsectorb="+idsectorb+"&idnichob="+idnichob,  
	    success: function(msg){    	
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               } 
	   
	           });
    	
    	
    } 
    
    
    
    //busca sectores al comienzo de cargar la pagina    
    
    
    function handlebuscarSectoresBuscar(){
    	
    	
    	var usuario = $("#usuario").val();
    	var idsectorU = $("#idsectorU").val();
    	
    	
    	    	
    	  $('div.block').block({ 
    		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
    	        overlayCSS: {backgroundColor: '#FFF'},
    	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
         }); 
    	
       
    	$.ajax({ 
    	    type: "GET",  
    	    url: "ColaboradoresTalonarios",  
    	    data: "view=BuscarSectorInicio"+"&idsectorU="+idsectorU+"&usuario="+usuario,  
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
	//handleListar(); 
   	});
    
    
    
    $('#idnichob').change(function (e) {   	
      	 e.preventDefault();       	
      	handleListar();  
      	  
      	});
    
    
    
    
    function handlebuscarNichosSectorBuscar(){
    	
      	
    	idsector = $('#idsectorb').val(); 	
    	
    	
    	if(idsector != ""){	
    		
    		
    	      
    	   	$.ajax({ 
    	   	    type: "GET",  
    	   	    url: "ColaboradoresTalonarios",  
    	   	    data: "view=buscarNichosSectorInicio&idsector="+idsector,  
    	   	    success: function(msg){  
    	   	    	
    	   	    	
    	   	    	$("#idnichob").html(msg);
    	   	    	
    	   	    	handleListar();  	   	    	
    	   	    	
    	   	    
    	   	    	
    	   	    	
    	   	               } 
    	   	   
    	   	           });  		
    		
    		
    	}else{
    		$("#idnichob").html("<option value=\"\">Todos</option>");
    		handleListar();
    		
    	}
    	
   	
      	
      	
      } 


    
    
    
    
    $( "#btnbuscardireccion" ).click(function() {
    	
    	var cp = $('#cp').val();
    	
    	if(cp!=""){
    	 
    		/*
    		$.blockUI({ css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
    	            overlayCSS: {backgroundColor: '#FFF'},
                     message: '<img src="skins/default/images/spinner-md.gif" /><br><h3> Espere un momento..</h3>'
                     });
                     */
    		
    		
    		
    	$("#preloader").html('<img src="assets/img/load-search.gif" />');
    		
    	
    	$.ajax({ 
        type: "GET",  
        url: "ColaboradoresTalonarios",  
        data: "view=getDireccion&cp="+cp,  
        success: function(msg){  
        	
        	var response = msg.split('#%#');
        	
        	$('#pais').val(response[0]);
        	$('#estado').val(response[1]);
        	$('#delmun').val(response[2]);
        	$('#colonia').html(response[3]);
        	
        	$("#preloader").html('');
        	
        	//$('#colonia').html(response[3]);
    	
    	/*$("#alerta").fadeIn();
    				$("#alerta").removeClass();
                    $("#alerta").addClass("alert alert-success");
    				$("#headAlerta").html("¡Correcto!");
    				$("#bodyAlerta").html("Se ha guardado el rol del sistema");*/
    /*	 
     $.unblockUI();
     */
                   } 
       
               });
    		   }else{
    		   	
    			
    			
    		   }
    	
    });


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
    
    
    
    
    function Comision(idsector)
    {
    	    $("#comision").val('');
    	    $('#formulario2').parsley().reset();		 
    		$("#aceptarmodalComision").attr("onClick", "javascript:actualizarComision('"+idsector+"');return false;");			 
    		$('#myModalComision').modal('show');		
    		    		
    	 
     }

    function actualizarComision(idcolaborador){  	
        
    	
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
     	    url: "ColaboradoresTalonarios",  
     	    data: "view=actualizarComision&idcolaborador="+idcolaborador+"&comision="+comision,  
     	    success: function(msg){ 	    	
    	    	
    	    	 $('#myModalComision').modal('hide');
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



    function EliminarColaboradores(){
    	
    	var arr = $("input:checked.colaborador").getCheckboxValues();
    

    	 if(arr.length==0){
    		alert("Debe elegir un colaborador");
    	 }else{
    		 
    		 $('#idscolaboradores').val(arr); 
    		 EliminarColaboradores2();
    		 
    		
    	 }
     }
        




    function EliminarColaboradores2()
    {
    	var idcolaboradores = $('#idscolaboradores').val();
    	var action = "DELETE";
    	
    	var procede = confirm("Se aliminaran los colaboradores seleccionados, esta seguro?");

    	if(procede){
    		
    		$('div.block').block({ 
    			css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
    			overlayCSS: {backgroundColor: '#FFF'},
    			message: '<img src="assets/img/load-search.gif" /><br><h2> Eliminado..</h2>'
    		}); 
    	
    		$.ajax({
    		type: "POST",
    		cache: false,
    		url: "ColaboradoresTalonarios",
    		data: { idcolaboradores: idcolaboradores,action:action},
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
        
        var idsectorb =	$("#idsectorb").val();
        var idnichob =	$("#idnichob").val();
       
        
        if(search!=""){ pag=1;}
    	
    	
    	location.href="ColaboradoresTalonarios?view=ExportExcel&pg="+pag+"&show="+show+"&search="+search+"&idsectorb="+idsectorb+"&idnichob="+idnichob;
    	
    	
    	
    }
	