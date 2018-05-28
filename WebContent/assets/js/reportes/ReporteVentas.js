


function ExportExcel() {	
	
	
	// var url = "http://13.84.158.61/app/planeacion/controllers/planesoperativo/exportexcel.class.php?&IDPlan="+idplan+"&IDPlanE="+idestretegico;
	// var url = "http://sorteoanahuac.mx:8080/sorteo/controllers/com.sorteo.reportes.controller/ReporteVentas?view=ExportExcel";
	
	//var url = "http://localhost:8080/sorteo/controllers/com.sorteo.reportes.controller/ReporteVentas?view=ExportExcel";
	
	    var url = "ReporteVentas?view=ExportExcel";
      
		var myWindow = window.open(url,'_blank');	
		myWindow.opener.document.focus(); 	
		//myWindow.document.write('<p>html to write...</p>');
	
    	//myWindow.document.write('<p>html to write...</p>');
   
    	//myWindow.opener.document.write("<p>This is the source window!</p>");
    	
    	
    	/*$(myWindow.popup).onload = function()
        {
                alert("Popup has loaded a page");
        };*/
	
    	
    	//myWindow.document.body.innerHTML = '<div align="center" style="margin-top:200px; font-family:Arial, Helvetica, sans-serif;"><img src="http://redanahuac.mx/app/serviciosocial/skins/default/images/spinner-md.gif"/><br /><br /><span>Generando Excel espere por favor...</span></div>';
    	

	//location.href = "ReporteVentas?view=ExportExcel";		

	
/*	$.ajax({
	type: "GET",
	cache: false,
	url: "ReporteVentas",
	data: "view=ExportExcel",
	success: function(msg){
		location.href = "ReporteVentas?view=ExportExcel";
	}
});	*/
	

}



function ExportExcel_RetornoTal() {	
	
	
	// var url = "http://13.84.158.61/app/planeacion/controllers/planesoperativo/exportexcel.class.php?&IDPlan="+idplan+"&IDPlanE="+idestretegico;
	// var url = "http://sorteoanahuac.mx:8080/sorteo/controllers/com.sorteo.reportes.controller/ReporteVentas?view=ExportExcel";
	
	//var url = "http://localhost:8080/sorteo/controllers/com.sorteo.reportes.controller/ReporteVentas?view=ExportExcel";
	
	    var url = "ReporteVentas?view=ExportExcel_RetornoTal";
      
		var myWindow = window.open(url,'_blank');	
		myWindow.opener.document.focus(); 	
		//myWindow.document.write('<p>html to write...</p>');	
  

}



//SELECCIONAR TODO

function seleccionarTodo(id) {

	if (document.getElementById('checkboxall' + id).checked == 1) {

		for (i = 0; i < document.forms.namedItem("formulario" + id).elements.length; i++) {
			if (document.forms.namedItem("formulario" + id).elements[i].type == "checkbox") {
				document.forms.namedItem("formulario" + id).elements[i].checked = 1
			}
		}
	}
	else {

		for (i = 0; i < document.forms.namedItem("formulario" + id).elements.length; i++)
			if (document.forms.namedItem("formulario" + id).elements[i].type == "checkbox")
				document.forms.namedItem("formulario" + id).elements[i].checked = 0
	}
}
