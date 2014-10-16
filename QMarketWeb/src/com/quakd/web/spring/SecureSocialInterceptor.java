package com.quakd.web.spring;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.quakd.web.dao.MemberDao;
import com.quakd.web.data.User;
import com.quakd.web.model.Member;
import com.quakd.web.spring.config.SocialContext;

@Component
public class SecureSocialInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = Logger.getLogger(SecureSocialInterceptor.class);

	@Autowired
	protected MemberDao memberDao;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.debug("Pre-handle");
		return loadUserInfo(request, response); 
	}


	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("Post-handle");
		loadUserInfo(request, response);
	}

	private boolean loadUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null) {
			HttpSession session = request.getSession(true);
			String connected = (String) request
					.getAttribute("provider_connection");

			User user = (User) session.getAttribute("user_id");
			if (user == null) {
				UserDetails userDetails = null;
				
				boolean auth = false;
				
				try {
					userDetails = (UserDetails) authentication
							.getPrincipal();
					log.debug(userDetails.getUsername());
					auth = true;
				} catch(Exception ex) {
					log.debug("User is not authenticated, forwarding to main login page.");
					session.setAttribute("intercept_message",
							"You must first sign-in to access this page.");
					try {
						userDetails = (UserDetails) authentication
								.getCredentials();
						log.debug(userDetails.getUsername());
						auth = true;
					} catch(Exception ex2) {
						log.debug("User is not authenticated, forwarding to main login page.");
						session.setAttribute("intercept_message",
								"You must first sign-in to access this page.");
					}
				}
				

				
				if(!auth) {
					response.sendRedirect(request.getContextPath() + "/signin");
					return false;
				}

				Member member = memberDao.findMember(userDetails.getUsername());

				String verified = (String) session
						.getAttribute("email_verified");

				if ("1".equals(verified)) {
					member.setEmailVerified("Y");
					memberDao.saveMember(member);
					session.removeAttribute("email_verified");
				}

				if ("N".equals(member.getEmailVerified())) {
					session.setAttribute("intercept_message",
							"Please verify your email address before logging in.");
					response.sendRedirect(request.getContextPath() + "/signin");
					return false;
				}

				user = new User();
				user.setUsername(member.getUsername());
				user.setFirstName(member.getFirstName());
				user.setLastName(member.getLastName());
				user.setSubscriber(false);
				user.setId(member.getMemberId());
				session.setAttribute("user_id", user);

			} else {
				String facebookUrl = null;
				String twitterUrl = null;
				String facebookName = null;
				String twitterName = null;
				boolean connectedToSocial = false;
				SocialContext socialContext = (SocialContext) request
						.getAttribute("social_ctx");
				if (socialContext != null) {
					if (socialContext.isFacebookCookiePresent()
							|| "facebook".equals(connected)) {
						if (socialContext.isFacebookSignedIn()) {
							facebookUrl = socialContext.getFacebookConnection()
									.getImageUrl();
							facebookName = socialContext
									.getFacebookConnection().getDisplayName();
							connectedToSocial = true;
						}
					} else {
						socialContext.setFacebookCookiePresent(false);
					}
					if (socialContext.isTwitterCookiePresent()
							|| "twitter".equals(connected)) {
						if (socialContext.isTwitterSignedIn()) {
							twitterUrl = socialContext.getTwitterConnection()
									.getImageUrl();
							twitterName = socialContext.getTwitterConnection()
									.getDisplayName();
							connectedToSocial = true;
						}
					} else {
						socialContext.setTwitterCookiePresent(false);
					}

					user.setFacebookImageUrl(facebookUrl);
					user.setTwitterImageUrl(twitterUrl);
					user.setFacebookName(facebookName);
					user.setTwitterName(twitterName);
					user.setConnectedToSocial(connectedToSocial);

				}
			}
		}

		return true;
	}

}
