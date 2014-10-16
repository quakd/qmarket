package com.quakd.web.spring;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SocialDetailsServiceImpl implements SocialUserDetailsService {
	
	private static Logger log = Logger.getLogger(SocialDetailsServiceImpl.class);	

	@Override
	public SocialUserDetails loadUserByUserId(String userId)
			throws UsernameNotFoundException, DataAccessException {
		log.info("Attempting to authenticate user " + userId);
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("member"));
		SocialUser user = new SocialUser(userId, "none", authorities);
		return user;
	}

}
