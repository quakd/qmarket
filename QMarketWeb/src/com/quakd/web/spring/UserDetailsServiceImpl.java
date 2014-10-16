package com.quakd.web.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.quakd.web.dao.MemberDao;
import com.quakd.web.dao.MemberRolesDao;
import com.quakd.web.model.Member;
import com.quakd.web.model.MemberRoles;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	protected MemberDao memberDao;

	@Autowired
	protected MemberRolesDao memberRolesDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String user)
			throws UsernameNotFoundException {

		log.debug("Attempting to load by username and get roles");
		

		Member member = memberDao.findMember(user);
		if (member == null) {
			throw new UsernameNotFoundException("No user found.");
		}
	

		boolean isActive = "Y".endsWith(member.getActive());
		List<MemberRoles> roles = memberRolesDao.getRolesById(member
				.getMemberId());

		if (roles == null) {
			throw new UsernameNotFoundException("No roles found for the user.");
		}

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (MemberRoles role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		} 
		
		log.debug("Here is the stored password " +member.getPassword());

		User userDetials = new User(member.getUsername(), member.getPassword()
				, isActive, isActive, isActive, isActive,
				authorities);

		log.debug("Found user and returning account details for "
				+ userDetials.getUsername());

		return userDetials;
	}

}
