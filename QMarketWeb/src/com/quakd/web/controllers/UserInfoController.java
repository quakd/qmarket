package com.quakd.web.controllers;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.quakd.web.dao.MemberDao;
import com.quakd.web.data.FileUpload;
import com.quakd.web.data.User;
import com.quakd.web.model.Member;
import com.quakd.web.utils.ValidateData;

@Controller
@Scope("request")
public class UserInfoController extends BaseController {
	
	private static Logger log = Logger.getLogger(UserInfoController.class);

	@Autowired
	protected MemberDao memberDao;
	
	@RequestMapping(value = "/secure/updateinfo")
	public ModelAndView updateinfo() {
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");
		Member findMember = memberDao.findMember(user.getId());
		ModelAndView mView = new ModelAndView("user_info", "member", findMember);
		mView.addObject("password", new Member());
		mView.addObject("uploadPhoto", new FileUpload());
		getRequest().setAttribute("navigation", "user_info_nav");
		return mView;		
	}
	
	@RequestMapping(value = "/secure/uploadphoto")
	public ModelAndView uploadphoto(@ModelAttribute("uploadPhoto") FileUpload upload, BindingResult result) throws Exception {
		HttpSession session = getRequest().getSession(true);
		User user = (User) session.getAttribute("user_id");
		if(upload != null) {
			MultipartFile userPic = upload.getFile();
			if(userPic != null) {
				if(userPic.isEmpty()) {
					//addMessage(MessageType.error, "You must select an image to upload.");	
					result.reject("NotEmpty.uploadPhoto", "You must select an image to upload.");
				}
				
				if(userPic.getSize() > 102400) {
					result.reject("Size.uploadPhoto", "Please upload a picture that is not bigger than 100 KB.");
				}
				
				if(!ValidateData.isValidImage(userPic.getContentType())) {
					result.reject("Type.uploadPhoto", "Only JPG, PNG, and GIF image formats are supported.");		
				}				

				if(!result.hasErrors()) {
					user.setQuakdImg(userPic.getBytes());
					user.setQuakdImgType(userPic.getContentType());
					memberDao.setProfileImage(user.getId(), userPic.getBytes(), userPic.getContentType());
				}
			}
		}	

		Member findMember = memberDao.findMember(user.getId());
		ModelAndView mView = new ModelAndView("user_info", "member", findMember);
		mView.addObject("password", new Member());
		mView.addObject("uploadPhoto", new FileUpload());
		getRequest().setAttribute("navigation", "user_info_nav");
		return mView;		
	}	

	@RequestMapping(value = "/secure/updatepassword")
	public ModelAndView updatepassword(@ModelAttribute("password") Member member,  BindingResult result) {
		HttpSession session = getRequest().getSession(true);
		

		if(member.getPassText() == null || member.getPassText().length() < 1) {
			result.reject("NotEmpty.member.passText", "Password cannot be empty.");
		}
		
		if(member.getRepassText() == null || member.getRepassText().length() < 1) {
			result.reject("NotEmpty.member.repassText", "Confirm-Password cannot be empty.");
		}		
		
		if(member.getPassText() != null && !member.getPassText().equals(member.getRepassText())) {
			result.reject("Pattern.member.passMatch", "Password and confirm password must match.");
		}
		
		if(member.getPassText() != null && !ValidateData.passwordIsComplex(member.getPassText())) {
			result.reject("Pattern.member.passText", "Password is not complex.");
		} 		
		
		if(!result.hasErrors()) {
			User user = (User) session.getAttribute("user_id");
			memberDao.updatePassword(user.getId(), member.getPassText());	
			addMessage(MessageType.success, "Updated password successfully.");						
		} else {
			addMessage(MessageType.error, "One or more fields contained errors below.");
		}
		
		User user = (User) session.getAttribute("user_id");
		Member findMember = memberDao.findMember(user.getId());
		ModelAndView mView = new ModelAndView("user_info", "member", findMember);
		mView.addObject("password", member);
		mView.addObject("uploadPhoto", new FileUpload());
		getRequest().setAttribute("navigation", "user_info_nav");
		return mView;			
	}
	
	@RequestMapping(value = "/secure/updatemember")
	public ModelAndView updatemember(@Validated @ModelAttribute("member") Member member, BindingResult result) {
		HttpSession session = getRequest().getSession(true);
		log.info("This has errors " + result.hasErrors());		
		User user = (User) session.getAttribute("user_id");
		if(result.hasErrors() || containsErrors()) {	
			addMessage(MessageType.error, "One or more fields contained errors below.");
		} else {
			member.setUsername(user.getUsername());
			member.setMemberId(user.getId());	
			memberDao.saveMember(member);
			addMessage(MessageType.success, "Updated contact information.");		
		}	
		ModelAndView mView = new ModelAndView("user_info", "member", member);
		mView.addObject("password", new Member());
		mView.addObject("uploadPhoto", new FileUpload());
		getRequest().setAttribute("navigation", "user_info_nav");
		return mView;
	}		

}
