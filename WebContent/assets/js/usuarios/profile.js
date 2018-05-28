
var global = 'false';

var handleBootstrapWizards=function(){"use strict";$("#wizard").bwizard()};

var FormWizard=function(){"use strict";return{init:function(){handleBootstrapWizards()}}}()

var editarUsuario=function(){"use strict";return{init:function(){ 
	
	handlecontrollersave();
	
}}}()


/*var existeUsr2=function(){"use strict";return{init:function(){ 
	
	existeUsr();
	
}}}()*/




$(function(){
	
	
});//End function jquery






function Editar(){	
	
	if( $('#formulario').parsley().validate() ){    		 
        
		editarUsuario.init();
    }
    return false;
	
}  





function BuscarUsuario() {
	
	var idusuario =  gup('idusuario');
	//var username =  gup('usr');
	
	if(idusuario!=""){
	 
		
		/*$.blockUI({ css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	            overlayCSS: {backgroundColor: '#FFF'},
                 message: '<img src="skins/default/images/spinner-md.gif" /><br><h3> Espere un momento..</h3>'
                 });*/
                 
		
		
		
	//$("#preloader").html('<img src="assets/img/load-search.gif" />');
		
		
	$.ajax({ 
    type: "GET",  
    url: "EditUsuarios",  
    data: "view=BuscarUsuario&idusuario="+idusuario,  
    success: function(msg){  
    	//alert(msg);
    	 
    	var response = msg.split('#%#');
    	
    	//USUARIO
    	$('#username').val(response[0]);
    	$('#nombre').val(response[1]);
    	$('#apaterno').val(response[2]);    	
    	$('#amaterno').val(response[3]);
    	$('#Contraseña').val(response[4]);
    	
    	
    	$('#rfc').val(response[22]);
    	$('#edad').val(response[23]);
    	
    	
    	//TELEFONO
    	$('#telefonocasa').val(response[5]);    	
    	$('#telefonooficina').val(response[6]);    	
    	$('#telefonomovil').val(response[7]);
    	//DIRECCION
    	$('#pais').val(response[8]);
    	$('#estado').val(response[9]);
    	$('#delmun').val(response[10]);
    	
        //	alert(response[15]);
    	
    	$('#colonia').html(response[11]);
    	//$("#idsector option[value="+ response[15] +"]").attr("selected",true); 
    	
    

    
    
    	
    	$('#calle').val(response[12]);
    	$('#numero').val(response[13]);
    	$('#cp').val(response[14]);
    	//CORREO
    	$('#correopersonal').val(response[16]);
    	$('#correotrabajo').val(response[17]);
    	$('#correootro').val(response[18]);
    	//REDES SOCIALES
    	$('#facebook').val(response[19]);
    	$('#twitter').val(response[20]);
    	$('#otrared').val(response[21]);
    	
    	
    	$('#idsector option:contains("'+response[15]+'")');
    	//$('#edad').val(response[2]);
    	//$('#rfc').html(response[3]);
    	
    	
    	
    	
    	//$.unblockUI();
/*	 
 $.unblockUI();
 */
               } 
   
           });
		   }else{
		   	
			
			
		   }
	
}







   function existeUsuario2(){
	
	//
	
	var username = $('#username').val();
	
	if(username!=""){	    	 
		
		
		
		
	//$("#preloader").html('<img src="assets/img/load-search.gif" />');
		
	
	$.ajax({ 
    type: "GET",  
    url: "Usuarios.do",  
    data: "view=ExisteUsuario&username="+username,  
    success: function(msg){  
    	
    	
    	
    	
    
    	
      	
	        if(msg.trim()=="existe"){     	        			    
	       
	        	//$( "#username" ).addClass( "form-control parsley-error" );
	        	
	        	var clase = $('#username').attr('class'); 
	        	$( "#username" ).removeClass( clase ).addClass( "form-control parsley-error" );    	        	 
	        	
	        	
	        	var id = $('#username').attr('data-parsley-id');    	       
	        	$( '#parsley-id-'+id).html('<li>El usuario ya existe!!, agregue otro porfavor.</li>');
	        	$( '#parsley-id-'+id ).removeClass( "parsley-errors-list" ).addClass( "parsley-errors-list filled");
	        	
				
	        	
	        	
	        	return true; 
			}else{
				
				
				var clase = $('#username').attr('class'); 
				var id = $('#username').attr('data-parsley-id'); 
				$( "#username" ).removeClass( clase ).addClass( "form-control" );
				$( '#parsley-id-'+id).html('');
	        	$( '#parsley-id-'+id ).removeClass( "parsley-errors-list filled" ).addClass( "parsley-errors-list");
	        	
	        	
	        	return false; 
				
			}
			
    	
	      
	        
    	
    	
    	
    	

       } 
   
      });
	
	
	
	
	
  }else{}
	
	
	
	
	
	
}












    var handlecontrollersave = function(){
    	
    	
    	var idusuario =  gup('idusuario');
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
    	
    //	var username = $('#username').val();
    //	var password = $('#password').val();
    	
    	
       
    	$('div.block').block({  css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
            overlayCSS: {backgroundColor: '#FFF'},
             message: '<img src="assets/img/load.gif" /><br><h3> Guardando..</h3>'
             });


    	$.ajax({ 
    	    type: "POST",  
    	    url: "EditUsuarios",  
    	    data: { idusuario: idusuario, nombre: nombre, apaterno: apaterno, amaterno : amaterno, edad:edad, rfc:rfc,telefonocasa:telefonocasa,telefonooficina:telefonooficina,telefonomovil:telefonomovil,correopersonal:correopersonal,correotrabajo:correotrabajo,correootro:correootro,facebook:facebook,twitter:twitter,otrared:otrared,cp:cp,pais:pais,estado:estado,delmun:delmun,colonia:colonia,calle:calle,numero:numero/*,username:username,password:password*/},  
    	    success: function(msg){  
    	    	
    
    	
    	    	if(msg.trim()==""){ 
 	    		    
                       $('div.block').unblock();  
                       $('#verFormulario').hide();
                       $('#wizard').show();

	    	   }else{
	    		   $.unblockUI();
	    		   
	    		   $.unblockUI();
	    		   $('html, body').animate({scrollTop:0}, 10);
					$("#alerta").fadeIn();
					$("#alerta").removeClass();
	                $("#alerta").addClass("alert alert-error");
					//$("#headAlerta").html("<font size=\"3\">¡Ups! ha ocurrido un Error</font>");
					$("#bodyAlerta").html("<strong>No te alarmes</strong>!! al parecer no se edito el usuario correctamente, por favor intentalo editar nuevamente."); 
	    		   
	    		   
	    	   }
	    	   
   				
    				
    	    	
    	    	
    	    	
    	    	
    	    	
    	    	
    	            } 
    	           });
	
	
     }


    
    
    
    
    
   
    function existeUsuario(){
    	
    	var ban = false;
    	
    	var username = $('#username').val();
    	
    	if(username!=""){	    	 
    		
    		
    		
    		
    	//$("#preloader").html('<img src="assets/img/load-search.gif" />');
    		
    	
    	$.ajax({ 
        type: "GET",  
        url: "Usuarios.do",  
        data: "view=ExisteUsuario&username="+username,  
        success: function(msg){  
        	
        	
        	
        	//alert(msg);      	
        	//parsley-id-5371
          	
    	        if(msg.trim()=="existe"){     	        			    
    	       
    	        	//$( "#username" ).addClass( "form-control parsley-error" );
    	        	
    	        	var clase = $('#username').attr('class'); 
    	        	$( "#username" ).removeClass( clase ).addClass( "form-control parsley-error" );    	        	 
    	        	
    	        	
    	        	var id = $('#username').attr('data-parsley-id');    	       
    	        	$( '#parsley-id-'+id).html('<li>El usuario ya existe!!, agregue otro porfavor.</li>');
    	        	$( '#parsley-id-'+id ).removeClass( "parsley-errors-list" ).addClass( "parsley-errors-list filled");
    	        	
    				//return TRUE; 
    	        	
    	        	ban = true;
    			}else{
    				
    				
    				var clase = $('#username').attr('class'); 
    				var id = $('#username').attr('data-parsley-id'); 
    				$( "#username" ).removeClass( clase ).addClass( "form-control" );
    				$( '#parsley-id-'+id).html('');
    	        	$( '#parsley-id-'+id ).removeClass( "parsley-errors-list filled" ).addClass( "parsley-errors-list");
    	        	
    	        	//return FALSE;
    	        	ban = false;
    				
    			}
    			
        	
    	       // return ban;
    	        
        	
        	
        	
        	
   
           } 
       
          });
    	
    	
    	
    	
    	
      }else{}
    	
    	
    	
    	return ban;
    	
    	
    }
    
    
    
    

    
    

//    $('#username').live("keypress",function(){   	    	
   	 
  /*  $( "#username").change(function() {	    	
    	
    	existeUsuario();  	
    	
    	
    });*/
    
    
    
    

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
    url: "EditUsuarios",  
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