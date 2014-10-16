package com.quakd.web.spring.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

public class FacebookInterceptor implements ConnectInterceptor<Facebook> {
	

	@Override
	public void preConnect(ConnectionFactory<Facebook> connectionFactory,
			org.springframework.util.MultiValueMap<String, String> parameters,
			WebRequest request) {
	}

	@Override
	public void postConnect(Connection<Facebook> connection, WebRequest request) { 
		// TODO Auto-generated method stub
		request.setAttribute("facebook", "1", RequestAttributes.SCOPE_SESSION);
	}



}
