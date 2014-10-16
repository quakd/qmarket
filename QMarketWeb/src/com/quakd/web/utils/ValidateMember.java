package com.quakd.web.utils;

import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.quakd.web.controllers.BaseController;
import com.quakd.web.controllers.BaseController.MessageType;
import com.quakd.web.dao.MemberDao;
import com.quakd.web.model.Country;
import com.quakd.web.model.Member;

public class ValidateMember implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Member member = (Member) target;
		
		if(member.getPassText() != null && !member.getPassText().equals(member.getRepassText())) {
			errors.reject("Pattern.member.passMatch");
		}
		
	}
	
}
