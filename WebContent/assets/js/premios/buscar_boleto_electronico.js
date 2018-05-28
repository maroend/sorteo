
function initialize()
{
	$("#input_boleto_id").attr("onkeyup", "javascript:input_boleto_keyup(event);return false;");
	$('#input_boleto_id').val('');
	//$('#panel_boleto').hide();
	reset();
}

var datos_pantalla = {
	 boleto: 0
	,status: 'v'
	,talonario: 0
	,estilo: ''
	,nombre: ''
	,domicilio: ''
	,telefono_fijo: ''
	,telefono_movil: ''
	,correo: '@'
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
		
		consultaComprador(boleto);
	}
}

function consultaComprador(boleto)
{
	var idsorteo = $('#pk_sorteo').val();
	$.ajax({
		type: 'GET',
		url:  'BuscarBoletoElectronico',
		data: 'view=Buscar&boleto='+boleto+'&idsorteo='+idsorteo,
		boleto: boleto,
		async: false,
		success: function(data)
		{
			if (data.trim() == "-1") {
				alert('Ocurrio un error al consultar el boleto');
			}
			else{
				data = jQuery.parseJSON(data);
				if (data.found == 'true') {
					
					if (data.incidencia!='N'){
						switch(data.incidencia)
						{
							case 'E': datos_pantalla.status = "Extraviado"; datos_pantalla.estilo='style_status_inc_extraviado'; break;
							case 'R': datos_pantalla.status = "Robado";     datos_pantalla.estilo='style_status_inc_robado'; break;
						}
					}
					// NO VENDIDOS
					else if (data.asignado == '0'){
						datos_pantalla.status = "No&nbsp;vendido";
						datos_pantalla.estilo='style_status_no_vendido';
					}
					// VENDIDOS
					else if (data.vendido == 'V' && data.retornado == '1'){
						datos_pantalla.status = "Vendido";
						datos_pantalla.estilo='style_status_vendido';
					}
					// TRANSITO
					else{
						datos_pantalla.status = "Transito";
						datos_pantalla.estilo='style_status_transito';
					}
					
					
					// ELECTRONICO
					if (data.electronico == '1') {
						
						datos_pantalla.talonario = data.talonario;
						datos_pantalla.nombre = data.nombre;
						datos_pantalla.domicilio = data.domicilio;
						datos_pantalla.telefono_fijo = data.telefono_fijo;
						datos_pantalla.telefono_movil = data.telefono_movil;
						datos_pantalla.correo = data.correo;
						
						datos_pantalla.url_boleto = data.url_boleto;
						var idsorteo = $('#pk_sorteo').val();
						/*
						var b64 = btoa("PRINT=" + this.boleto+"&idsorteo="+idsorteo);
						//$('#btn_link_to_print').attr('href','BuscarBoletoElectronico?q='+b64);
						*/
						$('#btn_link_to_print').attr('href',data.url_comprador+'id='+data.pkboleto+"&referencia="+data.referencia);
						
					}
					else{

						datos_pantalla.talonario = data.talonario;
						
						alert('Boleto fisico');
					}
					
					updateScreen();
				}
 				else {
 					alert('No se encontro el comprador.');
 				}
			}
		},
		error: function(msg) { alert("Error de conexion con el servidor"); }
   });
}

function reset()
{
	datos_pantalla.status='-';
	datos_pantalla.talonario='----';
	datos_pantalla.estilo='style_vacio';
	datos_pantalla.nombre='-';
	datos_pantalla.domicilio='-';
	datos_pantalla.telefono_fijo='-';
	datos_pantalla.telefono_movil='-';
	datos_pantalla.correo='@';
	datos_pantalla.url_boleto='';
	//$('#div_fisicos').hide();
	//$('#div_electronicos').show();
	updateScreen();
}

function updateScreen()
{
	$('#div_status_id').html('<div class="' + datos_pantalla.estilo + '">' + datos_pantalla.status + '</div>');
	$('#div_talonario_id').html(datos_pantalla.talonario);
	$('#div_nombre_id').html(datos_pantalla.nombre);
	$('#div_domicilio_id').html(datos_pantalla.domicilio);
	$('#div_telefono_fijo_id').html(datos_pantalla.telefono_fijo);
	$('#div_telefono_movil_id').html(datos_pantalla.telefono_movil);
	$('#div_correo_id').html(datos_pantalla.correo);
	$('#img_boleto_id').attr('src',datos_pantalla.url_boleto);
}

//17283


