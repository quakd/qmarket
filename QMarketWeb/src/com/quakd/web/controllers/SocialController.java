package com.quakd.web.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.quakd.web.dao.CompanyDao;
import com.quakd.web.dao.MemberDao;
import com.quakd.web.dao.QuaksDao;
import com.quakd.web.data.PostMessageData;
import com.quakd.web.data.RecentPosts;
import com.quakd.web.data.SocialPostType;
import com.quakd.web.data.User;
import com.quakd.web.model.CompanyLocation;
import com.quakd.web.model.Member;
import com.quakd.web.model.MemberQuaks;
import com.quakd.web.spring.config.SocialContext;
import com.quakd.web.utils.ValidateData;

@Controller
@Scope("request")
public class SocialController extends BaseController {

	private static Logger log = Logger.getLogger(SocialController.class);

	@Autowired
	protected MemberDao memberDao;
	
	@Autowired
	protected SocialContext socialContext;
	
	@Autowired
	protected QuaksDao quaksDao;
	
	@Autowired
	protected CompanyDao companyDao;
	
	@Autowired
	protected ProviderManager authenticationManager;
	
	@RequestMapping(value = "/secure/social/feeds")
	public ModelAndView getFeeds() {
		List<RecentPosts> recent = new ArrayList<RecentPosts>();
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");
		// determine what the user is connected to
		if (socialContext != null) {
			Facebook facebook = socialContext.getFacebook();
			if (facebook != null && socialContext.isFacebookCookiePresent()) {
				PagedList<Post> posts = facebook.feedOperations().getPosts();
				int j = 0;
				for (Post post : posts) {
					log.debug(post.getMessage());
					log.debug(post.getType().name());
					if (PostType.STATUS.equals(post.getType())) {
						RecentPosts recentPosts = new RecentPosts();
						recentPosts.setId(post.getId());
						recentPosts.setMessage(post.getMessage());
						recentPosts.setPostDate(post.getCreatedTime());
						recentPosts.setImageUrl(user.getFacebookImageUrl());
						recentPosts.setName(user.getFacebookName());
						if (j >= 5)
							break;
						recent.add(recentPosts);
						j++;
						log.debug(post.getMessage());
						log.debug(post.getName());
					}
				}
			}

			Twitter twitter = socialContext.getTwitter();
			if (twitter != null && socialContext.isTwitterCookiePresent()) {
				List<Tweet> tweets = twitter.timelineOperations()
						.getUserTimeline();
				int x = 0;
				for (Tweet tweet : tweets) {
					log.debug(tweet.getText());
					RecentPosts recentPosts = new RecentPosts();
					recentPosts.setId(String.valueOf(tweet.getId()));
					recentPosts.setMessage(tweet.getText());
					recentPosts.setPostDate(tweet.getCreatedAt());
					recentPosts.setImageUrl(user.getTwitterImageUrl());
					recentPosts.setName(user.getTwitterName());
					if (x >= 5)
						break;
					recent.add(recentPosts);
					x++;

				}
			}
		}
		return new ModelAndView("feeds", "feeds", recent);
	}

	@RequestMapping(value = "/secure/social/posts")
	public ModelAndView post(@ModelAttribute("post") PostMessageData data) {
		String post = data.getPost();
		Long locId = data.getLocId();
		log.info("Attempting to post the following message " + post + " for loc id " + locId);
		getRequest().setAttribute("navigation", "home_nav");	
		if (socialContext == null) {
			log.error("There is a null social context, should not happen");
			return new ModelAndView("landing");
		}
		
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");
		Long memberId = null;
		if (user != null) {
			memberId = user.getId();
			if (user.getLocId() != null) {
				locId = user.getLocId();
				user.setLocId(null);
			}
		}

		
		ModelAndView view = new ModelAndView("message_success");
	
		Facebook facebook = socialContext.getFacebook();
		Twitter twitter = socialContext.getTwitter();
		boolean isFacebook = facebook != null && socialContext.isFacebookCookiePresent();
		boolean isTwitter = twitter != null && socialContext.isTwitterCookiePresent();
		
		SocialPostType postType = SocialPostType.NONE;
		if(isFacebook && isTwitter) {
			postType = SocialPostType.ALL;
		} else if(isTwitter) {
			postType = SocialPostType.TWITTER;
		} else if(isFacebook) {
			postType = SocialPostType.FACEBOOK;
		}
		
		if(SocialPostType.TWITTER.equals(postType) || SocialPostType.ALL.equals(postType)) {
			if(post != null && post.length() > 140) {
				addMessage(MessageType.error,
						"Your message cannot be longer than 140 characters.");
				forceDialog();
				if(data != null) {
					return new ModelAndView("share_message", "post", data);
				}
			}
		}
		
		//get the user to sign in to the social media sites
		if(SocialPostType.NONE.equals(postType)) {
			
			log.debug("Attempting to redirect to facebook sign in.");
			session.setAttribute("POST_DATA", data);
			getRequest().setAttribute("scope", "email,publish_stream,offline_access,read_stream");
			return new ModelAndView("redirect:/signin/facebook");

			/*
			addMessage(MessageType.error,
					"You must connect to facebook or twitter.");
			forceDialog();
			return new ModelAndView("share_message", "post", data);
			*/
		}
		

		boolean error = false;
		String message = null;

		//the post was successful, store in the db
		MemberQuaks quaks = new MemberQuaks();
    	CompanyLocation locs = new CompanyLocation();
    	locs.setLocId(locId);
    	quaks.setCompanyLocation(locs);
    	quaks.setMessage(post);
    	quaks.setLink("temp");
    	
		Member member = new Member();
		member.setMemberId(memberId);
		quaks.setMember(member);
		
		quaks = quaksDao.saveUpdateQuak(quaks, postType);    	
   
		URL url = GetUrlFromRequest(getRequest());
		String linkTxt = url.toString()+"/secure/vendor/qp?qid=" + quaks.getQuakid();		
    	
		quaks.setLink(linkTxt);
		quaks = quaksDao.saveUpdateQuak(quaks, postType);

		CompanyLocation loc = companyDao.companyLocationById(locId);
		try {	
			if (isFacebook) {
				log.info("Posting the message to facebook");	
				PostData postData = new PostData(facebook.userOperations().getUserProfile().getId());
				postData.message(post);
				postData.link("http://www.google.com");
				postData.caption(loc.getCompanyInformation().getCompanyName());
				postData.description(loc.getCompanyInformation().getDescription());
				postData.picture("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/c28.28.345.345/s200x200/1011397_206856356137720_1448424231_n.png");
				facebook.feedOperations().post(postData);
				//facebook.feedOperations().updateStatus(post + linkTxt);
			}
		} catch (Exception ex) {
			error = true;
			log.error("Unable to post to facebook.", ex);
			message = "Unable to post to facebook. " + ex.getLocalizedMessage();
		}

		try {
			if (isTwitter) {
				log.info("Posting the message to twitter");
				TweetData d1 = new TweetData(post + " " + linkTxt);
				d1.atLocation((float) loc.getLon(),(float) loc.getLat());
				twitter.timelineOperations().updateStatus(d1);
			}
		} catch (Exception ex) {
			error = true;
			log.error("Unable to post to twitter.", ex);
			message = "Unable to post to facebook. " + ex.getLocalizedMessage();
		}


		if(!error) {			
			view.addObject("success", Boolean.TRUE);		
			view.addObject("qpond", quaks);
			//save to favorite and add to qponds
			quaksDao.saveFavorite(locId, memberId);  	
			quaksDao.saveReedem(quaks, memberId, quaks.getCompanyQponds().getQid());
			return view;
		} else {
			quaksDao.removeQuak(quaks.getQuakid());
		}
		
		view.addObject("success", Boolean.FALSE);
		view.addObject("post_error", message);
		return view;
	}

	@RequestMapping(value = "/secure/social/logout")
	public ModelAndView logout(String providerId, HttpServletResponse response)
	{
		HttpSession session = getRequest().getSession(true);
		if("facebook".equals(providerId)) {
			Cookie cookie = WebUtils.getCookie(getRequest(), "quakd_facebook");
			if(cookie != null) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			socialContext.removeFacebookConnection();
		} else if("twitter".equals(providerId)) {
			Cookie cookie = WebUtils.getCookie(getRequest(), "quakd_twitter");
			if(cookie != null) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			socialContext.removeTwitterConnection();
		}

		
		User user = (User) session.getAttribute("user_id");	
		if(user != null && socialContext.isSignedIn()) {
			if(user.getLocId() != null) {
				return new ModelAndView("redirect:/secure/quakd?id="+user.getLocId());
			}
		}
		
		getRequest().setAttribute("navigation", "home_nav");
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/social/login")
	public String login(HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			try {

				UserDetails userDetails = null;
				boolean authen = false;
				try {
					userDetails = (UserDetails) auth
							.getPrincipal();
					log.debug(userDetails.getUsername());
					authen = true;
				} catch(Exception ex) {
					log.debug("User is not authenticated, forwarding to main login page.");
					try {
						userDetails = (UserDetails) auth
								.getCredentials();
						log.debug(userDetails.getUsername());
						authen = true;
					} catch(Exception ex2) {
						log.debug("User is not authenticated, forwarding to main login page.");
					}
				}				
				
				if(authen) {
					log.debug("User is authenticated, forwarding to dashboard.");
					String user = userDetails.getUsername();
					HttpSession session = getRequest().getSession(true);
					User u = (User) session.getAttribute("user_id");

					CookieGenerator cookieGen = new CookieGenerator();
					//cookieGen.setCookieDomain("quakd.com");
					cookieGen.setCookieMaxAge(86400);
					cookieGen.setCookieName("quakd_facebook");
					cookieGen.addCookie(response, "1");
					
					return "redirect:/secure/home";
				}
			} catch(Exception ex) {
				log.debug("User is not authenticated, forwarding to main login page.");
			}
			
			
			return "redirect:/secure/home";			
		}
		
		//unable to login to selected to provider
		return "redirect:/signin?login_error=2" ;
	}
	
	@RequestMapping(value = "/social/signup")
	public String signup(WebRequest request) {
		//check to see if we have a member ID for the user
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if(connection != null) {
			String providerId = connection.getKey().getProviderId();
			String providerUserId = connection.fetchUserProfile().getUsername();
			
			if(providerUserId != null && !ValidateData.validEmail(providerUserId)) {
				providerUserId = connection.fetchUserProfile().getEmail();
			}		
			if(providerUserId != null && !ValidateData.validEmail(providerUserId)){
				providerUserId = connection.fetchUserProfile().getUsername() + "@" + providerId; 
			}

			log.info(providerId + " user = " + providerUserId);		
			Member stored = memberDao.findMember(providerUserId);
			
			//create new account for the user
			if(stored != null) {
				ProviderSignInUtils.handlePostSignUp(providerUserId, request);
			} else {			
				UserProfile user = connection.fetchUserProfile();
				log.info("Attempting to save " + providerUserId);
				memberDao.createSocialAccount(providerUserId, providerId, user.getFirstName(), user.getLastName());
				ProviderSignInUtils.handlePostSignUp(providerUserId, request);
			}
			
			return "redirect:/social/login?providerId=" +  providerId;

		}	
		
		//unable to login to selected to provider
		return "redirect:/signin?login_error=2" ;
	}

	@RequestMapping(value = "/secure/social/connected")
	public String connected(String providerId, HttpServletResponse response) {
		// we have connected to one of the social media sites -- lets create a
		// cookie
		// to commemorate the occasion

		if (providerId != null) {
			CookieGenerator cookieGen = new CookieGenerator();
			//cookieGen.setCookieDomain("quakd.com");
			cookieGen.setCookieMaxAge(86400);
			cookieGen.setCookieName("quakd_" + providerId);
			cookieGen.addCookie(response, "1");
		}
		
		HttpSession session = getRequest().getSession(true);		
		User user = (User) session.getAttribute("user_id");
		
		if(user != null) {
			if(user.getLocId() != null) {
				return "redirect:/secure/quakd?id="+user.getLocId();
			}
		}
		
		getRequest().setAttribute("provider_connection", providerId);
		//loadUserInfo(providerId);
		getRequest().setAttribute("navigation", "home_nav");
		return "home";
	}
	
}
