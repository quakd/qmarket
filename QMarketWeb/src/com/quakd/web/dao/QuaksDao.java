package com.quakd.web.dao;

import java.util.List;

import com.quakd.web.data.SocialPostType;
import com.quakd.web.model.CompanyQponds;
import com.quakd.web.model.MemberFavorites;
import com.quakd.web.model.MemberQidRedeem;
import com.quakd.web.model.MemberQuaks;

public interface QuaksDao {
	
	public MemberQuaks saveUpdateQuak(MemberQuaks quaks, final SocialPostType type);

	public CompanyQponds getQpond(final Long locId);
	
	public MemberQuaks getQuaks(final Long quakId);
	
	public MemberQidRedeem saveReedem(MemberQuaks quaks, Long memberId, Long qid);

	public  List<MemberQidRedeem> getRedeem(final Long memberId);
	
	public  MemberQidRedeem checkRedeems(final Long memberId, final Long qid);
	
	public CompanyQponds getCompanyQpond(Long qid);
	
	public MemberFavorites saveFavorite(final Long locId, final Long memberId);
	
	public void removeFavorite(final Long favId);
	
	public void removeQuak(final Long quakId);	
	
	public List<MemberFavorites> getFavorites(Long memberId);
}
