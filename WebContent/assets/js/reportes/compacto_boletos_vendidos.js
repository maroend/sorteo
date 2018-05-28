
var Listar=function(){"use strict";return{init:function(){ 
	
	
	handleListar();
	
}}}()


var handleListar = function()
{
	$('div.block').block({ 
		css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
		overlayCSS: {backgroundColor: '#FFF'},
		message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
	}); 

	$.ajax({ 
	    type: "GET",  
	    cache: false,
	    url: "StatusDeBoletos",  
	    data: "view=Buscar",
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
		}
	});
}    
