package com.quakd.web.dao;

import java.util.List;

import com.quakd.web.model.MemberRoles;

public interface MemberRolesDao {
	
	public List<MemberRoles> getRoles(String username);
	
	public List<MemberRoles> getRolesById(Long memberId);

	public void save(MemberRoles memberRole);
	
	public void delete(MemberRoles memberRole);
	
}
