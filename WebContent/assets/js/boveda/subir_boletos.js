
var numpremio=0;
var global = 'false';
var elem = document.querySelector('.js-switch');
var init = new Switchery(elem);
var handleBootstrapWizards=function(){"use strict";$("#wizard").bwizard()};

var FormWizard=function(){"use strict";return{init:function(){
	handleBootstrapWizards();
	
	$('#div_img_loading_id').hide();
	$('#div_botons_id').hide();
}}}()


$(function(){
	
	 $('previous').attr('aria-disabled',true);  
	
});//End function jquery




$("#wizard").bwizard({validating:function(e,t){

	if (t.index==0) {
		
		var formato = $('#input_formato_id').val();
		var costo = $('#input_costo_id').val();
		if (formato == '' || costo == '')
			return false;
	}
	else if(t.index==1){
		if (t.nextIndex	== 2){
			if(false===$('form[name="form-wizard"]').parsley().validate("wizard-step-1"))
				return false;
			var file = $("input[type='file']")[0].files[0];
			if(file==null)
				return false;
			
			handlecontrollersave();
		}
	}
	
}});


	
var processFile = function(fileName){
	var formato = $('#input_formato_id').val();
	var costo = $('#input_costo_id').val();

	$.ajax({
		type: 'GET',
		url: 'SubirBoletos',
		data: 'view=LoadExcelFile&fileName='+fileName+'&formato='+formato+'&costo='+costo,
		complete: function(msg){
			$('#div_img_loading_id').hide();
			$('#div_botons_id').show();
			closeTimer();
		},
		success: function(msg)
		{
			/*
			var rows = msg.split('#~#');
			
			var HTML = '<tr>  <th>Talonario</th> <th>Boletos</th> <th>Agregados</th>  </tr>';
			for(var j=0; j<rows.length; j++)
			{
				var tal_bols = rows[j].split('#&#');
				if(tal_bols.length>=2)
				{
					var talonario = tal_bols[0];
					var boletos = tal_bols[1].split('#|#');
					
					HTML += '<tr>'
						+'<td style="text-align:center; margin:1pt;"><a href=""><span class="label label-primary">&nbsp;'+talonario+'&nbsp;</span></a></td>'
						+'<td style="text-align:left; margin:1pt;">';
					var count=0;
					for (var i=0; i<boletos.length; i++)
						if (boletos[i]!=''){
							HTML += '<a href=""><span class="label label-default">'+boletos[i]+'</span></a> ';
							count++;
						}
					HTML += '<td style="text-align:center; margin:1pt;">'+count+'</td>';
					HTML += '</tr>';
				}
				HTML += '<tr><td style="height:5pt;"></td></tr>';
			}
			//*/
			var arr=msg.split('|');
			var HTML = '<br/>'
				+'Boletos insertados: '    + arr[0] + '<br/>'
				+'Talonarios insertados: ' + arr[1] + '<br/>';
			$('#tbody_result_id').html(HTML);
			//*/
			console.log('closeTimer()');
			closeTimer();
		},
		error: function(msg)
		{
			console.log('closeTimer()');
			closeTimer();
		}
	});
}

var handlecontrollersave = function(){
	
	preparaBoveda();
}

function preparaBoveda(){

 	$.ajax({
 	    type: 'GET',
 	    url: 'SubirBoletos',
 	    data: 'view=PrepararBoveda',
		complete: function(msg){
		},
 	    success: function(msg)
		{
			uploadFile(processFile);
	
			resetTimer();
		}
	});
}

function uploadFile(runOnSuccess){
	
	var fd = new FormData();    
	var file = $("input[type='file']")[0].files[0];
	fd.append("userfile", file);
	
	var fileName = file.name;
	var fileSize = file.size;
	
	$('#div_img_loading_id').show();
	
	$.ajax({
		type: 'POST',
		url: 'SubirBoletos?action=upload',
		data: fd,
		processData: false,
		contentType: false,
		success: function(msg){
			var data = jQuery.parseJSON(msg);
			runOnSuccess(data.filename);
		},
		error: function(msg){
			console.log('msg:'+msg);
		}
	});
}

// ___________________________________________________________________________
var timerProgressBar=null;
function resetTimer(){
	closeTimer();
	//console.log('resetTimer');
	timerProgressBar = setInterval(timerProgressBar_Tick, 5000);
}
function closeTimer(){
	//console.log('closeTimer');
	if(timerProgressBar!=null){
		clearTimeout(timerProgressBar);
		timerProgressBar=null;
	}
}
function timerProgressBar_Tick(){
	
	closeTimer();
	$.ajax({
		type: 'GET',
		url: 'ProgressBar',
		//data: 'view=ProgressBar',
		complete: function(msg){
			resetTimer();
		},
		success: function(msg)
		{
			if(msg != null && msg.length<5){
				$('#div_porcentaje_id').html(msg+" %");
				$('#progressbar_loading').val(msg);
				
				if(msg=='100'){
					if (console)
						console.log('finish');
				}
				else
					resetTimer();
			}
		},
		error: function(msg){
			resetTimer();
		}
	});
}

/*
function timerProgressBar_Tick(){
	
	closeTimer();
	$.ajax({
 	    type: 'GET',
 	    url: 'ProgressBar',
 	    data: '',
		complete: function(msg){
		},
 	    success: function(msg)
		{
			if(msg != null && msg.length<5){
				$('#div_porcentaje_id').html(msg+" %");
				console.log('      porcentaje:'+msg);
				$('#progressbar_loading').val(msg);
			}
			resetTimer();
		}
	});
}
*/
// ___________________________________________________________________________


