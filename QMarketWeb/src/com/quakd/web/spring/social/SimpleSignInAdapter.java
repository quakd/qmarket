package com.quakd.web.spring.social;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUser;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter  implements SignInAdapter {

	@Override
	public String signIn(String userId, Connection<?> connection,
			NativeWebRequest request) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("member"));
		SocialUser user = new SocialUser(userId, "Not1RealPassword#", authorities);
		
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userId, user, authorities));	
        
		/*
	    SecurityContextHolder.getContext().getAuthentication();
	    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("", "");
	    token.setDetails(new WebAuthenticationDetails(getRequest()));
	    Authentication authentication = authenticationManager.authenticate(token);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
		*/        
		return null;
	}



}
