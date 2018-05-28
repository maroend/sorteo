/*   
App Sorteo Anáhuac
Version: 1.0 
Website: http://www.redanahuac.mx/app/sorteo
*/


var pag = 1;


var ListarUsuarios=function(){"use strict";return{init:function(){ 
	
	handleListarUsuarios()
	
}}}()



function onkeyup_colfield_check(e){
    var enterKey = 13;
        if (e.which == enterKey){
        	handleListarUsuarios()
        }
}

    function changeShow(){
    	
    	handleListarUsuarios()
    }

    function getPag(page){
	
	pag = page;
	 handleListarUsuarios()
    }


    
//
    
    var ListarModal=function(){"use strict";return{init:function(){ 
        
    	handleListarModal();
    	
    }}}()

     
    var ListarUsuariosModal=function(){"use strict";return{init:function(){ 
        
    	handleListarUsuariosModal();
    	
    }}}()




    function onkeyup_colfield_checkModal(e){
        var enterKey = 13;
            if (e.which == enterKey){            
            	 ListarUsuariosModal.init();  
            }
    }

        function changeShowModal(){
        	
        	
        	 ListarUsuariosModal.init();  
        }

        function getPagModal(page){
    	
    	pag = page;
    
    	 ListarUsuariosModal.init();  
        }
        
        
        
        /*var handleListarModal = function(){
        	
        	
        	
        	
        	 var show =	$("#data-elements-lengthM3").val();
        	
             var search =	$("#searchtableM3").val();
            
             
      
       	
           
       	$.ajax({ 
       	    type: "GET",  
       	    url: "Usuarios.do",  
       	    data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,  
       	    success: function(msg){        	    	
       	    	
       	    	//$('div.block').unblock(); 
       	    	$("#listresultados2").html(msg);
       	    	       	    	
       	           	    	
       	    	
       	       } 
       	   
       	    });
           	
       }  */
        
        
        
        var handleListarUsuariosModal = function(){
        	           
       	 var show =	$("#data-elements-lengthM3").val();
         var search =	$("#searchtableM3").val();
           alert(show);
            
      	$('div.block').block({ 
      		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
      	        overlayCSS: {backgroundColor: '#FFF'},
      	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
            }); 
      	
          
      	$.ajax({ 
      	    type: "GET",  
            cache: false,
      	    url: "SorteosSectUsuarios",  
      	    data: "view=BuscarModal&pg="+pag+"&show="+show+"&search="+search,  
      	    success: function(msg){        	    	
      	    	
      	    	$('div.block').unblock(); 
      	    	$("#listresultados3").html(msg);
      	    	       	    	
      	           	    	
      	    	
      	       } 
      	   
      	    });
          	
      }  
        

    
        
    function AsignarUsuario(){        	
        	
        	$("#aceptarmodal3").attr("onClick", "javascript:AsignarUsuarioSorteo();return false;");         	
            $('#myModal3').modal('show');            
           // $("#listresultados3").html("");
            ListarUsuariosModal.init();  
           
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
        
    
    function AsignarUsuarioSorteo(){
    	
    	/*var valores = $('input:checkbox:checked').map(function() {
    	    return this.value;
    	}).get();*/
    	
    	
    	var valores = String($("input:checked").getCheckboxValues());
    	var action = "insertar";
    	var idsorteo = gup('idsorteo');
    	var idsector = gup('idsector');
    	
    	if(valores==""){
    		
    		alert("Debe seleccionar por lo menos un usuario.");
    		
    	}else{
    		
    		
    		
    		$.ajax({ 
           	    type: "POST",  
                cache: false,
           	    url: "SorteosSectUsuarios",  
           	    data: {action:action,idsorteo:idsorteo,idsector:idsector,valores:valores},
           	    success: function(msg){        	     
           	    	
           	     $.gritter.add({title:"USUARIOS AGREGADOS  AL SECTOR",text:"Los usuarios se han agregado con exito"});
           	     
           	     $('#myModal3').modal('hide');   
           	     
           	     setTimeout( handleListarUsuarios, 700);
           	
        	    } 
         });
    	
    		
    		
    	}
    	
    	
    }
        
        
        
  function ExisteRoleUsuario (idusuario){     	
        
	  
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
     }); 
        	
        	
        	$.ajax({ 
           	    type: "GET",  
                cache: false,
           	    url: "Usuarios.do",  
           	    data: "view=ExisteRoleUsuario&idusuario="+idusuario,  
           	    success: function(msg){        	     
           	    	
           	     $('#'+msg).prop("checked",true);  
           	     $('div.block').unblock();  
        	    
        	    
        	    } 
         });
    	
    	
  }
        
        
      
        
        function enviarRole(idusuario){       	  

        	var ban = $("input[name='idrole']:checked").val();
        	  
        	 if(!ban){
        		 
        		alert("Debe elegir un role");
        		
        	 }else{      
        		 
        		 $('#myModal').modal('hide');      		
        		 var idrole = $('input[name=idrole]:checked').val();     		 
        		 AgregarRoleUsuario(idusuario,idrole);
        		 
        		
        	 }
         }
        
                
        
        
        function AgregarRoleUsuario (idusuario,idrole){     	
        
        	
        	$('div.block').block({ 
     		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
     	        overlayCSS: {backgroundColor: '#FFF'},
     	         message: '<img src="assets/img/load-search.gif" /><br><h2> Guardando..</h2>'
           }); 
     	    
        	
        	$.ajax({ 
           	    type: "GET",  
                cache: false,
           	    url: "Usuarios.do",  
           	    data: "view=AgregarRoleUsuario&idusuario="+idusuario+"&idrole="+idrole,  
           	    success: function(msg){      
        	    	
           	        $("input:radio").prop('checked', false); 
           	        $('#myModal2').modal('hide');
           	        // ListarUsuarios.init();
        	    	 $('div.block').unblock();  
        	    	
            	    	
        	    	if(msg.trim()==""){    	 
         	    	 
        	    		 $('html, body').animate({scrollTop:0}, 10);
    	    	    	 $("#alerta").fadeIn();
    			      	 $("#alerta").removeClass();
                         $("#alerta").addClass("alert alert-success");
    			      // $("#headAlerta").html("¡Correcto!");
    				     $("#bodyAlerta").html("Se ha asignado el role al usuario con exito");
        	    		
        	    	
        		        	
        			}else{
        				
        					    $('html, body').animate({scrollTop:0}, 10);
        	 					$("#alerta").fadeIn();
        	 					$("#alerta").removeClass();
        	 	                $("#alerta").addClass("alert alert-error");
        	 					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
        	 					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se asigno el role, por favor intentalo nuevamente.");     					
        					
        					
        			  }   	    	
        	    	
        	    	
        	      } 
        	 });
    	
    	
         }
        
        
    
    //modal 2
    
    function Cerrar(){  	
    		
        $('#myModal').modal('hide'); 
        $('#idusuario').val('');
        $('#username').val('');
        $('#password').val(''); 
        
    	
    }  
    
    
    
    function CambiarPassword(idusuario){ 
  	   
    	$("#aceptarmodal").attr("onClick", "javascript:enviar();return false;");    
        $('#myModal').modal('show');
        BuscarUsuario(idusuario);      
        
    	
    }  
    
    
    
    
    function BuscarUsuario(idusuario) {
    	
    	$('#formulario').parsley().reset();
    
    	
    	if(idusuario!=""){
    	 
    		
    		/*$.blockUI({ css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
    	            overlayCSS: {backgroundColor: '#FFF'},
                     message: '<img src="skins/default/images/spinner-md.gif" /><br><h3> Espere un momento..</h3>'
                     });*/
                     
    		
    		
    	$.ajax({ 
        type: "GET", 
        cache: false, 
        url: "SorteosUsuarios",  
        data: "view=BuscarUsuario&idusuario="+idusuario,  
        success: function(msg){      	
        	 
        	var response = msg.split('#%#');
        	
        	//USUARIO
        	$('#username').val(response[0]);
        	$('#password').val(response[1]);
        	$('#password2').val(response[1]);
        	$('#idusuario').val(response[2]);
        	
        	
        
                   } 
       
               });
    		   }else{
    		   	
    			
    			
    		   }
    	
    }

    
    
  function enviar(){
    	
    	
	  
	    var idusuario = $('#idusuario').val();
    	var username = $('#username').val();
    	var password = $('#password').val();
   
    	
  if( $('#formulario').parsley().validate() ){    	
    	
    	
    	
    	$.blockUI({ css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
            overlayCSS: {backgroundColor: '#FFF'},
             message: '<img src="skins/default/images/spinner-md.gif" /><br><h3> Espere un momento..</h3>'
             });
    
    	
    	    	
    	$.ajax({ 
            type: "GET",  
            cache: false,
            url: "Usuarios.do",  
            data: "view=EditarPassword&idusuario="+idusuario+"&username="+username+"&password="+password,  
            success: function(msg){  
    	          
            	 $('#idusuario').val('');
                 $('#username').val('');
                 $('#password').val('');
                 
            	 $('#myModal').modal('hide');
    	
    	         $.unblockUI();
    	         
    	    	if(msg.trim()==""){ 
 	    		    
	        	     $('html, body').animate({scrollTop:0}, 10);
	    	    	 $("#alerta").fadeIn();
			      	 $("#alerta").removeClass();
                     $("#alerta").addClass("alert alert-success");
			      // $("#headAlerta").html("¡Correcto!");
				     $("#bodyAlerta").html("Se ha editado con exito el password del usuario");
    	    	
	    	   }else{
	    		  
	    		   
	    		   $('html, body').animate({scrollTop:0}, 10);
					$("#alerta").fadeIn();
					$("#alerta").removeClass();
	                $("#alerta").addClass("alert alert-error");
					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se edito el password del usuario, por favor intentalo editar nuevamente."); 
	    		   
	    		   
	    	   }
	    	   
   				
    	            } 
    	           });
    	
    	
        }
         return false;
    	
	
	
     }


  
  
  
        

    var handleListarUsuarios = function(){
    	
    	
     var show =	$("#data-elements-length").val();
     var search =	$("#searchtable").val();
     var idsorteo = gup('idsorteo');
     var idsector = gup('idsector');
    
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
      cache: false,
	    url: "SorteosSectUsuarios",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search+"&idsorteo="+idsorteo+"&idsector="+idsector,  
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               } 
	   
	           });
    	
    	
    } 


    
    
    var handlecontrollersave = function(){
    	
    	var username = $('#username').val();
    	var password = $('#password').val();
    
    	
    	$.ajax({ 
    	    type: "POST", 
          cache: false, 
    	    url: "SorteosUsuarios",  
    	    data: { nombre: nombre, apaterno: apaterno, amaterno : amaterno, edad:edad, rfc:rfc,telefonocasa:telefonocasa,telefonooficina:telefonooficina,telefonomovil:telefonomovil,correopersonal:correopersonal,correotrabajo:correotrabajo,correootro:correootro,facebook:facebook,twitter:twitter,otrared:otrared,cp:cp,pais:pais,estado:estado,delmun:delmun,colonia:colonia,calle:calle,numero:numero,username:username,password:password},  
    	    success: function(msg){  	    	
    	 
    	    	
    	    	
    	            } 
    	           });
	
	
     }


    
    





function Borrar(idusuario){ 
	
	 $('div.block').block({ 
	    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
        overlayCSS: {backgroundColor: '#FFF'},
         message: '<img src="assets/img/load-search.gif" /><br><h2> Borrando..</h2>'
  }); 

	 
	 var action = "eliminar";

$.ajax({ 
    type: "POST", 
    cache: false, 
    url: "SorteosSectUsuarios",  
    data: {action:action,idusuario:idusuario},  
    success: function(msg){  	    	
    	
    	$('div.block').unblock();   	 	    	
    	
    	
        if(msg.trim()==""){ 
        	
        	     $('html, body').animate({scrollTop:0}, 10);
    	   	     $("#alerta").fadeIn();
		      	 $("#alerta").removeClass();
                $("#alerta").addClass("alert alert-success");
		      // $("#headAlerta").html("¡Correcto!");
			     $("#bodyAlerta").html("Se ha eliminado con exito el usuario"); 	
    		
    	   }else{
    		   
    		  $('html, body').animate({scrollTop:0}, 10);
				$("#alerta").fadeIn();
				$("#alerta").removeClass();
                $("#alerta").addClass("alert alert-error");
				//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
				$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se elimino el usuario, por favor intentalo eliminar nuevamente."); 
    		   
    		   
    	   }
        ListarUsuarios.init();
    	   
       	
    	
    	
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











