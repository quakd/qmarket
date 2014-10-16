/**
 * RPC-jq.js
 * 
 * Small library built to handle either ajax-based form submission or iframe form submission (to handle the case
 * that form contains an input 'file' type element). In order for this library to work, you must include the JQ
 * core and UI tags in your JSP / HTML page.
 * 
 * Use either the js functions updateContentFromForm(formObj,['div1','div2']) for simple form submission
 * OR
 * Use updateContentFromURL(url,['div1','div2'], post or get ? true : false , web params) as onclick event for your anchor tag
 * 
 * The array div id's represents the target div containers that you wish to update in your view/page. An example usage
 * of updateContentFromForm which is probably what someone would more typically use would be done like:
 * 
 * <form action="someUrl" onsubmit="return new RPC().updateContentFromForm(this,['div1']);">
 * </form>
 * 
 * 
 **/
function RPC(noAnim) {
	
  this.divList = [];

  this.updateContentFromForm = function (form, list) {

      var oForm = jQuery(form);
      var self = this;
      var bUseGet = false; //oForm.attr("method") && oForm.attr("method").toLowerCase() == "get";    	
      var formURL = oForm.attr("action");

      if (formURL) {
          formURL = self.unescape(formURL);
      }

      this.divList = list;
      self.showBlockUI();
      if (oForm.find('input:file').length != 0) { //post the changes via iframe
          var frame = jQuery('<iframe style="display:none" id="iframeSubmitFile" name="iframeSubmitFile" ></iframe>');
          jQuery(document.body).append(frame);
          oForm.attr("target", "iframeSubmitFile");
          frame.load(function () {
              self.removeBlockUI();
              self.fileFrameLoaded();
          }
		);
          form.submit();
      } else { //do ajax style submission 
          jQuery.ajax(
	    {
	        url: formURL,
	        data: oForm.serialize(),
	        success: function (data, textStatus, XMLHttpRequest) { self.removeBlockUI(); self.processSuccess(data, textStatus, XMLHttpRequest); },
	        error: function (XMLHttpRequest, textStatus, errorThrown) { self.removeBlockUI(); self.processError(XMLHttpRequest, textStatus, errorThrown); },
	        dataType: 'html',
	        type: (bUseGet ? "GET" : "POST")
	    });
      }

      return false;
  };

this.showBlockUI = function () {
    $.blockUI({ overlayCSS: { backgroundColor: '#f0f0f0' }, message: $('<img src="/IDC/Images/spinnerLarge.gif"/> ') });
};

this.removeBlockUI = function () {
    $.unblockUI();
};
  
  this.showOverlay = function() {
		  
 	var overlay = jQuery('#_ajaxOverlay');
 	if(overlay.length == 0) {
 		overlay = jQuery('<div id="_ajaxOverlay" style="position:absolute;"></div>');
 		jQuery(document.body).append(overlay);
 		overlay.css('left',jQuery(document.body).scrollLeft());
 		overlay.css('top',jQuery(document.body).scrollTop()); 
 		overlay.css('width',jQuery(document.body).innerWidth());
 		overlay.css('height',jQuery(document.body).innerHeight());
 		overlay.css('display','block');
 		overlay.css('zIndex','99');
 		overlay.css('backgroundColor','#ffffff');	
 		if(document.all) {
 			overlay.css('filter','alpha(opacity=1)');
 		} else {
 			overlay.css('opacity','0.01')
 		}
 		overlay.css('cursor','progress');
 	}
 	
 	jQuery('select').hide();
 	
 	//make the overlay floating to follow the user as they scroll
 	jQuery(window).scroll(
 		function () {
 			var overlay = jQuery('#_ajaxOverlay');
 			if(overlay.length != 0) {
		    	overlay.css('left',jQuery(document.body).scrollLeft());  
		    	overlay.css('top',jQuery(document.body).scrollTop());  
 			}    		
 		}
 	);
 		
  };
  
  this.removeOverlay = function() {
  	jQuery('select').show();
  	jQuery('#_ajaxOverlay').remove();
  };

  this.updateContentFromURL = function (url, list, ispost, params, $options) {
      var self = this;

      if ($options == null) {
          $options = {};
      }

      if (typeof ($options) === 'undefined') {
          $options = {};
      }

      this.divList = list;

      self.showBlockUI();

      jQuery.ajax(
    {
        url: url,
        data: params,
        success: function (data, textStatus, XMLHttpRequest) { self.removeBlockUI(); self.processSuccess(data, textStatus, XMLHttpRequest); },
        error: function (XMLHttpRequest, textStatus, errorThrown) { self.removeBlockUI(); self.processError(XMLHttpRequest, textStatus, errorThrown); },
        dataType: 'html',
        complete: $options.complete ? function (XMLHttpRequest, textStatus) { $options.complete(XMLHttpRequest, textStatus); } : function () { },
        type: (ispost ? "POST" : "GET"),
        traditional: true
    });

      return false;
  };


  this.processSuccess = function (data, textStatus, XMLHttpRequest) {
      var self = this;
      //self.removeOverlay();
      if (XMLHttpRequest.readyState == 4) { // Complete
          if (XMLHttpRequest.status == 200) { // OK response 
              //Win1 = open("","ReplyWindow","width=575,height=250,scrollbars=yes,resizable=yes");
              //Win1.document.write('<HTML>');
              //Win1.document.write('<BODY>');
              //Win1.document.write('<textarea COLS=100 ROWS=75 name="Reply">');
              //Win1.document.write(data);
              //Win1.document.write('</textarea>');
              //Win1.document.write('</BODY>');
              //Win1.document.write('</HTML>');
              //Win1.document.close();
              //alert('Response'); 
              try {
                  var updatedDivs = self.getDOMObject(data);
                  self.replaceIdsWithNew(updatedDivs);
              } catch (e) {
                  //alert('RPC: Problem encountered when updating content: ' + e.name + ' ' + e.message);
              }
          } else {
              //alert("RPC: Problem with server response:\n " + textStatus);		
          }
      }
  };
  
  this.fileFrameLoaded = function() {
  	var self = this;
  	var frame = jQuery('#iframeSubmitFile');
  	var frameBody = frame.contents().find('html');	
  	if(frameBody.length != 0) {	
	  	var udivs = frameBody[0].getElementsByTagName("div");
		var updatedDivs = new UDivArray(udivs.length);	
		for(var i=0; i<udivs.length; i++) {
			var id = udivs[i].getAttribute("id");
			if (id != null) {
				updatedDivs[i].name = id;  
				updatedDivs[i].text = udivs[i].innerHTML;
            }
            this.findDialogMessage(id, frameBody[0]);
		} 
	  	self.replaceIdsWithNew(updatedDivs);
  	}
  	
  	//frame.remove();
  	setTimeout(function() { frame.remove() },300);
  };

  this.findDialogMessage = function (id, frame) {
      if (id == 'dialog-message-part' && frame != null) {
          var query = jQuery(frame).contents().find('#dialog-message-part');
          try {
              jQuery('#dialog-message-part').remove();
          } catch (e) {
          }
          var div = jQuery('<div id="dialog-message" title="Application Message" style="display: none;"></div>');
          jQuery(document.body).append(div);
          jQuery('#dialog-message')[0].innerHTML = query[0].innerHTML;

          jQuery('#dialog-message').dialog({ modal: true,
              height: '300',
              width: '300',
              buttons: {
                  Ok: function () {
                      jQuery(this).dialog("close");
                      jQuery('#dialog-message').remove();
                  }
              }
          });

          if (!jQuery('#dialog-message .validation,#dialog-message .error').length) {
              setTimeout(
                    function () {
                        if (jQuery('#dialog-message').length) {
                            jQuery('#dialog-message').dialog("close");
                            jQuery('#dialog-message').remove();
                        }
                    }, 3000);
          }
      }

      if (id == 'dialog-message') {
          jQuery('#dialog-message').dialog({ modal: true,
              height: '300',
              width: '300',
              buttons: {
                  Ok: function () {
                      jQuery(this).dialog("close");
                      jQuery('#dialog-message').remove();
                  }
              }
          });

          if (!jQuery('#dialog-message .validation,#dialog-message .error').length) {
              setTimeout(
                    function () {
                        if (jQuery('#dialog-message').length) {
                            jQuery('#dialog-message').dialog("close");
                            jQuery('#dialog-message').remove();
                        }
                    }, 3000);
          }
      }
  }

  this.getDOMObject = function (txt) {
      var errMsg = "";
      try {
          var div = jQuery('<div id="_ajaxResponse"></div>');
          jQuery(document.body).append(div);
          div.css('display', 'none');
          div.css('height', 0);
          div.css('width', 0);
          div[0].innerHTML = txt;
      } catch (e) {
          errMsg = e.message;
          alert("RPC: Problem parsing response as DOM " + errMsg);
          return null;
      }

      var appendedDiv = document.getElementById("_ajaxResponse");
      var udivs = null; //updated divs

      if (appendedDiv) {
          udivs = appendedDiv.getElementsByTagName("div");
      }

      //create an updated div object array
      var updatedDivs = new UDivArray(udivs.length);

      for (var i = 0; i < udivs.length; i++) {
          var id = udivs[i].getAttribute("id");
          if (id != null) {
              updatedDivs[i].name = id;
              updatedDivs[i].text = udivs[i].innerHTML;
          }
          this.findDialogMessage(id, null);
      }

      if (appendedDiv) {
          jQuery(appendedDiv).remove();
      }

      return updatedDivs;
  };

  this.processError = function(XMLHttpRequest, textStatus, errorThrown) {
  	//self.removeOverlay();
    //alert("RPC: Problem with server response:\n " + errorThrown);
  };

  this.replaceIdsWithNew = function (updatedDivs) {
      var self = this;

      //run the replace on existing divs on the page
      var edivs = document.getElementsByTagName("div");

      for (var i = 0; i < edivs.length; i++) {
          var name = edivs[i].getAttribute("id");
          if (name) {
              var index = -1;
              try {
                  index = updatedDivs.nameIndex(name)
              } catch (e) {
                  index = -1;
              }

              if (index > -1) {
                  var ediv = null;
                  if (name != "") ediv = document.getElementById(name);
                  if (ediv && index != -1 && self.nameInList(name, self.divList)) {
                      var txt = updatedDivs[index].text;
                      var script, scripts = '', regexp = /<script[^>]*>([\s\S]*?)<\/script>/gi;
                      while ((script = regexp.exec(txt))) scripts += script[1] + '\n';
                      if (scripts) { //force the browser to evaluate the scripts in the updated divs
                          var scriptElms = document.getElementsByName(name);
                          if (scriptElms && scriptElms.length > 0) {
                              scriptElms[0].text = scripts;
                              eval(scripts);
                          } else {
                              var jsSection = document.createElement('script');
                              jsSection.type = 'text/javascript';
                              jsSection.name = name;
                              jsSection.text = scripts;
                              document.body.appendChild(jsSection);
                          }
                          //now replace the script sections in the divs--remove them
                          txt = txt.replace(regexp, '');
                      }

                      if (!noAnim) {
                          jQuery(ediv).slideUp('slow');
                      }
                      ediv.innerHTML = txt;
                      $(".chosen-select").chosen();
                      if (!noAnim) {
                          jQuery(ediv).slideDown('slow');
                      }
                  }
              }
          }
      }

  };
 
 this.nameInList = function(value,list) {
  	for(var i=0;i<list.length;i++) {
 		if(value==list[i]) {
			list.splice( i, 1);
 			return true;
 		}
 	}
 	return false;
 };

 this.escape = function(sXml){
    return sXml.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&apos;");
 };

 this.unescape = function(sXml){
    return sXml.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, "\"").replace(/&apos;/g, "'");
 };

}; 

function UDivArray(size) {
	this.array = new Array(size);

	this.div = function() {
		var self = this;
		for(var i=0;i<this.array.length;i++) { 
			self.array[i] = new self.DivObject();
					
		}		
		return self.array;
	};
	
	this.DivObject = function() {	
		this.name = "";
		this.text = "";
	
		this.setProperties = function(name,text) {	
			this.name = name;
			this.text = text;	
		};
		
		this.getProperties = function() {		
			return [this.name,this.text];
		};
		
		Array.prototype.nameIndex = function(x) {
		    for (var i = 0; i < this.length; i++) {	 	
	        	if (this[i].name == x) return i;
	   		}
	   		return -1;	
		};		
						
	};
	
	return this.div();
};