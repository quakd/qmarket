package com.quakd.web.spring.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

public class CustomProviderController extends ProviderSignInController {

	public CustomProviderController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository repository, SignInAdapter adapter) {
		// TODO Auto-generated constructor stub
		super(connectionFactoryLocator, repository, adapter);
	}

}
