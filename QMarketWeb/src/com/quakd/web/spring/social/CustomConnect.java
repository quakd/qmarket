package com.quakd.web.spring.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

public class CustomConnect extends ConnectController {

	public CustomConnect(ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}
	
	@Override
	protected RedirectView connectionStatusRedirect(String providerId,
			NativeWebRequest request) {
		return  new RedirectView("/market/secure/social/connected?providerId=" + providerId + "&");  //super.connectionStatusRedirect(providerId, request);
	}
}
