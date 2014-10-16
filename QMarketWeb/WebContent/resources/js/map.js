
function WMSMap($options) {
	
	if(typeof($options)==='undefined'){
		$options={};
    }

    var self = this;
	
	//self.Clusterer = null;	
	
	self.milesBuffer = 10;
	self.MDiv = null;
	var gmap_ = null;

	self.MaxMarkersAdded = ($options.MaxMarkersAdded)?$options.MaxMarkersAdded:100; //100 by default
	self.ZoomOnOne = ($options.ZoomOnOne)?$options.ZoomOnOne:true;
	self.ShowInfoWindow = ($options.ShowInfoWindow) ? $options.ShowInfoWindow : true;
	
	var InfoWindow = null;
	var ResultNavigator = null;
	var SearchResults = null;
	var ResizeDiv = false;
	var isMobile = ($options.isMobile)?$options.isMobile:false;
	
    var gMarkers = new WMSMarkerArray();

	var MWidth = 0;
	var MHeight = 0;
	
	var PanelWidth = 320;
	
	var HideBtnDivId = 'map-slideout-trig';

	self.getGMap = function() {
	    return gmap_;
	};

	self.createMap = function(mapdiv) {
	    if (mapdiv == null) {
	        alert("Error: Must provide a div container for the map.");
	        return;
	    }
	   

	    self.MDiv = mapdiv;
	    
	    MWidth = $(mapdiv).width();
	    MHeight = $(mapdiv).height();

        //self.Clusterer = new ClusterMarker(map);
        //self.Clusterer.clusteringEnabled = true;
        //self.Clusterer.borderPadding = 512;
        
	    InfoWindow = new google.maps.InfoWindow({
			  content: '',
			  maxWidth: 320
			});

	    
	    if(!isMobile) {
	    
	        var elm = '<div id="results_nav_trigger" class="mapresultscnt" ' +
	        'style="float: left; min-height ' + MHeight + 'px; width: ' + PanelWidth + 'px; display: none;">'+
	        '</div>';
	        $(self.MDiv).css( { float: "right"  });
	        $(self.MDiv).after(elm);
	        
	        ResultNavigator = $('#results_nav_trigger');
	        
	        elm = '<div class="mapresultsheader">Search Results</div>';        
	        ResultNavigator.append(elm);
	        ResultNavigator.append('<div class="clearFix"></div>');
	        
	        elm = '<div id="map_search_results" style="max-height:' + MHeight +'px; overflow: auto;"></div>';
	        ResultNavigator.append(elm);        
	        SearchResults = $('#map_search_results');    
	
	
	        $('#'+HideBtnDivId).click(function(){
	            if (ResultNavigator.hasClass('visible')){
	            	self.showHideResults(false);
	            } else {
	            	self.showHideResults(true);
	            }
	        });
        
	    } else { 	        
	        var elm = '<div id="results_nav_trigger" class="mapresultscnt" ' +
	         'style="min-height ' + MHeight + 'px; width: 100%; display: none;">'+
	        '</div>';
	        $(self.MDiv).before(elm);
	        
	        ResultNavigator = $('#results_nav_trigger');
	        
	        elm = '<div class="mapresultsheader">Search Results</div>';        
	        ResultNavigator.append(elm);
	        ResultNavigator.append('<div class="clearFix"></div>');
	        
	        elm = '<div id="map_search_results" style="max-height:' + MHeight +'px; overflow: auto;"></div>';
	        ResultNavigator.append(elm);        
	        SearchResults = $('#map_search_results');    
	
	
	        $('#'+HideBtnDivId).click(function(){
	            if (ResultNavigator.hasClass('visible')){
	            	self.showHideResults(false);
	            } else {
	            	self.showHideResults(true);
	            }
	        });
	    	
	    }
	    
        var mapOptions = {
                center: new google.maps.LatLng(36.05798104702501, -86.671142578125),
                zoom: 4
              };
	    
        var map = new google.maps.Map(self.MDiv, mapOptions);
        gmap_ = map;	    
		
		setTimeout( function() {  google.maps.event.trigger(gmap_, "resize"); }, 2000);
        
        google.maps.event.addListener(map, 'idle', self.boundsChanged);

        
	};
	
	self.boundsChanged = function() {
		var env = self.getMapEnvelope();
		var lvl = gmap_.getZoom();
		
		if(ResizeDiv) {
			ResizeDiv = false;
			return;
		}
		
		if(lvl > 9 && !isInfoWindowOpen()) {
	  		self.removeMarkers(false);			
			SearchResults.empty();
			$('#'+HideBtnDivId).html("<span class='ui-button-text'>Show / Hide Results</span>");
	  		jQuery.ajax({
	  		    type: 'GET',
	  		    url: "/market/secure/search/bbox",
	  		    data: { "xmin" : env[0], "ymin" : env[1], "xmax" : env[2], "ymax" : env[3] },
	  		    contentType: "text/html",
	  		    success:  function(data) {	  		    	
	  		    	if(data != null && data.length > 0) {	
	  					$('#'+HideBtnDivId).html("<span class='ui-button-text'>Show / Hide Results "+ data.length +"</span>");
	  		    		for(var i = 0; i < data.length; i++) {
	  	  		    	  var pt = new google.maps.LatLng(data[i].lat, data[i].lon);
	  	  			      var marker = self.createMarker(pt, data[i], "marker", null).gmarker;
	  		    		}		    			    		
	  		    	}
	  		    },
	  	      error: function (xhr, ajaxOptions, thrownError) {
	  	    	  showMessage("No results found.");
	  	      }  		    
	  		});
		} 
	};
	
	self.showHideResults = function(open) {
		if(open) {
            if (ResultNavigator.hasClass('visible')){
            	return;
            } else {
            	ResizeDiv = true;
            	if(!isMobile) {
            		self.setMapSize(MWidth-PanelWidth,MHeight);
            		ResultNavigator.show('slide',  { direction : 'left' }, 500).css("display","block").addClass('visible');
            	} else {
                	ResultNavigator.show('slide', { direction : 'up' }, 500).css("display","block").addClass('visible');    
            	}       	
            }
		} else {
            if (ResultNavigator.hasClass('visible')){
            	if(!isMobile) {            	
	            	ResultNavigator.show('slide', { direction : 'right' }, 500, function() {
	            		ResultNavigator.css("display","none").removeClass('visible');
	            		self.setMapSize(MWidth,MHeight);
	            	});    
            	} else {
	            	ResultNavigator.show('slide', { direction : 'down' }, 500, function() {
	            		ResultNavigator.css("display","none").removeClass('visible');
	            	});             		
            	}
            }
		}
	}

	self.removeNamedMarker = function(name) {
	    for (var i = 0; i < gMarkers.length; i++) {
	    	if(gMarkers[i].name == name) { 
	    		self.removeMarker(gMarkers[i]);
	    	}
	    }
	};

	self.removeMarkers = function(force) {
	    for (var i = 0; i < gMarkers.length; i++) {
	    	if(gMarkers[i].del || force) { 
	    		if(gMarkers[i].gmarker!=null) {
	    			gMarkers[i].gmarker.setMap(null);
	    		}
	    		gMarkers.splice(i,1);
	    		i--;
	    	}
	    }
	};

	self.removeMarker = function(marker) {
		gMarkers[marker.indx].gmarker.setMap(null);
		gMarkers.splice(marker.indx,1);
	};

	self.setMapBounds = function(env, name) {
	    //left, bottom, right, top
	    var ne = new google.maps.LatLng(env[1], env[2]);
	    var sw = new google.maps.LatLng(env[3], env[0]);
	    var bounds = new google.maps.LatLngBounds(sw, ne);
	    
	    gmap_.fitBounds(bounds);
	};
	
	//left, bottom, right, top
	self.calcMaxEnvelope = function(left, right, top, bottom) {
        var maxenv = new Array();
        if (left.length > 0) {          
            maxenv[0] = parseFloat(left[0]);
            maxenv[1] = parseFloat(bottom[0]);
            maxenv[2] = parseFloat(right[0]);
            maxenv[3] = parseFloat(top[0]);
            for (var i = 1; i < left.length; i++) {
                if (parseFloat(left[i]) < maxenv[0]) maxenv[0] = parseFloat(left[i]);
                if (parseFloat(bottom[i]) < maxenv[1]) maxenv[1] = parseFloat(bottom[i]);
                if (parseFloat(right[i]) > maxenv[2]) maxenv[2] = parseFloat(right[i]);
                if (parseFloat(top[i]) > maxenv[3]) maxenv[3] = parseFloat(top[i]);
            }
        }
        return maxenv;       
    };
        
	
	self.setMapSize = function(width,height){
		//var LatLng = gmap_.getCenter();
		self.MDiv.style.height = height + "px";
		self.MDiv.style.width = width + "px";
		google.maps.event.trigger(gmap_, "resize");		
		//gmap_.setCenter(LatLng);
	};

	self.moveMap = function(left,top){
		self.MDiv.style.left = left + "px";
		self.MDiv.style.top = top + "px";;
	};

	self.getMapEnvelope = function() {
	    var bounds = gmap_.getBounds();
	    var sw = bounds.getSouthWest();
	    var ne = bounds.getNorthEast();

	    var Minx = sw.lng();
	    var Miny = sw.lat();
	    var Maxx = ne.lng();
	    var Maxy = ne.lat();

	    return [Minx, Miny, Maxx, Maxy];
	};

	self.createMarker = function(point, data, layerId, $markerOptions) {    
	    if ($markerOptions == null || typeof ($markerOptions) === 'undefined') {
	        $markerOptions = {};
	    }

		var marker = new google.maps.Marker({
	    	position:point,
	    	map: gmap_
	    });
		
		marker.info = InfoWindow;
		
		self.resultsWindow(data,point,marker);

	    google.maps.event.addListener(marker, "click", function() {
        	self.removeNamedMarker("buffer");
	    	InfoWindow.setContent(self.infoWindow(data,point));
	    	marker.info.open(gmap_, marker);
	    } );
	    
	    layerId = $markerOptions.markerName?$markerOptions.markerName:layerId;
	    //gmarker,name,type,del 
	    gMarkers.add(marker,layerId,$markerOptions.markerType,$markerOptions.markerDelete);
	    return gMarkers[gMarkers.length-1];
	};
	
	var isInfoWindowOpen = function(infoWindow){
	    var map = InfoWindow.getMap();
	    return (map !== null && typeof map !== "undefined");
	}	
	
	self.resultsWindow = function(data,point,marker) {
		var txt = "";
		txt += "<div class='mapresults'>";
		txt += "<div class='company_logo'>";
		txt += "<img src='/market/secure/vendor/image?id=" + data.companyInformation.companyCd + "' height='100' width='100' alt='Logo' />";
		txt += "</div>";
		txt += "<div class='company_info'>";
		txt += "<p class='company_name'>" + data.companyInformation.companyName + "</p>";
	    txt += data.address + "<br />";	    
	    txt += data.city + " " + data.usStates.code + "<br />";	 
	    txt += data.companyInformation.contactNo + "<br />";
		txt += "</div>";
	    txt += '</div>';
		txt += "<div class='clearFix height10'></div>";		
        var div = $(txt).appendTo(SearchResults);
        div.on("click", function() {
        	self.removeNamedMarker("buffer");
  			gmap_.setZoom(14);
  			gmap_.setCenter(point);
        	google.maps.event.trigger(marker, "click");				
        });
	};

	self.infoWindow = function(data,point) {
		var txt = "";
		txt += "<div class='mapinfowindow'>";
		txt += "<div class='company_logo'>";
		txt += "<img src='/market/secure/vendor/image?id=" + data.companyInformation.companyCd + "' height='100' width='100' alt='Logo' />";
		txt += "</div>";
		txt += "<div class='company_info'>";
		txt += "<p class='company_name'>" + data.companyInformation.companyName + "</p>";
	    txt += data.address + "<br />";	    
	    txt += data.city + " " + data.usStates.code + "<br />";	 
	    txt += data.companyInformation.contactNo + "<br />";    
		txt += "</div>";
		txt += '<hr />';
		txt += "<div class='clearFix height10'></div>";
		txt += "<div class='mapinfogobtn'><a href='/market/secure/quakd?id=" +data.locId+"'><img src='/market/resources/images/quakd_go_sm.png' height='50' width='50'/></a></div>";
		txt += '</div>';
	    return txt;
	};
	
	self.drawBuffer = function(point) {
		var bufferOptions = {
		          center: point,
		          radius: self.milesBuffer * 1609.344,
		          fillColor: "#ff69b4",
		          fillOpacity: 0.3,
		          strokeOpacity: 0.0,
		          strokeWeight: 0,
		          map: gmap_
		      };	
		var buffer = new google.maps.Circle(bufferOptions);
	    gMarkers.add(buffer,"buffer","graphic",false);
	    
	    var radi = gMarkers[gMarkers.length-1];
	    
	    return radi;
	};
	
	var showMessage = function(msg) {
		$('<div />').html(msg).dialog({
			title: "Message",
            buttons: {
                Ok: function () {
                    $(this).dialog("close");
                    $('#dialog-message').remove();
                }
            }			
		});		
	};
	
	/* first search function called */
	self.searchLocations = function(criteria) {
  		SearchResults.empty();
		$('#'+HideBtnDivId).html("<span class='ui-button-text'>Show / Hide Results</span>");
		self.removeMarkers(true);
		var addressonly = $("#map_address_check").prop("checked");

		if(addressonly) {
			self.searchAddress(criteria);
			return;
		}
		
  		jQuery.ajax({
  		    type: 'GET',
  		    url: "/market/secure/search/name",
  		    data: { "name" : criteria },
  		    contentType: "text/html",
  		    success:  function(data) {          
  		    	if(data != null && data.length > 0) {	
	  			    var left = [];
	  			    var right = [];
	  			    var top = [];
	  			    var bottom = [];
	  			    var pt  = null;
	  			    $('#'+HideBtnDivId).html("<span class='ui-button-text'>Show / Hide Results " + data.length + "</span>");
  		    		for(var i = 0; i < data.length; i++) {
  	  		    	  pt = new google.maps.LatLng(data[i].lat, data[i].lon);
  	  			      var marker = self.createMarker(pt, data[i], "marker", null).gmarker;
  	  			      left.push(pt.lng());
	  	  			  right.push(pt.lng());
	  	  			  top.push(pt.lat());
	  	  			  bottom.push(pt.lat());
  		    		}		    		
  		    		if(data.length > 1) {
  		    			var env = self.calcMaxEnvelope(left, right, top, bottom);
  			        	self.setMapBounds(env);
  		    		} else {
  		    			gmap_.setZoom(12);
  		    			gmap_.setCenter(pt);
  		    		} 		    		
  		    		//self.showHideResults(true);
  		    	} else {
  		    		showMessage("No results found.");
  		    	}
  		    },
  	      error: function (xhr, ajaxOptions, thrownError) {
  	    	showMessage("No results found.");
  	      }  		    
  		}); 			
	}
	

	self.searchAddress = function(address) {
		var geocoder = new google.maps.Geocoder();
	    geocoder.geocode( { 'address': address}, function(results, status) {
	        if (status == google.maps.GeocoderStatus.OK) {
	          var point = results[0].geometry.location;
		      //google.maps.event.trigger(marker, 'click');	      
		      var buffer = self.drawBuffer(point).gmarker;
		      gmap_.fitBounds(buffer.getBounds());
		      //make a call via AJAX to show locations nearby
		      findByLocation(point);
	        } else {
	          alert("Geocode was not successful for the following reason: " + status);
	        }
	      });
  	};
  	
  	var findByLocation = function(latlng) {
  		jQuery.ajax({
  		    type: 'GET',
  		    url: "/market/secure/search/location",
  		    data: { x : latlng.lng() , y : latlng.lat(), miles : self.milesBuffer},
  		    contentType: "text/html",
  		    success:  function(data) {          
  		    	if(data != null && data.length > 0) {	
  		    		$('#'+HideBtnDivId).html("<span class='ui-button-text'>Show / Hide Results " + data.length + "</span>");
  		    		for(var i = 0; i < data.length; i++) {
  	  		    	  var pt = new google.maps.LatLng(data[i].lat, data[i].lon);
  	  			      var marker = self.createMarker(pt, data[i], "marker", null).gmarker;
  		    		}
  		    		//self.showHideResults(true);
  		    	} else {
  	  	    	  showMessage("No results found.");
  		    	}
  		    },
  	      error: function (xhr, ajaxOptions, thrownError) {
  	    	  showMessage("No results found.");
  	      }  		    
  		}); 		
  	};
  	
  	self.convertDMSToDD = function(D,M,S) {
		var DD;
	    D < 0 ? DD = roundOff(D + (M/-60) + (S/-3600),6) : DD = roundOff(D + (M/60) + (S/3600),6);
	   	return DD;
  	};
  	
  	var roundOff = function(num, decimalplaces) {
 		 var decimalfactor = Math.pow(10,decimalplaces);
	     var roundedValue = Math.round(num*decimalfactor)/decimalfactor;
	     return roundedValue;	
  	};
	
};

function WMSMarkerArray() {
	var array = new Array();
	
	
	Array.prototype.nameIndex = function(str) {
	    for (var i = 0; i < this.length; i++) {	 	
        	if (this[i].name == str) return i;
   		}
   		return -1;	
	};

	Array.prototype.markerIndex = function(str) {
	    for (var i = 0; i < this.length; i++) {	 	
        	if (this[i].name == str) return i;
   		}
   		return -1;	
	};

	//push the marker on to the top of stack (last) return current length
	Array.prototype.add = function(gmarker,name,type,del) {
		var marker = new WMSMarker();
		marker.gmarker = gmarker ? gmarker : null;
		marker.name = name ? name : "";
		marker.type = type ? type : "";
		marker.del =  del; //del == 'false' ? false : true
		this.push(marker);
		marker.indx = this.length-1;
	}; 		
	
	var WMSMarker = function() {
		this.gmarker = null;	
		this.name = "";
		this.type = "";
		this.del = true;
		this.indx = 0;
	
		this.setProperties = function(gmarker,name,type,del) {
			this.gmarker = gmarker;	
			this.name = name;
			this.type = type;	
			this.del = del;
		};
		
		this.getProperties = function() {		
			return [this.gmarker,this.name,this.type,this.del];
		};
									
	};
	
	return array;
};