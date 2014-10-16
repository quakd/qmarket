package com.quakd.web.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.quakd.web.data.Message;

@Component
@Scope("request")
public abstract class BaseController {
	
	@Autowired
	private HttpServletRequest request;
	
	public enum MessageType {
	    info, warning, error, success
	}
	
	public BaseController()
	{
		
	}	
	
	public boolean containsErrors()
	{
		List<Message> messages = (List<Message>) request.getAttribute("messages");
		if(messages != null) 
		{
			for (Message message : messages) {
				if(message.getType().equals("error")) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void forceDialog()
	{
		request.setAttribute("forceMessage", Boolean.TRUE);
	}
	
	public void addMessage(MessageType type, String message)
	{
		List<Message> messages = (List<Message>) request.getAttribute("messages");
		if(messages==null) {
			messages = new ArrayList<Message>();
			request.setAttribute("messages", messages);
		}
		messages.add(new Message(type.name(), message));				
	}
	
	protected URL GetUrlFromRequest(HttpServletRequest request) {
		URL link = null;
		try {
			link = new URL(request.getScheme(), 
			        request.getServerName(), 
			        request.getServerPort(), 
			        request.getContextPath());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return link;
	}		

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {	
		this.request = request;
	}

	
}

