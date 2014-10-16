package com.quakd.web.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.quakd.web.dao.CompanyDao;
import com.quakd.web.dao.MemberDao;
import com.quakd.web.dao.QuaksDao;
import com.quakd.web.data.PostMessageData;
import com.quakd.web.data.User;
import com.quakd.web.model.CompanyInformation;
import com.quakd.web.model.CompanyLocation;
import com.quakd.web.model.CompanyMessage;
import com.quakd.web.model.Member;
import com.quakd.web.model.MemberFavorites;
import com.quakd.web.model.MemberQidRedeem;
import com.quakd.web.spring.config.SocialContext;
import com.quakd.web.utils.FileUtil;

@Controller
@Scope("request")
public class UserHomeController extends BaseController {

	private static Logger log = Logger.getLogger(UserHomeController.class);

	@Autowired
	protected MemberDao memberDao;

	@Autowired
	protected SocialContext socialContext;
	
	@Autowired
	protected CompanyDao companyDao;

	@Autowired
	protected QuaksDao quaksDao;
	
	@RequestMapping(value = "/secure/home")
	public String home(Locale locale) {		
		getRequest().setAttribute("navigation", "home_nav");
		
		return "home";
	}
	
	@RequestMapping(value = "/secure/splash")
	public String splash() {
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");
		if(user != null) {
			user.setSplashScreen(false);
		}
		return "home";
	}
	
	@RequestMapping(value = "/secure/quakd")
	public ModelAndView quakd(@RequestParam(value="id", required=false) Long id) {
		HttpSession session = getRequest().getSession(true);		
		User user = (User) session.getAttribute("user_id");
		getRequest().setAttribute("navigation", "home_nav");
		if(id != null) {
			if(user != null) {
				user.setLocId(id);
			}
			getRequest().setAttribute("company_cd", id);
			CompanyLocation loc = companyDao.companyLocationById(id);
			if(loc != null) {
				ModelAndView view = new ModelAndView("share_message");
				PostMessageData data = new PostMessageData();
				data.setLocId(loc.getLocId());
				view.addObject("post", data);
				Set<CompanyMessage> messages = loc.getCompanyInformation().getCompanyMessages();
				String message = "";
				if(messages != null && !messages.isEmpty()) {
					CompanyMessage msg = messages.iterator().next(); 
					message = msg.getMessage();
					data.setPost(message);
				}
				return view;
			}
		}
		addMessage(MessageType.error, "Unable to locate the business.");
		forceDialog();
		getRequest().setAttribute("navigation", "home_nav");
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/secure/favorites")
	public ModelAndView favorites(@RequestParam(value="id", required=false) Long id) {
		HttpSession session = getRequest().getSession(true);		
		User user = (User) session.getAttribute("user_id");
		getRequest().setAttribute("navigation", "favorites_nav");
		List<MemberFavorites> favs = quaksDao.getFavorites(user.getId());
		ModelAndView views = new ModelAndView("view_favorites");
		if(favs != null) {
			views.addObject("favorites", favs);
		}	
		return views;
	}	

	@RequestMapping(value = "/secure/qponds")
	public ModelAndView qponds(@RequestParam(value="id", required=false) Long id) {
		HttpSession session = getRequest().getSession(true);		
		User user = (User) session.getAttribute("user_id");
		getRequest().setAttribute("navigation", "qponds_nav");
		List<MemberQidRedeem> redeems = quaksDao.getRedeem(user.getId());
		 ModelAndView views = new ModelAndView("view_qponds");
		if(redeems != null) {
			views.addObject("qponds", redeems);
		}		
		return views;
	}	
	
	@RequestMapping(value = "/secure/profileimage")
	public ResponseEntity profileimage(@RequestParam(value="id", required=false) Long id) {	
		byte[] imgBytes = new byte[0];
		String imgType = "text/html";
		log.info("Attempting to load profile image " + id);
		
		if(id == null) {
			HttpSession session = getRequest().getSession(true);
			User user = (User) session.getAttribute("user_id");
			id = user.getId();

			if(user.getQuakdImgType() != null) {
				imgType = user.getQuakdImgType();
				imgBytes = user.getQuakdImg();
			} else {
				Member member = memberDao.findMember(id);	
				if(member != null && member.getMemberImg().length > 0)
				{
					imgBytes = member.getMemberImg();
					imgType = member.getMemberImgType();
					user.setQuakdImg(imgBytes);
					user.setQuakdImgType(imgType);
				}				
			}		   		
		} else {
			Member member = memberDao.findMember(id);	
			if(member != null && member.getMemberImg().length > 0)
			{
				imgBytes = member.getMemberImg();
				imgType = member.getMemberImgType();
			}				
		}

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.setContentType(MediaType.parseMediaType(imgType));
	    responseHeaders.setContentLength(imgBytes.length);			
		
	    return new ResponseEntity<byte[]>(imgBytes,
                responseHeaders, HttpStatus.OK);	
	}	
	
}
