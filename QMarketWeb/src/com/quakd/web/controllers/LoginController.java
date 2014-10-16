package com.quakd.web.controllers;

import java.net.URL;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.quakd.web.controllers.BaseController.MessageType;
import com.quakd.web.dao.HibernateSearchDao;
import com.quakd.web.dao.MemberDao;
import com.quakd.web.data.EmailBean;
import com.quakd.web.enums.UserTypes;
import com.quakd.web.model.Member;
import com.quakd.web.spring.EmailService;
import com.quakd.web.utils.FileUtil;
import com.quakd.web.utils.ValidateMember;

@Controller
public class LoginController extends BaseController {

	private static Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	protected MemberDao memberDao;
	
	@Autowired
	protected EmailService mailService;		
	
	@RequestMapping(value = "/landing")
	public String landing(HttpSession session, Locale locale, HttpServletResponse response, Device device) {
		log.debug("Send user to main landing page landing, is mobile = " + device.isMobile());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			try {
				UserDetails userDetails = (UserDetails) auth.getPrincipal();
				log.debug("User is authenticated, forwarding to dashboard.");
				return "redirect:/secure/home";
			} catch(Exception ex) {
				log.debug("User is not authenticated, forwarding to main login page.");
			}
			
			try {
				UserDetails userDetails = (UserDetails) auth
						.getCredentials();	
				log.debug("User is authenticated, forwarding to dashboard.");
				return "redirect:/secure/home";
			} catch(Exception ex) {
				log.debug("User is not authenticated, forwarding to main login page.");
			}
		}
		
		return "landing";
	}
	
	@RequestMapping(value = "/createUser") 
	public ModelAndView createUser(@Validated @ModelAttribute("member") Member member, BindingResult result, HttpServletRequest request) {
		//ValidateData.validateNewMember(member, this, memberDao);
		log.info("This has errors " + result.hasErrors());
		ValidateMember valid = new ValidateMember();
		valid.validate(member, result);
		if(result.hasErrors() || containsErrors()) {
			member.setPassText("");
			member.setRepassText("");
			addMessage(MessageType.error, "One or more fields contained errors below.");
		} else {
			//don't give hackers ability to set this
			member.setMemberId(null);
			
			if(memberDao.findMember(member.getUsername()) != null) {
				addMessage(MessageType.error,
						"This email account has already been registered, please use the sign-in or a different email address.");
				return new ModelAndView("signup",  "member", member);	
			}
			
			memberDao.saveMember(member);
			EmailBean emailBean = new EmailBean();
			emailBean.setTo(member.getUsername());
			emailBean.setName(member.getFirstName() + " " + member.getLastName());
			emailBean.setUserId(member.getMemberId());
			emailBean.setUserType(UserTypes.Member);
			
			URL link = GetUrlFromRequest(request);
			emailBean.setUrl(link.toString()+"/signin?email=" + member.getUsername() + "&token=" + member.getMemberId());
			
			mailService.generateRegisterEmail(emailBean);
			//addMessage(MessageType.success, "The account " + member.getUsername() + " has been created successfully.");
			return new ModelAndView("signup_email", "member", member); 
		}		
		
		return new ModelAndView("signup",  "member", member);		
	}



	@RequestMapping(value = "/logout")
	public String logout(Locale locale) {	
		HttpSession session = getRequest().getSession();
		if(session != null) {
			session.invalidate();
		}
		return "forward:/signin"; 
	}
	
	@RequestMapping(value = "/signup")
	public ModelAndView signup(Locale locale, Model model) {	
		return new ModelAndView("signup", "member", new Member());
	}	
	
	@Autowired
	protected HibernateSearchDao searchDao;

	@RequestMapping(value = "/createindex")
	public String createIndex(Locale locale) {
		searchDao.createFullIndex();
		return "landing";
	}	
	
	@RequestMapping(value = "/signin")
	public String signin(Locale locale, String email, String token, String login_error) {
		HttpSession session = getRequest().getSession();
		String attr = (String) session.getAttribute("intercept_message");
		
		if("1".equals(login_error)) {
			addMessage(MessageType.error, "Unable to login, please make sure you provide a correct username and password.");
		}
		else if("2".equals(login_error)) {
			addMessage(MessageType.error, "Unable to login to the selected provider, please try again later.");
		}
		
		if(attr != null) {
			addMessage(MessageType.error, attr);
		}
		
		if(token != null  && email != null) {
			Member member = memberDao.findMember(email);
			if(token.equals(member.getMemberId().toString())) {
				session.setAttribute("email_verified", "1");
			}
		}
		
		if(email != null) {
			Member member = memberDao.findMember(email);
			if(member != null) {
				if("N".equals(member.getEmailVerified()))
				{
					addMessage(MessageType.error, "Unable to login, your email address has not been verified.");
					forceDialog();				
				}
			}
		}
		
		return "signin";
	}		
	
	
	@RequestMapping(value = "/about")
	public String about(Locale locale) {	
		return "about";
	}	
	
	@RequestMapping(value = "/support")
	public String support(Locale locale) {	
		return "support";
	}

	@RequestMapping(value = "/privacy")
	public String privacy(Locale locale) {	
		return "privacy";
	}	

	
}
