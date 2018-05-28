var pag = 1;


var Listar=function(){"use strict";return{init:function(){ 
	
	
	handleListar();
	
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
	
	pag = page;
	handleListar()
    }



    var handleListar = function(){
    	
    	
     var show =	$("#data-elements-length").val();
     var search =	$("#searchtable").val();
     
		
	  $('div.block').block({ 
		    css: { backgroundColor: 'transparent', color: '#336B86', border:"null" },
	        overlayCSS: {backgroundColor: '#FFF'},
	         message: '<img src="assets/img/load-search.gif" /><br><h2> Buscando..</h2>'
      }); 
	
    
	$.ajax({ 
	    type: "GET",  
	    cache: false,
	    url: "FormatosNichos",  
	    data: "view=Buscar&pg="+pag+"&show="+show+"&search="+search,  
	    success: function(msg){  
	    	
	    	
	    	$('div.block').unblock(); 
	    	$("#listresultados").html(msg);
	    	
	    	
	               } 
	   
	           });
    	
    	
    }   
    
    
    
  
    
    
    