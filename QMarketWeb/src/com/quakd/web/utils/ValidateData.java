package com.quakd.web.utils;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.quakd.web.controllers.BaseController;
import com.quakd.web.controllers.BaseController.MessageType;
import com.quakd.web.dao.MemberDao;
import com.quakd.web.model.Country;
import com.quakd.web.model.Member;

public class ValidateData {


	private static Logger log = Logger.getLogger(ValidateData.class);	

	public static boolean passwordIsComplex(String password) {
		/*
		 * 
		 * (?=.*[0-9]) a digit must occur at least once (?=.*[a-z]) a lower case
		 * letter must occur at least once (?=.*[A-Z]) an upper case letter must
		 * occur at least once (?=.*[@#*=]) a special character must occur at
		 * least once (?=[\\S]+$) no whitespace allowed in the entire string
		 * .{8,20} at least 8 to 20 characters
		 */
		// at least 8 - 20 characters
		String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		return password.matches(pattern);
	}

	public static boolean validateIsNumber(String number) {
		String pattern = "[0-9]+";
		return number.matches(pattern);
	}
	
	public static boolean validPhoneNumber(String number) {
		String pattern = "\\d{3}-\\d{7}";
		return number.matches(pattern);
	}	
	
	
	public static boolean validEmail(String email) {
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
		return email.matches(pattern);
	}
	
	public static boolean isEmpty(String field) {
		return field == null || field.length() < 1;
	}

	public static boolean fieldBetween(String field, int min, int max) {
		if(!isEmpty(field)) {
			return (field.length() >= min && field.length() <= max);
		}
		return false;
	}
	
	public static boolean isValidImage(String contentType) {
		boolean valid = false;
		if(contentType == null) {
			contentType = "";
		}
		switch(contentType.toLowerCase()) {
			case "image/png":
				valid = true;
				break;
			case "image/jpeg":
				valid = true;
				break;
			case "image/jpg":
				valid = true;
				break;			
			case "image/gif":
				valid = true;
				break;				
			default:
				break;
		}
		return valid;
	}

}
