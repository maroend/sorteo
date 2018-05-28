/*   
Template Name: Color Admin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.1
Version: 1.5.0
Author: Sean Ngu
Website: http://www.seantheme.com/color-admin-v1.5/admin/

*/

var global = 'false';
var elem = document.querySelector('.js-switch');
var init = new Switchery(elem);
var handleBootstrapWizards=function(){"use strict";$("#wizard").bwizard()};

var FormWizard=function(){"use strict";return{init:function(){handleBootstrapWizards()}}}()


var guardarPremio=function(){"use strict";return{init:function(){
	
	handlecontrollersave();
	
}}}()



$(function(){
	
	 $('previous').attr('aria-disabled',true);  
	
});//End function jquery




$("#wizard").bwizard({validating:function(e,t){
	
	if(t.index==0){
		
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-1"))
			return false;
	}
	else if(t.index==1){
		
		if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-2"))
			return false

		guardarPremio.init();
	}
	
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
		url: 'PremiosColaboradores',
		data: { accion:'insertar', nombre:nombre, clasificacion:clasificacion, numero_premio:numero_premio, estrellas:estrellas, valor:valor, descripcion:descripcion, imagen:imagen},
		success: function(msg){
			
			uploadImage();
		}
	});
}


function uploadImage(){
	
	var fd = new FormData();    
	var file = $("input[type='file']")[0].files[0];
	fd.append("userfile", file);
	
	var fileName = file.name;
	var fileSize = file.size;
	
	$.ajax({
		type: 'POST',
		url: 'PremiosColaboradores',
		data: fd,
		processData: false,
		contentType: false,
		success: function(data){
			//alert('Imagen enviada');
		}
	});
	
}

