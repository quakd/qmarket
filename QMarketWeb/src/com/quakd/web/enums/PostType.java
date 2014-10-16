package com.quakd.web.enums;

public enum PostType {
	   Twitter("T"),
	   Facebook("F"),
	   Both("B");
	   
	    private PostType(final String text) {
	        this.text = text;
	    }

	    private final String text;


	    @Override
	    public String toString() {
	        return text;
	    }	
}
