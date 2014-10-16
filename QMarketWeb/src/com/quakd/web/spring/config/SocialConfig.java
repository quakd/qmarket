package com.quakd.web.spring.config;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.util.WebUtils;

import com.quakd.web.controllers.LoginController;
import com.quakd.web.spring.social.CustomConnect;
import com.quakd.web.spring.social.SimpleSignInAdapter;

@Configuration
public class SocialConfig implements InitializingBean {

	private static Logger log = Logger.getLogger(LoginController.class);

	@Inject
	PropertiesFactoryBean appProperties;

	@Inject
	DataSource dataSource;
	
	@Inject
	HttpServletRequest request;

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		String facebookClientId = "";
		String facebookClientSecret = "";
		String twitterConsumerKey = "";
		String twitterConsumerSecret = "";
		try {
			facebookClientId = appProperties.getObject().getProperty(
					"facebook.clientId");
			facebookClientSecret = appProperties.getObject().getProperty(
					"facebook.clientSecret");
			twitterConsumerKey = appProperties.getObject().getProperty(
					"twitter.consumerKey");
			twitterConsumerSecret = appProperties.getObject().getProperty(
					"twitter.consumerSecret");

		} catch (IOException e) {

		}
		log.info("getting connectionFactoryLocator");
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(
				facebookClientId, facebookClientSecret));
		registry.addConnectionFactory(new TwitterConnectionFactory(
				twitterConsumerKey, twitterConsumerSecret));
		return registry;
	}

	/**
	 * Singleton data access object providing access to connections across all
	 * users.
	 */
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepository() {
		return new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator(), Encryptors.noOpText());
		// return usersConnectionRepositiory;
	}
	

	@Bean	
	public ProviderSignInController providerSignInController() {
		ProviderSignInController controller = new ProviderSignInController(connectionFactoryLocator(), 
	        usersConnectionRepository(), new SimpleSignInAdapter());
		controller.setSignUpUrl("/social/signup");
		controller.setSignInUrl("/social/signin");
		controller.setPostSignInUrl("/social/login");
	    return controller;
	}


	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication == null) {
			throw new IllegalStateException(
					"Unable to get a ConnectionRepository: no user signed in");
		}
		return usersConnectionRepository().createConnectionRepository(
				authentication.getName());
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook() {
		Facebook api = connectionRepository().getPrimaryConnection(
				Facebook.class).getApi();
		return api;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Twitter twitter() {
		Twitter api = connectionRepository()
				.getPrimaryConnection(Twitter.class).getApi();
		return api;
	}

	@Bean
	@Scope(value = "request")
	public SocialContext socialContext() {
		SocialContext socialContext = new SocialContext(
				usersConnectionRepository());
		Cookie fbcookie = WebUtils.getCookie(request,
				"quakd_facebook");
		Cookie tweetcookie = WebUtils.getCookie(request,
				"quakd_twitter");	
		request.setAttribute("social_ctx", socialContext);
		if(fbcookie != null) {
			socialContext.setFacebookCookiePresent(true);
		}
		if(tweetcookie != null) {
			socialContext.setTwitterCookiePresent(true);
		}
		return socialContext;
	}
	
	@Bean
	public ConnectController connectController() {
		CustomConnect connectController = new CustomConnect(
				connectionFactoryLocator(), connectionRepository());
		// connectController.setApplicationUrl("http://localhost:8080/market/secure/callback");
		return connectController;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}