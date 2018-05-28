
/*
TODO:
- Check data labels after drilling. Label rank? New positions?
*/

var data = Highcharts.geojson(Highcharts.maps['countries/mx/mx-all']),
	separators = Highcharts.geojson(Highcharts.maps['countries/mx/mx-all'], 'mapline'),
	// Some responsiveness
	small = $('#container').width() < 400;

// Set drilldown pointers
$.each(data, function (i) {
	this.drilldown = this.properties['hc-key'];
	//this.value = 10; // Non-random bogus data
	//
	//this.value = parseInt(this.properties.labelrank);
	try{
		var key = this.properties["hc-key"];
		var val = searchValue(key);
		this.value = val == null ? 1 : val;
	}
	catch(e){ this.value=1; }
});

function searchValue(key) {
	for(var i=0;i<values.length;i++)
		if(values[i][0]==key)
			return values[i][1];
	return null;
}


// Instantiate the map
Highcharts.mapChart('container', {
	chart: {
		events: {
			drilldown: function (e) {
				if (!e.seriesOptions) {
					return;
					
					var cve_edo = e.point.drilldown;
					
					$.ajax({
						type: 'GET',
						cache: false,
						//url: 'countries/mx/mx-ag.json',
						url: 'Mapa',
						data: {'view':'getJSON', 'cve_edo':cve_edo},
						async: false,
						success: function(msg){
							
							var json = jQuery.parseJSON(msg);
							
							$('#container').highcharts('Map', {
						        series: json
						    });
						}
					});


					
					return;
					
					/*
					var chart = this;
					  //mapKey = 'countries/mx/' + e.point.drilldown + '-all',
					var mapKey = 'countries/mx/mx-ag-all';
						// Handle error, the timeout is cleared on success
					var fail = setTimeout(function () {
							if (!Highcharts.maps[mapKey]) {
								chart.showLoading('<i class="icon-frown"></i> Failed loading ' + e.point.name);
								fail = setTimeout(function () {
									chart.hideLoading();
								}, 1000);
							}
						}, 3000);

					// Show the spinner
					chart.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>'); // Font Awesome spinner

					// Load the drilldown map
					
				// $.getScript('https://code.highcharts.com/mapdata/' + mapKey + '.js', function () {
					$.getScript('http://localhost:8080/sorteo/' + mapKey + '.js', function () {

						data = Highcharts.geojson(Highcharts.maps[mapKey]);

						// Set a non-random bogus value
						$.each(data, function (i) {
							this.value = i;
						});

						// Hide loading and add series
						chart.hideLoading();
						clearTimeout(fail);
						chart.addSeriesAsDrilldown(e.point, {
							name: e.point.name,
							data: data,
							dataLabels: {
								enabled: true,
								format: '{point.name}'
							}
						});
					});
					/**/
				}

				this.setTitle(null, { text: e.point.name });
			},
			drillup: function () {
				this.setTitle(null, { text: '' });
			}
		}
	},

	title: {
		text: 'Highcharts Map Drilldown'
	},

	subtitle: {
		text: 'aaa',
		floating: true,
		align: 'right',
		y: 50,
		style: {
			fontSize: '16px'
		}
	},

	legend: small ? {} : {
		layout: 'vertical',
		align: 'right',
		verticalAlign: 'middle'
	},

	colorAxis: {
		min: 0,
		minColor: '#E6E7E8',
		maxColor: '#005645'
	},

	mapNavigation: {
		enabled: true,
		buttonOptions: {
			verticalAlign: 'bottom'
		}
	},

	plotOptions: {
		map: {
			states: {
				hover: {
					color: '#EEDD66'
				}
			}
		}
	},

	series: [{
		data: data,
		name: 'Mexico',
		dataLabels: {
			enabled: true,
			format: '{point.properties.postal-code}'
		}
	}, {
		type: 'mapline',
		data: separators,
		color: 'silver',
		enableMouseTracking: false,
		animation: {
			duration: 500
		}
	}],

	drilldown: {
		activeDataLabelStyle: {
			color: '#FFFFFF',
			textDecoration: 'none',
			textOutline: '1px #000000'
		},
		drillUpButton: {
			relativeTo: 'spacingBox',
			position: {
				x: 0,
				y: 60
			}
		}
	}
});


