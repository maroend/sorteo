/*   
App Sorteo Anáhuac
Version: 1.0
Author: Jose Carlos Ruiz Garcia
Website: http://www.redanahuac.mx/app/sorteo
*/



function showNichos(id){
	
	$("#nichos"+id).toggle();	
	
	if ($("#nichos"+id).is(":hidden")){
		
		$("#sector"+id+" i").removeClass( "fa fa-lg fa-minus-square" ).addClass( "fa fa-lg fa-plus-square" );
		
	}else{
		
		$("#sector"+id+" i").removeClass( "fa fa-lg fa-plus-square" ).addClass( "fa fa-lg fa-minus-square" );
		
	}
	
}



function showColaboradores(id){
	
	$("#colaboradores"+id).toggle();	
	
	if ($("#colaboradores"+id).is(":hidden")){
		
		$("#nicho"+id+" i").removeClass( "fa fa-lg fa-minus-square" ).addClass( "fa fa-lg fa-plus-square" );
		
	}else{
		
		$("#nicho"+id+" i").removeClass( "fa fa-lg fa-plus-square" ).addClass( "fa fa-lg fa-minus-square" );
		
	}
	
}


