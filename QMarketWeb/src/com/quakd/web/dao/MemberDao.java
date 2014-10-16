package com.quakd.web.dao;

import com.google.common.primitives.Bytes;
import com.quakd.web.model.Member;

public interface MemberDao {

	public void saveMember(final Member member);
	
	public void createSocialAccount(final String username, final String providerId, final String firstName, final String lastName);
	
	public void setProfileImage(final Long id, final byte[] bytes, final String type);

	public void updatePassword(final Long id, final String password);
	
	public void deleteMember(final Member member);

	public Member findMember(final String member);
	
	public Member findMember(final Long id);
	
	public boolean validPassword(final String username, final String password);

}
