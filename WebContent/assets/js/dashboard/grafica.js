
function initialize()
{
	$(consultaBoletos);
	$(iniciaGraficaDePieLegend);
}

function consultaBoletos() {
	var nivel = $('#hidden_nivel_id').val();

	$.ajax({
		type: "POST",
		cache: false,
		url: "grafica",
		data: "view=lineas&nivel="+nivel,
		async: false,
		success: function(msg){
			
    		var data = jQuery.parseJSON(msg);
    		
    		$('#grafica').highcharts('StockChart', {
    			
    			rangeSelector : {
    				selected : 1
    			},
    			
    			title : {
    			    text : 'Detalle de Venta Boletos'
    			},
    			
    			series : [{
    				name : 'Boletos Vendidos',
    				data : data,
    				tooltip: {
    					valueDecimals: 0
    				}
    			}]
    		});
    	}
   	});
}

function iniciaGraficaDePie() {
	
	var nivel = $('#hidden_nivel_id').val();

	$.ajax({
		type: "POST",
		cache: false,
		url: "grafica",
		data: "view=pie&nivel="+nivel,
		async: false,
		success: function(msg){
		    // Radialize the colors
		    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		        return {
		            radialGradient: { cx: 0.5, cy: 0.3, r: 0.9 },
		            stops: [
		                [0, color],
		                [1, Highcharts.Color(color).brighten(-0.2).get('rgb')] // darken
		            ]
		        };
		    });
		    
		    var data = jQuery.parseJSON(msg);

		    // Build the chart
		    $('#div_pie_id').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: ''
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b><br/> {point.percentage:0.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'darkblue'
		                    },
		                    connectorColor: 'silver'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: 'Boletos ',
		            data: data
		        }]
		    });
		    
		    
		    
		    
	    }// end success
   	});
}


function iniciaGraficaDePieLegend() {
	var nivel = $('#hidden_nivel_id').val();
	
	console.log("pie with legend");

	$.ajax({
		type : "POST",
		cache: false,
		url : "grafica",
		data : "view=pie&nivel=" + nivel,
		async : false,
		success : function(msg) {
			console.log("msg="+msg);
			var data = jQuery.parseJSON(msg);
			console.log("data="+data);
			// Build the chart
			$('#div_pie_id').highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : ''
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
				},
				plotOptions : {
					pie : {
						allowPointSelect : true,
						cursor : 'pointer',
						dataLabels : {
							enabled : true,
							format : '{point.percentage:0.1f} %',
							style : {
								color : (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'darkblue'
							},
							connectorColor : 'silver'
						},
						showInLegend : true
					}
				},
				series : [ {
					type : 'pie',
					name : 'Boletos ',
					data : data
				} ]
			});
		}
	});

}
