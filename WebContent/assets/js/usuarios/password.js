

var handleFormPasswordIndicator=function(){"use strict";
$("#password-indicator-visible").passwordStrength({targetDiv:"#passwordStrengthDiv2"})};



handleFormPasswordIndicator();



function ChangePassword(){

       if(validaInputs()){
            
            validarPasswordAnterior();
    	  // alert('entro');
            
       }

}


function validaInputs(){

   var validado = false;
   
      if($("#usuario").val()==""){ $("#usuario").css("border","red solid 2px"); validado = false;}else{ validado = true; $("#usuario").css("border","#CCD0D4 solid 2px");}
               
      if($("#password-actual").val()==""){ $("#password-actual").css("border","red solid 2px"); validado = false;}else{ validado = true; $("#password-actual").css("border","#CCD0D4 solid 2px");}

      if($("#password-indicator-visible").val()==""){ $("#password-indicator-visible").css("border","red solid 2px"); validado = false;}else{ validado = true; $("#password-indicator-visible").css("border","#CCD0D4 solid 2px");}

      if($("#password-indicator-default").val()==""){ $("#password-indicator-default").css("border","red solid 2px"); validado = false;}else{ validado = true; $("#password-indicator-default").css("border","#CCD0D4 solid 2px");}


   return validado;
}



function validarPasswordAnterior(){
      
      var validado = false;

     var passwordactual  = $('#password-actual').val();
     var usuario = $("#usuario").val();


      $.ajax({ 
      	    type: "GET",  
            cache: false,
      	    url: "Password",  
      	    data: "view=ValidaPassword&passwordactual="+passwordactual+"&usuario="+usuario,  
      	    success: function(msg){        	    	
      	    	
      	    	
                     //alert(msg);

                if(msg.trim()=="true"){
                	
                
                                validado =  true;
                               
                                if($("#password-indicator-visible").val() == $("#password-indicator-default").val()){ 
                                   SaveNewPassword(usuario,passwordactual);
            	               }else{           	            	
            	            	   
            	                  	$("#password-indicator-default").css("border","red solid 2px");
            	               	    alert("El Password Nuevo no coincide con el campo Repetir Password");
            	                  
            	               }

                }else{
                       validado =  false;
                       alert("El usuario y/o Password actual son incorrectos");
                      
                          //  alert('entros')
                }

      	       } 
      	   
      	    });

         return validado;
}




function SaveNewPassword(usuario,passwordactual){


var password = $("#password-indicator-visible").val();





           /*   $.ajax({
           	    type: "POST",               
           	    cache: false,
       	        url: "Password",    
           	    data: { password: password,usuario: usuario,passwordactual: passwordactual},
           	    success: function(msg){ */


$.ajax({ 
	    type: "GET",  
    cache: false,
	    url: "Password",  
	    data: "view=EditaPassword&passwordactual="+passwordactual+"&usuario="+usuario+"&password="+password,  
	    success: function(msg){    


           	    	alert(msg);
           	     
           	      $("#form-password").hide();
           	      $("#wizard").show();
           	     
           	     //Logout.do
           	     setTimeout( "redireccionar()" , 2000);
           	
        	    } 
         });

}


function redireccionar() 
{
location.href="Logout.do";
} 