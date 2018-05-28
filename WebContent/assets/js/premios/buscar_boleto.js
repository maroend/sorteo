
function initialize()
{
	//$(consultaPremios);
	$("#input_boleto_id").attr("onkeyup", "javascript:input_boleto_keyup(event);return false;");
	$('#input_boleto_id').val('');
	
	reset();
}

/*
var responsables = [
	{
		nombre: 'Scarlette C&oacute;rdoba',
		cajas: [
			[1, 400],
			[2, 800],
			[3, 1200],
			[4, 1600]]
	}, {
		nombre: 'Dany Aguilar',
		cajas: [
			[5, 2000],
			[6, 2400],
			[7, 2800],
			[8, 3200]]
	}, {
		nombre: 'Esther Pi&ntilde;a',
		cajas: [
			[9, 3600],
			[10, 4000],
			[11, 4400],
			[12, 4800]]
	}, {
		nombre: 'Cinthya',
		cajas: [
			[13, 5200],
			[14, 5600],
			[15, 6000],
			[16, 6400]]
	}, {
		nombre: 'Karina Luna',
		cajas: [
			[17, 6800],
			[18, 7200],
			[19, 7600],
			[20, 8000]]
	}, {
		nombre: 'Nadia',
		cajas: [
			[21, 8400],
			[22, 8800],
			[23, 9200],
			[24, 9600]]
	}, {
		nombre: 'Humberto P&eacute;rez',
		cajas: [
			[25, 10000],
			[26, 10400],
			[27, 10800],
			[28, 11200]]
	}, {
		nombre: 'Leopoldo',
		cajas: [
			[29, 11600],
			[30, 12000],
			[31, 12400],
			[32, 12800]]
	}, {
		nombre: 'Luzma',
		cajas: [
			[33, 13200],
			[34, 13600],
			[35, 14000],
			[36, 14400]]
	}, {
		nombre: 'Adriana Garc&iacute;a',
		cajas: [
			[37, 14800],
			[38, 15200],
			[39, 15600],
			[40, 16000]]
	}, {
		nombre: 'Erandi',
		cajas: [
			[41, 16400],
			[42, 16800],
			[43, 17200],
			[44, 17600]]
	}, {
		nombre: 'Alejandro Amado',
		cajas: [
			[45, 18000],
			[46, 18400],
			[47, 18800]]
	}
];
*/
var responsables = [
                	{
                		nombre: 'Cinthia Barraza',
                		cajas: [
                			[1, 400],
                			[2, 800],
                			[3, 1200],
                			[4, 1600]]
                	}, {
                		nombre: 'Adriana Garc&iacute;a',
                		cajas: [
                			[5, 2000],
                			[6, 2400],
                			[7, 2800],
                			[8, 3200]]
                	}, {
                		nombre: 'Fernando Aguilar',
                		cajas: [
                			[9, 3600],
                			[10, 4000],
                			[11, 4400],
                			[12, 4800]]
                	}, {
                		nombre: 'Cinthya Estrada',
                		cajas: [
                			[13, 5200],
                			[14, 5600],
                			[15, 6000],
                			[16, 6400]]
                	}, {
                		nombre: 'Pablo Salas',
                		cajas: [
                			[17, 6800],
                			[18, 7200],
                			[19, 7600],
                			[20, 8000]]
                	}, {
                		nombre: 'Nadia Garc&iacute;a',
                		cajas: [
                			[21, 8400],
                			[22, 8800],
                			[23, 9200],
                			[24, 9600]]
                	}, {
                		nombre: 'Humberto P&eacute;rez',
                		cajas: [
                			[25, 10000],
                			[26, 10400],
                			[27, 10800],
                			[28, 11200]]
                	}, {
                		nombre: 'Leopoldo Navarro',
                		cajas: [
                			[29, 11600],
                			[30, 12000],
                			[31, 12400],
                			[32, 12800]]
                	}, {
                		nombre: 'Luz Maria Aguilar',
                		cajas: [
                			[33, 13200],
                			[34, 13600],
                			[35, 14000],
                			[36, 14400]]
                	}, {
                		nombre: 'Natalia Estibaliz',
                		cajas: [
                			[37, 14800],
                			[38, 15200],
                			[39, 15600]]
                	}, {
                		nombre: 'Alejandro Amado',
                		cajas: [
                   			[40, 16000],
                			[41, 16400]]
                	}
                ];


var datos_pantalla = {
	 boleto: 0
	,status: 'v'
	,talonario: 0
	,caja: 0
	,responable: 'x'
	,estilo: ''
}

function buscarResponsable(talonario)
{
	for(var r=0; r<responsables.length; r++)
	{
		var cajas = responsables[r].cajas;
		for(var i=0; i<cajas.length; i++){
			if (talonario <= cajas[i][1]){
				datos_pantalla.talonario = talonario;
				datos_pantalla.caja = cajas[i][0];
				datos_pantalla.responsable = responsables[r].nombre;
				return 0;
			}
		}
	}
	return -1;
}

function input_boleto_keyup(e)
{
	var value = $('#input_boleto_id').val();
	var isDigit=true;
	var newValue='';
	for(var i=0; i<value.length; i++)
	{
		if('0'<=value[i] && value[i]<='9')
			newValue+=value[i];
		else
			isDigit=false;
	}
	if(!isDigit)
		$('#input_boleto_id').val(newValue);
	
	if(e.which == 13)
	{
		var boleto = $("#input_boleto_id").val().trim();
		if (boleto == '') {
			reset();
			return;
		}
		boleto = boleto.trim();
		while (boleto.length < 6)
			boleto = '0' + boleto;
		if (boleto == '000000')
			boleto = '00000';

		$("#input_boleto_id").val(boleto);
		
		consultaTalonario(boleto);
	}
}

function consultaTalonario(boleto)
{
	var idsorteo = $('#pk_sorteo').val();
	$.ajax({
		type: 'GET',
		url:  'BuscarBoleto',
		data: 'view=Buscar&boleto='+boleto+'&idsorteo='+idsorteo,
		async: false,
		success:
			function(msg)
			{
				var index_1 = msg.indexOf('<datos>');
				var index_2 = msg.indexOf('</datos>');
				if(0<=index_1 && index_1<index_2)
				{
					index_1 += 7;
					var datos = msg.substring(index_1, index_2);
					var array=datos.split('~');
					
					for(var i=0; i<array.length; i++)
						console.log('array['+i+']='+array[i]);
					var talonario = array[2];
					var vendido = array[3];
					var incidencia = array[4];
					var retornado = array[5];
					var asignado = array[6];
					var electronico = array[7];
					
					// INCIDENCIAS
					if (incidencia!='N'){
						switch(incidencia)
						{
							case 'E': datos_pantalla.status = "Extraviado"; datos_pantalla.estilo='style_status_inc_extraviado'; break;
							case 'R': datos_pantalla.status = "Robado";     datos_pantalla.estilo='style_status_inc_robado'; break;
						}
					}
					// NO VENDIDOS
					else if (asignado == '0'){
						datos_pantalla.status = "No&nbsp;vendido";
						datos_pantalla.estilo='style_status_no_vendido';
					}
					// VENDIDOS
					else if (vendido == 'V' && retornado == '1'){
						datos_pantalla.status = "Vendido";
						datos_pantalla.estilo='style_status_vendido';
					}
					// TRANSITO
					else{
						datos_pantalla.status = "Transito";
						datos_pantalla.estilo='style_status_transito';
					}
					
					
					// ELECTRONICO
					if (electronico == '1') {
						$('#div_fisicos').hide();
						$('#div_electronicos').show();
						
						datos_pantalla.talonario = talonario;
					}
					else{
						$('#div_fisicos').show();
						$('#div_electronicos').hide();
						if (buscarResponsable(talonario) != 0)
						{
							datos_pantalla.responsable = 'No se encuentra el responsable de caja';
							datos_pantalla.talonario = talonario;
						}
					}
					
					updateScreen();
				}
				else
				{
					reset();
					$('#div_responsable_id').html('N&uacute;mero de boleto fuera de rango.');
				}
			}
   });
}

function reset()
{
	datos_pantalla.status='-';
	datos_pantalla.talonario='-';
	datos_pantalla.caja='-';
	datos_pantalla.responsable='-';
	datos_pantalla.estilo='style_vacio';
	$('#div_fisicos').show();
	$('#div_electronicos').hide();
	updateScreen();
}

function updateScreen()
{
	$('#div_status_id').html('<div class="'+datos_pantalla.estilo+'">' + datos_pantalla.status + '</div>');
	$('#div_talonario_id').html(datos_pantalla.talonario);
	$('#div_caja_id').html(datos_pantalla.caja);
	$('#div_responsable_id').html(datos_pantalla.responsable);
}


