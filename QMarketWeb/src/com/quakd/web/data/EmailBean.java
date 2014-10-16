package com.quakd.web.data;

import org.hibernate.usertype.UserType;

import com.quakd.web.enums.UserTypes;

public class EmailBean {
	
	private String name;
	
	private String url;
	
	private String to;
	
	private String subject;
	
	private Long userId;
	
	private String from;
	
	private UserTypes userType;
	
	private String bodyText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public UserTypes getUserType() {
		return userType;
	}

	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}
	

}
