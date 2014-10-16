package com.quakd.web.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quakd.web.data.SocialPostType;
import com.quakd.web.model.CompanyInformation;
import com.quakd.web.model.CompanyLocation;
import com.quakd.web.model.CompanyQponds;
import com.quakd.web.model.Member;
import com.quakd.web.model.MemberFavorites;
import com.quakd.web.model.MemberQidRedeem;
import com.quakd.web.model.MemberQuaks;

@Repository 
public class QuaksDaoImpl implements QuaksDao {
	
	private static Logger log = Logger.getLogger(QuaksDaoImpl.class);	

    @Autowired
    SessionFactory sessionFactory;
 
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Transactional
	public MemberQuaks saveUpdateQuak(final MemberQuaks quaks, final SocialPostType post) {
    	Session session = getCurrentSession();		
		quaks.setSharedOn(post.toString());
		quaks.setCreatedDt(Calendar.getInstance().getTime());   
		quaks.setCompanyQponds(getQpond(quaks.getCompanyLocation().getLocId()));
		session.saveOrUpdate(quaks);
		session.refresh(quaks);
		return quaks;
	}
    
    
	@Transactional   
    @Override
	public MemberQuaks getQuaks(final Long quakId) {
    	Session session = getCurrentSession();		
    	MemberQuaks quakds = (MemberQuaks) session.get(MemberQuaks.class,quakId);
		return quakds;
	}

    @Transactional
	public CompanyQponds getQpond(final Long locId) {
    	Session session = getCurrentSession();		
    	CompanyLocation loc = (CompanyLocation) session.get(CompanyLocation.class,locId);
    	CompanyQponds randQpond =  null;
    	if(loc != null) {
        	Set<CompanyQponds> companyQpondses = loc.getCompanyInformation().getCompanyQpondses();
        	
			double totalWeight = 0.0d;
        	for (CompanyQponds companyQponds : companyQpondses) {
			    totalWeight += companyQponds.getWeight().doubleValue();
			}
        	
			// Now choose a random item
			int randomIndex = -1;
			double random = Math.random() * totalWeight;
			CompanyQponds[] array = (CompanyQponds[]) companyQpondses.toArray(new CompanyQponds[companyQpondses.size()]);
			for (int i = 0; i < array.length; ++i)
			{			
			    random -= array[i].getWeight().doubleValue();
			    if (random <= 0.0d)
			    {
			        randomIndex = i;
			        break;
			    }
			}
			randQpond = array[randomIndex];  
    	}
    	
    	return randQpond;
	}

    @Transactional
	public MemberQidRedeem saveReedem(MemberQuaks quaks, Long memberId, Long qid) {   	
    	Session session = getCurrentSession();
		Query query = session.createQuery("from MemberQidRedeem mr where mr.member.memberId = :id and mr.companyQponds.qid = :qid");
		query.setParameter("id", memberId);
		query.setParameter("qid", qid);
		List<MemberQidRedeem> list = query.list();		
		MemberQidRedeem redeem = null;
		if(list == null || list.size() < 1) {
			redeem = new MemberQidRedeem();
	    	redeem.setMemberQuaks(quaks);
	    	Member member = new Member();
	    	member.setMemberId(memberId);
	    	redeem.setMember(member);
	    	redeem.getCompanyQponds();
	    	redeem.setCdUsed("N");
	    	redeem.setCompanyQponds(quaks.getCompanyQponds());
	    	session.save(redeem);
		}
		
    	return redeem;		
	}
    
    @Transactional	
    public List<MemberQidRedeem> getRedeem(final Long memberId) {
    	Session session = getCurrentSession();
		Query query = session.createQuery("from MemberQidRedeem mr where mr.member.memberId = :id");
		query.setParameter("id", memberId);
		List<MemberQidRedeem> list = query.list();		
		return list;
	}

    @Transactional	
    public MemberQidRedeem checkRedeems(final Long memberId, final Long qid) {
    	Session session = getCurrentSession();
		Query query = session.createQuery("from MemberQidRedeem as mqr where mqr.member.memberId = :id and mqr.companyQponds.qid = :qid");
		query.setParameter("id", memberId);
		query.setParameter("qid", qid);		
		List<MemberQidRedeem> list = query.list();	
		MemberQidRedeem redeem = (list != null && list.size() > 0 ? list.get(0) : null);	
		return redeem;
	}

    @Transactional
	public MemberFavorites saveFavorite(Long locId, Long memberId) {
    	Session session = getCurrentSession();	    	
		Query query = session.createQuery("from MemberFavorites mf where mf.member.memberId = :id and mf.companyLocation.locId = :loc");    	
		query.setParameter("id", memberId);
		query.setParameter("loc", locId);
		List<MemberFavorites> list = query.list();	
		MemberFavorites favorites = null;
		if(list == null || list.size() < 1) {
	    	favorites = new MemberFavorites();
	    	CompanyLocation loc = new CompanyLocation();
	    	loc.setLocId(locId);
	    	Member member = new Member();
	    	member.setMemberId(memberId);
	    	favorites.setCompanyLocation(loc);
	    	favorites.setMember(member);
	    	session.save(favorites);			
		}
    	return favorites;
	}

    @Transactional
	public void removeFavorite(Long favId) {
    	Session session = getCurrentSession();		
    	MemberFavorites fav = (MemberFavorites) session.get(MemberFavorites.class,favId);
    	if(fav != null) {   	
    		session.delete(fav);
    	}
	}
 
    @Transactional
	public void removeQuak(Long quakid) {
    	Session session = getCurrentSession();		
    	MemberQuaks qks = (MemberQuaks) session.get(MemberQuaks.class,quakid);
    	if(qks != null) {   	
    		session.delete(qks);
    	}
	}
   
    @Transactional
	public CompanyQponds getCompanyQpond(Long qid) {
    	Session session = getCurrentSession();		
    	CompanyQponds qponds = (CompanyQponds) session.get(CompanyQponds.class,qid);
		return qponds;	
    }
  
    @Transactional
	public List<MemberFavorites> getFavorites(Long memberId) {
    	Session session = getCurrentSession();
		Query query = session.createQuery("from MemberFavorites mf where mf.member.memberId = :id");
		query.setParameter("id", memberId);
		List<MemberFavorites> list = query.list();		
		return list;
    }   
    
}
