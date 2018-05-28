// ___________________________________________________________________________
var __timerProgressBar=null;
var __div_porcentaje_id='';
var __progressbar_id='';
//'#div_porcentaje_id' , '#progressbar_loading'
function prepareTimer(div_porcentaje_id,progressbar_id){
	__div_porcentaje_id = '#' + div_porcentaje_id;
	__progressbar_id = '#' + progressbar_id;
	$(__div_porcentaje_id).html("0 %");
	$(__progressbar_id).val('0');
}
function resetTimer(){
	closeTimer();
	__timerProgressBar = setInterval(timerProgressBar_Tick, 2000);
}
function closeTimer(){
	if(__timerProgressBar!=null){
		clearTimeout(__timerProgressBar);
		__timerProgressBar=null;
	}
}
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
				$(__div_porcentaje_id).html(msg+" %");
				//if(console)console.log('      porcentaje:'+msg);
				$(__progressbar_id).val(msg);
			}
			resetTimer();
		}
	});
}
function finishTimer() {
	closeTimer();
	$(__div_porcentaje_id).html("100 %");
	$(__progressbar_id).val("100");
}
// ___________________________________________________________________________
