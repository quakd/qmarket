package com.quakd.web.data;

public class Message {
	
	public Message(String type, String message) {
		this.type = type;
		this.message = message;
		
		if("error".equals(type)) {
			setCss("ui-state-error ui-corner-all");
		} else {
			setCss("ui-state-highlight ui-corner-all");
		}
		
		
	}
	
	private String type;
	private String message;
	private String css;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	
	

}
