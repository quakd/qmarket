var webAppRoot = '/market';

var isMobile = false;

function ResizeMainArea(main)
{
	  var h = $(window).height();
	  var tot = main ? 120 : 170;
	  var size = h - tot;

	  var minsize = size < 250 ? 250 : size;
	  
	  if(main) {
		  $(".bgimage img")
		    .css('height', minsize);
	  }
	  
}

function LoadHeader(main)
{
	ResizeMainArea(main);	
	$('.signin').bind('click', ClickSignIn);
	$('.signup').bind('click', ClickSignUp);	

	$('input').addClass("ui-corner-all");
	
	 $( "input[type=submit], button" )
	 .button()
	 .click(function( event ) {
	 event.preventDefault();
	 });
	
	if(!main)
	{
		var logonContent = $('.logon-content').html();
		var logonTitle = $('.logon-title').html();
	     $('.dropDownMenu').qtip({
	    		    content: {
	    		        text: logonContent,
	    		        title: {
	    		            text: logonTitle
	    		        }   
	    		    },
			        style: { 
			        	classes: 'customTool qtip-rounded qtip-bootstrap'
			        },		    
	     position: {
	    	 my: 'top center',
	         at: 'bottom center', // at the bottom right of...
	         target: $('.dropDownMenu') // my target
	     },
	     show: {
	         event: 'click'
	     },
	     hide: {
	         event: 'click'
	     }		     
	     });
	
	     $("#quick_nav_btn").click(function(){
	    	 if($(this).hasClass("clicked")) {
	    		 $(this).removeClass("clicked");   
	    	 } else {
	    		 $(this).addClass("clicked");
	    	 }
	     });
	     
	     $('#searchTxt').watermark("Search...");
	     
	} else {
	    $('.bgimage').flexslider( { 
    		pauseOnAction: false, 
    		pauseOnHover: false,
    		controlNav: false,
    		useCSS: true,
    		directionNav: false
    		}
	    );			
	}
}

/*
$("#socialmedia-dlg").modal({onClose: function (dialog) {
	jQuery.ajax({
	    type: 'POST',
	    url: "${splashScreen}",
	    data: {},
	    contentType: "text/html"
	});			
	$.modal.close(); 
}});	
*/

function LoadSearch() {		
	
	$('#searchForm').submit(function() {
		ClickFindAddress();
		$('#search_logo').hide();
	});
	
	$('#searchAddressField').watermark("Vendor Name");
	
	//wmsMap = new WMSMap({ 'isMobile' : isMobile });	
	//wmsMap.createMap($("#map-canvas")[0]);     

}

function LoadSignUp() {
	$('#username').watermark("Quakd Username");
	$('#password').watermark("Password");
	$('#repassword').watermark("Confirm Password");
	/*
	$('#firstName').watermark("First Name");
	$('#lastName').watermark("Last Name");
	$('#address').watermark("Address");
	$('#state').watermark("State");
	$('#city').watermark("City");
	$('#zip').watermark("55555");	
	$('#phone1').watermark("777-8889999");
	*/	
}

function ClickFindAddress()
{

	var address = $('#searchAddressField').val();
	$('#vendorList').html('');
	jQuery.ajax({
	    type: 'GET',
	    url: webAppRoot + "/secure/search/name",
	    data: { "name" : address },
	    contentType: "text/html",
	    success:  function(data) {  
	    	if(data != null && data.length > 0) {
	    		for(var i = 0; i < data.length; i++) {
	    			var txt = "";
	    			txt += "<li style='margin: 0.5em 0;'>";
	    			txt += "<a href='/market/secure/quakd?id=" +data[i].locId+"' style='border: none;'>"
	    			txt += "<img src='/market/secure/vendor/image?id=" + data[i].companyInformation.companyCd + "' height='100' width='100' alt='Logo' />";
	    			txt += "<h2>" + data[i].companyInformation.companyName + "</h2>";
	    			/*
	    		    txt += data[i].address + "<br />";	    
	    		    txt += data[i].city + " " + data[i].usStates.code + "<br />";	 
	    		    txt += data[i].companyInformation.contactNo + "<br />";
	    		    */
	    			txt += "</a>";
	    		    txt += '</li>';
	    	        var div = $(txt).appendTo('#vendorList');
	    	        
	    		}	
	    		$("#vendorList").listview("refresh");

	    	} else {
	    		showMessage("No results found.");
	    	}
	    },
      error: function (xhr, ajaxOptions, thrownError) {
    	showMessage("No results found.");
      }  		    
	}); 
		
}

function showMessage(msg) {
	$('<div />').html(msg).dialog({
		title: "Message",
        buttons: {
            Ok: function () {
                $(this).dialog("close");
                $('#dialog-message').remove();
            }
        }			
	});		
}

function ClickSignIn() 
{
	window.location =  webAppRoot + '/signin';
}

function ClickSignUp() 
{
	window.location =  webAppRoot + '/signup';
}

function SubmitMessage() {
	var len = $('#socialMessage').val().length;
	if(len > 140) {
		$('#socialmsg-dlg').dialog({ modal: false,
	        buttons: {
	            Ok: function () {
	                $(this).dialog("close");
	                $('#dialog-message').remove();
	            }
	        }
	    });  	
	} else {
		$('#shareForm')[0].submit();	
	}
}