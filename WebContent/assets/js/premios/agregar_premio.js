
var numpremio=0;
var global = 'false';
var elem = document.querySelector('.js-switch');
var init = new Switchery(elem);
var handleBootstrapWizards=function(){"use strict";$("#wizard").bwizard()};

var FormWizard=function(){"use strict";return{init:function(){
	handleBootstrapWizards();

	$('#numero_premio_id').val(numpremio);
	$('#nombre_id').val('');
	$('#valor_id').val('');
	$('#descripcion_id').val('');
	$('#numero_premio_id').change(function() {	    	
		existeUsuario();
	});
}}}()


var guardarPremio=function(){"use strict";return{init:function(){
	
	handlecontrollersave();
	
}}}()



$(function(){
	
	 $('previous').attr('aria-disabled',true);  
	
});//End function jquery




$("#wizard").bwizard({validating:function(e,t){

//	<input type="text" name="numero_premio_id" id="numero_premio_id" value="#NUM_PREMIO#" placeholder="" class="form-control" data-parsley-group="wizard-step-1" required />
	
	if(t.index==0){
		/*
		var numero_premio = $('#numero_premio_id').val();
		$.ajax{
			type: 'GET',
			url: 'Premios',
			data: { numeroPremio:numero_premio },
			seccess: function(msg){
				var count = parseInt(msg);
				if (0 < count) {
					// Ya existe el numero de premio.
				}else {
					
				}
			}
		}
		
		if( $('#numero_premio_id').val()== ""){
				
				var clase = $('#numero_premio_id').attr('class');
				var id = $('#numero_premio_id').attr('data-parsley-id');
				$( "#numero_premio_id" ).removeClass( clase ).addClass( "form-control" );
				$( '#parsley-id-'+id).html('');
	        	$( '#parsley-id-'+id ).removeClass( "parsley-errors-list filled" ).addClass( "parsley-errors-list");
	        	
				
				
			}
		*/
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-1"))
			return false;
	}
	else if(t.index==1){
		
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-2"))
			return false

		guardarPremio.init();
	}
	/*
	else if(t.index==2){
		
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-3"))
			return false;
	
	}
	*/
}});



var handlecontrollersave = function(){
	
	var nombre = $('#nombre_id').val();
	var clasificacion = $('#clasificacion_id').val();
	var numero_premio = $('#numero_premio_id').val();
	var estrellas = $('#estrellas_id').val();
	var valor = $('#valor_id').val();
	var descripcion = $('#descripcion_id').val();
	
	var file = $("input[type='file']")[0].files[0];
	
	var fileName = file.name;
	var fileSize = file.size;
	
	var imagen = fileName;
	
	$.ajax({ 
		type: 'POST',  
		url: 'Premios', 
		data: { accion:'insertar', nombre:nombre, clasificacion:clasificacion, numero_premio:numero_premio, estrellas:estrellas, valor:valor, descripcion:descripcion, imagen:imagen},
		success: function(msg){
			
			uploadImage('Hola_Rogelio.jpg');
		}
	});
}

function existePremio(){
	alert('Buscar numero de premio ahora');

	var ban = false;
	var numeroPremio = $('#numero_premio_id').val();
	if(numeroPremio!=""){
		
		$.ajax({ 
			type: 'GET',  
			url: 'Premios.do',
			data: 'view=BuscarNumeroPremio&numeroPremio='+numeroPremio,  
			success: function(msg){  
				if(msg.trim()=="existe"){
			   
					var clase = $('#numero_premio_id').attr('class');
					$( "#numero_premio_id" ).removeClass( clase ).addClass( "form-control parsley-error" );    	        	 
					
					
					var id = $('#numero_premio_id').attr('data-parsley-id');    	       
					$( '#parsley-id-'+id).html('<li>El usuario ya existe!!, agregue otro porfavor.</li>');
					$( '#parsley-id-'+id ).removeClass( "parsley-errors-list" ).addClass( "parsley-errors-list filled");
					
					ban = true;
				}else{
					var clase = $('#numero_premio_id').attr('class'); 
					var id = $('#numero_premio_id').attr('data-parsley-id'); 
					$( "#numero_premio_id" ).removeClass( clase ).addClass( "form-control" );
					$( '#parsley-id-'+id).html('');
					$( '#parsley-id-'+id ).removeClass( "parsley-errors-list filled" ).addClass( "parsley-errors-list");
					
					ban = false;
				}
			}
		});
	}
	else { }
	return ban;
}


function uploadImage(newFileName){
	
	var fd = new FormData();    
	var file = $("input[type='file']")[0].files[0];
	fd.append("userfile", file);
	
	var fileName = file.name;
	var fileSize = file.size;
	
	$.ajax({
		type: 'POST',
		url: 'Premios',
		data: fd,
		processData: false,
		contentType: false,
		success: function(data){
		}
	});
	
}
