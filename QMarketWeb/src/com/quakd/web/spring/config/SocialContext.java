package com.quakd.web.spring.config;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;


public class SocialContext {

	private static Logger log = Logger.getLogger(SocialContext.class);

	private UsersConnectionRepository connectionRepository;
	
	private boolean facebookCookiePresent = false;
	
	private boolean twitterCookiePresent = false;

	public SocialContext(UsersConnectionRepository connectionRepository) {
		log.debug("Creating a social context object...");
		this.connectionRepository = connectionRepository;
	}

	public boolean isFacebookSignedIn() {
		log.debug("Checking to see if signed in to facebook.");
		return getFacebookConnection() != null;
	}
	
	public Connection<Facebook> getFacebookConnection() {
		String userId = checkSignIn();
		if(userId == null) {
			return null;
		}
		ConnectionRepository connectionRepo = connectionRepository
				.createConnectionRepository(userId);
		Connection<Facebook> facebookConnection = connectionRepo.findPrimaryConnection(Facebook.class);		
		return facebookConnection;
	}
	
	public void removeFacebookConnection() {
		String userId = checkSignIn();
		if(userId == null) {
			return;
		}
		ConnectionRepository connectionRepo = connectionRepository
				.createConnectionRepository(userId);		
		connectionRepo.removeConnections("facebook");
	}

	public void removeTwitterConnection() {
		String userId = checkSignIn();
		if(userId == null) {
			return;
		}
		ConnectionRepository connectionRepo = connectionRepository
				.createConnectionRepository(userId);		
		connectionRepo.removeConnections("twitter");
	}	

	public Connection<Twitter> getTwitterConnection() {
		String userId = checkSignIn();
		if(userId == null) {
			return null;
		}
		ConnectionRepository connectionRepo = connectionRepository
				.createConnectionRepository(userId);
		Connection<Twitter> twitterConnection = connectionRepo.findPrimaryConnection(Twitter.class);
		return twitterConnection;
	}
	
	public boolean isTwitterSignedIn() {
		log.debug("Checking to see if signed in to twitter.");
		return getTwitterConnection() != null;		
	}	

	private String checkSignIn() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null) {
			UserDetails userDetails = null;
			boolean authen = false;
			try {
				userDetails = (UserDetails) authentication
						.getPrincipal();
				log.debug(userDetails.getUsername());
				authen = true;
			} catch(Exception ex) {
				log.debug("User is not authenticated, forwarding to main login page.");
				try {
					userDetails = (UserDetails) authentication
							.getCredentials();
					log.debug(userDetails.getUsername());
					authen = true;
				} catch(Exception ex2) {
					log.debug("User is not authenticated, forwarding to main login page.");
				}
			}
			if(authen) {
				String userId = userDetails.getUsername();
				return userId;
			}
		}
		return null;
	}
	
	
	public Facebook getFacebook() {
		if(isFacebookSignedIn()) {
			return getFacebookConnection().getApi();
		}
		return null;
	}


	public Twitter getTwitter() {
		if(isTwitterSignedIn()) {
			return getTwitterConnection().getApi();
		}
		return null;
	}

	public boolean isFacebookCookiePresent() {
		return facebookCookiePresent;
	}

	public void setFacebookCookiePresent(boolean facebookCookiePresent) {
		this.facebookCookiePresent = facebookCookiePresent;
	}

	public boolean isTwitterCookiePresent() {
		return twitterCookiePresent;
	}
	
	public boolean isSignedIn() {
		return isFacebookSignedIn() || isTwitterSignedIn();
	}

	public void setTwitterCookiePresent(boolean twitterCookiePresent) {
		this.twitterCookiePresent = twitterCookiePresent;
	}
	
	
	
}
