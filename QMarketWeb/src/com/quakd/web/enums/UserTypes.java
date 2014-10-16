package com.quakd.web.enums;

public enum UserTypes {
	   
	   Member("M"),
	   Subscriber("S");

	   
	    private UserTypes(final String text) {
	        this.text = text;
	    }

	    private final String text;


	    @Override
	    public String toString() {
	        return text;
	    }	
}
